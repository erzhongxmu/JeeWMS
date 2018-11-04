<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>储位定义</title>
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
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="mdBinController.do?doUpdate" tiptype="1" >
			<input type="hidden" id="btn_sub" class="btn_sub"/>
			<input type="hidden" name="id" value='${mdBinPage.id}' >
			<div class="tab-wrapper">
			    <!-- tab -->
			    <ul class="nav nav-tabs">
			      <li role="presentation" class="active"><a href="javascript:void(0);">储位定义</a></li>
			    </ul>
			    <!-- tab内容 -->
			    <div class="con-wrapper" id="con-wrapper1" style="display: block;">
			      <div class="row form-wrapper">
			      
			      		<div class="row show-grid">
			      		          <div class="col-xs-3 text-center">
			          	<b  style="color:red">仓&emsp;&emsp;库</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="binStore" type="list" extendJson="{class:'form-control'}"
								dictTable="ba_store" dictField="store_code" dictText="store_name" defaultVal="${mdBinPage.binStore}" hasLabel="false"  title="仓库"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">仓库</label>
			          </div>
			          <div class="col-xs-3 text-center">
			          	<b>所属客户</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="suoShuKeHu" type="list" extendJson="{class:'form-control'}"
								dictTable="mv_cus" dictField="cus_code" dictText="cus_name" defaultVal="${mdBinPage.suoShuKeHu}" hasLabel="false"  title="所属客户"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">所属客户</label>
			          </div>
			          
						</div>
			      
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">库位编码</b>
			          </div>
			          <div class="col-xs-3">
								<input id="kuWeiBianMa" name="kuWeiBianMa" type="text" class="form-control"
									ignore="checked" readonly="readonly"
								   datatype="*" value='${mdBinPage.kuWeiBianMa}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">库位编码</label>
			          </div>
			                  <div class="col-xs-3 text-center">
			          	<b  style="color:red">库位条码</b>
			          </div>
			          <div class="col-xs-3">
								<input id="kuWeiTiaoMa" name="kuWeiTiaoMa" type="text" class="form-control"
									ignore="checked" readonly="readonly"
								   datatype="*" value='${mdBinPage.kuWeiTiaoMa}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">库位条码</label>
			          </div>
						</div>
		
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">库位类型</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="kuWeiLeiXing" type="list" extendJson="{class:'form-control'}"
								dictTable="ba_bin_type" dictField="bin_type_code" dictText="bin_type_name" defaultVal="${mdBinPage.kuWeiLeiXing}" hasLabel="false"  title="库位类型"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">库位类型</label>
			          </div>
			          			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">库位属性</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="kuWeiShuXing" type="list" extendJson="{class:'form-control'}"
								dictTable="ba_deg_type" dictField="deg_type_code" dictText="deg_type_name" defaultVal="${mdBinPage.kuWeiShuXing}" hasLabel="false"  title="库位属性"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">库位属性</label>
			          </div>
			          	</div>
			          <div class="row show-grid">
			          	          			          	          <div class="col-xs-3 text-center">
			          	<b  style="color:red">产品属性</b>
			          </div>
			          <div class="col-xs-3" style="width:490px">
								<t:dictSelect   field="chpShuXing" type="checkbox"  extendJson="{style:'width:490px';class:'form-control';datatype:'*'}"
								dictTable="ba_goods_type" dictField="goods_type_code" dictText="goods_type_name" defaultVal="${mdBinPage.chpShuXing}" hasLabel="false"  title="产品属性"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">产品属性</label>
			          </div>
						</div>
			          
		          
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  style="color:red">上架次序</b>
			          </div>
			          <div class="col-xs-3">
								<input id="shangJiaCiXu" name="shangJiaCiXu" type="text" class="form-control"
									ignore="checked"
								   datatype="*" value='${mdBinPage.shangJiaCiXu}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">上架次序</label>
			          </div>
			          	          <div class="col-xs-3 text-center">
			          	<b  style="color:red">取货次序</b>
			          </div>
			          <div class="col-xs-3">
								<input id="quHuoCiXu" name="quHuoCiXu" type="text" class="form-control"
									ignore="checked"
								   datatype="*" value='${mdBinPage.quHuoCiXu}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">取货次序</label>
			          </div>
						</div>
			        
							<div class="row show-grid">
			
			             <div class="col-xs-3 text-center">
			          	<b>最大重量</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zuiDaZhongLiang" name="zuiDaZhongLiang" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  datatype="d"
								   value='${mdBinPage.zuiDaZhongLiang}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">最大重量</label>
			          </div>
			          
			           <div class="col-xs-3 text-center">
			          	<b>最大托盘</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zuiDaTuoPan" name="zuiDaTuoPan" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  datatype="d"
								   value='${mdBinPage.zuiDaTuoPan}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">最大托盘</label>
			          </div>
			          
						</div>
			        
			        
			        								<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b  >长</b>
			          </div>
			          <div class="col-xs-3">
								<input id="chang" name="chang" type="text" class="form-control" 
									ignore="ignore" style="text-align:right"  datatype="d" value='${mdBinPage.chang}'
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">长</label>
			          </div>
			          		          <div class="col-xs-3 text-center">
			          	<b  >宽</b>
			          </div>
			          <div class="col-xs-3">
								<input id="kuan" name="kuan" type="text" class="form-control" 
									ignore="ignore" style="text-align:right"  datatype="d" value='${mdBinPage.kuan}'
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">宽</label>
			          </div>
			          
			                   		          <div class="col-xs-3 text-center">
			          	<b  >高</b>
			          </div>
			          <div class="col-xs-3">
								<input id="gao" name="gao" type="text" class="form-control" 
									ignore="ignore" style="text-align:right"  datatype="d" value='${mdBinPage.gao}'
								 />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">高</label>
			          </div>
			                    <div class="col-xs-3 text-center">
			          	<b  >最大体积</b>
			          </div>
			          <div class="col-xs-3">
								<input id="zuiDaTiJi" name="zuiDaTiJi" type="text" class="form-control"
									ignore="ignore" style="text-align:right"  datatype="d"
								   value='${mdBinPage.zuiDaTiJi}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">最大体积</label>
			          </div>
						</div>
			        
							<div class="row show-grid">
			          <div class="col-xs-3 text-center">
			          	<b>停&emsp;&emsp;用</b>
			          </div>
			          <div class="col-xs-3">
								<t:dictSelect field="tingYong" type="radio" extendJson="{class:'form-control'}"
								typeGroupCode="sf_yn" defaultVal="${mdBinPage.tingYong}" hasLabel="false"  title="停用"></t:dictSelect>     
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">停用</label>
			          </div>
			          	          <div class="col-xs-3 text-center">
			          	<b>备&emsp;&emsp;注</b>
			          </div>
		            <div class="col-xs-3">
								<input id="mingXi" name="mingXi" type="text" class="form-control"
									ignore="ignore" style="text-align:right"   
								   value='${mdBinPage.mingXi}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注</label>
			          </div>
						</div>
							<div class="row show-grid">
							     <div class="col-xs-3 text-center">
			          	<b>备&emsp;&emsp;注1</b>
			          </div>
		            <div class="col-xs-3">
								<input id="mingXi1" name="mingXi1" type="text" class="form-control"
									ignore="ignore" style="text-align:right"   
								   value='${mdBinPage.mingXi1}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注1</label>
			          </div>
			               <div class="col-xs-3 text-center">
			          	<b>备&emsp;&emsp;注2</b>
			          </div>
		            <div class="col-xs-3">
								<input id="mingXi2" name="mingXi2" type="text" class="form-control"
									ignore="ignore" style="text-align:right"   
								   value='${mdBinPage.mingXi2}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注2</label>
			          </div>
			             <div class="col-xs-3 text-center">
			          	<b>动&emsp;&emsp;线</b>
			          </div>
		            <div class="col-xs-3">
								<input id="mingXi3" name="mingXi3" type="text" class="form-control"
									ignore="ignore" style="text-align:right"   
								   value='${mdBinPage.mingXi3}' />
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">动线</label>
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
<script src = "webpage/com/zzjee/md/mdBin.js"></script>		
</html>