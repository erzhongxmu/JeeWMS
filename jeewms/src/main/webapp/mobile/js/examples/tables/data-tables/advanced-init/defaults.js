/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $.extend(true, $.fn.dataTable.defaults, $.po('dataTable'), {
        "searching": false,
        "ordering": false
    });

    $('#dataTableExample').DataTable();
})(window, document, jQuery);

