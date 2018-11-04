/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    window.Content.extend({
        loadMsg: function (page) { // 加载当前页通知
            var html;

            window.notifyFn.render();

            $.ajax({
                url: $.ctx + '/message/query?page=' + page,
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        html = template('newMessge', data);
                        $('#messageLists').html(html);
                    } else {
                        toastr.error('出错了，请重试！');
                    }
                },
                error: function () {
                    toastr.error('服务器异常，请稍后再试！');
                }
            });
        },
        fnExtend: function () { // 扩展notifyFn对象，便于socket消息通知中回调操作
            var self = this;

            $.extend(window.notifyFn, {
                messagePage: function () { // 在消息页面时的操作
                    var $adminMsg = $('#messageLists');

                    if (!$('#accountMsg').hasClass('active')) {
                        return;
                    }

                    if ($adminMsg.children('.no-message').length !== 0 || $('#paging').data('page') === 1) {
                        self.loadMsg('');
                    }
                },
                msgStatus: function (Id) { // 改变消息状态
                    var $element = $('[data-message-id="' + Id + '"]').children('.media'),
                        $readTab = $element.children(".media-right").find("a"),
                        $title = $element.children(".media-body").find("i");

                    $title.remove();
                    $readTab.attr("title", "删除");
                    $readTab.removeClass("wb-check").addClass("wb-close");
                }
            });
        },
        run: function (next) {
            var self = this, fn = window.notifyFn.unReadMsg,
                $pageContent = $('#admui-pageContent');

            this.loadMsg('');
            this.fnExtend();

            if (fn && $.isFunction(fn)) {
                fn($('#admui-navbarMessage').find('span.msg-num').text());
            }

            $.leavePage = function () {
                window.notifyFn.messagePage = null;
                window.notifyFn.msgStatus = null;
            };

            // 查看当前消息
            $pageContent.on("click", ".news-list", function (e) {
                e.preventDefault();

                var $that = $(this),
                    opts = $that.parents(".list-group-item").data();

                if ($that.find("i.icon").size() > 0) {
                    opts.readFlag = false;
                } else {
                    opts.readFlag = true;
                }
                window.notifyFn.readMsg(opts);
            });

            // 直接更改状态为已读
            $pageContent.on("click", ".wb-check", function (e) {
                e.preventDefault();

                window.notifyFn.readMsg($(this).parents('[data-message-id]').data());
            });

            // 删除已读消息
            $pageContent.on("click", ".wb-close", function (e) {
                e.preventDefault();
                var $item = $(this).closest(".list-group-item"),
                    $total = $('.msg-number'),
                    total = Number($total.text()),
                    page = $('#paging').data('page'),
                    ID = $item.data('messageId');

                alertify.theme('bootstrap')
                    .confirm("你确定要删除吗？", function () {

                        $.ajax({
                            url: $.ctx + '/message/delete',
                            type: 'POST',
                            data: {messageId: ID},
                            dataType: 'JSON',
                            success: function (data) {
                                if (data.success) {
                                    self.loadMsg(page);

                                    total--;
                                    $total.text(total);

                                    toastr.clear();
                                    toastr.success('删除成功！');
                                } else {
                                    toastr.error('出错了，请重试！');
                                }
                            },
                            error: function () {
                                toastr.error('服务器异常，请稍后再试！');
                            }
                        });
                    });
            });

            // 分页
            $pageContent.on('click', '.previous, .next', function () {
                var $item = $(this),
                    page = $item.parents('#paging').data('page'),
                    maxPage = $item.parents('#paging').data('max-page'),
                    $prev = $('.previous'),
                    $next = $('.next');

                if ($item.is('.previous')) {
                    if ($next.is(':hidden')) {
                        $next.show();
                    }
                    if (page === 2) {
                        $prev.hide();
                    }
                    page--;
                }
                else if ($item.is('.next')) {
                    if ($prev.is(':hidden')) {
                        $prev.show();
                    }
                    if (page === maxPage - 1) {
                        $next.hide();
                    }
                    page++;
                }
                self.loadMsg(page);
            });

            next();
        }
    });
})(window, document, jQuery);
