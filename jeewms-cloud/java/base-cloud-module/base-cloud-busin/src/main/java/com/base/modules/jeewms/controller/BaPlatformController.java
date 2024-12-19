package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaPlatform;
import com.base.modules.jeewms.service.IBaPlatformService;

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
 * @Description: 月台主数据
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="月台主数据")
@RestController
@RequestMapping("/jeewms/baPlatform")
@Slf4j
public class BaPlatformController extends JeecgController<BaPlatform, IBaPlatformService> {
	@Autowired
	private IBaPlatformService baPlatformService;

	/**
	 * 分页列表查询
	 *
	 * @param baPlatform
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "月台主数据-分页列表查询")
	@ApiOperation(value="月台主数据-分页列表查询", notes="月台主数据-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaPlatform baPlatform,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaPlatform> queryWrapper = QueryGenerator.initQueryWrapper(baPlatform, req.getParameterMap());
		Page<BaPlatform> page = new Page<BaPlatform>(pageNo, pageSize);
		IPage<BaPlatform> pageList = baPlatformService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baPlatform
	 * @return
	 */
	@AutoLog(value = "月台主数据-添加")
	@ApiOperation(value="月台主数据-添加", notes="月台主数据-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaPlatform baPlatform) {
		baPlatformService.save(baPlatform);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baPlatform
	 * @return
	 */
	@AutoLog(value = "月台主数据-编辑")
	@ApiOperation(value="月台主数据-编辑", notes="月台主数据-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaPlatform baPlatform) {
		baPlatformService.updateById(baPlatform);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "月台主数据-通过id删除")
	@ApiOperation(value="月台主数据-通过id删除", notes="月台主数据-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baPlatformService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "月台主数据-批量删除")
	@ApiOperation(value="月台主数据-批量删除", notes="月台主数据-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baPlatformService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "月台主数据-通过id查询")
	@ApiOperation(value="月台主数据-通过id查询", notes="月台主数据-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaPlatform baPlatform = baPlatformService.getById(id);
		if(baPlatform==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baPlatform);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baPlatform
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaPlatform baPlatform) {
        return super.exportXls(request, baPlatform, BaPlatform.class, "月台主数据");
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
        return super.importExcel(request, response, BaPlatform.class);
    }

}
