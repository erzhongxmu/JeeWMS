/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var sign="";
    // 表单向导
    // -------------------
    (function () {
        // 表单验证
        $('#exampleXcajForm').formValidation($.po('formValidation', {
            fields: {
                xm: {
                    validators: {
                        notEmpty: {
                            message: '请填写用户名'
                        },
                        stringLength: {
                            min: 2,
                            max: 30,
                            message: '用户名长度为2-30个字符'
                        }/*,
                        regexp: {
                            /!*regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: '用户名只能由字母，数字，小数点和下划线组成'*!/
                        }*/
                    }
                }
            }
        }));

        $('#exampleBxForm').formValidation($.po('formValidation', {
            fields: {
                xm: {
                    validators: {
                        notEmpty: {
                            message: '请填写用户名'
                        },
                        stringLength: {
                            min: 2,
                            max: 30,
                            message: '用户名长度为2-30个字符'
                        }
                    }
                }
            }
        }));
        $('#exampleSfzhyForm').formValidation($.po('formValidation', {
            fields: {
                xm: {
                    validators: {
                        notEmpty: {
                            message: '请填写用户名'
                        },
                        stringLength: {
                            min: 2,
                            max: 30,
                            message: '用户名长度为2-30个字符'
                        }
                    }
                }
            }
        }));
        $('#exampleZjscForm').formValidation($.po('formValidation', {

        }));
        $('#exampleSfForm').formValidation($.po('formValidation', {

        }));

        // 初始化
        var wizard = $("#exampleWizardForm").wizard($.po('wizard', {
            onNext: function () {
                console.log(sign);
                if(sign=="exampleXcaj"){
                    document.getElementById("validateButton1").click();
                }else if(sign=="exampleSfzhy"){
                    document.getElementById("validateButtonsfzhy").click();
                }else if(sign=="exampleZjsc"){
                    document.getElementById("validateButtonzjsc").click();
                }else if(sign=="exampleSf"){
                    document.getElementById("validateButtonsf").click();
                }
            },
            onFinish:function(){
                var keyid = $('#keyid').val();
                $.post({
                    url: "fxjMobileJjSqbController.do?do110",
                    dataType: JSON,
                    data: {"keyid": keyid},
                    error: function (data) {



                        if (confirm("您确定要关闭本页吗？")) {
                            window.opener = null;
                            window.open('', '_self');
                            window.close()
                        } else {

                        };


                    },
                    success: function (data) {
                        if (confirm("您确定要关闭本页吗？")) {
                            window.opener = null;
                            window.open('', '_self');
                            window.close()
                        } else {

                        };
                    }
                });
            },
            buttonsAppendTo: '.panel-body',
            buttonLabels: {
                next: '下一步',
                back: '上一步',
                finish: '提交'
            }
        })).data('wizard');

        // 表单验证
        // http://formvalidation.io/api/#is-valid
        wizard.get("#exampleSfzhy").setValidator(function () {
            var fv = $("#exampleSfzhyForm").data('formValidation');
            fv.validate();
            sign="exampleSfzhy";
            console.log("1");
            return fv.isValid();

        });

        wizard.get("#exampleXcaj").setValidator(function () {
            var fv = $("#exampleXcajForm").data('formValidation');
            fv.validate();
            sign="exampleXcaj";
            console.log("2");
            return fv.isValid();
        });

        wizard.get("#exampleZjsc").setValidator(function () {
            var fv = $("#exampleZjscForm").data('formValidation');
            fv.validate();
            sign="exampleZjsc";
            console.log("3");
            return fv.isValid();

        });

        wizard.get("#exampleSf").setValidator(function () {
            var fv = $("#exampleSfForm").data('formValidation');
            fv.validate();
            sign="exampleSf";
            console.log("4");
            return fv.isValid();
        });
    })();

})(document, window, jQuery);
