package com.zzjee.api;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ResourceUtil;

import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取和删除token的请求地址， 
 * 在Restful设计中其实就对应着登录和退出登录的资源映射
 * 
 * @author scott
 * @date 2015/7/30.
 */
@Controller
@RequestMapping("/tokens")
public class TokenController {
	private static final Logger logger = Logger.getLogger(TokenController.class);
	@Autowired
	private UserService userService;

	@Autowired
    SystemService systemService;


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		logger.info("获取TOKEN[{}]" + username);
		ResultDO D0 = new  ResultDO();

		// 验证
		if (StringUtils.isEmpty(username)) {
			D0.setErrorMsg("用户账号不能为空!");
			D0.setOK(false);
//			return new ResponseEntity("用户账号不能为空!", HttpStatus.FORBIDDEN);
		}
		// 验证
		if (StringUtils.isEmpty(username)) {
			D0.setErrorMsg("用户密码不能为空!");
			D0.setOK(false);
//			return new ResponseEntity("用户密码不能为空!", HttpStatus.FORBIDDEN);
		}
//		Assert.notNull(username, "username can not be empty");
//		Assert.notNull(password, "password can not be empty");

		TSUser user = userService.checkUserExits(username, password);
		if (user == null) {
			// 提示用户名或密码错误
			D0.setErrorMsg("用户账号密码错误!");
			D0.setOK(false);
			logger.info("获取TOKEN,账号密码错误[{}]" + username);
//			return new ResponseEntity("用户账号密码错误!", HttpStatus.FORBIDDEN);
		}else{
			Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
			StringBuilder hqlsb1 = new StringBuilder("select distinct f from TSFunction f,TSRoleFunction rf,TSRoleUser ru  ").append("where ru.TSRole.id=rf.TSRole.id and rf.TSFunction.id=f.id and ru.TSUser.id=? ");
			//-------author: zhoujf---start---date:20160923----for:组织机构角色赋权不起作用问题
			StringBuilder hqlsb2 = new StringBuilder("select distinct c from TSFunction c,TSRoleFunction rf,TSRoleOrg b,TSUserOrg a ")
					.append("where a.tsDepart.id=b.tsDepart.id and b.tsRole.id=rf.TSRole.id and rf.TSFunction.id=c.id and a.tsUser.id=?");
			//-------author: zhoujf---end---date:20160923----for:组织机构角色赋权不起作用问题
			//update-begin-Author:LiShaoQing Date:20171023 for:TASK #2383【功能】权限组集成，用户登录权限过滤--------
//			StringBuilder hqlsb3 = new StringBuilder("select distinct c from TSFunction c,TSFunctionGroupRelEntity g,TSFunctionGroupUserEntity u")
//					.append(" where g.tsFunctionGroup.id=u.groupId and c.id=g.tsFunction.id and u.userId=?");
//			//TODO hql执行效率慢 为耗时最多地方
			//-------author: zhangliang---start---date:20170725----for:TASK #2116 【性能问题】优化登录逻辑
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		log.info("================================开始时间:"+sdf.format(new Date())+"==============================");
			long start = System.currentTimeMillis();
			List<MenuEntity> listreturn = new ArrayList<MenuEntity>();
			List<TSFunction> list1 = systemService.findHql(hqlsb1.toString(), user.getId());
			List<TSFunction> list2 = systemService.findHql(hqlsb2.toString(), user.getId());
//			List<TSFunction> list3 = systemService.findHql(hqlsb3.toString(), user.getUserName());
			long end = System.currentTimeMillis();
//		log.info("================================结束时间:"+sdf.format(new Date())+"==============================");
//		log.info("================================耗时:"+(end-start)+"ms==============================");
			//-------author: zhangliang---end---date:20170725----for:TASK #2116 【性能问题】优化登录逻辑
			for (TSFunction function : list1) {
				MenuEntity  fh5 = new MenuEntity();
				fh5.setMenuImg(function.getTSIcon().getIconPath());
				fh5.setMenuText(function.getFunctionName());
				fh5.setMenuSrc(function.getFunctionUrl());
				listreturn.add(fh5);
			}
			for (TSFunction function : list2) {
				MenuEntity  fh5 = new MenuEntity();
				fh5.setMenuImg(function.getTSIcon().getIconPath());
				fh5.setMenuText(function.getFunctionName());
				fh5.setMenuSrc(function.getFunctionUrl());
				listreturn.add(fh5);
			}
//			for (TSFunction function : list3) {
//				MenuEntity  fh5 = new MenuEntity();
//				fh5.setMenuImg(function.getTSIcon().getIconPath());
//				fh5.setMenuText(function.getFunctionName());
//				fh5.setMenuSrc(function.getFunctionUrl());
//				listreturn.add(fh5);
//			}

			D0.setObj(listreturn);
			D0.setOK(true);
		}
		// 生成一个token，保存用户登录状态
		return new ResponseEntity(D0, HttpStatus.OK);
	}


	// 文件上传
	@RequestMapping(value = "/saveImage", method = RequestMethod.PUT)
	@ResponseBody
	public  ResultDO<?> saveImage(HttpServletRequest request) throws IllegalStateException, IOException {
		ResultDO D0 = new  ResultDO();

		String fileName = request.getParameter("imageFileName");
		String fileAddr = request.getParameter("fileAddr");
		InputStream ins = request.getInputStream();
		try {
			fileAddr = ResourceUtil.getConfigByName("webUploadpath") + File.separator + fileAddr;
			File f = new File(fileAddr);
			if (!f.exists()) {
				f.mkdirs();
			}
			OutputStream os = new FileOutputStream(fileAddr + File.separator + fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		D0.setOK(true);
		return D0;
	}

}
