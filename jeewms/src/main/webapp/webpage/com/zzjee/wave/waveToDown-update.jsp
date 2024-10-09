<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>wave_to_down</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="waveToDownController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${waveToDownPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								创建人登录名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="createBy" name="createBy" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.createBy}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建人登录名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								创建人名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="createName" name="createName" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.createName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建人名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								货主:
							</label>
						</td>
						<td class="value">
						     	 <input id="cusCode" name="cusCode" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.cusCode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">货主</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								客户名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="cusName" name="cusName" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.cusName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								waveId:
							</label>
						</td>
						<td class="value">
						     	 <input id="waveId" name="waveId" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.waveId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">waveId</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								商品编码:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsId" name="goodsId" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.goodsId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品编码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsName" name="goodsName" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.goodsName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								imCusCode:
							</label>
						</td>
						<td class="value">
						     	 <input id="imCusCode" name="imCusCode" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.imCusCode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">imCusCode</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								仓位:
							</label>
						</td>
						<td class="value">
						     	 <input id="binId" name="binId" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.binId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">仓位</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								托盘:
							</label>
						</td>
						<td class="value">
						     	 <input id="tinId" name="tinId" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.tinId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">托盘</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								生产日期:
							</label>
						</td>
						<td class="value">
						     	 <input id="proData" name="proData" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.proData}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">生产日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								baseGoodscount:
							</label>
						</td>
						<td class="value">
						     	 <input id="baseGoodscount" name="baseGoodscount" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.baseGoodscount}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">baseGoodscount</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								omBeiZhu:
							</label>
						</td>
						<td class="value">
						     	 <input id="omBeiZhu" name="omBeiZhu" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.omBeiZhu}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">omBeiZhu</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								基本单位:
							</label>
						</td>
						<td class="value">
						     	 <input id="baseUnit" name="baseUnit" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.baseUnit}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">基本单位</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								firstRq:
							</label>
						</td>
						<td class="value">
						     	 <input id="firstRq" name="firstRq" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.firstRq}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">firstRq</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								secondRq:
							</label>
						</td>
						<td class="value">
						     	 <input id="secondRq" name="secondRq" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${waveToDownPage.secondRq}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">secondRq</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								by1:
							</label>
						</td>
						<td class="value">
						     	 <input id="by1" name="by1" type="text" style="width: 150px" class="inputxt" datatype="*" 
						     	 ignore="checked" 
						     	 value='${waveToDownPage.by1}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">by1</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								by2:
							</label>
						</td>
						<td class="value">
						     	 <input id="by2" name="by2" type="text" style="width: 150px" class="inputxt" datatype="*" 
						     	 ignore="checked" 
						     	 value='${waveToDownPage.by2}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">by2</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								by3:
							</label>
						</td>
						<td class="value">
						     	 <input id="by3" name="by3" type="text" style="width: 150px" class="inputxt" datatype="*" 
						     	 ignore="checked" 
						     	 value='${waveToDownPage.by3}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">by3</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								by4:
							</label>
						</td>
						<td class="value">
						     	 <input id="by4" name="by4" type="text" style="width: 150px" class="inputxt" datatype="*" 
						     	 ignore="checked" 
						     	 value='${waveToDownPage.by4}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">by4</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								by5:
							</label>
						</td>
						<td class="value">
						     	 <input id="by5" name="by5" type="text" style="width: 150px" class="inputxt" datatype="*" 
						     	 ignore="checked" 
						     	 value='${waveToDownPage.by5}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">by5</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/wave/waveToDown.js"></script>		
