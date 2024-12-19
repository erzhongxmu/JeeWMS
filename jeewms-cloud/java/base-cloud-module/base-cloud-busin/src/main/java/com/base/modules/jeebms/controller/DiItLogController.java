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
import com.base.modules.jeebms.entity.DiItLog;
import com.base.modules.jeebms.service.IDiItLogService;

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
 * @Description: di_it_log
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Api(tags="di_it_log")
@RestController
@RequestMapping("/di/diItLog")
@Slf4j
public class DiItLogController extends BaseController<DiItLog, IDiItLogService> {
	@Autowired
	private IDiItLogService diItLogService;

	/**
	 * 分页列表查询
	 *
	 * @param diItLog
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "di_it_log-分页列表查询")
	@ApiOperation(value="di_it_log-分页列表查询", notes="di_it_log-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DiItLog diItLog,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DiItLog> queryWrapper = QueryGenerator.initQueryWrapper(diItLog, req.getParameterMap());
		Page<DiItLog> page = new Page<DiItLog>(pageNo, pageSize);
		IPage<DiItLog> pageList = diItLogService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param diItLog
	 * @return
	 */
	@AutoLog(value = "di_it_log-添加")
	@ApiOperation(value="di_it_log-添加", notes="di_it_log-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DiItLog diItLog) {
		diItLogService.save(diItLog);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param diItLog
	 * @return
	 */
	@AutoLog(value = "di_it_log-编辑")
	@ApiOperation(value="di_it_log-编辑", notes="di_it_log-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DiItLog diItLog) {
		diItLogService.updateById(diItLog);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "di_it_log-通过id删除")
	@ApiOperation(value="di_it_log-通过id删除", notes="di_it_log-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		diItLogService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "di_it_log-批量删除")
	@ApiOperation(value="di_it_log-批量删除", notes="di_it_log-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.diItLogService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "di_it_log-通过id查询")
	@ApiOperation(value="di_it_log-通过id查询", notes="di_it_log-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DiItLog diItLog = diItLogService.getById(id);
		if(diItLog==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(diItLog);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param diItLog
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DiItLog diItLog) {
        return super.exportXls(request, diItLog, DiItLog.class, "di_it_log");
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
        return super.importExcel(request, response, DiItLog.class);
    }

}
