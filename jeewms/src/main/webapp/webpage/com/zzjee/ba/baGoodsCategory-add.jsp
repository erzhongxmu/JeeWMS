<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品类目</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <style type="text/css">
  	.combo_self{height: 22px !important;width: 150px !important;}
  	.layout-header .btn {
	    margin:0;
	   float: none !important;
	}
	.btn-default {
	    height: 35px;
	    line-height: 35px;
	    font-size:14px;
	}
  </style>
  
  <script type="text/javascript">
	$(function(){
		$(".combo").removeClass("combo").addClass("combo combo_self");
		$(".combo").each(function(){
			$(this).parent().css("line-height","0px");
		});   
	});
  		
  		 /**树形列表数据转换**/
  function convertTreeData(rows, textField) {
      for(var i = 0; i < rows.length; i++) {
          var row = rows[i];
          row.text = row[textField];
          if(row.children) {
          	row.state = "open";
              convertTreeData(row.children, textField);
          }
      }
  }
  /**树形列表加入子元素**/
  function joinTreeChildren(arr1, arr2) {
      for(var i = 0; i < arr1.length; i++) {
          var row1 = arr1[i];
          for(var j = 0; j < arr2.length; j++) {
              if(row1.id == arr2[j].id) {
                  var children = arr2[j].children;
                  if(children) {
                      row1.children = children;
                  }
                  
              }
          }
      }
  }
  </script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="baGoodsCategoryController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${baGoodsCategoryPage.id }"/>
		<input id="createName" name="createName" type="hidden" value="${baGoodsCategoryPage.createName }"/>
		<input id="createBy" name="createBy" type="hidden" value="${baGoodsCategoryPage.createBy }"/>
		<input id="createTime" name="createTime" type="hidden" value="${baGoodsCategoryPage.createTime }"/>
		<input id="updateName" name="updateName" type="hidden" value="${baGoodsCategoryPage.updateName }"/>
		<input id="updateBy" name="updateBy" type="hidden" value="${baGoodsCategoryPage.updateBy }"/>
		<input id="updateTime" name="updateTime" type="hidden" value="${baGoodsCategoryPage.updateTime }"/>
		<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${baGoodsCategoryPage.sysOrgCode }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							类目编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="categoryCode" name="categoryCode" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="checked"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类目编码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类目名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="categoryName" name="categoryName" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="checked"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类目名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类目级别:
						</label>
					</td>
					<td class="value">
					     	 <input id="categoryLevel" name="categoryLevel" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类目级别</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							父级目录:
						</label>
					</td>
					<td class="value">
							<input id="pid" name="pid" type="text" style="width: 150px" class="inputxt easyui-combotree" 
							
							ignore="ignore"
							data-options="panelHeight:'220',
				                    url: 'baGoodsCategoryController.do?datagrid&field=id,categoryName',  
				                    loadFilter: function(data) {
				                    	var rows = data.rows || data;
				                    	var win = frameElement.api.opener;
				                    	var listRows = win.getDataGrid().treegrid('getData');
				                    	joinTreeChildren(rows, listRows);
				                    	convertTreeData(rows, 'categoryName');
				                    	return rows; 
				                    },
				                    onLoadSuccess: function() {
				                    	var win = frameElement.api.opener;
				                    	var currRow = win.getDataGrid().treegrid('getSelected');
				                    	if(!'${baGoodsCategoryPage.id}') {
				                    		//增加时，选择当前父菜单
				                    		if(currRow) {
				                    			$('#pid').combotree('setValue', currRow.id);
				                    		}
				                    	}else {
				                    		//编辑时，选择当前父菜单
				                    		if(currRow) {
				                    			$('#pid').combotree('setValue', currRow.pid);
				                    		}
				                    	}
				                    }">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">父级目录</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否为顶级目录:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="topNode" type="list"
									typeGroupCode="yes_no" defaultVal="${baGoodsCategoryPage.topNode}" hasLabel="false"  title="是否为顶级目录"  
									></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否为顶级目录</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/zzjee/ba/baGoodsCategory.js"></script>		
