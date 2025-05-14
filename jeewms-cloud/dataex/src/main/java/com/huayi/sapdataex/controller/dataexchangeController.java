package com.huayi.sapdataex.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huayi.utils.SapRFC;
import com.huayi.utils.voiceUtil;
import com.sap.conn.jco.JCoTable;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/sap/saprfc")
@Api(tags = "sap接口", value = "/sap接口")
public class dataexchangeController {


    @PostMapping("/texttoVoice")
    @ResponseBody
    public JSONObject texttoVoice(@RequestBody JSONObject jsonObject,
                                  HttpServletResponse response,
                                  HttpServletRequest request) {
        log.info("接口传入内容:{}", jsonObject.toJSONString());
        JSONObject json = new JSONObject();

        try {
            String text = jsonObject.get("text").toString();
            voiceUtil.textToSpeech(text);

        } catch (Exception e) {
            JSONObject CONTROL = new JSONObject();
            CONTROL.put("KEYDATA", "");
            CONTROL.put("TYPE", "E");
            CONTROL.put("MESSAGE", "数据保存错误");
            json.put("CONTROL", CONTROL);
            return json;
        }
        JSONObject CONTROL = new JSONObject();
        CONTROL.put("KEYDATA", "");
        CONTROL.put("TYPE", "S");
        CONTROL.put("MESSAGE", "数据保存成功");
        json.put("CONTROL", CONTROL);
        return json;
    }

    @PostMapping("/exdata")
    @ResponseBody
    public JSONObject exData(@RequestBody JSONObject jsonObject, HttpServletResponse response, HttpServletRequest request) {
        log.info("接口传入内容:{}", jsonObject.toJSONString());
        JSONObject json = new JSONObject();
        Map<String, Object> resmap = new HashMap<>(1024);
        try {
            //RFC名字
            String rfcName = "ZFM_PP01_GET";
            try{
                rfcName =jsonObject.get("rfcName").toString();
            }catch (Exception e){
                e.printStackTrace();
            }

            SapRFC saprfc = SapRFC.getInstance();
            saprfc.prepare(rfcName);
            //rfc 输入参数
            try{
                JSONObject rfcInpara = (JSONObject) jsonObject.get("rfcInpara");
                for (Map.Entry<String, Object> stringObjectEntry : rfcInpara.entrySet()) {
                    saprfc.addParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue().toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            //rfc 输入表
            try{
                JSONObject rfcInTable = (JSONObject) jsonObject.get("rfcInTable");
                for (Map.Entry<String, Object> stringObjectEntry : rfcInTable.entrySet()) {
                    JCoTable tabin = saprfc.getParamTableList(stringObjectEntry.getKey());
                    JSONArray jsonArray = (JSONArray) stringObjectEntry.getValue();
                    int j = 0;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        tabin.insertRow(j);
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Set<String> strings = jsonObject1.keySet();
                        for (String string : strings) {
                            tabin.setValue(string, jsonObject1.get(string).toString());
                        }
                        j++;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            saprfc.execCall();
            //rfc 输出表
            try{
                JSONObject rfcOutTable = (JSONObject) jsonObject.get("rfcOutTable");
                for (Map.Entry<String, Object> stringObjectEntry : rfcOutTable.entrySet()) {
                    JCoTable tab = saprfc.getResultTable(stringObjectEntry.getKey());
                    String[] getvalues = stringObjectEntry.getValue().toString().split(",");
                    List<wmientity> restable = getlistbyparandrable(tab, getvalues);
                    resmap.put(stringObjectEntry.getKey(), restable);
                }
            }catch (Exception e){
                String tableel = "\"AUFNR\",\"KDAUF\",\"KDPOS\",\"KUNNR\",\"ZZNAME1\",\"MATNR\",\"MATXT\",\"conf_date\",\"miss_parts\",\"GAMNG\",\"ZZNETWR\",\"FTRMI\",\"part_date\",\"asse_date\",\"test_date\",\"pain_date\",\"end_date\",\"otd_fc\",\"sttxt\",\"gltrp\",\"ZZTEXT_LINE\"";
                String[] getvalues = tableel.split(",");
                JCoTable tab = saprfc.getResultTable("ET_OUTPUT");

                List<wmientity> restable = getlistbyparandrable(tab, getvalues);
                resmap.put("ET_OUTPUT", restable);
                e.printStackTrace();
            }

        } catch (Exception e) {
            JSONObject CONTROL = new JSONObject();
            CONTROL.put("KEYDATA", "");
            CONTROL.put("TYPE", "E");
            CONTROL.put("MESSAGE", "数据保存错误");
            json.put("CONTROL", CONTROL);
            return json;
        }
        JSONObject CONTROL = new JSONObject();
        CONTROL.put("KEYDATA", resmap);
        CONTROL.put("TYPE", "S");
        CONTROL.put("MESSAGE", "数据保存成功");
        json.put("CONTROL", CONTROL);
        return json;
    }

    private static List<wmientity> getlistbyparandrable(JCoTable restable, String par[]) {
        List<wmientity> reslit = new ArrayList<wmientity>();
        for (int i = 0; i < restable.getNumRows(); i++) {
            wmientity t = new wmientity();
            try {
                Class cl = Class.forName("com.huayi.sapdataex.controller.wmientity");//反射得到类
                restable.setRow(i);
                Object obj = cl.newInstance();//新建一个实例
                for (int j = 1; j <= par.length; j++) {
                    String methodstr = "setWmX" + j;
                    Method method = cl.getMethod(methodstr, String.class);
                    method.invoke(obj, restable.getString(par[j - 1]));
                }
                t = (wmientity) obj;
                reslit.add(t);
            } catch (Exception e) {
            }
        }
        return reslit;
    }
}
