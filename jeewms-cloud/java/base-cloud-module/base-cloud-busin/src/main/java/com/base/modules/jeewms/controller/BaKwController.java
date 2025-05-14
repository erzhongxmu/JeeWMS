package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.util.StringUtil;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.controller.dto.BaKwDTO;
import com.base.modules.jeewms.controller.dto.KwPicDataDTO;
import com.base.modules.jeewms.entity.BaKw;
import com.base.modules.jeewms.entity.BaStore;
import com.base.modules.jeewms.entity.BaStoreArea;
import com.base.modules.jeewms.service.IBaKwService;
import com.base.modules.jeewms.service.IBaPartTypeService;
import com.base.modules.jeewms.service.IBaStoreAreaService;
import com.base.modules.jeewms.service.IBaStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.base.controller.JeecgController;
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
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 储位定义
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="储位定义")
@RestController
@RequestMapping("/jeewms/baKw")
@Slf4j
public class BaKwController extends JeecgController<BaKw, IBaKwService> {
	@Autowired
	private IBaKwService baKwService;
	@Autowired
    private IBaPartTypeService baPartTypeService;
    @Autowired
	private IBaStoreAreaService baStoreAreaService;
    @Autowired
	private IBaStoreService baStoreService;


	/**
	 * 分页列表查询
	 *
	 * @param baKw
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ba_kw-分页列表查询")
	@ApiOperation(value="ba_kw-分页列表查询", notes="ba_kw-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaKw baKw,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
//		QueryWrapper<BaKw> queryWrapper = QueryGenerator.initQueryWrapper(baKw, req.getParameterMap());
		QueryWrapper<BaKw> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda()
				.like(StringUtils.isNotEmpty(baKw.getKwName()),BaKw::getKwName,baKw.getKwName())
				.like(StringUtils.isNotEmpty(baKw.getKwCode()),BaKw::getKwCode,baKw.getKwCode())
				.like(StringUtils.isNotEmpty(baKw.getKwBarCode()),BaKw::getKwBarCode,baKw.getKwBarCode())
				.eq(StringUtils.isNotEmpty(baKw.getBinUse()),BaKw::getBinUse,baKw.getBinUse())
				.eq(StringUtils.isNotEmpty(baKw.getKwType()),BaKw::getKwType,baKw.getKwType())
				.eq(StringUtils.isNotEmpty(baKw.getStoreCode()),BaKw::getStoreCode,baKw.getStoreCode())
				.eq(BaKw::getStatus,"N");


		Map<String, String[]> parameterMap = req.getParameterMap();
		String strings = parameterMap.get("column")[0];
		String order = parameterMap.get("order")[0];
		if ("createTime".equals(strings)){
			queryWrapper.orderByDesc("create_time");
		}else {
			Set<String> key1= new HashSet<>();
			key1.add(strings);
			Map<String, Object> proxyPojoValue = StringUtil.getProxyPojoValue(new BaKw(), key1);
			System.out.println(proxyPojoValue);
			if ("asc".equals(order)){
				queryWrapper.orderByAsc(proxyPojoValue.get(strings).toString());
			}{
				queryWrapper.orderByDesc(proxyPojoValue.get(strings).toString());
			}
		}
		Page<BaKw> page = new Page<BaKw>(pageNo, pageSize);
		IPage<BaKw> pageList = baKwService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	@AutoLog(value = "获取仓位图数据")
    @ApiOperation(value = "获取仓位图数据", notes = "获取仓位图数据")
    @GetMapping(value = "/getKwPicData")
    public Result<?> getKwPicData(KwPicDataDTO param) {
	    return Result.ok(baKwService.getKwPicData(param));
	}

	/**
	 *   查询库位下拉列表
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="库位-下拉列表", notes="库位-下拉列表")
	@GetMapping(value = "/getKwList")
	public Result<?> geKwList(String areaCode) {

		return Result.ok(baKwService.lambdaQuery()
				.eq(StringUtils.isNotEmpty(areaCode),BaKw::getStoreAreaCode,areaCode)
				.eq(BaKw::getStatus,"N").list());
	}

	/**
	 *   查询库位下拉列表
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="库位-下拉列表", notes="库位-下拉列表")
	@PostMapping(value = "/getKwListByGoodsType")
	public Result<?> getKwListByGoodsType(@RequestBody List<String> goodsTypeIdList) {
		Map<String,Object> resultMap = new HashMap<>(1024);
//		for (String typeId : goodsTypeIdList) {
//			List<BaKw> baKwList = baKwService.lambdaQuery().apply("find_in_set('"+typeId+"',part_type)").list();
		QueryWrapper<BaKw> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ting_yong","N");
		List<BaKw> baKwList =baKwService.list(queryWrapper);
//		List<BaKw> baKwList =baKwService.list(queryWrapper).subList(0,10);
		// TODO: 2021/12/30  选择储位，根据类型判断产品类型
			if (baKwList != null && baKwList.size() > 0) {
				for (BaKw baKw : baKwList) {
					resultMap.put(baKw.getKwCode(),baKw.getKwCode());
				}
			}
//		}
		return Result.ok(resultMap);
	}





	/**
	 *   根据库位code查询库位
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="库位-根据库位code查询库位", notes="库位-根据库位code查询库位")
	@GetMapping(value = "/getKwByCode")
	public Result<?> getKwByCode(@RequestParam String kwCode) {
		return Result.ok(baKwService.lambdaQuery().eq(BaKw::getKwCode,kwCode).one());
	}
	 /**
	  *   启用/停用
	  *
	  * @param param
	  * @return
	  */
	 @AutoLog(value = "停用储位")
	 @ApiOperation(value="停用储位", notes="停用储位")
	 @PostMapping(value = "/startOrStop")
	 public Result<?> startOrStop(@RequestBody BaKwDTO param) {
		 BaKw baKw = new BaKw();
		 BeanUtils.copyProperties(param,baKw);
		 baKwService.updateById(baKw);
		 return Result.ok("操作成功");
	 }

	 /**
	  *   启用/停用
	  *
	  * @param param
	  * @return
	  */
	 @AutoLog(value = "批量停用储位")
	 @ApiOperation(value="批量停用储位", notes="批量停用储位")
	 @PostMapping(value = "/batchStartOrStop")
	 public Result<?> batchStartOrStop(@RequestBody List<BaKwDTO> param) {
		 baKwService.batchStartOrStop(param);
		 return Result.ok("操作成功");
	 }


	/**
	 *   添加
	 *
	 * @param baKw
	 * @return
	 */
	@AutoLog(value = "ba_kw-添加")
	@ApiOperation(value="ba_kw-添加", notes="ba_kw-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaKw baKw) {
//		baKw.setStatus("N");
//	    if ("Y".equals(baKw.getMixStore()) && StringUtils.isNotEmpty(baKw.getPartType())) {
//	        String[] partArray = baKw.getPartType().split(",");
//            Set<String> set = new HashSet<>();
//	        if (partArray.length > 0) {
//                for (String id : partArray) {
//                    BaPartType baPartType = baPartTypeService.getById(id);
//                    set.add(baPartType.getStackLimit());
//                }
//                if (set.size() > 0) {
//                    return Result.error("堆码极限不一致，不能混放！");
//                }
//            }
//	    }

	    //判断编码
		if (baKwService.lambdaQuery().eq(BaKw::getKwCode,baKw.getKwCode()).count() > 0) {
			return Result.error("本仓库已存在该库位");
		}

//		//查询库区
//		BaStoreArea baStoreArea = baStoreAreaService.lambdaQuery().eq(BaStoreArea::getAreaCode,baKw.getStoreAreaCode()).one();
//		if (baStoreArea != null ) {
//			//查询仓库
//			BaStore baStore = baStoreService.lambdaQuery().eq(BaStore::getStoreCode,baStoreArea.getWareCode()).one();
//			if (baStore != null) {
//				baKw.setStoreCode(baStore.getStoreCode());
//				baKw.setWareName(baStore.getStoreName());
//			}
//		}
//		baKw.setStoreCode("U8");
//		baKw.setWareName("U8虚拟仓");

		baKwService.save(baKw);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baKw
	 * @return
	 */
	@AutoLog(value = "ba_kw-编辑")
	@ApiOperation(value="ba_kw-编辑", notes="ba_kw-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaKw baKw) {
//		if ("Y".equals(baKw.getMixStore()) && StringUtils.isNotEmpty(baKw.getPartType())) {
//			String[] partArray = baKw.getPartType().split(",");
//			Set<String> set = new HashSet<>();
//			if (partArray.length > 0) {
//				for (String id : partArray) {
//					BaPartType baPartType = baPartTypeService.getById(id);
//					set.add(baPartType.getStackLimit());
//				}
//				if (set.size() > 0) {
//					return Result.error("堆码极限不一致，不能混放！");
//				}
//			}
//		}
//		baKw.setStoreCode("U8");
//		baKw.setWareName("U8虚拟仓");
		BaKw baKws = baKwService.lambdaQuery().eq(BaKw::getKwCode, baKw.getKwCode())
				.ne(BaKw::getId, baKw.getId()).one();
		if (baKws != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			baKwService.updateById(baKw);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_kw-通过id删除")
	@ApiOperation(value="ba_kw-通过id删除", notes="ba_kw-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baKwService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ba_kw-批量删除")
	@ApiOperation(value="ba_kw-批量删除", notes="ba_kw-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baKwService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_kw-通过id查询")
	@ApiOperation(value="ba_kw-通过id查询", notes="ba_kw-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaKw baKw = baKwService.getById(id);
		if(baKw==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baKw);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baKw
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaKw baKw) {
		// Step.1 组装查询条件
		QueryWrapper<BaKw> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(BaKw::getStatus,"N")
				.orderByDesc(BaKw::getCreateTime);
		if (StringUtils.isNotEmpty(baKw.getKwName())) {
			queryWrapper.lambda().like(BaKw::getKwName,baKw.getKwName().substring(1,baKw.getKwName().length()-1));
		}
		if (StringUtils.isNotEmpty(baKw.getKwCode())) {
			queryWrapper.lambda().like(StringUtils.isNotEmpty(baKw.getKwCode()),BaKw::getKwCode,baKw.getKwCode().substring(1,baKw.getKwCode().length()-1));
		}
		if (StringUtils.isNotEmpty(baKw.getKwBarCode())) {
			queryWrapper.lambda().like(StringUtils.isNotEmpty(baKw.getKwBarCode()),BaKw::getKwBarCode,baKw.getKwBarCode().substring(0,baKw.getKwBarCode().length()-1));
		}
		if (StringUtils.isNotEmpty(baKw.getKwType())) {
			queryWrapper.lambda().like(StringUtils.isNotEmpty(baKw.getKwType()),BaKw::getKwType,baKw.getKwType().substring(0,baKw.getKwType().length()-1));
		}
		if (StringUtils.isNotEmpty(baKw.getStoreCode())) {
			queryWrapper.lambda().like(StringUtils.isNotEmpty(baKw.getStoreCode()),BaKw::getStoreCode,baKw.getStoreCode().substring(0,baKw.getStoreCode().length()-1));
		}
		if (StringUtils.isNotEmpty(baKw.getBinUse())) {
			queryWrapper.lambda().like(StringUtils.isNotEmpty(baKw.getBinUse()),BaKw::getBinUse,baKw.getBinUse().substring(0,baKw.getBinUse().length()-1));
		}

//        System.err.println("一号没走通");
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		System.err.println(loginUser);
		LoginUser sysUser = JwtUtil.getLoginUser();

		// Step.2 获取导出数据
		List<BaKw> pageList = baKwService.list(queryWrapper);
		List<BaKw> exportList = null;

		// 过滤选中数据
		String selections = request.getParameter("selections");
		if (oConvertUtils.isNotEmpty(selections)) {
			List<String> selectionList = Arrays.asList(selections.split(","));
			exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
		} else {
			exportList = pageList;
		}

		// Step.3 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		mv.addObject(NormalExcelConstants.FILE_NAME, "库位"); //此处设置的filename无效 ,前端会重更新设置一下
		mv.addObject(NormalExcelConstants.CLASS, baKw.getClass());
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("库位报表", "导出人:" + sysUser.getRealname(), "库位"));
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
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<BaKw> list = ExcelImportUtil.importExcel(file.getInputStream(), BaKw.class, params);
				//update-begin-author:taoyan date:20190528 for:批量插入数据
				long start = System.currentTimeMillis();
				List<BaKw> listsave = new ArrayList<>();
				if (list.size() > 0) {
					for(BaKw baKw : list){
						if(StringUtils.isEmpty(baKw.getKwCode())){
							continue;
						}
						List<BaKw> list1 = baKwService.lambdaQuery().in(BaKw::getKwCode, baKw.getKwCode()).list();
						for (BaKw kw : list1) {
							baKwService.removeById(kw.getId());
						}
						listsave.add(baKw);
//						baKw.setStoreCode("U8");
//						baKw.setWareName("U8虚拟仓");
					}
				}
				baKwService.saveBatch(listsave);
				//400条 saveBatch消耗时间1592毫秒  循环插入消耗时间1947毫秒
				//1200条  saveBatch消耗时间3687毫秒 循环插入消耗时间5212毫秒
				log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				//update-end-author:taoyan date:20190528 for:批量插入数据
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
		return Result.error("文件导入失败！");
        //return super.importExcel(request, response, BaKw.class);
    }

	/**
	 * 获取对象ID
	 *
	 * @return
	 */
	private String getId(BaKw item) {
		try {
			return PropertyUtils.getProperty(item, "id").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/doExportBq")
	@ApiOperation(value = "导出储位标签")
	public void doExportBq(@RequestParam String ids,@RequestParam String type, HttpServletResponse response) {
		List<String> idList = Arrays.asList(ids.split(","));
		if("1".equals(type)){
			baKwService.doExportBq(idList, response);
		}else if("2".equals(type)){
			baKwService.doExportBq2(idList, response);
		}else{
			baKwService.doExportBq3(idList, response);
		}
	}

}
