<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wvDayCostSumList"  autoLoadData="false"  checkbox="true" pagination="true" fitColumns="false" title="按天合计费用" actionUrl="wvDayCostSumController.do?datagrid" idField="id"  pageSize="500" fit="true" queryMode="group">
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="费用日期"  field="costData" formatter="yyyy-MM-dd" query="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="客户"  field="cusCode"   query="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="客户名称"  field="cusName"  query="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="费用"  field="costCode"   query="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="费用名称"  field="costName"  query="true"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="数量"  field="costSl"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="单位"  field="costUnit"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="原价"  field="yuanj"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="不含税"  field="bhsj"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="税"  field="shuie"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="含税价"  field="hansj"    queryMode="group"  width="120"></t:dgCol>

<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="wvDayCostSumController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="wvDayCostSumController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wvDayCostSumController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wvDayCostSumController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="wvDayCostSumController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/bm/wvDayCostSumList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wvDayCostSumController.do?upload', "wvDayCostSumList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wvDayCostSumController.do?exportXls","wvDayCostSumList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wvDayCostSumController.do?exportXlsByT","wvDayCostSumList");
}

 </script>