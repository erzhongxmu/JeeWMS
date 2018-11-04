<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmDayCostList" autoLoadData="false" checkbox="true" pagination="true" fitColumns="false" title="欠费明细表" actionUrl="wmDayCostController.do?datagridqf" idField="id" pageSize="500" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="客户"  field="cusCode"   query="true" queryMode="single"    width="80"></t:dgCol>
        <t:dgCol title="费用日期"  field="costData" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="90"></t:dgCol>
    <t:dgCol title="客户名称"  field="cusName"    query="true" queryMode="single"   width="120"></t:dgCol>
    <t:dgCol title="费用"  field="costCode"   query="true" queryMode="single"  width="50"></t:dgCol>
    <t:dgCol title="费用名称"  field="costName"    query="true" queryMode="single"   width="90"></t:dgCol>

    <t:dgCol title="原价"  field="dayCostYj"    queryMode="group"  width="90"></t:dgCol>
    <t:dgCol title="不含税价"  field="dayCostBhs"    queryMode="group"  width="90"></t:dgCol>
    <t:dgCol title="税额"  field="dayCostSe"    queryMode="group"  width="90"></t:dgCol>
    <t:dgCol title="含税价"  field="dayCostHsj"    queryMode="group"  width="90"></t:dgCol>
    <t:dgCol title="费用来源"  field="costOri"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="备注"  field="beizhu"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="状态"  field="costSta"   query="true" queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="计费数量"  field="costSl"    queryMode="group"  width="90"></t:dgCol>
    <t:dgCol title="数量单位"  field="costUnit"    queryMode="group"  width="90"></t:dgCol>
      <t:dgCol title="是否结算"  field="costJs"    queryMode="group"  width="90"></t:dgCol>

      <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="wmDayCostController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="wmDayCostController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wmDayCostController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmDayCostController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>

      <t:dgToolBar title="收款"  icon="icon-remove"   funname="pljq"></t:dgToolBar>
      <%--<t:dgToolBar title="批量反结清"  icon="icon-remove"   funname="plfjq"></t:dgToolBar>--%>
      <t:dgToolBar title="查看" icon="icon-search" url="wmDayCostController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wmDayCostList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 function pljq(){

         var rows = $('#wmDayCostList').datagrid('getSelections');
         for(var i=0; i<rows.length; i++){
             var url = "wmDayCostController.do?dopljq&id="+rows[i].id;

             $.ajax({
                 async : false,
                 cache : false,
                 type : 'POST',
                 url : url,// 请求的action路径
                 error : function() {// 请求失败处理函数
                 },
                 success : function(data) {
                     var d = $.parseJSON(data);
                     if (d.success) {
							tip("费用结清成功");

                     }
                 }
             });



         }

 }
 function plfjq(){

     var rows = $('#wmDayCostList').datagrid('getSelections');
     for(var i=0; i<rows.length; i++){
         var url = "wmDayCostController.do?doplfjq&id="+rows[i].id;

         $.ajax({
             async : false,
             cache : false,
             type : 'POST',
             url : url,// 请求的action路径
             error : function() {// 请求失败处理函数
             },
             success : function(data) {
                 var d = $.parseJSON(data);
                 if (d.success) {
                     tip("费用反结清成功");

                 }
             }
         });



     }

 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmDayCostController.do?upload', "wmDayCostList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmDayCostController.do?exportXls","wmDayCostList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmDayCostController.do?exportXlsByT","wmDayCostList");
}

 </script>