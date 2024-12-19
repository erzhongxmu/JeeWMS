package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.BaArea;
import com.base.modules.jeewms.service.IBaAreaService;
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
 * @Description: 大区信息
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="大区信息")
@RestController
@RequestMapping("/jeewms/baArea")
@Slf4j
public class BaAreaController extends JeecgController<BaArea, IBaAreaService> {
	@Autowired
	private IBaAreaService baAreaService;

	/**
	 * 分页列表查询
	 *
	 * @param baArea
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "大区信息-分页列表查询")
	@ApiOperation(value="大区信息-分页列表查询", notes="大区信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaArea baArea,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaArea> queryWrapper = QueryGenerator.initQueryWrapper(baArea, req.getParameterMap());
		Page<BaArea> page = new Page<BaArea>(pageNo, pageSize);
		IPage<BaArea> pageList = baAreaService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baArea
	 * @return
	 */
	@AutoLog(value = "大区信息-添加")
	@ApiOperation(value="大区信息-添加", notes="大区信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaArea baArea) {
		baAreaService.save(baArea);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baArea
	 * @return
	 */
	@AutoLog(value = "大区信息-编辑")
	@ApiOperation(value="大区信息-编辑", notes="大区信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaArea baArea) {
		baAreaService.updateById(baArea);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "大区信息-通过id删除")
	@ApiOperation(value="大区信息-通过id删除", notes="大区信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baAreaService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "大区信息-批量删除")
	@ApiOperation(value="大区信息-批量删除", notes="大区信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baAreaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "大区信息-通过id查询")
	@ApiOperation(value="大区信息-通过id查询", notes="大区信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaArea baArea = baAreaService.getById(id);
		if(baArea==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baArea);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baArea
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaArea baArea) {
        return super.exportXls(request, baArea, BaArea.class, "大区信息");
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
        return super.importExcel(request, response, BaArea.class);
    }

}
