package com.base.modules.jeeerp.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.base.modules.jeeerp.entity.*;
import com.base.modules.jeeerp.mapper.BusiPoItemMapper;
import com.base.modules.jeeerp.mapper.BusiPoMapper;
import com.base.modules.jeeerp.mapper.BusiPrdOrdItemMapper;
import com.base.modules.jeeerp.service.IBusiPaymentReceivedService;
import com.base.modules.jeeerp.service.IBusiPrdOrdService;
import com.base.modules.jeeerp.vo.*;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.vo.WmImNoticeHPage;
import com.base.modules.util.GenerateCodeUtil;
import com.base.modules.util.PltnPushWms;
import com.base.modules.util.PltnSetState;
import com.base.modules.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.CommonConstant;
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
import com.base.modules.jeeerp.service.IBusiPoService;
import com.base.modules.jeeerp.service.IBusiPoItemService;
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

import static java.util.Objects.isNull;

/**
 * @Description: busi_po
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
@Api(tags="busi_po")
@RestController
@RequestMapping("/jeeerp/busiPo")
@Slf4j
public class BusiPoController {
	@Autowired
	private IBusiPoService busiPoService;
	@Autowired
	private IBusiPoItemService busiPoItemService;
	 @Autowired
	 private GenerateCodeUtil generateCodeUtil;
	 @Autowired
	 private PltnSetState pltnSetState;
	 @Autowired
	 private IBusiPrdOrdService busiPrdOrdService;
	 @Autowired
	 private BusiPoItemMapper busiPoItemMapper;
	 @Autowired
	 private BusiPoMapper busiPoMapper;
	 @Autowired
	 private PltnPushWms pltnPushWms;
	 @Autowired
	 private IBusiPaymentReceivedService busiPaymentReceivedService;

	 @Autowired
	 private IMdCusService mdCusService;


	 /**
	  * 分页列表查询
	  *
	  * @param busiPo
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_po-分页列表查询")
	 @ApiOperation(value="busi_po-分页列表查询", notes="busi_po-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<?> queryPageList(BusiPo busiPo,
									 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									 @RequestParam(name="sT",required = false) String  sT,
									 @RequestParam(name="nT",required = false) String nT,
									 HttpServletRequest req) {
		 QueryWrapper<BusiPo> queryWrapper = QueryGenerator.initQueryWrapper(busiPo, req.getParameterMap());
		 if(StringUtils.isNotBlank(sT)){
			 queryWrapper.lambda().gt(BusiPo::getCreateTime,sT);
		 }
		 if(StringUtils.isNotBlank(nT)){
			 queryWrapper.lambda().lt(BusiPo::getCreateTime,nT);
		 }
		 Page<BusiPo> page = new Page<BusiPo>(pageNo, pageSize);
		 IPage<BusiPo> pageList = busiPoService.page(page, queryWrapper);
		 for (BusiPo record : pageList.getRecords()) {
			 QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
			 MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,record.getQuery24());
			 MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
			 record.setXingYeFenLei(one2.getXingYeFenLei());

			 QueryWrapper<BusiPaymentReceived> PqueryWrapper =  new QueryWrapper<>();
			 PqueryWrapper.lambda().eq(BusiPaymentReceived::getQuery14,record.getQuery14());
			 PqueryWrapper.lambda().eq(BusiPaymentReceived::getQuery01,"FKD");
			 List<BusiPaymentReceived> list = busiPaymentReceivedService.list(PqueryWrapper);
			 BigDecimal i = new BigDecimal(0);
			 for (BusiPaymentReceived busiPaymentReceived : list) {
				 BigDecimal bigDecimal = new BigDecimal(busiPaymentReceived.getNum09().toString());
				 i = i.add(bigDecimal);
			 }
			 BigDecimal Num07 = new BigDecimal(record.getNum07().toString());
			 BigDecimal subtract = Num07.subtract(i);
			 record.setRemainingSum(String.valueOf(subtract));
		 }
		 return Result.OK(pageList);
	 }






	/**
	 *   添加
	 *
	 * @param busiPoPage
	 * @return
	 */
	@AutoLog(value = "busi_po-添加")
	@ApiOperation(value="busi_po-添加", notes="busi_po-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BusiPoPage busiPoPage) {
		BusiPo busiPo = new BusiPo();
		BeanUtils.copyProperties(busiPoPage, busiPo);
		busiPoService.saveMain(busiPo, busiPoPage.getBusiPoItemList());
		return Result.OK("添加成功！");
	}




	/**
	 *  编辑
	 *
	 * @param busiPoPage
	 * @return
	 */
	@AutoLog(value = "busi_po-编辑")
	@ApiOperation(value="busi_po-编辑", notes="busi_po-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BusiPoPage busiPoPage) {
		BusiPo busiPo = new BusiPo();
		BeanUtils.copyProperties(busiPoPage, busiPo);
		BusiPo busiPoEntity = busiPoService.getById(busiPo.getId());
		if(busiPoEntity==null) {
			return Result.error("未找到对应数据");
		}
		busiPoService.updateMain(busiPo, busiPoPage.getBusiPoItemList());
		return Result.OK("编辑成功!");
	}



	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_po-通过id删除")
	@ApiOperation(value="busi_po-通过id删除", notes="busi_po-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		BusiPo byId = busiPoService.getById(id);
		QueryWrapper<BusiPo> busiPoQueryWrapper = new QueryWrapper<>();
		busiPoQueryWrapper.lambda().eq(BusiPo::getQuery13,byId.getQuery13());
		List<BusiPo> list = busiPoService.list(busiPoQueryWrapper);
		for (BusiPo busiPo : list) {
			busiPoService.delMain(busiPo.getId());
		}
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "busi_po-批量删除")
	@ApiOperation(value="busi_po-批量删除", notes="busi_po-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		List<String> list1 = Arrays.asList(ids.split(","));
		for (String id : list1) {
			BusiPo byId = busiPoService.getById(id);
			QueryWrapper<BusiPo> busiPoQueryWrapper = new QueryWrapper<>();
			busiPoQueryWrapper.lambda().eq(BusiPo::getQuery13,byId.getQuery13());
			List<BusiPo> list = busiPoService.list(busiPoQueryWrapper);
			for (BusiPo busiPo : list) {
				busiPoService.delMain(busiPo.getId());
			}
		}
//		this.busiPoService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	 /**
	  *  样品管理批量删除
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "busi_po-批量删除")
	 @ApiOperation(value="busi_po-批量删除", notes="busi_po-批量删除")
	 @DeleteMapping(value = "/deleteItem")
	 public Result<?> deleteItem(@RequestParam(name="ids",required=true) String ids) {
		 String[] split = ids.split(",");
		 for (String s : split) {
 			 busiPoItemService.removeById(s);
		 }
		 return Result.OK("批量删除成功！");
	 }
	 /**
	  *  批量删除子表
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "busi_po-批量删除")
	 @ApiOperation(value="busi_po-批量删除", notes="busi_po-批量删除")
	 @DeleteMapping(value = "/deleteBatchItem")
	 public Result<?> deleteBatchItem(@RequestParam(name="ids",required=true) String ids) {
		 String[] split = ids.split(",");
		 for (String s : split) {
			 BusiPoItem busiPoItem = busiPoItemMapper.selectById(s);
			 busiPoItemMapper.deleteByMainIdItem(busiPoItem.getQuery04());
		 }
		 return Result.OK("批量删除成功！");
	 }

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_po-通过id查询")
	@ApiOperation(value="busi_po-通过id查询", notes="busi_po-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BusiPo busiPo = busiPoService.getById(id);
		if(busiPo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(busiPo);

	}

	 /**
	  * 查询子表list
	  *
	  * @param busiPoItem
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_po_item查询子表list")
	 @ApiOperation(value="busi_po_item查询子表list", notes="busi_po_item-查询子表list")
	 @GetMapping(value = "/BusiPoItemList")
	 public Result<?> queryBusiPoItemListByMainId(BusiPoItem busiPoItem,
												  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												  HttpServletRequest req){
		 QueryWrapper<BusiPoItem> queryWrapper = QueryGenerator.initQueryWrapper(busiPoItem, req.getParameterMap());
		 queryWrapper.lambda().groupBy(BusiPoItem::getQuery04);
		 Page<BusiPoItem> page = new Page<BusiPoItem>(pageNo, pageSize);
		 IPage<BusiPoItem> pageList = busiPoItemService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  * 查询子表list
	  *
	  * @param busiPoItem
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_po_item查询子表list")
	 @ApiOperation(value="busi_po_item查询子表list", notes="busi_po_item-查询子表list")
	 @GetMapping(value = "/BusiPoItemList2")
	 public Result<?> queryBusiPoItemListByMainId2(BusiPoItem busiPoItem,
												  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												  HttpServletRequest req){
		 QueryWrapper<BusiPoItem> queryWrapper = QueryGenerator.initQueryWrapper(busiPoItem, req.getParameterMap());
		 Page<BusiPoItem> page = new Page<BusiPoItem>(pageNo, pageSize);
		 IPage<BusiPoItem> pageList = busiPoItemService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }


	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_po_item通过主表ID查询")
	@ApiOperation(value="busi_po_item主表ID查询", notes="busi_po_item-通主表ID查询")
	@GetMapping(value = "/queryBusiPoItemByMainId")
	public Result<?> queryBusiPoItemListByMainId(@RequestParam(name="id",required=true) String id) {
		List<BusiPoItem> busiPoItemList = busiPoItemService.selectByMainId(id);
		return Result.OK(busiPoItemList);
	}

	 /**
	  * 通过主订单号查询全部
	  *
	  * @param busiPoItem 主订单号
	  * @return
	  */
	 @AutoLog(value = "busi_po_item通过主订单号查询全部")
	 @ApiOperation(value="busi_po_item通过主订单号查询全部", notes="busi_po_item-通过主订单号查询全部")
	 @GetMapping(value = "/BatchQueryBusiPoItemByMainId")
	 public Result<?> BatchQueryBusiPoItemByMainId(BusiPoItem busiPoItem,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												   HttpServletRequest req) {
		 QueryWrapper<BusiPoItem> queryWrapper = QueryGenerator.initQueryWrapper(busiPoItem, req.getParameterMap());
			 List<BusiPoItem> pageList = busiPoItemService.list(queryWrapper);
			 return Result.OK(pageList);
	 }
	 /**
	  *  编辑
	  *
	  * @param busiPoItem
	  * @return
	  */
	 @AutoLog(value = "busi_po_item-编辑")
	 @ApiOperation(value="busi_po_item-编辑", notes="busi_po_item-编辑")
	 @PutMapping(value = "/batchBusiPoItemEdit")
	 public Result<?> batchBusiPoItemEdit(@RequestBody List<BusiPoItem> busiPoItem) {
		 for (BusiPoItem poItem : busiPoItem) {
			 busiPoItemMapper.updateById(poItem);
		 }
		 return Result.OK("编辑成功!");
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param busiPo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BusiPo busiPo) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<BusiPo> queryWrapper = QueryGenerator.initQueryWrapper(busiPo, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<BusiPo> queryList = busiPoService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<BusiPo> busiPoList = new ArrayList<BusiPo>();
      if(oConvertUtils.isEmpty(selections)) {
          busiPoList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          busiPoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<BusiPoPage> pageList = new ArrayList<BusiPoPage>();
      for (BusiPo main : busiPoList) {
          BusiPoPage vo = new BusiPoPage();
          BeanUtils.copyProperties(main, vo);
          List<BusiPoItem> busiPoItemList = busiPoItemService.selectByMainId(main.getId());
          vo.setBusiPoItemList(busiPoItemList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "busi_po列表");
      mv.addObject(NormalExcelConstants.CLASS, BusiPoPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("busi_po数据", "导出人:"+sysUser.getRealname(), "busi_po"));
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
              List<BusiPoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), BusiPoPage.class, params);
			  int index = 0;
              for (BusiPoPage page : list) {
				  String code = generateCodeUtil.generateCode("busi_po","CGD");
              	  index++;
                  BusiPo po = new BusiPo();
                  BeanUtils.copyProperties(page, po);
				  po.setNum02(po.getNum01());
				  po.setNum03(new Double(0));
				  po.setQuery02("计划中");
				  po.setQuery04(code);
				  po.setQuery23(po.getQuery04()+ "-" +(index*10));
                  busiPoService.saveMain(po, page.getBusiPoItemList());
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





	// TODO 新增的采购所有接口，包含采购跟样品

	 /**
	  *  批量编辑
	  *
	  * @param busiPoPage
	  * @return
	  */
	 @AutoLog(value = "busi_po-编辑")
	 @ApiOperation(value="busi_po-编辑", notes="busi_po-编辑")
	 @PutMapping(value = "/batchEdit")
	 public synchronized Result<?> batchEdit(@RequestBody List<BusiPoPage> busiPoPage) {
		 // 查询使用单号查询
		 QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(BusiPo::getQuery13,busiPoPage.get(0).getQuery13());
		 List<BusiPo> busiPoList = busiPoService.list(queryWrapper);
		 for (BusiPo busiPo : busiPoList) {
			 busiPoMapper.deleteById(busiPo.getId());
		 }
		 // 重新插入数据
		 int index = 0;
		 for (BusiPoPage poPage : busiPoPage) {
			 index++;
			 BusiPo busiPo = new BusiPo();
			 BeanUtils.copyProperties(poPage, busiPo);
			 busiPo.setId("");
			 String SonPO = generateCodeUtil.getSonPO(index,busiPo.getQuery13());
			 busiPo.setQuery14(SonPO);
			 busiPo.setQuery23(busiPo.getQuery04()+ "-" +(index*10));
			 if(!isNull(busiPo.getNum03())){
				 if(busiPo.getNum03() == 0){
					 busiPo.setNum02(busiPo.getNum01());
				 }
			 }else {
				 busiPo.setNum02(busiPo.getNum01());
				 busiPo.setNum03(new Double(0));
			 }
			 if(!"打样已完成".equals(poPage.getQuery02()) && "YP".equals(poPage.getQuery01())){
				 QueryWrapper<BusiPoItem> queryWrapper5 = new QueryWrapper<>();
				 queryWrapper5.lambda().eq(BusiPoItem::getQuery14,poPage.getQuery14());
				 queryWrapper5.lambda().nested(i -> i.isNull(BusiPoItem::getQuery02).or().eq(BusiPoItem::getQuery02,""));
				 busiPoItemService.remove(queryWrapper5);

				 // 添加默认的打样进度
				 BusiPoItem busiPoItem = new BusiPoItem();
				 BeanUtils.copyProperties(busiPo, busiPoItem);
				 busiPoItem.setId("");
				 busiPoItem.setCreateBy("");
				 busiPoItem.setCreateName("");
				 busiPoItem.setCreateTime(null);
				 busiPoItem.setUpdateBy("");
				 busiPoItem.setUpdateName("");
				 busiPoItem.setUpdateTime(null);
				 busiPoItem.setQuery01("DYJD");
				 busiPoItem.setQuery02("");
				 busiPoItem.setLink01("样品单");
				 busiPoItem.setLink02(busiPo.getQuery23());
				 busiPoItem.setText01("");
				 busiPoItem.setText02("");
				 busiPoItem.setQuery13(busiPo.getQuery13());
				 busiPoItem.setQuery14(busiPo.getQuery14());
				 busiPoItemMapper.insert(busiPoItem);
			 }
			 busiPoMapper.insert(busiPo);
//			 busiPoService.saveMain(busiPo, poPage.getBusiPoItemList());
		 }
		 if(busiPoPage.get(0).getQuery17() != null && busiPoPage.get(0).getQuery17() != ""){
			 QueryWrapper<BusiPaymentReceived> queryWrapper3 = new QueryWrapper<>();
			 queryWrapper3.lambda().eq(BusiPaymentReceived::getQuery13,busiPoPage.get(0).getQuery13());
			 List<BusiPaymentReceived> list2 = busiPaymentReceivedService.list(queryWrapper3);
			 if(CollectionUtil.isNotEmpty(list2)){
				 for (BusiPaymentReceived busiPaymentReceived : list2) {
					 busiPaymentReceived.setQuery17(busiPoPage.get(0).getQuery17());
					 busiPaymentReceivedService.updateById(busiPaymentReceived);
				 }
			 }

			 QueryWrapper<BusiPoItem> queryWrapper4 = new QueryWrapper<>();
			 queryWrapper4.lambda().eq(BusiPoItem::getQuery13,busiPoPage.get(0).getQuery13());
			 List<BusiPoItem> list3 = busiPoItemService.list(queryWrapper4);
			 if(CollectionUtil.isNotEmpty(list3)){
				 for (BusiPoItem busipoitem : list3) {
					 busipoitem.setQuery17(busiPoPage.get(0).getQuery17());
					 busiPoItemService.updateById(busipoitem);
				 }
			 }
		 }
		 return Result.OK("编辑成功!");
	 }





	 /**
	  * 分页列表查询，按照单号分组
	  *
	  * @param busiPo
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_po-分页列表查询")
	 @ApiOperation(value="busi_po-分页列表查询", notes="busi_po-分页列表查询")
	 @GetMapping(value = "/list2")
	 public Result<?> queryPageList2(BusiPo busiPo,
									 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									 @RequestParam(name="sT",required = false) String  sT,
									 @RequestParam(name="nT",required = false) String nT,
									 HttpServletRequest req) {
		 String xingYeFenLei = busiPo.getXingYeFenLei();
		 busiPo.setXingYeFenLei(null);

		 QueryWrapper<BusiPo> queryWrapper = QueryGenerator.initQueryWrapper(busiPo, req.getParameterMap());
		 if(StringUtils.isNotBlank(sT)){
			 queryWrapper.lambda().ge(BusiPo::getCreateTime,sT);
		 }
		 if(StringUtils.isNotBlank(nT)){
			 queryWrapper.lambda().le(BusiPo::getCreateTime,nT);
		 }
		 if(StringUtils.isNotEmpty(xingYeFenLei)){
			 QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
			 MdCusqueryWrapper.lambda().eq(MdCus::getXingYeFenLei,xingYeFenLei);
			 List<MdCus> list = mdCusService.list(MdCusqueryWrapper);
			 List<String> arr = new ArrayList<>();
			 for (MdCus mdCus : list) {
				 arr.add(mdCus.getKeHuBianMa());
			 }
			 queryWrapper.lambda().in(BusiPo::getQuery24, arr);
		 }


		 queryWrapper.lambda().groupBy(BusiPo::getQuery04);
		 queryWrapper.lambda().orderByDesc(BusiPo::getCreateTime);
		 Page<BusiPo> page = new Page<BusiPo>(pageNo, pageSize);
		 IPage<BusiPo> pageList = busiPoService.page(page, queryWrapper);
		 if("YP".equals(busiPo.getQuery01())){
			 for (BusiPo record : pageList.getRecords()) {
				LambdaQueryWrapper<BusiPo> BusiPoWrapper  = new LambdaQueryWrapper<BusiPo>();
				BusiPoWrapper.eq(BusiPo::getQuery13,record.getQuery13());
				BusiPoWrapper.ne(BusiPo::getQuery02,"打样已完成");
				 List<BusiPo> list = busiPoService.list(BusiPoWrapper);
				 if(CollectionUtil.isNotEmpty(list)){
					 record.setQuery02(list.get(0).getQuery02());
				 }
			 }
		 }

		 QueryWrapper<BusiPo> queryWrapper2 = QueryGenerator.initQueryWrapper(busiPo, req.getParameterMap());
		 queryWrapper2.lambda().orderByDesc(BusiPo::getCreateTime);
		 Page<BusiPo> page2 = new Page<BusiPo>(pageNo, pageSize);
		 IPage<BusiPo> pageList2 = busiPoService.page(page2, queryWrapper2);

		 for (BusiPo record : pageList.getRecords()) {
			 String img = "";
			 for (BusiPo record2 : pageList2.getRecords()) {
				 if(record.getQuery04().equals(record2.getQuery04())){
					 if(!StringUtils.isEmpty(record2.getAttr2())){
						 if(img == ""){
							 img= record2.getAttr2();
						 }else {
							 img = img + "," + record2.getAttr2();
						 }
					 }
					 record.setAttr2(img);
				 }
			 }

			 QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
			 MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,record.getQuery24());
			 MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
			 record.setXingYeFenLei(one2.getXingYeFenLei());

			 QueryWrapper<BusiPaymentReceived> PqueryWrapper =  new QueryWrapper<>();
			 PqueryWrapper.lambda().eq(BusiPaymentReceived::getQuery13,record.getQuery13());
			 PqueryWrapper.lambda().eq(BusiPaymentReceived::getQuery01,"FKD");
			 List<BusiPaymentReceived> list = busiPaymentReceivedService.list(PqueryWrapper);
			 BigDecimal i = new BigDecimal(0);
			 for (BusiPaymentReceived busiPaymentReceived : list) {
				 BigDecimal bigDecimal = new BigDecimal(busiPaymentReceived.getNum09().toString());
				 i = i.add(bigDecimal);
			 }
			 BigDecimal Num09 = new BigDecimal(record.getNum09().toString());
			 BigDecimal subtract = Num09.subtract(i);
			 record.setRemainingSum(String.valueOf(subtract));
		 }

		 return Result.OK(pageList);
	 }


	 /**
	  *   批量添加采购主表
	  *
	  * @param busiPoPage
	  * @return
	  */
	 @AutoLog(value = "busi_po-批量添加")
	 @ApiOperation(value="busi_po-批量添加", notes="busi_po-批量添加")
	 @PostMapping(value = "/batchAdd")
	 public synchronized Result<?> batchAdd(@RequestBody List<BusiPoPage> busiPoPage) {
		 if(CollectionUtil.isEmpty(busiPoPage)){
			 return Result.error("数据为空");
		 }
		 int index = 0;
		 BusiPoPage poItem = busiPoPage.get(0);

		 String code = generateCodeUtil.generateCode("busi_po",poItem.getQuery01());
		 String PO = generateCodeUtil.generateCode("busi_po","PO");
		 for (BusiPoPage poPage : busiPoPage) {
			 index++;
			 BusiPo busiPo = new BusiPo();
			 BeanUtils.copyProperties(poPage, busiPo);
             busiPo.setNum02(poPage.getNum01());
             busiPo.setQuery13(PO); // 主PO
             String SonPO = generateCodeUtil.getSonPO(index,PO);
             busiPo.setQuery14(SonPO);
			 busiPo.setNum03(new Double(0));
			 busiPo.setQuery02("计划中");
			 busiPo.setQuery04(code);
			 busiPo.setQuery23(busiPo.getQuery04()+ "-" +(index*10));
			 busiPo.setId("");
			 busiPo.setCreateBy("");
			 busiPo.setCreateName("");
			 busiPo.setCreateTime(null);
			 busiPo.setUpdateBy("");
			 busiPo.setUpdateName("");
			 busiPo.setUpdateTime(null);
			 busiPo.setQuery37("未确认");
			 busiPoMapper.insert(busiPo);
		 }
		 return Result.OK("添加成功！");
	 }



	 /**
	  *   批量添加样品单
	  *
	  * @param busiPoPage
	  * @return
	  */
	 @AutoLog(value = "busi_po-批量添加样品单")
	 @ApiOperation(value="busi_po-批量添加样品单", notes="busi_po-批量添加样品单")
	 @PostMapping(value = "/batchYPAdd")
	 public synchronized Result<?> batchYPAdd(@RequestBody List<BusiPoPage> busiPoPage) {
		 if(CollectionUtil.isEmpty(busiPoPage)){
			 return Result.error("数据为空");
		 }
		 int index = 0;
		 BusiPoPage poItem = busiPoPage.get(0);
		 String code = generateCodeUtil.generateCode("busi_po",poItem.getQuery01());
		 String code2 = generateCodeUtil.generateCode("busi_po_item","DYJD");
		 String PO = generateCodeUtil.generateCode("busi_po","PO");
		 for (BusiPoPage poPage : busiPoPage) {
			 // 添加样品单列表
			 index++;
			 //未清数量
			 poPage.setNum02(poPage.getNum01());
			 //已完成数量
			 poPage.setNum03(new Double(0));
			 BusiPo busiPo = new BusiPo();
			 BeanUtils.copyProperties(poPage, busiPo);
			 //单号
			 busiPo.setQuery04(code);
			 //主批次
			busiPo.setQuery13(PO); // 主PO
			String SonPO = generateCodeUtil.getSonPO(index,PO);
			//子批次
			busiPo.setQuery14(SonPO);
			 //单据状态
			 busiPo.setQuery02("计划中");
			 String serial = busiPo.getQuery04()+ "-" +(index*10);
			 //单号-行项目号
			 busiPo.setQuery23(serial);
			 busiPo.setId("");
			 busiPo.setCreateBy("");
			 busiPo.setCreateName("");
			 busiPo.setCreateTime(null);
			 busiPo.setUpdateBy("");
			 busiPo.setUpdateName("");
			 busiPo.setUpdateTime(null);
			 busiPoMapper.insert(busiPo);
			 // 添加默认的打样进度
			 BusiPoItem busiPoItem = new BusiPoItem();
			 BeanUtils.copyProperties(poPage, busiPoItem);
			 busiPoItem.setId("");
			 busiPoItem.setCreateBy("");
			 busiPoItem.setCreateName("");
			 busiPoItem.setCreateTime(null);
			 busiPoItem.setUpdateBy("");
			 busiPoItem.setUpdateName("");
			 busiPoItem.setUpdateTime(null);
			 busiPoItem.setQuery04(code2);
			 busiPoItem.setQuery01("DYJD");
			 busiPoItem.setQuery02("");
			 busiPoItem.setLink01("样品单");
			 busiPoItem.setLink02(busiPo.getQuery23());
			 busiPoItem.setText01("");
			 busiPoItem.setText02("");
			 busiPoItem.setQuery13(busiPo.getQuery13());
			 busiPoItem.setQuery14(busiPo.getQuery14());
			 busiPoItemMapper.insert(busiPoItem);
		 }
		 return Result.OK("添加成功！");
	 }






	 /**
	  *   入库预约批量添加-推送WMS
	  *
	  * @param busiPoItemPage
	  * @return
	  */
	 @AutoLog(value = "busi_Po_Item-批量添加")
	 @ApiOperation(value="busi_Po_Item-批量添加", notes="busi_Po_Item-批量添加")
	 @PostMapping(value = "/batchAddItem")
	 public synchronized Result<?> batchAddItem(@RequestBody List<BusiPoItem> busiPoItemPage) {
		 if(CollectionUtil.isEmpty(busiPoItemPage)){
			 return Result.error("数据为空");
		 }
		 // 修改源数据状态
		 BusiPoItem busiPoItem2 = busiPoItemPage.get(0);
		 if("已完成".equals(busiPoItem2.getQuery02())){
			 return Result.error("单据已完成");
		 }

		 // 循环新增
		 int index = 0;
		 String code = generateCodeUtil.generateCode("busi_po_item",busiPoItem2.getQuery01());
		 String PO = "";
		 String str = "";
		 if(StringUtils.isNotEmpty(busiPoItem2.getQuery04())){
			 pltnSetState.setState("入库中",busiPoItem2.getQuery01(),busiPoItem2.getQuery04());
			 str = busiPoItem2.getQuery04();
		 }
		 for (BusiPoItem poItemPage : busiPoItemPage) {
			 index++;
			 poItemPage.setId("");
			 poItemPage.setQuery01(busiPoItem2.getQuery01());
			 if(!"已完成".equals(poItemPage.getQuery02())){
				 if(StringUtils.isNotEmpty(str)){
					poItemPage.setQuery02("入库中");
				 }else {
					poItemPage.setQuery02("未推送");
				 }
			 }
			 if(poItemPage.getQuery13() == "" || poItemPage.getQuery13() == null){
			 	if(StringUtil.isBlankOrNull(PO)){
					PO = generateCodeUtil.generateCode("busi_po","PO");
				}
				 poItemPage.setQuery13(PO); // 主PO
				 String SonPO = generateCodeUtil.getSonPO(index,PO);
				 poItemPage.setQuery14(SonPO);
			 }
			 // poItemPage.setLink01("采购订单");
			 poItemPage.setQuery18("入库预约");
			 poItemPage.setLink02(poItemPage.getQuery04());
			 poItemPage.setQuery04(code);
			 poItemPage.setQuery23(code + "-" + (index*10));
			 poItemPage.setCreateName(null);
			 poItemPage.setCreateBy(null);
			 poItemPage.setCreateTime(null);
			 poItemPage.setUpdateName(null);
			 poItemPage.setUpdateBy(null);
			 poItemPage.setUpdateTime(null);
			 poItemPage.setSysOrgCode(null);
			 busiPoItemMapper.insert(poItemPage);
		 }
		 if("1".equals(str)){
			 return Result.error(str + "入库数量不能大于未清数量");
		 }

		 // 推送WMS
		 if(StringUtils.isNotEmpty(str)){
			 pltnPushWms.putInOrder(busiPoItemPage);
		 }
		 return Result.OK("添加成功！");
	 }

	 /**
	  *   样品进度保存
	  *
	  * @param busiPoItem
	  * @return
	  */
	 @AutoLog(value = "样品进度保存")
	 @ApiOperation(value="样品进度保存", notes="样品进度保存")
	 @PostMapping(value = "/scheduleSave")
	 public Result<?> scheduleSave(@RequestBody BusiPoItem busiPoItem) {
		 BusiPoItem busiPoItem1 = busiPoItemMapper.selectById(busiPoItem.getId());
		 if(busiPoItem1 == null){
			 return Result.error("未查询到此数据");
		 }
		 QueryWrapper<BusiPoItem>  queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(BusiPoItem::getLink02,busiPoItem1.getLink02());
		 queryWrapper.lambda().eq(BusiPoItem::getQuery01,"DYJD");
		 List<BusiPoItem> list = busiPoItemService.list(queryWrapper);
		 int index = 1;
		 if(CollectionUtil.isNotEmpty(list)){
			 index = list.size();
		 }
		 // 创建新的打样进度
		 BusiPoItem PoItem = new BusiPoItem();
		 BeanUtils.copyProperties(busiPoItem1, PoItem);
		 PoItem.setId("");
		 PoItem.setCreateBy("");
		 PoItem.setCreateName("");
		 PoItem.setCreateTime(null);
		 PoItem.setUpdateBy("");
		 PoItem.setUpdateName("");
		 PoItem.setUpdateTime(null);
		 PoItem.setUpdateTime(null);

		 // 更新源数据--插入进度类型，附件，备注
		 busiPoItem1.setAttr1(busiPoItem.getAttr1());
		 busiPoItem1.setText01(busiPoItem.getText01());
		 busiPoItem1.setQuery02(busiPoItem.getQuery02());
		 busiPoItemMapper.updateById(busiPoItem1);

		 if("1".equals(busiPoItem.getQuery30())){//1继续打样  2，完成打样
			 PoItem.setAttr1("");
			 PoItem.setText01("");
			 busiPoItemMapper.insert(PoItem);
		 }else{
//			 QueryWrapper<BusiPoItem> queryWrapper4 = new QueryWrapper<>();
//			 queryWrapper4.lambda().eq(BusiPoItem::getQuery13,PoItem.getQuery13());
//			 queryWrapper4.lambda().nested(item->item.isNull(BusiPoItem::getQuery02).or().eq(BusiPoItem::getQuery02,""));
//			 List<BusiPoItem> list3 = busiPoItemService.list(queryWrapper4);
//			 if(CollectionUtil.isEmpty(list3)){
				 QueryWrapper<BusiPo>  busiPoQueryWrapper = new QueryWrapper<>();
				 busiPoQueryWrapper.lambda().eq(BusiPo::getQuery14,busiPoItem1.getQuery14());
				 List<BusiPo> list1 = busiPoService.list(busiPoQueryWrapper);
				 for (BusiPo busiPo : list1) {
					 busiPo.setQuery02("打样已完成");
					 busiPoService.updateById(busiPo);
				 }
//			 }
		 }





		 // 创建来样管理
		 BusiPoItem PoItem2 = new BusiPoItem();
		 BeanUtils.copyProperties(PoItem, PoItem2);
		 PoItem2.setAttr1(busiPoItem.getAttr1());
		 PoItem2.setText01(busiPoItem.getText01());
		 PoItem2.setId("");
		 PoItem2.setCreateBy("");
		 PoItem2.setCreateName("");
		 PoItem2.setCreateTime(null);
		 PoItem2.setUpdateBy("");
		 PoItem2.setUpdateName("");
		 PoItem2.setUpdateTime(null);
		 PoItem2.setUpdateTime(null);
		 PoItem2.setQuery01("LYGL");
		 PoItem2.setQuery02("未推送");
		 String code = generateCodeUtil.generateCode("busi_po_item","LYGL");
		 PoItem2.setQuery04(code);
		 PoItem2.setQuery29(String.valueOf(index));
		 busiPoItemMapper.insert(PoItem2);
		 return Result.OK("操作成功！");
	 }

	 /**
	  *   批量推送WMS接口
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "busi_Po_Item-推送WMS接口")
	 @ApiOperation(value="busi_Po_Item-推送WMS接口", notes="busi_Po_Item-推送WMS接口")
	 @GetMapping(value = "/samplePoPushWms")
	 public Result<?> samplePoPushWms( @RequestParam(name="ids") String ids) {
		 String[] split = ids.split(",");
		 List<String> list = new ArrayList<>();
		 for (String s : split) {
			 list.add(s);
		 }
		 //根据id批量查询
		 List<BusiPoItem> busiPoItems = busiPoItemService.listByIds(list);
		 for (BusiPoItem busiPoItem : busiPoItems) {
			 busiPoItem.setQuery02("已推送");
			 busiPoItemService.updateById(busiPoItem);

		 }
		 // 按主PO分次推送WMS
		 Map<String, List<BusiPoItem>> busiPo = busiPoItems.stream().collect(Collectors.groupingBy(BusiPoItem::getQuery13, Collectors.toList()));
		 for (Map.Entry<String, List<BusiPoItem>> entry : busiPo.entrySet()) {
 			 List<BusiPoItem> value = entry.getValue();
			 if("RKYY".equals(value.get(0).getQuery01())){
				 pltnPushWms.putInOrder(value);
			 }else {
				 pltnPushWms.sampleOrder(value);
			 }
		 }
		 return Result.OK("添加成功！");
	 }

	 /**
	  *   QC上传检验信息
	  *
	  * @param query04 操作单号
	  * @param query01 检验单据类型
	  * @param attr2 附件
	  * @param text03 备注
	  * @return
	  */
	 @AutoLog(value = "QC上传检验信息")
	 @ApiOperation(value="QC上传检验信息", notes="QC上传检验信息")
	 @GetMapping(value = "/QcReport")
	 public Result<?> QcReport(@RequestParam(name="query04",required = true) String query04,
							   @RequestParam(name="query01",required = true) String query01,
							   @RequestParam(name="attr2",required = false) String attr2,
							   @RequestParam(name="attr3",required = false) String attr3,
							   @RequestParam(name="text03",required = false) String text03
							   ) {
		 if("CGD".equals(query01) || "yp".equals(query01)){ // 采购单,样品单
			 QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<>();
			 queryWrapper.lambda().eq(BusiPo::getQuery04,query04);
			 List<BusiPo> list = busiPoService.list(queryWrapper);
			 for (BusiPo busiPo : list) {
				 if(attr2 != "" && attr2 != null){
					 busiPo.setAttr2(attr2);
				 }
				 if(attr3 != "" && attr3 != null){
					 busiPo.setAttr3(attr3);
				 }
				 if(text03 != "" && text03 != null){
					 busiPo.setText03(text03);
				 }
				 busiPoService.updateById(busiPo);
			 }
		 }else if("SCWG".equals(query01)){ // 加工单
			 QueryWrapper<BusiPrdOrd> queryWrapper = new QueryWrapper<>();
			 queryWrapper.lambda().eq(BusiPrdOrd::getQuery04,query04);
			 List<BusiPrdOrd> list = busiPrdOrdService.list(queryWrapper);
			 if(CollectionUtil.isNotEmpty(list)){
				 // 生产完工保存凭证
				 BusiPrdOrd busiPrdOrd1 = list.get(0);
				 QueryWrapper<BusiPrdOrd> queryWrapper2 = new QueryWrapper<>();
				 queryWrapper2.lambda().eq(BusiPrdOrd::getQuery14,busiPrdOrd1.getQuery14());
				 List<BusiPrdOrd> BusiPrdOrdlist = busiPrdOrdService.list(queryWrapper2);
				// 质检订单保存凭证
				 for (BusiPrdOrd busiPrdOrd : BusiPrdOrdlist) {
					 busiPrdOrd.setAttr2(attr2);
					 if(text03 != "" && text03 != null){
						 busiPrdOrd.setText03(text03);
					 }
					 busiPrdOrdService.updateById(busiPrdOrd);
				 }

				 // 采购单保存凭证
				 QueryWrapper<BusiPo> queryWrapperPO = new QueryWrapper<>();
				 queryWrapperPO.lambda().eq(BusiPo::getQuery14,busiPrdOrd1.getQuery14());
				 List<BusiPo> listpo = busiPoService.list(queryWrapperPO);
				 for (BusiPo busiPo : listpo) {
					 if(!StringUtil.isBlankOrNull(attr2)){
						 busiPo.setAttr2(attr2);
					 }
					 if(!StringUtil.isBlankOrNull(text03)){
						 busiPo.setText03(text03);
					 }
					 busiPoService.updateById(busiPo);
				 }
			 }

		 }
		 return Result.OK("添加成功！");
	 }
	 /**
	  *   QC上传检验信息
	  *
	  * @param query13 PO号
	  * @param query37 是否确认
	  * @param query01 单据类型
	  * @return
	  */
	 @AutoLog(value = "QC上传检验信息")
	 @ApiOperation(value="QC上传检验信息", notes="QC上传检验信息")
	 @GetMapping(value = "/editquery37")
	 public Result<?> editquery37(@RequestParam(name="query13",required = true) String query13,
							   @RequestParam(name="query37",required = true) String query37,
							   @RequestParam(name="query01",required = true) String query01
	 ) {
		 QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(BusiPo::getQuery13,query13);
		 queryWrapper.lambda().eq(BusiPo::getQuery01,query01);
		 List<BusiPo> list = busiPoService.list(queryWrapper);
		 for (BusiPo busiPo : list) {
			 busiPo.setQuery37(query37);
			 // 获取当前时间
			 LocalDateTime now = LocalDateTime.now();
			 // 定义日期时间格式
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			 // 格式化当前时间
			 String formattedDateTime = now.format(formatter);
			 busiPo.setQuery43(formattedDateTime);
			 busiPoService.updateById(busiPo);
		 }
		 return Result.OK("添加成功！");
	 }

	/**
	 *   修改状态
	 *
	 * @param query13 PO号
	 * @param query02 类型
	 * @param query01 单据类型
	 * @return
	 */
	@AutoLog(value = "修改状态")
	@ApiOperation(value="修改状态", notes="修改状态")
	@GetMapping(value = "/editquery02")
	public Result<?> editquery02(@RequestParam(name="query13",required = true) String query13,
								 @RequestParam(name="query02",required = true) String query02,
								 @RequestParam(name="query01",required = true) String query01
	) {
		QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(BusiPo::getQuery13,query13);
		queryWrapper.lambda().eq(BusiPo::getQuery01,query01);
		List<BusiPo> list = busiPoService.list(queryWrapper);
		for (BusiPo busiPo : list) {
			busiPo.setQuery02(query02);
			busiPoService.updateById(busiPo);
		}
		return Result.OK("成功！");
	}

	 /**
	  * 采购完成，完成原因
	  *
	  * @param ordId
	  * @param query22
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_po-分页列表查询")
	 @ApiOperation(value="busi_po-分页列表查询", notes="busi_po-分页列表查询")
	 @GetMapping(value = "/finishCause")
	 public Result<?> finishCause(@RequestParam(name="ordId", required=true) String ordId,
								  @RequestParam(name="query22", required=true) String query22,
									 HttpServletRequest req) {
		 QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(BusiPo::getQuery04,ordId);
		 List<BusiPo> list = busiPoService.list(queryWrapper);
		 List<BusiPo> list2 = new ArrayList<>();
		 for (BusiPo busiPo : list) {
			 busiPo.setQuery40(query22);
			 busiPo.setQuery02("已完成");
			 if(!busiPo.getNum01().equals(busiPo.getNum03())){
				 list2.add(busiPo);
			 }
			 busiPoService.updateById(busiPo);
		 }

		 // 修改入库数量不一致金额
		 if(CollectionUtil.isNotEmpty(list2)){
		 	// 查询关联的计划付款单
			 QueryWrapper<BusiPaymentReceived> queryWrapperPayment = new QueryWrapper<>();
			 queryWrapperPayment.lambda().eq(BusiPaymentReceived::getLink02,list2.get(0).getQuery04());
			 queryWrapperPayment.lambda().orderByDesc(BusiPaymentReceived::getCreateTime);
			 List<BusiPaymentReceived> list1 = busiPaymentReceivedService.list(queryWrapperPayment);

			 Double hszj = new Double(0); // 含税总金额
			 Double bhszj = new Double(0); // 不含税总金额
			 for (BusiPo busiPo : list2) {
				 hszj = hszj + busiPo.getNum02() * busiPo.getNum04();
				 bhszj = bhszj + busiPo.getNum02() * busiPo.getNum04();
			 }

			 int index = 0;
			 for (BusiPaymentReceived paymentReceived : list1) {
			 	try {
					if(index == 0){
						paymentReceived.setNum14(paymentReceived.getNum14() - hszj);
						paymentReceived.setNum15(paymentReceived.getNum15() - bhszj);
					}
					paymentReceived.setNum08(paymentReceived.getNum08() - hszj);
					paymentReceived.setNum09(paymentReceived.getNum09() - bhszj);
					busiPaymentReceivedService.updateById(paymentReceived);
					index++;
				}catch (Exception e){
			 		continue;
				}
			 }
		 }
		 return Result.OK();
	 }


	 /**
	  * Excel/Pdf数据
	  *
	  * @param orderids
	  * @param type
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_po-分页列表查询")
	 @ApiOperation(value="busi_po-分页列表查询", notes="busi_po-分页列表查询")
	 @GetMapping(value = "/orderExcelOrPdf")
	 public Result<?> orderExcelOrPdf(@RequestParam(name="orderids", required=true) String orderids,
									  @RequestParam(name="type", required=true) String type,
									  HttpServletRequest req) {
		 List<BusiPo> list3 = new ArrayList<>();

		 if( !"XSFP".equals(type) && !"XSFPHZ".equals(type) && !"CGZJ".equals(type) && !"ZJDD".equals(type)){
			 String[] split1 = orderids.split(",");
			 for (String s : split1) {
				 QueryWrapper<BusiPo> BusiPoqueryWrapper = new QueryWrapper<>();
				 //单号
				 BusiPoqueryWrapper.lambda().eq(BusiPo::getQuery04,s);
				 BusiPoqueryWrapper.lambda().groupBy(BusiPo::getQuery04);
				 List<BusiPo> list = busiPoService.list(BusiPoqueryWrapper);
				 list3.addAll(list);
			 }
			 if(CollectionUtil.isEmpty(list3)){
				 return Result.error("未查询到单据");
			 }
		 }
		 List<BusiPoPage> BusiPoPageList = new ArrayList<>();
		 if("CGHS".equals(type)){ // 采购含税
			 BusiPoPageList = busiPoService.CGHS(list3);
		 }else if("CGBHS".equals(type)){ // 采购不含税
			 BusiPoPageList = busiPoService.CGHS(list3);
		 }else if("YPHS".equals(type)){ // 样品含税
			 BusiPoPageList = busiPoService.CGHS(list3);
		 }else if("YPBHS".equals(type)){ // 样品不含税
			 BusiPoPageList = busiPoService.CGHS(list3);
		 }else if("FKD".equals(type)){ // 付款单
			 BusiPoPageList = busiPoService.FKD(list3);
		 }else if("XSFP".equals(type)){ // 形式发票
			 String[] split = orderids.split(",");
			 List<BusiPaymentReceived> list2 = new ArrayList<>();
			 for (String s : split) {
				 QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
				 queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04,s);
//				 queryWrapper.lambda().groupBy(BusiPaymentReceived::getQuery04);
				 List<BusiPaymentReceived> list1 = busiPaymentReceivedService.list(queryWrapper);
				 list2.addAll(list1);
			 }
			 BusiPoPageList = busiPoService.XSFP(list2);
		 }else if("XSFPHZ".equals(type)){ // 形式发票汇总
			 String[] split = orderids.split(",");
			 List<BusiPaymentReceived> list2 = new ArrayList<>();
			 for (String s : split) {
				 QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
				 queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04,s);
//				 queryWrapper.lambda().groupBy(BusiPaymentReceived::getQuery04);
				 List<BusiPaymentReceived> list1 = busiPaymentReceivedService.list(queryWrapper);
				 list2.addAll(list1);
			 }
			 BusiPoPageList = busiPoService.XSFPHZ(list2);
		 }else if("CGZJ".equals(type)){ // 采购质检单
			 List<WmImNoticeI> zjdd = busiPoService.CGZJ(orderids);
			 return Result.OK(zjdd);
		 }else if("ZJDD".equals(type)){ // 质检订单
			 List<WmImNoticeI> zjdd = busiPoService.ZJDD(orderids);
			 return Result.OK(zjdd);
		 }
		 return Result.OK(BusiPoPageList);
	 }



	 /**
	  * 分页列表查询
	  *
	  * @param busiPo
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_po-分页列表查询")
	 @ApiOperation(value="busi_po-分页列表查询", notes="busi_po-分页列表查询")
	 @GetMapping(value = "/getMakeOutAnInvoiceData")
	 public Result<?> getMakeOutAnInvoiceData(BusiPo busiPo,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 Page<BusiPoPage> page = new Page<BusiPoPage>(pageNo, pageSize);
		 HashMap hashMap = new HashMap(1024);
		 IPage<BusiPoPage> iPage = busiPoMapper.MakeOutAnInvoiceData(page, hashMap);
		 return Result.OK(iPage);
	 }



	 /**
	  * 总金额一览表查询
	  *
	  * @param busiPo
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "busi_po-总金额一览表")
	 @ApiOperation(value="busi_po-总金额一览表查询", notes="busi_po-总金额一览表")
	 @GetMapping(value = "/getTotalMoney")
	 public Result<?> getTotalMoney(BusiPo busiPo,
											  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
											  @RequestParam(name="type", defaultValue="GYS") String type,
											  HttpServletRequest req) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		 Page<BusiPoPage> page = new Page<BusiPoPage>(pageNo, pageSize);
		 HashMap hashMap = new HashMap(1024);
		 hashMap.put("query01",busiPo.getQuery01());
		 hashMap.put("query02",busiPo.getQuery02());
		 hashMap.put("query03",busiPo.getQuery03());
         // 查询出所有要展示的供应商 币种  订单类型
		 IPage<BusiPoPage> iPage = null;
		 if("GYS".equals(type)){
			 iPage = busiPoMapper.selectTotalMoneyList(page, hashMap);
		 }else {
			 iPage = busiPoMapper.selectTotalMoneyListKH(page, hashMap);
		 }
		 QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().orderByAsc(BusiPo::getCreateTime);
		 List<BusiPo> list = busiPoService.list(queryWrapper);
		 if(CollectionUtil.isNotEmpty(list)){
			 BusiPo busiPo1 = list.get(0);
			 SimpleDateFormat sdf = new SimpleDateFormat("yy");
			 String format = sdf.format(busiPo1.getCreateTime());
			 String format1 = sdf.format(new Date());

			 int i = Integer.parseInt(format);
			 int i1 = Integer.parseInt(format1);
			 int i2 = i1 - i + 1  ;
			 if(i2 == 0){
				 for (BusiPoPage record : iPage.getRecords()) {
					 record.setAttr1(String.valueOf(i));
					 Class<?> css = record.getClass();
						 HashMap hashMap2 = new HashMap(1024);
						 hashMap2.put("query01",record.getQuery01());
						 hashMap2.put("query02",record.getQuery02());
						 hashMap2.put("query03",record.getQuery03());
						 hashMap2.put("query04",i1);
					 List<BusiPoPage> list1 = null;
						 if("GYS".equals(type)){
							 list1 = busiPoMapper.selectTotalMoneyList2(hashMap2);
						 }else {
							 list1 = busiPoMapper.selectTotalMoneyList2KH(hashMap2);
						 }
						 if(CollectionUtil.isNotEmpty(list1)){
							 int h = 4;
							 int i4 = h + 0;
							 String s = String.valueOf(i4);
							 if(i4 < 10){
								 s= "0"+ s;
							 }
							 Method method= null;
							 method = css.getMethod("setQuery"+ s, String.class);
							 method.invoke(record, list1.get(0).getQuery04());
						 }
				 }
			 }else {
				 for (BusiPoPage record : iPage.getRecords()) {
					 record.setAttr1(String.valueOf(i));
					 Class<?> css = record.getClass();
					 for (int i3 = 0; i3 < i2; i3++) {
						 int index = i + i3;
						 HashMap hashMap2 = new HashMap(1024);
						 hashMap2.put("query01",record.getQuery01());
						 hashMap2.put("query02",record.getQuery02());
						 hashMap2.put("query03",record.getQuery03());
						 hashMap2.put("query04","20" + index);
						 List<BusiPoPage> list1 = null;
						 if("GYS".equals(type)){
							 list1 = busiPoMapper.selectTotalMoneyList2(hashMap2);
						 }else {
							 list1 = busiPoMapper.selectTotalMoneyList2KH(hashMap2);
						 }
						 if(CollectionUtil.isNotEmpty(list1)){
							 int h = 4;
							 int i4 = h + i3;
							 String s = String.valueOf(i4);
							 if(i4 < 10){
								 s= "0"+ s;
							 }
							 Method method= null;
							 method = css.getMethod("setQuery"+ s, String.class);
							 method.invoke(record, list1.get(0).getQuery04());
						 }
					 }
				 }

			 }
		 }

		 return Result.OK(iPage);
	 }





	/**
	 * 报表查询
	 *
	 * @param
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "BusiQuery-分页列表查询")
	@ApiOperation(value="BusiQuery-分页列表查询", notes="BusiQuery-分页列表查询")
	@GetMapping(value = "/getQuery")
	public Result<?> getQuery(BusiQueryPage busiquerypage,
							  @RequestParam(name="type") String type,
							  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
							  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
							  HttpServletRequest req) {
		Page<BusiQueryPage> page = new Page<BusiQueryPage>(pageNo, pageSize);
		HashMap hashMap = new HashMap(1024);
		IPage<BusiQueryPage> iPage = null;
		hashMap.put("query01",busiquerypage.getQuery01());
		hashMap.put("query02",busiquerypage.getQuery02());
		hashMap.put("query03",busiquerypage.getQuery03());
		hashMap.put("query04",busiquerypage.getQuery04());
		hashMap.put("query05",busiquerypage.getQuery05());
		hashMap.put("query06",busiquerypage.getQuery06());
		hashMap.put("query07",busiquerypage.getQuery07());
		hashMap.put("query08",busiquerypage.getQuery08());
		hashMap.put("query09",busiquerypage.getQuery09());
		hashMap.put("query10",busiquerypage.getQuery10());
		hashMap.put("query11",busiquerypage.getQuery11());
		hashMap.put("query12",busiquerypage.getQuery12());
		hashMap.put("query13",busiquerypage.getQuery13());
		hashMap.put("query14",busiquerypage.getQuery14());
		hashMap.put("query15",busiquerypage.getQuery15());
		hashMap.put("query16",busiquerypage.getQuery16());
		hashMap.put("query17",busiquerypage.getQuery17());
		hashMap.put("query18",busiquerypage.getQuery18());
		hashMap.put("query19",busiquerypage.getQuery19());
		hashMap.put("query20",busiquerypage.getQuery20());
		if("canPinGenJinTongJi".equals(type)){
			iPage = busiPoMapper.canPinGenJinTongJi(page, hashMap);
		}else if("chaXunMeiTianDingDan".equals(type)){
			iPage = busiPoMapper.chaXunMeiTianDingDan(page, hashMap);
		}else if("XiangChuKuXiangQing".equals(type)){
			iPage = busiPoMapper.XiangChuKuXiangQing(page, hashMap);
		}else if("huokuanzhichu".equals(type)){
			iPage = busiPoMapper.huokuanzhichu(page, hashMap);
		}else if("rukumingxi".equals(type)){
			iPage = busiPoMapper.rukumingxi(page, hashMap);
		}
		return Result.OK(iPage);
	}



	/**
	 * 导出excel
	 *
	 * @param request
	 * @param
	 */
	@RequestMapping(value = "/getQueryExportXls")
	public ModelAndView getQueryExportXls(HttpServletRequest request, BusiQueryPage busiquerypage) {
		// Step.1 组装查询条件查询数据
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		Page<BusiQueryPage> page = new Page<BusiQueryPage>(1, 10000000);
		HashMap hashMap = new HashMap(1024);
		IPage<BusiQueryPage> iPage = null;
		hashMap.put("query01",busiquerypage.getQuery01());
		hashMap.put("query02",busiquerypage.getQuery02());
		hashMap.put("query03",busiquerypage.getQuery03());
		hashMap.put("query04",busiquerypage.getQuery04());
		hashMap.put("query05",busiquerypage.getQuery05());
		hashMap.put("query06",busiquerypage.getQuery06());
		hashMap.put("query07",busiquerypage.getQuery07());
		hashMap.put("query08",busiquerypage.getQuery08());
		hashMap.put("query09",busiquerypage.getQuery09());
		hashMap.put("query10",busiquerypage.getQuery10());
		hashMap.put("query11",busiquerypage.getQuery11());
		hashMap.put("query12",busiquerypage.getQuery12());
		hashMap.put("query13",busiquerypage.getQuery13());
		hashMap.put("query14",busiquerypage.getQuery14());
		hashMap.put("query15",busiquerypage.getQuery15());
		hashMap.put("query16",busiquerypage.getQuery16());
		hashMap.put("query17",busiquerypage.getQuery17());
		hashMap.put("query18",busiquerypage.getQuery18());
		hashMap.put("query19",busiquerypage.getQuery19());
		hashMap.put("query20",busiquerypage.getQuery20());
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		if("canPinGenJinTongJi".equals(busiquerypage.getType())){
			iPage = busiPoMapper.canPinGenJinTongJi(page, hashMap);
			List<canPinGenJinTongJi> pageList = new ArrayList<canPinGenJinTongJi>();
			for (BusiQueryPage record : iPage.getRecords()) {
				canPinGenJinTongJi vo = new canPinGenJinTongJi();
				BeanUtils.copyProperties(record, vo);
				pageList.add(vo);
			}
			mv.addObject(NormalExcelConstants.FILE_NAME, "每日产品跟进统计");
			mv.addObject(NormalExcelConstants.CLASS, canPinGenJinTongJi.class);
			mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("每日产品跟进统计", "导出人:"+sysUser.getRealname(), "busi_po"));
			mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		}else if("chaXunMeiTianDingDan".equals(busiquerypage.getType())){
			iPage = busiPoMapper.chaXunMeiTianDingDan(page, hashMap);
			List<chaXunMeiTianDingDan> pageList = new ArrayList<chaXunMeiTianDingDan>();
			for (BusiQueryPage record : iPage.getRecords()) {
				chaXunMeiTianDingDan vo = new chaXunMeiTianDingDan();
				BeanUtils.copyProperties(record, vo);
				pageList.add(vo);
			}
			mv.addObject(NormalExcelConstants.FILE_NAME, "每天订单量");
			mv.addObject(NormalExcelConstants.CLASS, chaXunMeiTianDingDan.class);
			mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("每天订单量", "导出人:"+sysUser.getRealname(), "busi_po"));
			mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		}else if("XiangChuKuXiangQing".equals(busiquerypage.getType())){
			iPage = busiPoMapper.XiangChuKuXiangQing(page, hashMap);
			List<XiangChuKuXiangQing> pageList = new ArrayList<XiangChuKuXiangQing>();
			for (BusiQueryPage record : iPage.getRecords()) {
				XiangChuKuXiangQing vo = new XiangChuKuXiangQing();
				BeanUtils.copyProperties(record, vo);
				pageList.add(vo);
			}
			mv.addObject(NormalExcelConstants.FILE_NAME, "箱出库详情");
			mv.addObject(NormalExcelConstants.CLASS, XiangChuKuXiangQing.class);
			mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("箱出库详情", "导出人:"+sysUser.getRealname(), "busi_po"));
			mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		}else if("huokuanzhichu".equals(busiquerypage.getType())){
			iPage = busiPoMapper.huokuanzhichu(page, hashMap);
			List<huokuanzhichu> pageList = new ArrayList<huokuanzhichu>();
			for (BusiQueryPage record : iPage.getRecords()) {
				huokuanzhichu vo = new huokuanzhichu();
				BeanUtils.copyProperties(record, vo);
				pageList.add(vo);
			}
			mv.addObject(NormalExcelConstants.FILE_NAME, "箱出库详情");
			mv.addObject(NormalExcelConstants.CLASS, huokuanzhichu.class);
			mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("箱出库详情", "导出人:"+sysUser.getRealname(), "busi_po"));
			mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		}else if("rukumingxi".equals(busiquerypage.getType())){
			iPage = busiPoMapper.rukumingxi(page, hashMap);
			List<rukumingxi> pageList = new ArrayList<rukumingxi>();
			for (BusiQueryPage record : iPage.getRecords()) {
				rukumingxi vo = new rukumingxi();
				BeanUtils.copyProperties(record, vo);
				pageList.add(vo);
			}
			mv.addObject(NormalExcelConstants.FILE_NAME, "入库明细");
			mv.addObject(NormalExcelConstants.CLASS, rukumingxi.class);
			mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("入库明细", "导出人:"+sysUser.getRealname(), "busi_po"));
			mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		}
		return mv;
	}



}
