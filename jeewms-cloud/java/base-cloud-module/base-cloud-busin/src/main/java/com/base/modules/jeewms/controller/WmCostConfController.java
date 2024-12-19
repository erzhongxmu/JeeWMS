package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmCostConf;
import com.base.modules.jeewms.service.IWmCostConfService;
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
 * @Description: wm_cost_conf
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wm_cost_conf")
@RestController
@RequestMapping("/jeewms/wmCostConf")
@Slf4j
public class WmCostConfController extends JeecgController<WmCostConf, IWmCostConfService> {
	@Autowired
	private IWmCostConfService wmCostConfService;

	/**
	 * 分页列表查询
	 *
	 * @param wmCostConf
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_cost_conf-分页列表查询")
	@ApiOperation(value="wm_cost_conf-分页列表查询", notes="wm_cost_conf-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmCostConf wmCostConf,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmCostConf> queryWrapper = QueryGenerator.initQueryWrapper(wmCostConf, req.getParameterMap());
		Page<WmCostConf> page = new Page<WmCostConf>(pageNo, pageSize);
		IPage<WmCostConf> pageList = wmCostConfService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmCostConf
	 * @return
	 */
	@AutoLog(value = "wm_cost_conf-添加")
	@ApiOperation(value="wm_cost_conf-添加", notes="wm_cost_conf-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmCostConf wmCostConf) {
		wmCostConfService.save(wmCostConf);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmCostConf
	 * @return
	 */
	@AutoLog(value = "wm_cost_conf-编辑")
	@ApiOperation(value="wm_cost_conf-编辑", notes="wm_cost_conf-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmCostConf wmCostConf) {
		wmCostConfService.updateById(wmCostConf);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_cost_conf-通过id删除")
	@ApiOperation(value="wm_cost_conf-通过id删除", notes="wm_cost_conf-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmCostConfService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_cost_conf-批量删除")
	@ApiOperation(value="wm_cost_conf-批量删除", notes="wm_cost_conf-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmCostConfService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_cost_conf-通过id查询")
	@ApiOperation(value="wm_cost_conf-通过id查询", notes="wm_cost_conf-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmCostConf wmCostConf = wmCostConfService.getById(id);
		if(wmCostConf==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmCostConf);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmCostConf
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmCostConf wmCostConf) {
        return super.exportXls(request, wmCostConf, WmCostConf.class, "wm_cost_conf");
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
        return super.importExcel(request, response, WmCostConf.class);
    }

}
