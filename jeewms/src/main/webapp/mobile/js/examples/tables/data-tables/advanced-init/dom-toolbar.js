/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    $('#dataTableExample').dataTable($.po('dataTable', {
        "dom": '<"dt-dom-toolbar">frtip'
    }));

    $("div.dt-dom-toolbar").html('<b class="text-danger">自定义文字、图片等等</b>');
})(window, document, jQuery);

