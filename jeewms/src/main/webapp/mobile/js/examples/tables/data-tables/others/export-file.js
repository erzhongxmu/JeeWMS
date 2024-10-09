/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function($){
    "use strict";

    $('.dataTable').DataTable($.po('dataTable', {
        dom: '<"row"<"col-xs-6"<"hidden-xs"B>><"col-xs-6"f>><"row"<"col-xs-12"tr>><"row"<"col-sm-5"i><"col-sm-7"p>>',
        processing: true,
        autoWidth: false, //禁用自动调整列宽
        rowId: 'userId',
        buttons: {
            dom: {
                container: {
                    className: 'btn-group btn-group-sm'
                },
                button: {
                    className: 'btn btn-default btn-outline'
                }
            },
            buttons: [
                {
                    extend: 'copy',
                    text: '拷贝'
                },
                {
                    extend: 'excel',
                    text: '导出 Excel'
                },
                {
                    extend: 'csv',
                    text: '导出 CSV'
                },
                {
                    extend: 'print',
                    text: '打印'
                }
            ]
        }
    }));
})(jQuery);