package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.WvStock;
import org.jeecg.common.system.base.controller.JeecgController;;
import org.jeecg.common.system.util.JwtUtil;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.controller.dto.StockMoveDto;
import com.base.modules.jeewms.entity.WmToMoveGoods;
import com.base.modules.jeewms.entity.WvStockStt;
import com.base.modules.jeewms.service.IWmToMoveGoodsService;
import com.base.modules.jeewms.service.impl.WvStockSttServiceImpl;
import com.base.modules.jeewms.vo.EditBatchWmToMoveGoodsVo;
import com.base.modules.jeewms.vo.addWmToMoveVo;
import com.base.modules.util.ConstUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 库存转移
 * @Author: base-boot
 * @Date: 2021-05-26
 * @Version: V1.0
 */
@Api(tags = "库存转移")
@RestController
@RequestMapping("/jeewms/wmToMoveGoods")
@Slf4j
public class WmToMoveGoodsController extends JeecgController<WmToMoveGoods, IWmToMoveGoodsService> {
    @Autowired
    private IWmToMoveGoodsService wmToMoveGoodsService;
    @Autowired
    private WvStockSttServiceImpl wvStockSttService;

    /**
     * 分页列表查询
     *
     * @param wmToMoveGoods
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "库存转移-分页列表查询")
    @ApiOperation(value = "库存转移-分页列表查询", notes = "库存转移-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WmToMoveGoods wmToMoveGoods,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<WmToMoveGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmToMoveGoods, req.getParameterMap());
        //queryWrapper.lambda().ne(WmToMoveGoods::getMoveSta, ConstUtil.wm_sta4);
        queryWrapper.lambda().ne(WmToMoveGoods::getMoveSta, "库存不足");
        Page<WmToMoveGoods> page = new Page<WmToMoveGoods>(pageNo, pageSize);
        IPage<WmToMoveGoods> pageList = wmToMoveGoodsService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param wmToMoveGoods
     * @return
     */
    @AutoLog(value = "库存转移-添加")
    @ApiOperation(value = "库存转移-添加", notes = "库存转移-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmToMoveGoods wmToMoveGoods) {
        wmToMoveGoodsService.save(wmToMoveGoods);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param wmToMoveGoods
     * @return
     */
    @AutoLog(value = "库存转移-编辑")
    @ApiOperation(value = "库存转移-编辑", notes = "库存转移-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmToMoveGoods wmToMoveGoods) {
        wmToMoveGoodsService.updateById(wmToMoveGoods);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "库存转移-通过id删除")
    @ApiOperation(value = "库存转移-通过id删除", notes = "库存转移-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wmToMoveGoodsService.removeById(id);
        return Result.ok("删除成功!");
    }
    /**
     * 入库通知单下拉列标配
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/getNoticeHSelectList")
    @ApiOperation(value="库存转移通知单下拉列表")
    public Result getNoticeHSelectList() {
        return Result.ok(wmToMoveGoodsService.lambdaQuery().eq(WmToMoveGoods::getMoveSta,"计划中").list());
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "库存转移-批量删除")
    @ApiOperation(value = "库存转移-批量删除", notes = "库存转移-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wmToMoveGoodsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "库存转移-通过id查询")
    @ApiOperation(value = "库存转移-通过id查询", notes = "库存转移-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WmToMoveGoods wmToMoveGoods = wmToMoveGoodsService.getById(id);
        if (wmToMoveGoods == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmToMoveGoods);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wmToMoveGoods
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmToMoveGoods wmToMoveGoods) {
        return super.exportXls(request, wmToMoveGoods, WmToMoveGoods.class, "库存转移");
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
        return super.importExcel(request, response, WmToMoveGoods.class);
    }
    /*---------------------------------定制功能-批量修改转移开始-------------------------------------*/

    /**
     * @param list
     * @return
     * @Describe 批量修改转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    @AutoLog(value = "批量修改转移")
    @ApiOperation(value = "批量修改转移", notes = "批量修改转移")
    @PutMapping(value = "editBatch")
    public Result<?> editBatch(@RequestBody List<EditBatchWmToMoveGoodsVo> list) {
        Result<?> result = wmToMoveGoodsService.editBatch(list);
        return result;
    }

    /*---------------------------------定制功能-批量修改转移结束-------------------------------------*/


    /*---------------------------------生成托盘转移开始-------------------------------------*/

    /**
     *
     * @param wvStock
     * @return
     * @Describe 生成托盘转移-分页列表查询
     * @Author zly
     * @Create Date 2021/5/26
     */
    /*@AutoLog(value = "生成托盘转移-分页列表查询")
    @ApiOperation(value = "生成托盘转移-分页列表查询", notes = "生成托盘转移-分页列表查询")
    @GetMapping(value = "/findPageList")
    public Result<?> findPageList(WvStock wvStock,
                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        Result<?> pageList = wmToMoveGoodsService.findPageLists(wvStock, pageNo, pageSize,req);
        return pageList;
    }*/

    @AutoLog(value = "生成托盘转移-分页列表查询")
    @ApiOperation(value = "生成托盘转移-分页列表查询", notes = "生成托盘转移-分页列表查询")
    @GetMapping(value = "/findPageList")
    public Result<?> findPageList(WvStockStt wvStock,
                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {

        Result<?> pageList = wmToMoveGoodsService.findPageLists(wvStock, pageNo, pageSize,req);
        return pageList;
    }

    /**
     * @param vo
     * @return
     * @Describe 添加托盘转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    @AutoLog(value = "添加托盘转移")
    @ApiOperation(value = "添加托盘转移", notes = "添加托盘转移")
    @PostMapping(value = "/addWmToMove")
    public Result<?> addWmToMove(@RequestBody addWmToMoveVo vo) {
        Result<?> result = wmToMoveGoodsService.addWmToMove(vo);
        return result;
    }

    /**
     * @param vo
     * @return
     * @Describe pda库存转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    @ApiOperation(value = "pda库存转移", notes = "pda库存转移")
    @PostMapping(value = "/pdaStockMove")
    public Result<?> pdaStockMove(@RequestBody StockMoveDto vo) {
        wmToMoveGoodsService.pdaStockMove(vo);
        return Result.ok();
    }

    /**
     * 导出托盘转移excel
     *
     * @param request
     * @param wmTuopan
     */
    @RequestMapping(value = "/exportTpXls")
    public ModelAndView exportTpXls(HttpServletRequest request, WvStockStt wvStockStt) {
        // Step.1 组装查询条件
        QueryWrapper<WvStockStt> queryWrapper = QueryGenerator.initQueryWrapper(wvStockStt, request.getParameterMap());
        queryWrapper.lambda().eq(WvStockStt::getKuctype,"库存");
//        System.err.println("一号没走通");
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        System.err.println(loginUser);
        LoginUser sysUser = JwtUtil.getLoginUser();

        // Step.2 获取导出数据
        List<WvStockStt> pageList = wvStockSttService.list(queryWrapper);
        System.err.println(pageList.get(0));
        List<WvStockStt> exportList = null;

        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "托盘转移"); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, WvStockStt.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("托盘转移报表", "导出人:" + sysUser.getRealname(), "托盘转移"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }




    /*---------------------------------生成托盘转移结束-------------------------------------*/

    /*---------------------------------生成库存转移开始-------------------------------------*/
    /**
     * @param ids
     * @return
     * @Describe 添加储位转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    @AutoLog(value = "添加储位转移")
    @ApiOperation(value = "添加储位转移", notes = "添加储位转移")
    @PostMapping(value = "/addWmToMoveGoods")
    public Result<?> addWmToMoveGoods(@RequestBody List<String> ids) {
        Result<?> result = wmToMoveGoodsService.addWmToMoveGoods(ids);
        return result;
    }
    /*---------------------------------生成库存转移结束-------------------------------------*/

    /**
     * 获取对象ID
     *
     * @return
     */
    private String getId(WvStockStt item) {
        try {
            return PropertyUtils.getProperty(item, "id").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
