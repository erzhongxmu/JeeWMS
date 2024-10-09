/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */

(function(window, document, $){
    "use strict";

    $.components.register("markdown", {
        mode: "init",
        defaults: {
            autofocus: false,
            savable: false,
            language: 'zh'
        },
        init: function (context) {
            if (!$.fn.markdown) {
                return;
            }

            var defaults = this.defaults;

            $('textarea[data-plugin="markdown"]', context).each(function () {
                var options = $.extend(true, {}, defaults, $(this).data());

                $(this).markdown(options);
            });
        }
    });
})(window, document, jQuery);