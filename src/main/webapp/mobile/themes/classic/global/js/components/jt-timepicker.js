/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("timepicker", {
        mode: "default",
        defaults: {
            lang: {
                am: '上午',
                pm: '下午',
                AM: '上午',
                PM: '下午',
                decimal: '.',
                mins: '分钟',
                hr: '小时',
                hrs: '小时'
            },
            timeFormat: 'ag:i'
        }
    });
})(window, document, jQuery);