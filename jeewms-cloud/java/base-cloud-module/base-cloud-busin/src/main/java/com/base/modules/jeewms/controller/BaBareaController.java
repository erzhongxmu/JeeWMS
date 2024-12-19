package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.BaBarea;
import com.base.modules.jeewms.service.IBaBareaService;
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

 /**
 * @Description: 片区信息
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="片区信息")
@RestController
@RequestMapping("/jeewms/baBarea")
@Slf4j
public class BaBareaController extends JeecgController<BaBarea, IBaBareaService> {
	@Autowired
	private IBaBareaService baBareaService;

	/**
	 * 分页列表查询
	 *
	 * @param baBarea
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "片区信息-分页列表查询")
	@ApiOperation(value="片区信息-分页列表查询", notes="片区信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaBarea baBarea,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaBarea> queryWrapper = QueryGenerator.initQueryWrapper(baBarea, req.getParameterMap());
		Page<BaBarea> page = new Page<BaBarea>(pageNo, pageSize);
		IPage<BaBarea> pageList = baBareaService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baBarea
	 * @return
	 */
	@AutoLog(value = "片区信息-添加")
	@ApiOperation(value="片区信息-添加", notes="片区信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaBarea baBarea) {
		baBareaService.save(baBarea);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baBarea
	 * @return
	 */
	@AutoLog(value = "片区信息-编辑")
	@ApiOperation(value="片区信息-编辑", notes="片区信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaBarea baBarea) {
		baBareaService.updateById(baBarea);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "片区信息-通过id删除")
	@ApiOperation(value="片区信息-通过id删除", notes="片区信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baBareaService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "片区信息-批量删除")
	@ApiOperation(value="片区信息-批量删除", notes="片区信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baBareaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "片区信息-通过id查询")
	@ApiOperation(value="片区信息-通过id查询", notes="片区信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaBarea baBarea = baBareaService.getById(id);
		if(baBarea==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baBarea);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baBarea
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaBarea baBarea) {
        return super.exportXls(request, baBarea, BaBarea.class, "片区信息");
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
        return super.importExcel(request, response, BaBarea.class);
    }

}
