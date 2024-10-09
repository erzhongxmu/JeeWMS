/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("highlight", {
        mode: "init",
        defaults: {},
        init: function (context) {
            if (typeof hljs === "undefined") {
                return;
            }

            $('[data-plugin="highlight"]', context).each(function (i, block) {
                hljs.highlightBlock(block);
            });
        }
    });
})(window, document, jQuery);