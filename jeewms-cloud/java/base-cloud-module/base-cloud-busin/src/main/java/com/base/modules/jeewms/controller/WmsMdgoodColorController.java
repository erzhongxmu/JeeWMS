package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeewms.entity.WmsLogoConfig;
import com.base.modules.jeewms.entity.WmsMdgoodColor;
import com.base.modules.jeewms.service.IWmsMdgoodColorService;
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
 * @Description: 物料颜色
 * @Author: base-boot
 * @Date:   2022-05-16
 * @Version: V1.0
 */
@Api(tags="物料颜色")
@RestController
@RequestMapping("/jeewms/wmsMdgoodColor")
@Slf4j
public class WmsMdgoodColorController extends BaseController<WmsMdgoodColor, IWmsMdgoodColorService> {
	@Autowired
	private IWmsMdgoodColorService wmsMdgoodColorService;

	/**
	 * 分页列表查询
	 *
	 * @param wmsMdgoodColor
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "物料颜色-分页列表查询")
	@ApiOperation(value="物料颜色-分页列表查询", notes="物料颜色-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsMdgoodColor wmsMdgoodColor,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsMdgoodColor> queryWrapper = QueryGenerator.initQueryWrapper(wmsMdgoodColor, req.getParameterMap());
		Page<WmsMdgoodColor> page = new Page<WmsMdgoodColor>(pageNo, pageSize);
		IPage<WmsMdgoodColor> pageList = wmsMdgoodColorService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmsMdgoodColor
	 * @return
	 */
	@AutoLog(value = "物料颜色-添加")
	@ApiOperation(value="物料颜色-添加", notes="物料颜色-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsMdgoodColor wmsMdgoodColor) {
		if (wmsMdgoodColorService.lambdaQuery()
				.eq(WmsMdgoodColor::getColorCode,wmsMdgoodColor.getColorCode()).count()>0){
			return Result.error("编码已存在，请重新输入");
		}else {
			wmsMdgoodColorService.save(wmsMdgoodColor);
		}
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmsMdgoodColor
	 * @return
	 */
	@AutoLog(value = "物料颜色-编辑")
	@ApiOperation(value="物料颜色-编辑", notes="物料颜色-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsMdgoodColor wmsMdgoodColor) {
		WmsMdgoodColor wmsMdgoodColors = wmsMdgoodColorService.lambdaQuery().eq(WmsMdgoodColor::getColorCode, wmsMdgoodColor.getColorCode())
				.ne(WmsMdgoodColor::getId, wmsMdgoodColor.getId()).one();
		if (wmsMdgoodColors != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			wmsMdgoodColorService.updateById(wmsMdgoodColor);
		}
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物料颜色-通过id删除")
	@ApiOperation(value="物料颜色-通过id删除", notes="物料颜色-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsMdgoodColorService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "物料颜色-批量删除")
	@ApiOperation(value="物料颜色-批量删除", notes="物料颜色-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsMdgoodColorService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物料颜色-通过id查询")
	@ApiOperation(value="物料颜色-通过id查询", notes="物料颜色-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsMdgoodColor wmsMdgoodColor = wmsMdgoodColorService.getById(id);
		if(wmsMdgoodColor==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsMdgoodColor);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsMdgoodColor
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsMdgoodColor wmsMdgoodColor) {
        return super.exportXls(request, wmsMdgoodColor, WmsMdgoodColor.class, "物料颜色");
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
        return super.importExcel(request, response, WmsMdgoodColor.class);
    }

}
