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
import com.base.modules.jeeerp.api.entity.VOmsPayplanWq;
import com.base.modules.jeeerp.api.service.IVOmsPayplanWqService;

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
 * @Description: v_oms_payplan_wq
 * @Author: base-boot
 * @Date:   2023-04-14
 * @Version: V1.0
 */
@Api(tags="v_oms_payplan_wq")
@RestController
@RequestMapping("/jeeerp/vOmsPayplanWq")
@Slf4j
public class VOmsPayplanWqController extends BaseController<VOmsPayplanWq, IVOmsPayplanWqService> {
	@Autowired
	private IVOmsPayplanWqService vOmsPayplanWqService;

	/**
	 * 分页列表查询
	 *
	 * @param vOmsPayplanWq
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "v_oms_payplan_wq-分页列表查询")
	@ApiOperation(value="v_oms_payplan_wq-分页列表查询", notes="v_oms_payplan_wq-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(VOmsPayplanWq vOmsPayplanWq,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   @RequestParam(name="ids", required = false) String ids,
								   HttpServletRequest req) {
		QueryWrapper<VOmsPayplanWq> queryWrapper = QueryGenerator.initQueryWrapper(vOmsPayplanWq, req.getParameterMap());
		Page<VOmsPayplanWq> page = new Page<VOmsPayplanWq>(pageNo, pageSize);
		IPage<VOmsPayplanWq> pageList = vOmsPayplanWqService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	  * 分页列表查询
	  *
	  * @param vOmsPayplanWq
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "v_oms_payplan_wq-分页列表查询")
	 @ApiOperation(value="v_oms_payplan_wq-分页列表查询", notes="v_oms_payplan_wq-分页列表查询")
	 @GetMapping(value = "/list2")
	 public Result<?> queryPageList2(VOmsPayplanWq vOmsPayplanWq,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									@RequestParam(name="ids", required = false) String ids,
									HttpServletRequest req) {
		 QueryWrapper<VOmsPayplanWq> queryWrapper = QueryGenerator.initQueryWrapper(vOmsPayplanWq, req.getParameterMap());
		 if(!StringUtils.isEmpty(ids)){
			 ArrayList<String> arr = new ArrayList<>();
			 String[] split = ids.split(",");
			 for (String s : split) {
				 arr.add(s);
			 }
			 queryWrapper.in("id",arr);
		 }
		 List<VOmsPayplanWq> list = vOmsPayplanWqService.list(queryWrapper);
		 return Result.OK(list);
	 }

	/**
	 *   添加
	 *
	 * @param vOmsPayplanWq
	 * @return
	 */
	@AutoLog(value = "v_oms_payplan_wq-添加")
	@ApiOperation(value="v_oms_payplan_wq-添加", notes="v_oms_payplan_wq-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody VOmsPayplanWq vOmsPayplanWq) {
		vOmsPayplanWqService.save(vOmsPayplanWq);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param vOmsPayplanWq
	 * @return
	 */
	@AutoLog(value = "v_oms_payplan_wq-编辑")
	@ApiOperation(value="v_oms_payplan_wq-编辑", notes="v_oms_payplan_wq-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody VOmsPayplanWq vOmsPayplanWq) {
		vOmsPayplanWqService.updateById(vOmsPayplanWq);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "v_oms_payplan_wq-通过id删除")
	@ApiOperation(value="v_oms_payplan_wq-通过id删除", notes="v_oms_payplan_wq-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		vOmsPayplanWqService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "v_oms_payplan_wq-批量删除")
	@ApiOperation(value="v_oms_payplan_wq-批量删除", notes="v_oms_payplan_wq-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.vOmsPayplanWqService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "v_oms_payplan_wq-通过id查询")
	@ApiOperation(value="v_oms_payplan_wq-通过id查询", notes="v_oms_payplan_wq-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		VOmsPayplanWq vOmsPayplanWq = vOmsPayplanWqService.getById(id);
		if(vOmsPayplanWq==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(vOmsPayplanWq);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vOmsPayplanWq
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VOmsPayplanWq vOmsPayplanWq) {
        return super.exportXls(request, vOmsPayplanWq, VOmsPayplanWq.class, "v_oms_payplan_wq");
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
        return super.importExcel(request, response, VOmsPayplanWq.class);
    }

}
