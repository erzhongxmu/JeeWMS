<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmImNoticeHList" checkbox="true" fitColumns="false" title="越库通知" actionUrl="wmImNoticeHController.do?datagridyk" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"     queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="通知单号"  field="noticeId" query="true" sortable="true"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户编码"  field="cusCode"     queryMode="single" dictionary="mv_cus,cus_code,cus_name"  width="200"></t:dgCol>
   <t:dgCol title="预计到货时间"  field="imData" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="客户订单号"  field="imCusCode"   query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="车号"  field="imCarNo"   query="true" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="司机"  field="imCarDri"   query="true" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="司机电话"  field="imCarMobile"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="订单类型"  field="orderTypeCode"   query="true" queryMode="single" dictionary="ba_order_type,order_type_code,order_type_name"  width="60"></t:dgCol>
   <t:dgCol title="月台"  field="platformCode"   query="true" queryMode="single" dictionary="ba_platform,platform_code,platform_name"  width="50"></t:dgCol>
   <t:dgCol title="备注"  field="imBeizhu"    queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="单据状态"  field="imSta"   query="true" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="240"></t:dgCol>
   <t:dgConfOpt title="删除" url="wmImNoticeHController.do?doDel&id={id}"  urlclass="ace_button" message="确定要删除此越库通知" urlfont="fa-trash-o" exp="imSta#ne#已完成"/>
   <t:dgFunOpt title="越库通知" funname="print(id)"  urlclass="ace_button"  urlfont="	fa-print" exp="imSta#ne#已删除"/>
   <t:dgFunOpt title="越库单" funname="printysd(id)"  urlclass="ace_button"  urlfont="	fa-print" exp="imSta#ne#已删除"/>
      <t:dgFunOpt title="完成" funname="closeor(id)"  urlclass="ace_button"  urlfont="	fa-print" exp="imSta#ne#已完成"/>
   
   <t:dgToolBar title="录入" icon="icon-add" url="wmImNoticeHController.do?goAdd&orderTypeCode=04" funname="add" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wmImNoticeHController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmImNoticeHController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="wmImNoticeHController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wmImNoticeHList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
	 $("#wmImNoticeHListtb").find("input[name='imData_begin1']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd hh:mm:ss'});});
	 $("#wmImNoticeHListtb").find("input[name='imData_end2']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd hh:mm:ss'});});

 });
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmImNoticeHController.do?upload', "wmImNoticeHList");
}

function print(id){
	var url = "wmImNoticeHController.do?doPrint&id="+id;
	
	window.open(url);
}
function printysd(id){
	var url = "wmImNoticeHController.do?doPrintysd&id="+id;
	
	window.open(url);
}

function closeor(id){
	var url = "wmImNoticeHController.do?close&id="+id;
	
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
		        $('#wmImNoticeHList').datagrid('reload',{});
			}		
		}
	});
}
//导出
function ExportXls() {
	JeecgExcelExport("wmImNoticeHController.do?exportXls","wmImNoticeHList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmImNoticeHController.do?exportXlsByT","wmImNoticeHList");
}
 </script>