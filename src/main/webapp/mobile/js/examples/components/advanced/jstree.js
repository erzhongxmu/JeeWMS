/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = {
        run: function () {
            $('#data').jstree({ // 内嵌数据
                'core': {
                    'data': [
                        {
                            "text": "父节点", "children": [
                            {"text": "子节点 1"},
                            {"text": "子节点 2"}
                        ]
                        }
                    ]
                }
            });

            $('#frmt').jstree({ // 数据格式化
                'core': {
                    'data': [
                        {
                            "text": "父节点",
                            "state": {"opened": true},
                            "children": [
                                {
                                    "text": "子节点 1",
                                    "state": {"selected": true},
                                    "icon": "jstree-file"
                                },
                                {"text": "子节点 2", "state": {"disabled": true}}
                            ]
                        }
                    ]
                }
            });

            $('#ajax').data('jstree', false).empty().jstree({ // Ajax
                'core': {
                    'data': {
                        "url": $.ctx + "/data/examples/components/jstree-root.json",
                        "dataType": "JSON"
                    }
                }
            });

            $('#lazy').jstree({ // 懒加载
                'core': {
                    'data': {
                        "url": $.ctx + "/data/examples/components/jstree-root.json",
                        "dataType": "JSON",
                        "data": function (node) {
                            return {"id": node.id};
                        }
                    }
                }
            });

            $('#clbk').jstree({ // 从回调函数中获取数据
                'core': {
                    'data': function (node, cb) {
                        if (node.id === "#") {
                            cb([{"text": "父节点", "id": "1", "children": true}]);
                        }
                        else {
                            cb(["子节点"]);
                        }
                    }
                }
            });

            $( '#evts_button').on("click",function () { // 交互和事件
                var instance = $('#evts').jstree(true);
                instance.deselect_all();
                instance.select_node('1');
            });

            $('#evts')
                .on("changed.jstree", function (e, data) {
                    if (data.selected.length) {
                        toastr.info('您选择的节点是：' + data.instance.get_node(data.selected[0]).text);
                    }
                })
                .jstree({
                    'core': {
                        'multiple': false,
                        'data': [
                            {
                                "text": "父节点", "children": [
                                {"text": "子节点 1", "id": 1},
                                {"text": "子节点 2"}
                            ]
                            }
                        ]
                    }
                });
        }
    };

})(document, window, jQuery);