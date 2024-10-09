/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $('#dataTableExample').DataTable($.po('dataTable', {
        "processing": true,
        "ajax": $.ctx + "/data/examples/tables/dt-ajax-5.json",
        // 默认为data，这里定义为demo
        "dataSrc": "demo"
    }));
})(window, document, jQuery);

