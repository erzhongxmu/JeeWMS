<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tmsYufeiConfList" checkbox="true" pagination="true" fitColumns="false" title="运费配置" actionUrl="tmsYufeiConfController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="流程状态"  field="bpmStatus"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="配送点"  field="peisondian"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="运费类型"  field="yfType"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="运费名称"  field="yfTypeName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="运费单价"  field="yfPrice"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备注1"  field="yfBz1"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备注2"  field="yfBz2"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备注3"  field="yfBz3"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tmsYufeiConfController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tmsYufeiConfController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tmsYufeiConfController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tmsYufeiConfController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tmsYufeiConfController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/base/tmsYufeiConfList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });



//导入
function ImportXls() {
	openwindow('Excel导入', 'tmsYufeiConfController.do?upload', "tmsYufeiConfList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tmsYufeiConfController.do?exportXls","tmsYufeiConfList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tmsYufeiConfController.do?exportXlsByT","tmsYufeiConfList");
}

 </script>
