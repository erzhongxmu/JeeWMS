<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,tools"></t:base>
</head>
<body >
	<div style="width:95%;">
	    <legend>${notice.noticeTitle}</legend>
			<div class="form">
				<label class="form"><fmt:formatDate value='${notice.createTime}' type='both'/></label>
			</div>
	    <hr style="BORDER-RIGHT: #00686b 1px dotted; BORDER-TOP: #00686b 1px dotted; BORDER-LEFT: #00686b 1px dotted; BORDER-BOTTOM: #00686b 1px dotted" noShade SIZE=1/>
			<div class="form">
				${notice.noticeContent}
		    </div>
	    </div>
    </div>
</body>
</html>