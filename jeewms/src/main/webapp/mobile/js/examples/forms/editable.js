/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = {
        run: function(){
            //禁用 / 启用
            $('#editableEnable').click(function () {
                $('#editableUser .editable').editable('toggleDisabled');
            });


            var init_x_editable = function () {

                $.fn.editableform.buttons =
                    '<button type="submit" class="btn btn-primary btn-sm editable-submit">' +
                    '<i class="icon wb-check" aria-hidden="true"></i>' +
                    '</button>' +
                    '<button type="button" class="btn btn-default btn-sm editable-cancel">' +
                    '<i class="icon wb-close" aria-hidden="true"></i>' +
                    '</button>';

                $.fn.editabletypes.datefield.defaults.inputclass = "form-control input-sm";

                //defaults
                $.fn.editable.defaults.url = $.ctx + '/post';

                //editables
                $('#editableSuperuser').editable({
                    url: $.ctx + '/post',
                    type: 'text',
                    pk: 1,
                    name: 'username',
                    title: '请输入用户名'
                });

                $('#editableFirstname').editable({
                    validate: function (value) {
                        if ($.trim(value) === '') {
                            return '必填';
                        }
                    }
                });

                $('#editableSex').editable({
                    prepend: "请选择",
                    source: [{
                        value: 1,
                        text: '男'
                    }, {
                        value: 2,
                        text: '女'
                    }],
                    display: function (value, sourceData) {
                        var colors = {
                                "": "gray",
                                1: "green",
                                2: "blue"
                            },
                            elem = $.grep(sourceData, function (o) {
                                return o.value === value;
                            });

                        if (elem.length) {
                            $(this).text(elem[0].text).css("color", colors[value]);
                        } else {
                            $(this).empty();
                        }
                    }
                });


                $('#editableVacation').editable({
                    datepicker: {
                        todayBtn: 'linked'
                    }
                });

                $('#editableDob').editable();

                $('#editableEvent').editable({
                    placement: 'right',
                    combodate: {
                        firstItem: 'name'
                    }
                });

                $('#editableMeetingStart').editable({
                    format: 'yyyy-mm-dd hh:ii',
                    viewformat: 'yyyy/mm/dd hh:ii',
                    validate: function (v) {
                        if (v && v.getDate() === 10) {
                            return '日期不能为10！';
                        }
                    },
                    datetimepicker: {
                        todayBtn: 'linked',
                        weekStart: 1
                    }
                });

                $('#editableComments').editable({
                    showbuttons: 'bottom'
                });

                $('#editableNote').editable();
                $('#editablePencil').click(function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                    $('#editableNote').editable('toggle');
                });

                $('#editableState').editable({
                    source: ["Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Dakota", "North Carolina", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"]
                });

                var editableStates = ["Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Dakota", "North Carolina", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"],
                    states = new Bloodhound({
                        datumTokenizer: Bloodhound.tokenizers.whitespace,
                        queryTokenizer: Bloodhound.tokenizers.whitespace,
                        local: editableStates
                    });
                $('#editableState2').editable({
                    value: 'California',
                    typeahead: {
                        options: {
                            hint: true,
                            highlight: true,
                            minLength: 1
                        },
                        datasets: {
                            name: 'states',
                            source: states
                        }
                    }
                });

                $('#editableFruits').editable({
                    pk: 1,
                    limit: 3,
                    source: [{
                        value: 1,
                        text: '香蕉'
                    }, {
                        value: 2,
                        text: '桃子'
                    }, {
                        value: 3,
                        text: '苹果'
                    }, {
                        value: 4,
                        text: '西瓜'
                    }, {
                        value: 5,
                        text: '橘子'
                    }]
                });


                $('#editableAddress').editable({
                    url: $.ctx + '/post',
                    value: {
                        city: "上海",
                        street: "人民大道",
                        building: "12"
                    },
                    validate: function (value) {
                        if (value.city === '') {
                            return '请选择城市';
                        }
                    },
                    display: function (value) {
                        if (!value) {
                            $(this).empty();
                            return;
                        }
                        var html = '<b>' + $('<div>').text(value.city).html() + '</b>, ' + $('<div>').text(value.street).html() + ' st., bld. ' + $('<div>').text(value.building).html();
                        $(this).html(html);
                    }
                });

                // $("#editableUser").find(".form-control").addClass(".input-sm");
            };

            $.fn.editable.defaults.mode = 'inline';
            init_x_editable();

            // $('#editableControls').on("click", "label", function() {
            //   xMode = $(this).find("input").val();
            //   $.fn.editable.defaults.mode = xMode;
            //   destory_x_editable();
            //   init_x_editable();
            // });
        }
    };
})(document, window, jQuery);
