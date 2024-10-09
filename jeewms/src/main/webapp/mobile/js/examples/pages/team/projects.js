/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = App.extend({
        handleSelective: function () {
            var members = [{
                    id: 'uid_1',
                    name: '孙继红',
                    img: '/images/portraits/1.jpg'
                }, {
                    id: 'uid_2',
                    name: '张倩',
                    img: '/images/portraits/2.jpg'
                }, {
                    id: 'uid_3',
                    name: '孙咏梅',
                    img: '/images/portraits/3.jpg'
                }, {
                    id: 'uid_4',
                    name: '冉佩利',
                    img: '/images/portraits/4.jpg'
                }],
                selected = [{
                    id: 'uid_1',
                    name: '柳映秋',
                    img: '/images/portraits/1.jpg'
                }, {
                    id: 'uid_2',
                    name: '周伊娅',
                    img: '/images/portraits/2.jpg'
                }];

            $('[data-plugin="jquery-selective"]').selective({
                namespace: 'addMember',
                local: members,
                selected: selected,
                buildFromHtml: false,
                tpl: {
                    optionValue: function (data) {
                        return data.id;
                    },
                    frame: function () {
                        return '<div class="' + this.namespace + '">' +
                            this.options.tpl.items.call(this) +
                            '<div class="' + this.namespace + '-trigger">' +
                            this.options.tpl.triggerButton.call(this) +
                            '<div class="' + this.namespace + '-trigger-dropdown">' +
                            this.options.tpl.list.call(this) +
                            '</div>' +
                            '</div>' +
                            '</div>';
                    },
                    triggerButton: function () {
                        return '<div class="' + this.namespace + '-trigger-button"><i class="wb-plus"></i></div>';
                    },
                    listItem: function (data) {
                        return '<li class="' + this.namespace + '-list-item"><img class="avatar" src="' + data.img + '">' + data.name + '</li>';
                    },
                    item: function (data) {
                        return '<li class="' + this.namespace + '-item"><img class="avatar" src="' + data.img + '">' +
                            this.options.tpl.itemRemove.call(this) +
                            '</li>';
                    },
                    itemRemove: function () {
                        return '<span class="' + this.namespace + '-remove"><i class="wb-minus-circle"></i></span>';
                    },
                    option: function (data) {
                        return '<option value="' + this.options.tpl.optionValue.call(this, data) + '">' + data.name + '</option>';
                    }
                }
            });
        },

        handleProject: function () {
            $('#admui-pageContent').on('click', '[data-tag=project-delete]', function () {
                bootbox.setDefaults($.po('bootbox'));
                bootbox.dialog({
                    message: "您确定要删除这个项目吗？",
                    buttons: {
                        success: {
                            label: "删除",
                            className: "btn-danger",
                            callback: function () {
                                // $(e.target).closest('.list-group-item').remove();
                            }
                        }
                    }
                });
            });
        },

        run: function (next) {
            this.handleSelective();
            this.handleProject();

            next();
        }
    });
    
})(document, window, jQuery);
