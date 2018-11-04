/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("card", {
        mode: "init",
        defaults: {},
        init: function (context) {
            if (!$.fn.card) {
                return;
            }
            var defaults = $.components.getDefaults("card");

            $('[data-plugin="card"]', context).each(function () {
                var options = $.extend({}, defaults, $(this).data());

                if (options.target) {
                    options.container = $(options.target);
                }
                $(this).card(options);
            });
        }
    });
})(window, document, jQuery);