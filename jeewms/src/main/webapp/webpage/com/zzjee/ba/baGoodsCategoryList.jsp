<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="baGoodsCategoryList" checkbox="true" pagination="false" treegrid="true" treeField="categoryName" fitColumns="false" title="商品类目" actionUrl="baGoodsCategoryController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createTime" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateTime" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="类目编码"  field="categoryCode"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="类目名称"  field="categoryName"   query="true" queryMode="single"  width="220"></t:dgCol>
    <t:dgCol title="类目级别"  field="categoryLevel"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="父级目录"  field="pid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="是否为顶级目录"  field="topNode"  hidden="true"  queryMode="group" dictionary="yes_no" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="baGoodsCategoryController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="baGoodsCategoryController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="baGoodsCategoryController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="baGoodsCategoryController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="baGoodsCategoryController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/ba/baGoodsCategoryList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
		$("#baGoodsCategoryList").treegrid({
 				 onExpand : function(row){
 					var children = $("#baGoodsCategoryList").treegrid('getChildren',row.id);
 					 if(children.length<=0){
 					 	row.leaf=true;
 					 	$("#baGoodsCategoryList").treegrid('refresh', row.id);
 					 }
 				}
 		});
 });



//导入
function ImportXls() {
	openwindow('Excel导入', 'baGoodsCategoryController.do?upload', "baGoodsCategoryList");
}

//导出
function ExportXls() {
	JeecgExcelExport("baGoodsCategoryController.do?exportXls","baGoodsCategoryList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("baGoodsCategoryController.do?exportXlsByT","baGoodsCategoryList");
}

/**
 * 获取表格对象
 * @return 表格对象
 */
function getDataGrid(){
	var datagrid = $('#'+gridname);
	return datagrid;
}
 </script>
