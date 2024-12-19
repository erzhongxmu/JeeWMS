package com.huayi.sapdataex;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoTable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SapdataexApplicationTests {
    @Test
    void contextLoads() {

//        JSONObject jsonroot = new JSONObject();
//        jsonroot.put("rfcName","Z_WM_GET_BIN");
//        JSONObject jsoninpara = new JSONObject();
//        jsoninpara.put("MATNR","BJ3085B12");
//        jsoninpara.put("MTYPE","I");
//        jsonroot.put("rfcInPara",jsoninpara);
//        JSONObject jsonout = new JSONObject();
//        String  it_out_el = "WERKS," +
//                "LGORT," +
//                "MATNR," +
//                "CHARG," +
//                "MENGE";
//        jsonout.put("IT_OUT",it_out_el);
//        jsonroot.put("rfcOutTable",jsonout);
//
//        String url = "http://localhost:8082/dataex/sap/saprfc/exdata";
//        System.out.println(  HttpUtil.post(url,jsonroot.toJSONString()));




//
//        JSONObject jsonroot = new JSONObject();
//        jsonroot.put("rfcName","Z_DIGI_IN");
//        JSONObject jsoninTable = new JSONObject();
//        JSONArray jsonArray  =  new JSONArray();//客户号
//        JSONObject jsonObject1 = new JSONObject();
//        jsonObject1.put("QUERY01","QUERY011");
//        jsonObject1.put("QUERY02","QUERY021");
//        jsonArray.add(jsonObject1);
//        JSONObject jsonObject2 = new JSONObject();
//        jsonObject2.put("QUERY01","QUERY012");
//        jsonObject2.put("QUERY02","QUERY022");
//        jsonArray.add(jsonObject2);
//        jsoninTable.put("IT_IN",jsonArray);
//        jsonroot.put("rfcInTable",jsoninTable);
//        JSONObject jsonout = new JSONObject();
//        String  it_out_el = "QUERY01," +
//                "QUERY02" ;
//        jsonout.put("IT_OUT",it_out_el);
//        jsonroot.put("rfcOutTable",jsonout);
//
//        String url = "http://localhost:8082/dataex/sap/saprfc/exdata";
//        System.out.println(  HttpUtil.post(url,jsonroot.toJSONString()));

    }

}
