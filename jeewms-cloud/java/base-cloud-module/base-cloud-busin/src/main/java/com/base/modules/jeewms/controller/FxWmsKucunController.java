package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeewms.entity.FxWmsKucun;
import com.base.modules.jeewms.service.IFxWmsKucunService;
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
 * @Description: fx_wms_kucun
 * @Author: base-boot
 * @Date:   2022-07-01
 * @Version: V1.0
 */
@Api(tags="fx_wms_kucun")
@RestController
@RequestMapping("/jeewms/fxWmsKucun")
@Slf4j
public class FxWmsKucunController extends BaseController<FxWmsKucun, IFxWmsKucunService> {
	@Autowired
	private IFxWmsKucunService fxWmsKucunService;

	/**
	 * 分页列表查询
	 *
	 * @param fxWmsKucun
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "fx_wms_kucun-分页列表查询")
	@ApiOperation(value="fx_wms_kucun-分页列表查询", notes="fx_wms_kucun-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FxWmsKucun fxWmsKucun,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<FxWmsKucun> queryWrapper = QueryGenerator.initQueryWrapper(fxWmsKucun, req.getParameterMap());
		Page<FxWmsKucun> page = new Page<FxWmsKucun>(pageNo, pageSize);
		IPage<FxWmsKucun> pageList = fxWmsKucunService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param fxWmsKucun
	 * @return
	 */
	@AutoLog(value = "fx_wms_kucun-添加")
	@ApiOperation(value="fx_wms_kucun-添加", notes="fx_wms_kucun-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FxWmsKucun fxWmsKucun) {
		fxWmsKucunService.save(fxWmsKucun);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param fxWmsKucun
	 * @return
	 */
	@AutoLog(value = "fx_wms_kucun-编辑")
	@ApiOperation(value="fx_wms_kucun-编辑", notes="fx_wms_kucun-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FxWmsKucun fxWmsKucun) {
		fxWmsKucunService.updateById(fxWmsKucun);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "fx_wms_kucun-通过id删除")
	@ApiOperation(value="fx_wms_kucun-通过id删除", notes="fx_wms_kucun-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fxWmsKucunService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "fx_wms_kucun-批量删除")
	@ApiOperation(value="fx_wms_kucun-批量删除", notes="fx_wms_kucun-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fxWmsKucunService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "fx_wms_kucun-通过id查询")
	@ApiOperation(value="fx_wms_kucun-通过id查询", notes="fx_wms_kucun-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FxWmsKucun fxWmsKucun = fxWmsKucunService.getById(id);
		if(fxWmsKucun==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(fxWmsKucun);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fxWmsKucun
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FxWmsKucun fxWmsKucun) {
        return super.exportXls(request, fxWmsKucun, FxWmsKucun.class, "fx_wms_kucun");
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
        return super.importExcel(request, response, FxWmsKucun.class);
    }

}
