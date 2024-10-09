/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $('#dataTableExample').dataTable($.po('dataTable', {
        "aoColumns": [
            null,
            null,
            {"orderSequence": ["asc"]},
            {"orderSequence": ["desc", "asc", "asc"]},
            {"orderSequence": ["desc"]},
            null
        ]
    }));
})(window, document, jQuery);

