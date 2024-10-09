<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>PLC指令</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmsPlcController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${wmsPlcPage.id }"/>
		<input id="createName" name="createName" type="hidden" value="${wmsPlcPage.createName }"/>
		<input id="createBy" name="createBy" type="hidden" value="${wmsPlcPage.createBy }"/>
		<input id="createDate" name="createDate" type="hidden" value="${wmsPlcPage.createDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${wmsPlcPage.updateName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${wmsPlcPage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${wmsPlcPage.updateDate }"/>
		<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${wmsPlcPage.sysOrgCode }"/>
		<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${wmsPlcPage.sysCompanyCode }"/>
		<input id="bpmStatus" name="bpmStatus" type="hidden" value="${wmsPlcPage.bpmStatus }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							PLCIP:
						</label>
					</td>
					<td class="value">
					     	 <input id="plcIp" name="plcIp" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">PLCIP</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							PLC端口:
						</label>
					</td>
					<td class="value">
					     	 <input id="plcPort" name="plcPort" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">PLC端口</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							PLC型号:
						</label>
					</td>
					<td class="value">
					     	 <input id="plcType" name="plcType" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">PLC型号</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							指令备注:
						</label>
					</td>
					<td class="value">
					     	 <input id="comRemark" name="comRemark" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">指令备注</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							执行时间:
						</label>
					</td>
					<td class="value">
					     	 <input id="comTime" name="comTime" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">执行时间</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							执行顺序:
						</label>
					</td>
					<td class="value">
					     	 <input id="comSeq" name="comSeq" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">执行顺序</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							指令集:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="comCons" name="comCons" 
						  	 ignore="ignore"
						  	 ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">指令集</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备用1:
						</label>
					</td>
					<td class="value">
					     	 <input id="remark1" name="remark1" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备用1</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							指令编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="comNo" name="comNo" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">指令编号</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单步参数1:
						</label>
					</td>
					<td class="value">
					     	 <input id="query01" name="query01" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">单步参数1</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单步参数2:
						</label>
					</td>
					<td class="value">
					     	 <input id="query02" name="query02" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">单步参数2</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单步时间:
						</label>
					</td>
					<td class="value">
					     	 <input id="setpTime" name="setpTime" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">单步时间</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							步数:
						</label>
					</td>
					<td class="value">
					     	 <input id="setpNum" name="setpNum" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">步数</label>
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
  <script src = "webpage/com/zzjee/plc/wmsPlc.js"></script>		
