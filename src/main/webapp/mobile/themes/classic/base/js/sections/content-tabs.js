/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    $.site.contentTabs = {
        $instance: $('.site-menu'),
        relative: 0,
        init: function () {
            this.bind();
            this.getPath();

        },
        containerSize: function(){
        	this.labelWidth = this.$label.width();
            this.view = this.$view.width();
        },
        bind: function () {
            var self = this,
                $navContabs = $(".site-contabs"),
                $navContent = $navContabs.find("ul.con-tabs"),
                $label = this.$label = $navContent.find("li"),
                $view = this.$view = $navContabs.find(".contabs-scroll");

            this.containerSize($label, $view);

            $(document).on('click', 'a[data-pjax]', function (e) {
                var $item = $(this), result,
                    title = $item.text(),
                    href = $item.attr('href');

                title = title === '' ? $item.attr('title') : title;
                result = new RegExp(/^([a-zA-z]+:|#|javascript|www\.)/); // 不执行pjax的地址

                if (result.test(href)) {
                    e.preventDefault();
                    return;
                }

                if ($item.is('[target="_blank"]')) {
                    self.buildTag({name: title, url: $item.attr('href')}, e);
                }

            });

            // 标签页的移动  &&  关闭单个标签页
            $navContabs.on('click.site.contabs', 'button.pull-left', function () {
                self.labelPosition($navContent, self.labelWidth, "right");
            }).on('click.site.contabs', '.pull-right>.btn-icon', function () {
                var content = $navContent.width();

                self.labelPosition($navContent, self.labelWidth, "left", self.view, content);
            }).on('click.site.contabs', 'ul.con-tabs>li', function (e) {
                var $target = $(e.target),
                    $item = $(this);
                if ($target.is("i.wb-close-mini") && $item.is(".active")) {
                    self.closeTab();

                    e.preventDefault();
                } else if ($target.is("i.wb-close-mini")) {
                    $item.remove();

                    self.labelSize();
                    self.labelEvent($navContent, 'media');

                    e.preventDefault();
                } else if ($item.is(".active")) {
                    e.preventDefault();
                } else {
                    $item.siblings("li").removeClass("active");
                    $item.addClass("active");

                    self.enable($item);
                }
            });

            // 刷新当前 && 关闭其他 && 所有标签页
            $navContabs.on('click.site.contabs', '.pull-right li.reload-page', function () {
                var URL = $navContabs.find('ul.con-tabs>li.active>a').attr('href');

                $.pjax({
                    url: URL,
                    container: '#admui-pageContent',
                    replace: true
                });

            }).on('click.site.contabs', '.pull-right li.close-other', function () {
                $navContabs.find('ul.con-tabs>li').filter(function () {
                    return !$(this).is('.active') && $(this).index() !== 0;
                }).remove();

                $navContent.animate({left: 0}, 100);

                self.btnView('hide');
            }).on('click.site.contabs', '.pull-right li.close-all', function () {
                var $labels = $navContabs.find('ul.con-tabs>li'),
                    labelsURL = $labels.eq(0).find('a').attr('href');

                $labels.filter(function () {
                    return $(this).index() !== 0;
                }).remove();

                $navContent.animate({left: 0}, 100);

                self.btnView('hide');

                $.pjax({
                    url: labelsURL,
                    container: '#admui-pageContent',
                    replace: true
                });

                $labels.eq(0).addClass('active');
            });

            // 浏览器窗口大小改变,标签页的对应状态
            $(window).on('resize', this.resize);

        },
        resize: function(){
            var $navContabs = $(".site-contabs"),
                $navContent = $navContabs.find("ul.con-tabs");

            $.site.contentTabs.throttle(function () {
                $.site.contentTabs.view = $navContabs.find(".contabs-scroll").width();
                $.site.contentTabs.labelEvent($navContent, 'media');
            }, 200)();
        },
        enable: function ($el) {
            var href = $.trim($el.find('a').attr('href')), tabID,
                self = this;

            var isOpen = function () {
                var $navTabs = $('.nav-tabs'), $activeLi;

                if (self.$instance.parents('div.tab-pane.active').attr('id') !== tabID) {
                    $activeLi = $navTabs.find('a[href="#' + tabID + '"]').parent('li');

                    $('a[href="#' + tabID + '"]').tab('show');
                    $navTabs.find('li').removeClass('active');
                    $activeLi.addClass('active');
                    if($activeLi.parent('ul').hasClass('dropdown-menu')){
                        $activeLi.closest('.dropdown').addClass('active');
                    }
                }

                self.$instance.find('li.has-sub').removeClass('open');
                self.$instance.find('a').parent('li').removeClass('active');

                if (self.$instance.find('a[href="' + href + '"]').parents('li').hasClass('has-sub')) {
                    self.$instance.find('a[href="' + href + '"]').parents('li.has-sub').addClass('open');
                }
            };
            self.$instance.find('a').each(function () {
                var $item = $(this);
                if ($item.attr('href') === href) {
                    tabID = $item.parents('.tab-pane').attr('id');
                    isOpen();
                    $item.parent('li').addClass('active');
                    return false;
                }
            });

        },
        getPath: function () {
            var pathname = location.pathname,
                title = $('#admui-pageContent').find('title').text();

            if (pathname !== $.ctx + '/') {
                this.buildTag({name: title, url: pathname});
            }

            $('#admui-pageContent').find('title').remove();
        },
        buildTag: function (opt, event) { // 新建标签页
            var $labelNav = $(".con-tabs");

            if (event && this.checkTags(opt.url)) {
                event.preventDefault();
                return;
            }

            opt.name = opt.name === '' ? '无标题' : opt.name;

            $('title').text($.trim(opt.name)); //修改页面标题

            if ($labelNav.find("a[href='" + opt.url + "']").size() > 0) {
                return;
            }

            $labelNav.find("li.active").removeClass("active");

            $labelNav.append('<li class="active"><a data-pjax="#admui-pageContent" href="' + opt.url + '" title="' + opt.name + '' +
                '" rel="contents"><span>' + opt.name + '</span><i class="icon wb-close-mini"></i></a></li>');

            this.labelSize();
            this.labelEvent($labelNav, 'media', 'add');
        },
        checkTags: function (url) { // 标签查重
            var $labelNav = $(".con-tabs"),
                $currentLabel = $labelNav.find("a[href='" + url + "']");

            var content = $(".con-tabs").width();

            if ($currentLabel.size() > 0) {
                if ($currentLabel.closest('li').hasClass('active')) {
                    this.app($labelNav, $currentLabel.closest('li'), this.labelWidth, this.view, content);
                    return true;
                } else {
                    $labelNav.find("li.active").removeClass("active");
                    $labelNav.find("a[href='" + url + "']").closest("li").addClass("active");
                    this.app($labelNav, $currentLabel.closest('li'), this.labelWidth, this.view, content);
                    return false;
                }
            } else {
                return false;
            }
        },
        labelSize: function () { // 修改标签页盒子尺寸
            var labelNum, content, $labelNav = $(".con-tabs");

            labelNum = $labelNav.find("li").size();
            content = this.labelWidth * labelNum;
            $labelNav.css("width", content);
        },
        labelEvent: function (doc, media) { // 增删标签页的对应状态
            var content = $(".con-tabs").width();

            if (content > this.view) {
                this.labelPosition(doc, this.labelWidth, "left", this.view, content, media);
                this.btnView('visible');
            } else {
                this.btnView('hide');
            }

            if (this.currentView < this.view || this.currentContent > content) {
                this.labelPosition(doc, this.labelWidth, "right", this.view, content, media);
            }
            this.currentView = this.view;
            this.currentContent = content;
        },
        app: function (doc, $this, width, view, content) {
            var x = doc.position().left,
                prevAll = $this.prevAll('li').size() * width,
                nextAll = $this.nextAll('li').size() * width;

            if (-prevAll < x) {
                if(prevAll + x < view){
                    return false;
                }

                x =  -(prevAll - view + width);
            }else{
                if (-x < content - nextAll) {
                    return false;
                }

                x = -(content - nextAll - width);
            }

            doc.animate({
                left: x
            }, 100);
        },
        labelPosition: function (doc, width, dir, view, content, media) { // 标签页的位移
            var self = this,
                x = doc.position().left,
                callback = function (x) {
                    var flag = x + width;

                    if (flag > 0) {
                        self.relative = x;
                        return 0;
                    } else {
                        return x;
                    }
                };

            if (dir === "left") {
                if (x <= view - content) {
                    return false;
                }
                if (typeof media !== 'undefined') {
                    x = view - content;
                } else {
                    x = this.relative !== 0 ? x - width + this.relative : x - width;
                    this.relative = 0;
                }
            } else if (dir === "right") {
                if (x === 0) {
                    return false;
                }

                if (typeof media !== 'undefined') {
                    x = content <= view ? 0 : view - content;
                } else {
                    x = callback(x + width);
                }
            }

            doc.animate({
                left: x
            }, 100);
        },
        throttle: function (fn, interval) { // 函数节流操作
            var _fn = fn,
                timer,
                firstTime = true;
            return function () {
                var args = arguments,
                    self = this;

                if (firstTime) {
                    _fn.apply(self, args);
                    firstTime = false;
                }

                if (timer) {
                    return false;
                }

                timer = setTimeout(function () {
                    clearTimeout(timer);
                    timer = null;
                    _fn.apply(self, args);
                }, interval || 500);
            };
        },
        closeTab: function () {
            var $navContent = $(".site-contabs ul.con-tabs"),
                $item = $navContent.find('li.active'), labelsURL;

            this.$instance.find('.active').removeClass('active');

            if ($item.next("li").size() > 0) {
                labelsURL = $item.next("li").find("a").attr("href");

                $item.next("li").addClass("active");
            } else {
                labelsURL = $item.prev("li").find("a").attr("href");

                $item.prev("li").addClass("active");
            }

            $item.remove();

            this.labelSize();
            this.labelEvent($navContent, 'media');

            $.pjax({
                url: labelsURL,
                container: '#admui-pageContent',
                replace: true
            });

            this.$instance.find("a[href='" + labelsURL + "']").parent('li').addClass('active');
        },
        btnView: function (status) { // 标签页左右移动按钮状态
            var $siteContabs = $('.site-contabs'),
                $contabsLeftBtn = $siteContabs.children('button.pull-left'),
                $contabsRightBtn = $siteContabs.find('.pull-right > button.btn-icon');

            if (status === 'visible') {
                $contabsLeftBtn.removeClass('hide');
                $contabsRightBtn.removeClass('hide');
            } else if (status === 'hide') {
                $contabsLeftBtn.addClass('hide');
                $contabsRightBtn.addClass('hide');
            }
        }
    };
})(window, document, jQuery);
