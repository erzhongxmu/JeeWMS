package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaCostType;
import com.base.modules.jeewms.service.IBaCostTypeService;

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
 * @Description: 费用类型
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="费用类型")
@RestController
@RequestMapping("/jeewms/baCostType")
@Slf4j
public class BaCostTypeController extends JeecgController<BaCostType, IBaCostTypeService> {
	@Autowired
	private IBaCostTypeService baCostTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param baCostType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "费用类型-分页列表查询")
	@ApiOperation(value="费用类型-分页列表查询", notes="费用类型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaCostType baCostType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaCostType> queryWrapper = QueryGenerator.initQueryWrapper(baCostType, req.getParameterMap());
		Page<BaCostType> page = new Page<BaCostType>(pageNo, pageSize);
		IPage<BaCostType> pageList = baCostTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baCostType
	 * @return
	 */
	@AutoLog(value = "费用类型-添加")
	@ApiOperation(value="费用类型-添加", notes="费用类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaCostType baCostType) {
		baCostTypeService.save(baCostType);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baCostType
	 * @return
	 */
	@AutoLog(value = "费用类型-编辑")
	@ApiOperation(value="费用类型-编辑", notes="费用类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaCostType baCostType) {
		baCostTypeService.updateById(baCostType);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "费用类型-通过id删除")
	@ApiOperation(value="费用类型-通过id删除", notes="费用类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baCostTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "费用类型-批量删除")
	@ApiOperation(value="费用类型-批量删除", notes="费用类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baCostTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "费用类型-通过id查询")
	@ApiOperation(value="费用类型-通过id查询", notes="费用类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaCostType baCostType = baCostTypeService.getById(id);
		if(baCostType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baCostType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baCostType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaCostType baCostType) {
        return super.exportXls(request, baCostType, BaCostType.class, "费用类型");
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
        return super.importExcel(request, response, BaCostType.class);
    }

}
