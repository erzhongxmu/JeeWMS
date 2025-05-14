package com.base.modules.jeewms.pdaapi;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import com.base.modules.util.StringUtil;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "WMSPDA查询")
@RestController
@RequestMapping("/jeewms/pdaGapi")
@Slf4j
public class ApiGetControler {

    @Autowired
    private IWmsPdaService wmsPdaService;
    @Autowired
    private IMdGoodsItemService itemService;

    @Autowired
    private IWmToUpGoodsService goodsService;

    @Autowired
    private IBiCService biCService;


    @AutoLog(value = "PDA查询接口")
    @ApiOperation(value = "PDA查询接口", notes = "PDA查询接口")
    @GetMapping(value = "/getlist")
    public Result<?> getlist(@RequestParam(name = "username", required = false) String username,
                             @RequestParam(name = "listtype", required = true) String listtype,
                             @RequestParam(name = "query01", required = false) String query01,
                             @RequestParam(name = "query02", required = false) String query02,
                             @RequestParam(name = "query03", required = false) String query03,
                             @RequestParam(name = "query04", required = false) String query04,
                             @RequestParam(name = "query05", required = false) String query05,
                             @RequestParam(name = "query06", required = false) String query06,
                             @RequestParam(name = "query07", required = false) String query07,
                             @RequestParam(name = "project", required = false) String project,
                             @RequestParam(name = "tenantId", required = false) String tenantId,
                             @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                             @RequestParam(name = "pageSize", required = false, defaultValue = "100") Integer pageSize,
                             HttpServletRequest req) {
        Page<ApiresEntity> page = new Page<ApiresEntity>(pageNo, pageSize);
        HashMap<String, String> querymap = new HashMap<>(1024);
        querymap.put("tenantId", tenantId);
        synchronized (this) {
            if (listtype.equals(CONSTANTTYPE.listtype01)) {//成品入库

                if (StringUtils.isEmpty(query01)){
                    throw new JeecgBootException("请扫描单号");
                }
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType01(page, querymap);
                List<ApiresEntity> records = pageList.getRecords();
                WmToUpGoods wmToUpGoods = new WmToUpGoods();
                for (ApiresEntity record : records) {
                    List<WmToUpGoods> list = goodsService.lambdaQuery()
                            .eq(WmToUpGoods::getGoodsId, record.getQuery02())
                            .orderByDesc(WmToUpGoods::getCreateTime)
                            .list();

                    if (CollectionUtils.isNotEmpty(list)) {
                        wmToUpGoods = list.get(0);
                    }
                    record.setQuery07(wmToUpGoods.getKuWeiBianMa());
                }
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype02)) {//到货审核
                if (StringUtils.isEmpty(query01)){
                    throw new JeecgBootException("请扫描单号");
                }
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType02(page, querymap);
                List<ApiresEntity> records1 = new ArrayList<>();
                List<MdGoodsItem> mdGoodsItems = itemService.lambdaQuery().eq(MdGoodsItem::getSttr1, query02).list();
                if (CollectionUtils.isNotEmpty(mdGoodsItems)) {
                    for (MdGoodsItem mdGoodsItem : mdGoodsItems) {
                        HashMap<String, String> querymap1 = new HashMap<>(1024);
                        querymap1.put("query01", query01);
                        querymap1.put("query02", mdGoodsItem.getShpBianMa());
                        IPage<ApiresEntity> page1 = wmsPdaService.queryListType02(page, querymap1);
                        List<ApiresEntity> records = page1.getRecords();
                        for (ApiresEntity record : records) {
                            records1.add(record);
                        }
                    }
                    pageList.setRecords(records1);
                } else {
                    querymap.put("query01", query01);
                    querymap.put("query02", query02);
                    pageList = wmsPdaService.queryListType02(page, querymap);
                }
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype03)) {//采购入库
                if (StringUtils.isEmpty(query01)){
                    throw new JeecgBootException("请扫描单号");
                }
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = new Page<>();
                if ("SW".equals(project) || "GS".equals(project)){
                    pageList = wmsPdaService.queryListType03(page, querymap);
                    return Result.ok(pageList);
                }else if ("PLTN".equals(project)){
                    pageList = wmsPdaService.queryListType03pltn(page, querymap);
                    List<ApiresEntity> records1 = new ArrayList<>();
                    List<MdGoodsItem> mdGoodsItems = itemService.lambdaQuery().eq(MdGoodsItem::getSttr1, query02).list();
                    if (CollectionUtils.isNotEmpty(mdGoodsItems)) {
                        for (MdGoodsItem mdGoodsItem : mdGoodsItems) {
                            HashMap<String, String> querymap1 = new HashMap<>(1024);
                            querymap1.put("query01", query01);
                            querymap1.put("query02", mdGoodsItem.getShpBianMa());
                            IPage<ApiresEntity> page1 = wmsPdaService.queryListType03(page, querymap1);
                            List<ApiresEntity> records = page1.getRecords();
                            for (ApiresEntity record : records) {
                                records1.add(record);
                            }
                        }
                        pageList.setRecords(records1);
                    }
                    return Result.ok(pageList);
                }else {
                    return Result.ok(pageList);
                }

            } else if (listtype.equals(CONSTANTTYPE.listtype04)) {//现场仓领料
                if (StringUtils.isEmpty(query01)){
                    throw new JeecgBootException("请扫描单号");
                }
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType04(page, querymap);
                List<ApiresEntity> apiresEntityList = pageList.getRecords();
                for (ApiresEntity apiresEntity : apiresEntityList) {
                    HashMap<String, String> map = new HashMap<>(1024);
                    map.put("goods_id", apiresEntity.getQuery03());
                    List<ApiEntity> apiEntityList = wmsPdaService.querySpkcxx(map);
                    apiresEntity.setList01(apiEntityList);
                }
                pageList.setRecords(apiresEntityList);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype05)) {//销售组托
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType05(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype06)) {//销售出库
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType06(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype07)) {//拆托
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType07(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype08)) {//部门领料(出库)
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType08(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype09)) {//调拨管理
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType09(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype10)) {//货位转储
                if (StringUtils.isEmpty(query01)){
                    throw new JeecgBootException("请扫描储位");
                }
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType10(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype11)) {//暗盘
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType11(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype12)) {//明盘
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType12(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype13)) {//今日任务
                /*HashMap<String, String> querymap = new HashMap<>(1024);
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType13(page, querymap);*/
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
            } else if (listtype.equals(CONSTANTTYPE.listtype14)) {//商品库存
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType14(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype15)) {//半成品入库
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType15(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype16)) {//托盘清单
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType16(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype17)) {//直拨组托
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType05(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype18)) {//直拨组托库存
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType18(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype19)) {//下架
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                if ("SW".equals(project) || "GS".equals(project)) {
                    IPage<ApiresEntity> pageList = wmsPdaService.queryListType19swgs(page, querymap);
                    return Result.ok(pageList);
                } else if ("PLTN".equals(project)) {

                    if (StringUtils.isEmpty(query01)){
                        throw new JeecgBootException("请扫描单号");
                    }
                    /*if (StringUtils.isEmpty(query02)){
                        throw new JeecgBootException("请扫描箱号");
                    }*/
                    if (StringUtils.isNotEmpty(query03)) {
                        querymap.put("query03", query03);
                    } else {
                        query03 = "N";
                        querymap.put("query03", query03);
                    }
                    IPage<ApiresEntity> pageList = wmsPdaService.queryListType19pltn(page, querymap);
                    return Result.ok(pageList);
                }else {
                    IPage<ApiresEntity> pageList = wmsPdaService.queryListType19(page, querymap);
                    return Result.ok(pageList);
                }
            } else if (listtype.equals(CONSTANTTYPE.listtype20)) {//上架
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = new Page<>();
                if ("PLTN".equals(project)){
                    if (StringUtils.isEmpty(query01)){
                        throw new JeecgBootException("请扫描单号");
                    }
                     pageList = wmsPdaService.queryListType20pltn(page, querymap);
                }else {
                     pageList = wmsPdaService.queryListType20(page, querymap);
                }
                List<ApiresEntity> records = pageList.getRecords();
                WmToUpGoods wmToUpGoods = new WmToUpGoods();
                for (ApiresEntity record : records) {
                    List<WmToUpGoods> list = goodsService.lambdaQuery()
                            .eq(WmToUpGoods::getGoodsId, record.getQuery04())
                            .orderByDesc(WmToUpGoods::getCreateTime)
                            .list();
                    if (CollectionUtils.isNotEmpty(list)) {
                        wmToUpGoods = list.get(0);
                    }
                    record.setQuery10(wmToUpGoods.getKuWeiBianMa());
                    if (StringUtils.isNotEmpty(record.getQuery09())){
                        record.setQuery09("A".equals(record.getQuery09()) ? "" : record.getQuery09());
                    }
                }
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype21)) {//波次下架
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType21(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype22)) {//波次分拣
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType22(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype23)) {//复核
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType23(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype24)) {//商品资料
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType24(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype26)) {//其他入库
                if (StringUtils.isEmpty(query01)){
                    throw new JeecgBootException("请扫描单号");
                }
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = new Page<>();
                if ("SW".equals(project) || "GS".equals(project)){
                    pageList = wmsPdaService.queryListType26(page, querymap);
                    return Result.ok(pageList);
                }else if ("PLTN".equals(project)){
                    pageList = wmsPdaService.queryListType26(page, querymap);
                    List<ApiresEntity> records1 = new ArrayList<>();
                    List<MdGoodsItem> mdGoodsItems = itemService.lambdaQuery().eq(MdGoodsItem::getSttr1, query02).list();
                    if (CollectionUtils.isNotEmpty(mdGoodsItems)) {
                        for (MdGoodsItem mdGoodsItem : mdGoodsItems) {
                            HashMap<String, String> querymap1 = new HashMap<>(1024);
                            querymap1.put("query01", query01);
                            querymap1.put("query02", mdGoodsItem.getShpBianMa());
                            IPage<ApiresEntity> page1 = wmsPdaService.queryListType26(page, querymap1);
                            List<ApiresEntity> records = page1.getRecords();
                            for (ApiresEntity record : records) {
                                records1.add(record);
                            }
                        }
                        pageList.setRecords(records1);
                    }
                    return Result.ok(pageList);
                }else {
                    return Result.ok(pageList);
                }

            }else if (listtype.equals(CONSTANTTYPE.listtype25)) {
                querymap.put("query01", query01);
                querymap.put("query02", query02);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType25(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype32)) {
                querymap.put("query01", query01);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType32(page, querymap);
                return Result.ok(pageList);
            } else if (listtype.equals(CONSTANTTYPE.listtype33)) {
                querymap.put("query01", query01);
                IPage<ApiresEntity> pageList = wmsPdaService.queryListType33(page, querymap);
                return Result.ok(pageList);
            }
            return Result.ok(page);
        }
    }
}
