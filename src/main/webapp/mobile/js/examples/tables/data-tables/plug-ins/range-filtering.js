/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $.fn.dataTable.ext.search.push(
        function (settings, data) {
            var min = parseInt($('#DTMinAge').val(), 10);
            var max = parseInt($('#DTMaxAge').val(), 10);
            var age = parseFloat(data[3]) || 0;

            return !!(( isNaN(min) && isNaN(max) ) ||
            ( isNaN(min) && age <= max ) ||
            ( min <= age && isNaN(max) ) ||
            ( min <= age && age <= max ));
            
        }
    );

    var table = $('#dataTableExample').DataTable($.po('dataTable'));

    $('#admui-pageContent').on('keyup', '#DTMinAge, #DTMaxAge',function () {
        table.draw();
    });
})(window, document, jQuery);

