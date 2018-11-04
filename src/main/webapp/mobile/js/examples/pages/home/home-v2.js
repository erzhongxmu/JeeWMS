/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function () {
    'use strict';

    //统计图表
    new Chartist.Line('#widgetLinearea .ct-chart', {
        labels: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        series: [
            [0, 2.5, 2, 2.8, 2.6, 3.8, 0],
            [0, 1.4, 0.5, 2, 1.2, 0.9, 0]
        ]
    }, {
        low: 0,
        showArea: true,
        showPoint: false,
        showLine: false,
        fullWidth: true,
        chartPadding: {
            top: 0,
            right: 10,
            bottom: 0,
            left: 0
        },
        axisX: {
            showGrid: false,
            labelOffset: {
                x: -14,
                y: 0
            }
        },
        axisY: {
            labelOffset: {
                x: -10,
                y: 0
            },
            labelInterpolationFnc: function (num) {
                return num % 1 === 0 ? num : false;
            }
        }
    });
})();