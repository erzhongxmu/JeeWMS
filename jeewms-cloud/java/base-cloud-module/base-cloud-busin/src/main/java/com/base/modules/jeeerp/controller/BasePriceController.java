package com.base.modules.jeeerp.controller;

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
import com.base.modules.jeeerp.entity.BasePrice;
import com.base.modules.jeeerp.service.IBasePriceService;

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
 * @Description: base_price
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Api(tags="base_price")
@RestController
@RequestMapping("/jeeerp/basePrice")
@Slf4j
public class BasePriceController extends BaseController<BasePrice, IBasePriceService> {
	@Autowired
	private IBasePriceService basePriceService;

	/**
	 * 分页列表查询
	 *
	 * @param basePrice
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "base_price-分页列表查询")
	@ApiOperation(value="base_price-分页列表查询", notes="base_price-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BasePrice basePrice,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BasePrice> queryWrapper = QueryGenerator.initQueryWrapper(basePrice, req.getParameterMap());
		Page<BasePrice> page = new Page<BasePrice>(pageNo, pageSize);
		IPage<BasePrice> pageList = basePriceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param basePrice
	 * @return
	 */
	@AutoLog(value = "base_price-添加")
	@ApiOperation(value="base_price-添加", notes="base_price-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BasePrice basePrice) {
		basePriceService.save(basePrice);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param basePrice
	 * @return
	 */
	@AutoLog(value = "base_price-编辑")
	@ApiOperation(value="base_price-编辑", notes="base_price-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BasePrice basePrice) {
		basePriceService.updateById(basePrice);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "base_price-通过id删除")
	@ApiOperation(value="base_price-通过id删除", notes="base_price-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		basePriceService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "base_price-批量删除")
	@ApiOperation(value="base_price-批量删除", notes="base_price-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.basePriceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "base_price-通过id查询")
	@ApiOperation(value="base_price-通过id查询", notes="base_price-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BasePrice basePrice = basePriceService.getById(id);
		if(basePrice==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(basePrice);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param basePrice
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BasePrice basePrice) {
        return super.exportXls(request, basePrice, BasePrice.class, "base_price");
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
        return super.importExcel(request, response, BasePrice.class);
    }

}
