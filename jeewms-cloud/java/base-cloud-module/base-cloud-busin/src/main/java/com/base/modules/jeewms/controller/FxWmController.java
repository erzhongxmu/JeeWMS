package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeewms.entity.FxWm;
import com.base.modules.jeewms.service.IFxWmService;
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
 * @Description: fx_wm
 * @Author: base-boot
 * @Date:   2022-07-01
 * @Version: V1.0
 */
@Api(tags="fx_wm")
@RestController
@RequestMapping("/jeewms/fxWm")
@Slf4j
public class FxWmController extends BaseController<FxWm, IFxWmService> {
	@Autowired
	private IFxWmService fxWmService;

	/**
	 * 分页列表查询
	 *
	 * @param fxWm
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "fx_wm-分页列表查询")
	@ApiOperation(value="fx_wm-分页列表查询", notes="fx_wm-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FxWm fxWm,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<FxWm> queryWrapper = QueryGenerator.initQueryWrapper(fxWm, req.getParameterMap());
		Page<FxWm> page = new Page<FxWm>(pageNo, pageSize);
		IPage<FxWm> pageList = fxWmService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param fxWm
	 * @return
	 */
	@AutoLog(value = "fx_wm-添加")
	@ApiOperation(value="fx_wm-添加", notes="fx_wm-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FxWm fxWm) {
		fxWmService.save(fxWm);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param fxWm
	 * @return
	 */
	@AutoLog(value = "fx_wm-编辑")
	@ApiOperation(value="fx_wm-编辑", notes="fx_wm-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FxWm fxWm) {
		fxWmService.updateById(fxWm);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "fx_wm-通过id删除")
	@ApiOperation(value="fx_wm-通过id删除", notes="fx_wm-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fxWmService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "fx_wm-批量删除")
	@ApiOperation(value="fx_wm-批量删除", notes="fx_wm-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fxWmService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "fx_wm-通过id查询")
	@ApiOperation(value="fx_wm-通过id查询", notes="fx_wm-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FxWm fxWm = fxWmService.getById(id);
		if(fxWm==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(fxWm);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fxWm
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FxWm fxWm) {
        return super.exportXls(request, fxWm, FxWm.class, "fx_wm");
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
        return super.importExcel(request, response, FxWm.class);
    }

}
