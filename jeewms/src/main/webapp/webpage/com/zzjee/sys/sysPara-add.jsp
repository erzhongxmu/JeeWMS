<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>全局参数</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="sysParaController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${sysParaPage.id }"/>
		<input id="createName" name="createName" type="hidden" value="${sysParaPage.createName }"/>
		<input id="createBy" name="createBy" type="hidden" value="${sysParaPage.createBy }"/>
		<input id="createDate" name="createDate" type="hidden" value="${sysParaPage.createDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${sysParaPage.updateName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${sysParaPage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${sysParaPage.updateDate }"/>
		<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${sysParaPage.sysOrgCode }"/>
		<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${sysParaPage.sysCompanyCode }"/>
		<input id="bpmStatus" name="bpmStatus" type="hidden" value="${sysParaPage.bpmStatus }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							参数类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="parType" name="parType" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">参数类型</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							参数所属人:
						</label>
					</td>
					<td class="value">
					     	 <input id="parUsername" name="parUsername" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">参数所属人</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							参数值:
						</label>
					</td>
					<td class="value">
					     	 <input id="parValue" name="parValue" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">参数值</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/sys/sysPara.js"></script>		
