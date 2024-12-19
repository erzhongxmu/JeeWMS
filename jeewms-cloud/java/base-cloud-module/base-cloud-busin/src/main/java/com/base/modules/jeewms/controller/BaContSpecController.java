package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaContSpec;
import com.base.modules.jeewms.service.IBaContSpecService;

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
 * @Description: 箱规
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="箱规")
@RestController
@RequestMapping("/jeewms/baContSpec")
@Slf4j
public class BaContSpecController extends JeecgController<BaContSpec, IBaContSpecService> {
	@Autowired
	private IBaContSpecService baContSpecService;

	/**
	 * 分页列表查询
	 *
	 * @param baContSpec
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "箱规-分页列表查询")
	@ApiOperation(value="箱规-分页列表查询", notes="箱规-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaContSpec baContSpec,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaContSpec> queryWrapper = QueryGenerator.initQueryWrapper(baContSpec, req.getParameterMap());
		Page<BaContSpec> page = new Page<BaContSpec>(pageNo, pageSize);
		IPage<BaContSpec> pageList = baContSpecService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baContSpec
	 * @return
	 */
	@AutoLog(value = "箱规-添加")
	@ApiOperation(value="箱规-添加", notes="箱规-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaContSpec baContSpec) {
		baContSpecService.save(baContSpec);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baContSpec
	 * @return
	 */
	@AutoLog(value = "箱规-编辑")
	@ApiOperation(value="箱规-编辑", notes="箱规-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaContSpec baContSpec) {
		baContSpecService.updateById(baContSpec);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "箱规-通过id删除")
	@ApiOperation(value="箱规-通过id删除", notes="箱规-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baContSpecService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "箱规-批量删除")
	@ApiOperation(value="箱规-批量删除", notes="箱规-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baContSpecService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "箱规-通过id查询")
	@ApiOperation(value="箱规-通过id查询", notes="箱规-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaContSpec baContSpec = baContSpecService.getById(id);
		if(baContSpec==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baContSpec);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baContSpec
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaContSpec baContSpec) {
        return super.exportXls(request, baContSpec, BaContSpec.class, "箱规");
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
        return super.importExcel(request, response, BaContSpec.class);
    }

}
