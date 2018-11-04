<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="mdGoodsList" checkbox="true" pagination="true" fitColumns="false" title="商品信息" actionUrl="mdGoodsController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属客户"  field="suoShuKeHu"   query="true" queryMode="single" dictionary="md_cus,ke_hu_bian_ma,zhong_wen_qch"  width="120"></t:dgCol>
    <t:dgCol title="商品名称"  field="shpMingCheng"   query="true" queryMode="single"  width="160"></t:dgCol>
    <t:dgCol title="商品简称"  field="shpJianCheng"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="商品编码"  field="shpBianMa"   query="true" queryMode="single"  width="90"></t:dgCol>
    <t:dgCol title="客户商品编码"  field="shpBianMakh"   query="true" queryMode="single"  width="90"></t:dgCol>
    
    <t:dgCol title="商品型号"  field="shpXingHao"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="商品规格"  field="shpGuiGe"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="商品颜色"  field="shpYanSe"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="产品属性"  field="chpShuXing"  queryMode="single" dictionary="ba_goods_type,goods_type_code,goods_type_name"   width="120"></t:dgCol>
    <t:dgCol title="存放温层"  field="cfWenCeng"   query="true" queryMode="single" dictionary="ba_deg_type,deg_type_code,deg_type_name"  width="80"></t:dgCol>
    <t:dgCol title="拆零控制"  field="chlKongZhi"    queryMode="group" dictionary="sf_yn" width="60"></t:dgCol>
    <t:dgCol title="码盘单层数量"  field="mpDanCeng"   style="text-align:right"  queryMode="group"  width="60"></t:dgCol>
    <t:dgCol title="码盘层高"  field="mpCengGao"  style="text-align:right"   queryMode="group"  width="60"></t:dgCol>
    <t:dgCol title="计费商品类"  field="jfShpLei"    queryMode="group" dictionary="ba_goods_class,goods_class_code,goods_class_name"  width="70"></t:dgCol>
    <t:dgCol title="商品品牌"  field="shpPinPai"  hidden="true"  queryMode="group" dictionary="ba_goods_brand,goods_brand_code,goods_brand_name"  width="120"></t:dgCol>
    <t:dgCol title="商品条码"  field="shpTiaoMa"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="品牌图片"  field="ppTuPian"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="保质期"  field="bzhiQi"  style="text-align:right"  queryMode="group"  width="40"></t:dgCol>
    <t:dgCol title="允收天数"  field="zhlKgm"   style="text-align:right"  queryMode="group"  width="50"></t:dgCol>
    
    <t:dgCol title="单位"  field="shlDanWei"    queryMode="group" dictionary="ba_unit,unit_code,unit_zh_name"  width="40"></t:dgCol>
    <t:dgCol title="拆零单位"  field="jshDanWei"    queryMode="group" dictionary="ba_unit,unit_code,unit_zh_name"  width="70"></t:dgCol>
    <t:dgCol title="体积"  field="tiJiCm" style="text-align:right"   queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="重量"  field="zhlKg"   style="text-align:right"  queryMode="group"  width="50"></t:dgCol>
    <t:dgCol title="拆零数量"  field="chlShl" style="text-align:right"    queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="件数与体积比"  field="jtiJiBi"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="件数与毛重比"  field="jmZhongBi"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="件数与净重比"  field="jjZhongBi"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="尺寸单位"  field="chcDanWei"  hidden="true"  queryMode="group" dictionary="ba_unit,unit_code,unit_zh_name"  width="120"></t:dgCol>
    <t:dgCol title="长单品"  field="chDanPin"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="宽单品"  field="kuDanPin"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="高单品"  field="gaoDanPin"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="长"  field="chZhXiang"     queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="宽"  field="kuZhXiang"    queryMode="group"  width="80"></t:dgCol>
     <t:dgCol title="高"  field="gaoZhXiang"     queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="基准温度"  field="jiZhunwendu"     queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="商品描述"  field="shpMiaoShu"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="停用"  field="zhuangTai"   query="true" dictionary="sf_yn" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="mdGoodsController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="mdGoodsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="mdGoodsController.do?goUpdate" funname="update"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="mdGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="mdGoodsController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/md/mdGoodsList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'mdGoodsController.do?upload', "mdGoodsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("mdGoodsController.do?exportXls","mdGoodsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("mdGoodsController.do?exportXlsByT","mdGoodsList");
}

 </script>