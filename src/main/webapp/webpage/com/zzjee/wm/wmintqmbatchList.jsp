<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div   class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmInQmIbatchList" checkbox="true" pagination="false" fitColumns="true" title="退货登记" actionUrl="wmImNoticeHController.do?datagridtbatch" idField="id" fit="true" queryMode="group">
   <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="到货通知单"  field="imNoticeId"  query="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsCode"  query="true"    queryMode="single"    width="100"></t:dgCol>
      <t:dgCol title="商品名称"  field="goodsName"  query="true"    queryMode="single"    width="250"></t:dgCol>

    <t:dgCol title="预约数量"  field="goodsCount"    queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="已登记数量"  field="goodsQmCount"    queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="单位"  field="goodsUnit"    queryMode="single"  width="50"></t:dgCol>
      
<%--    <t:dgDelOpt title="删除" url="wmInQmIController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
   <t:dgFunOpt title="验收" funname="wmim(id)"  urlclass="ace_button"   />

<%--    <t:dgToolBar title="录入" icon="icon-add" url="wmInQmIController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wmInQmIController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--   <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmInQmIController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%> 
<%--    <t:dgToolBar title="查看" icon="icon-search" url="wmInQmIController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
    </div>
</div>
<script type="text/javascript">
function wmim(id) {
    var url = "wmInQmIController.do?goAddBatch&id=" + id;
    // createdetailwindow()

    // function addbytab(){
    addOneTab("退货验收", url);
    // var url = "wmInQmIController.do?goAddBatch&id=" + id;
    // add('退货登记', url, "wmInQmIbatchList","680","450");
}
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'departController.do?upload', "departList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("departController.do?exportXls","departList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("departController.do?exportXlsByT","departList");
    }


</script>