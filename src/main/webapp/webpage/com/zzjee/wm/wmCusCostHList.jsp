<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmCusCostHList" checkbox="true" fitColumns="false" title="客户费用" actionUrl="wmCusCostHController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"     queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户编码"  field="cusCode"   query="true" queryMode="single" dictionary="mv_cus,cus_code,cus_name"  width="120"></t:dgCol>
   <t:dgCol title="合同编号"  field="cusHetongid"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开始日期"  field="beginDate" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结束日期"  field="endDate" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="cusBeizhu"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="附件"  field="fujian"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
<%--    <t:dgDelOpt title="删除" url="wmCusCostHController.do?doDel&id={id}"  urlclass="ace_button" urlfont="fa-trash-o"/> --%>
   <t:dgToolBar title="录入" icon="icon-add" url="wmCusCostHController.do?goAdd" funname="add" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wmCusCostHController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmCusCostHController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="wmCusCostHController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wmCusCostHList.js"></script>		
 <script type="text/javascript">
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmCusCostHController.do?upload', "wmCusCostHList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmCusCostHController.do?exportXls","wmCusCostHList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmCusCostHController.do?exportXlsByT","wmCusCostHList");
}
 </script>