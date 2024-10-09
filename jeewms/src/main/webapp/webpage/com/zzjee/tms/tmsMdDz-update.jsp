<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>客户地址</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="tmsMdDzController.do?doUpdate" >
			<input id="id" name="id" type="hidden" value="${tmsMdDzPage.id }"/>
			<input id="createName" name="createName" type="hidden" value="${tmsMdDzPage.createName }"/>
			<input id="createBy" name="createBy" type="hidden" value="${tmsMdDzPage.createBy }"/>
			<input id="createDate" name="createDate" type="hidden" value="${tmsMdDzPage.createDate }"/>
			<input id="updateName" name="updateName" type="hidden" value="${tmsMdDzPage.updateName }"/>
			<input id="updateBy" name="updateBy" type="hidden" value="${tmsMdDzPage.updateBy }"/>
			<input id="updateDate" name="updateDate" type="hidden" value="${tmsMdDzPage.updateDate }"/>
			<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${tmsMdDzPage.sysOrgCode }"/>
			<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${tmsMdDzPage.sysCompanyCode }"/>
			<input id="bpmStatus" name="bpmStatus" type="hidden" value="${tmsMdDzPage.bpmStatus }"/>
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">用户:</label>
		     	 <input id="username" name="username" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsMdDzPage.username}'/>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">联系人:</label>
		     	 <input id="lianxiren" name="lianxiren" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsMdDzPage.lianxiren}'/>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">联系电话:</label>
		     	 <input id="dianhua" name="dianhua" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsMdDzPage.dianhua}'/>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">详细地址:</label>
		     	 <input id="xiangxidizhi" name="xiangxidizhi" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsMdDzPage.xiangxidizhi}'/>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">省份:</label>
		     	 <input id="shengfen" name="shengfen" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsMdDzPage.shengfen}'/>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">城市:</label>
		     	 <input id="chengshi" name="chengshi" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsMdDzPage.chengshi}'/>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">区域:</label>
		     	 <input id="quyu" name="quyu" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsMdDzPage.quyu}'/>
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">默认地址:</label>
					<t:dictSelect field="morendizhi" type="radio"   typeGroupCode="sf_yn"  defaultVal="${tmsMdDzPage.morendizhi}" hasLabel="false"  title="默认地址"></t:dictSelect>     
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">是否可用:</label>
					<t:dictSelect field="zhuangtai" type="radio"   typeGroupCode="sf_yn"  defaultVal="${tmsMdDzPage.zhuangtai}" hasLabel="false"  title="是否可用"></t:dictSelect>     
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">地址类型:</label>
		     	 <input id="dizhileixing" name="dizhileixing" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsMdDzPage.dizhileixing}'/>
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/tms/tmsMdDz.js"></script>		
