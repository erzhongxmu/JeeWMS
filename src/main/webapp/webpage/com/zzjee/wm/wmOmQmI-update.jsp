<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>下架任务</title>
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

  function gettext(){
      ajaxurl="wmOmQmIController.do?dogetbin&goodsid="+$('#goodsId').val()+"&tinid="+$('#tinId').val();
      $.get(ajaxurl).done(function (data) {
          var obj = eval('(' + data + ')');;
          console.log(obj);
          if(obj.success){
              $('#proData').val(obj.obj.proData);
              $('#binId').val(obj.obj.binId);
          }


      });
  }
  </script>
</head>

 <body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmOmQmIController.do?doUpdate" tiptype="1" >
		<input type="hidden" id="btn_sub" class="btn_sub"/>
		<input type="hidden" name="id" value='${wmOmQmIPage.id}' >
		<input id="createDate" name="createDate" type="hidden" value="${wmOmQmIPage.createDate }"/>
		<input id="createBy" name="createBy" type="hidden" value="${wmOmQmIPage.createBy }"/>
		<input id="createName" name="createName" type="hidden" value="${wmOmQmIPage.createName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${wmOmQmIPage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${wmOmQmIPage.updateDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${wmOmQmIPage.updateName }"/>
		<input id="tinTj" name="tinTj" type="hidden" value="${wmOmQmIPage.tinTj }"/>
		<input id="goodsBatch" name="goodsBatch" type="hidden" value="${wmOmQmIPage.goodsBatch }"/>
		<input id="tinZhl" name="tinZhl" type="hidden" value="${wmOmQmIPage.tinZhl }"/>
		<input id="recDeg" name="recDeg" type="hidden" value="${wmOmQmIPage.recDeg }"/>
		<input id="omQuat" name="omQuat" type="hidden" value="${wmOmQmIPage.omQuat }"/>
		<input id="omNoticeId" name="omNoticeId" type="hidden" value="${wmOmQmIPage.omNoticeId }"/>
			
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">下架任务 ${wmOmQmIPage.omNoticeId}</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
			      
			      					<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>任务接收人：</b>
			          </div>
			          <div class="col-xs-3">
							   
								   	<t:dictSelect field="assignTo" type="radio" extendJson="{class:'form-control'}" 
								typeGroupCode="assgin" defaultVal="${wmOmQmIPage.assignTo}" hasLabel="false"  title="任务接收人"></t:dictSelect>     
			
								   
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">任务接收人</label>
			          </div>
						</div>
			      
			      
			      <div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>生产日期：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="proData" name="proData" type="text" class="form-control" 
									ignore="ignore"
								   value='${wmOmQmIPage.proData}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">生产日期</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>托盘：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="tinId" name="tinId" type="text" class="form-control"  onchange="gettext()"
									ignore="ignore"
								   value='${wmOmQmIPage.tinId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">托盘</label>
			          </div>
						</div>
			          
			        					<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>仓位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="binId" name="binId" type="text" class="form-control" 
									ignore="ignore"
								   value='${wmOmQmIPage.binId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">仓位</label>
			          </div>
						</div>
			      
	
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>商品编码：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsId" name="goodsId" type="text" class="form-control" readonly="readonly"
									ignore="ignore"
								   value='${wmOmQmIPage.goodsId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商品编码</label>
			          </div>
						</div>
			          						<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>商品名称：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsName" name="goodsName" type="text" class="form-control" readonly="readonly"
									ignore="ignore"
								   value='${wmOmQmIPage.goodsName}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商品名称</label>
			          </div>
						</div>
			        



			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>基本单位数量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="baseGoodscount" name="baseGoodscount" type="text" class="form-control" 
									ignore="ignore"
								   value='${wmOmQmIPage.baseGoodscount}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">基本单位数量</label>
			          </div>
						</div>




							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>基本单位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="baseUnit" name="baseUnit" type="text" class="form-control" readonly="readonly"
									ignore="ignore"
								   value='${wmOmQmIPage.baseUnit}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">基本单位</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>数量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="qmOkQuat" name="qmOkQuat" type="text" class="form-control"  
									ignore="ignore"
								   value='${wmOmQmIPage.qmOkQuat}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">数量</label>
			          </div>
						</div>
			          
			        <div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>单位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsUnit" name="goodsUnit" type="text" class="form-control" readonly="readonly"
									ignore="ignore"
								   value='${wmOmQmIPage.goodsUnit}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">单位</label>
			          </div>
						</div>
			        
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>备注：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="itemText" name="itemText" type="text" class="form-control" 
									ignore="ignore"
								   value='${wmOmQmIPage.itemText}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注</label>
			          </div>
						</div>
			          
			        
							
						
							
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>是否已下架：</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="binSta" type="radio" extendJson="{class:'form-control'}" readonly="readonly"
								typeGroupCode="sf_yn" defaultVal="${wmOmQmIPage.binSta}" hasLabel="false"  title="是否已下架"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">是否已下架</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>货主：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="cusCode" name="cusCode" type="text" class="form-control" readonly="readonly"
									ignore="ignore"
								   value='${wmOmQmIPage.cusCode}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">货主</label>
			          </div>
						</div>
			          
			        							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>客户名称：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="cusName" name="cusName" type="text" class="form-control" readonly="readonly"
									ignore="ignore"
								   value='${wmOmQmIPage.cusName}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">客户名称</label>
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
<script src = "webpage/com/zzjee/wm/wmOmQmI.js"></script>		
</html>