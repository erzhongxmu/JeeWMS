package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmOmDelvI;
import com.base.modules.jeewms.service.IWmOmDelvIService;
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
 * @Description: wm_om_delv_i
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wm_om_delv_i")
@RestController
@RequestMapping("/jeewms/wmOmDelvI")
@Slf4j
public class WmOmDelvIController extends JeecgController<WmOmDelvI, IWmOmDelvIService> {
	@Autowired
	private IWmOmDelvIService wmOmDelvIService;

	/**
	 * 分页列表查询
	 *
	 * @param wmOmDelvI
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_om_delv_i-分页列表查询")
	@ApiOperation(value="wm_om_delv_i-分页列表查询", notes="wm_om_delv_i-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmOmDelvI wmOmDelvI,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmOmDelvI> queryWrapper = QueryGenerator.initQueryWrapper(wmOmDelvI, req.getParameterMap());
		Page<WmOmDelvI> page = new Page<WmOmDelvI>(pageNo, pageSize);
		IPage<WmOmDelvI> pageList = wmOmDelvIService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmOmDelvI
	 * @return
	 */
	@AutoLog(value = "wm_om_delv_i-添加")
	@ApiOperation(value="wm_om_delv_i-添加", notes="wm_om_delv_i-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmOmDelvI wmOmDelvI) {
		wmOmDelvIService.save(wmOmDelvI);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmOmDelvI
	 * @return
	 */
	@AutoLog(value = "wm_om_delv_i-编辑")
	@ApiOperation(value="wm_om_delv_i-编辑", notes="wm_om_delv_i-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmOmDelvI wmOmDelvI) {
		wmOmDelvIService.updateById(wmOmDelvI);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_om_delv_i-通过id删除")
	@ApiOperation(value="wm_om_delv_i-通过id删除", notes="wm_om_delv_i-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmOmDelvIService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_om_delv_i-批量删除")
	@ApiOperation(value="wm_om_delv_i-批量删除", notes="wm_om_delv_i-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmOmDelvIService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_om_delv_i-通过id查询")
	@ApiOperation(value="wm_om_delv_i-通过id查询", notes="wm_om_delv_i-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmOmDelvI wmOmDelvI = wmOmDelvIService.getById(id);
		if(wmOmDelvI==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmOmDelvI);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmOmDelvI
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmOmDelvI wmOmDelvI) {
        return super.exportXls(request, wmOmDelvI, WmOmDelvI.class, "wm_om_delv_i");
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
        return super.importExcel(request, response, WmOmDelvI.class);
    }

}
