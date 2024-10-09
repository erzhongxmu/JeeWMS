/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var $pageContent = $('#admui-pageContent');
    // 显示单个
    // -------------------------
    $('#exampleSingleItem').slick();


    // 显示多个
    // ----------------------------
    $('#exampleMultipleItems').slick({
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 3
    });

    // 响应式
    // --------------------------------
    $('#exampleResponsive').slick({
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: 4,
        slidesToScroll: 4,
        responsive: [{
            breakpoint: 1024,
            settings: {
                slidesToShow: 3,
                slidesToScroll: 3,
                infinite: true,
                dots: true
            }
        }, {
            breakpoint: 600,
            settings: {
                slidesToShow: 2,
                slidesToScroll: 2
            }
        }, {
            breakpoint: 480,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1
            }
        }
        ]
    });

    // 可变宽度
    // ----------------------------
    $('#exampleVariableWidth').slick({
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        centerMode: true,
        variableWidth: true
    });

    // 可变高度
    // -----------------------------
    $('#exampleAdaptiveHeight').slick({
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        adaptiveHeight: true
    });


    // 通过Data属性设置
    // -----------------------------
    $('#exampleData').slick();


    // 居中
    // -------------------------
    $('#exampleCenter').slick({
        centerMode: true,
        centerPadding: '60px',
        slidesToShow: 3,
        responsive: [{
            breakpoint: 768,
            settings: {
                arrows: false,
                centerMode: true,
                centerPadding: '40px',
                slidesToShow: 3
            }
        }, {
            breakpoint: 480,
            settings: {
                arrows: false,
                centerMode: true,
                centerPadding: '40px',
                slidesToShow: 1
            }
        }]
    });

    // 懒加载
    // --------------------------

    $('#exampleLazy').slick({
        lazyLoad: 'ondemand',
        slidesToShow: 3,
        slidesToScroll: 1
    });


    // 自动播放
    // ----------------------
    $('#exampleAutoplay').slick({
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000
    });

    // 渐变
    // ------------------
    $('#exampleFade').slick({
        dots: true,
        infinite: true,
        speed: 500,
        fade: true,
        slide: 'div',
        cssEase: 'linear'
    });


    // 添加 & 删除
    // --------------------------
    var slideIndex = 1;
    $('#exampleAddRemove').slick({
        dots: true,
        slidesToShow: 3,
        speed: 500,
        slidesToScroll: 3
    });

    $pageContent.on('click', '#exampleAddSlide', function () {
        slideIndex++;
        $('#exampleAddRemove').slick('slickAdd', '<div><h3>' + slideIndex + '</h3></div>');
    });

    $pageContent.on('click', '#exampleRemoveSlide', function () {
        $('#exampleAddRemove').slick('slickRemove', slideIndex - 1);
        if (slideIndex !== 0) {
            slideIndex--;
        }
    });


    // 过滤
    // -----------------------
    $('#exampleFiltering').slick({
        slidesToShow: 4,
        slidesToScroll: 4
    });

    var filtered = false;
    $pageContent.on('click', '#exampleFilter', function () {
        if (filtered === false) {
            $('#exampleFiltering').slick('slickFilter', ':even');
            $(this).text('不过滤');
            filtered = true;
        } else {
            $('#exampleFiltering').slick('slickUnfilter');
            $(this).text('过滤');
            filtered = false;
        }
    });


})(document, window, jQuery);