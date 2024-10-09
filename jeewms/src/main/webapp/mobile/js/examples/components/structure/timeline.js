/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    $('.timeline-item').appear();

    $('.timeline-item').not(':appeared').each(function () {
        var $item = $(this);
        $item.addClass('timeline-invisible');
        $item.find('.timeline-dot').addClass('invisible');
        $item.find('.timeline-info').addClass('invisible');
        $item.find('.timeline-content').addClass('invisible');
    });

    $('#admui-pageContent').on('appear', '.timeline-item.timeline-invisible', function () {
        var $item = $(this);
        $item.removeClass('timeline-invisible');

        $item.find('.timeline-dot').removeClass('invisible').addClass('animation-scale-up');

        if ($item.hasClass('timeline-reverse') || $item.css('float') === 'none') {
            $item.find('.timeline-info').removeClass('invisible').addClass('animation-slide-right');
            $item.find('.timeline-content').removeClass('invisible').addClass('animation-slide-right');
        } else {
            $item.find('.timeline-info').removeClass('invisible').addClass('animation-slide-left');
            $item.find('.timeline-content').removeClass('invisible').addClass('animation-slide-left');
        }
    });
})(document, window, jQuery);