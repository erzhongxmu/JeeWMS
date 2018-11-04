/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    var table = $('#dataTableExample').DataTable($.po('dataTable', {
        "ajax": $.ctx + "/data/examples/tables/dt-ajax.json",
        "columnDefs": [{
            "targets": -1,
            "data": null,
            "defaultContent": '<button class="btn btn-outline btn-default btn-sm">查看</button>'
        }]
    }));

    $('#admui-pageContent').on('click', '#dataTableExample tbody button', function () {
        var data = table.row($(this).parents('tr')).data();
        toastr.info(data[0] + " 的年薪是：" + data[5]);
    });
})(window, document, jQuery);

