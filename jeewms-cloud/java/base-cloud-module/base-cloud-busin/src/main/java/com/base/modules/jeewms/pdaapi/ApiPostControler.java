package com.base.modules.jeewms.pdaapi;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.base.common.api.vo.Result;
import com.base.common.system.vo.LoginUser;
import com.base.common.system.vo.SysUserCacheInfo;
import com.base.common.util.DateUtils;
import com.base.modules.jeeerp.controller.BusiOrdPriceController;
import com.base.modules.jeeerp.entity.BusiOm;
import com.base.modules.jeeerp.entity.BusiOrdPrice;
import com.base.modules.jeeerp.entity.BusiPo;
import com.base.modules.jeeerp.entity.BusiPrdOrdItem;
import com.base.modules.jeeerp.service.IBusiOmService;
import com.base.modules.jeeerp.service.IBusiPoService;
import com.base.modules.jeeerp.service.IBusiPrdOrdItemService;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.service.impl.GoodsMoveTask;
import com.base.modules.jeewms.service.impl.SmsSendImpl;
import com.base.modules.util.CommonConstant;
import com.base.modules.util.ConstUtil;
import com.base.modules.util.GenerateCodeUtil;
import com.base.modules.util.StringUtil;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import net.sf.saxon.event.IDFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.shiro.SecurityUtils;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.SysUserRemoteApi;
import org.jeecg.common.system.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.hutool.core.util.ArrayUtil.remove;

@Api(tags = "收货通知详细")
@RestController
@RequestMapping("/jeewms/pdaPapi")
@Slf4j
public class ApiPostControler {
    ExecutorService executor = Executors.newFixedThreadPool(8);
    @Autowired
    private IMdCusService mdCusService;
    @Autowired
    private IWmInQmIService wmInQmIService;
    @Autowired
    private IWmImNoticeHService wmImNoticeHService;
    @Autowired
    private IWmImNoticeIService wmImNoticeIService;
    @Autowired
    private IWmsPdaService wmsPdaService;
    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;
    @Autowired
    private IWmOmNoticeIService wmOmNoticeIService;
    @Autowired
    private IWmOmQmIService wmOmQmIService;
    @Autowired
    private IWmToMoveGoodsService wmToMoveGoodsService;
    @Autowired
    private IWmSttInGoodsService wmSttInGoodsService;
    @Autowired
    private IWmToDownGoodsService wmToDownGoodsService;

    @Autowired
    private IWvStockSttQueryService wvStockSttQueryService;

    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private GoodsMoveTask moveTask;

    @Autowired
    private IWmTuopanService tuopanService;


    @Autowired
    private SmsSendImpl smsSend;


    @Autowired
    private BusiOrdPriceController IBusiOrdPriceController;

    @Autowired
    private IBusiPrdOrdItemService busiPrdOrdItemService;
    @Autowired
    private IBusiPoService busiPoService;
    @Autowired
    private SysUserRemoteApi sysUserRemoteApi;

    @AutoLog(value = "pda操作记录")
    @ApiOperation(value = "pda操作记录", notes = "大区信息-分页列表查询")
    @PostMapping(value = "/postdata")
    public Result<?> postdata(@RequestBody ApiEntity apiEntity, HttpServletRequest req) throws Exception {
          {
            String username = apiEntity.getUsername();
            String listtype = apiEntity.getListtype();
            String project = apiEntity.getProject();
            if (StringUtils.isEmpty(listtype)) {
                return Result.error("参数listtype不能为空！");
            }
            if (listtype.equals(CONSTANTTYPE.listtype01)) {

                if ("PLTN".equals(project)){
                    if (StringUtils.isEmpty(apiEntity.getQuery08())){
                        throw new JeecgBootException("数量不能为空");
                    }
                    String query15 = apiEntity.getQuery15();
                    String query08 = apiEntity.getQuery08();
                    if(new BigDecimal(query15).compareTo(new BigDecimal(query08)) == 1){
                        throw new JeecgBootException("本次入库数量小于箱数");
                    }
                    //取余数
                    BigDecimal bigDecimal = new BigDecimal(query08).divideAndRemainder(new BigDecimal(query15))[1];
                    //入库数量
                    BigDecimal sub = NumberUtil.sub(query08, bigDecimal.toString());
                    //箱数
                    String div = NumberUtil.div(sub.toString(), query15).toString();
                    WmImNoticeI wmImNoticeI2 = wmImNoticeIService.getById(apiEntity.getId());
                    //WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, wmImNoticeI.getImNoticeId()).one();
                    Integer divs = Convert.toInt(div);
                    List<String> tinids = new ArrayList<>();
                    synchronized (this){
                        List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                        for (int i = 0; i < divs; i++) {
                            WmImNoticeI wmImNoticeI =  new WmImNoticeI();
                            BeanUtils.copyProperties(wmImNoticeI2,wmImNoticeI);
                            //箱号
//                            String tinid = wmImNoticeI2.getGoodsCode()+"-"+ generateCodeUtil.generateCode(CommonConstant.TABLE_NAME_WMS_TIN, CommonConstant.BASE_DEFAULT_CODE_TYPE_10);

                            String tinid = String.valueOf(System.currentTimeMillis());
                            Thread.sleep(3);
                            //托盘号
                            wmImNoticeI.setTinId(tinid);
                            //长
                            wmImNoticeI.setTinLength(apiEntity.getQuery17());
                            //宽
                            wmImNoticeI.setTinWidth(apiEntity.getQuery18());
                            //高
                            wmImNoticeI.setTinHigh(apiEntity.getQuery19());
                            //体积
//                            BigDecimal decimal = NumberUtil.mul(apiEntity.getQuery17(), apiEntity.getQuery18(), apiEntity.getQuery19());

                            if (StringUtils.isNotEmpty(apiEntity.getQuery07()) && apiEntity.getQuery07().indexOf(",") > 0) {
                                wmImNoticeI.setBinPlan(apiEntity.getQuery07().split(",")[1]);
                            } else {
                                wmImNoticeI.setBinPlan(apiEntity.getQuery07());
                            }
                            //重量
                            wmImNoticeI.setTinWeight(apiEntity.getQuery20());
                            wmImNoticeI.setGoodsQmCount(apiEntity.getQuery15());
                            wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery15());
                            wmImNoticeI.setShenqingsl(apiEntity.getQuery15());
                            wmImNoticeI.setTuoNum(apiEntity.getQuery15());
                            wmImNoticeI.setGoodsBatch(apiEntity.getQuery06());
                            wmImNoticeI.setGoodsPrdData(new Date());
                            wmImNoticeIList.add(wmImNoticeI);
                            tinids.add(tinid);
                        }
                        wmInQmIService.batchAdd(wmImNoticeIList);
                    }
                    try{
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                smsSend.labelPrinting(wmImNoticeI2.getId(),tinids,div,query15);
                                //打印箱唛
                                if (StringUtils.isNotEmpty(apiEntity.getQuery16()) && "1".equals(apiEntity.getQuery16())){
                                    smsSend.labelPrints(wmImNoticeI2.getId(),tinids,div,query15);
                                }
                            }
                        });
                    }catch (Exception e){
                    }
                    return Result.ok("操作成功");
                }else {
                    if (StringUtils.isEmpty(apiEntity.getQuery08())){
                        throw new JeecgBootException("数量不能为空");
                    }
                    List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                    WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(apiEntity.getId());
                    wmImNoticeI.setGoodsBatch(apiEntity.getQuery06());
                    wmImNoticeI.setTinId("A");
                    if (StringUtils.isNotEmpty(apiEntity.getQuery07()) && apiEntity.getQuery07().indexOf(",") > 0) {
                            wmImNoticeI.setBinPlan(apiEntity.getQuery07().split(",")[1]);
                    } else {
                        wmImNoticeI.setBinPlan(apiEntity.getQuery07());
                    }
                    wmImNoticeI.setGoodsQmCount(apiEntity.getQuery08());
                    wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery08());
                    wmImNoticeI.setShenqingsl(apiEntity.getQuery08());
                    wmImNoticeI.setGoodsPrdData(new Date());
                    wmImNoticeIList.add(wmImNoticeI);
                    wmInQmIService.batchAdd(wmImNoticeIList);
                    return Result.ok("操作成功");
                }

            } else if (listtype.equals(CONSTANTTYPE.listtype02)) {
                WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(apiEntity.getId());
                QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("notice_id", wmImNoticeI.getImNoticeId());
                WmImNoticeH wmImNoticeH = wmImNoticeHService.getOne(queryWrapper);
                wmImNoticeI.setU8Sta("1");
                wmImNoticeIService.updateById(wmImNoticeI);
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype03)) {

                if (StringUtils.isEmpty(apiEntity.getQuery13())){
                    throw new JeecgBootException("数量不能为空");
                }
                //待入库
                String query12 = apiEntity.getQuery12();
                //本次入库数量
                String query13 = apiEntity.getQuery13();
                if(new BigDecimal(query13).compareTo(new BigDecimal(query12)) == 1){
                    throw new JeecgBootException("本次入库数量大于待入库数量");
                }
                if ("SW".equals(project) || "GS".equals(project)) {
                    if (StringUtils.isEmpty(apiEntity.getQuery07())) {
                        throw new JeecgBootException("日期不能为空");
                    }
                    if (StringUtils.isEmpty(apiEntity.getQuery03())) {
                        throw new JeecgBootException("批次不能为空");
                    }
                    WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(apiEntity.getId());
                    List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
//                wmImNoticeI.setTinId("A");
                    wmImNoticeI.setTinId(apiEntity.getQuery09());

                    if (StringUtils.isNotEmpty(apiEntity.getQuery10()) && apiEntity.getQuery10().indexOf(",") > 0) {
                        wmImNoticeI.setBinPlan(apiEntity.getQuery10().split(",")[1]);
                    } else {
                        wmImNoticeI.setBinPlan(apiEntity.getQuery10());
                    }
                    wmImNoticeI.setGoodsQmCount(apiEntity.getQuery13());
                    wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery13());
                    wmImNoticeI.setShenqingsl(apiEntity.getQuery13());
                    wmImNoticeI.setGoodsBatch(apiEntity.getQuery03());
                    wmImNoticeI.setTenantId(apiEntity.getTenantId());
                    try {
                        wmImNoticeI.setGoodsPrdData(DateUtils.str2Date(apiEntity.getQuery07(), DateUtils.date_sdf.get()));

                    } catch (Exception e) {

                    }
                    wmImNoticeIList.add(wmImNoticeI);
                    wmInQmIService.batchAdd(wmImNoticeIList);
                    return Result.ok("操作成功");
                } else if ("PLTN".equals(project)) {
                    String query15 = apiEntity.getQuery15();//每箱数量

                    if(new BigDecimal(query15).compareTo(new BigDecimal(query13)) == 1){
                        throw new JeecgBootException("本次入库数量小于箱数");
                    }
                    //取余数
                    BigDecimal bigDecimal = new BigDecimal(query13).divideAndRemainder(new BigDecimal(query15))[1];
                    //入库数量
                    BigDecimal sub = NumberUtil.sub(query13, bigDecimal.toString());
                    //箱数
                    String div = NumberUtil.div(sub.toString(), query15).toString();

                    WmImNoticeI wmImNoticeI2 = wmImNoticeIService.getById(apiEntity.getId());

//                    WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, wmImNoticeI.getImNoticeId()).one();
                    Integer divs = Convert.toInt(div);

                    List<String> tinids = new ArrayList<>();
                    synchronized (this){
                        List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                        for (int i = 0; i < divs; i++) {
                            WmImNoticeI wmImNoticeI =  new WmImNoticeI();
                            BeanUtils.copyProperties(wmImNoticeI2,wmImNoticeI);
                            //箱号
//                            String tinid = wmImNoticeI2.getGoodsCode()+"-"+ generateCodeUtil.generateCode(CommonConstant.TABLE_NAME_WMS_TIN, CommonConstant.BASE_DEFAULT_CODE_TYPE_10);

                            String tinid = String.valueOf(System.currentTimeMillis());
                            Thread.sleep(3);
                            //托盘号
                            wmImNoticeI.setTinId(tinid);
                            //长
                            wmImNoticeI.setTinLength(apiEntity.getQuery17());
                            //宽
                            wmImNoticeI.setTinWidth(apiEntity.getQuery18());
                            //高
                            wmImNoticeI.setTinHigh(apiEntity.getQuery19());
                            //体积
//                            BigDecimal decimal = NumberUtil.mul(apiEntity.getQuery17(), apiEntity.getQuery18(), apiEntity.getQuery19());
                            if (StringUtils.isNotEmpty(apiEntity.getQuery10()) && apiEntity.getQuery10().indexOf(",") > 0) {
                                wmImNoticeI.setBinPlan(apiEntity.getQuery10().split(",")[1]);
                            } else {
                                wmImNoticeI.setBinPlan(apiEntity.getQuery10());
                            }
                            //重量
                            wmImNoticeI.setTinWeight(apiEntity.getQuery20());
                            wmImNoticeI.setGoodsQmCount(apiEntity.getQuery15());
                            wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery15());
                            wmImNoticeI.setShenqingsl(apiEntity.getQuery15());
                            wmImNoticeI.setTuoNum(apiEntity.getQuery15());
                            wmImNoticeI.setGoodsBatch(apiEntity.getQuery03());
                            try {
                                wmImNoticeI.setGoodsPrdData(DateUtils.str2Date(apiEntity.getQuery07(), DateUtils.datetimeFormat.get()));
                            } catch (Exception e) {
                            }
                            wmImNoticeIList.add(wmImNoticeI);
                            tinids.add(tinid);
                        }
                        wmInQmIService.batchAdd(wmImNoticeIList);
                    }
                        try{
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        smsSend.labelPrinting(wmImNoticeI2.getId(),tinids,div,query15);
                                    }catch (Exception elp){
                                        elp.printStackTrace();
                                    }
                                    //打印箱唛
                                    try{
                                        if (StringUtils.isNotEmpty(apiEntity.getQuery16()) && "1".equals(apiEntity.getQuery16())){
                                            smsSend.labelPrints(wmImNoticeI2.getId(),tinids,div,query15);
                                        }
                                    }catch (Exception exm){
                                        exm.printStackTrace();
                                    }
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    return Result.ok("操作成功");
                } else {
                    WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(apiEntity.getId());
                    List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                    wmImNoticeI.setTinId(apiEntity.getQuery09());
                    if (StringUtils.isNotEmpty(apiEntity.getQuery10()) && apiEntity.getQuery10().indexOf(",") > 0) {
                        wmImNoticeI.setBinPlan(apiEntity.getQuery10().split(",")[1]);
                    } else {
                        wmImNoticeI.setBinPlan(apiEntity.getQuery10());
                    }
                    wmImNoticeI.setGoodsQmCount(apiEntity.getQuery13());
                    wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery13());
                    wmImNoticeI.setGoodsBatch(apiEntity.getQuery03());
                    try {
                        wmImNoticeI.setGoodsPrdData(DateUtils.str2Date(apiEntity.getQuery07(), DateUtils.date_sdf.get()));

                    } catch (Exception e) {

                    }
                    wmImNoticeIList.add(wmImNoticeI);
                    wmInQmIService.batchAdd(wmImNoticeIList);
                    return Result.ok("操作成功");
                }
            } else if (listtype.equals(CONSTANTTYPE.listtype04)) {
                if (StringUtils.isEmpty(apiEntity.getQuery09())){
                    throw new JeecgBootException("库位不能为空");
                }
                if (StringUtils.isEmpty(apiEntity.getQuery10())){
                    throw new JeecgBootException("数量不能为空");
                }
                WmOmQmI wmOmQmI = wmOmQmIService.getById(apiEntity.getId());
                wmOmQmI.setOmQuat(apiEntity.getQuery10());
                wmOmQmI.setBinSta("N");
                wmOmQmIService.updateById(wmOmQmI);
                WmToMoveGoods wmToMoveGoods = new WmToMoveGoods();
                wmToMoveGoods.setOrderTypeCode("CWZY");
                wmToMoveGoods.setGoodsId(apiEntity.getQuery03());
                wmToMoveGoods.setGoodsName(apiEntity.getQuery04());
                wmToMoveGoods.setGoodsQua(apiEntity.getQuery08());
                wmToMoveGoods.setGoodsProData(wmOmQmI.getProData());
                wmToMoveGoods.setGoodsUnit(apiEntity.getQuery07());
                wmToMoveGoods.setCusCode(wmOmQmI.getCusCode());
                wmToMoveGoods.setCusName(wmOmQmI.getCusName());
                wmToMoveGoods.setTinFrom(wmOmQmI.getTinId());
                wmToMoveGoods.setTinId("ZY");
                if (StringUtils.isNotEmpty(apiEntity.getQuery09()) && apiEntity.getQuery09().indexOf(",") > 0) {
                    wmToMoveGoods.setBinFrom(apiEntity.getQuery09().split(",")[1]);
                } else {
                    wmToMoveGoods.setBinFrom(apiEntity.getQuery09());
                }
                wmToMoveGoods.setBinTo("XC1-A01");
                wmToMoveGoods.setMoveSta("已完成");
                wmToMoveGoods.setToCusCode(wmOmQmI.getCusCode());
                wmToMoveGoods.setToCusName(wmOmQmI.getCusName());
                wmToMoveGoods.setBaseUnit(apiEntity.getQuery07());
                wmToMoveGoods.setBaseGoodscount(apiEntity.getQuery08());
                wmToMoveGoods.setGoodsBatch(apiEntity.getQuery02());
                wmToMoveGoods.setToGoodsBatch(apiEntity.getQuery02());
                wmToMoveGoodsService.save(wmToMoveGoods);
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype05)) {

                WmOmQmI wmOmQmI = wmOmQmIService.getById(apiEntity.getId());
                BigDecimal quat = new BigDecimal(StringUtils.isNotEmpty(wmOmQmI.getOmQuat()) ? wmOmQmI.getOmQuat() : "0").add(new BigDecimal(apiEntity.getQuery06()));
                wmOmQmI.setOmQuat(quat.toString());
                if (StringUtils.isNotEmpty(wmOmQmI.getOmQuat()) && new BigDecimal(wmOmQmI.getOmQuat()).compareTo(new BigDecimal(wmOmQmI.getQmOkQuat())) >= 0) {
                    wmOmQmI.setBinSta("N");
                }
                wmOmQmI.setBinId("ZTQ");//组托区
                wmOmQmI.setTinId(apiEntity.getQuery05());//组托托盘
                wmOmQmIService.updateById(wmOmQmI);
                WmToMoveGoods wmToMoveGoods = new WmToMoveGoods();
                wmToMoveGoods.setOrderTypeCode("CWZY");
                wmToMoveGoods.setGoodsId(wmOmQmI.getGoodsId());
                wmToMoveGoods.setTenantId(wmOmQmI.getTenantId());
                wmToMoveGoods.setGoodsName(wmOmQmI.getGoodsName());
                wmToMoveGoods.setGoodsQua(apiEntity.getQuery06());
                wmToMoveGoods.setGoodsProData(wmOmQmI.getProData());
                wmToMoveGoods.setGoodsUnit(wmOmQmI.getGoodsUnit());
                wmToMoveGoods.setCusCode(wmOmQmI.getCusCode());
                wmToMoveGoods.setCusName(wmOmQmI.getCusName());
                wmToMoveGoods.setTinFrom(apiEntity.getQuery04());//库存托盘
                wmToMoveGoods.setTinId(apiEntity.getQuery05());//组托托盘
                if (StringUtils.isNotEmpty(apiEntity.getQuery02()) && apiEntity.getQuery02().indexOf(",") > 0) {
                    wmToMoveGoods.setBinFrom(apiEntity.getQuery02().split(",")[1]);//库存储位
                } else {
                    wmToMoveGoods.setBinFrom(apiEntity.getQuery02());//库存储位
                }
                wmToMoveGoods.setBinTo("ZTQ");//组托区
                wmToMoveGoods.setRunSta("计划中");
                wmToMoveGoods.setMoveSta("已完成");
                wmToMoveGoods.setToCusCode(wmOmQmI.getCusCode());
                wmToMoveGoods.setToCusName(wmOmQmI.getCusName());
                wmToMoveGoods.setBaseUnit(wmOmQmI.getBaseUnit());
                wmToMoveGoods.setBaseGoodscount(apiEntity.getQuery06());
                wmToMoveGoods.setGoodsBatch(wmOmQmI.getGoodsBatch());
                wmToMoveGoods.setToGoodsBatch(wmOmQmI.getGoodsBatch());
                wmToMoveGoodsService.save(wmToMoveGoods);
                try {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            moveTask.run();
                        }
                    });

                } catch (Exception e) {
                }
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype06)) {
                WmOmQmI wmOmQmI = wmOmQmIService.getById(apiEntity.getId());
                wmOmQmI.setOmQuat(apiEntity.getQuery09());
                wmOmQmI.setBinSta("H");
                wmOmQmIService.updateById(wmOmQmI);
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype07)) {
                WmOmQmI wmOmQmI = wmOmQmIService.getById(apiEntity.getId());
                wmOmQmI.setBinSta("I");
                wmOmQmI.setBinId("CTQ");
                wmOmQmI.setOmQuat("0");
                wmOmQmIService.updateById(wmOmQmI);
                WmToMoveGoods wmToMoveGoods = new WmToMoveGoods();
                wmToMoveGoods.setOrderTypeCode("CWZY");
                wmToMoveGoods.setGoodsId(wmOmQmI.getGoodsId());
                wmToMoveGoods.setGoodsName(wmOmQmI.getGoodsName());
                wmToMoveGoods.setGoodsQua(apiEntity.getQuery06());
                wmToMoveGoods.setGoodsProData(wmOmQmI.getProData());
                wmToMoveGoods.setGoodsUnit(wmOmQmI.getGoodsUnit());
                wmToMoveGoods.setCusCode(wmOmQmI.getCusCode());
                wmToMoveGoods.setCusName(wmOmQmI.getCusName());
                wmToMoveGoods.setTinFrom(apiEntity.getQuery04());
                wmToMoveGoods.setTinId(apiEntity.getQuery04());
                wmToMoveGoods.setBinFrom("ZTQ");//库存储位
                wmToMoveGoods.setBinTo("CTQ");//组托区
                wmToMoveGoods.setMoveSta("已完成");
                wmToMoveGoods.setToCusCode(wmOmQmI.getCusCode());
                wmToMoveGoods.setToCusName(wmOmQmI.getCusName());
                wmToMoveGoods.setBaseUnit(wmOmQmI.getBaseUnit());
                wmToMoveGoods.setBaseGoodscount(apiEntity.getQuery06());
                wmToMoveGoods.setGoodsBatch(wmOmQmI.getGoodsBatch());
                wmToMoveGoods.setToGoodsBatch(wmOmQmI.getGoodsBatch());
                wmToMoveGoodsService.save(wmToMoveGoods);
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype08)) {
                LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
                QueryWrapper<WmOmNoticeH> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("im_cus_code", apiEntity.getQuery19());
                WmOmNoticeH wmOmNoticeH = wmOmNoticeHService.getOne(queryWrapper);
                if (wmOmNoticeH == null) {
                    wmOmNoticeH = new WmOmNoticeH();
                    String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
                    int count = wmOmNoticeHService.lambdaQuery().apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'").count() + 1;
                    String noticeid = "LLCK" + today + "-" + String.format("%04d", count);
                    wmOmNoticeH.setOmNoticeId(noticeid);
                    wmOmNoticeH.setOrderTypeCode("14");
                    wmOmNoticeH.setCusCode("zx00001");
                    wmOmNoticeH.setCusName("增鑫");
                    wmOmNoticeH.setUrgent("N");
                    wmOmNoticeH.setOmSta("计划中");
                    wmOmNoticeH.setOmBeizhu("PDA领料出库");
                    wmOmNoticeH.setImCusCode(apiEntity.getQuery19());
                    wmOmNoticeH.setSysOrgCode(apiEntity.getQuery20());
                    wmOmNoticeH.setDelvData(new Date());
                    wmOmNoticeH.setCreateBy(sysUser.getUsername());
                    wmOmNoticeH.setCreateTime(new Date());
                    wmOmNoticeH.setStoreCode("U8");
                    wmOmNoticeH.setStoreName("U8虚拟仓");
                    wmOmNoticeHService.save(wmOmNoticeH);
                }
                WmOmNoticeI wmOmNoticeI = new WmOmNoticeI();
                wmOmNoticeI.setOmNoticeId(wmOmNoticeH.getOmNoticeId());
                wmOmNoticeI.setGoodsId(apiEntity.getQuery02());
                wmOmNoticeI.setGoodsQua(apiEntity.getQuery10());
                wmOmNoticeI.setGoodsUnit(apiEntity.getQuery04());
                wmOmNoticeI.setGoodsProData(apiEntity.getQuery03() != null ? new SimpleDateFormat("yyyy-MM-dd").parse(apiEntity.getQuery03()) : null);
                if (StringUtils.isNotEmpty(apiEntity.getQuery07()) && apiEntity.getQuery07().indexOf(",") > 0) {
                    wmOmNoticeI.setBinOm(apiEntity.getQuery07().split(",")[1]);//储位
                } else {
                    wmOmNoticeI.setBinOm(apiEntity.getQuery07());//储位
                }
                wmOmNoticeI.setGoodsQuaok("0");
                wmOmNoticeI.setCusCode("zx00001");
                wmOmNoticeI.setCusName("zx00001");
                wmOmNoticeI.setGoodsText(apiEntity.getQuery01());
                wmOmNoticeI.setOmSta("未复核");
                wmOmNoticeI.setBaseUnit(apiEntity.getQuery04());
                wmOmNoticeI.setBaseGoodscount(apiEntity.getQuery10());
                wmOmNoticeI.setPlanSta("Y");
                wmOmNoticeI.setGoodsName(apiEntity.getQuery01());
                if (StringUtils.isNotEmpty(apiEntity.getQuery07()) && apiEntity.getQuery07().indexOf(",") > 0) {
                    wmOmNoticeI.setBinId(apiEntity.getQuery07().split(",")[1]);//储位
                } else {
                    wmOmNoticeI.setBinId(apiEntity.getQuery07());//储位
                }
                wmOmNoticeI.setOmBeiZhu("PDA领料出库");
                wmOmNoticeI.setBarcode(apiEntity.getQuery02());
                wmOmNoticeI.setStoreCode("U8");
                wmOmNoticeI.setStoreName("U8虚拟仓");
                wmOmNoticeI.setCreateBy(sysUser.getUsername());
                wmOmNoticeI.setCreateTime(new Date());
                wmOmNoticeI.setSysOrgCode(apiEntity.getQuery20());
                wmOmNoticeI.setTinId(apiEntity.getQuery08());
                wmOmNoticeI.setGoodsBatch(apiEntity.getQuery11());
                wmOmNoticeIService.save(wmOmNoticeI);

                WmOmQmI wmOmQmI = new WmOmQmI();
                wmOmQmI.setOmNoticeId(wmOmNoticeH.getOmNoticeId());
                wmOmQmI.setIomNoticeItem(wmOmNoticeI.getId());
                wmOmQmI.setGoodsId(wmOmNoticeI.getGoodsId());
                wmOmQmI.setQmOkQuat(apiEntity.getQuery10());
                wmOmQmI.setOmQuat(apiEntity.getQuery10());
                wmOmQmI.setItemText("PDA领料出库");
                wmOmQmI.setProData(wmOmNoticeI.getGoodsProData() != null ? new SimpleDateFormat("yyyy-MM-dd").format(wmOmNoticeI.getGoodsProData()) : null);
                wmOmQmI.setTinId(wmOmNoticeI.getTinId());
                wmOmQmI.setBinId(wmOmNoticeI.getBinId());
                wmOmQmI.setTinTj("0.0");
                wmOmQmI.setBinSta("N");
                wmOmQmI.setCusCode("zx00001");
                wmOmQmI.setCusName(wmOmNoticeI.getCusName());
                wmOmQmI.setGoodsUnit(wmOmNoticeI.getGoodsUnit());
                wmOmQmI.setGoodsBatch(wmOmNoticeI.getGoodsBatch());
                wmOmQmI.setBaseUnit(wmOmNoticeI.getBaseUnit());
                wmOmQmI.setBaseGoodscount(wmOmNoticeI.getBaseGoodscount());
                wmOmQmI.setCusName(wmOmNoticeI.getCusName());
                wmOmQmI.setGoodsName(wmOmNoticeI.getGoodsName());
                wmOmQmI.setOmBeiZhu(wmOmNoticeI.getOmBeiZhu());
                wmOmQmI.setBarcode(wmOmNoticeI.getBarcode());
                wmOmQmI.setShpGuiGe(apiEntity.getQuery05());
                wmOmQmI.setCreateBy(sysUser.getUsername());
                wmOmQmI.setCreateTime(new Date());
                wmOmQmI.setSysOrgCode(apiEntity.getQuery20());
                wmOmQmIService.save(wmOmQmI);
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype09)) {
                WmToMoveGoods wmToMoveGoods = new WmToMoveGoods();
                wmToMoveGoods.setOrderTypeCode("CWZY");
                wmToMoveGoods.setGoodsId(apiEntity.getQuery02());
                wmToMoveGoods.setGoodsName(apiEntity.getQuery01());
                wmToMoveGoods.setGoodsQua(apiEntity.getQuery12());
                wmToMoveGoods.setGoodsProData(apiEntity.getQuery03());
                wmToMoveGoods.setGoodsUnit(apiEntity.getQuery04());
                wmToMoveGoods.setTinFrom(apiEntity.getQuery08());
                wmToMoveGoods.setTinId(apiEntity.getQuery08());
                if (StringUtils.isNotEmpty(apiEntity.getQuery07()) && apiEntity.getQuery07().indexOf(",") > 0) {
                    wmToMoveGoods.setBinFrom(apiEntity.getQuery07().split(",")[1]);//储位
                } else {
                    wmToMoveGoods.setBinFrom(apiEntity.getQuery07());//储位
                }
                if (StringUtils.isNotEmpty(apiEntity.getQuery11()) && apiEntity.getQuery11().indexOf(",") > 0) {
                    wmToMoveGoods.setBinTo(apiEntity.getQuery11().split(",")[1]);//储位
                } else {
                    wmToMoveGoods.setBinTo(apiEntity.getQuery11());//储位
                }
                wmToMoveGoods.setMoveSta("已完成");
                wmToMoveGoods.setBaseUnit(apiEntity.getQuery04());
                wmToMoveGoods.setBaseGoodscount(apiEntity.getQuery12());
                wmToMoveGoods.setStoreCodeFrom(apiEntity.getQuery06());
                wmToMoveGoods.setStoreCodeTo(apiEntity.getQuery10());
                wmToMoveGoods.setStoreNameFrom(apiEntity.getQuery06());
                wmToMoveGoods.setStoreNameTo(apiEntity.getQuery10());
                wmToMoveGoods.setGoodsBatch(apiEntity.getQuery03());
                wmToMoveGoods.setToGoodsBatch(apiEntity.getQuery03());
                wmToMoveGoodsService.save(wmToMoveGoods);
                Result<?> result = wmsPdaService.getU8Diaobodan(wmToMoveGoods.getId());
                return result;
            } else if (listtype.equals(CONSTANTTYPE.listtype10)) {
                WmToMoveGoods wmToMoveGoods = new WmToMoveGoods();
                wmToMoveGoods.setOrderTypeCode("CWZY");
                wmToMoveGoods.setGoodsId(apiEntity.getQuery02());
                wmToMoveGoods.setGoodsName(apiEntity.getQuery01());
                wmToMoveGoods.setGoodsQua(apiEntity.getQuery11());
                wmToMoveGoods.setGoodsProData(apiEntity.getQuery03());
                wmToMoveGoods.setGoodsUnit(apiEntity.getQuery04());
                wmToMoveGoods.setTinFrom(apiEntity.getQuery08());
                wmToMoveGoods.setTinId(apiEntity.getQuery08());
                wmToMoveGoods.setTenantId(apiEntity.getTenantId());
                if (StringUtils.isNotEmpty(apiEntity.getQuery07()) && apiEntity.getQuery07().indexOf(",") > 0) {
                    wmToMoveGoods.setBinFrom(apiEntity.getQuery07().split(",")[1]);//储位
                } else {
                    wmToMoveGoods.setBinFrom(apiEntity.getQuery07());//储位
                }
                if (StringUtils.isNotEmpty(apiEntity.getQuery10()) && apiEntity.getQuery10().indexOf(",") > 0) {
                    wmToMoveGoods.setBinTo(apiEntity.getQuery10().split(",")[1]);//储位
                } else {
                    wmToMoveGoods.setBinTo(apiEntity.getQuery10());//储位
                }
                wmToMoveGoods.setRunSta("计划中");
                wmToMoveGoods.setMoveSta("已完成");
                wmToMoveGoods.setCusCode(apiEntity.getQuery12());
                wmToMoveGoods.setToCusCode(apiEntity.getQuery12());
                try {
                    MdCus md = (MdCus) mdCusService.findByKeHuBianMa(apiEntity.getQuery12()).getResult();
                    wmToMoveGoods.setCusName(md.getZhongWenQch());
                    wmToMoveGoods.setToCusName(md.getZhongWenQch());
                } catch (Exception e) {

                }
                wmToMoveGoods.setBaseUnit(apiEntity.getQuery04());
                wmToMoveGoods.setBaseGoodscount(apiEntity.getQuery11());
                wmToMoveGoods.setStoreCodeFrom(apiEntity.getQuery06());
                wmToMoveGoods.setStoreCodeTo(apiEntity.getQuery06());
                wmToMoveGoods.setStoreNameFrom(apiEntity.getQuery06());
                wmToMoveGoods.setStoreNameTo(apiEntity.getQuery06());
                wmToMoveGoods.setGoodsBatch(apiEntity.getQuery03());
                wmToMoveGoods.setToGoodsBatch(apiEntity.getQuery03());
                wmToMoveGoodsService.save(wmToMoveGoods);
                try {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            moveTask.run();
                        }
                    });

                } catch (Exception e) {
                }
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype11)) {
                if (StringUtils.isEmpty(apiEntity.getQuery08())) {
                    return Result.error("请输入盘点数量！");
                }
                WmSttInGoods wmSttInGoods = wmSttInGoodsService.getById(apiEntity.getId());
                wmSttInGoods.setSttQua(apiEntity.getQuery08());
                wmSttInGoods.setTenantId(apiEntity.getTenantId());
                BigDecimal sttqua = new BigDecimal(wmSttInGoods.getSttQua());
                BigDecimal goodsqua = new BigDecimal(wmSttInGoods.getGoodsQua());
                BigDecimal decimal = goodsqua.subtract(sttqua);
                wmSttInGoods.setDiffQua(decimal.toString());
                wmSttInGoods.setSttSta("已完成");
                wmSttInGoodsService.updateById(wmSttInGoods);
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype12)) {
                if (StringUtils.isEmpty(apiEntity.getQuery09())) {
                    return Result.error("请输入盘点数量！");
                }
                WmSttInGoods wmSttInGoods = wmSttInGoodsService.getById(apiEntity.getId());
                wmSttInGoods.setSttQua(apiEntity.getQuery09());
                wmSttInGoods.setTenantId(apiEntity.getTenantId());
                BigDecimal sttqua = new BigDecimal(wmSttInGoods.getSttQua());
                BigDecimal goodsqua = new BigDecimal(wmSttInGoods.getGoodsQua());
                BigDecimal decimal = goodsqua.subtract(sttqua);
                wmSttInGoods.setDiffQua(decimal.toString());
                wmSttInGoods.setSttSta("已完成");
                wmSttInGoodsService.updateById(wmSttInGoods);
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype15)) {
                List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(apiEntity.getId());
                wmImNoticeI.setGoodsBatch(apiEntity.getQuery06());
                wmImNoticeI.setTinId("A");
                if (StringUtils.isNotEmpty(apiEntity.getQuery07()) && apiEntity.getQuery07().indexOf(",") > 0) {
                    wmImNoticeI.setBinPlan(apiEntity.getQuery07().split(",")[1]);//储位
                } else {
                    wmImNoticeI.setBinPlan(apiEntity.getQuery07());//储位
                }
                wmImNoticeI.setGoodsQmCount(apiEntity.getQuery08());
                wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery08());
                wmImNoticeI.setGoodsPrdData(new Date());
                wmImNoticeIList.add(wmImNoticeI);
                wmInQmIService.batchAdd(wmImNoticeIList);
            } else if (listtype.equals(CONSTANTTYPE.listtype17)) {
                List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(apiEntity.getId());
                wmImNoticeI.setGoodsBatch(apiEntity.getQuery06());
                wmImNoticeI.setTinId(apiEntity.getQuery08());
                wmImNoticeI.setBinPlan("ZTQ");
                wmImNoticeI.setGoodsQmCount(apiEntity.getQuery09());
                wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery09());
                wmImNoticeIList.add(wmImNoticeI);
                wmInQmIService.batchAdd(wmImNoticeIList);
                QueryWrapper<WmInQmI> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("im_notice_item", apiEntity.getId());
                List<WmInQmI> wmInQmIList = wmInQmIService.list(queryWrapper);
                for (WmInQmI wmInQmI : wmInQmIList) {
                    wmInQmIService.upToShelfOne(wmInQmI);
                }
                WmOmQmI wmOmQmI = wmOmQmIService.getById(apiEntity.getQuery20());
                BigDecimal quat = new BigDecimal(StringUtils.isNotEmpty(wmOmQmI.getOmQuat()) ? wmOmQmI.getOmQuat() : "0").add(new BigDecimal(apiEntity.getQuery09()));
                wmOmQmI.setOmQuat(quat.toString());
                if (StringUtils.isNotEmpty(wmOmQmI.getOmQuat()) && new BigDecimal(wmOmQmI.getOmQuat()).compareTo(new BigDecimal(wmOmQmI.getQmOkQuat())) >= 0) {
                    wmOmQmI.setBinSta("N");
                }
                wmOmQmI.setBinId("ZTQ");//组托区
                wmOmQmI.setTinId(apiEntity.getQuery08());//组托托盘
                wmOmQmIService.updateById(wmOmQmI);
                return Result.ok("操作成功");
            } else if (listtype.equals(CONSTANTTYPE.listtype19)) {//下架
                if (StringUtils.isEmpty(apiEntity.getQuery09())){
                    throw new JeecgBootException("数量不能为空");
                }
                List<String> ids = new ArrayList<>();
                ids.add(apiEntity.getId());
                Result<?> result = null;

                if ("SW".equals(project)) {
                    result = wmOmQmIService.dotowavedown1(ids,apiEntity.getQuery09(),apiEntity.getTenantId(), ConstUtil.wm_y);
                    return Result.ok(result);
                } else if ("GS".equals(project) || "PLTN".equals(project)) {
                    List<WvStockSttQuery> wvStockStt = new ArrayList<>();
                    WmOmQmI byId = wmOmQmIService.getById(apiEntity.getId());
                    if(!byId.getTinId().equals(apiEntity.getQuery08())) {
                        QueryWrapper<WmOmNoticeH> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("om_notice_id", byId.getOmNoticeId());
                        Boolean ischeckbatch = false;
                        try{
                            WmOmNoticeH wmOmNoticeH = wmOmNoticeHService.getOne(queryWrapper);
                            String ordertype = wmOmNoticeH.getOrderTypeCode();
                            String orderTypes = "12";
                            if(orderTypes.contains(ordertype)){
                                ischeckbatch = true;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        //88036ed87c0e92de7857e50afc801cb4用箱号查询出剩余库存
                        if(ischeckbatch){
                            wvStockStt = wvStockSttQueryService.lambdaQuery()
                                    .eq(WvStockSttQuery::getBinId, apiEntity.getQuery08())
                                    //.eq(WvStockSttQuery::getKuctype, "库存")
                                    .eq(WvStockSttQuery::getGoodsBatch,byId.getGoodsBatch())
                                    .eq(WvStockSttQuery::getGoodsQua, apiEntity.getQuery09().split("\\.")[0])
                                    .eq(WvStockSttQuery::getGoodsProData, apiEntity.getQuery06())
                                    .eq(WvStockSttQuery::getGoodsId, apiEntity.getQuery03())
                                    .list();
                        }else{
                            wvStockStt = wvStockSttQueryService.lambdaQuery()
                                    .eq(WvStockSttQuery::getBinId, apiEntity.getQuery08())
                                    //.eq(WvStockSttQuery::getKuctype, "库存")
//                                    .eq(WvStockSttQuery::getGoodsBatch,apiEntity.getQuery02())
                                    .eq(WvStockSttQuery::getGoodsQua, apiEntity.getQuery09().split("\\.")[0])
                                    .eq(WvStockSttQuery::getGoodsProData, apiEntity.getQuery06())
                                    .eq(WvStockSttQuery::getGoodsId, apiEntity.getQuery03())
                                    .list();
                        }


                        if (CollectionUtil.isEmpty(wvStockStt)) {
                            throw new JeecgBootException("请核实改箱信息或者箱子已出");
                        }

                        byId.setBinId(wvStockStt.get(0).getKuWeiBianMa());
                        byId.setTinId(apiEntity.getQuery08());
                        byId.setGoodsBatch(wvStockStt.get(0).getGoodsBatch());
                        byId.setBaseGoodscount(apiEntity.getQuery09());
                        wmOmQmIService.updateById(byId);
                    }
                    result = wmOmQmIService.dotowavedown(ids,apiEntity.getQuery09(), ConstUtil.wm_y,wvStockStt);
                        if (result.getCode() == 200){
                            List<WmTuopan> wmTuopans = tuopanService.lambdaQuery().eq(WmTuopan::getTinId, byId.getTinId()).list();
                            if (CollectionUtil.isNotEmpty(wmTuopans)){
                                WmTuopan tuopan = wmTuopans.get(0);
                                tuopan.setIsfails("1");
                                tuopanService.updateById(tuopan);
                            }
                        }
                  return Result.ok(result);
                } else {
                    List<WvStockSttQuery> wvStockStt = new ArrayList<>();

                    result = wmOmQmIService.dotowavedown(ids,apiEntity.getQuery09(), ConstUtil.wm_y,wvStockStt);
                    return Result.ok(result);
                }
            } else if (listtype.equals(CONSTANTTYPE.listtype20)) {//上架

                if (StringUtils.isEmpty(apiEntity.getQuery10())) {
                    throw new JeecgBootException("储位不能为空");
                }
               /* if (StringUtils.isEmpty(apiEntity.getQuery09())) {
                    throw new JeecgBootException("托盘不能为空");
                }*/
                WmInQmI wmInQmI = wmInQmIService.getById(apiEntity.getId());
                List<String> idList = new ArrayList<>();
                if ("SW".equals(project)) {
                    wmInQmI.setBinId(apiEntity.getQuery10());
                    wmInQmI.setTinId(apiEntity.getQuery09());
                    wmInQmI.setTenantId(apiEntity.getTenantId());
                    wmInQmIService.updateById(wmInQmI);
                    idList.add(apiEntity.getId());
                    wmInQmIService.upToShelf1(idList);
                    return Result.ok("操作成功！");

                } /*else if (project.equals("GS") || project.equals("PLTN")) {
                    *//*List<WmTuopan> tuopans = tuopanService.lambdaQuery().eq(WmTuopan::getTinId, apiEntity.getQuery09()).list();
                    if (CollectionUtil.isNotEmpty(tuopans)){
                        throw new JeecgBootException("该箱号已存在，请重新扫码");
                    }*//*
//                    WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, wmInQmI.getImNoticeId()).one();




                    //wmInQmIService.updateById(wmInQmI);
                    idList.add(apiEntity.getId());
                    List<WmTuopan> wmTuopans = tuopanService.lambdaQuery().eq(WmTuopan::getTinId, apiEntity.getQuery09()).eq(WmTuopan::getRemark,""+apiEntity.getQuery04()+","+apiEntity.getQuery01()).list();
                    WmTuopan tuopan = new WmTuopan();
                    if (CollectionUtil.isNotEmpty(wmTuopans)){
                        tuopan = wmTuopans.get(0);
                        if (StringUtils.isNotEmpty(tuopan.getIsupfails()) && tuopan.getIsupfails().equals("1")){
                            throw new JeecgBootException("该箱号已上架，请重新扫码上架");
                        }
                    } else {
                            throw new JeecgBootException("该箱号上架商品不符");
                    }
                    wmInQmI.setBinId(apiEntity.getQuery10());
                    wmInQmI.setTinId(apiEntity.getQuery09());
                    wmInQmIService.updateById(wmInQmI);
                    wmInQmIService.upToShelf(idList);
                    tuopan.setIsupfails("1");
                    tuopanService.updateById(tuopan);
                    return Result.ok("操作成功！");
                }*/ else if ("GS".equals(project) || "PLTN".equals(project)){
                    String[] split = apiEntity.getId().split(",");
                    for (String s : split) {
                        WmInQmI wmInQmIpltn = wmInQmIService.getById(s);
                        if("N".equals(wmInQmIpltn.getBinSta())){
                            wmInQmIpltn.setBinId(apiEntity.getQuery10());
                            wmInQmIService.updateById(wmInQmIpltn);
                        }
                        idList.add(s);
                    }
                    wmInQmIService.upToShelf(idList);
                    return Result.ok("操作成功");
                } else {
                    wmInQmI.setBinId(apiEntity.getQuery10());
                    wmInQmI.setTinId(apiEntity.getQuery09());
                    wmInQmIService.updateById(wmInQmI);
                    idList.add(apiEntity.getId());
                    wmInQmIService.upToShelf(idList);
                    return Result.ok("操作成功！");
                }
            } else if (listtype.equals(CONSTANTTYPE.listtype21)) {//波次下架
                /*List<String> ids = new ArrayList<>();
                ids.add(apiEntity.getId());
                Result<?> result = wmOmQmIService.dotowavedown1(ids,"",, ConstUtil.wm_h);
                return result;*/
            } else if (listtype.equals(CONSTANTTYPE.listtype22)) {

            } else if (listtype.equals(CONSTANTTYPE.listtype23)) {//复核
                List<String> ids = new ArrayList<>();
                ids.add(apiEntity.getId());
                Result<?> result = wmToDownGoodsService.dofubatch(ids,apiEntity.getTenantId());
                return result;
            } else if (listtype.equals(CONSTANTTYPE.listtype26)) {  //其他入库

                if (StringUtils.isEmpty(apiEntity.getQuery13())){
                    throw new JeecgBootException("数量不能为空");
                }
                //待入库
                String query12 = apiEntity.getQuery12();
                //本次入库数量
                String query13 = apiEntity.getQuery13();
                if(new BigDecimal(query13).compareTo(new BigDecimal(query12)) == 1){
                    throw new JeecgBootException("本次入库数量大于待入库数量");
                }
                if ("SW".equals(project) || "GS".equals(project)) {
                    if (StringUtils.isEmpty(apiEntity.getQuery07())) {
                        throw new JeecgBootException("日期不能为空");
                    }
                    if (StringUtils.isEmpty(apiEntity.getQuery03())) {
                        throw new JeecgBootException("批次不能为空");
                    }
                    WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(apiEntity.getId());
                    List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
//                wmImNoticeI.setTinId("A");
                    wmImNoticeI.setTinId(apiEntity.getQuery09());

                    if (StringUtils.isNotEmpty(apiEntity.getQuery10()) && apiEntity.getQuery10().indexOf(",") > 0) {
                        wmImNoticeI.setBinPlan(apiEntity.getQuery10().split(",")[1]);
                    } else {
                        wmImNoticeI.setBinPlan(apiEntity.getQuery10());
                    }
                    wmImNoticeI.setGoodsQmCount(apiEntity.getQuery13());
                    wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery13());
                    wmImNoticeI.setShenqingsl(apiEntity.getQuery13());
                    wmImNoticeI.setGoodsBatch(apiEntity.getQuery03());
                    wmImNoticeI.setTenantId(apiEntity.getTenantId());
                    try {
                        wmImNoticeI.setGoodsPrdData(DateUtils.str2Date(apiEntity.getQuery07(), DateUtils.date_sdf.get()));

                    } catch (Exception e) {

                    }
                    wmImNoticeIList.add(wmImNoticeI);
                    wmInQmIService.batchAdd(wmImNoticeIList);
                    return Result.ok("操作成功");
                } else if ("PLTN".equals(project)) {

                    String query15 = apiEntity.getQuery15();//每箱数量

                    if(new BigDecimal(query15).compareTo(new BigDecimal(query13)) == 1){
                        throw new JeecgBootException("本次入库数量小于箱数");
                    }
                    //取余数
                    BigDecimal bigDecimal = new BigDecimal(query13).divideAndRemainder(new BigDecimal(query15))[1];
                    //入库数量
                    BigDecimal sub = NumberUtil.sub(query13, bigDecimal.toString());
                    //箱数
                    String div = NumberUtil.div(sub.toString(), query15).toString();

                    WmImNoticeI wmImNoticeI2 = wmImNoticeIService.getById(apiEntity.getId());

                    //WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, wmImNoticeI.getImNoticeId()).one();
                    Integer divs = Convert.toInt(div);

                    List<String> tinids = new ArrayList<>();
                    synchronized (this){
                        List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                        for (int i = 0; i < divs; i++) {
                            WmImNoticeI wmImNoticeI =  new WmImNoticeI();
                            BeanUtils.copyProperties(wmImNoticeI2,wmImNoticeI);
                            //箱号
                            String tinid = String.valueOf(System.currentTimeMillis());
//                            String tinid = wmImNoticeI2.getGoodsCode()+"-"+ generateCodeUtil.generateCode(CommonConstant.TABLE_NAME_WMS_TIN, CommonConstant.BASE_DEFAULT_CODE_TYPE_10);

                            Thread.sleep(3);
                            //托盘号
                            wmImNoticeI.setTinId(tinid);
                            //长
                            wmImNoticeI.setTinLength(apiEntity.getQuery17());
                            //宽
                            wmImNoticeI.setTinWidth(apiEntity.getQuery18());
                            //高
                            wmImNoticeI.setTinHigh(apiEntity.getQuery19());
                            //体积
//                            BigDecimal decimal = NumberUtil.mul(apiEntity.getQuery17(), apiEntity.getQuery18(), apiEntity.getQuery19());
                            if (StringUtils.isNotEmpty(apiEntity.getQuery10()) && apiEntity.getQuery10().indexOf(",") > 0) {
                                wmImNoticeI.setBinPlan(apiEntity.getQuery10().split(",")[1]);
                            } else {
                                wmImNoticeI.setBinPlan(apiEntity.getQuery10());
                            }
                            //重量
                            wmImNoticeI.setTinWeight(apiEntity.getQuery20());
                            wmImNoticeI.setGoodsQmCount(apiEntity.getQuery15());
                            wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery15());
                            wmImNoticeI.setShenqingsl(apiEntity.getQuery15());
                            wmImNoticeI.setTuoNum(apiEntity.getQuery15());
                            wmImNoticeI.setGoodsBatch(apiEntity.getQuery03());
                            try {
                                wmImNoticeI.setGoodsPrdData(DateUtils.str2Date(apiEntity.getQuery07(), DateUtils.date_sdf.get()));
                            } catch (Exception e) {
                            }
                            wmImNoticeIList.add(wmImNoticeI);
                            tinids.add(tinid);
                        }
                        wmInQmIService.batchAdd(wmImNoticeIList);
                    }
                    try{
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    smsSend.labelPrinting(wmImNoticeI2.getId(),tinids,div,query15);

                                }catch (Exception elp){
                                    elp.printStackTrace();
                                }
                                //打印箱唛
                                try{
                                    if (StringUtils.isNotEmpty(apiEntity.getQuery16()) && "1".equals(apiEntity.getQuery16())){
                                        smsSend.labelPrints(wmImNoticeI2.getId(),tinids,div,query15);
                                    }
                                }catch (Exception exm){
                                    exm.printStackTrace();
                                }
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return Result.ok("操作成功");
                } else {
                    WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(apiEntity.getId());
                    List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                    wmImNoticeI.setTinId(apiEntity.getQuery09());
                    if (StringUtils.isNotEmpty(apiEntity.getQuery10()) && apiEntity.getQuery10().indexOf(",") > 0) {
                        wmImNoticeI.setBinPlan(apiEntity.getQuery10().split(",")[1]);
                    } else {
                        wmImNoticeI.setBinPlan(apiEntity.getQuery10());
                    }
                    wmImNoticeI.setGoodsQmCount(apiEntity.getQuery13());
                    wmImNoticeI.setGoodsWqmCount(apiEntity.getQuery13());
                    wmImNoticeI.setGoodsBatch(apiEntity.getQuery03());
                    try {
                        wmImNoticeI.setGoodsPrdData(DateUtils.str2Date(apiEntity.getQuery07(), DateUtils.date_sdf.get()));

                    } catch (Exception e) {

                    }
                    wmImNoticeIList.add(wmImNoticeI);
                    wmInQmIService.batchAdd(wmImNoticeIList);
                    return Result.ok("操作成功");
                }
            }else if (listtype.equals(CONSTANTTYPE.listtype24)) {//商品资料
                MdGoods mdGoods = mdGoodsService.getById(apiEntity.getId());
                mdGoods.setShpTiaoMa(apiEntity.getQuery03());//商品条码
                mdGoods.setZhlKg(apiEntity.getQuery04());//重量  KG
                mdGoods.setTiJiCm(apiEntity.getQuery05());//体积 立方厘米
                mdGoods.setChZhXiang(apiEntity.getQuery06());//长 厘米
                mdGoods.setKuZhXiang(apiEntity.getQuery07());//宽 厘米
                mdGoods.setGaoZhXiang(apiEntity.getQuery08());//高 厘米
                mdGoods.setMpDanCeng(apiEntity.getQuery09());//码盘单层
                mdGoods.setMpCengGao(apiEntity.getQuery10());//码盘层高


                Boolean isok = mdGoodsService.updateById(mdGoods);
                if (isok) {
                    return Result.OK();
                } else {
                    return Result.error("保存失败");
                }

            }else if (listtype.equals(CONSTANTTYPE.listtype31)) {
                LambdaQueryWrapper<BusiPo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(BusiPo::getQuery14,apiEntity.getQuery05());
                BusiPo Poobj = busiPoService.getOne(queryWrapper, false);
                if(Poobj == null){
                    return Result.error("查询不到PO：" + apiEntity.getQuery05());
                }
                QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
                MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,Poobj.getQuery24());
                MdCus KHobj = mdCusService.getOne(MdCusqueryWrapper, false);
                if(KHobj == null){
                    return Result.error("查询不到客户：" + Poobj.getQuery24());
                }
                BusiOrdPrice busiOrdPrice = new BusiOrdPrice();
                busiOrdPrice.setQuery01("QCFY");
                busiOrdPrice.setQuery06("QC FEES QC费用"); // 费用类型
                busiOrdPrice.setQuery15(apiEntity.getQuery05()); // 子PO号
                busiOrdPrice.setQuery08(apiEntity.getQuery07()); // 日期
                busiOrdPrice.setQuery09(apiEntity.getQuery08()); // 年月
                busiOrdPrice.setQuery12(apiEntity.getQuery06()); // 费用描述
                busiOrdPrice.setQuery14("Purchase"); // 部门
                busiOrdPrice.setQuery20("CNY"); // 币种
                busiOrdPrice.setQuery13(""); // Charge to client
                busiOrdPrice.setQuery14("Purchase"); // Charge to client
                busiOrdPrice.setQuery16(""); // SHIP#
                busiOrdPrice.setQuery17(""); // INV#
                busiOrdPrice.setQuery18(""); // Order Qty
                busiOrdPrice.setQuery19(apiEntity.getQuery19()); // Vendor
                busiOrdPrice.setQuery20("CNY"); // Currency
                busiOrdPrice.setQuery21(""); // Unit price
                busiOrdPrice.setQuery30(""); // BANK ACCOUNT

//
                List<String> strings = new ArrayList<>();
                strings.add(username);
                List<SysUser> sysUsers = sysUserRemoteApi.userList(strings);
                if(CollectionUtil.isNotEmpty(sysUsers)){
                    SysUser sysUser = sysUsers.get(0);
                    busiOrdPrice.setQuery07(sysUser.getRealname()); // 员工名
                }else {
                    busiOrdPrice.setQuery07(username); // 员工名
                }
                busiOrdPrice.setQuery10(KHobj.getZhongWenQch()); // 客户
                busiOrdPrice.setQuery11(KHobj.getXingYeFenLei()); // 企业属性
                busiOrdPrice.setQuery22(apiEntity.getQuery10()); // 总额
                busiOrdPrice.setQuery23("未确认"); // 状态
                Result<?> add = IBusiOrdPriceController.add(busiOrdPrice);
                if (add.getCode() == 200) {
                    return Result.OK();
                } else {
                    return Result.error("保存失败");
                }
            }else if (listtype.equals(CONSTANTTYPE.listtype33)) {
                BusiPrdOrdItem busiPrdOrdItem = new BusiPrdOrdItem();
                BeanUtils.copyProperties(apiEntity,busiPrdOrdItem);
//                busiPrdOrdItem.setAttr1(apiEntity.getQuery22());
                busiPrdOrdItem.setQuery31(listtype);
                boolean save = busiPrdOrdItemService.save(busiPrdOrdItem);
                if (save) {
                    return Result.OK();
                } else {
                    return Result.error("保存失败");
                }
            }
            return Result.error("参数未匹配到！");
        }


    }

}


































