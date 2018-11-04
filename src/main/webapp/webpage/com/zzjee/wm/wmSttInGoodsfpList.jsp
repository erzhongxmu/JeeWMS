<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmSttInGoodsList" pageSize="30" checkbox="true" pagination="true" fitColumns="false" title="库存盘点" actionUrl="wmSttInGoodsController.do?datagridfp" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
    <t:dgCol title="储位"  field="binId"   query="true" queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="托盘"  field="tinId"   query="true" queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品名称"  field="goodsName"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="数量"  field="goodsQua"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="单位"  field="goodsUnit"    queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="生产日期"  field="goodsProData" formatter="yyyy-MM-dd"  query="true" queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="批次"  field="goodsBatch"   query="true" queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="盘点数量"  field="sttQua"    queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="客户名称"  field="cusName"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="客户"  field="cusCode"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="盘点状态"  field="sttSta"   query="true"     width="120"></t:dgCol>

       <t:dgFunOpt title="复盘" funname="dosttfp(id)"  urlclass="ace_button"  exp="sttSta#eq#已完成"  />
<%--    <t:dgDelOpt title="删除" url="wmSttInGoodsController.do?doDel&id={id}" urlclass="ace_button" exp="sttSta#eq#计划中"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgFunOpt title="差异过账" funname="dorun(id)"  urlclass="ace_button"  exp="sttSta#eq#已完成"  /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="wmSttInGoodsController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wmSttInGoodsController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmSttInGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="wmSttInGoodsController.do?goUpdate" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wmSttInGoodsList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
 function dosttfp(id){
		var url = "wmSttInGoodsController.do?dostt&id="+id;
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
			        $('#wmSttInGoodsList').datagrid('reload',{});
				}		
			}
		});
	}  
 
 function dorun(id){
		var url = "wmSttInGoodsController.do?dorun&id="+id;
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
					tip("盘点过账成功");
			        $('#wmSttInGoodsList').datagrid('reload',{});
				}		
			}
		});
	}
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmSttInGoodsController.do?upload', "wmSttInGoodsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmSttInGoodsController.do?exportXls","wmSttInGoodsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmSttInGoodsController.do?exportXlsByT","wmSttInGoodsList");
}

 </script>