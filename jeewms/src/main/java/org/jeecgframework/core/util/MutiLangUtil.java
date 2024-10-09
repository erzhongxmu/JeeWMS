package org.jeecgframework.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.web.system.pojo.base.MutiLangEntity;
import org.jeecgframework.web.system.service.MutiLangServiceI;

import java.util.ArrayList;
import java.util.List;


/**
 * 字符串处理及转换工具类
 * @author  admin
 */
public class MutiLangUtil {
	private static Log logger = LogFactory.getLog(StringUtil.class);

	/**
	 * 通用删除消息方法
	 * 
	 * @param param_lang_key 如：common.delete.success.param
	 * @return XXX删除成功，如多语言删除成功
	 */
	public static String paramDelSuccess(String param_lang_key) {
		String message = getMutiLangInstance().getLang("common.delete.success.param", param_lang_key);
		return message;
	}

	/**
	 * 通用删除消息方法
	 *
	 * @param param_lang_key 如：common.delete.fail.param
	 * @return XXX删除失败，如系统图标失败，正在使用的图标，不允许删除。
	 */
	public static String paramDelFail(String param_lang_key) {
		String message = getMutiLangInstance().getLang("common.delete.fail.param", param_lang_key);
		return message;
	}

	
	/**
	 * 通用更新成功消息方法
	 * 
	 * @param param_lang_key 如：common.edit.success.param
	 * @return XXX更新成功，如多语言删除成功
	 */
	public static String paramUpdSuccess(String param_lang_key) {
		String message = getMutiLangInstance().getLang("common.edit.success.param", param_lang_key);
		return message;
	}
	
	/**
	 * 通用更新失败消息方法
	 * 
	 * @param param_lang_key 如：common.edit.success.param
	 * @return XXX更新失败，如多语言更新失败
	 */
	public static String paramUpdFail(String param_lang_key) {
		String message = getMutiLangInstance().getLang("common.edit.fail.param", param_lang_key);
		return message;
	}
	
	/**
	 * 通用添加消息方法
	 * 
	 * @param param_lang_key 如：common.edit.success.param
	 * @return XXX录入成功，如多语言录入成功
	 */
	public static String paramAddSuccess(String param_lang_key) {
		String message = getMutiLangInstance().getLang("common.add.success.param", param_lang_key);
		return message;
	}
	
	/**
	 * 通用国际化tree方法
	 * 
	 * @param treeList, mutiLangService
	 */
	public static void setMutiTree(List<?> treeList)
	{
		if(ListUtils.isNullOrEmpty(treeList)) {
            return;
        }
		
		for(Object treeItem : treeList)
		{
			ReflectHelper reflectHelper = new ReflectHelper(treeItem);
			String lang_key =  (String)reflectHelper.getMethodValue("text"); //treeItem.getText(); 
			String lang_context = getMutiLangInstance().getLang(lang_key);
			reflectHelper.setMethodValue("text", lang_context);
		}
	}
	
	/**
	 * ComboTree国际化方法
	 * @author xgj
	 * */
	public static void setMutiComboTree(List<ComboTree> treeList){
		for (ComboTree index : treeList) {  
			index.setText(getMutiLangInstance().getLang(index.getText()));  
            if (index.getChildren()!= null && index.getChildren().size() > 0 )   
            {     
            	setMutiComboTree(index.getChildren());  
            }  
        }
	}
	
	/**
	 * 检查国际化内容或lang_key是否已经存在
	 * 
	 * @param lang_key
	 * @return 如果存在则返回true，否则false
	 */
	public static boolean existLangKey(String lang_key)
	{
		List<MutiLangEntity> langKeyList = getMutiLangInstance().findByProperty(MutiLangEntity.class, "langKey", lang_key);
		if(!langKeyList.isEmpty())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 检查国际化内容或lang_key是否已经存在
	 * 
	 * @param lang_key
	 * @return 如果存在则返回true，否则false
	 */
	public static boolean existLangKey(String lang_key,String langCode)
	{
		String hql = "from MutiLangEntity where langKey = '"+lang_key+"' and langCode = '"+langCode+"'";
		List<MutiLangEntity> langKeyList = getMutiLangInstance().findByQueryString(hql);
		if(!langKeyList.isEmpty())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 检查国际化内容或context是否已经存在
	 * 
	 * @param lang_context
	 * @return 如果存在则返回true，否则false
	 */
	public static boolean existLangContext(String lang_context)
	{
		List<MutiLangEntity> langContextList = getMutiLangInstance().findByProperty(MutiLangEntity.class, "langContext", lang_context);
		if(!langContextList.isEmpty())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 通用得到MutiLangService方法
	 * 
	 * @return mutiLangService实例
	 */
	public static MutiLangServiceI getMutiLangInstance()
	{

		MutiLangServiceI mutiLangService = ApplicationContextUtil.getContext().getBean(MutiLangServiceI.class);	

		return mutiLangService;
	}
	
	public static String doMutiLang(String title, String langArg){
		String context = getMutiLangInstance().getLang(title, langArg);
		return context;
	}

    /**
     * 处理列表中对象的多语言属性值，即为列表中实体对象的属性值替换为多语言所对应的值
     * @param list 对象列表
     * @param attributes 多语言属性名列表
     */
	public static void setMutiLangValueForList(List<Object> list, String... attributes) {
        if (ListUtils.isNullOrEmpty(list)) {
            return;
        }
        if (attributes == null || attributes.length == 0) {
            return;
        }
        List<Object> newList = new ArrayList<Object>();
        for (Object obj : list) {
            // 如果直接操作列表中的原始对象，则会触发Hibernate的update操作，所以使用类似克隆的方式进行处理；
            Object cloneObj = null;
            try {
                cloneObj = Class.forName(obj.getClass().getName()).newInstance();
                MyBeanUtils.copyBean2Bean(cloneObj, obj);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            ReflectHelper reflectHelper = new ReflectHelper(cloneObj);
            for (String attribute : attributes) {
                String lang_key = (String) reflectHelper.getMethodValue(attribute);
                String lang_context = getMutiLangInstance().getLang(lang_key);
                reflectHelper.setMethodValue(attribute, lang_context);
            }
            newList.add(cloneObj);
        }
        list.clear();
        list.addAll(newList);
    }


}
