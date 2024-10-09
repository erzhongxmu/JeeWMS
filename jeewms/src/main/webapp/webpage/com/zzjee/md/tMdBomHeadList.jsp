<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tMdBomHeadList" checkbox="true" fitColumns="false" title="BOM抬头" actionUrl="tMdBomHeadController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="流程状态"  field="bpmStatus"  hidden="true"  queryMode="single"  dictionary="bpm_status"  width="120"></t:dgCol>
   <t:dgCol title="物料"  field="matcode"  query="true"  queryMode="single"  dictionary="pop_material,matcode,matname,matcode,matname"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  query="true"  queryMode="single"  dictionary="status"  width="120"></t:dgCol>
   <t:dgCol title="文本"  field="text"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="物料名称"  field="matname"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="设计数量"  field="qty"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="计量单位"  field="units"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="成本中心代码"  field="prccode"  hidden="true"  queryMode="single"  dictionary="t_md_costcenter,cscode,csname"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="成本中心名称"  field="prcname"  hidden="true"  queryMode="single"  width="120"></t:dgCol>--%>
   <t:dgCol title="生效日期"  field="startdate"  formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="失效日期"  field="enddate"  formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <%--<t:dgDelOpt title="删除" url="tMdBomHeadController.do?doDel&id={id}"  urlclass="ace_button" urlfont="fa-trash-o"/>--%>
   <t:dgToolBar title="录入" icon="icon-add" url="tMdBomHeadController.do?goAdd" funname="add" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tMdBomHeadController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tMdBomHeadController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="icon-search" url="tMdBomHeadController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/scm/md/tMdBomHeadList.js"></script>
 <script type="text/javascript">

//导入
function ImportXls() {
	openwindow('Excel导入', 'tMdBomHeadController.do?upload', "tMdBomHeadList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tMdBomHeadController.do?exportXls","tMdBomHeadList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tMdBomHeadController.do?exportXlsByT","tMdBomHeadList");
}
 </script>
