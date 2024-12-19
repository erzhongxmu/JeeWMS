package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.BaSyspType;
import com.base.modules.jeewms.service.IBaSyspTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

;

 /**
 * @Description: 参数类型
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="参数类型")
@RestController
@RequestMapping("/jeewms/baSyspType")
@Slf4j
public class BaSyspTypeController extends JeecgController<BaSyspType, IBaSyspTypeService> {
	@Autowired
	private IBaSyspTypeService baSyspTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param baSyspType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "参数类型-分页列表查询")
	@ApiOperation(value="参数类型-分页列表查询", notes="参数类型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaSyspType baSyspType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaSyspType> queryWrapper = QueryGenerator.initQueryWrapper(baSyspType, req.getParameterMap());
		Page<BaSyspType> page = new Page<BaSyspType>(pageNo, pageSize);
		IPage<BaSyspType> pageList = baSyspTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baSyspType
	 * @return
	 */
	@AutoLog(value = "参数类型-添加")
	@ApiOperation(value="参数类型-添加", notes="参数类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaSyspType baSyspType) {
		baSyspTypeService.save(baSyspType);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baSyspType
	 * @return
	 */
	@AutoLog(value = "参数类型-编辑")
	@ApiOperation(value="参数类型-编辑", notes="参数类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaSyspType baSyspType) {
		baSyspTypeService.updateById(baSyspType);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "参数类型-通过id删除")
	@ApiOperation(value="参数类型-通过id删除", notes="参数类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baSyspTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "参数类型-批量删除")
	@ApiOperation(value="参数类型-批量删除", notes="参数类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baSyspTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "参数类型-通过id查询")
	@ApiOperation(value="参数类型-通过id查询", notes="参数类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaSyspType baSyspType = baSyspTypeService.getById(id);
		if(baSyspType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baSyspType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baSyspType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaSyspType baSyspType) {
        return super.exportXls(request, baSyspType, BaSyspType.class, "参数类型");
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
        return super.importExcel(request, response, BaSyspType.class);
    }

}
