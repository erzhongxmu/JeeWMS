<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid   name="wvStockList" checkbox="true" pagination="true" fitColumns="false" title="生成库存转移任务" actionUrl="wvStockController.do?datagridkczy" idField="id" fit="false" queryMode="group">
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
       <t:dgCol title="操作" field="opt" width="80"></t:dgCol>
        <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="移动日期"  field="lastMove" formatter="yyyy-MM-dd hh:mm:ss" query="true"  queryMode="group"  width="120"></t:dgCol>
       
    <t:dgCol title="库存类型"  field="kuctype"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="储位"  field="kuWeiBianMa"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="托盘"  field="binId"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="货主"  field="cusCode"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="货主名称"  field="zhongWenQch"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品数量"  field="goodsQua"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="商品名称"  field="shpMingCheng"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="生产日期"  field="goodsProData" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="保质期"  field="bzhiQi"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="单位"  field="goodsUnit"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="状态"  field="moveSta"    queryMode="group"  width="50"></t:dgCol>

      <t:dgFunOpt title="生成托盘转移任务" funname="dostttpzy(id)"  urlclass="ace_button"  exp="moveSta#ne#计划中"  />
      <t:dgToolBar title="批量生成托盘转移任务"     funname="dosttALLSelect"></t:dgToolBar>
      <t:dgToolBar title="立即批量转移"     funname="dosttokALLSelect"></t:dgToolBar>
<%--    <t:dgDelOpt title="删除" url="wvStockController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="wvStockController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wvStockController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wvStockController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="wvStockController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>

    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
      <div name="searchColums1" style="float: left; padding-left: 0px;padding-top: 5px;">
          <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;" title="批量托盘">批量托盘为: </span>


          <%--<input type="text" name="batchbin" style="width: 100px; height: 30px;">--%>
          托盘：<input type="text" name="moveTinid"    class="form-control"   style="width: 100px; height: 30px;">

      </div>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wvStockList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){

 });


 function dosttokALLSelect() {
     var moveTinid;
     moveTinid = $('input[name="moveTinid"]').attr("value");
     if(moveTinid==""){
         alert("托盘不能为空");
     }else{
         dosttALLSelectbatch(moveTinid);
     }
 }

 function dosttALLSelect() {
     dosttALLSelectbatch("");
 }

 function dosttALLSelectbatch(moveTinid){

	 var ids = [];
	 var rows = $('#wvStockList').datagrid('getSelections');
	 for(var i=0; i<rows.length; i++){
		 if(rows[i].moveSta=="计划中"){
		
		 }else{
			 	ids.push(rows[i].id);
		 }
	 }
		var url = "wvStockController.do?doBatchStttpzy&ids="+ids+"&moveTinid="+moveTinid;
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
					tip("添加到托盘转移清单成功");
			        $('#wvStockList').datagrid('reload',{});
				}		
			}
		});
	}
 
 function dostttpzy(id){
		var url = "wvStockController.do?dostttpzy&id="+id;
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
					tip("添加到托盘转移清单成功");
			        $('#wvStockList').datagrid('reload',{});
				}		
			}
		});
	}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmToMoveGoodsController.do?upload', "wvStockList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wvStockController.do?exportXls","wvStockList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmToMoveGoodsController.do?exportXlsByT","wvStockList");
}

 </script>