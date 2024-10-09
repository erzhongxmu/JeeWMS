<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="fxjOtherLoginList" checkbox="true" pagination="true" fitColumns="false" title="第三方登录" actionUrl="fxjOtherLoginController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="流程状态"  field="bpmStatus"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="用户名"  field="username"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="姓名"  field="realname"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="三方id"  field="otherid"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="三方1"  field="otherre1"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="三方2"  field="otherre2"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="三方3"  field="otherre3"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="三方4"  field="otherre4"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="登录类型"  field="otherType"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="fxjOtherLoginController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="fxjOtherLoginController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="fxjOtherLoginController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="fxjOtherLoginController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="fxjOtherLoginController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/conf/fxjOtherLoginList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });



//导入
function ImportXls() {
	openwindow('Excel导入', 'fxjOtherLoginController.do?upload', "fxjOtherLoginList");
}

//导出
function ExportXls() {
	JeecgExcelExport("fxjOtherLoginController.do?exportXls","fxjOtherLoginList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("fxjOtherLoginController.do?exportXlsByT","fxjOtherLoginList");
}

 </script>
