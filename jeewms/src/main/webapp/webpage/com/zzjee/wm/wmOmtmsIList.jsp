<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!-- <h4>分类标题</h4> -->
	    <div class="row">
	      <div class="col-md-12 layout-header">
 	        <button id="addBtn_WmOmtmsI" type="button" class="btn btn-default">添加</button>
 	        <button id="delBtn_WmOmtmsI" type="button" class="btn btn-default">删除</button>
	        <%--<button id="sum_WmOmNoticeI" type="button" class="btn btn-default">合计和检查</button>--%>
	        <script type="text/javascript"> 
			$('#addBtn_WmOmtmsI').bind('click', function(){
		 		 var tr =  $("#add_wmOmtmsI_table_template tr").clone();
			 	 $("#add_wmOmtmsI_table").append(tr);
			 	 resetTrNum('add_wmOmtmsI_table');
			 	 return false;
		    });  
			$('#delBtn_WmOmtmsI').bind('click', function(){
		       $("#add_wmOmtmsI_table").find("input:checked").parent().parent().remove();
		        resetTrNum('add_wmOmtmsI_table');
		        return false;
		    });
			$('#sum_WmOmtmsI').bind('click', function(){
				trList = $("#add_wmOmtmsI_table").children("tr");

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
		    	resetTrNum('add_wmOmtmsI_table');
		    });
		</script>
	      </div>
	    </div>
<div style="margin: 0 15px; background-color: white;">    
	    <!-- Table -->
      <table id="wmOmtmsI_table" class="table table-bordered table-hover" style="margin-bottom: 0;">
		<thead>
	      <tr>
	        <th style="white-space:nowrap;width:50px;">序号</th>
	        <th style="white-space:nowrap;width:50px;">操作</th>
					  <th>
							快递公司
					  </th>
					  <th>
							运单号
					  </th>
<!-- 					  <th> -->
<!-- 							已经出货数量 -->
<!-- 					  </th> -->
<!-- 					  <th> -->
<!-- 							生产日期 -->
<!-- 					  </th> -->
					  <%--<th>--%>
							<%--下架任务是否已生成--%>
					  <%--</th>--%>
			  <th>
				  费用
			  </th>
					  <th>
						  备注
					  </th>
			  <%--<th>--%>
				  <%--生产日期--%>
			  <%--</th>--%>
	      </tr>
	    </thead>
        
	<tbody id="add_wmOmtmsI_table">
	<c:if test="${fn:length(wmOmtmsIList)  <= 0 }">
			<tr>
				<th scope="row"><div name="xh"></div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td>
							  			  	<%--<input id="wmOmtmsIList[0].fahuoren"  name="wmOmtmsIList[0].fahuoren" maxlength="32"--%>
 							  		<%--type="text"    style="width:420px;text-align: left" >--%>

					  <t:dictSelect field="wmOmtmsIList[#index#].fahuoren" type="select" typeGroupCode="tms_kd" hasLabel="false"  defaultVal=""></t:dictSelect>

					  <label class="Validform_label" style="display: none;">快递公司</label>
					</td>
				  <td>
					  	<input name="wmOmtmsIList[0].fadh" maxlength="32"
					  		type="text" class="form-control"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">运单号</label>
					</td>


				<td>
					<input name="wmOmNoticeIList[0].jiage" maxlength="32"
						   type="text" class="form-control"  style="width:120px;" >
					<label class="Validform_label" style="display: none;">费用</label>
				</td>

				<td>
					<input name="wmOmNoticeIList[0].ywddbz" maxlength="32"
						   type="text" class="form-control"  style="width:120px;" >
					<label class="Validform_label" style="display: none;">备注</label>
				</td>

   			</tr>
   			

   			
	</c:if>
	<c:if test="${fn:length(wmOmtmsIList)  > 0 }">
		<c:forEach items="${wmOmtmsIList}" var="poVal" varStatus="stuts">
			<tr>
				<th scope="row"><div name="xh">${stuts.index+1 }</div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>
					<input name="wmOmtmsIList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>

				   <td align="left">

					   <%--<input id="wmOmtmsIList[${stuts.index }].fahuoren" value="${poVal.fahuoren }"   maxlength="32"--%>
							  <%--type="text" class="form-control"  style="width:120px;" >--%>
					   <t:dictSelect field="wmOmtmsIList[${stuts.index }].fahuoren" type="select" typeGroupCode="tms_kd" hasLabel="false"  defaultVal="${poVal.fahuoren }"></t:dictSelect>


					  <label class="Validform_label" style="display: none;">快递公司</label>
				   </td>
				   <td align="left">
					  	<input name="wmOmtmsIList[${stuts.index }].fadh" maxlength="32"
					  		type="text" class="form-control"  style="width:120px;"  value="${poVal.fadh }">
					  <label class="Validform_label" style="display: none;">运单号</label>
				   </td>

				<td align="left">
					<input name="wmOmtmsIList[${stuts.index }].jiage" maxlength="32"
						   type="text" class="form-control"  style="width:120px;"  value="${poVal.jiage }">
					<label class="Validform_label" style="display: none;">费用</label>
				</td>
				<td align="left">
					<input name="wmOmtmsIList[${stuts.index }].ywddbz" maxlength="32"
						   type="text" class="form-control"  style="width:120px;"  value="${poVal.ywddbz }">
					<label class="Validform_label" style="display: none;">备注</label>
				</td>

   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
</div>