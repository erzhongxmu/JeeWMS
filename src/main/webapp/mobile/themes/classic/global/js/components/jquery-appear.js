/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("appear", {
        defaults: {},
        api: function () {
            if (!$.fn.appear) {
                return;
            }

            $("#admui-pageContent").on("appear", '[data-plugin="appear"]', function () {
                var $item = $(this),
                    animate = $item.data("animate");

                if ($item.hasClass('appear-no-repeat')) {
                    return;
                }
                $item.removeClass("invisible").addClass('animation-' + animate);

                if ($item.data("repeat") === false) {
                    $item.addClass('appear-no-repeat');
                }
            });

            $("#admui-pageContent").on("disappear", '[data-plugin="appear"]', function () {
                var $item = $(this),
                    animate = $item.data("animate");

                if ($item.hasClass('appear-no-repeat')) {
                    return;
                }

                $item.addClass("invisible").removeClass('animation-' + animate);
            });
        },

        init: function (context) {
            if (!$.fn.appear) {
                return;
            }
            var defaults = $.components.getDefaults("appear");

            $('[data-plugin="appear"]', context).appear(defaults);
            $('[data-plugin="appear"]', context).not(':appeared').addClass("invisible");
        }
    });
})(window, document, jQuery);