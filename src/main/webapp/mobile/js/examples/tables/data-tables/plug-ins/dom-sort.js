/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    /* Initialise the table with the required column ordering data types */
    /* Create an array with the values of all the input boxes in a column */
    $.fn.dataTable.ext.order['dom-text'] = function (settings, col) {
        return this.api().column(col, {order: 'index'}).nodes().map(function (td) {
            return $('input', td).val();
        });
    };

    /* Create an array with the values of all the input boxes in a column, parsed as numbers */
    $.fn.dataTable.ext.order['dom-text-numeric'] = function (settings, col) {
        return this.api().column(col, {order: 'index'}).nodes().map(function (td) {
            return $('input', td).val() * 1;
        });
    };

    /* Create an array with the values of all the select options in a column */
    $.fn.dataTable.ext.order['dom-select'] = function (settings, col) {
        return this.api().column(col, {order: 'index'}).nodes().map(function (td) {
            return $('select', td).val();
        });
    };

    /* Create an array with the values of all the checkboxes in a column */
    $.fn.dataTable.ext.order['dom-checkbox'] = function (settings, col) {
        return this.api().column(col, {order: 'index'}).nodes().map(function (td) {
            return $('input', td).prop('checked') ? '1' : '0';
        });
    };

    $('#dataTableExample').DataTable($.po('dataTable', {
        "columns": [
            null,
            {"orderDataType": "dom-text-numeric"},
            {"orderDataType": "dom-select"},
            {"orderDataType": "dom-text", type: 'string'},
            null,
            null
        ]
    }));
})(window, document, jQuery);

