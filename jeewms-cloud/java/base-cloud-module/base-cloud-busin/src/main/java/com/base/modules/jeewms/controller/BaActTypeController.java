package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.BaActType;
import com.base.modules.jeewms.service.IBaActTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 作业类型
 * @Author: base-boot
 * @Date: 2021-05-19
 * @Version: V1.0
 */
@Api(tags = "作业类型")
@RestController
@RequestMapping("/jeewms/baActType")
@Slf4j
public class BaActTypeController extends JeecgController<BaActType, IBaActTypeService> {
    @Autowired
    private IBaActTypeService baActTypeService;

    /**
     * 分页列表查询
     *
     * @param baActType
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "作业类型-分页列表查询")
    @ApiOperation(value = "作业类型-分页列表查询", notes = "作业类型-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(BaActType baActType,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<BaActType> queryWrapper = QueryGenerator.initQueryWrapper(baActType, req.getParameterMap());
        Page<BaActType> page = new Page<BaActType>(pageNo, pageSize);
        IPage<BaActType> pageList = baActTypeService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param baActType
     * @return
     */
    @AutoLog(value = "作业类型-添加")
    @ApiOperation(value = "作业类型-添加", notes = "作业类型-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody BaActType baActType) {
        baActTypeService.save(baActType);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param baActType
     * @return
     */
    @AutoLog(value = "作业类型-编辑")
    @ApiOperation(value = "作业类型-编辑", notes = "作业类型-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody BaActType baActType) {
        baActTypeService.updateById(baActType);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "作业类型-通过id删除")
    @ApiOperation(value = "作业类型-通过id删除", notes = "作业类型-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        baActTypeService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "作业类型-批量删除")
    @ApiOperation(value = "作业类型-批量删除", notes = "作业类型-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.baActTypeService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "作业类型-通过id查询")
    @ApiOperation(value = "作业类型-通过id查询", notes = "作业类型-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        BaActType baActType = baActTypeService.getById(id);
        if (baActType == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(baActType);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param baActType
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaActType baActType) {
        return super.exportXls(request, baActType, BaActType.class, "作业类型");
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
        return super.importExcel(request, response, BaActType.class);
    }

}
