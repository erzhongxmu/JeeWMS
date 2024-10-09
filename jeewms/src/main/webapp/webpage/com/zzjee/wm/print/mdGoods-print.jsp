<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>商品打印</title>
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
	<table border=0 cellpadding=0 cellspacing=0 width=1000 style='border-collapse:
 collapse;table-layout:fixed;width:200pt;margin-left: 30px;margin-top: -30px'>
		<col width=20 style='mso-width-source:userset;mso-width-alt:3612;width:17pt'>
		<col width=20 style='mso-width-source:userset;mso-width-alt:1560;width:17pt'>
		<col width=20 style='mso-width-source:userset;mso-width-alt:3612;width:17pt'>
		<col width=20 style='mso-width-source:userset;mso-width-alt:1560;width:17pt'>

		<tr height=40 style='mso-height-source:userset;height:50.0pt'>
			<th colspan=4 height=40 class=xl67 style='height:50.0pt' ><span style="font-size: 18pt"></span></th>
		</tr>

		<tr height=20 style='mso-height-source:userset;height:20.05pt'>

			<td    height=20 class=xl65 style='height:20.05pt;border:1.0pt solid black;text-align: center'>品名</td>

			<td  colspan=3  class=xl65 style='border:1.0pt solid black;text-align: center'> ${mdGoodsPage.shpMingCheng} </td>

        </tr>
		<%--<tr height=20 style='mso-height-source:userset;height:20.05pt'>

			<td    height=20 class=xl65 style='height:20.05pt;border:1.0pt solid black;text-align: center'>编码</td>

			<td  colspan=3  class=xl65 style='border:1.0pt solid black;text-align: center'> ${mdGoodsPage.shpBianMa} </td>

		</tr>--%>

		<tr height=20 style='mso-height-source:userset;height:20.05pt'>
			<td    height=20 class=xl65 style='height:20.05pt;border:1.0pt solid black;text-align: center'>规格</td>
			<td    class=xl65 style='border:1.0pt solid black;text-align: center'> ${mdGoodsPage.shpGuiGe} </td>
			<td    height=20 class=xl65 style='height:20.05pt;border:1.0pt solid black;text-align: center'>单位</td>
			<td    class=xl65 style='border:1.0pt solid black;text-align: center'> ${mdGoodsPage.shlDanWei} </td>
		</tr>
<%--		<tr height=20 style='mso-height-source:userset;height:20.05pt'>--%>

<%--			<td    height=20 class=xl65 style='height:20.05pt;border:1.0pt solid black;text-align: center'>单位</td>--%>

<%--			<td    class=xl65 colspan="3" style='border:1.0pt solid black;text-align: center'> ${mdGoodsPage.shlDanWei} </td>--%>


<%--		</tr>--%>
		<%--<tr height=20 style='mso-height-source:userset;height:20.05pt'>--%>

			<%--<td    height=20 class=xl65 style='height:20.05pt;border:1.0pt solid black;text-align: center'>品牌</td>--%>

			<%--<td  colspan=3  class=xl65 style='border:1.0pt solid black;text-align: left'> ${mdGoodsPage.shpPinPai} </td>--%>

		<%--</tr>--%>



		<tr height=40 style='mso-height-source:userset '>
			<td height=20 class=xl65 style='height:20.05pt;border:1.0pt solid black;text-align: center' >条码</td>
			<th colspan=3 height=80 class=xl65  style='border:1.0pt solid black;text-align: center' ><span style="font-size: 15pt"><img width="90%" src="rest/wmBaseController/showOrDownbarcodeByurl.do?qrvalue=${mdGoodsPage.shpBianMa }" /></span></th>
		</tr>
	</table>
</t:formvalid></div>

<a class="easyui-linkbutton" style="margin-top:3px" icon="icon-print" href="javascript:printall()">打印</a>

</body>
