<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wxConfigList" checkbox="true" pagination="true" fitColumns="false" title="配置信息" actionUrl="wxConfigController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="流程状态"  field="bpmStatus"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="前端编码"  field="appCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备注"  field="appRemark"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="appID"  field="appId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="appsecret"  field="appSecret"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="appkey"  field="appKey"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="商户号"  field="mchId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="通知地址"  field="notifyUrl"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="GRANT_TYPE"  field="grantType"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备用1"  field="wxBy1"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备用2"  field="wxBy2"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备用3"  field="wxBy3"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备用4"  field="wxBy4"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备用5"  field="wxBy5"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="wxConfigController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="wxConfigController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wxConfigController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="wxConfigController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="wxConfigController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/conf/wxConfigList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });



//导入
function ImportXls() {
	openwindow('Excel导入', 'wxConfigController.do?upload', "wxConfigList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wxConfigController.do?exportXls","wxConfigList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wxConfigController.do?exportXlsByT","wxConfigList");
}

 </script>
