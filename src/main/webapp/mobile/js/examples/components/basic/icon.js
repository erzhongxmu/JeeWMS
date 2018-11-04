/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var $pageContent =$('#admui-pageContent');
    window.Content = {
        run: function () {
            $pageContent.on('slide', '#iconChange', function (slideEvt) {
                var value = slideEvt.value;
                $('.panel .icon').css('font-size', value);
            });

            $pageContent.on('keyup', '.input-search input[type=text]', function () {
                var val = $(this).val();
                if (val !== '') {
                    $('[data-name]').addClass('is-hide');
                    $('[data-name*=' + val + ']').removeClass('is-hide');
                } else {
                    $('[data-name]').removeClass('is-hide');
                }

                $('.icon-group').each(function () {
                    var $group = $(this);
                    if ($group.find('[data-name]:not(.is-hide)').length === 0) {
                        $group.hide();
                    } else {
                        $group.show();
                    }
                });

            });
        }
    };

})(document, window, jQuery);