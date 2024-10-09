<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<c:if test="${show == '1'}">
<t:base type="jquery,easyui"></t:base>
<script type="text/javascript" src="plug-in/easyui/portal/jquery.portal.js"></script>
<link rel="stylesheet" type="text/css" href="plug-in/easyui/portal/portal.css">
</c:if>
<script type="text/javascript" src="plug-in/login/js/getMsgs.js"></script>

<script type="text/javascript" src="plug-in/Highcharts-2.2.5/js/highcharts.src.js"></script>
<script type="text/javascript" src="plug-in/Highcharts-2.2.5/js/modules/exporting.src.js"></script>
</head>

<div id="main_portal" style="height:100%;">
<div></div>
<div></div>
</div>

<div style="display: none">
	<div id="p1" title=<t:mutiLang langKey="com.sysname"/> collapsible="true">
		<div title=<t:mutiLang langKey="com.sysname"/> collapsible="true" closable="flase" style="height:350px;" >


	</div>

	<div id="p2" title="效期预警">
<!--       <div class="row J_mainContent" id="content-main" style="margin-left:-13px;"> -->
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="BiController.do?homebi" frameborder="0"   seamless></iframe>
<!--         </div> -->
	</div>

	<div id="p3">
	</div>
	<div id="p4">
	</div>
</div>
<script type="text/javascript">
$("#main_portal").css("height","100%");
<!--
var mainPortal;
$(function() {
	mainPanels =new Array();
	//if($("#p1").length==1){
		mainPanels.push({
			id : 'p1',
			title : 'WMS',
			height : 300,
			collapsible : true
		});
	//};
	//if($("#p2").length==1){
		mainPanels.push({
			id : 'p2',
			title : '系统提醒',
			height : 300,
			collapsible : true,
			tools:[{
			       iconCls:'icon-search',
			       handler:function(){
			         //MUI.openCenterTab('部门公告','affiche/dept/load/my.do?rel=gtpt_bmgg','gtpt_bmgg',false,true)
			       }
			    }
			  ]
		});
	//};
	
	//if($("#p3").length==1){
		mainPanels.push({
			id : 'p3',
			title : '用户分析',
			height : 290,
			collapsible : true,
			//href: 'logController.do?userBroswer&reportType=line',
			tools:[{
			       iconCls:'icon-search',
			       handler:function(){
			         //MUI.openCenterTab('我的会议','meeting/loadtome.do?rel=ggpt_hygl','ggpt_hygl',false,true)
			       }
			    }
			  ]
		});
	//};
	//if($("#p4").length==1){
		mainPanels.push({
			id : 'p4',
			title : '系统信息',
			height : 290,
			collapsible : true,
			//href: 'tSSmsController.do?getSysInfos',
			tools:[{
			       iconCls:'icon-search',
			       handler:function(){
			        // MUI.openCenterTab('新闻','news/load_news.do?rel=grbg_xw','grbg_xw',false,true)
			       }
			    }
			  ]
		});
	//};
	
	mainPortal = $('#main_portal').portal({
		border : false,
		fit : true,
		onStateChange : function() {
			$.cookie('portal-state', getPortalState(),{expires:7});
		}
	});
	var state = $.cookie('portal-state');
	if (!state) {
		state = 'p1,p3,:p2,p4';/*冒号代表列，逗号代表行*/
	}
	addPortalPanels(state);
	mainPortal.portal('resize');
	
 	$.post(
		'logController.do?userBroswer&reportType=line',
		function(data){
			$("#p3").html(data);
		}		
	);
	
	$.post(
		'tSSmsController.do?getSysInfos',
		function(data){
			var show = '${show}',html='';
			if(show == '0'){
				var REG_BODY = /<body[^>]*>([\s\S]*)<\/body>/,content='';
				var result = REG_BODY.exec(data);
				if(result && result.length === 2){
					content = result[1];
				}
				html = content;
			} else {
				html = data;
			}
			$("#p4").html(html);
			
		}		
	);
	
	
});

function getPanelOptions(id) {
	for ( var i = 0; i < mainPanels.length; i++) {
		if (mainPanels[i].id == id) {
			return mainPanels[i];
		}
	}
	return undefined;
}
function getPortalState() {
	var aa = [];
	for ( var columnIndex = 0; columnIndex < 2; columnIndex++) {
		var cc = [];
		var panels = mainPortal.portal('getPanels', columnIndex);
		for ( var i = 0; i < panels.length; i++) {
			cc.push(panels[i].attr('id'));
		}
		aa.push(cc.join(','));
	}
	return aa.join(':');
}
function addPortalPanels(portalState) {

	var columns = portalState.split(':');
	for ( var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
		var cc = columns[columnIndex].split(',');
		for ( var j = 0; j < cc.length; j++) {
			var options = getPanelOptions(cc[j]);
			if (options) {
				var p=$("#"+options.id);
				 if(p.length>0){
					p.panel(options);
					mainPortal.portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				} 
			}
		}
	}
}
//-->
</script>
<!-- update-end--Author:jg_renjie  Date:20160315 for：配合首页改造，引入portal组件 -->