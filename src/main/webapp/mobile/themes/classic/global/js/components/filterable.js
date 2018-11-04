/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("filterable", {
        mode: "init",
        defaults: {
            animationOptions: {
                duration: 750,
                easing: 'linear',
                queue: false
            }
        },
        init: function (context) {
            if (typeof $.fn.isotope === "undefined") {
                return;
            }

            var defaults = $.components.getDefaults('filterable');

            var callback = function () {
                $('[data-filterable]', context).each(function () {
                    var $this = $(this);

                    var options = $.extend(true, {}, defaults, $this.data(), {
                        filter: '*'
                    });

                    $this.isotope(options);
                });

                $('[data-filter]', context).click(function (e) {
                    var $this = $(this);
                    var target = $this.data('target');
                    var $li = $this.parent('li');

                    if (!target) {
                        target = $this.attr('href');
                        target = target && target.replace(/.*(?=#[^\s]*$)/, '');
                    }

                    $li.siblings('.active').each(function () {
                        $(this).find('a').attr('aria-expanded', false);
                        $(this).removeClass('active');
                    });

                    $li.addClass('active');
                    $this.attr('aria-expanded', true);

                    var $list = $(target, context);
                    var filter = $this.attr('data-filter');
                    if (filter !== '*') {
                        filter = '[data-type="' + filter + '"]';
                    }
                    $list.isotope({
                        filter: filter
                    });

                    e.preventDefault();
                });
            };

            if (context !== document) {
                callback();
            } else {
                $(window).on('load', function () {
                    callback();
                });
            }
        }
    });
})(window, document, jQuery);