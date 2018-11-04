<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!-- <h4>分类标题</h4> -->
	    <div class="row">
	      <div class="col-md-12 layout-header">
	        <button id="addBtn_WmCusCostI" type="button" class="btn btn-default">添加</button>
	        <button id="delBtn_WmCusCostI" type="button" class="btn btn-default">删除</button>
	        <script type="text/javascript"> 
			$('#addBtn_WmCusCostI').bind('click', function(){   
		 		 var tr =  $("#add_wmCusCostI_table_template tr").clone();
			 	 $("#add_wmCusCostI_table").append(tr);
			 	 resetTrNum('add_wmCusCostI_table');
			 	 return false;
		    });  
			$('#delBtn_WmCusCostI').bind('click', function(){   
		       $("#add_wmCusCostI_table").find("input:checked").parent().parent().remove();   
		        resetTrNum('add_wmCusCostI_table');
		        return false;
		    });
		    $(document).ready(function(){
		    	if(location.href.indexOf("load=detail")!=-1){
					$(":input").attr("disabled","true");
					$(".datagrid-toolbar").hide();
				}
		    	resetTrNum('add_wmCusCostI_table');
		    });
		    function count(){
		    }
		</script>
	      </div>
	    </div>
<div style="margin: 0 15px; background-color: white;">    
	    <!-- Table -->
      <table id="wmCusCostI_table" class="table table-bordered table-hover" style="margin-bottom: 0;">
		<thead>
	      <tr>
	        <th style="white-space:nowrap;width:50px;">序号</th>
	        <th style="white-space:nowrap;width:50px;">操作</th>
					  <th>
							费用名称
					  </th>
					  <th>
							价格RMB
					  </th>
					  <th>
							税率
					  </th>
					  <th>
							折扣
					  </th>
					  <th>
							不含税价RMB
					  </th>
					  <th>
							含税价RMB
					  </th>
	      </tr>
	    </thead>
        
	<tbody id="add_wmCusCostI_table">	
	<c:if test="${fn:length(wmCusCostIList)  <= 0 }">
			<tr>
				<th scope="row"><div name="xh"></div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>
					<input name="wmCusCostIList[0].id" type="hidden"/>
					<input name="wmCusCostIList[0].createName" type="hidden"/>
					<input name="wmCusCostIList[0].createBy" type="hidden"/>
					<input name="wmCusCostIList[0].createDate" type="hidden"/>
					<input name="wmCusCostIList[0].updateName" type="hidden"/>
					<input name="wmCusCostIList[0].updateBy" type="hidden"/>
					<input name="wmCusCostIList[0].updateDate" type="hidden"/>
					<input name="wmCusCostIList[0].sysOrgCode" type="hidden"/>
					<input name="wmCusCostIList[0].sysCompanyCode" type="hidden"/>
					<input name="wmCusCostIList[0].cusCostId" type="hidden"/>
				  <td>
							<t:dictSelect field="wmCusCostIList[0].costCode" type="list" extendJson="{class:'form-control',style:'width:150px'}"  
										dictTable="ba_cost" dictField="cost_code" dictText="cost_name" defaultVal="${wmCusCostIPage.costCode}" hasLabel="false"  title="费用名称"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">费用名称</label>
					</td>
				  <td>
					  	<input name="wmCusCostIList[0].costJg" maxlength="32" 
					  		type="text" class="form-control"  style="width:120px;"  datatype="*">
					  <label class="Validform_label" style="display: none;">价格RMB</label>
					</td>
				  <td>
					  	<input name="wmCusCostIList[0].costSl" maxlength="32" onchange="count()"
					  		type="text" class="form-control"  style="width:120px;"  datatype="*">
					  <label class="Validform_label" style="display: none;">税率</label>
					</td>
				  <td>
					  	<input name="wmCusCostIList[0].costZk" maxlength="32"  onchange="count()"
					  		type="text" class="form-control"  style="width:120px;"  datatype="*">
					  <label class="Validform_label" style="display: none;">折扣</label>
					</td>
				  <td>
					  	<input name="wmCusCostIList[0].costBhs" maxlength="32" 
					  		type="text" class="form-control"  style="width:120px;"  datatype="*">
					  <label class="Validform_label" style="display: none;">不含税价RMB</label>
					</td>
				  <td>
					  	<input name="wmCusCostIList[0].costHs" maxlength="32" 
					  		type="text" class="form-control"  style="width:120px;"  datatype="*">
					  <label class="Validform_label" style="display: none;">含税价RMB</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(wmCusCostIList)  > 0 }">
		<c:forEach items="${wmCusCostIList}" var="poVal" varStatus="stuts">
			<tr>
				<th scope="row"><div name="xh">${stuts.index+1 }</div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>
					<input name="wmCusCostIList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="wmCusCostIList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="wmCusCostIList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="wmCusCostIList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="wmCusCostIList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="wmCusCostIList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="wmCusCostIList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="wmCusCostIList[${stuts.index }].sysOrgCode" type="hidden" value="${poVal.sysOrgCode }"/>
					<input name="wmCusCostIList[${stuts.index }].sysCompanyCode" type="hidden" value="${poVal.sysCompanyCode }"/>
					<input name="wmCusCostIList[${stuts.index }].cusCostId" type="hidden" value="${poVal.cusCostId }"/>
				   <td align="left">
							<t:dictSelect field="wmCusCostIList[${stuts.index }].costCode" type="list" extendJson="{class:'form-control',style:'width:150px'}"  
										dictTable="ba_cost" dictField="cost_code" dictText="cost_name" defaultVal="${poVal.costCode }" hasLabel="false"  title="费用名称"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">费用名称</label>
				   </td>
				   <td align="left">
					  	<input name="wmCusCostIList[${stuts.index }].costJg" maxlength="32" 
					  		type="text" class="form-control"  style="width:120px;"  datatype="*" value="${poVal.costJg }">
					  <label class="Validform_label" style="display: none;">价格RMB</label>
				   </td>
				   <td align="left">
					  	<input name="wmCusCostIList[${stuts.index }].costSl" maxlength="32"  onchange="count()"
					  		type="text" class="form-control"  style="width:120px;"  datatype="*" value="${poVal.costSl }">
					  <label class="Validform_label" style="display: none;">税率</label>
				   </td>
				   <td align="left">
					  	<input name="wmCusCostIList[${stuts.index }].costZk" maxlength="32" onchange="count()"
					  		type="text" class="form-control"  style="width:120px;"  datatype="*" value="${poVal.costZk }">
					  <label class="Validform_label" style="display: none;">折扣</label>
				   </td>
				   <td align="left">
					  	<input name="wmCusCostIList[${stuts.index }].costBhs" maxlength="32"  onchange="count()"
					  		type="text" class="form-control"  style="width:120px;"  datatype="*" value="${poVal.costBhs }">
					  <label class="Validform_label" style="display: none;">不含税价RMB</label>
				   </td>
				   <td align="left">
					  	<input name="wmCusCostIList[${stuts.index }].costHs" maxlength="32" 
					  		type="text" class="form-control"  style="width:120px;"  datatype="*" value="${poVal.costHs }">
					  <label class="Validform_label" style="display: none;">含税价RMB</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
