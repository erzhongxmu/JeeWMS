<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="rpWmToUpGoodsList" checkbox="true" pagination="true" fitColumns="false" title="rp_wm_to_up_goods" actionUrl="rpWmToUpGoodsController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="原始单据编码"  field="orderId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="库位编码"  field="kuWeiBianMa"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="托盘码"  field="binId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="货主"  field="cusCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="中文全称"  field="zhongWenQch"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="goodsCode"  field="goodsCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="shpMingCheng"  field="shpMingCheng"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="生产日期"  field="goodsProData"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="bzhiQi"  field="bzhiQi"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="基本单位"  field="baseUnit"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="基本单位数量"  field="baseGoodscount"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="zhlKg"  field="zhlKg"    queryMode="group"  width="120"></t:dgCol>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" url="rpWmToUpGoodsController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="rpWmToUpGoodsController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="rpWmToUpGoodsController.do?goUpdate" funname="update"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="rpWmToUpGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="rpWmToUpGoodsController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/report/rpWmToUpGoodsList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });



//导入
function ImportXls() {
	openwindow('Excel导入', 'rpWmToUpGoodsController.do?upload', "rpWmToUpGoodsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("rpWmToUpGoodsController.do?exportXls","rpWmToUpGoodsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("rpWmToUpGoodsController.do?exportXlsByT","rpWmToUpGoodsList");
}

 </script>
