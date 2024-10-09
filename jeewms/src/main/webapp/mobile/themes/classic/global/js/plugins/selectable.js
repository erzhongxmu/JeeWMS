/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
    'use strict';

    var pluginName = 'asSelectable';

    var Plugin = $[pluginName] = function (element, options) {
        this.element = element;
        this.$element = $(element);
        this.options = $.extend({}, Plugin.defaults, options, this.$element.data());

        this.init();
    };

    Plugin.defaults = {
        allSelector: '.selectable-all',
        itemSelector: '.selectable-item',
        rowSelector: 'tr',
        rowSelectable: false,
        rowActiveClass: 'active',
        onChange: null
    };

    Plugin.prototype = {
        constructor: Plugin,
        init: function () {
            var self = this;
            var options = this.options;

            self.$element.on('change', options.allSelector, function () {
                var value = $(this).prop("checked");
                self.getItems().each(function () {
                    var $one = $(this);
                    $one.prop("checked", value).trigger('change', [true]);
                    self.selectRow($one, value);
                });
            });

            self.$element.on('click', options.itemSelector, function (e) {
                var $one = $(this),
                    value = $one.prop("checked");
                self.selectRow($one, value);
                e.stopPropagation();
            });

            self.$element.on('change', options.itemSelector, function () {
                var $all = self.$element.find(options.allSelector),
                    $row = self.getItems(),
                    total = $row.length,
                    checked = self.getSelected().length;

                if (total === checked) {
                    $all.prop('checked', true);
                } else {
                    $all.prop('checked', false);
                }

                self._trigger('change', checked);

                if (typeof options.callback === 'function') {
                    options.callback.call(this);
                }
            });

            if (options.rowSelectable) {
                self.$element.on('click', options.rowSelector, function (e) {
                    if ("checkbox" !== e.target.type && "button" !== e.target.type && "a" !== e.target.tagName.toLowerCase() && !$(e.target).parent("div.checkbox-custom").length) {
                        var $checkbox = $(options.itemSelector, this),
                            value = $checkbox.prop("checked");
                        $checkbox.prop("checked", !value);
                        self.selectRow($checkbox, !value);
                    }
                });
            }
        },

        selectRow: function (item, value) {
            if (value) {
                item.parents(this.options.rowSelector).addClass(this.options.rowActiveClass);
            } else {
                item.parents(this.options.rowSelector).removeClass(this.options.rowActiveClass);
            }
        },

        getItems: function () {
            return this.$element.find(this.options.itemSelector);
        },

        getSelected: function () {
            return this.getItems().filter(':checked');
        },

        _trigger: function (eventType) {
            var method_arguments = Array.prototype.slice.call(arguments, 1),
                data = [this].concat(method_arguments);

            // event
            this.$element.trigger(pluginName + '::' + eventType, data);

            // callback
            eventType = eventType.replace(/\b\w+\b/g, function (word) {
                return word.substring(0, 1).toUpperCase() + word.substring(1);
            });
            var onFunction = 'on' + eventType;
            if (typeof this.options[onFunction] === 'function') {
                this.options[onFunction].apply(this, method_arguments);
            }
        }
    };

    $.fn[pluginName] = function (options) {
        var method = options,
            api = this.first().data(pluginName),
            method_arguments = Array.prototype.slice.call(arguments, 1);

        if (typeof options === 'string') {
            if (/^_/.test(method)) {
                return false;
            } else if ((/^(get)/.test(method) && api && typeof api[method] === 'function')) {
                return api[method].apply(api, method_arguments);
            } else {
                return this.each(function () {
                    var api = $.data(this, pluginName);
                    if (api && typeof api[method] === 'function') {
                        api[method].apply(api, method_arguments);
                    }
                });
            }
        } else {
            return this.each(function () {
                if (!$.data(this, pluginName)) {
                    $.data(this, pluginName, new Plugin(this, options));
                }
            });
        }
    };
})(window, document, jQuery);
