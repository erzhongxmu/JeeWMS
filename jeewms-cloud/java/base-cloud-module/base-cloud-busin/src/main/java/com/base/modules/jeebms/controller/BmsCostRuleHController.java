package com.base.modules.jeebms.controller;

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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.common.api.vo.Result;

import com.base.common.system.vo.LoginUser;
import com.base.modules.jeebms.entity.BmsCostRuleH;
import com.base.modules.jeebms.entity.BmsCostRuleI;
import com.base.modules.jeebms.service.IBmsCostRuleHService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeebms.service.IBmsCostRuleIService;
import com.base.modules.jeebms.vo.BmsCostRuleHPage;
import com.base.modules.jeewms.pdaapi.ApiEntity;
import com.base.modules.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
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

 /**
 * @Description: bms_cost_rule_h
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Api(tags="bms_cost_rule_h")
@RestController
@RequestMapping("/bms/bmsCostRuleH")
@Slf4j
public class BmsCostRuleHController extends BaseController<BmsCostRuleH, IBmsCostRuleHService> {
	 @Autowired
	 private IBmsCostRuleHService bmsCostRuleHService;
	 @Autowired
	 private IBmsCostRuleIService bmsCostRuleIService;

	/* @Autowired
	 private IBaseSupplyService baseSupplyService;*/

	 @Autowired
	 private GenerateCodeUtil generateCodeUtil;



	 /**
	  * 分页列表查询
	  *
	  * @param bmsCostRuleH
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "计费规则主-分页列表查询")
	 @ApiOperation(value="计费规则主-分页列表查询", notes="计费规则主-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(BmsCostRuleH bmsCostRuleH,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<BmsCostRuleH> queryWrapper = QueryGenerator.initQueryWrapper(bmsCostRuleH, req.getParameterMap());
		 Page<BmsCostRuleH> page = new Page<BmsCostRuleH>(pageNo, pageSize);
		 IPage<BmsCostRuleH> pageList = bmsCostRuleHService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  *   添加
	  *
	  * @param bmsCostRuleHPage
	  * @return
	  */
	 @AutoLog(value = "计费规则主-添加")
	 @ApiOperation(value="计费规则主-添加", notes="计费规则主-添加")
	 @PostMapping(value = "/add")
	 public Result<?> add(@RequestBody BmsCostRuleHPage bmsCostRuleHPage) {
		 BmsCostRuleH bmsCostRuleH = new BmsCostRuleH();
		 BeanUtils.copyProperties(bmsCostRuleHPage, bmsCostRuleH);
		 bmsCostRuleHService.saveMain(bmsCostRuleH, bmsCostRuleHPage.getBmsCostRuleIList());
		 return Result.OK("添加成功！");
	 }

	 /**
	  *  编辑
	  *
	  * @param bmsCostRuleHPage
	  * @return
	  */
	 @AutoLog(value = "计费规则主-编辑")
	 @ApiOperation(value="计费规则主-编辑", notes="计费规则主-编辑")
	 @PutMapping(value = "/edit")
	 public Result<?> edit(@RequestBody BmsCostRuleHPage bmsCostRuleHPage) {
		 BmsCostRuleH bmsCostRuleH = new BmsCostRuleH();
		 BeanUtils.copyProperties(bmsCostRuleHPage, bmsCostRuleH);
		 BmsCostRuleH bmsCostRuleHEntity = bmsCostRuleHService.getById(bmsCostRuleH.getId());
		 if(bmsCostRuleHEntity==null) {
			 return Result.error("未找到对应数据");
		 }
		 bmsCostRuleHService.updateMain(bmsCostRuleH, bmsCostRuleHPage.getBmsCostRuleIList());
		 return Result.OK("编辑成功!");
	 }

	 /**
	  *   通过id删除
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "计费规则主-通过id删除")
	 @ApiOperation(value="计费规则主-通过id删除", notes="计费规则主-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		 bmsCostRuleHService.delMain(id);
		 return Result.OK("删除成功!");
	 }

	 /**
	  *  批量删除
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "计费规则主-批量删除")
	 @ApiOperation(value="计费规则主-批量删除", notes="计费规则主-批量删除")
	 @DeleteMapping(value = "/deleteBatch")
	 public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		 this.bmsCostRuleHService.delBatchMain(Arrays.asList(ids.split(",")));
		 return Result.OK("批量删除成功！");
	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "计费规则主-通过id查询")
	 @ApiOperation(value="计费规则主-通过id查询", notes="计费规则主-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		 BmsCostRuleH bmsCostRuleH = bmsCostRuleHService.getById(id);
		 if(bmsCostRuleH==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(bmsCostRuleH);

	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "计费规则子-通过主表ID查询")
	 @ApiOperation(value="计费规则子-通过主表ID查询", notes="计费规则子-通过主表ID查询")
	 @GetMapping(value = "/queryBmsCostRuleIByMainId")
	 public Result<?> queryBmsCostRuleIListByMainId(@RequestParam(name="id",required=true) String id) {
		 List<BmsCostRuleI> bmsCostRuleIList = bmsCostRuleIService.selectByMainId(id);
		 IPage <BmsCostRuleI> page = new Page<>();
		 page.setRecords(bmsCostRuleIList);
		 page.setTotal(bmsCostRuleIList.size());
		 return Result.OK(page);
	 }

	 /**
	  * 导出excel
	  *
	  * @param request
	  * @param bmsCostRuleH
	  */
	 @RequestMapping(value = "/exportXls")
	 public ModelAndView exportXls(HttpServletRequest request, BmsCostRuleH bmsCostRuleH) {
		 // Step.1 组装查询条件查询数据
		 QueryWrapper<BmsCostRuleH> queryWrapper = QueryGenerator.initQueryWrapper(bmsCostRuleH, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 //Step.2 获取导出数据
		 List<BmsCostRuleH> queryList = bmsCostRuleHService.list(queryWrapper);
		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 List<BmsCostRuleH> bmsCostRuleHList = new ArrayList<BmsCostRuleH>();
		 if(oConvertUtils.isEmpty(selections)) {
			 bmsCostRuleHList = queryList;
		 }else {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 bmsCostRuleHList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 }

		 // Step.3 组装pageList
		 List<BmsCostRuleHPage> pageList = new ArrayList<BmsCostRuleHPage>();
		 for (BmsCostRuleH main : bmsCostRuleHList) {
			 BmsCostRuleHPage vo = new BmsCostRuleHPage();
			 BeanUtils.copyProperties(main, vo);
			 List<BmsCostRuleI> bmsCostRuleIList = bmsCostRuleIService.selectByMainId(main.getCostNo());
			 vo.setBmsCostRuleIList(bmsCostRuleIList);
			 pageList.add(vo);
		 }

		 // Step.4 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "计费规则主列表");
		 mv.addObject(NormalExcelConstants.CLASS, BmsCostRuleHPage.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("计费规则主数据", "导出人:"+sysUser.getRealname(), "计费规则主"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		 return mv;
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
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<BmsCostRuleHPage> list = ExcelImportUtil.importExcel(file.getInputStream(), BmsCostRuleHPage.class, params);
				 for (BmsCostRuleHPage page : list) {
					 BmsCostRuleH po = new BmsCostRuleH();
					 BeanUtils.copyProperties(page, po);
					 bmsCostRuleHService.saveMain(po, page.getBmsCostRuleIList());
				 }
				 return Result.OK("文件导入成功！数据行数:" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(),e);
				 return Result.error("文件导入失败:"+e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.OK("文件导入失败！");
	 }


	 /**
	  * 计费复制
	  * @param
	  * @return
	  */
	 @AutoLog(value = "计费复制")
	 @ApiOperation(value="计费复制", notes="计费复制")
	 @PostMapping(value = "/jfcopy")
	 public Result<?> jfcopy(@RequestBody ApiEntity apiEntity) {
		 //源客户id
		 String query01 = apiEntity.getQuery01();
		 //计费规则id
		 String query02 = apiEntity.getQuery02();
		 //目标id客户
		 String query03 = apiEntity.getQuery03();
		 //计费规则id数组
		 String[] split = query02.split(",");
		 //目标客户id数组
		 String[] split2 = query03.split(",");
		 List<BmsCostRuleH> bmsCostRuleHLsit = new ArrayList<>();
		 List<BmsCostRuleI> bmsCostRuleILsit = new ArrayList<>();
		 //todo 缺少客户
		/* for (String s : split2) {
			 LambdaQueryWrapper<BaseSupply> qw = new LambdaQueryWrapper<>();
			 qw.eq(BaseSupply::getId,s);
			 BaseSupply baseSupply = baseSupplyService.getOne(qw);
			 for (String s1 : split) {
				 LambdaQueryWrapper<BmsCostRuleH> qw2 = new LambdaQueryWrapper<>();
				 qw2.eq(BmsCostRuleH::getId,s1);
				 //主表
				 BmsCostRuleH bmsCostRuleH = bmsCostRuleHService.getOne(qw2);
				 //子表
				 LambdaQueryWrapper<BmsCostRuleI> qw3 = new LambdaQueryWrapper<>();
				 qw3.eq(BmsCostRuleI::getContNo,bmsCostRuleH.getContNo());
				 List<BmsCostRuleI> list = bmsCostRuleIService.list(qw3);
				 String code = generateCodeUtil.generateCode(CommonConstant.TABLE_NAME_BMS_COST_RULEH,"JF");
				 bmsCostRuleH.setContNo(code);
				 bmsCostRuleH.setId(null);
				 bmsCostRuleH.setCreateTime(null);
				 bmsCostRuleH.setCreateBy(null);
				 bmsCostRuleH.setUpdateBy(null);
				 bmsCostRuleH.setUpdateTime(null);
				 bmsCostRuleH.setCostObjNo(baseSupply.getSupplyNo());
				 bmsCostRuleH.setCostObjName(baseSupply.getSupplyZhName());
				 bmsCostRuleH.setCostObjName(baseSupply.getSupplyZhName());
				 bmsCostRuleH.setCostObjType(apiEntity.getQuery04());
				 for (BmsCostRuleI bmsCostRuleI : list) {
					 bmsCostRuleI.setId(null);
					 bmsCostRuleI.setCreateTime(null);
					 bmsCostRuleI.setCreateBy(null);
					 bmsCostRuleI.setUpdateBy(null);
					 bmsCostRuleI.setUpdateTime(null);
					 bmsCostRuleI.setContNo(bmsCostRuleH.getContNo());
				 }
				 bmsCostRuleHLsit.add(bmsCostRuleH);
				 bmsCostRuleILsit.addAll(list);
//				 bmsCostRuleHService.saveMain(bmsCostRuleH,list);
			 }
		 }*/
		 bmsCostRuleHService.saveBatch(bmsCostRuleHLsit);
		 bmsCostRuleIService.saveBatch(bmsCostRuleILsit);
		 return Result.OK();
	 }

}
