package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaSexSta;
import com.base.modules.jeewms.service.IBaSexStaService;

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
 * @Description: ba_sex_sta
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="ba_sex_sta")
@RestController
@RequestMapping("/jeewms/baSexSta")
@Slf4j
public class BaSexStaController extends JeecgController<BaSexSta, IBaSexStaService> {
	@Autowired
	private IBaSexStaService baSexStaService;

	/**
	 * 分页列表查询
	 *
	 * @param baSexSta
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ba_sex_sta-分页列表查询")
	@ApiOperation(value="ba_sex_sta-分页列表查询", notes="ba_sex_sta-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaSexSta baSexSta,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaSexSta> queryWrapper = QueryGenerator.initQueryWrapper(baSexSta, req.getParameterMap());
		Page<BaSexSta> page = new Page<BaSexSta>(pageNo, pageSize);
		IPage<BaSexSta> pageList = baSexStaService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baSexSta
	 * @return
	 */
	@AutoLog(value = "ba_sex_sta-添加")
	@ApiOperation(value="ba_sex_sta-添加", notes="ba_sex_sta-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaSexSta baSexSta) {
		baSexStaService.save(baSexSta);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baSexSta
	 * @return
	 */
	@AutoLog(value = "ba_sex_sta-编辑")
	@ApiOperation(value="ba_sex_sta-编辑", notes="ba_sex_sta-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaSexSta baSexSta) {
		baSexStaService.updateById(baSexSta);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_sex_sta-通过id删除")
	@ApiOperation(value="ba_sex_sta-通过id删除", notes="ba_sex_sta-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baSexStaService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ba_sex_sta-批量删除")
	@ApiOperation(value="ba_sex_sta-批量删除", notes="ba_sex_sta-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baSexStaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_sex_sta-通过id查询")
	@ApiOperation(value="ba_sex_sta-通过id查询", notes="ba_sex_sta-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaSexSta baSexSta = baSexStaService.getById(id);
		if(baSexSta==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baSexSta);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baSexSta
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaSexSta baSexSta) {
        return super.exportXls(request, baSexSta, BaSexSta.class, "ba_sex_sta");
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
        return super.importExcel(request, response, BaSexSta.class);
    }

}
