/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    var options = $.extend(true, {}, {
        skipLabel: "<i class='wb-close'></i>",
        doneLabel: "<i class='wb-close'></i>",
        nextLabel: "下一个<i class='wb-chevron-right-mini'></i>",
        prevLabel: "<i class='wb-chevron-left-mini'></i>上一个",
        showBullets: false,
        hintButtonLabel: '完成',
        steps: [{
            element: "#admui-toggleMenubar",
            position: "right",
            intro: "菜单 <p class='content'>点击图标展开或收起左侧导航菜单</p>"
        }, {
            element: "#admui-navbarSubMenu",
            position: "right",
            intro: "更多 <p class='content'>点击展开显示更多菜单，并可以设置菜单</p>"
        }, {
            element: "#admui-navbarMessage",
            position: "left",
            intro: "消息 <p class='content'>Admui 所有的消息都可以在这里找得到哦~~</p>"
        }, {
            element: "#admui-navbarDisplay",
            position: "left",
            intro: "布局 <p class='content'>您可以根据自己的喜好，从这里设置 Admui 的布局、主题、色彩等</p>"
        }, {
            element: "#admui-navbarFullscreen",
            position: "left",
            intro: "全屏 <p class='content'>切换到全屏模式或退出全屏模式</p>"
        }, {
            element: "#admui-siteConTabs",
            position: "bottom",
            intro: "页签 <p class='content'>小页签，大世界 ^_^</p>"
        }]
    });
    $.configs.set('tour', options);

    window.Content = {
        startTour: function (flag) {
            if (typeof this.tour === 'undefined') {
                if (typeof introJs === 'undefined') {
                    return;
                }

                var tourOptions = $.configs.get('tour'),
                    self = this;
                flag = $('body').css('overflow');
                this.tour = introJs();

                this.tour.onbeforechange(function () {
                    $('body').css('overflow', 'hidden');
                });

                this.tour.oncomplete(function () {
                    $('body').css('overflow', flag);
                });

                this.tour.onexit(function () {
                    $('body').css('overflow', flag);
                });

                this.tour.setOptions(tourOptions);
                $('.site-tour-trigger').on('click', function () {
                    self.tour.start();
                });
            }

            if (!(window.localStorage && window.localStorage.getItem('startTour') && (flag !== true))) {
                this.tour.start();
                window.localStorage.setItem('startTour', true);
            }
        },
        run: function () {
            this.startTour();
        }
    };
})(window, document, jQuery);