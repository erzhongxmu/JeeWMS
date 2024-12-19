package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.common.util.UUIDGenerator;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.BaCity;
import com.base.modules.jeewms.service.IBaCityService;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 地区信息
 * @Author: base-boot
 * @Date: 2021-05-19
 * @Version: V1.0
 */
@Api(tags = "地区信息")
@RestController
@RequestMapping("/jeewms/baCity")
@Slf4j
public class BaCityController extends JeecgController<BaCity, IBaCityService> {
    @Autowired
    private IBaCityService baCityService;

    /**
     * 分页列表查询
     *
     * @param baCity
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "地区信息-分页列表查询")
    @ApiOperation(value = "地区信息-分页列表查询", notes = "地区信息-分页列表查询")
    @GetMapping(value = "/rootList")
    public Result<?> queryPageList(BaCity baCity,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        String parentId = baCity.getPid();
        if (oConvertUtils.isEmpty(parentId)) {
            parentId = "0";
        }
        baCity.setPid(null);
        QueryWrapper<BaCity> queryWrapper = QueryGenerator.initQueryWrapper(baCity, req.getParameterMap());
        // 使用 eq 防止模糊查询
        queryWrapper.eq("pid", parentId);
        Page<BaCity> page = new Page<BaCity>(pageNo, pageSize);
        IPage<BaCity> pageList = baCityService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 获取子数据
     *
     * @param testTree
     * @param req
     * @return
     */
    @AutoLog(value = "地区信息-获取子数据")
    @ApiOperation(value = "地区信息-获取子数据", notes = "地区信息-获取子数据")
    @GetMapping(value = "/childList")
    public Result<?> queryPageList(BaCity baCity, HttpServletRequest req) {
        QueryWrapper<BaCity> queryWrapper = QueryGenerator.initQueryWrapper(baCity, req.getParameterMap());
        List<BaCity> list = baCityService.list(queryWrapper);
        return Result.ok(list);
    }


    /**
     * 添加
     *
     * @param baCity
     * @return
     */
    @AutoLog(value = "地区信息-添加")
    @ApiOperation(value = "地区信息-添加", notes = "地区信息-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody BaCity baCity) {
        baCity.setCityCode(UUIDGenerator.generate());
        baCityService.addBaCity(baCity);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param baCity
     * @return
     */
    @AutoLog(value = "地区信息-编辑")
    @ApiOperation(value = "地区信息-编辑", notes = "地区信息-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody BaCity baCity) {
        baCityService.updateBaCity(baCity);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "地区信息-通过id删除")
    @ApiOperation(value = "地区信息-通过id删除", notes = "地区信息-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        baCityService.deleteBaCity(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "地区信息-批量删除")
    @ApiOperation(value = "地区信息-批量删除", notes = "地区信息-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.baCityService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "地区信息-通过id查询")
    @ApiOperation(value = "地区信息-通过id查询", notes = "地区信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        BaCity baCity = baCityService.getById(id);
        if (baCity == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(baCity);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param baCity
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaCity baCity) {
        return super.exportXls(request, baCity, BaCity.class, "地区信息");
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
        return super.importExcel(request, response, BaCity.class);
    }


    /**
     * @Describe : 地区递归查询数据查询
     * @Author: zly
     * @Create Date: 2021-05-18
     */
    @AutoLog(value = "地区信息-地区递归查询数据")
    @ApiOperation(value = "地区信息-地区递归查询数据", notes = "地区信息-地区递归查询数据")
    @GetMapping(value = "/getChild")
    public Result<?> getChild() {
        return baCityService.getChild();
    }
}
