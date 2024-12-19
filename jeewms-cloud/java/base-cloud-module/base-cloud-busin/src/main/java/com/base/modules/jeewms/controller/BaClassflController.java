package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaClassfl;
import com.base.modules.jeewms.service.IBaClassflService;

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
 * @Description: 行业分类
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
@Api(tags="行业分类")
@RestController
@RequestMapping("/jeewms/baClassfl")
@Slf4j
public class BaClassflController extends JeecgController<BaClassfl, IBaClassflService> {
	@Autowired
	private IBaClassflService baClassflService;

	/**
	 * 分页列表查询
	 *
	 * @param baClassfl
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "行业分类-分页列表查询")
	@ApiOperation(value="行业分类-分页列表查询", notes="行业分类-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaClassfl baClassfl,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaClassfl> queryWrapper = QueryGenerator.initQueryWrapper(baClassfl, req.getParameterMap());
		Page<BaClassfl> page = new Page<BaClassfl>(pageNo, pageSize);
		IPage<BaClassfl> pageList = baClassflService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baClassfl
	 * @return
	 */
	@AutoLog(value = "行业分类-添加")
	@ApiOperation(value="行业分类-添加", notes="行业分类-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaClassfl baClassfl) {
		baClassflService.save(baClassfl);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baClassfl
	 * @return
	 */
	@AutoLog(value = "行业分类-编辑")
	@ApiOperation(value="行业分类-编辑", notes="行业分类-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaClassfl baClassfl) {
		baClassflService.updateById(baClassfl);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "行业分类-通过id删除")
	@ApiOperation(value="行业分类-通过id删除", notes="行业分类-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baClassflService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "行业分类-批量删除")
	@ApiOperation(value="行业分类-批量删除", notes="行业分类-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baClassflService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "行业分类-通过id查询")
	@ApiOperation(value="行业分类-通过id查询", notes="行业分类-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaClassfl baClassfl = baClassflService.getById(id);
		if(baClassfl==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baClassfl);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baClassfl
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaClassfl baClassfl) {
        return super.exportXls(request, baClassfl, BaClassfl.class, "行业分类");
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
        return super.importExcel(request, response, BaClassfl.class);
    }

}
