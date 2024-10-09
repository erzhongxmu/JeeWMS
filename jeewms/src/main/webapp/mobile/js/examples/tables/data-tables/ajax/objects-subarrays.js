/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $('#dataTableExample').DataTable($.po('dataTable', {
        "processing": true,
        "ajax": $.ctx + "/data/examples/tables/dt-ajax-1.json",
        "columns": [
            {"data": "name"},
            {"data": "hr.position"},
            {"data": "contact.0"},
            {"data": "contact.1"},
            {"data": "hr.start_date"},
            {"data": "hr.salary"}
        ]
    }));
})(window, document, jQuery);

