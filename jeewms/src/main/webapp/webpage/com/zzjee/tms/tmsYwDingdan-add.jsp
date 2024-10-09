<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>运输订单</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
		<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
		<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
	 <!-- 自动补全 -->
	 <link rel="stylesheet" href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" type="text/css"></link>
	 <script type="text/javascript" src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js"></script>

 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password"   layout="div" >
			<input id="id" name="id" type="hidden" value="${tmsYwDingdanPage.id }">
			<input id="createName" name="createName" type="hidden" value="${tmsYwDingdanPage.createName }">
			<input id="createBy" name="createBy" type="hidden" value="${tmsYwDingdanPage.createBy }">
			<input id="createDate" name="createDate" type="hidden" value="${tmsYwDingdanPage.createDate }">
			<input id="updateName" name="updateName" type="hidden" value="${tmsYwDingdanPage.updateName }">
			<input id="updateBy" name="updateBy" type="hidden" value="${tmsYwDingdanPage.updateBy }">
			<input id="updateDate" name="updateDate" type="hidden" value="${tmsYwDingdanPage.updateDate }">
			<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${tmsYwDingdanPage.sysOrgCode }">
			<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${tmsYwDingdanPage.sysCompanyCode }">
	        <input id="zhuangtai" name="zhuangtai" type="hidden" value="已下单" />
		  <table border="1pt" style="width: 750px ">
           <tr><td colspan="4"><p style="font-size: large">发货人信息 </p></td></tr>
			  <tr>
				  <td colspan="4">
					  <div >
					  <label >发货人(输入姓名，电话地址搜索；如果是新的收货人，请按照：姓名-电话-地址   格式输入） </label>


					  </div>
				  </td>


			  </tr>

			  <tr>
				  <td colspan="4">
					  <div >

						  <t:autocomplete datatype="*"  entityName="VTmsDzEntity" searchField="dizhi" name="fahuoren"></t:autocomplete>

					  </div>
				  </td>


			  </tr>

			  <tr><td colspan="4"><p style="font-size: large">收货人信息 </p></td></tr>
			  <tr>
				  <td colspan="4">
					  <div >
						  <label>
							  收货人(输入姓名，电话地址搜索；如果是新的收货人，请按照：姓名-电话-地址   格式输入)
						  </label>
					  </div>
				  </td>
			  </tr>


			  <td colspan="4">
				  <div >
					   <t:autocomplete datatype="*" entityName="VTmsDzEntity" searchField="dizhi" name="shouhuoren"></t:autocomplete>

				  </div>
			  </td>

			  </tr>

			  <tr><td colspan="4"><p style="font-size: large">货物信息 </p></td></tr>

			  <tr>
				  <td>
					  <div >
						  <label >货物</label>
						  <input id="huowu" name="huowu" type="text" style="width: 120px" class="inputxt"  ignore="ignore" />

					  </div>
				  </td>
				  <td>
					  <div >
						  <label >件数</label>
						  <input id="hwshjs" name="hwshjs" type="text" style="width: 120px" class="inputxt"  ignore="ignore" />

					  </div>
				  </td>
				  <td>
					  <div >
						  <label >重量</label>
						  <input id="zhongl" name="zhongl" type="text" style="width: 120px" class="inputxt"  ignore="ignore" />
					  </div>
				  </td>
				  <td>
				  <div >
					  <label >送货方式</label>
					  <t:dictSelect field="hwshfs" type="radio"  typeGroupCode="tms_thfs"   defaultVal="送达" hasLabel="false"  title="送货方式"></t:dictSelect>

				  </div>
				  </td>

			  </tr>
			  <tr>
				  <td>
					  <div >
						  <label >长米</label>
						  <input id="chang" name="chang" type="text" style="width: 120px" class="inputxt"  ignore="ignore" />
					  </div>



				  </td>
				  <td>
					  <div >
						  <label >宽米</label>
						  <input id="kuan" name="kuan" type="text" style="width: 120px" class="inputxt"  ignore="ignore" />

					  </div>
				  </td>
				  <td>
					  <div >
						  <label >高米</label>
						  <input id="gao" name="gao" type="text" style="width: 120px" class="inputxt"  ignore="ignore" />

					  </div>

				  </td>
				  <td>
					  <div >
						  <label >立方米</label>
						  <input id="tiji" name="tiji" type="text" style="width: 120px" class="inputxt"  ignore="ignore" />

					  </div>

				  </td>

			  </tr>
			  <tr><td colspan="4"><p style="font-size: large">其他信息 </p></td></tr>
			  <tr>
				  <td>
					  <div >
						  <label >下单备注</label>
						  <input id="ywddbz" name="ywddbz" type="text" style="width: 120px" class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.ywddbz}'/>
						  <span class="Validform_checktip"></span>
					  </div>
				  </td>
				  <td>
					  <div >
						  <label >代收款金额</label>
						  <input id="daishouk" name="daishouk" type="text" style="width: 120px" class="inputxt"  ignore="ignore" />

					  </div>
				  </td>

				  <td>
					  <div  >
						  <label >客户单号:</label>
						  <input id="ywkhdh" name="ywkhdh" type="text"  class="inputxt"  ignore="ignore"  value='${tmsYwDingdanPage.ywkhdh}'/>
						  <span class="Validform_checktip"></span>
					  </div>
				  </td>

				  <td>
					  <div >
						  <label >是否等通知</label>
						  <t:dictSelect field="dengtongzhi" type="radio"  typeGroupCode="sf_tz"   defaultVal="N" hasLabel="false"  title="是否等通知"></t:dictSelect>

					  </div>
				  </td>

			  </tr>
		  </table>

	  <div style="margin:3px auto"><button onclick="sub('formobj');">下单</button></div>
  </t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/tms/tmsYwDingdan.js"></script>
 <script src="plug-in/jquery-plugs/form/jquery.form.js"></script>

 <script type="text/javascript">

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
			// $('#btn_sub').trigger('click');
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

    //表单提交
    function sub(formid){
        console.log('1');
        $.ajax({
            type:"POST",
            url:"tmsYwDingdanController.do?doAdd",
            data:$("#"+formid).serialize(),
            async:true,
            success:function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    $("#fahuoren").val("");
                    $("#shouhuoren").val("");
                    $("#huowu").val("");
                    $("#hwshjs").val("");
                    $("#zhongl").val("");
                    $("#chang").val("");
                    $("#kuan").val("");
                    $("#gao").val("");
                    $("#tiji").val("");
                    $("#ywddbz").val("");
                    $("#daishouk").val("");
                    $("#ywkhdh").val("");

                } else {
                    tip(d.msg);
                }
            }


        });


    }


  	</script>
  	