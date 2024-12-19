package com.base.modules.jeebms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeebms.entity.BmsCostDetail;
import com.base.modules.jeebms.entity.BmsCostRuleH;
import com.base.modules.jeebms.entity.BmsCostRuleI;
import com.base.modules.jeebms.entity.BmsCostSource;
import com.base.modules.jeebms.job.CostTaskJob;
import com.base.modules.jeebms.service.IBmsCostDetailService;
import com.base.modules.jeebms.service.IBmsCostRuleHService;
import com.base.modules.jeebms.service.IBmsCostRuleIService;
import com.base.modules.jeebms.service.IBmsCostSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.base.modules.util.CommonConstant.*;
import static com.base.modules.util.CommonConstantMSG.BMS_RULE_MSG;


@Api(tags = "费用管理控制器")
@RestController
@RequestMapping("/bms/api")
@Slf4j
public class BmsApiController {
    ExecutorService executor = Executors.newFixedThreadPool(8);
    @Autowired
    private dataExchangeController dataExchangeController;
    @Autowired
    private IBmsCostSourceService bmsCostSourceService;
    @Autowired
    private IBmsCostDetailService bmsCostDetailService;
    @Autowired
    private IBmsCostRuleHService bmsCostRuleHService;
    @Autowired
    private IBmsCostRuleIService bmsCostRuleIService;

    @Autowired
    private CostTaskJob costTaskJob;

    /**
     * 设备调用，扫描通知
     *
     * @return
     */
    @AutoLog(value = "执行费用计算")
    @ApiOperation(value = "执行费用计算", notes = "执行费用计算")
    @GetMapping(value = "/run")
    public Result<?> bmsrun(@RequestParam("ruleno") String ruleno,@RequestParam(value = "map",required=false) HashMap<String,String> map) {
        log.info("执行费用计算规则；ruleno：{}", ruleno);
        String[] split = ruleno.split(";");
        for (String s : split) {
            costruntask(s,map);
        }
        return Result.OK();
    }
    private String  costruntask(String ruleno,HashMap<String,String> map){
        String result = "";
        //1,生成数据到计费来源   调用数据集成的方法  ruleno和计费类型一样
        try {
            if(map != null){
                dataExchangeController.dataexchange(ruleno,map);
            }else {
                dataExchangeController.dataexchange(ruleno,null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //2,读取计费来源 读取未计算的费用来源
        LambdaQueryWrapper<BmsCostSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BmsCostSource::getStatus,"未计算");
        queryWrapper.eq(BmsCostSource::getCostSNo,ruleno);
        List<BmsCostSource> listbmsds = bmsCostSourceService.list(queryWrapper);
        //3,循环遍历计费来源，匹配计费规则，生成费用明细，并更新费用来源
        //推送集合
        List<BmsCostDetail> list = new ArrayList<>();
        for (BmsCostSource listbmsd : listbmsds) {
            //4.1,赋值基本信息。
            BmsCostDetail bmsCostDetail = genbmscostdetailbybcs(listbmsd);
            //4.2,计算费用。
            bmsCostDetail = countcost(listbmsd,bmsCostDetail);
            //4.3,保存费用。
            bmsCostDetailService.save(bmsCostDetail);
            //4.4,更新费用来源。
            listbmsd.setStatus(BUSI_DEFAULT_STATUS_1);
            bmsCostSourceService.updateById(listbmsd);
            list.add(bmsCostDetail);
        }
        return  result;
    }
    private BmsCostDetail genbmscostdetailbybcs(BmsCostSource listbmsd){
        BmsCostDetail bmsCostDetail = new BmsCostDetail();
        bmsCostDetail.setCreateBy(BUSI_DEFAULT_CREATE_BY);
        bmsCostDetail.setCreateTime(new Date());
        bmsCostDetail.setSysOrgCode(listbmsd.getSysOrgCode());
        bmsCostDetail.setIsDel(0);
        bmsCostDetail.setTenantId(listbmsd.getTenantId());
        bmsCostDetail.setCostSoNo(listbmsd.getCostSoNo());
        bmsCostDetail.setCostSoName(listbmsd.getCostSoName());
        bmsCostDetail.setCostObjType(listbmsd.getCostObjType());
        bmsCostDetail.setCostObjNo(listbmsd.getCostObjNo());
        bmsCostDetail.setCostObjName(listbmsd.getCostObjName());
        bmsCostDetail.setCostSoSum(listbmsd.getCostSoSum());
        bmsCostDetail.setCostSoUnit(listbmsd.getCostSoUnit());
        bmsCostDetail.setCostSoDate(listbmsd.getCostSoDate());
        return bmsCostDetail;
    }
    private BmsCostDetail countcost(BmsCostSource listbmsd,BmsCostDetail bmsCostDetail){
        //根据来源编号，计费对象类型 计费对象编号 来源日期 获取计费规则
        /**来源类型编号*/
        String costSNo = listbmsd.getCostSNo();
        /**计费对象类型*/
        String costObjType = listbmsd.getCostObjType();
        /**计费对象编号*/
        String costObjNo = listbmsd.getCostObjNo();
        /**来源日期*/
        Date costSoDate = listbmsd.getCostSoDate();
        LambdaQueryWrapper<BmsCostRuleH> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BmsCostRuleH::getCostSNo,costSNo);
        queryWrapper.eq(BmsCostRuleH::getCostObjType,costObjType);
        queryWrapper.like(BmsCostRuleH::getCostObjNo,costObjNo);
        queryWrapper.le(BmsCostRuleH::getCostValidBegin,costSoDate);
        queryWrapper.ge(BmsCostRuleH::getCostValidEnd,costSoDate);
        BmsCostRuleH bmsCostRuleH = bmsCostRuleHService.getOne(queryWrapper,false);
        // 获取到计费规则，根据计费规则获取规则子表
        if(bmsCostRuleH==null){
            bmsCostDetail.setCostDesc(BMS_RULE_MSG);
            bmsCostDetail.setStatus(TASK_DAYPLAN_STATUS_WSC);
            return bmsCostDetail;
        }
        LambdaQueryWrapper<BmsCostRuleI> queryWrapperi = new LambdaQueryWrapper<>();
        queryWrapperi.eq(BmsCostRuleI::getContNo,bmsCostRuleH.getContNo());
        queryWrapperi.le(BmsCostRuleI::getBeginSum,bmsCostDetail.getCostSoSum());
        queryWrapperi.ge(BmsCostRuleI::getEndSum,bmsCostDetail.getCostSoSum());
        BmsCostRuleI bmsCostRuleI = bmsCostRuleIService.getOne(queryWrapperi, false);
        bmsCostDetail.setCostNo(bmsCostRuleH.getCostNo());
        bmsCostDetail.setCostName(bmsCostRuleH.getCostName());
        bmsCostDetail.setCostDesc(bmsCostRuleH.getCostName());
        bmsCostDetail.setCostRuleNo(bmsCostRuleH.getCostRuleNo());
        bmsCostDetail.setCostRuleName(bmsCostRuleH.getCostRuleName());
        bmsCostDetail.setCostRuleItemNo(bmsCostRuleI.getItemNo());
        bmsCostDetail.setCostTypeNo(bmsCostRuleI.getCostTypeNo());
        bmsCostDetail.setBeginSum(bmsCostRuleI.getBeginSum());
        bmsCostDetail.setEndSum(bmsCostRuleI.getEndSum());
        bmsCostDetail.setCostUnit(bmsCostRuleI.getUnit());
        bmsCostDetail.setCostJfgz(bmsCostRuleI.getCostUnit());
        bmsCostDetail.setCostSl(bmsCostRuleI.getCostSl());
        bmsCostDetail.setCostHsj(bmsCostRuleI.getCostHsj());
        bmsCostDetail.setCostHb(bmsCostRuleI.getCostHb());
        bmsCostDetail.setWhNo(listbmsd.getWhNo());
        bmsCostDetail.setImNo(listbmsd.getImNo());
        bmsCostDetail.setId(listbmsd.getId());

        BigDecimal costbhsj = new BigDecimal("0");
        BigDecimal costyhsj = new BigDecimal("0");
        //来源数量
        BigDecimal sosum =  bmsCostDetail.getCostSoSum();
        //开始值
        BigDecimal beginSum = bmsCostRuleI.getBeginSum();
        //结束值
        BigDecimal endSum = bmsCostRuleI.getEndSum();
        //区间开始值
        BigDecimal qjbeginSum = bmsCostRuleI.getQjbeginSum();
        //区间结束值
        BigDecimal qjendSum = bmsCostRuleI.getQjendSum();
        //单位数量
        BigDecimal unitnumber = bmsCostRuleI.getUnitnumber();
        //递增单价
        BigDecimal qjPrice = bmsCostRuleI.getQjPrice();
        //含税单价
        BigDecimal costHsj = bmsCostDetail.getCostHsj();
        //不含税单价
        BigDecimal costJfgz = bmsCostDetail.getCostJfgz();

        if(qjbeginSum != null
                && qjendSum !=null
                && unitnumber !=null
                && qjPrice !=null){
            //来源数量 > 计费开始数量 && 来源数量 < 区间开始值
            if(sosum.compareTo(beginSum) == 1 && qjbeginSum.compareTo(sosum) == 1 ){
                costbhsj = bmsCostDetail.getCostJfgz().multiply(sosum);
                costyhsj = bmsCostDetail.getCostHsj().multiply(sosum);
            }else if(sosum.compareTo(qjbeginSum) == 1){
                if(sosum.compareTo(qjendSum) == 1){
                    sosum = qjendSum;
                }
                BigDecimal jtsum = qjendSum.subtract(qjbeginSum);
                //得到余数
                BigDecimal r = jtsum.remainder(unitnumber);
                //区间结束值 - 余数 / 单位数量
                BigDecimal divide = (jtsum.subtract(r)).divide(unitnumber);
                //价格 * 倍数
                BigDecimal dzprice = divide.multiply(qjPrice);
                //含税区间价格
                BigDecimal qjprice = dzprice.add(costJfgz);
                //不含税区间价格
                BigDecimal qjprice2 = dzprice.add(costHsj);
//                //不含税价格
//                costbhsj = qjprice.multiply(sosum);
//                //含税价格
//                costyhsj = qjprice2.multiply(sosum);

            }
        }else {
            costbhsj = bmsCostDetail.getCostJfgz().multiply(bmsCostDetail.getCostSoSum());
            costyhsj = bmsCostDetail.getCostHsj().multiply(bmsCostDetail.getCostSoSum());
        }
        bmsCostDetail.setCostCoBhsj(costbhsj);

        bmsCostDetail.setCostCoYhsj(costyhsj);


        bmsCostDetail.setStatus(TASK_DAYPLAN_STATUS_WSC);
        return bmsCostDetail;
    }





}
