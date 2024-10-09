/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    "use strict";

    window.Content = {
        queryIp: function () {
            var opts = $.extend(true, {},$.po('webuiPopover'), $('.ip-msg', '#admui-pageContent').data(),{
                trigger: 'click',
                title: '详细信息',
                type: 'async',
                content: function (data) {
                    var message = '出错了，请重试！';
                    if (data.success) {
                        message = data.message;
                    }
                    return message;
                }
            });
            $('.ip-msg', '#admui-pageContent').webuiPopover(opts);
        },
        run: function () {
            var oTable, self = this,
                $blackList = $("#blackList"),
                $pageContent = $("#admui-pageContent");

            oTable = $('.dataTable').DataTable($.po('dataTable',{
                rowId: 'blId',
                autoWidth: false,
                columns: [
                    {
                        "render": function (data, type, row, meta) {
                            return ( meta.row + 1);
                        }
                    },
                    {
                        "render": function (data, type, row) {
                            var html,
                                loginIp = row.loginIp;

                            if (loginIp === undefined) {
                                html = row[1];
                            } else {
                                html = '<a href="javascript:;" class="ip-msg" data-url="/query/ip?' + loginIp + '">' + loginIp + '</a>';
                            }
                            return html;
                        }
                    },
                    {"data": "createTime"},
                    {"data": "comment"},
                    {
                        "render": function () {
                            var html = '<a class="btn btn-pure btn-xs btn-default icon wb-close' +
                                ' delete-tr" href="#"></a>';
                            return html;
                        }
                    }
                ],
                drawCallback: function () {
                    this.api().column(0).nodes().each(function (cell, i) {
                        cell.innerHTML = i + 1;
                    });

                    self.queryIp();
                }
            }));

            $blackList.formValidation($.po('formValidation', {
                fields: {
                    ip: {
                        validators: {
                            notEmpty: {
                                message: "请填写IP地址"
                            }
                        }
                    }
                }
            }))
                .on('success.form.fv', function (e) {
                    e.preventDefault();
                    var validator = $(e.target).data('formValidation'),
                        blacklist = {
                            loginIp: validator.getFieldElements('ip').val(),
                            comment: validator.getFieldElements('comment').val()
                        };

                    $.ajax({
                        url: $.ctx + '/blacklist/save',
                        type: 'POST',
                        data: $(this).serialize(),
                        dataType: 'JSON',
                        success: function (data) {
                            if (data.success) {
                                blacklist.blId = data.blacklist.blId;
                                blacklist.createTime = data.blacklist.createTime;

                                $('#add').one('hidden.bs.modal', function () {
                                    oTable.row.add(blacklist).draw(false);

                                    toastr.success('添加成功！');
                                }).modal('hide');
                            } else {
                                toastr.error(data.msg);
                            }
                        },
                        error: function () {
                            toastr.error('服务器异常，请稍后再试！');
                        }
                    });
                });

            $pageContent.one('hide.bs.modal', '#add', function () { // 模态窗隐藏后
                $blackList.formValidation('resetForm', true);
                $blackList.find('textarea').val('');
            });

            $pageContent.on('click', '.delete-tr', function (e) { // 删除当前行
                e.preventDefault();
                var $item = $(this).closest('tr'),ID,
                    $tr = $item.prev();

                if($item.hasClass('child') && $tr.hasClass('parent')){
                    $item = $tr;
                }
                ID = oTable.row($item).id();

                alertify.theme('bootstrap')
                    .confirm('你确定要删除吗？', function(){
                        $.ajax({
                            url: $.ctx + '/blacklist/delete?blId=' + ID,
                            type: 'POST',
                            dataType: 'JSON',
                            success: function (data) {
                                if (data.success) {
                                    oTable.row($item).remove().draw(false);
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
        }
    };

})(window, document, jQuery);

