<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>第三方客户</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="mdCusOtherController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${mdCusOtherPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								创建人名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="createName" name="createName" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.createName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建人名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								创建人登录名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="createBy" name="createBy" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.createBy}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建人登录名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								创建日期:
							</label>
						</td>
						<td class="value">
									  <input id="createDate" name="createDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  
									  ignore="ignore"
									    value='<fmt:formatDate value='${mdCusOtherPage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								更新人名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="updateName" name="updateName" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.updateName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">更新人名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								更新人登录名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="updateBy" name="updateBy" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.updateBy}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">更新人登录名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								更新日期:
							</label>
						</td>
						<td class="value">
									  <input id="updateDate" name="updateDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  
									  ignore="ignore"
									    value='<fmt:formatDate value='${mdCusOtherPage.updateDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">更新日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								所属部门:
							</label>
						</td>
						<td class="value">
						     	 <input id="sysOrgCode" name="sysOrgCode" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.sysOrgCode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属部门</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								所属公司:
							</label>
						</td>
						<td class="value">
						     	 <input id="sysCompanyCode" name="sysCompanyCode" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.sysCompanyCode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属公司</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								所属客户:
							</label>
						</td>
						<td class="value">
						     	 <input id="suoShuKeHu" name="suoShuKeHu" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.suoShuKeHu}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属客户</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								中文全称:
							</label>
						</td>
						<td class="value">
						     	 <input id="zhongWenQch" name="zhongWenQch" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.zhongWenQch}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">中文全称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								助记码:
							</label>
						</td>
						<td class="value">
						     	 <input id="zhuJiMa" name="zhuJiMa" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.zhuJiMa}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">助记码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								客户简称:
							</label>
						</td>
						<td class="value">
						     	 <input id="keHuJianCheng" name="keHuJianCheng" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.keHuJianCheng}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户简称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								客户编码:
							</label>
						</td>
						<td class="value">
						     	 <input id="keHuBianMa" name="keHuBianMa" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.keHuBianMa}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户编码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								客户英文名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="keHuYingWen" name="keHuYingWen" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.keHuYingWen}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户英文名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								曾用企业代码:
							</label>
						</td>
						<td class="value">
						     	 <input id="zengYongQi" name="zengYongQi" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.zengYongQi}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">曾用企业代码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								曾用企业名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="zengYongQiYe" name="zengYongQiYe" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.zengYongQiYe}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">曾用企业名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								客户状态:
							</label>
						</td>
						<td class="value">
						     	 <input id="keHuZhuangTai" name="keHuZhuangTai" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.keHuZhuangTai}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户状态</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								企业属性:
							</label>
						</td>
						<td class="value">
						     	 <input id="xingYeFenLei" name="xingYeFenLei" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.xingYeFenLei}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">企业属性</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								客户等级:
							</label>
						</td>
						<td class="value">
						     	 <input id="keHuDengJi" name="keHuDengJi" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.keHuDengJi}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户等级</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								所属行业:
							</label>
						</td>
						<td class="value">
						     	 <input id="suoShuXingYe" name="suoShuXingYe" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.suoShuXingYe}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属行业</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								首签日期:
							</label>
						</td>
						<td class="value">
									  <input id="shouQianRiQi" name="shouQianRiQi" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  
									  ignore="ignore"
									    value='<fmt:formatDate value='${mdCusOtherPage.shouQianRiQi}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">首签日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								终止合作时间:
							</label>
						</td>
						<td class="value">
									  <input id="zhongZhiHeShiJian" name="zhongZhiHeShiJian" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  
									  ignore="ignore"
									    value='<fmt:formatDate value='${mdCusOtherPage.zhongZhiHeShiJian}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">终止合作时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								申请时间:
							</label>
						</td>
						<td class="value">
									  <input id="shenQingShiJian" name="shenQingShiJian" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  
									  ignore="ignore"
									    value='<fmt:formatDate value='${mdCusOtherPage.shenQingShiJian}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">申请时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								客户属性:
							</label>
						</td>
						<td class="value">
						     	 <input id="keHuShuXing" name="keHuShuXing" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.keHuShuXing}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户属性</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								归属组织代码:
							</label>
						</td>
						<td class="value">
						     	 <input id="guiShuZuZh" name="guiShuZuZh" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.guiShuZuZh}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">归属组织代码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								归属省份代码:
							</label>
						</td>
						<td class="value">
						     	 <input id="guiShuSheng" name="guiShuSheng" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.guiShuSheng}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">归属省份代码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								归属市代码:
							</label>
						</td>
						<td class="value">
						     	 <input id="guiShuShiDai" name="guiShuShiDai" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.guiShuShiDai}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">归属市代码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								归属县区代码:
							</label>
						</td>
						<td class="value">
						     	 <input id="guiShu" name="guiShu" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.guiShu}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">归属县区代码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="diZhi" name="diZhi" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.diZhi}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">地址</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								邮政编码:
							</label>
						</td>
						<td class="value">
						     	 <input id="youZhengBianMa" name="youZhengBianMa" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.youZhengBianMa}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">邮政编码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								主联系人:
							</label>
						</td>
						<td class="value">
						     	 <input id="zhuLianXiRen" name="zhuLianXiRen" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.zhuLianXiRen}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">主联系人</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								电话:
							</label>
						</td>
						<td class="value">
						     	 <input id="dianHua" name="dianHua" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.dianHua}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								手机:
							</label>
						</td>
						<td class="value">
						     	 <input id="shouJi" name="shouJi" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.shouJi}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">手机</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								传真:
							</label>
						</td>
						<td class="value">
						     	 <input id="chuanZhen" name="chuanZhen" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.chuanZhen}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">传真</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								Email地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="emaildiZhi" name="emaildiZhi" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.emaildiZhi}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">Email地址</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								网页地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="wangYeDiZhi" name="wangYeDiZhi" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.wangYeDiZhi}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">网页地址</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								法人代表:
							</label>
						</td>
						<td class="value">
						     	 <input id="faRenDaiBiao" name="faRenDaiBiao" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.faRenDaiBiao}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">法人代表</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								法人身份证号:
							</label>
						</td>
						<td class="value">
						     	 <input id="faRenShenFen" name="faRenShenFen" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.faRenShenFen}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">法人身份证号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								注册资金万元:
							</label>
						</td>
						<td class="value">
						     	 <input id="zhuCeZiJin" name="zhuCeZiJin" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.zhuCeZiJin}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">注册资金万元</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								币别:
							</label>
						</td>
						<td class="value">
						     	 <input id="biBie" name="biBie" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.biBie}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">币别</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								营业执照号:
							</label>
						</td>
						<td class="value">
						     	 <input id="yingYeZhiZhao" name="yingYeZhiZhao" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.yingYeZhiZhao}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">营业执照号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								税务登记证号:
							</label>
						</td>
						<td class="value">
						     	 <input id="shuiWuDeng" name="shuiWuDeng" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.shuiWuDeng}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">税务登记证号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								组织机构代码证:
							</label>
						</td>
						<td class="value">
						     	 <input id="zuZhiJiGou" name="zuZhiJiGou" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.zuZhiJiGou}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">组织机构代码证</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								道路运输经营许可证:
							</label>
						</td>
						<td class="value">
						     	 <input id="daoLuYunShu" name="daoLuYunShu" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.daoLuYunShu}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">道路运输经营许可证</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								主营业务:
							</label>
						</td>
						<td class="value">
						     	 <input id="zhuYingYeWu" name="zhuYingYeWu" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.zhuYingYeWu}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">主营业务</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								合作意向:
							</label>
						</td>
						<td class="value">
						     	 <input id="heYiXiang" name="heYiXiang" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.heYiXiang}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合作意向</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								批准机关:
							</label>
						</td>
						<td class="value">
						     	 <input id="piZhunJiGuan" name="piZhunJiGuan" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.piZhunJiGuan}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">批准机关</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								批准文号:
							</label>
						</td>
						<td class="value">
						     	 <input id="piZhunWenHao" name="piZhunWenHao" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.piZhunWenHao}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">批准文号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								注册日期:
							</label>
						</td>
						<td class="value">
									  <input id="zhuCeRiQi" name="zhuCeRiQi" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  
									  ignore="ignore"
									    value='<fmt:formatDate value='${mdCusOtherPage.zhuCeRiQi}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">注册日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						     	 <input id="beiZhu" name="beiZhu" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.beiZhu}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								联系人1:
							</label>
						</td>
						<td class="value">
						     	 <input id="zhuLianXiRen1" name="zhuLianXiRen1" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.zhuLianXiRen1}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人1</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								电话1:
							</label>
						</td>
						<td class="value">
						     	 <input id="dianHua1" name="dianHua1" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${mdCusOtherPage.dianHua1}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">电话1</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/md/mdCusOther.js"></script>		
