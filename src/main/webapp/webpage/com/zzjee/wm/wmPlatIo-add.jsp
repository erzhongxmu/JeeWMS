<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>月台进出</title>
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
								
   <script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>

 <body>

	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmPlatIoController.do?doAdd" tiptype="1" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="platOper" name="platOper"/>
			
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">月台进出</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>车号：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="carno" name="carno" type="text" class="form-control" 
									ignore="ignore" datatype="*"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">车号</label>
			          </div>
			          	          <div class="col-xs-3 text-center">
			          	<b>月台编号：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="platId" name="platId" type="text" class="form-control" 
									ignore="ignore" datatype="*"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">月台编号</label>
			          </div>
			          			          <div class="col-xs-3 text-center">
			          	<b>单据编号：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="docId" name="docId" type="text" class="form-control" 
									ignore="ignore" datatype="*"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">单据编号</label>
			          </div>
						</div>
			          
		          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>进入时间：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="inData" name="inData" type="text" 
									ignore="ignore"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;" class="form-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="date" pattern="yyyy-MM-dd hh:mm:ss"/>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">进入时间</label>
			          </div>
			          		          <div class="col-xs-3 text-center">
			          	<b>驶出时间：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="outData" name="outData" type="text" 
									ignore="ignore"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;" class="form-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="date" pattern="yyyy-MM-dd hh:mm:ss"/>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">驶出时间</label>
			          </div>
						</div>
			          
		          					<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>计划进入时间：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="planIndata" name="planIndata" type="text" 
									ignore="ignore" datatype="*"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;" class="form-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="date" pattern="yyyy-MM-dd hh:mm:ss"/>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">计划进入时间</label>
			          </div>
			          			          <div class="col-xs-3 text-center">
			          	<b>计划驶出时间：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="planOutdata" name="planOutdata" type="text" 
									ignore="ignore"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;" class="form-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="date" pattern="yyyy-MM-dd hh:mm:ss"/>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">计划驶出时间</label>
			          </div>
						</div>
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>月台状态：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="platSta" name="platSta" type="text" class="form-control" 
									ignore="ignore"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">月台状态</label>
			          </div>
			              <div class="col-xs-3 text-center">
			          	<b>备注：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="platBeizhu" name="platBeizhu" type="text" class="form-control" 
									ignore="ignore"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注</label>
			          </div>
						</div>
	
			          
			        
		
		       
			          <div class="row" id = "sub_tr" style="display: none;">
				        <div class="col-xs-12 layout-header">
				          <div class="col-xs-6"></div>
				          <div class="col-xs-6"><button type="button" onclick="neibuClick();" class="btn btn-default">提交</button></div>
				        </div>
				      </div>
			     </div>
			   </div>
			   
			   <div class="con-wrapper" id="con-wrapper2" style="display: block;"></div>
			 </div>
  </t:formvalid>

<script type="text/javascript">
   $(function(){
    //查看模式情况下,删除和上传附件功能禁止使用
	if(location.href.indexOf("load=detail")!=-1){
		$(".jeecgDetail").hide();
	}
	
	if(location.href.indexOf("mode=read")!=-1){
		//查看模式控件禁用
		$("#formobj").find(":input").attr("disabled","disabled");
	}
	if(location.href.indexOf("mode=onbutton")!=-1){
		//其他模式显示提交按钮
		$("#sub_tr").show();
	}
   });

  var neibuClickFlag = false;
  function neibuClick() {
	  neibuClickFlag = true; 
	  $('#btn_sub').trigger('click');
  }

</script>
 </body>
<script src = "webpage/com/zzjee/wm/wmPlatIo.js"></script>		
</html>