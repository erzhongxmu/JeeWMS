<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="rpWmHisStockKuList" checkbox="true" pagination="true" fitColumns="false" title="rp_wm_his_stock_ku" actionUrl="rpWmHisStockKuController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="结余日期"  field="hisDate"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="货主"  field="cusCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="中文全称"  field="zhongWenQch"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="储位"  field="kuWeiBianMa"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="托盘"  field="binId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="商品"  field="goodsId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="商品名称"  field="shpMingCheng"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="数量"  field="count"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="单位"  field="baseUnit"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="zhlKg"  field="zhlKg"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="拆零数量"  field="chlShl"    queryMode="group"  width="120"></t:dgCol>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" url="rpWmHisStockKuController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="rpWmHisStockKuController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="rpWmHisStockKuController.do?goUpdate" funname="update"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="rpWmHisStockKuController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="rpWmHisStockKuController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/report/rpWmHisStockKuList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'rpWmHisStockKuController.do?upload', "rpWmHisStockKuList");
}

//导出
function ExportXls() {
	JeecgExcelExport("rpWmHisStockKuController.do?exportXls","rpWmHisStockKuList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("rpWmHisStockKuController.do?exportXlsByT","rpWmHisStockKuList");
}

 </script>