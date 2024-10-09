/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    var t = $('#dataTableExample').DataTable($.po('dataTable'));
    var counter = 1;

    $('#admui-pageContent').on('click', '#DTAddRow', function () {
        t.row.add([
            counter + '.1',
            counter + '.2',
            counter + '.3',
            counter + '.4',
            counter + '.5'
        ]).draw(false);

        counter++;
    });

    // 自动添加第一行的数据
    $('#DTAddRow').click();
})(window, document, jQuery);

