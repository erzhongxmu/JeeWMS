<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="waveToDownList" checkbox="true" pagination="true" fitColumns="false" title="波次下架" actionUrl="waveToDownController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100"></t:dgCol>

      <t:dgCol title="创建人登录名称"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="货主"  field="cusCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="客户名称"  field="cusName"  query="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="波次号"  field="waveId"    query="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId"   query="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品名称"  field="goodsName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="单号"  field="imCusCode"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="仓位"  field="binId"   query="true"  queryMode="single" width="120"></t:dgCol>
    <t:dgCol title="托盘"  field="tinId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="生产日期"  field="proData"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="数量"  field="baseGoodscount"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="出库备注"  field="omBeiZhu"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="基本单位"  field="baseUnit"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="一次下架容器"  field="firstRq"  query="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="分拣容器"  field="secondRq"    queryMode="group"  width="120"></t:dgCol>
    <%--<t:dgCol title="by1"  field="by1"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="by2"  field="by2"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="by3"  field="by3"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="by4"  field="by4"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="by5"  field="by5"    queryMode="group"  width="120"></t:dgCol>--%>
      <t:dgFunOpt title="波次拣货单" funname="doprint(waveId)"  urlclass="ace_button"   exp="firstRq#ne#已打印"   />

      <%--<t:dgDelOpt title="删除" url="waveToDownController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="waveToDownController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="waveToDownController.do?goUpdate" funname="update"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="waveToDownController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="icon-search" url="waveToDownController.do?goUpdate" funname="detail"></t:dgToolBar>
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

 function doprint(id){
     var url = "waveToDownController.do?doPrintpage&waveid="+id;
     createdetailwindow(" 波次拣货单", url, 1200, 800);

     // window.open(url);
 }

//导入
function ImportXls() {
  openwindow('Excel导入', 'waveToDownController.do?upload', "waveToDownList");
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
