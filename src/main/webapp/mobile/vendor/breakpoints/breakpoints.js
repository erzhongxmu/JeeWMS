/*! breakpoints.js - v0.4.2 - 2015-04-22
 * https://github.com/amazingSurge/breakpoints.js
 * Copyright (c) 2015 amazingSurge; Licensed GPL */
(function(document, window, undefined) {
    "use strict";

    var Breakpoints = window.Breakpoints = function() {
        Breakpoints.define.apply(Breakpoints, arguments);
    };

    function each(obj, fn) {
        var continues;

        for (var i in obj) {
            continues = fn(i, obj[i]);
            if (continues === false) {
                break; //allow early exit
            }
        }
    }

    function isFunction(obj) {
        return typeof obj == 'function' || false;
    }

    function extend(obj, source) {
        for (var property in source) {
            obj[property] = source[property];
        }
        return obj;
    }

    Breakpoints.defaults = {
        // Extra small devices (phones)
        xs: {
            min: 0,
            max: 767
        },
        // Small devices (tablets)
        sm: {
            min: 768,
            max: 991
        },
        // Medium devices (desktops)
        md: {
            min: 992,
            max: 1199
        },
        // Large devices (large desktops)
        lg: {
            min: 1200,
            max: Infinity
        }
    };

    var MediaBuilder = Breakpoints.mediaBuilder = {
        min: function(min, unit) {
            return '(min-width: ' + min + unit + ')';
        },
        max: function(max, unit) {
            return '(max-width: ' + max + unit + ')';
        },
        between: function(min, max, unit) {
            return '(min-width: ' + min + unit + ') and (max-width: ' + max + unit + ')';
        },
        get: function(min, max, unit) {
            if (!unit) {
                unit = 'px';
            }
            if (min === 0) {
                return this.max(max, unit);
            }
            if (max === Infinity) {
                return this.min(min, unit);
            }
            return this.between(min, max, unit);
        }
    };

    var Callbacks = function() {
        var list = [];

        return {
            length: 0,
            add: function(fn, data, one) {
                list.push({
                    fn: fn,
                    data: data || {},
                    one: one || 0
                });

                this.length++;
            },
            remove: function(fn) {
                for (var i = 0; i < list.length; i++) {
                    if (list[i].fn === fn) {
                        list.splice(i, 1);
                        this.length--;
                        i--;
                    }
                }
            },
            empty: function() {
                list = [];
                this.length = 0;
            },
            call: function(caller, i, fn) {
                if (!i) {
                    i = this.length - 1;
                }
                var callback = list[i];

                if (isFunction(fn)) {
                    fn.call(this, caller, callback, i);
                } else {
                    if (isFunction(callback.fn)) {
                        callback.fn.call(caller || window, callback.data);
                    }
                }

                if (callback.one) {
                    delete list[i];
                    this.length--;
                }
            },
            fire: function(caller, fn) {
                for (var i in list) {
                    this.call(caller, i, fn);
                }
            }
        };
    };

    var ChangeEvent = {
        current: null,
        callbacks: new Callbacks(),
        trigger: function(size) {
            var previous = this.current;
            this.current = size;
            this.callbacks.fire(size, function(caller, callback) {
                if (isFunction(callback.fn)) {
                    callback.fn.call({
                        current: size,
                        previous: previous
                    }, callback.data);
                }
            });
        },
        one: function(data, fn) {
            return this.on(data, fn, 1);
        },
        on: function(data, fn, /*INTERNAL*/ one) {
            if (fn == null && isFunction(data)) {
                fn = data;
                data = undefined;
            }
            if (!isFunction(fn)) {
                return this;
            }
            this.callbacks.add(fn, data, one);
        },
        off: function(fn) {
            if (fn == null) {
                this.callbacks.empty();
            }
        }
    };

    var MediaQuery = Breakpoints.mediaQuery = function(name, media) {
        this.name = name;
        this.media = media;

        this.initialize.apply(this);
    }

    MediaQuery.prototype = {
        constructor: MediaQuery,
        initialize: function() {
            this.callbacks = {
                enter: new Callbacks(),
                leave: new Callbacks()
            };

            this.mql = (window.matchMedia && window.matchMedia(this.media)) || {
                matches: false,
                media: this.media,
                addListener: function() {},
                removeListener: function() {}
            };

            var self = this;
            this.mqlListener = function(mql) {
                var type = (mql.matches && 'enter') || 'leave';

                self.callbacks[type].fire(self);
            };
            this.mql.addListener(this.mqlListener);
        },

        on: function(types, data, fn, /*INTERNAL*/ one) {
            var type;
            if (typeof types === "object") {
                for (type in types) {
                    this.on(type, data, types[type], one);
                }
                return this;
            }

            if (fn == null && isFunction(data)) {
                fn = data;
                data = undefined;
            }

            if (!isFunction(fn)) {
                return this;
            }

            if (types in this.callbacks) {
                this.callbacks[types].add(fn, data, one);
                if (this.isMatched() && types === 'enter') {
                    this.callbacks[types].call(this);
                }
            }

            return this;
        },

        one: function(types, data, fn) {
            return this.on(types, data, fn, 1);
        },

        off: function(types, fn) {
            var type;
            if (typeof types === "object") {
                for (type in types) {
                    this.off(type, types[type]);
                }
                return this;
            }

            if (types == null) {
                this.callbacks.enter.empty();
                this.callbacks.leave.empty();
            }
            if (types in this.callbacks) {
                if (fn) {
                    this.callbacks[types].remove(fn);
                } else {
                    this.callbacks[types].empty();
                }
            }

            return this;
        },

        isMatched: function() {
            return this.mql.matches;
        },

        destory: function() {
            this.off();
        }
    };
    var Size = function(name, min, max, unit) {
        this.name = name;
        this.min = min ? min : 0;
        this.max = max ? max : Infinity;

        this.media = MediaBuilder.get(this.min, this.max, unit);

        this.initialize.apply(this);

        var self = this;
        this.changeListener = function() {
            if (self.isMatched()) {
                ChangeEvent.trigger(self);
            }
        };
        if (this.isMatched()) {
            ChangeEvent.current = this;
        }
        this.mql.addListener(this.changeListener);
    };

    Size.prototype = MediaQuery.prototype;
    Size.prototype.constructor = Size;

    extend(Size.prototype, {
        destory: function() {
            this.off();
            this.mql.removeListener(this.changeHander);
        }
    });

    var UnionSize = function(names) {
        this.name = names;
        this.sizes = [];

        var self = this;

        var media = [];
        each(names.split(' '), function(i, name) {
            var size = Breakpoints.get(name);
            if (size) {
                self.sizes.push(size);
                media.push(size.media);
            }
        });

        this.media = media.join(',');

        this.initialize.apply(this);
    };

    UnionSize.prototype = MediaQuery.prototype;
    UnionSize.prototype.constructor = UnionSize;

    var sizes = {};
    var unionSizes = {};


    Breakpoints = extend(Breakpoints, {
        defined: false,
        define: function(values, options) {
            if (this.defined) {
                this.destory();
            }

            if (!values) {
                values = Breakpoints.defaults;
            }

            this.options = extend(options || {}, {
                unit: 'px'
            });

            for (var size in values) {
                this.set(size, values[size].min, values[size].max, this.options.unit);
            }

            this.defined = true;
        },

        destory: function() {
            each(sizes, function(name, size) {
                size.destory();
            });
            sizes = {};
            ChangeEvent.current = null;
        },

        is: function(size) {
            var breakpoint = this.get(size);
            if (!breakpoint) {
                return null;
            }

            return breakpoint.isMatched();
        },

        /* get all size name */
        all: function() {
            var names = [];
            each(sizes, function(name) {
                names.push(name);
            });
            return names;
        },

        set: function(name, min, max, unit) {
            var size = this.get(name);
            if (size) {
                size.destory();
            }

            sizes[name] = new Size(name, min || null, max || null, unit || null)
            return sizes[name];
        },

        get: function(size) {
            if (sizes.hasOwnProperty(size)) {
                return sizes[size];
            }

            return null;
        },

        getUnion: function(sizes) {
            if (unionSizes.hasOwnProperty(sizes)) {
                return unionSizes[sizes];
            }

            unionSizes[sizes] = new UnionSize(sizes)

            return unionSizes[sizes];
        },

        getMin: function(size) {
            var obj = this.get(size);
            if (obj) {
                return obj.min;
            }
            return null;
        },

        getMax: function(size) {
            var obj = this.get(size);
            if (obj) {
                return obj.max;
            }
            return null;
        },

        current: function() {
            return ChangeEvent.current;
        },

        getMedia: function(size) {
            var obj = this.get(size);
            if (obj) {
                return obj.media;
            }
            return null;
        },

        on: function(sizes, types, data, fn, /*INTERNAL*/ one) {
            if (sizes === 'change') {
                fn = data;
                data = types;
                return ChangeEvent.on(data, fn, one);
            }
            if (sizes.indexOf(' ')) {
                var union = this.getUnion(sizes);

                if (union) {
                    union.on(types, data, fn, one);
                }
            } else {
                var size = this.get(sizes);

                if (size) {
                    size.on(types, data, fn, one);
                }
            }

            return this;
        },

        one: function(sizes, types, data, fn) {
            return this.on(sizes, types, data, fn, 1);
        },

        off: function(sizes, types, fn) {
            if (sizes === 'change') {
                return ChangeEvent.off(types);
            }

            if (sizes.indexOf(' ')) {
                var union = this.getUnion(sizes);

                if (union) {
                    union.off(types, fn);
                }
            } else {
                var size = this.get(sizes);

                if (size) {
                    size.off(types, fn);
                }
            }

            return this;
        }
    });

})(document, window);
