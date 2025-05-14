package com.base.modules.gswms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.vo.LoginUser;
import com.base.modules.jeewms.controller.dto.FrozenMdGoodDTO;
import com.base.modules.jeewms.controller.dto.WmOmNoticeHDTO;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.MdGoodsItem;
import com.base.modules.jeewms.service.IMdCusService;
import com.base.modules.jeewms.service.IMdGoodsItemService;
import com.base.modules.jeewms.service.IMdGoodsService;
import com.base.modules.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
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

;

/**
 * @Description: 商品信息
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="商品信息")
@RestController
@RequestMapping("/gswms/mdGoods")
@Slf4j
public class GsMdGoodsController extends JeecgController<MdGoods, IMdGoodsService> {
	@Autowired
	private IMdGoodsService mdGoodsService;
	@Autowired
	private IMdGoodsItemService mdGoodsItemService;
	@Autowired
	private IMdCusService mdCusService;

	/**
	 * 分页列表查询
	 *
	 * @param mdGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "商品信息-分页列表查询")
	@ApiOperation(value="商品信息-分页列表查询", notes="商品信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MdGoods mdGoods,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MdGoods> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotEmpty(mdGoods.getShpBianMa())){
			queryWrapper.like("shp_bian_ma",mdGoods.getShpBianMa());
		}
		if(StringUtils.isNotEmpty(mdGoods.getShpMingCheng())){
			queryWrapper.like("shp_ming_cheng",mdGoods.getShpMingCheng());
		}
		if(StringUtils.isNotEmpty(mdGoods.getClassification())){
			queryWrapper.eq("classification",mdGoods.getClassification());
		}
		if(StringUtils.isNotEmpty(mdGoods.getShpBianMakh())){
			queryWrapper.eq("shp_bian_makh",mdGoods.getShpBianMakh());
		}
		queryWrapper.orderByAsc("shp_bian_ma");
		Page<MdGoods> page = new Page<MdGoods>(pageNo, pageSize);
		IPage<MdGoods> pageList = mdGoodsService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	@AutoLog(value = "商品信息-分页列表查询")
	@ApiOperation(value="商品信息-分页列表查询", notes="商品信息-分页列表查询")
	@GetMapping(value = "/listpage")
	public Result<?> listpage(MdGoods mdGoods,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MdGoods> queryWrapper = QueryGenerator.initQueryWrapper(mdGoods, req.getParameterMap());
		Page<MdGoods> page = new Page<MdGoods>(pageNo, pageSize);
		IPage<MdGoods> pageList = mdGoodsService.page(page, queryWrapper);
		List<MdGoods> records = pageList.getRecords();
		for (MdGoods record : records) {
			List<MdGoodsItem> items = mdGoodsItemService.lambdaQuery().eq(MdGoodsItem::getSttr1, record.getShpTiaoMa()).list();
			record.setGoodsItems(items);
		}
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param mdGoods
	 * @return
	 */
	@AutoLog(value = "商品信息-添加")
	@ApiOperation(value="商品信息-添加", notes="商品信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MdGoods mdGoods) {
		if (mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa,mdGoods.getShpBianMa()).count() > 0) {
			throw new JeecgBootException("编码重复");
		}
		Result<?> result = mdGoodsService.add(mdGoods);
		return result;
	}

	/**
	 *   添加
	 *
	 * @param mdGoods
	 * @return
	 */
	@AutoLog(value = "商品信息-添加")
	@ApiOperation(value="商品信息-添加", notes="商品信息-添加")
	@PostMapping(value = "/addDetail")
	public Result<?> addDetail(@RequestBody MdGoodsItem mdGoods) {

		if (mdGoodsItemService.lambdaQuery().eq(MdGoodsItem::getShpBianMa,mdGoods.getShpBianMa()).count() > 0) {
			throw new JeecgBootException("编码重复");
		}
		boolean save = mdGoodsItemService.save(mdGoods);
		/*MdGoodsItem goodsItem = mdGoodsItemService.lambdaQuery().eq(MdGoodsItem::getShpBianMa, mdGoods.getShpBianMa()).one();
		if (goodsItem != null) {
			mdGoodsItemService.updateById(goodsItem);
		}else {
			mdGoodsItemService.save(mdGoods);
		}*/
		return Result.OK(save) ;
	}

	/**
	 *   添加
	 *
	 * @param mdGoods
	 * @return
	 */
	@AutoLog(value = "商品信息-添加")
	@ApiOperation(value="商品信息-添加", notes="商品信息-添加")
	@PostMapping(value = "/addgoods")
	public Result<?> addgoods(@RequestBody MdGoods mdGoods) {
		if (mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa,mdGoods.getShpBianMa()).count() > 0) {
			throw new JeecgBootException("编码重复");
		}
		if (mdGoodsItemService.lambdaQuery().eq(MdGoodsItem::getShpBianMa,mdGoods.getShpBianMa()).count() > 0) {
			throw new JeecgBootException("编码重复");
		}
		List<MdGoodsItem> goodsItems = mdGoods.getGoodsItems();
		for (MdGoodsItem goodsItem : goodsItems) {
				//箱子编码
//				goodsItem.setSttr1(mdGoods.getShpTiaoMa());
				mdGoodsItemService.save(goodsItem);
		}
		Result<?> result = mdGoodsService.add(mdGoods);
		return result;
	}


	/**
	 *  编辑子表
	 *
	 * @param mdGoods
	 * @return
	 */
	@AutoLog(value = "商品信息-编辑")
	@ApiOperation(value="商品信息-编辑", notes="商品信息-编辑")
	@PutMapping(value = "/editDetail")
	public Result<?> editDetail(@RequestBody MdGoodsItem mdGoods) {
		mdGoodsItemService.updateById(mdGoods);
		return Result.ok("编辑成功!");
	}

	@AutoLog(value = "商品信息-分页列表查询")
	@ApiOperation(value="商品信息-分页列表查询", notes="商品信息-分页列表查询")
	@GetMapping(value = "/listpageDetail")
	public Result<?> listpageDetail(MdGoodsItem mdGoodsItem,
							  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
							  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
							  HttpServletRequest req) {
		QueryWrapper<MdGoodsItem> queryWrapper = QueryGenerator.initQueryWrapper(mdGoodsItem, req.getParameterMap());
		List<MdGoodsItem> pageList = mdGoodsItemService.list(queryWrapper);
		return Result.OK(pageList);
	}
	/**
	 *  删除子表
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品信息-通过id删除")
	@ApiOperation(value="商品信息-通过id删除", notes="商品信息-通过id删除")
	@DeleteMapping(value = "/deleteDetail")
	public Result<?> deleteDetail(@RequestParam(name="id",required=true) String id) {
		mdGoodsItemService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除子表
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品信息-批量删除")
	@ApiOperation(value="商品信息-批量删除", notes="商品信息-批量删除")
	@DeleteMapping(value = "/deleteBatchDetail")
	public Result<?> deleteBatchDetail(@RequestParam(name="ids",required=true) String ids) {
		this.mdGoodsItemService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品信息-通过id查询")
	@ApiOperation(value="商品信息-通过id查询", notes="商品信息-通过id查询")
	@GetMapping(value = "/queryByDetailId")
	public Result<?> queryByDetailId(@RequestParam(name="id",required=true) String id) {
		MdGoodsItem itemServiceById = mdGoodsItemService.getById(id);
		if(itemServiceById==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(itemServiceById);
	}


	/**
	 *  编辑
	 *
	 * @param mdGoods
	 * @return
	 */
	@AutoLog(value = "商品信息-编辑")
	@ApiOperation(value="商品信息-编辑", notes="商品信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MdGoods mdGoods) {
		MdGoods mdGood = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, mdGoods.getShpBianMa())
				.ne(MdGoods::getId, mdGoods.getId()).one();
		if (mdGood != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			mdGoodsService.updateById(mdGoods);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品信息-通过id删除")
	@ApiOperation(value="商品信息-通过id删除", notes="商品信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mdGoodsService.removeById(id);
		return Result.ok("删除成功!");
	}

	 /**
	  *   通过编码查询
	  *
	  * @param goodsCode
	  * @return
	  */
	 @ApiOperation(value="商品信息-通过编码查询", notes="商品信息-通过编码查询")
	 @GetMapping(value = "/getByCode")
	 public Result<?> getByCode(@RequestParam(name="goodsCode",required=true) String goodsCode) {
		 return Result.ok(mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa,goodsCode).one());
	 }

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品信息-批量删除")
	@ApiOperation(value="商品信息-批量删除", notes="商品信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mdGoodsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品信息-通过id查询")
	@ApiOperation(value="商品信息-通过id查询", notes="商品信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MdGoods mdGoods = mdGoodsService.getById(id);
		if(mdGoods==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mdGoods);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mdGoods
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdGoods mdGoods) {
        //return super.exportXls(request, mdGoods, MdGoods.class, "商品信息");
		return super.exportXls(request, mdGoods, MdGoods.class, "商品信息");
    }

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param mdGoods
	 */
	@RequestMapping(value = "/downloadXls")
	public ModelAndView downloadXls(HttpServletRequest request, MdGoods mdGoods) {
		//return super.exportXls(request, mdGoods, MdGoods.class, "商品信息");
		// Step.1 组装查询条件
		QueryWrapper<MdGoods> queryWrapper = QueryGenerator.initQueryWrapper(mdGoods, request.getParameterMap());
//        System.err.println("一号没走通");
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		System.err.println(loginUser);
		LoginUser sysUser = JwtUtil.getLoginUser();

		// Step.2 获取导出数据
//		List<T> pageList = mdGoodsService.list(queryWrapper);
//		System.err.println(pageList.get(0));
		List<T> exportList = new ArrayList<>();

		// 过滤选中数据
//		String selections = request.getParameter("selections");
//		if (oConvertUtils.isNotEmpty(selections)) {
//			List<String> selectionList = Arrays.asList(selections.split(","));
//			exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
//		} else {
//			exportList = pageList;
//		}

		// Step.3 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		mv.addObject(NormalExcelConstants.FILE_NAME, "商品信息"); //此处设置的filename无效 ,前端会重更新设置一下
		mv.addObject(NormalExcelConstants.CLASS, MdGoods.class);
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品信息报表", "导出人:" + sysUser.getRealname(), "商品信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		return mv;
		//return super.exportXls(request, mdGoods, MdGoods.class, "商品信息");
	}

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel2", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			try {
				List<List<String>> sheetlist = ExcelUtil.publicReadExcel(file, 3, 0, 50);
				List<String> title = sheetlist.get(0);
				int sheetsize = sheetlist.size();
				Map<String, String> keymap = new HashMap<String, String>(1024);
				Map<String, String> cfmap = new HashMap<String, String>(1024);
				List<MdGoods> savelist = new ArrayList<MdGoods>();
				StringBuffer errmsg = new StringBuffer(1024);
				LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
				QueryWrapper<MdCus> w1 =new QueryWrapper<>();
				w1.select("ke_hu_bian_ma","zhong_wen_qch");
				List<MdCus> cuslist = mdCusService.list(w1);
				Map<String,String> cusmap = new HashMap(1024);
				for(MdCus cus:cuslist){
					cusmap.put(cus.getZhongWenQch(),cus.getKeHuBianMa());
				}
				Map idmap = new HashMap(1024);
				QueryWrapper<MdGoods> w2 = new QueryWrapper();
				for (int i = 0; i < sheetsize; i++) {
					List<String> l = sheetlist.get(i);
					String gys = l.get(9);
					if(StringUtils.isEmpty(gys)){
						errmsg.append("第").append(i+3).append("行供应商不能为空<br/>");
						continue;
					}else if(!cusmap.containsKey(gys)){
						errmsg.append("第").append(i+3).append("行供应商在系统中未维护<br/>");
						continue;
					}
					String gysdm = cusmap.get(gys);
                    String idkey = l.get(1)+"-"+gysdm;
					if(idmap.containsKey(idkey)){
						errmsg.append("第").append(i+3).append("行商品编码+供应商与第").append(idmap.get(idkey)).append("行数据相同，不能导入<br/>");
						continue;
					}
					idmap.put(idkey,i+3);

					w2 = new QueryWrapper();
					w2.eq("shp_bian_ma",l.get(1));
					w2.eq("shp_bian_makh",gysdm);
					int count = mdGoodsService.count(w2);
					if(count>0){
						errmsg.append("第").append(i+3).append("行商品编码+供应商在系统中已存在<br/>");
						continue;
					}

					MdGoods goods = new MdGoods();
					goods.setShpMingCheng(l.get(0));  //商品名称
					goods.setShpBianMa(l.get(1));  //商品编码
					goods.setShpXingHao(l.get(2));  //商品型号
					goods.setShpGuiGe(l.get(3));  //商品规格
					goods.setShpTiaoMa(l.get(4));  //商品条码
					goods.setChZhXiang(l.get(5));  //出厂包装-长
					goods.setKuZhXiang(l.get(6));  //出厂包装-宽
					goods.setGaoZhXiang(l.get(7));  //出厂包装-高
					goods.setShpBianMakh(gysdm);  //供应商编码
					goods.setCusName(l.get(9));  //供应商名称
					goods.setIfBackpacking(l.get(10));  //上线包装-是否翻包
					goods.setModel(l.get(11));  //商品分类
					goods.setWorkshop(l.get(12));  //车间
					goods.setBomZw(l.get(13));  //BOM中文名称
					goods.setClassification(l.get(14));  //商品分类
					goods.setFactoryType(l.get(15));  //出厂包装-类型
					goods.setOnlineType(l.get(16));  //上架包装-类型
					goods.setStorageArea(l.get(17));  //存放库位
					goods.setMaxStock(l.get(18));  //最大库存量（件）
					goods.setOnlineMode(l.get(19));  //上线包装-上线方式
					goods.setMinStock(l.get(20));  //最小库存量
					goods.setFactorySnpCase(l.get(21));  //出厂包装-SNP-箱
					goods.setFactorySnpPackage(l.get(22));  //出厂包装-SNP-包
					goods.setFactorySnpPiece(l.get(23));  //出厂包装-SNP-件
					goods.setFactorySnpCaseNum(l.get(24));  //出厂包装-SNP-箱数
					goods.setFactorySnpPackageNum(l.get(25));  //出厂包装-SNP-包数
					goods.setFactorySnpPieceNum(l.get(26));  //出厂包装-SNP-件数
					savelist.add(goods);
				}
				if(errmsg.length()>0){
					return Result.error("导入失败！原因如下：<br/>"+errmsg);
				}
				mdGoodsService.saveBatch(savelist);
				return Result.ok("文件导入成功！数据行数:" + (savelist.size()));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return Result.error("文件导入失败:" + e.getMessage());
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		return Result.ok("文件导入失败！");
    }

	 @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	 public Result<?> importExcel1(HttpServletRequest request, HttpServletResponse response) {
		 return super.importExcel(request, response, MdGoods.class);
	 }

    @RequestMapping("/order")
	@ApiOperation("下单")
    public Result<?> order(@RequestBody WmOmNoticeHDTO wmOmNoticeH) {
		mdGoodsService.order(wmOmNoticeH);
    	return Result.ok("操作成功");
	}


	 @RequestMapping("/frozen")
	 @ApiOperation("冻结/解冻")
	 public Result<?> frozen(@RequestBody @Valid FrozenMdGoodDTO param) {
		 mdGoodsService.frozen(param);
		 return Result.ok("操作成功");
	 }

}
