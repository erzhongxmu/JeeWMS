/**
 * 提交指定id表单到指定url
 * @param posturl
 * @param formid
 * @returns {string}
 */
function postform_ajax(posturl,formid){
    var returnmsg="1";
    console.log("1");
    $.ajax({
        cache: true,
        type: "POST",
        url:posturl,
        data:$('#'+formid).serialize(),
        async: false,
        error: function(request) {
            //alert("Connection error");
            returnmsg="Connection error";
        },
        success: function(data) {
            console.log(data);
            //alert(data);
            returnmsg=data;
        }
    });

    return returnmsg;
}

/**
 * post，返回string
 * @param posturl
 * @returns {string}
 */
function post_ajax(posturl){
    var returnmsg="1";
    console.log("1");
    $.ajax({
        cache: true,
        type: "POST",
        url:posturl,
        data:'',
        async: false,
        error: function(request) {
            //alert("Connection error");
            returnmsg="Connection error";
        },
        success: function(data) {
            //console.log(data);
            //alert(data);
            returnmsg=data;
        }
    });

    return returnmsg;
}

/**
 * 图片上传
 * @param c 上传input id
 * @param d 存储路径input id
 * @param d 执行方法
 */
var upload = function(c,d,urlstr,exc_fun){
    "use strict";
    var file = document.getElementById(c).files[0],
        reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function(e){
        $.ajax({
            url : urlstr,
            type: "POST",
            data : {
                imgfile:e.target.result//压缩后的base值
            },
            dataType:"json",
            cache:false,
            async:false,
            success : function(data) {
                if(data.success)
                {
                    //console.log(JSON.stringify(data));
                    $('#'+d).val(JSON.stringify(data.msg).replace(/\"/g, ""));
                    if(exc_fun=="sfhy"){;
                        var newStr = JSON.stringify(data.obj).replace(/\\/g,"");
                        newStr=newStr.trim('"');
                        newStr=eval('(' + newStr + ')');
                        sfhy(newStr);
                    }
                }else{

                }
            },
            error : function(){
                alert("上传失败");
            }
        });
    };
};

/**
 * 获取json 指定key的value
 * @param jsonstr json对象
 * @param key
 * @returns {string}
 */
function jsonVal(jsonstr,key) {
    var result="";
    for(var p in jsonstr){//遍历json对象的每个key/value对,p为key
        if(p==key){
            result= jsonstr[p];
        }
    }
    return result;
}

/**
 * 获取url指定key值
 * @param name
 * @returns {*}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

/**
 * 二维码识别
 * @param c 上传input id
 * @param d 存储结果input id
 * @param e 存储路径input id
 */
var QR_Droid = function(c,d,f){
"use strict";
var file = document.getElementById(c).files[0],
    reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e){
        $.ajax({
            url : "/fxj112/upload.do?QR_Droid",
            type: "POST",
            data : {
                imgfile:e.target.result//压缩后的base值
            },
            dataType:"json",
            cache:false,
            async:false,
            success : function(data) {
                if(data.success)
                {
                    var r=JSON.stringify(data.msg).replace(/\"/g, "");
                    console.log(r);
                    var arr=r.split("|");
                    $('#'+d).val(arr[0]);
                    $('#'+f).val(arr[1]);
                }else{

                }

            },
            error : function(){
                alert("上传失败");
            }
        });
    };
};

/**
 *去除字符串首尾指定字符
 * @param char
 * @param type
 * @returns {string}
 * http://www.jb51.net/article/101338.htm
 */
String.prototype.trim = function (char, type) {
    if (char) {
        if (type == 'left') {
            return this.replace(new RegExp('^\\'+char+'+', 'g'), '');
        } else if (type == 'right') {
            return this.replace(new RegExp('\\'+char+'+$', 'g'), '');
        }
        return this.replace(new RegExp('^\\'+char+'+|\\'+char+'+$', 'g'), '');
    }
    return this.replace(/^\s+|\s+$/g, '');
};


/** 数字金额大写转换(可以处理整数,小数,负数) */
function smalltoBIG(smallID,bigID)
{
    var aNum=$('#'+smallID).val();
    var n=aNum;
    var fraction = ['角', '分'];
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];
    var head = n < 0? '欠': '';
    n = Math.abs(n);

    var s = '';

    for (var i = 0; i < fraction.length; i++)
    {
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
    }
    s = s || '整';
    n = Math.floor(n);

    for (var i = 0; i < unit[0].length && n > 0; i++)
    {
        var p = '';
        for (var j = 0; j < unit[1].length && n > 0; j++)
        {
            p = digit[n % 10] + unit[1][j] + p;
            n = Math.floor(n / 10);
        }
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;
    }
    //return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
    $('#'+bigID).val(head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整'));
}