/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function(window, document, $){
    "use strict";

    /*global plyr*/

    $.components.register("plyr", {
        mode: "init",
        default: {
            i18n: {
                restart: "重新开始",
                rewind: "向后 {seektime} 秒",
                play: "播放",
                pause: "暂停",
                forward: "向前 {seektime} 秒",
                buffered: "缓冲",
                currentTime: "当前时间",
                duration: "持续时间",
                volume: "声音",
                toggleMute: "切换静音",
                toggleCaptions: "切换字幕",
                toggleFullscreen: "切换全屏"
            }
        },
        init: function () {
            if (typeof plyr === "undefined") {
                return;
            }

            (function (d, u) {
                var a = new XMLHttpRequest(),
                    b = d.body;

                // Check for CORS support
                if ("withCredentials" in a) {
                    a.open("GET", u, true);
                    a.send();
                    a.onload = function () {
                        var c = d.createElement("div");
                        c.style.display = "none";
                        c.innerHTML = a.responseText;
                        b.insertBefore(c, b.childNodes[0]);
                    };
                }
            })(document, "https://cdn.plyr.io/1.1.5/sprite.svg");

            plyr.setup();
        }
    });
})(window, document, jQuery);