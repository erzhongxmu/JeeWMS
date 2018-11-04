<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid pageSize="50"  name="wmOmQmIList" checkbox="true" pagination="true" fitColumns="false" title="下架任务" actionUrl="wmOmQmIController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="更新人"  field="updateName"     queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd hh:mm:ss"    queryMode="group"  width="130"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
    <t:dgCol title="出库通知单"  field="omNoticeId" query="true"   queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="出库通知行项目"  field="iomNoticeItem"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId"   query="true"     queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="商品名称"  field="goodsName"   query="true"    queryMode="single"  width="200"></t:dgCol>
      <%--     <t:dgCol title="出货数量"  field="omQuat"    queryMode="group"  width="120"></t:dgCol> --%>
    <t:dgCol title="客户订单号"  field="imCusCode"   query="true" queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="备注"  field="omBeizhu" query="true"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="数量"  field="qmOkQuat"   extendParams="editor:'text'"  queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="单位"  field="goodsUnit"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="基本单位数量"  field="baseGoodscount"   extendParams="editor:'text'"  queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="基本单位"  field="baseUnit"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="备注"  field="itemText"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="生产日期"  field="proData"      queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="托盘"  field="tinId"   query="true"   queryMode="single"  width="70"></t:dgCol>
   
<%--     <t:dgCol title="批次"  field="goodsBatch"    queryMode="group"  width="120"></t:dgCol> --%>
    <t:dgCol title="仓位"  field="binId"   query="true"   queryMode="single"  width="90"></t:dgCol>
<%--     <t:dgCol title="体积"  field="tinTj"    queryMode="group"  width="70"></t:dgCol> --%>
<%--     <t:dgCol title="重量"  field="tinZhl"    queryMode="group"  width="70"></t:dgCol> --%>
    <t:dgCol title="下架状态"  field="binSta"   query="true"    queryMode="single" dictionary="sf_yn" width="50"></t:dgCol>
    <t:dgCol title="货主"  field="cusCode"   query="true"    queryMode="single" dictionary="mv_cus,cus_code,cus_name"  width="120"></t:dgCol>
<%--     <t:dgCol title="温度"  field="recDeg"    queryMode="group"  width="120"></t:dgCol> --%>
    <t:dgCol title="波次号"  field="waveId"   query="true"    queryMode="single"  width="120"></t:dgCol>

<%--     <t:dgCol title="客户名称"  field="cusName"    queryMode="group"  width="120"></t:dgCol> --%>
<%--     <t:dgCol title="商品名称"  field="goodsName"    queryMode="group"  width="120"></t:dgCol> --%>
  <t:dgFunOpt title="下架" funname="wmtodown(id)"  urlclass="ace_button"   exp="binSta#eq#N"/>
<%--    <t:dgDelOpt title="删除" url="wmOmQmIController.do?doDel&id={id}"  exp="binSta#ne#Y" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="wmOmQmIController.do?goAdd" funname="add"></t:dgToolBar> --%>
    <t:dgToolBar title="编辑" icon="icon-edit" url="wmOmQmIController.do?goUpdate" funname="update"></t:dgToolBar>
<%--          <t:dgFunOpt title="任务确认" funname="taskassign(id)"  urlclass="ace_button"  exp="binSta#eq#I"  /> --%>
      <t:dgToolBar title="批量下架"    funname="doalltodown"></t:dgToolBar>
      <t:dgToolBar title="选择批量更改" icon="icon-edit"  funname="editRow"></t:dgToolBar>
      <t:dgToolBar  title="批量保存" icon="icon-save" url="wmOmQmIController.do?saveRows" funname="saveData"></t:dgToolBar>
      <t:dgToolBar   title="取消批量更改" icon="icon-undo" funname="reject"></t:dgToolBar>
      <%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmOmQmIController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="wmOmQmIController.do?goUpdate" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wmOmQmIList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function doalltodown(){

     var ids = [];
     var rows = $('#wmOmQmIList').datagrid('getSelections');
     for(var i=0; i<rows.length; i++){
         wmtodown(rows[i].id);
     }

 }


 function wmtodown(id){
		var url = "wmOmQmIController.do?dotodown&id="+id;
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			url : url,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				 var d = $.parseJSON(data);
				if (d.success) {
					tip("下架成功");
			        $('#wmOmQmIList').datagrid('reload',{});
				}		
			}
		});
	}


 //保存数据
 function saveData(title,addurl,gname){
     if(!endEdit(gname))
         return false;
     var rows=$('#'+gname).datagrid("getChanges","inserted");
     var uprows=$('#'+gname).datagrid("getChanges","updated");
     rows=rows.concat(uprows);
     if(rows.length<=0){
         tip("没有需要保存的数据！")
         return false;
     }
     var result={};
     for(var i=0;i<rows.length;i++){
         for(var d in rows[i]){
             result["omqmrows["+i+"]."+d]=rows[i][d];
         }
     }
     $.ajax({
         url:"<%=basePath%>/"+addurl,
         type:"post",
         data:result,
         dataType:"json",
         success:function(data){
             tip(data.msg);
             if(data.success){
                 reloadTable();
             }
         }
     })
 }
 //结束编辑
 function endEdit(gname){
     var  editIndex = $('#'+gname).datagrid('getRows').length-1;
     for(var i=0;i<=editIndex;i++){
         if($('#'+gname).datagrid('validateRow', i))
             $('#'+gname).datagrid('endEdit', i);
         else
             return false;
     }
     return true;
 }
 //编辑行
 function editRow(title,addurl,gname){
     var rows=$('#'+gname).datagrid("getChecked");
     if(rows.length==0){
         tip("请选择条目");
         return false;
     }
     for(var i=0;i<rows.length;i++){
         var index= $('#'+gname).datagrid('getRowIndex', rows[i]);
         $('#'+gname).datagrid('beginEdit', index);

     }
 }

 //取消编辑
 function reject(title,addurl,gname){
     $('#'+gname).datagrid('clearChecked');
     $('#'+gname).datagrid('rejectChanges');


 }

 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmOmQmIController.do?upload', "wmOmQmIList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmOmQmIController.do?exportXls","wmOmQmIList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmOmQmIController.do?exportXlsByT","wmOmQmIList");
}

 </script>