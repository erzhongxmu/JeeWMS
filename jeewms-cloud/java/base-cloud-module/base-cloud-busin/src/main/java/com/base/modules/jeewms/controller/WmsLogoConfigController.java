package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeewms.entity.WmsComType;
import com.base.modules.jeewms.entity.WmsLogoConfig;
import com.base.modules.jeewms.service.IWmsLogoConfigService;
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
 * @Description: LOGO 维护基础配置
 * @Author: base-boot
 * @Date:   2022-05-16
 * @Version: V1.0
 */
@Api(tags="LOGO 维护基础配置")
@RestController
@RequestMapping("/jeewms/wmsLogoConfig")
@Slf4j
public class WmsLogoConfigController extends BaseController<WmsLogoConfig, IWmsLogoConfigService> {
	@Autowired
	private IWmsLogoConfigService wmsLogoConfigService;

	/**
	 * 分页列表查询
	 *
	 * @param wmsLogoConfig
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "LOGO 维护基础配置-分页列表查询")
	@ApiOperation(value="LOGO 维护基础配置-分页列表查询", notes="LOGO 维护基础配置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsLogoConfig wmsLogoConfig,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsLogoConfig> queryWrapper = QueryGenerator.initQueryWrapper(wmsLogoConfig, req.getParameterMap());
		Page<WmsLogoConfig> page = new Page<WmsLogoConfig>(pageNo, pageSize);
		IPage<WmsLogoConfig> pageList = wmsLogoConfigService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmsLogoConfig
	 * @return
	 */
	@AutoLog(value = "LOGO 维护基础配置-添加")
	@ApiOperation(value="LOGO 维护基础配置-添加", notes="LOGO 维护基础配置-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsLogoConfig wmsLogoConfig) {
		if (wmsLogoConfigService.lambdaQuery()
				.eq(WmsLogoConfig::getLogoCode,wmsLogoConfig.getLogoCode()).count()>0){
			return Result.error("编码已存在，请重新输入");
		}else {
			wmsLogoConfigService.save(wmsLogoConfig);
		}
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmsLogoConfig
	 * @return
	 */
	@AutoLog(value = "LOGO 维护基础配置-编辑")
	@ApiOperation(value="LOGO 维护基础配置-编辑", notes="LOGO 维护基础配置-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsLogoConfig wmsLogoConfig) {
		WmsLogoConfig wmsLogoConfigs = wmsLogoConfigService.lambdaQuery().eq(WmsLogoConfig::getLogoCode, wmsLogoConfig.getLogoCode())
				.ne(WmsLogoConfig::getId, wmsLogoConfig.getId()).one();
		if (wmsLogoConfigs != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			wmsLogoConfigService.updateById(wmsLogoConfig);
		}
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "LOGO 维护基础配置-通过id删除")
	@ApiOperation(value="LOGO 维护基础配置-通过id删除", notes="LOGO 维护基础配置-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsLogoConfigService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "LOGO 维护基础配置-批量删除")
	@ApiOperation(value="LOGO 维护基础配置-批量删除", notes="LOGO 维护基础配置-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsLogoConfigService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "LOGO 维护基础配置-通过id查询")
	@ApiOperation(value="LOGO 维护基础配置-通过id查询", notes="LOGO 维护基础配置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsLogoConfig wmsLogoConfig = wmsLogoConfigService.getById(id);
		if(wmsLogoConfig==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsLogoConfig);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsLogoConfig
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsLogoConfig wmsLogoConfig) {
        return super.exportXls(request, wmsLogoConfig, WmsLogoConfig.class, "LOGO 维护基础配置");
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
        return super.importExcel(request, response, WmsLogoConfig.class);
    }

}
