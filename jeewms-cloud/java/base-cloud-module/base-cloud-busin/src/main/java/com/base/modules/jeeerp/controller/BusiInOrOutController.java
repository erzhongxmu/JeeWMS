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
import com.base.modules.jeeerp.entity.BusiInOrOut;
import com.base.modules.jeeerp.service.IBusiInOrOutService;

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
 * @Description: busi_in_or_out
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Api(tags="busi_in_or_out")
@RestController
@RequestMapping("/jeeerp/busiInOrOut")
@Slf4j
public class BusiInOrOutController extends BaseController<BusiInOrOut, IBusiInOrOutService> {
	@Autowired
	private IBusiInOrOutService busiInOrOutService;

	/**
	 * 分页列表查询
	 *
	 * @param busiInOrOut
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "busi_in_or_out-分页列表查询")
	@ApiOperation(value="busi_in_or_out-分页列表查询", notes="busi_in_or_out-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BusiInOrOut busiInOrOut,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BusiInOrOut> queryWrapper = QueryGenerator.initQueryWrapper(busiInOrOut, req.getParameterMap());
		Page<BusiInOrOut> page = new Page<BusiInOrOut>(pageNo, pageSize);
		IPage<BusiInOrOut> pageList = busiInOrOutService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param busiInOrOut
	 * @return
	 */
	@AutoLog(value = "busi_in_or_out-添加")
	@ApiOperation(value="busi_in_or_out-添加", notes="busi_in_or_out-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BusiInOrOut busiInOrOut) {
		busiInOrOutService.save(busiInOrOut);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param busiInOrOut
	 * @return
	 */
	@AutoLog(value = "busi_in_or_out-编辑")
	@ApiOperation(value="busi_in_or_out-编辑", notes="busi_in_or_out-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BusiInOrOut busiInOrOut) {
		busiInOrOutService.updateById(busiInOrOut);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_in_or_out-通过id删除")
	@ApiOperation(value="busi_in_or_out-通过id删除", notes="busi_in_or_out-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		busiInOrOutService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "busi_in_or_out-批量删除")
	@ApiOperation(value="busi_in_or_out-批量删除", notes="busi_in_or_out-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.busiInOrOutService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_in_or_out-通过id查询")
	@ApiOperation(value="busi_in_or_out-通过id查询", notes="busi_in_or_out-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BusiInOrOut busiInOrOut = busiInOrOutService.getById(id);
		if(busiInOrOut==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(busiInOrOut);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param busiInOrOut
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BusiInOrOut busiInOrOut) {
        return super.exportXls(request, busiInOrOut, BusiInOrOut.class, "busi_in_or_out");
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
        return super.importExcel(request, response, BusiInOrOut.class);
    }

}
