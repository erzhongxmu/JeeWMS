/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = {
        run: function(){
            if ($('.list-group[data-plugin="nav-tabs"]').length) {
                $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                    $(e.target).addClass('active').siblings().removeClass('active');
                });
            }
        }
    };
})(document, window, jQuery);