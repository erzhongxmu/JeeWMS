<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid pageSize="500" name="wvStockSttBinList" checkbox="true" pagination="true" fitColumns="false" title="储位盘点" actionUrl="wvStockSttBinController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="createDate"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="createName"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="createBy"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
       <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
    <t:dgCol title="库存类型"  field="kuctype"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="储位"  field="kuWeiBianMa"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="托盘"  field="binId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="货主"  field="cusCode"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="货主名称"  field="zhongWenQch"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品"  field="goodsId"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="数量"  field="goodsQua"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="品名"  field="shpMingCheng"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="生产日期"  field="goodsProData"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="保质期"  field="bzhiQi"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="单位"  field="goodsUnit"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="盘点状态"  field="sttSta"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="移动状态"  field="moveSta"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="最后移动时间"  field="lastMove" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>

       <t:dgFunOpt title="生成" funname="dostt(id)"  urlclass="ace_button"  exp="sttSta#ne#计划中"  />
    <t:dgToolBar title="批量生成"   url="wvStockSttBinController.do?doBatchStt" funname="dosttALLSelect"></t:dgToolBar>
<%--    <t:dgDelOpt title="删除" url="wvStockSttBinController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="wvStockSttBinController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wvStockSttBinController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wvStockSttBinController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="wvStockSttBinController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wvStockSttBinList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
 
 function dosttALLSelect(){

	 var ids = [];
	 var rows = $('#wvStockSttBinList').datagrid('getSelections');
	 for(var i=0; i<rows.length; i++){
		 if(rows[i].sttSta=="计划中"){
		
		 }else{
			 var url = "wvStockSttBinController.do?dostt&id="+rows[i].id+"&stttype=02";
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
     $('#wvStockSttBinList').datagrid('reload',{});
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
		var url = "wvStockSttBinController.do?dostt&id="+id+"&stttype=02";
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
			        $('#wvStockSttBinList').datagrid('reload',{});
				}		
			}
		});
	}
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wvStockSttBinController.do?upload', "wvStockSttBinList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wvStockSttBinController.do?exportXls","wvStockSttBinList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wvStockSttBinController.do?exportXlsByT","wvStockSttBinList");
}

 </script>