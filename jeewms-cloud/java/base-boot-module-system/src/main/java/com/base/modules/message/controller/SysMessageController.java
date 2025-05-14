package com.base.modules.message.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.dto.message.TemplateMessageDTO;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.common.system.vo.LoginUser;
import com.base.modules.message.entity.SysMessage;
import com.base.modules.message.service.ISysMessageService;
import com.base.modules.system.entity.SysUser;
import com.base.modules.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 消息
 * @Author: base-boot
 * @date: 2019-04-09
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/sys/message/sysMessage")
public class SysMessageController extends BaseController<SysMessage, ISysMessageService> {
	@Autowired
	private ISysMessageService sysMessageService;

	@Autowired
	private ISysBaseAPI sysBaseAPI;
	@Autowired
	private ISysUserService sysUserService;
	/**
	 * 分页列表查询
	 *
	 * @param sysMessage
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SysMessage sysMessage, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
		QueryWrapper<SysMessage> queryWrapper = QueryGenerator.initQueryWrapper(sysMessage, req.getParameterMap());
		Page<SysMessage> page = new Page<SysMessage>(pageNo, pageSize);
		IPage<SysMessage> pageList = sysMessageService.page(page, queryWrapper);
        return Result.ok(pageList);
	}

	/**
	 * 添加
	 *
	 * @param sysMessage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SysMessage sysMessage) {
		sysMessageService.save(sysMessage);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param sysMessage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SysMessage sysMessage) {
		sysMessageService.updateById(sysMessage);
        return Result.ok("修改成功!");

	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		sysMessageService.removeById(id);
        return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {

		this.sysMessageService.removeByIds(Arrays.asList(ids.split(",")));
	    return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
		SysMessage sysMessage = sysMessageService.getById(id);
		return Result.ok(sysMessage);
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 */
	@GetMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, SysMessage sysMessage) {
		return super.exportXls(request,sysMessage,SysMessage.class, "推送消息模板");
	}

	/**
	 * excel导入
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/importExcel")
	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		return super.importExcel(request, response, SysMessage.class);
	}

	/**
	 * 批量通过模板发送消息
	 * @param
	 */
	@AutoLog(value = "批量通过模板发送消息")
	@ApiOperation(value="批量通过模板发送消息", notes="批量通过模板发送消息")
	@GetMapping("/sendTemplateAnnouncement2")
	public Result<?> sendTemplateAnnouncement2(@RequestParam(name="toUser", required = true) String toUser,
											   @RequestParam(name="templateCode", required = true) String templateCode){
		TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO();
		String str = "";
		if("QTYG".equals(toUser)){
//            SysUser
			QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
			List<SysUser> list = sysUserService.list(queryWrapper);
			for (SysUser sysUser : list) {
				if(str == ""){
					str = sysUser.getUsername();
				}else {
					str = str + "," + sysUser.getUsername();
				}
			}
		}else {
			String[] split = toUser.split(",");
			for (String s : split) {
				if(str == ""){
					str = s;
				}else {
					str = str + "," + s;
				}
			}
		}
		if(str == ""){
			return Result.error("未查询到员工，请确认");
		}
		LoginUser login = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		templateMessageDTO.setFromUser(login.getRealname());
		templateMessageDTO.setToUser(str);
		templateMessageDTO.setTemplateCode(templateCode);
		sysBaseAPI.sendTemplateAnnouncement(templateMessageDTO);
		return Result.ok("发送成功");
	}

}
