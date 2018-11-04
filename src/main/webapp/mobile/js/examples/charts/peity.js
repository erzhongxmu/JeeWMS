/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    // 默认
    // ---------------------
    (function () {
        /* 动态图示例 */
        var dynamicChart = $("#examplePeityDynamic").peity("line", {
            width: 64,
            fill: [$.colors("purple", 200)],
            stroke: $.colors("purple", 500),
            height: 22
        });

        setInterval(function () {
            var random = Math.round(Math.random() * 10);
            var values = dynamicChart.text().split(",");
            values.shift();
            values.push(random);

            dynamicChart
                .text(values.join(","))
                .change();
        }, 1000);
    })();

    // 红色
    // -------------------
    (function () {
        /* 动态图示例 */
        var dynamicRedChart = $("#examplePeityDynamicRed").peity("line", {
            width: 64,
            fill: [$.colors("red", 200)],
            stroke: $.colors("red", 500),
            height: 22
        });

        setInterval(function () {
            var random = Math.round(Math.random() * 10);
            var values = dynamicRedChart.text().split(",");
            values.shift();
            values.push(random);

            dynamicRedChart
                .text(values.join(","))
                .change();
        }, 1000);
    })();

    // 绿色
    // -------------------
    (function () {
        /* 动态图示例 */
        var dynamicGreenChart = $("#examplePeityDynamicGreen").peity("line", {
            width: 64,
            fill: [$.colors("green", 200)],
            stroke: $.colors("green", 500),
            height: 22
        });

        setInterval(function () {
            var random = Math.round(Math.random() * 10);
            var values = dynamicGreenChart.text().split(",");
            values.shift();
            values.push(random);

            dynamicGreenChart
                .text(values.join(","))
                .change();
        }, 1000);
    })();

    // 桔色
    // --------------------
    (function () {
        /* 动态图示例 */
        var dynamicOrangeChart = $("#examplePeityDynamicOrange").peity("line", {
            width: 64,
            fill: [$.colors("orange", 200)],
            stroke: $.colors("orange", 500),
            height: 22
        });

        setInterval(function () {
            var random = Math.round(Math.random() * 10);
            var values = dynamicOrangeChart.text().split(",");
            values.shift();
            values.push(random);

            dynamicOrangeChart
                .text(values.join(","))
                .change();
        }, 1000);
    })();

})(document, window, jQuery);