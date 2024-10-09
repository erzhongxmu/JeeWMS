/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    /*global Switchery*/

    $.components.register("switchery", {
        mode: "init",
        defaults: {
            color: $.colors("purple", 600)
        },
        init: function (context) {
            if (typeof Switchery === "undefined") {
                return;
            }

            var defaults = $.components.getDefaults("switchery");

            $('[data-plugin="switchery"]', context).each(function () {
                var options = $.extend({}, defaults, $(this).data());

                new Switchery(this, options);
            });
        }
    });
})(window, document, jQuery);