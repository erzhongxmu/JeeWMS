/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("magnificPopup", {
        mode: "default",
        defaults: {
            type: "image",
            closeOnContentClick: true,
            /*image: {
                verticalFit: true
            },*/
            tClose: '关闭 (ESC)',
            tLoading: '加载中...',
            gallery: {
                tPrev: '上一张',
                tNext: '下一张',
                tCounter: '%curr% / %total%'
            },
            image: {
                tError: '<a href="%url%" target="_blank">图片</a> 加载失败'
            },
            ajax: {
                tError: '<a href="%url%" target="_blank">内容</a> 加载失败'
            }
        }
    });
})(window, document, jQuery);