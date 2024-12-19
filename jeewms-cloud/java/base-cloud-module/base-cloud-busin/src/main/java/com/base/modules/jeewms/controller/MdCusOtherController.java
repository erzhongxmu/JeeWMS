package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.MdCusOther;
import com.base.modules.jeewms.service.IMdCusOtherService;

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
 * @Description: 第三方客户
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="第三方客户")
@RestController
@RequestMapping("/jeewms/mdCusOther")
@Slf4j
public class MdCusOtherController extends JeecgController<MdCusOther, IMdCusOtherService> {
	@Autowired
	private IMdCusOtherService mdCusOtherService;

	/**
	 * 分页列表查询
	 *
	 * @param mdCusOther
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "第三方客户-分页列表查询")
	@ApiOperation(value="第三方客户-分页列表查询", notes="第三方客户-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MdCusOther mdCusOther,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MdCusOther> queryWrapper = QueryGenerator.initQueryWrapper(mdCusOther, req.getParameterMap());
		Page<MdCusOther> page = new Page<MdCusOther>(pageNo, pageSize);
		IPage<MdCusOther> pageList = mdCusOtherService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param mdCusOther
	 * @return
	 */
	@AutoLog(value = "第三方客户-添加")
	@ApiOperation(value="第三方客户-添加", notes="第三方客户-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MdCusOther mdCusOther) {
		mdCusOtherService.save(mdCusOther);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param mdCusOther
	 * @return
	 */
	@AutoLog(value = "第三方客户-编辑")
	@ApiOperation(value="第三方客户-编辑", notes="第三方客户-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MdCusOther mdCusOther) {
		mdCusOtherService.updateById(mdCusOther);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "第三方客户-通过id删除")
	@ApiOperation(value="第三方客户-通过id删除", notes="第三方客户-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mdCusOtherService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "第三方客户-批量删除")
	@ApiOperation(value="第三方客户-批量删除", notes="第三方客户-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mdCusOtherService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "第三方客户-通过id查询")
	@ApiOperation(value="第三方客户-通过id查询", notes="第三方客户-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MdCusOther mdCusOther = mdCusOtherService.getById(id);
		if(mdCusOther==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mdCusOther);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mdCusOther
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdCusOther mdCusOther) {
        return super.exportXls(request, mdCusOther, MdCusOther.class, "第三方客户");
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
        return super.importExcel(request, response, MdCusOther.class);
    }

}
