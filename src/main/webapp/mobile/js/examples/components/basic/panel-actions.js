/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = {
        run: function () {
            var $panel = $('#examplePanel'),
                api = $panel.data('panel-api'),
                $pageContent = $('#admui-pageContent');

            // 全屏
            $pageContent.on('click', '#exampleTogglFullscreene', function () {
                api.toggleFullscreen();
            });

            $pageContent.on('click', '#exampleEnterFullscreen', function () {
                api.enterFullscreen();
            });

            $pageContent.on('click', '#exampleLeaveFullscreen',function () {
                api.leaveFullscreen();
            });

            // 内容
            $pageContent.on('click', '#exampleToggleContent', function () {
                api.toggleContent();
            });

            $pageContent.on('click', '#exampleShowContent', function () {
                api.showContent();
            });

            $pageContent.on('click', '#exampleHideContent', function () {
                api.hideContent();
            });

            // 打开 / 关闭
            $pageContent.on('click', '#exampleToggle', function () {
                api.toggle();
            });

            $pageContent.on('click', '#exampleOpen', function () {
                api.open();
            });

            $pageContent.on('click', '#exampleClose', function () {
                api.close();
            });


            // 刷新
            var even = false;
            $pageContent.on('click', '#exampleReplace', function () {
                api.load(function (done) {
                    var $panel = $(this);
                    var content;

                    if (even) {
                        content = '印度首富穆克什·安巴尼创立的新公司“Reliance Jio”投入运营。从9月5日起，将向全印度数亿人民提供高速廉价的4G网络服务。该公司的4G网络已经覆盖全印度80%的地区。在试运营期间，Jio将向全印度人免费提供服务，直到今年年底。在免费期过后，其数据流量月资费也低至每月149卢比（约合15元人民币）。安巴尼上周在公司年度全体大会上对投资者说：“任何、所有能实现数字化的东西都将快速走向数字化，生活将走向数字化。”目前，只有五分之一的印度成年人口能够上网。在印度，公共WiFi热点极少。城市贫困区缺乏高速宽带所需的基础设备，更不用说乡村地区了。';
                        even = false;
                    } else {
                        content = '上海洛克公园日前宣布获得华人文化产业基金B轮4000万元投资，此前曾获得荣正投资、青松基金投资的数千万天使轮融资。洛克公园创始人戴富祺以美国纽约的街篮圣殿洛克公园为蓝本，打造属于中国的富有接头文化的篮球馆，戴富祺曾透露，洛克公园的目标是在2020年，达到全国50家店，会员达到50万，并启动上市计划。';
                        even = true;
                    }

                    $panel.find('.panel-body').html(content);
                    done();
                });
            });

            $pageContent.on('click', '#exampleLoad', function () {
                api.load();
            });

            $pageContent.on('click', '#exampleDone', function () {
                api.done();
            });
        }
    };
})(document, window, jQuery);

