<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>客户</title>
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
  <script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.config.js"></script>
  <script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.all.min.js"></script>
   <script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>

 <body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="mdCusController.do?doUpdate" tiptype="1" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="id" value='${mdCusPage.id}' >
			
			
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">客户</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
			      						<div class="row show-grid">
							          <div class="col-xs-3 text-center">
			          	<b  style="color:red">企业属性</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="xingYeFenLei" type="list" extendJson="{class:'form-control'}"
								dictTable="ba_com_type" dictField="com_type_code" dictText="com_type_name" defaultVal="${mdCusPage.xingYeFenLei}" hasLabel="false"  title="企业属性"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">企业属性</label>
			          </div>
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">客户编码</b>
			          </div>
			          <div class="col-xs-3">
								<input id="keHuBianMa" name="keHuBianMa" type="text" class="form-control"
									ignore="checked" readonly="readonly"
								   datatype="*" value='${mdCusPage.keHuBianMa}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">客户编码</label>
			          </div>
			            <div class="col-xs-3 text-center">
			          	<b  style="color:red">客户属性</b>
			          </div>
			          <div class="col-xs-3">
<t:dictSelect field="keHuShuXing" type="list" extendJson="{class:'form-control'}"
					defaultVal="${mdCusPage.keHuShuXing}"			dictTable="ba_keHuShuXing" dictField="kehushuxing_code" dictText="kehushuxing_name" hasLabel="false"  title="客户属性"></t:dictSelect>     
	
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">客户属性</label>
			          </div>
			          
			          		          <div class="col-xs-3 text-center">
			          	<b  style="color:red">出库计费方式</b>
			          </div>
			          <div class="col-xs-3">
<t:dictSelect field="keHuZhuangTai" type="list" extendJson="{class:'form-control'}"
					 defaultVal="${mdCusPage.keHuZhuangTai}"			dictTable="ba_kehuzhuangtai" dictField="kehuzhuangtai_code" dictText="kehuzhuangtai_name" hasLabel="false"  title="有无意向书"></t:dictSelect>     
	
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">出库计费方式</label>
			          </div>
						</div>
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">中文全称</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zhongWenQch" name="zhongWenQch" type="text" class="form-control"
									ignore="checked" style="width:490px"
								   datatype="*" value='${mdCusPage.zhongWenQch}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">中文全称</label>
			          </div>
			          </div>
			          <div class="row show-grid">
			          		          <div class="col-xs-3 text-center">
			          	<b   >合同号</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zhuJiMa" name="zhuJiMa" type="text" class="form-control"

								     value='${mdCusPage.zhuJiMa}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">合同号</label>
			          </div>
			          	          <div class="col-xs-3 text-center">
			          	<b   >营业执照</b>
			          </div>
			          <div class="col-xs-3">
								<input id="yingYeZhiZhao" name="yingYeZhiZhao" type="text" class="form-control"

								     value='${mdCusPage.yingYeZhiZhao}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">营业执照</label>
			          </div>
						</div>
	        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">地&emsp;&emsp;址</b>
			          </div>
			          <div class="col-xs-3">
								<input id="diZhi" name="diZhi" type="text" class="form-control"
									ignore="ignore" style="width:490px"
								   value='${mdCusPage.diZhi}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">地址</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">负责人</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zhuLianXiRen" name="zhuLianXiRen" type="text" class="form-control"
									ignore="ignore"
								   value='${mdCusPage.zhuLianXiRen}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">负责人</label>
			          </div>
			                    <div class="col-xs-3 text-center">
			          	<b>电&emsp;&emsp;话</b>
			          </div>
			          <div class="col-xs-3">
								<input id="dianHua" name="dianHua" type="text" class="form-control"
									ignore="ignore"
								   value='${mdCusPage.dianHua}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">电话</label>
			          </div>
						</div>
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">手&emsp;&emsp;机</b>
			          </div>
			          <div class="col-xs-3">
								<input id="shouJi" name="shouJi" type="text" class="form-control"
									ignore="ignore"
								   value='${mdCusPage.shouJi}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">手机</label>
			          </div>
			          	          <div class="col-xs-3 text-center">
			          	<b>Email地址</b>
			          </div>
			          <div class="col-xs-3">
								<input id="emaildiZhi" name="emaildiZhi" type="text" class="form-control"
									ignore="ignore"
								   datatype="e" value='${mdCusPage.emaildiZhi}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">Email地址</label>
			          </div>
						</div>
						
													<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  >联系人1</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zhuLianXiRen1" name="zhuLianXiRen1" type="text" class="form-control"
									ignore="ignore"
								   value='${mdCusPage.zhuLianXiRen1}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">联系人1</label>
			          </div>
			                    <div class="col-xs-3 text-center">
			          	<b>电&emsp;&emsp;话1</b>
			          </div>
			          <div class="col-xs-3">
								<input id="dianHua1" name="dianHua1" type="text" class="form-control"
									ignore="ignore"
								   value='${mdCusPage.dianHua1}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">电话1</label>
			          </div>
						</div>
						
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>备&emsp;&emsp;注</b>
			          </div>
			          <div class="col-xs-3">
						  	 	<textarea id="beiZhu" class="form-control" rows="6" 
									ignore="ignore" style="width:490px"
						  	 	name="beiZhu">${mdCusPage.beiZhu}</textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注</label>
			          </div>
						</div>
		       
			          <div class="row" id = "sub_tr" style="display: none;">
				        <div class="col-xs-12 layout-header">
				          <div class="col-xs-6"></div>
				          <div class="col-xs-6"><button type="button" onclick="neibuClick();" class="btn btn-default">提交</button></div>
				        </div>
				      </div>
			     </div>
			   </div>
			   
			   <div class="con-wrapper" id="con-wrapper2" style="display: block;"></div>
			 </div>
  </t:formvalid>

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
 </body>
<script src = "webpage/com/zzjee/md/mdCus.js"></script>		
</html>