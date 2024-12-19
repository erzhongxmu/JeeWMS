package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmTuopan;
import com.base.modules.jeewms.service.IWmTuopanService;
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
 * @Description: wm_tuopan
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wm_tuopan")
@RestController
@RequestMapping("/jeewms/wmTuopan")
@Slf4j
public class WmTuopanController extends JeecgController<WmTuopan, IWmTuopanService> {
	@Autowired
	private IWmTuopanService wmTuopanService;

	/**
	 * 分页列表查询
	 *
	 * @param wmTuopan
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_tuopan-分页列表查询")
	@ApiOperation(value="wm_tuopan-分页列表查询", notes="wm_tuopan-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmTuopan wmTuopan,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmTuopan> queryWrapper = QueryGenerator.initQueryWrapper(wmTuopan, req.getParameterMap());
		Page<WmTuopan> page = new Page<WmTuopan>(pageNo, pageSize);
		IPage<WmTuopan> pageList = wmTuopanService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmTuopan
	 * @return
	 */
	@AutoLog(value = "wm_tuopan-添加")
	@ApiOperation(value="wm_tuopan-添加", notes="wm_tuopan-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmTuopan wmTuopan) {
		wmTuopanService.save(wmTuopan);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmTuopan
	 * @return
	 */
	@AutoLog(value = "wm_tuopan-编辑")
	@ApiOperation(value="wm_tuopan-编辑", notes="wm_tuopan-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmTuopan wmTuopan) {
		wmTuopanService.updateById(wmTuopan);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_tuopan-通过id删除")
	@ApiOperation(value="wm_tuopan-通过id删除", notes="wm_tuopan-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmTuopanService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_tuopan-批量删除")
	@ApiOperation(value="wm_tuopan-批量删除", notes="wm_tuopan-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmTuopanService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_tuopan-通过id查询")
	@ApiOperation(value="wm_tuopan-通过id查询", notes="wm_tuopan-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmTuopan wmTuopan = wmTuopanService.getById(id);
		if(wmTuopan==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmTuopan);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmTuopan
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmTuopan wmTuopan) {
        return super.exportXls(request, wmTuopan, WmTuopan.class, "wm_tuopan");
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
        return super.importExcel(request, response, WmTuopan.class);
    }

}
