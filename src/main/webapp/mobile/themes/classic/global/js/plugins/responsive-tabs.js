/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    var Plugin,
        pluginName = 'responsiveHorizontalTabs',
        defaults = {
            navSelector: '.nav-tabs',
            itemSelector: '>li',
            dropdownSelector: '>.dropdown',
            dropdownItemSelector: 'li',
            tabParentSelector: '',
            tabSelector: '.tab-pane',
            activeClassName: 'active',
            noNavClassName: '.no-menu',
            closeSelector: '.close-tab',
            closeNav: true,
            fnCallback: ''
        };
    Plugin = function (el, options) {
        var $tabs = this.$tabs = $(el),
            opts = this.options = $.extend(true, {}, defaults, options),
            $nav = this.$nav = $tabs.find(opts.navSelector),
            $dropdown = this.$dropdown = $nav.find(opts.dropdownSelector);

        this.$items = $nav.find(opts.itemSelector).filter(function () {
            return !$(this).is($dropdown);
        });

        this.$dropdownItems = $dropdown.find(opts.dropdownItemSelector);

        if (opts.tabParentSelector !== ''){
            this.$tabPanel = $(opts.tabParentSelector).find(opts.tabSelector);
        }else{
            this.$tabPanel = $tabs.find(opts.tabSelector);
        }

        this.init();
    };

    Plugin.prototype = {
        init: function () {
            var length = this.itemsLenth = this.$items.length, dropWidth;

            if (length === 0) {
                throw 'There should be some tags here ';
            }

            if (this.$dropdown.length === 0) { // 没有下拉菜单
                this.flag = true;

                this.$nav.append('<li class="dropdown" role="presentation">'
                    + '<a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">'
                    + '<span class="caret"></span> 更多</a><ul class="dropdown-menu" role="menu"></ul></li>');

                this.$dropdown = this.$nav.find(this.options.dropdownSelector);
                this.$dropdown.css("opacity", 0); // 还需要将其隐藏起来
                dropWidth = this.$dropdown.width();
                dropWidth = dropWidth === 0 ? 90 : dropWidth;
                this.$dropdown.addClass("hidden").css("opacity", 1);
            } else {
                dropWidth = this.$dropdown.width();
            }

            this.breakpoints = [];
            for (var i = 0; i < length + 1; i++) {  // 按照标签数划分宽度等级
                var itemWidth = this.$items.eq(i).width(), widthLevel = 0;

                switch (i) {
                    case 0:
                        widthLevel = itemWidth + dropWidth;
                        break;
                    case length - 1:
                        widthLevel = this.breakpoints[i - 1] + itemWidth - dropWidth;
                        break;
                    case length:
                        widthLevel = this.breakpoints[i - 1] + dropWidth;
                        break;
                    default:
                        widthLevel = this.breakpoints[i - 1] + itemWidth;
                }
                this.breakpoints.push(widthLevel);
            }

            if (typeof this.options.fnCallback === 'function') {
                this.options.fnCallback(this.$tabs);
            }

            this.bind();
            this.layout();
            if (this.options.closeNav) {
                this.close();
            }
        },
        layout: function () {
            if (this.breakpoints.length <= 0) {
                return;
            }

            var width = this.$tabs.width() - 30, // 减去滚动条宽度
                i = 0,
                self = this,
                activeClassName = this.options.activeClassName,
                panelIndex = this.$tabPanel.filter('.' + activeClassName).index(),
                fn =function(i){
                    var v = i;

                    if (i === self.itemsLenth) {
                        v = i - 1;
                    }
                    for (; v < self.itemsLenth; v++) {
                        if(self.flag){
                            self.$dropdown.find("ul").append(self.$items.eq(v).prop("outerHTML"));
                        }else{
                            self.$dropdown.find('ul>li'+self.options.noNavClassName+':first')
                                .before(self.$items.eq(v).prop("outerHTML"));
                        }

                        self.$items.eq(v).hide();
                    }
                },
                callback =function(i){
                    for (var j = 0; j < self.itemsLenth + 1; j++) {
                        if (j < i) {
                            self.$items.eq(j).show();
                        } else {
                            fn(i);
                            self.$dropdown.find("ul>li").show();
                            break;
                        }
                    }
                    self.$dropdownItems = self.$dropdown.find(self.options.dropdownItemSelector);
                };

            for (; i < this.breakpoints.length; i++) { // 有一个宽度大于导航条的宽度
                if (this.breakpoints[i] > width) {
                    break;
                }
            }

            this.$items.removeClass(activeClassName);
            this.$dropdownItems.removeClass(activeClassName);
            this.$dropdown.removeClass(activeClassName);

            if (i === this.breakpoints.length) {
                if(this.flag){
                    this.$dropdown.addClass("hidden");
                }else{
                    this.$dropdown.find('ul>li:not(li'+this.options.noNavClassName+')').remove();
                }
                this.$items.show();
                this.$items.eq(panelIndex).addClass(activeClassName);
            } else {
                this.$dropdown.removeClass("hidden");
                if(this.flag){
                    this.$dropdown.find("ul>li").remove();
                }else{
                    this.$dropdown.find('ul>li:not(li'+this.options.noNavClassName+')').remove();
                }

                callback(i);

                if (panelIndex < i) {
                    this.$items.eq(panelIndex).addClass(activeClassName);
                } else {
                    this.$dropdown.addClass(activeClassName);
                    this.$dropdownItems.eq(panelIndex - i).addClass(activeClassName);
                }
            }
        },
        close: function () {
            var self = this;

            // 标签可关闭项
            this.$tabs.on('click', this.options.closeSelector, function(e){
                var $this = $(this),
                    $toggle = $this.closest('[data-toggle="tab"]'),
                    selector = $toggle.data('target'),
                    $li = $toggle.parent('li');

                if (!selector) {
                    selector = $toggle.attr('href');
                    selector = selector && selector.replace(/.*(?=#[^\s]*$)/, '');
                }

                if ($li.hasClass('active')) {
                    var $next = $li.siblings().eq(0).children('[data-toggle="tab"]');
                    if ($next.length > 0) {
                        var api = $next.tab().data('bs.tab');
                        api.show();
                    }
                }

                var $parent = $(selector);
                if (e) {
                    e.preventDefault();
                }

                $parent.trigger(e = $.Event('close.bs.tab'));

                if (e.isDefaultPrevented()) {
                    return;
                }

                $parent.removeClass('in');

                function refresh(){
                    self.$dropdown.find("ul>li:first").remove();

                    if(self.$dropdown.find('ul>li').length === 0) {
                        self.$dropdown.remove();
                    }
                }

                function removeElement() {
                    // detach from parent, fire event then clean up data
                    $parent.detach().trigger('closed.bs.tab').remove();
                    $li.detach().remove();
                    refresh();
                    self.init();
                }

                $.support.transition && $parent.hasClass('fade') ?
                    $parent
                        .one('bsTransitionEnd', removeElement)
                        .emulateTransitionEnd(150) :
                    removeElement();
            });
        },
        throttle: function (fn, interval) {
            var _fn = fn,
                timer,
                firstTime = true;
            return function () {
                var args = arguments,
                    _self = this;

                if (firstTime) {
                    _fn.apply(_self, args);
                    firstTime = false;
                }

                if (timer) {
                    return false;
                }

                timer = setInterval(function () {
                    clearInterval(timer);
                    timer = null;
                    _fn.apply(_self, args);
                }, interval || 500);
            };
        },
        bind: function () {
            var self = this;

            $(window).resize(function () {
                self.throttle(function () {
                    self.layout();
                }, 1000)();
            });
        }
    };

    $.fn[pluginName] = function (options) {
        if (typeof options === 'string') {
            var method = options,
                method_arguments = Array.prototype.slice.call(arguments, 1);
            if (/^_/.test(method)) {
                console.error('No such method : ' + options);
            } else {
                return this.each(function () {
                    var api = $.data(this, pluginName);
                    if (api && typeof api[method] === 'function') {
                        api[method].apply(api, method_arguments);
                    }
                });
            }
        } else {
            return this.each(function () {
                if (!$.data(this, pluginName)) {
                    $.data(this, pluginName, new Plugin(this, options));
                } else {
                    $.data(this, pluginName).init();
                }
            });
        }
    };
})(window, document, jQuery);