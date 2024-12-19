package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.BaWorkSta;
import com.base.modules.jeewms.service.IBaWorkStaService;
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
 * @Description: ba_work_sta
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="ba_work_sta")
@RestController
@RequestMapping("/jeewms/baWorkSta")
@Slf4j
public class BaWorkStaController extends JeecgController<BaWorkSta, IBaWorkStaService> {
	@Autowired
	private IBaWorkStaService baWorkStaService;

	/**
	 * 分页列表查询
	 *
	 * @param baWorkSta
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ba_work_sta-分页列表查询")
	@ApiOperation(value="ba_work_sta-分页列表查询", notes="ba_work_sta-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaWorkSta baWorkSta,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaWorkSta> queryWrapper = QueryGenerator.initQueryWrapper(baWorkSta, req.getParameterMap());
		Page<BaWorkSta> page = new Page<BaWorkSta>(pageNo, pageSize);
		IPage<BaWorkSta> pageList = baWorkStaService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baWorkSta
	 * @return
	 */
	@AutoLog(value = "ba_work_sta-添加")
	@ApiOperation(value="ba_work_sta-添加", notes="ba_work_sta-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaWorkSta baWorkSta) {
		baWorkStaService.save(baWorkSta);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baWorkSta
	 * @return
	 */
	@AutoLog(value = "ba_work_sta-编辑")
	@ApiOperation(value="ba_work_sta-编辑", notes="ba_work_sta-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaWorkSta baWorkSta) {
		baWorkStaService.updateById(baWorkSta);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_work_sta-通过id删除")
	@ApiOperation(value="ba_work_sta-通过id删除", notes="ba_work_sta-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baWorkStaService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ba_work_sta-批量删除")
	@ApiOperation(value="ba_work_sta-批量删除", notes="ba_work_sta-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baWorkStaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_work_sta-通过id查询")
	@ApiOperation(value="ba_work_sta-通过id查询", notes="ba_work_sta-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaWorkSta baWorkSta = baWorkStaService.getById(id);
		if(baWorkSta==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baWorkSta);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baWorkSta
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaWorkSta baWorkSta) {
        return super.exportXls(request, baWorkSta, BaWorkSta.class, "ba_work_sta");
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
        return super.importExcel(request, response, BaWorkSta.class);
    }

}
