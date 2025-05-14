package com.base.modules.jeeerp.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.modules.client.ISysBaseAPIClient;
import com.base.modules.jeeerp.entity.*;
import com.base.modules.jeeerp.mapper.BusiPrdOrdMapper;
import com.base.modules.jeeerp.service.*;
import com.base.modules.jeeerp.vo.BusiPrdOrdbzddexport;
import com.base.modules.jeeerp.vo.BusiPrdOrdsPage;
import com.base.modules.jeeerp.vo.BusiPrdOrdssPage;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.IMdCusService;
import com.base.modules.jeewms.service.IMdSupService;
import com.base.modules.jeewms.service.IWvStockSttQueryService;
import com.base.modules.util.DayiUtils;
import com.base.modules.util.GenerateCodeUtil;
import com.base.modules.util.PltnPushWms;
import com.base.modules.util.StringUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import com.base.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import com.base.common.api.vo.Result;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeeerp.vo.BusiPrdOrdPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

import static com.base.common.util.DateUtils.now;


/**
 * @Description: busi_prd_ord
 * @Author: base-boot
 * @Date: 2022-11-19
 * @Version: V1.0
 */
@Api(tags = "busi_prd_ord")
@RestController
@RequestMapping("/jeeerp/busiPrdOrd")
@Slf4j
public class BusiPrdOrdController {
    @Autowired
    private IBusiPrdOrdService busiPrdOrdService;
    @Autowired
    private IBusiPrdOrdItemService busiPrdOrdItemService;
    @Autowired
    private IBusiOrdCraftService busiOrdCraftService;
    @Autowired
    private GenerateCodeUtil generateCodeUtil;
    @Autowired
    private PltnPushWms pltnPushWms;
    @Autowired
    private BusiPrdOrdMapper busiPrdOrdMapper;
    @Autowired
    private ISysBaseAPIClient sysBaseAPIClient;

    @Autowired
    private IBusiPoService busiPoService;

    @Autowired
    private IMdSupService mdSupService;

    @Autowired
    private IBaseScheduleInfoService baseScheduleInfoService;

    @Autowired
    private IMdCusService mdCusService;

    /**
     * 分页列表查询
     *
     * @param busiPrdOrd
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "busi_prd_ord-分页列表查询")
    @ApiOperation(value = "busi_prd_ord-分页列表查询", notes = "busi_prd_ord-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(BusiPrdOrd busiPrdOrd,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(name="sT",required = false) String  sT,
                                   @RequestParam(name="nT",required = false) String nT,
                                   HttpServletRequest req) {
        QueryWrapper<BusiPrdOrd> queryWrapper = QueryGenerator.initQueryWrapper(busiPrdOrd, req.getParameterMap());
        if(StringUtils.isNotBlank(sT)){
            queryWrapper.lambda().ge(BusiPrdOrd::getCreateTime,sT);
        }
        if(StringUtils.isNotBlank(nT)){
            queryWrapper.lambda().le(BusiPrdOrd::getCreateTime,nT);
        }
        Page<BusiPrdOrd> page = new Page<BusiPrdOrd>(pageNo, pageSize);
        IPage<BusiPrdOrd> pageList = busiPrdOrdService.page(page, queryWrapper);
        for (BusiPrdOrd record : pageList.getRecords()) {
            QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
            MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,record.getQuery24());
            List<MdCus> list = mdCusService.list(MdCusqueryWrapper);
            if(CollectionUtil.isNotEmpty(list)){
                MdCus mdCus = list.get(0);
                record.setXingYeFenLei(mdCus.getXingYeFenLei());
            }
        }
        return Result.OK(pageList);
    }



    /**
     * 分页列表查询
     *
     * @param busiPrdOrd
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "BusiPrdOrdItem-分页列表查询")
    @ApiOperation(value = "BusiPrdOrdItem-分页列表查询", notes = "BusiPrdOrdItem-分页列表查询")
    @GetMapping(value = "/BusiPrdOrdItemList")
    public Result<?> queryPageList(BusiPrdOrdItem busiPrdOrdItem,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(name="sT",required = false) String  sT,
                                   @RequestParam(name="sT01",required = false) String  sT01,
                                   @RequestParam(name="nT",required = false) String nT,
                                   @RequestParam(name="nT01",required = false) String nT01,
                                   HttpServletRequest req) {
        QueryWrapper<BusiPrdOrdItem> queryWrapper = QueryGenerator.initQueryWrapper(busiPrdOrdItem, req.getParameterMap());
        if(StringUtils.isNotBlank(sT)){
            queryWrapper.lambda().ge(BusiPrdOrdItem::getCreateTime,sT);
        }
        if(StringUtils.isNotBlank(nT)){
            queryWrapper.lambda().le(BusiPrdOrdItem::getCreateTime,nT);
        }
        if(StringUtils.isNotBlank(sT01)){
            queryWrapper.lambda().ge(BusiPrdOrdItem::getQuery19,sT01);
        }
        if(StringUtils.isNotBlank(nT01)){
            queryWrapper.lambda().le(BusiPrdOrdItem::getQuery19,nT01);
        }
        Page<BusiPrdOrdItem> page = new Page<BusiPrdOrdItem>(pageNo, pageSize);
        IPage<BusiPrdOrdItem> pageList = busiPrdOrdItemService.page(page, queryWrapper);
        return Result.OK(pageList);
    }



    /**
     * 添加
     *
     * @param busiPrdOrdPage
     * @return
     */
    @AutoLog(value = "busi_prd_ord-添加")
    @ApiOperation(value = "busi_prd_ord-添加", notes = "busi_prd_ord-添加")
    @PostMapping(value = "/add")
    public synchronized Result<?> add(@RequestBody BusiPrdOrdPage busiPrdOrdPage) {
        busiPrdOrdService.BusiPrdOrdPageAdd(busiPrdOrdPage);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param busiPrdOrdPage
     * @return
     */
    @AutoLog(value = "busi_prd_ord-编辑")
    @ApiOperation(value = "busi_prd_ord-编辑", notes = "busi_prd_ord-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody BusiPrdOrdPage busiPrdOrdPage) {
        BusiPrdOrd busiPrdOrd = new BusiPrdOrd();
        BeanUtils.copyProperties(busiPrdOrdPage, busiPrdOrd);
        BusiPrdOrd busiPrdOrdEntity = busiPrdOrdService.getById(busiPrdOrd.getId());
        if (busiPrdOrdEntity == null) {
            return Result.error("未找到对应数据");
        }
//        if (!busiPrdOrdPage.getQuery01().equals("JG")) {
//            Result<?> result = sysBaseAPIClient.queryPageList2(busiPrdOrdPage.getQuery14(), 100);
//            if (result.getCode() != 200) {
//                return Result.error("未查询出排班数据");
//            }
//            JSONObject jsonObject = JSONObject.parseObject(result.getResult().toString());
//            JSONArray records = jsonObject.getJSONArray("records");
//            String str1 = "";
//            for (int i = 0; i < records.size(); i++) {
//                JSONObject object = records.getJSONObject(i);
//                String id = object.getString("id");
//                if (str1 == "") {
//                    str1 = id;
//                } else {
//                    str1 = str1 + ',' + id;
//                }
//            }
//            sysBaseAPIClient.deleteBatch(str1);
//            if (busiPrdOrdPage.getQuery21() != null && busiPrdOrdPage.getQuery21() != "") {
//                String[] split = busiPrdOrdPage.getQuery21().split("~");
//                List<String> betweenDate = DayiUtils.getBetweenDate(split[0], split[1]);
//                for (String s : betweenDate) {
//                    BaseScheduleInfo baseScheduleInfo = new BaseScheduleInfo();
//                    baseScheduleInfo.setPlanDate(s); // 日期
//                    baseScheduleInfo.setUserNo(busiPrdOrd.getQuery25()); // 质检
//                    baseScheduleInfo.setAttr1("仓库验货"); // 地点
//                    // 工作内容
//                    try {
//                        String str = "质检" + busiPrdOrd.getQuery14() + " - 数量：" + busiPrdOrdPage.getBusiPrdOrdItemList().get(0).getNum01().toString();
//                        baseScheduleInfo.setAttr2(str);
//                    } catch (Exception e) {
//
//                    }
//                    baseScheduleInfo.setPbType("1");
//
//                    // 指派人
//                    LoginUser login = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//                    baseScheduleInfo.setAttr3(login.getRealname());
//                    // 指派日期
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date now = new Date();
//                    String time = sdf.format(now);
//                    baseScheduleInfo.setAttr4(time);
//                    baseScheduleInfo.setAttr5("采购：" + busiPrdOrd.getQuery16() + " 跟单：" + busiPrdOrd.getQuery28());
//                    sysBaseAPIClient.add(baseScheduleInfo);
//                }
//            }
//        }
        busiPrdOrdService.updateMain(busiPrdOrd, busiPrdOrdPage.getBusiPrdOrdItemList(), busiPrdOrdPage.getBusiOrdCraftList());

        // 更新领料信息，加工完成的销售单号和内部发票
        if((busiPrdOrd.getQuery17() != null && busiPrdOrd.getQuery17() != "") || (busiPrdOrd.getLink03() != null && busiPrdOrd.getLink03() != "") || (busiPrdOrd.getQuery33() != null && busiPrdOrd.getQuery33() != "") ){
            QueryWrapper<BusiPrdOrd> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiPrdOrd::getQuery13,busiPrdOrd.getQuery13());
            queryWrapper.lambda().and(wq->{
                wq.eq(BusiPrdOrd::getQuery01,"SCDD");
                wq.or().eq(BusiPrdOrd::getQuery01,"SCWG");
            });
            List<BusiPrdOrd> list = busiPrdOrdService.list(queryWrapper);
            if(CollectionUtil.isNotEmpty(list)){
                for (BusiPrdOrd prdOrd : list) {
                    prdOrd.setQuery17(busiPrdOrd.getQuery17());
                    prdOrd.setLink03(busiPrdOrd.getLink03());
                    prdOrd.setQuery33(busiPrdOrd.getQuery33());
                    prdOrd.setQuery35(busiPrdOrd.getQuery35());
                    busiPrdOrdService.updateById(prdOrd);
                }
            }



        }




        return Result.OK("编辑成功!");
    }

    /**
     * 编辑主表
     *
     * @param busiPrdOrdPage
     * @return
     */
    @AutoLog(value = "busi_prd_ord-编辑主表")
    @ApiOperation(value = "busi_prd_ord-编辑主表", notes = "busi_prd_ord-编辑主表")
    @PutMapping(value = "/edit2")
    public Result<?> edit2(@RequestBody BusiPrdOrdPage busiPrdOrdPage) {
        BusiPrdOrd busiPrdOrd = new BusiPrdOrd();
        BeanUtils.copyProperties(busiPrdOrdPage, busiPrdOrd);
        BusiPrdOrd busiPrdOrdEntity = busiPrdOrdService.getById(busiPrdOrd.getId());
        if (busiPrdOrdEntity == null) {
            return Result.error("未找到对应数据");
        }
        busiPrdOrdMapper.updateById(busiPrdOrd);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "busi_prd_ord-通过id删除")
    @ApiOperation(value = "busi_prd_ord-通过id删除", notes = "busi_prd_ord-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        busiPrdOrdService.delMain(id);
        return Result.OK("删除成功!");
    }




    @AutoLog(value = "busi_prd_ord-编辑")
    @ApiOperation(value = "busi_prd_ord-编辑", notes = "busi_prd_ord-编辑")
    @PutMapping(value = "/itemedit")
    public Result<?> itemdelete(@RequestBody BusiPrdOrdItem busiprdorditem) {
        busiPrdOrdItemService.updateById(busiprdorditem);
        return Result.OK("成功!");
    }


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "busi_prd_ord-通过edit")
    @ApiOperation(value = "busi_prd_ord-通过edit", notes = "busi_prd_ord-通过edit")
    @DeleteMapping(value = "/itemdelete")
    public Result<?> itemdelete(@RequestParam(name = "id", required = true) String id) {
        LambdaQueryWrapper<BusiPrdOrdItem> busiPrdOrdItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        busiPrdOrdItemLambdaQueryWrapper.eq(BusiPrdOrdItem::getId,id);
        busiPrdOrdItemService.remove(busiPrdOrdItemLambdaQueryWrapper);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "busi_prd_ord-批量删除")
    @ApiOperation(value = "busi_prd_ord-批量删除", notes = "busi_prd_ord-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.busiPrdOrdService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "busi_prd_ord-通过id查询")
    @ApiOperation(value = "busi_prd_ord-通过id查询", notes = "busi_prd_ord-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        BusiPrdOrd busiPrdOrd = busiPrdOrdService.getById(id);
        if (busiPrdOrd == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(busiPrdOrd);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "busi_prd_ord_item通过主表ID查询")
    @ApiOperation(value = "busi_prd_ord_item主表ID查询", notes = "busi_prd_ord_item-通主表ID查询")
    @GetMapping(value = "/queryBusiPrdOrdItemByMainId")
    public Result<?> queryBusiPrdOrdItemListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<BusiPrdOrdItem> busiPrdOrdItemList = busiPrdOrdItemService.selectByMainId(id);
        return Result.OK(busiPrdOrdItemList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "busi_ord_craft通过主表ID查询")
    @ApiOperation(value = "busi_ord_craft主表ID查询", notes = "busi_ord_craft-通主表ID查询")
    @GetMapping(value = "/queryBusiOrdCraftByMainId")
    public Result<?> queryBusiOrdCraftListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<BusiOrdCraft> busiOrdCraftList = busiOrdCraftService.selectByMainId(id);
        return Result.OK(busiOrdCraftList);
    }

    /**
     * 推送成品到WMS
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "busi_prd_ord-推送成品到WMS")
    @ApiOperation(value = "busi_prd_ord-推送成品到WMS", notes = "busi_prd_ord-推送成品到WMS")
    @GetMapping(value = "/finishedPutin")
    public Result<?> finishedPutin(@RequestParam(name = "ids") String ids) {
        String[] split = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }
        List<BusiPrdOrd> busiPrdOrds = busiPrdOrdService.listByIds(list);
        // 推送WMS
        // 按主PO分次推送WMS
        Map<String, List<BusiPrdOrd>> busiPo = busiPrdOrds.stream().collect(Collectors.groupingBy(BusiPrdOrd::getQuery13, Collectors.toList()));
        for (Map.Entry<String, List<BusiPrdOrd>> entry : busiPo.entrySet()) {
            List<BusiPrdOrd> value = entry.getValue();
            pltnPushWms.finishedPutin(value);
        }


        return Result.OK("推送成功");
    }

    /**
     * 推送材料出库到WMS
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "busi_prd_ord-推送材料出库到WMS")
    @ApiOperation(value = "busi_prd_ord-推送材料出库到WMS", notes = "busi_prd_ord-推送材料出库到WMS")
    @GetMapping(value = "/materialsPutin")
    public Result<?> materialsPutin(@RequestParam(name = "ids") String ids) {
        String[] split = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }
        List<BusiPrdOrd> busiPrdOrds = busiPrdOrdService.listByIds(list);
        for (BusiPrdOrd busiPrdOrd : busiPrdOrds) {
            // 推送WMS
            pltnPushWms.materialsPutout(busiPrdOrd,"");
        }
        return Result.OK("推送成功");
    }

    /**
     * 修改加工期限
     *
     * @param ordId
     * @param query02
     * @param req
     * @return
     */
    @AutoLog(value = "busi_prd_ord-修改单据类型")
    @ApiOperation(value = "busi_prd_ord-修改单据类型", notes = "busi_prd_ord-修改单据类型")
    @GetMapping(value = "/updatequery02")
    public Result<?> updatequery02(
            @RequestParam(name = "ordId", required = true) String ordId,
            @RequestParam(name = "query02", required = true) String query02,
            HttpServletRequest req) {
        QueryWrapper<BusiPrdOrd> queryWrapperdd = new QueryWrapper<>();
        queryWrapperdd.lambda().eq(BusiPrdOrd::getQuery04, ordId);
        BusiPrdOrd onedd = busiPrdOrdService.getOne(queryWrapperdd, false);
        if(onedd == null ){
            return Result.error("查询不到订单");
        }
        onedd.setQuery02(query02);
        busiPrdOrdService.updateById(onedd);
        return Result.OK("成功");
    }


    /**
     * 修改加工期限
     *
     * @param ordId
     * @param query21
     * @param query22
     * @param req
     * @return
     */
    @AutoLog(value = "busi_prd_ord-修改加工期限")
    @ApiOperation(value = "busi_prd_ord-修改加工期限", notes = "busi_prd_ord-修改加工期限")
    @GetMapping(value = "/updateTimeLimit")
    public Result<?> updateTimeLimit(
            @RequestParam(name = "ordId", required = true) String ordId,
            @RequestParam(name = "query21", required = false) String query21,
            @RequestParam(name = "query22", required = false) String query22,
            @RequestParam(name = "query19", required = false) String query19,
            @RequestParam(name = "num04", required = false) String num04,
            @RequestParam(name = "type", required = true) String type,
            HttpServletRequest req) {

        try {
            QueryWrapper<BusiPrdOrd> queryWrapperdd = new QueryWrapper<>();
            queryWrapperdd.lambda().eq(BusiPrdOrd::getQuery04, ordId);
            BusiPrdOrd onedd = busiPrdOrdService.getOne(queryWrapperdd, false);
            if ("timeLimit".equals(type)) {
                onedd.setQuery21(query21);
            } else if ("finish".equals(type)) {
                onedd.setQuery22(query22);
                onedd.setQuery19(query19);
                onedd.setQuery02("已完成");
            }
            if(!StringUtil.isBlankOrNull(num04)){
                onedd.setNum04(Double.parseDouble(num04));
            }
            busiPrdOrdService.updateById(onedd);
        } catch (Exception e) {

        }
        if (!"finish".equals(type)) {
            QueryWrapper<BusiPrdOrd> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiPrdOrd::getLink02, ordId);
            queryWrapper.lambda().eq(BusiPrdOrd::getQuery01, "SCWG");
            BusiPrdOrd one = busiPrdOrdService.getOne(queryWrapper, false);

            QueryWrapper<BusiPrdOrd> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(BusiPrdOrd::getQuery04, ordId);
            BusiPrdOrd one2 = busiPrdOrdService.getOne(queryWrapper2, false);
            if ("timeLimit".equals(type)) {
                one.setQuery21(query21);
            } else if ("finish".equals(type)) {
                one.setQuery22(query22);
                one.setQuery19(query19);
            }
            if ("ZJDD".equals(one2.getQuery01())) {
                Result<?> result = sysBaseAPIClient.queryPageList2(one.getQuery14(), 100);
                if (result.getCode() != 200) {
                    return Result.error("未查询出排班数据");
                }
                JSONObject jsonObject = JSONObject.parseObject(result.getResult().toString());
                JSONArray records = jsonObject.getJSONArray("records");
                String str1 = "";
                for (int i = 0; i < records.size(); i++) {
                    JSONObject object = records.getJSONObject(i);
                    String id = object.getString("id");
                    if (str1 == "") {
                        str1 = id;
                    } else {
                        str1 = str1 + ',' + id;
                    }
                }
                sysBaseAPIClient.deleteBatch(str1);
                if (!StringUtil.isBlankOrNull(query21)) {
                    String[] split = query21.split("~");
                    List<String> betweenDate = DayiUtils.getBetweenDate(split[0], split[1]);
                    for (String s : betweenDate) {
                        BaseScheduleInfo baseScheduleInfo = new BaseScheduleInfo();
                        baseScheduleInfo.setPlanDate(s); // 日期
                        baseScheduleInfo.setUserNo(one.getQuery25()); // 质检

                        baseScheduleInfo.setAttr1("仓库验货"); // 地点
                        // 工作内容
                        String str = one.getQuery09() + "- 质检" + one.getQuery14()+ " - 品名：" + one.getQuery11() + " - 数量：" + one.getNum01().toString();
                        baseScheduleInfo.setAttr2(str);
                        baseScheduleInfo.setPbType("1");
                        // 指派人
                        LoginUser login = (LoginUser) SecurityUtils.getSubject().getPrincipal();
                        baseScheduleInfo.setAttr3(login.getRealname());
                        // 指派日期
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date now = new Date();
                        String time = sdf.format(now);
                        baseScheduleInfo.setAttr4(time);
                        baseScheduleInfo.setAttr5("采购：" + one.getQuery16() + " 跟单：" + one.getQuery28());
                        sysBaseAPIClient.add(baseScheduleInfo);
                    }
                }
            }
            busiPrdOrdService.updateById(one);
        }
        return Result.OK();
    }

    /**
     * 采购排班
     *
     * @param ordId
     * @param query21
     * @param query22
     * @param req
     * @return
     */
    @AutoLog(value = "busi_prd_ord-采购排班")
    @ApiOperation(value = "busi_prd_ord-采购排班", notes = "busi_prd_ord-采购排班")
    @GetMapping(value = "/updateTimeLimit2")
    public Result<?> updateTimeLimit2(
            @RequestParam(name = "ordId", required = true) String ordId,
            @RequestParam(name = "query21", required = false) String query21,
            @RequestParam(name = "query22", required = false) String query22,
            HttpServletRequest req) {
        QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<BusiPo>();
        queryWrapper.lambda().eq(BusiPo::getQuery04, ordId);
        BusiPo one = busiPoService.getOne(queryWrapper, false);
        if (!"JG".equals(one.getQuery01())) {
            Result<?> result = sysBaseAPIClient.queryPageList2(one.getQuery13(), 100);
            if (result.getCode() == 200) {
                JSONObject jsonObject = JSONObject.parseObject(result.getResult().toString());
                JSONArray records = jsonObject.getJSONArray("records");
                String str1 = "";
                for (int i = 0; i < records.size(); i++) {
                    JSONObject object = records.getJSONObject(i);
                    String id = object.getString("id");
                    if (StringUtil.isBlankOrNull(str1)) {
                        str1 = id;
                    } else {
                        str1 = str1 + ',' + id;
                    }
                }
                sysBaseAPIClient.deleteBatch(str1);
            }
            if (!StringUtil.isBlankOrNull(query21)) {
                String[] split = query21.split("~");
                List<String> betweenDate = DayiUtils.getBetweenDate(split[0], split[1]);
                for (String s : betweenDate) {
                    QueryWrapper<BaseScheduleInfo> MdSupqueryWrapperbs = new QueryWrapper<>();
                    MdSupqueryWrapperbs.lambda().eq(BaseScheduleInfo::getPlanDate, s);
                    MdSupqueryWrapperbs.lambda().eq(BaseScheduleInfo::getUserNo, query22);
                    List<BaseScheduleInfo> list = baseScheduleInfoService.list(MdSupqueryWrapperbs);
                    if (CollectionUtil.isNotEmpty(list)) {
                        return Result.error(s+"-"+query22+"-存在排班排班数据");
                    }
                }
                for (String s : betweenDate) {
                    BaseScheduleInfo baseScheduleInfo = new BaseScheduleInfo();
                    baseScheduleInfo.setPlanDate(s); // 日期
                    baseScheduleInfo.setUserNo(query22); // 质检

                    QueryWrapper<MdSup> MdSupqueryWrapper = new QueryWrapper<>();
                    MdSupqueryWrapper.lambda().eq(MdSup::getGysBianMa, one.getQuery08());
                    MdSup one2 = mdSupService.getOne(MdSupqueryWrapper, false);

                    baseScheduleInfo.setAttr1(one2.getDiZhi()); // 地点
                    // 工作内容
//                    深圳市华万达电子有限公司 - 质检PO23000210A - 品名：TISSUE BOX - 数量：100.0
//                    String str = "子PO号" + one.getQuery14() + " - 数量：" + one.getNum01().toString();
                    String str = one.getQuery09() + "- 质检" + one.getQuery14()+ " - 品名：" + one.getQuery11() + " - 数量：" + one.getNum01().toString();

                    baseScheduleInfo.setAttr2(str);
                    baseScheduleInfo.setPbType("1");
                    // 指派人
                    LoginUser login = (LoginUser) SecurityUtils.getSubject().getPrincipal();
                    baseScheduleInfo.setAttr3(login.getRealname());
                    // 指派日期
                    baseScheduleInfo.setAttr4(now());
                    baseScheduleInfo.setAttr5("采购：" + one.getQuery16() + " 跟单：" + one.getQuery28());
                    sysBaseAPIClient.add(baseScheduleInfo);
                }
            }
        }
        one.setQuery18(query21);
        one.setQuery19(query22);
        busiPoService.updateById(one);
        return Result.OK();
    }


    /**
     * 导出质检订单excel
     *
     * @param request
     * @param busiPrdOrd
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BusiPrdOrd busiPrdOrd) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<BusiPrdOrd> queryWrapper = QueryGenerator.initQueryWrapper(busiPrdOrd, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 获取导出数据
        List<BusiPrdOrd> queryList = busiPrdOrdService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<BusiPrdOrd> busiPrdOrdList = new ArrayList<BusiPrdOrd>();
        if (oConvertUtils.isEmpty(selections)) {
            busiPrdOrdList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            busiPrdOrdList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<BusiPrdOrdsPage> pageList = new ArrayList<BusiPrdOrdsPage>();
        for (BusiPrdOrd main : busiPrdOrdList) {
            BusiPrdOrdsPage vo = new BusiPrdOrdsPage();
            BeanUtils.copyProperties(main, vo);
            QueryWrapper<BusiPrdOrdItem> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("link02", main.getQuery04());
            List<BusiPrdOrdItem> busiPrdOrdItemList = busiPrdOrdItemService.list(queryWrapper1);
            vo.setBusiPrdOrdItemList(busiPrdOrdItemList);

            QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
            MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,vo.getQuery24());
            MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
            if(one2 != null){
                vo.setQuery24(one2.getZhongWenQch());
                vo.setXingYeFenLei(one2.getXingYeFenLei());
            }
            //List<BusiOrdCraft> busiOrdCraftList = busiOrdCraftService.selectByMainId(main.getId());
            //vo.setBusiOrdCraftList(busiOrdCraftList);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "质检订单");
        mv.addObject(NormalExcelConstants.CLASS, BusiPrdOrdsPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品信息", "导出人:" + sysUser.getRealname(), "busi_prd_ord"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }





    /**
     * 导出委外质检记录
     *
     * @param request
     * @param busiPrdOrd
     */
    @RequestMapping(value = "/exportXlsItem")
    public ModelAndView exportXlsItem(HttpServletRequest request, BusiPrdOrdItem busiPrdOrd) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<BusiPrdOrdItem> queryWrapper = QueryGenerator.initQueryWrapper(busiPrdOrd, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 获取导出数据
        List<BusiPrdOrdItem> queryList = busiPrdOrdItemService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<BusiPrdOrdItem> busiPrdOrdList = new ArrayList<BusiPrdOrdItem>();
        if (oConvertUtils.isEmpty(selections)) {
            busiPrdOrdList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            busiPrdOrdList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<BusiPrdOrdItem2> pageList = new ArrayList<BusiPrdOrdItem2>();
        for (BusiPrdOrdItem main : busiPrdOrdList) {
            BusiPrdOrdItem2 vo = new BusiPrdOrdItem2();
            BeanUtils.copyProperties(main, vo);
            pageList.add(vo);
        }
        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "质检订单");
        mv.addObject(NormalExcelConstants.CLASS, BusiPrdOrdItem2.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品信息", "导出人:" + sysUser.getRealname(), "busi_prd_ord"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 导入委外质检记录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcelItem", method = RequestMethod.POST)
    public Result<?> importExcelItem(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<BusiPrdOrdItem2> list = ExcelImportUtil.importExcel(file.getInputStream(), BusiPrdOrdItem2.class, params);
                for (BusiPrdOrdItem2 page : list) {
                    BusiPrdOrdItem po = new BusiPrdOrdItem();
                    BeanUtils.copyProperties(page, po);
                    po.setQuery31("QCJYJL");
                    busiPrdOrdItemService.save(po);
                }
                return Result.OK("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.OK("文件导入失败！");
    }


    /**
     * 导出加工订单excel
     *
     * @param request
     * @param busiPrdOrd
     */
    @RequestMapping(value = "/exportXls2")
    public ModelAndView exportXls2(HttpServletRequest request, BusiPrdOrd busiPrdOrd) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<BusiPrdOrd> queryWrapper = QueryGenerator.initQueryWrapper(busiPrdOrd, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 获取导出数据
        List<BusiPrdOrd> queryList = busiPrdOrdService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<BusiPrdOrd> busiPrdOrdList = new ArrayList<BusiPrdOrd>();
        if (oConvertUtils.isEmpty(selections)) {
            busiPrdOrdList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            busiPrdOrdList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<BusiPrdOrdbzddexport> pageList = new ArrayList<BusiPrdOrdbzddexport>();
        for (BusiPrdOrd main : busiPrdOrdList) {
            BusiPrdOrdbzddexport vo = new BusiPrdOrdbzddexport();
            BeanUtils.copyProperties(main, vo);
            try {
                QueryWrapper<MdCus> MdCusqueryWrapper = new QueryWrapper<>();
                MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa, main.getQuery24());
                MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
                vo.setQuery24(one2.getZhongWenQch());
                vo.setXingYeFenLei(one2.getXingYeFenLei());
            } catch (Exception e) {

            }
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "包装订单");
        mv.addObject(NormalExcelConstants.CLASS, BusiPrdOrdbzddexport.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("包装订单", "导出人:" + sysUser.getRealname(), "包装订单"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

//    /**
//     * 导出加工排班订单excel
//     *
//     * @param request
//     * @param busiPrdOrd
//     */
//    @RequestMapping(value = "/exportXls3")
//    public ModelAndView exportXls3(HttpServletRequest request, BusiPrdOrd busiPrdOrd) {
//        // Step.1 组装查询条件查询数据
//        QueryWrapper<BusiPrdOrd> queryWrapper = QueryGenerator.initQueryWrapper(busiPrdOrd, request.getParameterMap());
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//
//        //Step.2 获取导出数据
//        List<BusiPrdOrd> queryList = busiPrdOrdService.list(queryWrapper);
//        // 过滤选中数据
//        String selections = request.getParameter("selections");
//        List<BusiPrdOrd> busiPrdOrdList = new ArrayList<BusiPrdOrd>();
//        if (oConvertUtils.isEmpty(selections)) {
//            busiPrdOrdList = queryList;
//        } else {
//            List<String> selectionList = Arrays.asList(selections.split(","));
//            busiPrdOrdList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
//        }
//
//        // Step.3 组装pageList
//        List<BusiPrdOrdssPage> pageList = new ArrayList<BusiPrdOrdssPage>();
//        for (BusiPrdOrd main : busiPrdOrdList) {
//            BusiPrdOrdssPage vo = new BusiPrdOrdssPage();
//            BeanUtils.copyProperties(main, vo);
//            QueryWrapper<BusiPrdOrdItem> queryWrapper1 = new QueryWrapper<>();
//            queryWrapper1.eq("link02", main.getQuery04());
//            List<BusiPrdOrdItem> busiPrdOrdItemList = busiPrdOrdItemService.list(queryWrapper1);
//            vo.setBusiPrdOrdItemList(busiPrdOrdItemList);
//            //List<BusiOrdCraft> busiOrdCraftList = busiOrdCraftService.selectByMainId(main.getId());
//            //vo.setBusiOrdCraftList(busiOrdCraftList);
//            pageList.add(vo);
//        }
//
//        // Step.4 AutoPoi 导出Excel
//        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//        mv.addObject(NormalExcelConstants.FILE_NAME, "加工排班订单");
//        mv.addObject(NormalExcelConstants.CLASS, BusiPrdOrdssPage.class);
//        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品信息", "导出人:" + sysUser.getRealname(), "busi_prd_ord"));
//        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
//        return mv;
//    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<BusiPrdOrdPage> list = ExcelImportUtil.importExcel(file.getInputStream(), BusiPrdOrdPage.class, params);
                for (BusiPrdOrdPage page : list) {
                    BusiPrdOrd po = new BusiPrdOrd();
                    BeanUtils.copyProperties(page, po);
                    busiPrdOrdService.saveMain(po, page.getBusiPrdOrdItemList(), page.getBusiOrdCraftList());
                }
                return Result.OK("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.OK("文件导入失败！");
    }

    /**
     * 完成加工入库
     *
     * @param ordId
     * @return
     */
    @AutoLog(value = "busi_prd_ord-采购排班")
    @ApiOperation(value = "busi_prd_ord-采购排班", notes = "busi_prd_ord-采购排班")
    @GetMapping(value = "/updateTimeLimit3")
    public Result<?> updateTimeLimit3(
            @RequestParam(name = "ordId", required = true) String ordId
    ){
        QueryWrapper<BusiPrdOrd> querywrapper = new QueryWrapper<>();
        querywrapper.lambda().eq(BusiPrdOrd::getQuery04,ordId);
        BusiPrdOrd one = busiPrdOrdService.getOne(querywrapper,false);
        one.setQuery02("已完成");
        busiPrdOrdService.updateById(one);
        return Result.OK("操作成功");
    }

}
