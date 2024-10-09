<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>APP角色</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmsAppRoleController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${wmsAppRolePage.id }">
					<input id="createName" name="createName" type="hidden" value="${wmsAppRolePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${wmsAppRolePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${wmsAppRolePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${wmsAppRolePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${wmsAppRolePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${wmsAppRolePage.updateDate }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${wmsAppRolePage.sysOrgCode }">
					<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${wmsAppRolePage.sysCompanyCode }">
					<input id="query1" name="query1" type="hidden" value="${wmsAppRolePage.query1 }">
					<input id="query2" name="query2" type="hidden" value="${wmsAppRolePage.query2 }">
					<input id="query3" name="query3" type="hidden" value="${wmsAppRolePage.query3 }">
					<input id="query4" name="query4" type="hidden" value="${wmsAppRolePage.query4 }">
					<input id="query5" name="query5" type="hidden" value="${wmsAppRolePage.query5 }">
					<input id="query6" name="query6" type="hidden" value="${wmsAppRolePage.query6 }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								角色编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="approleCode" name="approleCode" type="text" style="width: 150px" class="inputxt"
						     	 ignore="ignore"
						     	 value='${wmsAppRolePage.approleCode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">角色编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								角色名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="approleName" name="approleName" type="text" style="width: 150px" class="inputxt"
						     	 ignore="ignore"
						     	 value='${wmsAppRolePage.approleName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">角色名称</label>
						</td>
					</tr>
<%--					<tr>--%>
<%--						<td align="right">--%>
<%--							<label class="Validform_label">--%>
<%--								app模块id:--%>
<%--							</label>--%>
<%--						</td>--%>
<%--						<td class="value">--%>
<%--						<input id="appmodelId" name="appmodelId" type="text" style="width: 150px" class="searchbox-inputtext"  --%>
<%--						ignore="ignore"--%>
<%--						 onclick="inputClick(this,'appmodel_code,appmodel_name','pop_app_fun')" value='${wmsAppRolePage.appmodelId}'>--%>
<%--							<span class="Validform_checktip"></span>--%>
<%--							<label class="Validform_label" style="display: none;">app模块id</label>--%>
<%--						</td>--%>
<%--					</tr>--%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								app模块编号:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="appmodelCode" style="width:600px;" class="inputxt" rows="6" name="appmodelCode"
						  	 	ignore="ignore"
										  onclick="inputClick(this,'appmodel_code','pop_app_fun')" multiple="multiple"
						  	 	>${wmsAppRolePage.appmodelCode}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">app模块编号</label>
						</td>
					</tr>
<%--					<tr>--%>
<%--						<td align="right">--%>
<%--							<label class="Validform_label">--%>
<%--								app模块名称:--%>
<%--							</label>--%>
<%--						</td>--%>
<%--						<td class="value">--%>
<%--						  	 	<textarea id="appmodelName" style="width:600px;" class="inputxt" rows="6" name="appmodelName"--%>
<%--						  	 	ignore="ignore"--%>
<%--						  	 	>${wmsAppRolePage.appmodelName}</textarea>--%>
<%--							<span class="Validform_checktip"></span>--%>
<%--							<label class="Validform_label" style="display: none;">app模块名称</label>--%>
<%--						</td>--%>
<%--					</tr>--%>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/uniapp/wmsAppRole.js"></script>
