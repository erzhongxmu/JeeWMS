package com.base.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.app.service.IWmsAppFunctionService;
import com.base.modules.mesapp.entity.WmsAppFunction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.base.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
//import org.jeecg.modules.jmreport.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 主数据—APP功能模块
 * @Author: base-boot
 * @Date:   2020-10-14
 * @Version: V1.0
 */
@Api(tags="主数据—APP功能模块")
@RestController
@RequestMapping("/wmsapp/wmsAppFunction")
@Slf4j
public class WmsAppFunctionController extends JeecgController<WmsAppFunction, IWmsAppFunctionService> {
	@Autowired
	private IWmsAppFunctionService wmsAppFunctionService;

	/**
	 * 分页列表查询
	 *
	 * @param mesAppFunction
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "主数据—APP功能模块-分页列表查询")
	@ApiOperation(value="主数据—APP功能模块-分页列表查询", notes="主数据—APP功能模块-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsAppFunction mesAppFunction,
	                               @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
	                               @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
	                               HttpServletRequest req) {
		QueryWrapper<WmsAppFunction> queryWrapper = QueryGenerator.initQueryWrapper(mesAppFunction, req.getParameterMap());
		Page<WmsAppFunction> page = new Page<WmsAppFunction>(pageNo, pageSize);
		IPage<WmsAppFunction> pageList = wmsAppFunctionService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param mesAppFunction
	 * @return
	 */
	@AutoLog(value = "主数据—APP功能模块-添加")
	@ApiOperation(value="主数据—APP功能模块-添加", notes="主数据—APP功能模块-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsAppFunction mesAppFunction) {
		wmsAppFunctionService.save(mesAppFunction);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param mesAppFunction
	 * @return
	 */
	@AutoLog(value = "主数据—APP功能模块-编辑")
	@ApiOperation(value="主数据—APP功能模块-编辑", notes="主数据—APP功能模块-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsAppFunction mesAppFunction) {
		wmsAppFunctionService.updateById(mesAppFunction);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "主数据—APP功能模块-通过id删除")
	@ApiOperation(value="主数据—APP功能模块-通过id删除", notes="主数据—APP功能模块-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsAppFunctionService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "主数据—APP功能模块-批量删除")
	@ApiOperation(value="主数据—APP功能模块-批量删除", notes="主数据—APP功能模块-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsAppFunctionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "主数据—APP功能模块-通过id查询")
	@ApiOperation(value="主数据—APP功能模块-通过id查询", notes="主数据—APP功能模块-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsAppFunction mesAppFunction = wmsAppFunctionService.getById(id);
		if(mesAppFunction==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mesAppFunction);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mesAppFunction
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsAppFunction mesAppFunction) {
        return super.exportXls(request, mesAppFunction, WmsAppFunction.class, "主数据—APP功能模块");
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
        return super.importExcel(request, response, WmsAppFunction.class);
    }

}
