/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var getExampleDatas = (function () {
            return [{
                text: '父节点 1',
                href: '#parent1',
                tags: ['4'],
                nodes: [{
                    text: '子节点 1',
                    href: '#child1',
                    tags: ['2'],
                    nodes: [{
                        text: '叶子节点 1',
                        href: '#叶子节点1',
                        tags: ['0']
                    }, {
                        text: '叶子节点 2',
                        href: '#叶子节点2',
                        tags: ['0']
                    }]
                }, {
                    text: '子节点 2',
                    href: '#child2',
                    tags: ['0']
                }]
            }, {
                text: '父节点 2',
                href: '#parent2',
                tags: ['0']
            }, {
                text: '父节点 3',
                href: '#parent3',
                tags: ['0']
            }, {
                text: '父节点 4',
                href: '#parent4',
                tags: ['0']
            }, {
                text: '父节点 5',
                href: '#parent5',
                tags: ['0']
            }];
        })(),
        defaults, treeViewExample = {};

    defaults = $.extend({}, {
        injectStyle: false,
        expandIcon: "icon wb-plus",
        collapseIcon: "icon wb-minus",
        emptyIcon: "icon",
        nodeIcon: "icon wb-folder",
        showBorder: false,
        color: "#000000",
        backColor: "#FFFFFF",
        borderColor: $.colors("blue-grey", 200),
        onhoverColor: $.colors("blue-grey", 100),
        selectedColor: "#ffffff",
        selectedBackColor: $.colors("purple", 600),

        searchResultColor: $.colors("purple", 600),
        searchResultBackColor: "#ffffff"
    });

    $.extend(treeViewExample, {
        basic: function () {
            $('.treeview', '#admui-pageContent').each(function () {
                var $item = $(this),
                    basic_options = $.extend({}, defaults, $item.data(), {
                        data: getExampleDatas
                    });

                $item.treeview(basic_options);
            });
        },

        jsonData: function () { // Json 数据示例
            var json = '[' +
                    '{' +
                    '"text": "父节点 1",' +
                    '"nodes": [' +
                    '{' +
                    '"text": "子节点 1",' +
                    '"nodes": [' +
                    '{' +
                    '"text": "叶子节点 1"' +
                    '},' +
                    '{' +
                    '"text": "叶子节点 2"' +
                    '}' +
                    ']' +
                    '},' +
                    '{' +
                    '"text": "子节点 2"' +
                    '}' +
                    ']' +
                    '},' +
                    '{' +
                    '"text": "父节点 2"' +
                    '},' +
                    '{' +
                    '"text": "父节点 3"' +
                    '},' +
                    '{' +
                    '"text": "父节点 4"' +
                    '},' +
                    '{' +
                    '"text": "父节点 5"' +
                    '}' +
                    ']',

                json_options = $.extend({}, defaults, {
                    data: json
                });

            $('#exampleJsonData').treeview(json_options);
        },

        check: function () { // 查找示例
            var options = $.extend({}, defaults, {
                data: getExampleDatas
            });

            var $searchableTree = $('#exampleSearchableTree').treeview(options);

            $('#admui-pageContent').on('keyup', '#inputSearchable', function (e) {
                var pattern = $(e.target).val();

                $searchableTree.treeview('search', [pattern, {
                    'ignoreCase': true,
                    'exactMatch': false
                }]);
            });
        },

        foldChange: function () { // 展开收起示例
            var options = $.extend({}, defaults, {
                    data: getExampleDatas
                }),
                $expandibleTree = $('#exampleExpandibleTree').treeview(options);

            $('#admui-pageContent').on('click', '#exampleExpandAll', function () {
                $expandibleTree.treeview('expandAll', {
                    levels: '99'
                });
            });

            $('#admui-pageContent').on('click', '#exampleCollapseAll', function () {
                $expandibleTree.treeview('collapseAll');
            });
        },

        event: function () {
            var events_toastr, options;

            events_toastr = function (msg) {
                toastr.info(msg, '', {
                    iconClass: 'toast-just-text toast-info',
                    positionClass: 'toast-bottom-right',
                    containertId: 'toast-bottom-right'
                });
            };

            options = $.extend({}, defaults, { // 事件
                data: getExampleDatas,
                onNodeCollapsed: function (event, node) {
                    events_toastr(node.text + ' 被收起');
                },
                onNodeExpanded: function (event, node) {
                    events_toastr(node.text + ' 被展开');
                },
                onNodeSelected: function (event, node) {
                    events_toastr(node.text + ' 被选中');
                },
                onNodeUnselected: function (event, node) {
                    events_toastr(node.text + ' 被取消选中');
                }
            });

            $('#exampleEvents').treeview(options);
        },

        run: function () {
            this.basic();
            this.jsonData();
            this.check();
            this.foldChange();
            this.event();
        }
    });

    treeViewExample.run();
})(document, window, jQuery);