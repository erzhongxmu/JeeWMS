package com.zzjee.util;


import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.service.SystemService;

import java.util.List;
import java.util.Map;

public class wzUtil {

    public static boolean checkstcok(String matcode,String matlocation,String matbatch, String basecount) {
        boolean flag = false;
        try {

            SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            String tsql = "select mat_qty"
                    + "  from v_wz_stock_a ws   where "
                    + "    ws.mat_code = ? "
                    + "   and ws.mat_location =  ? "
                    + "   and ws.mat_batch =  ? ";

            List<Map<String, Object>> result = systemService.findForJdbc(tsql, matcode, matlocation, matbatch);
            if (result.size() > 0) {
                if (Long.parseLong(result.get(0).get("mat_qty").toString()) >= Long.parseLong(basecount)) {
                    flag = true;
                }

            }
        }catch (Exception e){

        }
        return flag;
    }


}
