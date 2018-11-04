/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $.fn.dataTable.Api.register('column().data().sum()', function () {
        return this.reduce(function (a, b) {
            var x = parseFloat(a) || 0;
            var y = parseFloat(b) || 0;
            return x + y;
        });
    });

    var table = $('#dataTableExample').DataTable($.po('dataTable'));

    $('<button type="button" class="btn btn-outline btn-default">点击计算所有行里年龄列的总和</button>')
        .prependTo('#dataTableExampleButton')
        .on('click', function () {
            toastr.info('所有行的年龄总和为：' + table.column(3).data().sum());
        });

    $('<button type="button" class="btn btn-outline btn-default">点击计算可见行年龄列的总和</button>')
        .prependTo('#dataTableExampleButton')
        .on('click', function () {
            toastr.info('可见行的年龄总和为：' + table.column(3, {page: 'current'}).data().sum());
        });
})(window, document, jQuery);

