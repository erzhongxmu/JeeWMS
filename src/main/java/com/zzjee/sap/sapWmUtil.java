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
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            // 创建SAP RFC连接实例
            SapRFC saprfc = SapRFC.getInstance();
            // 准备调用SAP函数Z_WM_GET_LAGP
            saprfc.prepare("Z_WM_GET_LAGP");
            // 添加参数LGNUM
            saprfc.addParameter("LGNUM", lgnum);
            // 执行RFC调用
            saprfc.execCall();
//            saprfc.getParamTableList()
            // 获取输出表IT_OUT
            JCoTable tab = saprfc.getParamTableList("IT_OUT");
            // 打印输出表的行数
            System.out.print("rows:" + tab.getNumRows());
            result.put("IT_OUT", tab);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return result;
    }


    //获取物流凭证
    public  static Map<String,Object> getWmmseg(String mblnr) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SapRFC saprfc = SapRFC.getInstance();
            saprfc.prepare("Z_WM_GET_LAGP");
            saprfc.addParameter("LGNUM", mblnr);
            saprfc.execCall();
            JCoTable tab = saprfc.getResultTable("IT_OUT");
            System.out.print("rows:" + tab.getNumRows());
            result.put("IT_OUT", tab);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return result;
    }


    //获取WM单据
    //
    //DOCID
    public  static Map<String,Object> getWmdoc(String DOCTYPE,String DOCID,String tablename) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SapRFC saprfc = SapRFC.getInstance();
            saprfc.prepare("Z_WM_GET_WM_DOC");
            saprfc.addParameter("DOCTYPE", DOCTYPE);
            saprfc.addParameter("DOCID", DOCID);
            saprfc.execCall();
            JCoTable tab = saprfc.getResultTable(tablename);
            result.put("IT_OUT", tab);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return result;
    }

    //获取储位
    //
    public  static Map<String,Object> getcw(String LGNUM,String LGPLA) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
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
            System.out.print(e.getMessage());
        }
        return result;
    }
    //
    public  static Map<String,Object> getcwkc(String LGNUM,String MATNR,String LGPLA) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
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
            System.out.print(e.getMessage());
        }
        return result;
    }


}
