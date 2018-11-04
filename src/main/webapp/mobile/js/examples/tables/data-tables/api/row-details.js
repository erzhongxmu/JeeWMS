/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    window.Content = {
        run: function () {
            /* 格式化详情功能 - 根据需要修改 */
            function format(d) {
                // `d` 是该行的原始数据对象
                return '<table class="table table-bordered text-nowrap padding-left-50 margin-bottom-0" cellpadding="5">' +
                    '<tr>' +
                    '<td>姓名：</td>' +
                    '<td>' + d.name + '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>分机号码：</td>' +
                    '<td>' + d.extn + '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>备注：</td>' +
                    '<td>添加详细信息（如图片等）</td>' +
                    '</tr>' +
                    '</table>';
            }

            var table = $('#dataTableExample').DataTable($.po('dataTable', {
                "ajax": $.ctx + "/data/examples/tables/dt-ajax-2.json",
                "columns": [
                    {
                        "class": 'details-control',
                        "orderable": false,
                        "data": null,
                        "defaultContent": ''
                    },
                    {"data": "name"},
                    {"data": "position"},
                    {"data": "office"},
                    {"data": "salary"}
                ],
                "order": [[1, 'asc']]
            }));
            // 展开关闭详情时的事件监听
            $('#admui-pageContent').on('click', '#dataTableExample tbody td.details-control', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                if (row.child.isShown()) {
                    // 本行已展开
                    row.child.hide();
                    tr.removeClass('shown');
                }
                else {
                    // 展开本行
                    row.child(format(row.data())).show();
                    tr.addClass('shown');
                }
            });
        }
    };

})(window, document, jQuery);

