package com.base.modules.jeeerp.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.api.vo.Result;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeeerp.entity.*;
import com.base.modules.jeeerp.service.*;
import com.base.modules.jeeerp.vo.BusiPoPage;
import com.base.modules.jeeerp.vo.BusiPoYpPage;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.service.IMdCusService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: busi_po
 * @Author: base-boot
 * @Date: 2022-11-23
 * @Version: V1.0
 */
@Api(tags = "ExportTableData")
@RestController
@RequestMapping("/jeeerp/ExportTableData")
@Slf4j
public class ExportTableData {
    @Autowired
    private IBusiPoService busiPoService;
    @Autowired
    private IBusiPoItemService busiPoItemService;

    @Autowired
    private IBusiOmService busiOmService;
    @Autowired
    private IBusiOmItemService busiOmItemService;
    @Autowired
    private IBusiPaymentReceivedService busiPaymentReceivedService;

    @Autowired
    private IBusiPrdOrdService busiPrdOrdService;
    @Autowired
    private IMdCusService mdCusService;

    /**
     * 导出 busiPo excel
     *
     * @param request
     * @param busiPo
     */
    @RequestMapping(value = "/ExportBusiPo")
    public Result<?> ExportBusiPo(HttpServletRequest request, BusiPo busiPo) {
        // Step.1 组装查询条件查询数据
        String query01 = busiPo.getQuery01();
        String query02 = busiPo.getQuery02();
        String query03 = busiPo.getQuery03();
        busiPo.setQuery02(null);
        busiPo.setQuery03(null);
        QueryWrapper<BusiPo> queryWrapper = QueryGenerator.initQueryWrapper(busiPo, request.getParameterMap());
        if (StringUtils.isNotEmpty(query02)) {
            String[] split = query02.split(",");
            queryWrapper.in("query02", split);
        }
        if (StringUtils.isNotEmpty(query03)) {
            queryWrapper.groupBy(query03);
        } else {
            queryWrapper.groupBy("query13");
        }
        //Step.2 获取导出数据
        List<BusiPo> queryList = busiPoService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<BusiPo> busiPoList = new ArrayList<BusiPo>();
        if (oConvertUtils.isEmpty(selections)) {
            busiPoList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            busiPoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        List<MdCus> list1 = mdCusService.list();
        Map<String, List<MdCus>> collect = list1.stream().collect(Collectors.groupingBy(MdCus::getKeHuBianMa));

        QueryWrapper<BusiPaymentReceived> PqueryWrapper = new QueryWrapper<>();
        PqueryWrapper.lambda().eq(BusiPaymentReceived::getQuery01, "FKD");
        List<BusiPaymentReceived> Paymentlist = busiPaymentReceivedService.list(PqueryWrapper);
        Map<String, List<BusiPaymentReceived>> collect1 = Paymentlist.stream().collect(Collectors.groupingBy(BusiPaymentReceived::getQuery13));



        if ("YP".equals(query01)) {
            QueryWrapper<BusiPoItem> queryPOWrapper = new QueryWrapper<>();
            queryPOWrapper.lambda().eq(BusiPoItem::getQuery01, "DYJD");
            List<BusiPoItem> listitem = busiPoItemService.list(queryPOWrapper);
            Map<String, List<BusiPoItem>> collect2 = listitem.stream().collect(Collectors.groupingBy(BusiPoItem::getQuery14));

            LambdaQueryWrapper<BusiPo> BusiPoWrapper  = new LambdaQueryWrapper<BusiPo>();
            BusiPoWrapper.ne(BusiPo::getQuery02,"打样已完成");
            List<BusiPo> BusiPolist2 = busiPoService.list(BusiPoWrapper);
            Map<String, List<BusiPo>> collect3 = BusiPolist2.stream().collect(Collectors.groupingBy(BusiPo::getQuery13));

            List<BusiPoYpPage> pageList = new ArrayList<BusiPoYpPage>();
            for (BusiPo main : busiPoList) {
                BusiPoYpPage vo = new BusiPoYpPage();
                BeanUtils.copyProperties(main, vo);
                String query24 = vo.getQuery24();
                List<MdCus> mdCuses = collect.get(query24);
                if(CollectionUtil.isNotEmpty(mdCuses)){
                    vo.setQuery24(mdCuses.get(0).getZhongWenQch());
                    vo.setXingYeFenLei(mdCuses.get(0).getXingYeFenLei());
                }

                List<BusiPaymentReceived> busiPaymentReceiveds = collect1.get(vo.getQuery13());
                BigDecimal i = new BigDecimal(0);
                if(CollectionUtil.isNotEmpty(busiPaymentReceiveds)){
                    for (BusiPaymentReceived busiPaymentReceived : busiPaymentReceiveds) {
                        BigDecimal bigDecimal = new BigDecimal(busiPaymentReceived.getNum09().toString());
                        i = i.add(bigDecimal);
                    }
                    BigDecimal Num09 = new BigDecimal(vo.getNum09().toString());
                    BigDecimal subtract = Num09.subtract(i);
                    vo.setRemainingSum(String.valueOf(subtract));
                }else {
                    vo.setRemainingSum(String.valueOf(i));
                }


                List<BusiPoItem> busiPoItems = collect2.get(vo.getQuery14());
                if (!CollectionUtil.isEmpty(busiPoItems)) {
                    vo.setBusiPoItemList(busiPoItems);
                }

                String query10000 = request.getParameter("query10000");
                if(StringUtils.isBlank(query10000)){
                    List<BusiPo> busiPos = collect3.get(vo.getQuery13());
                    if(CollectionUtil.isNotEmpty(busiPos)){
                        vo.setQuery02(busiPos.get(0).getQuery02());
                    }
                }
                pageList.add(vo);
            }
            return Result.OK(pageList);
        } else {
            List<BusiPoPage> pageList = new ArrayList<BusiPoPage>();
            for (BusiPo main : busiPoList) {
                BusiPoPage vo = new BusiPoPage();
                BeanUtils.copyProperties(main, vo);
                String query24 = vo.getQuery24();
                List<MdCus> mdCuses = collect.get(query24);
                if(CollectionUtil.isNotEmpty(mdCuses)){
                    vo.setQuery24(mdCuses.get(0).getZhongWenQch());
                    vo.setXingYeFenLei(mdCuses.get(0).getXingYeFenLei());
                }

                List<BusiPaymentReceived> busiPaymentReceiveds = collect1.get(vo.getQuery13());
                BigDecimal i = new BigDecimal(0);
                if(CollectionUtil.isNotEmpty(busiPaymentReceiveds)){
                    for (BusiPaymentReceived busiPaymentReceived : busiPaymentReceiveds) {
                        BigDecimal bigDecimal = new BigDecimal(busiPaymentReceived.getNum09().toString());
                        i = i.add(bigDecimal);
                    }
                    BigDecimal Num09 = new BigDecimal(vo.getNum09().toString());
                    BigDecimal subtract = Num09.subtract(i);
                    vo.setRemainingSum(String.valueOf(subtract));
                }else {
                    vo.setRemainingSum(String.valueOf(i));
                }
                pageList.add(vo);
            }

            return Result.OK(pageList);
        }

    }


    /**
     * 导出 busiPoItem excel
     *
     * @param request
     * @param busiPoItem
     */
    @RequestMapping(value = "/ExportBusiPoItem")
    public Result<?> ExportBusiPoItem(HttpServletRequest request, BusiPoItem busiPoItem) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<BusiPoItem> queryWrapper = QueryGenerator.initQueryWrapper(busiPoItem, request.getParameterMap());
        //Step.2 获取导出数据
        List<BusiPoItem> queryList = busiPoItemService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<BusiPoItem> busiPoList = new ArrayList<BusiPoItem>();
        if (oConvertUtils.isEmpty(selections)) {
            busiPoList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            busiPoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        List<BusiPoItem> pageList = new ArrayList<BusiPoItem>();
        for (BusiPoItem main : busiPoList) {
            BusiPoItem vo = new BusiPoItem();
            BeanUtils.copyProperties(main, vo);
            pageList.add(vo);
        }

        return Result.OK(pageList);
    }


    /**
     * 导出 busiPaymentReceived excel
     *
     * @param request
     * @param busiPaymentReceived
     */
    @RequestMapping(value = "/ExportBusiPaymentReceived")
    public Result<?> ExportBusiPaymentReceived(HttpServletRequest request, BusiPaymentReceived busiPaymentReceived) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<BusiPaymentReceived> queryWrapper = QueryGenerator.initQueryWrapper(busiPaymentReceived, request.getParameterMap());
        //Step.2 获取导出数据
        List<BusiPaymentReceived> queryList = busiPaymentReceivedService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<BusiPaymentReceived> BusiPaymentReceivedList = new ArrayList<BusiPaymentReceived>();
        if (oConvertUtils.isEmpty(selections)) {
            BusiPaymentReceivedList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            BusiPaymentReceivedList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
            List<String> list = new ArrayList<>();
            for (BusiPaymentReceived paymentReceived : BusiPaymentReceivedList) {
                list.add(paymentReceived.getQuery04());
            }
            QueryWrapper<BusiPaymentReceived> queryWrappertemp = new QueryWrapper<>();
            queryWrappertemp.in("query04", list);
            BusiPaymentReceivedList = busiPaymentReceivedService.list(queryWrappertemp);
        }
        if (CollectionUtil.isNotEmpty(BusiPaymentReceivedList)) {
            for (BusiPaymentReceived paymentReceived : BusiPaymentReceivedList) {
                QueryWrapper<MdCus> MdCusqueryWrapper = new QueryWrapper<>();
                MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa, paymentReceived.getQuery24());
                MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
                paymentReceived.setQuery24(one2.getZhongWenQch());
                paymentReceived.setQuery31(one2.getXingYeFenLei());
                if (paymentReceived.getNum10() == null) {
                    paymentReceived.setNum10(new Double(0));
                }
            }
        }

        return Result.OK(BusiPaymentReceivedList);
    }

    /**
     * 导出 ExportBusiPrdOrder excel
     */

    @RequestMapping(value = "/ExportBusiPrdOrder")
    public Result<?> ExportBusiPrdOrder(HttpServletRequest request, BusiPrdOrd busiPrdOrd) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<BusiPrdOrd> queryWrapper = QueryGenerator.initQueryWrapper(busiPrdOrd, request.getParameterMap());
        //Step.2 获取导出数据
        List<BusiPrdOrd> queryList = busiPrdOrdService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<BusiPrdOrd> BusiPrdOrdList = new ArrayList<>();
        if (oConvertUtils.isEmpty(selections)) {
            BusiPrdOrdList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            BusiPrdOrdList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }
        return Result.OK(BusiPrdOrdList);
    }

    /**
     * 导出 busiOm excel
     *
     * @param request
     * @param busiOm
     */
    @RequestMapping(value = "/ExportBusiOm")
    public Result<?> ExportBusiOm(HttpServletRequest request, BusiOm busiOm) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<BusiOm> queryWrapper = QueryGenerator.initQueryWrapper(busiOm, request.getParameterMap());
        //Step.2 获取导出数据
        List<BusiOm> queryList = busiOmService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<BusiOm> busiPoList = new ArrayList<BusiOm>();
        if (oConvertUtils.isEmpty(selections)) {
            busiPoList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            busiPoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        List<BusiOm> pageList = new ArrayList<BusiOm>();
        for (BusiOm main : busiPoList) {
            BusiOm vo = new BusiOm();
            BeanUtils.copyProperties(main, vo);
            pageList.add(vo);
        }

        return Result.OK(pageList);
    }


    /**
     * 导出 busiOmItem excel
     *
     * @param request
     * @param busiOmItem
     */
    @RequestMapping(value = "/ExportBusiOmItem")
    public Result<?> ExportBusiOmItem(HttpServletRequest request, BusiOmItem busiOmItem) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<BusiOmItem> queryWrapper = QueryGenerator.initQueryWrapper(busiOmItem, request.getParameterMap());
        //Step.2 获取导出数据
        List<BusiOmItem> queryList = busiOmItemService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<BusiOmItem> busiPoList = new ArrayList<BusiOmItem>();
        if (oConvertUtils.isEmpty(selections)) {
            busiPoList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            busiPoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }
        List<BusiOmItem> pageList = new ArrayList<BusiOmItem>();
        for (BusiOmItem main : busiPoList) {
            BusiOmItem vo = new BusiOmItem();
            BeanUtils.copyProperties(main, vo);
            pageList.add(vo);
        }
        return Result.OK(pageList);
    }

}
