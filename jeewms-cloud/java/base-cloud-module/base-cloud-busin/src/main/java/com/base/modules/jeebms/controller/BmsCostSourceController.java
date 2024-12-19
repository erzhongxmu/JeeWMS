package com.base.modules.jeebms.controller;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.common.api.vo.Result;
import com.base.common.util.RedisUtil;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeebms.entity.BmsCostSource;
import com.base.modules.jeebms.service.IBmsCostSourceService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeeerp.service.IBusiOmService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
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
 * @Description: bms_cost_source
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Api(tags="计费来源")
@RestController
@RequestMapping("/bms/bmsCostSource")
@Slf4j
public class BmsCostSourceController extends BaseController<BmsCostSource, IBmsCostSourceService> {
	 ExecutorService executor = Executors.newFixedThreadPool(8);
	 @Autowired
	 private IBmsCostSourceService bmsCostSourceService;

	 @Autowired
	 private BmsApiController bmsApiController;

	 @Autowired
	 private RedisUtil redisUtil;
	 @Autowired
	 private IBusiOmService busiOmService;

	 /**
	  * 分页列表查询
	  *
	  * @param bmsCostSource
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "计费来源-分页列表查询")
	 @ApiOperation(value="计费来源-分页列表查询", notes="计费来源-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(BmsCostSource bmsCostSource,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<BmsCostSource> queryWrapper = QueryGenerator.initQueryWrapper(bmsCostSource, req.getParameterMap());
		 Page<BmsCostSource> page = new Page<BmsCostSource>(pageNo, pageSize);
		 IPage<BmsCostSource> pageList = bmsCostSourceService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  *   添加
	  *
	  * @param bmsCostSource
	  * @return
	  */
	 @AutoLog(value = "计费来源-添加")
	 @ApiOperation(value="计费来源-添加", notes="计费来源-添加")
	 @PostMapping(value = "/add")
	 public Result<?> add(@RequestBody BmsCostSource bmsCostSource) {
		 bmsCostSourceService.save(bmsCostSource);
		 return Result.OK("添加成功！");
	 }

	 /**
	  *  编辑
	  *
	  * @param bmsCostSource
	  * @return
	  */
	 @AutoLog(value = "计费来源-编辑")
	 @ApiOperation(value="计费来源-编辑", notes="计费来源-编辑")
	 @PutMapping(value = "/edit")
	 public Result<?> edit(@RequestBody BmsCostSource bmsCostSource) {
		 bmsCostSourceService.updateById(bmsCostSource);
		 return Result.OK("编辑成功!");
	 }

	 /**
	  *   通过id删除
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "计费来源-通过id删除")
	 @ApiOperation(value="计费来源-通过id删除", notes="计费来源-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		 bmsCostSourceService.removeById(id);
		 return Result.OK("删除成功!");
	 }

	 /**
	  *  批量删除
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "计费来源-批量删除")
	 @ApiOperation(value="计费来源-批量删除", notes="计费来源-批量删除")
	 @DeleteMapping(value = "/deleteBatch")
	 public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		 this.bmsCostSourceService.removeByIds(Arrays.asList(ids.split(",")));
		 return Result.OK("批量删除成功!");
	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "计费来源-通过id查询")
	 @ApiOperation(value="计费来源-通过id查询", notes="计费来源-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		 BmsCostSource bmsCostSource = bmsCostSourceService.getById(id);
		 if(bmsCostSource==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(bmsCostSource);
	 }


	 /**
	  * 通过id修改状态
	  *
	  * @return
	  */
	 @AutoLog(value = "通过id修改状态")
	 @ApiOperation(value="通过id修改状态", notes="通过id修改状态")
	 @PostMapping(value = "/queryByIdpl")
	 public Result<?> queryByIdpl(@RequestBody BmsCostSource bmsCostSource
	 ){
		 LambdaQueryWrapper<BmsCostSource> qw = new LambdaQueryWrapper<>();
		 qw.in(BmsCostSource::getId,bmsCostSource.getId());
		 BmsCostSource bmsCostSource1 = bmsCostSourceService.getOne(qw, false);
		 bmsCostSource1.setStatus("未计算");
		 bmsCostSource1.setCostSoSum(bmsCostSource.getCostSoSum());
		 bmsCostSourceService.updateById(bmsCostSource1);
		 return Result.OK("ok");
	 }
	 /**
	  * 导出excel
	  *
	  * @param request
	  * @param bmsCostSource
	  */
	 @RequestMapping(value = "/exportXls")
	 public ModelAndView exportXls(HttpServletRequest request, BmsCostSource bmsCostSource) {
		 return super.exportXls(request, bmsCostSource, BmsCostSource.class, "计费来源");
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
		 return super.importExcel(request, response, BmsCostSource.class);
	 }

}
