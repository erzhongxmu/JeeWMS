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
import com.base.modules.biconf.biapi.dto.VisualDTO;
import com.base.modules.biconf.biapi.dto.apiFile;
import com.base.modules.biconf.entity.BiVisual;
import com.base.modules.biconf.entity.BiVisualCategory;
import com.base.modules.biconf.entity.BiVisualConfig;
import com.base.modules.biconf.service.IBiVisualCategoryService;
import com.base.modules.biconf.service.IBiVisualConfigService;
import com.base.modules.biconf.service.IBiVisualMapService;
import com.base.modules.biconf.service.IBiVisualService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 可视化表 控制器
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping(LauncherConstant.APPLICATION_VISUAL_NAME + "/visual")
@Api(value = "可视化表", tags = "可视化数据接口")
public class VisualController{

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
	@ApiOperation(value = "详情", notes = "传入visual")
	public R<VisualDTO> detail(@RequestParam String id) {
		VisualDTO detail = new VisualDTO();
		BiVisual biVisual = this.biVisualService.getById(id);
		detail.setVisual(biVisual);
		try{
			QueryWrapper<BiVisualConfig> queryWrapper = new QueryWrapper<>();
			Page<BiVisualConfig> page = new Page<BiVisualConfig>(1, 10);
			queryWrapper.eq("visual_Id",id);

			IPage<BiVisualConfig> pageList = biVisualConfigService.page(page, queryWrapper);
			detail.setConfig(pageList.getRecords().get(0));
		}catch (Exception e){

		}

		return R.data(detail);
	}

	/**
	 * 分页 可视化表
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入visual")
	public R<IPage<BiVisual>> list(BiVisual visual, Query query,
								   @RequestParam(name="current", defaultValue="1") Integer current,
								   @RequestParam(name="size", defaultValue="10") Integer size,
								   HttpServletRequest req) {
 		QueryWrapper<BiVisual> queryWrapper = QueryGenerator.initQueryWrapper(visual, req.getParameterMap());
		Page<BiVisual> page = new Page<BiVisual>(current, size);
		IPage<BiVisual> pageList = biVisualService.page(page, queryWrapper);
 		return R.data(pageList);
	}

	/**
	 * 新增 可视化表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入visual")
	public R save(@Valid @RequestBody VisualDTO visual) {
		BiVisual biVisual = visual.getVisual();
		this.biVisualService.save(biVisual);
		int bivid = biVisual.getId();
		BiVisualConfig biVisualConfig = visual.getConfig();
		biVisualConfig.setVisualId(Integer.toString(bivid));
		this.biVisualConfigService.save(biVisualConfig);
		return R.data(bivid);

	}

	/**
	 * 修改 可视化表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入visual")
	public R update(@Valid @RequestBody VisualDTO visual) {
		this.biVisualService.updateById(visual.getVisual());
		return R.status(this.biVisualConfigService.updateById(visual.getConfig()));
	}


	/**
	 * 删除 可视化表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {

 		return R.status(this.biVisualService.removeByIds(Arrays.asList(ids.split(","))) );
	}

	/**
	 * 复制 可视化表
	 */
	@PostMapping("/copy")
	@ApiOperation(value = "复制", notes = "传入id")
	public R<String> copy(@ApiParam(value = "主键集合", required = true) @RequestParam String id) {
		BiVisual biVisual = this.biVisualService.getById(id);
 		biVisual.setId(null);
 		this.biVisualService.save(biVisual);
 		int newid = biVisual.getId();
 		QueryWrapper<BiVisualConfig> queryWrapper = new QueryWrapper<>();
		Page<BiVisualConfig> page = new Page<BiVisualConfig>(1, 10);
		queryWrapper.eq("visualId",id);
		IPage<BiVisualConfig> pageList = biVisualConfigService.page(page, queryWrapper);
		BiVisualConfig biVisualConfig = pageList.getRecords().get(0);
		biVisualConfig.setId(null);
		biVisualConfig.setVisualId(id);
		this.biVisualConfigService.save(biVisualConfig);
		return R.data(Integer.toString(newid));
	}

	/**
	 * 获取分类
	 */
	@GetMapping("category")
	@ApiOperation(value = "获取类型")
	public R category() {
		List<BiVisualCategory> list = biVisualCategoryService.list();
		return R.data(list);
	}

	/**
	 * 上传文件
	 */
	@SneakyThrows
	@PostMapping("/put-file")
	@ApiOperation(value = "上传", notes = "传入文件")
	public R<apiFile> putFile(@ApiParam(value = "上传文件", required = true) @RequestParam MultipartFile file) {
		apiFile apiFile =  new apiFile();
//		String url = OssBootUtil.upload(file,"bi");
//		apiFile.setLink(url);
		return R.data(apiFile);
	}

}
