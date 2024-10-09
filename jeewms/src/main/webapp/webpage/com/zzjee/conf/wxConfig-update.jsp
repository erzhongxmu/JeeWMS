<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>配置信息</title>
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
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wxConfigController.do?doUpdate" tiptype="1" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="id" value='${wxConfigPage.id}' >
			
			
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">配置信息</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>创建人名称：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="createName" name="createName" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.createName}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">创建人名称</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>创建人登录名称：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="createBy" name="createBy" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.createBy}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">创建人登录名称</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>创建日期：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="createDate" name="createDate" type="text" 
									ignore="ignore"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;"  class="form-control" onClick="WdatePicker()" value='<fmt:formatDate value='${wxConfigPage.createDate}' type="date" pattern="yyyy-MM-dd"/>' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">创建日期</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>更新人名称：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="updateName" name="updateName" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.updateName}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">更新人名称</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>更新人登录名称：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="updateBy" name="updateBy" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.updateBy}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">更新人登录名称</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>更新日期：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="updateDate" name="updateDate" type="text" 
									ignore="ignore"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;"  class="form-control" onClick="WdatePicker()" value='<fmt:formatDate value='${wxConfigPage.updateDate}' type="date" pattern="yyyy-MM-dd"/>' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">更新日期</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>所属部门：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="sysOrgCode" name="sysOrgCode" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.sysOrgCode}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">所属部门</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>所属公司：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="sysCompanyCode" name="sysCompanyCode" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.sysCompanyCode}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">所属公司</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>流程状态：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="bpmStatus" name="bpmStatus" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.bpmStatus}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">流程状态</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>前端编码：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="appCode" name="appCode" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.appCode}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">前端编码</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>备注：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="appRemark" name="appRemark" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.appRemark}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>appID：</b>
			          </div>
			          <div class="col-xs-3">
						  	 	<textarea id="appId" class="form-control" rows="6" 
									ignore="ignore"
						  	 	name="appId">${wxConfigPage.appId}</textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">appID</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>appsecret：</b>
			          </div>
			          <div class="col-xs-3">
						  	 	<textarea id="appSecret" class="form-control" rows="6" 
									ignore="ignore"
						  	 	name="appSecret">${wxConfigPage.appSecret}</textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">appsecret</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>appkey：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="appKey" name="appKey" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.appKey}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">appkey</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>商户号：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="mchId" name="mchId" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.mchId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商户号</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>通知地址：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="notifyUrl" name="notifyUrl" type="text" class="form-control"
									ignore="ignore"
								   value='${wxConfigPage.notifyUrl}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">通知地址</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>GRANT_TYPE：</b>
			          </div>
			          <div class="col-xs-3">
						  	 	<textarea id="grantType" class="form-control" rows="6" 
									ignore="ignore"
						  	 	name="grantType">${wxConfigPage.grantType}</textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">GRANT_TYPE</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>备用1：</b>
			          </div>
			          <div class="col-xs-3">
						  	 	<textarea id="wxBy1" class="form-control" rows="6" 
									ignore="ignore"
						  	 	name="wxBy1">${wxConfigPage.wxBy1}</textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备用1</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>备用2：</b>
			          </div>
			          <div class="col-xs-3">
						  	 	<textarea id="wxBy2" class="form-control" rows="6" 
									ignore="ignore"
						  	 	name="wxBy2">${wxConfigPage.wxBy2}</textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备用2</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>备用3：</b>
			          </div>
			          <div class="col-xs-3">
						  	 	<textarea id="wxBy3" class="form-control" rows="6" 
									ignore="ignore"
						  	 	name="wxBy3">${wxConfigPage.wxBy3}</textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备用3</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>备用4：</b>
			          </div>
			          <div class="col-xs-3">
						  	 	<textarea id="wxBy4" class="form-control" rows="6" 
									ignore="ignore"
						  	 	name="wxBy4">${wxConfigPage.wxBy4}</textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备用4</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>备用5：</b>
			          </div>
			          <div class="col-xs-3">
						  	 	<textarea id="wxBy5" class="form-control" rows="6" 
									ignore="ignore"
						  	 	name="wxBy5">${wxConfigPage.wxBy5}</textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备用5</label>
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
<script src = "webpage/com/zzjee/conf/wxConfig.js"></script>		
</html>