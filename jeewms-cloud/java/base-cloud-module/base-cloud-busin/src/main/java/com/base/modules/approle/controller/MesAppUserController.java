package com.base.modules.approle.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.approle.entity.MesAppUser;
import com.base.modules.approle.service.IMesAppUserService;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

 /**
 * @Description: APP用户对应角色
 * @Author: jeecg-boot
 * @Date:   2021-11-10
 * @Version: V1.0
 */
@Api(tags="APP用户对应角色")
@RestController
@RequestMapping("/jeewms/mesAppUser")
@Slf4j
public class MesAppUserController extends BaseController<MesAppUser, IMesAppUserService> {
	@Autowired
	private IMesAppUserService mesAppUserService;

	/**
	 * 分页列表查询
	 *
	 * @param mesAppUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "APP用户对应角色-分页列表查询")
	@ApiOperation(value="APP用户对应角色-分页列表查询", notes="APP用户对应角色-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MesAppUser mesAppUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MesAppUser> queryWrapper = QueryGenerator.initQueryWrapper(mesAppUser, req.getParameterMap());
		Page<MesAppUser> page = new Page<MesAppUser>(pageNo, pageSize);
		IPage<MesAppUser> pageList = mesAppUserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param mesAppUser
	 * @return
	 */
	@AutoLog(value = "APP用户对应角色-添加")
	@ApiOperation(value="APP用户对应角色-添加", notes="APP用户对应角色-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MesAppUser mesAppUser) {
		mesAppUserService.save(mesAppUser);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param mesAppUser
	 * @return
	 */
	@AutoLog(value = "APP用户对应角色-编辑")
	@ApiOperation(value="APP用户对应角色-编辑", notes="APP用户对应角色-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MesAppUser mesAppUser) {
		mesAppUserService.updateById(mesAppUser);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "APP用户对应角色-通过id删除")
	@ApiOperation(value="APP用户对应角色-通过id删除", notes="APP用户对应角色-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mesAppUserService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "APP用户对应角色-批量删除")
	@ApiOperation(value="APP用户对应角色-批量删除", notes="APP用户对应角色-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mesAppUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "APP用户对应角色-通过id查询")
	@ApiOperation(value="APP用户对应角色-通过id查询", notes="APP用户对应角色-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MesAppUser mesAppUser = mesAppUserService.getById(id);
		if(mesAppUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(mesAppUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mesAppUser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MesAppUser mesAppUser) {
        return super.exportXls(request, mesAppUser, MesAppUser.class, "APP用户对应角色");
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
        return super.importExcel(request, response, MesAppUser.class);
    }

}
