<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="mdCusList" checkbox="true" pagination="true" fitColumns="false" title="客户" actionUrl="mdCusController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="中文全称"  field="zhongWenQch"   query="true" queryMode="single"  width="160"></t:dgCol>
    <t:dgCol title="合同号"  field="zhuJiMa"   query="true" queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="客户简称"  field="keHuJianCheng"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="客户编码"  field="keHuBianMa"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="客户英文名称"  field="keHuYingWen"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="曾用企业代码"  field="zengYongQi"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="曾用企业名称"  field="zengYongQiYe"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="客户状态"  field="keHuZhuangTai"  hidden="true"  queryMode="group" dictionary="sf_yn" width="120"></t:dgCol>
    <t:dgCol title="企业属性"  field="xingYeFenLei"    queryMode="group" dictionary="ba_com_type,com_type_code,com_type_name"  width="120"></t:dgCol>
    <t:dgCol title="客户等级"  field="keHuDengJi"  hidden="true"  queryMode="group" dictionary="ba_com_deg,com_deg_code,com_deg_name"  width="120"></t:dgCol>
    <t:dgCol title="所属行业"  field="suoShuXingYe"  hidden="true"  queryMode="group" dictionary="ba_classfl,classfl_code,classfl_name"  width="120"></t:dgCol>
    <t:dgCol title="首签日期"  field="shouQianRiQi" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="终止合作时间"  field="zhongZhiHeShiJian" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="申请时间"  field="shenQingShiJian" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="客户属性"  field="keHuShuXing"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="归属组织代码"  field="guiShuZuZh"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="归属省份代码"  field="guiShuSheng"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="归属市代码"  field="guiShuShiDai"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="归属县区代码"  field="guiShu"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="地址"  field="diZhi"    queryMode="group"  width="200"></t:dgCol>
    <t:dgCol title="邮政编码"  field="youZhengBianMa"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="负责人"  field="zhuLianXiRen"    queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="电话"  field="dianHua"    queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="手机"  field="shouJi"    queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="传真"  field="chuanZhen"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="联系人1"  field="zhuLianXiRen1"    width="70"></t:dgCol>
    <t:dgCol title="电话1"  field="dianHua1"       width="70"></t:dgCol>
    
    <t:dgCol title="Email地址"  field="emaildiZhi"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="网页地址"  field="wangYeDiZhi"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="法人代表"  field="faRenDaiBiao"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="法人身份证号"  field="faRenShenFen"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="注册资金万元"  field="zhuCeZiJin"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="币别"  field="biBie"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="营业执照号"  field="yingYeZhiZhao"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="税务登记证号"  field="shuiWuDeng"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="组织机构代码证"  field="zuZhiJiGou"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="道路运输经营许可证"  field="daoLuYunShu"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="主营业务"  field="zhuYingYeWu"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="合作意向"  field="heYiXiang"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="批准机关"  field="piZhunJiGuan"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="批准文号"  field="piZhunWenHao"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="注册日期"  field="zhuCeRiQi" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="备注"  field="beiZhu"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
    <t:dgDelOpt title="删除" url="mdCusController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="mdCusController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="mdCusController.do?goUpdate" funname="update"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="mdCusController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="mdCusController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/md/mdCusList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'mdCusController.do?upload', "mdCusList");
}

//导出
function ExportXls() {
	JeecgExcelExport("mdCusController.do?exportXls","mdCusList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("mdCusController.do?exportXlsByT","mdCusList");
}

 </script>