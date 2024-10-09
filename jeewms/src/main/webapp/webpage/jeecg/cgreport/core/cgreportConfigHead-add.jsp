<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>动态报表配置抬头</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	
	$('#tts').tabs({
		   onSelect:function(title){
		       $('#tts .panel-body').css('width','auto');
			}
		});
  });
 //初始化下标
	function resetTrNum(tableId) {
		$tbody = $("#"+tableId+"");
		$tbody.find('>tr').each(function(i){
			$(':input, select,button,a', this).each(function(){
				var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'), val = $this.val();
				if(name!=null){
					if (name.indexOf("#index#") >= 0){
						$this.attr("name",name.replace('#index#',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
				if(id!=null){
					if (id.indexOf("#index#") >= 0){
						$this.attr("id",id.replace('#index#',i));
					}else{
						var s = id.indexOf("[");
						var e = id.indexOf("]");
						var new_id = id.substring(s+1,e);
						$this.attr("id",id.replace(new_id,i));
					}
				}
				if(onclick_str!=null){
					if (onclick_str.indexOf("#index#") >= 0){
						$this.attr("onclick",onclick_str.replace(/#index#/g,i));
					}else{
					}
				}
			});
		});
	}
	function decode(value, id) {//value传入值,id接受值
		var last = value.lastIndexOf("/");
		var filename = value.substring(last + 1, value.length);
		$("#" + id).text(decodeURIComponent(filename));
	}
 </script>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="cgreportConfigHeadController.do?doAdd">
	<input id="id" name="id" type="hidden" value="${cgreportConfigHeadPage.id }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"><t:mutiLang langKey="common.code"/>:</label></td>
			<td class="value"><input id="code" name="code" type="text" style="width: 150px" class="inputxt" datatype="w1"> <span class="Validform_checktip"></span></td>
			<td align="right"><label class="Validform_label"><t:mutiLang langKey="common.name"/> :</label></td>
			<td class="value"><input id="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*"> <span class="Validform_checktip"></span></td>
            <td align="right"><label class="Validform_label"><t:mutiLang langKey="common.dynamic.dbsource"/> :</label></td>
            <td class="value"><t:dictSelect field="dbSource" dictTable="t_s_data_source" dictField="DB_KEY" dictText="DB_KEY" /><span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"><t:mutiLang langKey="query.sql"/>:</label></td>
			<td class="value" colspan="5"><textarea rows="5" cols="150" id="cgrSql" name="cgrSql" datatype="*"></textarea> <span class="Validform_checktip"></span>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;您可以键入“${abc}”作为一个参数，这里abc是参数的名称。例如：<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;select * from table where id = <%="${abc}"%>。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;select * from table where id = <%="'${abc}'"%>（如果id字段为字符串类型）<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">注：参数只支持动态报表，popup暂不支持</font><p/>
			
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"><t:mutiLang langKey="common.description"/>:</label></td>


			<td class="value" colspan="5"><textarea rows="3" cols="150" id="content" name="content" ></textarea> <span class="Validform_checktip"></span></td>
			<!--update-end--Author:dangzhenghui  Date:20170316 for：TASK #1795 【页面校验】体验修改-->

		</tr>
		<!--update-begin--Author:huangzq  Date:20151129 for：[753]【在线报表】扩展增加俩字段，非必填-->
		<tr>
			<td align="right"><label class="Validform_label"><t:mutiLang langKey="common.returnvalfield"/>:</label></td>
			<td class="value"><input id="returnValField" name="returnValField" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span></td>
			<td align="right"><label class="Validform_label"><t:mutiLang langKey="common.returntxtfield"/>:</label></td>
			<td class="value"><input id="returnTxtField" name="returnTxtField" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span></td>
			<td align="right"><label class="Validform_label"><t:mutiLang langKey="common.returntxttype"/>:</label></td>
			<td class="value" colspan="2"> <t:dictSelect field="popRetype" typeGroupCode="pop_retype" hasLabel="false" defaultVal="1" /><span class="Validform_checktip"></span></td>
        </tr>
        <!--update-end--Author:huangzq  Date:20151129 for：[753]【在线报表】扩展增加俩字段，非必填-->   
	</table>
	<div style="width: auto; height: 200px;"><%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
	<div style="width: 800px; height: 1px;"></div>
	
	<t:tabs id="ttp" iframe="false" tabPosition="top" fit="false"><t:tab href="cgreportConfigHeadController.do?cgreportConfigParamList&id=${cgreportConfigHeadPage.id}" icon="icon-search" title="报表参数" id="cgreportConfigParam"></t:tab></t:tabs>				
	<t:tabs id="tt" iframe="false" tabPosition="top" fit="false"><t:tab href="cgreportConfigHeadController.do?cgreportConfigItemList&id=${cgreportConfigHeadPage.id}" icon="icon-search" title="dynamic.report.config.detail" id="cgreportConfigItem"></t:tab></t:tabs></div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display: none">
	<tbody id="add_cgreportConfigItem_table_template">
		<tr>
			<td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
			<td align="left"><input name="cgreportConfigItemList[#index#].fieldName" maxlength="36" type="text" class="inputxt" style="width: 120px;"></td>
			<td align="left"><input name="cgreportConfigItemList[#index#].orderNum" maxlength="10" type="text" class="inputxt" style="width: 30px;"></td>
			<td align="left"><input name="cgreportConfigItemList[#index#].fieldTxt" maxlength="1000" type="text" class="inputxt" style="width: 120px;"></td>
			<td align="left"><t:dictSelect field="cgreportConfigItemList[#index#].fieldType" extendJson="{style:'width:80px'}" type="list" typeGroupCode="fieldtype" defaultVal="String" hasLabel="false" title="common.text.type"></t:dictSelect></td>
			<td align="left"><select id="isShow" name="cgreportConfigItemList[#index#].isShow"  style="width: 60px;">
				<option value="Y"><t:mutiLang langKey="common.show"/></option>
				<option value="N"><t:mutiLang langKey="common.hide"/></option>
			</select></td>
			<td align="left"><input name="cgreportConfigItemList[#index#].fieldHref" maxlength="1000" type="text" class="inputxt" style="width: 120px;">
			<td align="left"><t:dictSelect field="cgreportConfigItemList[#index#].SMode"  extendJson="{style:'width:90px'}" type="list" typeGroupCode="searchmode" defaultVal="" hasLabel="false" title="common.query.module"></t:dictSelect></td>
			<td align="left"><input name="cgreportConfigItemList[#index#].replaceVa" maxlength="36" type="text" class="inputxt" style="width: 120px;"></td>
			<td align="left"><input name="cgreportConfigItemList[#index#].dictCode" maxlength="36" type="text" class="inputxt" style="width: 120px;"></td>
			<td align="left"><t:dictSelect field="cgreportConfigItemList[#index#].SFlag"  extendJson="{style:'width:60px'}" type="list" typeGroupCode="yesorno" defaultVal="" hasLabel="false" title="common.isquery"></t:dictSelect></td>
		</tr>
	</tbody>
</table>
<table style="display: none">
	<tbody id="add_cgreportConfigParam_table_template">
		<tr>
		 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
		 <td align="left"><input name="cgreportConfigParamList[#index#].paramName" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="*" ></td>
		 <td align="left"><input name="cgreportConfigParamList[#index#].paramDesc" maxlength="32" type="text" class="inputxt"  style="width:120px;" ></td>
	     <td align="left"><input name="cgreportConfigParamList[#index#].paramValue" maxlength="32" type="text" class="inputxt"  style="width:120px;" ></td>
		 <td align="left"><input name="cgreportConfigParamList[#index#].seq" maxlength="32" type="text" class="inputxt"  style="width:120px;"></td>
		</tr>
	 </tbody>
</table>
</body>
<script src="webpage/jeecg/cgreport/core/cgreportConfigHead.js"></script>