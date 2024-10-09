<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>费用维护</title>
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
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmDayCostController.do?doUpdate" tiptype="1" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="id" value='${wmDayCostPage.id}' >
			<input type="hidden" name="createName" value='${wmDayCostPage.createName}' >
			<input type="hidden" name="createDate" value='${wmDayCostPage.createDate}' >
			<input type="hidden" name="updateName" value='${wmDayCostPage.updateName}' >
			<input type="hidden" name="updateDate" value='${wmDayCostPage.updateDate}' >
			<input type="hidden" name="cusName" value='${wmDayCostPage.cusName}' >
			<input type="hidden" name="costName" value='${wmDayCostPage.costName}' >
		<input type="hidden" name="costJs" value='${wmDayCostPage.costJs}' >

		<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">费用维护</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
			
			          
			        
		
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>客户：</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="cusCode" type="list" extendJson="{class:'form-control'}"
								dictTable="mv_cus" dictField="cus_code" dictText="cus_name" defaultVal="${wmDayCostPage.cusCode}" hasLabel="false"  title="客户"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">客户</label>
			          </div>
						</div>
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>费用：</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="costCode" type="list" extendJson="{class:'form-control'}"
								dictTable="ba_cost" dictField="cost_code" dictText="cost_name" defaultVal="${wmDayCostPage.costCode}" hasLabel="false"  title="费用"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">费用</label>
			          </div>
						</div>
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>费用日期：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="costData" name="costData" type="text" 
									ignore="ignore"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;"  class="form-control" onClick="WdatePicker()" value='<fmt:formatDate value='${wmDayCostPage.costData}' type="date" pattern="yyyy-MM-dd"/>' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">费用日期</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>原价：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="dayCostYj" name="dayCostYj" type="text" class="form-control"
									ignore="ignore"
								   value='${wmDayCostPage.dayCostYj}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">原价</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>不含税价：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="dayCostBhs" name="dayCostBhs" type="text" class="form-control"
									ignore="ignore"
								   value='${wmDayCostPage.dayCostBhs}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">不含税价</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>税额：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="dayCostSe" name="dayCostSe" type="text" class="form-control"
									ignore="ignore"
								   value='${wmDayCostPage.dayCostSe}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">税额</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>含税价：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="dayCostHsj" name="dayCostHsj" type="text" class="form-control"
									ignore="ignore"
								   value='${wmDayCostPage.dayCostHsj}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">含税价</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>费用来源：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="costOri" name="costOri" type="text" class="form-control"
									ignore="ignore"
								   value='${wmDayCostPage.costOri}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">费用来源</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>备注：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="beizhu" name="beizhu" type="text" class="form-control"
									ignore="ignore"
								   value='${wmDayCostPage.beizhu}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>状态：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="costSta" name="costSta" type="text" class="form-control"
									ignore="ignore"
								   value='${wmDayCostPage.costSta}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">状态</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>计费数量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="costSl" name="costSl" type="text" class="form-control"
									ignore="ignore"
								   value='${wmDayCostPage.costSl}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">计费数量</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>数量单位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="costUnit" name="costUnit" type="text" class="form-control"
									ignore="ignore"
								   value='${wmDayCostPage.costUnit}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">数量单位</label>
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
<script src = "webpage/com/zzjee/wm/wmDayCost.js"></script>		
</html>