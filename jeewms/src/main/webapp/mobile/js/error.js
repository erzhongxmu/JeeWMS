/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function () {
    'use strict';

    var btn;
    if (typeof $ === 'undefined') {
        btn = document.getElementById('closeTab');

        btn.innerHTML = '退回上一页';

        btn.onclick = function () {
            history.go(-1);
        };
    } else {
        window.Content = {
            run: function () {
                if (typeof $.site.contentTabs !== 'undefined') {
                    $('#admui-pageContent').on('click', '#closeTab', function () {
                        $.site.contentTabs.closeTab();
                    });
                }
            }
        };
    }
})();
