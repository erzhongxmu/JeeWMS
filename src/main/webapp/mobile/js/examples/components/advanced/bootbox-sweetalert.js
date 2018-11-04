/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = {
        run: function () {
            window.exampleBootboxAlertCallback = function () {
                toastr.info("你好，回调函数");
            };

            window.exampleBootboxConfirmCallback = function (result) {
                toastr.info("Confirm 结果：" + result);
            };

            window.exampleBootboxPromptCallback = function (result) {
                if (result === null) {
                    toastr.info("Prompt 取消");
                } else {
                    toastr.info("嗨，<b>" + result + "</b>");
                }
            };

            // 示例
            // ----------------
            (function () {
                var $pageContent = $('#admui-pageContent');
                bootbox.setDefaults($.po('bootbox'));

                $pageContent.on('click', '#exampleBootboxPromptDefaultValue', function () {
                    bootbox.prompt({
                        title: "你叫什么名字？",
                        value: "Admui",
                        callback: function (result) {
                            if (result === null) {
                                toastr.info("Prompt 取消");
                            } else {
                                toastr.info("嗨，<b>" + result + "</b>");
                            }
                        }
                    });
                });

                $pageContent.on('click', '#exampleBootboxCustomDialog', function () {
                    bootbox.dialog({
                        message: "我是一个自定义对话框",
                        title: "自定义标题",
                        buttons: {
                            success: {
                                label: "成功",
                                className: "btn-success",
                                callback: function () {
                                    toastr.info("成功了！");
                                }
                            },
                            danger: {
                                label: "警告",
                                className: "btn-danger",
                                callback: function () {
                                    toastr.info("出问题了！");
                                }
                            },
                            main: {
                                label: "单击我",
                                className: "btn-primary",
                                callback: function () {
                                    toastr.info("Primary 按钮");
                                }
                            }
                        }
                    });
                });

                $pageContent.on('click', '#exampleBootboxCustomHtmlContents', function () {
                    bootbox.dialog({
                        title: "使用 HTML",
                        message: '<p>您可以使用 <b>html</b></p>'
                    });
                });

                $pageContent.on('click', '#exampleBootboxCustomHtmlForms', function () {
                    bootbox.dialog({
                        title: "这是一个含表单的弹框",
                        message: '<form class="form-horizontal">' +
                        '<div class="form-group">' +
                        '<label class="col-md-4 control-label" for="name">姓名</label>' +
                        '<div class="col-md-6">' +
                        '<input type="text" class="form-control input-md" id="name" name="name" placeholder="请输入姓名"> ' +
                        '<span class="help-block">这里填写您的姓名</span></div>' +
                        '</div>' +
                        '<div class="form-group">' +
                        '<label class="col-md-4 control-label" for="awesomeness">性别</label>' +
                        '<div class="col-md-6"><div class="radio-custom radio-primary">' +
                        '<input type="radio" name="sex" id="awesomeness-0" value="male" checked>' +
                        '<label for="awesomeness-0">男 </label>' +
                        '</div><div class="radio-custom radio-primary">' +
                        '<input type="radio" name="sex" id="awesomeness-1" value="female">' +
                        '<label for="awesomeness-1">女 </label>' +
                        '</div>' +
                        '</div></div>' +
                        '</form>',
                        buttons: {
                            success: {
                                label: "保存",
                                className: "btn-success",
                                callback: function () {
                                    var name = $('#name').val();
                                    var answer = $("input[name='sex']:checked").val();
                                    toastr.info("Hi，" + name + "，您选择了 <b>" + answer + "</b>");
                                }
                            }
                        }
                    });
                });
            })();

            // 样式
            // --------------
            (function () {
                var $pageContent = $('#admui-pageContent');

                $pageContent.on("click", '#exampleWarningConfirm', function () {
                    swal($.po('sweetalert', {
                            title: "您确定要这样做吗？",
                            text: "删除此文件后将无法恢复！",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: '#DD6B55',
                            confirmButtonText: '删除',
                            closeOnConfirm: false
                            //closeOnCancel: false
                        }),
                        function () {
                            swal("已删除！", "文件删除成功", "success");
                        });
                });

                $pageContent.on("click", '#exampleWarningCancel', function () {
                    swal($.po('sweetalert', {
                            title: "您确定要这样做吗？",
                            text: "删除此文件后将无法恢复！",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: '#DD6B55',
                            confirmButtonText: '删除',
                            cancelButtonText: "取消",
                            closeOnConfirm: false,
                            closeOnCancel: false
                        }),
                        function (isConfirm) {
                            if (isConfirm) {
                                swal("已删除！", "文件已删除", "success");
                            } else {
                                swal("已取消操作！", "您取消了删除文件的操作", "error");
                            }
                        });
                });
            })();
        }
    };

})(document, window, jQuery);