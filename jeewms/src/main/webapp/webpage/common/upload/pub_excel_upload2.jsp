<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>通用Excel导入${controller_name}</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
	<fieldset class="step">
<%--	<div class="form"><t:upload name="fiels" buttonText="选择要导入的文件" uploader="${controller_name}.do?importExcel2" auto="true" extend="*.xls;*.xlsx" id="file_upload" formData="documentTitle"></t:upload></div>--%>
<%--	<div class="form" id="filediv" style="height: 50px"></div>--%>
		<div class="col-xs-5">
			<t:webUploader auto="false"   name="fuJian" duplicate="true" fileNumLimit="3" url="${controller_name}.do?importExcel2"></t:webUploader>

		</div>
	</fieldset>
</t:formvalid>
</body>
</html>
