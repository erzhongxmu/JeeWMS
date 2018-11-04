<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>下架商品明细</title>
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
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmToDownGoodsController.do?doUpdate" tiptype="1" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="id" value='${wmToDownGoodsPage.id}' >
			<input id="createDate" name="createDate" type="hidden" value="${wmToDownGoodsPage.createDate }"/>
		<input id="createBy" name="createBy" type="hidden" value="${wmToDownGoodsPage.createBy }"/>
		<input id="createName" name="createName" type="hidden" value="${wmToDownGoodsPage.createName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${wmToDownGoodsPage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${wmToDownGoodsPage.updateDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${wmToDownGoodsPage.updateName }"/>
			
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">下架商品明细</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>商品编码：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsId" name="goodsId" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.goodsId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商品编码</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>数量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsQua" name="goodsQua" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.goodsQua}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">数量</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>确认数量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsQuaok" name="goodsQuaok" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.goodsQuaok}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">完成数量</label>
			          </div>
			          	          <div class="col-xs-3 text-center">
			          	<b>单位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsUnit" name="goodsUnit" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.goodsUnit}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">单位</label>
			          </div>
						</div>
			          
			        		<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>基本单位数量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="baseGoodscount" name="baseGoodscount" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.baseGoodscount}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">基本单位数量</label>
			          </div>
			          	          <div class="col-xs-3 text-center">
			          	<b>基本单位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="baseUnit" name="baseUnit" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.baseUnit}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">基本单位</label>
			          </div>
						</div>
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>原始单据编码：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="orderId" name="orderId" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.orderId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">原始单据编码</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>原始单据类型：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="orderType" name="orderType" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.orderType}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">原始单据类型</label>
			          </div>
						</div>
			          
			        
				
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>生产日期：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsProData" name="goodsProData" type="text" 
									ignore="ignore"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;"  class="form-control" onClick="WdatePicker()" value='${wmToDownGoodsPage.goodsProData}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">生产日期</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>源托盘码：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="binIdFrom" name="binIdFrom" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.binIdFrom}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">源托盘码</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>库位编码：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="kuWeiBianMa" name="kuWeiBianMa" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.kuWeiBianMa}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">库位编码</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>作业类型：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="actTypeCode" name="actTypeCode" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.actTypeCode}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">作业类型</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>货主：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="cusCode" name="cusCode" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.cusCode}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">货主</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>目标托盘：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="binIdTo" name="binIdTo" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.binIdTo}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">目标托盘</label>
			          </div>
						</div>
			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>状态：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="downSta" name="downSta" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToDownGoodsPage.downSta}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">状态</label>
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
<script src = "webpage/com/zzjee/wm/wmToDownGoods.js"></script>		
</html>