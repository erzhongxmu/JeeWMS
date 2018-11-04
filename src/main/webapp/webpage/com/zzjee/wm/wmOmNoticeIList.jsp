<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!-- <h4>分类标题</h4> -->
	    <div class="row">
	      <div class="col-md-12 layout-header">
 	        <button id="addBtn_WmOmNoticeI" type="button" class="btn btn-default">添加</button>
<!-- 	        <button id="delBtn_WmOmNoticeI" type="button" class="btn btn-default">删除</button> -->
	        <button id="sum_WmOmNoticeI" type="button" class="btn btn-default">合计和检查</button>
	        <script type="text/javascript"> 
			$('#addBtn_WmOmNoticeI').bind('click', function(){   
		 		 var tr =  $("#add_wmOmNoticeI_table_template tr").clone();
			 	 $("#add_wmOmNoticeI_table").append(tr);
			 	 resetTrNum('add_wmOmNoticeI_table');
			 	 return false;
		    });  
			$('#delBtn_WmOmNoticeI').bind('click', function(){   
		       $("#add_wmOmNoticeI_table").find("input:checked").parent().parent().remove();   
		        resetTrNum('add_wmOmNoticeI_table');
		        return false;
		    });
			$('#sum_WmOmNoticeI').bind('click', function(){   
				trList = $("#add_wmOmNoticeI_table").children("tr");

				 var heji = 0;
				 var dh = 0;
				 var msghe = "";
				 for (var i=0;i<trList.length;i++) {
				        var tdArr = trList.eq(i).find("td");
				        var goods_code
				        var strs= new Array(); //定义一数组 
				        try{
				        	strs= tdArr.eq(1).find('input').val().split('-');//    合计
						    goods_code = strs[0];
				        }catch(err){
				        	goods_code = tdArr.eq(1).find('input').val();
				        }
				      if(goods_code!=""){
				    	  
				      
				        var dh1 = tdArr.eq(2).find('input').val();//    合计
						var url = "wmOmNoticeHController.do?docheck&goodscode="+goods_code+"&goodsqua="+dh1;
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
   
								}else{		
									msghe = msghe +"/" + d.msg;
								}	
							}
						});
				        dh = dh1;
			             heji = heji*1 + dh*1;
				      }
				    }
				 
				 alert(msghe+"    合计:"+heji);

			    });
		    $(document).ready(function(){
		    	if(location.href.indexOf("load=detail")!=-1){
					$(":input").attr("disabled","true");
					$(".datagrid-toolbar").hide();
				}
		    	resetTrNum('add_wmOmNoticeI_table');
		    });
		</script>
	      </div>
	    </div>
<div style="margin: 0 15px; background-color: white;">    
	    <!-- Table -->
      <table id="wmOmNoticeI_table" class="table table-bordered table-hover" style="margin-bottom: 0;">
		<thead>
	      <tr>
	        <th style="white-space:nowrap;width:50px;">序号</th>
	        <th style="white-space:nowrap;width:50px;">操作</th>
					  <th>
							出货商品
					  </th>
					  <th>
							出货数量
					  </th>
<!-- 					  <th> -->
<!-- 							已经出货数量 -->
<!-- 					  </th> -->
<!-- 					  <th> -->
<!-- 							生产日期 -->
<!-- 					  </th> -->
					  <th>
							下架任务是否已生成
					  </th>
			  <th>
				  出货储位
			  </th>
					  <th>
						  出货托盘
					  </th>
			  <th>
				  生产日期
			  </th>
	      </tr>
	    </thead>
        
	<tbody id="add_wmOmNoticeI_table">	
	<c:if test="${fn:length(wmOmNoticeIList)  <= 0 }">
			<tr>
				<th scope="row"><div name="xh"></div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td>
							  			  	<input id="wmOmNoticeIList[0].goodsId"  name="wmOmNoticeIList[0].goodsId" maxlength="32" 
									ignore="ignore"  onclick="popClickone('wmOmNoticeIList[0].goodsId','goodsName','mvGoodsController.do?list')"
							  		type="text"    style="width:420px;text-align: left" >



					  <label class="Validform_label" style="display: none;">出货商品</label>
					</td>
				  <td>
					  	<input name="wmOmNoticeIList[0].goodsQua" maxlength="32" 
					  		type="text" class="form-control"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">出货数量</label>
					</td>
									  <td>
							<t:dictSelect field="wmOmNoticeIList[0].planSta" type="radio" extendJson="{class:'form-control',style:'width:150px'}"  
										typeGroupCode="sf_yn" defaultVal="${wmOmNoticeHPage.planSta}" hasLabel="false"  title="下架任务是否已生成"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">下架任务是否已生成</label>
					</td>

				<td>
					<input name="wmOmNoticeIList[0].binOm" maxlength="32"
						   type="text" class="form-control"  style="width:120px;" >
					<label class="Validform_label" style="display: none;">出货储位</label>
				</td>

				<td>
					<input name="wmOmNoticeIList[0].binId" maxlength="32"
						   type="text" class="form-control"  style="width:120px;" >
					<label class="Validform_label" style="display: none;">出货托盘</label>
				</td>
				<td align="left">
					<input name="wmOmNoticeIList[0].goodsProData" maxlength="32"
						   type="text" class="form-control" onClick="WdatePicker()"  style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;width:160px;"     >
					<label class="Validform_label" style="display: none;">生产日期</label>
				</td>
   			</tr>
   			

   			
	</c:if>
	<c:if test="${fn:length(wmOmNoticeIList)  > 0 }">
		<c:forEach items="${wmOmNoticeIList}" var="poVal" varStatus="stuts">
			<tr>
				<th scope="row"><div name="xh">${stuts.index+1 }</div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>
					<input name="wmOmNoticeIList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="wmOmNoticeIList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="wmOmNoticeIList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="wmOmNoticeIList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="wmOmNoticeIList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="wmOmNoticeIList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="wmOmNoticeIList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="wmOmNoticeIList[${stuts.index }].sysOrgCode" type="hidden" value="${poVal.sysOrgCode }"/>
					<input name="wmOmNoticeIList[${stuts.index }].sysCompanyCode" type="hidden" value="${poVal.sysCompanyCode }"/>
					<input name="wmOmNoticeIList[${stuts.index }].omNoticeId" type="hidden" value="${poVal.omNoticeId }"/>
					<input name="wmOmNoticeIList[${stuts.index }].goodsBatch" type="hidden" value="${poVal.goodsBatch }"/>
					<input name="wmOmNoticeIList[${stuts.index }].goodsUnit" type="hidden" value="${poVal.goodsUnit }"/>
					<input name="wmOmNoticeIList[${stuts.index }].goodsQuaok" type="hidden" value="${poVal.goodsQuaok }"/>
					
					<input name="wmOmNoticeIList[${stuts.index }].cusCode" type="hidden" value="${poVal.cusCode }"/>
					<input name="wmOmNoticeIList[${stuts.index }].waveId" type="hidden" value="${poVal.waveId }"/>
					<input name="wmOmNoticeIList[${stuts.index }].omSta" type="hidden" value="${poVal.omSta }"/>
					<input name="wmOmNoticeIList[${stuts.index }].baseUnit" type="hidden" value="${poVal.baseUnit }"/>
					<input name="wmOmNoticeIList[${stuts.index }].baseGoodscount" type="hidden" value="${poVal.baseGoodscount }"/>
				   <input name="wmOmNoticeIList[${stuts.index }].goodsId" type="hidden" value="${poVal.goodsId }"/>


					<%--
wmOmNoticeIList[${stuts.index }].goodsId<input name="wmOmNoticeIList[${stuts.index }].planSta" type="hidden" value="${poVal.planSta }"/> --%>

					
				   <td align="left">


					   <input id="wmOmNoticeIList[${stuts.index }].goodsName" value="${poVal.goodsName }" readonly="readonly"  name="wmOmNoticeIList[${stuts.index }].goodsName" maxlength="32"
							  ignore="ignore"
							  type="text"    style="width:420px;text-align: left" >
<%-- 					  	<input name="wmOmNoticeIList[${stuts.index }].goodsId" maxlength="32"  --%>
<%-- 					  		type="text" class="form-control"  style="width:120px;"  value="${poVal.goodsId }"> --%>
					  		<%--<t:dictSelect field="wmOmNoticeIList[${stuts.index }].goodsId" type="list" extendJson="{class:'form-control',style:'width:350px'}"--%>
														<%--dictTable="mv_goods" dictField="goods_code" dictText="goods_name" defaultVal="${poVal.goodsId }" hasLabel="false"  title="商品编码"></t:dictSelect>--%>
					  		
					  <label class="Validform_label" style="display: none;">出货商品</label>
				   </td>
				   <td align="left">
					  	<input name="wmOmNoticeIList[${stuts.index }].goodsQua" maxlength="32"
					  		type="text" class="form-control"  style="width:120px;"  value="${poVal.goodsQua }">
					  <label class="Validform_label" style="display: none;">出货数量</label>
				   </td>
				   <td>
							<t:dictSelect field="wmOmNoticeIList[${stuts.index }].planSta" type="radio" extendJson="{class:'form-control',style:'width:150px'}"  
										typeGroupCode="sf_yn" defaultVal="${poVal.planSta}" hasLabel="false"  title="下架任务是否已生成"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">下架任务是否已生成</label>
					</td>
				<td align="left">
					<input name="wmOmNoticeIList[${stuts.index }].binOm" maxlength="32"
						   type="text" class="form-control"  style="width:120px;"  value="${poVal.binOm }">
					<label class="Validform_label" style="display: none;">出货储位</label>
				</td>
				<td align="left">
					<input name="wmOmNoticeIList[${stuts.index }].binId" maxlength="32"
						   type="text" class="form-control"  style="width:120px;"  value="${poVal.binId }">
					<label class="Validform_label" style="display: none;">出货托盘</label>
				</td>
<!-- 				   <td align="left"> -->
<%-- 					  	<input name="wmOmNoticeIList[${stuts.index }].goodsQuaok" maxlength="32"  --%>
<%-- 					  		type="text" class="form-control"  style="width:120px;"   value="${poVal.goodsQuaok }"> --%>
<!-- 					  <label class="Validform_label" style="display: none;">已经出货数量</label> -->
<!-- 				   </td> -->
				   <td align="left">
							<input name="wmOmNoticeIList[${stuts.index }].goodsProData" maxlength="32"
					  		type="text" class="form-control" onClick="WdatePicker()"  style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;width:160px;"    value="<fmt:formatDate value='${poVal.goodsProData}' type="date" pattern="yyyy-MM-dd"/>">
					  <label class="Validform_label" style="display: none;">生产日期</label>
				   </td>
<!-- 				   <td align="left"> -->
<%-- 					  	<input name="wmOmNoticeIList[${stuts.index }].binOm" maxlength="32"  --%>
<%-- 					  		type="text" class="form-control"  style="width:120px;"  value="${poVal.binOm }"> --%>
<!-- 					  <label class="Validform_label" style="display: none;">出货托盘</label> -->
<!-- 				   </td> -->
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
</div>