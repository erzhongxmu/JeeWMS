package com.base.modules.bidata.controller;

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
import com.base.modules.bidata.entity.BiSql;
import com.base.modules.bidata.service.IBiSqlService;
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
 * @Description: 数据配置接口
 * @Author: base-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Api(tags="数据配置接口")
@RestController
@RequestMapping("/bidata/biSql")
@Slf4j
public class BiSqlController extends JeecgController<BiSql, IBiSqlService> {
	@Autowired
	private IBiSqlService biSqlService;

	/**
	 * 分页列表查询
	 *
	 * @param biSql
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "数据配置接口-分页列表查询")
	@ApiOperation(value="数据配置接口-分页列表查询", notes="数据配置接口-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BiSql biSql,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BiSql> queryWrapper = QueryGenerator.initQueryWrapper(biSql, req.getParameterMap());
		Page<BiSql> page = new Page<BiSql>(pageNo, pageSize);
		IPage<BiSql> pageList = biSqlService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param biSql
	 * @return
	 */
	@AutoLog(value = "数据配置接口-添加")
	@ApiOperation(value="数据配置接口-添加", notes="数据配置接口-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BiSql biSql) {
        biSql.setBpmStatus("1");
		biSqlService.save(biSql);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param biSql
	 * @return
	 */
	@AutoLog(value = "数据配置接口-编辑")
	@ApiOperation(value="数据配置接口-编辑", notes="数据配置接口-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BiSql biSql) {
		biSqlService.updateById(biSql);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "数据配置接口-通过id删除")
	@ApiOperation(value="数据配置接口-通过id删除", notes="数据配置接口-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		biSqlService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "数据配置接口-批量删除")
	@ApiOperation(value="数据配置接口-批量删除", notes="数据配置接口-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.biSqlService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "数据配置接口-通过id查询")
	@ApiOperation(value="数据配置接口-通过id查询", notes="数据配置接口-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BiSql biSql = biSqlService.getById(id);
		if(biSql==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(biSql);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param biSql
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BiSql biSql) {
        return super.exportXls(request, biSql, BiSql.class, "数据配置接口");
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
        return super.importExcel(request, response, BiSql.class);
    }

}
