/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("sweetalert", {
        mode: "api",
        defaults: {
            confirmButtonText: '确定',
            cancelButtonText: '取消'
        },
        api: function () {
            if (typeof swal === "undefined") {
                return;
            }

            var defaults = $.components.getDefaults("sweetalert");

            $(document).on('click.site.sweetalert', '[data-plugin="sweetalert"]', function () {
                var options = $.extend(true, {}, defaults, $(this).data());

                swal(options);
            });
        }
    });
})(window, document, jQuery);