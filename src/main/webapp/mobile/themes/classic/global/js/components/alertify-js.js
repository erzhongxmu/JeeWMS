/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    $.components.register("alertify", {
        mode: "init",
        defaults: {
            type: "alert",
            theme: "bootstrap"
        },
        init: function () {
            if (typeof alertify === "undefined") {
                return;
            }

            var defaults = $.components.getDefaults("alertify");

            $(document).on('click.site.alertify', '[data-plugin="alertify"]', function () {
                var $this = $(this),
                    options = $.extend(true, {}, defaults, $this.data());

                if (options.labelOk) {
                    options.okBtn = options.labelOk;
                }

                if (options.labelCancel) {
                    options.cancelBtn = options.labelCancel;
                }

                if (typeof options.delay !== 'undefined') {
                    alertify.delay(options.delay);
                }

                if (typeof options.theme !== 'undefined') {
                    alertify.theme(options.theme);
                }

                if (typeof options.cancelBtn !== 'undefined') {
                    alertify.cancelBtn(options.cancelBtn);
                }

                if (typeof options.okBtn !== 'undefined') {
                    alertify.okBtn(options.okBtn);
                }

                if (typeof options.placeholder !== 'undefined') {
                    alertify.delay(options.placeholder);
                }

                if (typeof options.defaultValue !== 'undefined') {
                    alertify.delay(options.defaultValue);
                }

                if (typeof options.maxLogItems !== 'undefined') {
                    alertify.delay(options.maxLogItems);
                }

                if (typeof options.closeLogOnClick !== 'undefined') {
                    alertify.delay(options.closeLogOnClick);
                }

                switch (options.type) {
                    case "alert":
                        alertify.alert(options.alertMessage);
                        break;
                    case "confirm":
                        alertify.confirm(options.confirmTitle, function () {
                            alertify.success(options.successMessage);
                        }, function () {
                            alertify.error(options.errorMessage);
                        });
                        break;
                    case "prompt":
                        alertify.prompt(options.promptTitle, function (str) {
                            var message = options.successMessage.replace('%s', str);
                            alertify.success(message);
                        }, function () {
                            alertify.error(options.errorMessage);
                        });
                        break;
                    case "log":
                        alertify.log(options.logMessage);
                        break;
                    case "success":
                        alertify.success(options.successMessage);
                        break;
                    case "error":
                        alertify.error(options.errorMessage);
                        break;
                }
            });
        }
    });

})(window, document, jQuery);