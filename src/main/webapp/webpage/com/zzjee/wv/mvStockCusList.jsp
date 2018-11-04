<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid  name="mvStockCusList" checkbox="true" pagination="true" fitColumns="false" title="客户库存" actionUrl="mvStockCusController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="库存类型"  field="kuctype"   query="true" queryMode="single"  width="50"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId"   query="true" queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="客户商品编码"  field="shpBianmakh"   query="true" queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="商品名称"  field="shpMingCheng"   query="true" queryMode="single"  width="300"></t:dgCol>
    <t:dgCol title="数量"  field="goodsQua" hidden="true"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="单位"  field="goodsUnit" hidden="true"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="库存数量"  field="baseGoodscount"    queryMode="group"  width="90"></t:dgCol>
      <t:dgCol title="重量KG"  field="zhongLiang"    queryMode="group"  width="90"></t:dgCol>

      <t:dgCol title="库存单位"  field="baseUnit"    queryMode="group"  width="90"></t:dgCol>
<%--     <t:dgCol title="储位编码"  field="kuWeiBianMa"   query="true" queryMode="single"  width="90"></t:dgCol> --%>
<%--     <t:dgCol title="托盘"  field="binId"   query="true" queryMode="single"  width="70"></t:dgCol> --%>
    <t:dgCol title="客户"  field="cusCode"   query="true" queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="客户名称"  field="zhongWenQch"    queryMode="group"  width="120"></t:dgCol>

<%--     <t:dgCol title="单位"  field="shlDanWei"    queryMode="group"  width="120"></t:dgCol> --%>
    <t:dgCol title="生产日期"  field="goodsProData" formatter="yyyy-MM-dd"  query="true" queryMode="single"  width="90"></t:dgCol>
    <t:dgCol title="保质期"  field="bzhiQi"    queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="到期日"  field="dqr"    queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="HITI"  field="hiti"    queryMode="group"  width="50"></t:dgCol>
        <t:dgCol title="库位类型"  field="kuWeiLeiXing"    queryMode="group"  width="50"></t:dgCol>
    
<%--     <t:dgCol title="取货次序"  field="quHuoCiXu"    queryMode="group"  width="90"></t:dgCol> --%>
<%--     <t:dgCol title="上架次序"  field="shangJiaCiXu"    queryMode="group"  width="90"></t:dgCol> --%>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="mvStockCusController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="mvStockCusController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="mvStockCusController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="mvStockCusController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="mvStockCusController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wv/mvStockCusList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'mvStockCusController.do?upload', "mvStockCusList");
}

//导出
function ExportXls() {
	JeecgExcelExport("mvStockCusController.do?exportXls","mvStockCusList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("mvStockCusController.do?exportXlsByT","mvStockCusList");
}

 </script>