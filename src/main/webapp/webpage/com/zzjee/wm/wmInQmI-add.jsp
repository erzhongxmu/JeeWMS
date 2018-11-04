<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>收货登记</title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="online/template/ledefault/css/vendor.css">
  <link rel="stylesheet" href="online/template/ledefault/css/bootstrap-theme.css">
  <link rel="stylesheet" href="online/template/ledefault/css/bootstrap.css">
  <link rel="stylesheet" href="online/template/ledefault/css/app.css">
  
  <link rel="stylesheet" href="plug-in/Validform/css/metrole/style.css" type="text/css"/>
  <link rel="stylesheet" href="plug-in/Validform/css/metrole/tablefrom.css" type="text/css"/>
  
  <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
  <script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
  <script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
  <script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
  <script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
  <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
  <script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.config.js"></script>
  <script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.all.min.js"></script>
	<link rel="stylesheet" href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" type="text/css"></link>
	<script type="text/javascript" src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js"></script>

	<script type="text/javascript">
  //编写自定义JS代码
  
  function gethuozhu(){
	ajaxurl="wmInQmIController.do?doGethuozhu&noticeid="+$('#imNoticeId').val();
	$.get(ajaxurl).done(function (data) {
        var obj = eval('(' + data + ')');;
        console.log(obj.obj.zhongWenQch);
        $('#custext').val(obj.obj.zhongWenQch);
    });
}
  
function gettext(){
	ajaxurl="wmInQmIController.do?doGettext&goodsid="+$('#goodsId').val()+"&noticeid="+$('#imNoticeId').val();
	$.get(ajaxurl).done(function (data) {
        var obj = eval('(' + data + ')');;      
        console.log(obj);
        if(!obj.success){
            $('#goodstext').val(obj.msg);
        }
        else{
	        $('#goodstext').val(obj.obj.shpMingCheng);
	        $('#qmOkQuat').val(obj.obj.chlShl);
        }
        
    });
}
function  setbatch(){
	$('#goodsBatch').val( $('#proData').val())
}

window.onload = function() { 
	gethuozhu();
	gettext();
	}; 
  </script>
</head>

 <body>

	<t:formvalid formid="formobj" dialog="true" usePlugin="password"   layout="div" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="binSta" name="binSta" value="N"/>
			
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">收货登记</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>到货通知单：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="imNoticeId" name="imNoticeId" type="text" class="form-control" 
									ignore="ignore" onchange="gethuozhu()"  datatype="*" value='${wmInQmIPage.imNoticeId}'
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">到货通知单</label>
			          </div>
			          			          				          			          <div class="col-xs-3 text-center">
			         <input id="custext" readonly="readonly" style="width:320px;" class="form-control" />
			          </div>
			          </div>
			          <div class="row show-grid">
			          			          <div class="col-xs-3 text-center">
			          	<b>商品编码：</b>
			          </div>
			          	          <div class="col-xs-3">
								<input id="goodsId" name="goodsId" type="text" class="form-control"  
									ignore="ignore" datatype="*" onchange="gettext()"  value='${wmInQmIPage.goodsId}'
								 />
								<span class="Validform_checktip" style="float:left;height:0px;"> </span>
						<label class="Validform_label" style="display: none">商品编码</label>
			          </div>
			          				          			          <div class="col-xs-3 text-center">
			         <input id="goodstext" readonly="readonly"  style="width:320px;" class="form-control" />
			          </div>	  
						</div>
			          
			        				<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>数量(${wmInQmIPage.goodsUnit}) ：</b>
			          </div>
			          <div class="col-xs-3">
						  <input id="qmOkQuaty" name="qmOkQuaty" value='${wmInQmIPage.qmOkQuat}' type="hidden"/>

						  <input id="qmOkQuat" name="qmOkQuat" type="text" class="form-control"
									ignore="checked" value='${wmInQmIPage.qmOkQuat}' style="width:80px;"
								 datatype="*" />   
							
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">数量</label>
			          </div>

										<div class="col-xs-3 text-center">
											<b>储位：</b>
										</div>
										<div class="col-xs-3">

											<t:autocomplete   entityName="MdBinEntity" searchField="kuWeiBianMa" name="binId"></t:autocomplete>



											<span class="Validform_checktip" style="float:left;height:0px;"></span>
											<label class="Validform_label" style="display: none">储位</label>
										</div>


						</div>

	
						
					<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>生产日期：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="proData" name="proData" type="text"
									ignore="checked" onchange="setbatch()" 
								  style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;"  class="form-control" onClick="WdatePicker()"    type="date"  />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">生产日期</label>
			          </div>
			          		          <div class="col-xs-3 text-center">
			          	<b>批次：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsBatch" name="goodsBatch" type="text" class="form-control" 
									ignore="ignore"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">批次</label>
			          </div>
						</div>

					 <div class="row show-grid">
					         <div class="col-xs-3 text-center">
			          	<b>收货温度（°C）：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="recDeg" name="recDeg" type="text" class="form-control" 
									ignore="ignore"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">收货温度</label>
			          </div>
			          <div class="col-xs-3 text-center">
			          	<b>备注：</b>
			          </div>
			          <div class="col-xs-3">
			          <t:dictSelect field="itemText" type="radio" extendJson="{class:'form-control'}"  defaultVal="良品"
								typeGroupCode="sf_lp" hasLabel="false"  title="品质"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
			          
				
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注</label>
			          </div>
						</div>

					  <div class="row show-grid">
					  <div class="col-xs-3 text-center">
						  <b>托盘：</b>
					  </div>
					  <div class="col-xs-3">
						  <input id="tinId" name="tinId" type="text" class="form-control"
								 value='${wmInQmIPage.tinId}'
						  />
						  <span class="Validform_checktip" style="float:left;height:0px;"></span>
						  <label class="Validform_label" style="display: none">托盘</label>
					  </div>
					  </div>

			          <%--<div class="row" id = "sub_tr" style="display: none;">--%>
				        <%--<div class="col-xs-12 layout-header">--%>
				          <%--<div class="col-xs-6"></div>--%>
				          <%--<div class="col-xs-6"><button type="button" onclick="neibuClick();" class="btn btn-default">提交</button></div>--%>
				        <%--</div>--%>
				      <%--</div>--%>
			     </div>
			   </div>
			   
			   <div class="con-wrapper" id="con-wrapper2" style="display: block;"></div>
			 </div>
		<div style="margin:3px auto"><button onclick="sub('formobj');">验收</button></div>

	</t:formvalid>

<script type="text/javascript">


    //表单提交
    function sub(formid){
        console.log('1');

		var qmOkQuaty = $("#qmOkQuaty").val();
		console.log("qmOkQuaty"+qmOkQuaty);
        var qmOkQuat = $("#qmOkQuat").val();
        console.log("qmOkQuaty"+qmOkQuaty);

        if(qmOkQuat==""||qmOkQuat<=0){
            alert("请输入数量");
		}else{
            $.ajax({
                type:"POST",
                url:"wmInQmIController.do?doAdd",
                data:$("#"+formid).serialize(),
                async:false,
                success:function (data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var msg = d.msg;
                        tip(msg);
                        qmOkQuaty = qmOkQuaty  - qmOkQuat;
                        $("#qmOkQuaty").val(qmOkQuaty);
                        $("#qmOkQuat").val(qmOkQuaty);
                        $("#binId").val("");
                        window.close();

                    } else {
                        tip(d.msg);
                    }
                }
            });
		}
    }



</script>
 </body>
<script src = "webpage/com/zzjee/wm/wmInQmI.js"></script>		
</html>