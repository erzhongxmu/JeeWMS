/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    $.components.register("panel", {
        api: function () {
            var $doc = $(document);
            $doc.off('click.site.panel');

            $doc.on('click.site.panel', '[data-toggle="panel-fullscreen"]', function (e) {
                e.preventDefault();
                var $this = $(this),
                    $panel = $this.closest('.panel');

                var api = $panel.data('panel-api');
                api.toggleFullscreen();
            });

            $doc.on('click.site.panel', '[data-toggle="panel-collapse"]', function (e) {
                e.preventDefault();
                var $this = $(this),
                    $panel = $this.closest('.panel');

                var api = $panel.data('panel-api');
                api.toggleContent();
            });

            $doc.on('click.site.panel', '[data-toggle="panel-close"]', function (e) {
                e.preventDefault();
                var $this = $(this),
                    $panel = $this.closest('.panel');

                var api = $panel.data('panel-api');
                api.close();
            });

            $doc.on('click.site.panel', '[data-toggle="panel-refresh"]', function (e) {
                e.preventDefault();
                var $this = $(this);
                var $panel = $this.closest('.panel');

                var api = $panel.data('panel-api');
                var callback = $this.data('loadCallback');

                if ($.isFunction(window[callback])) {
                    api.load(window[callback]);
                } else {
                    api.load();
                }
            });
        },

        init: function (context) {
            $('.panel', context).each(function () {
                var $this = $(this);

                var isFullscreen = false;
                var isClose = false;
                var isCollapse = false;
                var isLoading = false;

                var $fullscreen = $this.find('[data-toggle="panel-fullscreen"]');
                var $collapse = $this.find('[data-toggle="panel-collapse"]');
                var $loading;
                var self = this;

                if ($this.hasClass('is-collapse')) {
                    isCollapse = true;
                }

                var api = {
                    load: function (callback) {
                        var type = $this.data('loader-type');
                        if (!type) {
                            type = 'default';
                        }

                        $loading = $('<div class="panel-loading">' +
                            '<div class="loader loader-' + type + '"></div>' +
                            '</div>');

                        $loading.appendTo($this);

                        $this.addClass('is-loading');
                        $this.trigger('loading.uikit.panel');
                        isLoading = true;

                        if ($.isFunction(callback)) {
                            callback.call(self, this.done);
                        }
                    },
                    done: function () {
                        if (isLoading === true) {
                            $loading.remove();
                            $this.removeClass('is-loading');
                            $this.trigger('loading.done.uikit.panel');
                        }
                    },
                    toggleContent: function () {
                        if (isCollapse) {
                            this.showContent();
                        } else {
                            this.hideContent();
                        }
                    },

                    showContent: function () {
                        if (isCollapse !== false) {
                            $this.removeClass('is-collapse');

                            if ($collapse.hasClass('wb-plus')) {
                                $collapse.removeClass('wb-plus').addClass('wb-minus');
                            }

                            $this.trigger('shown.uikit.panel');

                            isCollapse = false;
                        }
                    },

                    hideContent: function () {
                        if (isCollapse !== true) {
                            $this.addClass('is-collapse');

                            if ($collapse.hasClass('wb-minus')) {
                                $collapse.removeClass('wb-minus').addClass('wb-plus');
                            }

                            $this.trigger('hidden.uikit.panel');
                            isCollapse = true;
                        }
                    },

                    toggleFullscreen: function () {
                        if (isFullscreen) {
                            this.leaveFullscreen();
                        } else {
                            this.enterFullscreen();
                        }
                    },
                    enterFullscreen: function () {
                        if (isFullscreen !== true) {
                            $this.addClass('is-fullscreen');

                            if ($fullscreen.hasClass('wb-expand')) {
                                $fullscreen.removeClass('wb-expand').addClass('wb-contract');
                            }

                            $this.trigger('enter.fullscreen.uikit.panel');
                            isFullscreen = true;
                        }
                    },
                    leaveFullscreen: function () {
                        if (isFullscreen !== false) {
                            $this.removeClass('is-fullscreen');

                            if ($fullscreen.hasClass('wb-contract')) {
                                $fullscreen.removeClass('wb-contract').addClass('wb-expand');
                            }

                            $this.trigger('leave.fullscreen.uikit.panel');
                            isFullscreen = false;
                        }
                    },
                    toggle: function () {
                        if (isClose) {
                            this.open();
                        } else {
                            this.close();
                        }
                    },
                    open: function () {
                        if (isClose !== false) {
                            $this.removeClass('is-close');
                            $this.trigger('open.uikit.panel');

                            isClose = false;
                        }
                    },
                    close: function () {
                        if (isClose !== true) {

                            $this.addClass('is-close');
                            $this.trigger('close.uikit.panel');

                            isClose = true;
                        }
                    }
                };

                $this.data('panel-api', api);
            });
        }
    });
})(window, document, jQuery);