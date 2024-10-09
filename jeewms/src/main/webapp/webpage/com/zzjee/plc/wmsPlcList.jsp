<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmsPlcList" checkbox="true" pagination="true" fitColumns="false" title="PLC指令" actionUrl="wmsPlcController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="流程状态"  field="bpmStatus"  hidden="true"  queryMode="single" dictionary="bpm_status" width="120"></t:dgCol>
      <t:dgCol title="PLCIP"  field="plcIp"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="PLC端口"  field="plcPort"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="PLC型号"  field="plcType"   query="true" queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="指令备注"  field="comRemark"   query="true" queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="执行时间"  field="comTime"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="执行顺序"  field="comSeq"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="指令集"  field="comCons"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="备用1"  field="remark1"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="指令编号"  field="comNo"   query="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单步参数1"  field="query01"    queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单步参数2"  field="query02"    queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单步时间"  field="setpTime"    queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="步数"  field="setpNum"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="wmsPlcController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="wmsPlcController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wmsPlcController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmsPlcController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="wmsPlcController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
      <t:dgToolBar title="批量执行"    funname="batchupbin"></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/plc/wmsPlcList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 function  batchupbin() {
     var rows = $('#wmsPlcList').datagrid('getSelections');
     if(rows.length > 0){



         var ids = [];
         for (var i = 0; i < rows.length; i++) {
             ids.push(rows[i].id);
         }
             var url = "wmsPlcController.do?dotoup&ids="+ids.join(',');
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

                     }
                 }
             });

         tip("执行成功");
         $('#wmsPlcList').datagrid('reload',{});
     }
 }


//导入
function ImportXls() {
  openwindow('Excel导入', 'wmsPlcController.do?upload', "wmsPlcList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmsPlcController.do?exportXls","wmsPlcList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmsPlcController.do?exportXlsByT","wmsPlcList");
}

 </script>
