/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package com.base.modules.biconf.biapi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.biconf.biapi.dto.LauncherConstant;
import com.base.modules.biconf.biapi.dto.R;
import com.base.modules.biconf.entity.BiVisualCategory;
import com.base.modules.biconf.service.IBiVisualCategoryService;
import com.base.modules.biconf.service.IBiVisualConfigService;
import com.base.modules.biconf.service.IBiVisualMapService;
import com.base.modules.biconf.service.IBiVisualService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 可视化分类表 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/category")
@Api(value = "可视化分类表", tags = "可视化分类接口")
public class VisualCategoryController  {
	@Autowired
	private IBiVisualMapService biVisualMapService;
	@Autowired
	private IBiVisualService biVisualService;
	@Autowired
	private IBiVisualConfigService biVisualConfigService;
	@Autowired
	private IBiVisualCategoryService biVisualCategoryService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入visualCategory")
	public R<BiVisualCategory> detail(BiVisualCategory visualCategory) {
		BiVisualCategory detail = biVisualCategoryService.getById(visualCategory.getId());
		return R.data(detail);
	}

	/**
	 * 列表 可视化分类表
	 */
	@GetMapping("/list")
	@ApiOperation(value = "列表", notes = "传入visualCategory")
	public R<List<BiVisualCategory>> list(BiVisualCategory biVisualCategory,
										  @RequestParam(name="current", defaultValue="1") Integer current,
										  @RequestParam(name="size", defaultValue="10") Integer size,  HttpServletRequest req) {
		QueryWrapper<BiVisualCategory> queryWrapper = QueryGenerator.initQueryWrapper(biVisualCategory, req.getParameterMap());
		Page<BiVisualCategory> page = new Page<BiVisualCategory>(current, size);
		IPage<BiVisualCategory> pageList = biVisualCategoryService.page(page, queryWrapper);
		return R.data(pageList.getRecords());
	}
//
//	/**
//	 * 分页 可视化分类表
//	 */
//	@GetMapping("/page")
//	@ApiOperation(value = "分页", notes = "传入visualCategory")
//	public R<IPage<VisualCategory>> page(VisualCategory visualCategory, Query query) {
//		IPage<VisualCategory> pages = visualCategoryService.page(Condition.getPage(query), Condition.getQueryWrapper(visualCategory));
//		return R.data(pages);
//	}
//
	/**
	 * 新增 可视化分类表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入visualCategory")
	public R save(@Valid @RequestBody BiVisualCategory visualCategory) {
		visualCategory.setIsDeleted(0);
		return R.status(biVisualCategoryService.save(visualCategory));
	}
//
	/**
	 * 修改 可视化分类表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入visualCategory")
	public R update(@Valid @RequestBody BiVisualCategory visualCategory) {
		return R.status(biVisualCategoryService.updateById(visualCategory));
	}
//
	/**
	 * 删除 可视化分类表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(biVisualCategoryService.removeByIds(Arrays.asList(ids.split(","))));
	}
//

}
