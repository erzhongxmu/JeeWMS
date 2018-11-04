/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = {
        run: function () {
            // 完整示例
            // ------------------------
            $('#exampleFullForm').formValidation($.po('formValidation', {
                button: {
                    selector: '#validateButton1',
                    disabled: 'disabled'
                },
                fields: {
                    username: {
                        validators: {
                            notEmpty: {
                                message: '请填写用户名'
                            },
                            stringLength: {
                                min: 6,
                                max: 30
                            },
                            regexp: {
                                regexp: /^[a-zA-Z0-9]+$/
                            }
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: '请填写邮箱'
                            },
                            emailAddress: {
                                message: '邮箱格式错误'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '请填写密码'
                            },
                            stringLength: {
                                min: 8
                            }
                        }
                    },
                    birthday: {
                        validators: {
                            notEmpty: {
                                message: '请填写生日'
                            },
                            date: {
                                format: 'YYYY/MM/DD'
                            }
                        }
                    },
                    github: {
                        validators: {
                            url: {
                                message: '请填写URL'
                            }
                        }
                    },
                    skills: {
                        validators: {
                            notEmpty: {
                                message: '请填写技能'
                            },
                            stringLength: {
                                max: 300
                            }
                        }
                    },
                    porto_is: {
                        validators: {
                            notEmpty: {
                                message: '请至少选择一项'
                            }
                        }
                    },
                    'for[]': {
                        validators: {
                            notEmpty: {
                                message: '请至少选择一项'
                            }
                        }
                    },
                    company: {
                        validators: {
                            notEmpty: {
                                message: '请选择公司'
                            }
                        }
                    },
                    browsers: {
                        validators: {
                            notEmpty: {
                                message: '请选择浏览器'
                            }
                        }
                    }
                }
            }));

            // 规则
            // -------------------------------
            $('#exampleConstraintsForm, #exampleConstraintsFormTypes').formValidation($.po('formValidation', {
                fields: {
                    type_email: {
                        validators: {
                            emailAddress: {
                                message: '邮箱格式不正确'
                            }
                        }
                    },
                    type_url: {
                        validators: {
                            url: {
                                message: '链接格式错误'
                            }
                        }
                    },
                    type_digits: {
                        validators: {
                            digits: {
                                message: '输入值不是一个数'
                            }
                        }
                    },
                    type_numberic: {
                        validators: {
                            integer: {
                                message: '请输入数字'
                            }
                        }
                    },
                    type_phone: {
                        validators: {
                            phone: {
                                message: '请输入正确的手机号'
                            }
                        }
                    },
                    type_credit_card: {
                        validators: {
                            creditCard: {
                                message: '信用卡卡号错误'
                            }
                        }
                    },
                    type_date: {
                        validators: {
                            date: {
                                format: 'YYYY/MM/DD'
                            }
                        }
                    },
                    type_color: {
                        validators: {
                            color: {
                                type: ['hex', 'hsl', 'hsla', 'keyword', 'rgb', 'rgba'], // The default value for type
                                message: '颜色值错误'
                            }
                        }
                    },
                    type_ip: {
                        validators: {
                            ip: {
                                ipv4: true,
                                ipv6: true,
                                message: 'IP格式有误'
                            }
                        }
                    }
                }
            }));

            // 标准模式
            // ---------------------------------
            $('#exampleStandardForm').formValidation($.po('formValidation', {
                button: {
                    selector: '#validateButton2',
                    disabled: 'disabled'
                },
                fields: {
                    standard_name: {
                        validators: {
                            notEmpty: {
                                message: '请填写用户名'
                            }
                        }
                    },
                    standard_email: {
                        validators: {
                            notEmpty: {
                                message: '请填写密码'
                            },
                            emailAddress: {
                                message: '请填写邮箱'
                            }
                        }
                    },
                    standard_content: {
                        validators: {
                            notEmpty: {
                                message: '请填写内容'
                            },
                            stringLength: {
                                max: 500,
                                message: '内容长度不能超过500个字符'
                            }
                        }
                    }
                }
            }));

            // 摘要模式
            // -------------------------------
            $('.summary-errors').hide();

            $('#exampleSummaryForm').formValidation($.po('formValidation', {
                button: {
                    selector: '#validateButton3',
                    disabled: 'disabled'
                },
                fields: {
                    summary_name: {
                        validators: {
                            notEmpty: {
                                message: '请填写用户名'
                            }
                        }
                    },
                    summary_email: {
                        validators: {
                            notEmpty: {
                                message: '请填写密码'
                            },
                            emailAddress: {
                                message: '请填写邮箱'
                            }
                        }
                    },
                    summary_content: {
                        validators: {
                            notEmpty: {
                                message: '请填写内容'
                            },
                            stringLength: {
                                max: 500,
                                message: '内容长度不能超过500个字符'
                            }
                        }
                    }
                }
            }))

                .on('success.form.fv', function () {
                    // 验证通过后重置错误信息提示
                    $('.summary-errors').html('');
                })

                .on('err.field.fv', function (e, data) {
                    $('.summary-errors').show();

                    /*// 获取字段消息
                     var messages = data.fv.getMessages(data.element);*/

                    // 如果已存在，删除字段消息
                    $('.summary-errors').find('li[data-field="' + data.field + '"]').remove();

                    /*// 遍历消息
                     for (var i in messages) {
                     // 在消息框中创建'li'元素
                     $('<li/>')
                     .attr('data-field', data.field)
                     .wrapInner(
                     $('<a/>')
                     .attr('href', 'javascript: void(0);')
                     // .addClass('alert alert-danger alert-dismissible')
                     .html(messages[i])
                     .on('click', function () {
                     // 定位到无效字段
                     data.element.focus();
                     })
                     ).appendTo('.summary-errors > ul');
                     }*/

                    // 隐藏默认消息
                    // $field.data('fv.messages')
                    data.element
                        .data('fv.messages')
                        .find('.help-block[data-fv-for="' + data.field + '"]')
                        .hide();
                })

                .on('success.field.fv', function (e, data) {
                    // 移除字段消息
                    $('.summary-errors > ul').find('li[data-field="' + data.field + '"]').remove();
                    if ($('#exampleSummaryForm').data('formValidation').isValid()) {
                        $('.summary-errors').hide();
                    }
                });
        }
    };
})(document, window, jQuery);
