package com.zzjee.wmutil;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.service.SystemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: caoez
 * Date: 13-7-26
 * Time: 下午2:07
 */


public class erpUtil {

	public   static   List  removeDuplicate(List list)  {
		for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
			for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
				if  (list.get(j).equals(list.get(i)))  {
					list.remove(j);
				}
			}
		}
		return list;
	}



//通过客户商品编码，或者WMS商品编码和单位找到WMS编码
	public static String geterpys(String poitemid,String matcode,String bcsl){
		Map<String,String> resultmap = new HashMap<>();
		String yssl= "0.00";
		String ddsl= "0.00";
		String res = "";
		SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
		String  tsql = "SELECT sum(mat_qty) as sumqty  FROM  t_wz_rk_item where by1 = ? and mat_code = ?" ;
		List<Map<String, Object>> result=  systemService.findForJdbc(tsql, poitemid,matcode);
		if(result.size() > 0) {
          try{
          	for(int i = 0; i < result.size(); i++){
				yssl = result.get(i).get("sumqty").toString();
			}
		  }catch (Exception e){

		  }
		}
		Double yssld = 0.00;

		Double bcsld = 0.00;
		tsql = "SELECT sum(mat_qty)  as sumqty FROM t_wz_po_item where id = ? and mat_code = ?";

		result=  systemService.findForJdbc(tsql, poitemid,matcode);
		if(result.size() > 0) {
			try{
				for(int i = 0; i < result.size(); i++){
					ddsl = result.get(i).get("sumqty").toString();
				}
			}catch (Exception e){

			}
		}
		Double ddsld = 0.00;
		try{
			yssld = Double.parseDouble(yssl);
			bcsld = Double.parseDouble(bcsl);
			ddsld = Double.parseDouble(ddsl);;

		}catch (Exception e){

		};
		if(ddsld<(yssld+bcsld)){
			res = "收货数量超过订单数量";
		}
		return res;
	}


}
