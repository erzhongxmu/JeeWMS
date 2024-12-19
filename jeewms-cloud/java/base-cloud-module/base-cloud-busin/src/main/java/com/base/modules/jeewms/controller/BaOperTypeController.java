package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaOperType;
import com.base.modules.jeewms.service.IBaOperTypeService;

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
 * @Description: 操作类型
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="操作类型")
@RestController
@RequestMapping("/jeewms/baOperType")
@Slf4j
public class BaOperTypeController extends JeecgController<BaOperType, IBaOperTypeService> {
	@Autowired
	private IBaOperTypeService baOperTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param baOperType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "操作类型-分页列表查询")
	@ApiOperation(value="操作类型-分页列表查询", notes="操作类型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaOperType baOperType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaOperType> queryWrapper = QueryGenerator.initQueryWrapper(baOperType, req.getParameterMap());
		Page<BaOperType> page = new Page<BaOperType>(pageNo, pageSize);
		IPage<BaOperType> pageList = baOperTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baOperType
	 * @return
	 */
	@AutoLog(value = "操作类型-添加")
	@ApiOperation(value="操作类型-添加", notes="操作类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaOperType baOperType) {
		baOperTypeService.save(baOperType);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baOperType
	 * @return
	 */
	@AutoLog(value = "操作类型-编辑")
	@ApiOperation(value="操作类型-编辑", notes="操作类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaOperType baOperType) {
		baOperTypeService.updateById(baOperType);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "操作类型-通过id删除")
	@ApiOperation(value="操作类型-通过id删除", notes="操作类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baOperTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "操作类型-批量删除")
	@ApiOperation(value="操作类型-批量删除", notes="操作类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baOperTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "操作类型-通过id查询")
	@ApiOperation(value="操作类型-通过id查询", notes="操作类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaOperType baOperType = baOperTypeService.getById(id);
		if(baOperType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baOperType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baOperType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaOperType baOperType) {
        return super.exportXls(request, baOperType, BaOperType.class, "操作类型");
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
        return super.importExcel(request, response, BaOperType.class);
    }

}
