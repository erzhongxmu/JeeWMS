<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmOmNoticeHList"  checkbox="true" pagination="true" fitColumns="true" 
  title="批量回单" actionUrl="wmOmNoticeHController.do?datagridbatchconf" idField="id"  queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="客户订单号"  field="imCusCode"   query="true" queryMode="single"  width="100"></t:dgCol>

   <t:dgCol title="备注"  field="omBeizhu" extendParams="editor:'text'" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="出货单号"  field="omNoticeId" query="true"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户"  field="cusCode"   query="true" queryMode="single"  dictionary="mv_cus,cus_code,cus_name" width="120"></t:dgCol>
   <t:dgCol title="回单时间"  field="delvData" extendParams="editor:'datebox'"   formatter="yyyy-MM-dd "    query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="收货人"  field="delvMember"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="收货人电话"  field="delvMobile"   query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="收货人地址"  field="delvAddr"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="承运人"  field="reMember"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="承运人电话"  field="reMobile"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="承运人车号"  field="reCarno"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="发货月台"  field="omPlatNo"   query="true" queryMode="single"  dictionary="ba_platform,platform_code,platform_name" width="60"></t:dgCol>
   <t:dgCol title="状态"  field="omSta"   query="true" queryMode="single"  width="60"></t:dgCol>
 	<t:dgToolBar operationCode="edit" title="选择回单" icon="icon-edit"  funname="editRow"></t:dgToolBar>
	<t:dgToolBar operationCode="save" title="回单保存" icon="icon-save" url="wmOmNoticeHController.do?saveRows" funname="saveData"></t:dgToolBar>
	<t:dgToolBar operationCode="undo" title="取消回单" icon="icon-undo" funname="reject"></t:dgToolBar>
   
<%--    <t:dgCol title="操作" field="opt" width="180"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="wmOmNoticeHController.do?doDel&id={id}"  urlclass="ace_button" exp="omSta#ne#已删除" urlfont="fa-trash-o"/> --%>
<%--       <t:dgFunOpt title="通知单" funname="print(id)"  urlclass="ace_button"  urlfont="	fa-print" exp="omSta#ne#已删除"/> --%>
<%--    <t:dgFunOpt title="出库单" funname="print(id)"  urlclass="ace_button"  urlfont="	fa-print" exp="omSta#ne#已删除"/> --%>
  
<%--    <t:dgToolBar title="录入" icon="icon-add" url="wmOmNoticeHController.do?goAdd" funname="add" width="100%" height="100%"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wmOmNoticeHController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar> --%>
 <%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmOmNoticeHController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%> 
<%--    <t:dgToolBar title="查看" icon="icon-search" url="wmOmNoticeHController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> </t:datagrid> --%>
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