<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>打印模板</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
	<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmPrintModelController.do?doUpdate" callback="jeecgFormFileCallBack@Override">
					<input id="id" name="id" type="hidden" value="${wmPrintModelPage.id }">
					<input id="createName" name="createName" type="hidden" value="${wmPrintModelPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${wmPrintModelPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${wmPrintModelPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${wmPrintModelPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${wmPrintModelPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${wmPrintModelPage.updateDate }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${wmPrintModelPage.sysOrgCode }">
					<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${wmPrintModelPage.sysCompanyCode }">
					<input id="query1" name="query1" type="hidden" value="${wmPrintModelPage.query1 }">
					<input id="query2" name="query2" type="hidden" value="${wmPrintModelPage.query2 }">
					<input id="query3" name="query3" type="hidden" value="${wmPrintModelPage.query3 }">
					<input id="query4" name="query4" type="hidden" value="${wmPrintModelPage.query4 }">
					<input id="query5" name="query5" type="hidden" value="${wmPrintModelPage.query5 }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								用户名:
							</label>
						</td>
						<td class="value">
						     	 <input id="userName" name="userName" type="text" style="width: 150px" class="inputxt"
						     	 ignore="ignore"
						     	 value='${wmPrintModelPage.userName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								打印类型:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="printType" type="list"
										typeGroupCode="print_type" defaultVal="${wmPrintModelPage.printType}" hasLabel="false"  title="打印类型"
										></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">打印类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								打印模板:
							</label>
						</td>
						<td class="value">
						     	 <input id="printModel" name="printModel" type="text" style="width: 150px" class="inputxt"
						     	 ignore="ignore"
						     	 value='${wmPrintModelPage.printModel}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">打印模板</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								模板地址:
							</label>
						</td>
						<td class="value">
							<t:webUploader auto="true" pathValues="${wmPrintModelPage.printServerAddress}" name="printServerAddress" duplicate="true" fileNumLimit="3"></t:webUploader>
						</td>
<%--						<td class="value">--%>
<%--									<table id="fileTable"></table>--%>
<%--										<table></table>--%>
<%--										<script type="text/javascript">--%>
<%--											var serverMsg="";--%>
<%--											$(function(){--%>
<%--												$('#printServerAddress').uploadify({--%>
<%--													buttonText:'添加文件',--%>
<%--													auto:false,--%>
<%--													progressData:'speed',--%>
<%--													multi:true,--%>
<%--													height:25,--%>
<%--													overrideEvents:['onDialogClose'],--%>
<%--													fileTypeDesc:'文件格式:',--%>
<%--													queueID:'filediv_file',--%>
<%--													fileSizeLimit:'15MB',--%>
<%--													swf:'plug-in/uploadify/uploadify.swf',	--%>
<%--													uploader:'cgUploadController.do?saveFiles&jsessionid='+$("#sessionUID").val()+'',--%>
<%--													onUploadStart : function(file) { --%>
<%--														var cgFormId=$("input[name='id']").val();--%>
<%--														$('#printServerAddress').uploadify("settings", "formData", {--%>
<%--															'cgFormId':cgFormId,--%>
<%--															'cgFormName':'wm_print_model',--%>
<%--															'cgFormField':'PRINT_SERVER_ADDRESS'--%>
<%--														});--%>
<%--													} ,--%>
<%--													onQueueComplete : function(queueData) {--%>
<%--														 var win = frameElement.api.opener;--%>
<%--														 win.reloadTable();--%>
<%--														 win.tip(serverMsg);--%>
<%--														 frameElement.api.close();--%>
<%--													},--%>
<%--													onUploadSuccess : function(file, data, response) {--%>
<%--														var d=$.parseJSON(data);--%>
<%--														if(d.success){--%>
<%--															var win = frameElement.api.opener;--%>
<%--															serverMsg = d.msg;--%>
<%--														}--%>
<%--													},--%>
<%--													onFallback: function() {--%>
<%--									                    tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")--%>
<%--									                },--%>
<%--									                onSelectError: function(file, errorCode, errorMsg) {--%>
<%--									                    switch (errorCode) {--%>
<%--									                    case - 100 : tip("上传的文件数量已经超出系统限制的" + $('#file').uploadify('settings', 'queueSizeLimit') + "个文件！");--%>
<%--									                        break;--%>
<%--									                    case - 110 : tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#file').uploadify('settings', 'fileSizeLimit') + "大小！");--%>
<%--									                        break;--%>
<%--									                    case - 120 : tip("文件 [" + file.name + "] 大小异常！");--%>
<%--									                        break;--%>
<%--									                    case - 130 : tip("文件 [" + file.name + "] 类型不正确！");--%>
<%--									                        break;--%>
<%--									                    }--%>
<%--									                },--%>
<%--									                onUploadProgress: function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {}--%>
<%--												});--%>
<%--											});--%>
<%--										</script>--%>
<%--										<span id="file_uploadspan"><input type="file" name="printServerAddress" id="printServerAddress" /></span> --%>
<%--										<div class="form" id="filediv_file"></div>--%>
<%--							<span class="Validform_checktip"></span>--%>
<%--							<label class="Validform_label" style="display: none;">模板地址</label>--%>
<%--						</td>--%>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								打印文件类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="printFileType" name="printFileType" type="text" style="width: 150px" class="inputxt"
						     	 ignore="ignore"
						     	 value='${wmPrintModelPage.printFileType}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">打印文件类型</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/wm/wmPrintModel.js"></script>
	  	<script type="text/javascript">
		  	//加载 已存在的 文件
		  	$(function(){
		  		var table = $("#fileTable");
		  		var cgFormId=$("input[name='id']").val();
		  		$.ajax({
		  		   type: "post",
		  		   url: "wmPrintModelController.do?getFiles&id=" +  cgFormId,
		  		   success: function(data){
		  			 var arrayFileObj = jQuery.parseJSON(data).obj;

		  			$.each(arrayFileObj,function(n,file){
		  				var tr = $("<tr style=\"height:34px;\"></tr>");
		  				var td_title = $("<td>" + file.title + "</td>")
		  		  		var td_download = $("<td><a href=\"commonController.do?viewFile&fileid=" + file.fileKey + "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity\" title=\"下载\">下载</a></td>")
		  		  		var td_view = $("<td><a href=\"javascript:void(0);\" onclick=\"openwindow('预览','commonController.do?openViewFile&fileid=" + file.fileKey + "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)\">预览</a></td>");
		  		  		var td_del = $("<td><a href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=" + file.fileKey + "',this)\">删除</a></td>");

		  		  		tr.appendTo(table);
		  		  		td_title.appendTo(tr);
		  		  		td_download.appendTo(tr);
		  		  		td_view.appendTo(tr);
		  		  		td_del.appendTo(tr);
		  			 });
		  		   }
		  		});
		  	})
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
				$('#printServerAddress').uploadify('upload', '*');
			}

			var neibuClickFlag = false;
			function neibuClick() {
				neibuClickFlag = true;
				$('#btn_sub').trigger('click');
			}
			function cancel() {
				$('#printServerAddress').uploadify('cancel', '*');
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
