package com.base.modules.jeebms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;

import com.base.common.util.oConvertUtils;
import com.base.modules.jeebms.entity.DiItMethod;
import com.base.modules.jeebms.service.IDiItMethodService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import com.base.common.system.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

 /**
 * @Description: di_it_method
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Api(tags="di_it_method")
@RestController
@RequestMapping("/di/diItMethod")
@Slf4j
public class DiItMethodController extends BaseController<DiItMethod, IDiItMethodService> {
	@Autowired
	private IDiItMethodService diItMethodService;

	/**
	 *
	 * 分页列表查询
	 *
	 * @param diItMethod
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "di_it_method-分页列表查询")
	@ApiOperation(value="di_it_method-分页列表查询", notes="di_it_method-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DiItMethod diItMethod,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DiItMethod> queryWrapper = QueryGenerator.initQueryWrapper(diItMethod, req.getParameterMap());
		Page<DiItMethod> page = new Page<DiItMethod>(pageNo, pageSize);
		IPage<DiItMethod> pageList = diItMethodService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param diItMethod
	 * @return
	 */
	@AutoLog(value = "di_it_method-添加")
	@ApiOperation(value="di_it_method-添加", notes="di_it_method-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DiItMethod diItMethod) {
		diItMethodService.save(diItMethod);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param diItMethod
	 * @return
	 */
	@AutoLog(value = "di_it_method-编辑")
	@ApiOperation(value="di_it_method-编辑", notes="di_it_method-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DiItMethod diItMethod) {
		diItMethodService.updateById(diItMethod);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "di_it_method-通过id删除")
	@ApiOperation(value="di_it_method-通过id删除", notes="di_it_method-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		diItMethodService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "di_it_method-批量删除")
	@ApiOperation(value="di_it_method-批量删除", notes="di_it_method-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.diItMethodService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "di_it_method-通过id查询")
	@ApiOperation(value="di_it_method-通过id查询", notes="di_it_method-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DiItMethod diItMethod = diItMethodService.getById(id);
		if(diItMethod==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(diItMethod);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param diItMethod
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DiItMethod diItMethod) {
        return super.exportXls(request, diItMethod, DiItMethod.class, "di_it_method");
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
        return super.importExcel(request, response, DiItMethod.class);
    }

}
