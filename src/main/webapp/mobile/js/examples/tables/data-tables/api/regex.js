/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    function filterGlobal() {
        $('#dataTableExample').DataTable($.po('dataTable')).search(
            $('#global_filter').val(),
            $('#global_regex').prop('checked'),
            $('#global_smart').prop('checked')
        ).draw();
    }

    function filterColumn(i) {
        $('#dataTableExample').DataTable($.po('dataTable')).column(i).search(
            $('#col' + i + '_filter').val(),
            $('#col' + i + '_regex').prop('checked'),
            $('#col' + i + '_smart').prop('checked')
        ).draw();
    }

    $('#dataTableExample').DataTable($.po('dataTable'));

    $('#admui-pageContent').on('keyup click', 'input.global_filter', function () {
        filterGlobal();
    });

    $('#admui-pageContent').on('keyup click', 'input.column_filter', function () {
        filterColumn($(this).parents('tr').attr('data-column'));
    });
})(window, document, jQuery);

