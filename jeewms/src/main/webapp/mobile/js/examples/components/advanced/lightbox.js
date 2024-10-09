/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    $('#exampleZoomGallery').magnificPopup($.po('magnificPopup', {  // 缩放示例
        delegate: 'a',
        type: 'image',
        closeOnContentClick: false,
        closeBtnInside: false,
        mainClass: 'mfp-with-zoom mfp-img-mobile',
        image: {
            verticalFit: true,
            titleSrc: function (item) {
                return item.el.attr('title') + ' &middot;  <a class="image-source-link" href="' + item.el.attr('data-source') + '" target="_blank">查看图片</a>';
            }
        },
        gallery: {
            enabled: true
        },
        zoom: {
            enabled: true,
            duration: 300, // CSS 动画也要设置同样的延迟时间
            opener: function (element) {
                return element.find('img');
            }
        }
    }));

    $('#exampleGallery').magnificPopup($.po('magnificPopup', {  // 相册示例
        delegate: 'a',
        type: 'image',
        tLoading: '正在加载图片 #%curr%...',
        mainClass: 'mfp-img-mobile',
        gallery: {
            enabled: true,
            navigateByImgClick: true,
            preload: [0, 1]
        },
        image: {
            tError: '无法加载图片 <a href="%url%"> #%curr%</a>。',
            titleSrc: function (item) {
                return item.el.attr('title') + '<small>by admui.com</small>';
            }
        }
    }));

    $('.popup-with-css-anim').magnificPopup($.po('magnificPopup', { // CSS 动画示例
        type: 'image',
        removalDelay: 500,
        preloader: true,
        callbacks: {
            beforeOpen: function () {
                this.st.image.markup = this.st.image.markup.replace('mfp-figure', 'mfp-figure mfp-with-anim');
                this.st.mainClass = this.st.el.attr('data-effect');
            }
        },
        closeOnContentClick: true,
        midClick: true
    }));

    $('.popup-youku, .popup-tengxun, .popup-baidumaps').magnificPopup($.po('magnificPopup', { // 视频 | 地图示例
        disableOn: 700,
        type: 'iframe',
        mainClass: 'mfp-fade',
        removalDelay: 160,
        preloader: false,

        fixedContentPos: false
    }));

    $('#examplePopupForm').magnificPopup($.po('magnificPopup', { // 表单示例
        type: 'inline',
        preloader: false,
        focus: '#inputName',

        // 当表单第一个元素自动获得焦点时，一些手机浏览器或产生缩放效果
        // 这非常影响外观，所以我们在分辨率较小时禁用了自动获得焦点
        callbacks: {
            beforeOpen: function () {
                if ($(window).width() <= 768) {
                    this.st.focus = false;
                } else {
                    this.st.focus = '#inputName';
                }
            }
        }
    }));

    $('#examplePopupAjaxAlignTop').magnificPopup($.po('magnificPopup', { // Ajax 示例
        type: 'ajax',
        alignTop: true,
        overflowY: 'scroll' // 设置滚动条避免文本溢出
    }));

    $('#examplePopupAjax').magnificPopup($.po('magnificPopup', {
        type: 'ajax'
    }));

    $('.popup-modal').magnificPopup({ // 模态框示例
        type: 'inline',
        preloader: false,
        modal: true
    });

    $(document).on('click', '.popup-modal-dismiss', function (e) { // 关闭模态框
        e.preventDefault();
        $.magnificPopup.close();
    });

    $('#exampleBrokenImage, #exampleBrokenAjax').magnificPopup($.po('magnificPopup')); // 错误处理示例

})(document, window, jQuery);