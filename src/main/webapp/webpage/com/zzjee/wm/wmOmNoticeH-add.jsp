<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>出货通知</title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="online/template/ledefault/css/vendor.css">
  <link rel="stylesheet" href="online/template/ledefault/css/bootstrap-theme.css">
  <link rel="stylesheet" href="online/template/ledefault/css/bootstrap.css">
  <link rel="stylesheet" href="online/template/ledefault/css/app.css">
  
  <link rel="stylesheet" href="plug-in/Validform/css/metrole/style.css" type="text/css"/>
  <link rel="stylesheet" href="plug-in/Validform/css/metrole/tablefrom.css" type="text/css"/>
  <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
  <script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
  <script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
  <script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
  <script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
  <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.config.js"></script>
  <script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.all.min.js"></script>
	<link rel="stylesheet" href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" type="text/css"></link>
	<script type="text/javascript" src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js"></script>

</head>


 <script type="text/javascript">
 $(document).ready(function(){
	 init();
	 $("#jform_tab .con-wrapper").hide(); //Hide all tab content  
	 $("#jform_tab li:first").addClass("active").show(); //Activate first tab  
	 $("#jform_tab .con-wrapper:first").show(); //Show first tab content
	 
	 
	 //On Click Event  
    $("#jform_tab li").click(function() {  
        $("#jform_tab li").removeClass("active"); //Remove any "active" class  
        $(this).addClass("active"); //Add "active" class to selected tab  
        $("#jform_tab .con-wrapper").hide(); //Hide all tab content  
        var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content  
        $(activeTab).fadeIn(); //Fade in the active content
        //$(""+activeTab).show(); 
        if( $(activeTab).html()!="") {
        	return false;
        }else{
        	$(activeTab).html('正在加载内容，请稍后...');
        	var url = $(this).attr("tab-ajax-url");
        	$.post(url, {}, function(data) {
        		 //$(this).attr("tab-ajax-cached", true);
        		$(activeTab).html(data);
        		
            });
        }  
        return false;  
    });  
  });
  //初始化下标
	function resetTrNum(tableId) {
		$tbody = $("#"+tableId+"");
		$tbody.find('>tr').each(function(i){
			$(':input, select', this).each(function(){
				var $this = $(this), name = $this.attr('name'), val = $this.val();
				if(name!=null){
					if (name.indexOf("#index#") >= 0){
						$this.attr("name",name.replace('#index#',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
			});
			$(this).find('div[name=\'xh\']').html(i+1);
		});
	}
	
	function init(){
    	var tabHead =$("#jform_tab li:first");
    	var tabBox = $("#jform_tab .con-wrapper:first"); 
    	var url = tabHead.attr("tab-ajax-url");
    	tabBox.html('正在加载内容，请稍后...');
    	$.post(url, {}, function(data) {
            tabBox.html(data);
    		//tabHead.attr("tab-ajax-cached", true);
        });
    }

 $(document).ready(function(){
     //绑定下拉框change事件，当下来框改变时调用 SelectChange()方法
     $("select[name='cusCode']").change(function() { SelectChange(); });
     SelectChange();
 })


 function SelectChange() {
     var selectcusValue = $("select[name='cusCode']").val();
//加载工地
     var url = "mvGoodsController.do?setvalue&cusCode="+selectcusValue;
     $.ajax({
         url:url,
         type:"GET",
         dataType:"JSON",
         async: false,
         success:function(data){
             if(data.success){


             }
         }
     });

 }


 </script>
 <body>
  <form id="formobj" action="wmOmNoticeHController.do?doAdd" name="formobj" method="post"><input type="hidden" id="btn_sub" class="btn_sub"/>
				
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			
			
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">出货通知</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" style="display: block;">
			      <div class="row form-wrapper">
							<div class="row show-grid">
			          <div class="col-xs-1 text-center">
			          	<b>客户：</b>
			          </div>
			          <div class="col-xs-2">
								<t:dictSelect  field="cusCode" type="list" extendJson="{class:'form-control',datatype:'*',style:'width:230px'}"
									defaultVal="${wmOmNoticeHPage.cusCode}" 	 	readonly="${wmOmNoticeHPage.readonly}" 	  dictTable="mv_cus" dictField="cus_code" dictText="cus_name"  hasLabel="false"  title="客户编码"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">客户编码</label>
			          </div>
			          
			        
			          <div class="col-xs-1 text-center">
			          	<b>要求交货时间：</b>
			          </div>
			          <div class="col-xs-2">
								<input id="delvData" name="delvData" type="text" class="form-control"
									ignore="checked"
									    style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;" class="form-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   type="date" pattern="yyyy-MM-dd hh:mm:ss" />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">要求交货时间</label>
			          </div>
			          
			                   <div class="col-xs-1 text-center">
			          	<b>订单类型：</b>
			          </div>
			          <div class="col-xs-2">
								<t:dictSelect field="orderTypeCode" type="list" extendJson="{class:'form-control',style:'width:150px'}"  
								dictTable="ba_order_type" dictField="order_type_code" dictText="order_type_name"  defaultVal="${wmOmNoticeHPage.orderTypeCode}" hasLabel="false"  title="订单类型"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">订单类型</label>
			          </div>
								<div class="col-xs-1 text-center">
									<b>客户订单号：</b>
								</div>
								<div class="col-xs-2">
									<input id="imCusCode" name="imCusCode" type="text" class="form-control"
										   ignore="ignore"
										    />
									<span class="Validform_checktip" style="float:left;height:0px;"></span>
									<label class="Validform_label" style="display: none">客户订单号</label>
								</div>

							</div>
					  <div class="row show-grid">

					  <div class="col-xs-1 text-center">
						  <b>三方客户：</b>
					  </div>
					  <div class="col-xs-5">

						  <t:autocomplete   entityName="MvCusOtherEntity" searchField="cusName" name="ocusCode"></t:autocomplete>


						  <%--<t:dictSelect id="ocusCodeid" field="ocusCode" type="list" extendJson="{class:'form-control',style:'width:230px'}"--%>
										 	 	  <%--dictTable="mv_cus_other" dictField="cus_code" dictText="cus_name"  hasLabel="false"  title="三方客户编码"></t:dictSelect>--%>
						  <%--<span class="Validform_checktip" style="float:left;height:0px;"></span>--%>
						  <%--<label class="Validform_label" style="display: none">三方客户</label>--%>
					  </div>


					  <%--<div class="col-xs-1 text-center">--%>
						  <%--<b>三方客户名称：</b>--%>
					  <%--</div>--%>
					  <%--<div class="col-xs-2">--%>
						  <%--<input id="ocusName" name="ocusName" type="text" class="form-control"--%>
								 <%--ignore="checked"--%>
								  <%--class="form-control" />--%>
						  <%--<span class="Validform_checktip" style="float:left;height:0px;"></span>--%>
						  <%--<label class="Validform_label" style="display: none">三方客户名称</label>--%>
					  <%--</div>--%>










			          <div class="col-xs-1 text-center">
			          	<b>收货人：</b>
			          </div>
			          <div class="col-xs-2">
								<input id="delvMember" name="delvMember" type="text" class="form-control"
									/>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">收货人</label>
			          </div>
			          
			        
			          <div class="col-xs-1 text-center">
			          	<b>收货人电话：</b>
			          </div>
			          <div class="col-xs-2">
								<input id="delvMobile" name="delvMobile" type="text" class="form-control"
									 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">收货人电话</label>
			          </div>
					  </div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-1 text-center">
			          	<b>收货人地址：</b>
			          </div>
			          <div class="col-xs-2">
								<input id="delvAddr" name="delvAddr" type="text" class="form-control"
									 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">收货人地址</label>
			          </div>
			          
			        
			          <div class="col-xs-1 text-center">
			          	<b>承运人：</b>
			          </div>
			          <div class="col-xs-2">
								<input id="reMember" name="reMember" type="text" class="form-control"
									 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">承运人</label>
			          </div>

			        
			          <div class="col-xs-1 text-center">
			          	<b>承运人电话：</b>
			          </div>
			          <div class="col-xs-2">
								<input id="reMobile" name="reMobile" type="text" class="form-control"
									/>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">承运人电话</label>
			          </div>
			          
			        
			          <div class="col-xs-1 text-center">
			          	<b>承运人车号：</b>
			          </div>
			          <div class="col-xs-2">
								<input id="reCarno" name="reCarno" type="text" class="form-control"
									/>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">承运人车号</label>
			          </div>
							</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-1 text-center">
			          	<b>发货月台：</b>
			          </div>
			          <div class="col-xs-2">
								<t:dictSelect field="omPlatNo" type="list" extendJson="{class:'form-control',style:'width:150px'}"  
								dictTable="ba_platform" dictField="platform_code" dictText="platform_name" hasLabel="false"  title="月台"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">月台</label>
			          </div>		          
			        
			          <div class="col-xs-1 text-center">
			          	<b>备注 </b>
			          </div>
			          <div class="col-xs-2">
								<input id="omBeizhu" name="omBeizhu" type="text" class="form-control"
									ignore="ignore"
								   />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注</label>
			          </div>
			          
			          			         		<div class="col-xs-1 text-center">
			          	<b>附件 </b>
			          </div>
			          <div class="col-xs-2">
			<t:webUploader auto="true"   name="fuJian" duplicate="true" fileNumLimit="3"></t:webUploader>
				
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">附件</label>
			          </div>
			          
							</div>
			          

			     </div>
			   </div>
			   
			   <div class="con-wrapper" style="display: block;"></div>
	</div>
		
			
			
<script type="text/javascript">
   $(function(){
    //查看模式情况下,删除和上传附件功能禁止使用
	if(location.href.indexOf("load=detail")!=-1){
		$(".jeecgDetail").hide();
	}
	
	if(location.href.indexOf("mode=read")!=-1){
		//查看模式控件禁用
		$("#formobj").find(":input").attr("disabled","disabled");
	}
	if(location.href.indexOf("mode=onbutton")!=-1){
		//其他模式显示提交按钮
		$("#sub_tr").show();
	}
   });

  var neibuClickFlag = false;
  function neibuClick() {
	  neibuClickFlag = true; 
	  $('#btn_sub').trigger('click');
  }
</script>

<div id="jform_tab" class="tab-wrapper">
	<!-- tab -->
    <ul class="nav nav-tabs">
		    	<li role="presentation" tab-ajax-url="wmOmNoticeHController.do?wmOmNoticeIList&omNoticeId=${wmOmNoticeHPage.omNoticeId}"><a href="#con-wrapper0">出货商品明细</a></li>
    </ul>
    
	     <div class="con-wrapper" id="con-wrapper0" style="display: none;"></div>
</div>


			
		<div align="center"  id = "sub_tr" style="display: none;" > <input type="button" value="提交" onclick="neibuClick();" class="ui_state_highlight"></div>
		<script src="plug-in/layer/layer.js"></script>
		<script type="text/javascript">
		$(function() {
			$("#formobj").Validform({
				tiptype: function(msg, o, cssctl) {
		            if (o.type == 3) {
		                layer.open({
		                    title: '提示信息',
		                    content: msg,
		                    icon: 5,
		                    shift: 6,
		                    btn: false,
		                    shade:false,time:5000,
		                    cancel: function(index) {
		                        o.obj.focus();
		                        layer.close(index);
		                    },
		                    yes: function(index) {
		                        o.obj.focus();
		                        layer.close(index);
		                    },
		                })
		            }
		        },
				btnSubmit: "#btn_sub",
				btnReset: "#btn_reset",
				ajaxPost: true,
				beforeSubmit: function(curform) {
					var tag = true;
					//提交前处理
					return tag;
				},
				usePlugin: {
					passwordstrength: {
						minLen: 6,
						maxLen: 18,
						trigger: function(obj, error) {
							if (error) {
								obj.parent().next().find(".Validform_checktip").show();
								obj.find(".passwordStrength").hide();
							} else {
								$(".passwordStrength").show();
								obj.parent().next().find(".Validform_checktip").hide();
							}
						}
					}
				},
				callback: function(data) {
					if (data.success == true) {
						 var win = frameElement.api.opener;
						 win.reloadTable();
						 win.tip(data.msg);
						 frameElement.api.close();
					} else {
						if (data.responseText == '' || data.responseText == undefined) {
							$.messager.alert('错误', data.msg);
							$.Hidemsg();
						} else {
							try {
								var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
								$.messager.alert('错误', emsg);
								$.Hidemsg();
							} catch(ex) {
								$.messager.alert('错误', data.responseText + '');
							}
						}
						return false;
					}
				}
			});
		});
		</script>
		
		</form>
		<!-- 添加 产品明细 模版 -->
		<table style="display:none">
			<tbody id="add_wmOmNoticeI_table_template">
				<tr>
					 <th scope="row"><div name="xh"></div></th>
					 <td><input style="width:20px;" type="checkbox" name="ck"/></td>
						  <td align="left">
<%-- 							  		<t:dictSelect field="wmOmNoticeIList[#index#].goodsId" type="list" extendJson="{class:'form-control',datatype:'*',style:'width:350px'}"   --%>
<%-- 											dictCondition="${wmOmNoticeHPage.wherecon}"				dictTable="mv_goods" dictField="goods_code" dictText="goods_name" defaultVal="" hasLabel="false"  title="商品编码"></t:dictSelect>      --%>
							  		
							  		
							  			  	<input id="wmOmNoticeIList[#index#].goodsId"  name="wmOmNoticeIList[#index#].goodsId" maxlength="32" 
									ignore="ignore"
							  		type="text" class="form-control searchbox-inputtext" onclick="popClickone('wmOmNoticeIList[#index#].goodsId','goodsName','mvGoodsController.do?list')"   style="width:420px;text-align: left" >



	<label class="Validform_label" style="display: none;">出货商品</label>
						  </td>
						  <td align="left">
							  	<input name="wmOmNoticeIList[#index#].goodsQua" maxlength="32" 
									ignore="ignore"
							  		type="text" class="form-control"  style="width:120px;" >
							  <label class="Validform_label" style="display: none;">出货数量</label>
						  </td>
						  
						   <td>
							<t:dictSelect field="wmOmNoticeIList[#index#].planSta" type="radio" extendJson="{class:'form-control',style:'width:150px'}"  
										typeGroupCode="sf_yn"  hasLabel="false"  title="下架任务是否已生成"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">下架任务是否已生成</label>
					</td>
  						  <td align="left">
 							  	<input name="wmOmNoticeIList[#index#].binOm" maxlength="32"
 									ignore="ignore"
 							  		type="text" class="form-control"  style="width:120px;" >
 						  <label class="Validform_label" style="display: none;">出货储位</label>
 						  </td>

 						  <td align="left">
 							  	<input name="wmOmNoticeIList[#index#].binId" maxlength="32"
 									ignore="ignore"
 							  		type="text" class="form-control"  style="width:120px;" >
 							  <label class="Validform_label" style="display: none;">出货托盘</label>
 						  </td>
					<td align="left">
						<input name="wmOmNoticeIList[#index#].goodsProData" maxlength="32"
							   type="text" class="form-control" onClick="WdatePicker()"  style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;width:160px;"
							   ignore="ignore"
						>
						<label class="Validform_label" style="display: none;">生产日期</label>
					</td>
					</tr>
				 </tbody>
		</table>
	<script src = "webpage/com/zzjee/wm/wmOmNoticeH.js"></script>	
 </body>
 </html>