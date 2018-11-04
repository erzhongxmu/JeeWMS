/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("selectpicker", {
        mode: "default",
        defaults: {
            noneSelectedText: '没有选中任何项',
            noneResultsText: '没有找到匹配项',
            countSelectedText: '已选中{1}项中的{0}项',
            maxOptionsText: ['超出限制 (最多选择{n}项)', '组选择超出限制(最多选择{n}组)'],
            selectAllText: '选择全部',
            deselectAllText: '取消全部选择',
            doneButtonText: '关闭',
            style: "btn-select",
            iconBase: "icon",
            tickIcon: "wb-check"
        }
    });
})(window, document, jQuery);