/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("gridstack", {
        mode: "init",
        defaults: {
            cellHeight: 80,
            verticalMargin: 20
        },
        init: function(context) {
            if (!$.fn.gridstack) {
                return;
            }

            var defaults = $.components.getDefaults("gridstack");

            $('[data-plugin="gridstack"]', context).each(function() {
                var options = $.extend(true, {}, defaults, $(this).data());

                $(this).gridstack(options);
            });
        }
    });
})(window, document, jQuery);