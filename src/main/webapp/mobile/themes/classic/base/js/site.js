/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (docuemnt, window, $) {
    'use strict';

    /* globals Breakpoints, screenfull*/

    window.Content = $.extend({}, $.objExtend);

    $.site = $.site || {};

    $.extend($.site, {
        run: function () {

            $.ctx = $('#admui-signOut').data('ctx') || $.ctx;

            function hideNavbar(){
                var $body = $('body');

                $body.addClass('site-navbar-collapsing');
                $('#admui-navbarCollapse').collapse('hide');

                setTimeout(function () {
                    $body.removeClass('site-navbar-collapsing');
                }, 10);

                $body.removeClass('site-navbar-collapse-show');
            }

            if (typeof $.site.menu !== 'undefined') {
                $.site.menu.init();
            }

            if (typeof $.site.contentTabs !== 'undefined') {
                $.site.contentTabs.init();
            }

            $('#admui-navMenu').responsiveHorizontalTabs({ // 导航条响应式
                tabParentSelector: '#admui-navTabs',
                fnCallback: function (el) {
                    if($('#admui-navMenu').is(':visible')) {
                        el.removeClass('is-load');
                    }
                }
            });

            if (typeof $.site.menubar !== 'undefined') { // 导航条&菜单的响应式工作
                $('.site-menubar').on('changing.site.menubar', function () {
                    var $menubar = $('[data-toggle="menubar"]');

                    $menubar.toggleClass('hided', !$.site.menubar.opened);
                    $menubar.toggleClass('unfolded', !$.site.menubar.folded);
                });

                $.site.menubar.init();

                Breakpoints.on('change', function () {
                    $.site.menubar.change();
                });

                /*
                 *  小屏幕下导航条展开 | 收起按钮
                 *  搜索按钮（href）
                 * */
                $(document).on('click', '[data-toggle="collapse"]', function (e) {
                    var $trigger = $(e.target),
                        href, target, $target;

                    if (!$trigger.is('[data-toggle="collapse"]')) {
                        $trigger = $trigger.parents('[data-toggle="collapse"]');
                    }

                    target = $trigger.attr('data-target') || (href = $trigger.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '');
                    $target = $(target);

                    if ($target.hasClass('navbar-search-overlap')) {
                        $target.find('input').focus();

                        e.preventDefault();
                    } else if ($target.attr('id') === 'admui-navbarCollapse') {
                        var isOpen = !$trigger.hasClass('collapsed'),
                            $body = $(document.body);

                        $body.addClass('site-navbar-collapsing');
                        $body.toggleClass('site-navbar-collapse-show', isOpen);

                        $('#admui-navMenu').responsiveHorizontalTabs({
                            tabParentSelector: '#admui-navTabs',
                            fnCallback: function (el) {
                                el.removeClass('is-load');
                            }
                        });

                        setTimeout(function () {
                            $body.removeClass('site-navbar-collapsing');
                        }, 350);
                    }
                });

                $(document).on('click', '[data-toggle="menubar"]', function () { // 菜单展开|收起控制按钮
                    if (Breakpoints.is('xs') && $('body').hasClass('site-menubar-open')){
                        hideNavbar();
                    }

                    $.site.menubar.toggle();
                });

                /*
                 *  菜单收起
                 *  导航条收起
                 * */

                $('.site-page').on('click', '#admui-pageContent', function () {
                    if (Breakpoints.is('xs') && $('body').hasClass('site-menubar-open')){
                        $.site.menubar.hide();

                        hideNavbar();
                    }
                });

                // 图标对应菜单展开
                $('#admui-navMenu >.nav-tabs >li:not(.no-menu)').on('click', function (e) {
                    if ($(e.target).closest('li').is('.dropdown')) {
                        return;
                    }

                    if (Breakpoints.is('xs')) {
                        $.site.menubar.open();
                    }
                });
            }

            if (typeof screenfull !== 'undefined') { // 全屏模式操作
                $(document).on('click', '[data-toggle="fullscreen"]', function () {
                    if (screenfull.enabled) {
                        screenfull.toggle();
                    }

                    return false;
                });
                if (screenfull.enabled) {
                    document.addEventListener(screenfull.raw.fullscreenchange, function () {
                        $('[data-toggle="fullscreen"]').toggleClass('active', screenfull.isFullscreen);
                    });
                }
            }

            /* 对下拉列表的其他功能 */
            $(document).on('show.bs.dropdown', function (e) {
                var $target = $(e.target), $menu,
                $trigger = e.relatedTarget ? $(e.relatedTarget) : $target.children('[data-toggle="dropdown"]'),
                animation = $trigger.data('animation');

                if (animation) {
                    $menu = $target.children('.dropdown-menu');

                    $menu.addClass('animation-' + animation);

                    $menu.one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                        $menu.removeClass('animation-' + animation);
                    });
                }
            });

            $('[data-toggle="tooltip"]').tooltip({trigger: 'hover'});
            $('[data-toggle="popover"]').popover();

            $.components.init();
            window.Content.run();

            this.theme();
            this.pjaxFun();
        },

        theme: function () { // 主题渲染
            if (!window.localStorage) {
                return;
            }

            var $body = $('body'),
                settingsName = 'admui.base.skinTools',
                $link = $('#admui-siteStyle', $('head')),
                settings = localStorage.getItem(settingsName),
                etx = $link.prop('href').indexOf('?v=') === -1 ? '' : '.min' ;

            if (!settings) {
                return;
            }

            settings = JSON.parse(settings);

            if (settings.themeColor && settings.themeColor !== 'primary') {
                $link.attr('href', '/themes/classic/base/skins/' + settings.themeColor + etx + '.css');
            }

            if (settings.sidebar && settings.sidebar === 'site-menubar-light') {
                $('nav.site-menubar').addClass('site-menubar-light');
            }

            if (settings.navbar && settings.navbar !== ''){
                $('.site-navbar').addClass(settings.navbar);
            }

            if (settings.navbarInverse === ''){
                $('.site-navbar').removeClass('navbar-inverse');
            }

            if (settings.menuDisplay && settings.menuDisplay === 'site-menubar-fold') {
                $.site.menubar.fold();

                if (settings.menuTxtIcon && settings.menuTxtIcon === 'site-menubar-keep'){
                    $body.addClass('site-menubar-keep');
                }else{
                    $body.addClass('site-menubar-fold-alt');
                }
            }

            if (settings.tabFlag === '') {
                $body.removeClass('site-contabs-open');
            }

        },

        pjaxFun: function () {
            var $body = $('body');

            $(document).pjax('a[data-pjax]', {replace: true});

            $(document).on('submit', 'form[data-pjax]', function (event) {
                var container = $(this).attr("data-pjax");
                $.pjax.submit(event, container, {replace: true});
            });

            $(document).on('pjax:start', function () {
                window.onresize = null;
                window.App = null;
                window.Content = $.extend({}, $.objExtend);

                $("#admui-pageContent").off();
                $(window).off('resize');
                $('#admui-navMenu').responsiveHorizontalTabs({ // 导航条响应式
                    tabParentSelector: '#admui-navTabs',
                    fnCallback: function (el) {
                        if($('#admui-navMenu').is(':visible')) {
                            el.removeClass('is-load');
                        }
                    }
                });
                $(window).on('resize', $.site.contentTabs.resize);

                $('head').find('script[pjax-script]').remove();
                $body.addClass("site-page-loading");
                $body.find('script:last').nextAll().remove();
                $body.find('nav:first').prevAll(':not(script)').remove();
                $(document).off('click.site.bootbox', '[data-plugin="bootbox"]');
                $(document).off('click.site.alertify', '[data-plugin="alertify"]');

                //清除body标签上新添加的内联样式
                $('body').removeAttr('style');
                $('html').removeAttr('style');

                if($.isFunction($.leavePage)){
                    $.leavePage();
                    $.leavePage = null;
                }

            });

            $(document).on('pjax:callback', function () {
                $.components.init();
                if (window.Content !== null) {
                    window.Content.run();
                }

                $body.removeClass("site-page-loading");

            });

            $(document).on('pjax:success', function () {
                $('[data-toggle="tooltip"]').tooltip();
                $('[data-toggle="popover"]').popover();

                // 清除控制台console信息
                //console.clear();

                // 给标签也换title
                var $labelNav = $(".con-tabs"),
                    title = $.trim($('title').text()),
                    labelTitle = $.trim($labelNav.find('li.active').text());

                if (title !== labelTitle) {
                    $labelNav.find("li.active span").text(title);
                }

            });
        }
    });

    $(document).ready(function () {
        $.site.run();
    });

})(document, window, jQuery);