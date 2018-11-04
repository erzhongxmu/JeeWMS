/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("peityBar", {
        mode: "init",
        defaults: {
            delimiter: ",",
            fill: [$.colors("purple", 400)],
            height: 18,
            max: null,
            min: 0,
            padding: 0.1,
            width: 44
        },
        init: function (context) {
            if (!$.fn.peity) {
                return;
            }

            var defaults = $.components.getDefaults("peityBar");

            $('[data-plugin="peityBar"]', context).each(function () {
                var $this = $(this),
                    options = $this.data();

                if (options.skin) {
                    if ($.colors(options.skin)) {
                        var skinColors = $.colors(options.skin);
                        defaults.fill = [skinColors[400]];
                    }
                }

                options = $.extend(true, {}, defaults, options);

                $this.peity('bar', options);
            });
        }
    });

    $.components.register("peityDonut", {
        mode: "init",
        defaults: {
            delimiter: null,
            fill: [$.colors("purple", 700), $.colors("purple", 400), $.colors("purple", 200)],
            height: null,
            innerRadius: null,
            radius: 11,
            width: null
        },
        init: function (context) {
            if (!$.fn.peity) {
                return;
            }

            var defaults = $.components.getDefaults("peityDonut");

            $('[data-plugin="peityDonut"]', context).each(function () {
                var $this = $(this),
                    options = $this.data();

                if (options.skin) {
                    if ($.colors(options.skin)) {
                        var skinColors = $.colors(options.skin);
                        defaults.fill = [skinColors[700], skinColors[400], skinColors[200]];
                    }
                }

                options = $.extend(true, {}, defaults, options);

                $this.peity('donut', options);
            });
        }
    });

    $.components.register("peityLine", {
        mode: "init",
        defaults: {
            delimiter: ",",
            fill: [$.colors("purple", 200)],
            height: 18,
            max: null,
            min: 0,
            stroke: $.colors("purple", 600),
            strokeWidth: 1,
            width: 44
        },
        init: function (context) {
            if (!$.fn.peity) {
                return;
            }

            var defaults = $.components.getDefaults("peityLine");

            $('[data-plugin="peityLine"]', context).each(function () {
                var $this = $(this),
                    options = $this.data();

                if (options.skin) {
                    if ($.colors(options.skin)) {
                        var skinColors = $.colors(options.skin);
                        defaults.fill = [skinColors[200]];
                        defaults.stroke = skinColors[600];
                    }
                }

                options = $.extend(true, {}, defaults, options);

                $this.peity('line', options);
            });
        }
    });

    $.components.register("peityPie", {
        mode: "init",
        defaults: {
            delimiter: null,
            fill: [$.colors("purple", 700), $.colors("purple", 400), $.colors("purple", 200)],
            height: null,
            radius: 11,
            width: null
        },
        init: function (context) {
            if (!$.fn.peity) {
                return;
            }

            var defaults = $.components.getDefaults("peityPie");

            $('[data-plugin="peityPie"]', context).each(function () {

                var $this = $(this),
                    options = $this.data();

                if (options.skin) {
                    if ($.colors(options.skin)) {
                        var skinColors = $.colors(options.skin);
                        defaults.fill = [skinColors[700], skinColors[400], skinColors[200]];
                    }
                }

                options = $.extend(true, {}, defaults, options);

                $this.peity('pie', options);
            });
        }
    });
})(window, document, jQuery);