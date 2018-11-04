/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("verticalTab", {
        mode: "init",
        init: function (context) {
            if (!$.fn.matchHeight) {
                return;
            }

            $('.nav-tabs-vertical', context).each(function () {
                $(this).children().matchHeight();
            });
        }
    });

    $.components.register("horizontalTab", {
        mode: "init",
        init: function (context) {
            if (!$.fn.responsiveHorizontalTabs) {
                return;
            }

            var $nav = $('[data-approve="nav-tabs"]', context);
            $nav.each(function () {
                var $item = $(this),
                    options = $.extend(true, {}, $item.data());

                $item.responsiveHorizontalTabs(options);
            });
        }
    });
})(window, document, jQuery);