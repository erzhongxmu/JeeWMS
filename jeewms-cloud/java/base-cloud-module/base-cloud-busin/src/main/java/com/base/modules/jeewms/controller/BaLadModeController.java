package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaLadMode;
import com.base.modules.jeewms.service.IBaLadModeService;

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
 * @Description: 提货方式
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="提货方式")
@RestController
@RequestMapping("/jeewms/baLadMode")
@Slf4j
public class BaLadModeController extends JeecgController<BaLadMode, IBaLadModeService> {
	@Autowired
	private IBaLadModeService baLadModeService;

	/**
	 * 分页列表查询
	 *
	 * @param baLadMode
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "提货方式-分页列表查询")
	@ApiOperation(value="提货方式-分页列表查询", notes="提货方式-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaLadMode baLadMode,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaLadMode> queryWrapper = QueryGenerator.initQueryWrapper(baLadMode, req.getParameterMap());
		Page<BaLadMode> page = new Page<BaLadMode>(pageNo, pageSize);
		IPage<BaLadMode> pageList = baLadModeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baLadMode
	 * @return
	 */
	@AutoLog(value = "提货方式-添加")
	@ApiOperation(value="提货方式-添加", notes="提货方式-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaLadMode baLadMode) {
		baLadModeService.save(baLadMode);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baLadMode
	 * @return
	 */
	@AutoLog(value = "提货方式-编辑")
	@ApiOperation(value="提货方式-编辑", notes="提货方式-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaLadMode baLadMode) {
		baLadModeService.updateById(baLadMode);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "提货方式-通过id删除")
	@ApiOperation(value="提货方式-通过id删除", notes="提货方式-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baLadModeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "提货方式-批量删除")
	@ApiOperation(value="提货方式-批量删除", notes="提货方式-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baLadModeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "提货方式-通过id查询")
	@ApiOperation(value="提货方式-通过id查询", notes="提货方式-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaLadMode baLadMode = baLadModeService.getById(id);
		if(baLadMode==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baLadMode);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baLadMode
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaLadMode baLadMode) {
        return super.exportXls(request, baLadMode, BaLadMode.class, "提货方式");
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
        return super.importExcel(request, response, BaLadMode.class);
    }

}
