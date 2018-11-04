/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    var table = $('#dataTableExample').DataTable($.po('dataTable'));
    $('#admui-pageContent').on('click', '#DTSubmitBtn', function () {
        var data = table.$('input, select').serialize();
        toastr.info("将向服务器提交以下数据：<br>" + data.substr(0, 120) + '...');
    });
})(window, document, jQuery);

