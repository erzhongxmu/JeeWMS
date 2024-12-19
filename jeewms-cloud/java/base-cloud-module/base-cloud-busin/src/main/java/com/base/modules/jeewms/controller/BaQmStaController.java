package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaQmSta;
import com.base.modules.jeewms.service.IBaQmStaService;

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
 * @Description: 品检状态
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="品检状态")
@RestController
@RequestMapping("/jeewms/baQmSta")
@Slf4j
public class BaQmStaController extends JeecgController<BaQmSta, IBaQmStaService> {
	@Autowired
	private IBaQmStaService baQmStaService;

	/**
	 * 分页列表查询
	 *
	 * @param baQmSta
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "品检状态-分页列表查询")
	@ApiOperation(value="品检状态-分页列表查询", notes="品检状态-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaQmSta baQmSta,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaQmSta> queryWrapper = QueryGenerator.initQueryWrapper(baQmSta, req.getParameterMap());
		Page<BaQmSta> page = new Page<BaQmSta>(pageNo, pageSize);
		IPage<BaQmSta> pageList = baQmStaService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baQmSta
	 * @return
	 */
	@AutoLog(value = "品检状态-添加")
	@ApiOperation(value="品检状态-添加", notes="品检状态-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaQmSta baQmSta) {
		baQmStaService.save(baQmSta);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baQmSta
	 * @return
	 */
	@AutoLog(value = "品检状态-编辑")
	@ApiOperation(value="品检状态-编辑", notes="品检状态-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaQmSta baQmSta) {
		baQmStaService.updateById(baQmSta);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "品检状态-通过id删除")
	@ApiOperation(value="品检状态-通过id删除", notes="品检状态-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baQmStaService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "品检状态-批量删除")
	@ApiOperation(value="品检状态-批量删除", notes="品检状态-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baQmStaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "品检状态-通过id查询")
	@ApiOperation(value="品检状态-通过id查询", notes="品检状态-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaQmSta baQmSta = baQmStaService.getById(id);
		if(baQmSta==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baQmSta);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baQmSta
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaQmSta baQmSta) {
        return super.exportXls(request, baQmSta, BaQmSta.class, "品检状态");
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
        return super.importExcel(request, response, BaQmSta.class);
    }

}
