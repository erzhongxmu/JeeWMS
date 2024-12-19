package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaSnroType;
import com.base.modules.jeewms.service.IBaSnroTypeService;

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
 * @Description: 编码类型
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="编码类型")
@RestController
@RequestMapping("/jeewms/baSnroType")
@Slf4j
public class BaSnroTypeController extends JeecgController<BaSnroType, IBaSnroTypeService> {
	@Autowired
	private IBaSnroTypeService baSnroTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param baSnroType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "编码类型-分页列表查询")
	@ApiOperation(value="编码类型-分页列表查询", notes="编码类型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaSnroType baSnroType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaSnroType> queryWrapper = QueryGenerator.initQueryWrapper(baSnroType, req.getParameterMap());
		Page<BaSnroType> page = new Page<BaSnroType>(pageNo, pageSize);
		IPage<BaSnroType> pageList = baSnroTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baSnroType
	 * @return
	 */
	@AutoLog(value = "编码类型-添加")
	@ApiOperation(value="编码类型-添加", notes="编码类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaSnroType baSnroType) {
		baSnroTypeService.save(baSnroType);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baSnroType
	 * @return
	 */
	@AutoLog(value = "编码类型-编辑")
	@ApiOperation(value="编码类型-编辑", notes="编码类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaSnroType baSnroType) {
		baSnroTypeService.updateById(baSnroType);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "编码类型-通过id删除")
	@ApiOperation(value="编码类型-通过id删除", notes="编码类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baSnroTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "编码类型-批量删除")
	@ApiOperation(value="编码类型-批量删除", notes="编码类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baSnroTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "编码类型-通过id查询")
	@ApiOperation(value="编码类型-通过id查询", notes="编码类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaSnroType baSnroType = baSnroTypeService.getById(id);
		if(baSnroType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baSnroType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baSnroType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaSnroType baSnroType) {
        return super.exportXls(request, baSnroType, BaSnroType.class, "编码类型");
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
        return super.importExcel(request, response, BaSnroType.class);
    }

}
