package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaCostConf;
import com.base.modules.jeewms.service.IBaCostConfService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

 /**
 * @Description: 费用模板
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="费用模板")
@RestController
@RequestMapping("/jeewms/baCostConf")
@Slf4j
public class BaCostConfController extends JeecgController<BaCostConf, IBaCostConfService> {
	@Autowired
	private IBaCostConfService baCostConfService;

	/**
	 * 分页列表查询
	 *
	 * @param baCostConf
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "费用模板-分页列表查询")
	@ApiOperation(value="费用模板-分页列表查询", notes="费用模板-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaCostConf baCostConf,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaCostConf> queryWrapper = QueryGenerator.initQueryWrapper(baCostConf, req.getParameterMap());
		Page<BaCostConf> page = new Page<BaCostConf>(pageNo, pageSize);
		IPage<BaCostConf> pageList = baCostConfService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baCostConf
	 * @return
	 */
	@AutoLog(value = "费用模板-添加")
	@ApiOperation(value="费用模板-添加", notes="费用模板-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaCostConf baCostConf) {
		baCostConfService.save(baCostConf);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baCostConf
	 * @return
	 */
	@AutoLog(value = "费用模板-编辑")
	@ApiOperation(value="费用模板-编辑", notes="费用模板-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaCostConf baCostConf) {
		baCostConfService.updateById(baCostConf);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "费用模板-通过id删除")
	@ApiOperation(value="费用模板-通过id删除", notes="费用模板-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baCostConfService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "费用模板-批量删除")
	@ApiOperation(value="费用模板-批量删除", notes="费用模板-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baCostConfService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "费用模板-通过id查询")
	@ApiOperation(value="费用模板-通过id查询", notes="费用模板-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaCostConf baCostConf = baCostConfService.getById(id);
		if(baCostConf==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baCostConf);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baCostConf
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaCostConf baCostConf) {
        return super.exportXls(request, baCostConf, BaCostConf.class, "费用模板");
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
        return super.importExcel(request, response, BaCostConf.class);
    }

}
