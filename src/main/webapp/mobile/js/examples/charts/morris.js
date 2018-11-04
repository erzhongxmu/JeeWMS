/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = {
        run: function () {
            this.mLine();
            this.mArea();
            this.mBar();
            this.mDonut();

        },
        mLine: function () { // 曲线图示例
            Morris.Line({
                element: 'exampleMorrisLine',
                data: [{
                    "y": "2016 Q3",
                    "a": 40,
                    "b": 100
                }, {
                    "y": "2015 Q4",
                    "a": 75,
                    "b": 165
                }, {
                    "y": "2015 Q4",
                    "a": 150,
                    "b": 240
                }, {
                    "y": "2014 Q4",
                    "a": 100,
                    "b": 270
                }, {
                    "y": "2013 Q4",
                    "a": 260,
                    "b": 300
                }, {
                    "y": "2012 Q4",
                    "a": 20,
                    "b": 225
                }, {
                    "y": "2011 Q4",
                    "a": 295,
                    "b": 110
                }],
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['收入', '支出'],
                resize: true,
                pointSize: 3,
                smooth: true,
                gridTextColor: '#474e54',
                gridLineColor: '#eef0f2',
                goalLineColors: '#e3e6ea',
                gridTextFamily: $.configs.get('site', 'fontFamily'),
                gridTextWeight: '300',
                numLines: 7,
                gridtextSize: 14,
                lineWidth: 1,
                lineColors: [$.colors("green", 600), $.colors("purple", 600)]
            });
        },
        mArea: function () { // 区域图
            Morris.Area({
                element: 'exampleMorrisArea',
                data: [{
                    y: '6',
                    a: 270,
                    b: 160
                }, {
                    y: '7',
                    a: 210,
                    b: 110
                }, {
                    y: '8',
                    a: 240,
                    b: 130
                }, {
                    y: '9',
                    a: 280,
                    b: 250
                }, {
                    y: '10',
                    a: 210,
                    b: 140
                }, {
                    y: '11',
                    a: 150,
                    b: 90
                }, {
                    y: '12',
                    a: 290,
                    b: 180
                }, {
                    y: '13',
                    a: 280,
                    b: 240
                }],
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['收入', '支出'],
                behaveLikeLine: true,
                ymax: 300,
                resize: true,
                pointSize: 3,
                smooth: true,
                gridTextColor: '#474e54',
                gridLineColor: '#eef0f2',
                goalLineColors: '#e3e6ea',
                gridTextFamily: $.configs.get('site', 'fontFamily'),
                gridTextWeight: '300',
                numLines: 7,
                gridtextSize: 14,
                lineWidth: 1,
                fillOpacity: 0.1,
                lineColors: [$.colors("purple", 600), $.colors("green", 600)]
            });
        },
        mBar: function () { // 柱状图
            Morris.Bar({
                element: 'exampleMorrisBar',
                data: [{
                    y: '2013-6',
                    a: 350,
                    b: 410
                }, {
                    y: '2013-7',
                    a: 110,
                    b: 300
                }, {
                    y: '2013-8',
                    a: 460,
                    b: 130
                }, {
                    y: '2013-9',
                    a: 250,
                    b: 310
                }
                    // { y: '2013-10', a: 50, b: 40 },
                    // { y: '2013-11', a: 75, b: 65 },
                    // { y: '2013-12', a: 100, b: 90 }
                ],
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['收入', '支出'],
                barGap: 6,
                barSizeRatio: 0.35,
                smooth: true,
                gridTextColor: '#474e54',
                gridLineColor: '#eef0f2',
                goalLineColors: '#e3e6ea',
                gridTextFamily: $.configs.get('site', 'fontFamily'),
                gridTextWeight: '300',
                numLines: 6,
                gridtextSize: 14,
                resize: true,
                barColors: [$.colors("red", 500), $.colors("blue-grey", 300)]
            });
        },
        mDonut: function () { // 环形图
            Morris.Donut({
                element: 'exampleMorrisDonut',
                data: [{
                    label: "线上",
                    value: 35
                }, {
                    label: "门店",
                    value: 48
                }, {
                    label: "市场",
                    value: 22
                }],
                // barSizeRatio: 0.35,
                resize: true,
                colors: [$.colors("red", 500), $.colors("purple", 500), $.colors("blue-grey", 300)]
            });
        }
    };

})(document, window, jQuery);