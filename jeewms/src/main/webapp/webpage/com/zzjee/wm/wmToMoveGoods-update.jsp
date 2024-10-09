<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>库存转移</title>
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
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wmToMoveGoodsController.do?doUpdate" tiptype="1" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="id" value='${wmToMoveGoodsPage.id}' >


			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">库存转移</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>商品编码：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsId" name="goodsId"  readonly="readonly"  type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.goodsId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商品编码</label>
			          </div>
						</div>


							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>商品名称：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsName" name="goodsName" readonly="readonly" style="width:300px" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.goodsName}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商品名称</label>
			          </div>
						</div>


							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>现有数量：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsQua" name="goodsQua"  readonly="readonly"  type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.goodsQua}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">数量</label>
			          </div>

						  <div class="col-xs-3 text-center">
							  <b>移动数量：</b>
						  </div>
						  <div class="col-xs-3">
							  <input id="baseGoodscount" name="baseGoodscount" type="text" class="form-control"
									 ignore="ignore"
									 value='${wmToMoveGoodsPage.baseGoodscount}' />
							  <span class="Validform_checktip" style="float:left;height:0px;"></span>
							  <label class="Validform_label" style="display: none">移动数量</label>
						  </div>
					  </div>

							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>生产日期：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsProData" name="goodsProData"  readonly="readonly"  type="text"
									ignore="ignore"
								style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;"  class="form-control" onClick="WdatePicker()" value='${wmToMoveGoodsPage.goodsProData}'  />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">生产日期</label>
			          </div>
								<div class="col-xs-3 text-center">
									<b>到生产日期：</b>
								</div>
								<div class="col-xs-3">
									<input id="toGoodsProData" name="toGoodsProData" type="text"
										   ignore="ignore"
										   style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;"  class="form-control" onClick="WdatePicker()" value='${wmToMoveGoodsPage.toGoodsProData}'  />
									<span class="Validform_checktip" style="float:left;height:0px;"></span>
									<label class="Validform_label" style="display: none">到生产日期</label>
								</div>
						</div>


							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>单位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="goodsUnit" name="goodsUnit"  readonly="readonly"  type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.goodsUnit}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">单位</label>
			          </div>
						</div>


							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>货主编码：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="cusCode" name="cusCode"  readonly="readonly"  type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.cusCode}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">货主编码</label>
			          </div>

								<div class="col-xs-3 text-center">
									<b>转移客户：</b>
								</div>
								<div class="col-xs-3">
									<t:dictSelect     field="toCusCode" type="list"   extendJson="  {class:'form-control',datatype:'*',style:'width:230px'}"
													  defaultVal="${wmToMoveGoodsPage.toCusCode}" dictTable="mv_cus" dictField="cus_code" dictText="cus_name"   hasLabel="false"  title="客户编码"></t:dictSelect>

									<span class="Validform_checktip" style="float:left;height:0px;"></span>
									<label class="Validform_label" style="display: none">转移客户</label>
								</div>

						</div>


							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>货主名称：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="cusName" name="cusName"  readonly="readonly"  style="width:300px" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.cusName}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">货主名称</label>
			          </div>
						</div>


							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>源托盘：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="tinFrom" name="tinFrom"   readonly="readonly" readonly="readonly" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.tinFrom}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">源托盘</label>
			          </div>

			          <div class="col-xs-3 text-center">
			          	<b>到托盘：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="tinId" name="tinId"  type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.tinId}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">到托盘</label>
			          </div>
						</div>


							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>源储位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="binFrom" name="binFrom"   readonly="readonly" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.binFrom}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">源储位</label>
			          </div>

			          <div class="col-xs-3 text-center">
			          	<b>到储位：</b>
			          </div>
			          <div class="col-xs-3">
								<input id="binTo" name="binTo" type="text" class="form-control"
									ignore="ignore"
								   value='${wmToMoveGoodsPage.binTo}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">到储位</label>
			          </div>
						</div>


							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>状态：</b>
			          </div>
			              <div class="col-xs-8">
								<t:dictSelect field="moveSta" type="radio" extendJson="{class:'form-control'}"
								typeGroupCode="move_sta" defaultVal="${wmToMoveGoodsPage.moveSta}" hasLabel="false"  title="状态"></t:dictSelect>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">状态</label>
			          </div>
<!-- 			          <div class="col-xs-3"> -->
<!-- 								<input id="moveSta" name="moveSta" type="text" class="form-control" -->
<!-- 									ignore="ignore" -->
<%-- 								   value='${wmToMoveGoodsPage.moveSta}' /> --%>
<!-- 						<span class="Validform_checktip" style="float:left;height:0px;"></span> -->
<!-- 						<label class="Validform_label" style="display: none">状态</label> -->
<!-- 			          </div> -->
						</div>







							<%--<div class="row show-grid">--%>
			          <%--<div class="col-xs-3 text-center">--%>
			          	<%--<b>转移客户名称：</b>--%>
			          <%--</div>--%>
			          <%--<div class="col-xs-3">--%>
								<%--<input id="toCusName" name="toCusName" style="width:300px" type="text" class="form-control"--%>
									<%--ignore="ignore"--%>
								   <%--value='${wmToMoveGoodsPage.toCusName}' />--%>
						<%--<span class="Validform_checktip" style="float:left;height:0px;"></span>--%>
						<%--<label class="Validform_label" style="display: none">转移客户名称</label>--%>
			          <%--</div>--%>
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

	if(location.href.indexOf("mode=read")!=-1||("计划中"!="${wmToMoveGoodsPage.moveSta}")){
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
<script src = "webpage/com/zzjee/wm/wmToMoveGoods.js"></script>
</html>
