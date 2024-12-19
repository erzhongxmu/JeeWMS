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
import com.base.modules.jeeerp.api.entity.VOmsInvWq;
import com.base.modules.jeeerp.api.entity.VOmsPayplanWq;
import com.base.modules.jeeerp.api.service.IVOmsInvWqService;

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
 * @Description: v_oms_inv_wq
 * @Author: base-boot
 * @Date:   2023-04-14
 * @Version: V1.0
 */
@Api(tags="v_oms_inv_wq")
@RestController
@RequestMapping("/jeeerp/vOmsInvWq")
@Slf4j
public class VOmsInvWqController extends BaseController<VOmsInvWq, IVOmsInvWqService> {
	@Autowired
	private IVOmsInvWqService vOmsInvWqService;

	/**
	 * 分页列表查询
	 *
	 * @param vOmsInvWq
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "v_oms_inv_wq-分页列表查询")
	@ApiOperation(value="v_oms_inv_wq-分页列表查询", notes="v_oms_inv_wq-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(VOmsInvWq vOmsInvWq,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   @RequestParam(name="ids", required = false) String ids,
								   HttpServletRequest req) {
		QueryWrapper<VOmsInvWq> queryWrapper = QueryGenerator.initQueryWrapper(vOmsInvWq, req.getParameterMap());
		Page<VOmsInvWq> page = new Page<VOmsInvWq>(pageNo, pageSize);
		IPage<VOmsInvWq> pageList = vOmsInvWqService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 分页列表查询
	  *
	  * @param vOmsInvWq
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "v_oms_inv_wq-分页列表查询")
	 @ApiOperation(value="v_oms_inv_wq-分页列表查询", notes="v_oms_inv_wq-分页列表查询")
	 @GetMapping(value = "/list2")
	 public Result<?> queryPageList2(VOmsInvWq vOmsInvWq,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									@RequestParam(name="ids", required = false) String ids,
									HttpServletRequest req) {
		 QueryWrapper<VOmsInvWq> queryWrapper = QueryGenerator.initQueryWrapper(vOmsInvWq, req.getParameterMap());
		 if(!StringUtils.isEmpty(ids)){
			 ArrayList<String> arr = new ArrayList<>();
			 String[] split = ids.split(",");
			 for (String s : split) {
				 arr.add(s);
			 }
			 queryWrapper.lambda().in(VOmsInvWq::getId,arr);
		 }
		 List<VOmsInvWq> list = vOmsInvWqService.list(queryWrapper);
		 return Result.OK(list);
	 }

	/**
	 *   添加
	 *
	 * @param vOmsInvWq
	 * @return
	 */
	@AutoLog(value = "v_oms_inv_wq-添加")
	@ApiOperation(value="v_oms_inv_wq-添加", notes="v_oms_inv_wq-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody VOmsInvWq vOmsInvWq) {
		vOmsInvWqService.save(vOmsInvWq);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param vOmsInvWq
	 * @return
	 */
	@AutoLog(value = "v_oms_inv_wq-编辑")
	@ApiOperation(value="v_oms_inv_wq-编辑", notes="v_oms_inv_wq-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody VOmsInvWq vOmsInvWq) {
		vOmsInvWqService.updateById(vOmsInvWq);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "v_oms_inv_wq-通过id删除")
	@ApiOperation(value="v_oms_inv_wq-通过id删除", notes="v_oms_inv_wq-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		vOmsInvWqService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "v_oms_inv_wq-批量删除")
	@ApiOperation(value="v_oms_inv_wq-批量删除", notes="v_oms_inv_wq-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.vOmsInvWqService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "v_oms_inv_wq-通过id查询")
	@ApiOperation(value="v_oms_inv_wq-通过id查询", notes="v_oms_inv_wq-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		VOmsInvWq vOmsInvWq = vOmsInvWqService.getById(id);
		if(vOmsInvWq==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(vOmsInvWq);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vOmsInvWq
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VOmsInvWq vOmsInvWq) {
        return super.exportXls(request, vOmsInvWq, VOmsInvWq.class, "v_oms_inv_wq");
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
        return super.importExcel(request, response, VOmsInvWq.class);
    }

}
