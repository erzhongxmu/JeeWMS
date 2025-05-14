package com.base.modules.jeewms.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.DateUtils;
import com.base.common.util.RedisUtil;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeeerp.entity.BusiOmTrace;
import com.base.modules.jeeerp.entity.BusiPrdOrd;
import com.base.modules.jeeerp.entity.BusiPrdOrdItem;
import com.base.modules.jeeerp.entity.ConfErp;
import com.base.modules.jeeerp.mapper.BusiPrdOrdMapper;
import com.base.modules.jeeerp.service.IBusiOmTraceService;
import com.base.modules.jeeerp.service.IConfErpService;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.entity.gs.WmOmNoticeHPagegs;
import com.base.modules.jeewms.entity.gs.WmOmNoticeIgs;
import com.base.modules.jeewms.mapper.WmOmQmIMapper;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.service.impl.AnalysisWarehousingImpl;
import com.base.modules.jeewms.vo.EditBatchWmOmNoticeHVo;
import com.base.modules.jeewms.vo.EditWmOmNoticeIListVo;
import com.base.modules.jeewms.vo.WmOmNoticeHPage;
import com.base.modules.jobhandler.U8JiekouXxlJob;
import com.base.modules.util.ConstUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

;

/**
 * @Description: 出货管理
 * @Author: base-boot
 * @Date: 2021-05-18
 * @Version: V1.0
 */
@Api(tags = "出货管理")
@RestController
@RequestMapping("/jeewms/wmOmNoticeH")
@Slf4j
public class WmOmNoticeHController extends JeecgController<WmOmNoticeH, IWmOmNoticeHService> {

    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;

    @Autowired
    private IWmOmNoticeIService wmOmNoticeIService;
    @Autowired
    private AnalysisWarehousingImpl analysisWarehousing;
    @Autowired
    private IWmOmQmIService wmOmQmIService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private U8JiekouXxlJob u8JiekouXxlJob;

    @Autowired
    private IWmImNoticeHService noticeHService;


    @Autowired
    private IWmImNoticeIService noticeIService;

    @Autowired
    private IMdGoodsService goodsService;

    @Autowired
    private IMdGoodsItemService goodsItemService;


    @Autowired
    private IBaGoodsTypeService goodsTypeService;
    @Autowired
    private IWmTuopanService tuopanService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BusiPrdOrdMapper busiPrdOrdMapper;

    @Autowired
    private IConfErpService confErpService;


    @Autowired
    private WmOmQmIMapper wmOmQmIMapper;


    @Autowired
    private IMdCusService mdCusService;
    @Autowired
    private IBusiOmTraceService iBusiOmTraceService;
    @Autowired
    private IWmToDownGoodsService wmToDownGoodsService;
    @Autowired
    private IWmTuopanService iWmTuopanService;
    /*---------------------------------主表处理-begin-------------------------------------*/

    /**
     * 分页列表查询
     *
     * @param wmOmNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "出货通知项目-分页列表查询")
    @ApiOperation(value = "出货通知项目-分页列表查询", notes = "出货通知项目-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WmOmNoticeH wmOmNoticeH,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<WmOmNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeH, req.getParameterMap());
        Page<WmOmNoticeH> page = new Page<WmOmNoticeH>(pageNo, pageSize);
        IPage<WmOmNoticeH> pageList = wmOmNoticeHService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 出库通知单下拉
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/getNoticeHSelectList")
    @ApiOperation(value = "出库通知单下拉列表")
    public Result getNoticeHSelectList() {
        QueryWrapper<WmOmQmI> qmIQueryWrapper = new QueryWrapper<>();
        qmIQueryWrapper.eq("bin_sta", "N")
                .select("distinct om_notice_id");
        return Result.ok(wmOmQmIService.list(qmIQueryWrapper));
    }

    /**
     * 出库供应商下拉
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/getCusSelectList")
    @ApiOperation(value = "出库供应商下拉")
    public Result getCusSelectList() {
        QueryWrapper<WmOmNoticeH> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("om_sta", ConstUtil.wm_sta4)
                .select("distinct cus_code,cus_name");
        return Result.ok(wmOmNoticeHService.list(queryWrapper));
    }

    /**
     * 任务接收人下拉
     *
     * @param id
     * @param response
     * @return
     */
    @PostMapping("/getJobPersonList")
    @ApiOperation(value = "出库通知单下拉列表")
    public Result getJobPersonList(@RequestBody List<String> idList) {
        return Result.ok(wmOmQmIService.getJobPersonList(idList));
    }

    /**
     * @param wmOmNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     * @Describe 出货通知项目-批量回单查询
     * @Author zly
     * @Create Date 2021/5/21
     */
    @AutoLog(value = "出货通知项目-批量回单查询")
    @ApiOperation(value = "出货通知项目-批量回单查询", notes = "出货通知项目-批量回单查询")
    @GetMapping(value = "/datagridbatchconf")
    public Result<?> datagridbatchconf(WmOmNoticeH wmOmNoticeH,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req) {
        QueryWrapper<WmOmNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeH, req.getParameterMap());
        queryWrapper.eq("om_sta", ConstUtil.wm_sta6);
        Page<WmOmNoticeH> page = new Page<WmOmNoticeH>(pageNo, pageSize);
        IPage<WmOmNoticeH> pageList = wmOmNoticeHService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * @param list
     * @return
     * @Describe 批量回单-批量回单修改
     * @Author zly
     * @Create Date 2021/5/21
     */
    @AutoLog(value = "出货通知项目-批量回单修改")
    @ApiOperation(value = "出货通知项目-批量回单修改", notes = "出货通知项目-批量回单修改")
    @PutMapping(value = "/editBatchReceipt")
    public Result<?> editBatchReceipt(@RequestBody List<EditBatchWmOmNoticeHVo> list) {
        Result<?> result = wmOmNoticeHService.editBatchReceipt(list);
        return result;
    }

    /**
     * 分页列表查询
     *
     * @param wmOmNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "客户出货和出货通知-分页列表查询")
    @ApiOperation(value = "客户出货和出货通知-分页列表查询", notes = "客户出货和出货通知-分页列表查询")
    @GetMapping(value = "/customerlist")
    public Result<?> customerList(WmOmNoticeH wmOmNoticeH,
                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        QueryWrapper<WmOmNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeH, req.getParameterMap());
//        queryWrapper.ne("om_sta", ConstUtil.wm_sta4);
//        queryWrapper.isNull("qh_sta");
        Page<WmOmNoticeH> page = new Page<WmOmNoticeH>(pageNo, pageSize);
        IPage<WmOmNoticeH> pageList = wmOmNoticeHService.page(page, queryWrapper);
        return Result.ok(pageList);
    }


    @AutoLog(value = "出货通知-拣货单和追溯单打印")
    @ApiOperation(value = "其他出货-拣货单和追溯单打印", notes = "其他出货-拣货单和追溯单打印")
    @GetMapping(value = "/doPrintpage")
    public Result<?> doPrintpage(@RequestParam(name = "id", required = true) String id) {
        Result<?> result = wmOmNoticeHService.doPrintpage(id);
        return result;
    }

    @AutoLog(value = "出货通知-打印出库单")
    @ApiOperation(value = "其他出货-打印出库单", notes = "其他出货-打印出库单")
    @GetMapping(value = "/doPrintpageckd")
    public Result<?> doPrintpageckd(@RequestParam(name = "id", required = true) String id) {
        Result<?> result = wmOmNoticeHService.doPrintpageckd(id);
        return result;
    }

    /**
     * 加急
     *
     * @param id
     * @returnf
     */

    /*@ApiOperation(value = "加急", notes = "加急")
    @PostMapping(value = "/urgent")
    public Result<?> add(@RequestParam String id) {
        WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
        wmOmNoticeH.setId(id);
        wmOmNoticeH.setUrgent("Y");
        wmOmNoticeHService.updateById(wmOmNoticeH);
        return Result.ok("操作成功");
    }*/

    /**
     * 加急
     *
     * @param id
     * @returnf
     */

    @ApiOperation(value = "加急", notes = "加急")
    @GetMapping(value = "/urgents")
    public Result<?> add(@RequestParam String id, @RequestParam String urgent) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHService.getById(id);
        wmOmNoticeH.setUrgent(urgent);
        wmOmNoticeHService.updateById(wmOmNoticeH);
        return Result.ok("操作成功");
    }

    /*--------------------------------其他出货-出货通知项目-begin----------------------------------------------*/

    /**
     * 分页列表查询
     *
     * @param wmOmNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "其他出货-分页列表查询")
    @ApiOperation(value = "其他出货-分页列表查询", notes = "其他出货-分页列表查询")
    @GetMapping(value = "/otherlist")
    public Result<?> otherlist(WmOmNoticeH wmOmNoticeH,
                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                               HttpServletRequest req) {
        QueryWrapper<WmOmNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeH, req.getParameterMap());
        queryWrapper.like("om_notice_id", "QT%");
        Page<WmOmNoticeH> page = new Page<WmOmNoticeH>(pageNo, pageSize);
        IPage<WmOmNoticeH> pageList = wmOmNoticeHService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    @AutoLog(value = "其他出货-导出通知单")
    @ApiOperation(value = "其他出货-导出通知单", notes = "其他出货-导出通知单")
    @GetMapping(value = "/downReceiveExcel")
    public void downReceiveExcel(@RequestParam(name = "id", required = true) String id, HttpServletResponse response) {
        wmOmNoticeHService.downReceiveExcel(id, response);
    }


    @AutoLog(value = "其他出货-导出装箱单-出库单-导出出库单")
    @ApiOperation(value = "其他出货-导出装箱单-出库单-导出出库单", notes = "其他出货-导出装箱单-出库单-导出出库单")
    @GetMapping(value = "/doPrintckd")
    public void doPrintckd(@RequestParam(name = "id", required = true) String id, HttpServletResponse response) {
        wmOmNoticeHService.doPrintckd(id, response);
    }

    /*--------------------------------其他出货-出货通知项目-begin----------------------------------------------*/

    /**
     * 添加
     *
     * @param wmOmNoticeHPage
     * @return
     */
    @AutoLog(value = "出库管理-添加")
    @ApiOperation(value = "出库管理-添加", notes = "出库管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmOmNoticeHPage wmOmNoticeHPage) {
        WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
        BeanUtils.copyProperties(wmOmNoticeHPage, wmOmNoticeH);
        wmOmNoticeHService.saveMain(wmOmNoticeH, wmOmNoticeHPage.getWmOmNoticeIList());
        return Result.ok("操作成功");
    }

    /**
     * 添加
     *
     * @param wmOmNoticeHPage
     * @return
     */
    @AutoLog(value = "双维添加")
    @ApiOperation(value = "出库管理-添加", notes = "出库管理-添加")
    @PostMapping(value = "/addsw")
    public Result<?> addSw(@RequestBody WmOmNoticeHPage wmOmNoticeHPage) {
        WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
        BeanUtils.copyProperties(wmOmNoticeHPage, wmOmNoticeH);
        String noticeId = wmOmNoticeHService.addSw(wmOmNoticeH, wmOmNoticeHPage.getWmOmNoticeIList());
        return Result.OK(noticeId);
    }


    /**
     * 编辑
     *
     * @param wmOmNoticeHPage
     * @return
     */
    @AutoLog(value = "出库管理-编辑")
    @ApiOperation(value = "出库管理-编辑", notes = "出库管理-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmOmNoticeHPage wmOmNoticeHPage) {
        WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
        BeanUtils.copyProperties(wmOmNoticeHPage, wmOmNoticeH);
        WmOmNoticeH wmOmNoticeHEntity = wmOmNoticeHService.getById(wmOmNoticeH.getId());
        if (wmOmNoticeHEntity == null) {
            return Result.error("未找到对应数据");
        }
        wmOmNoticeHService.updateMain(wmOmNoticeH, wmOmNoticeHPage.getWmOmNoticeIList());
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "出货-通过id删除")
    @ApiOperation(value = "出货-通过id删除", notes = "出货-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wmOmNoticeHService.delMains(id);
        return Result.ok("删除成功!");
    }


    /**
     * 双维
     * 通过omNoticeId删除
     *
     * @param omNoticeId
     * @return
     */
    @AutoLog(value = "双维omNoticeId删除")
    @ApiOperation(value = "出货-通过omNoticeId删除", notes = "出货-通过omNoticeId删除")
    @DeleteMapping(value = "/deleteByomNoticeId")
    public Result<?> deleteByomNoticeId(@RequestParam(name = "omNoticeId", required = true) String omNoticeId) {
        List<WmOmNoticeH> list = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, omNoticeId).list();
        for (WmOmNoticeH wmOmNoticeH : list) {
            List<WmOmQmI> lists = wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, wmOmNoticeH.getOmNoticeId()).list();
            if (CollectionUtil.isNotEmpty(lists)) {
                for (WmOmQmI wmOmQmI : lists) {
                    wmOmQmIService.removeById(wmOmQmI.getId());
                }
            }
            wmOmNoticeHService.delMain(wmOmNoticeH.getId());
        }
        return Result.ok("删除成功!");
    }

    @AutoLog(value = "pltn出库完成")
    @ApiOperation(value = "pltn出库完成", notes = "收货完成")
    @GetMapping(value = "/updateOmNoticeById2")
    public Result<?> updateOmNoticeById2(@RequestParam(name = "noticeId") String noticeId) {

        WmOmNoticeH omNoticeH = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, noticeId).one();
        if (omNoticeH != null) {
            List<WmOmQmI> list = wmOmQmIService.lambdaQuery()
                    .eq(WmOmQmI::getOmNoticeId, omNoticeH.getOmNoticeId())
                    .and(i -> i.eq(WmOmQmI::getBinSta, "I").or().eq(WmOmQmI::getBinSta, "N"))
                    .list();
            if (CollectionUtil.isNotEmpty(list)) {
                throw new JeecgBootException("操作失败");
            } else {
                try {
                    // 创建销售追踪
                    if(StringUtil.isNotEmpty(omNoticeH.getRemarks()) && "12".equals(omNoticeH.getOrderTypeCode())){
                        BusiOmTrace busiOmTrace = new BusiOmTrace();
                        LambdaQueryWrapper<BusiOmTrace> objectLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                        objectLambdaQueryWrapper1.eq(BusiOmTrace::getQuery01,omNoticeH.getU8Djcode1());
                        List<BusiOmTrace> list2 = iBusiOmTraceService.list(objectLambdaQueryWrapper1);
                        if(CollectionUtil.isNotEmpty(list2)){
                            busiOmTrace = list2.get(0);
                        }
                        // 查询销售单是否有多个客户SELECT * FROM (
                        //SELECT
                        //	a.query14,
                        //	b.zhong_wen_qch,
                        //	b.xing_ye_fen_lei,
                        //	a.query10,
                        //	a.query11,
                        //	a.query08,
                        //	a.num01,
                        //	a.num06,
                        //	a.num10,
                        //	a.num09,
                        //	sum(CASE WHEN c.query01 = 'QTFY' THEN c.query22 ELSE 0 END) as query22,
                        //	sum(CASE WHEN c.query01 = 'QCFY' THEN c.query22 ELSE 0 END) as qcTotal,
                        //	a.num09 + sum(CASE WHEN c.query01 = 'QTFY' THEN c.query22 ELSE 0 END) + sum(CASE WHEN c.query01 = 'QCFY' THEN c.query22 ELSE 0 END) as num99,
                        //	a.query17,
                        //	c.query06
                        //FROM
                        //	busi_po a
                        //	LEFT JOIN md_cus b ON b.ke_hu_bian_ma = a.query24
                        //	LEFT JOIN (SELECT query01,query15,query06,sum(query22) as query22  from busi_Ord_Price WHERE query01 in ('QTFY','QCFY') and query15 <> '' and query15 is not null GROUP BY query01,query15,query06) c on  c.query15 = a.query14
                        //	GROUP BY a.query14,
                        //	b.zhong_wen_qch,
                        //	b.xing_ye_fen_lei,
                        //	a.query10,
                        //	a.query11,
                        //	a.query08,
                        //	a.num01,
                        //	a.num06,
                        //	a.num10,
                        //	a.num09,a.query17,
                        //	c.query06
                        //	) aaa
                        //	WHERE aaa.query22 != 0 or aaa.qcTotal != 0
                        //
                        //	
                        LambdaQueryWrapper<WmOmNoticeH> wmomnoticeh = new LambdaQueryWrapper<>();
                        wmomnoticeh.eq(WmOmNoticeH::getU8Djcode1,omNoticeH.getU8Djcode1());
                        wmomnoticeh.groupBy(WmOmNoticeH::getCusCode);
                        List<WmOmNoticeH> list1 = wmOmNoticeHService.list(wmomnoticeh);


                        // 查询销售单是否有多个客户
                        LambdaQueryWrapper<WmOmNoticeH> WmOmlist2wmomnoticeh2 = new LambdaQueryWrapper<>();
                        WmOmlist2wmomnoticeh2.eq(WmOmNoticeH::getU8Djcode1,omNoticeH.getU8Djcode1());
                        List<WmOmNoticeH> WmOmlist2 = wmOmNoticeHService.list(WmOmlist2wmomnoticeh2);

                        WmOmNoticeH wmOmNoticeH = list1.get(0);
                        busiOmTrace.setQuery01(omNoticeH.getU8Djcode1()); // 装箱单号


                        LambdaQueryWrapper<WmOmNoticeH> wmomnoticeh2 = new LambdaQueryWrapper<>();
                        wmomnoticeh2.eq(WmOmNoticeH::getU8Djcode1,omNoticeH.getU8Djcode1());
                        List<WmOmNoticeH> list02 = wmOmNoticeHService.list(wmomnoticeh2);

                        List<String> collect2 = list02.stream().map(WmOmNoticeH::getOmNoticeId).collect(Collectors.toList());
                        LambdaQueryWrapper<WmToDownGoods> objectLambdaQueryWrapper02 = new LambdaQueryWrapper<>();
                        objectLambdaQueryWrapper02.in(WmToDownGoods::getOrderId,collect2);
                        objectLambdaQueryWrapper02.orderByDesc(WmToDownGoods::getCreateTime);
                        WmToDownGoods one22 = wmToDownGoodsService.getOne(objectLambdaQueryWrapper02, false);
                        if(one22 != null){
                            // 创建一个Date对象，表示当前时间
                            Date currentDate = one22.getCreateTime();
                            // 创建SimpleDateFormat对象，指定日期格式
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            // 使用SimpleDateFormat对象格式化Date对象
                            String formattedDate = dateFormat.format(currentDate);
                            busiOmTrace.setQuery02(formattedDate); // 出库日期
                        }
                        String query03 = "";
                        if(list1.size() == 1){
                            QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
                            MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,wmOmNoticeH.getCusCode());
                            MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
                            if(one2 == null){
                                throw new JeecgBootException("客户查询失败");
                            }
                            query03 = one2.getXingYeFenLei();
                            busiOmTrace.setQuery04(wmOmNoticeH.getCusCode()); // 客户名
                        }else {
                            busiOmTrace.setQuery04("MIXED CLIENT"); // 客户名
                            String s = "DUBAI,HK,USA,SINGAPOR";
                            List<String> split = Arrays.asList(s.split(","));
                            String s2 = "FRANCE,UK,SWITZERLAND";
                            List<String> split2 = Arrays.asList(s2.split(","));
                            if(split.contains(wmOmNoticeH.getShipmentAddress())){
                                query03 = "P-ASIA";
                            }else if(split2.contains(wmOmNoticeH.getShipmentAddress())){
                                query03 = "P-EUR";
                            }
                        }
                        busiOmTrace.setQuery03(query03); // 公司属性
                        busiOmTrace.setQuery05(wmOmNoticeH.getShipmentAddress()); // 目的地
                        busiOmTrace.setQuery09(wmOmNoticeH.getShipmentWay()); // 出货方式

                        List<String> collect = WmOmlist2.stream().map(WmOmNoticeH::getOmNoticeId).collect(Collectors.toList());
                        LambdaQueryWrapper<WmToDownGoods> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
                        objectLambdaQueryWrapper.in(WmToDownGoods::getOrderId,collect);
                        List<WmToDownGoods> pageList = wmToDownGoodsService.list(objectLambdaQueryWrapper);
                        String TinId = "";
                        for (WmToDownGoods wmToDownGoods : pageList) {
                            if(StringUtils.isBlank(TinId)){
                                TinId = wmToDownGoods.getBinIdFrom();
                            }else {
                                TinId = TinId + "," + wmToDownGoods.getBinIdFrom();
                            }
                        }
                        LambdaQueryWrapper<WmTuopan> objectLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
                        objectLambdaQueryWrapper2.in(WmTuopan::getTinId, Arrays.asList(TinId.split(",")));
                        List<WmTuopan> pageList2 = iWmTuopanService.list(objectLambdaQueryWrapper2);
                        double sum = pageList2.stream()
                                .mapToDouble(tuopan -> {
                                    double length = Double.parseDouble(tuopan.getTinLength());
                                    double width = Double.parseDouble(tuopan.getTinWidth());
                                    double high = Double.parseDouble(tuopan.getTinHigh());
                                    return length * width * high;
                                })
                                .sum();
                        double sum2 = pageList2.stream()
                                .mapToDouble(tuopan -> Double.parseDouble(tuopan.getTinWeight()))
                                .sum();
                        busiOmTrace.setQuery18(String.valueOf(sum / 1000000)); // 总体积
                        busiOmTrace.setQuery19(String.valueOf(sum2)); // 总重量
                        busiOmTrace.setQuery20(String.valueOf(pageList.size())); // 总箱数
                        busiOmTrace.setQuery11("On the way"); // 状态
                        iBusiOmTraceService.saveOrUpdate(busiOmTrace);
                    }
                }catch (Exception e){}
                omNoticeH.setOmSta("已完成");
                wmOmNoticeHService.updateById(omNoticeH);
            }
        }
        return Result.ok("操作成功");
    }

    /**
     * pda收货完成
     *
     * @param noticeId
     * @return
     */
    @AutoLog(value = "双维出库完成")
    @ApiOperation(value = "双维出库完成", notes = "收货完成")
    @GetMapping(value = "/updateOmNoticeById")
    public Result<?> updateOmNoticeById(@RequestParam(name = "noticeId") String noticeId) {
        WmOmNoticeH omNoticeH = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, noticeId).one();
        /*if (omNoticeH != null) {
            Map map1 = new LinkedHashMap();
            map1.put("noticeId", noticeId);
            map1.put("omSta", "已完成");
            JSONObject jsonObj = new JSONObject(map1);
            String Json = jsonObj.toString();
//            String post = HttpUtil.post(""https://retail.danbagui.com/api/storage/sync/record/callback", Json);
            String post = HttpUtil.post("https://retail.danbagui.com/api/storage/sync/record/callback", Json);
            JSONObject parse = (JSONObject) JSONObject.parse(post);
            String code = parse.get("code") != null ? parse.get("code").toString() : "";
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(code) && code.equals("SUCCESS")) {
                omNoticeH.setOmSta("已完成");
                wmOmNoticeHService.updateById(omNoticeH);
                List<WmOmQmI> list = wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, omNoticeH.getOmNoticeId()).list();
                if (CollectionUtil.isNotEmpty(list)) {
                    for (WmOmQmI wmOmQmI : list) {
                        wmOmQmI.setBinSta("Y");
                        wmOmQmIService.updateById(wmOmQmI);
                    }
                }
            }
        }*/
        return Result.ok("操作成功");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "出货-批量删除")
    @ApiOperation(value = "出货-批量删除", notes = "出货-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wmOmNoticeHService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "出库管理-通过id查询")
    @ApiOperation(value = "出库管理-通过id查询", notes = "出库管理-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHService.getById(id);
        if (wmOmNoticeH == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmOmNoticeH);

    }

    /*    *//**
     * 导出
     *
     * @return
     *//*
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmOmNoticeH wmOmNoticeH) {
        return super.exportXls(request, wmOmNoticeH, WmOmNoticeH.class, "出货");
    }

    *//**
     * 导入
     *
     * @return
     *//*
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WmOmNoticeH.class);
    }*/
    /*---------------------------------主表处理-end-------------------------------------*/


    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "出货详情-通过主表ID查询")
    @ApiOperation(value = "出货详情-通过主表ID查询", notes = "出货详情-通过主表ID查询")
    @GetMapping(value = "/queryWmOmNoticeIByMainId")
    public Result<?> queryWmOmNoticeIListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<WmOmNoticeI> wmOmNoticeIList = wmOmNoticeIService.selectByMainId(id);
        IPage<WmOmNoticeI> page = new Page<>();
        page.setRecords(wmOmNoticeIList);
        page.setTotal(wmOmNoticeIList.size());
        return Result.ok(page);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wmOmNoticeH
     */
   /* @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmOmNoticeH wmOmNoticeH) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WmOmNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeH, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 获取导出数据
        List<WmOmNoticeH> queryList = wmOmNoticeHService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WmOmNoticeH> wmOmNoticeHList = new ArrayList<WmOmNoticeH>();
        if (oConvertUtils.isEmpty(selections)) {
            wmOmNoticeHList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wmOmNoticeHList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<WmOmNoticeHPage> pageList = new ArrayList<WmOmNoticeHPage>();
        for (WmOmNoticeH main : wmOmNoticeHList) {
            WmOmNoticeHPage vo = new WmOmNoticeHPage();
            BeanUtils.copyProperties(main, vo);
            List<WmOmNoticeI> wmOmNoticeIList = wmOmNoticeIService.selectByMainId(main.getId());
            vo.setWmOmNoticeIList(wmOmNoticeIList);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "出库管理列表");
        mv.addObject(NormalExcelConstants.CLASS, WmOmNoticeHPage.class);
        //mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("出库管理数据", "导出人:"+sysUser.getRealname(), "出库管理"));
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("出库管理数据", "导出人:" + sysUser.getRealname(), "出库通知"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }*/

    /**
     * 导出excel
     *
     * @param request
     * @param wmOmNoticeH
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmOmNoticeH wmOmNoticeH) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WmOmNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeH, request.getParameterMap());

        //Step.2 获取导出数据
        List<WmOmNoticeH> queryList = wmOmNoticeHService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WmOmNoticeH> wmOmNoticeHList = new ArrayList<WmOmNoticeH>();
        if (StringUtils.isEmpty(selections)) {
            wmOmNoticeHList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wmOmNoticeHList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<WmOmNoticeHPage> pageList = new ArrayList<WmOmNoticeHPage>();
        for (WmOmNoticeH main : wmOmNoticeHList) {
            WmOmNoticeHPage vo = new WmOmNoticeHPage();
            BeanUtils.copyProperties(main, vo);
            if("13".equals(main.getOrderTypeCode())){
                List<WmOmNoticeI> wmOmNoticeIS = wmOmNoticeIService.selectByMainId(main.getOmNoticeId());
                for (WmOmNoticeI wmOmNoticeI : wmOmNoticeIS) {
                    QueryWrapper<WmOmQmI> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.lambda().eq(WmOmQmI::getOmNoticeId,wmOmNoticeI.getOmNoticeId());
                    queryWrapper2.lambda().eq(WmOmQmI::getGoodsId,wmOmNoticeI.getGoodsId());
                    queryWrapper2.lambda().groupBy(WmOmQmI::getGoodsBatch);
                    List<WmOmQmI> list = wmOmQmIService.list(queryWrapper2);
                    List<String> strarr = new ArrayList<>();
                    for (WmOmQmI wmOmQmI : list) {
                        strarr.add(wmOmQmI.getGoodsBatch());
                    }
//                    strarr = strarr.stream().distinct().collect(Collectors.toList());
                    String collect = strarr.stream().collect(Collectors.joining(", "));
                    wmOmNoticeI.setGoodsBatch(collect);
                }
                vo.setWmOmNoticeIList(wmOmNoticeIS);
            }else {
                List<WmOmNoticeI> wmOmNoticeIS = wmOmNoticeIService.selectByMainId(main.getOmNoticeId());
                vo.setWmOmNoticeIList(wmOmNoticeIS);
            }
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "wm_om_notice_h列表");
        mv.addObject(NormalExcelConstants.CLASS, WmOmNoticeHPage.class);
        //mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("wm_im_notice_h数据", "导出人:"+sysUser.getRealname(), "wm_im_notice_h"));
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("销售出库", "导出时间:" + DateUtils.getDataString(DateUtils.yyyymmddhhmmss.get()), "采购入库"));
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
                List<WmOmNoticeHPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WmOmNoticeHPage.class, params);
                for (WmOmNoticeHPage page : list) {
                    WmOmNoticeH po = new WmOmNoticeH();
                    BeanUtils.copyProperties(page, po);
                    wmOmNoticeHService.saveMain(po, page.getWmOmNoticeIList());
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

    /*--------------------------------子表处理-出货通知项目-begin----------------------------------------------*/

    /**
     * 通过主表ID查询
     *
     * @return
     */
    @AutoLog(value = "出货项目-通过主表ID查询")
    @ApiOperation(value = "出货项目-通过主表ID查询", notes = "出货项目-通过主表ID查询")
    @GetMapping(value = "/listWmOmNoticeIByMainId")
    public Result<?> listWmOmNoticeIByMainId(WmOmNoticeI wmOmNoticeI,
                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                             HttpServletRequest req) {
        QueryWrapper<WmOmNoticeI> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeI, req.getParameterMap());
        Page<WmOmNoticeI> page = new Page<WmOmNoticeI>(pageNo, pageSize);
        IPage<WmOmNoticeI> pageList = wmOmNoticeIService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param wmOmNoticeI
     * @return
     */
    @AutoLog(value = "出货项目-添加")
    @ApiOperation(value = "出货项目-添加", notes = "出货项目-添加")
    @PostMapping(value = "/addWmOmNoticeI")
    public Result<?> addWmOmNoticeI(@RequestBody WmOmNoticeI wmOmNoticeI) {
        //wmOmNoticeIService.save(wmOmNoticeI);
        Result<?> result = wmOmNoticeIService.saveWmOmQmIM(wmOmNoticeI);
        return result;
    }

    /**
     * 编辑
     *
     * @param wmOmNoticeI
     * @return
     */
    @AutoLog(value = "出货项目-编辑")
    @ApiOperation(value = "出货项目-编辑", notes = "出货项目-编辑")
    @PutMapping(value = "/editWmOmNoticeI")
    public Result<?> editWmOmNoticeI(@RequestBody WmOmNoticeI wmOmNoticeI) {
        wmOmNoticeIService.updateById(wmOmNoticeI);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "出货项目-通过id删除")
    @ApiOperation(value = "出货项目-通过id删除", notes = "出货项目-通过id删除")
    @DeleteMapping(value = "/deleteWmOmNoticeI")
    public Result<?> deleteWmOmNoticeI(@RequestParam(name = "id", required = true) String id) {
        wmOmNoticeIService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "出货项目-批量删除")
    @ApiOperation(value = "出货项目-批量删除", notes = "出货项目-批量删除")
    @DeleteMapping(value = "/deleteBatchWmOmNoticeI")
    public Result<?> deleteBatchWmOmNoticeI(@RequestParam(name = "ids", required = true) String ids) {
        this.wmOmNoticeIService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * @Describe : 批量修改出货项目
     * @Author: zly
     * @Create Date: 2021-05-18
     */
    @AutoLog(value = "批量修改出货项目-编辑")
    @ApiOperation(value = "批量修改出货项目-编辑", notes = "批量修改出货项目-编辑")
    @PutMapping(value = "/editWmOmNoticeIList")
    public Result<?> EditWmOmNoticeIList(@RequestBody List<EditWmOmNoticeIListVo> voList) {
        Result<?> result = wmOmNoticeIService.editWmOmNoticeIList(voList);
        return result;
    }

    /**
     * 下载模板
     *
     * @return
     */
    @RequestMapping(value = "/downloadWmOmNoticeI")
    public ModelAndView downloadWmOmNoticeI(HttpServletRequest request, WmOmNoticeI wmOmNoticeI) {
        // Step.1 组装查询条件
        QueryWrapper<WmOmNoticeI> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeI, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        // Step.2 获取导出数据
        //List<WmOmNoticeI> pageList = wmOmNoticeIService.list(queryWrapper);
        List<WmOmNoticeI> exportList = new ArrayList<>();

        // 过滤选中数据
//        String selections = request.getParameter("selections");
//        if (oConvertUtils.isNotEmpty(selections)) {
//            List<String> selectionList = Arrays.asList(selections.split(","));
//            exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
//        } else {
//            exportList = pageList;
//        }
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "出货通知"); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, WmOmNoticeI.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("出货通知", "导出人:" + sysUser.getRealname(), "出货通知"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 导出
     *
     * @return
     */
    @RequestMapping(value = "/exportWmOmNoticeI")
    public ModelAndView exportWmOmNoticeI(HttpServletRequest request, WmOmNoticeI wmOmNoticeI) {
        // Step.1 组装查询条件
        QueryWrapper<WmOmNoticeI> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeI, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        // Step.2 获取导出数据
        List<WmOmNoticeI> pageList = wmOmNoticeIService.list(queryWrapper);
        List<WmOmNoticeI> exportList = null;

        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "出货项目"); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, WmOmNoticeI.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("出货项目报表", "导出人:" + sysUser.getRealname(), "出货项目"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 导入
     *
     * @return
     */
    @RequestMapping(value = "/importWmOmNoticeI/{mainId}")
    public Result<?> importWmOmNoticeI(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<WmOmNoticeI> list = ExcelImportUtil.importExcel(file.getInputStream(), WmOmNoticeI.class, params);
                for (WmOmNoticeI temp : list) {
                    temp.setOmNoticeId(mainId);
                }
                long start = System.currentTimeMillis();
                wmOmNoticeIService.saveBatch(list);
                log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
                return Result.ok("文件导入成功！数据行数：" + list.size());
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
        return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-出货通知项目-end----------------------------------------------*/
    @RequestMapping(value = "/getBankListByExcel")
    public Result<?> getBankListByExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iter = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(iter.next());
        InputStream in = null;
        List<List<Object>> listob = null;
        try {
            in = multipartFile.getInputStream();
            listob = analysisWarehousing.getBankListByExcel(in, multipartFile.getOriginalFilename());
            in.close();
        } catch (Exception e) {

        }
        wmOmNoticeHService.insertAnalys(listob);
        return Result.ok("文件导入成功！数据行数：" + listob.size());
    }

    /**
     * 合计和检查
     *
     * @param request
     * @param goodscode
     * @param goodsqua
     * @return
     */
    @RequestMapping(value = "/docheck")
    @ResponseBody
    public Result docheck(HttpServletRequest request, String goodscode, String goodsqua) {
        String message = null;
        Result j = new Result();
        message = "成功";
        try {

            String goods = null;
            String goodsid = request.getParameter("goodscode");
            if (!StringUtil.isEmpty(goodsid)) {
                if (goodsid.endsWith("l")) {
                    goods = goodsid.substring(0, goodsid.length() - 1);
                } else {
                    goods = goodsid;
                }

            }
            String sql = "select sum(base_goodscount) as qua from wv_stock t where  t.goods_id LIKE  '%"
                    + goods + "%'";
            Map<String, Object> binMap = jdbcTemplate.queryForMap(sql);
            if (binMap != null) {
                if (Double.parseDouble(binMap.get("qua").toString()) < Double.parseDouble(request.getParameter("goodsqua").toString())) {
                    j.setSuccess(false);
                    message = request.getParameter("goodscode").toString() + "库存为" + binMap.get("qua").toString();
                    j.setMessage(message);
                    return j;
                }
            }
        } catch (Exception e) {
            j.setSuccess(false);
            message = request.getParameter("goodscode").toString() + "库存为0";
            j.setMessage(message);
            return j;
        }
        j.setMessage(message);
        return j;
    }

    @AutoLog(value = "获取U8销售发货单信息")
    @ApiOperation(value = "获取U8销售发货单信息", notes = "获取U8销售发货单信息")
    @GetMapping(value = "/getU8Fhd")
    public Result<?> getU8Fhd(@RequestParam(name = "date", required = false) String date) throws Exception {
        u8JiekouXxlJob.getU8Xiaoshoufahuo(date);
        return Result.ok("同步成功！");
    }

    @AutoLog(value = "获取U8生产发料信息")
    @ApiOperation(value = "获取U8生产发料信息", notes = "获取U8生产发料信息")
    @GetMapping(value = "/getU8Cld")
    public Result<?> getU8Cld(@RequestParam(name = "date", required = false) String date) throws Exception {
        u8JiekouXxlJob.getU8Lingliaoxinxi(date);
        return Result.ok("同步成功！");
    }

    @AutoLog(value = "打印标签")
    @ApiOperation(value = "打印标签", notes = "打印标签")
    @GetMapping(value = "/doPrintBqList")
    public Result<?> doPrintBqList(@RequestParam String id, HttpServletResponse response) {
        Result<?> result = wmOmNoticeHService.doPrintBqList(id);
        return result;
    }

    @AutoLog(value = "导出列表查询")
    @ApiOperation(value = "导出列表查询", notes = "导出列表查询")
    @GetMapping(value = "/queryLists")
    public Result<?> queryLists(@RequestParam(name = "id", required = true) String id) {

        //出货数量：baseGoodscount   每箱数量： qtypercarton   总箱数：totalqtypercarton
        WmOmNoticeH omNoticeH = new WmOmNoticeH();
        List<WmOmNoticeI> noticeIS = new ArrayList<>();
        List<WmOmNoticeI> noticeIS3 = new ArrayList<>();
        List<WmImNoticeI> noticeIS1 = new ArrayList<>();
        List<WmOmQmI> omQmIS1 = new ArrayList();
        WmImNoticeH imNoticeH = new WmImNoticeH();
        List<WmImNoticeH> wmImNoticeHS = new ArrayList();


        String qb = "";
        String cusname = "";
        String purman = "";
        String salesno = "";
        String totalqtypercarton = "";
        String[] split = id.split(",");
        for (String s : split) {
            /*String qb = "";
            String cusname = "";
            String purman = "";/jeewms/wmInQmI/edit
            String salesno = "";
            String totalqtypercarton = "";*/
            omNoticeH = wmOmNoticeHService.getById(s);
            if (StringUtil.isNotEmpty(omNoticeH.getU8ReturnCode())) {
                String[] split1 = omNoticeH.getU8ReturnCode().split(",");
                wmImNoticeHS = noticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId,split1[0]).list();
                if(CollectionUtil.isNotEmpty(wmImNoticeHS)){
                    imNoticeH = wmImNoticeHS.get(0);
                }
                qb = StringUtil.isNotEmpty(qb) ? qb + "+" + imNoticeH.getNoticeId() : qb + imNoticeH.getNoticeId();
                cusname = StringUtil.isNotEmpty(cusname) ? cusname + "+" + imNoticeH.getCusName() : cusname + imNoticeH.getCusName();
                purman = StringUtil.isNotEmpty(purman) ? purman + "+" + imNoticeH.getCreateBy() : purman + imNoticeH.getCreateBy();
                salesno = StringUtil.isNotEmpty(salesno) ? salesno + "+" + imNoticeH.getU8ReturnCode() : salesno + imNoticeH.getU8ReturnCode();
                noticeIS1 = noticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId, imNoticeH.getNoticeId()).list();
            }
            List<WmOmNoticeI> omNoticeIS = wmOmNoticeIService.lambdaQuery().eq(WmOmNoticeI::getOmNoticeId, omNoticeH.getOmNoticeId()).list();
            for (WmOmNoticeI omNoticeI : omNoticeIS) {
                List<WmOmQmI> omQmIS = wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, omNoticeH.getOmNoticeId()).eq(WmOmQmI::getGoodsId, omNoticeI.getGoodsId()).list();
                omQmIS1.addAll(omQmIS);
            }
        }

        HashMap<String, WmOmQmI> map = new HashMap<>(1024);
        for (WmOmQmI item : omQmIS1) {// 遍历原集合
            if (map.containsKey(item.getBaseGoodscount() + item.getGoodsId() + item.getOmNoticeId())) {// 若map中的key包含该型号
                WmOmQmI itemDTO = map.get(item.getBaseGoodscount() + item.getGoodsId() + item.getOmNoticeId());// 取出该对象，与集合中相同的对象进行数量合并
                itemDTO.setBaseGoodscount(String.valueOf(NumberUtil.add(item.getBaseGoodscount(), itemDTO.getBaseGoodscount())));
                itemDTO.setFirstRq(item.getFirstRq() + "," + itemDTO.getFirstRq());
                map.put(item.getBaseGoodscount() + item.getGoodsId() + item.getOmNoticeId(), itemDTO);// 将合并数量的该对象重新存入map集合，因key值相同，所以会覆盖掉之前的对象
            } else {
                map.put(item.getBaseGoodscount() + item.getGoodsId() + item.getOmNoticeId(), item);
            }
        }

        List<WmOmQmI> list = new ArrayList<>();
        for (String model : map.keySet()) {// 将map中的对象重新存放新的List集合
            WmOmQmI itemDTO = map.get(model);
            list.add(itemDTO);
        }

        for (WmOmQmI wmOmQmI : list) {
            WmOmNoticeI omNoticeI = wmOmNoticeIService.lambdaQuery()
                    .eq(WmOmNoticeI::getOmNoticeId, wmOmQmI.getOmNoticeId())
                    .eq(WmOmNoticeI::getGoodsId, wmOmQmI.getGoodsId())
                    .one();
            MdGoods mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, omNoticeI.getGoodsId()).one();
            List<MdGoodsItem> mdGoodsItems = goodsItemService.lambdaQuery().eq(MdGoodsItem::getSttr1, mdGoods.getShpBianMa()).list();

            BaGoodsType baGoodsType = new BaGoodsType();
            List<ConfErp> shfl = confErpService.lambdaQuery().eq(ConfErp::getQuery01, "SHFL").eq(ConfErp::getQuery04, wmOmQmI.getGoodsId()).list();
            String billingproductname = "";
            String billingproductunit = "";
            if(CollectionUtil.isNotEmpty(shfl)){
                billingproductname = shfl.get(0).getQuery08();
                billingproductunit = shfl.get(0).getQuery10();
            }
            omNoticeI.setBillingproductname(billingproductname);
            omNoticeI.setBillingproductunit(billingproductunit);
            List<BaGoodsType> baGoodsTypes = goodsTypeService.lambdaQuery().eq(BaGoodsType::getGoodsTypeName, mdGoods.getChpShuXing()).list();
            if (CollectionUtil.isNotEmpty(baGoodsTypes)) {
                baGoodsType = baGoodsTypes.get(0);
            }
            omNoticeI.setGoodsTypeEnname(baGoodsType.getGoodsTypeEnname());
            omNoticeI.setShpYanEnse(mdGoods.getShpYanSeCode());
            omNoticeI.setGoodsTypeName(mdGoods.getChpShuXing());
            omNoticeI.setGoodsId(mdGoods.getShpBianMa());
            omNoticeI.setGoodsguige(mdGoods.getShpGuiGe());
            omNoticeI.setGoodsUnit(mdGoods.getGoodsUnit());
            omNoticeI.setGoodsName(mdGoods.getShpMingCheng());
            omNoticeI.setGoodsjianchen(mdGoods.getYwMingCheng());
            //omNoticeI.setShpYanEnse(mdGoods.getShpYanEnse());
            omNoticeI.setGoodsTypeName(mdGoods.getChpShuXing());

            omNoticeI.setTaxrefund("0".equals(omNoticeI.getCheckname()) ? "否" : "是");
            omNoticeI.setBillingproductunit(mdGoods.getGoodsUnit());
            omNoticeI.setBaseGoodscount(wmOmQmI.getBaseGoodscount());
            omNoticeI.setQtypercarton(wmOmQmI.getQmOkQuat());
            omNoticeI.setFirstRq(wmOmQmI.getFirstRq());



            WmImNoticeH imNoticeH2 = new WmImNoticeH();
            List<WmImNoticeH> wmImNoticeHS2 = new ArrayList();
            List<WmImNoticeH> wmImNoticeHs = new ArrayList();
            List<WmOmNoticeH> wmOmNoticeH = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, wmOmQmI.getOmNoticeId()).list();
            if(CollectionUtil.isNotEmpty(wmOmNoticeH)){
                omNoticeI.setShipdepcode(wmOmNoticeH.get(0).getDelvAddr());
                omNoticeI.setShipdepname(wmOmNoticeH.get(0).getPiMaster());
                omNoticeI.setShiporgcode(wmOmNoticeH.get(0).getDelvMember());
                String[] split1 = wmOmNoticeH.get(0).getU8ReturnCode().split(",");
                wmImNoticeHS2 = noticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId,split1[0]).list();
            }

            if(CollectionUtil.isNotEmpty(wmImNoticeHS2)){
                imNoticeH2 = wmImNoticeHS2.get(0);
                wmImNoticeHs = noticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, imNoticeH2.getNoticeId()).list();
            }



            if(CollectionUtil.isNotEmpty(wmImNoticeHs)){
                WmImNoticeI w = noticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId,wmImNoticeHs.get(0).getNoticeId()).eq(WmImNoticeI::getGoodsCode,wmOmQmI.getGoodsId()).list().get(0);
                omNoticeI.setGoodsUnitPrice(w.getUnitPrice());
                omNoticeI.setContractlno(w.getContractlno());
                omNoticeI.setBillingproductunit(w.getGoodsUnit());
                omNoticeI.setCusName(wmImNoticeHs.get(0).getCusName());
                if(StringUtil.isNotEmpty(wmImNoticeHs.get(0).getPurchasename())){
                    omNoticeI.setCreateBy(wmImNoticeHs.get(0).getPurchasename());
                }

            }


            List<WmOmNoticeH> list1 = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, wmOmQmI.getOmNoticeId()).list();
            if(CollectionUtil.isNotEmpty(list1)){
                WmOmNoticeH wmOmNoticeH1 = list1.get(0);
                String[] split1 = wmOmNoticeH1.getU8ReturnCode().split(",");
                List<WmImNoticeH> list2 = noticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, split1[0]).list();
                if(CollectionUtil.isNotEmpty(list2)){
                    WmImNoticeH wmImNoticeH = list2.get(0);
                    omNoticeI.setAstreanum(wmImNoticeH.getAstreanum());
                    omNoticeI.setQbno(qb);
                    omNoticeI.setU8ReturnCode(wmImNoticeH.getU8ReturnCode());
                }
            }
            List<WmTuopan> wmTuopans = tuopanService.lambdaQuery().eq(WmTuopan::getTinId, wmOmQmI.getTinId()).list();
            if (CollectionUtil.isNotEmpty(wmTuopans)) {
                WmTuopan tuopan = wmTuopans.get(0);
                omNoticeI.setStickehno(tuopan.getTinId());
                omNoticeI.setCtnsizel(tuopan.getTinLength());
                omNoticeI.setCtnsizew(tuopan.getTinWidth());
                omNoticeI.setCtnsizeh(tuopan.getTinHigh());
                omNoticeI.setWeightctn(tuopan.getTinWeight());
                omNoticeI.setWolctn(String.valueOf(Double.parseDouble(NumberUtil.div(tuopan.getTinVolume(), "1000000").toString())));
                omNoticeI.setShippingmark(tuopan.getBoxmark());
            }
            if(StringUtils.isNotEmpty(omNoticeI.getBaseGoodscount()) && StringUtils.isNotEmpty(omNoticeI.getQtypercarton())){
                omNoticeI.setTotalqtypercarton(String.valueOf(NumberUtil.div(omNoticeI.getBaseGoodscount(), omNoticeI.getQtypercarton())));
            }
            if(StringUtils.isNotEmpty(omNoticeI.getTotalqtypercarton()) && StringUtils.isNotEmpty(omNoticeI.getWolctn())){
                omNoticeI.setTotalvol(String.valueOf(Double.parseDouble(StringUtils.isNotEmpty(omNoticeI.getWolctn()) ? NumberUtil.mul(omNoticeI.getWolctn(), omNoticeI.getTotalqtypercarton()).toString() : "")));
            }
            if(StringUtils.isNotEmpty(omNoticeI.getTotalqtypercarton())){
                omNoticeI.setTotalweight(StringUtils.isNotEmpty(omNoticeI.getWeightctn()) ? Convert.toDouble(NumberUtil.mul(omNoticeI.getWeightctn(), omNoticeI.getTotalqtypercarton())).toString() : "");
            }

            if ("1".equals(omNoticeI.getCheckname())) {
                if (CollectionUtil.isNotEmpty(mdGoodsItems)) {
                    int index = 0;
                    for (MdGoodsItem mdGoodsItem : mdGoodsItems) {
                        if(index != 0){
                            //总箱数
                            omNoticeI.setTotalqtypercarton("");
                            omNoticeI.setBaseGoodscount("");
                            omNoticeI.setTotalvol("");
                            omNoticeI.setTotalweight("");
                        }
                        index++;
                        WmOmNoticeI omNoticeI1 = new WmOmNoticeI();
                        BeanUtil.copyProperties(omNoticeI, omNoticeI1);
                        MdGoods mdGoods2 = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, mdGoodsItem.getSttr2()).one();
                        omNoticeI1.setGoodsId(mdGoods2.getShpBianMa());
                        omNoticeI1.setGoodsguige(mdGoods2.getShpGuiGe());
                        omNoticeI1.setGoodsUnit(mdGoods2.getGoodsUnit());
                        omNoticeI1.setGoodsName(mdGoods2.getShpMingCheng());
                        omNoticeI1.setGoodsjianchen(mdGoods2.getYwMingCheng());
                        omNoticeI1.setStickehstatus(omNoticeI.getId() + wmOmQmI.getQmOkQuat());
                        omNoticeI1.setShpYanEnse(mdGoods2.getShpYanEnse());
                        omNoticeI1.setGoodsTypeName(mdGoods2.getChpShuXing());
                        List<BaGoodsType> baGoodsTypes1 = goodsTypeService.lambdaQuery().eq(BaGoodsType::getGoodsTypeName, mdGoods2.getChpShuXing()).list();
                        if (CollectionUtil.isNotEmpty(baGoodsTypes1)) {
                            baGoodsType = baGoodsTypes1.get(0);
                        }
                        omNoticeI1.setGoodsTypeEnname(baGoodsType.getGoodsTypeEnname());
                        List<ConfErp> shfl1 = confErpService.lambdaQuery().eq(ConfErp::getQuery01, "SHFL").eq(ConfErp::getQuery04, mdGoods2.getShpBianMa()).list();
                        String billingproductname1 = "";
                        String billingproductunit1 = "";
                        if(CollectionUtil.isNotEmpty(shfl1)){
                            billingproductname1 = shfl1.get(0).getQuery08();
                            billingproductunit1 = shfl1.get(0).getQuery10();
                        }
                        omNoticeI1.setBillingproductname(billingproductname1);
                        omNoticeI1.setBillingproductunit(billingproductunit1);
                        noticeIS.add(omNoticeI1);
                    }
                }else {
                    noticeIS.add(omNoticeI);
                }
            }else {
                noticeIS.add(omNoticeI);
            }
        }
        omNoticeH.setWmOmNoticeIList(noticeIS);
        return Result.ok(omNoticeH);
    }



    @AutoLog(value = "导出装箱单数据")
    @ApiOperation(value = "导出装箱单数据", notes = "导出装箱单数据")
    @GetMapping(value = "/queryLists2")
    public Result<?> queryLists2(@RequestParam(name = "id", required = true) String id) {
        WmOmNoticeH omNoticeH = new WmOmNoticeH();
        List<WmOmNoticeI> noticeIS = new ArrayList<>();
        String[] split = id.split(",");
        String salesno = "";
        for (String s : split) {
            WmImNoticeH imNoticeH = new WmImNoticeH();
            List<WmImNoticeH> wmImNoticeHS = new ArrayList();
            List<WmOmNoticeI> omNoticeIS = new ArrayList<>();
            omNoticeH = wmOmNoticeHService.getById(s);

            if (StringUtil.isNotEmpty(omNoticeH.getU8ReturnCode())) {
                String[] split1 = omNoticeH.getU8ReturnCode().split(",");
                wmImNoticeHS = noticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, split1[0]).list();
                if (CollectionUtil.isNotEmpty(wmImNoticeHS)) {
                    imNoticeH = wmImNoticeHS.get(0);
                }
                salesno = StringUtil.isNotEmpty(salesno) ? salesno + "+" + imNoticeH.getU8ReturnCode() : salesno + imNoticeH.getU8ReturnCode();
            }
            if(StringUtil.isNotEmpty(omNoticeH.getOmNoticeId())){
                 omNoticeIS = wmOmNoticeIService.lambdaQuery().eq(WmOmNoticeI::getOmNoticeId, omNoticeH.getOmNoticeId()).list();

            }
            List<WmImNoticeI> list = noticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId, imNoticeH.getNoticeId()).list();
            for (WmImNoticeI wmImNoticeI : list) {
                for (WmOmNoticeI omNoticeI : omNoticeIS) {
                    if(wmImNoticeI.getGoodsCode().equals(omNoticeI.getGoodsId())){
                        MdGoods mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, omNoticeI.getGoodsId()).one();
                        BaGoodsType baGoodsType = new BaGoodsType();
                        omNoticeI.setShipdepcode(omNoticeH.getDelvAddr());
                        omNoticeI.setShipdepname(omNoticeH.getPiMaster());
                        omNoticeI.setShiporgcode(omNoticeH.getDelvMember());
                        List<ConfErp> shfl = confErpService.lambdaQuery().eq(ConfErp::getQuery01, "SHFL").eq(ConfErp::getQuery04, omNoticeI.getGoodsId()).list();
                        String billingproductname = "";
                        String billingproductunit = "";
                        if(CollectionUtil.isNotEmpty(shfl)){
                            billingproductname = shfl.get(0).getQuery08();
                            billingproductunit = shfl.get(0).getQuery10();
                        }
                        omNoticeI.setBillingproductname(billingproductname);
                        omNoticeI.setBillingproductunit(billingproductunit);
                        List<BaGoodsType> baGoodsTypes = goodsTypeService.lambdaQuery().eq(BaGoodsType::getGoodsTypeName, mdGoods.getChpShuXing()).list();
                        if (CollectionUtil.isNotEmpty(baGoodsTypes)) {
                            baGoodsType = baGoodsTypes.get(0);
                        }
                        omNoticeI.setGoodsTypeEnname(baGoodsType.getGoodsTypeEnname());
                        omNoticeI.setShpYanEnse(mdGoods.getShpYanSeCode());
                        omNoticeI.setGoodsTypeName(mdGoods.getChpShuXing());
                        omNoticeI.setGoodsId(mdGoods.getShpBianMa());
                        omNoticeI.setGoodsguige(mdGoods.getShpGuiGe());
                        omNoticeI.setGoodsUnit(mdGoods.getGoodsUnit());
                        omNoticeI.setGoodsName(mdGoods.getShpMingCheng());
                        omNoticeI.setGoodsjianchen(mdGoods.getYwMingCheng());
                        //omNoticeI.setShpYanEnse(mdGoods.getShpYanEnse());
                        omNoticeI.setGoodsTypeName(mdGoods.getChpShuXing());
                        omNoticeI.setTaxrefund("0".equals(omNoticeI.getCheckname()) ? "否" : "是");
                        omNoticeI.setBillingproductunit(mdGoods.getGoodsUnit());

                        omNoticeI.setGoodsUnitPrice(wmImNoticeI.getUnitPrice());
                        omNoticeI.setContractlno(wmImNoticeI.getContractlno());
                        omNoticeI.setBillingproductunit(wmImNoticeI.getGoodsUnit());
                        omNoticeI.setCusName(wmImNoticeHS.get(0).getCusName());
                        if(StringUtil.isNotEmpty(wmImNoticeHS.get(0).getPurchasename())){
                            omNoticeI.setCreateBy(wmImNoticeHS.get(0).getPurchasename());
                        }
                        omNoticeI.setU8ReturnCode(imNoticeH.getU8ReturnCode());
                        omNoticeI.setAstreanum(imNoticeH.getAstreanum());


                        List<MdGoodsItem> mdGoodsItems = goodsItemService.lambdaQuery().eq(MdGoodsItem::getSttr1, mdGoods.getShpBianMa()).list();

                        if ("1".equals(omNoticeI.getCheckname())) {
                            if (CollectionUtil.isNotEmpty(mdGoodsItems)) {
                                int index = 0;
                                for (MdGoodsItem mdGoodsItem : mdGoodsItems) {
                                    if(index != 0){
                                        //总箱数
                                        omNoticeI.setTotalqtypercarton("");
                                        omNoticeI.setBaseGoodscount("");
                                        omNoticeI.setTotalvol("");
                                        omNoticeI.setTotalweight("");
                                    }
                                    index++;
                                    WmOmNoticeI omNoticeI1 = new WmOmNoticeI();
                                    BeanUtil.copyProperties(omNoticeI, omNoticeI1);
                                    MdGoods mdGoods2 = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, mdGoodsItem.getSttr2()).one();
                                    omNoticeI1.setGoodsId(mdGoods2.getShpBianMa());
                                    omNoticeI1.setGoodsguige(mdGoods2.getShpGuiGe());
                                    omNoticeI1.setGoodsUnit(mdGoods2.getGoodsUnit());
                                    omNoticeI1.setGoodsName(mdGoods2.getShpMingCheng());
                                    omNoticeI1.setGoodsjianchen(mdGoods2.getYwMingCheng());
                                    omNoticeI1.setStickehstatus(omNoticeI.getId());
                                    omNoticeI1.setShpYanEnse(mdGoods2.getShpYanEnse());
                                    omNoticeI1.setGoodsTypeName(mdGoods2.getChpShuXing());
                                    List<BaGoodsType> baGoodsTypes1 = goodsTypeService.lambdaQuery().eq(BaGoodsType::getGoodsTypeName, mdGoods2.getChpShuXing()).list();
                                    if (CollectionUtil.isNotEmpty(baGoodsTypes1)) {
                                        baGoodsType = baGoodsTypes1.get(0);
                                    }
                                    omNoticeI1.setGoodsTypeEnname(baGoodsType.getGoodsTypeEnname());
                                    List<ConfErp> shfl1 = confErpService.lambdaQuery().eq(ConfErp::getQuery01, "SHFL").eq(ConfErp::getQuery04, mdGoods2.getShpBianMa()).list();
                                    String billingproductname1 = "";
                                    String billingproductunit1 = "";
                                    if(CollectionUtil.isNotEmpty(shfl1)){
                                        billingproductname1 = shfl1.get(0).getQuery08();
                                        billingproductunit1 = shfl1.get(0).getQuery10();
                                    }
                                    omNoticeI1.setBillingproductname(billingproductname1);
                                    omNoticeI1.setBillingproductunit(billingproductunit1);
                                    noticeIS.add(omNoticeI1);
                                }
                            }else {
                                noticeIS.add(omNoticeI);
                            }
                        }else {
                            noticeIS.add(omNoticeI);
                        }
                    }
                }
            }

        }
        omNoticeH.setWmOmNoticeIList(noticeIS);
        return Result.ok(omNoticeH);
    }


    /**
     * plqnid查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "出库管理-通过id查询")
    @ApiOperation(value = "出库管理-通过id查询", notes = "出库管理-通过id查询")
    @GetMapping(value = "/queryplqnById")
    public Result<?> queryplqnById(@RequestParam(name = "id", required = true) String id) {
        WmOmNoticeH wmOmNoticeH = null;
        if(id.contains("JG")){
            QueryWrapper<BusiPrdOrd> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiPrdOrd::getLink02,id);
            queryWrapper.lambda().eq(BusiPrdOrd::getQuery01,"SCDD");
            List<BusiPrdOrd> busiprdord = busiPrdOrdMapper.selectList(queryWrapper);
            if(CollectionUtil.isNotEmpty(busiprdord)){
                QueryWrapper<WmOmNoticeH> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.lambda().eq(WmOmNoticeH::getU8Djcode2,busiprdord.get(0).getQuery04());
                List<WmOmNoticeH> list = wmOmNoticeHService.list(queryWrapper2);
                if(CollectionUtil.isNotEmpty(list)){
                    wmOmNoticeH = list.get(0);
                }else {
                    return Result.error("未找到对应数据");
                }
            }else {
                return Result.error("未找到对应数据");
            }
        }else {
            wmOmNoticeH =  wmOmNoticeHService.getById(id);
        }
        List<WmOmNoticeI> wmOmNoticeIS = wmOmNoticeIService.lambdaQuery().eq(WmOmNoticeI::getOmNoticeId, wmOmNoticeH.getOmNoticeId()).list();
        MdGoods mdGoods = null;
        List<MdGoods> mdGoods3 = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmOmNoticeH.getFinishedGoodsCode()).list();
        if (CollectionUtil.isNotEmpty(mdGoods3)) {
            mdGoods = mdGoods3.get(0);
        }
        for (WmOmNoticeI wmOmNoticeI : wmOmNoticeIS) {
            List<String> goodsQuas = wmOmNoticeIS.stream().map(WmOmNoticeI::getGoodsQua).collect(Collectors.toList());
            List<BigDecimal> goodsqua = new ArrayList<>();
            for (String goodsQua : goodsQuas) {
                BigDecimal bigDecimal = new BigDecimal(goodsQua);
                goodsqua.add(bigDecimal);
            }
//            BigDecimal bigDecimal = goodsqua.stream().max(Comparator.comparing(x -> x)).orElse(null);
            BigDecimal min = goodsqua.stream().min(Comparator.comparing(x -> x)).orElse(null);
            //成品
            wmOmNoticeH.setGoodsId(mdGoods.getShpBianMa());
            wmOmNoticeH.setGoodsName(mdGoods.getShpMingCheng());
            wmOmNoticeH.setClassification(mdGoods.getClassification());
            wmOmNoticeH.setZuixiaoshul(min.toString());
            wmOmNoticeH.setShpmiaoshu(mdGoods.getShpMiaoShu());

            Object id0 = wmOmNoticeH.getOmNoticeId();
            QueryWrapper<WmOmQmI> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("om_notice_id", id0);
            queryWrapper3.eq("goods_id", wmOmNoticeI.getGoodsId());
            queryWrapper3.lambda().groupBy(WmOmQmI::getGoodsBatch);
            queryWrapper3.gt("qm_ok_quat", "0");
            List<WmOmQmI> wmOmQmIList = wmOmQmIMapper.selectList(queryWrapper3);
            List<String> strarr = new ArrayList<>();
            for (WmOmQmI wmOmQmI : wmOmQmIList) {
                strarr.add(wmOmQmI.getGoodsBatch());
            }
            String collect = strarr.stream().collect(Collectors.joining(", "));
            wmOmNoticeI.setGoodsBatch(collect);
        }
        wmOmNoticeH.setWmOmNoticeIList(wmOmNoticeIS);
        if (wmOmNoticeH == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmOmNoticeH);

    }

    /**
     * 国声导出excel
     *
     * @param request
     * @param wmOmNoticeH
     */
    @RequestMapping(value = "/exportXlsgs")
    public ModelAndView exportXlsgs(HttpServletRequest request, WmOmNoticeH wmOmNoticeH) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WmOmNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeH, request.getParameterMap());
        //Step.2 获取导出数据
        List<WmOmNoticeH> queryList = wmOmNoticeHService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WmOmNoticeH> wmOmNoticeHList = new ArrayList<WmOmNoticeH>();
        if (StringUtils.isEmpty(selections)) {
            wmOmNoticeHList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wmOmNoticeHList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<WmOmNoticeHPagegs> pageList = new ArrayList<WmOmNoticeHPagegs>();
        for (WmOmNoticeH main : wmOmNoticeHList) {
            WmOmNoticeHPagegs vo = new WmOmNoticeHPagegs();
            BeanUtils.copyProperties(main, vo);
            List<WmOmNoticeI> wmImNoticeIList = wmOmNoticeIService.selectByMainId(main.getOmNoticeId());
            List<WmOmNoticeIgs> noticeIgsd = new ArrayList<>();
            for (WmOmNoticeI wmOmNoticeI : wmImNoticeIList) {
                WmOmNoticeIgs noticeIgs = new WmOmNoticeIgs();
                BeanUtil.copyProperties(wmOmNoticeI, noticeIgs);
                noticeIgsd.add(noticeIgs);
            }
            vo.setWmOmNoticeIList(noticeIgsd);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "wm_im_notice_h列表");
        mv.addObject(NormalExcelConstants.CLASS, WmOmNoticeHPagegs.class);
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
                List<WmOmNoticeHPagegs> list = ExcelImportUtil.importExcel(file.getInputStream(), WmOmNoticeHPagegs.class, params);
                for (WmOmNoticeHPagegs page : list) {
                    WmOmNoticeH po = new WmOmNoticeH();
                    BeanUtils.copyProperties(page, po);
                    List<WmOmNoticeIgs> wmOmNoticeIList = page.getWmOmNoticeIList();
                    List<WmOmNoticeI> wmOmNoticeILists = new ArrayList<>();
                    for (WmOmNoticeIgs noticeIgs : wmOmNoticeIList) {
                        WmOmNoticeI noticeI = new WmOmNoticeI();
                        BeanUtils.copyProperties(noticeIgs, noticeI);
                        wmOmNoticeILists.add(noticeI);
                    }
                    wmOmNoticeHService.saveMain(po, wmOmNoticeILists);
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




}
