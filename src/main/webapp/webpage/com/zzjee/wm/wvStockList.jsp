<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid  pageSize="500"  name="wvStockList" checkbox="true" pagination="true" fitColumns="false" title="生成盘点单" actionUrl="wvStockController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
       <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
        <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="移动日期"  field="lastMove" formatter="yyyy-MM-dd hh:mm:ss" query="true"  queryMode="group"  width="120"></t:dgCol>
       
    <t:dgCol title="库存类型"  field="kuctype"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="储位"  field="kuWeiBianMa"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="托盘"  field="binId"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="货主"  field="cusCode"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="货主名称"  field="zhongWenQch"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品数量"  field="goodsQua"  query="true"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="商品名称"  field="shpMingCheng"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="生产日期"  field="goodsProData" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="保质期"  field="bzhiQi"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="单位"  field="goodsUnit"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="状态"  field="sttSta"    queryMode="group"  width="50"></t:dgCol>
    <t:dgFunOpt title="生成" funname="dostt(id)"  urlclass="ace_button"  exp="sttSta#ne#计划中"  />
    <t:dgToolBar title="批量生成"   url="wvStockController.do?doBatchStt" funname="dosttALLSelect"></t:dgToolBar>
   
<%--    <t:dgDelOpt title="删除" url="wvStockController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="wvStockController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wvStockController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wvStockController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="wvStockController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
    <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>

    <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wvStockList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){

 });
 
 function dosttALLSelect(){

	 var ids = [];
	 var rows = $('#wvStockList').datagrid('getSelections');
	 for(var i=0; i<rows.length; i++){
		 if(rows[i].sttSta=="计划中"){
		
		 }else{
			 var url = "wvStockController.do?dostt&id="+rows[i].id+"&stttype=01";
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
// 							tip("添加到盘点清单成功");
					 
						}		
					}
				});
			 	ids.push(rows[i].id);
		 }
	 }
     $('#wvStockList').datagrid('reload',{});
// 		var url = "wvStockController.do?doBatchStt&ids="+ids;
// 		$.ajax({
// 			async : false,
// 			cache : false,
// 			type : 'POST',
// 			url : url,// 请求的action路径
// 			error : function() {// 请求失败处理函数
// 			},
// 			success : function(data) {
// 				 var d = $.parseJSON(data);
// 				if (d.success) {
// 					tip("添加到盘点清单成功");
// 			        $('#wvStockList').datagrid('reload',{});
// 				}		
// 			}
// 		});
	}
 
 function dostt(id){
		var url = "wvStockController.do?dostt&id="+id+"&stttype=01";
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
					tip("添加到盘点清单成功");
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