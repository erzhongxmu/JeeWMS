package com.base.modules.jeeerp.controller;

import java.math.BigDecimal;
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
import com.base.modules.jeeerp.entity.BusiOmTrace;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeeerp.entity.BusiPaymentReceived;
import com.base.modules.jeeerp.entity.BusiPo;
import com.base.modules.jeeerp.service.IBusiOmTraceService;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.service.IMdCusService;
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
 * @Description: busi_om_trace
 * @Author: base-boot
 * @Date:   2024-03-12
 * @Version: V1.0
 */
@Api(tags="busi_om_trace")
@RestController
@RequestMapping("/jeeerp/busiOmTrace")
@Slf4j
public class BusiOmTraceController extends BaseController<BusiOmTrace, IBusiOmTraceService> {
	@Autowired
	private IBusiOmTraceService busiOmTraceService;

	 @Autowired
	 private IMdCusService mdCusService;
	/**
	 * 分页列表查询
	 *
	 * @param busiOmTrace
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "busi_om_trace-分页列表查询")
	@ApiOperation(value="busi_om_trace-分页列表查询", notes="busi_om_trace-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BusiOmTrace busiOmTrace,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BusiOmTrace> queryWrapper = QueryGenerator.initQueryWrapper(busiOmTrace, req.getParameterMap());
		Page<BusiOmTrace> page = new Page<BusiOmTrace>(pageNo, pageSize);
		IPage<BusiOmTrace> pageList = busiOmTraceService.page(page, queryWrapper);
		for (BusiOmTrace record : pageList.getRecords()) {
			try {
				QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
				MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,record.getQuery04());
				MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
				record.setQuery04(one2.getZhongWenQch());
			}catch (Exception e){

			}
		}
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param busiOmTrace
	 * @return
	 */
	@AutoLog(value = "busi_om_trace-添加")
	@ApiOperation(value="busi_om_trace-添加", notes="busi_om_trace-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BusiOmTrace busiOmTrace) {
		busiOmTraceService.save(busiOmTrace);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param busiOmTrace
	 * @return
	 */
	@AutoLog(value = "busi_om_trace-编辑")
	@ApiOperation(value="busi_om_trace-编辑", notes="busi_om_trace-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BusiOmTrace busiOmTrace) {
		busiOmTraceService.updateById(busiOmTrace);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_om_trace-通过id删除")
	@ApiOperation(value="busi_om_trace-通过id删除", notes="busi_om_trace-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		busiOmTraceService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "busi_om_trace-批量删除")
	@ApiOperation(value="busi_om_trace-批量删除", notes="busi_om_trace-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.busiOmTraceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "busi_om_trace-通过id查询")
	@ApiOperation(value="busi_om_trace-通过id查询", notes="busi_om_trace-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BusiOmTrace busiOmTrace = busiOmTraceService.getById(id);
		if(busiOmTrace==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(busiOmTrace);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param busiOmTrace
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BusiOmTrace busiOmTrace) {
        return super.exportXls(request, busiOmTrace, BusiOmTrace.class, "busi_om_trace");
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
        return super.importExcel(request, response, BusiOmTrace.class);
    }

}
