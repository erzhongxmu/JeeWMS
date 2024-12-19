package com.base.modules.biconf.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.modules.biconf.entity.BiVisualMap;
import com.base.modules.biconf.service.IBiVisualMapService;
import com.base.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 地图主数据
 * @Author: base-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Api(tags="地图主数据")
@RestController
@RequestMapping("/biconf/biVisualMap")
@Slf4j
public class BiVisualMapController extends JeecgController<BiVisualMap, IBiVisualMapService> {
	@Autowired
	private IBiVisualMapService biVisualMapService;

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
	public Result<?> queryPageList(BiVisualMap biVisualMap,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BiVisualMap> queryWrapper = QueryGenerator.initQueryWrapper(biVisualMap, req.getParameterMap());
		Page<BiVisualMap> page = new Page<BiVisualMap>(pageNo, pageSize);
		IPage<BiVisualMap> pageList = biVisualMapService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param biVisualMap
	 * @return
	 */
	@AutoLog(value = "地图主数据-添加")
	@ApiOperation(value="地图主数据-添加", notes="地图主数据-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BiVisualMap biVisualMap) {
		biVisualMapService.save(biVisualMap);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param biVisualMap
	 * @return
	 */
	@AutoLog(value = "地图主数据-编辑")
	@ApiOperation(value="地图主数据-编辑", notes="地图主数据-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BiVisualMap biVisualMap) {
		biVisualMapService.updateById(biVisualMap);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "地图主数据-通过id删除")
	@ApiOperation(value="地图主数据-通过id删除", notes="地图主数据-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		biVisualMapService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "地图主数据-批量删除")
	@ApiOperation(value="地图主数据-批量删除", notes="地图主数据-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.biVisualMapService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "地图主数据-通过id查询")
	@ApiOperation(value="地图主数据-通过id查询", notes="地图主数据-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BiVisualMap biVisualMap = biVisualMapService.getById(id);
		if(biVisualMap==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(biVisualMap);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param biVisualMap
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BiVisualMap biVisualMap) {
        return super.exportXls(request, biVisualMap, BiVisualMap.class, "地图主数据");
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
        return super.importExcel(request, response, BiVisualMap.class);
    }

}
