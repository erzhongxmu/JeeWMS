package com.base.modules.jeewms.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.util.DateUtils;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.entity.gs.WmImNoticeHPagegs;
import com.base.modules.jeewms.entity.gs.WmImNoticeIgs;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.service.impl.SmsSendImpl;
import com.base.modules.jeewms.vo.WmImNoticeHPage;
import com.base.modules.jeewms.vo.WmImNoticeHPages;
import com.base.modules.jeewms.vo.WmImNoticeHTwoPage;
import com.base.modules.jobhandler.U8JiekouXxlJob;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

;

/**
 * @Description: wm_im_notice_h
 * @Author: base-boot
 * @Date: 2021-05-21
 * @Version: V1.0
 */
@Api(tags = "进货管理")
@RestController
@RequestMapping("/jeewms/wmImNoticeH")
@Slf4j
public class WmImNoticeHController extends JeecgController<WmImNoticeH, IWmImNoticeHService> {
    ExecutorService executor = Executors.newFixedThreadPool(8);
    @Autowired
    private IWmImNoticeHService wmImNoticeHService;
    @Autowired
    private IWmImNoticeIService wmImNoticeIService;
    @Autowired
    private IWmInQmIService wmInQmIService;
    @Autowired
    private U8JiekouXxlJob u8JiekouXxlJob;

    @Autowired
    private IBaUnitService baUnitService;

    @Autowired
    private IWmToUpGoodsService wmToUpGoodsService;

    @Autowired
    private IWmToDownGoodsService wmToDownGoodsService;

    @Autowired
    private IWmOmNoticeHService noticeHService;

    @Autowired
    private IBaGoodsTypeService baGoodsTypeService;

    @Autowired
    private IMdGoodsService goodsService;

    @Autowired
    private IMdCusService cusService;

    @Autowired
    private IMdSupService supService;

    @Value("${jeecg.pritIp}")
    private String pritIp;

    @Value("${jeecg.pritIp2}")
    private String pritIp2;

    @Autowired
    private SmsSendImpl smsSend;

    @Autowired
    private IWvStockSttService wvStockSttService;


    /**
     * 分页列表查询
     *
     * @param wmImNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "客户进货、预约进货-分页列表查询", notes = "客户进货、预约进货-分页列表查询")
    @GetMapping(value = "/cusList")
    public Result<?> queryCusPageList(WmImNoticeH wmImNoticeH,
                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      HttpServletRequest req) {
        IPage<WmImNoticeH> wmImNoticeHIPage = wmImNoticeHService.selectCusList(wmImNoticeH, pageNo, pageSize, req);
        return Result.ok(wmImNoticeHService.selectCusList(wmImNoticeH, pageNo, pageSize, req));
       /* IPage<WmImNoticeH> page = new Page<WmImNoticeH>(pageNo, pageSize);
        List<WvStockStt> wvStockSttList = wvStockSttService.lambdaQuery().groupBy(WvStockStt::getGoodsBatch).eq(WvStockStt::getKuctype, "库存").list();
        String str = "";
        for (WvStockStt wvStockStt : wvStockSttList) {
            List<WmImNoticeI> list = wmImNoticeIService.lambdaQuery().eq(WmImNoticeI::getGoodsBatch, wvStockStt.getGoodsBatch()).list();
            if(CollectionUtil.isNotEmpty(list)){
                str +=""+list.get(0).getImNoticeId()+",";
            }
        }
        str = str.substring(0,str.length()-1);
        List<WmImNoticeH> WmImNoticeHlist = wmImNoticeHService.lambdaQuery().in(WmImNoticeH::getNoticeId,str.split(",")).list();
        IPage<WmImNoticeH> wmImNoticeHPage = page.setRecords(WmImNoticeHlist);*/


        //return Result.ok(wmImNoticeHPage);
    }
    /**
     * 分页列表查询
     *
     * @param wmImNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "越库订单-分页列表查询", notes = "越库订单-分页列表查询")
    @GetMapping(value = "/coressList")
    public Result<?> queryCoressListPageList(WmImNoticeH wmImNoticeH,
                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                             HttpServletRequest req) {

        return Result.ok(wmImNoticeHService.selectCressList(wmImNoticeH, pageNo, pageSize, req));
    }

    /**
     * 客户退货、退货预约
     *
     * @param wmImNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "客户退货、退货预约-分页列表查询", notes = "客户退货、退货预约-分页列表查询")
    @GetMapping(value = "/thList")
    public Result<?> queryThPageList(WmImNoticeH wmImNoticeH,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                     HttpServletRequest req) {

        return Result.ok(wmImNoticeHService.selectThList(wmImNoticeH, pageNo, pageSize, req));
    }


    /**
     * 批量验收
     *
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "批量验收-分页列表查询", notes = "批量验收-分页列表查询")
    @GetMapping(value = "/batchList")
    public Result<?> queryBatchPageList(WmImNoticeI wmImNoticeI,
                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                        HttpServletRequest req) {
        return Result.ok(wmImNoticeIService.queryBatchPageList(wmImNoticeI, pageNo, pageSize, req));
    }


    /**
     * 打印验收单
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/doPrintYsd")
    @ApiOperation(value = "打印验收单")
    public void doPrintYsd(@RequestParam String id, HttpServletResponse response) {
        wmImNoticeHService.doPrintYsd(id, response);
    }

    /**
     * 打印入库单
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/doPrintRkd")
    @ApiOperation(value = "打印入库单")
    public void doPrintRkd(@RequestParam String id, HttpServletResponse response) {
        wmImNoticeHService.doPrintRkd(id, response);
    }

    @GetMapping("/doPrintBq")
    @ApiOperation(value = "导出标签")
    public void doPrintBq(@RequestParam String id, HttpServletResponse response) {
        wmImNoticeHService.doPrintBq(id, response);
    }

    @AutoLog(value = "打印标签")
    @ApiOperation(value = "打印标签", notes = "打印标签")
    @GetMapping(value = "/doPrintBqList")
    public Result<?> doPrintBqList(@RequestParam String id, HttpServletResponse response) {
        Result<?> result = wmImNoticeHService.doPrintBqList(id);
        return result;
    }

    /**
     * 货品id
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/doPrintId")
    @ApiOperation(value = "货品id")
    public void doPrintId(@RequestParam String id, HttpServletResponse response) {
        wmImNoticeHService.doPrintId(id, response);
    }

    /**
     * 完成
     *
     * @return
     */
    @ApiOperation(value = "完成", notes = "完成")
    @PostMapping(value = "/complete")
    public Result<?> complete(@RequestParam String id) {
        wmImNoticeHService.complete(id);
        return Result.ok("操作成功");
    }

    /**
     * 入库通知单下拉列标配
     *
     * @return
     */
    @GetMapping("/getNoticeHSelectList")
    @ApiOperation(value = "入库通知单下拉列表")
    public Result getNoticeHSelectList() {
        QueryWrapper<WmImNoticeI> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bin_pre", "N")
                .select("distinct im_notice_id").apply("cast(goods_count as decimal) > cast(goods_qm_count as decimal )");
        return Result.ok(wmImNoticeIService.list(queryWrapper));
    }

    /**
     * 供应商下拉
     *
     * @return
     */
    @GetMapping("/getCusSelectList")
    @ApiOperation(value = "入库供应商下拉列表查询")
    public Result getCusSelectList() {
        QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("im_sta", "已完成")
                .select("distinct cus_code ,cus_name");
        return Result.ok(wmImNoticeHService.list(queryWrapper));
    }


    /**
     * 分页列表查询
     *
     * @param wmImNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "其他入库-分页列表查询", notes = "其他入库-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WmImNoticeH wmImNoticeH,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<WmImNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmImNoticeH, req.getParameterMap());
        Page<WmImNoticeH> page = new Page<WmImNoticeH>(pageNo, pageSize);
        IPage<WmImNoticeH> pageList = wmImNoticeHService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param wmImNoticeHPage
     * @return
     */
    @AutoLog(value = "wm_im_notice_h-添加")
    @ApiOperation(value = "wm_im_notice_h-添加", notes = "wm_im_notice_h-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmImNoticeHPage wmImNoticeHPage) {
        List<WmImNoticeH> imNoticeHS = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getImBeizhu, wmImNoticeHPage.getImBeizhu()).list();
        if (CollectionUtil.isNotEmpty(imNoticeHS)){
            for (WmImNoticeH imNoticeH : imNoticeHS) {
                if (!"07".equals(imNoticeH.getOrderType()) && !"09".equals(imNoticeH.getOrderType())){
                    if (imNoticeH.getOrderType().equals(wmImNoticeHPage.getOrderType())){
                        throw new JeecgBootException("主PO号已存在，请重新输入");
                    }
                }
            }
        }
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        BeanUtils.copyProperties(wmImNoticeHPage, wmImNoticeH);
        wmImNoticeHService.saveMain(wmImNoticeH, wmImNoticeHPage.getWmImNoticeIList());
        return Result.OK("添加成功");
    }

    /**
     * 添加双维
     *
     * @param wmImNoticeHPage
     * @return
     */
    @AutoLog(value = "双维添加")
    @ApiOperation(value = "wm_im_notice_h-添加", notes = "wm_im_notice_h-添加")
    @PostMapping(value = "/addNoticeH")
    public Result<?> addNoticeH(@RequestBody WmImNoticeHPage wmImNoticeHPage) {
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        BeanUtils.copyProperties(wmImNoticeHPage, wmImNoticeH);
        String noticeId = wmImNoticeHService.saveMain1(wmImNoticeH, wmImNoticeHPage.getWmImNoticeIList());
        return Result.OK(noticeId);
    }

    /**
     * pda收货完成双维
     *
     * @param noticeId
     * @return
     */
    @AutoLog(value = "pc采购入库完成")
    @ApiOperation(value = "pc采购入库完成", notes = "pc采购入库完成")
    @GetMapping(value = "/updateImNoticeById")
    public Result<?> updateImNoticeById(@RequestParam(name = "noticeId") String noticeId) {
        WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, noticeId).one();
        if (wmImNoticeH != null) {
            Map map1 = new LinkedHashMap();
            map1.put("noticeId", noticeId);
            map1.put("imSta", "已完成");

            wmImNoticeH.setImSta("已完成");
            List<WmInQmI> list = wmInQmIService.lambdaQuery()
                    .eq(WmInQmI::getImNoticeId, wmImNoticeH.getNoticeId())
                    .eq(WmInQmI::getBinSta,"N")
                    .list();
            if (CollectionUtil.isNotEmpty(list)) {
                    for (WmInQmI wmInQmI : list) {
                        wmInQmI.setBinSta("Y");
                        wmInQmIService.updateById(wmInQmI);
                    }
                throw new JeecgBootException("本单还有收货未上架的商品，不能完成此操作");
            }else {
                wmImNoticeHService.updateById(wmImNoticeH);
//                JSONObject jsonObj = new JSONObject(map1);
//                String Json = jsonObj.toString();
//            String post = HttpUtil.post(""https://retail.danbagui.com/api/storage/sync/record/callback", Json);
//                String post = HttpUtil.post("https://retail.danbagui.com/api/storage/sync/record/callback", Json);
//                JSONObject parse = (JSONObject) JSONObject.parse(post);
//                String code = parse.get("code") != null ? parse.get("code").toString() : "";
//                if (org.apache.commons.lang3.StringUtils.isNotEmpty(code) && code.equals("SUCCESS")) { }
            }

        }
        return Result.ok("操作成功");
    }

    /**
     * 强制入库
     *
     * @param wmImNoticeHPage
     * @return
     */
    @AutoLog(value = "wm_im_notice_h-添加")
    @ApiOperation(value = "wm_im_notice_h-添加", notes = "wm_im_notice_h-添加")
    @PostMapping(value = "/forceAdd")
    public Result<?> forceAdd(@RequestBody WmImNoticeHPage wmImNoticeHPage) {
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        BeanUtils.copyProperties(wmImNoticeHPage, wmImNoticeH);
        wmImNoticeHService.saveMainForce(wmImNoticeH, wmImNoticeHPage.getWmImNoticeIList());
        return Result.ok("添加成功！");
    }

    /**
     * 加急
     *
     * @param id
     * @return
     */

   /* @ApiOperation(value = "加急", notes = "加急")
    @PostMapping(value = "/urgent")
    public Result<?> add(@RequestParam String id) {
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        wmImNoticeH.setId(id);
        wmImNoticeH.setUrgent("Y");
        wmImNoticeHService.updateById(wmImNoticeH);
        return Result.ok("操作成功");
    }*/

    @ApiOperation(value = "更改加急状态", notes = "加急")
    @PostMapping(value = "/urgent")
    public Result<?> add(@RequestParam String id,@RequestParam String urgent) {
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        wmImNoticeH.setId(id);
        wmImNoticeH.setUrgent(urgent);
        wmImNoticeHService.updateById(wmImNoticeH);
        return Result.ok("操作成功");
    }


    /**
     * 编辑
     *
     * @param wmImNoticeHPage
     * @return
     */
    @AutoLog(value = "wm_im_notice_h-编辑")
    @ApiOperation(value = "wm_im_notice_h-编辑", notes = "wm_im_notice_h-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmImNoticeHPage wmImNoticeHPage) throws Exception{
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        BeanUtils.copyProperties(wmImNoticeHPage, wmImNoticeH);
        WmImNoticeH wmImNoticeHEntity = wmImNoticeHService.getById(wmImNoticeH.getId());
        if (wmImNoticeHEntity == null) {
            return Result.error("未找到对应数据");
        }
        wmImNoticeHService.updateMain(wmImNoticeH, wmImNoticeHPage.getWmImNoticeIList());
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "wm_im_notice_h-通过id删除")
    @ApiOperation(value = "wm_im_notice_h-通过id删除", notes = "wm_im_notice_h-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wmImNoticeHService.delMains(id);
        return Result.ok("删除成功!");
    }

    /**
     * 通过id删除双维
     *
     * @param noticeId
     * @return
     */
    @AutoLog(value = "双维删除")
    @ApiOperation(value = "wm_im_notice_h-通过noticeId删除", notes = "wm_im_notice_h-通过noticeId删除")
    @DeleteMapping(value = "/deleteBynoticeId")
    public Result<?> deleteBynoticeId(@RequestParam(name = "noticeId", required = true) String noticeId) {
        List<WmImNoticeH> wmImNoticeHS = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, noticeId).list();
        for (WmImNoticeH wmImNoticeH : wmImNoticeHS) {
            List<WmInQmI> list = wmInQmIService.lambdaQuery().eq(WmInQmI::getImNoticeId, wmImNoticeH.getNoticeId()).list();
            if (CollectionUtil.isNotEmpty(list)) {
                for (WmInQmI wmInQmI : list) {
                    wmInQmIService.removeById(wmInQmI.getId());
                }
            }
            wmImNoticeHService.delMain(wmImNoticeH.getId());
        }
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "wm_im_notice_h-批量删除")
    @ApiOperation(value = "wm_im_notice_h-批量删除", notes = "wm_im_notice_h-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wmImNoticeHService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "wm_im_notice_h-通过id查询", notes = "wm_im_notice_h-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WmImNoticeH wmImNoticeH = wmImNoticeHService.getById(id);
        if (wmImNoticeH == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmImNoticeH);

    }


 /*   *//**
     * 通过id查询
     *
     * @param id
     * @return
     *//*
    @ApiOperation(value = "通过", notes = "商品-通主表ID查询")
    @GetMapping(value = "/queryWmImNoticeIByMainId")
    public Result<?> queryWmImNoticeIListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.selectByMainId(id);
        for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
            List<WmToUpGoods> list = wmToUpGoodsService.lambdaQuery().eq(WmToUpGoods::getGoodsId, wmImNoticeI.getGoodsCode())
                    .orderByDesc(WmToUpGoods::getCreateTime).list();
            WmToUpGoods wmToUpGoods = new WmToUpGoods();
            if (CollectionUtil.isNotEmpty(list)) {
                wmToUpGoods = list.get(0);
            }
            wmImNoticeI.setBinPlan(wmToUpGoods.getKuWeiBianMa());
        }
        List<WmImNoticeI> collect = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(wmImNoticeIList)){
             collect = wmImNoticeIList.stream().sorted(Comparator.comparing(WmImNoticeI::getContractlno))
                    .collect(Collectors.toList());
        }
        return Result.ok(collect);
    }
*/

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "通过", notes = "商品-通主表ID查询")
    @GetMapping(value = "/queryWmImNoticeIByMainId")
    public Result<?> queryWmImNoticeIListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.selectByMainId(id);
        for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
            List<WmToUpGoods> list = wmToUpGoodsService.lambdaQuery().eq(WmToUpGoods::getGoodsId, wmImNoticeI.getGoodsCode())
                    .orderByDesc(WmToUpGoods::getCreateTime).list();
            WmToUpGoods wmToUpGoods = new WmToUpGoods();
            if (CollectionUtil.isNotEmpty(list)) {
                wmToUpGoods = list.get(0);
            }
            wmImNoticeI.setBinPlan(wmToUpGoods.getKuWeiBianMa());
        }
        List<WmImNoticeI> collect = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(wmImNoticeIList)){
            collect = wmImNoticeIList.stream().sorted(Comparator.comparing(WmImNoticeI::getContractlno,Comparator.nullsFirst(String::compareTo))) .collect(Collectors.toList());
        }
        return Result.ok(collect);
    }


    /**
     * 导出excel
     *
     * @param request
     * @param wmImNoticeH
     */
    @RequestMapping(value = "/exportXls2")
    public ModelAndView exportXls2(HttpServletRequest request, WmImNoticeH wmImNoticeH) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WmImNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmImNoticeH, request.getParameterMap());

        //Step.2 获取导出数据
        List<WmImNoticeH> queryList = wmImNoticeHService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WmImNoticeH> wmImNoticeHList = new ArrayList<WmImNoticeH>();
        if (StringUtils.isEmpty(selections)) {
            wmImNoticeHList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wmImNoticeHList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<WmImNoticeHPage> pageList = new ArrayList<WmImNoticeHPage>();
      /*  for (WmImNoticeH main : wmImNoticeHList) {
            WmImNoticeHPage vo = new WmImNoticeHPage();
            BeanUtils.copyProperties(main, vo);
            List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.selectByMainId(main.getNoticeId());
            vo.setWmImNoticeIList(wmImNoticeIList);
            pageList.add(vo);
        }*/

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "wm_im_notice_h列表");
        mv.addObject(NormalExcelConstants.CLASS, WmImNoticeHPage.class);
        //mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("wm_im_notice_h数据", "导出人:"+sysUser.getRealname(), "wm_im_notice_h"));
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("采购入库", "导出时间:" + DateUtils.getDataString(DateUtils.yyyymmddhhmmss.get()), "采购入库"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }




    /**
     * 导出excel
     *
     * @param request
     * @param wmImNoticeH
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmImNoticeH wmImNoticeH) {

        // Step.1 组装查询条件查询数据
        QueryWrapper<WmImNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmImNoticeH, request.getParameterMap());

        //Step.2 获取导出数据
        List<WmImNoticeH> queryList = wmImNoticeHService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WmImNoticeH> wmImNoticeHList = new ArrayList<WmImNoticeH>();
        if (StringUtils.isEmpty(selections)) {
            wmImNoticeHList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wmImNoticeHList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<WmImNoticeHPages> pageList = new ArrayList<WmImNoticeHPages>();
        for (WmImNoticeH main : wmImNoticeHList) {
            List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.selectByMainId(main.getNoticeId());
            for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
                WmImNoticeHPages vo = new WmImNoticeHPages();
                vo.setCusCode(main.getCusCode());
                vo.setCusName(main.getCusName());
                vo.setImData(main.getImData());
                if(StringUtil.isBlank(main.getOrderTypes())){
                    vo.setOrderType(main.getOrderType());
                }else{
                    vo.setOrderType(main.getOrderTypes());
                }
                vo.setImBeizhu(main.getImBeizhu());
                vo.setNoticeId(main.getNoticeId());
                vo.setSupCode(main.getSupCode());
                vo.setU8ReturnCode(main.getU8ReturnCode());
                vo.setAstreanum(main.getAstreanum());




                vo.setPurchasename(main.getPurchasename());
                vo.setGoodsCode(wmImNoticeI.getGoodsCode());
                vo.setGoodsCount(wmImNoticeI.getGoodsCount());
                vo.setGoodsBatch(wmImNoticeI.getGoodsBatch());
                vo.setGoodsUnit(wmImNoticeI.getGoodsUnit());
                vo.setGoodsQmCount(wmImNoticeI.getGoodsQmCount());
                vo.setGoodsName(wmImNoticeI.getGoodsName());
                vo.setImBeizhui(wmImNoticeI.getImBeizhu());
                vo.setUnitPrice(wmImNoticeI.getUnitPrice());
                vo.setContractlno(wmImNoticeI.getContractlno());
                vo.setTotalamountvat(wmImNoticeI.getTotalamountvat());
                vo.setChukudate(wmImNoticeI.getChukudate());
                vo.setYwMingCheng(wmImNoticeI.getYwMingCheng());
                pageList.add(vo);
            }
        }
        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "列表");
        mv.addObject(NormalExcelConstants.CLASS, WmImNoticeHPages.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("采购入库", "导出时间:" + DateUtils.getDataString(DateUtils.yyyymmddhhmmss.get()), "采购入库"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wmImNoticeH
     */
    @RequestMapping(value = "/exportXlsTwo")
    public ModelAndView exportXlsTwo(HttpServletRequest request, WmImNoticeH wmImNoticeH) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WmImNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmImNoticeH, request.getParameterMap());
        //Step.2 获取导出数据
        List<WmImNoticeH> queryList = wmImNoticeHService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WmImNoticeH> wmImNoticeHList = new ArrayList<WmImNoticeH>();
        if (oConvertUtils.isEmpty(selections)) {
            wmImNoticeHList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wmImNoticeHList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<WmImNoticeHTwoPage> pageList = new ArrayList<WmImNoticeHTwoPage>();
        for (WmImNoticeH main : wmImNoticeHList) {
            WmImNoticeHTwoPage vo = new WmImNoticeHTwoPage();
            BeanUtils.copyProperties(main, vo);
            List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.selectByMainId(main.getNoticeId());
            vo.setWmImNoticeIList(wmImNoticeIList);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "wm_im_notice_h列表");
        mv.addObject(NormalExcelConstants.CLASS, WmImNoticeHTwoPage.class);
        //mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("wm_im_notice_h数据", "导出人:"+sysUser.getRealname(), "wm_im_notice_h"));
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("预约数据", "导出时间:" + DateUtils.getDataString(DateUtils.yyyymmddhhmmss.get()), "预约"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    synchronized
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
                List<WmImNoticeHPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WmImNoticeHPage.class, params);
                for (WmImNoticeHPage page : list) {
                    WmImNoticeH po = new WmImNoticeH();
                    BeanUtils.copyProperties(page, po);
                    List<WmImNoticeI> wmImNoticeIList = page.getWmImNoticeIList();
                    wmImNoticeHService.saveMain(po, page.getWmImNoticeIList());
                }
                return Result.ok("文件导入成功！数据行数:" + list.size());
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
        return Result.ok("文件导入失败！");
    }

    @AutoLog(value = "U8生产订单")
    @ApiOperation(value = "U8生产订单", notes = "U8生产订单")
    @GetMapping(value = "/getU8Scdd")
    public Result<?> getU8Scdd(@RequestParam(name = "date", required = false) String date) throws Exception {
        u8JiekouXxlJob.getU8Shengchandingdan(date);
        return Result.ok("同步成功！");
    }

    @AutoLog(value = "U8采购订单")
    @ApiOperation(value = "U8采购订单", notes = "U8采购订单")
    @GetMapping(value = "/getU8Cgdd")
    public Result<?> getU8Cgdd(@RequestParam(name = "date", required = false) String date) throws Exception {
        u8JiekouXxlJob.getU8Caigoudingdan(date);
        return Result.ok("同步成功！");
    }

    @AutoLog(value = "U8未审核采购到货单")
    @ApiOperation(value = "U8未审核采购到货单", notes = "U8未审核采购到货单")
    @GetMapping(value = "/getU8Dhd")
    public Result<?> getU8Dhd(@RequestParam(name = "date", required = false) String date) throws Exception {
        u8JiekouXxlJob.getU8Caigoudaohuo(date);
        return Result.ok("同步成功！");
    }

    /**
     * 生成销售单
     *
     * @return
     */
    /*@AutoLog(value = "生成销售单")
    @ApiOperation(value = "生成销售单", notes = "生成销售单")
    @GetMapping(value = "/createSalesNo")
    public Result<?> createSalesNo(@RequestParam("ids") String ids) {
        String[] split = ids.split(",");
        List<String> list1 = Arrays.asList(split);
        //生成销售单
        WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
        wmOmNoticeH.setOrderTypes("销售出库");
        //订单类型
        wmOmNoticeH.setOrderTypeCode("12");
        List<WmOmNoticeI> wmOmNoticeIList = new ArrayList<>();
        for (String id : list1) {
            WmImNoticeH wmImNoticeH = wmImNoticeHService.getById(id);
            String u8ReturnCode = wmImNoticeH.getU8ReturnCode();
            //查询生产入库单
            List<WmImNoticeH> wmImNoticeHS = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getU8ReturnCode, u8ReturnCode).eq(WmImNoticeH::getOrderTypeCode, "07").list();

            List<WmImNoticeI> wmImNoticeIS = wmImNoticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId, wmImNoticeH.getNoticeId()).list();
            for (WmImNoticeI wmImNoticeI : wmImNoticeIS) {
                WmOmNoticeI wmOmNoticeI = new WmOmNoticeI();
                //批次
                wmOmNoticeI.setGoodsBatch(wmImNoticeI.getGoodsBatch());
                //商品编码
                wmOmNoticeI.setGoodsId(wmImNoticeI.getGoodsCode());
                //出货数量
                wmOmNoticeI.setGoodsQua(wmImNoticeI.getGoodsCount());
                //商品名称
                wmOmNoticeI.setGoodsName(wmImNoticeI.getGoodsName());
                //出货单位
                wmOmNoticeI.setGoodsUnit(wmImNoticeI.getGoodsUnit());
                //备注
                wmOmNoticeI.setOmBeiZhu(wmImNoticeI.getImBeizhu());
                //物料英文名称
                wmOmNoticeI.setYwMingCheng(wmImNoticeI.getYwMingCheng());
                //到货通知单号
                wmOmNoticeI.setQbno(wmImNoticeI.getImNoticeId());
                //规格
                wmOmNoticeI.setGoodsguige(wmImNoticeI.getShpGuiGe());
                //货主
                wmOmNoticeI.setCusCode(wmImNoticeH.getCusCode());
                wmOmNoticeI.setCusName(wmImNoticeH.getCusName());
                //客户
                wmOmNoticeI.setImclientcode(wmImNoticeH.getImclientcode());
                //销售发票号
                wmOmNoticeI.setU8ReturnCode(wmImNoticeH.getU8ReturnCode());
                //单价
                wmOmNoticeI.setGoodsUnitPrice(new BigDecimal(wmImNoticeI.getAvgunitprice()));
                //产品属性设置
                List<BaGoodsType> list = baGoodsTypeService.lambdaQuery()
                        .eq(BaGoodsType::getGoodsTypeCode, wmImNoticeI.getChpShuXing()).list();
                if (CollectionUtil.isNotEmpty(list)) {
                    wmOmNoticeI.setGoodsTypeEnname(list.get(0).getGoodsTypeEnname());
                    wmOmNoticeI.setGoodsTypeCode(list.get(0).getGoodsTypeCode());
                    wmOmNoticeI.setGoodsTypeName(list.get(0).getGoodsTypeName());
                }
                //颜色
                List<MdGoods> lists = goodsService.lambdaQuery()
                        .eq(MdGoods::getShpBianMa, wmImNoticeI.getGoodsCode()).list();
                if (CollectionUtil.isNotEmpty(lists)) {
                    wmOmNoticeI.setShpYanEnse(lists.get(0).getShpYanEnse());
                    wmOmNoticeI.setGoodsjianchen(lists.get(0).getShpJianCheng());
                }
                wmOmNoticeIList.add(wmOmNoticeI);
            }
        }
        noticeHService.saveMain(wmOmNoticeH, wmOmNoticeIList);
        return Result.ok("同步成功");
    }*/
    @AutoLog(value = "plqn采购合同打印")
    @ApiOperation(value = "采购合同打印", notes = "采购合同打印")
    @GetMapping(value = "/doPrintckd")
    public void doPrintckd(@RequestParam(name = "id", required = true) String id, String language,HttpServletResponse response) {
        wmImNoticeHService.doPrintckd(id, language,response);
    }

    @AutoLog(value = "plqn标签打印")
    @ApiOperation(value = "标签打印", notes = "标签打印")
    @GetMapping(value = "/labelPrinting")
    public Result<?> labelPrinting(@RequestParam(name = "ids", required = true) String ids, HttpServletResponse response) throws InterruptedException {
        String[] split = ids.split(",");
        for (String id : split) {
            WmInQmI wmInQmI = wmInQmIService.getById(id);
            WmImNoticeI imNoticeI = wmImNoticeIService.getById(wmInQmI.getImNoticeItem());
            WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, imNoticeI.getImNoticeId()).one();
            List<BaUnit> baUnits = baUnitService.lambdaQuery().eq(BaUnit::getUnitZhName, imNoticeI.getGoodsUnit()).list();
            BaUnit baUnit = new BaUnit();
            if (CollectionUtil.isNotEmpty(baUnits)){
                baUnit = baUnits.get(0);
            }
            Map map1 = new LinkedHashMap();
            String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
            //客户
            map1.put("data01", wmImNoticeH.getCusName());
            map1.put("data02", wmImNoticeH.getU8ReturnCode());
            map1.put("type", "ruku");
            Map map2 = new LinkedHashMap();
            List<Map> mapList = new ArrayList<>();
            //箱号
            String tinid = String.valueOf(System.currentTimeMillis());
            Thread.sleep(3);
            map2.put("data01", imNoticeI.getGoodsCode());
            map2.put("data02", wmImNoticeH.getCusName());
            map2.put("data03", imNoticeI.getGoodsName());
            if (baUnit != null) {
                map2.put("data04", imNoticeI.getShenqingsl()+" " + baUnit.getUnitEnName());
            }else {
                map2.put("data04", imNoticeI.getShenqingsl());
            }
            map2.put("data05", imNoticeI.getContractlno());
            map2.put("data06", DateUtil.format(imNoticeI.getCreateTime(),"yyyy-MM-dd"));
            map2.put("data07", tinid);
            mapList.add(map2);
            map1.put("printitem", mapList);
            JSONObject jsonObj = new JSONObject(map1);
            String Json = jsonObj.toString();
            String post = HttpUtil.post(pritIp+"util/uwms/client/print/ruku/admin", Json);
            String post1 = HttpUtil.post(pritIp+"util/uwms/client/print/ruku/admin", Json);
            Thread.sleep(2);
        }
        return Result.OK("打印成功");
    }



    @AutoLog(value = "plqn标签打印")
    @ApiOperation(value = "标签打印", notes = "标签打印")
    @GetMapping(value = "/labelPrinting2")
    public Result<?> labelPrinting2(@RequestParam(name = "ids", required = true) String ids, HttpServletResponse response) throws InterruptedException {
        String[] split = ids.split(",");
        for (String id : split) {
            WmToUpGoods wmToUpGoods = wmToUpGoodsService.getById(id);
            List<BaUnit> baUnits = baUnitService.lambdaQuery().eq(BaUnit::getUnitZhName, wmToUpGoods.getGoodsUnit()).list();
            BaUnit baUnit = new BaUnit();
            if (CollectionUtil.isNotEmpty(baUnits)){
                baUnit = baUnits.get(0);
            }
            Map map1 = new LinkedHashMap();
            //客户
            map1.put("data01", wmToUpGoods.getCusName());
            map1.put("type","ruku");
            Map map2 = new LinkedHashMap();
            List<Map> mapList = new ArrayList<>();
            //箱号
            Thread.sleep(3);
            map2.put("data01", wmToUpGoods.getGoodsId());
            map2.put("data02", wmToUpGoods.getCusName());
            map2.put("data03", wmToUpGoods.getGoodsName());
            if (baUnit != null) {
                map2.put("data04", wmToUpGoods.getGoodsQua()+" " + baUnit.getUnitEnName());
            }else {
                map2.put("data04", wmToUpGoods.getGoodsQua());
            }
            WmInQmI wmInQmI = new WmInQmI();
            WmImNoticeI imNoticeI = new WmImNoticeI();
            if (StringUtils.isNotEmpty(wmToUpGoods.getPareId())){
                if (baUnit != null) {
                    map2.put("data04", wmToUpGoods.getBaseGoodscount()+" " + baUnit.getUnitEnName());
                }else {
                    map2.put("data04", wmToUpGoods.getBaseGoodscount());
                }
            }
            wmInQmI = wmInQmIService.getById(wmToUpGoods.getOrderIdI());
            imNoticeI = wmImNoticeIService.getById(wmInQmI.getImNoticeItem());
            map2.put("data05", imNoticeI.getContractlno());
            map2.put("data06", DateUtil.format(wmToUpGoods.getCreateTime(),"yyyy-MM-dd"));
            map2.put("data07", wmToUpGoods.getBinId());
            mapList.add(map2);
            map1.put("printitem", mapList);
            JSONObject jsonObj = new JSONObject(map1);
            String Json = jsonObj.toString();
            String post = HttpUtil.post(pritIp+"util/uwms/client/print/ruku/admin", Json);
            Thread.sleep(2);
        }
        return Result.OK("打印成功");
    }



    @AutoLog(value = "plqn箱麦打印")
    @ApiOperation(value = "plqn箱麦打印", notes = "plqn箱麦打印")
    @GetMapping(value = "/labelPrints")
    public Result<?> labelPrints(@RequestParam(name = "ids", required = true) String ids, HttpServletResponse response) throws InterruptedException {
        String[] split = ids.split(",");
        for (String id : split) {
            WmInQmI byId = wmInQmIService.getById(id);
            WmImNoticeI imNoticeI = new WmImNoticeI();
            if (StringUtils.isNotEmpty(byId.getImNoticeItem())){
                imNoticeI = wmImNoticeIService.getById(byId.getImNoticeItem());
            }
            WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, imNoticeI.getImNoticeId()).one();

            List<BaUnit> baUnits = baUnitService.lambdaQuery().eq(BaUnit::getUnitZhName, imNoticeI.getGoodsUnit()).list();
            BaUnit baUnit = new BaUnit();
            if (CollectionUtil.isNotEmpty(baUnits)){
                baUnit = baUnits.get(0);
            }

            Map map1 = new LinkedHashMap();
            //客户
            map1.put("data01", wmImNoticeH.getCusName());
            map1.put("data02", wmImNoticeH.getU8ReturnCode());
            map1.put("type", "xiangmai");
            Map map2 = new LinkedHashMap();
            List<Map> mapList = new ArrayList<>();
            map2.put("data01", imNoticeI.getGoodsCode());
            map2.put("data02", imNoticeI.getGoodsName());
            if (baUnit != null) {
                map2.put("data03", imNoticeI.getShenqingsl()+" " + baUnit.getUnitEnName());
            }else {
                map2.put("data03", imNoticeI.getShenqingsl());
            }
            map2.put("data04", imNoticeI.getContractlno());
            mapList.add(map2);
            map1.put("printitem", mapList);
            JSONObject jsonObj = new JSONObject(map1);
            String Json = jsonObj.toString();
//            String post = HttpUtil.post(pritIp2+"util/uwms/client/print/xiangmai/admin", Json);
            String post1 = HttpUtil.post(pritIp2+"util/uwms/client/print/ruku/admin", Json);
            String post = HttpUtil.post(pritIp2+"util/uwms/client/print/ruku/admin", Json);
//            String post1 = HttpUtil.post(pritIp2+"util/uwms/client/print/xiangmai/admin", Json);
//        Thread.sleep(3);
            Thread.sleep(3);
        }
        return Result.OK("打印成功");

    }


    @AutoLog(value = "plqn箱麦打印")
    @ApiOperation(value = "plqn箱麦打印", notes = "plqn箱麦打印")
    @GetMapping(value = "/labelPrints1")
    public Result<?> labelPrints1(@RequestParam(name = "id", required = true) String id,
                                  @RequestParam(name = "query15", required = true) String query15,HttpServletResponse response) throws InterruptedException {
        WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(id);
        wmImNoticeI.setShenqingsl(query15);
        //待入库数量
        String query13 = NumberUtil.sub(wmImNoticeI.getGoodsCount(), wmImNoticeI.getGoodsQmCount()).toString();
        if(new BigDecimal(query15).compareTo(new BigDecimal(query13)) == 1){
            throw new JeecgBootException("本次入库数量小于箱数");
        }
        //取余数
        BigDecimal bigDecimal = new BigDecimal(query13).divideAndRemainder(new BigDecimal(query15))[1];
        //入库数量
        BigDecimal sub = NumberUtil.sub(query13, bigDecimal.toString());
        //箱数
        String div = NumberUtil.div(sub.toString(), query15).toString();
        wmImNoticeIService.updateById(wmImNoticeI);
//        for (int i = 0; i < divs; i++) {}
            try{
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        //打印箱唛
                        try{
                            smsSend.labelPrints(id,null,div,"0");
                        }catch (Exception exm){
                            exm.printStackTrace();
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        return Result.OK("打印成功");

    }



    @AutoLog(value = "plqn箱麦打印")
    @ApiOperation(value = "plqn箱麦打印", notes = "plqn箱麦打印")
    @PostMapping(value = "/labelPrints3")
    public Result<?> labelPrints3(@RequestParam(name = "id", required = true) String id,
                                  @RequestParam(name = "type", required = true) String type,
                                  @RequestParam(name = "pageType", required = true) String pageType,
                                  HttpServletResponse response) throws InterruptedException {
        List<HashMap<String,String>> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String[] ids = id.split(",");
            for (String s : ids) {
                HashMap<String,String> map1 = new HashMap<>(1024);
                if("xiangmai".equals(type) || "rk".equals(type)){
                    if("DHSH".equals(pageType)){
                        WmImNoticeI   wmImNoticeI = wmImNoticeIService.getById(s);
                        if(StringUtil.isNotEmpty(wmImNoticeI.getGoodsUnit())){
                            List<BaUnit> baUnit = baUnitService.lambdaQuery().eq(BaUnit::getUnitCode, wmImNoticeI.getGoodsUnit()).list();
                            map1.put("unitenname",baUnit.get(0).getUnitEnName());
                        }
                        map1.put("id",s);
                        map1.put("contractlno",wmImNoticeI.getContractlno());
                    }else if("SJ".equals(pageType)){
                        WmInQmI   wmInQmI = wmInQmIService.getById(s);
                        WmImNoticeI   wmImNoticeI = wmImNoticeIService.getById(wmInQmI.getImNoticeItem());
                        if(StringUtil.isNotEmpty(wmImNoticeI.getGoodsUnit())){
                            List<BaUnit> baUnit = baUnitService.lambdaQuery().eq(BaUnit::getUnitCode, wmImNoticeI.getGoodsUnit()).list();
                            map1.put("unitenname",baUnit.get(0).getUnitEnName());
                        }
                        map1.put("id",s);
                        map1.put("contractlno",wmInQmI.getGoodsBatch());
                        map1.put("createTime",sdf.format(wmImNoticeI.getCreateTime()));
                    }else if("SJTZ".equals(pageType) || "FX".equals(pageType)){
                        String[] split = s.split("/");
                        WmToUpGoods wmToUpGoods = wmToUpGoodsService.getById(split[0]);
                        //WmInQmI wmInQmI = new WmInQmI();
                        String goodsBatc = "";
                        if(wmToUpGoods==null){
                            WmToDownGoods wmToDownGoods = wmToDownGoodsService.getById(split[0]);
                            goodsBatc = wmToDownGoods.getGoodsBatch();
                            //wmInQmI = wmInQmIService.getById(wmToDownGoods.getOrderIdI());
                            map1.put("createTime",sdf.format(wmToDownGoods.getCreateTime()));
                        }else {
                            //wmInQmI = wmInQmIService.getById(wmToUpGoods.getOrderIdI());
                            goodsBatc = wmToUpGoods.getGoodsBatch();
                            map1.put("createTime",sdf.format(wmToUpGoods.getCreateTime()));
                        }

                        //WmImNoticeI   wmImNoticeI = wmImNoticeIService.getById(wmInQmI.getImNoticeItem());
                       /* if(StringUtil.isNotEmpty(wmImNoticeI.getGoodsUnit())){
                            List<BaUnit> baUnit = baUnitService.lambdaQuery().eq(BaUnit::getUnitCode, wmImNoticeI.getGoodsUnit()).list();
                            map1.put("unitenname",baUnit.get(0).getUnitEnName());
                        }*/

                        map1.put("id",s);
                        map1.put("contractlno",goodsBatc);

                    };
                }
                list.add(map1);
            }

        return Result.OK(list);
    }








    @AutoLog(value = "plqn箱麦打印")
    @ApiOperation(value = "plqn箱麦打印", notes = "plqn箱麦打印")
    @GetMapping(value = "/labelPrints2")
    public Result<?> labelPrints2(@RequestParam(name = "ids", required = true) String ids, HttpServletResponse response) throws InterruptedException {
        String[] split = ids.split(",");
        for (String id : split) {
            WmToUpGoods wmToUpGoods = wmToUpGoodsService.getById(id);
            WmInQmI wmInQmI = new WmInQmI();

            wmInQmI = wmInQmIService.getById(wmToUpGoods.getOrderIdI());
            WmImNoticeI imNoticeI = wmImNoticeIService.getById(wmInQmI.getImNoticeItem());
            WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, imNoticeI.getImNoticeId()).one();

            List<BaUnit> baUnits = baUnitService.lambdaQuery().eq(BaUnit::getUnitZhName, imNoticeI.getGoodsUnit()).list();
            BaUnit baUnit = new BaUnit();
            if (CollectionUtil.isNotEmpty(baUnits)){
                baUnit = baUnits.get(0);
            }
            Map map1 = new LinkedHashMap();
            //客户
            map1.put("data01", wmImNoticeH.getCusName());
            map1.put("data02", wmImNoticeH.getU8ReturnCode());
            Map map2 = new LinkedHashMap();
            List<Map> mapList = new ArrayList<>();
            map2.put("data01", imNoticeI.getGoodsCode());
            map2.put("data02", imNoticeI.getGoodsName());
            if (baUnit != null) {
                map2.put("data03", imNoticeI.getShenqingsl()+" " + baUnit.getUnitEnName());
            }else {
                map2.put("data03", imNoticeI.getShenqingsl());
            }
            if (StringUtils.isNotEmpty(wmToUpGoods.getPareId())){
                if (baUnit != null) {
                    map2.put("data03", wmToUpGoods.getBaseGoodscount()+" " + baUnit.getUnitEnName());
                }else {
                    map2.put("data03", wmToUpGoods.getBaseGoodscount());
                }
            }
            map2.put("data04", imNoticeI.getContractlno());
            mapList.add(map2);
            map1.put("printitem", mapList);
            JSONObject jsonObj = new JSONObject(map1);
            String Json = jsonObj.toString();
            String post = HttpUtil.post(pritIp2+"util/uwms/client/print/xiangmai/admin", Json);
            Thread.sleep(3);
        }
        return Result.OK("打印成功");

    }

    /**
     * plqn通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "plqn通过id查询", notes = "plqn通过id查询")
    @GetMapping(value = "/queryplqnById")
    public Result<?> queryplqnById(@RequestParam(name = "id", required = true) String id) {
        WmImNoticeH wmImNoticeH = wmImNoticeHService.getById(id);
        List<WmImNoticeI> wmImNoticeIS = wmImNoticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId, wmImNoticeH.getNoticeId()).list();
        for (WmImNoticeI wmImNoticeI : wmImNoticeIS) {
            wmImNoticeI.setU8ReturnCode(wmImNoticeH.getU8ReturnCode());
            List<MdGoods> mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmImNoticeI.getGoodsCode()).list();
            if (CollectionUtil.isNotEmpty(mdGoods)) {
                MdGoods mdGoods1 = mdGoods.get(0);
                wmImNoticeI.setShpmiaoshu(mdGoods1.getShpMiaoShu());
                wmImNoticeI.setShpGuiGe(mdGoods1.getShpGuiGe());
                wmImNoticeI.setChpShuXing(mdGoods1.getClassification());
            }

            List<MdCus> mdCuses = cusService.lambdaQuery().eq(MdCus::getKeHuBianMa, wmImNoticeI.getCusCode()).list();
            if (CollectionUtil.isNotEmpty(mdCuses)) {
                wmImNoticeI.setCusName(mdCuses.get(0).getZhongWenQch());
                wmImNoticeI.setXingYeFenLei(mdCuses.get(0).getXingYeFenLei());
            }

            List<MdSup> mdSups = supService.lambdaQuery().eq(MdSup::getGysBianMa, wmImNoticeH.getSupCode()).list();
            if (CollectionUtil.isNotEmpty(mdSups)) {
                wmImNoticeI.setSupName(mdSups.get(0).getZhongWenQch());
                wmImNoticeI.setDiZhi(mdSups.get(0).getDiZhi());
                wmImNoticeI.setZhuLianXiRen(mdSups.get(0).getZhuLianXiRen());
                wmImNoticeI.setShouJi(mdSups.get(0).getShouJi());
            }
            wmImNoticeI.setPurchasename(wmImNoticeH.getPurchasename());
        }
        if (CollectionUtil.isEmpty(wmImNoticeIS)) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmImNoticeIS);
    }


    /**
     * 国声导出excel
     *
     * @param request
     * @param wmImNoticeH
     */
    @RequestMapping(value = "/exportXlsgs")
    public ModelAndView exportXlsgs(HttpServletRequest request, WmImNoticeH wmImNoticeH) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WmImNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmImNoticeH, request.getParameterMap());

        //Step.2 获取导出数据
        List<WmImNoticeH> queryList = wmImNoticeHService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WmImNoticeH> wmImNoticeHList = new ArrayList<WmImNoticeH>();
        if (StringUtils.isEmpty(selections)) {
            wmImNoticeHList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wmImNoticeHList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<WmImNoticeHPagegs> pageList = new ArrayList<WmImNoticeHPagegs>();
        for (WmImNoticeH main : wmImNoticeHList) {
            WmImNoticeHPagegs vo = new WmImNoticeHPagegs();
            BeanUtils.copyProperties(main, vo);
            List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.selectByMainId(main.getNoticeId());
            List<WmImNoticeIgs> noticeIgsd = new ArrayList<>();
            for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
                WmImNoticeIgs noticeIgs = new WmImNoticeIgs();
                BeanUtil.copyProperties(wmImNoticeI,noticeIgs);
                noticeIgsd.add(noticeIgs);
            }
            vo.setWmImNoticeIList(noticeIgsd);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "wm_im_notice_h列表");
        mv.addObject(NormalExcelConstants.CLASS, WmImNoticeHPagegs.class);
        //mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("wm_im_notice_h数据", "导出人:"+sysUser.getRealname(), "wm_im_notice_h"));
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("采购入库", "导出时间:" + DateUtils.getDataString(DateUtils.yyyymmddhhmmss.get()), "采购入库"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }


    /**
     * 国声通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcelgs", method = RequestMethod.POST)
    public Result<?> importExcelgs(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<WmImNoticeHPagegs> list = ExcelImportUtil.importExcel(file.getInputStream(), WmImNoticeHPagegs.class, params);
                for (WmImNoticeHPagegs page : list) {
                    WmImNoticeH po = new WmImNoticeH();
                    BeanUtils.copyProperties(page, po);
                    List<WmImNoticeIgs> wmImNoticeIList = page.getWmImNoticeIList();
                    List<WmImNoticeI> wmImNoticeILists = new ArrayList<>();
                    for (WmImNoticeIgs noticeIgs : wmImNoticeIList) {
                        WmImNoticeI noticeI = new WmImNoticeI();
                        BeanUtils.copyProperties(noticeIgs,noticeI);
                        wmImNoticeILists.add(noticeI);
                    }
                    wmImNoticeHService.saveMain(po, wmImNoticeILists);
                }
                return Result.ok("文件导入成功！数据行数:" + list.size());
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
        return Result.ok("文件导入失败！");
    }


    /**
     * @param orderids
     * @return
     * @Describe 根据采购入库通知单号查询出子表数据返回
     * @Author
     * @Create Date 2022-08-26
     */
    @AutoLog(value = "根据采购入库通知单号查询")
    @ApiOperation(value = "根据采购入库通知单号查询", notes = "根据采购入库通知单号查询")
    @GetMapping(value = "getQueryData")
    public Result<?> getQueryData(@RequestParam(name = "orderids", required = true) String orderids) {
        if(StringUtil.isEmpty(orderids)){
            throw new JeecgBootException("采购入库单号为空");
        }
        String[] split = orderids.split(",");
        List<WmImNoticeI> list = new ArrayList<>();
        for (String s : split) {
            List<WmImNoticeI> wmImNoticeIlist = wmImNoticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId,s).list();
            if(CollectionUtil.isNotEmpty(wmImNoticeIlist)){
                for (WmImNoticeI wmImNoticeI : wmImNoticeIlist) {
                    list.add(wmImNoticeI);
                }
            }
        }
        return Result.ok(list);
    }


}
