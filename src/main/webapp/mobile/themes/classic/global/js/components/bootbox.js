/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("bootbox", {
        mode: "init",
        defaults: {
            message: "",
            locale: "zh_CN"
        },
        init: function () {
            if (typeof bootbox === "undefined") {
                return;
            }
            var defaults = $.components.getDefaults("bootbox");

            $(document).on('click.site.bootbox', '[data-plugin="bootbox"]', function () {
                var $item = $(this),
                    options = $item.data();

                options = $.extend(true, {}, defaults, options);
                if (options.classname) {
                    options.className = options.classname;
                }

                if (typeof options.callback === "string" && $.isFunction(window[options.callback])) {
                    options.callback = window[options.callback];
                }

                if (options.type) {
                    switch (options.type) {
                        case "alert":
                            bootbox.alert(options);
                            break;
                        case "confirm":
                            bootbox.confirm(options);
                            break;
                        case "prompt":
                            bootbox.prompt(options);
                            break;
                        default:
                            bootbox.dialog(options);
                    }
                } else {
                    bootbox.dialog(options);
                }
            });
        }
    });
})(window, document, jQuery);