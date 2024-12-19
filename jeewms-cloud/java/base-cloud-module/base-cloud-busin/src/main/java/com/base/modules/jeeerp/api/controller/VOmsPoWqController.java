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
import com.base.modules.jeeerp.api.entity.VOmsPoWq;
import com.base.modules.jeeerp.api.service.IVOmsPoWqService;

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
 * @Description: v_oms_po_wq
 * @Author: base-boot
 * @Date:   2023-04-14
 * @Version: V1.0
 */
@Api(tags="v_oms_po_wq")
@RestController
@RequestMapping("/jeeerp/vOmsPoWq")
@Slf4j
public class VOmsPoWqController extends BaseController<VOmsPoWq, IVOmsPoWqService> {
	@Autowired
	private IVOmsPoWqService vOmsPoWqService;

	/**
	 * 分页列表查询
	 *
	 * @param vOmsPoWq
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "v_oms_po_wq-分页列表查询")
	@ApiOperation(value="v_oms_po_wq-分页列表查询", notes="v_oms_po_wq-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(VOmsPoWq vOmsPoWq,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   @RequestParam(name="ids", required = false) String ids,
								   HttpServletRequest req) {
		QueryWrapper<VOmsPoWq> queryWrapper = QueryGenerator.initQueryWrapper(vOmsPoWq, req.getParameterMap());
		Page<VOmsPoWq> page = new Page<VOmsPoWq>(pageNo, pageSize);
		IPage<VOmsPoWq> pageList = vOmsPoWqService.page(page, queryWrapper);
		return Result.OK(pageList);
	}


	 /**
	  * 分页列表查询
	  *
	  * @param vOmsPoWq
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "v_oms_po_wq-分页列表查询")
	 @ApiOperation(value="v_oms_po_wq-分页列表查询", notes="v_oms_po_wq-分页列表查询")
	 @GetMapping(value = "/list2")
	 public Result<?> queryPageList2(VOmsPoWq vOmsPoWq,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									@RequestParam(name="ids", required = false) String ids,
									HttpServletRequest req) {
		 QueryWrapper<VOmsPoWq> queryWrapper = QueryGenerator.initQueryWrapper(vOmsPoWq, req.getParameterMap());
		 if(!StringUtils.isEmpty(ids)){
			 ArrayList<String> arr = new ArrayList<>();
			 String[] split = ids.split(",");
			 for (String s : split) {
				 arr.add(s);
			 }
			 queryWrapper.lambda().in(VOmsPoWq::getId,arr);
		 }
		 List<VOmsPoWq> list = vOmsPoWqService.list(queryWrapper);
		 return Result.OK(list);
	 }

	/**
	 *   添加
	 *
	 * @param vOmsPoWq
	 * @return
	 */
	@AutoLog(value = "v_oms_po_wq-添加")
	@ApiOperation(value="v_oms_po_wq-添加", notes="v_oms_po_wq-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody VOmsPoWq vOmsPoWq) {
		vOmsPoWqService.save(vOmsPoWq);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param vOmsPoWq
	 * @return
	 */
	@AutoLog(value = "v_oms_po_wq-编辑")
	@ApiOperation(value="v_oms_po_wq-编辑", notes="v_oms_po_wq-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody VOmsPoWq vOmsPoWq) {
		vOmsPoWqService.updateById(vOmsPoWq);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "v_oms_po_wq-通过id删除")
	@ApiOperation(value="v_oms_po_wq-通过id删除", notes="v_oms_po_wq-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		vOmsPoWqService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "v_oms_po_wq-批量删除")
	@ApiOperation(value="v_oms_po_wq-批量删除", notes="v_oms_po_wq-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.vOmsPoWqService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "v_oms_po_wq-通过id查询")
	@ApiOperation(value="v_oms_po_wq-通过id查询", notes="v_oms_po_wq-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		VOmsPoWq vOmsPoWq = vOmsPoWqService.getById(id);
		if(vOmsPoWq==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(vOmsPoWq);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vOmsPoWq
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VOmsPoWq vOmsPoWq) {
        return super.exportXls(request, vOmsPoWq, VOmsPoWq.class, "v_oms_po_wq");
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
        return super.importExcel(request, response, VOmsPoWq.class);
    }

}
