package com.base.modules.pda.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.pda.entity.WmPdaConfig;
import com.base.modules.pda.service.IWmPdaConfigService;
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
 * @Description: pda配置表
 * @Author: base-boot
 * @Date:   2022-04-27
 * @Version: V1.0
 */
@Api(tags="pda配置表")
@RestController
@RequestMapping("/pda/wmPdaConfig")
@Slf4j
public class WmPdaConfigController extends BaseController<WmPdaConfig, IWmPdaConfigService> {
	@Autowired
	private IWmPdaConfigService wmPdaConfigService;

	/**
	 * 分页列表查询
	 *
	 * @param wmPdaConfig
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "pda配置表-分页列表查询")
	@ApiOperation(value="pda配置表-分页列表查询", notes="pda配置表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmPdaConfig wmPdaConfig,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmPdaConfig> queryWrapper = QueryGenerator.initQueryWrapper(wmPdaConfig, req.getParameterMap());
		Page<WmPdaConfig> page = new Page<WmPdaConfig>(pageNo, pageSize);
		IPage<WmPdaConfig> pageList = wmPdaConfigService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmPdaConfig
	 * @return
	 */
	@AutoLog(value = "pda配置表-添加")
	@ApiOperation(value="pda配置表-添加", notes="pda配置表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmPdaConfig wmPdaConfig) {
		wmPdaConfigService.save(wmPdaConfig);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmPdaConfig
	 * @return
	 */
	@AutoLog(value = "pda配置表-编辑")
	@ApiOperation(value="pda配置表-编辑", notes="pda配置表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmPdaConfig wmPdaConfig) {
		wmPdaConfigService.updateById(wmPdaConfig);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "pda配置表-通过id删除")
	@ApiOperation(value="pda配置表-通过id删除", notes="pda配置表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmPdaConfigService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "pda配置表-批量删除")
	@ApiOperation(value="pda配置表-批量删除", notes="pda配置表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmPdaConfigService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "pda配置表-通过id查询")
	@ApiOperation(value="pda配置表-通过id查询", notes="pda配置表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmPdaConfig wmPdaConfig = wmPdaConfigService.getById(id);
		if(wmPdaConfig==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmPdaConfig);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmPdaConfig
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmPdaConfig wmPdaConfig) {
        return super.exportXls(request, wmPdaConfig, WmPdaConfig.class, "pda配置表");
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
        return super.importExcel(request, response, WmPdaConfig.class);
    }

}
