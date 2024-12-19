package com.base.modules.approle.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.approle.entity.MesAppRole;
import com.base.modules.approle.service.IMesAppRoleService;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: APP角色
 * @Author: jeecg-boot
 * @Date:   2021-11-10
 * @Version: V1.0
 */
@Api(tags="APP角色")
@RestController
@RequestMapping("/jeewms/mesAppRole")
@Slf4j
public class MesAppRoleController extends BaseController<MesAppRole, IMesAppRoleService> {
	@Autowired
	private IMesAppRoleService mesAppRoleService;

	/**
	 * 分页列表查询
	 *
	 * @param mesAppRole
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "APP角色-分页列表查询")
	@ApiOperation(value="APP角色-分页列表查询", notes="APP角色-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MesAppRole mesAppRole,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MesAppRole> queryWrapper = QueryGenerator.initQueryWrapper(mesAppRole, req.getParameterMap());
		Page<MesAppRole> page = new Page<MesAppRole>(pageNo, pageSize);
		IPage<MesAppRole> pageList = mesAppRoleService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param mesAppRole
	 * @return
	 */
	@AutoLog(value = "APP角色-添加")
	@ApiOperation(value="APP角色-添加", notes="APP角色-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MesAppRole mesAppRole) {
		mesAppRoleService.save(mesAppRole);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param mesAppRole
	 * @return
	 */
	@AutoLog(value = "APP角色-编辑")
	@ApiOperation(value="APP角色-编辑", notes="APP角色-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MesAppRole mesAppRole) {
		mesAppRoleService.updateById(mesAppRole);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "APP角色-通过id删除")
	@ApiOperation(value="APP角色-通过id删除", notes="APP角色-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mesAppRoleService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "APP角色-批量删除")
	@ApiOperation(value="APP角色-批量删除", notes="APP角色-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mesAppRoleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "APP角色-通过id查询")
	@ApiOperation(value="APP角色-通过id查询", notes="APP角色-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MesAppRole mesAppRole = mesAppRoleService.getById(id);
		if(mesAppRole==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(mesAppRole);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mesAppRole
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MesAppRole mesAppRole) {
        return super.exportXls(request, mesAppRole, MesAppRole.class, "APP角色");
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
        return super.importExcel(request, response, MesAppRole.class);
    }

}
