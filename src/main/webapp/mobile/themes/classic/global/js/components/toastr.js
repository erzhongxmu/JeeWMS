/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $.components.register("toastr", {
        mode: "api",
        defaults: {},
        api: function () {
            if (typeof toastr === "undefined") {
                return;
            }
            var defaults = $.components.getDefaults("toastr");

            $('#admui-pageContent').on('click.site.toastr', '[data-plugin="toastr"]', function (e) {
                e.preventDefault();
                
                var $this = $(this),
                    options = $.extend(true, {}, defaults, $this.data()),
                    message = options.message || '',
                    type = options.type || "info",
                    title = options.title || undefined;

                switch (type) {
                    case "success":
                        toastr.success(message, title, options);
                        break;
                    case "warning":
                        toastr.warning(message, title, options);
                        break;
                    case "error":
                        toastr.error(message, title, options);
                        break;
                    case "info":
                        toastr.info(message, title, options);
                        break;
                    default:
                        toastr.info(message, title, options);
                }
            });
        }
    });
})(window, document, jQuery);