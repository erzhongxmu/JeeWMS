/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("selective", {
        mode: "default",
        defaults: {
            search: function() {
                return '<input class="' + this.namespace + '-search" type="text" placeholder="搜索…">';
            },
            triggerButton: function() {
                return '<div class="' + this.namespace + '-trigger-button">添加</div>';
            }
        }
    });
})(window, document, jQuery);