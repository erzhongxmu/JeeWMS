<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div style="padding:0px;border:0px">
        <div style="float: left; padding-left: 0px;padding-top: 5px;">
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 55px;text-align:left;"
                   title="仓库">仓库: </span>
            <input type="text" name="cangku" value="agv"  style="width: 55px; height: 30px;">
            </span>
            <span  style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 55px;text-align:right;"
                                                  title="层数">层数: </span>
            <input type="text" name="cengshu" value="01" style="width: 55px; height: 30px;">
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 55px;text-align:right;"
                  title="行数">行数: </span>
            <input type="text" name="hangshu" value="03" style="width: 55px; height: 30px;">
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 55px;text-align:right;"
                  title="列数">列数: </span>
            <input type="text" name="lieshu"  value="03"  style="width: 55px; height: 30px;">
            </span>
        </div>
        <div name="searchColums" style="float: left; padding-left: 0px;padding-top: 5px;">

            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 55px;text-align:left;"
                  title="开始">开始: </span>
            <input type="text" id = "chuweiid" name="chuwei" style="width: 55px; height: 30px;">
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 55px;text-align:right;"
                  title="结束">结束: </span>
            <input type="text" id = "desid" name="des" style="width: 55px; height: 30px;">
            </span>

            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 75px;text-align:right;"
                  title="开始指令">开始指令: </span>
             <t:dictSelect id="startcom" field="startcom" type="list" extendJson="{class:'form-control'}"
                          dictTable="wms_plc" dictField="com_no" dictText="com_remark" hasLabel="false"   ></t:dictSelect>

            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 75px;text-align:right;"
                  title="中间指令">中间指令: </span>
            <t:dictSelect id="midcom" field="midcom" type="list" extendJson="{class:'form-control'}"
                          dictTable="wms_plc" dictField="com_no" dictText="com_remark" hasLabel="false"    ></t:dictSelect>
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 75px;text-align:right;"
                  title="结束指令">结束指令: </span>
            <t:dictSelect id="endcom" field="endcom" type="list" extendJson="{class:'form-control'}"
                          dictTable="wms_plc" dictField="com_no" dictText="com_remark" hasLabel="false"   ></t:dictSelect>

             </span>


<%--            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;">--%>
          <button onclick="chaxun('zhengxiang')">加载储位</button>  </span>
          <button onclick="chaxun('diaodu')">调度</button>  </span>
          <button onclick="chaxun('diaoduu')">U型调度</button>  </span>


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
        width: 160.5px;
        height:160.5px;
        line-height: 160.5px;
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
        var binid =  document.getElementById("chuweiid").value
       if(!binid){
            document.getElementById("chuweiid").value = name;

       }else{
            document.getElementById("desid").value =name;

       }
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
        var startcom;
        var midcom;
        var endcom;

        cangku = $('input[name="cangku"]').attr("value");
        chuwei = $('input[name="chuwei"]').attr("value");
        var hangshu = $('input[name="hangshu"]').attr("value") * 1;
        var lieshu = $('input[name="lieshu"]').attr("value") * 1;
        var cengshu = $('input[name="cengshu"]').attr("value");
        des = $('input[name="des"]').attr("value");
        startcom = 'no';
        midcom = 'no';
        endcom = 'no';
        var countstartcom=$("#startcom option").length;
        var countmidcom=$("#midcom option").length;
        var countendcom=$("#endcom option").length;
        for(var i=0;i<countstartcom;i++) {
            if ($("#startcom").get(0).options[i].selected) {
                startcom = $("#startcom").get(0).options[i].value;
            }
        }
        for(var i=0;i<countmidcom;i++) {

            if ($("#midcom").get(0).options[i].selected) {
                midcom = $("#midcom").get(0).options[i].value;
            }
        }
            for(var i=0;i<countendcom;i++) {

                if ($("#endcom").get(0).options[i].selected) {
                    endcom = $("#endcom").get(0).options[i].value;
                }
            }

        //加载消息
        var url = "mdBinController.do?getbinallagv&binstore=" +
            cangku +
            "&binid=" + chuwei +
            "&des=" + des+
            "&cengshu=" + cengshu+
            "&hangshu=" + hangshu+
            "&type=" + type+
            "&startcom=" + startcom+
            "&midcom=" + midcom+
            "&endcom=" + endcom;
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
                        var width = 170.5 * hangshu*1
                        $("#bottom").css("width",width);

                        for (let i = 1; i < list.length; i++) {
                            messageContent += " <div class='all' href='javascript:void(0);' style='background:" +list[i].colour+"' id='" +list[i].binid+"' onclick='javascript:addtab(\"" + list[i].binid + "\")';return false;'>";
                            messageContent += list[i].binid+ " </div> ";
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
