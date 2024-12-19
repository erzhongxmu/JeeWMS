package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaOperStep;
import com.base.modules.jeewms.service.IBaOperStepService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

 /**
 * @Description: 业务环节
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="业务环节")
@RestController
@RequestMapping("/jeewms/baOperStep")
@Slf4j
public class BaOperStepController extends JeecgController<BaOperStep, IBaOperStepService> {
	@Autowired
	private IBaOperStepService baOperStepService;

	/**
	 * 分页列表查询
	 *
	 * @param baOperStep
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "业务环节-分页列表查询")
	@ApiOperation(value="业务环节-分页列表查询", notes="业务环节-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaOperStep baOperStep,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaOperStep> queryWrapper = QueryGenerator.initQueryWrapper(baOperStep, req.getParameterMap());
		Page<BaOperStep> page = new Page<BaOperStep>(pageNo, pageSize);
		IPage<BaOperStep> pageList = baOperStepService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baOperStep
	 * @return
	 */
	@AutoLog(value = "业务环节-添加")
	@ApiOperation(value="业务环节-添加", notes="业务环节-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaOperStep baOperStep) {
		baOperStepService.save(baOperStep);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baOperStep
	 * @return
	 */
	@AutoLog(value = "业务环节-编辑")
	@ApiOperation(value="业务环节-编辑", notes="业务环节-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaOperStep baOperStep) {
		baOperStepService.updateById(baOperStep);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "业务环节-通过id删除")
	@ApiOperation(value="业务环节-通过id删除", notes="业务环节-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baOperStepService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "业务环节-批量删除")
	@ApiOperation(value="业务环节-批量删除", notes="业务环节-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baOperStepService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "业务环节-通过id查询")
	@ApiOperation(value="业务环节-通过id查询", notes="业务环节-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaOperStep baOperStep = baOperStepService.getById(id);
		if(baOperStep==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baOperStep);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baOperStep
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaOperStep baOperStep) {
        return super.exportXls(request, baOperStep, BaOperStep.class, "业务环节");
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
        return super.importExcel(request, response, BaOperStep.class);
    }

}
