/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = {
        gaugeLine: function () { // 仪表盘--动态变化
            var dynamicGauge = document.getElementById("exampleDynamicGauge"), random;

            var options = {
                strokeColor: $.colors("purple", 500)
            };

            setInterval(function () {
                random = Math.round(Math.random() * 1000);
                if (random > 700) {
                    options.strokeColor = $.colors("pink", 500);
                } else if (random < 300) {
                    options.strokeColor = $.colors("green", 500);
                }

                var gauge = new Gauge(dynamicGauge).setOptions($.po('gauge', options));
                gauge.maxValue = 1000;
                gauge.set(random);
            }, 1500);
        },
        gagueDonut: function () { // 环形图--动态变化
            var dynamicDonut = document.getElementById("exampleDynamicDonut"), random;

            var options = {
                strokeColor: $.colors("purple", 500)
            };
            setInterval(function () {
                random = Math.round(Math.random() * 1000);
                if (random > 700) {
                    options.strokeColor = $.colors("pink", 500);
                } else if (random < 300) {
                    options.strokeColor = $.colors("green", 500);
                }

                var donut = new Donut(dynamicDonut).setOptions($.po('donut', options));
                donut.animationSpeed = 50;
                donut.maxValue = 2000;
                donut.set(random);
            }, 1500);
        },
        run: function () {
            this.gagueDonut();
            this.gaugeLine();
        }
    };

})(document, window, jQuery);