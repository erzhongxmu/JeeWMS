<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>仓库</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="baStoreController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${baStorePage.id }">
					<input id="createName" name="createName" type="hidden" value="${baStorePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${baStorePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${baStorePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${baStorePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${baStorePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${baStorePage.updateDate }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${baStorePage.sysOrgCode }">
					<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${baStorePage.sysCompanyCode }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								仓库代码:
							</label>
						</td>
						<td class="value">
						     	 <input id="storeCode" name="storeCode" type="text" style="width: 150px" class="inputxt"  datatype="*" 
						     	 ignore="checked" 
						     	 value='${baStorePage.storeCode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">仓库代码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								仓库名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="storeName" name="storeName" type="text" style="width: 150px" class="inputxt"  datatype="*" 
						     	 ignore="checked" 
						     	 value='${baStorePage.storeName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">仓库名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								仓库属性:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="storeText" type="checkbox"
										dictTable="	 ba_goods_type" dictField="goods_type_code" dictText="goods_type_name" defaultVal="${baStorePage.storeText}" hasLabel="false"  title="仓库属性"  
										></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">仓库属性</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/ba/baStore.js"></script>		
