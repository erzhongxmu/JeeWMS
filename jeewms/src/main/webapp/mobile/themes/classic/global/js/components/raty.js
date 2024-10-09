/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("rating", {
        mode: "init",
        defaults: {
            targetKeep: true,
            icon: "font",
            starType: "i",
            starOff: "icon wb-star",
            starOn: "icon wb-star orange-600",
            cancelOff: "icon wb-minus-circle",
            cancelOn: "icon wb-minus-circle orange-600",
            starHalf: "icon wb-star-half orange-500",
            cancelHint: '取消评分',
            hints: ['很差', '差', '一般', '好', '非常好']
        },
        init: function (context) {
            if (!$.fn.raty) {
                return;
            }

            var defaults = this.defaults;

            $('[data-plugin="rating"]', context).each(function () {
                var $this = $(this);

                var options = $.extend(true, {}, defaults, $this.data());


                if (options.hints && typeof options.hints === 'string') {
                    options.hints = options.hints.split(',');
                }

                $this.raty(options);
            });
        }
    });
})(window, document, jQuery);