<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmOmNoticeHList" checkbox="true" fitColumns="true" title="其他出货 " actionUrl="wmOmNoticeHController.do?datagridqt" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"     queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="出货单号"  field="omNoticeId" query="true"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户"  field="cusCode"    queryMode="single"  dictionary="mv_cus,cus_code,cus_name" width="120"></t:dgCol>
   <t:dgCol title="要求交货时间"  field="delvData"  formatter="yyyy-MM-dd hh:mm:ss"    query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="收货人"  field="delvMember"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="收货人电话"  field="delvMobile"   query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="收货人地址"  field="delvAddr"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="承运人"  field="reMember"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="承运人电话"  field="reMobile"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="承运人车号"  field="reCarno"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="发货月台"  field="omPlatNo"   query="true" queryMode="single"  dictionary="ba_platform,platform_code,platform_name" width="60"></t:dgCol>
   <t:dgCol title="备注"  field="omBeizhu"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="omSta"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="180"></t:dgCol>
   <t:dgDelOpt title="删除" url="wmOmNoticeHController.do?doDel&id={id}"  urlclass="ace_button" exp="omSta#ne#已删除" urlfont="fa-trash-o"/>
      <t:dgFunOpt title="通知单" funname="print(id)"  urlclass="ace_button"  urlfont="	fa-print" exp="omSta#ne#已删除"/>
   <t:dgFunOpt title="出库单" funname="printckd(id)"  urlclass="ace_button"  urlfont="	fa-print" exp="omSta#ne#已删除"/>
    <t:dgFunOpt title="装箱单" funname="printckd(id)"  urlclass="ace_button"  urlfont="	fa-print" exp="omSta#ne#已删除"/>
   <t:dgToolBar title="录入" icon="icon-add" url="wmOmNoticeHController.do?goAdd&orderTypeCode=19" funname="add" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wmOmNoticeHController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmOmNoticeHController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="wmOmNoticeHController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/wm/wmOmNoticeHList.js"></script>		
 <script type="text/javascript">
 
 $(document).ready(function(){
	 $("#wmOmNoticeHListtb").find("input[name='delvData_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd hh:mm:ss'});});
	 $("#wmOmNoticeHListtb").find("input[name='delvData_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd hh:mm:ss'});});

 });
 
 function print(id){
		var url = "wmOmNoticeHController.do?doPrint&id="+id;
		
		window.open(url);
	}
 
 function printckd(id){
		var url = "wmOmNoticeHController.do?doPrintckd&id="+id;
		
		window.open(url);
	}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmOmNoticeHController.do?upload', "wmOmNoticeHList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmOmNoticeHController.do?exportXls","wmOmNoticeHList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmOmNoticeHController.do?exportXlsByT","wmOmNoticeHList");
}
 </script>