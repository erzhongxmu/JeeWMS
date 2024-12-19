package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmNoticeConf;
import com.base.modules.jeewms.service.IWmNoticeConfService;
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
 * @Description: 回单管理
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
@Api(tags="回单管理")
@RestController
@RequestMapping("/jeewms/wmNoticeConf")
@Slf4j
public class WmNoticeConfController extends JeecgController<WmNoticeConf, IWmNoticeConfService> {
	@Autowired
	private IWmNoticeConfService wmNoticeConfService;

	/**
	 * 分页列表查询
	 *
	 * @param wmNoticeConf
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "回单管理-分页列表查询")
	@ApiOperation(value="回单管理-分页列表查询", notes="回单管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmNoticeConf wmNoticeConf,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmNoticeConf> queryWrapper = QueryGenerator.initQueryWrapper(wmNoticeConf, req.getParameterMap());
		Page<WmNoticeConf> page = new Page<WmNoticeConf>(pageNo, pageSize);
		IPage<WmNoticeConf> pageList = wmNoticeConfService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmNoticeConf
	 * @return
	 */
	@AutoLog(value = "回单管理-添加")
	@ApiOperation(value="回单管理-添加", notes="回单管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmNoticeConf wmNoticeConf) {
		wmNoticeConfService.save(wmNoticeConf);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmNoticeConf
	 * @return
	 */
	@AutoLog(value = "回单管理-编辑")
	@ApiOperation(value="回单管理-编辑", notes="回单管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmNoticeConf wmNoticeConf) {
		wmNoticeConfService.updateById(wmNoticeConf);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "回单管理-通过id删除")
	@ApiOperation(value="回单管理-通过id删除", notes="回单管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmNoticeConfService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "回单管理-批量删除")
	@ApiOperation(value="回单管理-批量删除", notes="回单管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmNoticeConfService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "回单管理-通过id查询")
	@ApiOperation(value="回单管理-通过id查询", notes="回单管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmNoticeConf wmNoticeConf = wmNoticeConfService.getById(id);
		if(wmNoticeConf==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmNoticeConf);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmNoticeConf
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmNoticeConf wmNoticeConf) {
        return super.exportXls(request, wmNoticeConf, WmNoticeConf.class, "回单管理");
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
        return super.importExcel(request, response, WmNoticeConf.class);
    }

}
