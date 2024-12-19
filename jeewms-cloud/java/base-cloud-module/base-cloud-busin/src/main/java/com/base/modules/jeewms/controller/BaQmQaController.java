package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaQmQa;
import com.base.modules.jeewms.entity.BaStore;
import com.base.modules.jeewms.service.IBaQmQaService;

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
 * @Description: 品质代码
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="品质代码")
@RestController
@RequestMapping("/jeewms/baQmQa")
@Slf4j
public class BaQmQaController extends JeecgController<BaQmQa, IBaQmQaService> {
	@Autowired
	private IBaQmQaService baQmQaService;

	/**
	 * 分页列表查询
	 *
	 * @param baQmQa
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "品质代码-分页列表查询")
	@ApiOperation(value="品质代码-分页列表查询", notes="品质代码-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaQmQa baQmQa,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaQmQa> queryWrapper = QueryGenerator.initQueryWrapper(baQmQa, req.getParameterMap());
		Page<BaQmQa> page = new Page<BaQmQa>(pageNo, pageSize);
		IPage<BaQmQa> pageList = baQmQaService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baQmQa
	 * @return
	 */
	@AutoLog(value = "品质代码-添加")
	@ApiOperation(value="品质代码-添加", notes="品质代码-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaQmQa baQmQa) {
		if (baQmQaService.lambdaQuery()
				.eq(BaQmQa::getQmQaCode, baQmQa.getQmQaCode()).count()>0){
			return Result.error("编码已存在，请重新输入");
		}else {
			baQmQaService.save(baQmQa);
		}

		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baQmQa
	 * @return
	 */
	@AutoLog(value = "品质代码-编辑")
	@ApiOperation(value="品质代码-编辑", notes="品质代码-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaQmQa baQmQa) {
		BaQmQa baQmQas = baQmQaService.lambdaQuery().eq(BaQmQa::getQmQaCode, baQmQa.getQmQaCode())
				.ne(BaQmQa::getId, baQmQa.getId()).one();
		if (baQmQas != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			baQmQaService.updateById(baQmQa);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "品质代码-通过id删除")
	@ApiOperation(value="品质代码-通过id删除", notes="品质代码-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baQmQaService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "品质代码-批量删除")
	@ApiOperation(value="品质代码-批量删除", notes="品质代码-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baQmQaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "品质代码-通过id查询")
	@ApiOperation(value="品质代码-通过id查询", notes="品质代码-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaQmQa baQmQa = baQmQaService.getById(id);
		if(baQmQa==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baQmQa);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baQmQa
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaQmQa baQmQa) {
        return super.exportXls(request, baQmQa, BaQmQa.class, "品质代码");
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
        return super.importExcel(request, response, BaQmQa.class);
    }

}
