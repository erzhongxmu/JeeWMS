<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid singleSelect="true" name="mvGoodsList" checkbox="true" pagination="true" fitColumns="false" title="商品视图" actionUrl="mvGoodsController.do?datagrid" idField="goodsCode" fit="true" queryMode="group">
    <t:dgCol title="货主"  field="cusCode"     queryMode="single"  width="50"></t:dgCol>
    <t:dgCol title="商品"  field="goodsCode"     queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="商品名"  field="goodsName"   query="true" queryMode="single"  width="280"></t:dgCol>
    <t:dgCol title="单位"  field="shlDanWei"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="温层"  field="cfWenCeng"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="mpDanCeng"  field="mpDanCeng"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="mpCengGao"  field="mpCengGao"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="shpTiaoMa"  field="shpTiaoMa"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="bzhiQi"  field="bzhiQi"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="chlShl"  field="chlShl"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="tiJiCm"  field="tiJiCm"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="zhlKg"  field="zhlKg"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="baseunit"  field="baseunit"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="mvGoodsController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="mvGoodsController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="mvGoodsController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="mvGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="mvGoodsController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/md/mvGoodsList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'mvGoodsController.do?upload', "mvGoodsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("mvGoodsController.do?exportXls","mvGoodsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("mvGoodsController.do?exportXlsByT","mvGoodsList");
}

 </script>