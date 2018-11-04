<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>入库通知打印</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript" charset="utf-8" src="webpage/com/zzjee/wmjs/jquery.jqprint.js"></script>
<script language="javascript">
function printall(){

    $(".printdiv").jqprint();

}
function printview(){
	document.all.WebBrowser1.ExecWB(7,1);
}


</script>
	<style>
		tr
		{mso-height-source:auto;
			mso-ruby-visibility:none;}
		col
		{mso-width-source:auto;
			mso-ruby-visibility:none;}
		br
		{mso-data-placement:same-cell;}
		ruby
		{ruby-align:left;}
		.style0
		{mso-number-format:General;
			text-align:general;
			vertical-align:bottom;
			white-space:nowrap;
			mso-rotate:0;
			mso-background-source:auto;
			mso-pattern:auto;
			color:windowtext;
			font-size:14pt;
			font-weight:400;
			font-style:normal;
			text-decoration:none;
			font-family: 黑体;
			mso-font-charset:0;
			border:none;
			mso-protection:locked visible;
			mso-style-name:常规;
			mso-style-id:0;}
		td
		{mso-style-parent:style0;
			padding-top:1px;
			padding-right:1px;
			padding-left:1px;
			mso-ignore:padding;
			color:windowtext;
			font-size:14pt;
			font-weight:400;
			font-style:normal;
			text-decoration:none;
			font-family:黑体;
			mso-font-charset:0;
			mso-number-format:General;
			text-align:general;
			vertical-align:bottom;
			border:none;
			mso-background-source:auto;
			mso-pattern:auto;
			mso-protection:locked visible;
			white-space:nowrap;
			mso-rotate:0;}
		.xl65
		{mso-style-parent:style0;
			color:black;
			font-size:16pt;
			text-align:center;
			border:1.0pt solid black;}
		.xl66
		{mso-style-parent:style0;
			color:black;
			font-size:16pt;}
		.xl67
		{mso-style-parent:style0;
			color:black;
			font-size:18pt;
			text-align:center;}
		.xl68
		{mso-style-parent:style0;
			color:black;
			font-size:16pt;
			white-space:normal;}
		.xl69
		{mso-style-parent:style0;
			text-align:center;}

	</style>

</head>
<body style="overflow-y:hidden" scroll="no">
<a class="easyui-linkbutton" style="margin-top:3px" icon="icon-print" href="javascript:printall()">打印</a>

<div class="printdiv"><t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"  >
	<input id="content" type="hidden" value="${wmOmNoticeHPage.omNoticeId}">
	<table border=0 cellpadding=0 cellspacing=0 width=680 style='border-collapse:
 collapse;table-layout:fixed;width:438pt;margin-left: 30px;margin-top: -30px'>
		<col width=102 style='mso-width-source:userset;mso-width-alt:3612;width:76pt'>

		<col width=84 style='mso-width-source:userset;mso-width-alt:2986;width:63pt'>
		<col width=253 style='mso-width-source:userset;mso-width-alt:4010;width:252pt'>
		<col width=45 style='mso-width-source:userset;mso-width-alt:1592;width:34pt'>
		<col width=94  style='mso-width-source:userset;mso-width-alt:2986;
 width:73pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:50pt'>
		<col width=102 style='mso-width-source:userset;mso-width-alt:2389;width:102pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:50pt'>
		<tr height=18 style='height:13.2pt'>
			<td colspan=8 height=18 width=585 style='height:13.2pt;width:438pt'></td>
		</tr>
		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=4 height=40 class=xl67 style='height:30.0pt'><span style="font-size: 18pt">${comname}</span></td>
			<td colspan=3 class=xl68 width=168 style='width:126pt'>日期：${kprq}</td>
			<td  rowspan="2" class=xl69>
				<img src="rest/wmBaseController/showOrDownqrcodeByurl?qrvalue=${wmImNoticeHPage.noticeId}" style="width:80px;height:80px;vertical-align:right">
			</td>
		</tr>
		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=4 height=40 class=xl67 style='height:30.0pt'><span style="font-size: 18pt">入库通知单</span></td>
			<td colspan=3 class=xl68 width=168 style='width:126pt'>单号：${noticeid}</td>
			<td colspan=1 style='mso-ignore:colspan'></td>
		</tr>
		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=4 height=40 class=xl68 width=242 style='height:30.0pt;width:182pt'>货主：${cusname}</td>
			<td colspan=3 height=40 class=xl68 width=242 style='height:30.0pt;width:182pt'>供应商：${supname}</td>
			<td></td>
		</tr>
		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=4 class=xl68 width=337 style='width:252pt'>备注：${wmOmNoticeHPage.imBeizhu}</td>
			<td colspan=3 >WMS单号：${wmImNoticeHPage.noticeId}</td>
			<td></td>
		</tr>


		<tr height=33 style='mso-height-source:userset;height:25.05pt'>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>最近上架</td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>商品编码</td>
			<td height=33 class=xl65 style='height:25.05pt;border:1.0pt solid black;text-align: center'>商品名称</td>

			<td class=xl65 style='border:1.0pt solid black;text-align: center'>单位</td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>生产日期</td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>数量</td>

			<td class=xl65 style='border:1.0pt solid black;text-align: center'>托盘</td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>流通码</td>

			<td  ></td>
		</tr>

		<c:if test="${fn:length(wmImNoticeIList)  > 0 }">
		<c:forEach items="${wmImNoticeIList}" var="poVal" varStatus="stuts">

			<tr height=33 style='mso-height-source:userset;height:50px'>
				<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.binPlan }　</td>
				<td height=33 class=xl65 style='height:25.05pt;border:1.0pt solid black;text-align: center'>${poVal.goodsCode }</td>
				<td class=xl65 style='border:1.0pt solid black;text-align: center;word-break:break-all;'><span style='word-break:break-all;width: auto;font-size: 14pt'>${poVal.goodsName }</span></td>
				<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsUnit }</td>
				<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsPrdData }</td>
				<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsCount }</td>
				<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>
				<td class=xl65 align="center" valign="middle" style='border:1.0pt solid black'><img src="rest/wmBaseController/showOrDownqrcodeByurl?qrvalue=${poVal.barCode }" alt="${poVal.barCode }" style="width:40px;height:40px;vertical-align:middle;">　</td>
				<td  ></td>
			</tr>

		</c:forEach>
		</c:if>
		<tr height=20 style='height:25.0pt'>
			<td height=20 class=xl66 colspan=8 style='height:15.0pt;mso-ignore:colspan;text-align: justify'>主管：<span
					style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>制单： ${wmOmNoticeHPage.createBy}<span
					style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>仓管：<span
					style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>叉车：<span
					style='mso-spacerun:yes'></span></td>
			<td></td>
		</tr>

	</table>
</t:formvalid></div>
</body>