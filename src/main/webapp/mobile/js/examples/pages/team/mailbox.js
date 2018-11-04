/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var $pageContent = $('#admui-pageContent');

    window.Content = App.extend({
        handleAction: function () {
            var actionBtn = $('.site-action').actionBtn().data('actionBtn'),
                $selectable = $('[data-selectable]');

            $pageContent.on('click', '.site-action-toggle', '.site-action',function (e) {
                var $selected = $selectable.asSelectable('getSelected');

                if ($selected.length === 0) {
                    $('#addMailForm').modal('show');
                    e.stopPropagation();
                }
            });

            $pageContent.on('click', '[data-action="trash"]', '.site-action', function () {
                toastr.info('删除所选邮件');
            });

            $pageContent.on('click', '[data-action="inbox"]', '.site-action', function () {
                toastr.info('移动所选邮件到指定文件夹');
            });

            $pageContent.on('asSelectable::change', '[data-selectable]', function (e, api, checked) {
                if (checked) {
                    actionBtn.show();
                } else {
                    actionBtn.hide();
                }
            });
        },

        handleListItem: function () {
            $pageContent.on('click', '#addLabelToggle', function (e) {
                $('#addLabelForm').modal('show');
                e.stopPropagation();
            });

            $pageContent.on('click', '[data-tag=list-delete]', function (e) {
                bootbox.setDefaults($.po('bootbox'));
                bootbox.dialog({
                    message: "您确定要删除这个标签吗？",
                    buttons: {
                        success: {
                            label: "确定",
                            className: "btn-danger",
                            callback: function () {
                                $(e.target).closest('.list-group-item').remove();
                            }
                        }
                    }
                });
            });
        },

        itemTpl: function (data) {
            return '<tr id="' + data.id + '" data-mailbox="slidePanel" ' + (data.unread === 'true' ? 'class="unread"' : '') + '>' +
                '<td class="cell-60">' +
                '<span class="checkbox-custom checkbox-primary checkbox-lg">' +
                '<input type="checkbox" class="mailbox-checkbox selectable-item" id="mail_' + data.id + '"/>' +
                '<label for="mail_' + data.id + '"></label>' +
                '</span>' +
                '</td>' +
                '<td class="cell-30 responsive-hide">' +
                '<span class="checkbox-important checkbox-default">' +
                '<input type="checkbox" class="mailbox-checkbox mailbox-important" ' + (data.starred === 'true' ? 'checked="checked"' : '') + ' id="mail_' + data.id + '_important"/>' +
                '<label for="mail_' + data.id + '_important"></label>' +
                '</span>' +
                '</td>' +
                '<td class="cell-60 responsive-hide">' +
                '<a class="avatar" href="javascript:;"><img class="img-responsive" src="' + data.avatar + '" alt="..."></a>' +
                '</td>' +
                '<td>' +
                '<div class="content">' +
                '<div class="title">' + data.name + '</div>' +
                '<div class="abstract">' + data.title + '</div>' +
                '</div>' +
                '</td>' +
                '<td class="cell-30 responsive-hide">' +
                (data.attachments.length > 0 ? '<i class="icon wb-paperclip" aria-hidden="true"></i>' : '') +
                '</td>' +
                '<td class="cell-130">' +
                '<div class="time">' + data.time + '</div>' +
                (data.group.length > 0 ? '<div class="identity"><i class="wb-medium-point ' + data.color + '" aria-hidden="true"></i>' + data.group + '</div>' : '') +
                '</td>' +
                '</tr>';
        },

        attachmentsTpl: function (data) {
            var self = this,
                html = '';

            html += '<div class="mail-attachments">' +
                '<p><i Class="icon wb-paperclip"></i>Attachments | <a href="javascript:;">Download All</a></p>' +
                '<ul class="list-group">';

            $.each(data, function (n, item) {
                html += self.attachmentTpl(item);
            });

            html += '</ul></div>';

            return html;
        },

        attachmentTpl: function (data) {
            return '<li class="list-group-item">' +
                '<span class="name">' + data.name + '</span><span class="size">' + data.size + '</span>' +
                '<button type="button" class="btn btn-icon btn-pure btn-default"><i class="icon wb-download" aria-hidden="true"></i></button>' +
                '</li>';
        },

        messagesTpl: function (data) {
            var html = '';

            $.each(data.messages, function (n, item) {
                html += '<section class="slidePanel-inner-section">' +
                    '<div class="mail-header">' +
                    '<div class="mail-header-main">' +
                    '<a class="avatar" href="javascript:;"><img src="' + data.avatar + '" alt="..."></a>' +
                    '<div><span class="name">' + data.name + '</span></div>' +
                    '<div>' +
                    '<a href="javascript:;" class="mailbox-panel-email">' + data.email + '</a>' +
                    ' 发送给 <a href="javascript:;" class="margin-right-10">我</a>' +
                    '<span class="identity"><i class="wb-medium-point red-600" aria-hidden="true"></i>' + data.group + '</span>' +
                    '</div>' +
                    '</div>' +
                    '<div class="mail-header-right">' +
                    '<span class="time">' + item.time + '</span>' +
                    '<div class="btn-group actions" role="group">' +
                    '<button type="button" class="btn btn-icon btn-pure btn-default"><i class="icon wb-star" aria-hidden="true"></i></button>' +
                    '<button type="button" class="btn btn-icon btn-pure btn-default"><i class="icon wb-reply" aria-hidden="true"></i></button>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '<div class="mail-content">' + item.content + '</div>';

                if (n === 0) {
                    if (item.attachments && item.attachments.length > 0) {
                        html += this.attachmentsTpl(item.attachments);
                    }
                }

                html += '</section>';
            });

            return html;
        },

        initMail: function () {
            var self = this;

            $.getJSON($.ctx + '/data/examples/pages/mailbox.json', function (data) {
                var $wrap = $('#mailboxTable');

                self.buildMail($wrap, data);
                self.initMailData(data);
                self.handlSlidePanelPlugin();
            });
        },

        initMailData: function (data) {
            this.mailboxData = data;
        },

        buildMail: function ($wrap, data) {
            var self = this,
                $tbody = $('<tbody></tbody>');

            $.each(data, function (i, item) {
                self.buildItem($tbody, item);
            });

            $wrap.empty().append($tbody);
        },

        buildItem: function ($wrap, data) {
            $wrap.append($(this.itemTpl(data)).data('mailInfo', data));
        },

        handlePanel: function () {
            $pageContent.on('click', '[data-mailbox="slidePanel"]', function () {
                console.log(this, $(this));
            });
        },

        handlSlidePanelPlugin: function () {
            var self = this;

            if (typeof $.slidePanel === 'undefined') {
                return;
            }

            $pageContent.on('click', '[data-mailbox="slidePanel"]', function (e) {
                $.slidePanel.show({
                    url: $.ctx + '/data/examples/pages/mailbox-panel.tpl',
                    target: $(this)
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
                    },
                    contentFilter: function (data, object) {
                        var $target = $(object.target),
                            info = $target.data('mailInfo'),
                            $panel = $(data);

                        $('.mailbox-panel-title', $panel).html(info.title);

                        $('.slidePanel-messages', $panel).html(self.messagesTpl(info));

                        return $panel;
                    }
                }));

                e.stopPropagation();
            });
        },

        run: function (next) {
            this.handleAction();
            this.handleListItem();

            this.initMail();

            $('#addlabelForm').modal({
                show: false
            });

            $('#addMailForm').modal({
                show: false
            });

            $pageContent.on('click', '.checkbox-important', function (e) {
                e.stopPropagation();
            });

            this.handleMultiSelect();

            $('.markdown-edit').markdown({
                language: 'zh',
                iconlibrary: 'fa'
            });

            $.leavePage = function(){
                $.slidePanel = null;
            };

            next();
        }
    });
    
})(document, window, jQuery);
