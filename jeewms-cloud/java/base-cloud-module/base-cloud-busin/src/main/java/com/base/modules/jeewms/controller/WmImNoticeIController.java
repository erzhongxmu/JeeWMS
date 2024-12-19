package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.WmImNoticeI;
import com.base.modules.jeewms.service.IWmImNoticeIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: wm_im_notice_i
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="收货通知详细")
@RestController
@RequestMapping("/jeewms/wmImNoticeI")
@Slf4j
public class WmImNoticeIController extends JeecgController<WmImNoticeI, IWmImNoticeIService> {
	@Autowired
	private IWmImNoticeIService wmImNoticeIService;

	/**
	 * 分页列表查询
	 *
	 * @param wmImNoticeI
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_im_notice_i-分页列表查询")
	@ApiOperation(value="wm_im_notice_i-分页列表查询", notes="wm_im_notice_i-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmImNoticeI wmImNoticeI,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmImNoticeI> queryWrapper = QueryGenerator.initQueryWrapper(wmImNoticeI, req.getParameterMap());
		Page<WmImNoticeI> page = new Page<WmImNoticeI>(pageNo, pageSize);
		IPage<WmImNoticeI> pageList = wmImNoticeIService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
	  * 分页列表查询
	  *
	  * @param wmImNoticeI
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "批量收货分页查询-分页列表查询")
	 @ApiOperation(value="批量收货-分页列表查询", notes="批量收货-分页列表查询")
	 @GetMapping(value = "/batchList")
	 public Result<?> queryCusPageList(WmImNoticeI wmImNoticeI,
									   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									   HttpServletRequest req) {

		 return Result.ok(wmImNoticeIService.selectBatchList(wmImNoticeI,pageNo,pageSize,req));
	 }

	 /**
	  * 退货验收
	  *
	  * @param wmImNoticeI
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	 @ApiOperation(value="退货验收-分页列表查询", notes="退货验收-分页列表查询")
	 @GetMapping(value = "/ysList")
	 public Result<?> queryYsPageList(WmImNoticeI wmImNoticeI,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {

		 return Result.ok(wmImNoticeIService.selectYsList(wmImNoticeI,pageNo,pageSize));
	 }

	/**
	 *   添加
	 *
	 * @param wmImNoticeI
	 * @return
	 */
	@AutoLog(value = "wm_im_notice_i-添加")
	@ApiOperation(value="wm_im_notice_i-添加", notes="wm_im_notice_i-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmImNoticeI wmImNoticeI) {
		wmImNoticeIService.save(wmImNoticeI);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmImNoticeI
	 * @return
	 */
	@AutoLog(value = "wm_im_notice_i-编辑")
	@ApiOperation(value="wm_im_notice_i-编辑", notes="wm_im_notice_i-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmImNoticeI wmImNoticeI) {
		wmImNoticeIService.updateById(wmImNoticeI);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_im_notice_i-通过id删除")
	@ApiOperation(value="wm_im_notice_i-通过id删除", notes="wm_im_notice_i-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmImNoticeIService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_im_notice_i-批量删除")
	@ApiOperation(value="wm_im_notice_i-批量删除", notes="wm_im_notice_i-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmImNoticeIService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_im_notice_i-通过id查询")
	@ApiOperation(value="wm_im_notice_i-通过id查询", notes="wm_im_notice_i-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmImNoticeI wmImNoticeI = wmImNoticeIService.getById(id);
		if(wmImNoticeI==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmImNoticeI);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmImNoticeI
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmImNoticeI wmImNoticeI) {
        return super.exportXls(request, wmImNoticeI, WmImNoticeI.class, "wm_im_notice_i");
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
        return super.importExcel(request, response, WmImNoticeI.class);
    }

}
