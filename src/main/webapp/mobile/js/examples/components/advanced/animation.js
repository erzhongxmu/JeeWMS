/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    $('#admui-pageContent').on('click', '.select-loader', function () {
        var type = $(this).data('type'),
            curr = $('.example-loading .loader').data('type');

        if (type === curr) {
            return;
        }
        $('.example-loading .loader').removeClass('loader-' + curr).addClass('loader-' + type).data('type', type);

    });

    // NProgress
    // -----------------
    $('#admui-pageContent').on('click', '.btn', function (e) {
        var $target = $(e.target);
        var id = $target.attr('id');

        NProgress.configure($.po('nprogress'));

        switch (id) {
            case 'exampleNProgressStart':
                NProgress.start();
                break;
            case 'exampleNProgressSet':
                NProgress.set(0.50);
                break;
            case 'exampleNProgressInc':
                NProgress.inc();
                break;
            case 'exampleNProgressDone':
                NProgress.done(true);
                break;

            case 'exampleNProgressDefault':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;
            case 'exampleNProgressHeader':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar nprogress-bar-header" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;
            case 'exampleNProgressBottom':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar nprogress-bar-bottom" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;

            case 'exampleNProgressPrimary':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar nprogress-bar-primary" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;
            case 'exampleNProgressSuccess':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar nprogress-bar-success" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;
            case 'exampleNProgressInfo':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar nprogress-bar-info" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;
            case 'exampleNProgressWarning':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar nprogress-bar-warning" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;
            case 'exampleNProgressDanger':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar nprogress-bar-danger" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;
            case 'exampleNProgressDark':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar nprogress-bar-dark" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;
            case 'exampleNProgressLight':
                NProgress.done(true);
                NProgress.configure($.po('nprogress', {
                    template: '<div class="bar nprogress-bar-light" role="bar"></div><div class="spinner" role="spinner"><div class="spinner-icon"></div></div>'
                }));
                NProgress.start();
                break;
        }
    });

})(document, window, jQuery);