package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeewms.entity.WvDayCostSum;
import com.base.modules.jeewms.service.IWvDayCostSumService;
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
 * @Description: wv_day_cost_sum
 * @Author: base-boot
 * @Date:   2021-10-21
 * @Version: V1.0
 */
@Api(tags="wv_day_cost_sum")
@RestController
@RequestMapping("/jeewms/wvDayCostSum")
@Slf4j
public class WvDayCostSumController extends BaseController<WvDayCostSum, IWvDayCostSumService> {
	@Autowired
	private IWvDayCostSumService wvDayCostSumService;

	/**
	 * 分页列表查询
	 *
	 * @param wvDayCostSum
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wv_day_cost_sum-分页列表查询")
	@ApiOperation(value="wv_day_cost_sum-分页列表查询", notes="wv_day_cost_sum-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WvDayCostSum wvDayCostSum,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WvDayCostSum> queryWrapper = QueryGenerator.initQueryWrapper(wvDayCostSum, req.getParameterMap());
		Page<WvDayCostSum> page = new Page<WvDayCostSum>(pageNo, pageSize);
		IPage<WvDayCostSum> pageList = wvDayCostSumService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 分页列表查询
	  *
	  * @param wvDayCostSum
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "wv_day_cost_sum-分页列表查询")
	 @ApiOperation(value="wv_day_cost_sum-分页列表查询", notes="wv_day_cost_sum-分页列表查询")
	 @GetMapping(value = "/listy")
	 public Result<?> queryPageListy(WvDayCostSum wvDayCostSum,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<WvDayCostSum> queryWrapper = QueryGenerator.initQueryWrapper(wvDayCostSum, req.getParameterMap());

		 Page<WvDayCostSum> page = new Page<WvDayCostSum>(pageNo, pageSize);
		 IPage<WvDayCostSum> pageList = wvDayCostSumService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	/**
	 *   添加
	 *
	 * @param wvDayCostSum
	 * @return
	 */
	@AutoLog(value = "wv_day_cost_sum-添加")
	@ApiOperation(value="wv_day_cost_sum-添加", notes="wv_day_cost_sum-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WvDayCostSum wvDayCostSum) {
		wvDayCostSumService.save(wvDayCostSum);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wvDayCostSum
	 * @return
	 */
	@AutoLog(value = "wv_day_cost_sum-编辑")
	@ApiOperation(value="wv_day_cost_sum-编辑", notes="wv_day_cost_sum-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WvDayCostSum wvDayCostSum) {
		wvDayCostSumService.updateById(wvDayCostSum);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wv_day_cost_sum-通过id删除")
	@ApiOperation(value="wv_day_cost_sum-通过id删除", notes="wv_day_cost_sum-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wvDayCostSumService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wv_day_cost_sum-批量删除")
	@ApiOperation(value="wv_day_cost_sum-批量删除", notes="wv_day_cost_sum-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wvDayCostSumService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wv_day_cost_sum-通过id查询")
	@ApiOperation(value="wv_day_cost_sum-通过id查询", notes="wv_day_cost_sum-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WvDayCostSum wvDayCostSum = wvDayCostSumService.getById(id);
		if(wvDayCostSum==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wvDayCostSum);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wvDayCostSum
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WvDayCostSum wvDayCostSum) {
        return super.exportXls(request, wvDayCostSum, WvDayCostSum.class, "wv_day_cost_sum");
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
        return super.importExcel(request, response, WvDayCostSum.class);
    }

}
