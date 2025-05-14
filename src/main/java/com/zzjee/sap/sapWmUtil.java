package com.zzjee.sap;

import com.sap.conn.jco.JCoTable;
import org.jeecgframework.core.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 提供与SAP系统的WM模块交互的工具类，主要负责调用SAP的RFC函数。
 */
public class sapWmUtil {
   //获取储位
    public  static Map<String,Object> getWmbin(String lgnum) {
        Map<String, Object> result = new HashMap<String, Object>(1024);
        try {
            // 创建SAP RFC连接实例
            SapRFC saprfc = SapRFC.getInstance();
            // 准备调用SAP函数Z_WM_GET_LAGP
            saprfc.prepare("Z_WM_GET_LAGP");
            // 添加参数LGNUM
            saprfc.addParameter("LGNUM", lgnum);
            // 执行RFC调用
            saprfc.execCall();
            //  saprfc.getParamTableList()
            // 获取输出表IT_OUT
            JCoTable tab = saprfc.getParamTableList("IT_OUT");
            // 打印输出表的行数
            System.out.print("rows:" + tab.getNumRows());
            // 将输出表添加到结果Map中
            result.put("IT_OUT", tab);
        } catch (Exception e) {
            // 打印异常信息
            System.out.print(e.getMessage());
        }
        return result;
    }

    /**
     * 获取物流凭证信息
     * @param mblnr 物流凭证编号
     * @return 包含物流凭证信息的Map，其中键为"IT_OUT"，值为包含物流凭证信息的JCoTable对象
     */
    public  static Map<String,Object> getWmmseg(String mblnr) {
        Map<String, Object> result = new HashMap<String, Object>(1024);
        try {
            // 创建SAP RFC连接实例
            SapRFC saprfc = SapRFC.getInstance();
            // 准备调用SAP函数Z_WM_GET_LAGP
            saprfc.prepare("Z_WM_GET_LAGP");
            saprfc.addParameter("LGNUM", mblnr);
            // 执行RFC调用
            saprfc.execCall();
            // 获取输出表IT_OUT
            JCoTable tab = saprfc.getResultTable("IT_OUT");
            // 打印输出表的行数
            System.out.print("rows:" + tab.getNumRows());
            // 将输出表添加到结果Map中
            result.put("IT_OUT", tab);
        } catch (Exception e) {
            // 打印异常信息
            System.out.print(e.getMessage());
        }
        // 返回结果
        return result;
    }

    /*
    * 获取WM单据信息
    * @param DOCID 单据ID
    * @param DOCTYPE 单据类型
    * @param tablename 表格名称
    * @return 包含WM单据信息的Map，其中键为"IT_OUT"，值为包含WM单据信息的JCoTable对象
    */
    public  static Map<String,Object> getWmdoc(String DOCTYPE,String DOCID,String tablename) {
        Map<String, Object> result = new HashMap<String, Object>(1024);
        try {
            // 创建SAP RFC连接实例
            SapRFC saprfc = SapRFC.getInstance();
            // 准备调用SAP函数Z_WM_GET_WM_DOC
            saprfc.prepare("Z_WM_GET_WM_DOC");
            // 添加参数DOCTYPE
            saprfc.addParameter("DOCTYPE", DOCTYPE);
            // 添加参数DOCID
            saprfc.addParameter("DOCID", DOCID);
            // 执行RFC调用
            saprfc.execCall();
            // 获取指定表格名称的输出表
            JCoTable tab = saprfc.getResultTable(tablename);
            // 将输出表添加到结果Map中
            result.put("IT_OUT", tab);
        } catch (Exception e) {
            // 打印异常信息
            System.out.print(e.getMessage());
        }
        // 返回结果Map
        return result;
    }

    //获取储位
    public  static Map<String,Object> getcw(String LGNUM,String LGPLA) {
        Map<String, Object> result = new HashMap<String, Object>(1024);
        try {
            // 创建SapRFC对象
            SapRFC saprfc = SapRFC.getInstance();
            saprfc.prepare("Z_WM_GET_LAGP");
            if(StringUtil.isNotEmpty(LGNUM)){
                saprfc.addParameter("LGNUM", LGNUM);
            }
            if(StringUtil.isNotEmpty(LGPLA)){
                saprfc.addParameter("LGPLA", LGPLA);
            }
            saprfc.execCall();
            JCoTable tab = saprfc.getResultTable("IT_OUT");
            result.put("IT_OUT", tab);
        } catch (Exception e) {
            // 输出异常信息
            System.out.print(e.getMessage());
        }
        // 返回结果
        return result;
    }

    public  static Map<String,Object> getcwkc(String LGNUM,String MATNR,String LGPLA) {
        Map<String, Object> result = new HashMap<String, Object>(1024);
        try {
            // 创建SapRFC对象
            SapRFC saprfc = SapRFC.getInstance();
            saprfc.prepare("Z_WM_GET_LQUA");
            if(StringUtil.isNotEmpty(LGNUM)){
                saprfc.addParameter("LGNUM", LGNUM);
            }
            if(StringUtil.isNotEmpty(MATNR)){
                saprfc.addParameter("MATNR", MATNR);
            }
            if(StringUtil.isNotEmpty(LGPLA)){
                saprfc.addParameter("LGPLA", LGPLA);
            }
            saprfc.execCall();
            JCoTable tab = saprfc.getResultTable("IT_OUT");
            result.put("IT_OUT", tab);
        } catch (Exception e) {
            // 输出异常信息
            System.out.print(e.getMessage());
        }
        // 返回结果
        return result;
    }

}
