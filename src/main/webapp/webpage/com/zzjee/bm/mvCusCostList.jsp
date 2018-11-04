<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
 				
  <t:datagrid name="mvCusCostList" checkbox="true" pagination="true" fitColumns="false" title="客户账单" actionUrl="mvCusCostController.do?datagrid" idField="id" fit="false" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="客户编码"  field="cusCode" query="true"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="中文全称"  field="cusName"  query="true"    queryMode="single"  width="320"></t:dgCol>
    <t:dgCol title="计费日期"  field="costData"  hidden="true"  formatter="yyyy-MM-dd"     queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="300"></t:dgCol>
      <t:dgFunOpt title="导出未清账单" funname="print(id)"  urlclass="ace_button"  urlfont="	fa-print" />
      <t:dgFunOpt title="导出期间账单" funname="printqj(id)"  urlclass="ace_button"  urlfont="	fa-print" />

      <%--    <t:dgDelOpt title="删除" url="mvCusCostController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="mvCusCostController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="mvCusCostController.do?goUpdate" funname="update"></t:dgToolBar> --%>
   <%--<t:dgToolBar title="批量导出"  icon="icon-remove"   funname="printall"></t:dgToolBar>--%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="mvCusCostController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>

    
    <div name="searchColums" style="float: left; padding-left: 0px;padding-top: 5px;">
      <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;" title="计费周期">计费周期: </span>
              <input type="text" name="outtime_begin" style="width: 100px; height: 30px;">~
              <input type="text" name="outtime_end" style="width: 100px; height: 30px; margin-right: 20px;">
         </span>
         </div>
       
  </div>

 </div>

 <script src = "webpage/com/zzjee/bm/mvCusCostList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
	 $("#mvCusCostListtb").find("input[name='outtime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	 $("#mvCusCostListtb").find("input[name='outtime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

 });
 function printall(){
	 var begindate;
	 var enddate;
	 begindate = $('input[name="outtime_begin"]').attr("value");
	 enddate = $('input[name="outtime_end"]').attr("value");
	 if(begindate==""||enddate==""){
		 alert("开始或者结束日期不能为空");
	 }else{
		 var rows = $('#mvCusCostList').datagrid('getSelections');
		 for(var i=0; i<rows.length; i++){
				var url = "mvCusCostController.do?doPrint&id="+rows[i].id+"&begindate="+begindate+"&enddate="+enddate;
				window.open(url);
		 }
	 }
 }
 function print(id){
	 var begindate;
	 var enddate;
	 begindate = $('input[name="outtime_begin"]').attr("value");
	 enddate = $('input[name="outtime_end"]').attr("value");
	 if(begindate==""||enddate==""){
         var url = "mvCusCostController.do?doPrint&qj=wqj&id="+id+"&begindate="+begindate+"&enddate="+enddate;
         window.open(url);
//		 alert("开始或者结束日期不能为空");
	 }else{
			var url = "mvCusCostController.do?doPrint&qj=wqj&id="+id+"&begindate="+begindate+"&enddate="+enddate;
			window.open(url);
	 }

	}
 function printqj(id){
     var begindate;
     var enddate;
     begindate = $('input[name="outtime_begin"]').attr("value");
     enddate = $('input[name="outtime_end"]').attr("value");
     if(begindate==""||enddate==""){
//         var url = "mvCusCostController.do?doPrint&id="+id+"&begindate="+begindate+"&enddate="+enddate;
//         window.open(url);
		 alert("开始或者结束日期不能为空");
     }else{
         var url = "mvCusCostController.do?doPrint&qj=qj&id="+id+"&begindate="+begindate+"&enddate="+enddate;
         window.open(url);
     }

 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'mvCusCostController.do?upload', "mvCusCostList");
}

//导出
function ExportXls() {
	JeecgExcelExport("mvCusCostController.do?exportXls","mvCusCostList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("mvCusCostController.do?exportXlsByT","mvCusCostList");
}

 </script>