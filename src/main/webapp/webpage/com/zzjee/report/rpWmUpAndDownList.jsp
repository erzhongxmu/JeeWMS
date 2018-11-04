<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="rpWmUpAndDownList" checkbox="true" pagination="true" fitColumns="false" title="rp_wm_up_and_down" actionUrl="rpWmUpAndDownController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="createDate"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="orderId"  field="orderId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="kuWeiBianMa"  field="kuWeiBianMa"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="binId"  field="binId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="cusCode"  field="cusCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="zhongWenQch"  field="zhongWenQch"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="goodsId"  field="goodsId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="shpMingCheng"  field="shpMingCheng"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="goodsProData"  field="goodsProData"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="baseGoodscount"  field="baseGoodscount"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="baseUnit"  field="baseUnit"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="库位类型"  field="leixing"    queryMode="group"  width="120"></t:dgCol>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" url="rpWmUpAndDownController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="rpWmUpAndDownController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="rpWmUpAndDownController.do?goUpdate" funname="update"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="rpWmUpAndDownController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="rpWmUpAndDownController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/report/rpWmUpAndDownList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'rpWmUpAndDownController.do?upload', "rpWmUpAndDownList");
}

//导出
function ExportXls() {
	JeecgExcelExport("rpWmUpAndDownController.do?exportXls","rpWmUpAndDownList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("rpWmUpAndDownController.do?exportXlsByT","rpWmUpAndDownList");
}

 </script>