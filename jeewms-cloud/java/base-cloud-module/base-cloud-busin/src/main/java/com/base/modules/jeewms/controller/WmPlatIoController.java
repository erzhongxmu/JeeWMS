package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmPlatIo;
import com.base.modules.jeewms.service.IWmPlatIoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 月台进出
 * @Author: base-boot
 * @Date: 2021-05-18
 * @Version: V1.0
 */
@Api(tags = "月台进出")
@RestController
@RequestMapping("/jeewms/wmPlatIo")
@Slf4j
public class WmPlatIoController extends JeecgController<WmPlatIo, IWmPlatIoService> {
    @Autowired
    private IWmPlatIoService wmPlatIoService;

    /**
     * 分页列表查询
     *
     * @param wmPlatIo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "月台进出-分页列表查询")
    @ApiOperation(value = "月台进出-分页列表查询", notes = "月台进出-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WmPlatIo wmPlatIo,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<WmPlatIo> queryWrapper = QueryGenerator.initQueryWrapper(wmPlatIo, req.getParameterMap());
        Page<WmPlatIo> page = new Page<WmPlatIo>(pageNo, pageSize);
        IPage<WmPlatIo> pageList = wmPlatIoService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 分页列表查询
     *
     * @param wmPlatIo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "月台管理-分页列表查询")
    @ApiOperation(value = "月台管理-分页列表查询", notes = "月台管理-分页列表查询")
    @GetMapping(value = "/qList")
    public Result<?> qList(WmPlatIo wmPlatIo,
                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                           HttpServletRequest req) {
        QueryWrapper<WmPlatIo> queryWrapper = QueryGenerator.initQueryWrapper(wmPlatIo, req.getParameterMap());
        queryWrapper.ne("plat_sta", "完成");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        queryWrapper.ge("plan_indata", simpleDateFormat.format(new Date()));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 1);//加一天
        queryWrapper.le("plan_indata", simpleDateFormat.format(c.getTime()));
        Page<WmPlatIo> page = new Page<WmPlatIo>(pageNo, pageSize);
        IPage<WmPlatIo> pageList = wmPlatIoService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param wmPlatIo
     * @return
     */
    @AutoLog(value = "月台进出-添加")
    @ApiOperation(value = "月台进出-添加", notes = "月台进出-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmPlatIo wmPlatIo) {
        wmPlatIoService.save(wmPlatIo);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param wmPlatIo
     * @return
     */
    @AutoLog(value = "月台进出-编辑")
    @ApiOperation(value = "月台进出-编辑", notes = "月台进出-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmPlatIo wmPlatIo) {
        wmPlatIoService.updateById(wmPlatIo);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "月台进出-通过id删除")
    @ApiOperation(value = "月台进出-通过id删除", notes = "月台进出-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wmPlatIoService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "月台进出-批量删除")
    @ApiOperation(value = "月台进出-批量删除", notes = "月台进出-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wmPlatIoService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "月台进出-通过id查询")
    @ApiOperation(value = "月台进出-通过id查询", notes = "月台进出-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WmPlatIo wmPlatIo = wmPlatIoService.getById(id);
        if (wmPlatIo == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(wmPlatIo);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wmPlatIo
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmPlatIo wmPlatIo) {
        return super.exportXls(request, wmPlatIo, WmPlatIo.class, "月台进出");
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
        return super.importExcel(request, response, WmPlatIo.class);
    }

}
