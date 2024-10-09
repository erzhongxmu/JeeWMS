/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("buttons", {
        mode: "api",
        defaults: {},
        api: function () {
            $(document).on('click.site.loading', '[data-loading-text]', function () {
                var $btn = $(this),
                    text = $btn.text(),
                    i = 20,
                    loadingText = $btn.data('loadingText');

                $btn.text(loadingText + '(' + i + ')').css('opacity', '.6');

                var timeout = setInterval(function () {
                    $btn.text(loadingText + '(' + (--i) + ')');
                    if (i === 0) {
                        clearInterval(timeout);
                        $btn.text(text).css('opacity', '1');
                    }
                }, 1000);
            });

            $(document).on('click.site.morebutton', '[data-more]', function () {
                var $target = $($(this).data('more'));
                $target.toggleClass('show');
            });
        }
    });
})(window, document, jQuery);