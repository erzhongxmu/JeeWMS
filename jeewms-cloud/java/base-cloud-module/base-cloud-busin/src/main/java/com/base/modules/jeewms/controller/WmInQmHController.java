package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmInQmH;
import com.base.modules.jeewms.service.IWmInQmHService;
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
 * @Description: wm_in_qm_h
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wm_in_qm_h")
@RestController
@RequestMapping("/jeewms/wmInQmH")
@Slf4j
public class WmInQmHController extends JeecgController<WmInQmH, IWmInQmHService> {
	@Autowired
	private IWmInQmHService wmInQmHService;

	/**
	 * 分页列表查询
	 *
	 * @param wmInQmH
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_in_qm_h-分页列表查询")
	@ApiOperation(value="wm_in_qm_h-分页列表查询", notes="wm_in_qm_h-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmInQmH wmInQmH,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmInQmH> queryWrapper = QueryGenerator.initQueryWrapper(wmInQmH, req.getParameterMap());
		Page<WmInQmH> page = new Page<WmInQmH>(pageNo, pageSize);
		IPage<WmInQmH> pageList = wmInQmHService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmInQmH
	 * @return
	 */
	@AutoLog(value = "wm_in_qm_h-添加")
	@ApiOperation(value="wm_in_qm_h-添加", notes="wm_in_qm_h-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmInQmH wmInQmH) {
		wmInQmHService.save(wmInQmH);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmInQmH
	 * @return
	 */
	@AutoLog(value = "wm_in_qm_h-编辑")
	@ApiOperation(value="wm_in_qm_h-编辑", notes="wm_in_qm_h-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmInQmH wmInQmH) {
		wmInQmHService.updateById(wmInQmH);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_in_qm_h-通过id删除")
	@ApiOperation(value="wm_in_qm_h-通过id删除", notes="wm_in_qm_h-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmInQmHService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_in_qm_h-批量删除")
	@ApiOperation(value="wm_in_qm_h-批量删除", notes="wm_in_qm_h-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmInQmHService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_in_qm_h-通过id查询")
	@ApiOperation(value="wm_in_qm_h-通过id查询", notes="wm_in_qm_h-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmInQmH wmInQmH = wmInQmHService.getById(id);
		if(wmInQmH==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmInQmH);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmInQmH
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmInQmH wmInQmH) {
        return super.exportXls(request, wmInQmH, WmInQmH.class, "wm_in_qm_h");
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
        return super.importExcel(request, response, WmInQmH.class);
    }

}
