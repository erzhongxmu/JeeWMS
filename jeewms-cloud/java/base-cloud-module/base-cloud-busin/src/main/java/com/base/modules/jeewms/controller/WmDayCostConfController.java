package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmDayCostConf;
import com.base.modules.jeewms.service.IWmDayCostConfService;
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
 * @Description: 计费日期
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="计费日期")
@RestController
@RequestMapping("/jeewms/wmDayCostConf")
@Slf4j
public class WmDayCostConfController extends JeecgController<WmDayCostConf, IWmDayCostConfService> {
	@Autowired
	private IWmDayCostConfService wmDayCostConfService;

	/**
	 * 分页列表查询
	 *
	 * @param wmDayCostConf
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "计费日期-分页列表查询")
	@ApiOperation(value="计费日期-分页列表查询", notes="计费日期-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmDayCostConf wmDayCostConf,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmDayCostConf> queryWrapper = QueryGenerator.initQueryWrapper(wmDayCostConf, req.getParameterMap());
		Page<WmDayCostConf> page = new Page<WmDayCostConf>(pageNo, pageSize);
		IPage<WmDayCostConf> pageList = wmDayCostConfService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmDayCostConf
	 * @return
	 */
	@AutoLog(value = "计费日期-添加")
	@ApiOperation(value="计费日期-添加", notes="计费日期-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmDayCostConf wmDayCostConf) {
		wmDayCostConfService.save(wmDayCostConf);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmDayCostConf
	 * @return
	 */
	@AutoLog(value = "计费日期-编辑")
	@ApiOperation(value="计费日期-编辑", notes="计费日期-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmDayCostConf wmDayCostConf) {
		wmDayCostConfService.updateById(wmDayCostConf);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "计费日期-通过id删除")
	@ApiOperation(value="计费日期-通过id删除", notes="计费日期-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmDayCostConfService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "计费日期-批量删除")
	@ApiOperation(value="计费日期-批量删除", notes="计费日期-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmDayCostConfService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "计费日期-通过id查询")
	@ApiOperation(value="计费日期-通过id查询", notes="计费日期-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmDayCostConf wmDayCostConf = wmDayCostConfService.getById(id);
		if(wmDayCostConf==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmDayCostConf);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmDayCostConf
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmDayCostConf wmDayCostConf) {
        return super.exportXls(request, wmDayCostConf, WmDayCostConf.class, "计费日期");
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
        return super.importExcel(request, response, WmDayCostConf.class);
    }

}
