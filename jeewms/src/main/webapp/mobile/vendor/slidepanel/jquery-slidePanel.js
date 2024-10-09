/*! jQuery slidePanel - v0.2.2 - 2015-10-14
 * https://github.com/amazingSurge/jquery-slidePanel
 * Copyright (c) 2015 amazingSurge; Licensed GPL */
(function($, document, window, undefined) {
    "use strict";

    var SlidePanel = $.slidePanel = function() {
        SlidePanel.show.apply(this, arguments);
    };

    if (!Date.now) {
        Date.now = function() {
            return new Date().getTime();
        };
    }

    function getTime() {
        if (typeof window.performance !== 'undefined' && window.performance.now) {
            return window.performance.now();
        } else {
            return Date.now();
        }
    }

    var vendors = ['webkit', 'moz'];
    for (var i = 0; i < vendors.length && !window.requestAnimationFrame; ++i) {
        var vp = vendors[i];
        window.requestAnimationFrame = window[vp + 'RequestAnimationFrame'];
        window.cancelAnimationFrame = (window[vp + 'CancelAnimationFrame'] || window[vp + 'CancelRequestAnimationFrame']);
    }
    if (/iP(ad|hone|od).*OS (6|7|8)/.test(window.navigator.userAgent) || !window.requestAnimationFrame || !window.cancelAnimationFrame) {
        var lastTime = 0;
        window.requestAnimationFrame = function(callback) {
            var now = getTime();
            var nextTime = Math.max(lastTime + 16, now);
            return setTimeout(function() {
                    callback(lastTime = nextTime);
                },
                nextTime - now);
        };
        window.cancelAnimationFrame = clearTimeout;
    }

    var Support = (function() {
        var style = $('<support>').get(0).style,
            prefixes = ['webkit', 'Moz', 'O', 'ms'],
            events = {
                transition: {
                    end: {
                        WebkitTransition: 'webkitTransitionEnd',
                        MozTransition: 'transitionend',
                        OTransition: 'oTransitionEnd',
                        transition: 'transitionend'
                    }
                },
                animation: {
                    end: {
                        WebkitAnimation: 'webkitAnimationEnd',
                        MozAnimation: 'animationend',
                        OAnimation: 'oAnimationEnd',
                        animation: 'animationend'
                    }
                }
            },
            tests = {
                csstransforms: function() {
                    return !!test('transform');
                },
                csstransforms3d: function() {
                    return !!test('perspective');
                },
                csstransitions: function() {
                    return !!test('transition');
                },
                cssanimations: function() {
                    return !!test('animation');
                }
            };

        function test(property, prefixed) {
            var result = false,
                upper = property.charAt(0).toUpperCase() + property.slice(1);

            if (style[property] !== undefined) {
                result = property;
            }
            if (!result) {
                $.each(prefixes, function(i, prefix) {
                    if (style[prefix + upper] !== undefined) {
                        result = '-' + prefix.toLowerCase() + '-' + upper;
                        return false;
                    }
                });
            }

            if (prefixed) {
                return result;
            }
            if (result) {
                return true;
            } else {
                return false;
            }
        }

        function prefixed(property) {
            return test(property, true);
        }
        var support = {};
        if (tests.csstransitions()) {
            /* jshint -W053 */
            support.transition = new String(prefixed('transition'))
            support.transition.end = events.transition.end[support.transition];
        }

        if (tests.cssanimations()) {
            /* jshint -W053 */
            support.animation = new String(prefixed('animation'))
            support.animation.end = events.animation.end[support.animation];
        }

        if (tests.csstransforms()) {
            /* jshint -W053 */
            support.transform = new String(prefixed('transform'));
            support.transform3d = tests.csstransforms3d();
        }

        if (('ontouchstart' in window) || window.DocumentTouch && document instanceof window.DocumentTouch) {
            support.touch = true;
        } else {
            support.touch = false;
        }

        if (window.PointerEvent || window.MSPointerEvent) {
            support.pointer = true;
        } else {
            support.pointer = false;
        }

        support.prefixPointerEvent = function(pointerEvent) {
            return window.MSPointerEvent ?
                'MSPointer' + pointerEvent.charAt(9).toUpperCase() + pointerEvent.substr(10) :
                pointerEvent;
        }

        return support;
    })();

    function isPercentage(n) {
        return typeof n === 'string' && n.indexOf('%') != -1;
    }

    function isPx(n) {
        return typeof n === 'string' && n.indexOf('px') != -1;
    }

    function convertMatrixToArray(value) {
        if (value && (value.substr(0, 6) == "matrix")) {
            return value.replace(/^.*\((.*)\)$/g, "$1").replace(/px/g, '').split(/, +/);
        }
        return false;
    }

    function getHashCode(object) {
        if (typeof object !== 'string') {
            object = JSON.stringify(object);
        }

        var hash = 0,
            i, chr, len;
        if (object.length === 0) return hash;
        for (i = 0, len = object.length; i < len; i++) {
            chr = object.charCodeAt(i);
            hash = ((hash << 5) - hash) + chr;
            hash |= 0; // Convert to 32bit integer
        }

        return hash;
    }

    function easingBezier(mX1, mY1, mX2, mY2) {
        function a(aA1, aA2) {
            return 1.0 - 3.0 * aA2 + 3.0 * aA1;
        }

        function b(aA1, aA2) {
            return 3.0 * aA2 - 6.0 * aA1;
        }

        function c(aA1) {
            return 3.0 * aA1;
        }

        // Returns x(t) given t, x1, and x2, or y(t) given t, y1, and y2.
        function calcBezier(aT, aA1, aA2) {
            return ((a(aA1, aA2) * aT + b(aA1, aA2)) * aT + c(aA1)) * aT;
        }

        // Returns dx/dt given t, x1, and x2, or dy/dt given t, y1, and y2.
        function getSlope(aT, aA1, aA2) {
            return 3.0 * a(aA1, aA2) * aT * aT + 2.0 * b(aA1, aA2) * aT + c(aA1);
        }

        function getTForX(aX) {
            // Newton raphson iteration
            var aGuessT = aX;
            for (var i = 0; i < 4; ++i) {
                var currentSlope = getSlope(aGuessT, mX1, mX2);
                if (currentSlope === 0.0) return aGuessT;
                var currentX = calcBezier(aGuessT, mX1, mX2) - aX;
                aGuessT -= currentX / currentSlope;
            }
            return aGuessT;
        }

        if (mX1 === mY1 && mX2 === mY2) {
            return {
                css: 'linear',
                fn: function(aX) {
                    return aX;
                }
            };
        } else {
            return {
                css: 'cubic-bezier(' + mX1 + ',' + mY1 + ',' + mX2 + ',' + mY2 + ')',
                fn: function(aX) {
                    return calcBezier(getTForX(aX), mY1, mY2);
                }
            }
        }
    }

    var Easings = {
        'ease': easingBezier(0.25, 0.1, 0.25, 1.0),
        'linear': easingBezier(0.00, 0.0, 1.00, 1.0),
        'ease-in': easingBezier(0.42, 0.0, 1.00, 1.0),
        'ease-out': easingBezier(0.00, 0.0, 0.58, 1.0),
        'ease-in-out': easingBezier(0.42, 0.0, 0.58, 1.0)
    };

    SlidePanel.options = {
        skin: null,

        classes: {
            base: 'slidePanel',
            show: 'slidePanel-show',
            loading: 'slidePanel-loading',
            content: 'slidePanel-content',
            dragging: 'slidePanel-dragging',
            willClose: 'slidePanel-will-close'
        },

        closeSelector: null,

        template: function(options) {
            return '<div class="' + options.classes.base + ' ' + options.classes.base + '-' + options.direction + '">' +
                '<div class="' + options.classes.content + '"></div>' +
                '</div>';
        },

        loading: {
            appendTo: 'panel', // body, panel
            template: function(options) {
                return '<div class="' + options.classes.loading + '"></div>';
            },
            showCallback: function(options) {
                this.$el.addClass(options.classes.loading + '-show');
            },
            hideCallback: function(options) {
                this.$el.removeClass(options.classes.loading + '-show');
            }
        },

        contentFilter: function(content, object) {
            return content;
        },

        useCssTransforms3d: true,
        useCssTransforms: true,
        useCssTransitions: true,

        dragTolerance: 150,

        mouseDragHandler: null,
        mouseDrag: true,
        touchDrag: true,
        pointerDrag: true,

        direction: 'right', // top, bottom, left, right
        duration: '500',
        easing: 'ease', // linear, ease-in, ease-out, ease-in-out

        // callbacks
        beforeLoad: $.noop, // Before loading
        afterLoad: $.noop, // After loading
        beforeShow: $.noop, // Before opening
        afterShow: $.noop, // After opening
        onChange: $.noop, // On changing
        beforeChange: $.noop, // Before changing
        beforeHide: $.noop, // Before closing
        afterHide: $.noop, // After closing
        beforeDrag: $.noop, // Before drag
        afterDrag: $.noop // After drag
    };

    // View
    function View() {
        return this.initialize.apply(this, Array.prototype.slice.call(arguments));
    }

    $.extend(View.prototype, {
        initialize: function(options) {
            this.options = options;
            this._instance = null;
            this._showed = false;
            this._isLoading = false;

            this.build();
        },

        setLength: function() {
            switch (this.options.direction) {
                case 'top':
                case 'bottom':
                    this._length = this.$panel.outerHeight();
                    break;
                case 'left':
                case 'right':
                    this._length = this.$panel.outerWidth();
                    break;
            }
        },

        build: function() {
            if (this._builded) return;

            var options = this.options;

            var html = options.template.call(this, options);
            var self = this;

            this.$panel = $(html).appendTo('body');
            if (options.skin) {
                this.$panel.addClass(options.skin);
            }
            this.$content = this.$panel.find('.' + this.options.classes.content);

            if (options.closeSelector) {
                this.$panel.on('click', options.closeSelector, function() {
                    self.hide();
                    return false;
                });
            }
            this.loading = new Loading(this);

            this.setLength();
            this.setPosition(this.getHidePosition());

            if (options.mouseDrag || options.touchDrag || options.pointerDrag) {
                this.drag = new Drag(this);
            }

            this._builded = true;
        },

        getHidePosition: function() {
            var options = this.options;

            if (options.useCssTransforms || options.useCssTransforms3d) {
                switch (options.direction) {
                    case 'top':
                    case 'left':
                        return '-100';
                    case 'bottom':
                    case 'right':
                        return '100';
                }
            } else {
                switch (options.direction) {
                    case 'top':
                    case 'bottom':
                        return parseFloat(-(this._length / $(window).height()) * 100, 10);
                    case 'left':
                    case 'right':
                        return parseFloat(-(this._length / $(window).width()) * 100, 10);
                }
            }
        },

        empty: function() {
            this._instance = null;
            this.$content.empty();
        },

        load: function(object) {
            var self = this;
            var options = object.options;
            var previous = this._instance;

            _SlidePanel.trigger(this, 'beforeLoad', object);
            this.empty();

            function setContent(content) {
                content = options.contentFilter.call(this, content, object);
                self.$content.html(content);
                self.hideLoading();

                self._instance = object;

                _SlidePanel.trigger(self, 'afterLoad', object);
            }

            if (object.content) {
                setContent(object.content);
            } else if (object.url) {
                this.showLoading();

                $.ajax(object.url, object.settings || {}).done(function(data) {
                    setContent(data);
                });
            } else {
                setContent('');
            }
        },

        showLoading: function() {
            var self = this;
            this.loading.show(function() {
                self._isLoading = true;
            });
        },

        hideLoading: function() {
            var self = this;
            this.loading.hide(function() {
                self._isLoading = false;
            });
        },

        show: function(callback) {
            this.build();

            _SlidePanel.enter('show');
            _SlidePanel.trigger(this, 'beforeShow');

            $('html').addClass(this.options.classes.base + '-html');
            this.$panel.addClass(this.options.classes.show);

            var self = this;
            Animate.do(this, 0, function() {
                self._showed = true;
                _SlidePanel.trigger(self, 'afterShow');

                if ($.isFunction(callback)) {
                    callback.call(self);
                }
            });
        },

        change: function(object) {
            _SlidePanel.trigger(this, 'beforeShow');

            _SlidePanel.trigger(this, 'onChange', object, this._instance);

            this.load(object);

            _SlidePanel.trigger(this, 'afterShow');
        },

        revert: function(callback) {
            var self = this;
            Animate.do(this, 0, function() {
                if ($.isFunction(callback)) {
                    callback.call(self);
                }
            });
        },

        hide: function(callback) {
            _SlidePanel.leave('show');
            _SlidePanel.trigger(this, 'beforeHide');

            var self = this;

            Animate.do(this, this.getHidePosition(), function() {
                self.$panel.removeClass(self.options.classes.show);
                self._showed = false;
                self._instance = null;

                if (_SlidePanel._current === self) {
                    _SlidePanel._current = null;
                }

                if (!_SlidePanel.is('show')) {
                    $('html').removeClass(self.options.classes.base + '-html');
                }

                if ($.isFunction(callback)) {
                    callback.call(self);
                }

                _SlidePanel.trigger(self, 'afterHide');
            });
        },

        makePositionStyle: function(value) {
            var property, x = '0',
                y = '0';

            if (!isPercentage(value) && !isPx(value)) {
                value = value + '%';
            }

            if (this.options.useCssTransforms && Support.transform) {
                if (this.options.direction === 'left' || this.options.direction === 'right') {
                    x = value;
                } else {
                    y = value;
                }

                property = Support.transform.toString();

                if (this.options.useCssTransforms3d && Support.transform3d) {
                    value = "translate3d(" + x + "," + y + ",0)";
                } else {
                    value = "translate(" + x + "," + y + ")";
                }
            } else {
                property = this.options.direction;
            }
            var temp = {};
            temp[property] = value;
            return temp;
        },

        getPosition: function(px) {
            var value;

            if (this.options.useCssTransforms && Support.transform) {
                value = convertMatrixToArray(this.$panel.css(Support.transform));
                if (!value) {
                    return 0;
                }

                if (this.options.direction === 'left' || this.options.direction === 'right') {
                    value = value[12] || value[4];

                } else {
                    value = value[13] || value[5];
                }
            } else {
                value = this.$panel.css(this.options.direction);

                value = parseFloat(value.replace('px', ''));
            }

            if (px !== true) {
                value = (value / this._length) * 100;
            }

            return parseFloat(value, 10);
        },

        setPosition: function(value) {
            var style = this.makePositionStyle(value);
            this.$panel.css(style);
        }
    });

    // Loading
    function Loading() {
        return this.initialize.apply(this, Array.prototype.slice.call(arguments));
    };

    $.extend(Loading.prototype, {
        initialize: function(view) {
            this._view = view;
            this.build();
        },

        build: function() {
            if (this._builded) return;

            var options = this._view.options;
            var html = options.loading.template.call(this, options);
            this.$el = $(html);

            switch (options.loading.appendTo) {
                case 'panel':
                    this.$el.appendTo(this._view.$panel);
                    break;
                case 'body':
                    this.$el.appendTo('body');
                    break;
                default:
                    this.$el.appendTo(options.loading.appendTo);
            }

            this._builded = true;
        },

        show: function(callback) {
            this.build();
            var options = this._view.options;
            options.loading.showCallback.call(this, options);

            if ($.isFunction(callback)) {
                callback.call(this);
            }
        },

        hide: function(callback) {
            var options = this._view.options;
            options.loading.hideCallback.call(this, options);

            if ($.isFunction(callback)) {
                callback.call(this);
            }
        }
    });

    var Animate = {
        prepareTransition: function($el, property, duration, easing, delay) {
            var temp = [];
            if (property) {
                temp.push(property);
            }
            if (duration) {
                if ($.isNumeric(duration)) {
                    duration = duration + 'ms';
                }
                temp.push(duration);
            }
            if (easing) {
                temp.push(easing);
            } else {
                temp.push(this.easing.css);
            }
            if (delay) {
                temp.push(delay);
            }
            $el.css(Support.transition, temp.join(' '));
        },
        do: function(view, value, callback) {
            _SlidePanel.enter('animating');

            var duration = view.options.duration,
                easing = view.options.easing || 'ease';

            var self = this,
                style = view.makePositionStyle(value);
            for (var property in style) {
                break;
            }

            if (view.options.useCssTransitions && Support.transition) {
                setTimeout(function() {
                    self.prepareTransition(view.$panel, property, duration, easing);
                }, 20);

                view.$panel.one(Support.transition.end, function() {
                    if ($.isFunction(callback)) {
                        callback();
                    }

                    view.$panel.css(Support.transition, '');

                    _SlidePanel.leave('animating');
                });
                setTimeout(function() {
                    view.setPosition(value);
                }, 20);
            } else {
                var startTime = getTime();
                var start = view.getPosition();
                var end = value;

                var run = function(time) {
                    var percent = (time - startTime) / view.options.duration;

                    if (percent > 1) {
                        percent = 1;
                    }

                    percent = Easings[easing].fn(percent);

                    var current = parseFloat(start + percent * (end - start), 10);

                    view.setPosition(current);

                    if (percent === 1) {
                        window.cancelAnimationFrame(self._frameId);
                        self._frameId = null;

                        if ($.isFunction(callback)) {
                            callback();
                        }

                        _SlidePanel.leave('animating');
                    } else {
                        self._frameId = window.requestAnimationFrame(run);
                    }
                };

                self._frameId = window.requestAnimationFrame(run);
            }
        }
    }

    // Drag
    function Drag() {
        return this.initialize.apply(this, Array.prototype.slice.call(arguments));
    }

    $.extend(Drag.prototype, {
        initialize: function(view) {
            this._view = view;
            this.options = view.options;
            this._drag = {
                time: null,
                pointer: null
            };

            this.bindEvents();
        },
        bindEvents: function() {
            var self = this;
            var options = this.options,
                $panel = this._view.$panel;

            if (options.mouseDrag) {
                $panel.on(_SlidePanel.eventName('mousedown'), $.proxy(this.onDragStart, this));
                $panel.on(_SlidePanel.eventName('dragstart selectstart'), function() {
                    if (options.mouseDragHandler) {
                        if (!$(event.target).is(options.mouseDragHandler) && !($(event.target).parents(options.mouseDragHandler).length > 0)) {
                            return;
                        }
                    }
                    return false
                });
            }

            if (options.touchDrag && Support.touch) {
                $panel.on(_SlidePanel.eventName('touchstart'), $.proxy(this.onDragStart, this));
                $panel.on(_SlidePanel.eventName('touchcancel'), $.proxy(this.onDragEnd, this));
            }

            if (options.pointerDrag && Support.pointer) {
                $panel.on(_SlidePanel.eventName(Support.prefixPointerEvent('pointerdown')), $.proxy(this.onDragStart, this));
                $panel.on(_SlidePanel.eventName(Support.prefixPointerEvent('pointercancel')), $.proxy(this.onDragEnd, this));
            }
        },

        /**
         * Handles `touchstart` and `mousedown` events.
         */
        onDragStart: function(event) {
            var self = this;

            if (event.which === 3) {
                return;
            }

            var options = this.options;

            this._view.$panel.addClass(this.options.classes.dragging);

            this._position = this._view.getPosition(true);

            this._drag.time = new Date().getTime();
            this._drag.pointer = this.pointer(event);

            var callback = function() {
                _SlidePanel.enter('dragging');
                _SlidePanel.trigger(self._view, 'beforeDrag');
            }

            if (options.mouseDrag) {
                if (options.mouseDragHandler) {
                    if (!$(event.target).is(options.mouseDragHandler) && !($(event.target).parents(options.mouseDragHandler).length > 0)) {
                        return;
                    }
                }

                $(document).on(_SlidePanel.eventName('mouseup'), $.proxy(this.onDragEnd, this));

                $(document).one(_SlidePanel.eventName('mousemove'), $.proxy(function() {
                    $(document).on(_SlidePanel.eventName('mousemove'), $.proxy(this.onDragMove, this));

                    callback();
                }, this));
            }

            if (options.touchDrag && Support.touch) {
                $(document).on(_SlidePanel.eventName('touchend'), $.proxy(this.onDragEnd, this));

                $(document).one(_SlidePanel.eventName('touchmove'), $.proxy(function() {
                    $(document).on(_SlidePanel.eventName('touchmove'), $.proxy(this.onDragMove, this));

                    callback();
                }, this));
            }

            if (options.pointerDrag && Support.pointer) {
                $(document).on(_SlidePanel.eventName(Support.prefixPointerEvent('pointerup')), $.proxy(this.onDragEnd, this));

                $(document).one(_SlidePanel.eventName(Support.prefixPointerEvent('pointermove')), $.proxy(function() {
                    $(document).on(_SlidePanel.eventName(Support.prefixPointerEvent('pointermove')), $.proxy(this.onDragMove, this));

                    callback();
                }, this));
            }

            $(document).on(_SlidePanel.eventName('blur'), $.proxy(this.onDragEnd, this));

            event.preventDefault();
        },

        /**
         * Handles the `touchmove` and `mousemove` events.
         */
        onDragMove: function(event) {
            var distance = this.distance(this._drag.pointer, this.pointer(event));

            if (!_SlidePanel.is('dragging')) {
                return;
            }

            if (Math.abs(distance) > this.options.dragTolerance) {
                if (this._willClose !== true) {
                    this._willClose = true;
                    this._view.$panel.addClass(this.options.classes.willClose);
                }
            } else {
                if (this._willClose !== false) {
                    this._willClose = false;
                    this._view.$panel.removeClass(this.options.classes.willClose);
                }
            }

            if (!_SlidePanel.is('dragging')) {
                return;
            }

            event.preventDefault();
            this.move(distance);
        },

        /**
         * Handles the `touchend` and `mouseup` events.
         */
        onDragEnd: function(event) {
            var distance = this.distance(this._drag.pointer, this.pointer(event));

            $(document).off(_SlidePanel.eventName('mousemove mouseup touchmove touchend pointermove pointerup MSPointerMove MSPointerUp blur'));

            this._view.$panel.removeClass(this.options.classes.dragging);

            if (this._willClose === true) {
                this._willClose = false;
                this._view.$panel.removeClass(this.options.classes.willClose);
            }

            if (!_SlidePanel.is('dragging')) {
                return;
            }

            _SlidePanel.leave('dragging');

            _SlidePanel.trigger(this._view, 'afterDrag');

            if (Math.abs(distance) < this.options.dragTolerance) {
                this._view.revert();
            } else {
                _SlidePanel.hide();
            }
        },

        /**
         * Gets unified pointer coordinates from event.
         * @returns {Object} - Contains `x` and `y` coordinates of current pointer position.
         */
        pointer: function(event) {
            var result = {
                x: null,
                y: null
            };

            event = event.originalEvent || event || window.event;

            event = event.touches && event.touches.length ?
                event.touches[0] : event.changedTouches && event.changedTouches.length ?
                event.changedTouches[0] : event;

            if (event.pageX) {
                result.x = event.pageX;
                result.y = event.pageY;
            } else {
                result.x = event.clientX;
                result.y = event.clientY;
            }

            return result;
        },

        /**distance
         * Gets the distance of two pointer.
         */
        distance: function(first, second) {
            var d = this.options.direction;
            if (d === 'left' || d === 'right') {
                return second.x - first.x;
            } else {
                return second.y - first.y;
            }
        },

        move: function(value) {
            var position = this._position + value;

            if (this.options.direction === 'right' || this.options.direction === 'bottom') {
                if (position < 0) {
                    return;
                }
            } else {
                if (position > 0) {
                    return;
                }
            }

            if (!this.options.useCssTransforms && !this.options.useCssTransforms3d) {
                if (this.options.direction === 'right' || this.options.direction === 'bottom') {
                    position = -position;
                }
            }

            this._view.setPosition(position + 'px');
        }
    });

    // Instance
    function Instance() {
        return this.initialize.apply(this, Array.prototype.slice.call(arguments));
    };

    $.extend(Instance.prototype, {
        initialize: function(object) {
            var options = arguments[1] || {};

            if (typeof object === 'string') {
                object = {
                    url: object
                };
            } else if (object && object.nodeType == 1) {
                var $element = $(object);

                object = {
                    url: $element.attr('href'),
                    settings: $element.data('settings') || {},
                    options: $element.data() || {}
                }
            }

            if (object && object.options) {
                object.options = $.extend(true, options, object.options);
            } else {
                object.options = options;
            }

            object.options = $.extend(true, {}, SlidePanel.options, object.options);

            $.extend(this, object);

            return this;
        }
    });

    var _SlidePanel = {
        // Current state information.
        _states: {},
        _views: {},
        _current: null,

        /**
         * Checks whether the carousel is in a specific state or not.
         */
        is: function(state) {
            return this._states[state] && this._states[state] > 0;
        },

        /**
         * Enters a state.
         */
        enter: function(state) {
            if (this._states[state] === undefined) {
                this._states[state] = 0;
            }

            this._states[state]++;
        },

        /**
         * Leaves a state.
         */
        leave: function(state) {
            this._states[state]--;
        },

        trigger: function(view, event) {
            var method_arguments = Array.prototype.slice.call(arguments, 2),
                data = [view].concat(method_arguments);

            // event
            $(document).trigger('slidePanel::' + event, data);
            if ($.isFunction(view.options[event])) {
                view.options[event].apply(view, method_arguments);
            }
        },

        eventName: function(events) {
            if (typeof events !== 'string' || events === '') {
                return '.slidepanel';
            }
            events = events.split(' ');

            var length = events.length;
            for (var i = 0; i < length; i++) {
                events[i] = events[i] + '.slidepanel';
            }
            return events.join(' ');
        },

        show: function(object) {
            if (!(object instanceof Instance)) {
                switch (arguments.length) {
                    case 0:
                        object = new Instance();
                        break;
                    case 1:
                        object = new Instance(arguments[0]);
                        break;
                    case 2:
                        object = new Instance(arguments[0], arguments[1]);
                        break;
                }
            }

            var view = this.getView(object.options);
            var self = this;
            var callback = function() {
                view.show();
                view.load(object);
                self._current = view;
            }
            if (null !== this._current) {
                if (view === this._current) {
                    this._current.change(object);
                } else {
                    this._current.hide(callback);
                }
            } else {
                callback();
            }
        },

        getView: function(options) {
            var code = getHashCode(options);

            if (this._views.hasOwnProperty(code)) {
                return this._views[code];
            }

            return this._views[code] = new View(options);
        },

        hide: function(object) {
            if (object) {
                var view = this.getView(object.options);
                view.hide();
            } else {
                if (this._current !== null) {
                    var self = this;
                    this._current.hide();
                }
            }
        }
    };

    $.extend(SlidePanel, {
        is: function(state) {
            return _SlidePanel.is(state);
        },

        show: function(object, options) {
            _SlidePanel.show.apply(_SlidePanel, arguments);
            return this;
        },

        hide: function() {
            _SlidePanel.hide.apply(_SlidePanel, arguments);
            return this;
        }
    });

    $.fn.slidePanel = function(options) {
        if (typeof options === 'string') {
            var method = options;
            var method_arguments = Array.prototype.slice.call(arguments, 1);

            return this.each(function() {
                var instance = $.data(this, 'slidePanel');

                if (!(instance instanceof Instance)) {
                    instance = new Instance(this, method_arguments);
                    $.data(this, 'slidePanel', instance);
                }

                switch (method) {
                    case 'hide':
                        _SlidePanel.hide(instance);
                        break;
                    case 'show':
                        _SlidePanel.show(instance);
                        break;
                }
            });
        } else {
            return this.each(function() {
                if (!$.data(this, 'slidePanel')) {
                    $.data(this, 'slidePanel', new Instance(this, options));

                    $(this).on('click', function(e) {
                        var instance = $.data(this, 'slidePanel');
                        _SlidePanel.show(instance);

                        e.preventDefault();
                        e.stopPropagation();
                    });
                }
            });
        }
    };

})(jQuery, document, window);
