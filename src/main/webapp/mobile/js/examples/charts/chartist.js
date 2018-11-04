/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window) {
    'use strict';

    // CSS 动画示例
    // ------------------------------
    (function () {
        var cssAnimationData = {
            labels: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            series: [
                [1, 2, 2.7, 0, 3, 5, 3, 4, 8, 10, 12, 7],
                [0, 1.2, 2, 7, 2.5, 9, 5, 8, 9, 11, 14, 4],
                [10, 9, 8, 6.5, 6.8, 6, 5.4, 5.3, 4.5, 4.4, 3, 2.8]
            ]
        };

        var cssAnimationResponsiveOptions = [
            [
                {
                    axisX: {
                        labelInterpolationFnc: function (value, index) {
                            if (index % 2 !== 0) {
                                return false;
                            } else {
                                return value;
                            }
                        }
                    }
                }
            ]
        ];

        new Chartist.Line('#exampleLineAnimation', cssAnimationData, null, cssAnimationResponsiveOptions);
    })();


    // 曲线图 —— 简单示例
    // ----------------------------
    (function () {
        new Chartist.Line('#exampleSimpleLine', {
            labels: ['星期一', '星期二', '星期三', '星期四', '星期五'],
            series: [
                [12, 9, 7, 8, 5],
                [2, 1, 3.5, 7, 3],
                [1, 3, 4, 5, 6]
            ]
        }, {
            fullWidth: true,
            chartPadding: {
                right: 40
            }
        });
    })();


    // 曲线图 —— 散点图示例
    // -----------------------------
    (function () {
        var ctScatterTimes = function (n) {
            return Array.apply(null, new Array(n));
        };

        var ctScatterData = ctScatterTimes(52).map(Math.random).reduce(function (data, rnd, index) {
            data.labels.push(index + 1);
            data.series.forEach(function (series) {
                series.push(Math.random() * 100);
            });

            return data;
        }, {
            labels: [],
            series: ctScatterTimes(4).map(function () {
                return [];
            })
        });

        var ctScatterOptions = {
            showLine: false,
            axisX: {
                labelInterpolationFnc: function (value, index) {
                    return index % 13 === 0 ? 'W' + value : null;
                }
            }
        };

        var ctScatterResponsiveOptions = [
            ['screen and (min-width: 640px)', {
                axisX: {
                    labelInterpolationFnc: function (value, index) {
                        return index % 4 === 0 ? 'W' + value : null;
                    }
                }
            }]
        ];
        new Chartist.Line('#exampleLineScatter', ctScatterData, ctScatterOptions, ctScatterResponsiveOptions);

    })();

    // 曲线图 —— 带 Tooltip 提示效果
    // -----------------------------------------
    (function () {
        new Chartist.Line('#exampleTooltipsLine', {
            labels: ['1', '2', '3', '4', '5', '6'],
            series: [{
                name: '斐波那契序列',
                data: [1, 2, 3, 5, 8, 13]
            }, {
                name: '黄金分割',
                data: [1, 1.618, 2.618, 4.236, 6.854, 11.09]
            }]
        }, {
            plugins: [
                Chartist.plugins.tooltip()
            ]
        });

    })();

    // 曲线图 —— 区域柱状图示例
    // -------------------------------------
    (function () {
        new Chartist.Line('#exampleAreaLine', {
            labels: [1, 2, 3, 4, 5, 6, 7, 8],
            series: [
                [5, 9, 7, 8, 5, 3, 5, 4]
            ]
        }, {
            low: 0,
            showArea: true
        });
    })();

    // 曲线图 —— 双极区域柱状图示例
    // ------------------------------
    (function () {
        new Chartist.Line('#exampleOnlyArea', {
            labels: [1, 2, 3, 4, 5, 6, 7, 8],
            series: [
                [1, 2, 3, 1, -2, 0, 1, 0],
                [-2, -1, -2, -1, -2.5, -1, -2, -1],
                [0, 0, 0, 1, 2, 2.5, 2, 1],
                [2.5, 2, 1, 0.5, 1, 0.5, -1, -2.5]
            ]
        }, {
            high: 3,
            low: -3,
            showArea: true,
            showLine: false,
            showPoint: false,
            fullWidth: true,
            axisX: {
                showLabel: false,
                showGrid: false
            }
        });
    })();

    // 曲线图 —— 高级 SMIL 动画示例
    // -----------------------------------------
    (function () {
        var animationsChart = new Chartist.Line('#exampleLineAnimations', {
            labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
            series: [
                [12, 9, 7, 8, 5, 4, 6, 2, 3, 3, 4, 6],
                [4, 5, 3, 7, 3, 5, 5, 3, 4, 4, 5, 5],
                [5, 3, 4, 5, 6, 3, 3, 4, 5, 6, 3, 4],
                [3, 4, 5, 6, 7, 6, 4, 5, 6, 7, 6, 3]
            ]
        }, {
            low: 0
        });

        var seq = 0,
            delays = 80,
            durations = 500;

        animationsChart.on('created', function () {
            seq = 0;
        });

        animationsChart.on('draw', function (data) {
            seq++;

            if (data.type === 'line') {

                data.element.animate({
                    opacity: {
                        begin: seq * delays + 1000,
                        dur: durations,
                        from: 0,
                        to: 1
                    }
                });
            } else if (data.type === 'label' && data.axis === 'x') {
                data.element.animate({
                    y: {
                        begin: seq * delays,
                        dur: durations,
                        from: data.y + 100,
                        to: data.y,
                        easing: 'easeOutQuart'
                    }
                });
            } else if (data.type === 'label' && data.axis === 'y') {
                data.element.animate({
                    x: {
                        begin: seq * delays,
                        dur: durations,
                        from: data.x - 100,
                        to: data.x,
                        easing: 'easeOutQuart'
                    }
                });
            } else if (data.type === 'point') {
                data.element.animate({
                    x1: {
                        begin: seq * delays,
                        dur: durations,
                        from: data.x - 10,
                        to: data.x,
                        easing: 'easeOutQuart'
                    },
                    x2: {
                        begin: seq * delays,
                        dur: durations,
                        from: data.x - 10,
                        to: data.x,
                        easing: 'easeOutQuart'
                    },
                    opacity: {
                        begin: seq * delays,
                        dur: durations,
                        from: 0,
                        to: 1,
                        easing: 'easeOutQuart'
                    }
                });
            } else if (data.type === 'grid') {
                var pos1Animation = {
                    begin: seq * delays,
                    dur: durations,
                    from: data[data.axis.units.pos + '1'] - 30,
                    to: data[data.axis.units.pos + '1'],
                    easing: 'easeOutQuart'
                };

                var pos2Animation = {
                    begin: seq * delays,
                    dur: durations,
                    from: data[data.axis.units.pos + '2'] - 100,
                    to: data[data.axis.units.pos + '2'],
                    easing: 'easeOutQuart'
                };

                var ctAnimations = {};
                ctAnimations[data.axis.units.pos + '1'] = pos1Animation;
                ctAnimations[data.axis.units.pos + '2'] = pos2Animation;
                ctAnimations.opacity = {
                    begin: seq * delays,
                    dur: durations,
                    from: 0,
                    to: 1,
                    easing: 'easeOutQuart'
                };

                data.element.animate(ctAnimations);
            }
        });

        animationsChart.on('created', function () {
            if (window.__exampleAnimateTimeout) {
                clearTimeout(window.__exampleAnimateTimeout);
                window.__exampleAnimateTimeout = null;
            }
            window.__exampleAnimateTimeout = setTimeout(animationsChart.update.bind(animationsChart), 12000);
        });
    })();

    // 曲线图 —— Svg Path 动画示例
    // -----------------------------------
    (function () {
        var pathAnimationChart = new Chartist.Line('#examplePathAnimation', {
            labels: ['周一', '周二', '周三', '周四', '周五', '周六'],
            series: [
                [1, 5, 2, 5, 4, 3],
                [2, 3, 4, 8, 1, 2],
                [5, 4, 3, 2, 1, 0.5]
            ]
        }, {
            low: 0,
            showArea: true,
            showPoint: false,
            fullWidth: true
        });

        pathAnimationChart.on('draw', function (data) {
            if (data.type === 'line' || data.type === 'area') {
                data.element.animate({
                    d: {
                        begin: 2000 * data.index,
                        dur: 2000,
                        from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
                        to: data.path.clone().stringify(),
                        easing: Chartist.Svg.Easing.easeOutQuint
                    }
                });
            }
        });
    })();


    // 曲线图 —— 平滑曲线
    // -----------------------------------
    (function () {
        new Chartist.Line('#exampleSmoothingLine', {
            labels: [1, 2, 3, 4, 5],
            series: [
                [1, 5, 10, 0, 1],
                [10, 15, 0, 1, 2]
            ]
        }, {
            lineSmooth: Chartist.Interpolation.simple({
                divisor: 2
            }),
            fullWidth: true,
            chartPadding: {
                right: 20
            },
            low: 0
        });
    })();

    // 柱状图 —— 两极柱状图示例
    // -----------------------------
    (function () {
        var biPolarData = {
            labels: ['2', '3', '3', '4', '5', '6', '7', '8', '9', '10'],
            series: [
                [1, 2, 4, 8, 6, -2, -1, -4, -6, -2]
            ]
        };

        var biPolarOptions = {
            high: 10,
            low: -10,
            axisX: {
                labelInterpolationFnc: function (value, index) {
                    return index % 2 === 0 ? value : null;
                }
            }
        };

        new Chartist.Bar('#exampleBiPolarBar', biPolarData, biPolarOptions);

    })();

    // 柱状图 —— 移动端堆叠示例
    // ---------------------------------
    (function () {
        var overlappingData = {
            labels: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            series: [
                [5, 4, 3, 7, 5, 10, 3, 4, 8, 10, 6, 8],
                [3, 2, 9, 5, 4, 6, 4, 6, 7, 8, 7, 4]
            ]
        };

        var overlappingOptions = {
            seriesBarDistance: 10
        };

        var overlappingResponsiveOptions = [
            ['screen and (max-width: 640px)', {
                seriesBarDistance: 5,
                axisX: {
                    labelInterpolationFnc: function (value) {
                        return value[0];
                    }
                }
            }]
        ];

        new Chartist.Bar('#exampleOverlappingBar', overlappingData, overlappingOptions, overlappingResponsiveOptions);

    })();

    // 柱状图 —— 使用 Draw 事件在顶部添加圆形标记示例
    // ---------------------------------
    (function () {
        // Create a simple bi-polar bar chart
        var peakCirclesChart = new Chartist.Bar('#examplePeakCirclesBar', {
            labels: ['1', '3', '3', '4', '5', '6', '7', '8', '9', '10'],
            series: [
                [1, 2, 4, 8, 6, -2, -1, -4, -6, -2]
            ]
        }, {
            high: 10,
            low: -10,
            axisX: {
                labelInterpolationFnc: function (value, index) {
                    return index % 2 === 0 ? value : null;
                }
            }
        });

        peakCirclesChart.on('draw', function (data) {
            if (data.type === 'bar') {
                data.group.append(new Chartist.Svg('circle', {
                    cx: data.x2,
                    cy: data.y2,
                    r: Math.abs(Chartist.getMultiValue(data.value)) * 2 + 5
                }, 'ct-slice-pie'));
            }
        });
    })();


    // 柱状图 —— 多个柱状图示例
    // ----------------------------------
    (function () {
        new Chartist.Bar('#exampleMultiLabelsBar', {
            labels: ['第1季度', '第2季度', '第3季度', '第4季度'],
            series: [
                [60000, 40000, 80000, 70000],
                [40000, 30000, 70000, 65000],
                [8000, 3000, 10000, 6000]
            ]
        }, {
            seriesBarDistance: 10,
            axisX: {
                offset: 60
            },
            axisY: {
                offset: 80,
                labelInterpolationFnc: function (value) {
                    return value + ' CHF';
                },
                scaleMinSpace: 15
            }
        });
    })();


    // 柱状图 —— 堆叠示例
    // ----------------------------------
    (function () {
        new Chartist.Bar('#exampleStackedBar', {
            labels: ['Q1', 'Q2', 'Q3', 'Q4'],
            series: [
                [800000, 1200000, 1400000, 1300000],
                [200000, 400000, 500000, 300000],
                [100000, 200000, 400000, 600000]
            ]
        }, {
            stackBars: true,
            axisY: {
                labelInterpolationFnc: function (value) {
                    return (value / 1000) + 'k';
                }
            }
        }).on('draw', function (data) {
            if (data.type === 'bar') {
                data.element.attr({
                    style: 'stroke-width: 30px'
                });
            }
        });
    })();


    // 柱状图 —— 横向柱状图示例
    // -------------------------------
    (function () {
        new Chartist.Bar('#exampleHorizontalBar', {
            labels: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
            series: [
                [5, 4, 3, 7, 5, 10, 3],
                [3, 2, 9, 5, 4, 6, 4]
            ]
        }, {
            seriesBarDistance: 10,
            reverseData: true,
            horizontalBars: true,
            axisY: {
                offset: 70
            }
        });
    })();


    // 柱状图 —— 响应式柱状图
    // -----------------------------------
    (function () {
        new Chartist.Bar('#exampleResponsiveBar', {
            labels: ['第1部分', '第2部分', '第3部分', '第4部分'],
            series: [
                [5, 4, 3, 7],
                [3, 2, 9, 5],
                [1, 5, 8, 4],
                [2, 3, 4, 6],
                [4, 1, 2, 1]
            ]
        }, {
            stackBars: true,
            axisX: {
                labelInterpolationFnc: function (value) {
                    return value.split(/\s+/).map(function (word) {
                        return word[0];
                    }).join('');
                }
            },
            axisY: {
                offset: 20
            }
        }, [
            ['screen and (min-width: 480px)', {
                reverseData: true,
                horizontalBars: true,
                axisX: {
                    labelInterpolationFnc: Chartist.noop
                },
                axisY: {
                    offset: 60
                }
            }],
            ['screen and (min-width: 992px)', {
                stackBars: false,
                seriesBarDistance: 10
            }],
            ['screen and (min-width: 1200px)', {
                reverseData: false,
                horizontalBars: false,
                seriesBarDistance: 15
            }]
        ]);
    })();


    // 饼状图 —— 简单示例
    // ---------------------------
    (function () {
        var simplePiedata = {
            series: [5, 3, 4]
        };

        var simplePieSum = function (a, b) {
            return a + b;
        };

        new Chartist.Pie('#exampleSimplePie', simplePiedata, {
            labelInterpolationFnc: function (value) {
                return Math.round(value / simplePiedata.series.reduce(simplePieSum) * 100) + '%';
            }
        });
    })();


    // 柱状图 —— 自定义标签示例
    // ---------------------------------
    (function () {
        var labelsPieData = {
            labels: ['香蕉', '苹果', '葡萄'],
            series: [20, 15, 40]
        };

        var labelsPieOptions = {
            labelInterpolationFnc: function (value) {
                return value[0];
            }
        };

        var labelsPieResponsiveOptions = [
            ['screen and (min-width: 640px)', {
                chartPadding: 30,
                labelOffset: 100,
                labelDirection: 'explode',
                labelInterpolationFnc: function (value) {
                    return value;
                }
            }],
            ['screen and (min-width: 1024px)', {
                labelOffset: 80,
                chartPadding: 20
            }]
        ];

        new Chartist.Pie('#exampleLabelsPie', labelsPieData, labelsPieOptions, labelsPieResponsiveOptions);
    })();


    // 柱状图 —— 仪表盘
    // --------------------------
    (function () {
        new Chartist.Pie('#exampleGaugePie', {
            series: [20, 10, 30, 40]
        }, {
            donut: true,
            donutWidth: 60,
            startAngle: 270,
            total: 200,
            showLabel: false
        });
    })();

})(document, window);