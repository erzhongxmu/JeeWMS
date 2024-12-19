package com.base.modules.jeeerp.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import cn.hutool.core.collection.CollectionUtil;
import com.base.modules.jeeerp.entity.BusiPo;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.base.modules.jeeerp.mapper.BusiOmItemMapper;
import com.base.modules.jeeerp.service.IBusiPoItemService;
import com.base.modules.jeeerp.vo.BusiPoPage;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.entity.WmOmNoticeH;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.base.modules.jeewms.service.IMdCusService;
import com.base.modules.jeewms.service.IWmOmNoticeHService;
import com.base.modules.jeewms.vo.WmImNoticeHPage;
import com.base.modules.util.GenerateCodeUtil;
import com.base.modules.util.PltnPushWms;
import com.base.modules.util.PltnSetState;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
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
import com.base.modules.jeeerp.entity.BusiOmItem;
import com.base.modules.jeeerp.entity.BusiOm;
import com.base.modules.jeeerp.vo.BusiOmPage;
import com.base.modules.jeeerp.service.IBusiOmService;
import com.base.modules.jeeerp.service.IBusiOmItemService;
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
 * @Description: busi_om
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Api(tags="busi_om")
@RestController
@RequestMapping("/jeeerp/busiOm")
@Slf4j
public class BusiOmController {
	@Autowired
	private IBusiOmService busiOmService;
	@Autowired
	private IBusiOmItemService busiOmItemService;

	@Autowired
	private GenerateCodeUtil generateCodeUtil;
	 @Autowired
	 private BusiOmItemMapper busiOmItemMapper;

	 @Autowired
	 private IWmOmNoticeHService wmOmNoticeHService;

	 @Autowired
	 private PltnPushWms pltnPushWms;
	 @Autowired
	 private PltnSetState pltnSetState;
	 @Autowired
	 private IMdCusService mdCusService;

	 @Autowired
	 private IBusiPoItemService busiPoItemService;

	/**
	 * 分页列表查询
	 *
	 * @param busiOm
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "busi_om-分页列表查询")
	@ApiOperation(value="busi_om-分页列表查询", notes="busi_om-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BusiOm busiOm,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BusiOm> queryWrapper = QueryGenerator.initQueryWrapper(busiOm, req.getParameterMap());
		Page<BusiOm> page = new Page<BusiOm>(pageNo, pageSize);
		IPage<BusiOm> pageList = busiOmService.page(page, queryWrapper);
		return Result.OK(pageList);
	}



	/**
	 *   添加
	 *
	 * @param busiOmPage
	 * @return
	 */
	@AutoLog(value = "busi_om-添加")
	@ApiOperation(value="busi_om-添加", notes="busi_om-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BusiOmPage busiOmPage) {
		BusiOm busiOm = new BusiOm();
		BeanUtils.copyProperties(busiOmPage, busiOm);
		busiOmService.saveMain(busiOm, busiOmPage.getBusiOmItemList(),0);
		return Result.OK("添加成功！");
	}




	/**
	 *  编辑
	 *
	 * @param busiOmPage
	 * @return
	 */
	@AutoLog(value = "busi_om-编辑")
	@ApiOperation(value="busi_om-编辑", notes="busi_om-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BusiOmPage busiOmPage) {
		BusiOm busiOm = new BusiOm();
		BeanUtils.copyProperties(busiOmPage, busiOm);
		BusiOm busiOmEntity = busiOmService.getById(busiOm.getId());
		if(busiOmEntity==null) {
			return Result.error("未找到对应数据");
		}
		busiOmService.updateMain(busiOm, busiOmPage.getBusiOmItemList());
		return Result.OK("编辑成功!");
	}





	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_om-通过id删除")
	@ApiOperation(value="busi_om-通过id删除", notes="busi_om-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		busiOmService.delMain(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "busi_om-批量删除")
	@ApiOperation(value="busi_om-批量删除", notes="busi_om-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.busiOmService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_om-通过id查询")
	@ApiOperation(value="busi_om-通过id查询", notes="busi_om-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BusiOm busiOm = busiOmService.getById(id);
		if(busiOm==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(busiOm);

	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_om_item通过主表ID查询")
	@ApiOperation(value="busi_om_item主表ID查询", notes="busi_om_item-通主表ID查询")
	@GetMapping(value = "/queryBusiOmItemByMainId")
	public Result<?> queryBusiOmItemListByMainId(@RequestParam(name="id",required=true) String id) {
		List<BusiOmItem> busiOmItemList = busiOmItemService.selectByMainId(id);
		return Result.OK(busiOmItemList);
	}





    /**
    * 导出excel
    *
    * @param request
    * @param busiOm
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BusiOm busiOm) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<BusiOm> queryWrapper = QueryGenerator.initQueryWrapper(busiOm, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<BusiOm> queryList = busiOmService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<BusiOm> busiOmList = new ArrayList<BusiOm>();
      if(oConvertUtils.isEmpty(selections)) {
          busiOmList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          busiOmList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<BusiOmPage> pageList = new ArrayList<BusiOmPage>();
      for (BusiOm main : busiOmList) {
          BusiOmPage vo = new BusiOmPage();
          BeanUtils.copyProperties(main, vo);
          List<BusiOmItem> busiOmItemList = busiOmItemService.selectByMainId(main.getId());
          vo.setBusiOmItemList(busiOmItemList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "busi_om列表");
      mv.addObject(NormalExcelConstants.CLASS, BusiOmPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("busi_om数据", "导出人:"+sysUser.getRealname(), "busi_om"));
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
              List<BusiOmPage> list = ExcelImportUtil.importExcel(file.getInputStream(), BusiOmPage.class, params);
              int index = 0;
              for (BusiOmPage page : list) {
				  index++;
                  BusiOm po = new BusiOm();
                  BeanUtils.copyProperties(page, po);
                  busiOmService.saveMain(po, page.getBusiOmItemList(),index);
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





	// TODO 新增的销售所有接口
	 /**
	  * 分页列表查询，按照单号分组
	  *
	  * @param busiOm
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_om-分页列表查询")
	 @ApiOperation(value="busi_om-分页列表查询", notes="busi_om-分页列表查询")
	 @GetMapping(value = "/list2")
	 public Result<?> queryPageList2(BusiOm busiOm,
									 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									 HttpServletRequest req) {
		 QueryWrapper<BusiOm> queryWrapper = QueryGenerator.initQueryWrapper(busiOm, req.getParameterMap());
		 queryWrapper.lambda().groupBy(BusiOm::getQuery04);
		 Page<BusiOm> page = new Page<BusiOm>(pageNo, pageSize);
		 IPage<BusiOm> pageList = busiOmService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }


	 /**
	  *   添加
	  *
	  * @param busiOmPage
	  * @return
	  */
	 @AutoLog(value = "busi_om-批量添加")
	 @ApiOperation(value="busi_om-批量添加", notes="busi_om-批量添加")
	 @PostMapping(value = "/batchAdd")
	 @Transactional
	 public Result<?> add(@RequestBody List<BusiOmPage> busiOmPage) {
		 int index = 0;
		 BusiOmPage busiOmPage1 = busiOmPage.get(0);
		 String code = generateCodeUtil.generateCode("busi_om","XSD");
		 for (BusiOmPage omPage : busiOmPage) {
			 index++;
			 BusiOm busiOm = new BusiOm();
			 BeanUtils.copyProperties(omPage, busiOm);
			 busiOm.setQuery04(code);
			 String serial = code+(index*10);
			 busiOm.setQuery01("XSD");
			 busiOm.setQuery02("计划中");
			 busiOm.setNum02(busiOm.getNum01());
			 busiOm.setNum03(new Double(0));
			 busiOm.setQuery23(serial);
			 busiOmService.saveMain(busiOm, omPage.getBusiOmItemList(),index);
		 }
		 return Result.OK("添加成功！");
	 }


	 /**
	  *  批量编辑
	  *
	  * @param busiOmPage
	  * @return
	  */
	 @AutoLog(value = "busi_po-编辑")
	 @ApiOperation(value="busi_po-编辑", notes="busi_po-编辑")
	 @PutMapping(value = "/batchEdit")
	 @Transactional
	 public Result<?> batchEdit(@RequestBody List<BusiOmPage> busiOmPage) {
		 // 查询使用单号查询
		 QueryWrapper<BusiOm> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(BusiOm::getQuery04,busiOmPage.get(0).getQuery04());
		 List<BusiOm> busiOmList = busiOmService.list(queryWrapper);
		 for (BusiOm busiOm : busiOmList) {
			 busiOmService.delMain(busiOm.getId());
		 }
		 // 重新插入数据
		 int index = 0;
		 for (BusiOmPage OmPage : busiOmPage) {
			 index++;
			 BusiOm busiOm = new BusiOm();
			 BeanUtils.copyProperties(OmPage, busiOm);
			 busiOm.setId("");
			 busiOmService.saveMain(busiOm, OmPage.getBusiOmItemList(),index);
		 }
		 return Result.OK("编辑成功!");
	 }


	 /**
	  *   出库预约批量添加-推送WMS
	  *
	  * @param busiOmItemPage
	  * @return
	  */
	 @AutoLog(value = "busi_Om_Item-批量添加")
	 @ApiOperation(value="busi_Om_Item-批量添加", notes="busi_Po_Item-批量添加")
	 @PostMapping(value = "/batchAddItem")
	 @Transactional
	 public Result<?> batchAddItem(@RequestBody List<BusiOmItem> busiOmItemPage) {
		 if(CollectionUtil.isEmpty(busiOmItemPage)){
			 return Result.error("数据为空");
		 }
		 // 修改源数据状态
		 BusiOmItem busiOmItem2 = busiOmItemPage.get(0);
		 if(StringUtils.isNotEmpty(busiOmItem2.getQuery04())){
			 pltnSetState.setState("出库中",busiOmItem2.getQuery01(),busiOmItem2.getQuery04());
		 }
		 // 循环新增
		 int index = 0;
		 String code = generateCodeUtil.generateCode("busi_om_item","CKYY");
		 String PO = "";
		 for (BusiOmItem busiOmItem : busiOmItemPage) {
			 index++;
			 BusiOmItem busiOmItem3 = new BusiOmItem();
			 BeanUtils.copyProperties(busiOmItem, busiOmItem3);
			 busiOmItem3.setId("");
			 busiOmItem3.setQuery01("CKYY");
			 busiOmItem3.setLink01("出库预约");
			 busiOmItem3.setLink02(busiOmItem.getQuery04());
			 busiOmItem3.setQuery02("出库中");
			 busiOmItem3.setQuery23(code + "-" + (index*10));
			 if(StringUtils.isEmpty(busiOmItem3.getQuery04())){
				 busiOmItem3.setQuery04(code);
				 busiOmItem3.setNum02(busiOmItem3.getNum01());
				 busiOmItem3.setNum03(new Double(0));
			 }
			 if(busiOmItem3.getQuery13() == "" || busiOmItem3.getQuery13() == null){
				 PO = generateCodeUtil.generateCode("busi_po","PO");
				 busiOmItem3.setQuery13(PO); // 主PO
				 String SonPO = generateCodeUtil.getSonPO(index,PO);
				 busiOmItem3.setQuery14(SonPO);
			 }
			 busiOmItem3.setCreateName(null);
			 busiOmItem3.setCreateBy(null);
			 busiOmItem3.setCreateTime(null);
			 busiOmItem3.setUpdateName(null);
			 busiOmItem3.setUpdateBy(null);
			 busiOmItem3.setUpdateTime(null);
			 busiOmItem3.setSysOrgCode(null);
			 busiOmItemMapper.insert(busiOmItem3);
			 // ----
			 QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
			 MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,busiOmItem.getQuery08());
			 MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
			 busiOmItem.setQuery09(one2.getZhongWenQch());
			 busiOmItem.setQuery20("出库预约");
//			 busiOmItem.setQuery30(code);
			 busiOmItem.setLink02(busiOmItem.getQuery04());
			 busiOmItem.setQuery04(code);
		 }
		 // 推送WMS
		 pltnPushWms.putoutOrder(busiOmItemPage);
		 return Result.OK("添加成功！");
	 }

	 /**
	  * 通过主订单号查询全部
	  *
	  * @param query04 主订单号
	  * @return
	  */
	 @AutoLog(value = "busi_po_item通过主订单号查询全部")
	 @ApiOperation(value="busi_po_item通过主订单号查询全部", notes="busi_po_item-通过主订单号查询全部")
	 @GetMapping(value = "/BatchQueryBusiOmItemByMainId")
	 public Result<?> BatchQueryBusiPoItemByMainId(@RequestParam(name="query04",required=true) String query04) {
		 // 查询使用单号查询
		 QueryWrapper<BusiOmItem> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().like(BusiOmItem::getQuery04,query04);
		 List<BusiOmItem> BusiOmItem = busiOmItemService.list(queryWrapper);
		 return Result.OK(BusiOmItem);
	 }


	 /**
	  * 查询子表list
	  *
	  * @param busiOmItem
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_om_item查询子表list")
	 @ApiOperation(value="busi_om_item查询子表list", notes="busi_om_item-查询子表list")
	 @GetMapping(value = "/BusiOmItemList")
	 public Result<?> queryBusiOmItemListByMainId(BusiOmItem busiOmItem,
												  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												  HttpServletRequest req){
		 QueryWrapper<BusiOmItem> queryWrapper = QueryGenerator.initQueryWrapper(busiOmItem, req.getParameterMap());
		 queryWrapper.lambda().groupBy(BusiOmItem::getQuery04);
		 Page<BusiOmItem> page = new Page<BusiOmItem>(pageNo, pageSize);
		 IPage<BusiOmItem> pageList = busiOmItemService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  *  批量编辑子表
	  *
	  * @param busiOmItem
	  * @return
	  */
	 @AutoLog(value = "busi_po_item-编辑")
	 @ApiOperation(value="busi_po_item-编辑", notes="busi_po_item-编辑")
	 @PutMapping(value = "/batchBusiOmItemEdit")
	 @Transactional
	 public Result<?> batchBusiPoItemEdit(@RequestBody List<BusiOmItem> busiOmItem) {
		 for (BusiOmItem omItem : busiOmItem) {
			 busiOmItemService.updateById(omItem);
		 }
		 return Result.OK("编辑成功!");
	 }

	 /**
	  *   出样推送WMS接口
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "busi_Om_Item-出样推送WMS接口")
	 @ApiOperation(value="busi_Om_Item-出样推送WMS接口", notes="busi_Om_Item-出样推送WMS接口")
	 @GetMapping(value = "/sampleOmPushWms")
	 @Transactional
	 public Result<?> sampleOmPushWms( @RequestParam(name="ids") String ids) {
		 String[] split = ids.split(",");
		 List<String> list = new ArrayList<>();
		 for (String s : split) {
			 list.add(s);
		 }
		 List<BusiOmItem> busiOmItems = busiOmItemService.listByIds(list);
		 // 推送WMS
		 pltnPushWms.putoutOrder(busiOmItems);
		 return Result.OK("添加成功！");
	 }



	 /**
	  *   调拨添加
	  *
	  * @param busiOmPage
	  * @return
	  */
	 @AutoLog(value = "busi_om-调拨添加")
	 @ApiOperation(value="busi_om-调拨添加", notes="busi_om-调拨添加")
	 @PostMapping(value = "/batchAllotAdd")
	 @Transactional
	 public Result<?> batchAllotAdd(@RequestBody List<BusiOmPage> busiOmPage) {
		 int index = 0;
		 // 调拨单
		 BusiOmPage busiOmPage1 = busiOmPage.get(0);
		 String code = generateCodeUtil.generateCode("busi_om",busiOmPage1.getQuery01());
//		 String PO = generateCodeUtil.generateCode("busi_om","PO");
		 List<BusiOm> busiOm1 = new ArrayList<>();
		 for (BusiOmPage omPage : busiOmPage) {
			 index++;
			 BusiOm busiOm = new BusiOm();
			 BeanUtils.copyProperties(omPage, busiOm);
			 busiOm.setQuery04(code);
//			 busiOm.setQuery18(PO);
			 busiOm.setQuery02("计划中");
			 busiOm1.add(busiOm);
		 }
		 // 入库单
		 List<BusiPoItem> busipoitem = new ArrayList<>();
		 String code2 = generateCodeUtil.generateCode("busi_po_item","RKYY");
		 String PO = generateCodeUtil.generateCode("busi_po","PO");
		 int index1 = 0;
		 for (BusiOmPage omPage : busiOmPage) {
			 index1++;
			 BusiPoItem busipoitem1 = new BusiPoItem();
			 busipoitem1.setQuery01("RKYY");
			 busipoitem1.setQuery04(code2);
			 busipoitem1.setQuery02("已推送");
			 busipoitem1.setQuery07(omPage.getQuery06()); // 仓库
			 busipoitem1.setQuery08(omPage.getQuery07()); // 供应商编码
			 busipoitem1.setQuery09(omPage.getQuery17()); // 供应商名称
			 busipoitem1.setQuery13(PO); // 主PO
			 String SonPO = generateCodeUtil.getSonPO(index1,PO);
			 busipoitem1.setQuery14(SonPO); // 子PO
			 busipoitem1.setQuery24(omPage.getQuery12()); // 客户编码
			 busipoitem1.setQuery18("货物调拨"); // 订单类型
			 busipoitem1.setQuery16(omPage.getQuery10()); //采购人
			 busipoitem1.setText01(omPage.getText01()); // 备注
			 busipoitem1.setQuery17(omPage.getQuery09()); //内部发票号
			 busipoitem1.setLink03(omPage.getQuery08()); //销售单号
			 busipoitem1.setQuery10(omPage.getQuery14());
			 busipoitem1.setQuery11(omPage.getQuery15());
			 busipoitem1.setQuery12(omPage.getQuery16());
			 busipoitem1.setNum01(omPage.getNum01());
			 busipoitem.add(busipoitem1);
		 }

		 // 出库单
		 List<BusiOmItem> busiomitem = new ArrayList<>();
		 String code3 = generateCodeUtil.generateCode("busi_om_item","CKYY");
		 for (BusiOmPage omPage : busiOmPage) {
			 index1++;
			 BusiOmItem busiomitem1 = new BusiOmItem();
			 busiomitem1.setQuery01("CKYY");
			 busiomitem1.setQuery04(code3);
			 busiomitem1.setQuery02("已推送");
			 busiomitem1.setQuery07(omPage.getQuery05()); // 仓库
			 busiomitem1.setText01(omPage.getText01()); // 备注
			 busiomitem1.setQuery20("货物调拨"); // 订单类型
			 busiomitem1.setQuery14(omPage.getQuery11()); // 子PO
			 busiomitem1.setQuery14(omPage.getQuery11()); // 子PO
			 busiomitem1.setQuery10(omPage.getQuery14()); // 商品编码
			 busiomitem1.setQuery11(omPage.getQuery15()); // 商品名称
			 busiomitem1.setQuery12(omPage.getQuery16()); // 单位
			 busiomitem1.setNum01(omPage.getNum01()); // 数量
			 busiomitem.add(busiomitem1);
		 }

		 busiOmService.saveBatch(busiOm1);
		 busiPoItemService.saveBatch(busipoitem);
		 busiOmItemService.saveBatch(busiomitem);

		 // 推送WMS
		 pltnPushWms.putInOrder(busipoitem);
		 pltnPushWms.putoutOrder(busiomitem);
		 return Result.OK("添加成功！");
	 }
}
