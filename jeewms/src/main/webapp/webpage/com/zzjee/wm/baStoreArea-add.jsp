<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>ba_store_area</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="baStoreAreaController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${baStoreAreaPage.id }"/>
		<input id="createName" name="createName" type="hidden" value="${baStoreAreaPage.createName }"/>
		<input id="createBy" name="createBy" type="hidden" value="${baStoreAreaPage.createBy }"/>
		<input id="createDate" name="createDate" type="hidden" value="${baStoreAreaPage.createDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${baStoreAreaPage.updateName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${baStoreAreaPage.updateBy }"/>
		<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${baStoreAreaPage.sysOrgCode }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${baStoreAreaPage.updateDate }"/>
		<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${baStoreAreaPage.sysCompanyCode }"/>
		<input id="wareName" name="wareName" type="hidden" value="${baStoreAreaPage.wareName }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							仓库编码:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="wareCode" type="list"
									dictTable="ba_store" dictField="store_code" dictText="store_name" defaultVal="${baStoreAreaPage.wareCode}" hasLabel="false"  title="仓库编码"  
									></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">仓库编码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							库区编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="areaCode" name="areaCode" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="checked"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">库区编码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							库区名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="areaName" name="areaName" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="checked"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">库区名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							库区类型:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="areaType" type="list"
									typeGroupCode="area_type" defaultVal="${baStoreAreaPage.areaType}" hasLabel="false"  title="库区类型"  
									></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">库区类型</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/wm/baStoreArea.js"></script>		
