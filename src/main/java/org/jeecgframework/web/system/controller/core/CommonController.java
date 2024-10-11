package org.jeecgframework.web.system.controller.core;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * 通用业务处理
 *
 * @author admin
 *
 */
//@Scope("prototype")
@Controller
@RequestMapping("/commonController")
public class CommonController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CommonController.class);
	private SystemService systemService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * 通用列表页面跳转
	 */
	@RequestMapping(params = "listTurn")
	public ModelAndView listTurn(HttpServletRequest request) {
		String turn = request.getParameter("turn");// 跳转的目标页面
		return new ModelAndView(turn);
	}

	/**
	 * 附件预览页面打开链接
	 *
	 * @return
	 */
	@RequestMapping(params = "openViewFile")
	public ModelAndView openViewFile(HttpServletRequest request) {
		String fileid = request.getParameter("fileid");
		String subclassname = oConvertUtils.getString(request.getParameter("subclassname"), "org.jeecgframework.web.system.pojo.base.TSAttachment");
		String contentfield = oConvertUtils.getString(request.getParameter("contentfield"));
		Class fileClass = MyClassLoader.getClassByScn(subclassname);// 附件的实际类
		Object fileobj = systemService.getEntity(fileClass, fileid);
		ReflectHelper reflectHelper = new ReflectHelper(fileobj);
		String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));
		if ("dwg".equals(extend)) {
			String realpath = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
			request.setAttribute("realpath", realpath);
			return new ModelAndView("common/upload/dwgView");
		} else if (FileUtils.isPicture(extend)) {
			String realpath = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
			request.setAttribute("realpath", realpath);
			request.setAttribute("fileid", fileid);
			request.setAttribute("subclassname", subclassname);
			request.setAttribute("contentfield", contentfield);
			return new ModelAndView("common/upload/imageView");
		} else {
			String swfpath = oConvertUtils.getString(reflectHelper.getMethodValue("swfpath"));
			request.setAttribute("swfpath", swfpath);
			return new ModelAndView("common/upload/swfView");
		}

	}

	/**
	 * 附件预览读取
	 *
	 * @return
	 */
	@RequestMapping(params = "viewFile")
	public void viewFile(HttpServletRequest request, HttpServletResponse response) {
		String fileid =oConvertUtils.getString(request.getParameter("fileid"));
		String subclassname = oConvertUtils.getString(request.getParameter("subclassname"), "com.jeecg.base.pojo.TSAttachment");
		Class fileClass = MyClassLoader.getClassByScn(subclassname);// 附件的实际类
		Object fileobj = systemService.getEntity(fileClass, fileid);
		ReflectHelper reflectHelper = new ReflectHelper(fileobj);
		UploadFile uploadFile = new UploadFile(request, response);
		String contentfield = oConvertUtils.getString(request.getParameter("contentfield"), uploadFile.getByteField());
		byte[] content = (byte[]) reflectHelper.getMethodValue(contentfield);
		String path = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
		String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));
		String attachmenttitle = oConvertUtils.getString(reflectHelper.getMethodValue("attachmenttitle"));
		uploadFile.setExtend(extend);
		uploadFile.setTitleField(attachmenttitle);
		uploadFile.setRealPath(path);
		uploadFile.setContent(content);
		//uploadFile.setView(true);
		systemService.viewOrDownloadFile(uploadFile);
	}

	@RequestMapping(params = "importdata")
	public ModelAndView importdata() {
		return new ModelAndView("system/upload");
	}

	/**
	 * 生成XML文件
	 *
	 * @return
	 */
	@RequestMapping(params = "createxml")
	public void createxml(HttpServletRequest request, HttpServletResponse response) {
		String field = request.getParameter("field");
		String entityname = request.getParameter("entityname");
		ImportFile importFile = new ImportFile(request, response);
		importFile.setField(field);
		importFile.setEntityName(entityname);
		importFile.setFileName(entityname + ".bak");
		importFile.setEntityClass(MyClassLoader.getClassByScn(entityname));
		systemService.createXml(importFile);
	}

	/**
	 * 生成XML文件parserXml
	 *
	 * @return
	 */
	@RequestMapping(params = "parserXml")
	@ResponseBody
	public AjaxJson parserXml(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson json = new AjaxJson();
		String allowedExtension = ".xml"; // 允许的文件扩展名
		long maxSize = 1024 * 1024 * 100; // 最大文件大小100MB
		String fileName = null;
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + "uploads/"; // 更改存储位置

		// 确保上传目录存在
		File file = new File(ctxPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 直接从request获取
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while (fileNames.hasNext()) {
			String formFieldName = fileNames.next();
			MultipartFile mf = multipartRequest.getFile(formFieldName);

			// 检查文件类型和大小
			if (mf.isEmpty() || !mf.getOriginalFilename().toLowerCase().endsWith(allowedExtension) || mf.getSize() > maxSize) {
				json.setMsg("文件类型错误或文件大小超过100M或文件为空");
				json.setSuccess(false);
				return json;
			}

			// 重命名文件
			fileName = UUID.randomUUID().toString() + "_" + mf.getOriginalFilename();
			String savePath = ctxPath + fileName;

			try {
				// 保存文件
				mf.transferTo(new File(savePath));
				systemService.parserXml(savePath); // 使用完整路径
				json.setSuccess(true);
			} catch (IOException e) {
				e.printStackTrace();
				json.setMsg("Failed to save file.");
				json.setSuccess(false);
			}
		}

		return json;
	}

	/**
	 * 自动完成请求返回数据
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getAutoList")
	public void getAutoList(HttpServletRequest request, HttpServletResponse response, Autocomplete autocomplete) {
		String jsonp = request.getParameter("jsonpcallback");
		String trem = StringUtil.getEncodePra(request.getParameter("trem"));// 重新解析参数
		autocomplete.setTrem(trem);
		System.out.println("************************"+trem);
		List autoList = systemService.getAutoList(autocomplete);
		String labelFields = autocomplete.getLabelField();
		String[] fieldArr = labelFields.split(",");
		String valueField = autocomplete.getValueField();
		String[] allFieldArr = null;
		if (StringUtil.isNotEmpty(valueField)) {
			allFieldArr = new String[fieldArr.length+1];
			for (int i=0; i<fieldArr.length; i++) {
				allFieldArr[i] = fieldArr[i];
			}
			allFieldArr[fieldArr.length] = valueField;
		}

		try {
			String str = TagUtil.getAutoList(autocomplete, autoList);
			str = "(" + str + ")";
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(JSONHelper.listtojson(allFieldArr,allFieldArr.length,autoList));
            response.getWriter().flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (IOException e) {
			}
		}

	}

	/**
	 * 删除继承于TSAttachment附件的公共方法
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delObjFile")
	@ResponseBody
	public AjaxJson delObjFile(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		TSAttachment attachment = systemService.getEntity(TSAttachment.class,fileKey);
		String subclassname = attachment.getSubclassname(); // 子类类名
		Object objfile = systemService.getEntity(MyClassLoader.getClassByScn(subclassname), attachment.getId());// 子类对象
		message = "" + attachment.getAttachmenttitle() + "删除成功";
		systemService.delete(objfile);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 继承于TSAttachment附件公共列表跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "objfileList")
	public ModelAndView objfileList(HttpServletRequest request) {
		Object object = null;// 业务实体对象
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		TSAttachment attachment = systemService.getEntity(TSAttachment.class,fileKey);
		String businessKey = oConvertUtils.getString(request.getParameter("businessKey"));// 业务主键
		String busentityName = oConvertUtils.getString(request.getParameter("busentityName"));// 业务主键
		String typename = oConvertUtils.getString(request.getParameter("typename"));// 类型
		String typecode = oConvertUtils.getString(request.getParameter("typecode"));// 类型typecode
		if (StringUtil.isNotEmpty(busentityName) && StringUtil.isNotEmpty(businessKey)) {
			object = systemService.get(MyClassLoader.getClassByScn(busentityName), businessKey);
			request.setAttribute("object", object);
			request.setAttribute("businessKey", businessKey);
		}
		if(attachment!=null)
		{
			request.setAttribute("subclassname",attachment.getSubclassname());
		}
		request.setAttribute("fileKey", fileKey);
		request.setAttribute("typecode", typecode);
		request.setAttribute("typename", typename);
		request.setAttribute("typecode", typecode);
		return new ModelAndView("common/objfile/objfileList");
	}

	/**
	 * 继承于TSAttachment附件公共列表数据
	 */
	@RequestMapping(params = "objfileGrid")
	public void objfileGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String businessKey = oConvertUtils.getString(request.getParameter("businessKey"));
		String subclassname = oConvertUtils.getString(request.getParameter("subclassname"));// 子类类名
		String type = oConvertUtils.getString(request.getParameter("typename"));
		String code = oConvertUtils.getString(request.getParameter("typecode"));
		String filekey = oConvertUtils.getString(request.getParameter("filekey"));
		CriteriaQuery cq = new CriteriaQuery(MyClassLoader.getClassByScn(subclassname), dataGrid);
		cq.eq("businessKey", businessKey);
		if (StringUtil.isNotEmpty(type)) {
			cq.createAlias("TBInfotype", "TBInfotype");
			cq.eq("TBInfotype.typename", type);
		}
		if (StringUtil.isNotEmpty(filekey)) {
			cq.eq("id", filekey);
		}
		if (StringUtil.isNotEmpty(code)) {
			cq.createAlias("TBInfotype", "TBInfotype");
			cq.eq("TBInfotype.typecode", code);
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
}
