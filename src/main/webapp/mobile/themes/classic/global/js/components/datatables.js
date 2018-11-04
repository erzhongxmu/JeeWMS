/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("dataTable", {
        defaults: {
            responsive: true,
            dom: "<'row'<'col-xs-6'<'hidden-xs'l>><'col-xs-6'f>>" + "<'row'<'col-xs-12'tr>>" + "<'row'<'col-sm-5'i><'col-sm-7'p>>",
            language: {
                "sSearchPlaceholder": "快速查找",
                "lengthMenu": "每页显示 _MENU_ 条",
                "search": "_INPUT_",
                "info": "第 _START_ 至 _END_ 项，共 _TOTAL_ 项",
                "infoEmpty": "共 0 项",
                "emptyTable": "无数据",
                "zeroRecords": "抱歉，没有找到符合条件的记录",
                "sInfoFiltered": "(从 _MAX_ 条记录中查找)",
                "loadingRecords": "加载中，请稍后…",
                "processing": "正在处理，请稍后…",
                "paginate": {
                    "first": "第一页",
                    "last": "最后一页",
                    "previous": '<i class="icon wb-chevron-left-mini"></i>',
                    "next": '<i class="icon wb-chevron-right-mini"></i>'
                },
                "aria": {
                    "sortAscending": "升序排列",
                    "sortDescending": "降序排列"
                }
            }
        },
        init: function (context) {
            if (!$.fn.dataTable) {
                return;
            }

            var defaults = this.defaults;

            $('[data-plugin="dataTable"]', context).each(function () {
                var options = $.extend(true, {}, defaults, $(this).data());


                $(this).dataTable(options);
            });
        }
    });
})(window, document, jQuery);