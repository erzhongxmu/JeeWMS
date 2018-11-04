/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $('#dataTableExample').DataTable($.po('dataTable', {
        initComplete: function () {
            var api = this.api();
            api.columns().indexes().flatten().each(function (i) {
                var column = api.column(i);
                var select = $('<select class="form-control"><option value="">--请选择--</option></select>')
                    .appendTo($(column.footer()).empty())
                    .on('change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
                        column
                            .search(val ? '^' + val + '$' : '', true, false)
                            .draw();
                    });
                column.data().unique().sort().each(function (d) {
                    select.append('<option value="' + d + '">' + d + '</option>');
                });
            });
        }
    }));
})(window, document, jQuery);

