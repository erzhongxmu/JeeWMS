<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="waveToDownList" sortName="waveId" sortOrder="desc" checkbox="true" pagination="true" fitColumns="false" title="波次下架导出" actionUrl="waveToDownController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="货主"  field="cusCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="客户名称"  field="cusName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="波次号"  field="waveId"  query="true"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId"   query="true"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品名称"  field="goodsName"   query="true"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="客户订单号"  field="imCusCode"   query="true" queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="备注"  field="omBeizhu" query="true"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="仓位"  field="binId"   query="true"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="托盘"  field="tinId"   query="true"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="生产日期"  field="proData"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="数量"  field="baseGoodscount"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="基本单位"  field="baseUnit"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <%--<t:dgDelOpt title="删除" url="waveToDownController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="waveToDownController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="waveToDownController.do?goUpdate" funname="update"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="waveToDownController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="waveToDownController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wave/waveToDownList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'waveToDownController.do?upload', "waveToDownList");
}

//导出
function ExportXls() {
	JeecgExcelExport("waveToDownController.do?exportXls","waveToDownList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("waveToDownController.do?exportXlsByT","waveToDownList");
}

 </script>