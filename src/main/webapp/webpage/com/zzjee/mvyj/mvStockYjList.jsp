<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid sortName="resDate" sortOrder="asc" name="mvStockYjList" checkbox="true" pagination="true" fitColumns="false" title="效期预警" actionUrl="mvStockYjController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="库存类型"  field="kuctype"    queryMode="group"  width="80"></t:dgCol> 
     <t:dgCol title="商品编码"  field="goodsId"  query="true"   queryMode="single"   width="100"></t:dgCol>
         <t:dgCol title="客户商品编码"  field="shpBianmakh"  query="true"   queryMode="single"   width="100"></t:dgCol>
    <t:dgCol title="商品名称"  field="shpMingCheng"  query="true"   queryMode="single"  width="300"></t:dgCol>
    <t:dgCol title="基本数量"  field="baseGoodscount"    queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="基本单位"  field="baseUnit"    queryMode="group"  width="60"></t:dgCol>
<%--     <t:dgCol title="储位"  field="kuWeiBianMa"   query="true"  queryMode="single"  width="90"></t:dgCol> --%>
<%--     <t:dgCol title="托盘"  field="binId"  query="true"   queryMode="single"   width="60"></t:dgCol> --%>
    <t:dgCol title="客户编码"  field="cusCode"   query="true"  queryMode="single"   width="80"></t:dgCol>
    <t:dgCol title="客户名称"  field="zhongWenQch"   query="true"  queryMode="single"  width="120"></t:dgCol>
  
    <t:dgCol title="生产日期"  field="goodsProData"  query="true" formatter="yyyy-MM-dd"   queryMode="single"   width="90"></t:dgCol>
    <t:dgCol title="保质期天"  field="bzhiQi"    queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="到期日"  field="dqr"  query="true" formatter="yyyy-MM-dd"    queryMode="single"  width="90"></t:dgCol>
    <t:dgCol title="剩余天数"  field="resDate"    queryMode="group"  width="80"></t:dgCol>
      <t:dgCol title="剩余比例"  field="guoqiBili" query="true"   queryMode="group"  width="80"></t:dgCol>

      <%--     <t:dgCol title="上架次序"  field="shangJiaCiXu"    queryMode="group"  width="80"></t:dgCol> --%>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="mvStockYjController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="mvStockYjController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="mvStockYjController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="mvStockYjController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="mvStockYjController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar operationCode="export" title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/mvyj/mvStockYjList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'mvStockYjController.do?upload', "mvStockYjList");
}

//导出
function ExportXls() {
	JeecgExcelExport("mvStockYjController.do?exportXls","mvStockYjList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("mvStockYjController.do?exportXlsByT","mvStockYjList");
}

 </script>