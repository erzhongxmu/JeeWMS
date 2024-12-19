package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.base.modules.jeewms.service.IWmOmNoticeIService;
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
 * @Description: 出货详情
 * @Author: base-boot
 * @Date:   2021-05-28
 * @Version: V1.0
 */
@Api(tags="出货详情")
@RestController
@RequestMapping("/jeewms/wmOmNoticeI")
@Slf4j
public class WmOmNoticeIController extends JeecgController<WmOmNoticeI, IWmOmNoticeIService> {
	@Autowired
	private IWmOmNoticeIService wmOmNoticeIService;

	/**
	 * 分页列表查询
	 *
	 * @param wmOmNoticeI
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "出货详情-分页列表查询")
	@ApiOperation(value="出货详情-分页列表查询", notes="出货详情-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmOmNoticeI wmOmNoticeI,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmOmNoticeI> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeI, req.getParameterMap());
		Page<WmOmNoticeI> page = new Page<WmOmNoticeI>(pageNo, pageSize);
		IPage<WmOmNoticeI> pageList = wmOmNoticeIService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmOmNoticeI
	 * @return
	 */
	@AutoLog(value = "出货详情-添加")
	@ApiOperation(value="出货详情-添加", notes="出货详情-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmOmNoticeI wmOmNoticeI) {
		wmOmNoticeIService.save(wmOmNoticeI);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmOmNoticeI
	 * @return
	 */
	@AutoLog(value = "出货详情-编辑")
	@ApiOperation(value="出货详情-编辑", notes="出货详情-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmOmNoticeI wmOmNoticeI) {
		wmOmNoticeIService.updateById(wmOmNoticeI);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "出货详情-通过id删除")
	@ApiOperation(value="出货详情-通过id删除", notes="出货详情-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmOmNoticeIService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "出货详情-批量删除")
	@ApiOperation(value="出货详情-批量删除", notes="出货详情-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmOmNoticeIService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "出货详情-通过id查询")
	@ApiOperation(value="出货详情-通过id查询", notes="出货详情-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmOmNoticeI wmOmNoticeI = wmOmNoticeIService.getById(id);
		if(wmOmNoticeI==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmOmNoticeI);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmOmNoticeI
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmOmNoticeI wmOmNoticeI) {
        return super.exportXls(request, wmOmNoticeI, WmOmNoticeI.class, "出货详情");
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
        return super.importExcel(request, response, WmOmNoticeI.class);
    }

}
