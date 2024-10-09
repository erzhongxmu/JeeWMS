<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmToDownGoodsList"  checkbox="true" pagination="true" fitColumns="true" 
  title="波次复核" actionUrl="wmToDownGoodsController.do?datagridwave" idField="id"  queryMode="group">
 <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="下架人"  field="createName"     queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="下架时间"  field="createDate" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="复核人"  field="updateName"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="复核时间"  field="updateDate" formatter="yyyy-MM-dd hh:mm:ss"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
        <t:dgCol title="原始单据编码"  field="orderId"   query="true" queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId" dictionary="mv_goods,goods_code,goods_name"  query="true" queryMode="single"  width="180"></t:dgCol>
    <t:dgCol title="数量"  field="goodsQua"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="复核数量"  field="goodsQuaok"  extendParams="editor:'text'"  queryMode="single"  width="120"></t:dgCol>

<%--     <t:dgCol title="原始单据行项目"  field="orderIdI"  hidden="true"  queryMode="single"  width="120"></t:dgCol> --%>
<%--     <t:dgCol title="原始单据类型"  field="orderType"    queryMode="single"  width="120"></t:dgCol> --%>
    <t:dgCol title="单位"  field="goodsUnit"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="生产日期"  field="goodsProData" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="批次"  field="goodsBatch"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="源托盘码"  field="binIdFrom"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="库位编码"  field="kuWeiBianMa"   query="true" queryMode="single"  width="120"></t:dgCol>
<%--     <t:dgCol title="作业类型"  field="actTypeCode"   query="true" queryMode="single"  width="120"></t:dgCol> --%>
    <t:dgCol title="货主"  field="cusCode" dictionary="mv_cus,cus_code,cus_name"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="目标托盘"  field="binIdTo"   query="true" queryMode="single"  width="120"></t:dgCol>

  
<%--     <t:dgToolBar operationCode="add" title="录入" icon="icon-add"  funname="addRow"></t:dgToolBar> --%>
	<t:dgToolBar operationCode="edit" title="选择复核" icon="icon-edit"  funname="editRow"></t:dgToolBar>
	<t:dgToolBar operationCode="save" title="复核保存" icon="icon-save" url="wmToDownGoodsController.do?saveRows" funname="saveData"></t:dgToolBar>
	<t:dgToolBar operationCode="undo" title="取消复核" icon="icon-undo" funname="reject"></t:dgToolBar>
<%-- 	 <t:dgToolBar title="批量删除"  icon="icon-remove" url="jeecgListDemoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
//添加行
	function addRow(title,addurl,gname){
		$('#'+gname).datagrid('appendRow',{});
		var editIndex = $('#'+gname).datagrid('getRows').length-1;
		$('#'+gname).datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
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
				result["downrows["+i+"]."+d]=rows[i][d];
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
 
 </script>