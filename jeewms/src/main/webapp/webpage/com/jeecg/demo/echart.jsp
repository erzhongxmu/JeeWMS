<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/ichart/css/gallery.css"></link>
<script type="text/javascript" src="plug-in/echarts/echarts.js"></script>
</head>
<body style="padding:0">  
<div style="padding:10px;clear: both;">  
    <div id="psline" style="height:500px;"></div>  
</div>  
</body>  

<script type="text/javascript">  
    //图表  
    var myChart = echarts.init(document.getElementById('psline'));  
  
 // 显示标题，图例和空的坐标轴
    myChart.setOption({

        tooltip: {},
        legend: {
            data:['登录次数']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '登录次数',
            type: 'bar',
            data: []
        }]
    });
    myChart.showLoading();
 // 异步加载数据
    $.get('jeecgListDemoController.do?doEchart').done(function (data) {
        myChart.hideLoading();

        var obj = eval('(' + data + ')');;
        console.log(obj.obj);
        myChart.setOption({
            title: {
                text: '登录次数'
            },
            xAxis: {
                 data: obj.obj.yAxis[0].data,
            },
            series: [{
                name: '登录次数',
                 data: obj.obj.series[0].data,
            }]
        });
    });
   
 
</script>  

</html>
