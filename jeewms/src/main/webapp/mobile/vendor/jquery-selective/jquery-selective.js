/*! jQuery Selective - v0.1.0 - 2015-09-30
* https://github.com/amazingSurge/jquery-selective
* Copyright (c) 2015 amazingSurge; Licensed GPL */
/*! jQuery Selective - v0.1.0 - 2014-09-06
 * https://github.com/amazingSurge/jquery-selective
 * Copyright (c) 2014 amazingSurge; Licensed GPL */
(function(window, document, $, undefined) {
    "use strict";
    // Constructor
    var Selective = $.Selective = function(element, options) {
        this.el = element;
        this.$el = $(element).css({
            display: 'none'
        }) || $('<select></select>');

        this.options = $.extend(true, {}, Selective.defaults, options);

        this.namespace = this.options.namespace;

        var self = this;
        var $frame = $(this.options.tpl.frame.call(this));

        //get the select    
        var _build = function() {
            self.$el.html(self.options.tpl.select.call(self));
            return self.$el.children('select');
        };
        this.$select = this.$el.is('select') === true ? this.$el : _build();

        this.$el.after($frame);

        this._init();
        this.opened = false;
    };

    Selective.defaults = {
        namespace: 'selective',
        buildFromHtml: true,
        closeOnSelect: false,
        local: null,
        selected: null,
        withSearch: false,
        searchType: null, //'change' or 'keyup'
        ajax: {
            work: false,
            url: null,
            quietMills: null,
            loadMore: false,
            pageSize: null
        },
        tpl: {
            frame: function() {
                return '<div class="' + this.namespace + '">' +
                    '<div class="' + this.namespace + '-trigger">' +
                    this.options.tpl.triggerButton.call(this) +
                    '<div class="' + this.namespace + '-trigger-dropdown">' +
                    '<div class="' + this.namespace + '-list-wrap">' +
                    this.options.tpl.list.call(this) +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    this.options.tpl.items.call(this) +
                    '</div>';
            },
            search: function() {
                return '<input class="' + this.namespace + '-search" type="text" placeholder="Search...">';
            },
            select: function() {
                return '<select class="' + this.namespace + '-select" name="' + this.namespace + '" multiple="multiple"></select>';
            },
            optionValue: function(data) {
                return data.name;
            },
            option: function(content) {
                return '<option value="' + this.options.tpl.optionValue.call(this) + '">' + content + '</option>';
            },
            items: function() {
                return '<ul class="' + this.namespace + '-items"></ul>';
            },
            item: function(content) {
                return '<li class="' + this.namespace + '-item">' +
                    content +
                    this.options.tpl.itemRemove.call(this) +
                    '</li>';
            },
            itemRemove: function() {
                return '<span class="' + this.namespace + '-remove">x</span>';
            },
            triggerButton: function() {
                return '<div class="' + this.namespace + '-trigger-button">Add</div>';
            },
            list: function() {
                return '<ul class="' + this.namespace + '-list"></ul>';
            },
            listItem: function(content) {
                return '<li class="' + this.namespace + '-list-item">' + content + '</li>';
            }
        },
        query: function() {}, //function(api, search_text, page) {},
        onBeforeShow: null,
        onAfterShow: null,
        onBeforeHide: null,
        onAfterHide: null,
        onBeforeSearch: null,
        onAfterSearch: null,
        onBeforeSelected: null,
        onAfterSelected: null,
        onBeforeUnselect: null,
        onAfterUnselect: null,
        onBeforeItemRemove: null,
        onAfterItemRemove: null,
        onBeforeItemAdd: null,
        onAfterItemAdd: null
    };

    Selective.prototype = {
        constructor: Selective,
        _list: {
            build: function(self, data) {
                var $list = $('<ul></ul>');
                var $options = self._options.getOptions(self);
                if (self.options.buildFromHtml === true) {
                    if ($options.length !== 0) {
                        $.each($options, function(i, n) {
                            var $li = $(self.options.tpl.listItem.call(self, n.text)),
                                $n = $(n);
                            self.setIndex($li, $n);
                            if ($n.attr('selected') !== undefined) {
                                self.select($li);
                            }
                            $list.append($li);
                        });
                    }
                } else {
                    if (data !== null) {
                        $.each(data, function(i) {
                            var $li = $(self.options.tpl.listItem.call(self, data[i]));
                            self.setIndex($li, data[i]);
                            $list.append($li);
                        });
                        if ($options.length !== 0) {
                            $.each($options, function(i, n) {
                                var $n = $(n),
                                    li = self.getItem('li', $list, self.options.tpl.optionValue($n.data('selective_index')));
                                if (li !== undefined) {
                                    self._list.select(self, li);
                                }
                            });
                        }
                    }
                }
                self.$list.append($list.children('li'));
                return self;
            },
            buildSearch: function(self) {
                if (self.options.withSearch === true) {
                    self.$triggerDropdown.prepend(self.options.tpl.search.call(self));
                    self.$search = self.$triggerDropdown.find('.' + self.namespace + '-search');
                }
                return self;
            },
            select: function(self, obj) {
                self._trigger("beforeSelected");
                obj.addClass(self.namespace + '-selected');
                self._trigger("afterSelected");
                return self;
            },
            unselect: function(self, obj) {
                self._trigger("beforeUnselected");
                obj.removeClass(self.namespace + '-selected');
                self._trigger("afterUnselected");
                return self;
            },
            click: function(self) {
                self.$list.on('click', 'li', function() {
                    var $this = $(this);
                    if (!$this.hasClass(self.namespace + '-selected')) {
                        self.select($this);
                    }
                });
            },
            filter: function(self, val) {
                $.expr[':'].Contains = function(a, i, m) {
                    return jQuery(a).text().toUpperCase()
                        .indexOf(m[3].toUpperCase()) >= 0;
                };
                if (val) {
                    self.$list.find("li:not(:Contains(" + val + "))").slideUp();
                    self.$list.find("li:Contains(" + val + ")").slideDown();
                } else {
                    self.$list.children('li').slideDown();
                }
                return self;
            },
            loadMore: function(self, pageMax) {
                var _pageMax = pageMax || 9999;
                if (_pageMax > self.page) {
                    self.$listWrap.on('scroll.selective', function() {
                        var listHeight = self.$list.outerHeight(),
                            wrapHeight = self.$listWrap.height(),
                            wrapScrollTop = self.$listWrap.scrollTop(),
                            below = listHeight - wrapHeight - wrapScrollTop;
                        if (below === 0) {
                            self.options.query(self, self.$search.val(), ++self.page);
                        }
                    });
                }
                return self;
            },
            loadMoreRemove: function(self) {
                self.$listWrap.off('scroll.selective');
                return self;
            }
        },
        _options: {
            getOptions: function(self) {
                self.$options = self.$select.find('option');
                return self.$options;
            },
            select: function(self, opt) {
                opt.prop('selected', true);
                return self;
            },
            unselect: function(self, opt) {
                opt.prop('selected', false);
                return self;
            },
            add: function(self, data) {
                if (self.options.buildFromHtml === false &&
                    self.getItem('option', self.$select, self.options.tpl.optionValue(data)) === undefined) {
                    var $option = $(self.options.tpl.option.call(self, data));
                    self.setIndex($option, data);
                    self.$select.append($option);
                    return $option;
                }
            },
            remove: function(self, opt) {
                opt.remove();
                return self;
            }
        },
        _items: {
            withDefaults: function(self, data) {
                if (data !== null) {
                    $.each(data, function(i) {
                        self._options.add(self, data[i]);
                        self._options.select(self, self.getItem('option', self.$select, self.options.tpl.optionValue(data[i])));
                        self._items.add(self, data[i]);
                    });
                }
            },
            add: function(self, data, content) {
                // self._trigger("beforeItemAdd");
                var $item, fill;
                if (self.options.buildFromHtml === true) {
                    fill = content;
                } else {
                    fill = data;
                }
                $item = $(self.options.tpl.item.call(self, fill));
                self.setIndex($item, data);
                self.$items.append($item);
                // self._trigger("afterItemAdd");
                // return $item;
            },
            remove: function(self, obj) {
                var $li, $option;
                if (self.options.buildFromHtml === true) {
                    self._list.unselect(self, obj.data('selective_index'));
                    self._options.unselect(self, obj.data('selective_index').data('selective_index'));
                } else {
                    $li = self.getItem('li', self.$list, self.options.tpl.optionValue(obj.data('selective_index')));
                    if ($li !== undefined) {
                        self._list.unselect(self, $li);
                    }
                    $option = self.getItem('option', self.$select, self.options.tpl.optionValue(obj.data('selective_index')));
                    self._options.unselect(self, $option)._options.remove(self, $option);
                }

                obj.remove();
                return self;
            },
            click: function(self) {
                self.$items.on('click', '.' + self.namespace + '-remove', function() {
                    var $this = $(this),
                        $item = $this.parents('li');
                    self.itemRemove($item);
                });
            }
        },
        _search: {
            change: function(self) {
                self.$search.change(function() {
                    self._trigger("beforeSearch");
                    if (self.options.buildFromHtml === true) {
                        self._list.filter(self, self.$search.val());
                    } else {
                        if (self.$search.val() !== '') {
                            self.page = 1;
                            self.options.query(self, self.$search.val(), self.page);
                        } else {
                            self.update(self.options.local);
                        }
                    }
                    self._trigger("afterSearch");
                });
            },
            keyup: function(self) {
                var quietMills = self.options.ajax.quietMills || 1000,
                    old_value = '',
                    current_value = '',
                    timeout;

                self.$search.on('keyup', function(e) {
                    self._trigger("beforeSearch");
                    current_value = self.$search.val();
                    if (self.options.buildFromHtml === true) {
                        if (current_value !== old_value) {
                            self._list.filter(self, current_value);
                        }
                    } else {
                        if (current_value !== old_value || e.keyCode === 13) {
                            window.clearTimeout(timeout);
                            timeout = window.setTimeout(function() {
                                if (current_value !== '') {
                                    self.page = 1;
                                    self.options.query(self, current_value, self.page);
                                } else {
                                    self.update(self.options.local);
                                }
                            }, quietMills);
                        }
                    }
                    old_value = current_value;
                    self._trigger("afterSearch");
                });
            },
            bind: function(self, type) {
                if (type === 'change') {
                    this.change(self);
                } else if (type === 'keyup') {
                    this.keyup(self);
                }
            }
        },
        _init: function() {
            this.$selective = this.$el.next('.' + this.namespace);
            this.$items = this.$selective.find('.' + this.namespace + '-items');
            this.$trigger = this.$selective.find('.' + this.namespace + '-trigger');
            this.$triggerButton = this.$selective.find('.' + this.namespace + '-trigger-button');
            this.$triggerDropdown = this.$selective.find('.' + this.namespace + '-trigger-dropdown');
            this.$listWrap = this.$selective.find('.' + this.namespace + '-list-wrap');
            this.$list = this.$selective.find('.' + this.namespace + '-list');

            this._items.withDefaults(this, this.options.selected);
            this.update(this.options.local)._list.buildSearch(this);

            var self = this;
            self.$triggerButton.on('click', function() {
                if (self.opened === false) {
                    self.show(self);
                } else if (self.opened === true) {
                    self.hide(self);
                }
            });

            this._list.click(this);
            this._items.click(this);

            if (this.options.withSearch === true) {
                this._search.bind(this, this.options.searchType);
            }
        },
        _trigger: function(eventType) {
            var method_arguments = Array.prototype.slice.call(arguments, 1),
                data = [this].concat(method_arguments);

            // event
            this.$el.trigger('selective::' + eventType, data);

            // callback
            eventType = eventType.replace(/\b\w+\b/g, function(word) {
                return word.substring(0, 1).toUpperCase() + word.substring(1);
            });
            var onFunction = 'on' + eventType;
            if (typeof this.options[onFunction] === 'function') {
                this.options[onFunction].apply(this, method_arguments);
            }
        },
        _show: function() {
            var self = this;
            $(document).on('click.selective', function(e) {
                if (self.options.closeOnSelect === true) {
                    if ($(e.target).closest(self.$triggerButton).length === 0 &&
                        $(e.target).closest(self.$search).length === 0) {
                        self._hide(self);
                    }
                } else {
                    if ($(e.target).closest(self.$trigger).length === 0) {
                        self._hide(self);
                    }
                }
            });

            self.$trigger.addClass(self.namespace + '-active');
            self.opened = true;

            if (self.options.ajax.loadMore === true) {
                self._list.loadMore(self);
            }
            return self;
        },
        _hide: function() {
            var self = this;
            $(document).off('click.selective');

            self.$trigger.removeClass(self.namespace + '-active');
            self.opened = false;

            if (self.options.ajax.loadMore === true) {
                self._list.loadMoreRemove(self);
            }
            return self;
        },
        show: function() {
            this._trigger("beforeShow");
            this._show(this);
            this._trigger("afterShow");
            return this;
        },
        hide: function() {
            this._trigger("beforeHide");
            this._hide(this);
            this._trigger("afterHide");
            return this;
        },
        select: function($li) {
            this._list.select(this, $li);
            var data = $li.data('selective_index');
            if (this.options.buildFromHtml === true) {
                this._options.select(this, data);
                this.itemAdd($li, data.text());
            } else {
                this._options.add(this, data);
                this._options.select(this, this.getItem('option', this.$select, this.options.tpl.optionValue(data)));
                this.itemAdd(data);

            }
            return this;
        },
        unselect: function($li) {
            this._list.unselect(this, $li);
            return this;
        },
        setIndex: function(obj, index) {
            obj.data('selective_index', index);
            return this;
        },
        getItem: function(type, $list, index) {
            var $items = $list.children(type),
                position = '';
            for (var i = 0; i < $items.length; i++) {
                if (this.options.tpl.optionValue($items.eq(i).data('selective_index')) === index) {
                    position = i;
                }
            }
            return position === '' ? undefined : $items.eq(position);
        },
        itemAdd: function(data, content) {
            this._trigger("beforeItemAdd");
            this._items.add(this, data, content);
            this._trigger("afterItemAdd");
            return this;
        },
        itemRemove: function($li) {
            this._trigger("beforeItemRemove");
            this._items.remove(this, $li);
            this._trigger("afterItemRemove");
            return this;
        },
        optionAdd: function(data) {
            this._options.add(this, data);
            return this;
        },
        optionRemove: function(opt) {
            this._options.remove(this, opt);
            return this;
        },
        update: function(data) {
            this.$list.empty();
            this.page = 1;
            if (data !== null) {
                this._list.build(this, data);
            } else {
                this._list.build(this);
            }
            return this;
        }
    };

    // Collection method.
    $.fn.selective = function(options) {
        if (typeof options === 'string') {
            var method = options;
            var method_arguments = Array.prototype.slice.call(arguments, 1);

            if (/^\_/.test(method)) {
                return false;
            } else if ((/^(get)$/.test(method))) {
                var api = this.first().data('selective');
                if (api && typeof api[method] === 'function') {
                    return api[method].apply(api, method_arguments);
                }
            } else {
                return this.each(function() {
                    var api = $.data(this, 'selective');
                    if (api && typeof api[method] === 'function') {
                        api[method].apply(api, method_arguments);
                    }
                });
            }
        } else {
            return this.each(function() {
                if (!$.data(this, 'selective')) {
                    $.data(this, 'selective', new Selective(this, options));
                }
            });
        }
    };
}(window, document, jQuery));
