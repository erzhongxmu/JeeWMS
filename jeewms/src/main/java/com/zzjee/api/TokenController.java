package com.zzjee.api;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.zzjee.conf.entity.FxjOtherLoginEntity;
import com.zzjee.conf.entity.WxConfigEntity;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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


	@ApiOperation(value = "获取token")
	@RequestMapping(value = "/tmslogin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> tmslogin(@ApiParam(name = "username", value = "用户账号", required = true)@RequestParam String username,@ApiParam(name = "password", value = "用户密码", required = true) @RequestParam String password) {
		ResultDO D0 = new  ResultDO();
		logger.info("获取TOKEN[{}]" + username);
		// 验证
		if (StringUtils.isEmpty(username)) {
			D0.setOK(false);
			D0.setErrorMsg("用户账号不能为空!");
		}
		// 验证
		if (StringUtils.isEmpty(username)) {
			D0.setOK(false);
			D0.setErrorMsg("用户密码不能为空!");
		}

		TSUser user = userService.checkUserExits(username, password);
		if (user == null) {
			logger.info("获取TOKEN,户账号密码错误[{}]" + username);
			D0.setOK(false);
			D0.setErrorMsg("用户账号密码错误!");
		}else{
			String token = user.getMobilePhone();
			try{
					D0.setErrorCode("V2.0");
					D0.setErrorMsg("http");
//				}
			}catch (Exception e){

			}

			D0.setObj(token);
			D0.setOK(true);
		}

		// 生成一个token，保存用户登录状态
		return new ResponseEntity(D0, HttpStatus.OK);
	}



	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody TSBaseUser tsBaseUser, HttpServletRequest request) {
		logger.info("获取TOKEN[{}]" + tsBaseUser.getUserName());
		ResultDO D0 = new  ResultDO();

		// 验证
		if (StringUtils.isEmpty(tsBaseUser.getUserName())) {
			D0.setErrorMsg("用户账号不能为空!");
			D0.setOK(false);
			return new ResponseEntity("用户账号不能为空!", HttpStatus.OK);
		}
		// 验证
		if (StringUtils.isEmpty(tsBaseUser.getUserName())) {
			D0.setErrorMsg("用户密码不能为空!");
			D0.setOK(false);
			return new ResponseEntity("用户密码不能为空!", HttpStatus.OK);
		}
		TSUser user = userService.checkUserExits(tsBaseUser.getUserName(), tsBaseUser.getPassword());
		if (user == null) {
			D0.setErrorMsg("用户账号密码错误!");
			D0.setOK(false);
			logger.info("获取TOKEN,账号密码错误[{}]" + tsBaseUser.getUserName());
		}else{

			D0.setObj(user);
			D0.setOK(true);
		}
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
			fileAddr = f.getCanonicalPath();
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

	@RequestMapping(value = "/getuser/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据username获取用户信息",notes="根据username获取用户信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> getuser(@ApiParam(required=true,name="username",value="username")@PathVariable("username") String username,
									  @ApiParam(required=false,name="password",value="password")@RequestParam String password) {
		TSUser task = userService.checkUserExits(username, password);
		if(!StringUtil.isEmpty(task.getDepartid())){
			TSDepart tsDepart = systemService.get(TSDepart.class,task.getDepartid());
			if(tsDepart!=null){
				tsDepart.setTSDeparts(null);
				tsDepart.setTSPDepart(null);
				task.setCurrentDepart(tsDepart);
			}else{
				try{
					TSUserOrg tsDepart1 =  task.getUserOrgList().get(0);

					tsDepart1.setTsDepart(null);
					task.setCurrentDepart (tsDepart1.getTsDepart());
				}catch (Exception e1){
				}

			}
		}
		task.setUserOrgList(null);

		return org.jeecgframework.jwt.util.Result.success(task);
	}

}
