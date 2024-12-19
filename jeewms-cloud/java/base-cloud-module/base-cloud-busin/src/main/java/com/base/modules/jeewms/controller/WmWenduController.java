package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmWendu;
import com.base.modules.jeewms.service.IWmWenduService;
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
 * @Description: wm_wendu
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wm_wendu")
@RestController
@RequestMapping("/jeewms/wmWendu")
@Slf4j
public class WmWenduController extends JeecgController<WmWendu, IWmWenduService> {
	@Autowired
	private IWmWenduService wmWenduService;

	/**
	 * 分页列表查询
	 *
	 * @param wmWendu
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_wendu-分页列表查询")
	@ApiOperation(value="wm_wendu-分页列表查询", notes="wm_wendu-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmWendu wmWendu,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmWendu> queryWrapper = QueryGenerator.initQueryWrapper(wmWendu, req.getParameterMap());
		Page<WmWendu> page = new Page<WmWendu>(pageNo, pageSize);
		IPage<WmWendu> pageList = wmWenduService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmWendu
	 * @return
	 */
	@AutoLog(value = "wm_wendu-添加")
	@ApiOperation(value="wm_wendu-添加", notes="wm_wendu-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmWendu wmWendu) {
		wmWenduService.save(wmWendu);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmWendu
	 * @return
	 */
	@AutoLog(value = "wm_wendu-编辑")
	@ApiOperation(value="wm_wendu-编辑", notes="wm_wendu-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmWendu wmWendu) {
		wmWenduService.updateById(wmWendu);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_wendu-通过id删除")
	@ApiOperation(value="wm_wendu-通过id删除", notes="wm_wendu-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmWenduService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_wendu-批量删除")
	@ApiOperation(value="wm_wendu-批量删除", notes="wm_wendu-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmWenduService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_wendu-通过id查询")
	@ApiOperation(value="wm_wendu-通过id查询", notes="wm_wendu-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmWendu wmWendu = wmWenduService.getById(id);
		if(wmWendu==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmWendu);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmWendu
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmWendu wmWendu) {
        return super.exportXls(request, wmWendu, WmWendu.class, "wm_wendu");
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
        return super.importExcel(request, response, WmWendu.class);
    }

}
