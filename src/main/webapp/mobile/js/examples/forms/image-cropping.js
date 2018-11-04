/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (document, window, $) {
    'use strict';

    var $content = $('#admui-pageContent');
    // 简单示例
    // ----------------------
    $("#simpleCropper img").cropper({
        preview: "#simpleCropperPreview >.img-preview",
        responsive: true
    });

    // 复杂示例
    // --------------------
    var $exampleFullCropper = $("#exampleFullCropper img"),
        $inputDataX = $("#inputDataX"),
        $inputDataY = $("#inputDataY"),
        $inputDataHeight = $("#inputDataHeight"),
        $inputDataWidth = $("#inputDataWidth");

    // cropper
    $exampleFullCropper.cropper({
        aspectRatio: 16 / 9,
        preview: "#exampleFullCropperPreview > .img-preview",
        responsive: true,
        crop: function () {
            var data = $(this).data('cropper');
            if (typeof data !== 'undefined') {
                data = data.getCropBoxData();
                $inputDataX.val(Math.round(data.left));
                $inputDataY.val(Math.round(data.top));
                $inputDataHeight.val(Math.round(data.height));
                $inputDataWidth.val(Math.round(data.width));
            }
        }
    });

    // 按钮
    $content.on("click", "[data-cropper-method]", function () {
        var data = $(this).data(),
            method = $(this).data('cropper-method'),
            result;
        if (method) {
            result = $exampleFullCropper.cropper(method, data.option);
        }

        if (method === 'getCroppedCanvas') {
            $('#getDataURLModal').modal();
            $('#getImageData').html(result);
        }

    });

    // 处理上传
    var $inputImage = $("#inputImage");

    if (window.FileReader) {
        $inputImage.change(function () {
            var fileReader = new FileReader(),
                files = this.files,
                file;

            if (!files.length) {
                return;
            }

            file = files[0];

            if (/^image\/\w+$/.test(file.type)) {
                fileReader.readAsDataURL(file);
                fileReader.onload = function () {
                    $exampleFullCropper.cropper("reset", true).cropper("replace", this.result);
                    $inputImage.val("");
                };
            } else {
                toastr.warnings("请选择图片文件");
            }
        });
    } else {
        $inputImage.addClass("hide");
    }

    // 设置数据
    $("#setCropperData").click(function () {
        var data = {
            left: parseInt($inputDataX.val()),
            top: parseInt($inputDataY.val()),
            width: parseInt($inputDataWidth.val()),
            height: parseInt($inputDataHeight.val())
        };
        $exampleFullCropper.cropper("setCropBoxData", data);
    });

    $.leavePage = function () {
        $("#simpleCropper img").cropper('destroy');
        $exampleFullCropper.cropper('destroy');
    };

})(document, window, jQuery);
