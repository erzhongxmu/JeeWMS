/*! jQuery plugin - v0.1.1 - 2015-04-10
* https://github.com/amazingSurge/jquery-strength
* Copyright (c) 2015 amazingSurge; Licensed GPL */
var PasswordStrength = (function(){
  var MULTIPLE_NUMBERS_RE = /\d.*?\d.*?\d/;
  var MULTIPLE_SYMBOLS_RE = /[!@#$%^&*?_~].*?[!@#$%^&*?_~]/;
  var UPPERCASE_LOWERCASE_RE = /([a-z].*[A-Z])|([A-Z].*[a-z])/;
  var SYMBOL_RE = /[!@#\$%^&*?_~]/;

  function PasswordStrength() {
    this.username = null;
    this.password = null;
    this.score = 0;
    this.status = null;
  }

  PasswordStrength.fn = PasswordStrength.prototype;

  PasswordStrength.fn.test = function() {
    var score;
    this.score = score = 0;

    if (this.containInvalidMatches()) {
      this.status = "invalid";
    } else if (this.usesCommonWord()) {
      this.status = "invalid";
    } else {
      score += this.scoreFor("password_size");
      score += this.scoreFor("numbers");
      score += this.scoreFor("symbols");
      score += this.scoreFor("uppercase_lowercase");
      score += this.scoreFor("numbers_chars");
      score += this.scoreFor("numbers_symbols");
      score += this.scoreFor("symbols_chars");
      score += this.scoreFor("only_chars");
      score += this.scoreFor("only_numbers");
      score += this.scoreFor("username");
      score += this.scoreFor("sequences");
      score += this.scoreFor("repetitions");

      if (score < 0) {
        score = 0;
      }

      if (score > 100) {
        score = 100;
      }

      if (score < 35) {
        this.status = "weak";
      }

      if (score >= 35 && score < 70) {
        this.status = "good";
      }

      if (score >= 70) {
        this.status = "strong";
      }
    }

    this.score = score;
    return this.score;
  };

  PasswordStrength.fn.scoreFor = function(name) {
    score = 0;

    switch (name) {
      case "password_size":
        if (this.password.length < 6) {
          score = -100;
        } else {
          score = this.password.length * 4;
        }
        break;

      case "numbers":
        if (this.password.match(MULTIPLE_NUMBERS_RE)) {
          score = 5;
        }
        break;

      case "symbols":
        if (this.password.match(MULTIPLE_SYMBOLS_RE)) {
          score = 5;
        }
        break;

      case "uppercase_lowercase":
        if (this.password.match(UPPERCASE_LOWERCASE_RE)) {
          score = 10;
        }
        break;

      case "numbers_chars":
        if (this.password.match(/[a-z]/i) && this.password.match(/[0-9]/)) {
          score = 15;
        }
        break;

      case "numbers_symbols":
        if (this.password.match(/[0-9]/) && this.password.match(SYMBOL_RE)) {
          score = 15;
        }
        break;

      case "symbols_chars":
        if (this.password.match(/[a-z]/i) && this.password.match(SYMBOL_RE)) {
          score = 15;
        }
        break;

      case "only_chars":
        if (this.password.match(/^[a-z]+$/i)) {
          score = -15;
        }
        break;

      case "only_numbers":
        if (this.password.match(/^\d+$/i)) {
          score = -15;
        }
        break;

      case "username":
        if (this.password == this.username) {
          score = -100;
        } else if (this.password.indexOf(this.username) != -1) {
          score = -15;
        }
        break;

      case "sequences":
        score += -15 * this.sequences(this.password);
        score += -15 * this.sequences(this.reversed(this.password));
        break;

      case "repetitions":
        score += -(this.repetitions(this.password, 2) * 4);
        score += -(this.repetitions(this.password, 3) * 3);
        score += -(this.repetitions(this.password, 4) * 2);
        break;
    };

    return score;
  };

  PasswordStrength.fn.isGood = function() {
    return this.status == "good";
  };

  PasswordStrength.fn.isWeak = function() {
    return this.status == "weak";
  };

  PasswordStrength.fn.isStrong = function() {
    return this.status == "strong";
  };

  PasswordStrength.fn.isInvalid = function() {
    return this.status == "invalid";
  };

  PasswordStrength.fn.isValid = function(level) {
    if(level == "strong") {
      return this.isStrong();
    } else if (level == "good") {
      return this.isStrong() || this.isGood();
    } else {
      return !this.containInvalidMatches() && !this.usesCommonWord();
    }
  };

  PasswordStrength.fn.containInvalidMatches = function() {
    if (!this.exclude) {
      return false;
    }

    if (!this.exclude.test) {
      return false;
    }

    return this.exclude.test(this.password.toString());
  };

  PasswordStrength.fn.usesCommonWord = function() {
    return PasswordStrength.commonWords.indexOf(this.password.toLowerCase()) >= 0;
  };

  PasswordStrength.fn.sequences = function(text) {
    var matches = 0;
    var sequenceSize = 0;
    var codes = [];
    var len = text.length;
    var previousCode, currentCode;

    for (var i = 0; i < len; i++) {
      currentCode = text.charCodeAt(i);
      previousCode = codes[codes.length - 1];
      codes.push(currentCode);

      if (previousCode) {
        if (currentCode == previousCode + 1 || previousCode == currentCode) {
          sequenceSize += 1;
        } else {
          sequenceSize = 0;
        }
      }

      if (sequenceSize == 2) {
        matches += 1;
      }
    }

    return matches;
  };

  PasswordStrength.fn.repetitions = function(text, size) {
    var count = 0;
    var matches = {};
    var len = text.length;
    var substring;
    var occurrences;
    var tmpText;

    for (var i = 0; i < len; i++) {
      substring = text.substr(i, size);
      occurrences = 0;
      tmpText = text;

      if (matches[substring] || substring.length < size) {
        continue;
      }

      matches[substring] = true;

      while ((i = tmpText.indexOf(substring)) != -1) {
        occurrences += 1;
        tmpText = tmpText.substr(i + 1);
      };

      if (occurrences > 1) {
        count += 1;
      }
    }

    return count;
  };

  PasswordStrength.fn.reversed = function(text) {
    var newText = "";
    var len = text.length;

    for (var i = len -1; i >= 0; i--) {
      newText += text.charAt(i);
    }

    return newText;
  };

  PasswordStrength.test = function(username, password) {
    strength = new PasswordStrength();
    strength.username = username;
    strength.password = password;
    strength.test();
    return strength;
  };

  PasswordStrength.commonWords = ["!qaz1qaz", "!qaz2wsx", "!qaz2wsx", "!qazxsw2", "!qazzaq1", "#edc4rfv", "000000", "010203", "1111", "11111", "111111", "11111111", "112233", "1212", "121212", "123123", "1234", "12345", "123456", "1234567", "12345678", "123456789", "1234567890", "123qweasd", "12qw!@qw", "1313", "131313", "1941.salembbb.41", "1qaz!qaz", "1qaz@wsx", "1qazxsw@", "1qazzaq!", "2000", "2112", "2222", "232323", "2wsx@wsx", "3333", "3edc#edc", "4128", "4321", "4444", "5150", "5555", "555555", "654321", "6666", "666666", "6969", "696969", "7777", "777777", "7777777", "8675309", "987654", "@wsx2wsx", "aaaa", "aaaaaa", "aaliyah1", "abc123", "abc123abc", "abc123abc", "abcabc123", "abcabc123", "abcd1234", "abcdef", "abgrtyu", "abigail1", "access", "access14", "action", "addison1", "admin", "adobe123", "airforce1", "alabama1", "albert", "alex", "alexander1", "alexandra1", "alexis", "allison1", "amanda", "amateur", "america1", "anderson1", "andrea", "andrew", "angel", "angel101", "angel123", "angela", "angelina1", "angels", "animal", "annabelle1", "anthony", "anthony1", "anthony11", "antonio1", "apollo", "apple", "apples", "arianna1", "arsenal", "arsenal1", "arsenal12", "arsenal123", "arthur", "asdf", "asdfgh", "ashley", "ashley12", "asshole", "asshole1", "atlanta1", "august", "august08", "august10", "august12", "august20", "august22", "austin", "austin02", "austin316", "australia1", "awesome1", "azerty", "baby", "babyboy1", "babygirl1", "babygirl1", "babygurl1", "badboy", "bailey", "bailey12", "banana", "barcelona1", "barney", "baseball", "baseball1", "batista1", "batman", "beach", "bear", "beautiful1", "beaver", "beavis", "beckham7", "beer", "bella123", "benjamin1", "bentley1", "bethany1", "bigcock", "bigdaddy", "bigdaddy1", "bigdick", "bigdog", "bigtits", "bill", "billy", "birdie", "bitch", "bitches", "biteme", "black", "blazer", "blessed1", "blink-182", "blink182", "blonde", "blondes", "blondie1", "blowjob", "blowme", "blue", "bond007", "bonnie", "booboo", "boobs", "booger", "boomer", "booty", "boricua1", "boston", "bradley1", "brandon", "brandon1", "brandon2", "brandon7", "brandy", "braves", "braxton1", "brayden1", "brazil", "breanna1", "brian", "brianna1", "brittany1", "brittney1", "bronco", "broncos", "broncos1", "brooklyn1", "brownie1", "bubba", "bubbles1", "buddy", "buddy123", "bulldog", "buster", "butter", "buttercup1", "butterfly1", "butterfly7", "butthead", "buttons1", "calvin", "camaro", "cameron", "cameron1", "canada", "candy123", "captain", "carlos", "carolina1", "carter", "casper", "cassandra1", "catherine1", "celtic1888", "chargers1", "charles", "charles1", "charlie", "charlie1", "charlotte1", "charmed1", "cheese", "chelsea", "chelsea1", "chelsea123", "chester", "chester1", "chevy", "cheyenne1", "chicago", "chicago1", "chicken", "chicken1", "chocolate1", "chopper1", "chris", "chris123", "christian1", "christina1", "christine1", "christmas1", "classof08", "clayton1", "cocacola", "cock", "coffee", "college", "college1", "colombia1", "colorado1", "compaq", "computer", "computer1", "cookie", "cool", "cooper", "corvette", "courtney1", "cowboy", "cowboys", "cowboys1", "cream", "cricket1", "crystal", "crystal1", "cumming", "cumshot", "cunt", "cutiepie1", "daisy123", "dakota", "dallas", "dallas22", "dan1elle", "daniel", "daniela1", "danielle", "danielle1", "dave", "david", "david123", "death666", "debbie", "december1", "december21", "dennis", "derrick1", "destiny1", "deuseamor", "devil666", "diablo", "diamond", "diamond1", "diamonds1", "dick", "dirty", "doctor", "doggie", "dolphin", "dolphin1", "dolphins", "dolphins1", "dominic1", "donald", "douglas1", "dragon", "dreams", "driver", "eagle", "eagle1", "eagles", "edward", "einstein", "elizabeth1", "elizabeth2", "england1", "enjoy", "enter", "eric", "erotic", "extreme", "falcon", "falcons1", "falcons7", "familia", "fender", "ferrari", "fire", "firebird", "fish", "fishing", "florida", "florida1", "flower", "flyers", "football", "football1", "ford", "forever", "forever1", "forever21", "formula1", "frank", "frankie1", "fred", "freddie1", "freddy", "freedom", "freedom1", "friday13", "friends1", "friends2", "fuck", "fucked", "fucker", "fucking", "fuckme", "fuckoff1", "fuckyou", "fuckyou1", "fuckyou2", "fuckyou2", "gabriel1", "gandalf", "gangsta1", "garrett1", "gateway", "gateway1", "gators", "gemini", "genesis1", "george", "georgia1", "gerrard8", "giants", "giggles1", "ginger", "girl", "girls", "goddess1", "godislove1", "golden", "golf", "golfer", "gordon", "gordon24", "grandma1", "great", "green", "greenday1", "gregory", "guitar", "gunner", "hammer", "hannah", "happy", "hardcore", "harley", "harry123", "hawaii50", "heather", "heather1", "hello", "hello123", "helpme", "hentai", "hershey1", "hockey", "holiday1", "hollywood1", "honey123", "hooters", "horney", "horny", "hotdog", "house", "houston1", "hunter", "hunter01", "hunting", "iceman", "iloveme1", "iloveme2", "iloveyou", "iloveyou1", "iloveyou2", "iloveyou2", "internet", "internet1", "inuyasha1", "ireland1", "isabella1", "isabelle1", "iverson3", "iwantu", "iydgtvmujl6f", "jack", "jackie", "jackson", "jackson1", "jackson5", "jaguar", "jake", "jamaica1", "james", "james123", "january1", "january29", "japan", "jasmine", "jasmine1", "jason", "jasper", "jazmine1", "jeffrey1", "jehovah1", "jennifer", "jennifer1", "jennifer2", "jeremiah1", "jeremy", "jessica", "jessica1", "jessica7", "jesus", "jesus123", "jesus143", "jesus1st", "jesus4me", "jesus777", "jesuscristo", "jesusis#1", "jesusis1", "john", "john3:16", "johncena1", "johnny", "johnson", "jonathan1", "jordan", "jordan01", "jordan12", "jordan23", "joseph", "joshua", "joshua01", "juice", "junior", "justice1", "justin", "justin01", "justin11", "justin21", "justin23", "katelyn1", "katherine1", "kathryn1", "katrina1", "kelly", "kendall1", "kennedy1", "kenneth1", "kevin", "killer", "kimberly1", "king", "kitty", "knight", "kristen1", "kristin1", "l6fkiy9on", "ladies", "ladybug1", "lakers", "lakers24", "lampard8", "laura123", "lauren", "leather", "lebron23", "legend", "letmein", "letmein1", "liberty1", "lindsay1", "lindsey1", "little", "liverp00l", "liverpool1", "liverpool123", "london", "longhorns1", "love", "love4ever", "lover", "lovers", "loveyou2", "lucky", "lucky123", "m1chelle", "mackenzie1", "maddog", "madison", "madison01", "madison1", "madonna1", "maggie", "magic", "magnum", "makayla1", "marcelo", "marie123", "marine", "marines1", "marissa1", "mark", "marlboro", "marshall1", "martin", "marvin", "master", "matrix", "matt", "matthew", "matthew1", "matthew2", "matthew3", "maverick", "maxwell", "maxwell1", "melanie1", "melissa", "melissa1", "member", "mercedes", "mercedes1", "merlin", "metallica1", "michael", "michael01", "michael07", "michael1", "michael2", "michael7", "micheal1", "michele1", "michelle", "michelle1", "michelle2", "mickey", "midnight", "midnight1", "mike", "miller", "mine", "miranda1", "mistress", "molly123", "money", "monica", "monique1", "monkey", "monkey01", "monkey12", "monkey13", "monkeys1", "monster", "monster1", "montana1", "morgan", "mother", "mountain", "movie", "muffin", "murphy", "music", "music123", "mustang", "mustang1", "myspace1", "naked", "nascar", "natalie1", "natasha1", "nathan", "nathan06", "naughty", "ncc1701", "newyork", "newyork1", "nicholas", "nicholas1", "nichole1", "nicole", "nicole12", "ninja", "nipple", "nipples", "nirvana1", "november1", "november11", "november15", "november16", "nursing1", "october1", "october13", "october22", "oliver", "omarion1", "orange", "orlando1", "ou812", "p4ssword", "p@$$w0rd", "p@55w0rd", "p@ssw0rd", "pa$$w0rd", "pa55w0rd", "pa55word", "packers", "panther", "panther1", "panthers1", "panties", "paris", "parker", "pass", "pass1234", "passion1", "passw0rd", "passw0rd", "passw0rd1", "password", "password01", "password1", "password1", "password1!", "password11", "password12", "password12", "password123", "password123", "password13", "password2", "password21", "password3", "password4", "password5", "password7", "password9", "patches1", "patricia1", "patrick", "patrick1", "paul", "peaches", "peaches1", "peanut", "peanut01", "peanut11", "pebbles1", "penguin1", "penis", "pepper", "peter", "phantom", "phantom1", "phoenix", "phoenix1", "photoshop", "pickles1", "playboy1", "player", "please", "pokemon1", "poohbear1", "poohbear1", "pookie", "popcorn1", "porn", "porno", "porsche", "power", "pr1nc3ss", "pr1ncess", "precious1", "preston1", "prince", "princess", "princess01", "princess07", "princess08", "princess1", "princess12", "princess123", "princess13", "princess15", "princess18", "princess19", "princess2", "princess21", "princess23", "princess24", "princess4", "princess5", "princess7", "private", "prototype1", "pumpkin1", "purple", "pussies", "pussy", "qazwsx", "qwert", "qwerty", "qwerty123", "qwertyui", "rabbit", "rachel", "racing", "raiders", "raiders1", "rainbow", "rainbow1", "ranger", "rangers", "rangers1", "raymond1", "rebecca", "rebecca1", "rebelde1", "redskins", "redskins1", "redsox", "redwings", "ricardo1", "richard", "richard1", "robert", "robert01", "rock", "rocket", "rockstar1", "rocky123", "rockyou1", "rockyou1", "ronaldo7", "rosebud", "runner", "rush2112", "russell1", "russia", "rusty123", "sabrina1", "sail2boat3", "samantha", "samantha1", "sammy", "samson", "sandra", "santana1", "saturn", "savannah1", "scooby", "scooter", "scooter1", "scorpio", "scorpio1", "scorpion", "scotland1", "scott", "scrappy1", "sebastian1", "secret", "senior06", "senior07", "september1", "serenity1", "sexsex", "sexy", "shadow", "shannon", "shannon1", "shaved", "shit", "shopping1", "sierra", "silver", "skippy", "skittles1", "slayer", "slipknot1", "slut", "smith", "smokey", "smokey01", "snickers1", "snoopy", "snowball1", "soccer", "soccer11", "soccer12", "soccer13", "soccer14", "soccer17", "softball1", "sophie", "spanky", "sparky", "spartan117", "special1", "spencer1", "spider", "spiderman1", "spongebob1", "squirt", "srinivas", "star", "stars", "start123", "startrek", "starwars", "starwars1", "steelers", "steelers1", "stephanie1", "stephen1", "steve", "steven", "sticky", "stupid", "success", "suckit", "summer", "summer01", "summer05", "summer06", "summer07", "summer08", "summer99", "sunshine", "sunshine1", "super", "superman", "superman1", "superstar1", "surfer", "sweetie1", "sweetpea1", "swimming", "sydney", "taylor", "taylor13", "tbfkiy9on", "teddybear1", "teens", "tennis", "teresa", "test", "tester", "testing", "theman", "thesims2", "thirteen13", "thomas", "thumper1", "thunder", "thunder1", "thx1138", "tiffany", "tiffany1", "tiger", "tiger123", "tigers", "tigger", "tigger01", "tigger12", "tigger123", "time", "timothy1", "tinkerbell1", "titanic1", "tits", "tomcat", "topgun", "toyota", "travis", "trinity1", "trinity3", "tristan1", "trouble", "trouble1", "trustno1", "trustno1", "trustno1", "tucker", "turtle", "twilight1", "twitter", "unicorn1", "united", "vagina", "valerie1", "vampire1", "vanessa1", "vanilla1", "veronica1", "victor", "victoria", "victoria1", "video", "viking", "vincent1", "viper", "voodoo", "voyager", "walter", "warrior", "welcome", "welcome1", "welcome123", "welcome2", "whatever", "whatever1", "white", "whitney1", "william", "william1", "willie", "wilson", "winner", "winston", "winston1", "winter", "winter06", "wizard", "wolf", "women", "xavier", "xxxx", "xxxxx", "xxxxxx", "xxxxxxxx", "yamaha", "yankee", "yankees", "yankees1", "yankees2", "yellow", "young", "z,iyd86i", "zachary1", "zaq!1qaz", "zaq!2wsx", "zaq!xsw2", "zaq1!qaz", "zaq1@wsx", "zaq1zaq!", "zxcvbn", "zxcvbnm", "zzzzzz"];

  return PasswordStrength;
})();

(function($, document, window, undefined) {
    "use strict";

    var pluginName = 'strength';

    var Plugin = $[pluginName] = function(element, options) {
        this.element = element;
        this.$element = $(element);

        this.options = $.extend({}, Plugin.defaults, options, this.$element.data());

        this.classes = this.options.classes;

        this.$username = $(this.options.usernameField);

        this.score = 0;
        this.status = null;

        this.trigger('init');
        this.init();
    };

    Plugin.defaults = {
        skin: null,

        showMeter: true,
        showToggle: true,

        usernameField: '',

        templates: {
            toggle: '<span class="input-group-addon"><input type="checkbox" class="{toggleClass}" title="Show/Hide Password" /></span>',
            meter: '<div class="{meterClass}">{score}</div>',
            score: '<span class="label {scoreClass}"></span>',
            main: '<div class="{containerClass}"><div class="input-group">{input}{toggle}</div>{meter}</div>'
        },

        classes: {
            container: 'strength-container',
            status: 'strength-{status}',
            input: 'strength-input',
            toggle: 'strength-toggle',
            meter: 'strength-meter',
            score: 'strength-score'
        },

        scoreLables: {
            empty: 'Empty',
            invalid: 'Invalid',
            weak: 'Weak',
            good: 'Good',
            strong: 'Strong'
        },

        scoreClasses: {
            empty: '',
            invalid: 'label-danger',
            weak: 'label-warning',
            good: 'label-info',
            strong: 'label-success'
        },

        emptyStatus: true,

        scoreCallback: null,
        statusCallback: null
    };

    Plugin.prototype = {
        constructor: Plugin,
        init: function() {
            this.createHtml();

            this.$element.addClass(this.classes.input);

            this.$toggle = this.$container.find('.' + this.classes.toggle);
            this.$meter = this.$container.find('.' + this.classes.meter);

            this.$score = this.$container.find('.' + this.classes.score);
            this.$input = this.$container.find('.' + this.classes.input);

            this.bindEvents();

            this.initialized = true;
            this.trigger('ready');
        },
        bindEvents: function() {
            var self = this;

            this.$toggle.on('change', function() {
                self.toggle();
            });

            this.$input.bind('keyup.strength keydown.strength', function() {
                self.check();
            });

            this.$element.on('strength::check', function(e, api, score, status) {
                self.$score.html(self.options.scoreLables[status]);

                if (status !== self.status) {
                    var newClass = self.options.scoreClasses[status],
                        oldClass = self.options.scoreClasses[self.status];
                    self.$score.removeClass(oldClass).addClass(newClass);

                    self.trigger('statusChange', status, self.status);
                }

                self.status = status;
                self.score = score;
            });

            this.$element.on('strength::statusChange', function(e, api, current, old) {
                self.$container.removeClass(self.getStatusClass(old)).addClass(self.getStatusClass(current));
            });
        },

        getStatusClass: function(status) {
            return this.options.classes.status.replace('{status}', status);
        },
        createHtml: function() {
            var output = this.options.templates.main;

            output = output.replace('{containerClass}', this.classes.container);
            output = output.replace('{toggle}', this.generateToggle());
            output = output.replace('{meter}', this.generateMeter());
            output = output.replace('{score}', this.generateScore());
            output = output.replace('{input}', '<div class="' + this.classes.input + '"></div>');
            this.$container = $(output);

            if (this.options.skin) {
                this.$container.addClass(this.options.skin);
            }

            this.$element.before(this.$container);
            var $holder = this.$container.find('.' + this.classes.input);
            var el = this.$element.detach();
            $holder.before(el);
            $holder.remove();
        },

        generateToggle: function() {
            if (this.options.showToggle) {
                var output = this.options.templates.toggle;

                output = output.replace('{toggleClass}', this.classes.toggle);
                return output;
            } else {
                return '';
            }
        },

        generateMeter: function() {
            if (this.options.showMeter) {
                var output = this.options.templates.meter;

                output = output.replace('{meterClass}', this.classes.meter);
                return output;
            } else {
                return '';
            }
        },

        generateScore: function() {
            var output = this.options.templates.score;

            output = output.replace('{scoreClass}', this.classes.score);
            return output;
        },

        check: function() {
            var score = 0;
            var status = null;

            if ($.isFunction(this.options.scoreCallback)) {
                score = this.options.scoreCallback.call(this);

                if ($.isFunction(this.options.statusCallback)) {
                    status = this.options.statusCallback.call(this, score);
                }
            } else {
                var check = new PasswordStrength();
                check.username = this.$username.val() || null,
                    check.password = this.$input.val();

                score = check.test();
                status = check.status;
            }

            if (this.options.emptyStatus & status !== 'invalid' && this.$input.val() === '') {
                status = 'empty';
            }

            this.trigger('check', score, status);
        },

        getScore: function() {
            if (!this.score) {
                this.check();
            }
            return this.score;
        },

        getStatus: function() {
            if (!this.status) {
                this.check();
            }
            return this.status;
        },

        toggle: function() {
            var type = this.$toggle.is(":checked") ? "text" : "password";

            this.$input.attr('type', type);

            this.trigger('toggle', type);
        },

        trigger: function(eventType) {
            var method_arguments = Array.prototype.slice.call(arguments, 1),
                data = [this].concat(method_arguments);

            // event
            this.$element.trigger(pluginName + '::' + eventType, data);

            // callback
            eventType = eventType.replace(/\b\w+\b/g, function(word) {
                return word.substring(0, 1).toUpperCase() + word.substring(1);
            });
            var onFunction = 'on' + eventType;
            if (typeof this.options[onFunction] === 'function') {
                this.options[onFunction].apply(this, method_arguments);
            }
        },

        destory: function() {
            this.$element.data(pluginName, null);
            this.trigger('destory');
        }
    };

    $.fn[pluginName] = function(options) {
        if (typeof options === 'string') {
            var method = options;
            var method_arguments = Array.prototype.slice.call(arguments, 1);

            if (/^\_/.test(method)) {
                return false;
            } else if ((/^(get)/.test(method))) {
                var api = this.first().data(pluginName);
                if (api && typeof api[method] === 'function') {
                    return api[method].apply(api, method_arguments);
                }
            } else {
                return this.each(function() {
                    var api = $.data(this, pluginName);
                    if (api && typeof api[method] === 'function') {
                        api[method].apply(api, method_arguments);
                    }
                });
            }
        } else {
            return this.each(function() {
                if (!$.data(this, pluginName)) {
                    $.data(this, pluginName, new Plugin(this, options));
                }
            });
        }
    };
})(jQuery, document, window);
