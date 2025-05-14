package com.base.modules.jeeerp.controller;

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
import javax.transaction.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.common.api.vo.Result;
import com.base.common.util.DateUtils;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeeerp.entity.BusiOrdPrice;
import com.base.modules.jeeerp.service.IBusiOrdPriceService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeeerp.vo.BusiOrdPriceSalary;
import com.base.modules.jeeerp.vo.BusiOrdPriceSalary2;
import com.base.modules.jeeerp.vo.BusiOrdPriceSalary3;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.entity.WmOmNoticeH;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.base.modules.jeewms.service.IMdCusService;
import com.base.modules.jeewms.vo.WmOmNoticeHPage;
import com.base.modules.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.system.entity.SysUser;
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
 * @Description: busi_ord_price
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Api(tags="busi_ord_price")
@RestController
@RequestMapping("/jeeerp/busiOrdPrice")
@Slf4j
public class BusiOrdPriceController extends BaseController<BusiOrdPrice, IBusiOrdPriceService> {
	@Autowired
	private IBusiOrdPriceService busiOrdPriceService;

	 @Autowired
	 private GenerateCodeUtil generateCodeUtil;

	 @Autowired
	 private IMdCusService mdCusService;
	/**
	 * 分页列表查询
	 *
	 * @param busiOrdPrice
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "busi_ord_price-分页列表查询")
	@ApiOperation(value="busi_ord_price-分页列表查询", notes="busi_ord_price-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BusiOrdPrice busiOrdPrice,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String attr5 = busiOrdPrice.getAttr5();
		if(busiOrdPrice.getQuery01().contains("QTFY") || busiOrdPrice.getQuery01().contains("QCFY")){
			busiOrdPrice.setAttr5(null);
		}
		QueryWrapper<BusiOrdPrice> queryWrapper = QueryGenerator.initQueryWrapper(busiOrdPrice, req.getParameterMap());
		if((busiOrdPrice.getQuery01().contains("QTFY") || busiOrdPrice.getQuery01().contains("QCFY")) && StringUtils.isNotBlank(attr5)){
			QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
			MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,attr5);
			MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
			attr5 = attr5 + "," + one2.getZhongWenQch();
			queryWrapper.lambda().in(BusiOrdPrice::getQuery10,Arrays.asList(attr5.split(",")));
		}
		Page<BusiOrdPrice> page = new Page<BusiOrdPrice>(pageNo, pageSize);
		IPage<BusiOrdPrice> pageList = busiOrdPriceService.page(page, queryWrapper);
		// 其他费用查询客户名称
		if(busiOrdPrice.getQuery01().contains("QTFY") || busiOrdPrice.getQuery01().contains("QCFY")){
			for (BusiOrdPrice record : pageList.getRecords()) {
				QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
				MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,record.getQuery10());
				MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
				if(one2 != null){
					record.setQuery10(one2.getZhongWenQch());
				}
			}
		}
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param busiOrdPrice
	 * @return
	 */
	@AutoLog(value = "busi_ord_price-添加")
	@ApiOperation(value="busi_ord_price-添加", notes="busi_ord_price-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BusiOrdPrice busiOrdPrice) {
		String code = "";
		if("QTFY".equals(busiOrdPrice.getQuery01())){
			code = generateCodeUtil.generateCode("busi_ord_price","QTFY");
			busiOrdPrice.setQuery05(code);
		}
		if("YGGZ".equals(busiOrdPrice.getQuery01())){
			code = generateCodeUtil.generateCode("busi_ord_price","YGGZ");
			busiOrdPrice.setQuery08(code);
		}
		if("QCFY".equals(busiOrdPrice.getQuery01())){
			code = generateCodeUtil.generateCode("busi_ord_price","QCFY");
			busiOrdPrice.setQuery05(code);
		}
		busiOrdPriceService.save(busiOrdPrice);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param busiOrdPrice
	 * @return
	 */
	@AutoLog(value = "busi_ord_price-编辑")
	@ApiOperation(value="busi_ord_price-编辑", notes="busi_ord_price-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BusiOrdPrice busiOrdPrice) {
		busiOrdPriceService.updateById(busiOrdPrice);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_ord_price-通过id删除")
	@ApiOperation(value="busi_ord_price-通过id删除", notes="busi_ord_price-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		busiOrdPriceService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "busi_ord_price-批量删除")
	@ApiOperation(value="busi_ord_price-批量删除", notes="busi_ord_price-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.busiOrdPriceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_ord_price-通过id查询")
	@ApiOperation(value="busi_ord_price-通过id查询", notes="busi_ord_price-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BusiOrdPrice busiOrdPrice = busiOrdPriceService.getById(id);
		if(busiOrdPrice==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(busiOrdPrice);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param busiOrdPrice
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BusiOrdPrice busiOrdPrice) {
        return super.exportXls(request, busiOrdPrice, BusiOrdPrice.class, "busi_ord_price");
    }
   /**
    * 导出excel
    *
    * @param request
    * @param busiOrdPrice
    */
    @RequestMapping(value = "/exportXlssal")
    public ModelAndView exportXlssal(HttpServletRequest request, BusiOrdPrice busiOrdPrice) {

		// Step.1 组装查询条件查询数据
		QueryWrapper<BusiOrdPrice> queryWrapper = QueryGenerator.initQueryWrapper(busiOrdPrice, request.getParameterMap());

		//Step.2 获取导出数据
		List<BusiOrdPrice> queryList = busiOrdPriceService.list(queryWrapper);
		// 过滤选中数据
		String selections = request.getParameter("selections");
		List<BusiOrdPrice> list = new ArrayList<BusiOrdPrice>();
		if (StringUtils.isEmpty(selections)) {
			list = queryList;
		} else {
			List<String> selectionList = Arrays.asList(selections.split(","));
			list = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		}
		List<BusiOrdPriceSalary> listexp = new ArrayList<BusiOrdPriceSalary>();
		List<BusiOrdPriceSalary2> busiordpricesalary2 = new ArrayList<BusiOrdPriceSalary2>();
		List<BusiOrdPriceSalary3> busiordpricesalary3 = new ArrayList<BusiOrdPriceSalary3>();
		for (BusiOrdPrice ordPrice : list) {
			if(busiOrdPrice.getQuery01().contains("YGGZ")){
				BusiOrdPriceSalary sal = new BusiOrdPriceSalary();
				BeanUtils.copyProperties(ordPrice, sal);
				listexp.add(sal);
			}else if(busiOrdPrice.getQuery01().contains("QTFY")){
				BusiOrdPriceSalary2 sal = new BusiOrdPriceSalary2();
				BeanUtils.copyProperties(ordPrice, sal);
				QueryWrapper<MdCus> w1 =new QueryWrapper<>();
				w1.lambda().eq(MdCus::getKeHuBianMa,sal.getQuery10());
				MdCus one = mdCusService.getOne(w1, false);
				if(one != null){
					sal.setQuery10(one.getZhongWenQch());
				}
				busiordpricesalary2.add(sal);
			}else if (busiOrdPrice.getQuery01().contains("QCFY")){
				BusiOrdPriceSalary3 sal = new BusiOrdPriceSalary3();
				BeanUtils.copyProperties(ordPrice, sal);
				QueryWrapper<MdCus> w1 =new QueryWrapper<>();
				w1.lambda().eq(MdCus::getKeHuBianMa,sal.getQuery10());
				MdCus one = mdCusService.getOne(w1, false);
				if(one != null){
					sal.setQuery10(one.getZhongWenQch());
				}
				busiordpricesalary3.add(sal);
			}

		}

		// Step.4 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		if(busiOrdPrice.getQuery01().contains("YGGZ")){
			mv.addObject(NormalExcelConstants.FILE_NAME, "salary");
			mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("salary", "exportTime:" + DateUtils.getDataString(DateUtils.yyyymmddhhmmss.get()), "salsry"));
			mv.addObject(NormalExcelConstants.CLASS, BusiOrdPriceSalary.class);
			mv.addObject(NormalExcelConstants.DATA_LIST, listexp);
		}else if(busiOrdPrice.getQuery01().contains("QTFY")){
			mv.addObject(NormalExcelConstants.FILE_NAME, "EXPENSE");
			mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("EXPENSE", "exportTime:" + DateUtils.getDataString(DateUtils.yyyymmddhhmmss.get()), "EXPENSE"));
			mv.addObject(NormalExcelConstants.CLASS, BusiOrdPriceSalary2.class);
			mv.addObject(NormalExcelConstants.DATA_LIST, busiordpricesalary2);
		}else if(busiOrdPrice.getQuery01().contains("QCFY")){
			mv.addObject(NormalExcelConstants.FILE_NAME, "QCEXPENSE");
			mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("QCEXPENSE", "exportTime:" + DateUtils.getDataString(DateUtils.yyyymmddhhmmss.get()), "QCEXPENSE"));
			mv.addObject(NormalExcelConstants.CLASS, BusiOrdPriceSalary3.class);
			mv.addObject(NormalExcelConstants.DATA_LIST, busiordpricesalary3);
		}
		return mv;
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
	@Transactional
    @RequestMapping(value = "/importExcelsal", method = RequestMethod.POST)
    public Result<?> importExcelsal(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			int iw = 0;
			try {
				List<BusiOrdPriceSalary> list = ExcelImportUtil.importExcel(file.getInputStream(), BusiOrdPriceSalary.class, params);
				for (BusiOrdPriceSalary page : list) {
					BusiOrdPrice po = new BusiOrdPrice();
					BeanUtils.copyProperties(page, po);
					po.setQuery01("YGGZ");
					busiOrdPriceService.save(po);
					iw++;
				}
				return Result.ok("文件导入成功！数据行数:" + list.size());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return Result.error("文件导入失败:" + iw);
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Result.ok("文件导入失败！");
    }  /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {


        return super.importExcel(request, response, BusiOrdPrice.class);
    }



/**
 * 修改状态为已确认
 *
 * @param id
 * @return
 */
	 @AutoLog(value = "修改状态为已确认")
	 @ApiOperation(value="修改状态为已确认", notes="修改状态为已确认")
	 @GetMapping(value = "/ConfirmedPayment")
	 public Result<?> ConfirmedPayment(@RequestParam(name="id",required=true) String id) {
		 BusiOrdPrice busiOrdPrice = busiOrdPriceService.getById(id);
		 if(busiOrdPrice==null) {
			 return Result.error("未找到对应数据");
		 }
		 busiOrdPrice.setQuery23("已确认");
		 busiOrdPriceService.updateById(busiOrdPrice);
		 return Result.OK("成功");
	 }


	 /**
	  * 通过excel导入数据
	  *
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping(value = "/importQTFY", method = RequestMethod.POST)
	 public Result<?> importQTFY(HttpServletRequest request, HttpServletResponse response) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<BusiOrdPriceSalary2> list = ExcelImportUtil.importExcel(file.getInputStream(), BusiOrdPriceSalary2.class, params);
				 for (BusiOrdPriceSalary2 page : list) {
					 BusiOrdPrice po = new BusiOrdPrice();
					 BeanUtils.copyProperties(page, po);
					 po.setQuery01("QTFY");
					 if(StringUtils.isEmpty(po.getQuery05())){
						 String code = "";
						 if("QTFY".equals(po.getQuery01())){
							 code = generateCodeUtil.generateCode("busi_ord_price","QTFY");
						 }
						 po.setQuery05(code);
						 busiOrdPriceService.save(po);
					 }
				 }
				 return Result.ok("文件导入成功！数据行数:" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.ok("文件导入失败！");
	 }

	 /**
	  * 通过excel导入数据
	  *
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping(value = "/importQCFY", method = RequestMethod.POST)
	 public Result<?> importQCFY(HttpServletRequest request, HttpServletResponse response) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<BusiOrdPriceSalary3> list = ExcelImportUtil.importExcel(file.getInputStream(), BusiOrdPriceSalary3.class, params);
				 for (BusiOrdPriceSalary3 page : list) {
					 BusiOrdPrice po = new BusiOrdPrice();
					 BeanUtils.copyProperties(page, po);
					 po.setQuery01("QCFY");
					 if(StringUtils.isEmpty(po.getQuery05())){
						 String code = "";
						 if("QCFY".equals(po.getQuery01())){
							 code = generateCodeUtil.generateCode("busi_ord_price","QCFY");
						 }
						 po.setQuery05(code);
						 busiOrdPriceService.save(po);
					 }
				 }
				 return Result.ok("文件导入成功！数据行数:" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.ok("文件导入失败！");
	 }
 }
