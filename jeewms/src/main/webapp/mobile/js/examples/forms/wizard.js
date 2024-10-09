/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    // 表单向导
    // -------------------
    (function () {
        // 表单验证
        $('#exampleAccountForm').formValidation($.po('formValidation', {
            fields: {
                username: {
                    validators: {
                        notEmpty: {
                            message: '请填写用户名'
                        },
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '用户名长度为6-30个字符'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: '用户名只能由字母，数字，小数点和下划线组成'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '请填写密码'
                        },
                        different: {
                            field: 'username',
                            message: '密码不能和用户名一致'
                        }
                    }
                }
            }
        }));

        $("#exampleBillingForm").formValidation($.po('formValidation', {
            fields: {
                number: {
                    validators: {
                        notEmpty: {
                            message: '请填写信用卡卡号'
                        }
                        // creditCard: {
                        //   message: '信用卡卡号填写有误'
                        // }
                    }
                },
                cvv: {
                    validators: {
                        notEmpty: {
                            message: '请填写CVV码'
                        }
                        // cvv: {
                        //   creditCardField: 'number',
                        //   message: 'CVV码验证未通过'
                        // }
                    }
                }
            }
        }));

        // 初始化
        var wizard = $("#exampleWizardForm").wizard($.po('wizard', {
            buttonsAppendTo: '.panel-body'
        })).data('wizard');

        // 表单验证
        // http://formvalidation.io/api/#is-valid
        wizard.get("#exampleAccount").setValidator(function () {
            var fv = $("#exampleAccountForm").data('formValidation');
            fv.validate();

            return fv.isValid();

        });

        wizard.get("#exampleBilling").setValidator(function () {
            var fv = $("#exampleBillingForm").data('formValidation');
            fv.validate();

            return fv.isValid();

        });
    })();


    // 表单向导
    // -----------------------------
    // http://formvalidation.io/api/#is-valid-container
    (function () {
        $("#exampleWizardFormContainer").wizard($.po('wizard', {
            onInit: function () {
                $('#exampleFormContainer').formValidation($.po('formValidation', {
                    fields: {
                        username: {
                            validators: {
                                notEmpty: {
                                    message: '请填写用户名'
                                }
                            }
                        },
                        password: {
                            validators: {
                                notEmpty: {
                                    message: '请填写密码'
                                }
                            }
                        },
                        number: {
                            validators: {
                                notEmpty: {
                                    message: '请填写信用卡卡号'
                                }
                            }
                        },
                        cvv: {
                            validators: {
                                notEmpty: {
                                    message: '请填写CVV码'
                                }
                            }
                        }
                    }
                }));
            },
            validator: function () {
                var fv = $('#exampleFormContainer').data('formValidation');

                var $this = $(this);

                // 验证的容器
                fv.validateContainer($this);

                var isValidStep = fv.isValidContainer($this);
                return !(isValidStep === false || isValidStep === null);


            },
            onFinish: function () {
                // $('#exampleFormContainer').submit();
            },
            buttonsAppendTo: '.panel-body'
        }));
    })();

    // 分页
    // --------------------------
    (function () {
        $("#exampleWizardPager").wizard($.po('wizard', {
            step: '.wizard-pane',
            templates: {
                buttons: function () {
                    var options = this.options;
                    return '<div class="btn-group btn-group-sm">' +
                        '<a class="btn btn-default btn-outline" href="#' + this.id + '" data-wizard="back" role="button">' + options.buttonLabels.back + '</a>' +
                        '<a class="btn btn-success btn-outline pull-right" href="#' + this.id + '" data-wizard="finish" role="button">' + options.buttonLabels.finish + '</a>' +
                        '<a class="btn btn-default btn-outline pull-right" href="#' + this.id + '" data-wizard="next" role="button">' + options.buttonLabels.next + '</a>' +
                        '</div>';
                }
            },
            buttonLabels: {
                next: '<i class="icon wb-chevron-right" aria-hidden="true"></i>',
                back: '<i class="icon wb-chevron-left" aria-hidden="true"></i>',
                finish: '<i class="icon wb-check" aria-hidden="true"></i>'
            },

            buttonsAppendTo: '.panel-actions'
        }));
    })();

    // 进度条
    // --------------------------
    (function () {
        $("#exampleWizardProgressbar").wizard($.po('wizard', {
            step: '.wizard-pane',
            onInit: function () {
                this.$progressbar = this.$element.find('.progress-bar').addClass('progress-bar-striped');
            },
            onBeforeShow: function (step) {
                step.$element.tab('show');
            },
            onFinish: function () {
                this.$progressbar.removeClass('progress-bar-striped').addClass('progress-bar-success');
            },
            onAfterChange: function (prev, step) {
                var total = this.length();
                var current = step.index + 1;
                var percent = (current / total) * 100;

                this.$progressbar.css({
                    width: percent + '%'
                }).find('.sr-only').text(current + '/' + total);
            },
            buttonsAppendTo: '.panel-body'
        }));
    })();

    // 选项卡
    // -------------------
    (function () {
        $("#exampleWizardTabs").wizard($.po('wizard', {
            step: '> .nav > li > a',
            onBeforeShow: function (step) {
                step.$element.tab('show');
            },
            classes: {
                step: {
                    //done: 'color-done',
                    error: 'color-error'
                }
            },
            onFinish: function () {
                toastr.success('完成');
            },
            buttonsAppendTo: '.tab-content'
        }));
    })();

    // 折叠面板
    // ------------------------
    (function () {
        var defaults = $.components.getDefaults("wizard");

        $("#exampleWizardAccordion").wizard($.po('wizard', {
            step: '.panel-title[data-toggle="collapse"]',
            classes: {
                step: {
                    //done: 'color-done',
                    error: 'color-error'
                }
            },
            templates: {
                buttons: function () {
                    return '<div class="panel-footer">' + defaults.templates.buttons.call(this) + '</div>';
                }
            },
            onBeforeShow: function (step) {
                step.$pane.collapse('show');
            },

            onBeforeHide: function (step) {
                step.$pane.collapse('hide');
            },

            onFinish: function () {
                toastr.success('完成');
            },

            buttonsAppendTo: '.panel-collapse'
        }));
    })();

})(document, window, jQuery);
