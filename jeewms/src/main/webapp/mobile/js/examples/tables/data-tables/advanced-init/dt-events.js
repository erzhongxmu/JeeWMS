/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    var eventFired = function (type) {
        var n = $('#dataTableEventDemoInfo')[0];
        n.innerHTML += '<div>' + type + ' 事件 - ' + new Date().getTime() + '</div>';
        n.scrollTop = n.scrollHeight;
    };

    $('#dataTableExample')
        .on('order.dt', function () {
            eventFired('排序');
        })
        .on('search.dt', function () {
            eventFired('查找');
        })
        .on('page.dt', function () {
            eventFired('分页');
        }).DataTable($.po('dataTable'));
})(window, document, jQuery);

