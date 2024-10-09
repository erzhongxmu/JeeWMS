/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    window.Content = {
        $tagsPage: $('#admui-siteConTabs > .contabs-scroll'),
        exampleOne: null,
        exampleTwo: null,
        timeExample: null,
        chartExample1: function () { // 折线图颜色
            var timeline_labels = [];
            var timeline_data1 = [];
            var timeline_data2 = [];
            var timeline_data3 = [];
            var totalPoints = 20;
            var updateInterval = 2000;
            var now = new Date().getTime();

            function GetData() {
                timeline_labels.shift();
                timeline_data1.shift();
                timeline_data2.shift();
                timeline_data3.shift();

                while (timeline_data1.length < totalPoints) {
                    var x = Math.random() * 100 + 800;
                    var y = Math.random() * 100 + 400;
                    var z = Math.random() * 100 + 200;
                    timeline_labels.push(now += updateInterval);
                    timeline_data1.push(x);
                    timeline_data2.push(y);
                    timeline_data3.push(z);
                }
            }

            var timlelineData = {
                labels: timeline_labels,
                series: [
                    timeline_data1,
                    timeline_data2,
                    timeline_data3
                ]
            };
            var timelineOptions = {
                low: 0,
                showArea: true,
                showPoint: false,
                showLine: false,
                fullWidth: true,
                chartPadding: {
                    top: 0,
                    right: 0,
                    bottom: 0,
                    left: 0
                },
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
                plugins: [
                    Chartist.plugins.tooltip()
                ]
            };
            var self = this;
            function draw() {
                GetData();

                new Chartist.Line("#widgetLineareaColor .ct-chart", timlelineData,
                    timelineOptions);
                self.exampleOne = setTimeout(draw, updateInterval);

            }

            draw();
        },
        chartExample2: function () { // 条形堆叠图
            var timeline_labels = [];
            var timeline_data1 = [];
            var timeline_data2 = [];
            var totalPoints = 30;
            var updateInterval = 2500;
            var now = new Date().getTime();

            function GetData() {
                timeline_labels.shift();
                timeline_data1.shift();
                timeline_data2.shift();

                while (timeline_data1.length < totalPoints) {
                    var x = Math.floor(Math.random() * 100) + 800;
                    var y = Math.floor(Math.random() * 100) + 600;
                    timeline_labels.push(now += updateInterval);
                    timeline_data1.push(x);
                    timeline_data2.push(y);
                }
            }

            var timlelineData = {
                labels: timeline_labels,
                series: [
                    timeline_data1,
                    timeline_data2
                ]
            };
            var timelineOptions = {
                stackBars: true,
                fullWidth: true,
                seriesBarDistance: 0,
                chartPadding: {
                    top: 0,
                    right: 30,
                    bottom: 30,
                    left: 20
                },
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
                plugins: [
                    Chartist.plugins.tooltip()
                ]
            };

            var self = this;
            function draw() {
                GetData();

                new Chartist.Bar("#widgetStackedBar .ct-chart", timlelineData,
                    timelineOptions);
                self.exampleTwo = setTimeout(draw, updateInterval);

            }

            draw();
        },
        linePoint1: function () { // Widget Linepoint

            new Chartist.Line("#widgetLinepoint .ct-chart", {
                labels: ['1', '2', '3', '4', '5', '6', '7', '8'],
                series: [
                    [1, 1.5, 0.5, 2, 1, 2.5, 1.5, 2]
                ]
            }, {
                low: 0,
                showArea: false,
                showPoint: true,
                showLine: true,
                fullWidth: true,
                lineSmooth: false,
                chartPadding: {
                    top: 10,
                    right: -4,
                    bottom: 10,
                    left: -4
                },
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
                plugins: [
                    Chartist.plugins.tooltip()
                ]
            });
        },
        saleBar: function () { // Widget Sale Bar
            new Chartist.Bar("#widgetSaleBar .ct-chart", {
                labels: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q'],
                series: [
                    [50, 90, 100, 90, 110, 100, 120, 130, 115, 95, 80, 85, 100, 140, 130, 120]
                ]
            }, {
                low: 0,
                fullWidth: true,
                chartPadding: {
                    top: 0,
                    right: 20,
                    bottom: 30,
                    left: 20
                },
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
                plugins: [
                    Chartist.plugins.tooltip()
                ]
            });
        },
        overallViews: function () { // Widget Overall Views
            new Chartist.Bar("#widgetOverallViews .small-bar-one", {
                labels: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'],
                series: [
                    [120, 60, 100, 50, 40, 120, 80, 130]
                ]
            }, {
                low: 0,
                fullWidth: true,
                chartPadding: {
                    top: -10,
                    right: 0,
                    bottom: 0,
                    left: 0
                },
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
                plugins: [
                    Chartist.plugins.tooltip()
                ]
            });

            new Chartist.Bar("#widgetOverallViews .small-bar-two", {
                labels: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'],
                series: [
                    [50, 90, 30, 90, 130, 40, 120, 90]
                ]
            }, {
                low: 0,
                fullWidth: true,
                chartPadding: {
                    top: -10,
                    right: 0,
                    bottom: 0,
                    left: 0
                },
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
                plugins: [
                    Chartist.plugins.tooltip()
                ]
            });

            new Chartist.Line("#widgetOverallViews .line-chart", {
                labels: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
                series: [
                    [20, 50, 70, 110, 100, 200, 230],
                    [50, 80, 140, 130, 150, 110, 160]
                ]
            }, {
                low: 0,
                showArea: false,
                showPoint: false,
                showLine: true,
                lineSmooth: false,
                fullWidth: true,
                chartPadding: {
                    top: 0,
                    right: 10,
                    bottom: 0,
                    left: 10
                },
                axisX: {
                    showLabel: true,
                    showGrid: false,
                    offset: 30
                },
                axisY: {
                    showLabel: true,
                    showGrid: true,
                    offset: 30
                },
                plugins: [
                    Chartist.plugins.tooltip()
                ]
            });
        },
        timeLine: function () { // Widget Timeline
            var timeline_labels = [];
            var timeline_data1 = [];
            var timeline_data2 = [];
            var totalPoints = 20;
            var updateInterval = 1000;
            var now = new Date().getTime();

            function GetData() {
                timeline_labels.shift();
                timeline_data1.shift();
                timeline_data2.shift();

                while (timeline_data1.length < totalPoints) {
                    var x = Math.random() * 100 + 800;
                    var y = Math.random() * 100 + 400;
                    timeline_labels.push(now += updateInterval);
                    timeline_data1.push(x);
                    timeline_data2.push(y);
                }
            }

            var timlelineData = {
                labels: timeline_labels,
                series: [
                    timeline_data1,
                    timeline_data2
                ]
            };

            var timelineOptions = {
                low: 0,
                showArea: true,
                showPoint: false,
                showLine: false,
                fullWidth: true,
                chartPadding: {
                    top: 0,
                    right: 0,
                    bottom: 0,
                    left: 0
                },
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
                plugins: [
                    Chartist.plugins.tooltip()
                ]
            };

            var  self = this;
            function draw() {
                GetData();

                new Chartist.Line("#widgetTimeline .ct-chart", timlelineData, timelineOptions);
                self.timeExample = setTimeout(draw, updateInterval);
            }

            draw();
        },
        skyCon: function () {
            var snow = new Skycons({
                "color": $.colors("blue-grey", 500)
            });
            snow.set(document.getElementById("widgetSnow"), "snow");
            snow.play();

            var sunny = new Skycons({
                "color": $.colors("blue-grey", 700)
            });
            sunny.set(document.getElementById("widgetSunny"), "clear-day");
            sunny.play();
        },
        linePoint2: function () {
            new Chartist.Line("#widgetLinepointDate .ct-chart", {
                labels: ['1', '2', '3', '4', '5', '6', '7', '8'],
                series: [
                    [36, 45, 28, 19, 39, 46, 35, 13]
                ]
            }, {
                low: 0,
                showArea: false,
                showPoint: true,
                showLine: true,
                fullWidth: true,
                lineSmooth: false,
                chartPadding: {
                    top: 5,
                    right: -4,
                    bottom: 10,
                    left: -4
                },
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
                plugins: [
                    Chartist.plugins.tooltip()
                ]
            });
        },
        run: function () {
            var self = this;

            this.chartExample1();
            this.chartExample2();
            this.linePoint1();
            this.linePoint2();
            this.saleBar();
            this.overallViews();
            this.timeLine();
            this.skyCon();

            $.leavePage = function(){
                clearTimeout(self.exampleOne);
                clearTimeout(self.exampleTwo);
                clearTimeout(self.timeExample);
            };
        }
    };
})(window, document, jQuery);