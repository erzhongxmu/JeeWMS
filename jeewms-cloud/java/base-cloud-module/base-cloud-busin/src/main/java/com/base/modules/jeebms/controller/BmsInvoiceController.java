package com.base.modules.jeebms.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.common.api.vo.Result;

import com.base.common.util.oConvertUtils;
import com.base.modules.jeebms.entity.BmsInvoice;
import com.base.modules.jeebms.entity.BmsPayment;
import com.base.modules.jeebms.service.IBmsInvoiceService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeebms.service.IBmsPaymentService;
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
 * @Description: bms_invoice
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Api(tags="开票记录")
@RestController
@RequestMapping("/bms/bmsInvoice")
@Slf4j
public class BmsInvoiceController extends BaseController<BmsInvoice, IBmsInvoiceService> {
	 @Autowired
	 private IBmsInvoiceService bmsInvoiceService;
	 @Autowired
	 private GenerateCodeUtil generateCodeUtil;
	 @Autowired
	 private IBmsPaymentService bmsPaymentService;
	 /**
	  * 分页列表查询
	  *
	  * @param bmsInvoice
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "开票记录-分页列表查询")
	 @ApiOperation(value="开票记录-分页列表查询", notes="开票记录-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(BmsInvoice bmsInvoice,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<BmsInvoice> queryWrapper = QueryGenerator.initQueryWrapper(bmsInvoice, req.getParameterMap());
		 Page<BmsInvoice> page = new Page<BmsInvoice>(pageNo, pageSize);
		 IPage<BmsInvoice> pageList = bmsInvoiceService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  *   添加
	  *
	  * @param bmsInvoice
	  * @return
	  */
	 @AutoLog(value = "开票记录-添加")
	 @ApiOperation(value="开票记录-添加", notes="开票记录-添加")
	 @PostMapping(value = "/add")
	 public Result<?> add(@RequestBody BmsInvoice bmsInvoice) {
		 String invoiceNo    = generateCodeUtil.generateCode(CommonConstant.TABLE_NAME_BMS_INVOICE, CommonConstant.BASE_DEFAULT_CODE_TYPE_10);
		 bmsInvoice.setInvoiceDocNo(invoiceNo);
		 bmsInvoiceService.save(bmsInvoice);
		 return Result.OK("添加成功！");
	 }

	 /**
	  *  编辑
	  *
	  * @param bmsInvoice
	  * @return
	  */
	 @AutoLog(value = "开票记录-编辑")
	 @ApiOperation(value="开票记录-编辑", notes="开票记录-编辑")
	 @PutMapping(value = "/edit")
	 public Result<?> edit(@RequestBody BmsInvoice bmsInvoice) {
		 bmsInvoiceService.updateById(bmsInvoice);
		 return Result.OK("编辑成功!");
	 }

	 /**
	  *   通过id删除
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "开票记录-通过id删除")
	 @ApiOperation(value="开票记录-通过id删除", notes="开票记录-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		 bmsInvoiceService.removeById(id);
		 return Result.OK("删除成功!");
	 }

	 /**
	  *  批量删除
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "开票记录-批量删除")
	 @ApiOperation(value="开票记录-批量删除", notes="开票记录-批量删除")
	 @DeleteMapping(value = "/deleteBatch")
	 public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		 this.bmsInvoiceService.removeByIds(Arrays.asList(ids.split(",")));
		 return Result.OK("批量删除成功!");
	 }
	 /**
	  *  批量删除
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "付款-批量")
	 @ApiOperation(value="付款-批量", notes="付款-批量")
	 @GetMapping(value = "/payBatch")
	 public Result<?> payBatch(@RequestParam(name = "ids", required = true) String ids,
							   @RequestParam(name = "payNo", required = true) String payNo,
							   @RequestParam(name = "remark", required = true) String remark) {
		 Collection<? extends Serializable> idList = Arrays.asList(ids.split(","));
		 for (Serializable serializable : idList) {
			 BmsInvoice bmsInvoice = bmsInvoiceService.getById(serializable);
			 bmsInvoice.setStatus("1");
			 bmsInvoiceService.updateById(bmsInvoice);
			 LambdaQueryWrapper<BmsPayment> queryWrapper = new LambdaQueryWrapper<>();
			 queryWrapper.eq(BmsPayment::getBankId,payNo);
			 List<BmsPayment> list = bmsPaymentService.list(queryWrapper);
			 if(list!=null&&list.size()>0){
				 continue;
			 }
			 BmsPayment bmsPayment = new BmsPayment();

			 bmsPayment.setCostObjType(bmsInvoice.getCostObjType());
			 bmsPayment.setCostObjNo(bmsInvoice.getCostObjNo());
			 bmsPayment.setCostObjName(bmsInvoice.getCostObjName());
			 String code    = generateCodeUtil.generateCode(CommonConstant.TABLE_NAME_BMS_PAY,CommonConstant.BASE_DEFAULT_CODE_TYPE_10);
			 bmsPayment.setPaymetNo(code);
			 bmsPayment.setPaymetType("10");
			 bmsPayment.setInvoiceDocNo(bmsInvoice.getInvoiceDocNo());
			 bmsPayment.setPaymetSum(bmsInvoice.getInvoiceSum());
			 bmsPayment.setInvoiceSum(bmsInvoice.getInvoiceSum());
			 bmsPayment.setBankId(payNo);
			 bmsPayment.setRemark(remark);
			 bmsPayment.setStatus("0");



			 bmsPaymentService.save(bmsPayment);
		 }
		 return Result.OK("批量删除成功!");
	 }
	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "开票记录-通过id查询")
	 @ApiOperation(value="开票记录-通过id查询", notes="开票记录-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		 BmsInvoice bmsInvoice = bmsInvoiceService.getById(id);
		 if(bmsInvoice==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(bmsInvoice);
	 }

	 /**
	  * 导出excel
	  *
	  * @param request
	  * @param bmsInvoice
	  */
	 @RequestMapping(value = "/exportXls")
	 public ModelAndView exportXls(HttpServletRequest request, BmsInvoice bmsInvoice) {
		 return super.exportXls(request, bmsInvoice, BmsInvoice.class, "开票记录");
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
		 return super.importExcel(request, response, BmsInvoice.class);
	 }

}
