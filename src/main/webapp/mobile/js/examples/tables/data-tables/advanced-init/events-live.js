/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    var table = $('#dataTableExample').DataTable($.po('dataTable'));
    $('#admui-pageContent').on('click', '#dataTableExample tbody tr', function () {
        var data = table.row(this).data();
        toastr.info('您单击了"' + data[0] + '"的行');
    });
})(window, document, jQuery);

