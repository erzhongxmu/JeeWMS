package org.jeecgframework.tag.core.easyui;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.easyui.ColumnValue;
import org.jeecgframework.tag.vo.easyui.DataGridColumn;
import org.jeecgframework.tag.vo.easyui.DataGridUrl;
import org.jeecgframework.tag.vo.easyui.OptTypeDirection;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;


import com.google.common.base.Function;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 
 * 类描述：DATAGRID标签处理类
 * 
 @author admin
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
@SuppressWarnings({"serial","rawtypes","unchecked","static-access"})
public class DataGridTag extends TagSupport {
	private final String DATE_FORMATTER = "yyyy-MM-dd";
	private final String DATETIME_FORMATTER = "yyyy-MM-dd hh:mm:ss";
	
	protected String fields = "";// 显示字段
	protected String searchFields = "";// 查询字段  Author:qiulu  Date:20130618 for：添加对区间查询的支持
	protected String name;// 表格标示
	protected String title;// 表格标示
	protected String idField="id";// 主键字段
	protected boolean treegrid = false;// 是否是树形列表
	protected List<DataGridUrl> urlList = new ArrayList<DataGridUrl>();// 列表操作显示
	protected List<DataGridUrl> toolBarList = new ArrayList<DataGridUrl>();// 工具条列表
	protected List<DataGridColumn> columnList = new ArrayList<DataGridColumn>();// 列表操作显示
	protected List<ColumnValue> columnValueList = new ArrayList<ColumnValue>();// 值替换集合
	protected List<ColumnValue> columnStyleList = new ArrayList<ColumnValue>();// 颜色替换集合
	public Map<String, Object> map;// 封装查询条件
	private String actionUrl;// 分页提交路径
	public int allCount;
	public int curPageNo;
	public int pageSize = 10;
	public boolean pagination = true;// 是否显示分页
	private String width;
	private String height;
	private boolean checkbox = false;// 是否显示复选框
	private boolean showPageList = true;// 定义是否显示页面列表
	private boolean openFirstNode = false;//是不是展开第一个节点
	private boolean fit = true;// 是否允许表格自动缩放，以适应父容器
	private boolean fitColumns = true;// 当为true时，自动展开/合同列的大小，以适应的宽度，防止横向滚动.
	private String sortName;//定义的列进行排序
	private String sortOrder = "asc";//定义列的排序顺序，只能是"递增"或"降序".
	private boolean showRefresh = true;// 定义是否显示刷新按钮
	private boolean showText = true;// 定义是否显示刷新按钮
	private String style = "easyui";// 列表样式easyui,datatables,jqgrid
	private String onLoadSuccess;// 数据加载完成调用方法
	private String onClick;// 单击事件调用方法
	private String onDblClick;// 双击事件调用方法
	private String queryMode = "single";//查询模式
	private String entityName;//对应的实体对象
	private String rowStyler;//rowStyler函数
	private String extendParams;//扩展参数,easyui有的,但是jeecg没有的参数进行扩展
	private boolean autoLoadData=true; // 列表是否自动加载数据
	//private boolean frozenColumn=false; // 是否是冰冻列    默认不是
	private String langArg;
	
	private Boolean singleSelect;//是否单选true,false

	protected String cssTheme ;

	private boolean isShowSearch=true;//检索区域是否可收缩

	
	private String treeField;//树形列表展示列
	
	public String getCssTheme() {
		return cssTheme;
	}
	public void setCssTheme(String cssTheme) {
		this.cssTheme = cssTheme;
	}


	private boolean queryBuilder = false;// 高级查询器
	public boolean isQueryBuilder() {
		return queryBuilder;
	}

	public void setQueryBuilder(boolean queryBulder) {
		this.queryBuilder = queryBulder;
	}

	public void setTreeField(String treeField) {
		this.treeField = treeField;
	}

	//json转换中的系统保留字
	protected static Map<String,String> syscode = new HashMap<String,String>();
	static{
		syscode.put("class", "clazz");
	}
	
	@Autowired
	private static SystemService systemService;
	
	public void setOnLoadSuccess(String onLoadSuccess) {
		this.onLoadSuccess = onLoadSuccess;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTreegrid(boolean treegrid) {
		this.treegrid = treegrid;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFit(boolean fit) {
		this.fit = fit;
	}

	public void setShowPageList(boolean showPageList) {
		this.showPageList = showPageList;
	}

	public void setShowRefresh(boolean showRefresh) {
		this.showRefresh = showRefresh;
	}
	
	public void setSingleSelect(Boolean singleSelect) {
		this.singleSelect = singleSelect;
	}
	
	public boolean getIsShowSearch() {
		return isShowSearch;
	}
	public void setIsShowSearch(boolean isShowSearch) {
		this.isShowSearch = isShowSearch;
	}
	/**
	 * 设置询问操作URL
	 * @param urlfont 
	 * @param urlclass 
	 */
	public void setConfUrl(String url, String title, String message, String exp,String operationCode, String urlStyle, String urlclass, String urlfont) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setType(OptTypeDirection.Confirm);
		dataGridUrl.setMessage(message);
		dataGridUrl.setExp(exp);
		dataGridUrl.setUrlStyle(urlStyle);

		dataGridUrl.setUrlclass(urlclass);
		dataGridUrl.setUrlfont(urlfont);

		installOperationCode(dataGridUrl, operationCode,urlList);
	}

	/**
	 * 设置删除操作URL
	 */
	public void setDelUrl(String url, String title, String message, String exp, String funname,String operationCode, String urlStyle,String urlclass,String urlfont) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setType(OptTypeDirection.Del);
		dataGridUrl.setMessage(message);
		dataGridUrl.setExp(exp);
		dataGridUrl.setFunname(funname);
		dataGridUrl.setUrlStyle(urlStyle);

		dataGridUrl.setUrlclass(urlclass);
		dataGridUrl.setUrlfont(urlfont);

		installOperationCode(dataGridUrl, operationCode,urlList);
	}
	/**
	 * 设置默认操作URL
	 */
	public void setDefUrl(String url, String title, String exp,String operationCode, String urlStyle,String urlclass,String urlfont) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setType(OptTypeDirection.Deff);
		dataGridUrl.setExp(exp);
		dataGridUrl.setUrlStyle(urlStyle);

		dataGridUrl.setUrlclass(urlclass);
		dataGridUrl.setUrlfont(urlfont);

		installOperationCode(dataGridUrl, operationCode,urlList);
		
	}
	/**
	 * 设置工具条
	 * @param height2 
	 * @param width2 
	 */
	public void setToolbar(String url, String title, String icon, String exp,String onclick, String funname,String operationCode, String width2, String height2) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setType(OptTypeDirection.ToolBar);
		dataGridUrl.setIcon(icon);
		dataGridUrl.setOnclick(onclick);
		dataGridUrl.setExp(exp);
		dataGridUrl.setFunname(funname);
		dataGridUrl.setWidth(String.valueOf(width2));
		dataGridUrl.setHeight(String.valueOf(height2));
		installOperationCode(dataGridUrl, operationCode,toolBarList);
		
	}

	/**
	 * 设置自定义函数操作URL
	 */
	public void setFunUrl(String title, String exp, String funname,String operationCode, String urlStyle,String urlclass,String urlfont) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setType(OptTypeDirection.Fun);
		dataGridUrl.setExp(exp);
		dataGridUrl.setFunname(funname);
		dataGridUrl.setUrlStyle(urlStyle);

		dataGridUrl.setUrlclass(urlclass);
		dataGridUrl.setUrlfont(urlfont);

		installOperationCode(dataGridUrl, operationCode,urlList);
		
	}

	/**
	 * 设置自定义函数操作URL
	 * @param urlfont 
	 * @param urlclass 
	 */
	public void setOpenUrl(String url, String title, String width, String height, String exp,String operationCode, String openModel, String urlStyle, String urlclass, String urlfont) {
		DataGridUrl dataGridUrl = new DataGridUrl();
		dataGridUrl.setTitle(title);
		dataGridUrl.setUrl(url);
		dataGridUrl.setWidth(width);
		dataGridUrl.setHeight(height);
		dataGridUrl.setType(OptTypeDirection.valueOf(openModel));
		dataGridUrl.setExp(exp);
		dataGridUrl.setUrlStyle(urlStyle);

		dataGridUrl.setUrlclass(urlclass);
		dataGridUrl.setUrlfont(urlfont);

		installOperationCode(dataGridUrl, operationCode,urlList);
		
	}

	/**
	 * 
	 * <b>Summary: </b> setColumn(设置字段)
	 * 
	 * @param title
	 * @param field
	 * @param width
	 * @param showLen 
	 */
	public void setColumn(String title, String field, Integer width,Integer showLen,String rowspan, 
			String colspan, String align, boolean sortable, boolean checkbox, 
			String formatter,String formatterjs, boolean hidden, String replace, 
			String treefield, boolean image,String imageSize, 
			boolean query, String url, String funname, 
			String arg,String queryMode, String dictionary,boolean popup,
			boolean frozenColumn,String extend,
			String style,String downloadName,boolean isAuto,String extendParams,String editor,String defaultVal) {
		DataGridColumn dataGridColumn = new DataGridColumn();
		dataGridColumn.setAlign(align);
		dataGridColumn.setCheckbox(checkbox);
		dataGridColumn.setColspan(colspan);
		dataGridColumn.setField(field);
		dataGridColumn.setFormatter(formatter);
		dataGridColumn.setFormatterjs(formatterjs);
		dataGridColumn.setHidden(hidden);
		dataGridColumn.setRowspan(rowspan);
		dataGridColumn.setSortable(sortable);
		dataGridColumn.setTitle(title);
		dataGridColumn.setWidth(width);
		//author：xugj--start--date:2016年5月11日 for:TASK #1080 【UI标签改造】t:dgCol 显示内容长度控制 -->
		dataGridColumn.setShowLen(showLen);
		//author：xugj--end---date:2016年5月11日 for:TASK #1080 【UI标签改造】t:dgCol 显示内容长度控制 -->
		dataGridColumn.setTreefield(treefield);
		dataGridColumn.setImage(image);
		dataGridColumn.setImageSize(imageSize);
		dataGridColumn.setReplace(replace);
		dataGridColumn.setQuery(query);
		dataGridColumn.setUrl(url);
		dataGridColumn.setFunname(funname);
		dataGridColumn.setArg(arg);
		dataGridColumn.setQueryMode(queryMode);
		dataGridColumn.setDictionary(dictionary);
		dataGridColumn.setPopup(popup);
		dataGridColumn.setFrozenColumn(frozenColumn);
		dataGridColumn.setExtend(extend);
		dataGridColumn.setStyle(style);
		dataGridColumn.setDownloadName(downloadName);
		dataGridColumn.setAutocomplete(isAuto);
		dataGridColumn.setExtendParams(extendParams);
		dataGridColumn.setEditor(editor);

		dataGridColumn.setDefaultVal(defaultVal);

		columnList.add(dataGridColumn);
		Set<String> operationCodes = (Set<String>) super.pageContext.getRequest().getAttribute(Globals.OPERATIONCODES);
		if (null!=operationCodes) {
			for (String MyoperationCode : operationCodes) {
				if (oConvertUtils.isEmpty(MyoperationCode)) {
                    break;
                }
				systemService = ApplicationContextUtil.getContext().getBean(
							SystemService.class);
				TSOperation operation = systemService.getEntity(TSOperation.class, MyoperationCode);
				if(operation.getOperationcode().equals(field)){
					columnList.remove(dataGridColumn);
				}
			}
		}
		if (field != "opt") {
			fields += field + ",";
			if ("group".equals(queryMode)) {
				searchFields += field + "," + field + "_begin," + field + "_end,";
			} else {
				searchFields += field + ",";
			}
		}
		if (StringUtil.isNotEmpty(replace)) {
			String[] test = replace.split(",");
			String lang_key = "";
			String text = "";
			String value = "";
			for (String string : test) {
				lang_key = string.substring(0, string.indexOf("_"));
				text += MutiLangUtil.getMutiLangInstance().getLang(lang_key) + ",";
				
				value += string.substring(string.indexOf("_") + 1) + ",";
			}
			setColumn(field, text, value);

		}
		if (!StringUtils.isBlank(dictionary)&&(!popup)) {
			if(dictionary.contains(",")){
				;
				String[] dic = dictionary.split(",");
				String text = "";
				String value = "";
				String sql = "select " + dic[1] + " as field," + dic[2]
						+ " as text from " + dic[0];
				systemService = ApplicationContextUtil.getContext().getBean(
						SystemService.class);
				List<Map<String, Object>> list = systemService.findForJdbc(sql);
				for (Map<String, Object> map : list){
					text += map.get("text") + ",";
					value += map.get("field") + ",";
				}
				if(list.size()>0) {
                    setColumn(field, text, value);
                }
			}else{
				String text = "";
				String value = "";
				List<TSType> typeList = ResourceUtil.allTypes.get(dictionary.toLowerCase());
				if (typeList != null && !typeList.isEmpty()) {
					for (TSType type : typeList) {
						text += MutiLangUtil.doMutiLang(type.getTypename(), "") + ",";
						value += type.getTypecode() + ",";
					}
				setColumn(field, text, value);
				}
			}
		}
		if(StringUtil.isNotEmpty(style)){
			String[] temp = style.split(",");
			String text = "";
			String value = "";
			if(temp.length == 1&&temp[0].indexOf("_")==-1){
				text = temp[0];
			}else{
				for (String string : temp) {
					text += string.substring(0, string.indexOf("_")) + ",";
					value += string.substring(string.indexOf("_") + 1) + ",";
				}
			}
			setStyleColumn(field, text, value);
		}
	}
	
	/**
	 * 设置 颜色替换值
	 * @param field
	 * @param text
	 * @param value
	 */
	private void setStyleColumn(String field, String text, String value) {
		ColumnValue columnValue = new ColumnValue();
		columnValue.setName(field);
		columnValue.setText(text);
		columnValue.setValue(value);
		columnStyleList.add(columnValue);
	}

	/**
	 * 
	 * <b>Summary: </b> setColumn(设置字段替换值)
	 * 
	 * @param name
	 * @param text
	 * @param value
	 */
	public void setColumn(String name, String text, String value) {
		ColumnValue columnValue = new ColumnValue();
		columnValue.setName(name);
		columnValue.setText(text);
		columnValue.setValue(value);
		columnValueList.add(columnValue);
	}

	@Override
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = null;
		try {
			title = MutiLangUtil.doMutiLang(title, langArg);
			
			out = this.pageContext.getOut();
//			String indexStyle =null;

//			Cookie[] cookies = ((HttpServletRequest) super.pageContext
//					.getRequest()).getCookies();
//			for (Cookie cookie : cookies) {
//				if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
//					continue;
//				}
//				if (cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")) {
//					indexStyle = cookie.getValue();
//				}
//			}
//			SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme((HttpServletRequest) super.pageContext.getRequest());
			if (style.equals("easyui")) {
//				if("ace".equals(sysThemesEnum.getStyle())){
//					out.print(this.aceStyleTable().toString());
//				}else{
					out.print(end().toString());
					out.flush();
//				}

			}else if("jqgrid".equals(style)){
				out.print(jqGrid().toString());
				out.flush();

			}else{
				out.print(datatables().toString());
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {

					out.clearBuffer();
					end().setLength(0);
					// 清空资源
					urlList.clear();
					toolBarList.clear();
					columnValueList.clear();
					columnStyleList.clear();
					columnList.clear();
					fields = "";
					searchFields = "";

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return EVAL_PAGE;
	}

	/**
	 * jqgrid构建datagrid
	 * @return
	 */
	public StringBuffer jqGrid(){
		StringBuffer sb = new StringBuffer();
		sb.append("<link href=\"plug-in-ui/hplus/css/bootstrap.min.css?v=3.3.6\" rel=\"stylesheet\">");
		sb.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"plug-in-ui/hplus/css/plugins/jqgrid/ui.jqgrid.css\">");
		sb.append("<script src=\"plug-in-ui/hplus/js/jquery.min.js\"></script>");
		sb.append("<link rel=\"stylesheet\" href=\"plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css\" type=\"text/css\"></link>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/lhgDialog/lhgdialog.min.js\"></script>");
		sb.append("<script src=\"plug-in-ui/hplus/js/bootstrap.min.js\"></script>");
		sb.append("<script src=\"plug-in-ui/hplus/js/plugins/peity/jquery.peity.min.js\"></script>");
		sb.append("<script src=\"plug-in-ui/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js\"></script>");
		sb.append("<script src=\"plug-in-ui/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js\"></script>");
		sb.append("<script src=\"plug-in/tools/datagrid_2_jqgrid.js\"></script>");
		sb.append("<style>");
		sb.append("#t_"+name+"{border-bottom:1px solid #ddd;}");
		sb.append("#t_"+name+" .btn{margin-right:10px;}");
		sb.append(".search_div{padding:10px;}");
		sb.append(".tool_bar_div{padding:10px;}");
		sb.append("</style>");
		sb.append("<table id=\""+name+"\"></table>");
		sb.append("<div id=\"gridPager\"></div>");
		sb.append("<script type=\"text/javascript\">");
		sb.append("$(document).ready(function() {");
		sb.append(" $.jgrid.defaults.styleUI=\"Bootstrap\";");
		sb.append("$('#"+name+"').jqGrid({");
		sb.append("url:'" + actionUrl + "&dataStyle=jqgrid&field=" + fields + "',");
		sb.append("datatype:\"json\",");
		sb.append("mtype:\"POST\",");
		sb.append("height:'auto',");
		sb.append("autowidth:true,");
		sb.append("shrinkToFit: true,");
		sb.append("multiselect: true,");
		sb.append("toolbar:[true,'top'],");
		StringBuffer colNameBuffer = new StringBuffer();
		StringBuffer colModelBuffer = new StringBuffer();
		for (DataGridColumn column : columnList) {
				colNameBuffer.append("'");
				colNameBuffer.append(column.getTitle());
				colNameBuffer.append("',");
				if("opt".equals(column.getField())){
					colModelBuffer.append("{name:'");
					colModelBuffer.append(column.getField());
					colModelBuffer.append("',index:'");
					colModelBuffer.append(column.getField());
					colModelBuffer.append("',width:'");
					colModelBuffer.append(column.getWidth());
					colModelBuffer.append("',align:'");
					colModelBuffer.append(column.getAlign());
					colModelBuffer.append("' ");
					colModelBuffer.append(",hidden:");
					colModelBuffer.append(column.isHidden());
					colModelBuffer.append(",formatter:currencyFormatter");
					colModelBuffer.append("},");
				}else{
					colModelBuffer.append("{name:'");
					colModelBuffer.append(column.getField());
					colModelBuffer.append("',index:'");
					colModelBuffer.append(column.getField());
					colModelBuffer.append("',width:'");
					colModelBuffer.append(column.getWidth());
					colModelBuffer.append("',align:'");
					colModelBuffer.append(column.getAlign());
					colModelBuffer.append("' ");
					if(oConvertUtils.isNotEmpty(column.getFormatter())){
						if("yyyy-MM-dd".equals(column.getFormatter())){
							colModelBuffer.append(",formatter:'date'");
						}else{
							colModelBuffer.append(",formatter:");
							colModelBuffer.append(column.getFormatter());
						}
//						colModelBuffer.append(date);
//						colModelBuffer.append("' ");
					}
					
					if(oConvertUtils.isNotEmpty(column.getReplace())){
						colModelBuffer.append(",formatter:replaceFormatter");
						colModelBuffer.append(",formatoptions:{replace:");
						String[] replaceArray = column.getReplace().split(",");
						StringBuffer replaceBuffer = new StringBuffer();
						replaceBuffer.append("{");
						if(replaceArray.length > 0){
							String text = "";
							String value = "";
							for (String replaceOri : replaceArray) {
								String lang_key = replaceOri.split("_")[0];
								text = MutiLangUtil.getMutiLangInstance().getLang(lang_key);
								value =replaceOri.split("_")[1];
								replaceBuffer.append("'");
								replaceBuffer.append(value);
								replaceBuffer.append("':'");
								replaceBuffer.append(text);
								replaceBuffer.append("',");
							}
						}
						replaceBuffer.append("}");
						colModelBuffer.append(replaceBuffer.toString());
						colModelBuffer.append("}");
					}
					if(oConvertUtils.isNotEmpty(column.getFormatterjs())){
						colModelBuffer.append(",formatter:"+column.getFormatterjs());
					}
					colModelBuffer.append(",hidden:");
					colModelBuffer.append(column.isHidden());
					colModelBuffer.append("},");
			}
		}
		String colNames = colNameBuffer.toString();
		colNames = colNames.substring(0,colNames.length()-1);
		String colModels = colModelBuffer.toString();
		colModels = colModels.substring(0,colModels.length()-1);
		sb.append("colNames:[");
		sb.append(colNames);
		sb.append("], colModel:[");
		sb.append(colModels);
		sb.append("],");
		sb.append("rownumbers:true,");
		sb.append("viewrecords: true,");
		sb.append("rowNum:"+pageSize+",");
		sb.append("rowList:["+pageSize+","+2*pageSize+","+3*pageSize+","+4*pageSize+","+5*pageSize+"],");
//		sb.append("jsonReader:{");
//		sb.append("id: \"blackId\",");
//		sb.append("repeatitems : false},");
		sb.append("pager:$('#gridPager')");
		sb.append(",caption:'");
		sb.append(title);
		sb.append("'});");
		
		//自适应表格宽度
//		sb.append("$(\"#"+name+"\").setGridWidth($(window).width()*0.99);");
		
		//表格顶部，查询、工具栏
		sb.append("$('#t_"+name+"').append('");
		if(hasQueryColum(columnList)){
			sb.append("<div id=\""+name+"tb\" class=\"search_div row\">");
			sb.append("<div name=\"searchColums\" class=\"search-content\"><form name=\""+name+"Form\" id=\""+name+"Form\"></form></div><div class=\"col-sm-1 pull-right\">");
			sb.append("<button class=\"btn btn-success\" type=\"button\" onclick=\"javascript:"+name+"search();\"><span><i class=\"fa fa-search\"></i>查询</span></button>");
			sb.append("</div></div>");
		}
		sb.append("<div class=\"tool_bar_div bg-info\"></div>");
		sb.append("');");
		
		//表格顶部查询
		if(hasQueryColum(columnList) && !columnList.isEmpty()){
			for (DataGridColumn column : columnList) {
				if(column.isQuery()){
					sb.append("$('#t_"+name+" .search-content form').append('");
					sb.append("<label style=\"margin-right:10px;margin-left:10px;\">");
					sb.append(column.getTitle());
					sb.append("</label>");
					String dictionary = column.getDictionary();
					
					if(oConvertUtils.isNotEmpty(dictionary)){
						//字典数据信息，存在两种处理方式，一种是表格元素数据，一种是字典表当中的数据
						sb.append("<select  name=\"");
						sb.append(column.getField());
						sb.append("\">");
						sb.append("<option value=\"\">---请选择---</option>");
						if(dictionary.indexOf(",")>-1){
							//表格数据信息
							try{
								String[] dictionaryArray = dictionary.split(",");
								if(dictionaryArray.length == 3){
									String sql = "select " + dictionaryArray[1]+","+dictionaryArray[2]+" from "+dictionaryArray[0];
									List<Map<String, Object>> dictionaryList = systemService.findForJdbc(sql);
									if(dictionaryList != null && !dictionaryList.isEmpty()){
										for (Map<String, Object> map : dictionaryList) {
											if(map.containsKey(dictionaryArray[1]) && map.containsKey(dictionaryArray[2])){
												sb.append("<option value=\"");
												sb.append(map.get(dictionaryArray[1]));
												sb.append(">");
												sb.append(map.get(dictionaryArray[2]));
												sb.append("</option>");
											}
										}
									}
								}
							}catch (Exception e) {
								// TODO: 字典数据异常
							}
						}else{
							//字典表数据
							List<TSType> typeList = ResourceUtil.allTypes.get(dictionary.toLowerCase());
							if(typeList != null && !typeList.isEmpty()){
								for (TSType type : typeList) {
									sb.append("<option value=\"");
									sb.append(type.getTypecode());
									sb.append(">");
									sb.append(type.getTypename());
									sb.append("</option>");
								}
							}
						}
						sb.append("</select>");
					}else if(oConvertUtils.isNotEmpty(column.getReplace())){
						sb.append("<select  name=\""+column.getField().replaceAll("_","\\.")+"\" WIDTH=\"100\" style=\"width: 104px\"> ");
						sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
						String[] test = column.getReplace().split(",");
						String text = "";
						String value = "";
						for (String string : test) {
							String lang_key = string.split("_")[0];
							text = MutiLangUtil.getMutiLangInstance().getLang(lang_key);
							value =string.split("_")[1];
							if(column.getDefaultVal()!=null&&column.getDefaultVal().trim().equals(value)){
								sb.append("<option value =\""+value+"\" selected=\"selected\">"+text+"</option>");
							}else{
								sb.append("<option value =\""+value+"\" >"+text+"</option>");
							}
						}
						sb.append("</select>");
					}else{
						sb.append("<input  onkeypress=\"EnterPress(event)\" onkeydown=\"EnterPress()\"  type=\"text\" name=\""+column.getField().replaceAll("_","\\.")+"\"  "+extendAttribute(column.getExtend())+" ");
						if(this.DATE_FORMATTER.equals(column.getFormatter())){
							sb.append(" style=\"width: 160px\" class=\"Wdate\" onClick=\"WdatePicker()\" ");
						}else if(this.DATETIME_FORMATTER.equals(column.getFormatter())){
							sb.append(" style=\"width: 160px\" class=\"Wdate\" onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" ");
						}else{
							sb.append(" style=\"width: 120px\" class=\"inuptxt\" ");
						}
						if(oConvertUtils.isNotEmpty(column.getDefaultVal())){
							sb.append(" value=\""+column.getDefaultVal()+"\" ");
						}
						sb.append(" />");
					}
					sb.append("');");
				}
			}
		}
		
		//工具栏的处理方式
		if(toolBarList.size() > 0){
			for (DataGridUrl toolBar : toolBarList) {
				sb.append("$('#t_"+name+" .tool_bar_div').append('");
				sb.append("<button class=\"btn btn-success\"");
				sb.append(" onclick=\"");
				sb.append(toolBar.getFunname());
				sb.append("(\\'");
				sb.append(toolBar.getTitle());
				sb.append("\\',\\'");
				sb.append(toolBar.getUrl());
				sb.append("\\',\\'");
				sb.append(name);
				sb.append("\\',");
				String width = toolBar.getWidth().contains("%")?"'"+toolBar.getWidth()+"'":toolBar.getWidth();
				String height = toolBar.getHeight().contains("%")?"'"+toolBar.getHeight()+"'":toolBar.getHeight();
				sb.append(width+","+height+")");
				sb.append("\" >");
				//工具栏图标显示
				String toolBarIcon = toolBar.getIcon();
				if(oConvertUtils.isNotEmpty(toolBarIcon)){
					if(toolBarIcon.equals("icon-add") ){
						sb.append("<i class=\"fa fa-plus\"></i>");
					}else if(toolBarIcon.equals("icon-edit") ){
						sb.append("<i class=\"fa fa-edit\"></i>");
					}else if (toolBarIcon.equals("icon-put") ) {
						sb.append("<i class=\"fa fa-download\"></i>");
					}else if (toolBarIcon.equals("icon-putout")) {
						sb.append("<i class=\"fa fa-upload\"></i>");
					}else if (toolBarIcon.equals("icon-remove") ) {
						sb.append("<i class=\"fa fa-trash-o\"></i>");
					}else if (toolBarIcon.equals("icon-search") ) {
						sb.append("<i class=\"fa fa-search\"></i>");
					}else{
						sb.append("<i class=\"fa "+toolBarIcon+"\"></i>");
					}
				}
				sb.append(toolBar.getTitle());
				sb.append("</button>");
				sb.append("');");
			}
		}
		//添加在底部的按钮
//		sb.append("$('#"+name+"').navGrid('#gridPager',{edit:false,add:false,del:false,search:false})");
//		if(toolBarList.size() > 0){
//			for (DataGridUrl toolBar : toolBarList) {
//				sb.append(".navButtonAdd('#gridPager',{");
//				sb.append("caption:'");
//				sb.append(toolBar.getTitle());
//				sb.append("'");
//				sb.append(",buttonicon:'");
//				sb.append(toolBar.getIcon());
//				sb.append("'");
//				sb.append(",onClickButton:");
//				sb.append("function(){");
//				if(oConvertUtils.isNotEmpty(toolBar.getOnclick())){
//					sb.append(toolBar.getOnclick());
//				}else{
//					sb.append(toolBar.getFunname());
//					sb.append("('");
//					sb.append(toolBar.getTitle());
//					sb.append("','");
//					sb.append(toolBar.getUrl());
//					sb.append("','");
//					sb.append(name);
//					sb.append("',");
//					String width = toolBar.getWidth().contains("%")?"'"+toolBar.getWidth()+"'":toolBar.getWidth();
//					String height = toolBar.getHeight().contains("%")?"'"+toolBar.getHeight()+"'":toolBar.getHeight();
//					sb.append(width+","+height+")");
//				}
//				sb.append("}");
//				sb.append(",position:'last'");
//				sb.append("})");
//			}
//		}
		sb.append("});");
		sb.append("function currencyFormatter(cellvalue, options, rec){ ");
		sb.append("var index = options.pos;");
		StringBuffer optSb = new StringBuffer();
		this.getOptUrl(optSb);
		sb.append(optSb.toString());
		sb.append("}");
		sb.append("function reloadTable(){");
		sb.append("try{");
		sb.append("	$(\'#\'+gridname).trigger(\"reloadGrid\");" );
		sb.append("}catch(ex){}");
		sb.append("}");
		
		//数据替换
		sb.append("function replaceFormatter(cellvalue,options,rec){");
		sb.append("var formatterOptions = options.colModel.formatoptions;");
		sb.append("var replace = formatterOptions.replace;");
		sb.append("return replace[cellvalue];");
		sb.append("}");
		
		//回车查询
		sb.append("function EnterPress(e){");
		sb.append("var e = e || window.event;");
		sb.append("if(e.keyCode == 13){ ");
		sb.append(name+"search();");
		sb.append("}}");
		
		//提交查询
		sb.append("function " + name + "search(){");
		sb.append("try { if(! $(\"#"+name+"Form\").Validform({tiptype:3}).check()){return false;} } catch (e){}");
		sb.append("var queryParams = '';");
		sb.append("$(\'#" + name + "tb\').find('*').each(function(){ if($(this).attr('name') != undefined && $(this).val() != ''){queryParams += \"&\" + $(this).attr('name') + \"=\" + $(this).val();}});");
		sb.append("console.log(queryParams);");
		sb.append("var url = '"+actionUrl+"&dataStyle=jqgrid&field="+searchFields+"' + queryParams;");
		sb.append("console.log(url);");
		sb.append("$(\'#" + name + "\').jqGrid('setGridParam',{url:url,page:1}).trigger(\"reloadGrid\");" + "}");
		sb.append("</script>");
		return sb;
	}

	/**
	 * datatables构造方法
	 * 
	 * @return
	 */
	public StringBuffer datatables() {
		StringBuffer sb = new StringBuffer();
		sb.append("<link href=\"plug-in-ui/hplus/css/plugins/dataTables/dataTables.bootstrap.css\" rel=\"stylesheet\">");
		sb.append("<script src=\"plug-in-ui/hplus/js/plugins/dataTables/jquery.dataTables.js\"></script>");
		sb.append("<script type=\"text/javascript\">");
		sb.append("$(document).ready(function() {");
		sb.append("var oTable = $(\'#userList\').dataTable({");
		// sb.append(
		// "\"sDom\" : \"<\'row\'<\'span6\'l><\'span6\'f>r>t<\'row\'<\'span6\'i><\'span6\'p>>\",");
		sb.append("\"bProcessing\" : true,");// 当datatable获取数据时候是否显示正在处理提示信息"
		sb.append("\"bPaginate\" : true,"); // 是否分页"
		sb.append("\"sPaginationType\" : \"full_numbers\",");// 分页样式full_numbers,"
		sb.append("\"bFilter\" : true,");// 是否使用内置的过滤功能"
		sb.append("\"bSort\" : true, ");// 排序功能"
		sb.append("\"bAutoWidth\" : true,");// 自动宽度"
		sb.append("\"bLengthChange\" : true,");// 是否允许用户自定义每页显示条数"
		sb.append("\"bInfo\" : true,");// 页脚信息"
		sb.append("\"sAjaxSource\" : \""+ actionUrl + "&field=" + fields+"\",");
		sb.append("\"bServerSide\" : true,");// 指定从服务器端获取数据
		sb.append("\"oLanguage\" : {" + "\"sLengthMenu\" : \" _MENU_ 条记录\"," + "\"sZeroRecords\" : \"没有检索到数据\"," + "\"sInfo\" : \"第 _START_ 至 _END_ 条数据 共 _TOTAL_ 条\"," + "\"sInfoEmtpy\" : \"没有数据\"," + "\"sProcessing\" : \"正在加载数据...\"," + "\"sSearch\" : \"搜索\"," + "\"oPaginate\" : {" + "\"sFirst\" : \"首页\"," + "\"sPrevious\" : \"前页\", " + "\"sNext\" : \"后页\"," + "\"sLast\" : \"尾页\"" + "}" + "},"); // 汉化
		// 获取数据的处理函数 \"data\" : {_dt_json : JSON.stringify(aoData)},
		sb.append("\"fnServerData\" : function(sSource, aoData, fnCallback, oSettings) {");
		// + "\"data\" : {_dt_json : JSON.stringify(aoData)},"
		sb.append("oSettings.jqXHR = $.ajax({" + "\"dataType\" : \'json\'," + "\"type\" : \"POST\"," + "\"url\" : sSource," + "\"data\" : aoData," + "\"success\" : fnCallback" + "});},");
		sb.append("\"aoColumns\" : [ ");
		int i = 0;
		for (DataGridColumn column : columnList) {
			i++;
			sb.append("{");
			sb.append("\"sTitle\":\"" + column.getTitle() + "\"");
			if (column.getField().equals("opt")) {
				sb.append(",\"mData\":\"" + idField + "\"");
				sb.append(",\"sWidth\":\"20%\"");
				sb.append(",\"bSortable\":false");
				sb.append(",\"bSearchable\":false");
				sb.append(",\"mRender\" : function(data, type, rec) {");
				this.getOptUrl(sb);
				sb.append("}");
			} else {
				int colwidth = (column.getWidth() == null) ? column.getTitle().length() * 15 : column.getWidth();
				sb.append(",\"sName\":\"" + column.getField() + "\"");
				sb.append(",\"mDataProp\":\"" + column.getField() + "\"");
				sb.append(",\"mData\":\"" + column.getField() + "\"");
				sb.append(",\"sWidth\":\"" + colwidth + "\"");
				sb.append(",\"bSortable\":" + column.isSortable() + "");

				sb.append(",\"bVisible\":" + !column.isHidden() + "");

				sb.append(",\"bSearchable\":" + column.isQuery() + "");
			}
			sb.append("}");
			if (i < columnList.size()) {
                sb.append(",");
            }
		}

		sb.append("]" + "});" + "});" + "</script>");
		sb.append("<table width=\"100%\"  class=\"" + style + "\" id=\"" + name + "\" toolbar=\"#" + name + "tb\"></table>");
		return sb;

	}

	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * easyui构造方法
	 * 
	 * @return
	 */
	public StringBuffer end() {
		String grid = "";
		StringBuffer sb = new StringBuffer();
		width = (width == null) ? "auto" : width;
		height = (height == null) ? "auto" : height;
		sb.append("<script type=\"text/javascript\">");     
		sb.append("$(function(){  storage=$.localStorage;if(!storage)storage=$.cookieStorage;");
		sb.append(this.getNoAuthOperButton());
		if (treegrid) {
			grid = "treegrid";
			sb.append("$(\'#" + name + "\').treegrid({");
			sb.append("idField:'id',");
			if(StringUtils.isNotEmpty(treeField)){
				sb.append("treeField:'"+treeField+"',");
			}else{
				sb.append("treeField:'text',");
			}
		} else {
			grid = "datagrid";
			sb.append("$(\'#" + name + "\').datagrid({");
			sb.append("idField: '" + idField + "',");
		}
		if (title != null) {
			sb.append("title: \'" + title + "\',");
		}
	
		
		if(autoLoadData) {
            sb.append("url:\'" + actionUrl + "&field=" + fields + "\',");
        } else {
            sb.append("url:\'',");
        }
		if(StringUtils.isNotEmpty(rowStyler)){
			sb.append("rowStyler: function(index,row){ return "+rowStyler+"(index,row);},");
		}
		if(StringUtils.isNotEmpty(extendParams)){
			sb.append(extendParams);
		}
		if (fit) {
			sb.append("fit:true,");
		} else {
			sb.append("fit:false,");
		}

		if(hasQueryColum(columnList)){
			String queryParams = "";
			queryParams += "queryParams:{";
			for (DataGridColumn col : columnList) {
				if (col.isQuery()&&col.getDefaultVal()!=null&&!col.getDefaultVal().trim().equals("")) {
					//sb.append("queryParams:{documentTitle:'woniu'},");

					if(!"group".equals(col.getQueryMode())){
						queryParams += col.getField()+":'"+col.getDefaultVal()+"',";
					}

				}
			}
			if(queryParams.indexOf(",")>-1){
				queryParams = queryParams.substring(0, queryParams.length()-1);
			}
			queryParams += "},";
			//System.out.println("queryParams===="+queryParams);
			sb.append(queryParams);
		}

		sb.append(StringUtil.replaceAll("loadMsg: \'{0}\',", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.data.loading")));
		sb.append("pageSize: " + pageSize + ",");
		sb.append("pagination:" + pagination + ",");
		sb.append("pageList:[" + pageSize * 1 + "," + pageSize * 2 + "," + pageSize * 3 + "],");
		if(StringUtils.isNotBlank(sortName)){
			sb.append("sortName:'" +sortName +"',");
		}
		sb.append("sortOrder:'" + sortOrder + "',");
		sb.append("rownumbers:true,");
		if(singleSelect==null){
			sb.append("singleSelect:" + !checkbox + ",");
		}else{
			sb.append("singleSelect:" + singleSelect + ",");
		}
		if (fitColumns) {
			sb.append("fitColumns:true,");
		} else {
			sb.append("fitColumns:false,");
		}
		sb.append("striped:true,showFooter:true,");
		sb.append("frozenColumns:[[");
		this.getField(sb,0);
		sb.append("]],");
		
		sb.append("columns:[[");
		this.getField(sb);
		sb.append("]],");
		sb.append("onLoadSuccess:function(data){$(\"#"+name+"\")."+grid+"(\"clearSelections\");");
		if(openFirstNode&&treegrid){
			sb.append(" if(data==null){");
			sb.append(" var firstNode = $(\'#" + name + "\').treegrid('getRoots')[0];");
			sb.append(" $(\'#" + name + "\').treegrid('expand',firstNode.id)}");
		}

		sb.append("if(!"+treegrid+"){");
		sb.append("if(data.total && data.rows.length==0) {");
		sb.append("var grid = $(\'#"+name+"\');");
		sb.append("var curr = grid.datagrid(\'getPager\').data(\"pagination\").options.pageNumber;");
		sb.append("grid.datagrid({pageNumber:(curr-1)});}}");

		if (StringUtil.isNotEmpty(onLoadSuccess)) {
			sb.append(onLoadSuccess + "(data);");
		}
		sb.append("},");
		if (StringUtil.isNotEmpty(onDblClick)) {
			sb.append("onDblClickRow:function(rowIndex,rowData){" + onDblClick + "(rowIndex,rowData);},");
		}
		if (treegrid) {
			sb.append("onClickRow:function(rowData){");
		}
		else {
			sb.append("onClickRow:function(rowIndex,rowData){");
		}
		/**行记录赋值*/
		sb.append("rowid=rowData.id;");
		sb.append("gridname=\'"+name+"\';");
		if (StringUtil.isNotEmpty(onClick)) {
			if (treegrid) {
				sb.append("" + onClick + "(rowData);");
			}else{
				sb.append("" + onClick + "(rowIndex,rowData);");
			}
		}
		sb.append("}");
		sb.append("});");
		this.setPager(sb, grid);

		sb.append("try{restoreheader();}catch(ex){}");
		sb.append("});");

		sb.append("function reloadTable(){");
		sb.append("try{");
		sb.append("	$(\'#\'+gridname).datagrid(\'reload\');" );
		sb.append("	$(\'#\'+gridname).treegrid(\'reload\');" );
		sb.append("}catch(ex){}");
		sb.append("}");
		sb.append("function reload" + name + "(){" + "$(\'#" + name + "\')." + grid + "(\'reload\');" + "}");
		sb.append("function get" + name + "Selected(field){return getSelected(field);}");
		sb.append("function getSelected(field){" + "var row = $(\'#\'+gridname)." + grid + "(\'getSelected\');" + "if(row!=null)" + "{" + "value= row[field];" + "}" + "else" + "{" + "value=\'\';" + "}" + "return value;" + "}");
		sb.append("function get" + name + "Selections(field){" + "var ids = [];" + "var rows = $(\'#" + name + "\')." + grid + "(\'getSelections\');" + "for(var i=0;i<rows.length;i++){" + "ids.push(rows[i][field]);" + "}" + "ids.join(\',\');" + "return ids" + "};");
		sb.append("function getSelectRows(){");
		sb.append("	return $(\'#"+name+"\').datagrid('getChecked');");
		sb.append("}");

		sb.append(" function saveHeader(){");
		sb.append(" var columnsFields =null;var easyextends=false;try{columnsFields = $('#"+name+"').datagrid('getColumns');easyextends=true;");
		sb.append("}catch(e){columnsFields =$('#"+name+"').datagrid('getColumnFields');}");
		sb.append("	var cols = storage.get( '"+name+"hiddenColumns');var init=true;	if(cols){init =false;} " +
				"var hiddencolumns = [];for(var i=0;i< columnsFields.length;i++) {if(easyextends){");
		sb.append("hiddencolumns.push({field:columnsFields[i].field,hidden:columnsFields[i].hidden});}else{");
		sb.append( " var columsDetail = $('#"+name+"').datagrid(\"getColumnOption\", columnsFields[i]); ");
		sb.append( "if(init){hiddencolumns.push({field:columsDetail.field,hidden:columsDetail.hidden,visible:(columsDetail.hidden==true?false:true)});}else{");
		sb.append("for(var j=0;j<cols.length;j++){");
		sb.append("		if(cols[j].field==columsDetail.field){");
		sb.append("					hiddencolumns.push({field:columsDetail.field,hidden:columsDetail.hidden,visible:cols[j].visible});");
		sb.append("		}");
		sb.append("}");
		sb.append("}} }");
		sb.append("storage.set( '"+name+"hiddenColumns',JSON.stringify(hiddencolumns));");
		sb.append( "}");

		sb.append(" function isShowBut(){");
		sb.append("	  var isShowSearchId = $(\'#isShowSearchId\').val();");
		sb.append("	  if(isShowSearchId == \"true\"){");
		sb.append("		  $(\"#searchColums\").hide();");
		sb.append("	  	  $(\'#isShowSearchId\').val(\"false\");");
		sb.append("	  	  $(\'#columsShow\').remove(\"src\");");
		sb.append("	  	  $(\'#columsShow\').attr(\"src\",\"plug-in/easyui/themes/default/images/accordion_expand.png\");");
		sb.append("	  } else{");
		sb.append("		  $(\"#searchColums\").show();");
		sb.append("	  	  $(\'#isShowSearchId\').val(\"true\");");
		sb.append("	  	  $(\'#columsShow\').remove(\"src\");");
		sb.append("	  	  $(\'#columsShow\').attr(\"src\",\"plug-in/easyui/themes/default/images/accordion_collapse.png\");");
		sb.append("	  }");
		sb.append("}");

		sb.append( "function restoreheader(){");
		sb.append("var cols = storage.get( '"+name+"hiddenColumns');if(!cols)return;");
		sb.append( "for(var i=0;i<cols.length;i++){");
		sb.append( "	try{");
		sb.append("if(cols.visible!=false)$('#"+name+"').datagrid((cols[i].hidden==true?'hideColumn':'showColumn'),cols[i].field);");
		sb.append( "}catch(e){");
		sb.append( "}");
		sb.append( "}");
		sb.append( "}");
		sb.append( "function resetheader(){");
		sb.append("var cols = storage.get( '"+name+"hiddenColumns');if(!cols)return;");
		sb.append( "for(var i=0;i<cols.length;i++){");
		sb.append( "	try{");
		sb.append("  $('#"+name+"').datagrid((cols.visible==false?'hideColumn':'showColumn'),cols[i].field);");
		sb.append( "}catch(e){");
		sb.append( "}");
		sb.append( "}");
		sb.append( "}");

		if (columnList.size() > 0) {
			sb.append("function " + name + "search(){");
			//update by jg_renjie at 2016/1/11 for:TASK #823 增加form实现Form表单验证
			sb.append("try { if(! $(\"#"+name+"Form\").Validform({tiptype:3}).check()){return false;} } catch (e){}");
			sb.append("if(true){");
			//update by jg_renjie at 2016/1/11 for:TASK #823 增加form实现Form表单验证
			sb.append("var queryParams=$(\'#" + name + "\').datagrid('options').queryParams;");
			sb.append("$(\'#" + name + "tb\').find('*').each(function(){queryParams[$(this).attr('name')]=$(this).val();});");
			sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "',pageNumber:1});" + "}}");
			
			//高级查询执行方法
			sb.append("function dosearch(params){");
			sb.append("var jsonparams=$.parseJSON(params);");
			sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "',queryParams:jsonparams});" + "}");

			 //searchbox框执行方法
			  searchboxFun(sb,grid);

			//生成重置按钮功能js

			//回车事件
			sb.append("function EnterPress(e){");
			sb.append("var e = e || window.event;");
			sb.append("if(e.keyCode == 13){ ");
			sb.append(name+"search();");
			sb.append("}}");

				
			sb.append("function searchReset(name){");
			sb.append(" $(\"#\"+name+\"tb\").find(\":input\").val(\"\");");
			//update by jg_renjie at 2016/1/11 for:TASK #823 增加form实现Form表单验证,此处避免reset时走验证，代码做了冗余
			//String func = name.trim() + "search();";
			//sb.append(func);
			sb.append("var queryParams=$(\'#" + name + "\').datagrid('options').queryParams;");
			sb.append("$(\'#" + name + "tb\').find('*').each(function(){queryParams[$(this).attr('name')]=$(this).val();});");
			sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "',pageNumber:1});");
			//update by jg_renjie at 2016/1/11 for:TASK #823 增加form实现Form表单验证,此处避免reset时走验证，代码做了冗余
			sb.append("}");
		}
		sb.append("</script>");
		sb.append("<table width=\"100%\"   id=\"" + name + "\" toolbar=\"#" + name + "tb\"></table>");
		sb.append("<div id=\"" + name + "tb\" style=\"padding:3px; height: auto\">");

		if(hasQueryColum(columnList)&&isShowSearch==true){
			sb.append("<input  id=\"columsShow\" type=\"image\" src=\"plug-in/easyui/themes/default/images/accordion_collapse.png\" onclick=\"isShowBut()\">");
		}

		if(hasQueryColum(columnList)){
			sb.append("<div name=\"searchColums\" id=\"searchColums\" >");

			sb.append("<input  id=\"isShowSearchId\" type=\"hidden\" value=\""+isShowSearch+"\"/>");

			//-----longjb1 增加用于高级查询的参数项 
			sb.append("<input  id=\"_sqlbuilder\" name=\"sqlbuilder\"   type=\"hidden\" />");
			//update by jg_renjie at 2016/1/11 for:TASK #823 增加form实现Form表单验证

			sb.append("<form onkeydown='if(event.keyCode==13){" + name + "search();return false;}' id='"+name+"Form'>");

			sb.append("<link rel=\"stylesheet\" href=\"plug-in/Validform/css/style.css\" type=\"text/css\">");
			sb.append("<link rel=\"stylesheet\" href=\"plug-in/Validform/css/tablefrom.css\" type=\"text/css\">");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/Validform/js/Validform_Datatype_zh-cn.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/Validform/js/datatype_zh-cn.js\"></script>");
			//update by jg_renjie at 2016/1/11 for:TASK #823

			getSearchFormInfo(sb);

			sb.append("</form></div>");
		}
		if(toolBarList.size()==0 && !hasQueryColum(columnList)){
			sb.append("<div style=\"height:0px;\" >");
		}else{
			sb.append("<div style=\"height:30px;\" class=\"datagrid-toolbar\">");
		}
		sb.append("<span style=\"float:left;\" >");
		if(toolBarList.size()>0)
		{
			for (DataGridUrl toolBar : toolBarList) {
				sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\""+toolBar.getIcon()+"\" ");
				if(StringUtil.isNotEmpty(toolBar.getOnclick()))
				{
					sb.append("onclick="+toolBar.getOnclick()+"");
				}
				else {
					sb.append("onclick=\""+toolBar.getFunname()+"(");
					if(!toolBar.getFunname().equals("doSubmit"))
					{
					sb.append("\'"+toolBar.getTitle()+"\',");
					}
					String width = toolBar.getWidth().contains("%")?"'"+toolBar.getWidth()+"'":toolBar.getWidth();
					String height = toolBar.getHeight().contains("%")?"'"+toolBar.getHeight()+"'":toolBar.getHeight();
					sb.append("\'"+toolBar.getUrl()+"\',\'"+name+"\',"+width+","+height+")\"");
				}
				sb.append(">"+toolBar.getTitle()+"</a>");
			}
		}
		sb.append("</span>");
		if("group".equals(getQueryMode()) && hasQueryColum(columnList)){//如果表单是组合查询
			sb.append("<span style=\"float:right\">");
			sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-search\" onclick=\""+  name+ StringUtil.replaceAll("search()\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.query")));
			sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\" onclick=\"searchReset('"+name+ StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.reset")) );
			if(queryBuilder){
				sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-search\" onclick=\"queryBuilder('"+ StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.querybuilder")) );
			}sb.append("</span>");
		}else if("single".equals(getQueryMode())&& hasQueryColum(columnList)){//如果表单是单查询
			sb.append("<span style=\"float:right\">");
			sb.append("<input id=\""+name+"searchbox\" class=\"easyui-searchbox\"  data-options=\"searcher:"+name+ StringUtil.replaceAll("searchbox,prompt:\'{0}\',menu:\'#", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.input.keyword")) +name+"mm\'\"></input>");
			sb.append("<div id=\""+name+"mm\" style=\"width:120px\">");
			for (DataGridColumn col : columnList) {
				if (col.isQuery()) {
					sb.append("<div data-options=\"name:\'"+col.getField().replaceAll("_","\\.")+"\',iconCls:\'icon-ok\' "+extendAttribute(col.getExtend())+" \">"+col.getTitle()+"</div>  ");
				}
			}
			sb.append("</div>");
			sb.append("</span>");
		}
		sb.append("</div>");
		if(queryBuilder){
			addQueryBuilder(sb,"easyui-linkbutton");
		}
		return sb;
	}

	/**
	 * 构建查询form当中的信息
	 * @param sb
	 */
	private void getSearchFormInfo(StringBuffer sb) {
		//如果表单是组合查询		
		if("group".equals(getQueryMode())){
			for (DataGridColumn col : columnList) {
				if (col.isQuery()) {
					sb.append("<span style=\"display:-moz-inline-box;display:inline-block;\">");
					sb.append("<span style=\"vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; \" title=\""+col.getTitle()+"\">"+col.getTitle()+"：</span>");
					if("single".equals(col.getQueryMode())){
						if(!StringUtil.isEmpty(col.getReplace())){
							sb.append("<select name=\""+col.getField().replaceAll("_","\\.")+"\" WIDTH=\"100\" style=\"width: 104px\"> ");
							sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
							String[] test = col.getReplace().split(",");
							String text = "";
							String value = "";
							
							
							
							for (String string : test) {
								String lang_key = string.split("_")[0];
								text = MutiLangUtil.getMutiLangInstance().getLang(lang_key);
								value =string.split("_")[1];

								if(col.getDefaultVal()!=null&&col.getDefaultVal().trim().equals(value)){
									sb.append("<option value =\""+value+"\" selected=\"selected\">"+text+"</option>");
								}else{
									sb.append("<option value =\""+value+"\" >"+text+"</option>");
								}

								
							}
							sb.append("</select>");
						}else if(!StringUtil.isEmpty(col.getDictionary())){
							if(col.getDictionary().contains(",")&&col.isPopup()){
								String[] dic = col.getDictionary().split(",");
								String sql = "select " + dic[1] + " as field," + dic[2]
										+ " as text from " + dic[0];
								//System.out.println(dic[0]+"--"+dic[1]+"--"+dic[2]);
							//	<input type="text" name="order_code"  style="width: 100px"  class="searchbox-inputtext" value="" onClick="inputClick(this,'account','user_msg');" />

								if(col.getDefaultVal()!=null&&!col.getDefaultVal().trim().equals("")){
									sb.append("<input type=\"text\" name=\""+col.getField().replaceAll("_","\\.")+"\" style=\"width: 120px\" class=\"searchbox-inputtext\" value=\"\" onClick=\"inputClick(this,'"+dic[1]+"','"+dic[0]+"');\" value=\""+col.getDefaultVal()+"\"/> ");
								}else{
									sb.append("<input type=\"text\" name=\""+col.getField().replaceAll("_","\\.")+"\" style=\"width: 120px\" class=\"searchbox-inputtext\" value=\"\" onClick=\"inputClick(this,'"+dic[1]+"','"+dic[0]+"');\" /> ");
								}

								
							}else if(col.getDictionary().contains(",")&&(!col.isPopup())){
								String[] dic = col.getDictionary().split(",");
								String sql = "select " + dic[1] + " as field," + dic[2]
										+ " as text from " + dic[0];
								systemService = ApplicationContextUtil.getContext().getBean(
										SystemService.class);
								List<Map<String, Object>> list = systemService.findForJdbc(sql);
								sb.append("<select name=\""+col.getField().replaceAll("_","\\.")+"\" WIDTH=\"100\" style=\"width: 104px\"> ");
								sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
								for (Map<String, Object> map : list){

									if(col.getDefaultVal()!=null&&col.getDefaultVal().trim().equals(map.get("field"))){
										sb.append(" <option value=\""+map.get("field")+"\" selected=\"selected\">");
									}else{
										sb.append(" <option value=\""+map.get("field")+"\">");
									}


									sb.append(map.get("text"));
									sb.append(" </option>");
								}
								sb.append("</select>");
							}else{
								Map<String, List<TSType>> typedatas = ResourceUtil.allTypes;
								List<TSType> types = typedatas.get(col.getDictionary().toLowerCase());
								sb.append("<select name=\""+col.getField().replaceAll("_","\\.")+"\" WIDTH=\"100\" style=\"width: 104px\"> ");
								sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
								if (types != null) {
									for (TSType type : types) {

										if(col.getDefaultVal()!=null&&col.getDefaultVal().trim().equals(type.getTypecode())){
											sb.append(" <option value=\""
													+ type.getTypecode()
													+ "\" selected=\"selected\">");
										}else{
											sb.append(" <option value=\""
													+ type.getTypecode()
													+ "\">");
										}

									
										sb.append(MutiLangUtil.getMutiLangInstance().getLang(type.getTypename()));
										sb.append(" </option>");
									}
								}
								sb.append("</select>");
							}
						}else if(col.isAutocomplete()){
							sb.append(getAutoSpan(col.getField().replaceAll("_","\\."),extendAttribute(col.getExtend())));
						}else{

							sb.append("<input onkeypress=\"EnterPress(event)\" onkeydown=\"EnterPress()\"  type=\"text\" name=\""+col.getField().replaceAll("_","\\.")+"\"  "+extendAttribute(col.getExtend())+" ");
							if(this.DATE_FORMATTER.equals(col.getFormatter())){
								sb.append(" style=\"width: 160px\" class=\"Wdate\" onClick=\"WdatePicker()\" ");
							}else if(this.DATETIME_FORMATTER.equals(col.getFormatter())){
								sb.append(" style=\"width: 160px\" class=\"Wdate\" onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" ");
							}else{
								sb.append(" style=\"width: 120px\" class=\"inuptxt\" ");
							}
							if(oConvertUtils.isNotEmpty(col.getDefaultVal())){
								sb.append(" value=\""+col.getDefaultVal()+"\" ");
							}
							sb.append(" />");

							
							//sb.append("<input onkeypress=\"EnterPress(event)\" onkeydown=\"EnterPress()\"  type=\"text\" name=\""+col.getField().replaceAll("_","\\.")+"\"  "+extendAttribute(col.getExtend())+"  class=\"inuptxt\" style=\"width: 100px\" value="+col.getDefaultVal()==null?"":"\""+col.getDefaultVal()+"\""+"/>");
							
						}
					}else if("group".equals(col.getQueryMode())){

						if(this.DATE_FORMATTER.equals(col.getFormatter())){
							sb.append("<input type=\"text\" name=\""+col.getField()+"_begin\"  style=\"width: 94px\" "+extendAttribute(col.getExtend())+" class=\"Wdate\" onClick=\"WdatePicker()\"/>");
							sb.append("<span style=\"display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;\">~</span>");
							sb.append("<input type=\"text\" name=\""+col.getField()+"_end\"  style=\"width: 94px\" "+extendAttribute(col.getExtend())+" class=\"Wdate\" onClick=\"WdatePicker()\"/>");
						}else if(this.DATETIME_FORMATTER.equals(col.getFormatter())){
							sb.append("<input type=\"text\" name=\""+col.getField()+"_begin1\"  style=\"width: 140px\" "+extendAttribute(col.getExtend())+" class=\"Wdate\" onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\"/>");
							sb.append("<span style=\"display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;\">~</span>");
							sb.append("<input type=\"text\" name=\""+col.getField()+"_end2\"  style=\"width: 140px\" "+extendAttribute(col.getExtend())+" class=\"Wdate\" onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\"/>");
						}else{
							sb.append("<input type=\"text\" name=\""+col.getField()+"_begin\"  style=\"width: 94px\" "+extendAttribute(col.getExtend())+" class=\"inuptxt\"/>");
							sb.append("<span style=\"display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;\">~</span>");
							sb.append("<input type=\"text\" name=\""+col.getField()+"_end\"  style=\"width: 94px\" "+extendAttribute(col.getExtend())+" class=\"inuptxt\"/>");
						}

					}
					sb.append("</span>");
				}
			}
		}
	}


	/**
	 * 生成扩展属性
	 * @param field
	 * @return
	 */
	private String extendAttribute(String field) {
		if(StringUtil.isEmpty(field)){
			return "";
		}
		field = dealSyscode(field,1);
		StringBuilder re = new StringBuilder();
		try{
			JSONObject obj = JSONObject.fromObject(field);
			Iterator it = obj.keys();
			while(it.hasNext()){
				String key = String.valueOf(it.next());
				JSONObject nextObj = null;
				try {
					 nextObj =((JSONObject)obj.get(key));
					 Iterator itvalue =nextObj.keys();
						re.append(key+"="+"\"");
						if(nextObj.size()<=1){
							String onlykey = String.valueOf(itvalue.next());
							if("value".equals(onlykey)){
								re.append(nextObj.get(onlykey)+"");
							}else{
								re.append(onlykey+":"+nextObj.get(onlykey)+"");
							}
						}else{
							while(itvalue.hasNext()){
								String multkey = String.valueOf(itvalue.next());
								String multvalue = nextObj.getString(multkey);
								re.append(multkey+":"+multvalue+";");
							}
							re.deleteCharAt(re.length()-1);
						}
						re.append("\" ");
				} catch (Exception e) {
					re.append(key+"="+"\"");
					re.append(obj.get(key)+"\"");
					re.append("\" ");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return dealSyscode(re.toString(), 2);
	}
	
	
	/**
	 * 生成扩展属性
	 * @param field
	 * @return
	 */
	private String extendAttributeOld(String field) {
		StringBuffer sb = new StringBuffer();
		//增加扩展属性
		if (!StringUtils.isBlank(field)) {
			Gson gson = new Gson();
			Map<String, String> mp = gson.fromJson(field, Map.class);
			for(Map.Entry<String, String> entry: mp.entrySet()) { 

				sb.append(entry.getKey()+"=" + gson.toJson(entry.getValue()) + "\"");
				} 
		}
		return sb.toString();
	}
	
	/**
	 * 处理否含有json转换中的保留字
	 * @param field
	 * @param flag 1:转换 2:还原
	 * @return
	 */
	private String dealSyscode(String field,int flag) {
		String change = field;
		Iterator it = syscode.keySet().iterator();
		while(it.hasNext()){
			String key = String.valueOf(it.next());
			String value = String.valueOf(syscode.get(key));
			if(flag==1){
				change = field.replaceAll(key, value);
			}else if(flag==2){
				change = field.replaceAll(value, key);
			}
		}
		return change;
	}
	/**
	 * 判断是否存在查询字段
	 * @return hasQuery true表示有查询字段,false表示没有
	 */
	protected boolean hasQueryColum(List<DataGridColumn> columnList ) {
		boolean hasQuery = false;
		for (DataGridColumn col : columnList) {
			if(col.isQuery()){
				hasQuery =  true;
			}
		}
		return hasQuery;
	}
	/**
	 * 拼装操作地址
	 * 
	 * @param sb
	 */
	protected void getOptUrl(StringBuffer sb) {
		//注：操作列表会带入合计列中去，故加此判断
		sb.append("if(!rec.id){return '';}");
		List<DataGridUrl> list = urlList;
		sb.append("var href='';");
		for (DataGridUrl dataGridUrl : list) {
			String url = dataGridUrl.getUrl();
			MessageFormat formatter = new MessageFormat("");
			if (dataGridUrl.getValue() != null) {
				String[] testvalue = dataGridUrl.getValue().split(",");
				List value = new ArrayList<Object>();
				for (String string : testvalue) {
					value.add("\"+rec." + string + " +\"");
				}
				url = formatter.format(url, value.toArray());
			}
			if (url != null && dataGridUrl.getValue() == null) {

				url = formatUrl(url);
			}
			String exp = dataGridUrl.getExp();// 判断显示表达式
			if (StringUtil.isNotEmpty(exp)) {
				String[] ShowbyFields = exp.split("&&");
				for (String ShowbyField : ShowbyFields) {
					int beginIndex = ShowbyField.indexOf("#");
					int endIndex = ShowbyField.lastIndexOf("#");
					String exptype = ShowbyField.substring(beginIndex + 1, endIndex);// 表达式类型
					String field = ShowbyField.substring(0, beginIndex);// 判断显示依据字段
					String[] values = ShowbyField.substring(endIndex + 1, ShowbyField.length()).split(",");// 传入字段值
					String value = "";
					for (int i = 0; i < values.length; i++) {
						value += "'" + "" + values[i] + "" + "'";
						if (i < values.length - 1) {
							value += ",";
						}
					}
					if ("eq".equals(exptype)) {
						sb.append("if($.inArray(rec." + field + ",[" + value + "])>=0){");
					}
					if ("ne".equals(exptype)) {
						sb.append("if($.inArray(rec." + field + ",[" + value + "])<0){");
					}
					if ("empty".equals(exptype) && value.equals("'true'")) {
						sb.append("if(rec." + field + "==''){");
					}
					if ("empty".equals(exptype) && value.equals("'false'")) {
						sb.append("if(rec." + field + "!=''){");
					}
				}
			}

			StringBuffer style = new StringBuffer();
			if (!StringUtil.isEmpty(dataGridUrl.getUrlStyle())) {
				style.append(" style=\'");
				style.append(dataGridUrl.getUrlStyle());
				style.append("\' ");
			}

			StringBuffer urlclass = new StringBuffer();
			if(!StringUtil.isEmpty(dataGridUrl.getUrlclass())){
				urlclass.append(" class=\'");
				urlclass.append(dataGridUrl.getUrlclass());
				urlclass.append("\'");
			}
			StringBuffer urlfont = new StringBuffer();
			if(!StringUtil.isEmpty(dataGridUrl.getUrlfont())){
				urlfont.append(" <i class=\' fa ");
				urlfont.append(dataGridUrl.getUrlfont());
				urlfont.append("\'></i>");			
			}

			if (OptTypeDirection.Confirm.equals(dataGridUrl.getType())) {

				if(!StringUtil.isEmpty(dataGridUrl.getUrlclass())){
					sb.append("href+=\"<a href=\'#\'  "+urlclass.toString()+"  onclick=confirm(\'" + url + "\',\'" + dataGridUrl.getMessage() + "\',\'"+name+"\')" + style.toString() + "> "+urlfont.toString()+" \";");
				}else{
					sb.append("href+=\"[<a href=\'#\' onclick=confirm(\'" + url + "\',\'" + dataGridUrl.getMessage() + "\',\'"+name+"\')" + style.toString() + "> \";");
				}

			}
			if (OptTypeDirection.Del.equals(dataGridUrl.getType())) {
				if(!StringUtil.isEmpty(dataGridUrl.getUrlclass())){//倘若urlclass不为空，则去掉链接前面的"[";
					sb.append("href+=\"<a href=\'#\'  "+urlclass.toString()+"  onclick=delObj(\'" + url + "\',\'"+name+"\')" + style.toString() + "> "+urlfont.toString()+" \";");
				}else{
					sb.append("href+=\"[<a href=\'#\'  onclick=delObj(\'" + url + "\',\'"+name+"\')" + style.toString() + ">\";");
				}
				
			}
			if (OptTypeDirection.Fun.equals(dataGridUrl.getType())) {
				String name = TagUtil.getFunction(dataGridUrl.getFunname());
				String parmars = TagUtil.getFunParams(dataGridUrl.getFunname());
				if(!StringUtil.isEmpty(dataGridUrl.getUrlclass())){//倘若urlclass不为空，则去掉链接前面的"[";
					sb.append("href+=\"<a href=\'#\'  "+urlclass.toString()+"  onclick=" + name + "(" + parmars + ")" + style.toString() + "> "+urlfont.toString()+"\";");
				}else{
					sb.append("href+=\"[<a href=\'#\'   onclick=" + name + "(" + parmars + ")" + style.toString() + ">\";");
				}
				
			}
			if (OptTypeDirection.OpenWin.equals(dataGridUrl.getType())) {

				if(!StringUtil.isEmpty(dataGridUrl.getUrlclass())){//倘若urlclass不为空，则去掉链接前面的"[";
					sb.append("href+=\"<a href=\'#\' "+urlclass.toString()+" onclick=openwindow('" + dataGridUrl.getTitle() + "','" + url + "','"+name+"'," + dataGridUrl.getWidth() + "," + dataGridUrl.getHeight() + ")" + style.toString() + ">"+urlfont.toString()+"\";");
				}else{
					sb.append("href+=\"[<a href=\'#\' onclick=openwindow('" + dataGridUrl.getTitle() + "','" + url + "','"+name+"'," + dataGridUrl.getWidth() + "," + dataGridUrl.getHeight() + ")" + style.toString() + ">\";");
				}

			}															//update-end--Author:liuht  Date:20130228 for：弹出窗口设置参数不生效
			if (OptTypeDirection.Deff.equals(dataGridUrl.getType())) {

				if(!StringUtil.isEmpty(dataGridUrl.getUrlclass())){
					sb.append("href+=\"<a href=\'" + url + "' "+urlclass.toString()+" title=\'"+dataGridUrl.getTitle()+"\'" + style.toString() + ">"+urlfont.toString()+"\";");
				}else{
					sb.append("href+=\"[<a href=\'" + url + "' title=\'"+dataGridUrl.getTitle()+"\'" + style.toString() + ">\";");
				}

			}

			if (OptTypeDirection.OpenTab.equals(dataGridUrl.getType())) {
				sb.append("href+=\"[<a href=\'#\' onclick=addOneTab('" + dataGridUrl.getTitle() + "','" + url  + "')>\";");
			}
			if(!StringUtil.isEmpty(dataGridUrl.getUrlclass())){//倘若urlclass不为空，则去掉链接后面的"]";
				sb.append("href+=\"" + dataGridUrl.getTitle() + "</a>&nbsp;\";");
			}else{
				sb.append("href+=\"" + dataGridUrl.getTitle() + "</a>]\";");
			}
			

			if (StringUtil.isNotEmpty(exp)) {
				for (int i = 0; i < exp.split("&&").length; i++) {
					sb.append("}");
				}

			}
		}
		sb.append("return href;");
	}

	/**
	 * 列自定义函数
	 * 
	 * @param sb
	 * @param column
	 */
	protected void getFun(StringBuffer sb, DataGridColumn column) {
		String url = column.getUrl();
		url = formatUrl(url);
		sb.append("var href=\"<a style=\'color:red\' href=\'#\' onclick=" + column.getFunname() + "('" + column.getTitle() + "','" + url + "')>\";");
		sb.append("return href+value+\'</a>\';");

	}

	/**
	 * 格式化URL
	 * 
	 * @return
	 */
	protected String formatUrl(String url) {
		MessageFormat formatter = new MessageFormat("");
		String parurlvalue = "";
		if (url.indexOf("&") >= 0) {
			String beforeurl = url.substring(0, url.indexOf("&"));// 截取请求地址
			String parurl = url.substring(url.indexOf("&") + 1, url.length());// 截取参数
			String[] pras = parurl.split("&");
			List value = new ArrayList<Object>();
			int j = 0;
			for (int i = 0; i < pras.length; i++) {
				if (pras[i].indexOf("{") >= 0 || pras[i].indexOf("#") >= 0) {
					String field = pras[i].substring(pras[i].indexOf("{") + 1, pras[i].lastIndexOf("}"));
					parurlvalue += "&" + pras[i].replace("{" + field + "}", "{" + j + "}");
					value.add("\"+rec." + field + " +\"");
					j++;
				} else {
					parurlvalue += "&" + pras[i];
				}
			}
			url = formatter.format(beforeurl + parurlvalue, value.toArray());
		}
		return url;

	}
	/**
	 * 拼接字段  普通列
	 * 
	 * @param sb
	 * 
	 */
	 protected void getField(StringBuffer sb){
		 getField(  sb,1);
	 }
	/**
	 * 拼接字段
	 * 
	 * @param sb
	 * @frozen 0 冰冻列    1 普通列
	 */
	protected void getField(StringBuffer sb,int frozen) {
		// 复选框
		if (checkbox&&frozen==0) {
			sb.append("{field:\'ck\',checkbox:\'true\'},");
		}
		int i = 0;
		for (DataGridColumn column : columnList) {
			i++;
			if((column.isFrozenColumn()&&frozen==0)||(!column.isFrozenColumn()&&frozen==1)){
			String field;
			if (treegrid) {

				field = column.getTreefield();
				if(StringUtils.isEmpty(field)){
					field = column.getField();
				}

			} else {
				field = column.getField();
			}
			sb.append("{field:\'" + field + "\',title:\'" + column.getTitle() + "\'");
			if(column.getWidth() != null){
				sb.append(",width:"+column.getWidth());
			}
			if (column.getAlign()!=null){
				sb.append(",align:\'" + column.getAlign() + "\'");
			}
			if(StringUtils.isNotEmpty(column.getExtendParams())){

				if(column.getExtendParams().indexOf("editor:'combobox'")>-1){//倘若扩展参数中包含editor:'combobox'
					StringBuffer comboboxStr =new StringBuffer();//声明一个替换扩展参数中的editor:'combobox'的变量
					if(!StringUtil.isEmpty(column.getDictionary())){//根据数据字典生成editor:'combobox'
						if(column.getDictionary().contains(",")){
							String[] dic = column.getDictionary().split(",");
							String sql = "select " + dic[1] + " as field," + dic[2]+ " as text from " + dic[0];
							systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
							List<Map<String, Object>> list = systemService.findForJdbc(sql);
							
							comboboxStr.append("editor:{type:'combobox',options:{valueField:'typecode',textField:'typename',data:[");
							for (Map<String, Object> map : list){
								comboboxStr.append("{'typecode':'"+map.get("field")+"','typename':'"+map.get("text")+"'},");
							}
							comboboxStr.append("],required:true}}");
							//再增加formatter参数
							
							comboboxStr.append(",formatter:function(value,row){");
							for (Map<String, Object> map : list){
								comboboxStr.append("if(value =='"+map.get("field")+"'){");
								comboboxStr.append("return '"+map.get("text")+"';");
								comboboxStr.append("}");
							}
							comboboxStr.append("return row."+map.get("field")+";");
							comboboxStr.append("}");
						}else{
							Map<String, List<TSType>> typedatas = ResourceUtil.allTypes;
							List<TSType> types = typedatas.get(column.getDictionary().toLowerCase());
							if (types != null) {
								comboboxStr.append("editor:{type:'combobox',options:{valueField:'typecode',textField:'typename',data:[");
								for (TSType type : types) {
									comboboxStr.append("{'typecode':'"+type.getTypecode()+"','typename':'"+MutiLangUtil.getMutiLangInstance().getLang(type.getTypename())+"'},");
								}
								comboboxStr.append("],required:true}}");
								//再增加formatter参数
								comboboxStr.append(",formatter:function(value,row){");
								for (TSType type : types) {
									comboboxStr.append("if(value =='"+type.getTypecode()+"'){");
									comboboxStr.append("return '"+MutiLangUtil.getMutiLangInstance().getLang(type.getTypename())+"';");
									comboboxStr.append("}");
								}
								comboboxStr.append("return row."+field+";");
								comboboxStr.append("}");
							}
						}
				   }
					column.setExtendParams(column.getExtendParams().replaceAll("editor:'combobox'", comboboxStr.toString()));//替换扩展参数
					//System.out.println("column.getExtendParams()=="+column.getExtendParams());
				}

				//sb.append(","+column.getExtendParams().substring(0,column.getExtendParams().length()-1));
				if(column.getExtendParams().endsWith(",") || column.getExtendParams().endsWith("''")){
					sb.append(","+column.getExtendParams().substring(0,column.getExtendParams().length()-1));
				}else{
					sb.append(","+column.getExtendParams());
				}

				
			}

			// 隐藏字段
			if (column.isHidden()) {
				sb.append(",hidden:true");
			}

			if (!treegrid) {
				// 字段排序
				if ((column.isSortable()) && (field.indexOf("_") <= 0 && field != "opt")) {
					sb.append(",sortable:" + column.isSortable() + "");
				}
			}
			if(column.getFormatterjs()!=null){

				if(StringUtils.isNotEmpty(column.getExtendParams())&&column.getExtendParams().indexOf("editor:'combobox'")>-1){//倘若扩展参数中包含editor:'combobox'
						//不再重复增加formatter参数，
				}else{
					sb.append(",formatter:function(value,rec,index){");
					sb.append(" return "+column.getFormatterjs()+"(value,rec,index);}");
				}

			}else {
				// 显示图片
				if (column.isImage()) {
					if (column.getImageSize() != null) {// 自定义显示图片
	                    String[] tld = column.getImageSize().split(",");
	                    sb.append(",formatter:function(value,rec,index){");
	                    sb.append(" return '<img width=\"" + tld[0]
	                                    + "\" height=\"" + tld[1]
	                                    + "\" border=\"0\" src=\"'+value+'\"/>';}");
	                    tld = null;
					}else{
						sb.append(",formatter:function(value,rec,index){");
						sb.append(" return '<img border=\"0\" src=\"'+value+'\"/>';}");
					}
				} else if(column.getDownloadName() != null){
	            	sb.append(",formatter:function(value,rec,index){");
	                sb.append(" return '<a target=\"_blank\" href=\"'+value+'\">"
	                		+ column.getDownloadName() + "</a>';}");
	            }else if (column.getUrl() != null) { // 自定义链接
					sb.append(",formatter:function(value,rec,index){");
					this.getFun(sb, column);
					sb.append("}");
				}else if (column.getField().equals("opt")) {// 加入操作
					sb.append(",formatter:function(value,rec,index){");
					// sb.append("return \"");
					this.getOptUrl(sb);
					sb.append("}");
				}else if(column.getFormatter()!=null)
				{
					sb.append(",formatter:function(value,rec,index){");
					sb.append(" return new Date().format('"+column.getFormatter()+"',value);}");
				}

				else if(column.getShowLen()!=null){ //设置了显示多少长度的
					sb.append(",formatter:function(value,rec,index){");
					sb.append(" if(value==undefined) {return ''} ");
					sb.append(" if(value.length<=");sb.append(column.getShowLen());sb.append(") {return value}");
					sb.append(" else{ return '<a title= '+value+'>'+ value.substring(0,");sb.append(column.getShowLen());sb.append(")+'...';}}");
				}
				else if (columnValueList.size() > 0 && !column.getField().equals("opt")) {// 值替換
					String testString = "";
					for (ColumnValue columnValue : columnValueList) {
						if (columnValue.getName().equals(column.getField())) {
							String[] value = columnValue.getValue().split(",");
							String[] text = columnValue.getText().split(",");
							sb.append(",formatter:function(value,rec,index){");
							sb.append("if(value==undefined) return '';");
							sb.append("var valArray = value.split(',');");
							sb.append("if(valArray.length > 1){");
							sb.append("var checkboxValue = '';");
							sb.append("for(var k=0; k<valArray.length; k++){");
							for(int j = 0; j < value.length; j++){
								sb.append("if(valArray[k] == '" + value[j] + "'){ checkboxValue = checkboxValue + \'" + text[j] + "\' + ',';}");
							}
							sb.append("}");
							sb.append("return checkboxValue.substring(0,checkboxValue.length-1);");
							sb.append("}");
							sb.append("else{");
							for (int j = 0; j < value.length; j++) {
								testString += "if(value=='" + value[j] + "'){return \'" + text[j] + "\';}";
							}
							sb.append(testString);
							sb.append("else{return value;}");
							sb.append("}");
							sb.append("}");
						}
					}
				}
			}
			// 背景设置
			if (columnStyleList.size() > 0 && !column.getField().equals("opt")) {
				String testString = "";
				for (ColumnValue columnValue : columnStyleList) {
					if (columnValue.getName().equals(column.getField())) {
						String[] value = columnValue.getValue().split(",");
						String[] text = columnValue.getText().split(",");
						sb.append(",styler:function(value,rec,index){");
						if((value.length == 0||StringUtils.isEmpty(value[0]))&&text.length==1){
							if(text[0].indexOf("(")>-1){
								testString = " return \'" + text[0].replace("(", "(value,rec,index") + "\'";
							}else{
								testString = " return \'" + text[0] + "\'";
							}
						}else{
							for (int j = 0; j < value.length; j++) {
								testString += "if(value=='" + value[j] + "'){return \'" + text[j] + "\'}";
							}
						}
						sb.append(testString);
						sb.append("}");
					}
				}
				
			}
			sb.append("}");
			// 去除末尾,
			if (i < columnList.size()) {
				sb.append(",");
			}
		}
		}
	}
	/**
	 * 设置分页条信息
	 * 
	 * @param sb
	 */
	protected void setPager(StringBuffer sb, String grid) {
		sb.append("$(\'#" + name + "\')." + grid + "(\'getPager\').pagination({");
		sb.append("beforePageText:\'\'," + "afterPageText:\'/{pages}\',");
		if (showText) {
			sb.append("displayMsg:\'{from}-{to}" + MutiLangUtil.getMutiLangInstance().getLang("common.total") + " {total}" + MutiLangUtil.getMutiLangInstance().getLang("common.item") + "\',");
		} else {
			sb.append("displayMsg:\'\',");
		}
		if (showPageList == true) {
			sb.append("showPageList:true,");
		} else {
			sb.append("showPageList:false,");
		}
		sb.append("showRefresh:" + showRefresh + "");
		sb.append("});");// end getPager
		sb.append("$(\'#" + name + "\')." + grid + "(\'getPager\').pagination({");
		sb.append("onBeforeRefresh:function(pageNumber, pageSize){ $(this).pagination(\'loading\');$(this).pagination(\'loaded\'); }");
		sb.append("});");
	}
	//列表查询框函数
	protected void searchboxFun(StringBuffer sb,String grid)
	{
		sb.append("function "+name+"searchbox(value,name){");
		sb.append("var queryParams=$(\'#" + name + "\').datagrid('options').queryParams;");
		sb.append("queryParams[name]=value;queryParams.searchfield=name;$(\'#" + name + "\')." + grid + "(\'reload\');}");
		sb.append("$(\'#"+name+"searchbox\').searchbox({");
		sb.append("searcher:function(value,name){");
		sb.append(""+name+"searchbox(value,name);");
		sb.append("},");
		sb.append("menu:\'#"+name+"mm\',");
		sb.append(StringUtil.replaceAll("prompt:\'{0}\'", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.input.query.keyword")));
		sb.append("});");
	}
  
	public String getNoAuthOperButton(){
		StringBuffer sb = new StringBuffer();
		if(ResourceUtil.getSessionUserName().getUserName().equals("admin")|| !Globals.BUTTON_AUTHORITY_CHECK){
		}else{
			Set<String> operationCodes = (Set<String>) super.pageContext.getRequest().getAttribute(Globals.OPERATIONCODES);
			if (null!=operationCodes) {
				for (String MyoperationCode : operationCodes) {
					if (oConvertUtils.isEmpty(MyoperationCode)) {
                        break;
                    }
					systemService = ApplicationContextUtil.getContext().getBean(
								SystemService.class);
					TSOperation operation = systemService.getEntity(TSOperation.class, MyoperationCode);
					if (operation.getOperationcode().startsWith(".") || operation.getOperationcode().startsWith("#")){
						if (operation.getOperationType().intValue()==Globals.OPERATION_TYPE_HIDE){
							//out.append("$(\""+name+"\").find(\"#"+operation.getOperationcode().replaceAll(" ", "")+"\").hide();");
							sb.append("$(\""+operation.getOperationcode().replaceAll(" ", "")+"\").hide();");
						}else {
							//out.append("$(\""+name+"\").find(\"#"+operation.getOperationcode().replaceAll(" ", "")+"\").find(\":input\").attr(\"disabled\",\"disabled\");");
							sb.append("$(\""+operation.getOperationcode().replaceAll(" ", "")+"\").attr(\"disabled\",\"disabled\");");
							sb.append("$(\""+operation.getOperationcode().replaceAll(" ", "")+"\").find(\":input\").attr(\"disabled\",\"disabled\");");
						}
					}
				}
			}
			
		}
		//org.jeecgframework.core.util.LogUtil.info("----getNoAuthOperButton-------"+sb.toString());
		return sb.toString(); 
	}
	
	/**
	 * 描述：组装菜单按钮操作权限
	 * dateGridUrl：url
	 * operationCode：操作码
	 * optList： 操作列表
	 * @version 1.0
	 */
	private void installOperationCode(DataGridUrl dataGridUrl,String operationCode,List optList){
		if(ResourceUtil.getSessionUserName().getUserName().equals("admin")|| !Globals.BUTTON_AUTHORITY_CHECK){
			optList.add(dataGridUrl);
		}else if(!oConvertUtils.isEmpty(operationCode)){
			Set<String> operationCodes = (Set<String>) super.pageContext.getRequest().getAttribute(Globals.OPERATIONCODES);
			if (null!=operationCodes) {
				List<String> operationCodesStr = new ArrayList<String>();
				for (String MyoperationCode : operationCodes) {
					if (oConvertUtils.isEmpty(MyoperationCode)) {
                        break;
                    }
					systemService = ApplicationContextUtil.getContext().getBean(
								SystemService.class);
					TSOperation operation = systemService.getEntity(TSOperation.class, MyoperationCode);
					operationCodesStr.add(operation.getOperationcode());
				}
				if (!operationCodesStr.contains(operationCode)){
					optList.add(dataGridUrl);
				}
			}
		}else {
			optList.add(dataGridUrl);
		}
	}
	
	/**
	 * 获取自动补全的panel
	 * @param filed
	 * @author JueYue
	 * @return
	 */
	private String getAutoSpan(String filed,String extend){
		String id = filed.replaceAll("\\.","_");
		StringBuffer nsb = new StringBuffer();
		nsb.append("<script type=\"text/javascript\">");
		nsb.append("$(document).ready(function() {") 
		.append("$(\"#"+getEntityName()+"_"+id+"\").autocomplete(\"commonController.do?getAutoList\",{")
		.append("max: 5,minChars: 2,width: 200,scrollHeight: 100,matchContains: true,autoFill: false,extraParams:{")
        .append("featureClass : \"P\",style : \"full\",	maxRows : 10,labelField : \""+filed+"\",valueField : \""+filed+"\",")
		.append("searchField : \""+filed+"\",entityName : \""+getEntityName()+"\",trem: function(){return $(\"#"+getEntityName()+"_"+id+"\").val();}}");
		nsb.append(",parse:function(data){return jeecgAutoParse.call(this,data);}");
		nsb.append(",formatItem:function(row, i, max){return row['"+filed+"'];} ");
		nsb.append("}).result(function (event, row, formatted) {");
		nsb.append("$(\"#"+getEntityName()+"_"+id+"\").val(row['"+filed+"']);}); });")
        .append("</script>")
        .append("<input class=\"inuptxt\" type=\"text\" id=\""+getEntityName()+"_"+id+"\" name=\""+filed+"\"  "+extend+ StringUtil.replace(" nullmsg=\"\" errormsg=\"{0}\"/>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("input.error")));
		return nsb.toString();
	}
	/**
	 * 获取实体类名称,没有这根据规则设置
	 * @return
	 */
	private String getEntityName() {
		if(StringUtils.isEmpty(entityName)){
			entityName = actionUrl.substring(0,actionUrl.indexOf("Controller"));
			entityName = (entityName.charAt(0)+"").toUpperCase()+entityName.substring(1)+"Entity";
		}
		return entityName;
	}
	
	public boolean isFitColumns() {
		return fitColumns;
	}

	public void setFitColumns(boolean fitColumns) {
		this.fitColumns = fitColumns;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}

	public boolean isAutoLoadData() {
		return autoLoadData;
	}

	public void setAutoLoadData(boolean autoLoadData) {
		this.autoLoadData = autoLoadData;
	}

	public void setOpenFirstNode(boolean openFirstNode) {
		this.openFirstNode = openFirstNode;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setRowStyler(String rowStyler) {
		this.rowStyler = rowStyler;
	}

	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}

	public void setLangArg(String langArg) {
		this.langArg = langArg;
	}

	public StringBuffer aceStyleTable() {
		String grid = "";
		StringBuffer sb = new StringBuffer();
		width = (width == null) ? "auto" : width;
		height = (height == null) ? "auto" : height;
//		sb.append("<link rel=\"stylesheet\" href=\"plug-in/easyui/themes/metro/main.css\" />");
		sb.append("<script type=\"text/javascript\">");
		sb.append("$(function(){  storage=$.localStorage;if(!storage)storage=$.cookieStorage;");
		sb.append(this.getNoAuthOperButton());
		if (treegrid) {
			grid = "treegrid";
			sb.append("$(\'#" + name + "\').treegrid({");
			sb.append("idField:'id',");
			sb.append("treeField:'text',");
		} else {
			grid = "datagrid";
			sb.append("$(\'#" + name + "\').datagrid({");
			sb.append("idField: '" + idField + "',");
		}
		if (title != null) {
			sb.append("title: \'" + title + "\',");
		}
		
		if(autoLoadData) {
            sb.append("url:\'" + actionUrl + "&field=" + fields + "\',");
        } else {
            sb.append("url:\'',");
        }
		if(StringUtils.isNotEmpty(rowStyler)){
			sb.append("rowStyler: function(index,row){ return "+rowStyler+"(index,row);},");
		}
		if(StringUtils.isNotEmpty(extendParams)){
			sb.append(extendParams);
		}
		if (fit) {
			sb.append("fit:true,");
		} else {
			sb.append("fit:false,");
		}
		sb.append(StringUtil.replaceAll("loadMsg: \'{0}\',", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.data.loading")));
		sb.append("striped:true,pageSize: " + pageSize + ",");
		sb.append("pagination:" + pagination + ",");
		sb.append("pageList:[" + pageSize * 1 + "," + pageSize * 2 + "," + pageSize * 3 + "],");
		if(StringUtils.isNotBlank(sortName)){
			sb.append("sortName:'" +sortName +"',");
		}
		sb.append("sortOrder:'" + sortOrder + "',");
		sb.append("rownumbers:true,");
		if(singleSelect==null){
			sb.append("singleSelect:" + !checkbox + ",");
		}else{
			sb.append("singleSelect:" + singleSelect + ",");
		}
		if (fitColumns) {
			sb.append("fitColumns:true,");
		} else {
			sb.append("fitColumns:false,");
		}
		sb.append("showFooter:true,");
		sb.append("frozenColumns:[[");
		this.getField(sb,0);
		sb.append("]],");
		
		sb.append("columns:[[");
		this.getField(sb);
		sb.append("]],");
		sb.append("onLoadSuccess:function(data){$(\"#"+name+"\")."+grid+"(\"clearSelections\");");
		if(openFirstNode&&treegrid){
			sb.append(" if(data==null){");
			sb.append(" var firstNode = $(\'#" + name + "\').treegrid('getRoots')[0];");
			sb.append(" $(\'#" + name + "\').treegrid('expand',firstNode.id)}");
		}
		if (StringUtil.isNotEmpty(onLoadSuccess)) {
			sb.append(onLoadSuccess + "(data);");
		}
		sb.append("},");
		if (StringUtil.isNotEmpty(onDblClick)) {
			sb.append("onDblClickRow:function(rowIndex,rowData){" + onDblClick + "(rowIndex,rowData);},");
		}
		if (treegrid) {
			sb.append("onClickRow:function(rowData){");
		}
		else {
			sb.append("onClickRow:function(rowIndex,rowData){");
		}
		/**行记录赋值*/
		sb.append("rowid=rowData.id;");
		sb.append("gridname=\'"+name+"\';");
		if (StringUtil.isNotEmpty(onClick)) {
			if (treegrid) {
				sb.append("" + onClick + "(rowData);");
			}else{
				sb.append("" + onClick + "(rowIndex,rowData);");
			}
		}
		sb.append("}");
		sb.append("});");
		this.setPager(sb, grid);
		sb.append("try{restoreheader();}catch(ex){}");
		sb.append("});");
		sb.append("function reloadTable(){");
		sb.append("try{");
		sb.append("	$(\'#\'+gridname).datagrid(\'reload\');" );
		sb.append("	$(\'#\'+gridname).treegrid(\'reload\');" );
		sb.append("}catch(ex){}");
		sb.append("}");
		sb.append("function reload" + name + "(){" + "$(\'#" + name + "\')." + grid + "(\'reload\');" + "}");
		sb.append("function get" + name + "Selected(field){return getSelected(field);}");
		sb.append("function getSelected(field){" + "var row = $(\'#\'+gridname)." + grid + "(\'getSelected\');" + "if(row!=null)" + "{" + "value= row[field];" + "}" + "else" + "{" + "value=\'\';" + "}" + "return value;" + "}");
		sb.append("function get" + name + "Selections(field){" + "var ids = [];" + "var rows = $(\'#" + name + "\')." + grid + "(\'getSelections\');" + "for(var i=0;i<rows.length;i++){" + "ids.push(rows[i][field]);" + "}" + "ids.join(\',\');" + "return ids" + "};");
		sb.append("function getSelectRows(){");
		sb.append("	return $(\'#"+name+"\').datagrid('getChecked');}");

		sb.append(" function saveHeader(){");
		sb.append(" var columnsFields =null;var easyextends=false;try{columnsFields = $('#"+name+"').datagrid('getColumns');easyextends=true;");
		sb.append("}catch(e){columnsFields =$('#"+name+"').datagrid('getColumnFields');}");
		sb.append("	var cols = storage.get( '"+name+"hiddenColumns');var init=true;	if(cols){init =false;} " +
				"var hiddencolumns = [];for(var i=0;i< columnsFields.length;i++) {if(easyextends){");
		sb.append("hiddencolumns.push({field:columnsFields[i].field,hidden:columnsFields[i].hidden});}else{");
		sb.append( " var columsDetail = $('#"+name+"').datagrid(\"getColumnOption\", columnsFields[i]); ");
		sb.append( "if(init){hiddencolumns.push({field:columsDetail.field,hidden:columsDetail.hidden,visible:(columsDetail.hidden==true?false:true)});}else{");
		sb.append("for(var j=0;j<cols.length;j++){");
		sb.append("		if(cols[j].field==columsDetail.field){");
		sb.append("					hiddencolumns.push({field:columsDetail.field,hidden:columsDetail.hidden,visible:cols[j].visible});");
		sb.append("		}");
		sb.append("}");
		sb.append("}} }");
		sb.append("storage.set( '"+name+"hiddenColumns',JSON.stringify(hiddencolumns));");
		sb.append( "}");
		sb.append( "function restoreheader(){");
		sb.append("var cols = storage.get( '"+name+"hiddenColumns');if(!cols)return;");
		sb.append( "for(var i=0;i<cols.length;i++){");
		sb.append( "	try{");
		sb.append("if(cols.visible!=false)$('#"+name+"').datagrid((cols[i].hidden==true?'hideColumn':'showColumn'),cols[i].field);");
		sb.append( "}catch(e){");
		sb.append( "}");
		sb.append( "}");
		sb.append( "}");
		sb.append( "function resetheader(){");
		sb.append("var cols = storage.get( '"+name+"hiddenColumns');if(!cols)return;");
		sb.append( "for(var i=0;i<cols.length;i++){");
		sb.append( "	try{");
		sb.append("  $('#"+name+"').datagrid((cols.visible==false?'hideColumn':'showColumn'),cols[i].field);");
		sb.append( "}catch(e){");
		sb.append( "}");
		sb.append( "}");
		sb.append( "}");

		if (columnList.size() > 0) {
			sb.append("function " + name + "search(){");
			sb.append("var queryParams=$(\'#" + name + "\').datagrid('options').queryParams;");
			sb.append("$(\'#" + name + "tb\').find('*').each(function(){queryParams[$(this).attr('name')]=$(this).val();});");
			sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "',pageNumber:1});" + "}");
			
			//高级查询执行方法
			sb.append("function dosearch(params){");
			sb.append("var jsonparams=$.parseJSON(params);");
			sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "',queryParams:jsonparams});" + "}");
			 //searchbox框执行方法
			  searchboxFun(sb,grid);
			//回车事件
			sb.append("function EnterPress(e){");
			sb.append("var e = e || window.event;");
			sb.append("if(e.keyCode == 13){ ");
			sb.append(name+"search();");
			sb.append("}}");

				
			sb.append("function searchReset(name){");
			sb.append(" $(\"#"+name+"tb\").find(\":input\").val(\"\");");
			String func = name.trim() + "search();";
			sb.append(func);
			sb.append("}");
		}
		sb.append("</script>");
		sb.append("<table width=\"100%\"   id=\"" + name + "\" toolbar=\"#" + name + "tb\"></table>");
		sb.append("<div id=\"" + name + "tb\" style=\"padding:3px; height: auto\">");
		if(hasQueryColum(columnList)){
			sb.append("<div name=\"searchColums\">");
			sb.append("<input  id=\"_sqlbuilder\" name=\"sqlbuilder\"   type=\"hidden\" />");
			//如果表单是组合查询
			if("group".equals(getQueryMode())){
				for (DataGridColumn col : columnList) {
					if (col.isQuery()) {
						sb.append("<span style=\"display:-moz-inline-box;display:inline-block;\">");
						sb.append("<span style=\"vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; \" title=\""+col.getTitle()+"\">"+col.getTitle()+"：</span>");
						if("single".equals(col.getQueryMode())){
							if(!StringUtil.isEmpty(col.getReplace())){
								sb.append("<select name=\""+col.getField().replaceAll("_","\\.")+"\" WIDTH=\"100\" style=\"width: 104px\"> ");
								sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
								String[] test = col.getReplace().split(",");
								String text = "";
								String value = "";
								
								
								
								for (String string : test) {
									String lang_key = string.split("_")[0];
									text = MutiLangUtil.getMutiLangInstance().getLang(lang_key);
									value =string.split("_")[1];
									sb.append("<option value =\""+value+"\">"+text+"</option>");
								}
								sb.append("</select>");
							}else if(!StringUtil.isEmpty(col.getDictionary())){
								if(col.getDictionary().contains(",")){
									String[] dic = col.getDictionary().split(",");
									String sql = "select " + dic[1] + " as field," + dic[2]
											+ " as text from " + dic[0];
									systemService = ApplicationContextUtil.getContext().getBean(
											SystemService.class);
									List<Map<String, Object>> list = systemService.findForJdbc(sql);
									sb.append("<select name=\""+col.getField().replaceAll("_","\\.")+"\" WIDTH=\"100\" style=\"width: 104px\"> ");
									sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
									for (Map<String, Object> map : list){
										sb.append(" <option value=\""+map.get("field")+"\">");
										sb.append(map.get("text"));
										sb.append(" </option>");
									}
									sb.append("</select>");
								}else{
									Map<String, List<TSType>> typedatas = ResourceUtil.allTypes;
									List<TSType> types = typedatas.get(col.getDictionary().toLowerCase());
									sb.append("<select name=\""+col.getField().replaceAll("_","\\.")+"\" WIDTH=\"100\" style=\"width: 104px\"> ");
									sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
									for (TSType type : types) {
										sb.append(" <option value=\""+type.getTypecode()+"\">");
										sb.append(MutiLangUtil.getMutiLangInstance().getLang(type.getTypename()));
										sb.append(" </option>");
									}
									sb.append("</select>");
								}
							}else if(col.isAutocomplete()){
								sb.append(getAutoSpan(col.getField().replaceAll("_","\\."),extendAttribute(col.getExtend())));
							}else{
								sb.append("<input onkeypress=\"EnterPress(event)\" onkeydown=\"EnterPress()\"  type=\"text\" name=\""+col.getField().replaceAll("_","\\.")+"\"  "+extendAttribute(col.getExtend())+" class=\"inuptxt\"/>");
							}
						}else if("group".equals(col.getQueryMode())){
							sb.append("<input type=\"text\" name=\""+col.getField()+"_begin\"   "+extendAttribute(col.getExtend())+" class=\"inuptxt\"/>");
							sb.append("<span style=\"display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;\">~</span>");
							sb.append("<input type=\"text\" name=\""+col.getField()+"_end\"   "+extendAttribute(col.getExtend())+" class=\"inuptxt\"/>");

						}
						sb.append("</span>");
					}
				}
			}
			sb.append("</div>");
		}
		if(toolBarList.size()==0 && !hasQueryColum(columnList)){
			sb.append("<div style=\"height:0px;\" >");
		}else{
			sb.append("<div style=\"height:30px;\" class=\"datagrid-toolbar\">");
		}
		sb.append("<span style=\"float:left;\" >");
		if(toolBarList.size()>0)
		{
			for (DataGridUrl toolBar : toolBarList) {
				
				sb.append("<a href=\"#\" class=\"button\" plain=\"true\" icon=\""+toolBar.getIcon()+"\" ");
				if(StringUtil.isNotEmpty(toolBar.getOnclick()))
				{
					sb.append("onclick="+toolBar.getOnclick()+"");
				}
				else {
					sb.append("onclick=\""+toolBar.getFunname()+"(");
					if(!toolBar.getFunname().equals("doSubmit"))
					{
					sb.append("\'"+toolBar.getTitle()+"\',");
					}
					String width = toolBar.getWidth().contains("%")?"'"+toolBar.getWidth()+"'":toolBar.getWidth();
					String height = toolBar.getHeight().contains("%")?"'"+toolBar.getHeight()+"'":toolBar.getHeight();
					sb.append("\'"+toolBar.getUrl()+"\',\'"+name+"\',"+width+","+height+")\"");
				}
				sb.append(">"+toolBar.getTitle()+"</a>");
			}
		}
		sb.append("</span>");
		if("group".equals(getQueryMode()) && hasQueryColum(columnList)){//如果表单是组合查询
			sb.append("<span style=\"float:right\">");
			sb.append("<a href=\"#\" class=\"button\" iconCls=\"icon-search\" onclick=\""+  name+ StringUtil.replaceAll("search()\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.query")));
			sb.append("<a href=\"#\" class=\"button\" iconCls=\"icon-reload\" onclick=\"searchReset('"+name+ StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.reset")) );
			if(queryBuilder){
				sb.append("<a href=\"#\" class=\"button\" iconCls=\"icon-search\" onclick=\"queryBuilder('"+ StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.querybuilder")) );
			}sb.append("</span>");
		}else if("single".equals(getQueryMode())&& hasQueryColum(columnList)){//如果表单是单查询
			sb.append("<span style=\"float:right\">");
			sb.append("<input id=\""+name+"searchbox\" class=\"easyui-searchbox\"  data-options=\"searcher:"+name+ StringUtil.replaceAll("searchbox,prompt:\'{0}\',menu:\'#", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.input.keyword")) +name+"mm\'\"></input>");
			sb.append("<div id=\""+name+"mm\" style=\"width:120px\">");
			for (DataGridColumn col : columnList) {
				if (col.isQuery()) {
					sb.append("<div data-options=\"name:\'"+col.getField().replaceAll("_","\\.")+"\',iconCls:\'icon-ok\' "+extendAttribute(col.getExtend())+" \">"+col.getTitle()+"</div>  ");
				}
			}
			sb.append("</div>");
			sb.append("</span>");
		}
		sb.append("</div>");
		if(queryBuilder){
			addQueryBuilder(sb,"button");
		}
		return sb;
	}

	private void appendLine(StringBuffer sb,String str) {
		String format = "\r\n"; //调试  格式化
		sb.append(str).append(format);
	}
	/**
	 * TODO 语言做成多语翻译，保留历史查询记录
	 * @param sb
	 */
	private void addQueryBuilder(StringBuffer sb,String buttonSytle) {

		appendLine(sb,"<div style=\"position:relative;overflow:auto;\">");
		appendLine(sb,"<div id=\""+name+"_qbwin\" class=\"easyui-window\" data-options=\"closed:true,title:'高级查询构造器'\" style=\"width:600px;height:370px;padding:0px\">");
		appendLine(sb,"	<div class=\"easyui-layout\" data-options=\"fit:true\">");
		appendLine(sb,"		<div data-options=\"region:'east',split:true\" style=\"width:130px\"><div class=\"easyui-accordion\" style=\"width:120px;height:300px;\">");
		appendLine(sb,"<div title=\"查询历史\" data-options=\"iconCls:'icon-search'\" style=\"padding:0px;\">");
		appendLine(
				sb,
				"	<ul id=\""
						+ name
						+ "tt\" class=\"easyui-tree\" data-options=\"onClick:function(node){");
		appendLine(sb, "historyQuery( node.id);  ");
		appendLine(sb, "},ondbClick: function(node){");
		appendLine(sb, "$(this).tree('beginEdit',node.target);");
		appendLine(sb, "},onContextMenu: function(e,node){");
		appendLine(sb, "		e.preventDefault();");
		appendLine(sb, "		$(this).tree('select',node.target);");
		appendLine(sb, "		$('#"+name+"mmTree').menu('show',{");
		appendLine(sb, "			left: e.pageX,");
		appendLine(sb, "			top: e.pageY");
		appendLine(sb, "		});");
		appendLine(sb, "	},  ");
		appendLine(sb, " onAfterEdit:function(node){  ");
		appendLine(sb,
				"    if(node.text!=''){ "+name+"his[node.id].name=node.text; saveHistory();}	}");
		appendLine(sb, "\">");
		appendLine(sb, "	</ul>");
		appendLine(sb, "</div>");
		appendLine(sb, "</div></div>");
		appendLine(sb,"		<div data-options=\"region:'center'\" style=\"padding:0px;\">");
		appendLine(sb,"			<table id=\""+name+"tg\" class=\"easyui-treegrid\" title=\"查询条件编辑\" style=\"width:450px;height:300px;\"");
		appendLine(sb,"		data-options=\"");
		appendLine(sb,"			iconCls: 'icon-ok',");
		appendLine(sb,"			rownumbers: true,");
		appendLine(sb,"			animate: true,");
		appendLine(sb,"			fitColumns: true,");
		appendLine(sb,"			//url: 'sqlbuilder.json',//可以预加载条件\r\n");
		appendLine(sb,"			method: 'get',");
		appendLine(sb,"			idField: 'id',");
		appendLine(sb,"autoEditing: true,  ");
				appendLine(sb,"extEditing: false, ");
						appendLine(sb,"singleEditing: false ,");
		appendLine(sb,"			treeField: 'field',toolbar:toolbar,onContextMenu: onContextMenu");
		appendLine(sb,"		\">");
		appendLine(sb,"<thead>");
		appendLine(sb,"	<tr>");
		sb
		.append("	<th data-options=\"field:'relation',width:18,formatter:function(value,row){");
appendLine(sb,"				return value=='and'?'并且':'或者';");
appendLine(sb,"			},editor:{");
appendLine(sb,"				type:'combobox',");
appendLine(sb,"				options:{");
appendLine(sb,"				valueField:'relationId',");
appendLine(sb,"						textField:'relationName',");
appendLine(sb,"						data:  ");
appendLine(sb,"						[  ");
appendLine(sb,"						{'relationId':'and','relationName':'并且'},  ");
appendLine(sb,"						{'relationId':'or','relationName':'或者'}  ");
appendLine(sb,"						],  ");
appendLine(sb,"						required:true");
appendLine(sb,"					}}\">关系</th>");
		sb
				.append("		<th data-options=\"field:'field',width:30,formatter:function(value,row){");
		appendLine(sb,"			var data= ");
		StringBuffer fieldArray=new StringBuffer();
		fieldArray.append("	[  ");
		for (int i=0;i<columnList.size();i++){
			DataGridColumn col =columnList.get(i);
			if("opt".equals(col.getField())) {
                continue;//忽略操作虚拟字段
            }
			fieldArray.append("	{'fieldId':'"+getDBFieldName(col.getField())+"','fieldName':'"+col.getTitle()+"'");
			if(col.getEditor()!=null){
				fieldArray.append(",editor:'"+col.getEditor()+"'");
			}
			fieldArray.append("}");
			if(i<columnList.size()-1){
				fieldArray.append(",");
			}
		}
//		appendLine(sb,"				{'fieldId':'office_Phone','fieldName':'办公电话'},");
		fieldArray.append("]");
		sb.append(fieldArray).append(";");
		appendLine(sb,"for(var i=0;i<data.length;i++){");
		appendLine(sb,"if(value == data[i]['fieldId']){");
		appendLine(sb,"return data[i]['fieldName'];");
		appendLine(sb,"}");
		appendLine(sb,"}");
		appendLine(sb,"return value;");
		appendLine(sb,"},editor:{");
		appendLine(sb,"type:'combobox',");
		appendLine(sb,"	options:{");
		appendLine(sb,"valueField:'fieldId',");
		appendLine(sb,"textField:'fieldName',");
		appendLine(sb,"data:  ");
		sb.append(fieldArray);
		appendLine(sb," , ");
		appendLine(sb, "required:true,onSelect : function(record) {");
		appendLine(sb,"var opts = $('#"+name+"tg').treegrid('getColumnOption','value');");
		appendLine(sb, "	if(record.editor){");
		appendLine(sb, "			opts.editor=record.editor;");
		appendLine(sb, "	}else{");
		appendLine(sb, "			opts.editor='text';");
		appendLine(sb, "	}");
		appendLine(sb, "	var tr = $(this).closest('tr.datagrid-row');");
		appendLine(sb, "	var index = parseInt(tr.attr('node-id'));");
		appendLine(sb, "	$('#"+name+"tg').treegrid('endEdit', index);");
		appendLine(sb, "	$('#"+name+"tg').treegrid('beginEdit', index);");
		appendLine(sb, "}");
		appendLine(sb,"}}\">字段</th>");
		appendLine(sb,"<th data-options=\"field:'condition',width:20,align:'right',formatter:function(value,row){");
		appendLine(sb,"							var data=  ");
		appendLine(sb,"					[  ");
		Map<String, List<TSType>> typedatas = ResourceUtil.allTypes;
		List<TSType> types = typedatas.get("rulecon");
		if (types != null) {
			for (int i=0;i<types.size();i++){
				TSType type = types.get(i);
				appendLine(sb," {'conditionId':'"+type.getTypecode()+"','conditionName':'"
						+MutiLangUtil.getMutiLangInstance().getLang(type.getTypename())+"'}");
				if(i<types.size()-1){
					appendLine(sb,",");
				}
			}
		}
		appendLine(sb,"];");
		appendLine(sb,"	for(var i=0;i<data.length;i++){");
		appendLine(sb,"			if(value == data[i]['conditionId']){");
		appendLine(sb,"			return data[i]['conditionName'];");
		appendLine(sb,"			}");
		appendLine(sb,"		}");
		appendLine(sb,"		return value;");
		appendLine(sb,"		},editor:{");
		appendLine(sb,"		type:'combobox',");
		appendLine(sb,"		options:{");
		appendLine(sb,"			valueField:'conditionId',");
		appendLine(sb,"				textField:'conditionName',	");
		appendLine(sb,"			data:  ");
		appendLine(sb,"[");
		if (types != null) {
			for (int i=0;i<types.size();i++){
				TSType type = types.get(i);
				appendLine(sb," {'conditionId':'"+type.getTypecode()+"','conditionName':'"
						+MutiLangUtil.getMutiLangInstance().getLang(type.getTypename())+"'}");
				if(i<types.size()-1){
					appendLine(sb,",");
				}
			}
		}
		appendLine(sb,"				],  ");
		appendLine(sb,"				required:true");
		appendLine(sb,"			}}\">条件</th>");
		sb
				.append("	<th data-options=\"field:'value',width:30,editor:'text'\">值</th>");
		appendLine(sb,"<th data-options=\"field:'opt',width:30,formatter:function(value,row){");
		appendLine(sb,"	return '<a  onclick=\\'removeIt('+row.id+')\\' >删除</a>';}\">操作</th>");
		appendLine(sb,"		</tr>");
		appendLine(sb,"	</thead>");
		appendLine(sb,"	</table>");
		appendLine(sb,"</div>");
		appendLine(sb,"<div data-options=\"region:'south',border:false\" style=\"text-align:right;padding:5px 0 0;\">");
		appendLine(sb,"<a class=\""+buttonSytle+"\" data-options=\"iconCls:'icon-ok'\" href=\"javascript:void(0)\" onclick=\"javascript:queryBuilderSearch()\">确定</a>");
		appendLine(sb,"<a class=\""+buttonSytle+"\" data-options=\"iconCls:'icon-cancel'\" href=\"javascript:void(0)\" onclick=\"javascript:$('#"+name+"_qbwin').window('close')\">取消</a>");
		appendLine(sb,"		</div>");
		appendLine(sb,"	</div>	");
		appendLine(sb,"</div>		");
		appendLine(sb,"</div>");
		appendLine(sb,"<div id=\"mm\" class=\"easyui-menu\" style=\"width:120px;\">");
		appendLine(sb,"	<div onclick=\"append()\" data-options=\"iconCls:'icon-add'\">添加</div>");
		appendLine(sb,"	<div onclick=\"edit()\" data-options=\"iconCls:'icon-edit'\">编辑</div>");
		appendLine(sb,"	<div onclick=\"save()\" data-options=\"iconCls:'icon-save'\">保存</div>");
		appendLine(sb,"	<div onclick=\"removeIt()\" data-options=\"iconCls:'icon-remove'\">删除</div>");
		appendLine(sb,"	<div class=\"menu-sep\"></div>");
		appendLine(sb,"	<div onclick=\"cancel()\">取消</div>");
		appendLine(sb,"<div onclick=\"expand()\">Expand</div>");
		appendLine(sb,"</div><div id=\""+name+"mmTree\" class=\"easyui-menu\" style=\"width:100px;\">");
		appendLine(sb,"<div onclick=\"editTree()\" data-options=\"iconCls:'icon-edit'\">编辑</div>");
		appendLine(sb,"<div onclick=\"deleteTree()\" data-options=\"iconCls:'icon-remove'\">删除</div></div>");
		//已在baseTag中引入
//		appendLine(sb,"<script type=\"text/javascript\" src=\"plug-in/jquery/jquery.cookie.js\" ></script>");
//		appendLine(sb,"<script type=\"text/javascript\" src=\"plug-in/jquery-plugs/storage/jquery.storageapi.min.js\" ></script>"); 
		appendLine(sb,"<script type=\"text/javascript\">");
		appendLine(sb,"var toolbar = [{");
		appendLine(sb,"	text:'',");
		appendLine(sb,"	iconCls:'icon-add',");
		appendLine(sb,"	handler:function(){append();}");
		appendLine(sb,"},{");
		appendLine(sb,"	text:'',");
		appendLine(sb,"	iconCls:'icon-edit',");
		appendLine(sb,"	handler:function(){edit();}");
		appendLine(sb,"},{");
		appendLine(sb,"	text:'',");
		appendLine(sb,"	iconCls:'icon-remove',");
		appendLine(sb,"	handler:function(){removeIt();}");
		appendLine(sb,"},'-',{");
		appendLine(sb,"	text:'',");
		appendLine(sb,"	iconCls:'icon-save',");
		appendLine(sb,"	handler:function(){save();}");
		appendLine(sb,"	}];");
		appendLine(sb,"function onContextMenu(e,row){");
		appendLine(sb,"	e.preventDefault();");
		appendLine(sb,"	$(this).treegrid('select', row.id);");
		appendLine(sb,"	$('#mm').menu('show',{");
		appendLine(sb,"		left: e.pageX,");
		appendLine(sb,"		top: e.pageY");
		appendLine(sb,"	});");
		appendLine(sb,"}");
		appendLine(sb,"	var idIndex = 100;");
		appendLine(sb,"function append(){");
		appendLine(sb,"	idIndex++;");
		appendLine(sb,"	var node = $('#"+name+"tg').treegrid('getSelected');");
		appendLine(sb,"	$('#"+name+"tg').treegrid('append',{");
		appendLine(sb,"		data: [{");
		appendLine(sb,"			id: idIndex,");
		appendLine(sb,"			field: '',");
		appendLine(sb,"		condition:'like',");
		appendLine(sb,"		value: '%a%',");
		appendLine(sb,"		relation: 'and'");
		appendLine(sb,"				}]");
		appendLine(sb,"});$('#"+name+"tg').datagrid('beginEdit',idIndex);");
		appendLine(sb,"}");
		appendLine(sb,"		function removeIt(id){");
		appendLine(sb,"var node = $('#"+name+"tg').treegrid('getSelected');");
		appendLine(sb,"if(id){");
		appendLine(sb,"$('#"+name+"tg').treegrid('remove', id);");
		appendLine(sb,"}else if(node){	$('#"+name+"tg').treegrid('remove', node.id);");
		appendLine(sb,"}");
		appendLine(sb,"}");
		appendLine(sb,"function collapse(){");
		appendLine(sb,"	var node = $('#"+name+"tg').treegrid('getSelected');");
		appendLine(sb,"if(node){");
		appendLine(sb,"	$('#"+name+"tg').treegrid('collapse', node.id);");
		appendLine(sb,"}");
		appendLine(sb,"}");
		appendLine(sb,"function expand(){");
		appendLine(sb,"var node = $('#"+name+"tg').treegrid('getSelected');");
		appendLine(sb,"if(node){");
		appendLine(sb,"	$('#"+name+"tg').treegrid('expand', node.id);");
		appendLine(sb,"}");
		appendLine(sb,"}");
		appendLine(sb,"var editingId;");
		appendLine(sb,"function edit(id){");
		appendLine(sb,"var row = $('#"+name+"tg').treegrid('getSelected');");
		appendLine(sb,"if(id){	$('#"+name+"tg').treegrid('beginEdit', id);}else if(row){");
		appendLine(sb,"	$('#"+name+"tg').treegrid('beginEdit', row.id);");
		appendLine(sb,"}");
		appendLine(sb,"}");
		appendLine(sb,"function save(){");
		appendLine(sb,"	var t = $('#"+name+"tg');");
		appendLine(sb,"	var nodes = t.treegrid('getRoots');");
		appendLine(sb,"	for (var i = 0; i < nodes.length; i++) {");
		appendLine(sb,"	t.treegrid('endEdit',nodes[i].id);}");
		appendLine(sb,"	}");
		appendLine(sb,"function cancel(){");
		appendLine(sb,"	var t = $('#"+name+"tg');");
		appendLine(sb,"var nodes = t.treegrid('getRoots');for (var i = 0; i < nodes.length; i++) {t.treegrid('cancelEdit',nodes[i].id);}");
		appendLine(sb,"}");
		appendLine(sb, "var "+name+"his=new Array();");
		appendLine(sb, " function historyQuery(index) {");
		appendLine(sb, "	  var data  = { rows:JSON.parse("+name+"his[index].json)};  ");
		appendLine(sb, "	    var t = $('#" + name + "tg');");
		appendLine(sb, "		var data = t.treegrid('loadData',data);");
		appendLine(sb, "		$('#_sqlbuilder').val( "+name+"his[index].json);   ");
		appendLine(sb, "		"+name+"search();");
		appendLine(sb, "	}");
		appendLine(sb, "function view(){");
		appendLine(sb,"save();");
		appendLine(sb,"var t = $('#"+name+"tg');");
		appendLine(sb,"var data = t.treegrid('getData');");
		appendLine(sb,"return   JSON.stringify(data) ;");
		appendLine(sb,"}");
		appendLine(sb,"	 function queryBuilder() {");
		appendLine(sb,"	$('#"+name+"_qbwin').window('open');");
		appendLine(sb,"}");

		appendLine(sb, "function queryBuilderSearch() {");
		appendLine(sb, "         var json =  view();");
		appendLine(sb, "	$('#_sqlbuilder').val(json);  ");
		appendLine(sb, "	var isnew=true;");
		appendLine(sb, "for(var i=0;i< "+name+"his.length;i++){");
		appendLine(sb, "	if("+name+"his[i]&&"+name+"his[i].json==json){");
		appendLine(sb, "		isnew=false;");
		appendLine(sb, "	}");
		appendLine(sb, "}");
		appendLine(sb, "if(isnew){");
		appendLine(sb, " "+name+"his.push({name:'Query'+"+name+"his.length,json:json});saveHistory();");
		appendLine(sb, "var name= 'Query'+( "+name+"his.length-1);");
		appendLine(sb, "	var name= 'Query'+("+name+"his.length-1);");
		appendLine(sb, "appendTree("+name+"his.length-1,name);");
		appendLine(sb, "}");
		appendLine(sb, "	" + name + "search();");
		appendLine(sb, " }");
		appendLine(sb, " $(document).ready(function(){ ");
		appendLine(sb, " storage=$.localStorage;if(!storage)storage=$.cookieStorage;");
		appendLine(sb, "	var _qhistory = storage.get('" + name+ "_query_history');");
		appendLine(sb, " if(_qhistory){");
		appendLine(sb, " "+name+"his=_qhistory;");
		// appendLine(sb, " 	var data  = { rows:his[0]};");
		appendLine(sb, " 	for(var i=0;i< "+name+"his.length;i++){");
		appendLine(sb, " 		if("+name+"his[i])appendTree(i,"+name+"his[i].name);");
		appendLine(sb, " 	}restoreheader();");
		appendLine(sb, " }});");
		appendLine(sb, "function saveHistory(){");
		appendLine(sb, "	var history=new Array();");
		appendLine(sb, "	for(var i=0;i<" + name + "his.length;i++){");
		appendLine(sb, "		if(" + name + "his[i]){");
		appendLine(sb, "			history.push(" + name + "his[i]);");
		appendLine(sb, "		}");
		appendLine(sb, "	}");
		appendLine(sb, "	storage.set( '"+name+"_query_history',JSON.stringify(history));");
		appendLine(sb, "}");
		appendLine(sb, "function deleteTree(){");
		appendLine(sb, "	var tree = $('#" + name
				+ "tt');var node= tree.tree('getSelected');");
		appendLine(sb, "	" + name + "his[node.id]=null;saveHistory();");
		appendLine(sb, "	tree.tree('remove', node.target);");
		appendLine(sb, "}");
		appendLine(sb, "function editTree(){");
		appendLine(sb, "	var node = $('#" + name + "tt').tree('getSelected');");
		appendLine(sb, "	$('#" + name + "tt').tree('beginEdit',node.target);");
		appendLine(sb, "	saveHistory();");
		appendLine(sb, "}");
		appendLine(sb, "function appendTree(id,name){");
		appendLine(sb, "	$('#"+name+"tt').tree('append',{");
		appendLine(sb, "	data:[{");
		appendLine(sb, "id : id,");
		appendLine(sb, "text :name");
		appendLine(sb, "	}]");
		appendLine(sb, "});");
		appendLine(sb, "}");
		

		appendLine(sb, "</script>");
	}
	/**
	 * hibernate字段名转换为数据库名称，只支持标准命名
	 * 否则转换错误
	 * @param fieldName
	 * @return
	 */
	String getDBFieldName(String fieldName){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<fieldName.length();i++){
			char c =  fieldName.charAt(i);
			if(c<='Z'&&c>='A'){
				sb.append("_").append((char)((int)c+32));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
