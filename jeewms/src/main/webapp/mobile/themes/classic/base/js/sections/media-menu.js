/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    var $body = $('body'),
        $html = $('html');

    $.site.menubar = {
        opened: null,
        folded: null,
        top: false,
        foldAlt: false,
        $instance: null,
        auto: true,

        init: function () {
            var self = this;

            $html.removeClass('css-menubar').addClass('js-menubar');

            this.$instance = $("#admui-navTabs");
            this.tagId = $('.nav-tabs li.active > a').attr('href');

            if(this.tagId === '#'){
                this.tagId = $('.nav-tabs li.active').find('ul>li.active>a').attr('href');
            }

            if (this.$instance.length === 0) {
                return;
            }

            // 鼠标经过左侧菜单显示图标
            if ($body.is('.site-menubar-fold-alt')) {
                this.foldAlt = true;
            }

            // 鼠标经过左侧菜单显示文字
            if ($body.is('.site-menubar-keep')) {
                if ($body.hasClass('site-menubar-fold')) { // 收起
                    this.auto = 'fold';
                } else if ($body.hasClass('site-menubar-unfold')) { //展开
                    this.auto = 'unfold';
                }
            }

            this.$instance.on('changed.site.menubar', function () {
                self.update();
            });

            $('.nav-tabs li:not(.no-menu)').on('shown.bs.tab', function (event) {
                var tagId = self.tagId = $(event.target).attr('href');
                if ($body.hasClass('site-menubar-fold')) {
                    self.hoverscroll.enable(tagId);
                } else if ($body.hasClass('site-menubar-unfold')) {
                    self.slimScroll.enable();
                }
            });

            this.change();
        },

        change: function () {
            var breakpoint = Breakpoints.current();

            if (this.auto !== true) {
                switch (this.auto) {
                    case 'fold':
                        this.reset();
                        if (breakpoint.name === 'xs') {
                            this.hide();
                        } else {
                            this.fold();
                        }
                        return;
                    case 'unfold':
                        this.reset();
                        if (breakpoint.name === 'xs') {
                            this.hide();
                        } else {
                            this.unfold();
                        }
                        return;
                }
            }

            this.reset();

            if (breakpoint) {
                switch (breakpoint.name) {
                    case 'lg':
                        this.unfold();
                        break;
                    case 'md':
                    case 'sm':
                        this.fold();
                        break;
                    case 'xs':
                        this.hide();
                        break;
                }
            }
            Breakpoints.on('xs', 'leave', function () {
                $('#admui-navMenu').responsiveHorizontalTabs({
                    tabParentSelector: '#admui-navTabs',
                    fnCallback: function (el) {
                        if ($('#admui-navMenu').is(':visible')) {
                            el.removeClass('is-load');
                        }
                    }
                });
            });
        },

        animate: function (doing, callback) {
            var self = this;
            $body.addClass('site-menubar-changing');

            doing.call(self);
            this.$instance.trigger('changing.site.menubar');

            callback.call(self);
            $body.removeClass('site-menubar-changing');

            self.$instance.trigger('changed.site.menubar');
        },

        reset: function () {
            this.opened = null;
            this.folded = null;
            $body.removeClass('site-menubar-hide site-menubar-open site-menubar-fold site-menubar-unfold');
            $html.removeClass('disable-scrolling');
        },

        open: function () {
            if (this.opened !== true) {
                this.animate(function () {
                    $body.removeClass('site-menubar-hide').addClass('site-menubar-open site-menubar-unfold');
                    this.opened = true;

                    $html.addClass('disable-scrolling');

                }, function () {
                    this.slimScroll.enable();
                });
            }
        },

        hide: function () {
            this.hoverscroll.disable();

            if (this.opened !== false) {
                this.animate(function () {

                    $html.removeClass('disable-scrolling');
                    $body.removeClass('site-menubar-open').addClass('site-menubar-hide site-menubar-unfold');
                    this.opened = false;

                }, function () {
                    this.slimScroll.enable();
                });
            }
        },

        unfold: function () {
            this.hoverscroll.disable();

            if (this.folded !== false) {
                this.animate(function () {
                    $body.removeClass('site-menubar-fold').addClass('site-menubar-unfold');
                    this.folded = false;

                }, function () {
                    this.slimScroll.enable();
                });
            }
        },

        fold: function () {
            this.slimScroll.destroy();

            if (this.folded !== true) {
                this.animate(function () {
                    $body.removeClass('site-menubar-unfold').addClass('site-menubar-fold');
                    this.folded = true;

                }, function () {
                    this.hoverscroll.enable(this.tagId);
                });
            }
        },

        toggle: function () {
            var breakpoint = Breakpoints.current(),
                folded = this.folded,
                opened = this.opened;

            switch (breakpoint.name) {
                case 'lg':
                    if (folded === null || folded === false) {
                        this.fold();
                    } else {
                        this.unfold();
                    }
                    break;
                case 'md':
                case 'sm':
                    if (folded === null || folded === true) {
                        this.unfold();
                    } else {
                        this.fold();
                    }

                    $('#admui-navMenu').responsiveHorizontalTabs({
                        tabParentSelector: '#admui-navTabs'
                    });
                    break;
                case 'xs':
                    if (opened === null || opened === false) {
                        this.open();
                    } else {
                        this.hide();
                    }
                    break;
            }
        },

        update: function () {
            this.hoverscroll.update();
        },

        slimScroll: {
            api: null,
            native: false,
            init: function () {
                // if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                //   this.native = true;
                //   $body.addClass('site-menubar-native');
                //   return;
                // }

                if ($body.is('.site-menubar-native')) {
                    this.native = true;
                    return;
                }

                $.site.menubar.$instance.slimScroll($.po('slimScroll'));
            },

            enable: function () {
                if (this.native) {
                    return;
                }
                this.init();
            },

            destroy: function () {
                $.site.menubar.$instance.slimScroll({destroy: true});
                $.site.menubar.$instance.removeAttr('style');
            }
        },

        hoverscroll: {
            api: null,

            init: function (tagId) {
                $.site.menubar.$instance.find(tagId).children('div').attr('style', '');
                this.api = $.site.menubar.$instance.find(tagId).asHoverScroll({
                    namespace: 'hoverscorll',
                    direction: 'vertical',
                    list: '.site-menu',
                    item: '> li',
                    exception: '.site-menu-sub',
                    fixed: false,
                    boundary: 100,
                    onEnter: function () {
                        //$(this).siblings().removeClass('hover');
                        //$(this).addClass('hover');
                    },
                    onLeave: function () {
                        //$(this).removeClass('hover');
                    }
                }).data('asHoverScroll');
            },

            update: function () {
                if (this.api) {
                    this.api.update();
                }
            },

            enable: function (tagId) {
                if (tagId !== this.tagId) {
                    this.tagId = tagId;
                    this.init(tagId);
                } else {
                    this.api.enable();
                }
            },

            disable: function () {
                if (this.api) {
                    this.api.disable();
                }
            }
        }
    };
})(window, document, jQuery);
