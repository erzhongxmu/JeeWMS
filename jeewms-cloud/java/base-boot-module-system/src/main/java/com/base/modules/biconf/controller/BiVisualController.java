package com.base.modules.biconf.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.modules.biconf.entity.BiVisual;
import com.base.modules.biconf.service.IBiVisualService;
import com.base.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 大屏列表
 * @Author: base-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Api(tags="大屏列表")
@RestController
@RequestMapping("/biconf/biVisual")
@Slf4j
public class BiVisualController extends JeecgController<BiVisual, IBiVisualService> {
	@Autowired
	private IBiVisualService biVisualService;

	/**
	 * 分页列表查询
	 *
	 * @param biVisual
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "大屏列表-分页列表查询")
	@ApiOperation(value="大屏列表-分页列表查询", notes="大屏列表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BiVisual biVisual,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BiVisual> queryWrapper = QueryGenerator.initQueryWrapper(biVisual, req.getParameterMap());
		Page<BiVisual> page = new Page<BiVisual>(pageNo, pageSize);
		IPage<BiVisual> pageList = biVisualService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param biVisual
	 * @return
	 */
	@AutoLog(value = "大屏列表-添加")
	@ApiOperation(value="大屏列表-添加", notes="大屏列表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BiVisual biVisual) {
		biVisualService.save(biVisual);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param biVisual
	 * @return
	 */
	@AutoLog(value = "大屏列表-编辑")
	@ApiOperation(value="大屏列表-编辑", notes="大屏列表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BiVisual biVisual) {
		biVisualService.updateById(biVisual);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "大屏列表-通过id删除")
	@ApiOperation(value="大屏列表-通过id删除", notes="大屏列表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		biVisualService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "大屏列表-批量删除")
	@ApiOperation(value="大屏列表-批量删除", notes="大屏列表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.biVisualService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "大屏列表-通过id查询")
	@ApiOperation(value="大屏列表-通过id查询", notes="大屏列表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BiVisual biVisual = biVisualService.getById(id);
		if(biVisual==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(biVisual);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param biVisual
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BiVisual biVisual) {
        return super.exportXls(request, biVisual, BiVisual.class, "大屏列表");
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
        return super.importExcel(request, response, BiVisual.class);
    }

}
