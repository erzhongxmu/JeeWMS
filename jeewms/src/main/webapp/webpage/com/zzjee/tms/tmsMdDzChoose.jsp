<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tmsMdDzList" singleSelect="true" checkbox="true" pagination="true" fitColumns="true" title="客户地址" actionUrl="tmsMdDzController.do?datagridchoose" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%--<t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="流程状态"  field="bpmStatus"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <t:dgCol title="用户"  field="username"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="lianxiren"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="联系电话"  field="dianhua"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="详细地址"  field="xiangxidizhi"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="省份"  field="shengfen"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="城市"  field="chengshi"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="区域"  field="quyu"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="默认地址"  field="morendizhi"  queryMode="group"  dictionary="sf_yn"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="是否可用"  field="zhuangtai"  queryMode="group"  dictionary="sf_yn"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="地址类型"  field="dizhileixing"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" url="tmsMdDzController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="tmsMdDzController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tmsMdDzController.do?goUpdate" funname="update"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tmsMdDzController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="tmsMdDzController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/tms/tmsMdDzList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });



//导入
function ImportXls() {
	openwindow('Excel导入', 'tmsMdDzController.do?upload', "tmsMdDzList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tmsMdDzController.do?exportXls","tmsMdDzList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tmsMdDzController.do?exportXlsByT","tmsMdDzList");
}

 </script>
