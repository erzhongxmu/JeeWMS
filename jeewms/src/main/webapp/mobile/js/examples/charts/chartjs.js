/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    Chart.defaults.global.responsive = true;

    // 曲线图示例
    var lineChart = (function () {
        var lineChartData = {
            labels: ["1月", "2", "3月", "4月", "5月", "6月", "7月"],
            scaleShowGridLines: true,
            scaleShowVerticalLines: false,
            scaleGridLineColor: "#ebedf0",
            datasets: [{
                fillColor: "rgba(204, 213, 219, .1)",
                strokeColor: $.colors("blue-grey", 300),
                pointColor: $.colors("blue-grey", 300),
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: $.colors("blue-grey", 300),
                data: [65, 59, 80, 81, 56, 55, 40]
            }, {
                fillColor: "rgba(98, 168, 234, .1)",
                strokeColor: $.colors("purple", 600),
                pointColor: $.colors("purple", 600),
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: $.colors("purple", 600),
                data: [28, 48, 40, 19, 86, 27, 90]
            }]
        };

        return new Chart($("#exampleChartjsLine").get(0).getContext("2d")).Line(lineChartData);
    })();

    // 柱状图示例
    var barChart = (function () {
        var barChartData = {
            labels: ["1月", "2", "3月", "4月", "5月"],
            scaleShowGridLines: true,
            scaleShowVerticalLines: false,
            scaleGridLineColor: "#ebedf0",
            barShowStroke: false,
            datasets: [{
                fillColor: $.colors("blue", 500),
                strokeColor: $.colors("blue", 500),
                highlightFill: $.colors("blue", 500),
                highlightStroke: $.colors("blue", 500),
                data: [65, 45, 75, 50, 60]
            }, {
                fillColor: $.colors("blue-grey", 300),
                strokeColor: $.colors("blue-grey", 300),
                highlightFill: $.colors("blue-grey", 300),
                highlightStroke: $.colors("blue-grey", 300),
                data: [30, 20, 40, 25, 45]
            }]
        };

        return new Chart(document.getElementById("exampleChartjsBar").getContext("2d")).Bar(barChartData);
    })();

    // 雷达图示例
    var radarChart = (function () {
        var radarChartData = {
            labels: ["吃饭", "喝水", "睡觉", "设计", "编码", "娱乐", "跑步"],
            pointLabelFontSize: 14,
            datasets: [{
                fillColor: "rgba(204,213,219,0.35)",
                strokeColor: "rgba(0,0,0,0)",
                pointColor: $.colors("blue-grey", 300),
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: $.colors("blue-grey", 300),
                data: [65, 59, 90, 81, 56, 55, 40]
            }, {
                fillColor: "rgba(250,122,122,0.25)",
                strokeColor: "rgba(0,0,0,0)",
                pointColor: $.colors("red", 500),
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: $.colors("red", 500),
                data: [28, 48, 40, 19, 96, 27, 100]
            }]
        };

        return new Chart(document.getElementById("exampleChartjsRadar").getContext("2d"))
            .Radar(radarChartData, {
                scaleShowLabels: false,
                pointLabelFontSize: 10
            });
    })();

    // 极地区域图示例
    var polarChart =(function () {
        var chartData = [{
            value: 300,
            color: $.colors("red", 600),
            label: "红色"

        }, {
            value: 200,
            color: $.colors("purple", 500),
            label: "蓝色"
        }, {
            value: 100,
            color: $.colors("blue-grey", 200),
            label: "蓝灰色"
        }, {
            value: 50,
            color: $.colors("blue-grey", 300),
            label: "深蓝灰色"
        }];

        return new Chart(document.getElementById("exampleChartjsPloarArea").getContext("2d"))
            .PolarArea(chartData);
    })();

    // 饼状图示例
    var pieChart = (function () {
        var pieData = [{
            value: 50,
            color: $.colors("purple", 500),
            label: "蓝色"
        }, {
            value: 50,
            color: $.colors("blue-grey", 200),
            label: "蓝灰色"
        }];

        return new Chart(document.getElementById("exampleChartjsPie").getContext("2d")).Pie(pieData);
    })();

    // 环形图示例
    var dougChart = (function () {
        var doughnutData = [{
            value: 45,
            color: $.colors("red", 500),
            label: "红色"
        }, {
            value: 15,
            color: $.colors("blue-grey", 200),
            label: "蓝灰色"
        }, {
            value: 60,
            color: $.colors("purple", 500),
            label: "蓝色"
        }];

        return new Chart(document.getElementById("exampleChartjsDonut").getContext("2d"))
            .Doughnut(doughnutData);
    })();

    $.leavePage = function () {
        lineChart.destroy();
        barChart.destroy();
        radarChart.destroy();
        polarChart.destroy();
        pieChart.destroy();
        dougChart.destroy();
    };

})(document, window, jQuery);