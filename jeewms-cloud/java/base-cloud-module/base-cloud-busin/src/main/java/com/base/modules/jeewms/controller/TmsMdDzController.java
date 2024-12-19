package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.TmsMdDz;
import com.base.modules.jeewms.service.ITmsMdDzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: tms_md_dz
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="tms_md_dz")
@RestController
@RequestMapping("/jeewms/tmsMdDz")
@Slf4j
public class TmsMdDzController extends JeecgController<TmsMdDz, ITmsMdDzService> {
	@Autowired
	private ITmsMdDzService tmsMdDzService;

	/**
	 * 分页列表查询
	 *
	 * @param tmsMdDz
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tms_md_dz-分页列表查询")
	@ApiOperation(value="tms_md_dz-分页列表查询", notes="tms_md_dz-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TmsMdDz tmsMdDz,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TmsMdDz> queryWrapper = QueryGenerator.initQueryWrapper(tmsMdDz, req.getParameterMap());
		Page<TmsMdDz> page = new Page<TmsMdDz>(pageNo, pageSize);
		IPage<TmsMdDz> pageList = tmsMdDzService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param tmsMdDz
	 * @return
	 */
	@AutoLog(value = "tms_md_dz-添加")
	@ApiOperation(value="tms_md_dz-添加", notes="tms_md_dz-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TmsMdDz tmsMdDz) {
		tmsMdDzService.save(tmsMdDz);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param tmsMdDz
	 * @return
	 */
	@AutoLog(value = "tms_md_dz-编辑")
	@ApiOperation(value="tms_md_dz-编辑", notes="tms_md_dz-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TmsMdDz tmsMdDz) {
		tmsMdDzService.updateById(tmsMdDz);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tms_md_dz-通过id删除")
	@ApiOperation(value="tms_md_dz-通过id删除", notes="tms_md_dz-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		tmsMdDzService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tms_md_dz-批量删除")
	@ApiOperation(value="tms_md_dz-批量删除", notes="tms_md_dz-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tmsMdDzService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tms_md_dz-通过id查询")
	@ApiOperation(value="tms_md_dz-通过id查询", notes="tms_md_dz-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TmsMdDz tmsMdDz = tmsMdDzService.getById(id);
		if(tmsMdDz==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tmsMdDz);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tmsMdDz
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TmsMdDz tmsMdDz) {
        return super.exportXls(request, tmsMdDz, TmsMdDz.class, "tms_md_dz");
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
        return super.importExcel(request, response, TmsMdDz.class);
    }

}
