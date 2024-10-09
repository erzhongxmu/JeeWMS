<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>出库单打印</title>
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
			font-size:22pt;
			text-align:center;
			border:1.0pt solid black;}
		.xl66
		{mso-style-parent:style0;
			color:black;
			font-size:22pt;}
		.xl67
		{mso-style-parent:style0;
			color:black;
			font-size:25pt;
			text-align:center;}
		.xl68
		{mso-style-parent:style0;
			color:black;
			font-size:22pt;
			white-space:normal;}
		.xl69
		{mso-style-parent:style0;
			text-align:center;}

	</style>

</head>
<body style="overflow-y:auto" scroll="no">
<a class="easyui-linkbutton" style="margin-top:3px" icon="icon-print" href="javascript:printall()">打印</a>

<div class="printdiv"><t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"  >
	<input id="content" type="hidden" value="${printHeader.header05}">
	<table border=0 cellpadding=0 cellspacing=0 width=680 style='border-collapse:
 collapse;table-layout:fixed;width:438pt;margin-left: 30px;margin-top: -30px'>
		<col width=100 style='mso-width-source:userset;mso-width-alt:3612;width:100pt'>

			<%--<col width=84 style='mso-width-source:userset;mso-width-alt:2986;width:63pt'>--%>
		<col width=100 style='mso-width-source:userset;mso-width-alt:4010;width:80pt'>
		<col width=100 style='mso-width-source:userset;mso-width-alt:1592;width:80pt'>
		<col width=100  style='mso-width-source:userset;mso-width-alt:2986;width:70pt'>
		<col width=77 style='mso-width-source:userset;mso-width-alt:2389;width:77pt'>
		<col width=102 style='mso-width-source:userset;mso-width-alt:2389;width:70pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:70pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:70pt'>
		<col width=67 style='mso-width-source:userset;mso-width-alt:2389;width:50pt'>


		<tr height=18 style='height:13.2pt'>
			<td colspan=9 height=18 width=585 style='height:13.2pt;width:438pt'></td>
		</tr>
		<tr height=18 style='height:13.2pt'>

			<td colspan=8 height=18 width=585 style='height:13.2pt;width:438pt;text-align: center'><span style="font-size: 25pt">出库单</span></td>
			<td  rowspan="3" class=xl69>
				<img src="images/icon_sx.jpeg" style="width:80px;height:80px;vertical-align:right">
			</td>
		</tr>
		<tr height=10 style='mso-height-source:userset;height:10.0pt'>

			<td colspan=3 class=xl68 width=168 ><span style="font-size: 20pt">${printHeader.header14}</span></td>
			<td colspan=3 height=40 class=168 style='height:30.0pt'><span style="font-size: 20pt">${printHeader.header15} </span></td>
			<td colspan=2 height=40 class=xl67 style='height:30.0pt'><span style="font-size: 20pt"> </span></td>

		</tr>
<%--		<tr height=40 style='mso-height-source:userset;height:30.0pt'>--%>
<%--			<td colspan=3 height=40 class=xl67 style='height:30.0pt'><span style="font-size: 22pt">${printHeader.header17}</span> </td>--%>
<%--			<td colspan=3 class=xl68 width=168 style='width:126pt'><span style="font-size: 22pt">${printHeader.header18}</span></td>--%>
<%--			<td colspan=3 class=xl68 width=168 style='width:126pt'><span style="font-size: 22pt">${printHeader.header19}</span></td>--%>
<%--		</tr>--%>
		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=4 height=40 class=xl68 style='height:30.0pt'><span style="font-size: 20pt">${printHeader.header06}</span></td>
			<td colspan=5 height=40 class=xl68 style='height:30.0pt'><span style="font-size: 20pt">${printHeader.header05}</span></td>
<%--			<td colspan=3 height=40 class=xl68 style='height:30.0pt'><span style="font-size: 22pt">${printHeader.header21}</span></td>--%>
		</tr>
		<tr height=40 style='mso-height-source:userset;height:30.0pt'>
			<td colspan=4 class=xl68 style='width:252pt'><span style="font-size: 20pt">${printHeader.header10}</span></td>
<%--			<td colspan=3 class=xl68 style='width:252pt'><span style="font-size: 20pt">${printHeader.header20}</span></td>--%>
			<td colspan=5 class=xl68 style='width:252pt'><span style="font-size: 20pt">${printHeader.header09}</span></td>
		</tr>


		<tr  style='mso-height-source:userset;height:18.05pt'>
				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>序号</td>--%>

				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>商品编码</td>--%>
				<%--				&lt;%&ndash;<td class=xl65 style='border:1.0pt solid black;text-align: center'>商品编码</td>&ndash;%&gt;--%>
				<%--			<td height=33 class=xl65 style='height:25.05pt;border:1.0pt solid black;text-align: center'>商品</td>--%>
				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>生产日期</td>--%>

				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>箱数</td>--%>
				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>数量</td>--%>
				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>重量</td>--%>

				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>体积</td>--%>
				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>库存</td>--%>

			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 22pt">商品</span></td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 22pt">名称</span></td>
				<%--<td class=xl65 style='border:1.0pt solid black;text-align: center'>商品编码</td>--%>
			<td height=33 class=xl65 style='height:25.05pt;border:1.0pt solid black;text-align: center'><span style="font-size: 22pt">单价</span></td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 22pt">批次</span></td>

			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 22pt">规格</span></td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 22pt">数量</span></td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 22pt">重量</span></td>

			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 22pt">库区</span></td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 22pt">备注</span></td>
				<%--			<td class=xl65 style='border:1.0pt solid black;text-align: center'>备注</td>--%>
		</tr>

		<c:if test="${fn:length(listitem)  > 0 }">
			<c:forEach items="${listitem}" var="poVal" varStatus="stuts">

				<tr  style='mso-height-source:userset;height:30px'>
					<td class=xl65 style='border:1.0pt solid black;text-align: center;word-break:break-all;'><span style="font-size: 18pt">${poVal.item01 }</span>　</td>
						<%--<td height=33 class=xl65 style='height:25.05pt;border:1.0pt solid black;text-align: center'>${poVal.goodsCode }</td>--%>
					<td class=xl65 style='border:1.0pt solid black;text-align: center;word-break:break-all;'><span style="font-size: 18pt">${poVal.item02 }</span></td>
					<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 18pt">${poVal.item11}</span></td>

					<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>
					<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 18pt">${poVal.item10 }</span></td>
					<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 18pt">${poVal.item06 }</span></td>

					<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 18pt">${poVal.item07 }</span></td>
					<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 18pt">成品区</span></td>

					<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>


					<td  ></td>
				</tr>

			</c:forEach>
			<tr height=33 style='mso-height-source:userset;height:50px'>
				<td class=xl65 style='border:1.0pt solid black;text-align: left' colspan="5"><span style="font-size: 18pt">合计：</span></td>
					<%--				<td class=xl65 style='border:1.0pt solid black;text-align: center;word-break:break-all;' colspan="4"><span style='word-break:break-all;width: auto;font-size: 14pt'></span></td>--%>
					<%--				<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>--%>

					<%--				<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>--%>
					<%--				<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>--%>
				<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 18pt">${printHeader.header12}</span></td>

				<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 18pt">${printHeader.header13}</span></td>
				<td class=xl65 style='border:1.0pt solid black;text-align: center' colspan="2"></td>

					<%--				<td class=xl65 style='border:1.0pt solid black;text-align: center'></td>--%>
			</tr>
		</c:if>
		<tr height=33 style='mso-height-source:userset;height:50px'>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 18pt">收货人</span></td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center;word-break:break-all;'><span style="font-size: 18pt">产品有无破损</span></td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center'><span style="font-size: 18pt">有 &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 无</span> </td>

			<td class=xl65 style='border:1.0pt solid black;vertical-align:top;text-align: left' colspan="3" rowspan="2" ><span style="font-size: 18pt">收货人签字:</span></td>

			<td class=xl65 style='border:1.0pt solid black;vertical-align:top;text-align: left' colspan="3" rowspan="2"><span style="font-size: 18pt">意见栏：</span></td>
		</tr>
		<tr height=33 style='mso-height-source:userset;height:50px'>
			<td class=xl65 style='border:1.0pt solid black;text-align: center' ><span style="font-size: 18pt">注意事项</span></td>
			<td class=xl65 style='border:1.0pt solid black;text-align: center' colspan="2"></td>
		</tr>
		<tr height=40 style='mso-height-source:userset;height:60px;width: 822px'>
			<td class=xl65 style='border:1.0pt solid black;vertical-align:top;text-align: left;word-wrap:break-word;word-break:break-all;' colspan="9" ><span style="font-size: 18pt">
				重要提示：尊敬的客户，因您购买的商品为贵重易碎物品，请你认真确认送达产品的完好性以及产品的型号、颜色、数量是否与您购买时一致，配件是否齐全。验收并核对无误后请当面签字确认，若有发现破损或缺件商品，可对该商品予以拒收。客户签收后，我们对产品质量以外的问题可予以积极协助，但不作退货处理或承担相关损失。

			</span></td>
		</tr>
		<tr height=15 style='height:25.0pt'>
			<td height=20 class=xl66 colspan=3 style='height:15.0pt;mso-ignore:colspan;text-align: justify'>
				<span style="font-size: 18pt">拣货员:</span>
			</td>
			<td height=20 class=xl66 colspan=3 style='height:15.0pt;mso-ignore:colspan;text-align: justify'>
				<span style="font-size: 18pt">复核员:</span>
			</td>
			<td height=20 class=xl66 colspan=3 style='height:15.0pt;mso-ignore:colspan;text-align: justify'>
				<span style="font-size: 18pt">司机:</span>
			</td>
		</tr>
		<tr height=15 style='height:25.0pt'>
			<td height=20 class=xl66 colspan=""  style='height:15.0pt;mso-ignore:colspan;text-align: justify'>
				<span style="font-size: 18pt">白联:存根</span>
			</td>
			<td height=20 class=xl66 colspan="2"  style='height:15.0pt;mso-ignore:colspan;text-align: justify'>
				<span style="font-size: 18pt">红联:回执</span>
			</td>
			<td height=20 class=xl66 colspan="2"  style='height:15.0pt;mso-ignore:colspan;text-align: justify'>
				<span style="font-size: 18pt">蓝联:</span>
			</td>
			<td height=20 class=xl66 colspan=2 style='height:15.0pt;mso-ignore:colspan;text-align: justify'>
				<span style="font-size: 18pt">绿联:财务</span>
			</td>
			<td height=20 class=xl66 colspan=2 style='height:15.0pt;mso-ignore:colspan;text-align: justify'>
				<span style="font-size: 18pt">黄联:客户</span>
			</td>
		</tr>

	</table>
</t:formvalid></div>
</body>
