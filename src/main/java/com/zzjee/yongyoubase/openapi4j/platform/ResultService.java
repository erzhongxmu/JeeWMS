/**     * @Title: Controller  * @Description: 出货通知  * @author erzhongxmu  * @date 2017-08-15 23:18:59  * @version V1.0     *  */  package com.zzjee.yongyoubase.openapi4j.platform;


import com.zzjee.yongyoubase.openapi4j.exception.OpenAPIException;
import com.zzjee.yongyoubase.openapi4j.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ResourceUtil;

import java.util.Properties;


public class ResultService {
	

	/**
	 * 异步新增查看新增结果
	 * @param requestid
	 * @return
	 * @throws OpenAPIException
	 */
	    public static String getResult(String requestid) throws OpenAPIException {
//	    	Properties prop = PropUtil.getProperties("/config.properties");
//	        String url = prop.getProperty("api_url_result_get");
			String url = ResourceUtil.getConfigByName("api_url_result_get");
//			String from_account = ResourceUtil.getConfigByName("from_account");
//			String app_key = ResourceUtil.getConfigByName("app_key");
	        url = StringUtils.replace(url, "{requestid}", requestid);

	         String resultStr = null;
			try {
				resultStr =  HttpUtil.get(url);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new OpenAPIException(e.getMessage(),e);
			}
	       
	       
	        return resultStr;
	    }
	    
	   

	   
}
