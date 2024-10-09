<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>APP角色分配 </title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmsAppUserController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${wmsAppUserPage.id }"/>
		<input id="createName" name="createName" type="hidden" value="${wmsAppUserPage.createName }"/>
		<input id="createBy" name="createBy" type="hidden" value="${wmsAppUserPage.createBy }"/>
		<input id="createDate" name="createDate" type="hidden" value="${wmsAppUserPage.createDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${wmsAppUserPage.updateName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${wmsAppUserPage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${wmsAppUserPage.updateDate }"/>
		<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${wmsAppUserPage.sysOrgCode }"/>
		<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${wmsAppUserPage.sysCompanyCode }"/>
		<input id="query1" name="query1" type="hidden" value="${wmsAppUserPage.query1 }"/>
		<input id="query2" name="query2" type="hidden" value="${wmsAppUserPage.query2 }"/>
		<input id="query3" name="query3" type="hidden" value="${wmsAppUserPage.query3 }"/>
		<input id="query4" name="query4" type="hidden" value="${wmsAppUserPage.query4 }"/>
		<input id="query5" name="query5" type="hidden" value="${wmsAppUserPage.query5 }"/>
		<input id="query6" name="query6" type="hidden" value="${wmsAppUserPage.query6 }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="appuserCode" name="appuserCode" type="text" style="width: 150px" class="inputxt"

					     	  ignore="ignore"
					     	  />
<%--						<t:userSelect title="用户名称" selectedNamesInputId="userNames" selectedIdsInputId="appuserCode" windowWidth="600px" windowHeight="300px"></t:userSelect>--%>


					<%--						<t:userSelect selectedNamesInputId="userNames" selectedIdsInputId="appuserCode" > </t:userSelect>--%>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户编号</label>
						</td>
				</tr>
<%--				<tr>--%>
<%--					<td align="right">--%>
<%--						<label class="Validform_label">--%>
<%--							用户名称:--%>
<%--						</label>--%>
<%--					</td>--%>
<%--					<td class="value">--%>
<%--					     	 <input id="appuserName" name="appuserName" type="text" style="width: 150px" class="inputxt"--%>

<%--					     	  ignore="ignore"--%>
<%--					     	  />--%>
<%--							<span class="Validform_checktip"></span>--%>
<%--							<label class="Validform_label" style="display: none;">用户名称</label>--%>
<%--						</td>--%>
<%--				</tr>--%>
<%--				<tr>--%>
<%--					<td align="right">--%>
<%--						<label class="Validform_label">--%>
<%--							角色id:--%>
<%--						</label>--%>
<%--					</td>--%>
<%--					<td class="value">--%>
<%--						<input id="approleId" name="approleId" type="text" style="width: 150px" class="searchbox-inputtext" 						ignore="ignore"--%>
<%--						 onclick="inputClick(this,'approle_code,approle_name','pop_app_role')" />--%>
<%--							<span class="Validform_checktip"></span>--%>
<%--							<label class="Validform_label" style="display: none;">角色id</label>--%>
<%--						</td>--%>
<%--				</tr>--%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							角色编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="approleCode" name="approleCode" type="text" style="width: 150px" class="inputxt"
									onclick="inputClick(this,'approle_code','pop_app_role')" multiple="multiple"
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">角色编号</label>
						</td>
				</tr>
<%--				<tr>--%>
<%--					<td align="right">--%>
<%--						<label class="Validform_label">--%>
<%--							角色名称:--%>
<%--						</label>--%>
<%--					</td>--%>
<%--					<td class="value">--%>
<%--					     	 <input id="approleName" name="approleName" type="text" style="width: 150px" class="inputxt"--%>

<%--					     	  ignore="ignore"--%>
<%--					     	  />--%>
<%--							<span class="Validform_checktip"></span>--%>
<%--							<label class="Validform_label" style="display: none;">角色名称</label>--%>
<%--						</td>--%>
<%--				</tr>--%>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/uniapp/wmsAppUser.js"></script>
