<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="rpPeriodInOutList" checkbox="false" autoLoadData="false" pagination="true" fitColumns="false" title="进销存统计表" actionUrl="rpPeriodInOutController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="期间选择"  field="createDate1" formatter="yyyy-MM-dd"    query="true"  queryMode="group"  width="1"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="流程状态"  field="bpmStatus"  hidden="true"  queryMode="single" dictionary="bpm_status" width="120"></t:dgCol>
    <t:dgCol title="期间"  field="datePeriod"     queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="用户名"  field="username"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品名称"  field="goodsName"   query="true" queryMode="single"  width="220"></t:dgCol>
    <t:dgCol title="单位"  field="goodsUnit"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="规格"  field="goodsGuige"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="期初数量"  field="goodsQc"    queryMode="single"  width="120"></t:dgCol>

    <t:dgCol title="入库数量"  field="goodsIn"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="出库数量"  field="goodsOut"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="期末数量"  field="goodsQm"    queryMode="single"  width="120"></t:dgCol>

    <t:dgCol title="现库存"  field="goodsNow"    queryMode="single"  width="120"></t:dgCol>


    <t:dgCol title="供应商编号"  field="supCode"   query="true" queryMode="single"  width="100"></t:dgCol>

    <t:dgCol title="供应商名称"  field="supName"   query="true" queryMode="single"  width="120"></t:dgCol>

   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" url="rpPeriodInOutController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="rpPeriodInOutController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="rpPeriodInOutController.do?goUpdate" funname="update"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="rpPeriodInOutController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="rpPeriodInOutController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 });



//导入
function ImportXls() {
	openwindow('Excel导入', 'rpPeriodInOutController.do?upload', "rpPeriodInOutList");
}

//导出
function ExportXls() {
	JeecgExcelExport("rpPeriodInOutController.do?exportXls","rpPeriodInOutList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("rpPeriodInOutController.do?exportXlsByT","rpPeriodInOutList");
}

 </script>
