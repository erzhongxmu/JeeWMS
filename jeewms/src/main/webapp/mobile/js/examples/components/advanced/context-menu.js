/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    // 示例 2
    // ------
    (function () {
        $('#exampleContext').contextmenu({
            target: '#exampleContextMenu',
            before: function (e) {
                // 如果右击 span ，则不显示右键菜单
                e.preventDefault();
                if (e.target.tagName === 'SPAN') {
                    e.preventDefault();
                    this.closemenu();
                    return false;
                }
                this.getMenu().find("li").eq(4).find('a').html("选中内容");
                return true;
            }
        });
    })();


    // 示例 3
    // ------
    (function () {
        var $pageContent = $('#admui-pageContent');

        $('#exampleContext2').contextmenu({
            target: '#exampleContextMenu',
            onItem: function (context, e) {
                toastr.info($.trim($(e.target).text()));
            }
        });

        $pageContent.on('show.bs.context', '#exampleContextMenu', function () {
            toastr.info('显示之前');
        });

        $pageContent.on('shown.bs.context', '#exampleContextMenu', function () {
            toastr.info('显示之后');
        });

        $pageContent.on('hide.bs.context', '#exampleContextMenu', function () {
            toastr.info('隐藏之前');
        });

        $pageContent.on('hidden.bs.context', '#exampleContextMenu', function () {
            toastr.info('隐藏之后');
        });
    })();


})(document, window, jQuery);