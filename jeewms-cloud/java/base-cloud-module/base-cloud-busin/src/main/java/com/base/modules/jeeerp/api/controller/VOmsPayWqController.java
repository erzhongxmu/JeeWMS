package com.base.modules.jeeerp.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.base.common.api.vo.Result;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeeerp.api.entity.VOmsPayWq;
import com.base.modules.jeeerp.api.entity.VOmsPayplanWq;
import com.base.modules.jeeerp.api.service.IVOmsPayWqService;

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
 * @Description: v_oms_pay_wq
 * @Author: base-boot
 * @Date:   2023-04-14
 * @Version: V1.0
 */
@Api(tags="v_oms_pay_wq")
@RestController
@RequestMapping("/jeeerp/vOmsPayWq")
@Slf4j
public class VOmsPayWqController extends BaseController<VOmsPayWq, IVOmsPayWqService> {
	@Autowired
	private IVOmsPayWqService vOmsPayWqService;

	/**
	 * 分页列表查询
	 *
	 * @param vOmsPayWq
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "v_oms_pay_wq-分页列表查询")
	@ApiOperation(value="v_oms_pay_wq-分页列表查询", notes="v_oms_pay_wq-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(VOmsPayWq vOmsPayWq,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   @RequestParam(name="ids", required = false) String ids,
								   HttpServletRequest req) {
		QueryWrapper<VOmsPayWq> queryWrapper = QueryGenerator.initQueryWrapper(vOmsPayWq, req.getParameterMap());
		Page<VOmsPayWq> page = new Page<VOmsPayWq>(pageNo, pageSize);
		IPage<VOmsPayWq> pageList = vOmsPayWqService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 分页列表查询
	  *
	  * @param vOmsPayWq
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "v_oms_pay_wq-分页列表查询")
	 @ApiOperation(value="v_oms_pay_wq-分页列表查询", notes="v_oms_pay_wq-分页列表查询")
	 @GetMapping(value = "/list2")
	 public Result<?> queryPageList2(VOmsPayWq vOmsPayWq,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									@RequestParam(name="ids", required = false) String ids,
									HttpServletRequest req) {
		 QueryWrapper<VOmsPayWq> queryWrapper = QueryGenerator.initQueryWrapper(vOmsPayWq, req.getParameterMap());
		 if(!StringUtils.isEmpty(ids)){
			 ArrayList<String> arr = new ArrayList<>();
			 String[] split = ids.split(",");
			 for (String s : split) {
				 arr.add(s);
			 }
			 queryWrapper.lambda().in(VOmsPayWq::getId,arr);
		 }
		 Page<VOmsPayWq> page = new Page<VOmsPayWq>(pageNo, pageSize);
		 List<VOmsPayWq> list = vOmsPayWqService.list(queryWrapper);
		 return Result.OK(list);
	 }

	/**
	 *   添加
	 *
	 * @param vOmsPayWq
	 * @return
	 */
	@AutoLog(value = "v_oms_pay_wq-添加")
	@ApiOperation(value="v_oms_pay_wq-添加", notes="v_oms_pay_wq-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody VOmsPayWq vOmsPayWq) {
		vOmsPayWqService.save(vOmsPayWq);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param vOmsPayWq
	 * @return
	 */
	@AutoLog(value = "v_oms_pay_wq-编辑")
	@ApiOperation(value="v_oms_pay_wq-编辑", notes="v_oms_pay_wq-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody VOmsPayWq vOmsPayWq) {
		vOmsPayWqService.updateById(vOmsPayWq);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "v_oms_pay_wq-通过id删除")
	@ApiOperation(value="v_oms_pay_wq-通过id删除", notes="v_oms_pay_wq-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		vOmsPayWqService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "v_oms_pay_wq-批量删除")
	@ApiOperation(value="v_oms_pay_wq-批量删除", notes="v_oms_pay_wq-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.vOmsPayWqService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "v_oms_pay_wq-通过id查询")
	@ApiOperation(value="v_oms_pay_wq-通过id查询", notes="v_oms_pay_wq-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		VOmsPayWq vOmsPayWq = vOmsPayWqService.getById(id);
		if(vOmsPayWq==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(vOmsPayWq);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vOmsPayWq
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VOmsPayWq vOmsPayWq) {
        return super.exportXls(request, vOmsPayWq, VOmsPayWq.class, "v_oms_pay_wq");
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
        return super.importExcel(request, response, VOmsPayWq.class);
    }

}
