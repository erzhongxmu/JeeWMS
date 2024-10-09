/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("material", {
        init: function (context) {
            $('.form-material', context).each(function () {
                var $this = $(this);

                if ($this.data('material') === true) {
                    return;
                }

                var $control = $this.find('.form-control');

                // Add hint label if required
                if ($control.attr("data-hint")) {
                    $control.after("<div class=hint>" + $control.attr("data-hint") + "</div>");
                }

                if ($this.hasClass("floating")) {
                    // Add floating label if required
                    if ($control.hasClass("floating-label")) {
                        var placeholder = $control.attr("placeholder");
                        $control.attr("placeholder", null).removeClass("floating-label");
                        $control.after("<div class=floating-label>" + placeholder + "</div>");
                    }

                    // Set as empty if is empty
                    if ($control.val() === null || $control.val() === "undefined" || $control.val() === "") {
                        $control.addClass("empty");
                    }
                }

                // Support for file input
                if ($control.next().is("[type=file]")) {
                    $this.addClass('form-material-file');
                }

                $this.data('material', true);
            });
        },
        api: function () {
            function _isChar(e) {
                if (typeof e.which === "undefined") {
                    return true;
                } else if (typeof e.which === "number" && e.which > 0) {
                    return !e.ctrlKey && !e.metaKey && !e.altKey && e.which !== 8 && e.which !== 9;
                }
                return false;
            }

            $(document).on("keydown.site.material paste.site.material", ".form-control", function (e) {
                if (_isChar(e)) {
                    $(this).removeClass("empty");
                }
            }).on("keyup.site.material change.site.material", ".form-control", function () {
                var $this = $(this);
                if ($this.val() === "" && (typeof $this[0].checkValidity !== "undefined" && $this[0].checkValidity())) {
                    $this.addClass("empty");
                } else {
                    $this.removeClass("empty");
                }
            }).on("focus", ".form-material-file", function () {
                $(this).find("input").addClass("focus");
            })
                .on("blur", ".form-material-file", function () {
                    $(this).find("input").removeClass("focus");
                })
                .on("change", ".form-material-file [type=file]", function () {
                    var $this = $(this);
                    var value = "";
                    $.each($this[0].files, function (i, file) {
                        value += file.name + ", ";
                    });
                    value = value.substring(0, value.length - 2);
                    if (value) {
                        $this.prev().removeClass("empty");
                    } else {
                        $this.prev().addClass("empty");
                    }
                    $this.prev().val(value);
                });
        }
    });
})(window, document, jQuery);