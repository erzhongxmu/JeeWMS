package com.base.modules.jeewms.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.MdSup;
import com.base.modules.jeewms.entity.gs.BaPartTypegs;
import com.base.modules.jeewms.entity.gs.MdGoodsgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.base.controller.JeecgController;;
import org.jeecg.common.system.util.JwtUtil;
import com.base.common.system.vo.LoginUser;
import com.base.modules.jeewms.entity.BaPartType;
import com.base.modules.jeewms.service.IBaPartTypeService;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 商品分类
 * @Author: base-boot
 * @Date:   2021-06-07
 * @Version: V1.0
 */
@Api(tags="商品分类")
@RestController
@RequestMapping("/jeewms/baPartType")
@Slf4j
public class BaPartTypeController extends JeecgController<BaPartType, IBaPartTypeService> {
	@Autowired
	private IBaPartTypeService baPartTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param baPartType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "商品分类-分页列表查询")
	@ApiOperation(value="商品分类-分页列表查询", notes="商品分类-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaPartType baPartType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaPartType> queryWrapper = QueryGenerator.initQueryWrapper(baPartType, req.getParameterMap());
		Page<BaPartType> page = new Page<BaPartType>(pageNo, pageSize);
		IPage<BaPartType> pageList = baPartTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
	  * 下拉列表
	  *
	  * @param
	  * @return
	  */
	 @ApiOperation(value="商品分类-下拉列表", notes="商品分类-下拉列表")
	 @GetMapping(value = "/getSelectList")
	 public Result<?> getSelectList() {
		 return Result.ok(baPartTypeService.list());
	 }

	/**
	 *   添加
	 *
	 * @param baPartType
	 * @return
	 */
	@AutoLog(value = "商品分类-添加")
	@ApiOperation(value="商品分类-添加", notes="商品分类-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody @Valid BaPartType baPartType) {
		//检验数字
		/*try {
			Double.parseDouble(baPartType.getStackLimit());
			Double.parseDouble(baPartType.getMaxStock());
		}catch (Exception e) {
			return Result.error("堆码极限、最大库存量必须为数字类型");
		}*/
		if (baPartTypeService.lambdaQuery().eq(BaPartType::getAttr4,baPartType.getAttr4()).count() > 0) {
			throw new JeecgBootException("编码不能重复");
		}
		if (baPartTypeService.lambdaQuery().eq(BaPartType::getTypeName,baPartType.getTypeName()).count() > 0) {
			throw new JeecgBootException("名称不能重复");
		}
		baPartTypeService.save(baPartType);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baPartType
	 * @return
	 */
	@AutoLog(value = "商品分类-编辑")
	@ApiOperation(value="商品分类-编辑", notes="商品分类-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaPartType baPartType) {

		BaPartType baPartTypes = baPartTypeService.lambdaQuery()
				.eq(BaPartType::getAttr4, baPartType.getAttr4())
				.ne(BaPartType::getId, baPartType.getId()).one();

		BaPartType baPartTypess = baPartTypeService.lambdaQuery()
				.eq(BaPartType::getTypeName, baPartType.getTypeName())
				.ne(BaPartType::getId, baPartType.getId()).one();
		if (baPartTypes != null) {
			return Result.error("编码已存在，请重新输入");
		}else if (baPartTypess != null){
			return Result.error("名称已存在，请重新输入");
		}else {
			baPartTypeService.updateById(baPartType);
		}


		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品分类-通过id删除")
	@ApiOperation(value="商品分类-通过id删除", notes="商品分类-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baPartTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品分类-批量删除")
	@ApiOperation(value="商品分类-批量删除", notes="商品分类-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baPartTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品分类-通过id查询")
	@ApiOperation(value="商品分类-通过id查询", notes="商品分类-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaPartType baPartType = baPartTypeService.getById(id);
		if(baPartType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baPartType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baPartType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaPartType baPartType) {
        return super.exportXls(request, baPartType, BaPartType.class, "商品分类");
    }

	 /**
	  * 下载模板
	  *
	  * @param request
	  * @param baPartType
	  */
	 @RequestMapping(value = "/downloadXls")
	 public ModelAndView downloadXls(HttpServletRequest request, BaPartType baPartType) {
		 // Step.1 组装查询条件
		 QueryWrapper<BaPartType> queryWrapper = QueryGenerator.initQueryWrapper(baPartType, request.getParameterMap());
//        System.err.println("一号没走通");
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 System.err.println(loginUser);
		 LoginUser sysUser = JwtUtil.getLoginUser();

		 // Step.2 获取导出数据
//		 List<T> pageList = service.list(queryWrapper);
//		 System.err.println(pageList.get(0));
		 List<T> exportList = new ArrayList<>();

		 // 过滤选中数据
//		 String selections = request.getParameter("selections");
//		 if (oConvertUtils.isNotEmpty(selections)) {
//			 List<String> selectionList = Arrays.asList(selections.split(","));
//			 exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
//		 } else {
//			 exportList = pageList;
//		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "商品分类"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, BaPartType.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams( "商品分类报表", "导出人:" + sysUser.getRealname(), "商品分类"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
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
        return super.importExcel(request, response, BaPartType.class);
    }



	/**
	 * 导出excel
	 *
	 * @param request
	 * @param baPartType
	 */
	@RequestMapping(value = "/exportXlsgs")
	public ModelAndView exportXlsgs(HttpServletRequest request, BaPartType baPartType) {
		// Step.1 组装查询条件
		QueryWrapper<BaPartType> queryWrapper = QueryGenerator.initQueryWrapper(baPartType, request.getParameterMap());
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		System.err.println(loginUser);
		LoginUser sysUser = JwtUtil.getLoginUser();
		List<BaPartType> pageList = baPartTypeService.list(queryWrapper);
		List<BaPartType> exportList = null;

		// 过滤选中数据
		String selections = request.getParameter("selections");
		if (oConvertUtils.isNotEmpty(selections)) {
			List<String> selectionList = Arrays.asList(selections.split(","));
			exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		} else {
			exportList = pageList;
		}
		List<BaPartTypegs> list = new ArrayList<BaPartTypegs>();
		for (BaPartType main : exportList) {
			BaPartTypegs vo = new BaPartTypegs();
			BeanUtils.copyProperties(main, vo);
			list.add(vo);
		}
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		mv.addObject(NormalExcelConstants.FILE_NAME, "商品分类");
		mv.addObject(NormalExcelConstants.CLASS, BaPartTypegs.class);
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品分类", "导出人:" + sysUser.getRealname(), "商品分类"));
		mv.addObject(NormalExcelConstants.DATA_LIST, list);
		return mv;
	}


	@RequestMapping(value = "/importExcelgs", method = RequestMethod.POST)
	public Result<?> importExcelgs(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<BaPartTypegs> list = ExcelImportUtil.importExcel(file.getInputStream(), BaPartTypegs.class, params);
				long start = System.currentTimeMillis();
				for (BaPartTypegs partTypegs : list) {
					List<BaPartType> list1 = baPartTypeService.lambdaQuery().in(BaPartType::getTypeName, partTypegs.getTypeName()).list();
					if (CollectionUtil.isNotEmpty(list1)){
						for (BaPartType baPartType : list1) {
							baPartTypeService.removeById(baPartType.getId());
						}
					}
					BaPartType goods = new BaPartType();
					BeanUtil.copyProperties(partTypegs,goods);
					System.out.println(goods);
					baPartTypeService.save(goods);
				}
				log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				return Result.ok("文件导入成功！数据行数：" + list.size());
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
		return Result.OK("文件导入失败！");
	}
}
