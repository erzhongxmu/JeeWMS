/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    window.Content.extend({
        run: function (next) {
            $('#accountMsg').formValidation($.po('formValidation', {
                fields: {
                    oldPwd: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },
                            callback: {
                                message: '密码不正确',
                                callback: function (value, validator, $field) {
                                    return $field.data('valid');
                                }
                            }
                        }
                    },
                    newPwd: {
                        enabled: false,
                        validators: {
                            notEmpty: {
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
                        enabled: false,
                        validators: {
                            notEmpty: {
                                message: '确认密码不能为空'
                            },
                            identical: {
                                field: 'newPwd',
                                message: '确认密码必须和密码保持一致'
                            }
                        }
                    }
                }
            }))
                .on('focus', '[name="oldPwd"]', function () {
                    $('#accountMsg')
                        .formValidation('enableFieldValidators', 'oldPwd', false, 'callback');
                })
                .on('focusout', '[name="oldPwd"]', function () {
                    var $item = $(this),
                        thisVal = $item.val(),
                        $accountMsg = $('#accountMsg');

                    if (thisVal !== "") {
                        $.ajax({
                            url: $.ctx + '/user/check',
                            type: 'POST',
                            data: {oldPwd: thisVal},
                            dataType: 'JSON',
                            success: function (data) {
                                $item.data('valid', data.success);
                                $accountMsg
                                    .formValidation('enableFieldValidators', 'oldPwd', true, 'callback')
                                    .formValidation('validateField', 'oldPwd');
                                if (data.success) {
                                    $accountMsg
                                        .formValidation('enableFieldValidators', 'newPwd', true)
                                        .formValidation('enableFieldValidators', 'confirm', true);
                                } else {
                                    $accountMsg
                                        .formValidation('enableFieldValidators', 'newPwd', false)
                                        .formValidation('enableFieldValidators', 'confirm', false);
                                }
                            },
                            error: function () {
                                $accountMsg
                                    .formValidation('enableFieldValidators', 'newPwd', false)
                                    .formValidation('enableFieldValidators', 'confirm', false);
                                toastr.error('服务器异常，请稍后再试！');
                            }
                        });
                    }
                })
                .on('success.form.fv', function (e) {
                    e.preventDefault();
                    var pwd = $(e.target).data('formValidation').getFieldElements('confirm').val();
                    $.ajax({
                        url: $.ctx + '/user/changePassword',
                        type: 'POST',
                        data: {password: pwd},
                        dataType: 'JSON',
                        success: function (data) {
                            var time = 5,timer;
                            if (data.success){
                                alertify.theme('bootstrap')
                                    .alert('<p id="modifyPwd"><span>'+time+'</span>秒后将自动跳转到登录页面</p>');

                                timer = setInterval(function () {
                                    time--;
                                    $('#modifyPwd span').text(time);
                                    if(time === 0){
                                        clearTimeout(timer);
                                        location.href = '/system/logout';
                                    }
                                },1000);
                            }
                            else{
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

})(window, document, jQuery);