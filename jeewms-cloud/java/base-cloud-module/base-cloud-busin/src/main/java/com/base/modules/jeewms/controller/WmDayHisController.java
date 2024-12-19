package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmDayHis;
import com.base.modules.jeewms.service.IWmDayHisService;
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
 * @Description: wm_day_his
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wm_day_his")
@RestController
@RequestMapping("/jeewms/wmDayHis")
@Slf4j
public class WmDayHisController extends JeecgController<WmDayHis, IWmDayHisService> {
	@Autowired
	private IWmDayHisService wmDayHisService;

	/**
	 * 分页列表查询
	 *
	 * @param wmDayHis
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_day_his-分页列表查询")
	@ApiOperation(value="wm_day_his-分页列表查询", notes="wm_day_his-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmDayHis wmDayHis,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmDayHis> queryWrapper = QueryGenerator.initQueryWrapper(wmDayHis, req.getParameterMap());
		Page<WmDayHis> page = new Page<WmDayHis>(pageNo, pageSize);
		IPage<WmDayHis> pageList = wmDayHisService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmDayHis
	 * @return
	 */
	@AutoLog(value = "wm_day_his-添加")
	@ApiOperation(value="wm_day_his-添加", notes="wm_day_his-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmDayHis wmDayHis) {
		wmDayHisService.save(wmDayHis);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmDayHis
	 * @return
	 */
	@AutoLog(value = "wm_day_his-编辑")
	@ApiOperation(value="wm_day_his-编辑", notes="wm_day_his-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmDayHis wmDayHis) {
		wmDayHisService.updateById(wmDayHis);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_day_his-通过id删除")
	@ApiOperation(value="wm_day_his-通过id删除", notes="wm_day_his-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmDayHisService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_day_his-批量删除")
	@ApiOperation(value="wm_day_his-批量删除", notes="wm_day_his-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmDayHisService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_day_his-通过id查询")
	@ApiOperation(value="wm_day_his-通过id查询", notes="wm_day_his-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmDayHis wmDayHis = wmDayHisService.getById(id);
		if(wmDayHis==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmDayHis);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmDayHis
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmDayHis wmDayHis) {
        return super.exportXls(request, wmDayHis, WmDayHis.class, "wm_day_his");
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
        return super.importExcel(request, response, WmDayHis.class);
    }

}
