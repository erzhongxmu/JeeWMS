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
	</style>
</head>
<body style="overflow-y:auto" scroll="no">
<a class="easyui-linkbutton" style="margin-top:3px" icon="icon-print" href="javascript:printall()">打印</a>

<div class="printdiv" style="margin-top: 10px">
	<c:if test="${fn:length(wmImNoticeIList)  > 0 }">
		<c:forEach items="${wmImNoticeIList}" var="poVal" varStatus="stuts">
			<div  style="text-align:center;">
					<span>
							${poVal.goodsName }
					</span>
				<div id="printContent" style="margin-top: 10px;page-break-after:always">
					<img src="rest/wmBaseController/showOrDownbarcodeByurl?qrvalue=${poVal.barCode }" alt="${poVal.barCode }" style="height:40px;vertical-align:middle;">
				</div>
			</div>
		</c:forEach>
	</c:if>
</div>
</body>
