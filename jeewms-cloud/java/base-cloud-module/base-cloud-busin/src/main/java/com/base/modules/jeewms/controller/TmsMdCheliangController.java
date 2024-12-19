package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.TmsMdCheliang;
import com.base.modules.jeewms.service.ITmsMdCheliangService;
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
 * @Description: tms_md_cheliang
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="tms_md_cheliang")
@RestController
@RequestMapping("/jeewms/tmsMdCheliang")
@Slf4j
public class TmsMdCheliangController extends JeecgController<TmsMdCheliang, ITmsMdCheliangService> {
	@Autowired
	private ITmsMdCheliangService tmsMdCheliangService;

	/**
	 * 分页列表查询
	 *
	 * @param tmsMdCheliang
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tms_md_cheliang-分页列表查询")
	@ApiOperation(value="tms_md_cheliang-分页列表查询", notes="tms_md_cheliang-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TmsMdCheliang tmsMdCheliang,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TmsMdCheliang> queryWrapper = QueryGenerator.initQueryWrapper(tmsMdCheliang, req.getParameterMap());
		Page<TmsMdCheliang> page = new Page<TmsMdCheliang>(pageNo, pageSize);
		IPage<TmsMdCheliang> pageList = tmsMdCheliangService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param tmsMdCheliang
	 * @return
	 */
	@AutoLog(value = "tms_md_cheliang-添加")
	@ApiOperation(value="tms_md_cheliang-添加", notes="tms_md_cheliang-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TmsMdCheliang tmsMdCheliang) {
		tmsMdCheliangService.save(tmsMdCheliang);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param tmsMdCheliang
	 * @return
	 */
	@AutoLog(value = "tms_md_cheliang-编辑")
	@ApiOperation(value="tms_md_cheliang-编辑", notes="tms_md_cheliang-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TmsMdCheliang tmsMdCheliang) {
		tmsMdCheliangService.updateById(tmsMdCheliang);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tms_md_cheliang-通过id删除")
	@ApiOperation(value="tms_md_cheliang-通过id删除", notes="tms_md_cheliang-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		tmsMdCheliangService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tms_md_cheliang-批量删除")
	@ApiOperation(value="tms_md_cheliang-批量删除", notes="tms_md_cheliang-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tmsMdCheliangService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tms_md_cheliang-通过id查询")
	@ApiOperation(value="tms_md_cheliang-通过id查询", notes="tms_md_cheliang-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TmsMdCheliang tmsMdCheliang = tmsMdCheliangService.getById(id);
		if(tmsMdCheliang==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tmsMdCheliang);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tmsMdCheliang
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TmsMdCheliang tmsMdCheliang) {
        return super.exportXls(request, tmsMdCheliang, TmsMdCheliang.class, "tms_md_cheliang");
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
        return super.importExcel(request, response, TmsMdCheliang.class);
    }

}
