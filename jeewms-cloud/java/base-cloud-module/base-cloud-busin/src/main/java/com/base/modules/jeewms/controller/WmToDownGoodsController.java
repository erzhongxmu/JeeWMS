package com.base.modules.jeewms.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;

import com.base.modules.jeeerp.mapper.BusiPoMapper;
import com.base.modules.jeeerp.vo.BusiQueryPage;
import com.base.modules.jeewms.entity.WmOmNoticeH;
import com.base.modules.jeewms.entity.WmTuopan;
import com.base.modules.jeewms.mapper.WmToDownGoodsMapper;
import com.base.modules.jeewms.service.IWmOmNoticeHService;
import com.base.modules.jeewms.service.IWmTuopanService;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.base.controller.JeecgController;;
import org.jeecg.common.system.util.JwtUtil;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.BaKw;
import com.base.modules.jeewms.entity.WmToDownGoods;
import com.base.modules.jeewms.service.IBaKwService;
import com.base.modules.jeewms.service.IWmToDownGoodsService;
import com.base.modules.jeewms.vo.BatchLoadingReviewVo;
import com.base.modules.jeewms.vo.EditBatchWmToDownGoodsVo;
import com.base.modules.jeewms.vo.OrderPickingVo;
import com.base.modules.jeewms.vo.WmToDownGoodsVo;
import com.base.modules.util.ConstUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 下架明细
 * @Author: base-boot
 * @Date: 2021-05-20
 * @Version: V1.0
 */
@Api(tags = "下架明细")
@RestController
@RequestMapping("/jeewms/wmToDownGoods")
@Slf4j
public class WmToDownGoodsController extends JeecgController<WmToDownGoods, IWmToDownGoodsService> {
    @Autowired
    private IWmToDownGoodsService wmToDownGoodsService;
    @Autowired
    private IBaKwService baKwService;
    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;

    @Autowired
    private IWmTuopanService tuopanService;


    @Autowired
    private WmToDownGoodsMapper wmToDownGoodsMapper;
    /**
     * 分页列表查询
     *
     * @param wmToDownGoods
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "下架明细-分页列表查询")
    @ApiOperation(value = "下架明细-分页列表查询", notes = "下架明细-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WmToDownGoods wmToDownGoods,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<WmToDownGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmToDownGoods, req.getParameterMap());
        Page<WmToDownGoods> page = new Page<WmToDownGoods>(pageNo, pageSize);
        IPage<WmToDownGoods> pageList = wmToDownGoodsService.page(page, queryWrapper);
        pageList.getRecords().forEach(item -> {
            BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, item.getKuWeiBianMa()).one();
            if (baKw != null) {
                item.setAreaCode(baKw.getStoreAreaCode());
                item.setKwName(baKw.getKwName());
            }
        });
        return Result.ok(pageList);
    }


    /**
     * 分页列表查询
     *
     * @param wmToDownGoods
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "按单拣货-分页列表查询")
    @ApiOperation(value = "按单拣货-分页列表查询", notes = "按单拣货-分页列表查询")
    @GetMapping(value = "/orderPickingList")
    public Result<?> orderPickingList(WmToDownGoods wmToDownGoods,
                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      HttpServletRequest req) {
        Page<WmToDownGoods> page = new Page<WmToDownGoods>(pageNo, pageSize);
        HashMap hashMap = new HashMap(1024);
        IPage<WmToDownGoods> iPage = null;
        hashMap.put("goods_id",wmToDownGoods.getGoodsId());
        hashMap.put("order_id",wmToDownGoods.getOrderId());
        hashMap.put("bin_id_to",wmToDownGoods.getBinIdTo());
        hashMap.put("bin_id_from",wmToDownGoods.getBinIdFrom());
        hashMap.put("goods_batch",wmToDownGoods.getGoodsBatch());
        hashMap.put("u8_djcode1",wmToDownGoods.getU8Djcode1());
        hashMap.put("goods_name",wmToDownGoods.getGoodsName());
        String s = wmToDownGoods.getColumn().replaceAll("[A-Z]", "_$0").toLowerCase();
        if("u8_djcode1".equals(s)){
            s = "c.u8_djcode1";
        }else {
            s = "a." + s;
        }
        hashMap.put("column",s);
        hashMap.put("order",wmToDownGoods.getOrder());
        IPage iPage1 = wmToDownGoodsMapper.orderPicking(page, hashMap);
        return Result.ok(iPage1);

//        QueryWrapper<WmToDownGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmToDownGoods, req.getParameterMap());
//        queryWrapper.and(QueryWrapper -> QueryWrapper.ne("remarks","FXKW").or().isNull("remarks"));
//        Page<WmToDownGoods> page = new Page<WmToDownGoods>(pageNo, pageSize);
//        IPage<WmToDownGoods> pageList = wmToDownGoodsService.page(page, queryWrapper);
//        List<WmToDownGoods> records = pageList.getRecords();
//        //List<WmToDownGoods> goodsList = new ArrayList<>();
//        for (WmToDownGoods record : records) {
//            //查询箱子规格、重量
//            List<WmTuopan> wmTuopans = tuopanService.lambdaQuery().eq(WmTuopan::getTinId, record.getBinIdTo()).list();
//            if(CollectionUtil.isNotEmpty(wmTuopans)){
//                record.setTinVolume(wmTuopans.get(0).getTinLength()+"*"+wmTuopans.get(0).getTinWidth()+"*"+wmTuopans.get(0).getTinHigh()+"CM");
//                record.setTinWeight(wmTuopans.get(0).getTinWeight());
//            }
//            List<WmOmNoticeH> list = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, record.getOrderId()).list();
//            if (CollectionUtil.isNotEmpty(list)){
//                WmOmNoticeH wmOmNoticeH = list.get(0);
//                record.setU8Djcode1(wmOmNoticeH.getU8Djcode1());
//                //goodsList.add(record);
//            }
//        }
//        //pageList.setRecords(goodsList);
//        return Result.ok(pageList);
    }

    @AutoLog(value = "按单拣货-编辑")
    @ApiOperation(value = "按单拣货-编辑", notes = "按单拣货-编辑")
    @PutMapping(value = "/orderPickingEdit")
    public Result<?> orderPickingEdit(@RequestBody OrderPickingVo orderPickingVo) {
        Result<?> result = wmToDownGoodsService.orderPickingEdit(orderPickingVo);
        return result;
    }

    /**
     * 添加
     *
     * @param wmToDownGoods
     * @return
     */
    @AutoLog(value = "下架明细-添加")
    @ApiOperation(value = "下架明细-添加", notes = "下架明细-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmToDownGoods wmToDownGoods) {

        wmToDownGoodsService.save(wmToDownGoods);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param wmToDownGoods
     * @return
     */
    @AutoLog(value = "下架明细-编辑")
    @ApiOperation(value = "下架明细-编辑", notes = "下架明细-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmToDownGoods wmToDownGoods) {
        if (!wmToDownGoods.getBinIdTo().equals(wmToDownGoods.getBinIdFrom())){
            List<WmTuopan> wmTuopans = tuopanService.lambdaQuery().eq(WmTuopan::getTinId, wmToDownGoods.getBinIdTo()).list();
            if (CollectionUtil.isNotEmpty(wmTuopans)){
                WmTuopan tuopan = wmTuopans.get(0);
                if (StringUtils.isNotEmpty(tuopan.getIsfails())  && "1".equals(tuopan.getIsfails())){
                    throw  new JeecgBootException("该箱已出，请重新输入"+tuopan.getTinId());
                }
                tuopan.setIsfails("1");
                tuopanService.updateById(tuopan);
            }

            List<WmTuopan> wmTuopans1 = tuopanService.lambdaQuery().eq(WmTuopan::getTinId, wmToDownGoods.getBinIdFrom()).list();
            if (CollectionUtil.isNotEmpty(wmTuopans1)){
                WmTuopan tuopan1 = wmTuopans1.get(0);
                tuopan1.setIsfails("");
                tuopanService.updateById(tuopan1);
            }
        }
        wmToDownGoods.setBinIdFrom(wmToDownGoods.getBinIdTo());
        wmToDownGoodsService.updateById(wmToDownGoods);
        return Result.ok("编辑成功!");
    }

    /**
     * 批量编辑
     *
     * @param wmToDownGoods
     * @return
     */
    @AutoLog(value = "批量编辑")
    @ApiOperation(value = "批量编辑", notes = "批量编辑")
    @PostMapping(value = "/updateBatch")
    public Result<?> updateBatch(@RequestBody List<WmToDownGoods> wmToDownGoods) {
        wmToDownGoodsService.updateBatchById(wmToDownGoods);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "下架明细-通过id删除")
    @ApiOperation(value = "下架明细-通过id删除", notes = "下架明细-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wmToDownGoodsService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "下架明细-批量删除")
    @ApiOperation(value = "下架明细-批量删除", notes = "下架明细-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wmToDownGoodsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "下架明细-通过id查询")
    @ApiOperation(value = "下架明细-通过id查询", notes = "下架明细-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WmToDownGoods wmToDownGoods = wmToDownGoodsService.getById(id);
        if (wmToDownGoods == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmToDownGoods);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wmToDownGoods
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmToDownGoods wmToDownGoods) {
        //return super.exportXls(request, wmToDownGoods, WmToDownGoods.class, "下架明细");
        return super.exportXls(request, wmToDownGoods, WmToDownGoods.class, "拣货单");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wmToDownGoods
     */
    @RequestMapping(value = "/exportXlsTwo")
    public ModelAndView exportXlsTwo(HttpServletRequest request, WmToDownGoods wmToDownGoods) {
        // Step.1 组装查询条件
        QueryWrapper<WmToDownGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmToDownGoods, request.getParameterMap());
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        LoginUser sysUser = JwtUtil.getLoginUser();

        // Step.2 获取导出数据
        List<WmToDownGoods> pageList = wmToDownGoodsService.list(queryWrapper);
        List<WmToDownGoods> exportList = null;
        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }
        List<WmToDownGoodsVo> list = new ArrayList<WmToDownGoodsVo>();
        for (WmToDownGoods main : exportList) {
            WmToDownGoodsVo vo = new WmToDownGoodsVo();
            BeanUtils.copyProperties(main, vo);
            list.add(vo);
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "下架调整"); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, WmToDownGoodsVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("下架调整" + "报表", "导出人:" + sysUser.getRealname(), "下架调整"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
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
        return super.importExcel(request, response, WmToDownGoods.class);
    }


    /**
     * @param list
     * @return
     * @Describe 下架明细-批量更改
     * @Author zly
     * @Create Date 2021/5/21
     */
    @AutoLog(value = "下架明细-批量更改")
    @ApiOperation(value = "下架明细-批量更改", notes = "下架明细-批量更改")
    @PutMapping(value = "editBatch")
    public Result<?> editBatch(@RequestBody List<EditBatchWmToDownGoodsVo> list) {
        Result<?> result = wmToDownGoodsService.editBatch(list);
        return result;
    }


    /**
     * @return
     * @Describe 装车复核页面查询
     * @Author zly
     * @Create Date 2021/5/21
     */
    @AutoLog(value = "下架明细-装车复核页面查询")
    @ApiOperation(value = "下架明细-装车复核页面查询", notes = "下架明细-装车复核页面查询")
    @GetMapping(value = "/datagridzzfh")
    public Result<?> datagridzzfh(WmToDownGoods wmToDownGoods,
                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        QueryWrapper<WmToDownGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmToDownGoods, req.getParameterMap());
        queryWrapper.eq("down_sta", ConstUtil.wm_sta8);
        Page<WmToDownGoods> page = new Page<WmToDownGoods>(pageNo, pageSize);
        IPage<WmToDownGoods> pageList = wmToDownGoodsService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * @param ids
     * @return
     * @Describe 下架明细-装车复核-一键复核
     * @Author zly
     * @Create Date 2021/5/21
     */
    @AutoLog(value = "下架功能-装车复核-一键复核")
    @ApiOperation(value = "下架功能-装车复核-一键复核", notes = "下架功能-装车复核-一键复核")
    @PutMapping(value = "dofubatch")
    public Result<?> dofubatch(@RequestBody List<String> ids) {
        Result<?> result = wmToDownGoodsService.dofubatch(ids,0);
        return result;
    }

    /**
     * @param vo
     * @return
     * @Describe 下架明细-装车复核-批量复核
     * @Author zly
     * @Create Date 2021/5/21
     */
    @AutoLog(value = "下架功能-装车复核-批量复核")
    @ApiOperation(value = "下架功能-装车复核-批量复核", notes = "下架功能-装车复核-批量复核")
    @PutMapping(value = "batchLoadingReview")
    public Result<?> batchLoadingReview(@RequestBody List<BatchLoadingReviewVo> vo) {
        Result<?> result = wmToDownGoodsService.batchLoadingReview(vo);
        return result;
    }

    @AutoLog(value = "出货详情-通过快递面单号查询")
    @ApiOperation(value="出货详情-通过快递面单号查询", notes="出货详情-通过快递面单号查询")
    @GetMapping(value = "/queryWmOmNoticeIByMainNo")
    public Result<?> queryWmOmNoticeIByMainNo(@RequestParam(name="id",required=true) String id) {
        Result<?> result = wmToDownGoodsService.queryWmOmNoticeIByMainNo(id);
        return result;
    }

    @AutoLog(value = "扫码复核")
    @ApiOperation(value="扫码复核", notes="扫码复核")
    @GetMapping(value = "/scanCodeReview")
    public Result<?> scanCodeReview(@RequestParam(name="id",required=true) String id,
                                    @RequestParam(name="barCode",required=true) String barCode) {
        Result<?> result = wmToDownGoodsService.scanCodeReview(id,barCode);
        return result;
    }

    @AutoLog(value = "完成复核")
    @ApiOperation(value="完成复核", notes="完成复核")
    @GetMapping(value = "/finishReview")
    public Result<?> finishReview(@RequestParam(name="id",required=true) String id,
                                  @RequestParam(name="packingNo",required=true) String packingNo,
                                  @RequestParam(name="childWaybillCount",required=true) String childWaybillCount) {
        Result<?> result = wmToDownGoodsService.finishReview(id,packingNo,childWaybillCount);
        return result;
    }

    @AutoLog(value = "重新复核")
    @ApiOperation(value="重新复核", notes="重新复核")
    @GetMapping(value = "/afreshReview")
    public Result<?> afreshReview(@RequestParam(name="id",required=true) String id) {
        Result<?> result = wmToDownGoodsService.afreshReview(id);
        return result;
    }



}
