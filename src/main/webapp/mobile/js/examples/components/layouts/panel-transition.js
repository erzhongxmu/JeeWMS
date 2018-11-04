/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var $example = $('#exampleTransition'),
        $pageContent = $('#admui-pageContent');

    $pageContent.on('click.panel.transition', '[data-type]', function () {
        var type = $(this).data('type');

        $example.data('animateList').run(type);
    });

    $pageContent.on('close.uikit.panel', '[class*=blocks-] > li > .panel', function () {
        $(this).parent().hide();
    });

})(document, window, jQuery);