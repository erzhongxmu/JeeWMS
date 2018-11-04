<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmDayCostConfList" pageSize="100" checkbox="true" pagination="true" fitColumns="false" title="计费日期配置" actionUrl="wmDayCostConfController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="计费日期"  field="costDate" formatter="yyyy-MM-dd"  query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="是否已计费"  field="costSf"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="180"></t:dgCol>
   <t:dgDelOpt title="删除" url="wmDayCostConfController.do?doDel&id={id}" urlclass="ace_button"   urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="wmDayCostConfController.do?goAdd" funname="add"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wmDayCostConfController.do?goUpdate" funname="update"></t:dgToolBar> --%>
      <t:dgFunOpt title="核算费用" funname="docount(id)"  urlclass="ace_button"  urlfont="	fa-print" exp="costSf#ne#Y"/>

      <t:dgToolBar title="批量核算"   url="wmDayCostConfController.do?doBatchhs" funname="dosttALLSelect"></t:dgToolBar>

   <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmDayCostConfController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="wmDayCostConfController.do?goUpdate" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wmDayCostConfList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 function dosttALLSelect(){
	 
	 
	 
	 var rows = $('#wmDayCostConfList').datagrid('getSelections');
	 for(var i=0; i<rows.length; i++){
		 var url = "wmDayCostConfController.do?doCount&id="+rows[i].id;
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
// 						tip("成功");
				       
					}		
				}
			});	

	 }
	 $('#wmDayCostConfList').datagrid('reload',{});
	 
 }
 function docount(id){
		var url = "wmDayCostConfController.do?doCount&id="+id;
		
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
					tip("成功");
			        $('#wmDayCostConfList').datagrid('reload',{});
				}		
			}
		});
	}
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmDayCostConfController.do?upload', "wmDayCostConfList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmDayCostConfController.do?exportXls","wmDayCostConfList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmDayCostConfController.do?exportXlsByT","wmDayCostConfList");
}

 </script>