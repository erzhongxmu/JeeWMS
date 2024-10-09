/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("formValidation", {
        mode: "default",
        defaults: {
            locale:'zh_CN',
            framework: 'bootstrap',
            excluded: ':disabled',
            icon: {
                valid: 'icon wb-check',
                invalid: 'icon wb-close',
                validating: 'icon wb-refresh'
            }
        }
    });
})(window, document, jQuery);