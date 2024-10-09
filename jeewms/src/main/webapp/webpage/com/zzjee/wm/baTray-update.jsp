<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>ba_tray</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="baTrayController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${baTrayPage.id }">
					<input id="createName" name="createName" type="hidden" value="${baTrayPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${baTrayPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${baTrayPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${baTrayPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${baTrayPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${baTrayPage.updateDate }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${baTrayPage.sysOrgCode }">
					<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${baTrayPage.sysCompanyCode }">
					<input id="attr1" name="attr1" type="hidden" value="${baTrayPage.attr1 }">
					<input id="attr2" name="attr2" type="hidden" value="${baTrayPage.attr2 }">
					<input id="attr3" name="attr3" type="hidden" value="${baTrayPage.attr3 }">
					<input id="attr4" name="attr4" type="hidden" value="${baTrayPage.attr4 }">
					<input id="attr5" name="attr5" type="hidden" value="${baTrayPage.attr5 }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								托盘编码:
							</label>
						</td>
						<td class="value">
						     	 <input id="trayCode" name="trayCode" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="checked" 
						     	 value='${baTrayPage.trayCode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">托盘编码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								托盘名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="trayName" name="trayName" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="checked" 
						     	 value='${baTrayPage.trayName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">托盘名称</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/wm/baTray.js"></script>		
