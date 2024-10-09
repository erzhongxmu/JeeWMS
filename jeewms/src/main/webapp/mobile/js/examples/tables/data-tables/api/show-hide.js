/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    var table = $('#dataTableExample').DataTable($.po('dataTable', {
        "scrollY": "200px",
        "paging": false
    }));

    $('#admui-pageContent').on('click', '#DTToggleBtn .btn', function () {
        // 获取 API 对象
        var column = table.column($(this).attr('data-column'));

        // 显示切换
        column.visible(!column.visible());
    });
})(window, document, jQuery);

