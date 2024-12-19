package com.base.modules.jeeerp.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.modules.jeeerp.entity.TaxClassification;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import com.base.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import com.base.common.api.vo.Result;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeeerp.entity.ConfErpItem;
import com.base.modules.jeeerp.entity.ConfErp;
import com.base.modules.jeeerp.vo.ConfErpPage;
import com.base.modules.jeeerp.service.IConfErpService;
import com.base.modules.jeeerp.service.IConfErpItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

 /**
 * @Description: conf_erp
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Api(tags="conf_erp")
@RestController
@RequestMapping("/jeeerp/confErp")
@Slf4j
public class ConfErpController {
	@Autowired
	private IConfErpService confErpService;
	@Autowired
	private IConfErpItemService confErpItemService;

	/**
	 * 分页列表查询
	 *
	 * @param confErp
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "conf_erp-分页列表查询")
	@ApiOperation(value="conf_erp-分页列表查询", notes="conf_erp-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ConfErp confErp,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ConfErp> queryWrapper = QueryGenerator.initQueryWrapper(confErp, req.getParameterMap());
		Page<ConfErp> page = new Page<ConfErp>(pageNo, pageSize);
		IPage<ConfErp> pageList = confErpService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param confErpPage
	 * @return
	 */
	@AutoLog(value = "conf_erp-添加")
	@ApiOperation(value="conf_erp-添加", notes="conf_erp-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ConfErpPage confErpPage) {
		ConfErp confErp = new ConfErp();
		BeanUtils.copyProperties(confErpPage, confErp);
		confErpService.saveMain(confErp, confErpPage.getConfErpItemList());
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param confErpPage
	 * @return
	 */
	@AutoLog(value = "conf_erp-编辑")
	@ApiOperation(value="conf_erp-编辑", notes="conf_erp-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ConfErpPage confErpPage) {
		ConfErp confErp = new ConfErp();
		BeanUtils.copyProperties(confErpPage, confErp);
		ConfErp confErpEntity = confErpService.getById(confErp.getId());
		if(confErpEntity==null) {
			return Result.error("未找到对应数据");
		}
		confErpService.updateMain(confErp, confErpPage.getConfErpItemList());
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "conf_erp-通过id删除")
	@ApiOperation(value="conf_erp-通过id删除", notes="conf_erp-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		confErpService.delMain(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "conf_erp-批量删除")
	@ApiOperation(value="conf_erp-批量删除", notes="conf_erp-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.confErpService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "conf_erp-通过id查询")
	@ApiOperation(value="conf_erp-通过id查询", notes="conf_erp-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ConfErp confErp = confErpService.getById(id);
		if(confErp==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(confErp);

	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "conf_erp_item通过主表ID查询")
	@ApiOperation(value="conf_erp_item主表ID查询", notes="conf_erp_item-通主表ID查询")
	@GetMapping(value = "/queryConfErpItemByMainId")
	public Result<?> queryConfErpItemListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ConfErpItem> confErpItemList = confErpItemService.selectByMainId(id);
		return Result.OK(confErpItemList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param confErp
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ConfErp confErp) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ConfErp> queryWrapper = QueryGenerator.initQueryWrapper(confErp, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<ConfErp> queryList = confErpService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<ConfErp> confErpList = new ArrayList<ConfErp>();
      if(oConvertUtils.isEmpty(selections)) {
          confErpList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          confErpList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<ConfErpPage> pageList = new ArrayList<ConfErpPage>();
      for (ConfErp main : confErpList) {
          ConfErpPage vo = new ConfErpPage();
          BeanUtils.copyProperties(main, vo);
          List<ConfErpItem> confErpItemList = confErpItemService.selectByMainId(main.getId());
          vo.setConfErpItemList(confErpItemList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "conf_erp列表");
      mv.addObject(NormalExcelConstants.CLASS, ConfErpPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("conf_erp数据", "导出人:"+sysUser.getRealname(), "conf_erp"));
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
              List<ConfErpPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ConfErpPage.class, params);
              for (ConfErpPage page : list) {
                  ConfErp po = new ConfErp();
                  BeanUtils.copyProperties(page, po);
                  confErpService.saveMain(po, page.getConfErpItemList());
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
	  * 导出excel
	  *
	  * @param request
	  * @param confErp
	  */
	 @RequestMapping(value = "/exportXlsTaxClassification")
	 public ModelAndView exportXlsTaxClassification(HttpServletRequest request, ConfErp confErp) {
		 // Step.1 组装查询条件查询数据
		 QueryWrapper<ConfErp> queryWrapper = QueryGenerator.initQueryWrapper(confErp, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 //Step.2 获取导出数据
		 List<ConfErp> queryList = confErpService.list(queryWrapper);
		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 List<ConfErp> confErpList = new ArrayList<ConfErp>();
		 if(oConvertUtils.isEmpty(selections)) {
			 confErpList = queryList;
		 }else {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 confErpList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 }

		 // Step.3 组装pageList
		 List<TaxClassification> pageList = new ArrayList<TaxClassification>();
		 for (ConfErp main : confErpList) {
			 TaxClassification vo = new TaxClassification();
			 BeanUtils.copyProperties(main, vo);
			 List<ConfErpItem> confErpItemList = confErpItemService.selectByMainId(main.getId());
//			 vo.setConfErpItemList(confErpItemList);
			 pageList.add(vo);
		 }

		 // Step.4 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "conf_erp列表");
		 mv.addObject(NormalExcelConstants.CLASS, TaxClassification.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("conf_erp数据", "导出人:"+sysUser.getRealname(), "conf_erp"));
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
	 @RequestMapping(value = "/importExcelTaxClassification", method = RequestMethod.POST)
	 public Result<?> importExcelTaxClassification(HttpServletRequest request, HttpServletResponse response) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<TaxClassification> list = ExcelImportUtil.importExcel(file.getInputStream(), TaxClassification.class, params);
				 for (TaxClassification page : list) {
					 ConfErp po = new ConfErp();
					 page.setQuery01("SHFL");
					 BeanUtils.copyProperties(page, po);
					 confErpService.saveMain(po, null);
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

}
