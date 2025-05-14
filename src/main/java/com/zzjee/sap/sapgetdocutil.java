package com.zzjee.sap;

import com.sap.conn.jco.JCoTable;
import com.zzjee.wmapi.entity.wmientity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sapgetdocutil {

// 获取WM文档信息
public static List<wmientity>   getWmdoc(String DOCTYPE,String DOCID,String tablename,String par[]) {
    // Map<String, Object> result = new HashMap<String, Object>(1024);
    // 创建一个 wmientity 类型的列表来存储结果
    List<wmientity> reslit = new ArrayList<wmientity>();
    try {
        // 调用sapWmUtil的getWmdoc方法获取数据
    	Map<String, Object> result = sapWmUtil.getWmdoc(DOCTYPE,DOCID,tablename);
        // 将返回的结果转换为JCoTable类型
        JCoTable restable =  (JCoTable)result.get("IT_OUT");
        // 打印日志信息
        org.jeecgframework.core.util.LogUtil.info("===================获取凭证开始===================");
        // 调用getlistbyparandrable方法处理数据
        reslit = getlistbyparandrable(restable,par);
        // 打印日志信息
        org.jeecgframework.core.util.LogUtil.info("===================获取凭证结束===================");

    } catch (Exception e) {
        // 如果出现异常，打印堆栈跟踪
        e.printStackTrace();
    }
    // 返回处理后的数据列表
    return  reslit;
}

    //获取储位库存信息
    public static List<wmientity>   getcwkc(String LGNUM,String MATNR,String LGPLA,String par[]){
        // Map<String, Object> result = new HashMap<String, Object>(1024);
        // 创建一个wmientity类型的列表来存储结果
        List<wmientity> reslit = new ArrayList<wmientity>();
        try {
            // 调用sapWmUtil的getcwkc方法获取数据
        	Map<String, Object> result = sapWmUtil.getcwkc(LGNUM,MATNR,LGPLA);
            // 将返回的结果转换为JCoTable类型
            JCoTable restable =  (JCoTable)result.get("IT_OUT");
            // 打印日志信息
            org.jeecgframework.core.util.LogUtil.info("===================获取库存开始===================");
            // 调用getlistbyparandrable方法处理数据
            reslit = getlistbyparandrable(restable,par);
            // 打印日志信息
            org.jeecgframework.core.util.LogUtil.info("===================获取库存结束===================");
        } catch (Exception e) {
            // 打印堆栈跟踪
            e.printStackTrace();
        }
        // 返回处理后的数据列表
        return  reslit;
    }

    //获取储位
    public static List<wmientity>   getcw(String LGNUM,String LGPLA,String par[]){
        // Map<String, Object> result = new HashMap<String, Object>(1024);
        // 创建一个wmientity类型的列表来存储结果
        List<wmientity> reslit = new ArrayList<wmientity>();
        try {
            // 调用sapWmUtil的getcw方法获取数据
        	Map<String, Object> result = sapWmUtil.getcw(LGNUM,LGPLA);
            //  将返回的结果转换为JCoTable类型
            JCoTable restable =  (JCoTable)result.get("IT_OUT");
            // 打印日志信息
            org.jeecgframework.core.util.LogUtil.info("===================获取储位开始===================");
            // 调用getlistbyparandrable方法处理数据
            reslit = getlistbyparandrable(restable,par);
            // 打印日志信息
            org.jeecgframework.core.util.LogUtil.info("===================获取储位结束===================");
        } catch (Exception e) {
            // 打印堆栈跟踪
            e.printStackTrace();
        }
        // 返回处理后的数据列表
        return  reslit;
    }

    // 将JCoTable数据转换为wmientity列表
    private static List<wmientity> getlistbyparandrable(JCoTable restable, String par[]){
        List<wmientity> reslit = new ArrayList<wmientity>();
        for(int i=0;i<restable.getNumRows();i++){
            //wmientity t = new wmientity();
            try {
                // 使用反射获取wmientity类
                Class cl = Class.forName("com.zzjee.wmapi.entity.wmientity");
                // 设置JCoTable的当前行
                restable.setRow(i);
                // 创建wmientity的一个新实例
                Object obj = cl.newInstance();
                for(int j = 1;j<=par.length;j++){
                    // 根据参数生成setter方法名
                    String methodstr = "setWmX"+j;
                    // 获取对应的setter方法
                    Method method = cl.getMethod(methodstr,String.class);
                    // 调用setter方法设置属性值
                    method.invoke(obj,restable.getString(par[j-1]));
                }
                // 强制转换为wmientity类型
                wmientity t = (wmientity) obj;
                // 将处理后的数据添加到列表中
                reslit.add(t);
            }catch (Exception e){
                // 出现异常，可以在这里进行错误处理
            }
        }
        // 返回处理后的数据列表
        return reslit;

    }
}
