/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $.components.register("slimScroll", {
        mode: "default",
        defaults: {
            height : '100%',
            size : '4px',
            color: $.configs.colors['blue-grey']['500'],
            position : 'right',
            distance : '1px',
            railVisible : true,
            railColor : $.configs.colors['blue-grey']['300'],
            railOpacity : 0.1,
            railDraggable : true,
            wheelStep : 15,
            borderRadius: '4px',
            railBorderRadius : '4px'
        }
    });

})(window, document, jQuery);
