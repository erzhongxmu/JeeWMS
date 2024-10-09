/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("nprogress", {
        mode: "init",
        defaults: {
            minimum: 0.15,
            trickleRate: 0.07,
            trickleSpeed: 360,
            showSpinner: false,
            template: '<div class="bar" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
        },
        init: function () {
            if (typeof NProgress === "undefined") {
                return;
            }
            var defaults = $.components.getDefaults('nprogress');
            NProgress.configure(defaults);
        }
    });
})(window, document, jQuery);