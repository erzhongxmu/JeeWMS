<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>商品信息</title>
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
  function counttiji(){
	var chang =  $("#chZhXiang").val();
	var kuan =  $("#kuZhXiang").val();
	var gao =  $("#gaoZhXiang").val();
	var tijicm = chang * kuan * gao;
	$("#tiJiCm").val(tijicm);
  }
  
  
  
  
  </script>
</head>

 <body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="mdGoodsController.do?doUpdate" tiptype="1" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="id" value='${mdGoodsPage.id}' >
			
			
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">商品信息</a></li>
			      <li role="presentation" class="active">体积单位为立方厘米，重量单位为KG，保质期按天为单位</li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">所属客户</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect readonly="readonly" field="suoShuKeHu" type="list" extendJson="{class:'form-control'}"
											  dictTable="mv_cus" dictField="cus_code" dictText="cus_name"  defaultVal="${mdGoodsPage.suoShuKeHu}" hasLabel="false"  title="所属客户"></t:dictSelect>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">所属客户</label>
			          </div>
			          	          <div class="col-xs-3 text-center">
			          	<b  style="color:red">存放温层</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="cfWenCeng" type="list" extendJson="{class:'form-control';datatype:'*'}"
								dictTable="ba_deg_type" dictField="deg_type_code" dictText="deg_type_name" defaultVal="${mdGoodsPage.cfWenCeng}" hasLabel="false"  title="存放温层"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">存放温层</label>
			          </div>
			          
			          			          	          <div class="col-xs-3 text-center">
			          	<b  style="color:red">产品属性</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="chpShuXing" type="list" extendJson="{class:'form-control';datatype:'*'}"
								dictTable="ba_goods_type" dictField="goods_type_code" dictText="goods_type_name" defaultVal="${mdGoodsPage.chpShuXing}" hasLabel="false"  title="产品属性"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">产品属性</label>
			          </div>
			          
			          
			                  <div class="col-xs-3 text-center">
			          	<b  style="color:red">基准温度</b>
			          </div>
			          <div class="col-xs-3">
			          <input id="jiZhunwendu" name="jiZhunwendu" type="text" class="form-control" 
									ignore="checked" value='${mdGoodsPage.jiZhunwendu}'
								/>
									<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">基准温度</label>
			          </div>
			          
						</div>
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">商品编码</b>
			          </div>
			          <div class="col-xs-3">
								<input id="shpBianMa" name="shpBianMa" type="text" class="form-control"
									ignore="checked" readonly="readonly"
								   datatype="*" value='${mdGoodsPage.shpBianMa}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商品编码</label>
			          </div>
			              <div class="col-xs-3 text-center">
			          	<b   >客户商品编码</b>
			          </div>
			          <div class="col-xs-3">
								<input id="shpBianMakh" name="shpBianMakh" type="text" class="form-control"
									ignore="checked"  
								    value='${mdGoodsPage.shpBianMakh}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">客户商品编码</label>
			          </div>
			                    <div class="col-xs-3 text-center">
			          	<b  style="color:red">商品条码</b>
			          </div>
			          <div class="col-xs-3">
								<input id="shpTiaoMa" name="shpTiaoMa" type="text" class="form-control"
									ignore="ignore" 
								   value='${mdGoodsPage.shpTiaoMa}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商品条码</label>
			          </div>
						</div>
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">商品名称</b>
			          </div>
			          <div class="col-xs-3">
								<input id="shpMingCheng" name="shpMingCheng" type="text" class="form-control"
									ignore="checked" style="width:490px"
								   datatype="*" value='${mdGoodsPage.shpMingCheng}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">商品名称</label>
			          </div>
						</div>
			          
			        <div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">单位</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="shlDanWei" type="list" extendJson="{class:'form-control'}"
								dictTable="ba_unit" dictField="unit_code" dictText="unit_zh_name" defaultVal="${mdGoodsPage.shlDanWei}" hasLabel="false"  title="单位"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">单位</label>
			          </div>

			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">体积</b>
			          </div>
			          <div class="col-xs-3">
								<input id="tiJiCm" name="tiJiCm" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  datatype="d"
								   value='${mdGoodsPage.tiJiCm}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">体积</label>
			          </div>
	
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">重量</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zhlKg" name="zhlKg" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  
								   value='${mdGoodsPage.zhlKg}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">重量</label>
			          </div>
						

			        
			        
			        				<div class="row show-grid">
			    
			          	          <div class="col-xs-3 text-center">
			          	<b  style="color:red">长</b>
			          </div>
			          <div class="col-xs-3">
								<input id="chZhXiang" name="chZhXiang" type="text" class="form-control" 
									ignore="ignore" style="text-align:right"  datatype="d"  value='${mdGoodsPage.chZhXiang}'  onchange="counttiji()"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">长</label>
			          </div>
			               <div class="col-xs-3 text-center">
			          	<b  style="color:red">宽</b>
			          </div>
			          <div class="col-xs-3">
								<input id="kuZhXiang" name="kuZhXiang" type="text" class="form-control" 
									ignore="ignore" style="text-align:right"  datatype="d"   value='${mdGoodsPage.kuZhXiang}'  onchange="counttiji()"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">宽</label>
			          </div>
			          			               <div class="col-xs-3 text-center">
			          	<b  style="color:red">高</b>
			          </div>
			          <div class="col-xs-3">
								<input id="gaoZhXiang" name="gaoZhXiang" type="text" class="form-control" 
									ignore="ignore" style="text-align:right"  datatype="d"  value='${mdGoodsPage.gaoZhXiang}'  onchange="counttiji()"
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">高</label>
			          </div>
						</div>
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">保质期</b>
			          </div>
			          <div class="col-xs-3">
								<input id="bzhiQi" name="bzhiQi" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  datatype="d"
								   value='${mdGoodsPage.bzhiQi}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">保质期</label>
			          </div>
			          			          			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">允收天数</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zhlKgm" name="zhlKgm" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  
								   value='${mdGoodsPage.zhlKgm}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">允收天数</label>
			          </div>
						</div>
			          			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">计费商品类</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="jfShpLei" type="list" extendJson="{class:'form-control'}"
								dictTable="ba_goods_class" dictField="goods_class_code" dictText="goods_class_name" defaultVal="${mdGoodsPage.jfShpLei}" hasLabel="false"  title="计费商品类"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">计费商品类</label>
			          </div>
	
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">码盘单层数量</b>
			          </div>
			          <div class="col-xs-3">
								<input id="mpDanCeng" name="mpDanCeng" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  datatype="d"
								   value='${mdGoodsPage.mpDanCeng}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">码盘单层数量</label>
			          </div>

			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">码盘层高</b>
			          </div>
			          <div class="col-xs-3">
								<input id="mpCengGao" name="mpCengGao" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  datatype="d"
								   value='${mdGoodsPage.mpCengGao}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">码盘层高</label>
			          </div>
					
						</div>
			          
			        

			          							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">拆零控制</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="chlKongZhi" type="radio" extendJson="{class:'form-control';datatype:'*'}"
								typeGroupCode="sf_yn" defaultVal="${mdGoodsPage.chlKongZhi}" hasLabel="false"  title="拆零控制"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">拆零控制</label>
			          </div>
			          		          <div class="col-xs-3 text-center">
			          	<b style="color:red">拆零单位</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="jshDanWei" type="list" extendJson="{class:'form-control';datatype:'*'}"
								dictTable="ba_unit" dictField="unit_code" dictText="unit_zh_name" defaultVal="${mdGoodsPage.jshDanWei}" hasLabel="false"  title="拆零单位"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">拆零单位</label>
			          </div>
			          		          <div class="col-xs-3 text-center">
			          	<b  style="color:red">拆零数量</b>
			          </div>
			          <div class="col-xs-3">
								<input id="chlShl" name="chlShl" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  datatype="*" 
								   value='${mdGoodsPage.chlShl}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">拆零数量</label>
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
<script src = "webpage/com/zzjee/md/mdGoods.js"></script>		
</html>