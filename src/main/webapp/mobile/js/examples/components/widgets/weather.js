/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document) {
    'use strict';

    // 天气小工具示例

    var ex1_sunny = new Skycons({
        "color": "white"
    });
    ex1_sunny.set(document.getElementById("ex4-rain"), "rain");
    ex1_sunny.play();

    var ex5 = new Skycons({
            "color": "white"
        }),
        ex5_list = [
            "ex5-partly-cloudy",
            "ex5-sunny",
            "ex5-cloudy",
            "ex5-rain"
        ],
        ex5_type = [
            "partly-cloudy-day",
            "clear-day",
            "cloudy",
            "rain"
        ],
        ex5_i;

    for (ex5_i = ex5_list.length; ex5_i--;) {
        ex5.set(ex5_list[ex5_i], ex5_type[ex5_i]);
    }

    ex5.play();

    var ex6_snow = new Skycons({
        "color": "white"
    });
    ex6_snow.set(document.getElementById("ex6-snow"), "snow");
    ex6_snow.play();

    var ex7_sleet = new Skycons({
        "color": "#212121"
    });
    ex7_sleet.set(document.getElementById("ex7-sleet"), "sleet");
    ex7_sleet.play();

})(document);