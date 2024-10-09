<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- context path -->
<t:base type="jquery,easyui"></t:base>
<script type="text/javascript" src="plug-in/Highcharts-2.2.5/js/highcharts.src.js"></script>
<script type="text/javascript" src="plug-in/Highcharts-2.2.5/js/modules/exporting.src.js"></script>
	<%--<%@include file="/webpage/mobile/mobile_head.jsp" %>--%>
	<link rel="stylesheet" href="mobile/fonts/themify/themify.css">
	<link rel="stylesheet" href="mobile/css/home.css">
	<link rel="stylesheet" href="mobile/css/custom-mobile.css">
	<link rel="stylesheet" href="mobile/css/examples/pages/home/ecommerce.css">
	<link rel="stylesheet" href="mobile/themes/classic/global/css/bootstrap.css">
	<link rel="stylesheet" href="mobile/themes/classic/base/css/site.css" id="admui-siteStyle">

	<!-- 图标 CSS-->
	<link rel="stylesheet" href="mobile/fonts/font-awesome/font-awesome.css">
	<link rel="stylesheet" href="mobile/fonts/web-icons/web-icons.css">

	<!-- 插件 CSS -->
	<link rel="stylesheet" href="mobile/vendor/animsition/animsition.css">
	<link rel="stylesheet" href="mobile/vendor/toastr/toastr.css">
	<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

	<script type="text/javascript">
        $(function() {
            $(document).ready(function() {
                var chart;
                $.ajax({
                    type : "POST",
                    url : "BiController.do?dayCount&reportType=line",
                    success : function(jsondata) {
                        data = eval(jsondata);
                        var xAxisCategories = new Array();

                        for(var i = 0; i < data[0].data.length; i++){
                            xAxisCategories[i] = data[0].data[i].name;
                        }
                        chart = new Highcharts.Chart({
                            chart : {
                                renderTo : 'containerdayline',
                                plotBackgroundColor : null,
                                plotBorderWidth : null,
                                plotShadow : false
                            },
                            title : {
                                text : '建单数人员对比'
                            },
                            xAxis : {
                                categories : xAxisCategories
                            },
                            tooltip : {
                                shadow: false,
                                percentageDecimals : 0,
                                formatter: function() {
                                    return  '<b>'+this.point.name + '</b>:' +  Highcharts.numberFormat(this.y, 0) ;
                                }

                            },

                            plotOptions : {
                                pie : {
                                    allowPointSelect : true,
                                    cursor : 'pointer',
                                    showInLegend : true,
                                    dataLabels : {
                                        enabled : true,
                                        color : '#000000',
                                        connectorColor : '#000000',
                                        formatter : function() {
                                            return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage, 1)+"%";
                                        }
                                    }
                                }
                            },
                            series : data
                        });
                    }
                });
            });
        });
	</script>
<script type="text/javascript">
	$(function() {
		$(document).ready(function() {
			var chart;
			$.ajax({
				type : "POST",
				url : "BiController.do?cpNamedownCount&reportType=column",
				success : function(jsondata) {
					data = eval(jsondata);
                    var xAxisCategories = new Array();

                    for(var i = 0; i < data[0].data.length; i++){
                        xAxisCategories[i] = data[0].data[i].name;
                    }
					//console.log(data);//Highcharts报表插件bug,IE8下不能出现该语句,否则报表不显示					

					chart = new Highcharts.Chart({
						chart : {
							renderTo : 'containerCol',
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : '验收数排行'
						},
						xAxis : {

							categories :xAxisCategories

						},
						tooltip : {
							 percentageDecimals : 0,
							 formatter: function() {
            					return  '<b>'+this.point.name + '</b>:' +  Highcharts.numberFormat(this.y, 0);
         					}

						},

						plotOptions : {
							column : {
								allowPointSelect : true,
								cursor : 'pointer',
								showInLegend : true,
								dataLabels : {
									enabled : true,
									color : '#000000',
									connectorColor : '#000000',
									formatter : function() {

										return  Highcharts.numberFormat(this.y, 1);

									}
								}
							}
						},

						series:data//,IE8不喜欢多余的逗号

					});
				}
			});
		});
	});
</script>


<script type="text/javascript">
	$(function() {
		$(document).ready(function() {
			var chart;
			$.ajax({
				type : "POST",
				url : "BiController.do?cpNameupCount&reportType=pie",
				success : function(jsondata) {
					data = eval(jsondata);
                    var xAxisCategories = new Array();

                    for(var i = 0; i < data[0].data.length; i++){
                        xAxisCategories[i] = data[0].data[i].name;
                    }
					chart = new Highcharts.Chart({
						chart : {
							renderTo : 'containerPie',
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : '上下架排行'
						},
						xAxis : {
							categories : xAxisCategories
						},
						tooltip : {
							shadow: false,
							percentageDecimals : 0,
							formatter: function() {
            					return  '<b>'+this.point.name + '</b>:' +  Highcharts.numberFormat(this.y, 0) ;
         					}

						},

						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								showInLegend : true,
								dataLabels : {
									enabled : true,
									color : '#000000',
									connectorColor : '#000000',
									formatter : function() {
										return   Highcharts.numberFormat(this.y, 1);
									}
								}
							}
						},
						series : data
					});
				}
			});
		});
	});
</script>
	<script>
        setInterval(function(){
            var myDate = new Date();
            var month=myDate.getMonth()+1;
            month =(month<10 ? "0"+month:month);
            var x="欢迎您使用WMS人员效率主题看板，现在时间："+myDate.getFullYear()+"年"+month+"月"+myDate.getDate()+"日 "+myDate.getHours()+"点"+myDate.getMinutes()+"分"+myDate.getSeconds()+"秒";
            document.getElementById("x"). innerHTML=x;
        },1000);
	</script>
	<style>
		.a{background:red;width:350px;height:80px;color:#ffffff;font-size:13px;line-height:80px;text-align:center}
	</style>
</head>

	<div class="col-ms-12 col-xs-12 col-md-12" id="ecommerceChartView">
		<div class="widget widget-shadow">
			<div class="widget-content tab-content bg-white padding-10">

					<div class="media" style="border:0px">
						<div class="media-body" style="padding-top: 10px;padding-left: 50px; ">
							<h3 class="media-heading" id=x> </h3>


						</div>


					</div>

			</div>
		</div>
	</div>
<div style="padding-bottom: -100px;padding-top: -100px;  height: 140px ">
	<div  >

		<div class="col-lg-3 col-sm-3 col-xs-12 info-panel">
			<div class="widget widget-shadow">
				<div class="widget-content bg-white padding-20">
					<button type="button" class="btn btn-floating btn-sm btn-warning">
						<i class="icon wb-shopping-cart"></i>
					</button>
					<span class="margin-left-15 font-weight-400">订单总数</span>
					<div class="content-text text-center margin-bottom-0">
						<i class="text-danger icon wb-triangle-up font-size-20"> </i>
						<span class="font-size-30 font-weight-100">${num1}</span>
						<p class="blue-grey-400 font-weight-100 margin-0">订单总数</p>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-3 col-sm-3 col-xs-12 info-panel">
			<div class="widget widget-shadow">
				<div class="widget-content bg-white padding-20">
					<button type="button" class="btn btn-floating btn-sm btn-danger">
						<i class="icon fa-yen"></i>
					</button>
					<span class="margin-left-15 font-weight-400">验收总次数</span>
					<div class="content-text text-center margin-bottom-0">
						<i class="text-success icon wb-triangle-up font-size-20"> </i>
						<span class="font-size-30 font-weight-100">${num2}</span>
						<p class="blue-grey-400 font-weight-100 margin-0">验收总次数</p>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-3 col-sm-3 col-xs-12 info-panel">
			<div class="widget widget-shadow">
				<div class="widget-content bg-white padding-20">
					<button type="button" class="btn btn-floating btn-sm btn-success">
						<i class="icon wb-eye"></i>
					</button>
					<span class="margin-left-15 font-weight-400">上架总次数</span>
					<div class="content-text text-center margin-bottom-0">
						<i class="text-danger icon wb-triangle-up font-size-20"> </i>
						<span class="font-size-30 font-weight-100">${num3}</span>
						<p class="blue-grey-400 font-weight-100 margin-0">上架总次数</p>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-3 col-sm-3 col-xs-12 info-panel">
			<div class="widget widget-shadow">
				<div class="widget-content bg-white padding-20">
					<button type="button" class="btn btn-floating btn-sm btn-primary">
						<i class="icon wb-user"></i>
					</button>
					<span class="margin-left-15 font-weight-400">下架总次数</span>
					<div class="content-text text-center margin-bottom-0">
						<i class="text-danger icon wb-triangle-up font-size-20"> </i>
						<span class="font-size-30 font-weight-100">${num4}</span>
						<p class="blue-grey-400 font-weight-100 margin-0">下架总次数</p>
					</div>
				</div>
			</div>
		</div>

		</div>
</div>


<span id="containerPie" style="float: left; width: 36%; height:350px;"></span>
<span id="containerdayline" style="float: left; width: 33%; height:350px;"></span>
<span id="containerCol" style="float: left; width: 31%;height:350px;"></span>

<script type="text/javascript">
	$(function(){
		$(document.body).css("width","99.3%");
    });
</script>

</html>