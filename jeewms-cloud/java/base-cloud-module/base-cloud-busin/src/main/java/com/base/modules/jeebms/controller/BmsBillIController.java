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
import com.base.modules.jeebms.entity.BmsBillI;
import com.base.modules.jeebms.service.IBmsBillIService;

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
 * @Description: bms_bill_i
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Api(tags="bms_bill_i")
@RestController
@RequestMapping("/bms/bmsBillI")
@Slf4j
public class BmsBillIController extends BaseController<BmsBillI, IBmsBillIService> {
	@Autowired
	private IBmsBillIService bmsBillIService;

	/**
	 * 分页列表查询
	 *
	 * @param bmsBillI
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "bms_bill_i-分页列表查询")
	@ApiOperation(value="bms_bill_i-分页列表查询", notes="bms_bill_i-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BmsBillI bmsBillI,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BmsBillI> queryWrapper = QueryGenerator.initQueryWrapper(bmsBillI, req.getParameterMap());
		Page<BmsBillI> page = new Page<BmsBillI>(pageNo, pageSize);
		IPage<BmsBillI> pageList = bmsBillIService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param bmsBillI
	 * @return
	 */
	@AutoLog(value = "bms_bill_i-添加")
	@ApiOperation(value="bms_bill_i-添加", notes="bms_bill_i-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BmsBillI bmsBillI) {
		bmsBillIService.save(bmsBillI);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param bmsBillI
	 * @return
	 */
	@AutoLog(value = "bms_bill_i-编辑")
	@ApiOperation(value="bms_bill_i-编辑", notes="bms_bill_i-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BmsBillI bmsBillI) {
		bmsBillIService.updateById(bmsBillI);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bms_bill_i-通过id删除")
	@ApiOperation(value="bms_bill_i-通过id删除", notes="bms_bill_i-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		bmsBillIService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "bms_bill_i-批量删除")
	@ApiOperation(value="bms_bill_i-批量删除", notes="bms_bill_i-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.bmsBillIService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bms_bill_i-通过id查询")
	@ApiOperation(value="bms_bill_i-通过id查询", notes="bms_bill_i-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BmsBillI bmsBillI = bmsBillIService.getById(id);
		if(bmsBillI==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(bmsBillI);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param bmsBillI
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BmsBillI bmsBillI) {
        return super.exportXls(request, bmsBillI, BmsBillI.class, "bms_bill_i");
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
        return super.importExcel(request, response, BmsBillI.class);
    }

}
