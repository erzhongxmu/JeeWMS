/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document) {
    'use strict';

    if ($('#admui-pageContent').find('#accountContent').size() > 0) {
        $('#displayForm').prepend('<input type="hidden" name="userId" value="' + $('#admui-signOut')
                .attr("data-user") + '">');
    }

    if (!window.localStorage) {
        return;
    }

    if (document.addEventListener) {
        var Storage = {
            set: function (key, value) {
                if (!window.localStorage) {
                    return null;
                }

                if (!key || !value) {
                    return null;
                }

                if (typeof value === "object") {
                    value = JSON.stringify(value);
                }

                localStorage.setItem(key, value);
            },
            get: function (key) {
                if (!window.localStorage) {
                    return null;
                }

                var value = localStorage.getItem(key);

                if (!value) {
                    return null;
                }

                if (typeof value === 'string') {
                    value = JSON.parse(value);
                }

                return value;
            }
        };

        var Skintools = {
            storageKey: 'admui.base.skinTools',
            path: $.ctx + '/themes/classic/base',
            $siteSidebar: $('.site-menubar'),
            $siteNavbar: $('.site-navbar'),
            navbarSkins: 'bg-primary-600 bg-brown-600 bg-cyan-600 bg-green-600 bg-grey-600 bg-indigo-600 bg-orange-600 bg-pink-600 bg-purple-600 bg-red-600 bg-teal-600 bg-yellow-700',
            defaultSettings: {
                sidebar: 'site-menubar-dark',
                navbar: 'bg-primary-600',
                navbarInverse: 'navbar-inverse',
                themeColor: 'primary',
                menuDisplay: 'site-menubar-unfold',
                menuTxtIcon: 'site-menubar-keep',
                tabFlag: 'site-contabs-open'
            },
            init: function () {
                var self = this,
                    $pageContent = $('#admui-pageContent');

                $pageContent.on('change', '#skintoolsSidebar', function () { // 菜单主题
                    self.sidebarEvents($(this));
                });
                $pageContent.on('click', '#skintoolsNavbar input', function () { // 导航条颜色
                    self.navbarEvents($(this));
                });
                $pageContent.on('click', '#skintoolsPrimary input', function () { // 主题颜色
                    self.primaryEvents($(this));
                });

                $pageContent.on("change", 'input[name="menuDisplay"]', function () { // 菜单显示
                    var $menuFold = $("#menuFoldSetting"),
                        value = $(this).val();
                    if (value === 'site-menubar-unfold') {
                        $menuFold.addClass("hidden");
                        $.site.menubar.unfold();
                    } else {
                        $menuFold.removeClass("hidden");
                        $.site.menubar.fold();
                    }
                    self.updateSetting('menuDisplay', value);
                });

                // 鼠标经过左侧菜单时显示文字 | 图标
                $pageContent.on("change", 'input[name="menuTxtIcon"]', function () {
                    var value = $(this).val();
                    if (value === 'site-menubar-keep') { // 显示文字
                        $("body").removeClass("site-menubar-fold-alt").addClass("site-menubar-keep");
                    } else {
                        $("body").removeClass("site-menubar-keep").addClass("site-menubar-fold-alt");
                    }
                    self.updateSetting('menuTxtIcon', value);
                });

                $pageContent.on('change', 'input[name="tabFlag"]', function () { // Tab页签
                    var value = $(this).val();
                    if (value === 'site-contabs-open') {
                        $('#admui-siteConTabs ul.con-tabs').removeAttr('style');
                        $("body").addClass("site-contabs-open");

                        $.site.contentTabs.containerSize();
                    } else {
                        $("body").removeClass("site-contabs-open");
                    }
                    self.updateSetting('tabFlag', value);
                });

                $pageContent.on("click", "button[name='save']", function (e) {
                    e.preventDefault();
                    var settings = Storage.get(self.storageKey);

                    if (settings === null) {
                        settings = self.defaultSettings;
                    }

                    // console.info(settings); //可以保存数据到后台
                });

                $pageContent.on('click', '#skintoolsReset', function () { // 恢复默认
                    self.reset();
                });

                $('#skintoolsSidebar').selectpicker($.po('selectpicker'));

                this.initLocalStorage();
            },
            initLocalStorage: function () {
                this.settings = Storage.get(this.storageKey);

                if (this.settings === null) {
                    this.settings = $.extend(true, {}, this.defaultSettings);

                    Storage.set(this.storageKey, this.settings);
                }

                if (this.settings && $.isPlainObject(this.settings)) {
                    $.each(this.settings, function (n, v) {
                        switch (n) {
                            case 'boxLayout':
                                $('#boxLayout', '#admui-pageContent').prop('checked', v !== "");
                                break;
                            case 'sidebar':
                                $('#skintoolsSidebar').selectpicker('val', [v]);
                                break;
                            case 'navbar':
                                $('input[value="' + v + '"]', $("#skintoolsNavbar>ul")).prop('checked', true);
                                break;
                            case 'navbarInverse':
                                $('#navbarDisplay', '#admui-pageContent').prop('checked', v !== '');
                                break;
                            case 'menuDisplay':
                                $('input[value="' + v + '"]', '#displayForm').prop('checked', true);
                                break;
                            case 'menuTxtIcon':
                                if ($('#menuFold').is(':checked')) {
                                    $('#menuFoldSetting').removeClass('hidden ');
                                    $('input[name="menuTxtIcon"]', '#displayForm').parent('label')
                                        .removeClass('active');
                                    $('input[value="' + v + '"]', '#displayForm').prop('checked', true)
                                        .parent('label').addClass('active');
                                }
                                break;
                            case 'themeColor':
                                $('input[value="' + v + '"]', "#skintoolsPrimary").prop('checked', true);
                                break;
                            case 'tabFlag':
                                $('input[value="' + v + '"]', '#displayForm').prop('checked', true);
                        }
                    });
                }
            },

            updateSetting: function (item, value) {
                this.settings[item] = value;

                Storage.set(this.storageKey, this.settings);
            },
            sidebarEvents: function ($item) {
                var val = $item.val();

                this.sidebarImprove(val);
                this.updateSetting('sidebar', val);
            },
            navbarEvents: function ($item) {
                var val = $item.val(),
                    checked = $item.prop('checked');

                this.navbarImprove(val, checked);

                if (val === 'navbar-inverse') {
                    this.updateSetting('navbarInverse', checked ? val : '');
                } else {
                    this.updateSetting('navbar', val);
                }
            },
            primaryEvents: function ($item) {
                var val = $item.val();

                this.primaryImprove(val);

                this.updateSetting('themeColor', val);
            },
            sidebarImprove: function (val) {
                if (val === 'site-menubar-light') {
                    this.$siteSidebar.removeClass('site-menubar-dark').addClass(val);
                }
                else {
                    this.$siteSidebar.removeClass('site-menubar-light').addClass(val);
                }
            },
            navbarImprove: function (val, checked) {
                if (val === 'navbar-inverse') {
                    checked ? this.$siteNavbar.addClass(val) : this.$siteNavbar.removeClass(val);
                }
                else {
                    this.$siteNavbar.removeClass(this.navbarSkins).addClass(val);
                }
            },
            primaryImprove: function (val) {
                var $link = $('#admui-siteStyle', $('head')), href,
                    etx = $link.prop('href').indexOf('?v=') === -1 ? '' : '.min' ;

                if (val === 'primary') {
                    href = this.path + '/css/site' + etx + '.css';
                }
                else {
                    href = this.path + '/skins/' + val + etx + '.css';
                }

                $link.attr('href', href);
            },
            reset: function () {
                localStorage.clear();
                location.reload(true);
            }
        };

        Skintools.init();
    }

})(window, document);
