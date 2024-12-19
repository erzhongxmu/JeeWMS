package com.base.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.app.service.IWmsAppUserService;
import com.base.modules.mesapp.entity.WmsAppUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.base.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 主数据—用户功能
 * @Author: base-boot
 * @Date:   2020-10-14
 * @Version: V1.0
 */
@Api(tags="主数据—用户功能")
@RestController
@RequestMapping("/wmsapp/wmsAppUser")
@Slf4j
public class WmsAppUserController extends JeecgController<WmsAppUser, IWmsAppUserService> {
	@Autowired
	private IWmsAppUserService wmsAppUserService;

	/**
	 * 分页列表查询
	 *
	 * @param mesAppUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "主数据—用户功能-分页列表查询")
	@ApiOperation(value="主数据—用户功能-分页列表查询", notes="主数据—用户功能-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsAppUser mesAppUser,
	                               @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
	                               @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
	                               HttpServletRequest req) {
		QueryWrapper<WmsAppUser> queryWrapper = QueryGenerator.initQueryWrapper(mesAppUser, req.getParameterMap());
		Page<WmsAppUser> page = new Page<WmsAppUser>(pageNo, pageSize);
		IPage<WmsAppUser> pageList = wmsAppUserService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param mesAppUser
	 * @return
	 */
	@AutoLog(value = "主数据—用户功能-添加")
	@ApiOperation(value="主数据—用户功能-添加", notes="主数据—用户功能-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsAppUser mesAppUser) {
		wmsAppUserService.save(mesAppUser);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param mesAppUser
	 * @return
	 */
	@AutoLog(value = "主数据—用户功能-编辑")
	@ApiOperation(value="主数据—用户功能-编辑", notes="主数据—用户功能-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsAppUser mesAppUser) {
		wmsAppUserService.updateById(mesAppUser);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "主数据—用户功能-通过id删除")
	@ApiOperation(value="主数据—用户功能-通过id删除", notes="主数据—用户功能-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsAppUserService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "主数据—用户功能-批量删除")
	@ApiOperation(value="主数据—用户功能-批量删除", notes="主数据—用户功能-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsAppUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "主数据—用户功能-通过id查询")
	@ApiOperation(value="主数据—用户功能-通过id查询", notes="主数据—用户功能-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsAppUser mesAppUser = wmsAppUserService.getById(id);
		if(mesAppUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mesAppUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mesAppUser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsAppUser mesAppUser) {
        return super.exportXls(request, mesAppUser, WmsAppUser.class, "主数据—用户功能");
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
        return super.importExcel(request, response, WmsAppUser.class);
    }

}
