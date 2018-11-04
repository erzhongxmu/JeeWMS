/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    // 简单曲线图示例
    // ----------------------
    (function () {
        c3.generate({
            bindto: '#exampleC3SimpleLine',
            data: {
                columns: [
                    ['数据1', 100, 165, 140, 270, 200, 140, 220],
                    ['数据2', 110, 80, 100, 85, 125, 90, 100]
                ]
            },
            color: {
                pattern: [$.colors("purple", 600), $.colors("green", 600)]
            },
            axis: {
                x: {
                    tick: {
                        outer: false
                    }
                },
                y: {
                    max: 300,
                    min: 0,
                    tick: {
                        outer: false,
                        count: 7,
                        values: [0, 50, 100, 150, 200, 250, 300]
                    }
                }
            },
            grid: {
                x: {
                    show: false
                },
                y: {
                    show: true
                }
            }
        });
    })();


    // 曲线区域图示例
    // -----------------------
    (function () {
        c3.generate({
            bindto: '#exampleC3LineRegions',
            data: {
                columns: [
                    ['数据1', 100, 165, 140, 270, 200, 140, 220],
                    ['数据2', 110, 80, 100, 85, 125, 90, 100]
                ],
                regions: {
                    'data1': [{
                        'start': 1,
                        'end': 2,
                        'style': 'dashed'
                    }, {
                        'start': 3
                    }], // currently 'dashed' style only
                    'data2': [{
                        'end': 3
                    }]
                }
            },
            color: {
                pattern: [$.colors("purple", 600), $.colors("green", 600)]
            },
            axis: {
                x: {
                    tick: {
                        outer: false
                    }
                },
                y: {
                    max: 300,
                    min: 0,
                    tick: {
                        outer: false,
                        count: 7,
                        values: [0, 50, 100, 150, 200, 250, 300]
                    }
                }
            },
            grid: {
                x: {
                    show: false
                },
                y: {
                    show: true
                }
            }
        });
    })();


    // 时间序列图示例
    // ---------------------
    (function () {
        var time_series_chart = c3.generate({
            bindto: '#exampleC3TimeSeries',
            data: {
                x: 'x',
                columns: [
                    ['x', '2013-01-01', '2013-01-02', '2013-01-03', '2013-01-04', '2013-01-05', '2013-01-06'],
                    ['数据1', 80, 125, 100, 220, 80, 160],
                    ['数据2', 40, 85, 45, 155, 50, 65]
                ]
            },
            color: {
                pattern: [$.colors("purple", 600), $.colors("green", 600), $.colors("red", 500)]
            },
            padding: {
                right: 40
            },
            axis: {
                x: {
                    type: 'timeseries',
                    tick: {
                        outer: false,
                        format: '%Y-%m-%d'
                    }
                },
                y: {
                    max: 300,
                    min: 0,
                    tick: {
                        outer: false,
                        count: 7,
                        values: [0, 50, 100, 150, 200, 250, 300]
                    }
                }
            },
            grid: {
                x: {
                    show: false
                },
                y: {
                    show: true
                }
            }
        });

        setTimeout(function () {
            time_series_chart.load({
                columns: [
                    ['数据3', 210, 180, 260, 290, 250, 240]
                ]
            });
        }, 1000);
    })();


    // 曲线图示例
    // -----------------
    (function () {
        c3.generate({
            bindto: '#exampleC3Spline',
            data: {
                columns: [
                    ['数据1', 100, 165, 140, 270, 200, 140, 220],
                    ['数据2', 110, 80, 100, 85, 125, 90, 100]
                ],
                type: 'spline'
            },
            color: {
                pattern: [$.colors("purple", 600), $.colors("green", 600)]
            },
            axis: {
                x: {
                    tick: {
                        outer: false
                    }
                },
                y: {
                    max: 300,
                    min: 0,
                    tick: {
                        outer: false,
                        count: 7,
                        values: [0, 50, 100, 150, 200, 250, 300]
                    }
                }
            },
            grid: {
                x: {
                    show: false
                },
                y: {
                    show: true
                }
            }
        });
    })();


    // 散点图示例
    // ------------------
    (function () {
        c3.generate({
            bindto: '#exampleC3Scatter',
            data: {
                xs: {
                    setosa: 'setosa_x',
                    versicolor: 'versicolor_x'
                },
                columns: [
                    ["setosa_x", 3.5, 3.0, 3.2, 3.1, 3.6, 3.9, 3.4, 3.4, 2.9, 3.1, 3.7, 3.4, 3.0, 3.0, 4.0, 4.2, 3.9, 3.5, 3.8, 3.8, 3.4, 3.7, 3.6, 3.3, 3.4, 3.0, 3.4, 3.5, 3.4, 3.2, 3.1, 3.4, 4.1, 4.2, 3.1, 3.2, 3.5, 3.6, 3.0, 3.4, 3.5, 2.3, 3.2, 3.5, 3.8, 3.0, 3.8, 3.2, 3.7, 3.3],
                    ["versicolor_x", 3.2, 3.2, 3.1, 2.3, 2.8, 2.8, 3.3, 2.4, 2.9, 2.7, 2.0, 3.0, 2.2, 2.9, 2.9, 3.1, 3.0, 2.7, 2.2, 2.5, 3.2, 2.8, 2.5, 2.8, 2.9, 3.0, 2.8, 3.0, 2.9, 2.6, 2.4, 2.4, 2.7, 2.7, 3.0, 3.4, 3.1, 2.3, 3.0, 2.5, 2.6, 3.0, 2.6, 2.3, 2.7, 3.0, 2.9, 2.9, 2.5, 2.8],
                    ["setosa", 0.2, 0.2, 0.2, 0.2, 0.2, 0.4, 0.3, 0.2, 0.2, 0.1, 0.2, 0.2, 0.1, 0.1, 0.2, 0.4, 0.4, 0.3, 0.3, 0.3, 0.2, 0.4, 0.2, 0.5, 0.2, 0.2, 0.4, 0.2, 0.2, 0.2, 0.2, 0.4, 0.1, 0.2, 0.2, 0.2, 0.2, 0.1, 0.2, 0.2, 0.3, 0.3, 0.2, 0.6, 0.4, 0.3, 0.2, 0.2, 0.2, 0.2],
                    ["versicolor", 1.4, 1.5, 1.5, 1.3, 1.5, 1.3, 1.6, 1.0, 1.3, 1.4, 1.0, 1.5, 1.0, 1.4, 1.3, 1.4, 1.5, 1.0, 1.5, 1.1, 1.6, 1.3, 1.5, 1.2, 1.3, 1.4, 1.4, 1.2, 1.5, 1.0, 1.1, 1.0, 1.2, 1.6, 1.5, 1.6, 1.5, 1.3, 1.3, 1.3, 1.2, 1.4, 1.2, 1.0, 1.3, 1.2, 1.3, 1.3, 1.1, 1.3]
                ],
                type: 'scatter'
            },
            color: {
                pattern: [$.colors("green", 600), $.colors("red", 500)]
            },
            axis: {
                x: {
                    label: 'Sepal.Width',
                    tick: {
                        outer: false,
                        fit: false
                    }
                },
                size: {
                    height: 400
                },
                padding: {
                    right: 40
                },
                y: {
                    label: 'Petal.Width',
                    tick: {
                        outer: false,
                        count: 5,
                        values: [0, 0.4, 0.8, 1.2, 1.6]
                    }
                }
            },
            grid: {
                x: {
                    show: false
                },
                y: {
                    show: true
                }
            }
        });
    })();


    // 柱状图示例
    // --------------
    (function () {
        var bar_chart = c3.generate({
            bindto: '#exampleC3Bar',
            data: {
                columns: [
                    ['数据1', 30, 200, 100, 400, 150, 250],
                    ['数据2', 130, 100, 140, 200, 150, 50]
                ],
                type: 'bar'
            },
            bar: {
                // width: {
                //  ratio: 0.55 
                // }
                width: {
                    max: 20
                }
            },
            color: {
                pattern: [$.colors("red", 400), $.colors("blue-grey", 400), $.colors("blue-grey", 200)]
            },
            grid: {
                y: {
                    show: true
                },
                x: {
                    show: false
                }
            }
        });

        setTimeout(function () {
            bar_chart.load({
                columns: [
                    ['数据3', 130, -150, 200, 300, -200, 100]
                ]
            });
        }, 1000);
    })();


    // Example C3 Stacked Bar
    // ----------------------
    (function () {
        var stacked_bar_chart = c3.generate({
            bindto: '#exampleC3StackedBar',
            data: {
                columns: [
                    ['数据1', -30, 200, 300, 400, -150, 250],
                    ['数据2', 130, 100, -400, 100, -150, 50],
                    ['数据3', -230, 200, 200, -300, 250, 250]
                ],
                type: 'bar',
                groups: [
                    ['数据1', 'data2']
                ]
            },
            color: {
                pattern: [$.colors("purple", 500), $.colors("blue-grey", 300), $.colors("purple", 500), $.colors("light-green", 500)]
            },
            bar: {
                width: {
                    max: 45
                }
            },
            grid: {
                y: {
                    show: true,
                    lines: [{
                        value: 0
                    }]
                }
            }
        });

        setTimeout(function () {
            stacked_bar_chart.groups([
                ['数据1', 'data2', 'data3']
            ]);
        }, 1000);

        setTimeout(function () {
            stacked_bar_chart.load({
                columns: [
                    ['数据4', 100, -250, 150, 200, -300, -100]
                ]
            });
        }, 1500);

        setTimeout(function () {
            stacked_bar_chart.groups([
                ['数据1', 'data2', 'data3', 'data4']
            ]);
        }, 2000);
    })();


    // 组合图示例
    // ----------------------
    (function () {
        c3.generate({
            bindto: '#exampleC3Combination',
            data: {
                columns: [
                    ['数据1', 30, 20, 50, 40, 60, 50],
                    ['数据2', 200, 130, 90, 240, 130, 220],
                    ['数据3', 300, 200, 160, 400, 250, 250],
                    ['数据4', 200, 130, 90, 240, 130, 220],
                    ['数据5', 130, 120, 150, 140, 160, 150],
                    ['数据6', 90, 70, 20, 50, 60, 120]
                ],
                type: 'bar',
                types: {
                    data3: 'spline',
                    data4: 'line',
                    data6: 'area'
                },
                groups: [
                    ['数据1', 'data2']
                ]
            },
            color: {
                pattern: [$.colors("blue-grey", 400), $.colors("blue-grey", 200), $.colors("yellow", 600), $.colors("purple", 600), $.colors("red", 400), $.colors("green", 600)]
            },
            grid: {
                x: {
                    show: false
                },
                y: {
                    show: true
                }
            }
        });
    })();


    // 饼状图
    // --------------
    (function () {
        c3.generate({
            bindto: '#exampleC3Pie',
            data: {
                // iris data from R
                columns: [
                    ['数据1', 100],
                    ['数据2', 40]
                ],
                type: 'pie'
            },
            color: {
                pattern: [$.colors("purple", 500), $.colors("blue-grey", 200)]
            },
            legend: {
                position: 'right'
            },
            pie: {
                label: {
                    show: false
                }
            }
        });
    })();


    // 环形图
    // ----------------
    (function () {
        c3.generate({
            bindto: '#exampleC3Donut',
            data: {
                columns: [
                    ['数据1', 120],
                    ['数据2', 40],
                    ['数据3', 80]
                ],
                type: 'donut'
            },
            color: {
                pattern: [$.colors("purple", 500), $.colors("blue-grey", 200), $.colors("red", 400)]
            },
            legend: {
                position: 'right'
            },
            donut: {
                label: {
                    show: false
                },
                width: 10,
                title: "C3 环形图"
            }
        });
    })();

    // 区域缩放1示例
    // ----------------
    (function () {
        c3.generate({
            bindto: '#exampleC3Subchart',
            data: {
                columns: [
                    ['数据1', 100, 165, 140, 270, 200, 140, 220, 210, 190, 100, 170, 250],
                    ['数据2', 110, 80, 100, 85, 125, 90, 100, 130, 120, 90, 100, 115]
                ],
                type: 'spline'
            },
            color: {
                pattern: [$.colors("purple", 600), $.colors("green", 600)]
            },
            subchart: {
                show: true
            }
        });
    })();

    // 区域缩放2示例
    // ----------------
    (function () {
        c3.generate({
            bindto: '#exampleC3Zoom',
            data: {
                columns: [
                    ['数据', 30, 200, 100, 400, 150, 250, 150, 200, 170, 240, 350, 150, 100, 400, 150, 250, 150, 200, 170, 240, 100, 150, 250, 150, 200, 170, 240, 30, 200, 100, 400, 150, 250, 150, 200, 170, 240, 350, 150, 100, 400, 350, 220, 250, 300, 270, 140, 150, 90, 150, 50, 120, 70, 40]
                ],
                colors: {
                    sample: $.colors("purple", 600)
                }
            },
            zoom: {
                enabled: true
            }
        });
    })();

})(document, window, jQuery);