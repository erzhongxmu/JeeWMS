package com.base.modules.jeewms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.common.api.vo.Result;
import com.base.common.system.vo.LoginUser;
import com.base.modules.jeewms.entity.BaGoodsType;
import com.base.modules.jeewms.entity.BaUnit;
import com.base.modules.jeewms.entity.WmsComType;
import com.base.modules.jeewms.service.IBaUnitService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.base.controller.JeecgController;;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

/**
 * @Description: 计量单位
 * @Author: base-boot
 * @Date: 2021-05-18
 * @Version: V1.0
 */
@Api(tags = "计量单位")
@RestController
@RequestMapping("/jeewms/baUnit")
@Slf4j
public class BaUnitController extends JeecgController<BaUnit, IBaUnitService> {
    @Autowired
    private IBaUnitService baUnitService;

    /**
     * 分页列表查询
     *
     * @param baUnit
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "计量单位-分页列表查询")
    @ApiOperation(value = "计量单位-分页列表查询", notes = "计量单位-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(BaUnit baUnit,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<BaUnit> queryWrapper = QueryGenerator.initQueryWrapper(baUnit, req.getParameterMap());
        Page<BaUnit> page = new Page<BaUnit>(pageNo, pageSize);
        IPage<BaUnit> pageList = baUnitService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param baUnit
     * @return
     */
    @AutoLog(value = "计量单位-添加")
    @ApiOperation(value = "计量单位-添加", notes = "计量单位-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody BaUnit baUnit) {
        if (baUnitService.lambdaQuery()
                .eq(BaUnit::getUnitCode, baUnit.getUnitCode()).count() > 0) {
            return Result.error("编码已存在，请重新输入");
        } else {
            baUnitService.save(baUnit);
        }
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param baUnit
     * @return
     */
    @AutoLog(value = "计量单位-编辑")
    @ApiOperation(value = "计量单位-编辑", notes = "计量单位-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody BaUnit baUnit) {
		BaUnit unit = baUnitService.lambdaQuery().eq(BaUnit::getUnitCode, baUnit.getUnitCode())
                .ne(BaUnit::getId, baUnit.getId()).one();
        if (unit != null) {
            return Result.error("编码已存在，请重新输入");
        }else {
            baUnitService.updateById(baUnit);
        }
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "计量单位-通过id删除")
    @ApiOperation(value = "计量单位-通过id删除", notes = "计量单位-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        baUnitService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "计量单位-批量删除")
    @ApiOperation(value = "计量单位-批量删除", notes = "计量单位-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.baUnitService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "计量单位-通过id查询")
    @ApiOperation(value = "计量单位-通过id查询", notes = "计量单位-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        BaUnit baUnit = baUnitService.getById(id);
        if (baUnit == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(baUnit);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param baUnit
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaUnit baUnit) {
        return super.exportXls(request, baUnit, BaUnit.class, "计量单位");
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
                List<BaUnit> list = ExcelImportUtil.importExcel(file.getInputStream(), BaUnit.class, params);
                long start = System.currentTimeMillis();
                List<BaUnit> listsave = new ArrayList<>();
                if (list.size() > 0) {
                    for(BaUnit baUnit : list){
                        if(org.apache.commons.lang3.StringUtils.isEmpty(baUnit.getUnitCode())){
                            continue;
                        }
                        List<BaUnit> list1 = baUnitService.lambdaQuery().in(BaUnit::getUnitCode, baUnit.getUnitCode()).list();
                        for (BaUnit kw : list1) {
                            baUnitService.removeById(kw.getId());
                        }
                        listsave.add(baUnit);
                    }
                }
                baUnitService.saveBatch(listsave);
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

}
