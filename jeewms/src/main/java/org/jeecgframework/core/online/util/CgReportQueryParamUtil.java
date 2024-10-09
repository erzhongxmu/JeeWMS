package org.jeecgframework.core.online.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeecgframework.core.online.def.CgReportConstant;
import org.jeecgframework.core.util.DBTypeUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;

/**
 * 
 * @Title:QueryParamUtil
 * @description:动态报表查询条件处理工具
 * @author 赵俊夫
 * @date Jul 5, 2013 10:22:31 PM
 * @version V1.0
 */
public class CgReportQueryParamUtil

{
	/**
	 * 组装查询参数
	 * @param request 请求(查询值从此处取)
	 * @param item 动态报表字段配置
	 * @param params 参数存放
	 */
	@SuppressWarnings("unchecked")
	public static void loadQueryParams(HttpServletRequest request, Map item, Map params) {
		String filedName = (String) item.get(CgReportConstant.ITEM_FIELDNAME);
		String queryMode = (String) item.get(CgReportConstant.ITEM_QUERYMODE);
		String filedType = (String) item.get(CgReportConstant.ITEM_FIELDTYPE);
		if("single".equals(queryMode)){
			//单条件组装方式
			String value =request.getParameter(filedName);
			try {
				if(StringUtil.isEmpty(value)){
					return;
				}
				String uri = request.getQueryString();
				if(uri.contains(filedName+"=")){
					String contiansChinesevalue = new String(value.getBytes("UTF-8"), "UTF-8");
					value = contiansChinesevalue;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return;
			} 
			sql_inj_throw(value);
			value = applyType(filedType,value);
			if(!StringUtil.isEmpty(value)){
				if(value.contains("*")){
					//模糊查询
					value = value.replaceAll("\\*", "%");
					params.put(filedName, CgReportConstant.OP_LIKE+value);
				}else{
					if(ResourceUtil.getConfigByName("sysfuzseach").equals("on")){
						params.put(filedName, CgReportConstant.OP_LIKE+ "'"+"%"+value+"%"+ "'");//模糊查询

					}else{
						params.put(filedName, CgReportConstant.OP_EQ+value);//模糊查询

					}
				}
			}
		}else if("group".equals(queryMode)){
			//范围查询组装
			String begin = request.getParameter(filedName+"_begin");
			sql_inj_throw(begin);
			begin= applyType(filedType,begin);
			String end = request.getParameter(filedName+"_end");
			sql_inj_throw(end);
			end= applyType(filedType,end);
			if(!StringUtil.isEmpty(begin)){
				String re = CgReportConstant.OP_RQ+begin;
				if(!StringUtil.isEmpty(end)){
					re +=" AND "+filedName+CgReportConstant.OP_LQ+end;
				}
				params.put(filedName, re);
			}else if(!StringUtil.isEmpty(end)){
				String re = CgReportConstant.OP_LQ+end;
				params.put(filedName, re);
			}
		}
	}
	
	/**
	 * 将结果集转化为列表json格式
	 * @param result 结果集
	 * @param size 总大小
	 * @return 处理好的json格式
	 */
	public static String getJson(List<Map<String, Object>> result,Long size){
		JSONObject main = new JSONObject();
		JSONArray rows = new JSONArray();
		main.put("total",size );
		for(Map m:result){
			JSONObject item = new JSONObject();
			Iterator  it =m.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				String value =String.valueOf(m.get(key));
				key = key.toLowerCase();
				if(key.contains("time")||key.contains("date")){
					value = datatimeFormat(value);
				}
				item.put(key,value );
			}
			rows.add(item);
		}
		main.put("rows", rows);
		return main.toString();
	}
	
	/**
	 * 将结果集转化为列表json格式(不含分页信息)
	 * @param result 结果集
	 * @return 处理好的json格式
	 */
	public static String getJson(List<Map<String, Object>> result){
		JSONArray rows = new JSONArray();
		for(Map m:result){
			JSONObject item = new JSONObject();
			Iterator  it =m.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				String value =String.valueOf(m.get(key));
				key = key.toLowerCase();
				if(key.contains("time")||key.contains("date")){
					value = datatimeFormat(value);
				}
				item.put(key,value );
			}
			rows.add(item);
		}
		return rows.toString();
	}
	
	/**
	 * 将毫秒数去掉
	 * @param datetime
	 * @return
	 */
	public static String datatimeFormat(String datetime){
		SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		SimpleDateFormat  dateFormatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d  = null;
		try{
			d = dateFormat.parse(datetime);
			return dateFormatTo.format(d);
		}catch (Exception e) {
			return datetime;
		}
	}
	
	/**
	 * 防止sql注入
	 * @param str 输入sql
	 * @return 是否存在注入关键字
	 */
	public static boolean sql_inj(String str) {
		if(StringUtil.isEmpty(str)){
			return false;
		}
		String inj_str = "'|and|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare|;|or|+|,";
//		String inj_str = "'|and|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		String inj_stra[] = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(" "+inj_stra[i]+" ") >= 0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 当存在sql注入时抛异常
	 * @param str 输入sql
	 */
	public static void sql_inj_throw(String str){
		if(sql_inj(str)){
			throw new RuntimeException("请注意,填入的参数可能存在SQL注入!");
		}
	}
	
	/**
	 * 根据字段类型 进行处理
	 * @param fieldType 字段类型
	 * @param value 值
	 * @return
	 */
	public static String applyType(String fieldType, String value) {
		if(!StringUtil.isEmpty(value)){
			String result = "";
			if(CgReportConstant.TYPE_STRING.equalsIgnoreCase(fieldType)){

				//if(ResourceUtil.fuzzySearch&&(!value.contains("*"))){
				//	value="*"+value+"*";
				//}
				if(ResourceUtil.getConfigByName("sysfuzseach").equals("on")){
					if(value.contains("*")){
						result = "'" +value+ "'";
					}else{
						result =  value;
					}

				}else{
					result = "'" +value+ "'";
				}


			}else if(CgReportConstant.TYPE_DATE.equalsIgnoreCase(fieldType)){
				result = getDateFunction(value, "yyyy-MM-dd");
			}else if(CgReportConstant.TYPE_DOUBLE.equalsIgnoreCase(fieldType)){
				result = value;
			}else if(CgReportConstant.TYPE_INTEGER.equalsIgnoreCase(fieldType)){
				result = value;
			}else{
				result = value;
			}
			return result;
		}else{
			return "";
		}
	}
	
	/**
	 * 跨数据库返回日期函数
	 * @param dateStr 日期值
	 * @param dateFormat 日期格式
	 * @return 日期函数
	 */
	public static String getDateFunction(String dateStr,String dateFormat){
		String dbType = getDBType();
		String dateFunction = "";
		if("mysql".equalsIgnoreCase(dbType)){
			//mysql日期函数
			dateFunction = "'"+dateStr+"'";
		}else if("oracle".equalsIgnoreCase(dbType)){
			//oracle日期函数
			dateFunction = "TO_DATE('"+dateStr+"','"+dateFormat+"')";
		}else if("sqlserver".equalsIgnoreCase(dbType)){
			//sqlserver日期函数
			dateFunction = "(CONVERT(VARCHAR,'"+dateStr+"') as DATETIME)";
		}else if("postgres".equalsIgnoreCase(dbType)){
			//postgres日期函数
			dateFunction = "'"+dateStr+"'::date ";
		}else{
			dateFunction = dateStr;
		}
		return dateFunction;
	}
	
	/**
	 * 获取数据库类型
	 * @return
	 */
	public static String getDBType(){
		return DBTypeUtil.getDBType();
	}
}
