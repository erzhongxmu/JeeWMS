/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    // 实时显示示例
    // ---------------------
    (function () {
        var $exampleFlotRealtime = $("#exampleFlotRealtime");
        if (!$.isFunction($.fn.plot) || $exampleFlotRealtime.length === 0) {
            return;
        }

        var data = [];
        var totalPoints = 250;

        function getRandomData() {
            if (data.length > 0) {
                data = data.slice(1);
            }
            // 随机数
            while (data.length < totalPoints) {
                var prev = data.length > 0 ? data[data.length - 1] : 50;
                var y = prev + Math.random() * 10 - 5;
                if (y < 0) {
                    y = 0;
                } else if (y > 100) {
                    y = 100;
                }
                data.push(y);
            }
            // 与X轴对应产生Y轴随机数
            var res = [];
            for (var i = 0; i < data.length; ++i) {
                res.push([i, data[i]]);
            }
            return res;
        }

        var labelColor = $.colors("grey", 600);
        // 设置控制部件
        var updateInterval = 30;

        var plot = $.plot($exampleFlotRealtime, [{
            data: getRandomData()
        }], {

            colors: [$.colors("blue-grey", 100)],
            series: {
                shadowSize: 0,
                lines: {
                    show: true,
                    lineWidth: 0,
                    fill: true,
                    fillColor: $.colors("blue-grey", 100)
                }
            },
            legend: {
                show: false
            },
            xaxis: {
                show: false,
                font: {
                    color: labelColor
                }
            },
            yaxis: {
                tickColor: "#edeff2",
                color: "#474e54",
                min: 0,
                max: 100,
                font: {
                    size: 14,
                    color: labelColor,
                    weight: "300"
                    // family: "OpenSans Light"
                }
            },
            grid: {
                color: "#474e54",
                tickColor: "#e3e6ea",
                backgroundColor: {
                    colors: ["#fff", "#fff"]
                },
                borderWidth: {
                    top: 0,
                    right: 0,
                    bottom: 1,
                    left: 0
                },
                borderColor: "#eef0f2"
            }
        });

        function update() {
            plot.setData([getRandomData()]);
            // 轴不发生变化，我们并不需要调用plot.setupGrid（）
            plot.draw();
            setTimeout(update, updateInterval);
        }

        update();

    })();


    // 全背景曲线图示例
    // -------------------------
    (function () {
        var b = [
            [1262304000000, 0],
            [1264982400000, 500],
            [1267401600000, 700],
            [1270080000000, 1300],
            [1272672000000, 2600],
            [1275350400000, 1300],
            [1277942400000, 1700],
            [1280620800000, 1300],
            [1283299200000, 1500],
            [1285891200000, 2000],
            [1288569600000, 1500],
            [1291161600000, 1200]
        ];
        var a = [{
            label: "值",
            data: b
        }];

        $.plot("#exampleFlotFullBg", a, {
            xaxis: {
                min: (new Date(2009, 12, 1)).getTime(),
                max: (new Date(2010, 11, 2)).getTime(),
                mode: "time",
                tickSize: [1, "月"],
                monthNames: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
                tickLength: 0,
                // tickColor: "#edeff2",
                color: "#474e54",
                font: {
                    size: 14,
                    weight: 300
                    // family: "OpenSans Light"
                }
            },
            yaxis: {
                tickColor: "#edeff2",
                color: "#474e54",
                font: {
                    size: 14,
                    weight: "300"
                    // family: "OpenSans Light"
                }
            },
            series: {
                shadowSize: 0,
                lines: {
                    show: true,
                    // fill: true,
                    // fillColor: "#ff0000",
                    lineWidth: 1.5
                },
                points: {
                    show: true,
                    fill: true,
                    fillColor: $.colors("purple", 600),
                    radius: 3,
                    lineWidth: 1
                }
            },
            colors: [$.colors("purple", 400)],
            grid: {
                // show: true,
                hoverable: true,
                clickable: true,
                // color: "green",
                // tickColor: "red",
                backgroundColor: {
                    colors: ["#fcfdfe", "#fcfdfe"]
                },
                borderWidth: 0
                // borderColor: "#ff0000"
            },
            legend: {
                show: false
            }
        });

    })();


    // 曲线图示例
    // ------------------
    (function () {
        var dt1 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.25) {
            dt1.push([i, Math.sin(i)]);
        }

        var dt2 = [];
        for (i = 0; i < Math.PI * 2; i += 0.25) {
            dt2.push([i, Math.cos(i)]);
        }

        var f_chart = $("#exampleFlotCurve");

        $.plot(f_chart, [{
            label: "sin(x)",
            data: dt1,
            color: $.colors("purple", 400),
            points: {
                show: true,
                fill: true,
                radius: 3,
                fillColor: $.colors("purple", 400)
            }
        }, {
            label: "cos(x)",
            data: dt2,
            yaxis: 2,
            color: $.colors("green", 400),
            points: {
                show: true,
                fill: true,
                radius: 3,
                fillColor: $.colors("green", 600)
            }
        }], {
            series: {
                shadowSize: 0,
                lines: {
                    show: true,
                    lineWidth: 1.5
                },
                points: {
                    show: true,
                    radius: 3,
                    lineWidth: 1
                }
            },
            xaxes: [{
                ticks: [
                    0, [Math.PI / 2, "\u03c0/2"],
                    [Math.PI, "\u03c0"],
                    [Math.PI * 3 / 2, "3\u03c0/2"],
                    [Math.PI * 2, "2\u03c0"]
                ]
            }

            ],
            yaxes: [{
                ticks: 5,
                min: -2,
                max: 2,
                tickDecimals: 3
            }, {
                ticks: 5,
                min: -1,
                max: 1,
                tickLength: 0,
                tickDecimals: 2,
                position: "right"
            }],
            grid: {
                hoverable: true,
                color: "#474e54",
                tickColor: "#e3e6ea",
                backgroundColor: {
                    colors: ["#fff", "#fff"]
                },
                borderWidth: {
                    top: 1,
                    right: 1,
                    bottom: 1,
                    left: 1
                },
                borderColor: "#eef0f2"
            },
            legend: {
                show: false
            }
        });

    })();


    // 混合图示例
    // ----------------
    (function () {
        var b1 = [];
        for (var i = 0; i < 14; i += 0.5) {
            b1.push([i, Math.cos(i) + 1]);
        }

        var b2 = [
            [2, 3],
            [4, 8],
            [6, 5],
            [9, 13]
        ];

        var b3 = [];
        for (i = 0; i < 14; i += 0.5) {
            b3.push([i, Math.cos(i) + Math.sin(i) - 1]);
        }

        var b4 = [];
        for (i = 0; i < 14; i += 0.1) {
            b4.push([i, Math.sqrt(i * 10) - 4 * Math.cos(i)]);
        }

        var b5 = [];
        for (i = 0; i < 14; i += 1.5) {
            b5.push([i, (Math.cos(i) + 2 * Math.sin(i)) + 6]);
        }

        var b6 = [];
        for (i = 0; i < 14; i += 0.5 + Math.random()) {
            b6.push([i, Math.sqrt(i + 2 * Math.cos(i)) - Math.sin(i) - 3]);
        }

        $.plot("#exampleFlotMix", [{
            data: b2,
            bars: {
                show: true,
                align: "center",
                fill: true,
                fillColor: $.colors("blue-grey", 100)
            }
        }, {
            data: b1,
            lines: {
                show: true,
                fill: true,
                fillColor: "rgba(251,213,181,.1)"
            }
        }, {
            data: b3,
            points: {
                show: true,
                fill: true,
                fillColor: $.colors("green", 600),
                radius: 2
            }
        }, {
            data: b4,
            lines: {
                show: true
            },
            points: {
                show: false
            }
        }, {
            data: b5,
            lines: {
                show: true
            },
            points: {
                show: true,
                fill: true,
                fillColor: $.colors("purple", 600),
                radius: 2
            }
        }, {
            data: b6,
            lines: {
                show: true,
                steps: true
            }
        }], {
            xaxis: {
                tickLength: 0,
                color: "#474e54",
                font: {
                    size: 14,
                    weight: 300
                    // family: "OpenSans Light"
                }
            },
            yaxis: {
                tickColor: "#edeff2",
                color: "#474e54",
                font: {
                    size: 14,
                    weight: "300"
                    // family: "OpenSans Light"
                }
            },
            grid: {
                color: "#474e54",
                tickColor: "#e3e6ea",
                backgroundColor: {
                    colors: ["#fff", "#fff"]
                },
                borderWidth: {
                    top: 0,
                    right: 0,
                    bottom: 1,
                    left: 0
                },
                borderColor: "#eef0f2"
            },
            series: {
                shadowSize: 0
            },
            colors: [$.colors("blue-grey", 100),
                $.colors("orange", 200),
                $.colors("green", 600),
                $.colors("yellow", 600),
                $.colors("purple", 600),
                $.colors("purple", 200)
            ]
        });
    })();


    // 堆叠柱状图示例
    // ----------------------
    (function () {
        var a1 = [];
        for (var i = 1; i <= 4; i += 1) {
            a1.push([i, parseInt(Math.random() * 30)]);
        }

        var a2 = [];
        for (i = 1; i <= 4; i += 1) {
            a2.push([i, parseInt(Math.random() * 30)]);
        }

        var a3 = [];
        for (i = 1; i <= 4; i += 1) {
            a3.push([i, parseInt(Math.random() * 30)]);
        }

        var a4 = [];
        for (i = 1; i <= 4; i += 1) {
            a4.push([i, parseInt(Math.random() * 30 - 10)]);
        }

        $.plot("#exampleFlotStackBar", [{
            data: a1,
            bars: {
                fill: true,
                fillColor: $.colors("light-green", 500)
            }
        }, {
            data: a2,
            bars: {
                fill: true,
                fillColor: $.colors("blue-grey", 300)
            }
        }, {
            data: a3,
            bars: {
                fill: true,
                fillColor: $.colors("purple", 500)
            }
        }, {
            data: a4,
            bars: {
                fill: true,
                fillColor: $.colors("purple", 500)
            }
        }], {
            series: {
                stack: true,
                shadowSize: 0,
                lines: {
                    show: false,
                    fill: true,
                    steps: false
                },
                bars: {
                    show: true,
                    align: "center",
                    barWidth: 0.38
                }
            },
            colors: [$.colors("light-green", 500), $.colors("blue-grey", 300), $.colors("purple", 500), $.colors("purple", 500)],
            xaxis: {
                tickLength: 0,
                color: "#474e54",
                min: 0,
                max: 5.5,
                ticks: [1, 2, 3, 4],
                font: {
                    size: 14,
                    weight: 300
                    // family: "OpenSans Light"
                }
            },
            yaxis: {
                tickColor: "#edeff2",
                color: "#474e54",
                min: -10,
                font: {
                    size: 14,
                    weight: "300"
                    // family: "OpenSans Light"
                }
            },
            grid: {
                color: "#474e54",
                tickColor: "#e3e6ea",
                backgroundColor: {
                    colors: ["#fff", "#fff"]
                },
                borderWidth: {
                    top: 0,
                    right: 0,
                    bottom: 1,
                    left: 0
                },
                borderColor: "#eef0f2"
            }
        });
    })();


    // 横向柱状图示例
    // ---------------------------
    (function () {
        var a11 = [];
        for (var i = 1; i <= 5; i += 1) {
            a11.push([parseInt(Math.random() * 30), i]);
        }

        var a22 = [];
        for (i = 1; i <= 5; i += 1) {
            a22.push([parseInt(Math.random() * 30), i]);
        }

        var a33 = [];
        for (i = 1; i <= 5; i += 1) {
            a33.push([parseInt(Math.random() * 30), i]);
        }

        $.plot("#exampleFlotHorizontalBar", [{
            data: a11,
            bars: {
                fill: true,
                fillColor: $.colors("purple", 500)
            }
        }, {
            data: a22,
            bars: {
                fill: true,
                fillColor: $.colors("blue-grey", 300)
            }
        }, {
            data: a33,
            bars: {
                fill: true,
                fillColor: $.colors("red", 500)
            }
        }], {
            series: {
                stack: true,
                lines: {
                    show: false,
                    fill: true
                },
                bars: {
                    show: true,
                    barWidth: 0.55,
                    align: "center",
                    horizontal: true
                }
            },
            colors: [$.colors("purple", 500), $.colors("blue-grey", 300), $.colors("red", 500)],
            xaxis: {
                color: "#474e54",
                font: {
                    size: 14,
                    weight: 300
                    // family: "OpenSans Light"
                }
            },
            yaxis: {
                tickLength: 0,
                tickColor: "#edeff2",
                color: "#474e54",
                min: 0,
                max: 6,
                ticks: [1, 2, 3, 4, 5],
                font: {
                    size: 14,
                    weight: "300"
                    // family: "OpenSans Light"
                }
            },
            grid: {
                color: "#474e54",
                tickColor: "#e3e6ea",
                backgroundColor: {
                    colors: ["#fff", "#fff"]
                },
                borderWidth: {
                    top: 1,
                    right: 1,
                    bottom: 1,
                    left: 1
                },
                borderColor: "#eef0f2"
            }
        });
    })();

    // 饼状图示例
    // ----------------
    (function () {
        var pieData = [],
            series = 2,
            $btnPieDefault =  $("#btnPieDefault"),
            placeholder = $("#exampleFlotPie");

        for (var i = 0; i < series; i++) {
            pieData[i] = {
                label: "示例" + (i + 1),
                data: Math.floor(Math.random() * 100) + 1
            };
        }

        // 使用自定义标签
        function labelFormatter(label, series) {
            return "<div" + " style='" + "font-size: 8pt; text-align: center; padding: 2px; color: #747474;'" + ">" + label + "<br/>" + Math.round(series.percent) + "%</div>";
        }

        // 默认
        $btnPieDefault.click(function () {
            placeholder.unbind();

            $.plot(placeholder, pieData, {
                series: {
                    pie: {
                        show: true
                    }
                },
                colors: [$.colors("purple", 500), $.colors("blue-grey", 200)]
            });
        });

        // 隐藏图例
        $("#btnPieWithoutLegend").click(function () {
            placeholder.unbind();

            $.plot(placeholder, pieData, {
                series: {
                    pie: {
                        show: true,
                        label: {
                            show: true
                        }
                    }
                },
                colors: [$.colors("purple", 500), $.colors("blue-grey", 200)],
                legend: {
                    show: false
                }
            });
        });

        // 圆形
        $("#btnPieLabelRadius").click(function () {
            placeholder.unbind();

            $.plot(placeholder, pieData, {
                series: {
                    pie: {
                        show: true,
                        radius: 1,
                        label: {
                            show: true,
                            radius: 3 / 4,
                            formatter: labelFormatter
                        }
                    }
                },
                colors: [$.colors("purple", 500), $.colors("blue-grey", 200)],
                legend: {
                    show: false
                }
            });
        });

        // 矩形
        $("#btnPieRectangular").click(function () {
            placeholder.unbind();

            $.plot(placeholder, pieData, {
                series: {
                    pie: {
                        show: true,
                        radius: 500,
                        label: {
                            show: true,
                            formatter: labelFormatter,
                            threshold: 0.1
                        }
                    }
                },
                colors: [$.colors("purple", 500), $.colors("blue-grey", 200)],
                legend: {
                    show: false
                }
            });
        });

        // 环形
        $("#btnPieDonutHole").click(function () {
            placeholder.unbind();

            $.plot(placeholder, pieData, {
                series: {
                    pie: {
                        innerRadius: 0.5,
                        show: true
                    }
                },
                colors: [$.colors("purple", 500), $.colors("blue-grey", 200)]
            });
        });

        // 动态
        $("#btnPieInteractivity").click(function () {
            placeholder.unbind();

            $.plot(placeholder, pieData, {
                series: {
                    pie: {
                        show: true
                    }
                },
                colors: [$.colors("purple", 500), $.colors("blue-grey", 200)],
                grid: {
                    hoverable: true,
                    clickable: true
                }

            });

            placeholder.bind("plothover", function (event, pos, obj) {
                if (!obj) {
                    return;
                }

                var percent = parseFloat(obj.series.percent).toFixed(2);
                $("#hover").html("<span style='font-weight:bold; color:" + obj.series.color + "'>" + obj.series.label + " (" + percent + "%)</span>");
            });

            placeholder.bind("plotclick", function (event, pos, obj) {
                if (!obj) {
                    return;
                }

                var percent = parseFloat(obj.series.percent).toFixed(2);
                toastr.info("" + obj.series.label + ": " + percent + "%");
            });
        });

        // 显示初始默认图表
       $btnPieDefault.click();

    })();


    // 访客图示例
    // ---------------------
    (function () {
        var d = [
            [1196463600000, 0],
            [1196550000000, 0],
            [1196636400000, 0],
            [1196722800000, 77],
            [1196809200000, 3636],
            [1196895600000, 3575],
            [1196982000000, 2736],
            [1197068400000, 1086],
            [1197154800000, 676],
            [1197241200000, 1205],
            [1197327600000, 906],
            [1197414000000, 710],
            [1197500400000, 639],
            [1197586800000, 540],
            [1197673200000, 435],
            [1197759600000, 301],
            [1197846000000, 575],
            [1197932400000, 481],
            [1198018800000, 591],
            [1198105200000, 608],
            [1198191600000, 459],
            [1198278000000, 234],
            [1198364400000, 1352],
            [1198450800000, 686],
            [1198537200000, 279],
            [1198623600000, 449],
            [1198710000000, 468],
            [1198796400000, 392],
            [1198882800000, 282],
            [1198969200000, 208],
            [1199055600000, 229],
            [1199142000000, 177],
            [1199228400000, 374],
            [1199314800000, 436],
            [1199401200000, 404],
            [1199487600000, 253],
            [1199574000000, 218],
            [1199660400000, 476],
            [1199746800000, 462],
            [1199833200000, 448],
            [1199919600000, 442],
            [1200006000000, 403],
            [1200092400000, 204],
            [1200178800000, 194],
            [1200265200000, 327],
            [1200351600000, 374],
            [1200438000000, 507],
            [1200524400000, 546],
            [1200610800000, 482],
            [1200697200000, 283],
            [1200783600000, 221],
            [1200870000000, 483],
            [1200956400000, 523],
            [1201042800000, 528],
            [1201129200000, 483],
            [1201215600000, 452],
            [1201302000000, 270],
            [1201388400000, 222],
            [1201474800000, 439],
            [1201561200000, 559],
            [1201647600000, 521],
            [1201734000000, 477],
            [1201820400000, 442],
            [1201906800000, 252],
            [1201993200000, 236],
            [1202079600000, 525],
            [1202166000000, 477],
            [1202252400000, 386],
            [1202338800000, 409],
            [1202425200000, 408],
            [1202511600000, 237],
            [1202598000000, 193],
            [1202684400000, 357],
            [1202770800000, 414],
            [1202857200000, 393],
            [1202943600000, 353],
            [1203030000000, 364],
            [1203116400000, 215],
            [1203202800000, 214],
            [1203289200000, 356],
            [1203375600000, 399],
            [1203462000000, 334],
            [1203548400000, 348],
            [1203634800000, 243],
            [1203721200000, 126],
            [1203807600000, 157],
            [1203894000000, 288]
        ];

        for (var i = 0; i < d.length; ++i) {
            d[i][0] += 60 * 60 * 1000;
        }

        function weekendAreas(axes) {

            var markings = [],
                d = new Date(axes.xaxis.min);

            d.setUTCDate(d.getUTCDate() - ((d.getUTCDay() + 1) % 7));
            d.setUTCSeconds(0);
            d.setUTCMinutes(0);
            d.setUTCHours(0);

            var i = d.getTime();

            do {
                markings.push({
                    xaxis: {
                        from: i,
                        to: i + 2 * 24 * 60 * 60 * 1000
                    }
                });
                i += 7 * 24 * 60 * 60 * 1000;
            } while (i < axes.xaxis.max);

            return markings;
        }

        var options = {
            series: {
                lines: {
                    show: true,
                    lineWidth: 1
                },
                shadowSize: 0
            },
            colors: [$.colors("purple", 600)],
            selection: {
                mode: "x",
                color: [$.colors("purple", 300)]
            },
            xaxis: {
                tickLength: 0,
                mode: "time",
                color: "#474e54",
                font: {
                    size: 14,
                    weight: 300
                    // family: "OpenSans Light"
                }
            },
            yaxis: {
                tickColor: "#edeff2",
                color: "#474e54",
                font: {
                    size: 14,
                    weight: "300"
                    // family: "OpenSans Light"
                }
            },
            grid: {
                markings: weekendAreas,
                color: "#474e54",
                tickColor: "#e3e6ea",
                backgroundColor: {
                    colors: ["#fff", "#fff"]
                },
                borderWidth: {
                    top: 0,
                    right: 0,
                    bottom: 1,
                    left: 0
                },
                borderColor: "#eef0f2"
            }
        };

        var _plot = $.plot("#exampleFlotVisitors", [d], options);

        var overview = $.plot("#exampleFlotVisitorsOverview", [d], {
            series: {
                lines: {
                    show: true,
                    lineWidth: 1
                },
                shadowSize: 0
            },
            colors: [$.colors("purple", 600)],
            xaxis: {
                ticks: [],
                mode: "time"
            },
            yaxis: {
                ticks: [],
                min: 0,
                autoscaleMargin: 0.1
            },
            selection: {
                mode: "x",
                color: [$.colors("purple", 300)]
            },
            grid: {
                // markings: weekendAreas,
                color: "#474e54",
                tickColor: "#e3e6ea",
                backgroundColor: {
                    colors: ["#fff", "#fff"]
                },
                borderWidth: {
                    top: 1,
                    right: 1,
                    bottom: 1,
                    left: 1
                },
                borderColor: "#eef0f2"
            }
        });

        $("#exampleFlotVisitors").bind("plotselected", function (event, ranges) {

            $.each(_plot.getXAxes(), function (_, axis) {
                var opts = axis.options;
                opts.min = ranges.xaxis.from;
                opts.max = ranges.xaxis.to;
            });
            _plot.setupGrid();
            _plot.draw();
            _plot.clearSelection();

            overview.setSelection(ranges, true);
        });

        $("#exampleFlotVisitorsOverview").bind("plotselected", function (event, ranges) {
            _plot.setSelection(ranges);
        });
    })();

    // Tooltip 示例
    // --------------------
    (function () {
        $("<div class='flot-tooltip'></div>").css({
            position: "absolute",
            color: "#fff",
            display: "none",
            border: "1px solid #777",
            padding: "2px",
            "background-color": "#777",
            opacity: 0.80
        }).appendTo("body");


        $("#exampleFlotCurve").bind("plothover", function (event, pos, item) {
            if (item) {
                var x = item.datapoint[0].toFixed(2),
                    y = item.datapoint[1].toFixed(2);
                $(".flot-tooltip").html(item.series.label + " ：" + x + "，" + y)
                    .css({
                        top: item.pageY + 5,
                        left: item.pageX + 5
                    })
                    .fadeIn(200);
            } else {
                $(".flot-tooltip").hide();
            }
        });

        $("#exampleFlotFullBg").bind("plothover", function (event, pos, item) {
            if (item) {
                var x = item.datapoint[0].toFixed(2),
                    y = item.datapoint[1].toFixed(2);
                $(".flot-tooltip").html(item.series.label + " ： " + x + " ， " + y)
                    .css({
                        top: item.pageY + 5,
                        left: item.pageX + 5
                    })
                    .fadeIn(200);
            } else {
                $(".flot-tooltip").hide();
            }
        });

        $("#exampleFlotRealtime").bind("plothover", function (event, pos, item) {
            if (item) {
                var x = item.datapoint[0].toFixed(2),
                    y = item.datapoint[1].toFixed(2);
                $(".flot-tooltip").html("x | " + x + "," + " y | " + y)
                    .css({
                        top: item.pageY + 5,
                        left: item.pageX + 5
                    })
                    .fadeIn(200);
            } else {
                $(".flot-tooltip").hide();
            }
        });

    })();

})(document, window, jQuery);