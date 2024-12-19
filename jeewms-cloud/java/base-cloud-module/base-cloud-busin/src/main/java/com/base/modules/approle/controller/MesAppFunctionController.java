package com.base.modules.approle.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.approle.entity.MesAppFunction;
import com.base.modules.approle.service.IMesAppFunctionService;
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
 * @Description: APP功能
 * @Author: jeecg-boot
 * @Date:   2021-11-10
 * @Version: V1.0
 */
@Api(tags="APP功能")
@RestController
@RequestMapping("/jeewms/mesAppFunction")
@Slf4j
public class MesAppFunctionController extends BaseController<MesAppFunction, IMesAppFunctionService> {
	@Autowired
	private IMesAppFunctionService mesAppFunctionService;

	/**
	 * 分页列表查询
	 *
	 * @param mesAppFunction
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "APP功能-分页列表查询")
	@ApiOperation(value="APP功能-分页列表查询", notes="APP功能-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MesAppFunction mesAppFunction,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MesAppFunction> queryWrapper = QueryGenerator.initQueryWrapper(mesAppFunction, req.getParameterMap());
		Page<MesAppFunction> page = new Page<MesAppFunction>(pageNo, pageSize);
		IPage<MesAppFunction> pageList = mesAppFunctionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param mesAppFunction
	 * @return
	 */
	@AutoLog(value = "APP功能-添加")
	@ApiOperation(value="APP功能-添加", notes="APP功能-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MesAppFunction mesAppFunction) {
		mesAppFunctionService.save(mesAppFunction);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param mesAppFunction
	 * @return
	 */
	@AutoLog(value = "APP功能-编辑")
	@ApiOperation(value="APP功能-编辑", notes="APP功能-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MesAppFunction mesAppFunction) {
		mesAppFunctionService.updateById(mesAppFunction);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "APP功能-通过id删除")
	@ApiOperation(value="APP功能-通过id删除", notes="APP功能-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mesAppFunctionService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "APP功能-批量删除")
	@ApiOperation(value="APP功能-批量删除", notes="APP功能-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mesAppFunctionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "APP功能-通过id查询")
	@ApiOperation(value="APP功能-通过id查询", notes="APP功能-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MesAppFunction mesAppFunction = mesAppFunctionService.getById(id);
		if(mesAppFunction==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(mesAppFunction);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mesAppFunction
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MesAppFunction mesAppFunction) {
        return super.exportXls(request, mesAppFunction, MesAppFunction.class, "APP功能");
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
        return super.importExcel(request, response, MesAppFunction.class);
    }

}
