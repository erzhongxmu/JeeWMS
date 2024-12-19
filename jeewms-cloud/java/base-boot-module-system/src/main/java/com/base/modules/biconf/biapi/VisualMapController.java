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

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.biconf.biapi.dto.LauncherConstant;
import com.base.modules.biconf.biapi.dto.R;
import com.base.modules.biconf.entity.BiVisualMap;
import com.base.modules.biconf.service.IBiVisualCategoryService;
import com.base.modules.biconf.service.IBiVisualConfigService;
import com.base.modules.biconf.service.IBiVisualMapService;
import com.base.modules.biconf.service.IBiVisualService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;

/**
 * 可视化地图配置表 控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/map")
@Api(value = "可视化地图配置表", tags = "可视化地图配置接口")
public class VisualMapController   {
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
	@ApiOperation(value = "详情", notes = "传入visualMap")
	public R<BiVisualMap> detail(BiVisualMap visualMap) {
		BiVisualMap detail = biVisualMapService.getById(visualMap.getId());
		return R.data(detail);
	}

	/**
	 * 数据详情
	 */
//	@GetMapping("/data")
//	@ApiOperation(value = "数据详情", notes = "传入id")
//	public Map<String, Object> data(Long id) {
//		BiVisualMap detail = biVisualMapService.getById(id);
//		Map<String,Object>  map =
//		return  (Map)getInstance().readValue(detail.getData(), Map.class);
//	}
//
	/**
	 * 分页列表查询
	 *
	 * @param biVisualMap
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "地图主数据-分页列表查询")
	@ApiOperation(value="地图主数据-分页列表查询", notes="地图主数据-分页列表查询")
	@GetMapping(value = "/list")
	public R<?> queryPageList(BiVisualMap biVisualMap,
							  @RequestParam(name="current", defaultValue="1") Integer current,
							  @RequestParam(name="size", defaultValue="10") Integer size,
							  HttpServletRequest req) {
		QueryWrapper<BiVisualMap> queryWrapper = QueryGenerator.initQueryWrapper(biVisualMap, req.getParameterMap());
		Page<BiVisualMap> page = new Page<BiVisualMap>(current, size);
		IPage<BiVisualMap> pageList = biVisualMapService.page(page, queryWrapper);
		return R.data(pageList);
	}
	/**
	 * 新增 可视化地图配置表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入visualMap")
	public R save(@Valid @RequestBody BiVisualMap visualMap) {
		return R.status(biVisualMapService.save(visualMap));
	}

	/**
	 * 修改 可视化地图配置表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入visualMap")
	public R update(@Valid @RequestBody BiVisualMap visualMap) {
		return R.status(biVisualMapService.updateById(visualMap));
	}
//
//
	/**
	 * 删除 可视化地图配置表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(this.biVisualMapService.removeByIds(Arrays.asList(ids.split(","))));
 	}


}
