package com.base.modules.jeewms.controller;

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
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.WmCartonNumber;
import com.base.modules.jeewms.service.IWmCartonNumberService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: wm_carton_number
 * @Author: base-boot
 * @Date:   2022-07-06
 * @Version: V1.0
 */
@Api(tags="wm_carton_number")
@RestController
@RequestMapping("/jeewms/wmCartonNumber")
@Slf4j
public class WmCartonNumberController extends BaseController<WmCartonNumber, IWmCartonNumberService> {
	@Autowired
	private IWmCartonNumberService wmCartonNumberService;

	/**
	 * 分页列表查询
	 *
	 * @param wmCartonNumber
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_carton_number-分页列表查询")
	@ApiOperation(value="wm_carton_number-分页列表查询", notes="wm_carton_number-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmCartonNumber wmCartonNumber,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmCartonNumber> queryWrapper = QueryGenerator.initQueryWrapper(wmCartonNumber, req.getParameterMap());
		Page<WmCartonNumber> page = new Page<WmCartonNumber>(pageNo, pageSize);
		IPage<WmCartonNumber> pageList = wmCartonNumberService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 * @param wmCartonNumber
	 * @return
	 */
	@AutoLog(value = "wm_carton_number-添加")
	@ApiOperation(value="wm_carton_number-添加", notes="wm_carton_number-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmCartonNumber wmCartonNumber) {
		if(StringUtils.isEmpty(wmCartonNumber.getBinId())){
			throw  new JeecgBootException("箱码不能为空");
		}
		Result<?> w = wmCartonNumberService.add(wmCartonNumber);
		return Result.OK(w);
	}

	/**
	 *  编辑
	 *
	 * @param wmCartonNumber
	 * @return
	 */
	@AutoLog(value = "wm_carton_number-编辑")
	@ApiOperation(value="wm_carton_number-编辑", notes="wm_carton_number-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmCartonNumber wmCartonNumber) {
		wmCartonNumber.setStatus(0);
		wmCartonNumberService.updateById(wmCartonNumber);
		return Result.OK("编辑成功!");
	}




	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_carton_number-通过id删除")
	@ApiOperation(value="wm_carton_number-通过id删除", notes="wm_carton_number-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmCartonNumberService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_carton_number-批量删除")
	@ApiOperation(value="wm_carton_number-批量删除", notes="wm_carton_number-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmCartonNumberService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_carton_number-通过id查询")
	@ApiOperation(value="wm_carton_number-通过id查询", notes="wm_carton_number-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmCartonNumber wmCartonNumber = wmCartonNumberService.getById(id);
		if(wmCartonNumber==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmCartonNumber);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmCartonNumber
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmCartonNumber wmCartonNumber) {
        return super.exportXls(request, wmCartonNumber, WmCartonNumber.class, "wm_carton_number");
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
        return super.importExcel(request, response, WmCartonNumber.class);
    }

}
