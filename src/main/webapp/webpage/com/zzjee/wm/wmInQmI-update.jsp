<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>收货登记</title>
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
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmInQmIController.do?doUpdate" tiptype="1" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="id" value='${wmInQmIPage.id}' >
			<input type="hidden" name="imNoticeItem" value='${wmInQmIPage.imNoticeItem}' >
					<input id="createDate" name="createDate" type="hidden" value="${wmInQmIPage.createDate }"/>
		<input id="createBy" name="createBy" type="hidden" value="${wmInQmIPage.createBy }"/>
		<input id="createName" name="createName" type="hidden" value="${wmInQmIPage.createName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${wmInQmIPage.updateBy }"/>
		<input id="updateDate" name="updateDate" type="hidden" value="${wmInQmIPage.updateDate }"/>
		<input id="updateName" name="updateName" type="hidden" value="${wmInQmIPage.updateName }"/>
				<input id="baseUnit" name="baseUnit" type="hidden" value="${wmInQmIPage.baseUnit }"/>
		<input id="baseGoodscount" name="baseGoodscount" type="hidden" value="${wmInQmIPage.baseGoodscount }"/>
			<input id="baseQmcount" name="baseQmcount" type="hidden" value="${wmInQmIPage.baseQmcount }"/>
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">收货登记</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>到货通知单：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="imNoticeId" name="imNoticeId" type="text" class="form-control"
									ignore="ignore" readonly="readonly"
								   value='${wmInQmIPage.imNoticeId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">到货通知单</label>
			          </div>
			          			          <div class="col-xs-3 text-center">
			          	<b>货主：</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="cusCode" type="list" extendJson="{class:'form-control'}"  readonly="readonly"
								dictTable="mv_cus" dictField="cus_code" dictText="cus_name" defaultVal="${wmInQmIPage.cusCode}" hasLabel="false"  title="货主"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">货主</label>
			          </div>
						</div>
			          						<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>托盘：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="tinId" name="tinId" type="text" class="form-control"
									ignore="checked" 
								   datatype="*" value='${wmInQmIPage.tinId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">托盘</label>
			          </div>
			          			          <div class="col-xs-3 text-center">
			          	<b>储位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="binId" name="binId" type="text" class="form-control"
									ignore="ignore"
								   value='${wmInQmIPage.binId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">储位</label>
			          </div>
			          		        
	
			          <div class="col-xs-3 text-center">
			          	<b>是否已上架：</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect   readonly="readonly" field="binSta" type="list" extendJson="{class:'form-control'}"
								typeGroupCode="sf_yn" defaultVal="${wmInQmIPage.binSta}" hasLabel="false"  title="是否已上架"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">是否已上架</label>
			          </div>
				
						</div>

			          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>商品：</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="goodsId" type="list" extendJson="{class:'form-control'}"  readonly="readonly"
								dictTable="mv_goods" dictField="goods_code" dictText="goods_name" defaultVal="${wmInQmIPage.goodsId}" hasLabel="false"  title="商品编码"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商品编码</label>
			          </div>
			          	          <div class="col-xs-3 text-center">
			          	<b>单位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsUnit" name="goodsUnit" type="text" class="form-control" readonly="readonly"
									ignore="ignore"
								   value='${wmInQmIPage.goodsUnit}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">单位</label>
			          </div>
			          		          <div class="col-xs-3 text-center">
			          	<b>到货数量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="imQuat" name="imQuat" type="text" class="form-control"
									ignore="checked" readonly="readonly"
								   datatype="*" value='${wmInQmIPage.imQuat}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">到货数量</label>
			          </div>
			                <div class="col-xs-3 text-center">
			          	<b>数量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="qmOkQuat" name="qmOkQuat" type="text" class="form-control"
									ignore="checked" 
								   datatype="*" value='${wmInQmIPage.qmOkQuat}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">数量</label>
			          </div>
			                    <div class="col-xs-3 text-center">
			          	<b>体积：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="tinTj" name="tinTj" type="text" class="form-control"
									ignore="ignore" 
								   value='${wmInQmIPage.tinTj}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">体积</label>
			          </div>
			                 <div class="col-xs-3 text-center">
			          	<b>重量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="tinZhl" name="tinZhl" type="text" class="form-control"
									ignore="ignore" 
								   value='${wmInQmIPage.tinZhl}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">重量</label>
			          </div>
						</div>
			          
			          						<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>生产日期：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="proData" name="proData" type="text" 
									ignore="checked"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;"  class="form-control" onClick="WdatePicker()" datatype="*" value="${wmInQmIPage.proData}" />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">生产日期</label>
			          </div>
			          		          <div class="col-xs-3 text-center">
			          	<b>批次：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsBatch" name="goodsBatch" type="text" class="form-control"
									ignore="ignore"
								   value='${wmInQmIPage.goodsBatch}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">批次</label>
			          </div>
						</div>
			        
							<div class="row show-grid">
								          <div class="col-xs-3 text-center">
			          	<b>收货温度：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="recDeg" name="recDeg" type="text" class="form-control"
									ignore="ignore"
								   value='${wmInQmIPage.recDeg}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">收货温度</label>
			          </div>
			          <div class="col-xs-3 text-center">
			          	<b>备注：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="itemText" name="itemText" type="text" class="form-control"
									ignore="ignore"
								   value='${wmInQmIPage.itemText}' />
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
<script src = "webpage/com/zzjee/wm/wmInQmI.js"></script>		
</html>