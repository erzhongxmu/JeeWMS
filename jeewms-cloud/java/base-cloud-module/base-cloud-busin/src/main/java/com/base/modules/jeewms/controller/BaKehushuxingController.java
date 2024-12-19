package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaKehushuxing;
import com.base.modules.jeewms.service.IBaKehushuxingService;

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
 * @Description: 客户计费属性
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="客户计费属性")
@RestController
@RequestMapping("/jeewms/baKehushuxing")
@Slf4j
public class BaKehushuxingController extends JeecgController<BaKehushuxing, IBaKehushuxingService> {
	@Autowired
	private IBaKehushuxingService baKehushuxingService;

	/**
	 * 分页列表查询
	 *
	 * @param baKehushuxing
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "客户计费属性-分页列表查询")
	@ApiOperation(value="客户计费属性-分页列表查询", notes="客户计费属性-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaKehushuxing baKehushuxing,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaKehushuxing> queryWrapper = QueryGenerator.initQueryWrapper(baKehushuxing, req.getParameterMap());
		Page<BaKehushuxing> page = new Page<BaKehushuxing>(pageNo, pageSize);
		IPage<BaKehushuxing> pageList = baKehushuxingService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baKehushuxing
	 * @return
	 */
	@AutoLog(value = "客户计费属性-添加")
	@ApiOperation(value="客户计费属性-添加", notes="客户计费属性-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaKehushuxing baKehushuxing) {
		baKehushuxingService.save(baKehushuxing);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baKehushuxing
	 * @return
	 */
	@AutoLog(value = "客户计费属性-编辑")
	@ApiOperation(value="客户计费属性-编辑", notes="客户计费属性-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaKehushuxing baKehushuxing) {
		baKehushuxingService.updateById(baKehushuxing);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户计费属性-通过id删除")
	@ApiOperation(value="客户计费属性-通过id删除", notes="客户计费属性-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baKehushuxingService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "客户计费属性-批量删除")
	@ApiOperation(value="客户计费属性-批量删除", notes="客户计费属性-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baKehushuxingService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户计费属性-通过id查询")
	@ApiOperation(value="客户计费属性-通过id查询", notes="客户计费属性-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaKehushuxing baKehushuxing = baKehushuxingService.getById(id);
		if(baKehushuxing==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baKehushuxing);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baKehushuxing
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaKehushuxing baKehushuxing) {
        return super.exportXls(request, baKehushuxing, BaKehushuxing.class, "客户计费属性");
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
        return super.importExcel(request, response, BaKehushuxing.class);
    }

}
