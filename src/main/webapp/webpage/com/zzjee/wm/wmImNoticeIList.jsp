<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!-- <h4>分类标题</h4> -->
	    <div class="row">
	      <div class="col-md-12 layout-header">
 	        <button id="addBtn_WmImNoticeI" type="button" class="btn btn-default">添加</button>
<!-- 	        <button id="delBtn_WmImNoticeI" type="button" class="btn btn-default">删除</button> -->
	        <button id="sum_WmImNoticeI" type="button" class="btn btn-default">合计</button>
	        <script type="text/javascript"> 
	        
			$('#addBtn_WmImNoticeI').bind('click', function(){
		 		 var tr =  $("#add_wmImNoticeI_table_template tr").clone();
			 	 $("#add_wmImNoticeI_table").append(tr);
			 	 resetTrNum('add_wmImNoticeI_table');
			 	 return false;
		    });
			$('#delBtn_WmImNoticeI').bind('click', function(){   
		       $("#add_wmImNoticeI_table").find("input:checked").parent().parent().remove();   
		        resetTrNum('add_wmImNoticeI_table');
		        return false;
		    });
			$('#sum_WmImNoticeI').bind('click', function(){   
				trList = $("#add_wmImNoticeI_table").children("tr");

				 var heji = 0;
				 var dh = 0;
				 for (var i=0;i<trList.length;i++) {
				        var tdArr = trList.eq(i).find("td");
				 
				        var history_income_remark = tdArr.eq(2).find('input').val();//    合计
				        dh = history_income_remark;
			             heji = heji*1 + dh*1;
				    }
				 alert("合计"+heji);

			    });
		    $(document).ready(function(){
		    	if(location.href.indexOf("load=detail")!=-1){
					$(":input").attr("disabled","true");
					$(".datagrid-toolbar").hide();
				}
		    	resetTrNum('add_wmImNoticeI_table');
		    });
		
		</script>

	      </div>
	    </div>

<div style="margin: 0 15px; background-color: white;">    
	    <!-- Table -->
      <table id="wmImNoticeI_table" class="table table-bordered table-hover" style="margin-bottom: 0;">
		<thead>
	      <tr>
	        <th style="white-space:nowrap;width:50px;">序号</th>
	        <th style="white-space:nowrap;width:50px;">操作</th>
					  <th>
							商品编码 
					  </th>
					  <th>
							数量
					  </th>
<!-- 					  	<th> -->
<!-- 							单位 -->
<!-- 					  </th> -->
<!-- 					  <th> -->
<!-- 							生产日期 -->
<!-- 					  </th> -->
					  <th>
							收货完成
					  </th>
<!-- 					  <th> -->
<!-- 							单位 -->
<!-- 					  </th> -->
	      </tr>
	    </thead>
        
	<tbody id="add_wmImNoticeI_table">	
	<c:if test="${fn:length(wmImNoticeIList)  <= 0 }">
			<tr>
				<th scope="row"><div name="xh"></div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>

				  <td>
					   	<input id="wmImNoticeIList[0].goodsCode"  name="wmImNoticeIList[0].goodsCode"    maxlength="32"
							  		type="text"    style="width:420px;text-align: left" onclick="popClickone('wmImNoticeIList[0].goodsCode','goodsName','mvGoodsController.do?list')">

				  </td>

				  <td>
					  	<input name="wmImNoticeIList[0].goodsCount" maxlength="32"
					  		type="text" class="form-control"  style="width:120px;text-align: right"  >
					  <label class="Validform_label" style="display: none;">数量</label>

					</td>

				  <td>
							<t:dictSelect field="wmImNoticeIList[0].binPre" type="radio" extendJson="{class:'form-control',style:'width:150px'}"
										typeGroupCode="sf_yn" defaultVal="N" hasLabel="false"  title="收货完成"></t:dictSelect>
					  <label class="Validform_label" style="display: none;">收货完成</label>
					</td>

   			</tr>





	</c:if>
	<c:if test="${fn:length(wmImNoticeIList)  > 0 }">
		<c:forEach items="${wmImNoticeIList}" var="poVal" varStatus="stuts">
			<tr>
				<th scope="row"><div name="xh">${stuts.index+1 }</div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>
					<input name="wmImNoticeIList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="wmImNoticeIList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="wmImNoticeIList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="wmImNoticeIList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="wmImNoticeIList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="wmImNoticeIList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="wmImNoticeIList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="wmImNoticeIList[${stuts.index }].sysOrgCode" type="hidden" value="${poVal.sysOrgCode }"/>
					<input name="wmImNoticeIList[${stuts.index }].sysCompanyCode" type="hidden" value="${poVal.sysCompanyCode }"/>
					<input name="wmImNoticeIList[${stuts.index }].imNoticeId" type="hidden" value="${poVal.imNoticeId }"/>
					<input name="wmImNoticeIList[${stuts.index }].imNoticeItem" type="hidden" value="${poVal.imNoticeItem }"/>
					<input name="wmImNoticeIList[${stuts.index }].goodsBatch" type="hidden" value="${poVal.goodsBatch }"/>
					<input name="wmImNoticeIList[${stuts.index }].goodsFvol" type="hidden" value="${poVal.goodsFvol }"/>
					<input name="wmImNoticeIList[${stuts.index }].goodsWeight" type="hidden" value="${poVal.goodsWeight }"/>
					<input name="wmImNoticeIList[${stuts.index }].binPlan" type="hidden" value="${poVal.binPlan }"/>
					<input name="wmImNoticeIList[${stuts.index }].goodsQmCount" type="hidden" value="${poVal.goodsQmCount }"/>
					<input name="wmImNoticeIList[${stuts.index }].goodsWqmCount" type="hidden" value="${poVal.goodsWqmCount }"/>
					<input name="wmImNoticeIList[${stuts.index }].baseUnit" type="hidden" value="${poVal.baseUnit }"/>
					<input name="wmImNoticeIList[${stuts.index }].baseGoodscount" type="hidden" value="${poVal.baseGoodscount }"/>

				<input name="wmImNoticeIList[${stuts.index }].goodsCode" type="hidden" value="${poVal.goodsCode }"/>

			<%-- 					<input name="wmImNoticeIList[${stuts.index }].goodsUnit" type="hidden" value="${poVal.goodsUnit }"/> --%>
				   <td align="left">


					   <input id="wmOmNoticeIList[${stuts.index }].goodsName"    value="${poVal.goodsName }" readonly="readonly"  name="wmOmNoticeIList[${stuts.index }].goodsName" maxlength="32"
							  ignore="ignore"
							  type="text"    style="width:420px;text-align: left" >

							<%--<t:dictSelect field="wmImNoticeIList[${stuts.index }].goodsCode" type="list" extendJson="{class:'form-control',style:'width:350px'}"  --%>
								<%--dictCondition="${wmImNoticeHPage.wherecon}"				dictTable="mv_goods" dictField="goods_code" dictText="goods_name" defaultVal="${poVal.goodsCode }" hasLabel="false"  title="商品编码"></t:dictSelect>      --%>
					  <label class="Validform_label" style="display: none;">商品编码</label>
<%-- 				   	<input id="wmImNoticeIList[${stuts.index }].goodsCode"  name="wmImNoticeIList[${stuts.index }].goodsCode"    maxlength="32"  --%>
									   
<%-- 						value="${poVal.goodsCode }"	  		type="text"    style="width:420px;text-align: left" > --%>
				  
<%-- 				  				<t:choose  hiddenName="wmImNoticeIList[${stuts.index }].goodsCode" hiddenid="goodsName" url="mvGoodsController.do?list" name="mvGoodsList" width="900"  height="610" icon="icon-search" title="选择" textname="goodsName"  isInit="true"></t:choose> --%>
				  
				  
				   </td>
				   <td align="left">
					  	<input name="wmImNoticeIList[${stuts.index }].goodsCount" maxlength="32"  datatype="*"  
					  		type="text" class="form-control"  style="width:120px;text-align: right"  value="${poVal.goodsCount }">
					  <label class="Validform_label" style="display: none;">数量</label>
				   </td>
				   
<!-- 				   				   <td align="left"> -->
<%-- 							<t:dictSelect field="wmImNoticeIList[${stuts.index }].goodsUnit" type="list" extendJson="{class:'form-control',style:'width:250px'}"   --%>
<%-- 											dictTable="ba_unit" dictField="unit_code" dictText="unit_zh_name" defaultVal="${poVal.goodsUnit }" hasLabel="false"  title="单位"></t:dictSelect>      --%>
<!-- 					  <label class="Validform_label" style="display: none;">单位</label> -->
<!-- 				   </td> -->
				   
<!-- 				   <td align="left"> -->
<%-- 							<input name="wmImNoticeIList[${stuts.index }].goodsPrdData" maxlength="32"  --%>
<%-- 					  		type="text" class="form-control" onClick="WdatePicker()"  style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;width:160px;"    value="<fmt:formatDate value='${poVal.goodsPrdData}' type="date" pattern="yyyy-MM-dd"/>"> --%>
<!-- 					  <label class="Validform_label" style="display: none;">生产日期</label> -->
<!-- 				   </td> -->
				   <td align="left">
							<t:dictSelect field="wmImNoticeIList[${stuts.index }].binPre" type="radio" extendJson="{class:'form-control',style:'width:150px'}"  
										typeGroupCode="sf_yn" defaultVal="${poVal.binPre }" hasLabel="false"  title="收货完成"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">收货完成</label>
				   </td>
<!-- 				   <td align="left"> -->
<%-- 					  	<input name="wmImNoticeIList[${stuts.index }].goodsUnit" maxlength="32"  --%>
<%-- 					  		type="text" class="form-control"  style="width:120px;"  value="${poVal.goodsUnit }"> --%>
<!-- 					  <label class="Validform_label" style="display: none;">单位</label> -->
<!-- 				   </td> -->
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
</div>