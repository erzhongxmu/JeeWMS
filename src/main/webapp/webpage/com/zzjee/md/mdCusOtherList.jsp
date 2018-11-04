<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="mdCusOtherList" checkbox="true" pagination="false" fitColumns="false" title="第三方客户" actionUrl="mdCusOtherController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <%--<t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="创建人登录名称"  field="createBy"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="更新人名称"  field="updateName"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="更新人登录名称"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="所属部门"  field="sysOrgCode"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="所属公司"  field="sysCompanyCode"    queryMode="group"  width="120"></t:dgCol>--%>
    <t:dgCol title="所属客户" query="true"  field="suoShuKeHu"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="中文全称"  query="true" field="zhongWenQch"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="助记码"  field="zhuJiMa"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="客户简称"  field="keHuJianCheng"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="客户编码" query="true"  field="keHuBianMa"    queryMode="single"  width="120"></t:dgCol>
    <%--<t:dgCol title="客户英文名称"  field="keHuYingWen"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="曾用企业代码"  field="zengYongQi"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="曾用企业名称"  field="zengYongQiYe"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="客户状态"  field="keHuZhuangTai"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="企业属性"  field="xingYeFenLei"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="客户等级"  field="keHuDengJi"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="所属行业"  field="suoShuXingYe"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="首签日期"  field="shouQianRiQi" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="终止合作时间"  field="zhongZhiHeShiJian" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="申请时间"  field="shenQingShiJian" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="客户属性"  field="keHuShuXing"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="归属组织代码"  field="guiShuZuZh"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="归属省份代码"  field="guiShuSheng"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="归属市代码"  field="guiShuShiDai"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="归属县区代码"  field="guiShu"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="地址"  field="diZhi"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="邮政编码"  field="youZhengBianMa"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="主联系人"  field="zhuLianXiRen"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="电话"  field="dianHua"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="手机"  field="shouJi"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="传真"  field="chuanZhen"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="Email地址"  field="emaildiZhi"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="网页地址"  field="wangYeDiZhi"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="法人代表"  field="faRenDaiBiao"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="法人身份证号"  field="faRenShenFen"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="注册资金万元"  field="zhuCeZiJin"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="币别"  field="biBie"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="营业执照号"  field="yingYeZhiZhao"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="税务登记证号"  field="shuiWuDeng"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="组织机构代码证"  field="zuZhiJiGou"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="道路运输经营许可证"  field="daoLuYunShu"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="主营业务"  field="zhuYingYeWu"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="合作意向"  field="heYiXiang"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="批准机关"  field="piZhunJiGuan"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="批准文号"  field="piZhunWenHao"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="注册日期"  field="zhuCeRiQi" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="备注"  field="beiZhu"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="联系人1"  field="zhuLianXiRen1"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="电话1"  field="dianHua1"    queryMode="group"  width="120"></t:dgCol>--%>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="mdCusOtherController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="mdCusOtherController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="mdCusOtherController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="mdCusOtherController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="mdCusOtherController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
    <t:dgToolBar operationCode="uasimpcus" title="第三方系统导入" icon="icon-put" funname="otherimp"></t:dgToolBar>

    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
    <div name="searchColums1" style="float: left; padding-left: 0px;padding-top: 5px;">
      <%--<input type="text" name="batchbin" style="width: 100px; height: 30px;">--%>
      日期：<input type="text" name="batchdate"    class="form-control" onClick="WdatePicker()" style="width: 100px; height: 30px;">

    </div>
  </div>
 </div>
 <script src = "webpage/com/zzjee/md/mdCusOtherList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 function  otherimp() {
     var batchdate;
     batchdate = $('input[name="batchdate"]').attr("value");
     if(batchdate==""){
         alert("日期不能为空");
     }else{
         var url = "mdCusOtherController.do?doGet&formDate="+batchdate;
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
         tip("获取成功");
         $('#mdCusOtherList').datagrid('reload',{});
     }
 }
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'mdCusOtherController.do?upload', "mdCusOtherList");
}

//导出
function ExportXls() {
	JeecgExcelExport("mdCusOtherController.do?exportXls","mdCusOtherList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("mdCusOtherController.do?exportXlsByT","mdCusOtherList");
}

 </script>