<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>运输订单</title>
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
		<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
		<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
   <script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>

 <body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tmsYwDingdanController.do?doUpdate" tiptype="1" callback="jeecgFormFileCallBack@Override">
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="id" value='${tmsYwDingdanPage.id}' >
			
			
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">运输订单</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>单号：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="fadh" name="fadh" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.fadh}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">单号</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>下单人：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="username" name="username" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.username}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">下单人</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>发货人：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="fahuoren" name="fahuoren" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.fahuoren}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">发货人</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>发货人电话：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="fhrdh" name="fhrdh" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.fhrdh}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">发货人电话</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>发货人地址：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="fhrdz" name="fhrdz" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.fhrdz}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">发货人地址</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>收货人：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="shouhuoren" name="shouhuoren" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.shouhuoren}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">收货人</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>收货人地址：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="shrdh" name="shrdh" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.shrdh}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">收货人地址</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>车号：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="chehao" name="chehao" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.chehao}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">车号</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>货物：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="huowu" name="huowu" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.huowu}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">货物</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>长米：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="chang" name="chang" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.chang}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">长米</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>宽米：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="kuan" name="kuan" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.kuan}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">宽米</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>高米：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="gao" name="gao" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.gao}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">高米</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>立方米：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="tiji" name="tiji" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.tiji}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">立方米</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>重量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zhongl" name="zhongl" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.zhongl}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">重量</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>代收款金额：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="daishouk" name="daishouk" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.daishouk}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">代收款金额</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>是否等通知：</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="dengtongzhi" type="radio" extendJson="{class:'form-control'}"   typeGroupCode="sf_yn"  defaultVal="${tmsYwDingdanPage.dengtongzhi}" hasLabel="false"  title="是否等通知"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">是否等通知</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>价格：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="jiage" name="jiage" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.jiage}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">价格</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>下单附件：</b>
			          </div>
			          <div class="col-xs-3">
								<table id="xiadanfj_fileTable"></table>
									<table></table>
									<script type="text/javascript">
										var serverMsg="";
										$(function(){
											$('#xiadanfj').uploadify({
												buttonText:'添加图片',
												auto:false,
												progressData:'speed',
												multi:true,
												height:25,
												overrideEvents:['onDialogClose'],
												fileTypeDesc:'文件格式:',
												queueID:'filediv_xiadanfj',
												fileSizeLimit:'15MB',
												swf:'plug-in/uploadify/uploadify.swf',	
												uploader:'cgUploadController.do?saveFiles&jsessionid='+$("#sessionUID").val()+'',
												onUploadStart : function(file) { 
													var cgFormId=$("input[name='id']").val();
													$('#xiadanfj').uploadify("settings", "formData", {
														'cgFormId':cgFormId,
														'cgFormName':'tms_yw_dingdan',
														'cgFormField':'XIADANFJ'
													});
												} ,
												onQueueComplete : function(queueData) {
													 var win = frameElement.api.opener;
													 win.reloadTable();
													 win.tip(serverMsg);
													 frameElement.api.close();
												},
												onUploadSuccess : function(file, data, response) {
													var d=$.parseJSON(data);
													if(d.success){
														var win = frameElement.api.opener;
														serverMsg = d.msg;
													}
												},
												onFallback: function() {
								                    tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
								                },
								                onSelectError: function(file, errorCode, errorMsg) {
								                    switch (errorCode) {
								                    case - 100 : tip("上传的文件数量已经超出系统限制的" + $('#file').uploadify('settings', 'queueSizeLimit') + "个文件！");
								                        break;
								                    case - 110 : tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#file').uploadify('settings', 'fileSizeLimit') + "大小！");
								                        break;
								                    case - 120 : tip("文件 [" + file.name + "] 大小异常！");
								                        break;
								                    case - 130 : tip("文件 [" + file.name + "] 类型不正确！");
								                        break;
								                    }
								                },
								                onUploadProgress: function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {}
											});
										});
									</script>
									<span id="file_uploadspan"><input type="file" name="xiadanfj" id="xiadanfj" /></span> 
									<div class="form" id="filediv_xiadanfj"></div>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">下单附件</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>回单附件：</b>
			          </div>
			          <div class="col-xs-3">
								<table id="huidanfj_fileTable"></table>
									<table></table>
									<script type="text/javascript">
										var serverMsg="";
										$(function(){
											$('#huidanfj').uploadify({
												buttonText:'添加图片',
												auto:false,
												progressData:'speed',
												multi:true,
												height:25,
												overrideEvents:['onDialogClose'],
												fileTypeDesc:'文件格式:',
												queueID:'filediv_huidanfj',
												fileSizeLimit:'15MB',
												swf:'plug-in/uploadify/uploadify.swf',	
												uploader:'cgUploadController.do?saveFiles&jsessionid='+$("#sessionUID").val()+'',
												onUploadStart : function(file) { 
													var cgFormId=$("input[name='id']").val();
													$('#huidanfj').uploadify("settings", "formData", {
														'cgFormId':cgFormId,
														'cgFormName':'tms_yw_dingdan',
														'cgFormField':'HUIDANFJ'
													});
												} ,
												onQueueComplete : function(queueData) {
													 var win = frameElement.api.opener;
													 win.reloadTable();
													 win.tip(serverMsg);
													 frameElement.api.close();
												},
												onUploadSuccess : function(file, data, response) {
													var d=$.parseJSON(data);
													if(d.success){
														var win = frameElement.api.opener;
														serverMsg = d.msg;
													}
												},
												onFallback: function() {
								                    tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
								                },
								                onSelectError: function(file, errorCode, errorMsg) {
								                    switch (errorCode) {
								                    case - 100 : tip("上传的文件数量已经超出系统限制的" + $('#file').uploadify('settings', 'queueSizeLimit') + "个文件！");
								                        break;
								                    case - 110 : tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#file').uploadify('settings', 'fileSizeLimit') + "大小！");
								                        break;
								                    case - 120 : tip("文件 [" + file.name + "] 大小异常！");
								                        break;
								                    case - 130 : tip("文件 [" + file.name + "] 类型不正确！");
								                        break;
								                    }
								                },
								                onUploadProgress: function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {}
											});
										});
									</script>
									<span id="file_uploadspan"><input type="file" name="huidanfj" id="huidanfj" /></span> 
									<div class="form" id="filediv_huidanfj"></div>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">回单附件</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>状态：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zhuangtai" name="zhuangtai" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.zhuangtai}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">状态</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>下单人名字：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="xdrmz" name="xdrmz" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.xdrmz}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">下单人名字</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>司机：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="siji" name="siji" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.siji}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">司机</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>送达时间：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="sdsj" name="sdsj" type="text" style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;" class="form-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" ignore="ignore"  value='<fmt:formatDate value='${tmsYwDingdanPage.sdsj}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">送达时间</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>预计送达时间：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="yjsdsj" name="yjsdsj" type="text" style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;" class="form-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" ignore="ignore"  value='<fmt:formatDate value='${tmsYwDingdanPage.yjsdsj}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">预计送达时间</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>收货人电话：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="shrsj" name="shrsj" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.shrsj}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">收货人电话</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>送货方式：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="hwshfs" name="hwshfs" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.hwshfs}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">送货方式</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>件数：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="hwshjs" name="hwshjs" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.hwshjs}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">件数</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>运费：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="hwyf" name="hwyf" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.hwyf}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">运费</label>
			          </div>
							</div>
			          
			        

			          <div class="col-xs-3 text-center">
			          	<b>卸货费：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="hwxhf" name="hwxhf" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.hwxhf}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">卸货费</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>货物总费用：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="hwzfy" name="hwzfy" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.hwzfy}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">货物总费用</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>下单备注：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="ywddbz" name="ywddbz" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.ywddbz}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">下单备注</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>派车备注：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="ywpcbz" name="ywpcbz" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.ywpcbz}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">派车备注</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>装车备注：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="ywzcbz" name="ywzcbz" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.ywzcbz}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">装车备注</label>
			          </div>
			          
			        
			          <div class="col-xs-3 text-center">
			          	<b>回单备注：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="ywhdbz" name="ywhdbz" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.ywhdbz}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">回单备注</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>客户单号：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="ywkhdh" name="ywkhdh" type="text" class="form-control" ignore="ignore"  value='${tmsYwDingdanPage.ywkhdh}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">客户单号</label>
			          </div>
							<div class="col-xs-2 text-center"><b></b></div>
			         		<div class="col-xs-4"></div>
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
	  	//加载 已存在的 文件
	  	$(function(){
	  		var cgFormId=$("input[name='id']").val();
	  		$.ajax({
	  		   type: "post",
	  		   url: "tmsYwDingdanController.do?getFiles&id=" +  cgFormId,
	  		   success: function(data){
	  			 var arrayFileObj = jQuery.parseJSON(data).obj;
	  			 
	  			$.each(arrayFileObj,function(n,file){
	  				var fieldName = file.field.toLowerCase();
	  				var table = $("#"+fieldName+"_fileTable");
	  				var tr = $("<tr style=\"height:34px;\"></tr>");
	  				var title = file.title;
	  				if(title.length > 15){
	  					title = title.substring(0,12) + "...";
	  				}
	  				var td_title = $("<td>" + title + "</td>");
	  		  		var td_download = $("<td><a  style=\"margin-left:10px;\" href=\"commonController.do?viewFile&fileid=" + file.fileKey + "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity\" title=\"下载\">下载</a></td>")
	  		  		var td_view = $("<td><a  style=\"margin-left:10px;\" href=\"javascript:void(0);\" onclick=\"openwindow('预览','commonController.do?openViewFile&fileid=" + file.fileKey + "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)\">预览</a></td>");
	  		  		var td_del = $("<td><a  style=\"margin-left:10px;\" href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=" + file.fileKey + "',this)\">删除</a></td>");
	  		  		tr.appendTo(table);
	  		  		td_title.appendTo(tr);
	  		  		td_download.appendTo(tr);
	  		  		td_view.appendTo(tr);
	  		  		td_del.appendTo(tr);
	  			 });
	  		   }
	  		});
	  	});
	  	
		  	/**
		 	 * 删除图片数据资源
		 	 */
		  	function del(url,obj){
		  		var content = "请问是否要删除该资源";
		  		var navigatorName = "Microsoft Internet Explorer"; 
		  		if( navigator.appName == navigatorName ){ 
		  			$.dialog.confirm(content, function(){
		  				submit(url,obj);
		  			}, function(){
		  			});
		  		}else{
		  			layer.open({
						title:"提示",
						content:content,
						icon:7,
						yes:function(index){
							submit(url,obj);
						},
						btn:['确定','取消'],
						btn2:function(index){
							layer.close(index);
						}
					});
		  		}
		  	}
		  	
		  	function submit(url,obj){
		  		$.ajax({
		  			async : false,
		  			cache : false,
		  			type : 'POST',
		  			url : url,// 请求的action路径
		  			error : function() {// 请求失败处理函数
		  			},
		  			success : function(data) {
		  				var d = $.parseJSON(data);
		  				if (d.success) {
		  					var msg = d.msg;
		  					tip(msg);
		  					obj.parentNode.parentNode.parentNode.deleteRow(obj.parentNode.parentNode);
		  				} else {
		  					tip(d.msg);
		  				}
		  			}
		  		});
		  	}
	  	
  		function jeecgFormFileCallBack(data){
  			if (data.success == true) {
				uploadFile(data);
			} else {
				if (data.responseText == '' || data.responseText == undefined) {
					$.messager.alert('错误', data.msg);
					$.Hidemsg();
				} else {
					try {
						var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
						$.messager.alert('错误', emsg);
						$.Hidemsg();
					} catch(ex) {
						$.messager.alert('错误', data.responseText + '');
					}
				}
				return false;
			}
			if (!neibuClickFlag) {
				var win = frameElement.api.opener;
				win.reloadTable();
			}
  		}
  		function upload() {
					$('#xiadanfj').uploadify('upload', '*');
					$('#huidanfj').uploadify('upload', '*');
		}
		
		var neibuClickFlag = false;
		function neibuClick() {
			neibuClickFlag = true; 
			$('#btn_sub').trigger('click');
		}
		function cancel() {
					$('#xiadanfj').uploadify('cancel', '*');
					$('#huidanfj').uploadify('cancel', '*');
		}
		function uploadFile(data){
			if(!$("input[name='id']").val()){
				if(data.obj!=null && data.obj!='undefined'){
					$("input[name='id']").val(data.obj.id);
				}
			}
			if($(".uploadify-queue-item").length>0){
				upload();
			}else{
				if (neibuClickFlag){
					alert(data.msg);
					neibuClickFlag = false;
				}else {
					var win = frameElement.api.opener;
					win.reloadTable();
					win.tip(data.msg);
					frameElement.api.close();
				}
			}
		}
  	</script>

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
<script src = "webpage/com/zzjee/tms/tmsYwDingdan.js"></script>		
</html>