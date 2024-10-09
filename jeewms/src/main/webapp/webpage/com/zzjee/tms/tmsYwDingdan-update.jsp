<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>运输订单</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
		<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
		<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="tmsYwDingdanController.do?doUpdate" callback="jeecgFormFileCallBack@Override">
			<input id="id" name="id" type="hidden" value="${tmsYwDingdanPage.id }"/>
			<input id="createName" name="createName" type="hidden" value="${tmsYwDingdanPage.createName }"/>
			<input id="createBy" name="createBy" type="hidden" value="${tmsYwDingdanPage.createBy }"/>
			<input id="createDate" name="createDate" type="hidden" value="${tmsYwDingdanPage.createDate }"/>
			<input id="updateName" name="updateName" type="hidden" value="${tmsYwDingdanPage.updateName }"/>
			<input id="updateBy" name="updateBy" type="hidden" value="${tmsYwDingdanPage.updateBy }"/>
			<input id="updateDate" name="updateDate" type="hidden" value="${tmsYwDingdanPage.updateDate }"/>
			<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${tmsYwDingdanPage.sysOrgCode }"/>
			<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${tmsYwDingdanPage.sysCompanyCode }"/>
	  <input id="xdrmz"  name="xdrmz"  type="hidden"type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.xdrmz}'/>
	  <input id="username" name="username" type="hidden" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.username}'/>

	  <%--<div class="form">--%>
		  <%--<label class="Validform_label">单号:</label>--%>
		  <input id="fadh" name="fadh" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.fadh}'/>
		  <%--<span class="Validform_checktip"></span>--%>
	  <%--</div>--%>

	  <table border="1pt"  >
		  <tr><td colspan="4"><p style="font-size: large">发货人信息 </p></td></tr>
		  <tr>
			  <td>
					  <label class="Validform_label">发货人:</label>
					  <input id="fahuoren" name="fahuoren" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.fahuoren}'/>
					  <span class="Validform_checktip"></span>
			  </td>
			  <td>

					  <label class="Validform_label">发货人电话:</label>
					  <input id="fhrdh" name="fhrdh" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.fhrdh}'/>
					  <span class="Validform_checktip"></span>

			  </td>
			  <td>

					  <label class="Validform_label">发货人地址:</label>
					  <input id="fhrdz" name="fhrdz" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.fhrdz}'/>
					  <span class="Validform_checktip"></span>

			  </td>

		  </tr>
		  <tr><td colspan="4"><p style="font-size: large">收货人信息 </p></td></tr>
		  <tr>
			  <td>

					  <label class="Validform_label">收货人:</label>
					  <input id="shouhuoren" name="shouhuoren" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.shouhuoren}'/>
					  <span class="Validform_checktip"></span>

			  </td>
			  <td>

					  <label class="Validform_label">收货人电话:</label>
					  <input id="shrsj" name="shrsj" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.shrsj}'/>
					  <span class="Validform_checktip"></span>

			  </td>
			  <td>

					  <label class="Validform_label">收货人地址:</label>
					  <input id="shrdh" name="shrdh" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.shrdh}'/>
					  <span class="Validform_checktip"></span>

			  </td>

		  </tr>






		  <tr><td colspan="4"><p style="font-size: large">货物信息 </p></td></tr>


		  <tr>
			  <td>

					  <label class="Validform_label">货物:</label>
					  <input id="huowu" name="huowu" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.huowu}'/>
					  <span class="Validform_checktip"></span>

			  </td>
			  <td>

					  <label class="Validform_label">件数:</label>
					  <input id="hwshjs" name="hwshjs" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.hwshjs}'/>
					  <span class="Validform_checktip"></span>


			  </td>
			  <td>

					  <label class="Validform_label">重量:</label>
					  <input id="zhongl" name="zhongl" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.zhongl}'/>
					  <span class="Validform_checktip"></span>

			  </td>
			  <td>

					  <label class="Validform_label">送货方式:</label>
					  <t:dictSelect field="hwshfs" type="radio"  typeGroupCode="tms_thfs"   defaultVal="${tmsYwDingdanPage.hwshfs}" hasLabel="false"  title="送货方式"></t:dictSelect>


			  </td>
		  </tr>


		  <tr>
			  <td>

					  <label class="Validform_label">长米:</label>
					  <input id="chang" name="chang" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.chang}'/>
					  <span class="Validform_checktip"></span>

			  </td>
			  <td>

					  <label class="Validform_label">宽米:</label>
					  <input id="kuan" name="kuan" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.kuan}'/>
					  <span class="Validform_checktip"></span>

			  </td>
			  <td>

					  <label class="Validform_label">高米:</label>
					  <input id="gao" name="gao" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.gao}'/>
					  <span class="Validform_checktip"></span>


			  </td>
			  <td>

					  <label class="Validform_label">立方米:</label>
					  <input id="tiji" name="tiji" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.tiji}'/>
					  <span class="Validform_checktip"></span>


			  </td>

		  </tr>
		  <tr><td colspan="4"><p style="font-size: large">其他信息 </p></td></tr>
		  <tr>
				  <%--<td>--%>
				  <%--<div class="form">--%>
				  <%--<label class="Validform_label">下单附件:</label>--%>
				  <%--<t:webUploader name="xiadanfj"  bizType="photosucai" auto="true" extensions="doc,txt,jpg" buttonStyle="btn-green btn-L" ></t:webUploader>--%>

				  <%--<div class="form" id="filediv_xiadanfj"></div>--%>

				  <%--</div>--%>
				  <%--</td>--%>
					  <td>

							  <label class="Validform_label">下单备注:</label>
							  <input id="ywddbz" name="ywddbz" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.ywddbz}'/>
							  <span class="Validform_checktip"></span>

					  </td>
			  <td>

					  <label class="Validform_label">代收款金额:</label>
					  <input id="daishouk" name="daishouk" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.daishouk}'/>
					  <span class="Validform_checktip"></span>

			  </td>
					  <td>

						  <label >客户单号:</label>
						  <input id="ywkhdh" name="ywkhdh" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.ywkhdh}'/>
						  <span class="Validform_checktip"></span>

				  </td>


			  <td>

					  <label class="Validform_label">是否等通知:</label>
					  <t:dictSelect field="dengtongzhi" type="radio"   typeGroupCode="sf_tz"  defaultVal="${tmsYwDingdanPage.dengtongzhi}" hasLabel="false"  title="是否等通知"></t:dictSelect>
					  <span class="Validform_checktip"></span>

			  </td>

		  </tr>
	  </table>


	    </fieldset>
  </t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/tms/tmsYwDingdan.js"></script>		
  	<script type="text/javascript">
	  	//加载 已存在的 文件
	  	$(function(){
	  		var table = $("#fileTable");
	  		var cgFormId=$("input[name='id']").val();
	  		$.ajax({
	  		   type: "post",
	  		   url: "tmsYwDingdanController.do?getFiles&id=" +  cgFormId,
	  		   success: function(data){
	  			 var arrayFileObj = jQuery.parseJSON(data).obj;
	  			 
	  			$.each(arrayFileObj,function(n,file){
	  				var fieldName = file.field.toLowerCase();
	  				var table = $("#"+fieldName+"_fileTable");
	  				var tr = $("<tr style=\"height:34px;\"></tr>");
	  				var td_title = $("<td>" + file.title + "</td>")
	  		  		var td_download = $("<td><a href=\"commonController.do?viewFile&fileid=" + file.fileKey + "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity\" title=\"下载\">下载</a></td>")
	  		  		var td_view = $("<td><a href=\"javascript:void(0);\" onclick=\"openwindow('预览','commonController.do?openViewFile&fileid=" + file.fileKey + "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)\">预览</a></td>");
	  		  		var td_del = $("<td><a href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=" + file.fileKey + "',this)\">删除</a></td>");
	  		  		
	  		  		tr.appendTo(table);
	  		  		td_title.appendTo(tr);
	  		  		td_download.appendTo(tr);
	  		  		td_view.appendTo(tr);
	  		  		td_del.appendTo(tr);
	  			 });
	  		   }
	  		});
	  	});
	  	
		  	/**
		 	 * 删除图片数据资源
		 	 */
		  	function del(url,obj){
		  		var content = "请问是否要删除该资源";
		  		var navigatorName = "Microsoft Internet Explorer"; 
		  		if( navigator.appName == navigatorName ){ 
		  			$.dialog.confirm(content, function(){
		  				submit(url,obj);
		  			}, function(){
		  			});
		  		}else{
		  			layer.open({
						title:"提示",
						content:content,
						icon:7,
						yes:function(index){
							submit(url,obj);
						},
						btn:['确定','取消'],
						btn2:function(index){
							layer.close(index);
						}
					});
		  		}
		  	}
		  	
		  	function submit(url,obj){
		  		$.ajax({
		  			async : false,
		  			cache : false,
		  			type : 'POST',
		  			url : url,// 请求的action路径
		  			error : function() {// 请求失败处理函数
		  			},
		  			success : function(data) {
		  				var d = $.parseJSON(data);
		  				if (d.success) {
		  					var msg = d.msg;
		  					tip(msg);
		  					obj.parentNode.parentNode.parentNode.deleteRow(obj.parentNode.parentNode);
		  				} else {
		  					tip(d.msg);
		  				}
		  			}
		  		});
		  	}
	  	
  		function jeecgFormFileCallBack(data){
  			if (data.success == true) {
				uploadFile(data);
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
			if (!neibuClickFlag) {
				var win = frameElement.api.opener;
				win.reloadTable();
			}
  		}
  		function upload() {
					$('#xiadanfj').uploadify('upload', '*');
					$('#huidanfj').uploadify('upload', '*');
		}
		
		var neibuClickFlag = false;
		function neibuClick() {
			neibuClickFlag = true; 
			$('#btn_sub').trigger('click');
		}
		function cancel() {
					$('#xiadanfj').uploadify('cancel', '*');
					$('#huidanfj').uploadify('cancel', '*');
		}
		function uploadFile(data){
			if(!$("input[name='id']").val()){
				if(data.obj!=null && data.obj!='undefined'){
					$("input[name='id']").val(data.obj.id);
				}
			}
			if($(".uploadify-queue-item").length>0){
				upload();
			}else{
				if (neibuClickFlag){
					alert(data.msg);
					neibuClickFlag = false;
				}else {
					var win = frameElement.api.opener;
					win.reloadTable();
					win.tip(data.msg);
					frameElement.api.close();
				}
			}
		}
  	</script>
