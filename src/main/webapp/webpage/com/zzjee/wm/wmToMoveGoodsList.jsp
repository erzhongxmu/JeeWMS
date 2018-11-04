<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmToMoveGoodsList" checkbox="true" pagination="true" fitColumns="false" title="库存转移" actionUrl="wmToMoveGoodsController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"    queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
<%--     <t:dgCol title="原始单据类型"  field="orderTypeCode"  hidden="true"  quefryMode="group"  width="120"></t:dgCol> --%>
<%--     <t:dgCol title="原始单据编码"  field="orderId"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
<%--     <t:dgCol title="原始单据行项目"  field="orderIdI"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
    <t:dgCol title="商品编码"  field="goodsId"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品名称"  field="goodsName"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="数量"  field="goodsQua" extendParams="editor:'text'"    queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="基本单位数量"  field="baseGoodscount" extendParams="editor:'text'"    queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="生产日期"  field="goodsProData" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>
      <t:dgCol title="到生产日期"  field="toGoodsProData" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>

      <t:dgCol title="单位"  field="baseUnit"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="客户编码"  field="cusCode"   query="true" queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="客户名称"  field="cusName"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="源托盘"  field="tinFrom"   query="true" queryMode="single"  width="70"></t:dgCol>
    <t:dgCol title="到托盘"  field="tinId"  extendParams="editor:'text'"   query="true" queryMode="single"  width="70"></t:dgCol>
    <t:dgCol title="源储位"  field="binFrom"   query="true" queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="到储位"  field="binTo"   extendParams="editor:'text'"  query="true" queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="状态"  field="moveSta"    queryMode="group"  width="50"></t:dgCol>
      <t:dgCol title="执行状态"  field="runSta" query="true"  queryMode="single"  width="50"></t:dgCol>

      <t:dgCol title="转移客户"  field="toCusCode"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="转移客户名称"  field="toCusName"   query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
   <t:dgDelOpt title="删除" url="wmToMoveGoodsController.do?doDel&id={id}" urlclass="ace_button"  exp="moveSta#ne#已完成"  urlfont="fa-trash-o"/>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="wmToMoveGoodsController.do?goAdd" funname="add"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wmToMoveGoodsController.do?goUpdate" funname="update" ></t:dgToolBar>
      <%--<t:dgToolBar title="质押" icon="icon-edit" url="wmToMoveGoodsController.do?goUpdate" funname="update" ></t:dgToolBar>--%>


      <t:dgToolBar title="选择批量更改" icon="icon-edit"  funname="editRow"></t:dgToolBar>
      <t:dgToolBar  title="批量保存" icon="icon-save" url="wmToMoveGoodsController.do?saveRows" funname="saveData"></t:dgToolBar>
      <t:dgToolBar   title="取消批量更改" icon="icon-undo" funname="reject"></t:dgToolBar>
      <t:dgToolBar title="批量确定转移"     funname="dobatchUpdate"></t:dgToolBar>

    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmToMoveGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="wmToMoveGoodsController.do?goUpdate" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wmToMoveGoodsList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });


 function dobatchUpdate(){
     var  moveSta = "已完成";

     var ids = [];
     var rows = $('#wmToMoveGoodsList').datagrid('getSelections');

     for(var i=0; i<rows.length; i++){
         if(rows[i].moveSta=="计划中"){
             ids.push(rows[i].id);
         }
     }
     if(ids.size<=0){
         alert("请选择数据");
     }else{
         var url = "wmToMoveGoodsController.do?doBatchUpdate&ids="+ids+"&moveSta="+moveSta;
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
                     tip("批量设置状态已完成成功");
                     $('#wmToMoveGoodsList').datagrid('reload',{});
                 }
             }
         });
     }
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
             result["wmtomoverows["+i+"]."+d]=rows[i][d];
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
	openuploadwin('Excel导入', 'wmToMoveGoodsController.do?upload', "wmToMoveGoodsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmToMoveGoodsController.do?exportXls","wmToMoveGoodsList");
}

 </script>