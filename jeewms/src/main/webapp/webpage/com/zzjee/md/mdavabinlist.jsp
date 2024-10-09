<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div style="padding:0px;border:0px">
<%--        <div style="margin-left:100px"> 桃红色标识此储位有货，淡绿色标识此储位为空</div>--%>
        <div name="searchColums" style="float: left; padding-left: 0px;padding-top: 5px;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"
                  title="仓库">仓库: </span>
            <input type="text" name="cangku" style="width: 100px; height: 30px;">
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"
                  title="储位">储位: </span>
            <input type="text" name="chuwei" style="width: 100px; height: 30px;">
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"
                  title="其他">其他: </span>
            <input type="text" name="des" style="width: 100px; height: 30px;">
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"
                  title="层数">层数: </span>
            <input type="text" name="cengshu" value="01" style="width: 100px; height: 30px;">
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"
                  title="行数">行数: </span>
            <input type="text" name="hangshu" value="60" style="width: 100px; height: 30px;">
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"
                  title="列数">列数: </span>
            <input type="text" name="lieshu"  value="60"  style="width: 100px; height: 30px;">
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;">
          <button onclick="chaxun('zhengxiang')">正向排列查询</button>  </span>
          <button onclick="chaxun('fanxiang')">反向排列查询</button>  </span>
        </div>


    </div>
    <div id="bottom" style="margin-top:60px;border:2px">

    </div>
</div>
<style type="text/css">
    #bottom{
        /*width: 1000px;*/
        /*background: red;*/
        /*width: 100%;*/
    }
    a.a01:link, a.a01:visited {
        font-size: 12px;
        font-family: verdana;
        /*width: 90px;*/
        margin: 1px;
        color: #1A1A1A;
        display: inline-table;
        background-color: #FF4040;
    }

    a.a01:active, a.a01:hover {
        font-size: 12px;
        font-family: verdana;
        /*width: 90px;*/
        margin: 1px;
        color: #1A1A1A;
        display: inline-table;
    }

    a.a02:link, a.a01:visited {
        font-size: 12px;
        font-family: verdana;
        /*width: 90px;*/
        margin: 1px;
        color: #1A1A1A;
        display: inline-table;
        background-color: #66CD00;
    }

    a.a02:active, a.a01:hover {
        font-size: 12px;
        font-family: verdana;
        /*width: 90px;*/
        margin: 1px;
        color: #1A1A1A;
        display: inline-table;
    }
    .all{
        /*display: inline-block;*/
        width: 16.5px;
        height:16.5px;
        line-height: 16.5px;
        text-align: center;
        margin-right: 1px;
        margin-bottom: 1px;
    }
    #bottom{
        display: flex;
        flex-wrap: wrap;
        margin: auto;
    }

</style>
<script type="text/javascript">
    function addtab(name) {
        var str = name.replace('|', '\n');

        alert(str);
    }

    $(document).ready(function () {
        chaxun();
// 	 $("#mvCusCostListtb").find("input[name='outtime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
// 	 $("#mvCusCostListtb").find("input[name='outtime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

    });

    function chaxun(type) {

        var cangku;
        var chuwei;
        var des;
        cangku = $('input[name="cangku"]').attr("value");
        chuwei = $('input[name="chuwei"]').attr("value");
        var hangshu = $('input[name="hangshu"]').attr("value") * 1;
        var lieshu = $('input[name="lieshu"]').attr("value") * 1;
        var cengshu = $('input[name="cengshu"]').attr("value");
        des = $('input[name="des"]').attr("value");
        //加载消息
        var url = "mdBinController.do?getbinall&binstore=" + cangku + "&binid=" + chuwei + "&des=" + des+ "&cengshu=" + cengshu+ "&hangshu=" + hangshu+ "&type=" + type;
        $.ajax({
            url: url,
            type: "GET",
            dataType: "JSON",
            async: false,
            success: function (data) {
                if (data.success) {
                    var messageList = data.attributes.messageList;
                    var messageCount = data.obj;

                    var messageContent = "";
                    var tincount = 0;
                    if (messageList.length > 0) {
                        let num = hangshu * lieshu +1;  // 行数乘以列数  计算一共有多少格子
                        let obj ={
                            bin_store: "",
                            binid: "",
                            colour: "white",
                            des: "",
                            tincount: "",
                            xnode: "",
                            ynode: "",
                            znode: "",
                        } ;// 创建一个空数字 用于填充空格子(因为空格子后台不返回 前端循环渲染需要填充数据)
                        let list = []; // 创建数组填充所有格子
                        //循环填充数组
                        for(let s = 0 ; s < num ; s ++){
                            list.push(obj);
                        }
                        for(let o = 0; o < messageList.length; o++){
                            if(messageList[o].ynode*1 == 1){
                                console.log(messageList[o].ynode*1 * messageList[o].xnode*1 )
                                list[messageList[o].ynode*1 * messageList[o].xnode*1 ] = messageList[o]
                            }
                        else{
                                console.log((messageList[o].ynode*1 - 1) * hangshu*1 + messageList[o].xnode*1)
                                list[(messageList[o].ynode*1 - 1) * hangshu*1 + messageList[o].xnode*1 ] = messageList[o]
                            }
                        }
                        // console.log(list)
                        //计算宽度
                        var width = 17.5 * hangshu*1
                        $("#bottom").css("width",width);

                        for (let i = 1; i < list.length; i++) {
                            messageContent += " <div class='all' href='javascript:void(0);' style='background:" +list[i].colour+"' onclick='javascript:addtab(\"" + list[i].des + "\")';return false;'>";
                            messageContent += list[i].tincount+ " </div> ";
                            // tincount = list[i].tincount + 0;
                            // if (tincount > 0) {
                            //     messageContent += " <a class='a01 all' href='javascript:void(0);'  onclick='javascript:addtab(\"" + list[i].des + "\")';return false;'>";
                            //     messageContent += list[i].binid + " </a> ";
                            //
                            // } else {
                            //     messageContent += " <a class='a02 all' href='javascript:void(0);' >";
                            //     messageContent += list[i].binid + " </a> ";
                            //
                            // }
                        }
                       // var aList =  $('.all')
                        // console.log(aList)

                        // for (var i = 0; i < messageList.length; i++) {
                        //     tincount = messageList[i].tincount + 0;
                        //     if (tincount > 0) {
                        //         messageContent += " <a class='a01' href='javascript:void(0);' onclick='javascript:addtab(\"" + messageList[i].des + "\")';return false;'>";
                        //         messageContent += messageList[i].binid + " </a> ";
                        //
                        //     } else {
                        //         messageContent += " <a class='a02' href='javascript:void(0);' >";
                        //         messageContent += messageList[i].binid + " </a> ";
                        //
                        //     }
                        // }


                    }

                    $("#bottom").html(messageContent);
                    // console.dir(messageContent);
                }
            }
        });

    }


    function print(id) {

        if (begindate == "" || enddate == "") {
            alert("开始或者结束日期不能为空");
        } else {
            var url = "mvCusCostController.do?doPrint&id=" + id + "&begindate=" + begindate + "&enddate=" + enddate;
            window.open(url);
        }

    }

</script>
