<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- 自动补全 -->
<link rel="stylesheet" href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js"></script>
<div class="easyui-layout" fit="true">
 <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wmInQmIList"  checkbox="true" pagination="true" fitColumns="false" title="收货登记" actionUrl="wmInQmIController.do?datagrid" idField="id" fit="false"  queryMode="group">
   <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="验收人"  field="createName"     queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="收货日期"  field="createDate" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="130"></t:dgCol>
    <t:dgCol title="上架人"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="上架日期"  field="updateDate" formatter="yyyy-MM-dd hh:mm:ss" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="到货通知单"  field="imNoticeId"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户订单号"  field="imCusCode"   query="true" queryMode="single"  width="90"></t:dgCol>

   <t:dgCol title="到货通知行项目"  field="imNoticeItem"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品编码"  field="goodsId"     query="true"     width="100"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"     query="true" queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="到货数量"  field="imQuat"    queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="数量"  field="qmOkQuat"    queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="备注"  field="itemText"    queryMode="single"  width="50"></t:dgCol>
    <t:dgCol title="温度"  field="recDeg"  hidden="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="生产日期"  field="proData" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="托盘"  field="tinId"  query="true"  queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="单位"  field="goodsUnit"    queryMode="single"  width="50"></t:dgCol>
    <t:dgCol title="批次"  field="goodsBatch"  hidden="true"  queryMode="single"  width="60"></t:dgCol>
    <t:dgCol title="储位"  field="binId"  query="true"   queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="体积"  field="tinTj"  queryMode="single"  width="50"></t:dgCol>
    <t:dgCol title="重量"  field="tinZhl"    queryMode="single"  width="50"></t:dgCol>
    <t:dgCol title="基本单位"  field="baseUnit"  queryMode="single"  width="50"></t:dgCol>
    <t:dgCol title="基本单位数量"  field="baseGoodscount"    queryMode="single"  width="50"></t:dgCol>
    <t:dgCol title="货主"  field="cusCode"  query="true"  queryMode="single" dictionary="mv_cus,cus_code,cus_name"  width="180"></t:dgCol>
    <t:dgCol title="是否已上架"  field="binSta"  query="true"  queryMode="single" dictionary="sf_yn" width="120"></t:dgCol>

   <t:dgDelOpt operationCode="delinqm" title="删除" url="wmInQmIController.do?doDel&id={id}" urlclass="ace_button"   urlfont="fa-trash-o"/>
   <t:dgFunOpt title="上架" funname="wmtoup(id)"  urlclass="ace_button"   exp="binSta#ne#Y"/>

   <t:dgToolBar title="录入" icon="icon-add" url="wmInQmIController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wmInQmIController.do?goUpdate" funname="update"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="wmInQmIController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="批量更改储位"    funname="batchupdate"></t:dgToolBar>
   <t:dgToolBar title="批量更改生产日期"    funname="batchupdatepro"></t:dgToolBar>


   <t:dgToolBar title="批量上架"    funname="batchupbin"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="wmInQmIController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>

   <div name="searchColums1" style="float: left; padding-left: 0px;padding-top: 5px;">
    <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;" title="批量储位">批量储位为: </span>

    <t:autocomplete   entityName="MdBinEntity" searchField="kuWeiBianMa" name="batchbin"></t:autocomplete>


    <%--<input type="text" name="batchbin" style="width: 100px; height: 30px;">--%>
    生产日期：<input type="text" name="batchdate"    class="form-control" onClick="WdatePicker()" style="width: 100px; height: 30px;">

   </div>

  </div>

 </div>

 <script src = "webpage/com/zzjee/wm/wmInQmIList.js"></script>

 <script type="text/javascript">
 $(document).ready(function(){
 });

 
 function batchupdatepro() {
     var batchdate;
     batchdate = $('input[name="batchdate"]').attr("value");
     if(batchdate==""){
         alert("生产日期不能为空");
     }else{
         var rows = $('#wmInQmIList').datagrid('getSelections');
         if(rows.length > 0){
             for(var i=0; i<rows.length; i++){
                 if(rows[i].binSta=='N'){
                     var url = "wmInQmIController.do?dobatchUpdatedate&id="+rows[i].id+"&batchdate="+batchdate;
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
                 }
             }
             tip("批量修改成功");
             $('#wmInQmIList').datagrid('reload',{});
         }

     }
 }
 
 function  batchupbin() {
     var rows = $('#wmInQmIList').datagrid('getSelections');
     if(rows.length > 0){
         for(var i=0; i<rows.length; i++){
             var url = "wmInQmIController.do?dotoup&id="+rows[i].id;
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
         }
         tip("上架成功");
         $('#wmInQmIList').datagrid('reload',{});
     }
 }

function batchupdate() {
    var batchbin;
    batchbin = $('input[name="batchbin"]').attr("value");
    if(batchbin==""){
        alert("储位不能为空");
    }else{
        var rows = $('#wmInQmIList').datagrid('getSelections');
        if(rows.length > 0){
            for(var i=0; i<rows.length; i++){
                if(rows[i].binSta=='N'){
                    var url = "wmInQmIController.do?dobatchUpdate&id="+rows[i].id+"&binid="+batchbin;
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
                }
            }
            tip("批量修改成功");
            $('#wmInQmIList').datagrid('reload',{});
        }

    }
}

 function wmtoup(id){
		var url = "wmInQmIController.do?dotoup&id="+id;
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
					tip("上架成功");
			        $('#wmInQmIList').datagrid('reload',{});
				}		
			}
		});
	}



//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wmInQmIController.do?upload', "wmInQmIList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wmInQmIController.do?exportXls","wmInQmIList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wmInQmIController.do?exportXlsByT","wmInQmIList");
}

 </script>