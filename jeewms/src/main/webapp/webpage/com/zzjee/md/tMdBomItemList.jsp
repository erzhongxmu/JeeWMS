<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!-- <h4>分类标题</h4> -->
	    <div class="row">
	      <div class="col-md-12 layout-header">
	        <button id="addBtn_TMdBomItem" type="button" class="btn btn-default">添加</button>
	        <button id="delBtn_TMdBomItem" type="button" class="btn btn-default">删除</button>
	        <script type="text/javascript"> 
			$('#addBtn_TMdBomItem').bind('click', function(){   
		 		 var tr =  $("#add_tMdBomItem_table_template tr").clone();
			 	 $("#add_tMdBomItem_table").append(tr);
			 	 resetTrNum('add_tMdBomItem_table');
			 	 return false;
		    });  
			$('#delBtn_TMdBomItem').bind('click', function(){   
		       $("#add_tMdBomItem_table").find("input:checked").parent().parent().remove();   
		        resetTrNum('add_tMdBomItem_table');
		        return false;
		    });
		    $(document).ready(function(){
		    	if(location.href.indexOf("load=detail")!=-1){
					$(":input").attr("disabled","true");
					$(".datagrid-toolbar").hide();
				}
		    	resetTrNum('add_tMdBomItem_table');
		    });
		</script>
	      </div>
	    </div>
<div style="margin: 0 15px; background-color: white;">    
	    <!-- Table -->
      <table id="tMdBomItem_table" class="table table-bordered table-hover" style="margin-bottom: 0;">
		<thead>
	      <tr>
	        <th style="white-space:nowrap;width:50px;">序号</th>
	        <th style="white-space:nowrap;width:50px;">操作</th>
					  <th>
							组件
					  </th>
					  <th>
							数量
					  </th>
					  <th>
							单位
					  </th>
					  <th>
							损耗率
					  </th>
					  <th>
							文本
					  </th>
					  <th>
							物料名称
					  </th>

	      </tr>
	    </thead>
        
	<tbody id="add_tMdBomItem_table">	
	<c:if test="${fn:length(tMdBomItemList)  <= 0 }">
			<tr>
				<th scope="row"><div name="xh"></div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>
					<input name="tMdBomItemList[0].id" type="hidden"/>
					<input name="tMdBomItemList[0].createName" type="hidden"/>
					<input name="tMdBomItemList[0].createBy" type="hidden"/>
					<input name="tMdBomItemList[0].createDate" type="hidden"/>
					<input name="tMdBomItemList[0].updateName" type="hidden"/>
					<input name="tMdBomItemList[0].updateBy" type="hidden"/>
					<input name="tMdBomItemList[0].updateDate" type="hidden"/>
					<input name="tMdBomItemList[0].sysOrgCode" type="hidden"/>
					<input name="tMdBomItemList[0].sysCompanyCode" type="hidden"/>
					<input name="tMdBomItemList[0].bpmStatus" type="hidden"/>
					<input name="tMdBomItemList[0].bomid" type="hidden"/>
					<input name="tMdBomItemList[0].prccode" type="hidden"/>
					<input name="tMdBomItemList[0].prcname" type="hidden"/>
				  <td>
						<input  id="tMdBomItemList[0].component" name="tMdBomItemList[0].component" type="text" style="width: 150px" class="form-control"  value="${poVal.component }"  ignore="ignore"  onclick="popupClick(this,'matcode,matname,unit','component,itemname,unit','pop_material')"/> 			 
					  <label class="Validform_label" style="display: none;">组件</label>
					</td>
				  <td>
					  	<input name="tMdBomItemList[0].quantity" maxlength="32" type="text" class="form-control"  style="width:120px;"  ignore="ignore" />
					  <label class="Validform_label" style="display: none;">数量</label>
					</td>
				  <td>
					  	<input name="tMdBomItemList[0].unit" maxlength="10" type="text" class="form-control"  style="width:120px;"  ignore="ignore" />
					  <label class="Validform_label" style="display: none;">单位</label>
					</td>
				  <td>
					  	<input name="tMdBomItemList[0].scrap" maxlength="10" type="text" class="form-control"  style="width:120px;"  ignore="ignore" />
					  <label class="Validform_label" style="display: none;">损耗率</label>
					</td>
				  <td>
					  	<input name="tMdBomItemList[0].text" maxlength="50" type="text" class="form-control"  style="width:120px;"  ignore="ignore" />
					  <label class="Validform_label" style="display: none;">文本</label>
					</td>
				  <td>
					  	<input name="tMdBomItemList[0].itemname" maxlength="132" type="text" class="form-control"  style="width:120px;"  ignore="ignore" />
					  <label class="Validform_label" style="display: none;">物料名称</label>
					</td>

   			</tr>
	</c:if>
	<c:if test="${fn:length(tMdBomItemList)  > 0 }">
		<c:forEach items="${tMdBomItemList}" var="poVal" varStatus="stuts">
			<tr>
				<th scope="row"><div name="xh">${stuts.index+1 }</div></th>
				<td><input style="width:20px;" type="checkbox" name="ck"/></td>
					<input name="tMdBomItemList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tMdBomItemList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tMdBomItemList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tMdBomItemList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tMdBomItemList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tMdBomItemList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tMdBomItemList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tMdBomItemList[${stuts.index }].sysOrgCode" type="hidden" value="${poVal.sysOrgCode }"/>
					<input name="tMdBomItemList[${stuts.index }].sysCompanyCode" type="hidden" value="${poVal.sysCompanyCode }"/>
					<input name="tMdBomItemList[${stuts.index }].bpmStatus" type="hidden" value="${poVal.bpmStatus }"/>
					<input name="tMdBomItemList[${stuts.index }].bomid" type="hidden" value="${poVal.bomid }"/>
					<input name="tMdBomItemList[${stuts.index }].prccode" type="hidden" value="${poVal.prccode }"/>
					<input name="tMdBomItemList[${stuts.index }].prcname" type="hidden" value="${poVal.prcname }"/>
				   <td align="left">
							<input  id="tMdBomItemList[${stuts.index }].component" name="tMdBomItemList[${stuts.index }].component"  type="text"  class="form-control"   ignore="ignore"   onclick="popupClick(this,'matcode,matname,unit','component,itemname,unit','pop_material')"   value="${poVal.component }" /> 			 
					  <label class="Validform_label" style="display: none;">组件</label>
				   </td>
				   <td align="left">
					  	<input name="tMdBomItemList[${stuts.index }].quantity" maxlength="32" type="text" class="form-control"  style="width:120px;"  ignore="ignore"  value="${poVal.quantity }"/>
					  <label class="Validform_label" style="display: none;">数量</label>
				   </td>
				   <td align="left">
					  	<input name="tMdBomItemList[${stuts.index }].unit" maxlength="10" type="text" class="form-control"  style="width:120px;"  ignore="ignore"  value="${poVal.unit }"/>
					  <label class="Validform_label" style="display: none;">单位</label>
				   </td>
				   <td align="left">
					  	<input name="tMdBomItemList[${stuts.index }].scrap" maxlength="10" type="text" class="form-control"  style="width:120px;"  ignore="ignore"  value="${poVal.scrap }"/>
					  <label class="Validform_label" style="display: none;">损耗率</label>
				   </td>
				   <td align="left">
					  	<input name="tMdBomItemList[${stuts.index }].text" maxlength="50" type="text" class="form-control"  style="width:120px;"  ignore="ignore"  value="${poVal.text }"/>
					  <label class="Validform_label" style="display: none;">文本</label>
				   </td>
				   <td align="left">
					  	<input name="tMdBomItemList[${stuts.index }].itemname" maxlength="132" type="text" class="form-control"  style="width:120px;"  ignore="ignore"  value="${poVal.itemname }"/>
					  <label class="Validform_label" style="display: none;">物料名称</label>
				   </td>

   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
