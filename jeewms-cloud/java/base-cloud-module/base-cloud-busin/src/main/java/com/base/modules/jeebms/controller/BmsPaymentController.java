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
import com.base.modules.jeebms.entity.BmsPayment;
import com.base.modules.jeebms.service.IBmsPaymentService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.util.CommonConstant;
import com.base.modules.util.GenerateCodeUtil;
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
 * @Description: bms_payment
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Api(tags="付款记录")
@RestController
@RequestMapping("/bms/bmsPayment")
@Slf4j
public class BmsPaymentController extends BaseController<BmsPayment, IBmsPaymentService> {
	 @Autowired
	 private IBmsPaymentService bmsPaymentService;
	 @Autowired
	 private GenerateCodeUtil generateCodeUtil;
	 /**
	  * 分页列表查询
	  *
	  * @param bmsPayment
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "付款记录-分页列表查询")
	 @ApiOperation(value="付款记录-分页列表查询", notes="付款记录-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(BmsPayment bmsPayment,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<BmsPayment> queryWrapper = QueryGenerator.initQueryWrapper(bmsPayment, req.getParameterMap());
		 Page<BmsPayment> page = new Page<BmsPayment>(pageNo, pageSize);
		 IPage<BmsPayment> pageList = bmsPaymentService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  *   添加
	  *
	  * @param bmsPayment
	  * @return
	  */
	 @AutoLog(value = "付款记录-添加")
	 @ApiOperation(value="付款记录-添加", notes="付款记录-添加")
	 @PostMapping(value = "/add")
	 public Result<?> add(@RequestBody BmsPayment bmsPayment) {
		 String payNo    = generateCodeUtil.generateCode(CommonConstant.TABLE_NAME_BMS_PAY,CommonConstant.BASE_DEFAULT_CODE_TYPE_10);
		 bmsPayment.setPaymetNo(payNo);
		 bmsPaymentService.save(bmsPayment);
		 return Result.OK("添加成功！");
	 }

	 /**
	  *  编辑
	  *
	  * @param bmsPayment
	  * @return
	  */
	 @AutoLog(value = "付款记录-编辑")
	 @ApiOperation(value="付款记录-编辑", notes="付款记录-编辑")
	 @PutMapping(value = "/edit")
	 public Result<?> edit(@RequestBody BmsPayment bmsPayment) {
		 bmsPaymentService.updateById(bmsPayment);
		 return Result.OK("编辑成功!");
	 }

	 /**
	  *   通过id删除
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "付款记录-通过id删除")
	 @ApiOperation(value="付款记录-通过id删除", notes="付款记录-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		 bmsPaymentService.removeById(id);
		 return Result.OK("删除成功!");
	 }

	 /**
	  *  批量删除
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "付款记录-批量删除")
	 @ApiOperation(value="付款记录-批量删除", notes="付款记录-批量删除")
	 @DeleteMapping(value = "/deleteBatch")
	 public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		 this.bmsPaymentService.removeByIds(Arrays.asList(ids.split(",")));
		 return Result.OK("批量删除成功!");
	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "付款记录-通过id查询")
	 @ApiOperation(value="付款记录-通过id查询", notes="付款记录-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		 BmsPayment bmsPayment = bmsPaymentService.getById(id);
		 if(bmsPayment==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(bmsPayment);
	 }

	 /**
	  * 导出excel
	  *
	  * @param request
	  * @param bmsPayment
	  */
	 @RequestMapping(value = "/exportXls")
	 public ModelAndView exportXls(HttpServletRequest request, BmsPayment bmsPayment) {
		 return super.exportXls(request, bmsPayment, BmsPayment.class, "付款记录");
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
		 return super.importExcel(request, response, BmsPayment.class);
	 }

}
