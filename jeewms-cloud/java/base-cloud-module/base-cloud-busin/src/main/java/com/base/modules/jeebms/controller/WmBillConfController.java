package com.base.modules.jeebms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeebms.entity.WmBillConf;
import com.base.modules.jeebms.service.IWmBillConfService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import com.base.common.system.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

 /**
 * @Description: 账单基础信息配置
 * @Author: base-boot
 * @Date:   2021-12-26
 * @Version: V1.0
 */
@Api(tags="账单基础信息配置")
@RestController
@RequestMapping("/jeebms/wmBillConf")
@Slf4j
public class WmBillConfController extends BaseController<WmBillConf, IWmBillConfService> {
	@Autowired
	private IWmBillConfService wmBillConfService;

	/**
	 * 分页列表查询
	 *
	 * @param wmBillConf
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "账单基础信息配置-分页列表查询")
	@ApiOperation(value="账单基础信息配置-分页列表查询", notes="账单基础信息配置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmBillConf wmBillConf,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmBillConf> queryWrapper = QueryGenerator.initQueryWrapper(wmBillConf, req.getParameterMap());
		Page<WmBillConf> page = new Page<WmBillConf>(pageNo, pageSize);
		IPage<WmBillConf> pageList = wmBillConfService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmBillConf
	 * @return
	 */
	@AutoLog(value = "账单基础信息配置-添加")
	@ApiOperation(value="账单基础信息配置-添加", notes="账单基础信息配置-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmBillConf wmBillConf) {
		wmBillConfService.save(wmBillConf);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmBillConf
	 * @return
	 */
	@AutoLog(value = "账单基础信息配置-编辑")
	@ApiOperation(value="账单基础信息配置-编辑", notes="账单基础信息配置-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmBillConf wmBillConf) {
		wmBillConfService.updateById(wmBillConf);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "账单基础信息配置-通过id删除")
	@ApiOperation(value="账单基础信息配置-通过id删除", notes="账单基础信息配置-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmBillConfService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "账单基础信息配置-批量删除")
	@ApiOperation(value="账单基础信息配置-批量删除", notes="账单基础信息配置-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmBillConfService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "账单基础信息配置-通过id查询")
	@ApiOperation(value="账单基础信息配置-通过id查询", notes="账单基础信息配置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmBillConf wmBillConf = wmBillConfService.getById(id);
		if(wmBillConf==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmBillConf);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmBillConf
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmBillConf wmBillConf) {
        return super.exportXls(request, wmBillConf, WmBillConf.class, "账单基础信息配置");
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
        return super.importExcel(request, response, WmBillConf.class);
    }

}
