package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmToUp;
import com.base.modules.jeewms.service.IWmToUpService;
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
 * @Description: wm_to_up
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wm_to_up")
@RestController
@RequestMapping("/jeewms/wmToUp")
@Slf4j
public class WmToUpController extends JeecgController<WmToUp, IWmToUpService> {
	@Autowired
	private IWmToUpService wmToUpService;

	/**
	 * 分页列表查询
	 *
	 * @param wmToUp
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_to_up-分页列表查询")
	@ApiOperation(value="wm_to_up-分页列表查询", notes="wm_to_up-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmToUp wmToUp,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmToUp> queryWrapper = QueryGenerator.initQueryWrapper(wmToUp, req.getParameterMap());
		Page<WmToUp> page = new Page<WmToUp>(pageNo, pageSize);
		IPage<WmToUp> pageList = wmToUpService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmToUp
	 * @return
	 */
	@AutoLog(value = "wm_to_up-添加")
	@ApiOperation(value="wm_to_up-添加", notes="wm_to_up-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmToUp wmToUp) {
		wmToUpService.save(wmToUp);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmToUp
	 * @return
	 */
	@AutoLog(value = "wm_to_up-编辑")
	@ApiOperation(value="wm_to_up-编辑", notes="wm_to_up-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmToUp wmToUp) {
		wmToUpService.updateById(wmToUp);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_to_up-通过id删除")
	@ApiOperation(value="wm_to_up-通过id删除", notes="wm_to_up-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmToUpService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_to_up-批量删除")
	@ApiOperation(value="wm_to_up-批量删除", notes="wm_to_up-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmToUpService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_to_up-通过id查询")
	@ApiOperation(value="wm_to_up-通过id查询", notes="wm_to_up-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmToUp wmToUp = wmToUpService.getById(id);
		if(wmToUp==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmToUp);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmToUp
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmToUp wmToUp) {
        return super.exportXls(request, wmToUp, WmToUp.class, "wm_to_up");
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
        return super.importExcel(request, response, WmToUp.class);
    }

}
