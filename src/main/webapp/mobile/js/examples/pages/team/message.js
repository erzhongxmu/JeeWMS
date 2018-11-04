/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var $pageContent = $('#admui-pageContent');
    window.Content = App.extend({
        scrollChatsToBottom: function () {
            var $chatsWrap = $(".page-message-chats");
            var chatsWrapH = $chatsWrap.height();
            var chatsH = $(".chats", $chatsWrap).outerHeight();
            var historyBtnH = $("#historyBtn").outerHeight();

            $chatsWrap.scrollTop(chatsH + historyBtnH - chatsWrapH);
        },

        handleResize: function () {
            var self = this;

            $(window).on("resize", function () {
                self.scrollChatsToBottom();
            });
        },

        handleTalking: function () {
            var self = this;
            var $chatsWrap = $(".page-message-chats");
            var $textareaWrap = $('.page-message-input');

            autosize($('.message-input textarea'));

            $pageContent.on('autosize:resized', '.message-input textarea', function () {
                var height = $textareaWrap.outerHeight();
                $chatsWrap.css('height', 'calc(100% - ' + height + 'px)');
                self.scrollChatsToBottom();
            });


            $pageContent.on("click", ".message-input-btn>button", function () {
                var talkContents = $(".message-input>.form-control").val();
                var $newMsg = $(
                    "<div class='chat-content'>" +
                    "<p>" + talkContents + "</p>" +
                    "</div>"
                );

                if (talkContents.length > 0) {
                    $(".chat").last().find(".chat-body").append($newMsg);
                    $(".message-input>.form-control").attr("placeholder", "");
                    $(".message-input>.form-control").val("");
                } else {
                    $(".message-input>.form-control").attr("placeholder", "type text here...");
                }

                $(".message-input>.form-control").focus();

                self.scrollChatsToBottom();
            });
        },
        run: function (next) {
            this.scrollChatsToBottom();
            this.handleResize();
            this.handleTalking();

            next();
        }
    });

    
}(document, window, jQuery));
