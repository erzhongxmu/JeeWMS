/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    // 工具栏
    // -----------------------
    window.customRefreshCallback = function (done) {
        var $panel = $(this);
        setTimeout(function () {
            done();
            $panel.find('.panel-body').html('近日沃尔沃宣布将联合瑞典汽车安全公司奥托立夫(AUTOLIV)成立一个独立子公司，该公司将专攻瞄准未来车型自动驾驶技 术相关的研发。奥托立夫公司(AUTOLIV)是在瑞典设立的一家国际跨国公司，公司多年来研发汽车电子安全系统、电子控制单元，汽车方向盘系统以及夜视 和雷达传感系统，新的合作能够让新公司吸取Autoliv多年来在汽车驾驶安全配件制造方面的经验，研发未来用于沃尔沃或其它厂商的无人驾驶软件系统。');
        }, 1000);
    };

})(document, window, jQuery);