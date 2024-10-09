<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>追溯页面</title>
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

<div class="printdiv"><t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"  >
	<input id="content" type="hidden" value="${wmOmNoticeHPage.omNoticeId}">
	<table border=0 cellpadding=0 cellspacing=0 width=2340 style='border-collapse:
 collapse;table-layout:fixed;width:438pt;margin-left: 30px;margin-top: -30px'>
		<col width=102 style='mso-width-source:userset;mso-width-alt:3612;width:76pt'>
		<col width=45 style='mso-width-source:userset;mso-width-alt:1560;width:34pt'>
		<col width=102 style='mso-width-source:userset;mso-width-alt:3612;width:76pt'>
		<col width=45 style='mso-width-source:userset;mso-width-alt:1560;width:34pt'>

		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<th colspan=12 height=40 class=xl67 style='height:30.0pt' ><span style="font-size: 18pt">${comname}</span></th>
		</tr>
		    <td colspan=3 class=xl68 width=168 style='width:126pt'>日期：${kprq}</td>
			<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=4 height=40 class=xl68 width=242 style='height:30.0pt;width:182pt'>货主：${cusname}</td>

		</tr>
		</tr>
		<td colspan=4 height=40 class=xl68 width=242 style='height:30.0pt;width:182pt'>客户：${ocusname}</td>
		</tr>



		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=2 class=xl68 width=337 style='width:252pt'>备注：${wmOmNoticeHPage.omBeizhu}</td>

		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=2 >单号：${wmOmNoticeHPage.omNoticeId}</td>
	</tr>
    .
		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=4 class=xl68 width=337 style='width:252pt'>地址：${wmOmNoticeHPage.delvAddr}</td>

		</tr>

		<%--<tr height=40 style='mso-height-source:userset;height:30.0pt'>--%>
			<%--<td colspan=5 class=xl68 width=337 style='width:252pt'>总体积：${tijisum}</td>--%>
			<%--<td colspan=4 >总重量：${zhlsum}</td>--%>
			<%--<td></td>--%>
		<%--</tr>--%>
		<td>
			          .
		</td>

		<tr height=20 style='mso-height-source:userset;height:20.05pt'>
			<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>储位</td>--%>
			<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>商品编码</td>--%>
			<td  colspan=3  height=20 class=xl65 style='height:20.05pt;border:1.0pt solid black;text-align: center'>商品</td>

			<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>单位</td>--%>
			<td  colspan=3  class=xl65 style='border:1.0pt solid black;text-align: center'>生产日期</td>
			<td  colspan=2  class=xl65 style='border:1.0pt solid black;text-align: center'>数量</td>
			<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>重量</td>--%>
			<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>体积</td>--%>
			<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>规格</td>--%>
			<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>拣货</td>--%>
			<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>品质</td>--%>
					<%--<td  colspan=2  class=xl65 style='border:1.0pt solid black;text-align: center'>追溯码</td>--%>

                    <td  ></td>
                </tr>

                <c:if test="${fn:length(wmOmQmIList)  > 0 }">
                <c:forEach items="${wmOmQmIList}" var="poVal" varStatus="stuts">

                    <tr height=20 style='mso-height-source:userset;height:20px'>
                        <%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.binId }　</td>--%>
				<%--<td height=33 class=xl65 style='height:25.05pt;border:1.0pt solid black;text-align: center'>${poVal.goodsId }</td>--%>
				<td colspan=3 class=xl65 style='border:1.0pt solid black;text-align: center;word-break:break-all;'><span style='word-break:break-all;width: auto;font-size: 10pt'>${poVal.goodsName }</span></td>
				<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.baseUnit }</td>--%>
				<td colspan=3 class=xl65 style='border:1.0pt solid black;text-align: center'><span style='word-break:break-all;width: auto;font-size: 10pt'>${poVal.proData }</span></td>
				<td colspan=2 class=xl65 style='border:1.0pt solid black;text-align: center'><span style='word-break:break-all;width: auto;font-size: 10pt'>${poVal.baseGoodscount }</span></td>
				<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.tinZhl }</td>--%>
				<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.tinTj }</td>--%>
				<%--<tdcolspan=2 class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.shpGuiGe }</td>--%>
				<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.pickNotice }</td>--%>
				<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>${poVal.tinId }/${poVal.baoZhiq }</td>--%>

				<td  ></td>
			</tr>

		</c:forEach>
		</c:if>

		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<th colspan=3 height=400 class=xl67 style='height:30.0pt' ><span style="font-size: 18pt"><img src="systemController/showOrDownByurl.do?dbPath=${mdcusother.zuZhiJiGou }"    ></span></th>
		</tr>
	</table>
</t:formvalid></div>
</body>