/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.edit = function () {
        $('.click2edit').summernote($.po('summernote', {
            lang: 'zh-CN'
        }));
    };
    window.save = function () {
        $('.click2edit').destroy();
    };
})(document, window, jQuery);
