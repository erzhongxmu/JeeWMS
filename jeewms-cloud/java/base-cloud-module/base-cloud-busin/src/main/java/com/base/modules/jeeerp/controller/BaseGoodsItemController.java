package com.base.modules.jeeerp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeeerp.entity.BaseGoodsItem;
import com.base.modules.jeeerp.service.IBaseGoodsItemService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @Description: base_goods_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Api(tags="base_goods_item")
@RestController
@RequestMapping("/jeeerp/baseGoodsItem")
@Slf4j
public class BaseGoodsItemController extends BaseController<BaseGoodsItem, IBaseGoodsItemService> {
	@Autowired
	private IBaseGoodsItemService baseGoodsItemService;

	/**
	 * 分页列表查询
	 *
	 * @param baseGoodsItem
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "base_goods_item-分页列表查询")
	@ApiOperation(value="base_goods_item-分页列表查询", notes="base_goods_item-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaseGoodsItem baseGoodsItem,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaseGoodsItem> queryWrapper = QueryGenerator.initQueryWrapper(baseGoodsItem, req.getParameterMap());
		Page<BaseGoodsItem> page = new Page<BaseGoodsItem>(pageNo, pageSize);
		IPage<BaseGoodsItem> pageList = baseGoodsItemService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baseGoodsItem
	 * @return
	 */
	@AutoLog(value = "base_goods_item-添加")
	@ApiOperation(value="base_goods_item-添加", notes="base_goods_item-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaseGoodsItem baseGoodsItem) {
		baseGoodsItemService.save(baseGoodsItem);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baseGoodsItem
	 * @return
	 */
	@AutoLog(value = "base_goods_item-编辑")
	@ApiOperation(value="base_goods_item-编辑", notes="base_goods_item-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaseGoodsItem baseGoodsItem) {
		baseGoodsItemService.updateById(baseGoodsItem);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "base_goods_item-通过id删除")
	@ApiOperation(value="base_goods_item-通过id删除", notes="base_goods_item-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baseGoodsItemService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "base_goods_item-批量删除")
	@ApiOperation(value="base_goods_item-批量删除", notes="base_goods_item-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baseGoodsItemService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "base_goods_item-通过id查询")
	@ApiOperation(value="base_goods_item-通过id查询", notes="base_goods_item-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaseGoodsItem baseGoodsItem = baseGoodsItemService.getById(id);
		if(baseGoodsItem==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(baseGoodsItem);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baseGoodsItem
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaseGoodsItem baseGoodsItem) {
        return super.exportXls(request, baseGoodsItem, BaseGoodsItem.class, "base_goods_item");
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
        return super.importExcel(request, response, BaseGoodsItem.class);
    }

}
