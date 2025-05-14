package com.base.modules.jeewms.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.RedisUtil;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.vo.EditBatchWmQmIVo;
import com.base.modules.jeewms.vo.InsertAssignToBatchVo;
import com.base.modules.jeewms.vo.WavebatchVo;
import com.base.modules.util.ConstUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
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
import java.util.stream.Collectors;

;

/**
 * @Description: 下架任务
 * @Author: base-boot
 * @Date: 2021-05-20
 * @Version: V1.0
 */
@Api(tags = "下架任务")
@RestController
@RequestMapping("/jeewms/wmOmQmI")
@Slf4j
public class WmOmQmIController extends JeecgController<WmOmQmI, IWmOmQmIService> {

    @Autowired
    private IWmOmQmIService wmOmQmIService;

    @Autowired
    private IWmToDownGoodsService wmToDownGoodsService;
    @Autowired
    private IUserToGoodsService userToGoodsService;
    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private IWmTuopanService tuopanService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;

    @Value("${jeecg.pritIp}")
    private String pritIp;

    @Value("${jeecg.pritIp2}")
    private String pritIp2;

    @Value("${jeecg.pritIp3}")
    private String pritIp3;


    /*---------------------------------主表处理-begin-------------------------------------*/

    /**
     * 分页列表查询
     *
     * @param wmOmQmI
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "下架任务-分页列表查询")
    @ApiOperation(value = "下架任务-分页列表查询", notes = "下架任务-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WmOmQmI wmOmQmI,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
//        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        List<MdGoods> goodsList = userToGoodsService.query(loginUser.getUsername());
//        if (!goodsList.isEmpty()) {
            QueryWrapper<WmOmQmI> queryWrapper = QueryGenerator.initQueryWrapper(wmOmQmI, req.getParameterMap());
            queryWrapper.in("bin_sta", "N","H");//未下架
            Page<WmOmQmI> page = new Page<WmOmQmI>(pageNo, pageSize);
            return Result.ok(wmOmQmIService.page(page, queryWrapper));
//        }
//        return Result.ok(new Page<>());
    }
    /*---------------------------------确认任务-begin-------------------------------------*/

    /**
     * 商品下拉列表查询
     *
     * @return
     */
    @ApiOperation(value = "商品下拉列表查询", notes = "商品下拉列表查询")
    @GetMapping(value = "/getGoodsSelectList")
    public Result<?> getGoodsSelectList() {
        QueryWrapper<WmOmQmI> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("bin_sta", "N");
        queryWrapper.select("distinct goods_id ");
        return Result.ok(wmOmQmIService.list(queryWrapper));
    }


    /**
     * 确认任务分页列表查询
     *
     * @param wmOmQmI
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "确认下架任务分页列表查询")
    @ApiOperation(value = "确认下架任务分页列表查询", notes = "确认下架任务分页列表查询")
    @GetMapping(value = "/datagridassign")
    public Result<?> datagridassign(WmOmQmI wmOmQmI,
                                    @RequestParam(name="order_type",required=false) String order_type,
                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                    HttpServletRequest req) {
        QueryWrapper<WmOmQmI> queryWrapper = QueryGenerator.initQueryWrapper(wmOmQmI, req.getParameterMap());
        List<String> omNoticeIdList = wmOmQmIService.queryOmNoticeIdList();
        if("一单一品".equals(order_type)){
            queryWrapper.notIn("om_notice_id", omNoticeIdList);
        }else if("一单多品".equals(order_type)){
            queryWrapper.in("om_notice_id", omNoticeIdList);
        }
        Page<WmOmQmI> page = new Page<WmOmQmI>(pageNo, pageSize);
        IPage<WmOmQmI> pageList = wmOmQmIService.page(page, queryWrapper);
        pageList.getRecords().forEach(item -> {
            MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, item.getGoodsId()).one();
            if (mdGoods != null) {
                item.setGoodsTypeId(mdGoods.getClassification());
            }


            List<WmTuopan> list = tuopanService.lambdaQuery().eq(WmTuopan::getTinId, item.getTinId()).list();
            if (CollectionUtil.isNotEmpty(list)) {
                WmTuopan tuopan = new WmTuopan();
                for (WmTuopan wmTuopan : list) {
                    if (StringUtils.isNotEmpty(wmTuopan.getTinWeight())){
                        tuopan = wmTuopan;
                    }
                }
                if (tuopan!=null){
                    BigDecimal div = NumberUtil.div(tuopan.getTinVolume(), String.valueOf("1000000"));
                    item.setTinVolume(Convert.toDouble(div).toString());
                    item.setTinWeight(tuopan.getTinWeight());
                }
            }
        });
        return Result.ok(pageList);
    }

    /**
     * @param ids
     * @return
     * @Describe 批量确认任务
     * @Author zly
     * @Create Date 2021/5/24
     */
    @AutoLog(value = "批量确认任务")
    @ApiOperation(value = "批量确认任务", notes = "批量确认任务")
    @PutMapping(value = "doassignbatch")
    public Result<?> doassignbatch(@RequestBody List<String> ids) {
        Result<?> result = wmOmQmIService.doassignbatch(ids);
        return result;
    }

    /**
     * @param vo
     * @return
     * @Describe 波次生成和波次生成到指定容器
     * @Author zly
     * @Create Date 2021/5/24
     */
    @AutoLog(value = "波次生成和波次生成到指定容器")
    @ApiOperation(value = "波次生成和波次生成到指定容器", notes = "波次生成和波次生成到指定容器")
    @PutMapping(value = "wavebatch")
    public Result<?> wavebatch(@RequestBody WavebatchVo vo) {
        Result<?> result = wmOmQmIService.wavebatch(vo);
        return result;
    }

    /**
     * @param vo
     * @return
     * @Describe 批量设置接收人
     * @Author zly
     * @Create Date 2021/5/24
     */
    @AutoLog(value = "批量设置接收人")
    @ApiOperation(value = "批量设置接收人", notes = "批量设置接收人")
    @PutMapping(value = "insertAssignToBatch")
    public Result<?> insertAssignToBatch(@RequestBody InsertAssignToBatchVo vo) {
        Result<?> result = wmOmQmIService.insertAssignToBatch(vo);
        return result;
    }

    /*---------------------------------确认任务-begin-------------------------------------*/

    /**
     * 添加
     *
     * @param wmOmQmI
     * @return
     */
    @AutoLog(value = "下架任务-添加")
    @ApiOperation(value = "下架任务-添加", notes = "下架任务-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmOmQmI wmOmQmI) {
        wmOmQmIService.save(wmOmQmI);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param wmOmQmI
     * @return
     */
    @AutoLog(value = "下架任务-编辑")
    @ApiOperation(value = "下架任务-编辑", notes = "下架任务-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmOmQmI wmOmQmI) {
        wmOmQmIService.updateById(wmOmQmI);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "下架任务-通过id删除")
    @ApiOperation(value = "下架任务-通过id删除", notes = "下架任务-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wmOmQmIService.delMain(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "下架任务-批量删除")
    @ApiOperation(value = "下架任务-批量删除", notes = "下架任务-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wmOmQmIService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "下架任务-通过id查询")
    @ApiOperation(value = "下架任务-通过id查询", notes = "下架任务-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WmOmQmI wmOmQmI = wmOmQmIService.getById(id);
        if (wmOmQmI == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmOmQmI);
    }

    /**
     * 导出
     *
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmOmQmI wmOmQmI) {
        //return super.exportXls(request, wmOmQmI, WmOmQmI.class, "下架任务");
        return super.exportXls(request, wmOmQmI, WmOmQmI.class, "任务");
    }

    /**
     * 导入
     *
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WmOmQmI.class);
    }
    /*---------------------------------主表处理-end-------------------------------------*/


    /*--------------------------------子表处理-下架明细-begin----------------------------------------------*/

    /**
     * 通过主表ID查询
     *
     * @return
     */
    @AutoLog(value = "下架明细-通过主表ID查询")
    @ApiOperation(value = "下架明细-通过主表ID查询", notes = "下架明细-通过主表ID查询")
    @GetMapping(value = "/listWmToDownGoodsByMainId")
    public Result<?> listWmToDownGoodsByMainId(WmToDownGoods wmToDownGoods,
                                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                               HttpServletRequest req) {
        QueryWrapper<WmToDownGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmToDownGoods, req.getParameterMap());
        Page<WmToDownGoods> page = new Page<WmToDownGoods>(pageNo, pageSize);
        IPage<WmToDownGoods> pageList = wmToDownGoodsService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param wmToDownGoods
     * @return
     */
    @AutoLog(value = "下架明细-添加")
    @ApiOperation(value = "下架明细-添加", notes = "下架明细-添加")
    @PostMapping(value = "/addWmToDownGoods")
    public Result<?> addWmToDownGoods(@RequestBody WmToDownGoods wmToDownGoods) {
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
    @PutMapping(value = "/editWmToDownGoods")
    public Result<?> editWmToDownGoods(@RequestBody WmToDownGoods wmToDownGoods) {
        wmToDownGoodsService.updateById(wmToDownGoods);
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
    @DeleteMapping(value = "/deleteWmToDownGoods")
    public Result<?> deleteWmToDownGoods(@RequestParam(name = "id", required = true) String id) {
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
    @DeleteMapping(value = "/deleteBatchWmToDownGoods")
    public Result<?> deleteBatchWmToDownGoods(@RequestParam(name = "ids", required = true) String ids) {
        this.wmToDownGoodsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 导出
     *
     * @return
     */
    @RequestMapping(value = "/exportWmToDownGoods")
    public ModelAndView exportWmToDownGoods(HttpServletRequest request, WmToDownGoods wmToDownGoods) {
        // Step.1 组装查询条件
        QueryWrapper<WmToDownGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmToDownGoods, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

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

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "下架明细"); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, WmToDownGoods.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("下架明细报表", "导出人:" + sysUser.getRealname(), "下架明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 导入
     *
     * @return
     */
    @RequestMapping(value = "/importWmToDownGoods/{mainId}")
    public Result<?> importWmToDownGoods(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<WmToDownGoods> list = ExcelImportUtil.importExcel(file.getInputStream(), WmToDownGoods.class, params);
                for (WmToDownGoods temp : list) {
                    temp.setOrderIdI(mainId);
                }
                long start = System.currentTimeMillis();
                wmToDownGoodsService.saveBatch(list);
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

    /*--------------------------------子表处理-下架明细-end----------------------------------------------*/

    /**
     * @param ids
     * @return
     * @Describe 下架功能
     * @Author zly
     * @Create Date 2021/5/20
     */
    @AutoLog(value = "双维下架")
    @ApiOperation(value = "下架功能-波次批量下架", notes = "下架功能-批量下架")
    @PutMapping(value = "dotowavedown")
    public Result<?> dotowavedown(@RequestBody List<String> ids) {
        Result<?> result = wmOmQmIService.dotowavedown(ids,"",ConstUtil.wm_h,null);
        return result;
    }

    /**
     * @param wmOmQmIList
     * @return
     * @Describe 下架功能
     * @Author zly
     * @Create Date 2021/5/20
     */
    @AutoLog(value = "下架功能-批量下架")
    @ApiOperation(value = "下架功能-批量下架", notes = "下架功能-批量下架")
    @PostMapping(value = "pdaDotowavedown")
    public Result<?> pdaDotowavedown(@RequestBody List<WmOmQmI> wmOmQmIList) {
        //对接双维接口
        Result<?> result = wmOmQmIService.pdaDotowavedown1(wmOmQmIList, ConstUtil.wm_h);
        return result;
    }

    /**
     * @param ids
     * @return
     * @Describe 下架功能
     * @Author zly
     * @Create Date 2021/5/20
     */
    @AutoLog(value = "双维批量下架")
    @ApiOperation(value = "下架功能-批量下架", notes = "下架功能-批量下架")
    @PutMapping(value = "dotodown")
    public Result<?> dotodown(@RequestBody List<String> ids) {
        //对接双维接口
        Result<?> result = wmOmQmIService.dotowavedown(ids,"", ConstUtil.wm_y,null);
        return result;
    }


    /**
     * @param ids
     * @return
     * @Describe 下架功能
     * @Author zly
     * @Create Date 2021/5/20
     */
    @AutoLog(value = "下架功能-批量下架")
    @ApiOperation(value = "下架功能-批量下架", notes = "下架功能-批量下架")
    @PutMapping(value = "dotodown1")
    public Result<?> dotodown1(@RequestBody List<String> ids) {
        Result<?> result = wmOmQmIService.dotowavedown(ids,"", ConstUtil.wm_y,null);
        return result;
    }

    /**
     * @param list
     * @return
     * @Describe 下架-批量更改
     * @Author zly
     * @Create Date 2021/5/20
     */
    @AutoLog(value = "下架功能-批量更改")
    @ApiOperation(value = "下架功能-批量更改", notes = "下架功能-批量更改")
    @PutMapping(value = "editBatch")
    public Result<?> editBatch(@RequestBody List<EditBatchWmQmIVo> list) {
        Result<?> result = wmOmQmIService.editBatch(list);
        return result;
    }


    @AutoLog(value = "plqn标签打印")
    @ApiOperation(value = "标签打印", notes = "标签打印")
    @GetMapping(value = "/labelPrinting")
    public Result<?> labelPrintings(@RequestParam(name = "ids", required = true) String ids, HttpServletResponse response) throws InterruptedException {
        synchronized (this){
            String[] splits = ids.split(",");
            for (String id : splits) {
                String tinids = "T0000";
                String tinids1 = "T0000";
                WmOmQmI omQmI = wmOmQmIService.getById(id);
                WmOmNoticeH wmOmNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, omQmI.getOmNoticeId()).one();
                List<WmOmQmI> wmOmQmIS1 = wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, wmOmNoticeHS.getOmNoticeId()).orderByAsc(WmOmQmI::getCreateTime).list();
                List<WmOmNoticeH> omNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getU8Djcode1, wmOmNoticeHS.getU8Djcode1()).list();
                List<WmOmQmI> omQmIS = new ArrayList<>();
                for (WmOmNoticeH wmOmNoticeH : omNoticeHS) {
                    List<WmOmQmI> wmOmQmIS = wmOmQmIService.lambdaQuery()
                            .eq(WmOmQmI::getOmNoticeId, wmOmNoticeH.getOmNoticeId())
                            .orderByAsc(WmOmQmI::getCreateTime)
                            .list();
                    if (CollectionUtil.isNotEmpty(wmOmQmIS)) {
                        for (WmOmQmI omQmIs : wmOmQmIS) {
                            Integer integer = Convert.toInt(tinids.substring(tinids.length() - 4, tinids.length())) + 1;
                            tinids = "T" + String.format("%04d", integer);
                            if (StringUtils.isEmpty(omQmIs.getFirstRq())){
                                omQmIs.setFirstRq(tinids);
                                wmOmQmIService.updateById(omQmIs);
                            }
                        }
                    }
                }
                for (WmOmQmI omQmIs : wmOmQmIS1) {
                    Integer integer = Convert.toInt(tinids1.substring(tinids1.length() - 4, tinids1.length())) + 1;
                    tinids1 = "T" + String.format("%04d", integer);
                    if (StringUtils.isEmpty(omQmIs.getSecondRq())){
                        omQmIs.setSecondRq(tinids1);
                        wmOmQmIService.updateById(omQmIs);
                    }
                }
                omQmIS.addAll(wmOmQmIS1);
                int size = omQmIS.size();
                int lengths = 1;
                String omid = (String) redisUtil.get(omQmI.getOmNoticeId() + "-" + omQmI.getGoodsId());

                if (StringUtils.isNotEmpty(omid)){
                    String[] split = omid.split(",");
                    List<String> list = Arrays.asList(split);
                    int index = -1;
                    index = list.indexOf(id);
                    if(index == -1){
                        lengths = split.length + 1;
                    }else {
                        lengths = index + 1;
                    }
                    redisUtil.set(omQmI.getOmNoticeId()+"-"+omQmI.getGoodsId(),omid+","+id);
                }else{
                    redisUtil.set(omQmI.getOmNoticeId()+"-"+omQmI.getGoodsId(),id);
                }
                Thread.sleep(1);
                WmOmQmI omQmI2 = wmOmQmIService.getById(id);
                Integer integer = Convert.toInt(omQmI2.getSecondRq().substring(omQmI2.getSecondRq().length() - 4, tinids1.length()));
                Map map1 = new LinkedHashMap();
                map1.put("data01", wmOmNoticeHS.getU8Djcode1());
                map1.put("data02", omQmI2.getFirstRq());
                map1.put("data03", integer+"/"+size);
                System.out.println(omQmI.getFirstRq()+"--------");
                JSONObject jsonObj = new JSONObject(map1);
                String Json = jsonObj.toString();
                String post = HttpUtil.post(pritIp3+"util/uwms/client/print/chuku/admin", Json);
                if (StringUtils.isNotEmpty(post)){
                    String post1 = HttpUtil.post(pritIp3+"util/uwms/client/print/chuku/admin", Json);
                }
            }
            return Result.OK("打印成功");
        }
    }





  /*  @AutoLog(value = "plqn标签打印")
    @ApiOperation(value = "标签打印", notes = "标签打印")
    @GetMapping(value = "/labelPrinting3")
    public Result<?> labelPrintings3 (@RequestParam(name = "id", required = true) String id,
                                     @RequestParam(name = "type", required = true) String type,
                                     @RequestParam(name = "pageType", required = true) String pageType,
                                    HttpServletResponse response) throws InterruptedException {
        synchronized (this){
            List<HashMap<String,String>> list = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String[] ids = id.split(",");
            for (String s : ids) {
                HashMap<String,String> map1 = new HashMap<>(1024);
                if(pageType.equals("XJ")){
                    String tinids = "T0000";
                    String tinids1 = "T0000";
                    WmOmQmI omQmI = wmOmQmIService.getById(s);
                    WmOmNoticeH wmOmNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, omQmI.getOmNoticeId()).one();
                    List<WmOmQmI> wmOmQmIS1 = wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, wmOmNoticeHS.getOmNoticeId()).eq(WmOmQmI::getBinSta, 'N').eq(WmOmQmI::getGoodsId,omQmI.getGoodsId()).orderByAsc(WmOmQmI::getUpdateTime).list();
                    List<WmOmQmI> wmOmQmIS2 = wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, wmOmNoticeHS.getOmNoticeId()).eq(WmOmQmI::getGoodsId,omQmI.getGoodsId()).orderByAsc(WmOmQmI::getUpdateTime).list();
                    List<WmOmNoticeH> omNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getU8Djcode1, wmOmNoticeHS.getU8Djcode1()).list();
                    List<WmOmQmI> omQmIS = new ArrayList<>();
                    for (WmOmNoticeH wmOmNoticeH : omNoticeHS) {
                        List<WmOmQmI> wmOmQmIS = wmOmQmIService.lambdaQuery()
                                .eq(WmOmQmI::getOmNoticeId, wmOmNoticeH.getOmNoticeId())
                                .eq(WmOmQmI::getBinSta, 'N')
                                .orderByAsc(WmOmQmI::getUpdateTime)
                                .list();
                        if (CollectionUtil.isNotEmpty(wmOmQmIS)) {
                            for (WmOmQmI omQmIs : wmOmQmIS) {
                                Integer integer = Convert.toInt(tinids.substring(tinids.length() - 4, tinids.length())) + 1;
                                tinids = "T" + String.format("%04d", integer);
                                if (StringUtils.isEmpty(omQmIs.getFirstRq())){
                                    omQmIs.setFirstRq(tinids);
                                    wmOmQmIService.updateById(omQmIs);
                                }
                            }
                        }
                    }
                    for (WmOmQmI omQmIs : wmOmQmIS1) {
                        Integer integer = Convert.toInt(tinids1.substring(tinids1.length() - 4, tinids1.length())) + 1;
                        tinids1 = "T" + String.format("%04d", integer);
                        if (StringUtils.isEmpty(omQmIs.getSecondRq())){
                            omQmIs.setSecondRq(tinids1);
                            wmOmQmIService.updateById(omQmIs);
                        }
                    }
                    omQmIS.addAll(wmOmQmIS1);
                    int size = wmOmQmIS2.size();
                    int lengths = 1;
                    String omid = (String) redisUtil.get(omQmI.getOmNoticeId() + "-" + omQmI.getGoodsId());

                    if (StringUtils.isNotEmpty(omid)){
                        String[] split = omid.split(",");
                        List<String> list2 = Arrays.asList(split);
                        int index = -1;
                        index = list2.indexOf(s);
                        if(index == -1){
                            lengths = split.length + 1;
                        }else {
                            lengths = index + 1;
                        }
                        redisUtil.set(omQmI.getOmNoticeId()+"-"+omQmI.getGoodsId(),omid+","+s);
                    }else{
                        redisUtil.set(omQmI.getOmNoticeId()+"-"+omQmI.getGoodsId(),s);
                    }
                    Thread.sleep(1);
                    WmOmQmI omQmI2 = wmOmQmIService.getById(s);
                    Integer integer = Convert.toInt(omQmI2.getSecondRq().substring(omQmI2.getSecondRq().length() - 4, tinids1.length()));
                    map1.put("data01", wmOmNoticeHS.getU8Djcode1());
                    map1.put("data02", omQmI2.getFirstRq());
                    map1.put("data03", integer+"/"+size);
                    map1.put("data04",sdf.format(omQmI2.getCreateTime()));
                    map1.put("id",s);
                }else if(pageType.equals("XJTZ")){
                    WmToDownGoods wmToDownGoods = wmToDownGoodsService.getById(s);
                    String tinids = "T0000";
                    String tinids1 = "T0000";
                    WmOmQmI omQmI = wmOmQmIService.getById(wmToDownGoods.getOrderIdI());
                    WmOmNoticeH wmOmNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, omQmI.getOmNoticeId()).one();
                    List<WmOmQmI> wmOmQmIS1 = wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, wmOmNoticeHS.getOmNoticeId()).orderByAsc(WmOmQmI::getCreateTime).list();
                    List<WmOmNoticeH> omNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getU8Djcode1, wmOmNoticeHS.getU8Djcode1()).list();
                    List<WmOmQmI> omQmIS = new ArrayList<>();
                    for (WmOmNoticeH wmOmNoticeH : omNoticeHS) {
                        List<WmOmQmI> wmOmQmIS = wmOmQmIService.lambdaQuery()
                                .eq(WmOmQmI::getOmNoticeId, wmOmNoticeH.getOmNoticeId())
                                .orderByAsc(WmOmQmI::getCreateTime)
                                .list();
                        if (CollectionUtil.isNotEmpty(wmOmQmIS)) {
                            for (WmOmQmI omQmIs : wmOmQmIS) {
                                Integer integer = Convert.toInt(tinids.substring(tinids.length() - 4, tinids.length())) + 1;
                                tinids = "T" + String.format("%04d", integer);
                                if (StringUtils.isEmpty(omQmIs.getFirstRq())){
                                    omQmIs.setFirstRq(tinids);
                                    wmOmQmIService.updateById(omQmIs);
                                }
                            }
                        }
                    }
                    for (WmOmQmI omQmIs : wmOmQmIS1) {
                        Integer integer = Convert.toInt(tinids1.substring(tinids1.length() - 4, tinids1.length())) + 1;
                        tinids1 = "T" + String.format("%04d", integer);
                        if (StringUtils.isEmpty(omQmIs.getSecondRq())){
                            omQmIs.setSecondRq(tinids1);
                            wmOmQmIService.updateById(omQmIs);
                        }
                    }
                    omQmIS.addAll(wmOmQmIS1);
                    int size = omQmIS.size();
                    int lengths = 1;
//            String omid = (String) redisUtil.get(omQmI.getOmNoticeId() + "-" + omQmI.getGoodsId());
//            if (StringUtils.isNotEmpty(omid)){
//                String[] split = omid.split(",");
//                List<String> list = Arrays.asList(split);
//                int index = -1;
//                index = list.indexOf(omQmI.getId());
//                if(index == -1){
//                    lengths = split.length + 1;
//                }else {
//                    lengths = index + 1;
//                }
//                redisUtil.set(omQmI.getOmNoticeId()+"-"+omQmI.getGoodsId(),omid+","+omQmI.getId());
//            }else{
//                redisUtil.set(omQmI.getOmNoticeId()+"-"+omQmI.getGoodsId(),omQmI.getId());
//            }
                    Thread.sleep(1);

                    WmOmQmI omQmI2 = new WmOmQmI();
                    if (StringUtils.isEmpty(wmToDownGoods.getOrderIdI())){
                        WmToDownGoods goodsServiceById = wmToDownGoodsService.getById(wmToDownGoods.getParenId());
                        omQmI2 = wmOmQmIService.getById(goodsServiceById.getOrderIdI());
                    }else {
                        omQmI2 = wmOmQmIService.getById(wmToDownGoods.getOrderIdI());
                    }
                    Integer integer = Convert.toInt(omQmI2.getSecondRq().substring(omQmI2.getSecondRq().length() - 4, tinids1.length()));
                    map1.put("data01", wmOmNoticeHS.getU8Djcode1());
                    map1.put("data02", omQmI2.getFirstRq());
                    map1.put("data03", integer+"/"+size);
                    map1.put("data04",sdf.format(omQmI2.getCreateTime()));
                    map1.put("id",s);
                }

                list.add(map1);
            }
            return Result.OK(list);
        }
    }*/


    @AutoLog(value = "plqn标签打印")
    @ApiOperation(value = "标签打印", notes = "标签打印")
    @PostMapping(value = "/labelPrinting3")
    public Result<?> labelPrintings3 (@RequestParam(name = "id", required = true) String id,
                                      @RequestParam(name = "type", required = true) String type,
                                      @RequestParam(name = "pageType", required = true) String pageType,
                                      HttpServletResponse response) throws InterruptedException {
        synchronized (this){
            List<HashMap<String,String>> list = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String[] ids = id.split(",");
            for (String s : ids) {
                HashMap<String,String> map1 = new HashMap<>(1024);
                if("XJ".equals(pageType)){
                    WmOmQmI omQmI = wmOmQmIService.getById(s);
                    WmOmNoticeH wmOmNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, omQmI.getOmNoticeId()).one();
                    WmOmQmI omQmI2 = wmOmQmIService.getById(s);
                    map1.put("data01", wmOmNoticeHS.getU8Djcode1());
                    map1.put("data02", omQmI2.getFirstRq());
                    map1.put("data03", omQmI2.getSecondRq());
                    map1.put("data04",sdf.format(omQmI2.getCreateTime()));
                    map1.put("id",s);
                }else if("XJTZ".equals(pageType)){
                    WmToDownGoods wmToDownGoods = wmToDownGoodsService.getById(s);
                    WmOmQmI omQmI = wmOmQmIService.getById(wmToDownGoods.getOrderIdI());
                    if(omQmI==null){

                    }
                    WmOmNoticeH wmOmNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, omQmI.getOmNoticeId()).one();
                    WmOmQmI omQmI2 = new WmOmQmI();
                    if (StringUtils.isEmpty(wmToDownGoods.getOrderIdI())){
                        WmToDownGoods goodsServiceById = wmToDownGoodsService.getById(wmToDownGoods.getParenId());
                        omQmI2 = wmOmQmIService.getById(goodsServiceById.getOrderIdI());
                    }else {
                        omQmI2 = wmOmQmIService.getById(wmToDownGoods.getOrderIdI());
                    }
                    map1.put("data01", wmOmNoticeHS.getU8Djcode1());
                    map1.put("data02", omQmI2.getFirstRq());
                    map1.put("data03", omQmI2.getSecondRq());
                    map1.put("data04",sdf.format(omQmI2.getCreateTime()));
                    map1.put("id",s);
                }

                list.add(map1);
            }
            return Result.OK(list);
        }
    }



    @AutoLog(value = "plqn标签打印")
    @ApiOperation(value = "标签打印", notes = "标签打印")
    @GetMapping(value = "/labelPrinting2")
    public Result<?> labelPrintings2(@RequestParam(name = "ids", required = true) String ids, HttpServletResponse response) throws InterruptedException {
        synchronized (this){
            String[] splits = ids.split(",");
            for (String id : splits) {
                WmToDownGoods wmToDownGoods = wmToDownGoodsService.getById(id);
                String tinids = "T0000";
                String tinids1 = "T0000";
                WmOmQmI omQmI = wmOmQmIService.getById(wmToDownGoods.getOrderIdI());
                WmOmNoticeH wmOmNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, omQmI.getOmNoticeId()).one();
                List<WmOmQmI> wmOmQmIS1 = wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, wmOmNoticeHS.getOmNoticeId()).orderByAsc(WmOmQmI::getCreateTime).list();
                List<WmOmNoticeH> omNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getU8Djcode1, wmOmNoticeHS.getU8Djcode1()).list();
                List<WmOmQmI> omQmIS = new ArrayList<>();
                for (WmOmNoticeH wmOmNoticeH : omNoticeHS) {
                    List<WmOmQmI> wmOmQmIS = wmOmQmIService.lambdaQuery()
                            .eq(WmOmQmI::getOmNoticeId, wmOmNoticeH.getOmNoticeId())
                            .orderByAsc(WmOmQmI::getCreateTime)
                            .list();
                    if (CollectionUtil.isNotEmpty(wmOmQmIS)) {
                        for (WmOmQmI omQmIs : wmOmQmIS) {
                            Integer integer = Convert.toInt(tinids.substring(tinids.length() - 4, tinids.length())) + 1;
                            tinids = "T" + String.format("%04d", integer);
                            if (StringUtils.isEmpty(omQmIs.getFirstRq())){
                                omQmIs.setFirstRq(tinids);
                                wmOmQmIService.updateById(omQmIs);
                            }
                        }
                    }
                }
                for (WmOmQmI omQmIs : wmOmQmIS1) {
                    Integer integer = Convert.toInt(tinids1.substring(tinids1.length() - 4, tinids1.length())) + 1;
                    tinids1 = "T" + String.format("%04d", integer);
                    if (StringUtils.isEmpty(omQmIs.getSecondRq())){
                        omQmIs.setSecondRq(tinids1);
                        wmOmQmIService.updateById(omQmIs);
                    }
                }
                omQmIS.addAll(wmOmQmIS1);
                int size = omQmIS.size();
                int lengths = 1;
//            String omid = (String) redisUtil.get(omQmI.getOmNoticeId() + "-" + omQmI.getGoodsId());
//            if (StringUtils.isNotEmpty(omid)){
//                String[] split = omid.split(",");
//                List<String> list = Arrays.asList(split);
//                int index = -1;
//                index = list.indexOf(omQmI.getId());
//                if(index == -1){
//                    lengths = split.length + 1;
//                }else {
//                    lengths = index + 1;
//                }
//                redisUtil.set(omQmI.getOmNoticeId()+"-"+omQmI.getGoodsId(),omid+","+omQmI.getId());
//            }else{
//                redisUtil.set(omQmI.getOmNoticeId()+"-"+omQmI.getGoodsId(),omQmI.getId());
//            }
                Thread.sleep(1);

                WmOmQmI omQmI2 = new WmOmQmI();
                if (StringUtils.isEmpty(wmToDownGoods.getOrderIdI())){
                    WmToDownGoods goodsServiceById = wmToDownGoodsService.getById(wmToDownGoods.getParenId());
                    omQmI2 = wmOmQmIService.getById(goodsServiceById.getOrderIdI());
                }else {
                    omQmI2 = wmOmQmIService.getById(wmToDownGoods.getOrderIdI());
                }
                Map map1 = new LinkedHashMap();
                Integer integer = Convert.toInt(omQmI2.getSecondRq().substring(omQmI2.getSecondRq().length() - 4, tinids1.length()));
                map1.put("data01", wmOmNoticeHS.getU8Djcode1());
                map1.put("data02", omQmI2.getFirstRq());
                map1.put("data03", integer+"/"+size);
                System.out.println(omQmI.getFirstRq()+"--------");
                JSONObject jsonObj = new JSONObject(map1);
                String Json = jsonObj.toString();
                String post = HttpUtil.post(pritIp3+"util/uwms/client/print/chuku/admin", Json);
            }
            return Result.OK("打印成功");
        }
    }


}
