package com.base.modules.jeebms.controller;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;

import com.base.common.system.vo.LoginUser;
import com.base.common.util.DateUtils;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeebms.entity.BmsBillH;
import com.base.modules.jeebms.entity.BmsBillI;
import com.base.modules.jeebms.entity.BmsCostDetail;
import com.base.modules.jeebms.service.IBmsBillHService;
import com.base.modules.jeebms.service.IBmsBillIService;
import com.base.modules.jeebms.service.IBmsCostDetailService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeebms.vo.BmsBillHPage;
import com.base.modules.util.CommonConstant;
import com.base.modules.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import com.base.common.system.base.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

import static com.base.modules.util.CommonConstant.TASK_DAYPLAN_STATUS_WSC;

/**
 * @Description: bms_cost_detail
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Api(tags="费用详情")
@RestController
@RequestMapping("/bms/bmsCostDetail")
@Slf4j
public class BmsCostDetailController extends BaseController<BmsCostDetail, IBmsCostDetailService> {
	 @Autowired
	 private IBmsCostDetailService bmsCostDetailService;

	 /*@Autowired
	 private IActBusinessService actBusinessService;*/
	 @Autowired
	 private IBmsBillHService bmsBillHService;
	 @Autowired
	 private IBmsBillIService bmsBillIService;
	 @Autowired
	 private GenerateCodeUtil generateCodeUtil;
	 /**
	  * 分页列表查询
	  *
	  * @param bmsCostDetail
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "费用详情-分页列表查询")
	 @ApiOperation(value = "费用详情-分页列表查询", notes = "费用详情-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(BmsCostDetail bmsCostDetail,
									@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
									@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<BmsCostDetail> queryWrapper = QueryGenerator.initQueryWrapper(bmsCostDetail, req.getParameterMap());
		 Page<BmsCostDetail> page = new Page<BmsCostDetail>(pageNo, pageSize);
		 IPage<BmsCostDetail> pageList = bmsCostDetailService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  * 添加
	  *
	  * @param bmsCostDetail
	  * @return
	  */
	 @AutoLog(value = "费用详情-添加")
	 @ApiOperation(value = "费用详情-添加", notes = "费用详情-添加")
	 @PostMapping(value = "/add")
	 public Result<?> add(@RequestBody BmsCostDetail bmsCostDetail) {
		 bmsCostDetailService.save(bmsCostDetail);
		 //actBusinessService.startAct(CommonConstant.ACT_LC_KEY_ZDGL, CommonConstant.TABLE_NAME_BMS_COST_DETAIL, bmsCostDetail.getId(), 0,null);
		 return Result.OK("添加成功！");
	 }

	 /**
	  * 编辑
	  *
	  * @param bmsCostDetail
	  * @return
	  */
	 @AutoLog(value = "费用详情-编辑")
	 @ApiOperation(value = "费用详情-编辑", notes = "费用详情-编辑")
	 @PutMapping(value = "/edit")
	 public Result<?> edit(@RequestBody BmsCostDetail bmsCostDetail) {
		 bmsCostDetailService.updateById(bmsCostDetail);
		 return Result.OK("编辑成功!");
	 }

	 /**
	  * 通过id删除
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "费用详情-通过id删除")
	 @ApiOperation(value = "费用详情-通过id删除", notes = "费用详情-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		 bmsCostDetailService.removeById(id);
		 return Result.OK("删除成功!");
	 }

	 /**
	  * 批量删除
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "费用详情-批量删除")
	 @ApiOperation(value = "费用详情-批量删除", notes = "费用详情-批量删除")
	 @DeleteMapping(value = "/deleteBatch")
	 public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		 this.bmsCostDetailService.removeByIds(Arrays.asList(ids.split(",")));
		 return Result.OK("批量删除成功!");
	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "费用详情-通过id查询")
	 @ApiOperation(value = "费用详情-通过id查询", notes = "费用详情-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
		 BmsCostDetail bmsCostDetail = bmsCostDetailService.getById(id);
		 if (bmsCostDetail == null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(bmsCostDetail);
	 }

	 /**
	  * 导出excel
	  *
	  * @param request
	  * @param bmsCostDetail
	  */
	 @RequestMapping(value = "/exportXls")
	 public ModelAndView exportXls(HttpServletRequest request, BmsCostDetail bmsCostDetail) {
		 return super.exportXls(request, bmsCostDetail, BmsCostDetail.class, "费用详情");
	 }

	 /**
	  * 生成账单
	  *
	  * @param request
	  * @param bmsCostDetail
	  */
	 @AutoLog(value = "生成账单")
	 @ApiOperation(value = "生成账单", notes = "生成账单")
	 @RequestMapping(value = "/genbill")
	 public Result<?> genbill(HttpServletRequest request, BmsCostDetail bmsCostDetail) {
		 // Step.1 组装查询条件
		 bmsCostDetail.setStatus(TASK_DAYPLAN_STATUS_WSC);
		 QueryWrapper<BmsCostDetail> queryWrapper = QueryGenerator.initQueryWrapper(bmsCostDetail, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 // Step.2 获取数据
		 List<BmsCostDetail> pageList = bmsCostDetailService.list(queryWrapper);
		 List<BmsCostDetail> exportList = null;
		 // Step.3 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }
		 HashMap<String, String> objMap = new HashMap<>(1024);
		 for (BmsCostDetail costDetail : exportList) {
			 if (!"0".equals(costDetail.getStatus())) {
				 continue;
			 }
			 objMap.put(costDetail.getCostObjNo(), costDetail.getCostObjNo());
		 }
		 for (Map.Entry<String, String> stringStringEntry : objMap.entrySet()) {
			 String costObjNo = stringStringEntry.getValue();
			 String costHb = "";
			 String billPeriod = "";
			 String costObjType = "";
			 String costObjName = "";
			 BigDecimal billSum = new BigDecimal("0");
			 BmsBillHPage bmsBillHPage = new BmsBillHPage();
			 List<BmsBillI> bmsBillIList = new ArrayList<>();
			 for (BmsCostDetail costDetail : exportList) {
				 if (!costObjNo.equals(costDetail.getCostObjNo())) {
					 continue;
				 }
				 // Step.4 生成账单
				 BmsBillI bmsBillI = new BmsBillI();
				 bmsBillI.setCostNo(costDetail.getCostNo());
				 bmsBillI.setCostName(costDetail.getCostName());
				 bmsBillI.setCostDesc(costDetail.getCostDesc());
				 bmsBillI.setCostSoNo(costDetail.getCostSoNo());
				 bmsBillI.setCostSoName(costDetail.getCostSoName());
				 bmsBillI.setCostSoDate(costDetail.getCostSoDate());
				 bmsBillI.setCostSl(costDetail.getCostSl());
				 bmsBillI.setCostHb(costDetail.getCostHb());
				 bmsBillI.setCostCoBhsj(costDetail.getCostCoBhsj());
				 bmsBillI.setCostCoYhsj(costDetail.getCostCoYhsj());
				 bmsBillIList.add(bmsBillI);
				 // Step.5 更新状态
				 costDetail.setStatus("生成账单");
				 bmsCostDetailService.updateById(costDetail);
				 billPeriod = DateUtils.date2Str(costDetail.getCostSoDate(),DateUtils.yyyyMM.get()) ;
				 costObjType= costDetail.getCostObjType();
				 billSum = billSum.add(costDetail.getCostCoYhsj());
				 costObjName = costDetail.getCostObjName();
				 costHb = costDetail.getCostHb();
			 }
			 bmsBillHPage.setBillType("10");
			 bmsBillHPage.setBillPeriod(billPeriod);
			 bmsBillHPage.setCostObjType(costObjType);
			 bmsBillHPage.setCostObjNo(costObjNo);
			 bmsBillHPage.setCostObjName(costObjName);
			 bmsBillHPage.setBillSum(billSum);
			 bmsBillHPage.setCostHb(costHb);
			 bmsBillHPage.setRemark("");
			 bmsBillHPage.setStatus(CommonConstant.TABLE_NAME_BMS_BILL_STATUS_0);
			 bmsBillHPage.setBmsBillIList(bmsBillIList);
			 BmsBillH bmsBillH = new BmsBillH();
			 BeanUtils.copyProperties(bmsBillHPage, bmsBillH);
			 String billNo = generateCodeUtil.generateCode(CommonConstant.TABLE_NAME_BMS_BILL,CommonConstant.BASE_DEFAULT_CODE_TYPE_10);
			 bmsBillH.setBillNo(billNo);
			 bmsBillHService.saveMain(bmsBillH, bmsBillHPage.getBmsBillIList());
		 }

		 return Result.OK(exportList);
	 }


	 /**
	  * 获取对象ID
	  *
	  * @return
	  */
	 private String getId(BmsCostDetail item) {
		 try {
			 return PropertyUtils.getProperty(item, "id").toString();
		 } catch (Exception e) {
			 e.printStackTrace();
			 return null;
		 }
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
		 return super.importExcel(request, response, BmsCostDetail.class);
	 }

}
