<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>RFID表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="rfidBuseController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${rfidBusePage.id }"/>
		<input id="createName" name="createName" type="hidden" value="${rfidBusePage.createName }"/>
		<input id="updateName" name="updateName" type="hidden" value="${rfidBusePage.updateName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${rfidBusePage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${rfidBusePage.updateDate }"/>
		<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${rfidBusePage.sysOrgCode }"/>
		<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${rfidBusePage.sysCompanyCode }"/>
		<input id="bpmStatus" name="bpmStatus" type="hidden" value="${rfidBusePage.bpmStatus }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							创建人登录名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="createBy" name="createBy" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建人登录名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							创建日期:
						</label>
					</td>
					<td class="value">
							   <input id="createDate" name="createDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" 
					      						 
					      						ignore="ignore"
					      						/>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建日期</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="rfidType" name="rfidType" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类型</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							业务编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="rfidBuseno" name="rfidBuseno" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务编号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							业务内容:
						</label>
					</td>
					<td class="value">
					     	 <input id="rfidBusecont" name="rfidBusecont" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务内容</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							RFID1:
						</label>
					</td>
					<td class="value">
					     	 <input id="rfidId1" name="rfidId1" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">RFID1</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							RFID2:
						</label>
					</td>
					<td class="value">
					     	 <input id="rfidId2" name="rfidId2" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">RFID2</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							RFID3:
						</label>
					</td>
					<td class="value">
					     	 <input id="rfidId3" name="rfidId3" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">RFID3</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/rfid/rfidBuse.js"></script>		
