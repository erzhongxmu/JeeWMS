/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $('#dataTableExample').DataTable($.po('dataTable', {
        "columns": [
            {"data": "姓名"},
            {"data": "职位"},
            {"data": "工作地点"},
            {"data": "年龄"},
            {"data": "入职时间"},
            {"data": "年薪"}
        ]
    }));
})(window, document, jQuery);

