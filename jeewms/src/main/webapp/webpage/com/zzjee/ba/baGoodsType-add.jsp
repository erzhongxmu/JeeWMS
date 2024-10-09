<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>产品属性</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="baGoodsTypeController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${baGoodsTypePage.id }"/>
		<input id="createName" name="createName" type="hidden" value="${baGoodsTypePage.createName }"/>
		<input id="createBy" name="createBy" type="hidden" value="${baGoodsTypePage.createBy }"/>
		<input id="createDate" name="createDate" type="hidden" value="${baGoodsTypePage.createDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${baGoodsTypePage.updateName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${baGoodsTypePage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${baGoodsTypePage.updateDate }"/>
		<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${baGoodsTypePage.sysOrgCode }"/>
		<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${baGoodsTypePage.sysCompanyCode }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							产品属性编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="goodsTypeCode" name="goodsTypeCode" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">产品属性编码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							产品属性名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="goodsTypeName" name="goodsTypeName" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">产品属性名称</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/ba/baGoodsType.js"></script>		
