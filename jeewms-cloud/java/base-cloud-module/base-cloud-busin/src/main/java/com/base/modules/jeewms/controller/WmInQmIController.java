package com.base.modules.jeewms.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.base.controller.JeecgController;;
import org.jeecg.common.system.util.JwtUtil;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.controller.dto.BatchUpdateProduceDateDTO;
import com.base.modules.jeewms.controller.dto.batchUpdateBinDTO;
import com.base.modules.jeewms.vo.WmInQmIVo;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 进货管理——批量收货
 * @Author: base-boot
 * @Date: 2021-05-20
 * @Version: V1.0
 */
@Api(tags = "进货管理——收货登记")
@RestController
@RequestMapping("/jeewms/wmInQmI")
@Slf4j
public class WmInQmIController extends JeecgController<WmInQmI, IWmInQmIService> {
    @Autowired
    private IWmInQmIService wmInQmIService;
    @Autowired
    private IBaKwService baKwService;
    @Autowired
    private IUserToGoodsService userToGoodsService;

    @Autowired
    private IMdGoodsService goodsService;

    @Autowired
    private IWmImNoticeIService wmImNoticeIService;

    @Autowired
    private IWmTuopanService iWmTuopanService;

    /**
     * 分页列表查询
     *
     * @param wmInQmI
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "进货管理——批量收货-分页列表查询")
    @ApiOperation(value = "进货管理——批量收货-分页列表查询", notes = "进货管理——批量收货-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WmInQmI wmInQmI,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<WmInQmI> queryWrapper = QueryGenerator.initQueryWrapper(wmInQmI, req.getParameterMap());
        queryWrapper.orderByDesc("create_time");
//        queryWrapper.orderByDesc("SUBSTRING(REPLACE(im_notice_id,'-',''),'5') ");
        Page<WmInQmI> page = new Page<WmInQmI>(pageNo, pageSize);
        IPage<WmInQmI> pageList = wmInQmIService.page(page, queryWrapper);
        List<MdGoods> goodsList = userToGoodsService.query(loginUser.getUsername());
        if (!goodsList.isEmpty()) {
            queryWrapper.in("goods_id", goodsList.stream().map(MdGoods::getShpBianMa).collect(Collectors.toList()));
            pageList.getRecords().forEach(item -> {
                item.setRecommendBinId(baKwService.selectRecommandBin(item.getGoodsTypeId()));
            });
            return Result.ok(pageList);
        }
        List<WmInQmI> records = pageList.getRecords();
        for (WmInQmI record : records) {
            List<MdGoods> list = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, record.getGoodsId()).list();
            if (CollectionUtil.isNotEmpty(list)){
                record.setYwMingCheng(list.get(0).getYwMingCheng());
            }
        }
        return Result.ok(pageList);
    }


    @ApiOperation(value = "可用储位分页查询", notes = "可用储位分页查询")
    @GetMapping(value = "/getAvlBin")
    public Result<?> getAvlBin(@RequestParam String id,
                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        return Result.ok(wmInQmIService.getAvlBin(id, pageNo, pageSize));
    }

    @ApiOperation(value = "获取推荐库位", notes = "获取推荐库位")
    @GetMapping(value = "/getRecommendBin")
    public Result<?> getRecommendBin(String goodsId) {

        return Result.ok(wmInQmIService.getRecommendBin(goodsId));
    }


    /**
     * 退货上架列表查询
     *
     * @param wmInQmI
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "进货管理——批量收货-分页列表查询")
    @ApiOperation(value = "进货管理——批量收货-分页列表查询", notes = "进货管理——批量收货-分页列表查询")
    @GetMapping(value = "/sjList")
    public Result<?> querySjPageList(WmInQmI wmInQmI,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                     HttpServletRequest req) {

        IPage<WmInQmI> pageList = wmInQmIService.querySjPageList(wmInQmI, pageNo, pageSize);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param wmInQmI
     * @return
     */
//	@AutoLog(value = "进货管理——批量收货-添加")
    @ApiOperation(value = "进货管理——批量收货-添加", notes = "进货管理——批量收货-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmInQmI wmInQmI) {
        wmInQmIService.add(wmInQmI);
        return Result.ok("添加成功！");
    }

    /**
     * 添加
     *
     * @return
     */
    @AutoLog(value = "批量验收")
    @ApiOperation(value = "批量验收", notes = "批量验收")
    @PostMapping(value = "/batchAdd")
    public Result<?> batchAdd(@RequestBody List<WmImNoticeI> wmImNoticeIList) {
        wmInQmIService.batchAdd(wmImNoticeIList);
        return Result.ok("添加成功！");
    }



    /**
     * 双维上架
     *
     * @param wmInQmI
     * @return
     */
    @AutoLog(value = "双维上架")
    @ApiOperation(value = "双维上架", notes = "双维上架")
    @PostMapping(value = "/upToShelf")
    public Result<?> upToShelf(@RequestBody WmInQmI wmInQmI) {
        String idList = wmInQmI.getIdList();
        String[] split = idList.split(",");
        List<String> strings = Arrays.asList(split);
        wmInQmIService.upToShelf(strings);
        return Result.ok("添加成功！");
    }
    /**
     * 添加
     *
     * @param wmInQmI
     * @return
     */
    @AutoLog(value = "上架")
    @ApiOperation(value = "上架", notes = "上架")
    @PostMapping(value = "/upToShelf1")
    public Result<?> upToShelf1(@RequestBody WmInQmI wmInQmI) {
        String idList = wmInQmI.getIdList();
        String[] split = idList.split(",");
        List<String> strings = Arrays.asList(split);
        wmInQmIService.upToShelf(strings);
        return Result.ok("添加成功！");
    }


    /**
     * 上架
     *
     * @return
     */
    @AutoLog(value = "强制上架")
    @ApiOperation(value = "强制上架", notes = "强制上架")
    @PostMapping(value = "/upToShelfForce")
    public Result<?> upToShelfForce(@RequestBody List<String> idList) {
        wmInQmIService.upToShelfForce(idList);
        return Result.ok("上架成功！");
    }


    /**
     * 上架
     *
     * @param wmInQmI
     * @return
     */
    @AutoLog(value = "上架")
    @ApiOperation(value = "上架", notes = "上架")
    @PostMapping(value = "/upToShelfOne")
    public Result<?> upToShelfOne(@RequestBody WmInQmI wmInQmI) {
        wmInQmIService.upToShelfOne(wmInQmI);
        return Result.ok("上架成功！");
    }

    /**
     * 上架
     *
     * @param wmInQmI
     * @return
     */
    @AutoLog(value = "强制上架")
    @ApiOperation(value = "强制上架", notes = "强制上架")
    @PostMapping(value = "/upToShelfOneforce")
    public Result<?> upToShelfOneforce(@RequestBody WmInQmI wmInQmI) {
        wmInQmIService.upToShelfOneforce(wmInQmI);
        return Result.ok("上架成功！");
    }

    /**
     * 批量更改储位
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "批量更改储位", notes = "批量更改储位")
    @PostMapping(value = "/batchUpdateBin")
    public Result<?> batchUpdateBin(@RequestBody batchUpdateBinDTO param) {
        wmInQmIService.batchUpdateBin(param);
        return Result.ok("更新成功！");
    }

    /**
     * 批量更新生产日期
     *
     * @param param
     * @return
     */
    @ApiOperation(value = "批量更新生产日期", notes = "批量更新生产日期")
    @PostMapping(value = "/batchUpdateProduceDate")
    public Result<?> batchUpdateProduceDate(@RequestBody BatchUpdateProduceDateDTO param) {
        wmInQmIService.batchUpdateProduceDate(param);
        return Result.ok("更新成功！");
    }


    /**
     * 编辑
     *
     * @param wmInQmI
     * @return
     */
    @AutoLog(value = "进货管理——批量收货-编辑")
    @ApiOperation(value = "进货管理——批量收货-编辑", notes = "进货管理——批量收货-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmInQmI wmInQmI) {
        WmInQmI byId = wmInQmIService.getById(wmInQmI.getId());

        List<WmTuopan> list2 = iWmTuopanService.lambdaQuery().eq(WmTuopan::getTinId,byId.getTinId()).
                list();
        WmTuopan wmTuopan = list2.get(0).setTinId(wmInQmI.getTinId());
        List<WmTuopan> list = iWmTuopanService.lambdaQuery().eq(WmTuopan::getTinId, wmInQmI.getBinId()).list();
        if(CollectionUtils.isEmpty(list)){
            iWmTuopanService.updateById(wmTuopan);
        }
        wmInQmIService.updateById(wmInQmI);
        /*if(StringUtils.isNotEmpty(wmInQmI.getImNoticeItem())){
            WmImNoticeI noticeIServiceById = wmImNoticeIService.getById(wmInQmI.getImNoticeItem());
            noticeIServiceById.setShenqingsl(wmInQmI.getQmOkQuat());
            wmImNoticeIService.updateById(noticeIServiceById);
        }*/
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "进货管理——批量收货-通过id删除")
    @ApiOperation(value = "进货管理——批量收货-通过id删除", notes = "进货管理——批量收货-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wmInQmIService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "进货管理——批量收货-批量删除")
    @ApiOperation(value = "进货管理——批量收货-批量删除", notes = "进货管理——批量收货-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wmInQmIService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "进货管理——批量收货-通过id查询")
    @ApiOperation(value = "进货管理——批量收货-通过id查询", notes = "进货管理——批量收货-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WmInQmI wmInQmI = wmInQmIService.getById(id);
        if (wmInQmI == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmInQmI);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wmInQmI
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmInQmI wmInQmI) {
        //return super.exportXls(request, wmInQmI, WmInQmI.class, "进货管理——批量收货");
        return super.exportXls(request, wmInQmI, WmInQmI.class, "登记");
    }

    /**
     * 退货上架导出excel
     *
     * @param request
     * @param wmInQmI
     */
    @RequestMapping(value = "/exportXlsTwo")
    public ModelAndView exportXlsTwo(HttpServletRequest request, WmInQmI wmInQmI) {
        // Step.1 组装查询条件
        QueryWrapper<WmInQmI> queryWrapper = QueryGenerator.initQueryWrapper(wmInQmI, request.getParameterMap());
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        LoginUser sysUser = JwtUtil.getLoginUser();

        // Step.2 获取导出数据
        List<WmInQmI> pageList = wmInQmIService.list(queryWrapper);
        List<WmInQmI> exportList = null;
        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }

        List<WmInQmIVo> list = new ArrayList<WmInQmIVo>();
        for (WmInQmI main : exportList) {
            WmInQmIVo vo = new WmInQmIVo();
            BeanUtils.copyProperties(main, vo);
            list.add(vo);
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "退货上架"); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, WmInQmIVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("退货上架" + "报表", "导出人:" + sysUser.getRealname(), "退货上架"));
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
        return super.importExcel(request, response, WmInQmI.class);
    }

}
