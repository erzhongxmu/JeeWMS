<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车辆管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="tmsMdCheliangController.do?doAdd" >
			<input id="id" name="id" type="hidden" value="${tmsMdCheliangPage.id }">
			<input id="createName" name="createName" type="hidden" value="${tmsMdCheliangPage.createName }">
			<input id="createBy" name="createBy" type="hidden" value="${tmsMdCheliangPage.createBy }">
			<input id="createDate" name="createDate" type="hidden" value="${tmsMdCheliangPage.createDate }">
			<input id="updateName" name="updateName" type="hidden" value="${tmsMdCheliangPage.updateName }">
			<input id="updateBy" name="updateBy" type="hidden" value="${tmsMdCheliangPage.updateBy }">
			<input id="updateDate" name="updateDate" type="hidden" value="${tmsMdCheliangPage.updateDate }">
			<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${tmsMdCheliangPage.sysOrgCode }">
			<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${tmsMdCheliangPage.sysCompanyCode }">
			<input id="bpmStatus" name="bpmStatus" type="hidden" value="${tmsMdCheliangPage.bpmStatus }">
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">车号:</label>
					     	 <input id="chepaihao" name="chepaihao" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">车型:</label>
					     	 <input id="chexing" name="chexing" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">最大体积:</label>
					     	 <input id="zuidatiji" name="zuidatiji" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">载重:</label>
					     	 <input id="zaizhong" name="zaizhong" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">载人数:</label>
					     	 <input id="zairen" name="zairen" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">准驾驾照:</label>
					     	 <input id="jiazhao" name="jiazhao" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">姓名:</label>
				<input id="zhuangtai" name="zhuangtai" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />

 		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">电话:</label>
					     	 <input id="beizhu" name="beizhu" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">默认司机:</label>
					     	 <input id="username" name="username" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />


				<span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">车牌号:</label>
					     	 <input id="gpsid" name="gpsid" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
		      <span class="Validform_checktip"></span>
		    </div>			<div class="form">
		      <label class="Validform_label">区域:</label>
					     	 <input id="quyu" name="quyu" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/tms/tmsMdCheliang.js"></script>
