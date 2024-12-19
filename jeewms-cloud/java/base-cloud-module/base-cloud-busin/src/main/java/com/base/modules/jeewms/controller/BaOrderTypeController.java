package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaOrderType;
import com.base.modules.jeewms.service.IBaOrderTypeService;

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
 * @Description: 订单类型
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="订单类型")
@RestController
@RequestMapping("/jeewms/baOrderType")
@Slf4j
public class BaOrderTypeController extends JeecgController<BaOrderType, IBaOrderTypeService> {
	@Autowired
	private IBaOrderTypeService baOrderTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param baOrderType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "订单类型-分页列表查询")
	@ApiOperation(value="订单类型-分页列表查询", notes="订单类型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaOrderType baOrderType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaOrderType> queryWrapper = QueryGenerator.initQueryWrapper(baOrderType, req.getParameterMap());
		Page<BaOrderType> page = new Page<BaOrderType>(pageNo, pageSize);
		IPage<BaOrderType> pageList = baOrderTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baOrderType
	 * @return
	 */
	@AutoLog(value = "订单类型-添加")
	@ApiOperation(value="订单类型-添加", notes="订单类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaOrderType baOrderType) {
		baOrderTypeService.save(baOrderType);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baOrderType
	 * @return
	 */
	@AutoLog(value = "订单类型-编辑")
	@ApiOperation(value="订单类型-编辑", notes="订单类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaOrderType baOrderType) {
		baOrderTypeService.updateById(baOrderType);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单类型-通过id删除")
	@ApiOperation(value="订单类型-通过id删除", notes="订单类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baOrderTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单类型-批量删除")
	@ApiOperation(value="订单类型-批量删除", notes="订单类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baOrderTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单类型-通过id查询")
	@ApiOperation(value="订单类型-通过id查询", notes="订单类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaOrderType baOrderType = baOrderTypeService.getById(id);
		if(baOrderType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baOrderType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baOrderType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaOrderType baOrderType) {
        return super.exportXls(request, baOrderType, BaOrderType.class, "订单类型");
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
        return super.importExcel(request, response, BaOrderType.class);
    }

}
