/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = {
        run: function () {
            this.exampleOne();
            this.exampleTwo();
            this.exampleThree();
            this.exampleFour();
            this.exampleFive();
        },
        exampleOne: function(){ // 曲线图示例
            var seriesData = [
                [],
                [],
                []
            ];
            var random = new Rickshaw.Fixtures.RandomData(150);

            for (var i = 0; i < 150; i++) {
                random.addData(seriesData);
            }

            var $element = $('#exampleChart');
            var graph = new Rickshaw.Graph({
                element: $element.get(0),
                width: $element.width(),
                height: 300,
                renderer: 'line',
                series: [{
                    color: $.colors("purple", 500),
                    data: seriesData[0],
                    name: '北京'
                }, {
                    color: $.colors("red", 500),
                    data: seriesData[1],
                    name: '上海'
                }, {
                    color: $.colors("green", 500),
                    data: seriesData[2],
                    name: '广州'
                }]
            });

            graph.render();

            setInterval(function () {
                random.removeData(seriesData);
                random.addData(seriesData);
                graph.update();

            }, 2000);

            new Rickshaw.Graph.HoverDetail({
                graph: graph
            });

            var legend = new Rickshaw.Graph.Legend({
                graph: graph,
                element: document.getElementById('exampleChartLegend')
            });

            new Rickshaw.Graph.Behavior.Series.Toggle({
                graph: graph,
                legend: legend
            });

            var axes = new Rickshaw.Graph.Axis.Time({
                graph: graph
            });
            axes.render();

            $(window).on('resize', function () {
                graph.configure({
                    width: $element.width()
                });
                graph.render();
            });
        },
        exampleTwo: function () { // 散点图示例
            var seriesData = [
                [],
                [],
                []
            ];
            var random = new Rickshaw.Fixtures.RandomData(150);

            for (var i = 0; i < 150; i++) {
                random.addData(seriesData);
            }

            var $element = $('#exampleScatterChart');
            var graph = new Rickshaw.Graph({
                element: $element.get(0),
                width: $element.width(),
                height: 300,
                renderer: 'scatterplot',
                series: [{
                    color: $.colors("purple", 500),
                    data: seriesData[0],
                    name: '北京'
                }, {
                    color: $.colors("red", 500),
                    data: seriesData[1],
                    name: '上海'
                }, {
                    color: $.colors("green", 500),
                    data: seriesData[2],
                    name: '广州'
                }]
            });

            graph.render();

            new Rickshaw.Graph.HoverDetail({
                graph: graph
            });

            var legend = new Rickshaw.Graph.Legend({
                graph: graph,
                element: document.getElementById('exampleScatterLegend')
            });

            new Rickshaw.Graph.Behavior.Series.Toggle({
                graph: graph,
                legend: legend
            });

            $(window).on('resize', function () {
                graph.configure({
                    width: $element.width()
                });
                graph.render();
            });
        },
        exampleThree: function () { // 堆叠柱状图图示例
            var seriesData = [
                [],
                [],
                []
            ];
            var random = new Rickshaw.Fixtures.RandomData(150);

            for (var i = 0; i < 150; i++) {
                random.addData(seriesData);
            }

            var $element = $('#exampleStackedChart');
            var graph = new Rickshaw.Graph({
                element: $element.get(0),
                width: $element.width(),
                height: 300,
                renderer: 'bar',
                series: [{
                    color: $.colors("purple", 700),
                    data: seriesData[0],
                    name: '北京'
                }, {
                    color: $.colors("purple", 500),
                    data: seriesData[1],
                    name: '上海'
                }, {
                    color: $.colors("purple", 300),
                    data: seriesData[2],
                    name: '广州'
                }]
            });

            graph.render();

            setInterval(function () {
                random.removeData(seriesData);
                random.addData(seriesData);
                graph.update();

            }, 2000);

            new Rickshaw.Graph.HoverDetail({
                graph: graph
            });

            var legend = new Rickshaw.Graph.Legend({
                graph: graph,
                element: document.getElementById('exampleStackedLegend')
            });

            new Rickshaw.Graph.Behavior.Series.Toggle({
                graph: graph,
                legend: legend
            });

            $(window).on('resize', function () {
                graph.configure({
                    width: $element.width()
                });
                graph.render();
            });
        },
        exampleFour: function () { // 区域图示例
            var seriesData = [
                [],
                [],
                []
            ];
            var random = new Rickshaw.Fixtures.RandomData(150);

            for (var i = 0; i < 150; i++) {
                random.addData(seriesData);
            }

            var $element = $('#exampleAreaChart');
            var graph = new Rickshaw.Graph({
                element: $element.get(0),
                width: $element.width(),
                height: 300,
                renderer: 'area',
                stroke: true,
                series: [{
                    color: $.colors("purple", 700),
                    data: seriesData[0],
                    name: '北京'
                }, {
                    color: $.colors("purple", 500),
                    data: seriesData[1],
                    name: '上海'
                }, {
                    color: $.colors("purple", 300),
                    data: seriesData[2],
                    name: '广州'
                }]
            });

            graph.render();

            setInterval(function () {
                random.removeData(seriesData);
                random.addData(seriesData);
                graph.update();

            }, 2000);

            new Rickshaw.Graph.HoverDetail({
                graph: graph
            });

            var legend = new Rickshaw.Graph.Legend({
                graph: graph,
                element: document.getElementById('exampleAreaLegend')
            });

            new Rickshaw.Graph.Behavior.Series.Toggle({
                graph: graph,
                legend: legend
            });

            $(window).on('resize', function () {
                graph.configure({
                    width: $element.width()
                });
                graph.render();
            });
        },
        exampleFive: function () { // 组合图示例
            var seriesData = [
                [],
                [],
                [],
                [],
                []
            ];
            var random = new Rickshaw.Fixtures.RandomData(50);

            for (var i = 0; i < 75; i++) {
                random.addData(seriesData);
            }

            var $element = $('#exampleMultipleChart');
            var graph = new Rickshaw.Graph({
                element: $element.get(0),
                width: $element.width(),
                height: 300,
                renderer: 'multi',
                dotSize: 5,
                series: [{
                    name: '温度',
                    data: seriesData.shift(),
                    color: $.colors("green", 500),
                    renderer: 'stack'
                }, {
                    name: '体感温度',
                    data: seriesData.shift(),
                    color: $.colors("cyan", 500),
                    renderer: 'stack'
                }, {
                    name: '降雨量',
                    data: seriesData.shift(),
                    color: $.colors("blue", 500),
                    renderer: 'scatterplot'
                }, {
                    name: '空气质量',
                    data: seriesData.shift().map(function (d) {
                        return {
                            x: d.x,
                            y: d.y / 4
                        };
                    }),
                    color: $.colors("indigo", 500),
                    renderer: 'bar'
                }, {
                    name: '紫外线',
                    data: seriesData.shift().map(function (d) {
                        return {
                            x: d.x,
                            y: d.y * 1.5
                        };
                    }),
                    renderer: 'line',
                    color: $.colors("red", 500)
                }]
            });

            new Rickshaw.Graph.RangeSlider.Preview({
                graph: graph,
                element: document.querySelector('#exampleMultipleSlider')
            });

            graph.render();

            new Rickshaw.Graph.HoverDetail({
                graph: graph
            });

            var legend = new Rickshaw.Graph.Legend({
                graph: graph,
                element: document.querySelector('#exampleMultipleLegend')
            });

            new Rickshaw.Graph.Behavior.Series.Highlight({
                graph: graph,
                legend: legend,
                disabledColor: function () {
                    return 'rgba(0, 0, 0, 0.2)';
                }
            });

            new Rickshaw.Graph.Behavior.Series.Toggle({
                graph: graph,
                legend: legend
            });

            $(window).on('resize', function () {
                graph.configure({
                    width: $element.width()
                });
                graph.render();
            });
        }
    };

})(document, window, jQuery);