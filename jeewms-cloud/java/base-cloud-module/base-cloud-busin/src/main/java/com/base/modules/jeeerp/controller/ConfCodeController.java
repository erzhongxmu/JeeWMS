package com.base.modules.jeeerp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;

import com.base.modules.jeeerp.entity.ConfCode;
import com.base.modules.jeeerp.service.IConfCodeService;
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
 * @Description: conf_code
 * @Author: base-boot
 * @Date:   2022-04-06
 * @Version: V1.0
 */
@Api(tags="conf_code")
@RestController
@RequestMapping("/jeeerp/confCode")
@Slf4j
public class ConfCodeController extends BaseController<ConfCode, IConfCodeService> {
	@Autowired
	private IConfCodeService confCodeService;

	/**
	 * 分页列表查询
	 *
	 * @param confCode
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "conf_code-分页列表查询")
	@ApiOperation(value="conf_code-分页列表查询", notes="conf_code-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ConfCode confCode,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
		QueryWrapper<ConfCode> queryWrapper = QueryGenerator.initQueryWrapper(confCode, req.getParameterMap());
		Page<ConfCode> page = new Page<ConfCode>(pageNo, pageSize);
		IPage<ConfCode> pageList = confCodeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param confCode
	 * @return
	 */
	@AutoLog(value = "conf_code-添加")
	@ApiOperation(value="conf_code-添加", notes="conf_code-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ConfCode confCode) {
		QueryWrapper<ConfCode> qw = new QueryWrapper<>();
		qw.eq("table_name",confCode.getTableName());
		qw.eq("code_type",confCode.getCodeType());
		ConfCode obj = confCodeService.getOne(qw, false);
		if(obj!=null){
			return Result.error("失败");
		}
		confCodeService.save(confCode);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param confCode
	 * @return
	 */
	@AutoLog(value = "conf_code-编辑")
	@ApiOperation(value="conf_code-编辑", notes="conf_code-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ConfCode confCode) {
		QueryWrapper<ConfCode> qw = new QueryWrapper<>();
		qw.eq("table_name",confCode.getTableName());
		qw.eq("code_type",confCode.getCodeType());
		qw.ne("id",confCode.getId());
		ConfCode obj = confCodeService.getOne(qw, false);
		if(obj!=null){
			return Result.error("失败");
		}
		confCodeService.updateById(confCode);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "conf_code-通过id删除")
	@ApiOperation(value="conf_code-通过id删除", notes="conf_code-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		confCodeService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "conf_code-批量删除")
	@ApiOperation(value="conf_code-批量删除", notes="conf_code-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.confCodeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "conf_code-通过id查询")
	@ApiOperation(value="conf_code-通过id查询", notes="conf_code-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ConfCode confCode = confCodeService.getById(id);
		if(confCode==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(confCode);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param confCode
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ConfCode confCode) {
        return super.exportXls(request, confCode, ConfCode.class, "conf_code");
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
        return super.importExcel(request, response, ConfCode.class);
    }

}
