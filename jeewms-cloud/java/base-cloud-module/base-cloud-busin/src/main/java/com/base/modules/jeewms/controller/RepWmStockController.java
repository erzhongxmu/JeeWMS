package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeewms.entity.RepWmStock;
import com.base.modules.jeewms.service.IRepWmStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: rep_wm_stock
 * @Author: base-boot
 * @Date:   2022-05-09
 * @Version: V1.0
 */
@Api(tags="rep_wm_stock")
@RestController
@RequestMapping("/jeewms/repWmStock")
@Slf4j
public class RepWmStockController extends BaseController<RepWmStock, IRepWmStockService> {
	@Autowired
	private IRepWmStockService repWmStockService;

	/**
	 * 分页列表查询
	 *
	 * @param repWmStock
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "rep_wm_stock-分页列表查询")
	@ApiOperation(value="rep_wm_stock-分页列表查询", notes="rep_wm_stock-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(RepWmStock repWmStock,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<RepWmStock> queryWrapper = QueryGenerator.initQueryWrapper(repWmStock, req.getParameterMap());
		Page<RepWmStock> page = new Page<RepWmStock>(pageNo, pageSize);
		IPage<RepWmStock> pageList = repWmStockService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param repWmStock
	 * @return
	 */
	@AutoLog(value = "rep_wm_stock-添加")
	@ApiOperation(value="rep_wm_stock-添加", notes="rep_wm_stock-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody RepWmStock repWmStock) {
		repWmStockService.save(repWmStock);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param repWmStock
	 * @return
	 */
	@AutoLog(value = "rep_wm_stock-编辑")
	@ApiOperation(value="rep_wm_stock-编辑", notes="rep_wm_stock-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody RepWmStock repWmStock) {
		repWmStockService.updateById(repWmStock);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "rep_wm_stock-通过id删除")
	@ApiOperation(value="rep_wm_stock-通过id删除", notes="rep_wm_stock-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		repWmStockService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "rep_wm_stock-批量删除")
	@ApiOperation(value="rep_wm_stock-批量删除", notes="rep_wm_stock-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.repWmStockService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "rep_wm_stock-通过id查询")
	@ApiOperation(value="rep_wm_stock-通过id查询", notes="rep_wm_stock-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		RepWmStock repWmStock = repWmStockService.getById(id);
		if(repWmStock==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(repWmStock);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param repWmStock
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RepWmStock repWmStock) {
        return super.exportXls(request, repWmStock, RepWmStock.class, "rep_wm_stock");
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
        return super.importExcel(request, response, RepWmStock.class);
    }

}
