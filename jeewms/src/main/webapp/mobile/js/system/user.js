/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var $content = $("#admui-pageContent"),
        $userAccont = $("#userAccountInfo");

    window.Content = App.extend({
        handleAction: function () {
            var self = this,
                actionBtn = $('.site-action').actionBtn().data('actionBtn'),
                $selectable = $('.dataTable');

            $selectable.asSelectable($.po('selectable', $(this).data()));

            $content.on('click', '.site-action-toggle', '.site-action', function (e) { // 新增用户
                var $selected = $selectable.asSelectable('getSelected'),
                    $userInfo = $('#userInfoForm');
                self.thisRow = null;

                if ($selected.length === 0) {
                    e.stopPropagation();
                    self.getAuth();

                    $userInfo.find("input[name='loginName']").removeAttr("readonly").val("");
                    $userInfo.find("input[name='userId']").val("");
                }
            });

            $content.on('click', '[data-action="delete"]', '.site-action', function () { // 删除所选用户
                var $tr = $('#user_tables').find('tbody > tr'),
                    userIds = [];

                alertify.theme('bootstrap')
                    .confirm("您确定要删除所选用户吗？", function () {
                        $tr.each(function () {
                            if ($(this).find(':checkbox').is(':checked')) {
                                userIds.push(self.table.row($(this)).data().userId);
                            }
                        });

                        $.ajax({
                            url: $.ctx + '/user/delete',
                            type: 'POST',
                            data: {userId: userIds},
                            traditional: true,
                            dataType: 'JSON',
                            success: function (data) {
                                if (data.success) {
                                    $tr.each(function () {
                                        if ($(this).find(':checkbox').is(':checked')) {
                                            self.table.row($(this)).remove().draw(false);
                                        }
                                    });
                                    toastr.success('删除成功！');
                                } else {
                                    toastr.error('出错了，请重试！');
                                }
                            },
                            error: function () {
                                toastr.error('服务器异常，请稍后再试！');
                            }
                        });
                        actionBtn.hide();
                    });
            });

            /*隐藏用户信息编辑弹出层*/
            $content.on('hide.bs.modal', '#userInfoForm', function () {
                var $userAccount = $('#userAccountInfo');
                $userAccount.formValidation('resetForm', true);
                $userAccount.find('input[name="state"]').prop('checked', false).val('NORMAL');
            });

            /* 选中用户 --禁止所选用户 */
            $content.on('click', '[data-action="move"]', '.site-action', function (e) {
                var $tr = $('#user_tables').find('tbody > tr'),
                    userIds = [];

                alertify.theme('bootstrap')
                    .confirm('你确定要禁用所选用户吗？', function () {
                        $tr.each(function () {
                            if ($(this).find(':checkbox').is(':checked')) {
                                userIds.push(self.table.row($(this)).data().userId);
                            }

                        });
                        $.ajax({
                            url: $.ctx + '/user/forbid',
                            type: 'POST',
                            data: {userId: userIds},
                            traditional: true,
                            dataType: 'JSON',
                            success: function (data) {
                                if (data.success) {
                                    $tr.each(function () {
                                        if ($(this).find(':checkbox').is(':checked')) {
                                            $(this).addClass('disabled');
                                        }
                                    });
                                }
                                else {
                                    toastr.error('出错了，请重试！');
                                }
                            },
                            error: function () {
                                toastr.error('服务器异常，请稍后再试！');
                            }
                        });
                    }, function () {
                        actionBtn.hide();
                    });
                e.stopPropagation();
            });

            $content.on('asSelectable::change', '.dataTable', function (e, api, checked) {
                if (checked) {
                    actionBtn.show();
                } else {
                    actionBtn.hide();
                }
            });

            $userAccont.find("input:checkbox").change(function () { // 禁止所选用户
                var $item = $(this);

                if ($item.is(":checked")) {
                    $item.val('FORBIDDEN');
                } else {
                    $item.val('NORMAL');
                }
            });
        },

        handleListItem: function () {
            var self = this;

            $content.on('click', '[data-tag="list-delete"]', function (e) {
                var $item = $(this).parents("div.list-group-item"),
                    dataUser = $item.attr("data-user"),
                    dataId = $item.attr("data-id");

                alertify.theme('bootstrap')
                    .confirm("您确定要删除该角色吗？", function () {
                        $.ajax({
                            url: $.ctx + '/role/delete?roleId=' + dataId,
                            type: 'POST',
                            dataType: 'JSON',
                            success: function (data) {
                                if (data.success) {
                                    $item.remove();
                                    self.allUsers -= dataUser;
                                    $("[data-allUsers]").attr("data-allUsers", self.allUsers).text(self.allUsers);
                                    self.currentRole($item.next('.list-group-item'));
                                    toastr.success("角色删除成功！");
                                } else {
                                    toastr.error(data.msg);
                                }
                            },
                            error: function () {
                                toastr.error('服务器异常，请稍后再试！');
                            }
                        });
                    });

                e.stopPropagation();
            });

            $content.on('click', '.page-aside-inner .page-aside-section:not(.hidden-xs) .list-group-item', function () {
                self.currentRole($(this));
            });
        },

        handleTable: function () {
            var self = this;

            self.table = $('.dataTable').DataTable($.po('dataTable', {
                dom: '<"row"<"col-xs-6"<"hidden-xs"B>><"col-xs-6"f>><"row"<"col-xs-12"tr>><"row"<"col-sm-5"i><"col-sm-7"p>>',
                processing: true,
                autoWidth: false, //禁用自动调整列宽
                ajax: $.ctx + '/role/user',
                rowId: 'userId',
                buttons: {
                    dom: {
                        container: {
                            className: 'btn-group btn-group-sm'
                        },
                        button: {
                            className: 'btn btn-default btn-outline'
                        }
                    },
                    buttons: [
                        {
                            extend: 'copy',
                            text: '拷贝'
                        },
                        {
                            extend: 'excel',
                            text: '导出 Excel'
                        },
                        {
                            extend: 'csv',
                            text: '导出 CSV'
                        },
                        {
                            extend: 'print',
                            text: '打印'
                        }
                    ]
                },
                columns: [
                    {
                        "render": function () {
                            var checkbox = '<span class="checkbox-custom checkbox-primary">' +
                                '<input type="checkbox" class="contacts-checkbox selectable-item">' +
                                '<label></label></span>';
                            return checkbox;
                        }
                    },
                    {"data": "loginName"},
                    {"data": "createTime"},
                    {"data": "lastLoginTime"},
                    {"data": "loginCount"},
                    {"data": "lastLoginIp"},
                    {
                        "render": function () {
                            var edit = '<button type="button" class="btn btn-sm btn-icon btn-pure btn-default"' + ' data-toggle="edit"><i class="icon wb-edit" aria-hidden="true"></i></button>';
                            return edit;
                        }
                    }
                ],
                rowCallback: function (row, data) {
                    if (data.state === "FORBIDDEN") {
                        $(row).addClass('disabled');
                    }

                    if (data.userId === self.currentUser) {
                        $(row).find('input:checkbox').prop('disabled', true);
                    }
                }
            }));
        },

        handleEdit: function () {
            var self = this, timer;

            $content.on('click', '.page-users button[data-toggle=edit]', function () { //编辑该用户
                var $userInfo = $('#userInfoForm'),$tr,
                    $checkbox = $userAccont.find('input:checkbox');

                self.$item = $(this).closest('tr');
                $tr = self.$item.prev();

                if(self.$item.hasClass('child') && $tr.hasClass('parent')){
                    self.$item = $tr;
                }

                self.thisRow = self.table.row(self.$item).data();

                $('#userAccountInfo')
                    .formValidation('enableFieldValidators', 'password', false, 'notEmpty')
                    .formValidation('enableFieldValidators', 'confirm', false, 'notEmpty');

                $userInfo.find("input[name='loginName']").attr("readonly", "").val(self.thisRow.loginName);
                $userInfo.find("input[name='userId']").val(self.thisRow.userId);

                if (self.thisRow.userId === self.currentUser) {
                    $checkbox.prop('disabled', true);
                } else {
                    if ($checkbox.prop('disabled')) {
                        $checkbox.prop('disabled', false);
                    }

                    if (self.thisRow.state === 'FORBIDDEN') {
                        $checkbox.prop("checked", true).val('FORBIDDEN');
                    }
                }

                self.getAuth();
            });

            $content.on('click', '.btn-edit, #addRoleToggle', function (e) { // 编辑角色信息
                var $item = $(this),
                    $roleInfo = $('#roleInfo'),
                    ID = $item.parents('div.list-group-item').attr('data-id');

                $('#roleForm').modal('show');

                if (ID !== undefined) {
                    $roleInfo.find('input[name="roleName"]').val($item.parent().prev('span.list-text').text());
                }

                $roleInfo.find('input[name="roleId"]').val(ID);

                $('[data-plugin="treeview"]').data('jstree', false).empty().jstree({
                    "checkbox": {
                        "keep_selected_style": false
                    },
                    "plugins": ["checkbox", "search"],
                    "core": {
                        'data': {
                            "url": $.ctx + '/role/menus?roleId=' + (ID === undefined ? -1 : ID),
                            "dataType": "JSON"
                        }
                    }
                });
                e.stopPropagation();

            });

            $content.on('keyup', 'input[name="jstree_search"]', function () { // 搜索树节点
                var $item = $(this);
                if (timer) {
                    clearTimeout(timer);
                }

                timer = setTimeout(function () {
                    var v = $item.val();
                    $('[data-plugin="treeview"]').jstree(true).search(v);
                }, 250);
            });

            $content.on('click', '.btn-unfold', function () { // 展开所有树节点
                $('[data-plugin="treeview"]').jstree().open_all();
            });

            $content.on('click', '.btn-fold', function () { // 折叠所有树节点
                $('[data-plugin="treeview"]').jstree().close_all();
            });

            $content.on('click', ".btn-treeview", function () { // 保存菜单树ID
                var $treeview = $('[data-plugin="treeview"]'),
                    data = [],
                    checkedData = $treeview.jstree("get_checked");

                var treeData = function ($el) {
                    var $items = $el.children('ul').children('li'), i = 0;

                    for (; i < $items.length; i++) {
                        if ($items.eq(i).attr("aria-selected") === "true" || $items.eq(i).children('a').find('i.jstree-undetermined').size() > 0) {
                            data.push($items.eq(i).prop('id'));
                        }
                        if ($items.eq(i).children("ul").size() > 0) {
                            treeData($items.eq(i));
                        }
                    }
                };

                var treeArry = function (checkedData) {
                    var item = checkedData.shift(),
                        temp = [];

                    for (var n in data) {
                        if (item !== data[n]) {
                            temp.push(item);
                        }
                    }
                    if (temp.length === data.length) {
                        data.push(item);
                    }
                    if (checkedData.length > 0) {
                        treeArry(checkedData);
                    }
                };

                treeData($treeview);
                treeArry(checkedData);
                $('#roleInfo').find('input[name="roleAuth"]').val(data);
            });

            $('#roleForm').on('hide.bs.modal', function () { // 添加角色模态窗
                $('#roleInfo').formValidation('resetForm', true);
            });
        },

        getAuth: function () {
            var html, self = this,
                ID = self.thisRow === null ? -1 : self.thisRow.userId;

            $.ajax({
                url: $.ctx + '/user/role?userId=' + ID,
                dataType: 'JSON',
                success: function (data) {
                    if (data.success) {
                        template.helper('authVal', function (auth) {
                            if (auth) {
                                return 'selected';
                            }
                        });

                        html = template('userAuth', data);
                        $userAccont.find('select').html(html).multiSelect('refresh');

                        $('#userInfoForm').modal('show');
                    } else {
                        toastr.error('出错了，请重试！');
                    }
                },
                error: function () {
                    toastr.error('服务器异常，请稍后再试！');
                }
            });
        },

        currentRole: function ($item) {
            var $parents = $('.page-aside-inner'),
                ID = $item.attr('data-id'),
                url = $.ctx + (ID === undefined ? '/role/user' : '/role/user?roleId=' + ID);

            if (!$item.is('.active')) {
                $parents.find('.list-group-item').removeClass('active');
                $item.addClass('active');
                this.table.ajax.url(url).load();
            }
        },

        roleInfoCallback: function (validator, data) {
            var storeData = {
                role: {}
            }, html;
            storeData.role.name = validator.getFieldElements('roleName').val();
            if (data.id) {
                storeData.role.id = data.id;
                html = template('roleTpl', storeData);
            }

            $('#roleForm').one('hidden.bs.modal', function () {
                var $roleContent = $('.role-contents');
                if (data.id) {
                    $roleContent.append(html);
                } else {
                    $roleContent.children('div.active').find('span.list-text').text(storeData.role.name);
                }
            }).modal('hide');
        },

        run: function (next) {
            var self = this;

            this.currentUser = $('#admui-signOut').data('user');
            this.allUsers = $("[data-allUsers]").attr("data-allUsers");

            this.handleTable();
            this.handleAction();
            this.handleListItem();
            this.handleEdit();

            $userAccont.formValidation($.po('formValidation', {
                fields: {
                    loginName: {
                        validators: {
                            notEmpty: {
                                message: '账号信息不能为空'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                enabled: true,
                                message: '密码不能为空'
                            },
                            stringLength: {
                                min: 6,
                                max: 30,
                                message: '密码必须大于6且小于30个字符'
                            }
                        }
                    },
                    confirm: {
                        validators: {
                            notEmpty: {
                                enabled: true,
                                message: '确认密码不能为空'
                            },
                            identical: {
                                field: 'password',
                                message: '确认密码必须和密码保持一致'
                            }
                        }
                    },
                    limit: {
                        validators: {
                            notEmpty: {
                                message: '必须选中一个角色'
                            }
                        }
                    }
                }
            }))
                .on('success.form.fv', function (e) {
                    e.preventDefault();

                    var $item = $(this),
                        validator = $(e.target).data('formValidation'),
                        callback = function () {
                            var state = validator.getFieldElements('state').val();
                            self.table.row(self.$item).data().state = state;

                            if (state === "NORMAL") {
                                if (self.$item.hasClass('disabled')) {
                                    self.$item.removeClass('disabled');
                                }
                            } else if (state === "FORBIDDEN") {
                                if (!self.$item.hasClass('disabled')) {
                                    self.$item.addClass('disabled');
                                }
                            }
                        };

                    $.ajax({
                        url: $.ctx + '/user/save',
                        type: 'POST',
                        data: $item.serialize(),
                        dataType: 'JSON',
                        success: function (data) {
                            if (data.success) {
                                if (self.thisRow !== null) {
                                    callback();
                                } else {
                                    self.table.row.add(data.user).draw(false);
                                }

                                $('#userInfoForm').modal('hide');
                                toastr.success(data.msg);
                            } else {
                                toastr.error(data.msg);
                            }
                        },
                        error: function () {
                            toastr.error('服务器异常，请稍后再试！');
                        }
                    });
                });

            $("#roleInfo").formValidation($.po('formValidation', {
                fields: {
                    roleName: {
                        validators: {
                            notEmpty: {
                                message: '请填写角色名称'
                            }
                        }
                    }
                }
            }))
                .on('success.form.fv', function (e) {
                    e.preventDefault();
                    var validator = $(e.target).data('formValidation');

                    $.ajax({
                        url: $.ctx + '/role/save',
                        type: 'POST',
                        data: $("#roleInfo").serialize(),
                        dataType: 'JSON',
                        success: function (data) {
                            if (data.success) {
                                self.roleInfoCallback(validator, data);
                            } else {
                                toastr.error('出错了，请重试！');
                            }
                        },
                        error: function () {
                            toastr.error('服务器异常，请稍后再试！');
                        }
                    });
                });

            next();
        }
    });

})(document, window, jQuery);
