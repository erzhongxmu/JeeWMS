/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    window.Content = App.extend({
        handlePagination: function () {
            var $allPage = $('.panel-footer .pagination li').not('.panel-footer .pagination li:first-child').not('.panel-footer .pagination li:last-child');
            var $prev = $('.panel-footer .pagination li').first();
            var $next = $('.panel-footer .pagination li').last();

            function isEnd(obj) {
                var $prevObj = obj.prev();
                var $nextObj = obj.next();

                if ($prevObj.find('a').attr('aria-label') === 'Previous') {
                    $prevObj.addClass('disabled');
                    $next.removeClass('disabled');
                } else if ($nextObj.find('a').attr('aria-label') === 'Next') {
                    $nextObj.addClass('disabled');
                    $prev.removeClass('disabled');
                } else {
                    $next.removeClass('disabled');
                    $prev.removeClass('disabled');
                }
            }

            $allPage.on('click', function () {
                $(this).siblings('li').removeClass('active');
                $(this).addClass('active');
                isEnd($(this));
            });

            $prev.on('click', function () {
                var $prevPage = $allPage.filter('.active').prev();
                if ($(this).attr("class") !== "disabled") {
                    $prevPage.addClass('active');
                    $prevPage.siblings('li').removeClass('active');
                    isEnd($prevPage);
                }
            });

            $next.on('click', function () {
                var $nextPage = $allPage.filter('.active').next();
                if ($(this).attr("class") !== "disabled") {
                    $nextPage.addClass('active');
                    $nextPage.siblings('li').removeClass('active');
                    isEnd($nextPage);
                }
            });

        },
        handleChart: function () {

            /* create line chart */

            var scoreChart = function (data) {

                var scoreChart = new Chartist.Line(data, {
                    labels: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
                    series: [{
                        name: "series-1",
                        data: [0.8, 1.5, 0.8, 2.7, 2.4, 3.9, 1.1]
                    }, {
                        name: "series-2",
                        data: [2.2, 3, 2.7, 3.6, 1.5, 1, 2.9]
                    }]
                }, {
                    lineSmooth: Chartist.Interpolation.simple({
                        divisor: 100
                    }),
                    fullWidth: true,
                    chartPadding: {
                        right: 25
                    },
                    series: {
                        "series-1": {
                            showArea: false
                        },
                        "series-2": {
                            showArea: false
                        }
                    },
                    axisX: {
                        showGrid: false
                    },
                    axisY: {
                        scaleMinSpace: 40
                    },
                    plugins: [
                        Chartist.plugins.tooltip()
                    ],
                    low: 0,
                    height: 250
                });
                scoreChart.on('draw', function (data) {
                    if (data.type === 'point') {
                        var parent = new Chartist.Svg(data.element._node.parentNode);
                        parent.elem('line', {
                            x1: data.x,
                            y1: data.y,
                            x2: data.x + 0.01,
                            y2: data.y,
                            "class": 'ct-point-content'
                        });
                    }
                });

            };

            /*var WeekLabelList = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
             var WeekSeries1List = {
             name: "series-1",
             data: [0.8, 1.5, 0.8, 2.7, 2.4, 3.9, 1.1]
             };
             var WeekSeries2List = {
             name: "series-2",
             data: [2.2, 3, 2.7, 3.6, 1.5, 1, 2.9]
             };*/

            /* create bar chart */
            var barChart = function (data) {
                var barChart = new Chartist.Bar(data, {
                    labels: ['梅小燕', '唐雪琴', '陈媚婉', '嵇慧莉', '刘文平', '孙玉珍', '柳映秋'],
                    series: [
                        [3.3, 3.5, 2.5, 2, 3.7, 2.7, 1.9],
                        [2, 4, 3.5, 2.7, 3.3, 3.5, 2.5]
                    ]
                }, {
                    axisX: {
                        showGrid: false
                    },
                    axisY: {
                        showGrid: false,
                        scaleMinSpace: 30
                    },
                    height: 210,
                    seriesBarDistance: 24
                });

                barChart.on('draw', function (data) {
                    if (data.type === 'bar') {
                        var parent = new Chartist.Svg(data.element._node.parentNode);
                        parent.elem('line', {
                            x1: data.x1,
                            x2: data.x2,
                            y1: data.y2,
                            y2: 0,
                            "class": 'ct-bar-fill'
                        });

                        data.element.attr({
                            style: 'stroke-width: 20px'
                        });
                    }
                });
            };

            /* run chart */
            $(document).on('slidePanel::afterLoad', function () {
                scoreChart('.trends-chart');
                barChart('.member-chart');
            });
        },
        handleSelective: function () {
            var self = this,
                member = [{
                    id: 'uid_1',
                    name: '梅小燕',
                    avatar: '/images/portraits/1.jpg'
                }, {
                    id: 'uid_2',
                    name: '赵桦',
                    avatar: '/images/portraits/2.jpg'
                }, {
                    id: 'uid_3',
                    name: '曹洁群',
                    avatar: '/images/portraits/3.jpg'
                }, {
                    id: 'uid_4',
                    name: '嵇慧莉',
                    avatar: '/images/portraits/4.jpg'
                }, {
                    id: 'uid_5',
                    name: '孙玉珍',
                    avatar: '/images/portraits/5.jpg'
                }, {
                    id: 'uid_6',
                    name: '柳映秋',
                    avatar: '/images/portraits/6.jpg'
                }, {
                    id: 'uid_7',
                    name: '周伊娅',
                    avatar: '/images/portraits/7.jpg'
                }];

            function getNum(num) {
                return Math.ceil(Math.random() * (num + 1));
            }

            function getMember() {
                return member[getNum(member.length - 1) - 1];
            }

            function isSame(items) {
                var _items = items;
                var _member = getMember();

                if (_items.indexOf(_member) === -1) {
                    return _member;
                } else {
                    return isSame(_items);
                }
            }

            function pushMember(num) {
                var items = [];
                for (var i = 0; i < num; i++) {
                    items.push(isSame(items));
                }
                self.items = items;
            }

            function setItems(membersNum) {
                var num = getNum(membersNum - 1);
                pushMember(num);
            }

            $('[data-plugin="jquery-selective"]').each(function () {
                setItems(member.length);

                var items = self.items;

                $(this).selective({
                    namespace: 'addMember',
                    local: member,
                    selected: items,
                    buildFromHtml: false,
                    tpl: {
                        optionValue: function (data) {
                            return data.id;
                        },
                        frame: function () {
                            return '<div class="' + this.namespace + '">' +
                                this.options.tpl.items.call(this) +
                                '<div class="' + this.namespace + '-trigger">' +
                                this.options.tpl.triggerButton.call(this) +
                                '<div class="' + this.namespace + '-trigger-dropdown">' +
                                this.options.tpl.list.call(this) +
                                '</div>' +
                                '</div>' +
                                '</div>';

                        },
                        triggerButton: function () {
                            return '<div class="' + this.namespace + '-trigger-button"><i class="wb-plus"></i></div>';
                        },
                        listItem: function (data) {
                            return '<li class="' + this.namespace + '-list-item"><img class="avatar" src="' + data.avatar + '"> ' + data.name + '</li>';
                        },
                        item: function (data) {
                            return '<li class="' + this.namespace + '-item"><img class="avatar" src="' + data.avatar + '" title="' + data.name + '"> ' +
                                this.options.tpl.itemRemove.call(this) +
                                '</li>';
                        },
                        itemRemove: function () {
                            return '<span class="' + this.namespace + '-remove"><i class="wb-minus-circle"></i></span>';
                        },
                        option: function (data) {
                            return '<option value="' + this.options.tpl.optionValue.call(this, data) + '">' + data.name + '</option>';
                        }
                    }
                });

            });

        },
        run: function (next) {
            this.items = [];

            this.handlePagination();
            this.handleChart();
            this.handleSelective();

            next();
        }
    });

})(document, window, jQuery);
