/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    function format(d) {
        return '入职时间: ' + d.hireDate + '<br>' +
            '年薪: ' + d.salary + '<br>' +
            '子行可以放置一些您希望显示的信息，如图片，链接，表格等';
    }

    var dt = $('#dataTableExample').DataTable($.po('dataTable', {
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "http://demo.admui.com/employee/all/get",
            "dataType": "jsonp"
        },
        "columns": [
            {
                "class": "details-control",
                "orderable": false,
                "data": null,
                "defaultContent": ""
            },
            {"data": "name"},
            {"data": "title"},
            {"data": "base"},
            {"data": "age"}

        ],
        "order": [[1, 'asc']]
    }));

    // 数组跟踪显示行详细信息的ID
    var detailRows = [];

    $('#dataTableExample tbody').on('click', 'tr td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = dt.row(tr);
        var idx = $.inArray(tr.attr('id'), detailRows);

        if (row.child.isShown()) {
            tr.removeClass('details');
            row.child.hide();

            // 从'open'数组中移除
            detailRows.splice(idx, 1);
        }
        else {
            tr.addClass('details');
            row.child(format(row.data())).show();

            // 向'open'数组添加
            if (idx === -1) {
                detailRows.push(tr.attr('id'));
            }
        }
    });

    // 在每次绘制时，循环'detailRows'数组并显示子行
    dt.on('draw', function () {
        $.each(detailRows, function (i, id) {
            $('#' + id + ' td.details-control').trigger('click');
        });
    });
})(window, document, jQuery);

