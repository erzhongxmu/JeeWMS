package com.base.modules.jeewms.controller;

import cn.hutool.core.util.ObjectUtil;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.WmImNoticeI;
import com.base.modules.jeewms.service.IBiCService;
import com.base.modules.jeewms.vo.Highchart;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 首页数据展示
 * @Author: base-boot
 * @Date: 2021-05-17
 * @Version: V1.0
 */
@RestController
@RequestMapping("/jeewms/bic")
@Slf4j
public class BiController extends JeecgController<WmImNoticeI, IBiCService> {

    @Autowired
    private IBiCService biCService;

    @AutoLog(value = "首页-数据")
    @ApiOperation(value = "首页-数据", notes = "首页-数据")
    @GetMapping(value = "/homebi")
    public Result<?> bi() {
        String num1 = "0";
        String num2 = "0";
        String num3 = "0";
        String num4 = "0";
        String num5 = "0";   //库存转移计划中的数量
        if(ObjectUtil.isNotEmpty(biCService.getNum1())){
            num1 = biCService.getNum1().get("linecount").toString();
        }
        if(ObjectUtil.isNotEmpty(biCService.getNum2())){
            num2 = biCService.getNum2().get("linecount").toString();
        }
        if(ObjectUtil.isNotEmpty(biCService.getNum3())){
            num3 = biCService.getNum3().get("linecount").toString();
        }
        if(ObjectUtil.isNotEmpty(biCService.getNum4())){
            num4 = biCService.getNum4().get("linecount").toString();
        }
        if(ObjectUtil.isNotEmpty(biCService.getNum5())){
            num5 = biCService.getNum5().get("linecount").toString();
        }
        Map<String, Object> map = new HashMap<>(1024);
        map.put("num1", num1);
        map.put("num2", num2);
        map.put("num3", num3);
        map.put("num4", num4);
        map.put("num5", num5);

        return Result.ok(map);
    }

    /**
     * highchart
     *
     * @return
     */
    @AutoLog(value = "首页-近七日下架数量")
    @ApiOperation(value = "首页-近七日下架数量", notes = "首页-近七日下架数量")
    @GetMapping(value = "/dayCount")
    public Result<?> dayCountmonth(HttpServletRequest request, String reportType, HttpServletResponse response) {
        List<Highchart> list = new ArrayList<Highchart>();
        Highchart hc = new Highchart();
        List<Map<String, Object>> maplist = biCService.getDayCountmonth();
        List lt = new ArrayList();
        hc.setName("近七日下架数量");
        hc.setType(reportType);
        Map<String, Object> map;
        if (maplist.size() > 0) {
            for (Map<String, Object> object : maplist) {
                map = new HashMap<String, Object>(1024);
                Map<String, Object> obj = object;
                try {

                    map.put("name", obj.get("create_time").toString());
                    map.put("y", (int) Double.parseDouble(obj.get("amount").toString()));
                    map.put("date", obj.get("date").toString());
                } catch (Exception e) {

                }
                Double groupCount = 0.00;
                try {
                    groupCount = Double.parseDouble(obj.get("amount").toString());
                } catch (Exception e) {

                }
                lt.add(map);
            }
        }

        hc.setData(lt);
        list.add(hc);
        return Result.ok(list);
    }


    /**
     * highchart
     *
     * @return
     */
    @AutoLog(value = "首页-上架数量前6")
    @ApiOperation(value = "首页-上架数量前6", notes = "首页-上架数量前6")
    @GetMapping(value = "/cpNameupCount")
    public Result<?> studentCountmonth(HttpServletRequest request, String reportType, HttpServletResponse response) {
        List<Highchart> list = new ArrayList<Highchart>();
        Highchart hc = new Highchart();
        List<Map<String, Object>> maplist = biCService.getStudentCountmonth();
        List lt = new ArrayList();
        //hc = new Highchart();
        hc.setName("上架数量前6");
        hc.setType(reportType);
        Map<String, Object> map;

        if (maplist.size() > 0) {
            for (Map<String, Object> object : maplist) {
                map = new HashMap<String, Object>(1024);
                Map<String, Object> obj = object;
                map.put("name", obj.get("goodsid").toString());
                map.put("y", (int) Double.parseDouble(obj.get("amount").toString()));
                lt.add(map);
            }
        }

        hc.setData(lt);
        list.add(hc);
        return Result.ok(list);
    }

    /**
     * highchart
     *
     * @return
     */
    @AutoLog(value = "首页-下架数量前6")
    @ApiOperation(value = "首页-上架数量前6", notes = "首页-下架数量前6")
    @GetMapping(value = "/cpNamedownCount")
    public Result<?> studentCount(HttpServletRequest request, String reportType, HttpServletResponse response) {
        List<Highchart> list = new ArrayList<Highchart>();
        Highchart hc = new Highchart();
        List<Map<String, Object>> maplist = biCService.getStudentCount();
        List lt = new ArrayList();
        //hc = new Highchart();
        hc.setName("下架数量前6");
        hc.setType(reportType);
        Map<String, Object> map;

        if (maplist.size() > 0) {
            for (Map<String, Object> object : maplist) {
                map = new HashMap<String, Object>(1024);
                Map<String, Object> obj = object;
                map.put("name", obj.get("goodsid").toString());
                map.put("y", (int) Double.parseDouble(obj.get("amount").toString()));
                map.put("y1", (int) Double.parseDouble(obj.get("amountd").toString()));
                lt.add(map);
            }
        }

        hc.setData(lt);
        list.add(hc);
        return Result.ok(list);
    }
}
