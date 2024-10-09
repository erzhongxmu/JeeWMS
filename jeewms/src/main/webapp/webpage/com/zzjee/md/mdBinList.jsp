<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="mdBinList" checkbox="true" pagination="true" fitColumns="false" title="储位定义" actionUrl="mdBinController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="所属公司"  field="sysCompanyCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="库存地点"  field="kuWeiMingCheng"    queryMode="group"  width="120"></t:dgCol>--%>
    <t:dgCol title="仓库"  field="binStore" query="true"   queryMode="single" dictionary="ba_store,store_code,store_name"  width="90"></t:dgCol>
    <t:dgCol title="库位编码"  field="kuWeiBianMa"   query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="库位条码"  field="kuWeiTiaoMa"   query="true" queryMode="single"                         width="120"></t:dgCol>
    <t:dgCol title="库位类型"  field="kuWeiLeiXing"   query="true" queryMode="single" dictionary="ba_bin_type,bin_type_code,bin_type_name"  width="80"></t:dgCol>
    <t:dgCol title="库位属性"  field="kuWeiShuXing"    queryMode="group" dictionary="ba_deg_type,deg_type_code,deg_type_name"  width="80"></t:dgCol>
    <t:dgCol title="产品属性"  field="chpShuXing"  queryMode="single" dictionary="ba_goods_type,goods_type_code,goods_type_name"   width="120"></t:dgCol>

    <t:dgCol title="上架次序"  field="shangJiaCiXu"    queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="取货次序"  field="quHuoCiXu"    queryMode="group"  width="80"></t:dgCol>
    <t:dgCol title="所属客户"  field="suoShuKeHu"    queryMode="group" dictionary="mv_cus,cus_code,cus_name"  width="120"></t:dgCol>

      <t:dgCol title="最小批量"  field="tiJiDanWei"     queryMode="group"    width="70"></t:dgCol>
    <t:dgCol title="最大批量"  field="zhongLiangDanWei"    queryMode="group"    width="70"></t:dgCol>
      <t:dgCol title="储位组别"  field="zuiDaMianJi"    queryMode="group"  width="120"></t:dgCol>

      <t:dgCol title="出库口"  field="mianJiDanWei"     queryMode="group"    width="70"></t:dgCol>

    <t:dgCol title="最大体积"  field="zuiDaTiJi"  style="text-align:right" queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="最大重量"  field="zuiDaZhongLiang"  style="text-align:right"  queryMode="group"  width="70"></t:dgCol>
    <t:dgCol title="最大托盘"  field="zuiDaTuoPan"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="长度"  field="chang"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="宽度"  field="kuan"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="高度"  field="gao"     queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="停用"  field="tingYong"    queryMode="group" dictionary="sf_yn" width="70"></t:dgCol>
    <t:dgCol title="备注"  field="mingXi"    queryMode="group"  width="120"></t:dgCol>

    <t:dgCol title="备注1"  field="mingXi1"    queryMode="group"  width="120"></t:dgCol>

    <t:dgCol title="备注2"  field="mingXi2"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="电子标签ID"  field="lorabqId"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="x坐标"  field="xnode"    query="true" queryMode="single"    width="120"></t:dgCol>
    <t:dgCol title="y坐标"  field="ynode"    query="true" queryMode="single"    width="120"></t:dgCol>
    <t:dgCol title="z坐标"  field="znode"    query="true" queryMode="single"    width="120"></t:dgCol>

    <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="mdBinController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"  exp="tingYong#ne#Y"/>
   <t:dgDelOpt title="同步WCS" url="mdBinController.do?doHad&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"  exp="tingYong#ne#Y"/>
   <t:dgToolBar title="录入" height="700" icon="icon-add" url="mdBinController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑"   height="700"  icon="icon-edit" url="mdBinController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"    url="mdBinController.do?doBatchDel" operationCode="deleteALLSelect" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看"  height="700"  icon="icon-search" url="mdBinController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/zzjee/md/mdBinList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });



//导入
function ImportXls() {
	openwindow('Excel导入', 'mdBinController.do?upload', "mdBinList");
}

//导出
function ExportXls() {
	JeecgExcelExport("mdBinController.do?exportXls","mdBinList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("mdBinController.do?exportXlsByT","mdBinList");
}

 </script>
