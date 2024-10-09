<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="vYsddList" isShowSearch="true" checkbox="false" pagination="true" fitColumns="true" title="运输订单报表(默认显示当天)" actionUrl="vYsddController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="下单日期"  field="createDate"  formatter="yyyy-MM-dd"  query="true"  queryMode="group"  width="190"></t:dgCol>
   <t:dgCol title="送达时间"  field="sdsj"  formatter="yyyy-MM-dd hh:mm:ss"  query="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发货人"  field="fahuoren"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="货物"  field="huowu"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="件数"  field="hwshjs"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="重量"  field="zhongl"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="立方米"  field="tiji"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="收货人地址"  field="shrdh"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收货人"  field="shouhuoren"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="送货方式"  field="hwshfs"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="收货人电话"  field="shrsj"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="代收款金额"  field="daishouk"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="运费"  field="hwyf"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="货物总费用"  field="hwzfy"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="卸货费"  field="hwxhf"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="车号"  field="chehao"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="zhuangtai"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="回单备注"  field="ywhdbz"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="上午或下午"  field="by1"  query="true"  queryMode="single"  width="80"></t:dgCol>

   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" url="vYsddController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="vYsddController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="vYsddController.do?goUpdate" funname="update"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="vYsddController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="vYsddController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/tmsv/vYsddList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });



//导入
function ImportXls() {
	openwindow('Excel导入', 'vYsddController.do?upload', "vYsddList");
}

//导出
function ExportXls() {
	JeecgExcelExport("vYsddController.do?exportXls","vYsddList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("vYsddController.do?exportXlsByT","vYsddList");
}

 </script>
