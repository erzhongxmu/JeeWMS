/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    var table = $('#dataTableExample').DataTable($.po('dataTable')),
        $pageContent = $('#admui-pageContent');

    $pageContent.on('click', '#dataTableExample tbody tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

    $pageContent.on('click', '#DTDelRow',function () {
        table.row('.selected').remove().draw(false);
    });
})(window, document, jQuery);

