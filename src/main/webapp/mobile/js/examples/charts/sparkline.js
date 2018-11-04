/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var $sparklineCompositebarChart = $('.sparkline-compositebar-chart'),
        $sparklineCompositeline = $('.sparkline-compositeline'),
        $sparklineCompositebar = $('.sparkline-compositebar');

    // 基本
    // ---------------
    // 饼状图
    $(".sparkline-pie-chart").sparkline([4, 2, 6], {
        type: 'pie',
        height: '162px',
        sliceColors: [$.colors("purple", 500), $.colors("purple", 700), $.colors("purple", 600)]
    });

    // 折线图
    $(".sparkline-line-chart").sparkline([1, 3, 4, 2, 3, 6, 5, 3], {
        type: 'line',
        height: '162px',
        width: '200px',
        normalRangeMin: 0,
        spotRadius: 2,
        spotColor: $.colors("red", 600),
        highlightSpotColor: $.colors("red", 700),
        lineColor: $.colors("red", 500),
        highlightLineColor: $.colors("red", 500),
        fillColor: $.colors("red", 100)
    });

    // 柱状图
    $(".sparkline-bar-chart").sparkline([4, 7, 3, 2, 5, 6, 8, 5, 4, 8], {
        type: 'bar',
        height: '162px',
        barWidth: 10,
        barSpacing: 6,
        barColor: $.colors("purple", 500),
        negBarColor: $.colors("purple", 600)
    });

    // 组合图
    $sparklineCompositebarChart.sparkline('html', {
        type: 'bar',
        height: '162px',
        barWidth: 10,
        barSpacing: 5,
        barColor: $.colors("blue-grey", 300)
    });

    $sparklineCompositebarChart.sparkline([4, 5, 6, 6, 5, 5, 3, 6, 4, 2], {
        composite: true,
        fillColor: false,
        lineColor: $.colors("purple", 400)
    });

    $sparklineCompositebarChart.sparkline([1, 4, 5, 2, 3, 5, 6, 1, 3, 6], {
        composite: true,
        fillColor: false,
        lineColor: $.colors("red", 400)
    });


    // 类型
    // ---------------
    // 折线图，从标签中取值
    $('.sparkline-line').sparkline('html', {
        height: '32px',
        width: '150px',
        lineColor: $.colors("red", 600),
        fillColor: $.colors("red", 100)
    });

    // 使用内联值的柱状图
    $('.sparkline-bar').sparkline('html', {
        type: 'bar',
        height: '32px',
        barWidth: 10,
        barSpacing: 5,
        barColor: $.colors("purple", 500),
        negBarColor: $.colors("red", 500),
        stackedBarColor: [$.colors("purple", 500), $.colors("red", 500)]
    });

    // 组合折线图，第二个使用JavaScript赋值
    $sparklineCompositeline.sparkline('html', {
        height: '32px',
        width: '150px',
        fillColor: false,
        lineColor: $.colors("purple", 500),
        spotColor: $.colors("green", 500),
        minSpotColor: $.colors("purple", 500),
        maxSpotColor: $.colors("green", 500),
        changeRangeMin: 0,
        chartRangeMax: 10
    });
    $sparklineCompositeline.sparkline([4, 1, 5, 7, 9, 8, 7, 6, 6, 4, 7, 8, 4, 3, 2, 5, 6, 7], {
        composite: true,
        fillColor: false,
        height: '32px',
        width: '150px',
        lineColor: $.colors("red", 500),
        spotColor: $.colors("green", 500),
        minSpotColor: $.colors("purple", 500),
        maxSpotColor: $.colors("green", 500),
        changeRangeMin: 0,
        chartRangeMax: 10
    });

    // 范围折线图
    $('.sparkline-normalline').sparkline('html', {
        fillColor: false,
        height: '32px',
        width: '150px',
        lineColor: $.colors("red", 600),
        spotColor: $.colors("purple", 500),
        minSpotColor: $.colors("purple", 500),
        maxSpotColor: $.colors("purple", 500),
        normalRangeColor: $.colors("blue-grey", 300),
        normalRangeMin: -1,
        normalRangeMax: 8
    });

    // 折线图和柱状图的组合图
    $sparklineCompositebar.sparkline('html', {
        type: 'bar',
        height: '32px',
        barWidth: 10,
        barSpacing: 5,
        barColor: $.colors("purple", 500)
    });

    $sparklineCompositebar.sparkline([4, 1, 5, 7, 9, 9, 8, 7, 6, 6, 4, 7, 8, 4, 3, 2, 2, 5, 6, 7], {
        composite: true,
        fillColor: false,
        lineColor: $.colors("red", 600),
        spotColor: $.colors("purple", 500)
    });

    // 离散图
    $('.sparkline-discrete1').sparkline('html', {
        type: 'discrete',
        height: '32px',
        lineColor: $.colors("purple", 500),
        xwidth: 36
    });

    $('.sparkline-discrete2').sparkline('html', {
        type: 'discrete',
        height: '32px',
        lineColor: $.colors("purple", 500),
        thresholdColor: $.colors("red", 600),
        thresholdValue: 4
    });

    // 子弹图
    $('.sparkline-bullet').sparkline('html', {
        type: 'bullet',
        targetColor: $.colors("red", 500),
        targetWidth: '2',
        performanceColor: $.colors("purple", 600),
        rangeColors: [$.colors("purple", 100), $.colors("purple", 200), $.colors("purple", 400)]
    });

    // 自定义
    $('.sparkline-linecustom').sparkline('html', {
        height: '32px',
        width: '150px',
        lineColor: $.colors("red", 400),
        fillColor: $.colors("blue-grey", 300),
        minSpotColor: false,
        maxSpotColor: false,
        spotColor: $.colors("green", 500),
        spotRadius: 2
    });

    // 三态图
    $('.sparkline-tristate').sparkline('html', {
        type: 'tristate',
        height: '32px',
        barWidth: 10,
        barSpacing: 5,
        posBarColor: $.colors("purple", 500),
        negBarColor: $.colors("blue-grey", 300),
        zeroBarColor: $.colors("red", 500)
    });

    $('.sparkline-tristatecols').sparkline('html', {
        type: 'tristate',
        height: '32px',
        barWidth: 10,
        barSpacing: 5,
        posBarColor: $.colors("purple", 500),
        negBarColor: $.colors("blue-grey", 300),
        zeroBarColor: $.colors("red", 500),
        colorMap: {
            '-4': $.colors("red", 700),
            '-2': $.colors("purple", 600),
            '2': $.colors("blue-grey", 400)
        }
    });

    // 箱图
    $('.sparkline-boxplot').sparkline('html', {
        type: 'box',
        height: '20px',
        width: '68px',
        lineColor: $.colors("purple", 700),
        boxLineColor: $.colors("purple", 400),
        boxFillColor: $.colors("purple", 400),
        whiskerColor: $.colors("blue-grey", 500),
        // outlierLineColor: $.colors("blue-grey", 300),
        // outlierFillColor: false,
        medianColor: $.colors("red", 500)
        // targetColor: $.colors("green", 500)
    });

    $('.sparkline-boxplotraw').sparkline([1, 3, 5, 8, 10, 15, 18], {
        type: 'box',
        height: '20px',
        width: '78px',
        raw: true,
        showOutliers: true,
        target: 6,
        lineColor: $.colors("purple", 700),
        boxLineColor: $.colors("purple", 400),
        boxFillColor: $.colors("purple", 400),
        whiskerColor: $.colors("blue-grey", 500),
        outlierLineColor: $.colors("blue-grey", 300),
        outlierFillColor: $.colors("blue-grey", 100),
        medianColor: $.colors("red", 500),
        targetColor: $.colors("green", 500)
    });

    // 饼状图
    $('.sparkline-pie').sparkline('html', {
        type: 'pie',
        height: '30px',
        sliceColors: [$.colors("purple", 500), $.colors("purple", 700), $.colors("purple", 600)]
    });

    $('.sparkline-pie-1').sparkline('html', {
        type: 'pie',
        height: '30px',
        sliceColors: [$.colors("purple", 500), $.colors("blue-grey", 300)]
    });

})(document, window, jQuery);