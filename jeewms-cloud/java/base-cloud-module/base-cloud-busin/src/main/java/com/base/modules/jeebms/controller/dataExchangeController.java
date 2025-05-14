package com.base.modules.jeebms.controller;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.common.api.vo.Result;

import com.base.common.util.dynamic.db.DynamicDBUtil;

import com.base.modules.jeebms.entity.DiItLog;
import com.base.modules.jeebms.entity.DiItMethod;
import com.base.modules.jeebms.mapper.DiItMethodMapper;
import com.base.modules.jeebms.service.IDiItLogService;
import com.base.modules.jeebms.service.IDiItMethodService;
import com.xkcoding.http.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.online.cgreport.service.a.b;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(tags = "数据交换控制器")
@RestController
@RequestMapping("/di/exchange")
@Slf4j
public class dataExchangeController {
    @Autowired
    private IDiItMethodService diItMethodService;
    @Autowired
    private DiItMethodMapper diItMethodMapper;
    @Autowired
    private IDiItLogService diItLogService;
    @Autowired
    private b cgreportAPIService;

    /**
     * 设备调用，扫描通知
     *
     * @return
     */
//    @AutoLog(value = "执行数据交换")
    @ApiOperation(value = "执行数据交换", notes = "执行数据交换")
    @GetMapping(value = "/run")
    public Result<?> dataexchange(@RequestParam("ruleno") String ruleno,@RequestParam(value = "map",required = false) HashMap<String,String> map) {
        log.info("采集规则；ruleno：{}", ruleno);
        return Result.OK(createmesEmMachineDatacoll(ruleno,map));
    }

    public int createmesEmMachineDatacoll(String ruleno,HashMap<String,String> map) {
        // TODO: 2022/5/13 SAP RFC
        LambdaQueryWrapper<DiItMethod> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DiItMethod::getDiItRuleNo, ruleno);
        List<DiItMethod> list = diItMethodService.list(queryWrapper);
        for (DiItMethod t : list) {
            if ("API".equals(t.getDiDsType().toUpperCase()) && "SQL".equals(t.getDiDdsType().toUpperCase())) {
                try {
                    //        "http://172.26.5.199:8088/device/getDeviceInfo"
                    //接口地址  暂时只能读取返回值为JSONARRAY实体
                    String res = HttpUtil.get(t.getDiSapi());
                    JSONObject jsonres = JSON.parseObject(res);
                    //状态码节点state
                    String rescode = jsonres.get(t.getDiSstatus()).toString();
                    //保存状态码200
                    // TODO: 2022/4/16
                    if (rescode.equals(t.getDiSstatus())) {
                        //数据码节点	data
                        JSONArray recordMap = JSON.parseArray(jsonres.get(t.getDiSdata()).toString());
                        String sql1 = t.getDiDsql();
                        String dcgreportheadkey = t.getDiDdatasource();
                        for (int i = 0; i < recordMap.size(); i++) {
                            //数据保存SQL
                            String sqlout = sql1;
                            JSONObject jsonObject = JSONObject.parseObject(recordMap.getJSONObject(i).toString());
                            for (Map.Entry entry : jsonObject.entrySet()) {
                                sqlout = StringUtils.replace(sqlout, "{" + entry.getKey().toString() + "}", entry.getValue().toString());
                            }
                            try {
                                if (StringUtils.isNullOrEmpty(dcgreportheadkey)) {
                                    diItMethodMapper.creatDatacoll(sqlout);
                                } else {
                                    DynamicDBUtil.findList(dcgreportheadkey, sqlout);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if ("SQL".equals(t.getDiDsType().toUpperCase())
                    && "SQL".equals(t.getDiDdsType().toUpperCase())) {
                String scgreportheadkey = t.getDiSdata();//源数据源
                String sSQL = t.getDiSsql();
                String dcgreportheadkey = t.getDiDdata();//目的数据源
                List<Map<String, Object>> list1 = new ArrayList<>();
                //map替换sql中数据
                if(map != null){
                    for (String key : map.keySet()) {
                       if(t.getDiSsql().contains(key)){
                           sSQL = sSQL.replace(key,"'"+map.get(key)+"'");
                       }
                    }
                }
                if (!StringUtils.isNullOrEmpty(sSQL)){
                if (StringUtils.isNullOrEmpty(scgreportheadkey)) {
                    list1 = diItMethodMapper.creatDatacoll(sSQL);
                } else {
                    list1 = DynamicDBUtil.findList(scgreportheadkey, sSQL);
                }
                }

            String code = t.getDiDatasource();
            try {
                if(StringUtil.isNotEmpty(code)){
                    Map maps = new HashMap(1024);
                    maps.put("getAll", "true");
                    Object result = this.cgreportAPIService.getDataById(code, maps).get("records");
                    list1 = (List<Map<String, Object>>) result;
                    log.info(result.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Map<String, Object> stringObjectMap : list1) {
                String dSQL = t.getDiDsql();
                for (int i = 1; i <= stringObjectMap.size(); i++) {
                    String dataelement = "query" + i;
                    String query01 = "";
                    try {
                        Object o = stringObjectMap.get(dataelement);
                        if (o != null) {
                            query01 = o.toString();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dSQL = StringUtils.replace(dSQL, "{" + dataelement + "}", query01);
                }
                try {
                    if (StringUtils.isNullOrEmpty(dcgreportheadkey)) {
                        String[] split = dSQL.split(";");
                        for (String s : split) {
                            if (!StringUtils.isNullOrEmpty(s)) {
                                diItMethodMapper.creatDatacoll(s);
                            }
                        }
                    } else {
                        DynamicDBUtil.update(dcgreportheadkey, dSQL);
                    }
                    DiItLog diItLog = new DiItLog();
                    diItLog.setCreateTime(new Date());
                    diItLog.setDiItNo(ruleno);
                    diItLog.setDiItName("");
                    diItLog.setRemark("");
                    diItLog.setDiSdata(stringObjectMap.toString());
                    diItLog.setDiSstatus("");
                    diItLog.setDiDrundata(dSQL);
                    diItLog.setDiDrunstatus("");
                    diItLogService.save(diItLog);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
        return 1;
}

}
