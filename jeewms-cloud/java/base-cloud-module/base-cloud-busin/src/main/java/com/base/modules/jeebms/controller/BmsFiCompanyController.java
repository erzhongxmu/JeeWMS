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
import com.base.modules.jeebms.entity.BmsFiCompany;
import com.base.modules.jeebms.service.IBmsFiCompanyService;

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
 * @Description: bms_fi_company
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Api(tags="bms_fi_company")
@RestController
@RequestMapping("/bms/bmsFiCompany")
@Slf4j
public class BmsFiCompanyController extends BaseController<BmsFiCompany, IBmsFiCompanyService> {
	@Autowired
	private IBmsFiCompanyService bmsFiCompanyService;

	/**
	 * 分页列表查询
	 *
	 * @param bmsFiCompany
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "bms_fi_company-分页列表查询")
	@ApiOperation(value="bms_fi_company-分页列表查询", notes="bms_fi_company-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BmsFiCompany bmsFiCompany,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BmsFiCompany> queryWrapper = QueryGenerator.initQueryWrapper(bmsFiCompany, req.getParameterMap());
		Page<BmsFiCompany> page = new Page<BmsFiCompany>(pageNo, pageSize);
		IPage<BmsFiCompany> pageList = bmsFiCompanyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param bmsFiCompany
	 * @return
	 */
	@AutoLog(value = "bms_fi_company-添加")
	@ApiOperation(value="bms_fi_company-添加", notes="bms_fi_company-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BmsFiCompany bmsFiCompany) {
		bmsFiCompanyService.save(bmsFiCompany);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param bmsFiCompany
	 * @return
	 */
	@AutoLog(value = "bms_fi_company-编辑")
	@ApiOperation(value="bms_fi_company-编辑", notes="bms_fi_company-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BmsFiCompany bmsFiCompany) {
		bmsFiCompanyService.updateById(bmsFiCompany);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bms_fi_company-通过id删除")
	@ApiOperation(value="bms_fi_company-通过id删除", notes="bms_fi_company-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		bmsFiCompanyService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "bms_fi_company-批量删除")
	@ApiOperation(value="bms_fi_company-批量删除", notes="bms_fi_company-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.bmsFiCompanyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bms_fi_company-通过id查询")
	@ApiOperation(value="bms_fi_company-通过id查询", notes="bms_fi_company-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BmsFiCompany bmsFiCompany = bmsFiCompanyService.getById(id);
		if(bmsFiCompany==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(bmsFiCompany);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param bmsFiCompany
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BmsFiCompany bmsFiCompany) {
        return super.exportXls(request, bmsFiCompany, BmsFiCompany.class, "bms_fi_company");
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
        return super.importExcel(request, response, BmsFiCompany.class);
    }

}
