package com.base.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.modules.app.service.IWmsAppRoleService;
import com.base.modules.mesapp.entity.WmsAppRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * @Description: 主数据—APP角色
 * @Author: base-boot
 * @Date:   2020-10-14
 * @Version: V1.0
 */
@Api(tags="主数据—APP角色")
@RestController
@RequestMapping("/wmsapp/wmsAppRole")
@Slf4j
public class WmsAppRoleController extends JeecgController<WmsAppRole, IWmsAppRoleService> {
	@Autowired
	private IWmsAppRoleService wmsAppRoleService;

	/**
	 * 分页列表查询
	 *
	 * @param mesAppRole
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "主数据—APP角色-分页列表查询")
	@ApiOperation(value="主数据—APP角色-分页列表查询", notes="主数据—APP角色-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsAppRole mesAppRole,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsAppRole> queryWrapper = QueryGenerator.initQueryWrapper(mesAppRole, req.getParameterMap());
		Page<WmsAppRole> page = new Page<WmsAppRole>(pageNo, pageSize);
		IPage<WmsAppRole> pageList = wmsAppRoleService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param mesAppRole
	 * @return
	 */
	@AutoLog(value = "主数据—APP角色-添加")
	@ApiOperation(value="主数据—APP角色-添加", notes="主数据—APP角色-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsAppRole mesAppRole) {
		wmsAppRoleService.save(mesAppRole);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param mesAppRole
	 * @return
	 */
	@AutoLog(value = "主数据—APP角色-编辑")
	@ApiOperation(value="主数据—APP角色-编辑", notes="主数据—APP角色-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsAppRole mesAppRole) {
		wmsAppRoleService.updateById(mesAppRole);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "主数据—APP角色-通过id删除")
	@ApiOperation(value="主数据—APP角色-通过id删除", notes="主数据—APP角色-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsAppRoleService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "主数据—APP角色-批量删除")
	@ApiOperation(value="主数据—APP角色-批量删除", notes="主数据—APP角色-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsAppRoleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "主数据—APP角色-通过id查询")
	@ApiOperation(value="主数据—APP角色-通过id查询", notes="主数据—APP角色-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsAppRole mesAppRole = wmsAppRoleService.getById(id);
		if(mesAppRole==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mesAppRole);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mesAppRole
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsAppRole mesAppRole) {
        return super.exportXls(request, mesAppRole, WmsAppRole.class, "主数据—APP角色");
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
        return super.importExcel(request, response, WmsAppRole.class);
    }

}
