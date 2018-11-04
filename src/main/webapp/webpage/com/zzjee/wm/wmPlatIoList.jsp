<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmPlatIoList" checkbox="false" pagination="true" fitColumns="false" title="月台进出" actionUrl="wmPlatIoController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
    <t:dgCol title="车号"  field="carno"   query="true" queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="单据编号"  field="docId"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="月台编号"  field="platId"    queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="进入时间"  field="inData" formatter="yyyy-MM-dd hh:mm:ss"  query="false" queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="驶出时间"  field="outData" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="月台状态"  field="platSta"   query="false" queryMode="single"  width="70"></t:dgCol>
    <t:dgCol title="备注"  field="platBeizhu"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="计划进入时间"  field="planIndata" formatter="yyyy-MM-dd hh:mm:ss"  query="false" queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="计划驶出时间"  field="planOutdata" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
<%--     <t:dgCol title="月台操作"  field="platOper"    queryMode="single"  width="80"></t:dgCol> --%>
   
   <t:dgConfOpt exp="platSta#eq#计划中"  title="占用" url="wmPlatIoController.do?doOnplat&id={id}" urlclass="ace_button"  message="确定要占用此月台" urlfont="fa-plus-circle"/>
    <t:dgConfOpt exp="platSta#eq#占用"  title="释放" url="wmPlatIoController.do?doOutplat&id={id}" urlclass="ace_button"  message="确定要释放此月台" urlfont="fa-minus-circle"/>
   <t:dgToolBar title="录入" icon="icon-add" url="wmPlatIoController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wmPlatIoController.do?goUpdate" funname="update"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmPlatIoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="wmPlatIoController.do?goUpdate" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wmPlatIoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmPlatIoController.do?upload', "wmPlatIoList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmPlatIoController.do?exportXls","wmPlatIoList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmPlatIoController.do?exportXlsByT","wmPlatIoList");
}

 </script>