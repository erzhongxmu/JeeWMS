/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    // Webui Popover - 表格
    // ------------------------------------
    (function () {
        var tableContent = $('#examplePopoverTable').html();

        $('#examplePopWithTable').webuiPopover($.po("webuiPopover", {
            title: 'WebUI Popover',
            content: tableContent,
            width: 500
        }));
    })();

    // Webui Popover - 列表组
    // -----------------------------------
    (function () {
        var listContent = $('#examplePopoverList').html();

        $('#examplePopWithList').webuiPopover($.po("webuiPopover", {
            content: listContent,
            title: '',
            padding: false
        }));

    })();

    // Webui Popover - 内容较多
    // --------------------------------------------
    (function () {
        var largeContent = $('#examplePopoverLargeContent').html();

        $('#examplePopWithLargeContent').webuiPopover($.po("webuiPopover", {
            title: 'WebUI Popover',
            content: largeContent,
            width: 400,
            height: 350,
            closeable: true
        }));
    })();

})(document, window, jQuery);