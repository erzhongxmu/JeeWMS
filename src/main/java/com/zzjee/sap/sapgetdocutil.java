package com.zzjee.sap;

import com.sap.conn.jco.JCoTable;
import com.zzjee.wmapi.entity.wmientity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sapgetdocutil {
    //获取镭射清单
    public static List<wmientity>   getlsqd(String id, String par[]){
        Map<String, Object> result = new HashMap<String, Object>();
        List<wmientity> reslit = new ArrayList<wmientity>();
        try {
            result = sapWmUtil.getWmlsqd(id);
            JCoTable restable =  (JCoTable)result.get("IT_OUT");
            org.jeecgframework.core.util.LogUtil.info("===================获取镭射清单开始===================");
            reslit = getlistbyparandrable(restable,par);
            org.jeecgframework.core.util.LogUtil.info("===================获取镭射清单开始===================");
        } catch (Exception e) {
                e.printStackTrace();
        }
        return  reslit;
    }
//获取WMDOC
public static List<wmientity>    getWmdoc(String DOCTYPE,String DOCID,String tablename,String par[]) {
    Map<String, Object> result = new HashMap<String, Object>();
    List<wmientity> reslit = new ArrayList<wmientity>();
    try {
        result = sapWmUtil.getWmdoc(DOCTYPE,DOCID,tablename);
        JCoTable restable =  (JCoTable)result.get("IT_OUT");
        org.jeecgframework.core.util.LogUtil.info("===================获取凭证开始===================");
        reslit = getlistbyparandrable(restable,par);
        org.jeecgframework.core.util.LogUtil.info("===================获取凭证结束===================");

    } catch (Exception e) {
        e.printStackTrace();
    }
    return  reslit;
}


//获取储位库存
    public static List<wmientity>   getcwkc(String LGNUM,String MATNR,String LGPLA,String par[]){
        Map<String, Object> result = new HashMap<String, Object>();
        List<wmientity> reslit = new ArrayList<wmientity>();
        try {
            result = sapWmUtil.getcwkc(LGNUM,MATNR,LGPLA);
            JCoTable restable =  (JCoTable)result.get("IT_OUT");
            org.jeecgframework.core.util.LogUtil.info("===================获取库存开始===================");
            reslit = getlistbyparandrable(restable,par);
            org.jeecgframework.core.util.LogUtil.info("===================获取库存结束===================");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  reslit;
    }
    //获取储位
    public static List<wmientity>   getcw(String LGNUM,String LGPLA,String par[]){
        Map<String, Object> result = new HashMap<String, Object>();
        List<wmientity> reslit = new ArrayList<wmientity>();
        try {
            result = sapWmUtil.getcw(LGNUM,LGPLA);
            JCoTable restable =  (JCoTable)result.get("IT_OUT");
            org.jeecgframework.core.util.LogUtil.info("===================获取储位开始===================");
            reslit = getlistbyparandrable(restable,par);
            org.jeecgframework.core.util.LogUtil.info("===================获取储位结束===================");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  reslit;
    }

    private static List<wmientity> getlistbyparandrable(JCoTable restable, String par[]){
        List<wmientity> reslit = new ArrayList<wmientity>();
        for(int i=0;i<restable.getNumRows();i++){
            wmientity t = new wmientity();
            try {
                Class cl = Class.forName("com.yserp.wmapi.entity.wmientity");//反射得到类
                restable.setRow(i);
                Object obj = cl.newInstance();//新建一个实例
                for(int j = 1;j<=par.length;j++){
                    String methodstr = "setWmX"+j;
                    Method method = cl.getMethod(methodstr,String.class);
                    method.invoke(obj,restable.getString(par[j-1]));
                }
                t = (wmientity) obj;
                reslit.add(t);
            }catch (Exception e){

            }

        }
        return reslit;

    }
}
