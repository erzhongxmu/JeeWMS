<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmToUpgoodsList" checkbox="true" fitColumns="false" title="上架明细" actionUrl="wmToUpController.do?datagridgoods" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"     queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"   queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="托盘码"  field="binId"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="库位编码"  field="kuWeiBianMa"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="作业类型"  field="actTypeCode"   query="true" queryMode="single" dictionary="ba_act_type,act_type_code,act_type_name"  width="120"></t:dgCol>
   <t:dgCol title="货主"  field="cusCode"  dictionary="md_cus,ke_hu_bian_ma,zhong_wen_qch"  query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="wmToUpController.do?doDel&id={id}"  urlclass="ace_button" urlfont="fa-trash-o"/> --%>
   <t:dgToolBar title="录入" icon="icon-add" url="wmToUpController.do?goAdd" funname="add" ></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wmToUpController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmToUpController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="wmToUpController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
        
    </div>
</div>
<div data-options="region:'east',
	title:'<t:mutiLang langKey="member.list"/>',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="userListpanel"></div>
</div>

<script type="text/javascript">
<!--

    $(function() {
        var li_east = 0;
    });
    function addOrg() {
        var id = "";
        var rowsData = $('#departList').datagrid('getSelections');
        if (rowsData.length == 1) {
            id = rowsData[0].id;
        }
        var url = "departController.do?add&id=" + id;

        add('<t:mutiLang langKey="common.add.param" langArg="common.department"/>', url, "departList","680","450");

    }

    function queryUsersByDepart(departid){
        var title = '<t:mutiLang langKey="member.list"/>';
        if(li_east == 0 || $('#main_depart_list').layout('panel','east').panel('options').title != title){
            $('#main_depart_list').layout('expand','east');
        }
        <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 500});
        $('#userListpanel').panel("refresh", "departController.do?userList&departid=" + departid);
    }
    /**
     * 为 组织机构 设置 角色
     * @param departid 组织机构主键
     * @param departname 组织机构名称
     */
    function setRoleByDepart(departid, departname){
        var currentTitle = $('#main_depart_list').layout('panel', 'east').panel('options').title;
        if(li_east == 0 || currentTitle.indexOf("<t:mutiLang langKey="current.org"/>") < 0){
            $('#main_depart_list').layout('expand','east');
        }
        var title = departname + ':<t:mutiLang langKey="current.org"/>';
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 200});
        var url = {
            <%--title :"test",--%>
            href:"roleController.do?roleTree&orgId=" + departid
        }
        $('#userListpanel').panel(url);
        $('#userListpanel').panel("refresh");
    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'departController.do?upload', "departList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("departController.do?exportXls","departList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("departController.do?exportXlsByT","departList");
    }

//-->
</script>