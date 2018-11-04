/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function () {
    'use strict';

    window.Content = App.extend({
        run: function (next) {
            var $pageContent = $('#admui-pageContent'),
                $actionBtn = $('.site-action').actionBtn({
                    toggleSelector: '.list-group-item',
                    listSelector: '.site-action-buttons'
                }).data("actionBtn"),
                $noteList = $(".list-group-item");

            $actionBtn.show();

            $pageContent.on("click", '.site-action-toggle', function (e) {
                if (!$noteList.hasClass("active")) {
                    $('#addNewNote').modal('show');

                    e.stopPropagation();
                } else {
                    $(".list-group-item").removeClass("active");
                    $actionBtn.hide();
                }

            });

            $pageContent.on("click", ".list-group-item", function () {
                $(this).addClass("active").siblings().removeClass("active");

                if ($(this).hasClass("active")) {
                    $actionBtn.show();
                }
            });

            next();
        }
    });

}());
