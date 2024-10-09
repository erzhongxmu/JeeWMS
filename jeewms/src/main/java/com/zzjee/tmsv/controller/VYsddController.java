package com.zzjee.tmsv.controller;

import com.alibaba.fastjson.JSONArray;
import com.zzjee.tmsv.entity.VYsddEntity;
import com.zzjee.tmsv.service.VYsddServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**   
 * @Title: Controller  
 * @Description: v_ysdd
 * @author onlineGenerator
 * @date 2018-05-04 00:07:37
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/vYsddController")
@Api(value="VYsdd",description="v_ysdd",tags="vYsddController")
public class VYsddController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VYsddController.class);

	@Autowired
	private VYsddServiceI vYsddService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * v_ysdd列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tmsv/vYsddList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(VYsddEntity vYsdd, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(VYsddEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vYsdd, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_createDate_begin = request.getParameter("createDate_begin");
		String query_createDate_end = request.getParameter("createDate_end");
		if(StringUtil.isNotEmpty(query_createDate_begin)) {
			cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_begin));

		}else{

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			System.out.println("当前日期:"+sf.format(c.getTime()));
			c.add(Calendar.DAY_OF_MONTH, -1);
//			System.out.println("增加一天后日期:"+sf.format(c.getTime()));
			cq.ge("createDate", DateUtils.str2Date(sf.format(c.getTime()),DateUtils.date_sdf));
		}
		if(StringUtil.isNotEmpty(query_createDate_end)){
			cq.le("createDate",  new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_end));
		}
		String query_sdsj_begin = request.getParameter("sdsj_begin");
		String query_sdsj_end = request.getParameter("sdsj_end");
		if(StringUtil.isNotEmpty(query_sdsj_begin)){
			cq.ge("sdsj", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_sdsj_begin));
		}
		if(StringUtil.isNotEmpty(query_sdsj_end)){
			cq.le("sdsj", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_sdsj_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.vYsddService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除v_ysdd
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(VYsddEntity vYsdd, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		vYsdd = systemService.getEntity(VYsddEntity.class, vYsdd.getId());
		message = "v_ysdd删除成功";
		try{
			vYsddService.delete(vYsdd);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "v_ysdd删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除v_ysdd
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "v_ysdd删除成功";
		try{
			for(String id:ids.split(",")){
				VYsddEntity vYsdd = systemService.getEntity(VYsddEntity.class,
				id
				);
				vYsddService.delete(vYsdd);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "v_ysdd删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加v_ysdd
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(VYsddEntity vYsdd, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "v_ysdd添加成功";
		try{
			vYsddService.save(vYsdd);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "v_ysdd添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新v_ysdd
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(VYsddEntity vYsdd, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "v_ysdd更新成功";
		VYsddEntity t = vYsddService.get(VYsddEntity.class, vYsdd.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vYsdd, t);
			vYsddService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "v_ysdd更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * v_ysdd新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(VYsddEntity vYsdd, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(vYsdd.getId())) {
			vYsdd = vYsddService.getEntity(VYsddEntity.class, vYsdd.getId());
			req.setAttribute("vYsddPage", vYsdd);
		}
		return new ModelAndView("com/zzjee/tmsv/vYsdd-add");
	}
	/**
	 * v_ysdd编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(VYsddEntity vYsdd, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(vYsdd.getId())) {
			vYsdd = vYsddService.getEntity(VYsddEntity.class, vYsdd.getId());
			req.setAttribute("vYsddPage", vYsdd);
		}
		return new ModelAndView("com/zzjee/tmsv/vYsdd-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","vYsddController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public void exportXls(VYsddEntity vYsdd, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(VYsddEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vYsdd, request.getParameterMap());

        String query_createDate_begin = request.getParameterMap().get("createDate_begin")[0];
        if(StringUtil.isNotEmpty(query_createDate_begin)) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            System.out.println("当前日期:" + sf.format(c.getTime()));
            c.add(Calendar.DAY_OF_MONTH, -1);
//			System.out.println("增加一天后日期:"+sf.format(c.getTime()));
            cq.ge("createDate", DateUtils.str2Date(sf.format(c.getTime()), DateUtils.date_sdf));
        }
        List<VYsddEntity> vYsdds = this.vYsddService.getListByCriteriaQuery(cq,false);
		OutputStream fileOut = null;
//		BufferedImage bufferImg = null;
//		String codedFileName = null;

		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename=zhangdan.xls"
					);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("zhangdan");
//			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

			sheet.setMargin(HSSFSheet.TopMargin,0.1);// 页边距（上）
			sheet.setMargin(HSSFSheet.BottomMargin,0.1);// 页边距（下）
			sheet.setMargin(HSSFSheet.LeftMargin,0.1);// 页边距（左）
			sheet.setMargin(HSSFSheet.RightMargin,0.05);// 页边距（右

			sheet.setColumnWidth(0, 15 * 256);
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 15 * 256);
			sheet.setColumnWidth(3, 15 * 256);
			sheet.setColumnWidth(4, 15 * 256);
			sheet.setColumnWidth(5, 15 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 15 * 256);
			sheet.setColumnWidth(8, 15 * 256);
			sheet.setColumnWidth(9, 15 * 256);
			sheet.setColumnWidth(10, 15 * 256);
			sheet.setColumnWidth(11, 15 * 256);
			sheet.setColumnWidth(12, 15 * 256);
			sheet.setColumnWidth(13, 15 * 256);
			sheet.setColumnWidth(14, 15 * 256);
			sheet.setColumnWidth(15, 15 * 256);
			sheet.setColumnWidth(16, 15 * 256);
			sheet.setColumnWidth(17, 30 * 256);
			sheet.setColumnWidth(18, 30 * 256);
			sheet.setColumnWidth(19, 15 * 256);

			// 创建两种单元格格式
			CellStyle cs = wb.createCellStyle();
			CellStyle cs1 = wb.createCellStyle();
			CellStyle cs2 = wb.createCellStyle();
			CellStyle cs3 = wb.createCellStyle();
			CellStyle cs3r = wb.createCellStyle();
			// 创建两种字体
			Font f = wb.createFont();
			Font f2 = wb.createFont();
			Font f5 = wb.createFont();
			// 创建第一种字体样式（用于列名）
			f.setFontHeightInPoints((short) 16);
			f.setColor(IndexedColors.BLACK.getIndex());
			f.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// 创建第二种字体样式（用于值）
			f2.setFontHeightInPoints((short) 10);
			f2.setColor(IndexedColors.BLACK.getIndex());
			f5.setFontHeightInPoints((short) 8);
			f5.setColor(IndexedColors.BLACK.getIndex());

			// 设置第一种单元格的样式（用于列名）
			cs.setFont(f);
			cs.setBorderLeft(CellStyle.BORDER_NONE);
			cs.setBorderRight(CellStyle.BORDER_NONE);
			cs.setBorderTop(CellStyle.BORDER_NONE);
			cs.setBorderBottom(CellStyle.BORDER_NONE);
			cs.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

			cs1.setFont(f2);
			cs1.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cs1.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs1.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs1.setBorderBottom(CellStyle.BORDER_MEDIUM);
			cs1.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// 设置第二种单元格的样式（用于值）
			cs2.setFont(f2);
			cs2.setBorderLeft(CellStyle.BORDER_NONE);
			cs2.setBorderRight(CellStyle.BORDER_NONE);
			cs2.setBorderTop(CellStyle.BORDER_NONE);
			cs2.setBorderBottom(CellStyle.BORDER_NONE);
			cs2.setWrapText(true);

			// cs2.setAlignment(CellStyle.BORDER_NONE);

			cs3.setFont(f2);
			cs3.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cs3.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs3.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs3.setBorderBottom(CellStyle.BORDER_MEDIUM);
			cs3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			cs3r.setFont(f2);
			cs3r.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cs3r.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs3r.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs3r.setBorderBottom(CellStyle.BORDER_MEDIUM);
			cs3r.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//			String[] columnNames = { "送达时间","发货人","货物	","件数","重量","体积","收货人地址","收货人","送货方式","收货人电话","代收款","运费","卸货费","总费用","车号","状态","回单备注" };
			String[] columnNames = { "送达时间","发货人","货物	","件数","重量","体积","货号","收货人地址","收货人","送货方式","收货人电话","代收款","运费", "回单备注","车号","上午或下午" };

			Row rowColumnName = sheet.createRow((short) 0); // 列名

			int coli = 0;
			for (int i = 0; i < columnNames.length; i++) {

				Cell cell = rowColumnName.createCell(coli);
				coli++;
				rowColumnName.setHeight((short) 300);
				cell.setCellValue(columnNames[i]);
				cell.setCellStyle(cs1);

			}
			int rows = 1;
          for(int j = 0;j<vYsdds.size();j++){
			  Row rowColumnNameta = sheet.createRow((short) rows); // 列名
			  rows++;
			  coli = 0;
//             try{
//				 setcellvalue(rowColumnNameta,coli, (short)300,DateUtils.date2Str(vYsdds.get(j).getCreateDate(),DateUtils.date_sdf),cs3);
//
//			 }catch (Exception e){
//				 setcellvalue(rowColumnNameta,coli, (short)300,"",cs3);
//			 }
			  try{
				  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getSdsj().toString(),cs3);

			  }catch (Exception e){
				 setcellvalue(rowColumnNameta,coli, (short)300,DateUtils.date2Str(vYsdds.get(j).getCreateDate(),DateUtils.date_sdf),cs3);
			  }
			  coli++;
			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getFahuoren(),cs3);


			  coli++;
			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getHuowu(),cs3);
			  coli++;
			  try{
				  setcellvalueunm(rowColumnNameta,coli, (short)300,Double.parseDouble(vYsdds.get(j).getHwshjs()),cs3r);
			  }catch (Exception e){
				  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getHwshjs(),cs3r);
			  }

			  coli++;
			  try{
				  setcellvalueunm(rowColumnNameta,coli, (short)300,Double.parseDouble(vYsdds.get(j).getZhongl()),cs3r);
			  }catch (Exception e){
				  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getZhongl(),cs3r);
			  }
			  coli++;
			  try{
				  setcellvalueunm(rowColumnNameta,coli, (short)300,Double.parseDouble(vYsdds.get(j).getTiji()),cs3r);
			  }catch (Exception e){
				  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getTiji(),cs3r);
			  }

			  coli++;
			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getYwkhdh(),cs3);

			  coli++;
			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getShrdh(),cs3);

			  coli++;
			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getShouhuoren(),cs3);

			  coli++;
			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getHwshfs(),cs3);
			  coli++;
			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getShrsj(),cs3);

			  coli++;
			  try{
			  setcellvalueunm(rowColumnNameta,coli, (short)300,Double.parseDouble(vYsdds.get(j).getDaishouk()),cs3r);
			  }catch (Exception e){
				  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getDaishouk(),cs3r);
			  }
			  coli++;
			  try{
			  setcellvalueunm(rowColumnNameta,coli, (short)300,Double.parseDouble(vYsdds.get(j).getHwyf()),cs3r);

			  }catch (Exception e){
				  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getHwyf(),cs3r);
			  }

//			  coli++;
//			  try{
//				  setcellvalueunm(rowColumnNameta,coli, (short)300,Double.parseDouble(vYsdds.get(j).getHwxhf()),cs3);
//			  }catch (Exception e){
//				  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getHwxhf(),cs3r);
//			  }
//
//			  coli++;
//			  try{
//			  setcellvalueunm(rowColumnNameta,coli, (short)300,Double.parseDouble(vYsdds.get(j).getHwzfy()),cs3r);
//			  }catch (Exception e){
//				  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getHwzfy(),cs3);
//
//			  }


//			  coli++;
//			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getZhuangtai(),cs3);

			  coli++;
			  try{

				  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getYwhdbz(),cs3);
			  }catch (Exception e){

			  }
			  coli++;
			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getChehao(),cs3);

			  coli++;
			  try{
				  if(StringUtil.isEmpty(vYsdds.get(j).getBy1())){
					  GregorianCalendar ca = new GregorianCalendar();
					  ca.setTime(vYsdds.get(j).getCreateDate());
					  if(ca.get(GregorianCalendar.AM_PM) == 0){
						  vYsdds.get(j).setBy1("上午");
					  }else{
						  vYsdds.get(j).setBy1("下午");
					  }
				  }
			  }catch (Exception e){

			  }
			  setcellvalue(rowColumnNameta,coli, (short)300,vYsdds.get(j).getBy1(),cs3);

		  }
			//插入图片
			fileOut = response.getOutputStream();
			HSSFPrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setcellvalue(Row rowColumnNameta,int i,short height,String value,CellStyle cell){
		Cell cell0 = rowColumnNameta.createCell(i);
		rowColumnNameta.setHeight(height);
		cell0.setCellValue(value);
		cell0.setCellStyle(cell);
	}
	public void setcellvalueunm(Row rowColumnNameta,int i,short height,Double value,CellStyle cell){
		Cell cell0 = rowColumnNameta.createCell(i);
		rowColumnNameta.setHeight(height);
		cell0.setCellValue(value);
		cell0.setCellStyle(cell);

		cell0.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(VYsddEntity vYsdd, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"v_ysdd");
    	modelMap.put(NormalExcelConstants.CLASS,VYsddEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("v_ysdd列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
//	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<VYsddEntity> listVYsddEntitys = ExcelImportUtil.importExcel(file.getInputStream(),VYsddEntity.class,params);
				for (VYsddEntity vYsdd : listVYsddEntitys) {
					vYsddService.save(vYsdd);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="v_ysdd列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<VYsddEntity>> list() {
		List<VYsddEntity> listVYsdds=vYsddService.getList(VYsddEntity.class);
		return Result.success(listVYsdds);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取v_ysdd信息",notes="根据ID获取v_ysdd信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		VYsddEntity task = vYsddService.get(VYsddEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取v_ysdd信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建v_ysdd")
	public ResponseMessage<?> create(@ApiParam(name="v_ysdd对象")@RequestBody VYsddEntity vYsdd, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<VYsddEntity>> failures = validator.validate(vYsdd);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			vYsddService.save(vYsdd);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("v_ysdd信息保存失败");
		}
		return Result.success(vYsdd);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新v_ysdd",notes="更新v_ysdd")
	public ResponseMessage<?> update(@ApiParam(name="v_ysdd对象")@RequestBody VYsddEntity vYsdd) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<VYsddEntity>> failures = validator.validate(vYsdd);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			vYsddService.saveOrUpdate(vYsdd);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新v_ysdd信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新v_ysdd信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除v_ysdd")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			vYsddService.deleteEntityById(VYsddEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("v_ysdd删除失败");
		}

		return Result.success();
	}
}
