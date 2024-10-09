/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var $pageContent = $('#admui-pageContent');
    // 进度条动画
    // --------------------------
    $pageContent.on('click', '#exampleButtonStart', function () {
        $('[data-plugin="progress"]').asProgress('start');
    });
    $pageContent.on('click', '#exampleButtonFinish', function () {
        $('[data-plugin="progress"]').asProgress('finish');
    });
    $pageContent.on('click', '#exampleButtonGoto', function () {
        $('[data-plugin="progress"]').asProgress('go', 50);
    });
    $pageContent.on('click', '#exampleButtonGotoPercentage', function () {
        $('[data-plugin="progress"]').asProgress('go', '50%');
    });
    $pageContent.on('click', '#exampleButtonStop', function () {
        $('[data-plugin="progress"]').asProgress('stop');
    });
    $pageContent.on('click', '#exampleButtonReset', function () {
        $('[data-plugin="progress"]').asProgress('reset');
    });
    $pageContent.on('click', '#exampleButtonRandom', function (e) {
        e.preventDefault();

        $('[data-plugin="progress"]').each(function () {
            var number = Math.round(Math.random(1) * 100) + '%';
            $(this).asProgress('go', number);
        });
    });

})(document, window, jQuery);