<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>APP功能</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmsAppFunctionController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${wmsAppFunctionPage.id }"/>
		<input id="createName" name="createName" type="hidden" value="${wmsAppFunctionPage.createName }"/>
		<input id="createBy" name="createBy" type="hidden" value="${wmsAppFunctionPage.createBy }"/>
		<input id="createDate" name="createDate" type="hidden" value="${wmsAppFunctionPage.createDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${wmsAppFunctionPage.updateName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${wmsAppFunctionPage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${wmsAppFunctionPage.updateDate }"/>
		<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${wmsAppFunctionPage.sysOrgCode }"/>
		<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${wmsAppFunctionPage.sysCompanyCode }"/>
		<input id="query1" name="query1" type="hidden" value="${wmsAppFunctionPage.query1 }"/>
		<input id="query2" name="query2" type="hidden" value="${wmsAppFunctionPage.query2 }"/>
		<input id="query3" name="query3" type="hidden" value="${wmsAppFunctionPage.query3 }"/>
		<input id="query4" name="query4" type="hidden" value="${wmsAppFunctionPage.query4 }"/>
		<input id="query5" name="query5" type="hidden" value="${wmsAppFunctionPage.query5 }"/>
		<input id="query6" name="query6" type="hidden" value="${wmsAppFunctionPage.query6 }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							app模块编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="appmodelCode" name="appmodelCode" type="text" style="width: 150px" class="inputxt" 
					     	  datatype="*" 
					     	  ignore="checked"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">app模块编号</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							app模块名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="appmodelName" name="appmodelName" type="text" style="width: 150px" class="inputxt" 
					     	  datatype="*" 
					     	  ignore="checked"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">app模块名称</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							app模块排序:
						</label>
					</td>
					<td class="value">
					     	 <input id="appmodelSort" name="appmodelSort" type="text" style="width: 150px" class="inputxt" 
					     	  datatype="*" 
					     	  ignore="checked"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">app模块排序</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="type" name="type" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类型</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							路径:
						</label>
					</td>
					<td class="value">
					     	 <input id="route" name="route" type="text" style="width: 150px" class="inputxt" 
					     	  datatype="*" 
					     	  ignore="checked"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">路径</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							图片:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="picture" name="picture" 
						  	 ignore="checked"
						  	 ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">图片</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否禁用:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="ifBind" type="radio"
									typeGroupCode="sf_yn" defaultVal="${wmsAppFunctionPage.ifBind}" hasLabel="false"  title="是否禁用"  datatype="*"  
									></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否禁用</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/uniapp/wmsAppFunction.js"></script>		
