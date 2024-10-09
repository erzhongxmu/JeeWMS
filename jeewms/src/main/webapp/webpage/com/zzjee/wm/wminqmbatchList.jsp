<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div  class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
  <t:datagrid  name="wmInQmIbatchList" checkbox="true"  pageSize="10" fitColumns="false" title="批量收货" actionUrl="wmImNoticeHController.do?datagridbatch" sortName="createDate"  sortOrder="desc" idField="id" fit="true" queryMode="group">
   <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"    queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"    queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="到货通知单"  field="imNoticeId"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户订单号"  field="imCusCode"   query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="备注"  field="imBeizhu"   query="true"  queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="商品编码"  field="goodsCode"   query="true"    queryMode="single"    width="100"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"   query="true"    queryMode="single"    width="250"></t:dgCol>
    <t:dgCol title="预约数量"  field="goodsCount"    queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="已登记数量"  field="goodsQmCount"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="未收货数量"  field="goodsWqmCount"  extendParams="editor:'text'"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="生产日期"  field="goodsPrdData"  extendParams="editor:'datebox'"   formatter="yyyy-MM-dd "  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="批次"  field="goodsBatch"  extendParams="editor:'text'"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="储位"  field="binPlan"  extendParams="editor:'text'"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="托盘"  field="tinId"  extendParams="editor:'text'"   queryMode="single"  width="120"></t:dgCol>

    <t:dgCol title="单位"  field="goodsUnit"    queryMode="single"  width="50"></t:dgCol>
<%--    <t:dgDelOpt title="删除" url="wmInQmIController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/> --%>
   <t:dgFunOpt title="验收" funname="wmim(id)"  urlclass="ace_button"   />

   <t:dgToolBar operationCode="edit" title="选择验收" icon="icon-edit"  funname="editRow"></t:dgToolBar>
   <t:dgToolBar operationCode="save" title="验收保存" icon="icon-save" url="wmInQmIController.do?saveRows" funname="saveData"></t:dgToolBar>
   <t:dgToolBar operationCode="undo" title="取消验收" icon="icon-undo" funname="reject"></t:dgToolBar>

<%--    <t:dgToolBar title="录入" icon="icon-add" url="wmInQmIController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="wmInQmIController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--   <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmInQmIController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="wmInQmIController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
</div>
</div>
<script type="text/javascript">
    //保存数据
    function saveData(title,addurl,gname){
        if(!endEdit(gname))
            return false;
        var rows=$('#'+gname).datagrid("getSelections");
        // var uprows=$('#'+gname).datagrid("getChanges","updated");
        // rows=rows.concat(uprows);
        if(rows.length<=0){
            tip("没有需要保存的数据！")
            return false;
        }
        var result={};
        for(var i=0;i<rows.length;i++){
            for(var d in rows[i]){
                result["wminqmrows["+i+"]."+d]=rows[i][d];
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






    function wmim(id) {
    var url = "wmInQmIController.do?goAddBatch&id=" + id;
    // createdetailwindow()

        // function addbytab(){
        //     addOneTab("验收", url);

        createwindow("验收", url,"740px","420px");

        // }
    // add('批量收货', url, "wmInQmIbatchList","100%","100%");
}
    //导入
    function ImportXls() {
        openwindow('Excel导入', 'departController.do?upload', "departList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("departController.do?exportXls","departList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("departController.do?exportXlsByT","departList");
    }


</script>
