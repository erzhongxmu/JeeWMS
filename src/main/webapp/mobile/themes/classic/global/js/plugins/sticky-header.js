/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function ($) {
    "use strict";

    var pluginName = 'stickyHeader',
        defaults = {
            headerSelector: '.header',
            changeHeaderOn: 100,
            activeClassName: 'active-sticky-header',
            min: 50,
            method: 'toggle'
        };

    var Plugin = function (el, options) {
        this.isActive = false;
        this.init(options);
        this.bind();
    };

    Plugin.prototype = {
        init: function (options) {
            var $el = this.$el.css('transition', 'none'),
                $header = this.$header = $el.find(options.headerSelector).css({
                    position: 'absolute',
                    top: 0,
                    left: 0
                });

            this.options = $.extend(true, {}, defaults, options, $header.data());
            this.headerHeight = $header.outerHeight();
            // this.offsetTop()
            // $el.css('transition','all .5s linear');
            // $header.css('transition','all .5s linear');
            this.$el.css('paddingTop', this.headerHeight);
        },

        _toggleActive: function () {
            if (this.isActive) {
                this.$header.css('height', this.options.min);
            } else {
                this.$header.css('height', this.headerHeight);
            }
        },


        bind: function () {
            var self = this;
            this.$el.on('scroll', function () {
                if (self.options.method === 'toggle') {
                    if ($(this).scrollTop() > self.options.changeHeaderOn && !self.isActive) {
                        self.$el.addClass(self.options.activeClassName);
                        self.isActive = true;
                        self.$header.css('height', self.options.min);
                        self.$el.trigger('toggle:sticky', [self, self.isActive]);
                    } else if ($(this).scrollTop() <= self.options.changeHeaderOn && self.isActive) {
                        self.$el.removeClass(self.options.activeClassName);
                        self.isActive = false;
                        self.$header.css('height', self.headerHeight);
                        self.$el.trigger('toggle:sticky', [self, self.isActive]);
                    }
                } else if (self.options.method === 'scroll') {
                    var offset = Math.max(self.headerHeight - $(this).scrollTop(), self.options.min);
                    if (offset === self.headerHeight) {
                        self.$el.removeClass(self.options.activeClassName);
                    } else {
                        self.$el.addClass(self.options.activeClassName);
                    }
                    self.$header.css('height', offset);
                    self.$el.trigger('toggle:sticky', [self]);
                }
            });
        }
    };

    $.fn[pluginName] = function (options) {
        if (typeof options === 'string') {
            var method = options;
            var method_arguments = Array.prototype.slice.call(arguments, 1);
            if (/^_/.test(method)) {
                return false;
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
                    $.data(this, pluginName).init(options);
                }
            });
        }
    };
})(jQuery);
