<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>mv_cus_other</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="mvCusOtherController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${mvCusOtherPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属客户:
						</label>
					</td>
					<td class="value">
					     	 <input id="suoShuKeHu" name="suoShuKeHu" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属客户</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							客户编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="cusCode" name="cusCode" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户编码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							cusName:
						</label>
					</td>
					<td class="value">
					     	 <input id="cusName" name="cusName" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">cusName</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/md/mvCusOther.js"></script>		
