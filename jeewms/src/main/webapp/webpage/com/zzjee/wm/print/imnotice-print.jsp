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
<body style="overflow-y:auto" scroll="no">
<a class="easyui-linkbutton" style="margin-top:3px" icon="icon-print" href="javascript:printall()">打印</a>

<div class="printdiv"><t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"  >
	<input id="content" type="hidden" value="${wmOmNoticeHPage.omNoticeId}">
	<table border=0 cellpadding=0 cellspacing=0 width=680 style='border-collapse:
 collapse;table-layout:fixed;width:438pt;margin-left: 50px;margin-top: -30px'>
		<col width=102 style='mso-width-source:userset;mso-width-alt:3612;width:76pt'>

			<%--<col width=84 style='mso-width-source:userset;mso-width-alt:2986;width:63pt'>--%>
		<col width=253 style='mso-width-source:userset;mso-width-alt:4010;width:252pt'>
		<col width=45 style='mso-width-source:userset;mso-width-alt:1592;width:34pt'>
		<col width=94  style='mso-width-source:userset;mso-width-alt:2986;
 width:34pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:50pt'>
		<col width=102 style='mso-width-source:userset;mso-width-alt:2389;width:60pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:50pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:50pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:50pt'>

		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:50pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:50pt'>

		<tr height=18 style='height:13.2pt'>
			<td colspan=9 height=18 width=585 style='height:13.2pt;width:438pt'></td>
		</tr>
		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=11 height=40 class=xl67 style='height:30.0pt;text-align: center;'><span style="font-size: 18pt">${comname}入库通知单</span></td>

		</tr>
		<tr height=25 style='mso-height-source:userset;height:25.0pt'>
			<td colspan=4 height=25 class=xl68 width=242 style='height:25.0pt;width:182pt'>日期：${kprq}</td>
			<td colspan=4 class=xl68 width=168 style='width:126pt'>采购单号：${noticeid}</td>
			<td  rowspan="3" class=xl69>
				<img src="rest/wmBaseController/showOrDownqrcodeByurl?qrvalue=${wmImNoticeHPage.noticeId}" style="width:80px;height:80px;vertical-align:right">
			</td>
			<td></td>
			<td rowspan="7" style="font-size: 10px;">①<br/>白<br/>存<br/>根<br/><br/>②<br/>红<br/>货<br/>主<br/><br/>③<br/>绿<br/>回<br/>单<br/><br/>④<br/>黄<br/>财<br/>务<br/></td>
		</tr>
		<tr height=25 style='mso-height-source:userset;height:25.0pt'>
			<td colspan=4 height=25 class=xl68 width=242 style='height:25.0pt;width:182pt'>货主：${cusname}</td>
			<td colspan=4 height=25 class=xl68 width=242 style='height:25pt;width:182pt'>生产厂商：${supname}</td>
		</tr>
		<tr height=25 style='mso-height-source:userset;height:25.0pt'>
			<td colspan=4 class=xl68 width=337 style='width:252pt'>备注：${wmImNoticeHPage.imBeizhu}</td>
			<td colspan=4 >进货单号：${wmImNoticeHPage.noticeId}</td>
		</tr>
		<tr height=25 style='mso-height-source:userset;height:25.0pt'>
			<td colspan=8 class=xl68 width=337 style='width:252pt'>仓库：${storeName}</td>
		</tr>



		<tr height=33 style='mso-height-source:userset;height:25.05pt'>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>商品编码</td>
				<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>商品编码</td>--%>
			<td height=33 class=xl65 style='height:25.05pt;border:1.0pt solid black;text-align: center'>商品</td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>规格</td>

			<td class=xl65 style='border:1.0pt solid black;text-align: center'>单位</td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>生产日期</td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>生产批号</td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'>保质期</td>

			<td class=xl65 style='border:1.0pt solid black;text-align: center'>数量</td>
				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>体积</td>--%>
				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>重量</td>--%>

			<td class=xl65 style='border:1.0pt solid black;text-align: center'>储位</td>

			<td class=xl65 style='border:1.0pt solid black;text-align: center'>二维码</td>

		</tr>

		<c:if test="${fn:length(wmImNoticeIList)  > 0 }">
			<c:forEach items="${wmImNoticeIList}" var="poVal" varStatus="stuts">

				<tr height=33 style='mso-height-source:userset;height:50px'>
					<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsCode }　</td>
						<%--<td height=33 class=xl65 style='height:25.05pt;border:1.0pt solid black;text-align: center'>${poVal.goodsCode }</td>--%>
					<td class=xl65 style='border:1.0pt solid black;text-align: center;word-break:break-all;'><span style='word-break:break-all;width: auto;font-size: 14pt'>${poVal.goodsName }</span></td>
					<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.shpGuiGe }</td>

					<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsUnit }</td>
					<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsPrdData }</td>
					<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>
					<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.bzhiQi }</td>

					<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsCount }</td>
						<%--				<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsFvol }</td>--%>

						<%--				<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsWeight }</td>--%>

					<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>

					<td class=xl65 align="center" valign="middle" style='border:1.0pt solid black'><img src="rest/wmBaseController/showOrDownqrcodeByurl?qrvalue=${poVal.goodsCode }" alt="${poVal.goodsCode }" style="width:40px;height:40px;vertical-align:middle;">　</td>
					<td  ></td>
				</tr>

			</c:forEach>
			<tr height=33 style='mso-height-source:userset;height:50px'>
				<td class=xl65 style='border:1.0pt solid black;text-align: center'>合计</td>
					<%--<td height=33 class=xl65 style='height:25.05pt;border:1.0pt solid black;text-align: center'>${poVal.goodsCode }</td>--%>
				<td class=xl65 colspan="6" style='border:1.0pt solid black;text-align: center;word-break:break-all;'><span style='word-break:break-all;width: auto;font-size: 14pt'></span></td>
					<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>

                    <td class=xl65 style='border:1.0pt solid black;text-align: center'></td>
                    <td class=xl65 style='border:1.0pt solid black;text-align: center'></td>
                    <td class=xl65 style='border:1.0pt solid black;text-align: center'></td>--%>

				<td class=xl65 style='border:1.0pt solid black;text-align: center'>${totalCount}</td>
					<%--				<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsFvol }</td>--%>

					<%--				<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.goodsWeight }</td>--%>

				<td class=xl65 colspan="2"  style='border:1.0pt solid black;text-align: center'></td>

					<%--<td class=xl65 align="center" valign="middle" style='border:1.0pt solid black'><img src="rest/wmBaseController/showOrDownqrcodeByurl?qrvalue=${poVal.goodsCode }" alt="${poVal.goodsCode }" style="width:40px;height:40px;vertical-align:middle;">　</td>
                    --%>
			</tr>
		</c:if>
		<tr height=20 style='height:25.0pt'>
			<td height=20 class=xl66 colspan=8 style='height:15.0pt;mso-ignore:colspan;text-align: justify'>制单：<span
					style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>仓管： ${wmOmNoticeHPage.createBy}<span
					style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>主管：<span
					style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span>叉车：<span
					style='mso-spacerun:yes'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
			<td></td>
		</tr>

	</table>
</t:formvalid></div>
</body>
