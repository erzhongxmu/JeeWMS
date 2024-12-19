package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmDayCost;
import com.base.modules.jeewms.service.IWmDayCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 费用
 * @Author: base-boot
 * @Date: 2021-05-24
 * @Version: V1.0
 */
@Api(tags = "费用")
@RestController
@RequestMapping("/jeewms/wmDayCost")
@Slf4j
public class WmDayCostController extends JeecgController<WmDayCost, IWmDayCostService> {
    @Autowired
    private IWmDayCostService wmDayCostService;

    /**
     * 费用明细-分页列表查询
     *
     * @param wmDayCost
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "费用明细-分页列表查询")
    @ApiOperation(value = "费用明细-分页列表查询", notes = "费用明细-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WmDayCost wmDayCost,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<WmDayCost> queryWrapper = QueryGenerator.initQueryWrapper(wmDayCost, req.getParameterMap());
        Page<WmDayCost> page = new Page<WmDayCost>(pageNo, pageSize);
        IPage<WmDayCost> pageList = wmDayCostService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 收款管理-分页列表查询
     *
     * @param wmDayCost
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "收款管理-分页列表查询")
    @ApiOperation(value = "收款管理-分页列表查询", notes = "收款管理-分页列表查询")
    @GetMapping(value = "/datagridsk")
    public Result<?> datagridsk(WmDayCost wmDayCost,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                HttpServletRequest req) {
        QueryWrapper<WmDayCost> queryWrapper = QueryGenerator.initQueryWrapper(wmDayCost, req.getParameterMap());
        queryWrapper.eq("cost_js", "Y");
        Page<WmDayCost> page = new Page<WmDayCost>(pageNo, pageSize);
        IPage<WmDayCost> pageList = wmDayCostService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 收款管理-分页列表查询
     *
     * @param wmDayCost
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "欠费管理-分页列表查询")
    @ApiOperation(value = "欠费管理-分页列表查询", notes = "欠费管理-分页列表查询")
    @GetMapping(value = "/datagridqf")
    public Result<?> datagridqf(WmDayCost wmDayCost,
                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                HttpServletRequest req) {
        QueryWrapper<WmDayCost> queryWrapper = QueryGenerator.initQueryWrapper(wmDayCost, req.getParameterMap());
        queryWrapper.eq("cost_js", "N");
        Page<WmDayCost> page = new Page<WmDayCost>(pageNo, pageSize);
        IPage<WmDayCost> pageList = wmDayCostService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * @param ids
     * @return
     * @Describe 收款
     * @Author zly
     * @Create Date 2021/5/24
     */
    @AutoLog(value = "收款")
    @ApiOperation(value = "收款", notes = "收款")
    @PutMapping(value = "dopljq")
    public Result<?> dopljq(@RequestBody List<String> ids) {
        String costjs = "Y";
        Result<?> result = wmDayCostService.dopljq(ids,costjs);
        return result;
    }

    /**
     * @param ids
     * @return
     * @Describe 取消收款
     * @Author zly
     * @Create Date 2021/5/24
     */
    @AutoLog(value = "取消收款")
    @ApiOperation(value = "取消收款", notes = "取消收款")
    @PutMapping(value = "doplfjq")
    public Result<?> doplfjq(@RequestBody List<String> ids) {
        String costjs = "N";
        Result<?> result = wmDayCostService.dopljq(ids,costjs);
        return result;
    }

    /**
     * 添加
     *
     * @param wmDayCost
     * @return
     */
    @AutoLog(value = "费用-添加")
    @ApiOperation(value = "费用-添加", notes = "费用-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmDayCost wmDayCost) {
        wmDayCost.setCostJs("N");
        wmDayCostService.save(wmDayCost);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param wmDayCost
     * @return
     */
    @AutoLog(value = "费用-编辑")
    @ApiOperation(value = "费用-编辑", notes = "费用-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmDayCost wmDayCost) {
        wmDayCostService.updateById(wmDayCost);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "费用-通过id删除")
    @ApiOperation(value = "费用-通过id删除", notes = "费用-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wmDayCostService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "费用-批量删除")
    @ApiOperation(value = "费用-批量删除", notes = "费用-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wmDayCostService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "费用-通过id查询")
    @ApiOperation(value = "费用-通过id查询", notes = "费用-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WmDayCost wmDayCost = wmDayCostService.getById(id);
        if (wmDayCost == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmDayCost);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wmDayCost
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmDayCost wmDayCost) {
        return super.exportXls(request, wmDayCost, WmDayCost.class, "费用");
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
        return super.importExcel(request, response, WmDayCost.class);
    }

}
