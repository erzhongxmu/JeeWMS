/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, $) {
    'use strict';

    var $content = $("#admui-pageContent");

    /*公用模块对象*/
    var app = {
        handleSlidePanel: function () {
            if (typeof $.slidePanel === 'undefined') {
                return;
            }

            $content.on('click', '[data-toggle=slidePanel]', function (e) {
                $.slidePanel.show({
                    url: $(this).data('url'),
                    settings: {
                        cache: false
                    }
                }, $.po('slidePanel', {
                    template: function (options) {
                        return '<div class="' + options.classes.base + ' ' + options.classes.base + '-' + options.direction + '">' +
                            '<div class="' + options.classes.base + '-scrollable"><div>' +
                            '<div class="' + options.classes.content + '"></div>' +
                            '</div></div>' +
                            '<div class="' + options.classes.base + '-handler"></div>' +
                            '</div>';
                    },
                    afterLoad: function () {
                        this.$panel.find('.' + this.options.classes.base + '-scrollable')
                            .slimScroll($.po('slimScroll'));
                    }
                }));

                e.stopPropagation();
            });
        },
        handleMultiSelect: function () {
            var $all = $('.select-all');

            $content.on('change', '.multi-select', function (e, isSelectAll) {
                if (isSelectAll) {
                    return;
                }

                var $select = $('.multi-select'),
                    total = $select.length,
                    checked = $select.find('input:checked').length;
                if (total === checked) {
                    $all.find('input').prop('checked', true);
                } else {
                    $all.find('input').prop('checked', false);
                }
            });

            $all.on('change', function () {
                var checked = $(this).find('input').prop('checked');

                $('.multi-select input').each(function () {
                    $(this).prop('checked', checked).trigger('change', [true]);
                });

            });
        },
        handleListActions: function () { // 操作主体部分，左侧菜单编辑
            $content.on('keydown', '.list-editable [data-bind]', function (event) {
                var keycode = (event.keyCode ? event.keyCode : event.which);

                if (keycode === 13 || keycode === 27) {
                    var $input = $(this),
                        bind = $input.data('bind'),
                        $list = $input.parents('.list-group-item'),
                        $content = $list.find('.list-content'),
                        $editable = $list.find('.list-editable'),
                        $update = bind ? $list.find(bind) : $list.find('.list-text');

                    if (keycode === 13) {
                        $update.html($input.val());
                    } else {
                        $input.val($update.text());
                    }

                    $content.show();
                    $editable.hide();
                }
            });

            $content.on('click', '[data-toggle="list-editable-close"]', function () {
                var $btn = $(this),
                    $list = $btn.parents('.list-group-item'),
                    $content = $list.find('.list-content'),
                    $editable = $list.find('.list-editable');

                $content.show();
                $editable.hide();
            });
        },
        pageAside: function () {
            var pageAside = $(".page-aside"),
                isOpen = pageAside.hasClass('open');

            pageAside.toggleClass('open', !isOpen);
        },
        run: function () {
            var self = this;

            // 小屏下侧边栏滚动
            $content.on('click', '.page-aside-switch', function (e) {
                self.pageAside();
                e.stopPropagation();
            });

        }
    };

    window.App = $.extend({}, $.objExtend);
    App.extend(app);
})(window, jQuery);
