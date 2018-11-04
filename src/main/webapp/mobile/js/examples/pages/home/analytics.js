/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    // 折线图提示
    // ------------------------------
    (function () {

        var options = {
            showArea: true,
            low: 0,
            high: 8000,
            height: 240,
            fullWidth: true,
            axisX: {
                offset: 40
            },
            axisY: {
                offset: 30,
                labelInterpolationFnc: function (value) {
                    if (value === 0) {
                        return null;
                    }
                    return value / 1000 + 'k';
                },
                scaleMinSpace: 40
            },
            plugins: [
                Chartist.plugins.tooltip()
            ]
        };

        //本日
        var dayLabelList = ['1:00-3:00', '3:00-6:00', '6:00-9:00', '9:00-12:00', '12:00-15:00', '15:00-18:00', '18:00-21:00', '21:00-1:00'];
        var daySeries1List = {
            name: 'series-1',
            data: [0, 7300, 6200, 6833, 7568, 4620, 4856, 2998]
        };
        var daySeries2List = {
            name: 'series-2',
            data: [0, 3100, 7200, 5264, 5866, 2200, 3850, 1032]
        };

        //本周
        var weekLabelList = ['周一', '周二', '周三', '周四', '周五', '周六', '周日', ''];
        var weekSeries1List = {
            name: 'series-1',
            data: [0, 2400, 6200, 7833, 5568, 3620, 4856, 2998]
        };
        var weekSeries2List = {
            name: 'series-2',
            data: [0, 4100, 6800, 5264, 5866, 3200, 2850, 1032]
        };

        //本月
        var monthLabelList = ["9.1", "9.2", "9.3", "9.4", "9.5", "9.6", "9.7"];
        var monthSeries1List = {
            name: 'series-1',
            data: [0, 6400, 5200, 7833, 5568, 3620, 5856, 0]
        };
        var monthSeries2List = {
            name: 'series-2',
            data: [0, 3100, 4800, 5264, 6866, 3200, 2850, 1032]
        };

        var newScoreLineChart = function (chartId, labelList, series1List, series2List, options) {

            var lineChart = new Chartist.Line(chartId, {
                labels: labelList,
                series: [series1List, series2List]
            }, options);

            //开始创建
            lineChart.on('draw', function (data) {
                var elem, parent;
                if (data.type === 'point') {
                    elem = data.element;
                    parent = new Chartist.Svg(elem._node.parentNode);

                    parent.elem('line', {
                        x1: data.x,
                        y1: data.y,
                        x2: data.x + 0.01,
                        y2: data.y,
                        "class": 'ct-point-content'
                    });
                }
            });
        };

        var createKindChart = function (clickli) {
            var selectLi = clickli || $("#productOverviewWidget .product-filters").children(".active"),
                chartId = selectLi.children("a").attr("href");

            switch (chartId) {
                case "#scoreLineToDay":
                    newScoreLineChart(chartId, dayLabelList,
                        daySeries1List, daySeries2List, options);
                    break;
                case "#scoreLineToWeek":
                    newScoreLineChart(chartId, weekLabelList,
                        weekSeries1List, weekSeries2List, options);
                    break;
                case "#scoreLineToMonth":
                    newScoreLineChart(chartId, monthLabelList,
                        monthSeries1List, monthSeries2List, options);
                    break;
            }
        };

        createKindChart();

        $(".product-filters li").on("click", function () {
            createKindChart($(this));
        });

    })();

    //// 浏览器排行
    // ------------------------------
    (function () {
        var overlappingBarsDataOne = {
            labels: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'],
            series: [
                [3, 4, 6, 10, 8, 6, 3, 4],
                [2, 3, 5, 8, 6, 5, 4, 3]
            ]
        };
        var overlappingBarsDataTwo = {
            labels: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'],
            series: [
                [2, 4, 5, 10, 6, 8, 3, 5],
                [3, 5, 6, 5, 4, 6, 3, 3]
            ]
        };
        var overlappingBarsDataThree = {
            labels: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'],
            series: [
                [5, 2, 6, 7, 10, 8, 6, 5],
                [4, 3, 5, 6, 8, 6, 4, 3]
            ]
        };
        /*var overlappingBarsDataFour = {
         labels: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'],
         series: [
         [2, 1, 5, 6, 7, 10, 8, 5],
         [4, 3, 4, 5, 5, 8, 6, 3]
         ]
         };*/

        var barsData = [overlappingBarsDataOne, overlappingBarsDataTwo, overlappingBarsDataThree, overlappingBarsDataThree];

        var overlappingBarsOptions = {
            low: 0,
            high: 10,
            seriesBarDistance: 6,
            fullWidth: true,
            axisX: {
                showLabel: false,
                showGrid: false,
                offset: 0
            },
            axisY: {
                showLabel: false,
                showGrid: false,
                offset: 0
            },
            chartPadding: {
                //   top: 20,
                //   right: 115,
                //   bottom: 55,
                left: 30
            }
        };

        var responsiveOptions = [
            ['screen and (max-width: 640px)', {
                seriesBarDistance: 6,
                axisX: {
                    labelInterpolationFnc: function (value) {
                        return value[0];
                    }
                }
            }]
        ];

        var createBar = function (chartId, data, options, responsiveOptions) {
            new Chartist.Bar(chartId, data, options, responsiveOptions);
        };

        $("#productOptionsData .ct-chart").each(function (index) {
            createBar(this, barsData[index], overlappingBarsOptions, responsiveOptions);
        });

    })();

    //// 周条形图
    // ------------------------------
    (function () {
        new Chartist.Bar('#weekStackedBarChart', {
                labels: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
                series: [
                    [4, 4.5, 5, 6, 7, 7.5, 7],
                    [6, 5.5, 5, 4, 3, 2.5, 3]
                ]
            }, {
                stackBars: true
            }
        ).on('draw', function (data) {
            if (data.type === 'bar') {
                data.element.attr({
                    style: 'stroke-width: 20px'
                });
            }
        });
    })();


    // 环形图
    // ---------------------
    (function () {

        var map = Morris.Donut({
            resize: true,
            element: 'browersVistsDonut',
            data: [{
                label: 'Chrome',
                value: 425
            }, {
                label: 'Firfox',
                value: 1670
            }, {
                label: 'Safari',
                value: 1100
            }],
            colors: ['#f96868', '#62a9eb', '#f3a754']
        });

        window.morris = [];
        window.morris.push(map);

    })();

})(document, window, jQuery);