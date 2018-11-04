package com.zzjee.sap;

import com.sap.conn.jco.JCoTable;
import org.jeecgframework.core.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class sapWmUtil {
   //获取储位
    public  static Map<String,Object> getWmbin(String lgnum) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SapRFC saprfc = SapRFC.getInstance();
            saprfc.prepare("Z_WM_GET_LAGP");
            saprfc.addParameter("LGNUM", lgnum);
            saprfc.execCall();
//            saprfc.getParamTableList()
            JCoTable tab = saprfc.getParamTableList("IT_OUT");
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

    //获取镭射清单
    //
    public  static Map<String,Object> getWmlsqd(String ZLSQD) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SapRFC saprfc = SapRFC.getInstance();
            saprfc.prepare("Z_WM_CCS_SAP");
            saprfc.addParameter("ZLSQD", ZLSQD);
            saprfc.addParameter("ZCCSTYPE", "R");
            saprfc.execCall();
            JCoTable tab = saprfc.getResultTable("IT_OUT");
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



    //获取镭射清单
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

    //更新镭射清单
    //
    public  static Map<String,Object> updateWmlsqd(Map<String,Object> inmap) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SapRFC saprfc = SapRFC.getInstance();
            saprfc.prepare("Z_WM_CCS_SAP");
            saprfc.addParameter("ZLSQD", inmap.get("ZLSQD").toString());
            saprfc.addParameter("ZCCSTYPE", inmap.get("ZCCSTYPE").toString());
            JCoTable tabin = saprfc.getParamTableList("IT_IN");
            tabin.appendRow();
            tabin.appendRows(0);
            tabin.setValue("ZLSQD","1");
            tabin.setValue("ZPGDH","1");
            tabin.setValue("AUFNR","1");
            saprfc.execCall();
            JCoTable tabout = saprfc.getResultTable("IT_OUT");
            System.out.print("rows:" + tabout.getNumRows());
            result.put("IT_OUT", tabout);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return result;
    }

}
