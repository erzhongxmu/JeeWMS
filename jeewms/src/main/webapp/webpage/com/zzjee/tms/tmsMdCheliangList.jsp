<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tmsMdCheliangList" checkbox="true" pagination="true" fitColumns="true" title="车辆管理" actionUrl="tmsMdCheliangController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="流程状态"  field="bpmStatus"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="车号"  field="chepaihao"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="车型"  field="chexing"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="最大体积"  field="zuidatiji"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="载重"  field="zaizhong"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="载人数"  field="zairen"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="准驾驾照"  field="jiazhao"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="zhuangtai"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="beizhu"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="默认司机"  field="username"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="车牌号"  field="gpsid"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="区域"  field="quyu"  queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tmsMdCheliangController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="重排车号"     funname="dowaveALLSelect"></t:dgToolBar>

   <t:dgToolBar title="录入" icon="icon-add" url="tmsMdCheliangController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tmsMdCheliangController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tmsMdCheliangController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tmsMdCheliangController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/tms/tmsMdCheliangList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function dowaveALLSelect(){

  var ids = [];
  var rows = $('#tmsMdCheliangList').datagrid('getSelections');
  for(var i=0; i<rows.length; i++){
   ids.push(rows[i].id);
  }
  var url = "tmsMdCheliangController.do?doassignwave";
  $.ajax({
   async : true,
   cache : false,
   type : 'POST',
   data : {
    ids : ids.join(',')
   },
   url : url,// 请求的action路径
   error : function() {// 请求失败处理函数
   },
   success : function(data) {
    var d = $.parseJSON(data);
    if (d.success) {
     tip("重排车号成功");
     $('#tmsMdCheliangList').datagrid('reload',{});
    }
   }
  });
 }

//导入
function ImportXls() {
	openwindow('Excel导入', 'tmsMdCheliangController.do?upload', "tmsMdCheliangList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tmsMdCheliangController.do?exportXls","tmsMdCheliangList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tmsMdCheliangController.do?exportXlsByT","tmsMdCheliangList");
}

 </script>
