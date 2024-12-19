package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmHisStock;
import com.base.modules.jeewms.service.IWmHisStockService;
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
 * @Description: wm_his_stock
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wm_his_stock")
@RestController
@RequestMapping("/jeewms/wmHisStock")
@Slf4j
public class WmHisStockController extends JeecgController<WmHisStock, IWmHisStockService> {
	@Autowired
	private IWmHisStockService wmHisStockService;

	/**
	 * 分页列表查询
	 *
	 * @param wmHisStock
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_his_stock-分页列表查询")
	@ApiOperation(value="wm_his_stock-分页列表查询", notes="wm_his_stock-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmHisStock wmHisStock,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmHisStock> queryWrapper = QueryGenerator.initQueryWrapper(wmHisStock, req.getParameterMap());
		Page<WmHisStock> page = new Page<WmHisStock>(pageNo, pageSize);
		IPage<WmHisStock> pageList = wmHisStockService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmHisStock
	 * @return
	 */
	@AutoLog(value = "wm_his_stock-添加")
	@ApiOperation(value="wm_his_stock-添加", notes="wm_his_stock-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmHisStock wmHisStock) {
		wmHisStockService.save(wmHisStock);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmHisStock
	 * @return
	 */
	@AutoLog(value = "wm_his_stock-编辑")
	@ApiOperation(value="wm_his_stock-编辑", notes="wm_his_stock-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmHisStock wmHisStock) {
		wmHisStockService.updateById(wmHisStock);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_his_stock-通过id删除")
	@ApiOperation(value="wm_his_stock-通过id删除", notes="wm_his_stock-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmHisStockService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_his_stock-批量删除")
	@ApiOperation(value="wm_his_stock-批量删除", notes="wm_his_stock-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmHisStockService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_his_stock-通过id查询")
	@ApiOperation(value="wm_his_stock-通过id查询", notes="wm_his_stock-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmHisStock wmHisStock = wmHisStockService.getById(id);
		if(wmHisStock==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmHisStock);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmHisStock
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmHisStock wmHisStock) {
        return super.exportXls(request, wmHisStock, WmHisStock.class, "wm_his_stock");
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
        return super.importExcel(request, response, WmHisStock.class);
    }

}
