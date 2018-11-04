package com.zzjee.bm.controller;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.tools.ant.taskdefs.Exit;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.zzjee.bm.entity.MvCusCostEntity;
import com.zzjee.bm.service.MvCusCostServiceI;
import com.zzjee.md.entity.MdCusEntity;

/**   
 * @Title: Controller  
 * @Description: mv_cus_cost
 * @author erzhongxmu
 * @date 2017-10-19 12:23:14
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/mvCusCostController")
public class MvCusCostController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MvCusCostController.class);

	@Autowired
	private MvCusCostServiceI mvCusCostService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * mv_cus_cost列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/bm/mvCusCostList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid

	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(MvCusCostEntity mvCusCost,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MvCusCostEntity.class, dataGrid);
		//查询条件组装器
//		request.setAttribute("costData_begin", "1990-01-01");
//		request.setAttribute("costData_end", "2990-01-01");
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mvCusCost, request.getParameterMap());
		try{
		//自定义追加查询条件
//			cq.ge("costData",  DateUtils.str2Date("1990-01-01", DateUtils.date_sdf) );
//			cq.le("costData", DateUtils.str2Date("2990-01-01", DateUtils.date_sdf) );

		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.mvCusCostService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除mv_cus_cost
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(MvCusCostEntity mvCusCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		mvCusCost = systemService.getEntity(MvCusCostEntity.class, mvCusCost.getId());
		message = "删除成功";
		try{
			mvCusCostService.delete(mvCusCost);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	@RequestMapping(params = "doPrint")
	@ResponseBody
	public void downReceiveExcelysd(MvCusCostEntity mvCusCost,HttpServletRequest request,HttpServletResponse response) {
		OutputStream fileOut = null;
		BufferedImage bufferImg = null;
		String codedFileName = null;
		mvCusCost = systemService.getEntity(MvCusCostEntity.class,
				request.getParameter("id"));// 获取抬头
		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ mvCusCost.getCusCode()+"("+request.getParameter("begindate")+" "+request.getParameter("enddate")+").xls");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sum");
			HSSFSheet sheet1 = wb.createSheet("detail");
			HSSFSheet sheet2 = wb.createSheet("sum1");

			sheet1.setColumnWidth(0, 20 * 256);
			sheet1.setColumnWidth(1, 20 * 256);
			sheet1.setColumnWidth(2, 20 * 256);
			sheet1.setColumnWidth(3, 20 * 256);
			sheet1.setColumnWidth(4, 10 * 256);
			sheet1.setColumnWidth(5, 20 * 256);

			sheet2.setColumnWidth(0, 15 * 256);
			sheet2.setColumnWidth(1, 15 * 256);
			sheet2.setColumnWidth(2, 15 * 256);
			sheet2.setColumnWidth(3, 15 * 256);
			sheet2.setColumnWidth(4, 15 * 256);
			sheet2.setColumnWidth(5, 15 * 256);
			sheet2.setColumnWidth(6, 15 * 256);
			sheet2.setColumnWidth(7, 15 * 256);
			sheet2.setColumnWidth(8, 15 * 256);
			sheet2.setColumnWidth(9, 15 * 256);
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255,(short) 1, 1, (short) 3, 1);

			try{
				bufferImg = ImageIO.read(new File(ResourceUtil.getConfigByName("comfilogo")));
				ImageIO.write(bufferImg, "jpg", byteArrayOut);
				//画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
				//anchor主要用于设置图片的属性
				anchor.setAnchorType(3);
				//插入图片
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

			}catch (Exception e){

			}

            
           
            
			sheet.setMargin(HSSFSheet.TopMargin,0.1);// 页边距（上）    
			sheet.setMargin(HSSFSheet.BottomMargin,0.1);// 页边距（下）    
			sheet.setMargin(HSSFSheet.LeftMargin,0.1);// 页边距（左）    
			sheet.setMargin(HSSFSheet.RightMargin,0.05);// 页边距（右   
//			sheet.setDisplayGridlines(true);
		     //set print grid lines or not
//			sheet.setPrintGridlines(true);
			sheet.setColumnWidth(0, 9 * 256);
			sheet.setColumnWidth(1, 10 * 256);
			sheet.setColumnWidth(2, 10 * 256);
			sheet.setColumnWidth(3, 10 * 256);
			sheet.setColumnWidth(4, 10 * 256);
			sheet.setColumnWidth(5, 9 * 256);
			sheet.setColumnWidth(6, 10 * 256);
			sheet.setColumnWidth(7, 10 * 256);
			sheet.setColumnWidth(8, 10 * 256);
			sheet.setColumnWidth(9, 10 * 256);
//			sheet.setColumnWidth(10, 3 * 256);
			// sheet.setColumnWidth(6, 8 * 256);
			// sheet.setColumnWidth(7, 8 * 256);
			// sheet.setColumnWidth(8, 8 * 256);
			// 创建两种单元格格式
						CellStyle cs = wb.createCellStyle();
						CellStyle cs1 = wb.createCellStyle();
						CellStyle cs2 = wb.createCellStyle();
						CellStyle cs3 = wb.createCellStyle();
			CellStyle cs3d = wb.createCellStyle();
						CellStyle cs4 = wb.createCellStyle();
						CellStyle cs5 = wb.createCellStyle();
						CellStyle cs51 = wb.createCellStyle();
						CellStyle cs52 = wb.createCellStyle();
						CellStyle cs53 = wb.createCellStyle();
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
						cs1.setBorderLeft(CellStyle.BORDER_NONE);
						cs1.setBorderRight(CellStyle.BORDER_NONE);
						cs1.setBorderTop(CellStyle.BORDER_NONE);
						cs1.setBorderBottom(CellStyle.BORDER_NONE);
						cs1.setAlignment(HSSFCellStyle.ALIGN_CENTER);

						cs1.setWrapText(true);
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
						cs3.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			cs3d.setFont(f2);
			cs3d.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cs3d.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs3d.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs3d.setBorderBottom(CellStyle.BORDER_MEDIUM);
			cs3d.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

						CellStyle cs31 = wb.createCellStyle();
						cs31.setFont(f2);
						cs31.setBorderLeft(CellStyle.BORDER_MEDIUM);
						cs31.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs31.setBorderTop(CellStyle.BORDER_MEDIUM);
						cs31.setBorderBottom(CellStyle.BORDER_NONE);
						
						CellStyle cs32 = wb.createCellStyle();
						cs32.setFont(f2);
						cs32.setBorderLeft(CellStyle.BORDER_MEDIUM);
						cs32.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs32.setBorderTop(CellStyle.BORDER_NONE);
						cs32.setBorderBottom(CellStyle.BORDER_NONE);

						
						CellStyle cs32c = wb.createCellStyle();
						cs32c.setFont(f2);
						cs32c.setBorderLeft(CellStyle.BORDER_MEDIUM);
						cs32c.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs32c.setBorderTop(CellStyle.BORDER_NONE);
						cs32c.setBorderBottom(CellStyle.BORDER_NONE);
						cs32c.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						
						CellStyle cs32r = wb.createCellStyle();
						cs32r.setFont(f2);
						cs32r.setBorderLeft(CellStyle.BORDER_MEDIUM);
						cs32r.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs32r.setBorderTop(CellStyle.BORDER_NONE);
						cs32r.setBorderBottom(CellStyle.BORDER_NONE);
						cs32r.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
						
						
						CellStyle cs32ra = wb.createCellStyle();
						cs32ra.setFont(f2);
						cs32ra.setBorderLeft(CellStyle.BORDER_MEDIUM);
						cs32ra.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs32ra.setBorderTop(CellStyle.BORDER_MEDIUM);
						cs32ra.setBorderBottom(CellStyle.BORDER_MEDIUM);
						cs32ra.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
						
						CellStyle cs33 = wb.createCellStyle();
						cs33.setFont(f2);
						cs33.setBorderLeft(CellStyle.BORDER_MEDIUM);
						cs33.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs33.setBorderTop(CellStyle.BORDER_NONE);
						cs33.setBorderBottom(CellStyle.BORDER_MEDIUM);
						
						
						CellStyle cs34 = wb.createCellStyle();
						cs34.setFont(f2);
						cs34.setBorderLeft(CellStyle.BORDER_MEDIUM);
						cs34.setBorderRight(CellStyle.BORDER_NONE);
						cs34.setBorderTop(CellStyle.BORDER_NONE);
						cs34.setBorderBottom(CellStyle.BORDER_NONE);
						
						CellStyle cs35 = wb.createCellStyle();
						cs35.setFont(f2);
						cs35.setBorderLeft(CellStyle.BORDER_NONE);
						cs35.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs35.setBorderTop(CellStyle.BORDER_NONE);
						cs35.setBorderBottom(CellStyle.BORDER_NONE);
						
						
						CellStyle cs35c = wb.createCellStyle();
						cs35c.setFont(f2);
						cs35c.setBorderLeft(CellStyle.BORDER_NONE);
						cs35c.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs35c.setBorderTop(CellStyle.BORDER_NONE);
						cs35c.setBorderBottom(CellStyle.BORDER_NONE);
						cs35c.setAlignment(HSSFCellStyle.ALIGN_RIGHT);


			CellStyle cs35cd = wb.createCellStyle();
			cs35cd.setFont(f2);
			cs35cd.setBorderLeft(CellStyle.BORDER_NONE);
			cs35cd.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs35cd.setBorderTop(CellStyle.BORDER_NONE);
			cs35cd.setBorderBottom(CellStyle.BORDER_NONE);
			cs35cd.setAlignment(HSSFCellStyle.ALIGN_RIGHT);


						CellStyle cs36 = wb.createCellStyle();
						cs36.setFont(f2);
						cs36.setBorderLeft(CellStyle.BORDER_MEDIUM);
						cs36.setBorderRight(CellStyle.BORDER_NONE);
						cs36.setBorderTop(CellStyle.BORDER_NONE);
						cs36.setBorderBottom(CellStyle.BORDER_MEDIUM);
						
						CellStyle cs37 = wb.createCellStyle();
						cs37.setFont(f2);
						cs37.setBorderLeft(CellStyle.BORDER_NONE);
						cs37.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs37.setBorderTop(CellStyle.BORDER_NONE);
						cs37.setBorderBottom(CellStyle.BORDER_MEDIUM);
						
						cs4.setFont(f2);
						cs4.setBorderTop(CellStyle.BORDER_MEDIUM);
						cs4.setBorderBottom(CellStyle.BORDER_MEDIUM);

						cs5.setFont(f2);
						cs5.setBorderLeft(CellStyle.BORDER_THIN);
						cs5.setBorderRight(CellStyle.BORDER_THIN);
						cs5.setBorderTop(CellStyle.BORDER_THIN);
						cs5.setBorderBottom(CellStyle.BORDER_THIN);
						cs5.setWrapText(true);
						cs51.setFont(f2);
						cs51.setBorderLeft(CellStyle.BORDER_THIN);
						cs51.setBorderRight(CellStyle.BORDER_THIN);
						cs51.setBorderTop(CellStyle.BORDER_THIN);
						cs51.setBorderBottom(CellStyle.BORDER_THIN);
						cs51.setAlignment(HSSFCellStyle.ALIGN_CENTER);

						cs51.setWrapText(true);
			
						cs52.setFont(f5);
						cs52.setBorderLeft(CellStyle.BORDER_NONE);
						cs52.setBorderRight(CellStyle.BORDER_NONE);
						cs52.setBorderTop(CellStyle.BORDER_NONE);
						cs52.setBorderBottom(CellStyle.BORDER_NONE);
						cs52.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						cs53.setFont(f2);
						cs53.setBorderLeft(CellStyle.BORDER_THIN);
						cs53.setBorderRight(CellStyle.BORDER_THIN);
						cs53.setBorderTop(CellStyle.BORDER_THIN);
						cs53.setBorderBottom(CellStyle.BORDER_THIN);
						cs53.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
						
						cs52.setWrapText(true);
						cs52.setRotation((short)255);
						
			int page = 0;
			int cerconNo = 1;
			String begindate = request.getParameter("begindate");
			String enddate = request.getParameter("enddate");
			if(StringUtil.isEmpty(begindate)){
				begindate = "2017-01-01";
			}
			if(StringUtil.isEmpty(enddate)){
				enddate =  DateUtils.date2Str(DateUtils.date_sdf);
			}
			String tsql = "select 	wc.cus_code,    wc.cus_name,    wc.cost_code,    wc.cost_name, "    
				   +" sum(wc.day_cost_yj) as yuanj,   (sum(wc.day_cost_bhs)- sum(wc.day_cost_yj)) as tiaozheng,    sum(wc.day_cost_bhs) as bhsj,"
				   +"  sum(wc.day_cost_se) as shuie,     sum(wc.day_cost_hsj) as hansj, sum(wc.cost_sl) as costsl,wc.cost_unit "
				   +" from		 wm_day_cost wc" 
				        +" where (wc.cost_js <> 'Y' or wc.cost_js is null) and  wc.day_cost_yj <> 0 and  wc.cus_code = ? and to_days(wc.cost_data) >= to_days(?) and to_days(wc.cost_data) <= to_days(?)"
				   +"  group by wc.cus_code , wc.cus_name , wc.cost_code , wc.cost_name " ;
			 if(request.getParameter("qj").equals("qj")){
				 tsql = "select 	wc.cus_code,    wc.cus_name,    wc.cost_code,    wc.cost_name, "
						 +" sum(wc.day_cost_yj) as yuanj,   (sum(wc.day_cost_bhs)- sum(wc.day_cost_yj)) as tiaozheng,    sum(wc.day_cost_bhs) as bhsj,"
						 +"  sum(wc.day_cost_se) as shuie,     sum(wc.day_cost_hsj) as hansj,  sum(wc.cost_sl) as costsl,wc.cost_unit  "
						 +" from		 wm_day_cost wc"
						 +" where    wc.day_cost_yj <> 0 and  wc.cus_code = ? and to_days(wc.cost_data) >= to_days(?) and to_days(wc.cost_data) <= to_days(?)"
						 +"  group by wc.cus_code , wc.cus_name , wc.cost_code , wc.cost_name " ;
			 }

			String mxtsql = "select DATE_FORMAT(wc.cost_data,'%Y-%m-%d') as cost_data, 	wc.cus_code,    wc.cus_name,    wc.cost_code,    wc.cost_name, "
					+" sum(wc.day_cost_yj) as yuanj,   (sum(wc.day_cost_bhs)- sum(wc.day_cost_yj)) as tiaozheng,    sum(wc.day_cost_bhs) as bhsj,"
					+"  sum(wc.day_cost_se) as shuie,     sum(wc.day_cost_hsj) as hansj, sum(wc.cost_sl) as costsl,wc.cost_unit "
					+" from		 wm_day_cost wc"
					+" where (wc.cost_js <> 'Y' or wc.cost_js is null) and  wc.day_cost_yj <> 0 and  wc.cus_code = ? and to_days(wc.cost_data) >= to_days(?) and to_days(wc.cost_data) <= to_days(?)"
					+"     group by wc.cost_data, wc.cus_code , wc.cus_name , wc.cost_code , wc.cost_name order by wc.cost_data" ;
			if(request.getParameter("qj").equals("qj")){
				mxtsql = "select DATE_FORMAT(wc.cost_data,'%Y-%m-%d') as cost_data,  	wc.cus_code,    wc.cus_name,    wc.cost_code,    wc.cost_name, "
						+" sum(wc.day_cost_yj) as yuanj,   (sum(wc.day_cost_bhs)- sum(wc.day_cost_yj)) as tiaozheng,    sum(wc.day_cost_bhs) as bhsj,"
						+"  sum(wc.day_cost_se) as shuie,     sum(wc.day_cost_hsj) as hansj,  sum(wc.cost_sl) as costsl,wc.cost_unit  "
						+" from		 wm_day_cost wc"
						+" where    wc.day_cost_yj <> 0 and  wc.cus_code = ? and to_days(wc.cost_data) >= to_days(?) and to_days(wc.cost_data) <= to_days(?)"
						+"  group by wc.cost_data, wc.cus_code , wc.cus_name , wc.cost_code , wc.cost_name order by wc.cost_data " ;
			}

			String sumsql = " select  wc.cus_code,    wc.cus_name, DATE_FORMAT(wc.cost_data,'%Y-%m-%d') AS  cost_data,  wc.cost_code,    wc.cost_name, sum(wc.day_cost_hsj) as hansj,  sum(wc.cost_sl) as costsl,wc.cost_unit  " +
					"   from wm_day_cost wc"
					+" where (wc.cost_js <> 'Y' or wc.cost_js is null) and  wc.day_cost_yj <> 0 and  wc.cus_code = ? and to_days(wc.cost_data) >= to_days(?) and to_days(wc.cost_data) <= to_days(?)"
					+"     group by wc.cus_code , wc.cus_name ,DATE_FORMAT(wc.cost_data,'%Y-%m-%d'), wc.cost_code , wc.cost_name ORDER BY wc.cost_data" ;
			if(request.getParameter("qj").equals("qj")){
				sumsql = " select  wc.cus_code,    wc.cus_name, DATE_FORMAT(wc.cost_data,'%Y-%m-%d') AS  cost_data,  wc.cost_code,    wc.cost_name, sum(wc.day_cost_hsj) as hansj,  sum(wc.cost_sl) as costsl,wc.cost_unit  " +
						"   from wm_day_cost wc"
						+" where       wc.cus_code = ? and to_days(wc.cost_data) >= to_days(?) and to_days(wc.cost_data) <= to_days(?)"
						+"  group by wc.cus_code , wc.cus_name ,DATE_FORMAT(wc.cost_data,'%Y-%m-%d'), wc.cost_code , wc.cost_name ORDER BY wc.cost_data " ;
			}
			String sumdissql = " select  wc.cus_code,    wc.cus_name, DATE_FORMAT(wc.cost_data,'%Y-%m-%d') AS  cost_data   " +
					"   from wm_day_cost wc"
					+" where (wc.cost_js <> 'Y' or wc.cost_js is null)   and  wc.cus_code = ? and to_days(wc.cost_data) >= to_days(?) and to_days(wc.cost_data) <= to_days(?)"
					+"     group by wc.cus_code , wc.cus_name ,DATE_FORMAT(wc.cost_data,'%Y-%m-%d')  ORDER BY wc.cost_data" ;
			if(request.getParameter("qj").equals("qj")){
				sumdissql = " select  wc.cus_code,    wc.cus_name, DATE_FORMAT(wc.cost_data,'%Y-%m-%d') AS  cost_data  " +
						"   from wm_day_cost wc"
						+" where         wc.cus_code = ? and to_days(wc.cost_data) >= to_days(?) and to_days(wc.cost_data) <= to_days(?)"
						+"  group by wc.cus_code , wc.cus_name ,DATE_FORMAT(wc.cost_data,'%Y-%m-%d')  ORDER BY wc.cost_data " ;
			}
				List<Map<String, Object>> result = systemService
					.findForJdbc(tsql, mvCusCost.getCusCode(),begindate,enddate);

			List<Map<String, Object>> resultmx = systemService
					.findForJdbc(mxtsql, mvCusCost.getCusCode(),begindate,enddate);

			List<Map<String, Object>> resultsum = systemService
					.findForJdbc(sumsql, mvCusCost.getCusCode(),begindate,enddate);
			List<Map<String, Object>> resultcountsum = systemService
					.findForJdbc(sumdissql, mvCusCost.getCusCode(),begindate,enddate);
			// 创建第一行
			int rowsNum = 0;
			Row row = sheet.createRow((short) 0); // 第一行空白
			rowsNum =rowsNum +1;
			Row row1 = sheet.createRow((short) 1); // 第二行标题
			row1.setHeight((short) 1450);
			Cell cellTitle = row1.createCell(5);
				cellTitle.setCellValue("对账单");
			cellTitle.setCellStyle(cs);
		
			Row row2 = sheet.createRow((short) 2); // 第一行空白
			
			Row rowHead1 = sheet.createRow((short) 3); // 头部第一行
			Cell cellHead1 = rowHead1.createCell(0);
			rowHead1.setHeight((short) 300);
			cellHead1.setCellValue("保管人" );
			cellHead1.setCellStyle(cs31);
			
			Cell cellHead11 = rowHead1.createCell(1);
			cellHead11.setCellStyle(cs31);
			Cell cellHead12 = rowHead1.createCell(2);
			cellHead12.setCellStyle(cs31);
			Cell cellHead13 = rowHead1.createCell(3);
			cellHead13.setCellStyle(cs31);
			Cell cellHead14 = rowHead1.createCell(4);
			cellHead14.setCellStyle(cs31);
			
			Cell cellHead2 = rowHead1.createCell(5);
			cellHead2.setCellValue("存货人" );
			cellHead2.setCellStyle(cs31);
			Cell cellHead16 = rowHead1.createCell(6);
			cellHead16.setCellStyle(cs31);
			Cell cellHead17 = rowHead1.createCell(7);
			cellHead17.setCellStyle(cs31);
			Cell cellHead181 = rowHead1.createCell(8);
			cellHead181.setCellStyle(cs31);
			Cell cellHead19 = rowHead1.createCell(9);
			cellHead19.setCellStyle(cs31);
			
			
			Row rowHead4 = sheet.createRow((short) 4); // 头部第二行
			Cell cellHead4 = rowHead4.createCell(0);
			rowHead4.setHeight((short) 300);
			cellHead4.setCellValue("公司名称: ");
			cellHead4.setCellStyle(cs34);
			
			Cell cellHead42 = rowHead4.createCell(1);
			cellHead42.setCellValue(ResourceUtil.getConfigByName("comfiname")  );
			cellHead42.setCellStyle(cs2);
			
	
			Cell cellHead149 = rowHead4.createCell(9);
			cellHead149.setCellStyle(cs35);
			
			Cell cellHead43 = rowHead4.createCell(5);
			cellHead43.setCellValue("公司名称:"  );
			cellHead43.setCellStyle(cs34);
			
	        MdCusEntity md = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", mvCusCost.getCusCode());
			Cell cellHead44 = rowHead4.createCell(6);
			cellHead44.setCellValue(md.getZhongWenQch() );
			cellHead44.setCellStyle(cs35);
			
			
			Row rowHead5 = sheet.createRow((short) 5); // 头部第二行
			Cell cellHead5 = rowHead5.createCell(0);
			rowHead5.setHeight((short) 300);
			cellHead5.setCellValue("公司地址: ");
			cellHead5.setCellStyle(cs34);
			
			Cell cellHead52 = rowHead5.createCell(1);
			cellHead52.setCellValue(ResourceUtil.getConfigByName("comfiaddr")   );
			cellHead52.setCellStyle(cs2);
			
			Cell cellHead53 = rowHead5.createCell(5);
			cellHead53.setCellValue("公司地址:"  );
			cellHead53.setCellStyle(cs34);
			
			Cell cellHead55 = rowHead5.createCell(6);
			cellHead55.setCellValue(md.getDiZhi() );
			cellHead55.setCellStyle(cs35);
			Cell cellHead159 = rowHead5.createCell(9);
			cellHead159.setCellStyle(cs35);
			Row rowHead6 = sheet.createRow((short) 6); // 头部第二行
			rowHead6.setHeight((short) 300);
			Cell cellHead6 = rowHead6.createCell(0);
			cellHead6.setCellValue("联系电话:");
			cellHead6.setCellStyle(cs34);
			
			Cell cellHead62 = rowHead6.createCell(1);
			cellHead62.setCellValue(ResourceUtil.getConfigByName("comfitel")   );
			cellHead62.setCellStyle(cs2);
			
			Cell cellHead63 = rowHead6.createCell(5);
			cellHead63.setCellValue("联系电话:"  );
			cellHead63.setCellStyle(cs34);
			
			Cell cellHead66 = rowHead6.createCell(6);
			cellHead66.setCellValue(md.getShouJi() );
			cellHead66.setCellStyle(cs35);
			Cell cellHead169 = rowHead6.createCell(9);
			cellHead169.setCellStyle(cs35)
			;
			Row rowHead7 = sheet.createRow((short) 7); // 头部第二行
			rowHead7.setHeight((short) 300);
			Cell cellHead7 = rowHead7.createCell(0);
			cellHead7.setCellValue("收款账号: ");
			cellHead7.setCellStyle(cs34);
			
			Cell cellHead72 = rowHead7.createCell(1);
			cellHead72.setCellValue(ResourceUtil.getConfigByName("comfibankid")    );
			cellHead72.setCellStyle(cs2);
			
			Cell cellHead73 = rowHead7.createCell(5);
			cellHead73.setCellValue("注册号:"  );
			cellHead73.setCellStyle(cs34);
			
			Cell cellHead76 = rowHead7.createCell(6);
			cellHead76.setCellValue(md.getYingYeZhiZhao());
			cellHead76.setCellStyle(cs35);
			
			
			Cell cellHead179 = rowHead7.createCell(9);
			cellHead179.setCellStyle(cs35);
			
			Row rowHead8 = sheet.createRow((short) 8); // 头部第二行
			rowHead8.setHeight((short) 300);
			Cell cellHead8 = rowHead8.createCell(0);
			cellHead8.setCellValue("开户行:");
			cellHead8.setCellStyle(cs34);
			
			Cell cellHead82 = rowHead8.createCell(1);
			cellHead82.setCellValue(ResourceUtil.getConfigByName("comfibankname")   );
			cellHead82.setCellStyle(cs2);
			
			Cell cellHead83 = rowHead8.createCell(5);
			cellHead83.setCellValue("账户号码:"  );
			cellHead83.setCellStyle(cs34);
			
			Cell cellHead86 = rowHead8.createCell(6);
			cellHead86.setCellValue("" );
			cellHead86.setCellStyle(cs35);
			
			Cell cellHead189 = rowHead8.createCell(9);
			cellHead189.setCellStyle(cs35);
			
			Row rowHead9 = sheet.createRow((short) 9); // 头部第二行
			rowHead9.setHeight((short) 300);
			Cell cellHead9 = rowHead9.createCell(0);
			cellHead9.setCellValue("注册号: ");
			cellHead9.setCellStyle(cs36);
			
			Cell cellHead92 = rowHead9.createCell(1);
			cellHead92.setCellValue(ResourceUtil.getConfigByName("comfizhucehao") );
			cellHead92.setCellStyle(cs37);
			
			
			Cell cellHead199 = rowHead9.createCell(2);
			cellHead199.setCellStyle(cs37);
			Cell cellHead1993 = rowHead9.createCell(3);
			cellHead1993.setCellStyle(cs37);
			Cell cellHead1994 = rowHead9.createCell(4);
			cellHead1994.setCellStyle(cs37);
			
			Cell cellHead93 = rowHead9.createCell(5);
			cellHead93.setCellValue("账单日期:"  );
			cellHead93.setCellStyle(cs36);
			
			Cell cellHead96 = rowHead9.createCell(6);
			cellHead96.setCellValue(DateUtils.date2Str(DateUtils.getDate(), DateUtils.date_sdf) );
			cellHead96.setCellStyle(cs37);
			
			Cell cellHead197 = rowHead9.createCell(7);
			cellHead197.setCellStyle(cs37);
			Cell cellHead1998 = rowHead9.createCell(8);
			cellHead1998.setCellStyle(cs37);
			Cell cellHead1999 = rowHead9.createCell(9);
			cellHead1999.setCellStyle(cs37);
			
			

			// 合并单元格
			CellRangeAddress c = new CellRangeAddress(0, 0, 0, 9); // 第一行空白
			CellRangeAddress c1 = new CellRangeAddress(1, 1, 1, 4);// 第二行标题
			CellRangeAddress c12 = new CellRangeAddress(1, 1, 5, 9);// 第二行标题
			CellRangeAddress c2 = new CellRangeAddress(2, 2, 0, 9);// 第三行地址
			CellRangeAddress c3 = new CellRangeAddress(3, 3, 0, 4);// 第四行电话
			CellRangeAddress c32 = new CellRangeAddress(3, 3, 5, 9);// 第四行电话
			
			CellRangeAddress c4 = new CellRangeAddress(4, 4, 1, 4);// 第5行 到货日期
			CellRangeAddress c42 = new CellRangeAddress(4, 4, 6, 9);// 第5行预约单号
			CellRangeAddress c5 = new CellRangeAddress(5, 5, 1, 4);// 第6行客户采购单号
			CellRangeAddress c52 = new CellRangeAddress(5, 5, 6, 9);// 第6行月台
			CellRangeAddress c6 = new CellRangeAddress(6, 6, 1, 4);// 第7行客户名称
			CellRangeAddress c62 = new CellRangeAddress(6, 6, 6, 9);// 第7行车号
			CellRangeAddress c7 = new CellRangeAddress(7, 7, 1, 4);// 第7行客户名称
			CellRangeAddress c72 = new CellRangeAddress(7, 7, 6, 9);// 第7行车号
			CellRangeAddress c8 = new CellRangeAddress(8, 8, 1, 4);// 第7行客户名称
			CellRangeAddress c82 = new CellRangeAddress(8, 8, 6, 9);// 第7行车号
			CellRangeAddress c9 = new CellRangeAddress(9, 9, 1, 4);// 第7行客户名称
			CellRangeAddress c92 = new CellRangeAddress(9, 9, 6, 9);// 第7行车号
			sheet.addMergedRegion(c);
			sheet.addMergedRegion(c1);
			sheet.addMergedRegion(c12);
			sheet.addMergedRegion(c2);
			sheet.addMergedRegion(c3);
			sheet.addMergedRegion(c32);
			sheet.addMergedRegion(c4);
			sheet.addMergedRegion(c5);
			sheet.addMergedRegion(c6);
			sheet.addMergedRegion(c7);
			sheet.addMergedRegion(c8);
			sheet.addMergedRegion(c9);
			
			sheet.addMergedRegion(c42);
			sheet.addMergedRegion(c52);
			sheet.addMergedRegion(c62);
			sheet.addMergedRegion(c72);
			sheet.addMergedRegion(c82);
			sheet.addMergedRegion(c92);
			
			
			Row row12 = sheet.createRow((short) 10); // 第一行空白
			row12.setHeight((short) 300);
			Row row13 = sheet.createRow((short) 11); // 第一行空白
			row13.setHeight((short) 300);
			Row rowColumnName = sheet.createRow((short) 12); // 列名
			
			String[] columnNames = { "结算期间", "上期末付款", "本期合计", "累计应付款", "结清日期" };

			String[] columnNamessum = { "计费日期", "入库数量", "入库单位", "入库操作费", "出库数量", "出库单位", "出库操作费","每日库存", "单位", "每日仓租",  };

			String[] columnNamesdetail = { "计费日期", "费用名称", "费用RMB", "计费数量", "计费单位", "备注" };

//新格式
			Row rowColumnNamesum = sheet2.createRow((short) 0); // 列名
			int colisum = 0;
			for (int i = 0; i < columnNamessum.length; i++) {

				Cell cell = rowColumnNamesum.createCell(colisum);
				colisum = colisum + 1;
				rowColumnName.setHeight((short) 300);
				cell.setCellValue(columnNamessum[i]);
				cell.setCellStyle(cs3);
			}
			DecimalFormat dfsum=new DecimalFormat(".##");
			int cellsNumsum = 0;
			for (int i = 0; i < resultcountsum.size(); i++) {
				cellsNumsum++;
				Row rowColumnValue = sheet2.createRow((short) cellsNumsum); // 行
				rowColumnValue.setHeight((short) 300);
				Cell cell1 = rowColumnValue.createCell(0);
				cell1.setCellValue(resultcountsum.get(i).get("cost_data")
						.toString());
				cell1.setCellStyle(cs3);

				for(int v =1;v<=9;v++){//先填充空数据
					Cell cellv = rowColumnValue.createCell(v);
					cellv.setCellValue("");
					cellv.setCellStyle(cs3d);
				}
   				int sumcount = 0;
				for (int j = 0; j < resultsum.size(); j++) {
				if(resultcountsum.get(i).get("cost_data").toString().equals(resultsum.get(j).get("cost_data").toString())){
						//wc.cost_name, sum(wc.day_cost_hsj) as hansj,  sum(wc.cost_sl) as costsl,wc.cost_unit
						if(resultsum.get(j).get("cost_code").toString().equals("501")){// 操作费
							sumcount =sumcount + 1;
							try{


							Cell cell3 = rowColumnValue.createCell(3);
							cell3.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("hansj")
									.toString()))));
							cell3.setCellStyle(cs3d);
							cell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

							Cell cell4 = rowColumnValue.createCell(1);

							cell4.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("costsl")
									.toString()))));
							cell4.setCellStyle(cs3d);
							cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

							Cell cell5 = rowColumnValue.createCell(2);
							cell5.setCellValue(resultsum.get(j).get("cost_unit")
									.toString());
							cell5.setCellStyle(cs3);
							}catch (Exception e){

							}
					}
						if(resultsum.get(j).get("cost_code").toString().equals("5011")){// 操作费出
							sumcount =sumcount + 1;
							try{
							Cell cell3 = rowColumnValue.createCell(6);
							cell3.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("hansj")
									.toString()))));
							cell3.setCellStyle(cs3d);
							cell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

							Cell cell4 = rowColumnValue.createCell(4);

							cell4.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("costsl")
									.toString()))));
							cell4.setCellStyle(cs3d);
							cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

							Cell cell5 = rowColumnValue.createCell(5);
							cell5.setCellValue(resultsum.get(j).get("cost_unit")
									.toString());
							cell5.setCellStyle(cs3);
						}catch (Exception e){

						}
						}
						if(resultsum.get(j).get("cost_code").toString().equals("1011")){// 仓租
							sumcount =sumcount + 1;
							try{
							Cell cell3 = rowColumnValue.createCell(9);
							cell3.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("hansj")
									.toString()))));
							cell3.setCellStyle(cs3d);
							cell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

							Cell cell4 = rowColumnValue.createCell(7);

							cell4.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("costsl")
									.toString()))));
							cell4.setCellStyle(cs3d);
							cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

							Cell cell5 = rowColumnValue.createCell(8);
							cell5.setCellValue(resultsum.get(j).get("cost_unit")
									.toString());
							cell5.setCellStyle(cs3);
						}catch (Exception e){

					}
						}
						if (sumcount ==3){
							break;
						}
					}

				}
			}





//新格式


			Row rowColumnNamedetail = sheet1.createRow((short) 0); // 列名
			int colidetail = 0;
			for (int i = 0; i < columnNamesdetail.length; i++) {

				Cell cell = rowColumnNamedetail.createCell(colidetail);
				colidetail = colidetail + 1;
				rowColumnName.setHeight((short) 300);
				cell.setCellValue(columnNamesdetail[i]);
				cell.setCellStyle(cs3);
			}

			DecimalFormat dfdetail=new DecimalFormat(".##");
			int cellsNumdetail = 0;

			for (int i = 0; i < resultmx.size(); i++) {
//				mxtsql = "select wc.cost_data, 	wc.cus_code,    wc.cus_name,    wc.cost_code,    wc.cost_name, "
//						+" sum(wc.day_cost_yj) as yuanj,   (sum(wc.day_cost_bhs)- sum(wc.day_cost_yj)) as tiaozheng,    sum(wc.day_cost_bhs) as bhsj,"
//						+"  sum(wc.day_cost_se) as shuie,     sum(wc.day_cost_hsj) as hansj,  sum(wc.cost_sl) as costsl,wc.cost_unit  "

				cellsNumdetail++;
				Row rowColumnValue = sheet1.createRow((short) cellsNumdetail); // 行
				rowColumnValue.setHeight((short) 300);

				Cell cell1 = rowColumnValue.createCell(0);
				cell1.setCellValue(resultmx.get(i).get("cost_data")
						.toString());
				cell1.setCellStyle(cs3);

				Cell cell2 = rowColumnValue.createCell(1);
				cell2.setCellValue(resultmx.get(i).get("cost_name")
						.toString());
				cell2.setCellStyle(cs3);


				Cell cell3 = rowColumnValue.createCell(2);
				cell3.setCellValue(Double.parseDouble(dfdetail.format(Double.parseDouble(resultmx.get(i).get("hansj")
						.toString()))));
				cell3.setCellStyle(cs3d);
				cell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

				Cell cell4 = rowColumnValue.createCell(3);

				cell4.setCellValue(Double.parseDouble(dfdetail.format(Double.parseDouble(resultmx.get(i).get("costsl")
						.toString()))));
				cell4.setCellStyle(cs3d);
				cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

				Cell cell5 = rowColumnValue.createCell(4);
				cell5.setCellValue(resultmx.get(i).get("cost_unit")
						.toString());
				cell5.setCellStyle(cs3);
				Cell cell6 = rowColumnValue.createCell(5);
				cell6.setCellValue("");
				cell6.setCellStyle(cs3);
			}





			int coli = 0;
			for (int i = 0; i < columnNames.length; i++) {
				if(i==0){
					coli = 0;
				}
				if(i==1){
					coli = 3;
				}
				if(i==2){
					coli = 5;
				}
				if(i==3){
					coli = 7;
				}
				if(i==4){
					coli = 9;
				}
				Cell cell = rowColumnName.createCell(coli);
				rowColumnName.setHeight((short) 300);
				cell.setCellValue(columnNames[i]);
				cell.setCellStyle(cs3);
				if(i!=4){
					Cell cell1 = rowColumnName.createCell(coli+1);
					cell1.setCellValue("");
					cell1.setCellStyle(cs3);
				}

				if(i==0){
					Cell cell4 = rowColumnName.createCell(coli+2);
					cell4.setCellValue("");
					cell4.setCellStyle(cs3);
				}
			}
			Row rowColumnName15 = sheet.createRow((short) 13); // 列名
			for (int i = 0; i < columnNames.length; i++) {
				if(i==0){
					coli = 0;
				}
				if(i==1){
					coli = 3;
				}
				if(i==2){
					coli = 5;
				}
				if(i==3){
					coli = 7;
				}
				if(i==4){
					coli = 9;
				}
				Cell cell = rowColumnName15.createCell(coli);
				rowColumnName15.setHeight((short) 400);
				cell.setCellValue("");
				if(i==0){
					cell.setCellValue(request.getParameter("begindate")+"至"+request.getParameter("enddate"));
				}
				cell.setCellStyle(cs3);
				if(i!=4){
				Cell cell1 = rowColumnName15.createCell(coli+1);
				cell1.setCellValue("");
				cell1.setCellStyle(cs3);
				}
				if(i==0){
					Cell cell4 = rowColumnName15.createCell(coli+2);
					cell4.setCellValue("");
					cell4.setCellStyle(cs3);
				}
			}
			
			CellRangeAddress c141 = new CellRangeAddress(12, 12, 0, 2);// 第5行 到货日期
			CellRangeAddress c142 = new CellRangeAddress(12, 12, 3, 4);// 第5行预约单号
			CellRangeAddress c143 = new CellRangeAddress(12, 12, 5, 6);// 第6行客户采购单号
			CellRangeAddress c144 = new CellRangeAddress(12, 12, 7, 8);// 第6行月台
			CellRangeAddress c145 = new CellRangeAddress(12, 12, 9, 9);// 第7行客户名称
			CellRangeAddress c151 = new CellRangeAddress(13, 13, 0, 2);// 第7行车号
			CellRangeAddress c152 = new CellRangeAddress(13, 13, 3, 4);// 第7行客户名称
			CellRangeAddress c153 = new CellRangeAddress(13, 13, 5, 6);// 第7行车号
			CellRangeAddress c154 = new CellRangeAddress(13, 13, 7, 8);// 第7行客户名称
			CellRangeAddress c155 = new CellRangeAddress(13, 13, 9, 9);// 第7行车号
			
			
			sheet.addMergedRegion(c141);
			sheet.addMergedRegion(c142);
			sheet.addMergedRegion(c143);
			sheet.addMergedRegion(c144);
			sheet.addMergedRegion(c145);
			sheet.addMergedRegion(c151);
			sheet.addMergedRegion(c152);
			sheet.addMergedRegion(c153);
			sheet.addMergedRegion(c154);
			sheet.addMergedRegion(c155);	
			
			Row row16 = sheet.createRow((short) 14); // 第一行空白
			row16.setHeight((short) 300);
			Row row17 = sheet.createRow((short) 15); // 第一行空白
			row17.setHeight((short) 300);
			Row rowHead18 = sheet.createRow((short) 16); // 头部第二行
			rowHead18.setHeight((short) 300);
			Cell cellHead18 = rowHead18.createCell(0);
			cellHead18.setCellValue("序号 ");
			cellHead18.setCellStyle(cs3);
			
			Cell cellHead182 = rowHead18.createCell(1);
			cellHead182.setCellValue("费用说明"  );
			cellHead182.setCellStyle(cs3);
			
			Cell cellHead1822 = rowHead18.createCell(2);
			cellHead1822.setCellValue(""  );
			cellHead1822.setCellStyle(cs3);
			Cell cellHead1823 = rowHead18.createCell(3);
			cellHead1823.setCellValue(""  );
			cellHead1823.setCellStyle(cs3);
			Cell cellHead1824 = rowHead18.createCell(4);
			cellHead1824.setCellValue(""  );
			cellHead1824.setCellStyle(cs3);
			
			
			
			Cell cellHead183 = rowHead18.createCell(5);
			cellHead183.setCellValue("调整"  );
			cellHead183.setCellStyle(cs3);
			
			Cell cellHead186 = rowHead18.createCell(6);
			cellHead186.setCellValue("金额" );
			cellHead186.setCellStyle(cs3);
			
			Cell cellHead1827 = rowHead18.createCell(7);
			cellHead1827.setCellValue(""  );
			cellHead1827.setCellStyle(cs3);
			Cell cellHead1828 = rowHead18.createCell(8);
			cellHead1828.setCellValue(""  );
			cellHead1828.setCellStyle(cs3);
			Cell cellHead1829 = rowHead18.createCell(9);
			cellHead1829.setCellValue(""  );
			cellHead1829.setCellStyle(cs3);
			
			CellRangeAddress c182 = new CellRangeAddress(16, 16, 1, 4);// 第7行客户名称
			CellRangeAddress c183 = new CellRangeAddress(16, 16, 6, 9);// 第7行车号
			sheet.addMergedRegion(c182);
			sheet.addMergedRegion(c183);
			
			int cellsNum = 16;
			Double sumyuanj =   0.00;
			Double tiaozheng =    0.00; 

			Double sumbhsj =   0.00;
			Double sumshuie =    0.00; 
			Double sumhansj =   0.00;
			DecimalFormat df=new DecimalFormat(".##");

			for (int i = 0; i < result.size(); i++) {
         		
				cellsNum++;
				Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
				rowColumnValue.setHeight((short) 300);
				
						Cell cell1 = rowColumnValue.createCell(0);
						cell1.setCellValue(cerconNo);
						cell1.setCellStyle(cs32c);
						Cell cell2 = rowColumnValue.createCell(1);
						try {
							//result.get(i).get("cost_code")
							//									.toString()+
							if(ResourceUtil.getConfigByName("sysdzd").equals("yes")){
								cell2.setCellValue(result.get(i).get("cost_name")
										.toString()+"("+ df.format(Double.parseDouble(result.get(i).get("costsl")
										.toString())) + result.get(i).get("cost_unit")
										.toString() +")" ) ;
							}else{
								cell2.setCellValue(result.get(i).get("cost_name").toString()) ;
							}

						} catch (Exception e) {
							// TODO: handle exception
						}

//						cell2.setCellStyle(cs5);
						
						
//						Cell cell22 = rowColumnValue.createCell(2);
//						cell22.setCellValue("");
//						cell22.setCellStyle(cs5);
//						
//						Cell cell23 = rowColumnValue.createCell(3);
//						cell23.setCellValue("");
//						cell23.setCellStyle(cs5);
//						
						Cell cell24 = rowColumnValue.createCell(4);
							cell24.setCellStyle(cs35);
						
						CellRangeAddress c192 = new CellRangeAddress(cellsNum, cellsNum, 1, 4);// 第7行客户名称
						sheet.addMergedRegion(c192);
                        try {
    						Cell cell4 = rowColumnValue.createCell(5);// 生产日期
    						Double doublet = Double.parseDouble(result.get(i)
									.get("tiaozheng").toString());
    						tiaozheng = tiaozheng + doublet;
    						cell4.setCellValue(df.format(doublet));
    						cell4.setCellStyle(cs35c);
    					
						} catch (Exception e) {
							// TODO: handle exception
						
						}
                        
					 try {
							Cell cell5 = rowColumnValue.createCell(6);// 温度
					
							cell5.setCellStyle(cs35c);	
							Double doublet = Double.parseDouble(result.get(i)
									.get("bhsj").toString());
							cell5.setCellValue(df.format(doublet));
											} catch (Exception e) {
												// TODO: handle exception
											}
//						Cell cell47 = rowColumnValue.createCell(7);// 生产日期
//
//						cell47.setCellStyle(cs53);
//						Cell cell48 = rowColumnValue.createCell(8);// 生产日期
//
//						cell48.setCellStyle(cs53);
//						
						Cell cell49 = rowColumnValue.createCell(9);// 生产日期

						cell49.setCellStyle(cs35c);
					 
					 
						CellRangeAddress c193 = new CellRangeAddress(cellsNum, cellsNum, 6, 9);// 第7行车号
						sheet.addMergedRegion(c193);

					 try {
						 System.out.println(result.get(i).get("yuanj").toString());
						 sumyuanj = sumyuanj +  Double.parseDouble(result.get(i).get("yuanj").toString());
						} catch (Exception e) {
							// TODO: handle exception
						}
					 try {
						 sumbhsj = sumbhsj +  Double.parseDouble(result.get(i).get("bhsj").toString());
						} catch (Exception e) {
							// TODO: handle exception
						}
								 try {
									 sumshuie = sumshuie + Double.parseDouble(result.get(i).get("shuie").toString());
						} catch (Exception e) {
							// TODO: handle exception
						}
								 try {
									 sumhansj = sumhansj + Double.parseDouble(result.get(i).get("hansj").toString());
						} catch (Exception e) {
							// TODO: handle exception
						}
			

					
				cerconNo++;
			}
			 cellsNum++;
				Row rowColumnValuebo = sheet.createRow((short) cellsNum); // 列名
				rowColumnValuebo.setHeight((short) 300);
				Cell cellb0 = rowColumnValuebo.createCell(0);
				Cell cellb1 = rowColumnValuebo.createCell(1);
				Cell cellb2 = rowColumnValuebo.createCell(2);
				Cell cellb3 = rowColumnValuebo.createCell(3);
				Cell cellb4 = rowColumnValuebo.createCell(4);
				Cell cellb5 = rowColumnValuebo.createCell(5);
				Cell cellb6 = rowColumnValuebo.createCell(6);
				Cell cellb7 = rowColumnValuebo.createCell(7);
				Cell cellb8 = rowColumnValuebo.createCell(8);
				Cell cellb9 = rowColumnValuebo.createCell(9);
				cellb0.setCellStyle(cs33);
				cellb1.setCellStyle(cs33);
				cellb2.setCellStyle(cs33);
				cellb3.setCellStyle(cs33);
				cellb4.setCellStyle(cs33);
				cellb5.setCellStyle(cs33);
				cellb6.setCellStyle(cs33);
				cellb7.setCellStyle(cs33);
				cellb8.setCellStyle(cs33);
				cellb9.setCellStyle(cs33);
				
				
				CellRangeAddress c1954 = new CellRangeAddress(cellsNum, cellsNum, 1, 4);// 第7行车号
				sheet.addMergedRegion(c1954);
				CellRangeAddress c1959 = new CellRangeAddress(cellsNum, cellsNum, 6, 9);// 第7行车号
				sheet.addMergedRegion(c1959);
				
            	 cellsNum++;
 				Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
 				rowColumnValue.setHeight((short) 300);
 				Cell cell3 = rowColumnValue.createCell(5);// 备注
 				try {
 					cell3.setCellValue("合计");
				} catch (Exception e) {
					// TODO: handle exception
				}
 	
 				cell3.setCellStyle(cs32r);
 				
 				Cell cell4 = rowColumnValue.createCell(6);// 重量
 				try {
 					cell4.setCellValue(df.format(sumbhsj));
				} catch (Exception e) {
					// TODO: handle exception
				}
 				cell4.setCellStyle(cs32r);
 				
				Cell cell47 = rowColumnValue.createCell(7);// 生产日期

				cell47.setCellStyle(cs32r);
				Cell cell48 = rowColumnValue.createCell(8);// 生产日期

				cell48.setCellStyle(cs32r);
				
				Cell cell49 = rowColumnValue.createCell(9);// 生产日期

				cell49.setCellStyle(cs32r);
 				
 				
 				
 				CellRangeAddress c193 = new CellRangeAddress(cellsNum, cellsNum, 6, 9);// 第7行车号
				sheet.addMergedRegion(c193);
				
				
				cellsNum++;
 				Row rowColumnValuew = sheet.createRow((short) cellsNum); // 列名
 				rowColumnValuew.setHeight((short) 300);
 				Cell cell5 = rowColumnValuew.createCell(5);// 备注
 				try {
 					cell5.setCellValue("税");
				} catch (Exception e) {
					// TODO: handle exception
				}
 			
 				cell5.setCellStyle(cs32r);
 				Cell cell6 = rowColumnValuew.createCell(6);// 重量
 				try {
 					cell6.setCellValue(df.format(sumshuie));
				} catch (Exception e) {
					// TODO: handle exception
				}
 				cell6.setCellStyle(cs32r);
 				
				Cell cell471 = rowColumnValuew.createCell(7);// 生产日期

				cell471.setCellStyle(cs32r);
				Cell cell481 = rowColumnValuew.createCell(8);// 生产日期

				cell481.setCellStyle(cs32r);
				
				Cell cell491 = rowColumnValuew.createCell(9);// 生产日期

				cell491.setCellStyle(cs32r);
 				
 				CellRangeAddress c194 = new CellRangeAddress(cellsNum, cellsNum, 6, 9);// 第7行车号
				sheet.addMergedRegion(c194);
				
 				cellsNum++;
 				Row rowColumnValuew1 = sheet.createRow((short) cellsNum); // 列名
 				rowColumnValuew1.setHeight((short) 300);
 				Cell cell51 = rowColumnValuew1.createCell(5);// 备注
 				try {
 					cell51.setCellValue("总计");
				} catch (Exception e) {
					// TODO: handle exception
				}
 			
 				cell51.setCellStyle(cs32ra);
 				Cell cell61 = rowColumnValuew1.createCell(6);// 重量
 				try {
 					cell61.setCellValue(df.format(sumhansj));
				} catch (Exception e) {
					// TODO: handle exception
				}
 				cell61.setCellStyle(cs32ra);
 				
 				Cell cell4711 = rowColumnValuew1.createCell(7);// 生产日期

				cell4711.setCellStyle(cs32ra);
				Cell cell4811 = rowColumnValuew1.createCell(8);// 生产日期

				cell4811.setCellStyle(cs32ra);
				
				Cell cell4911 = rowColumnValuew1.createCell(9);// 生产日期

				cell4911.setCellStyle(cs32ra);
 				
 				CellRangeAddress c195 = new CellRangeAddress(cellsNum, cellsNum, 6, 9);// 第7行车号
				sheet.addMergedRegion(c195);
				
				
				
				Row row161 = sheet.createRow((short) 1 + cellsNum); // 第一行空白
				row161.setHeight((short) 300);
				Row row171 = sheet.createRow((short) 2 + cellsNum); // 第一行空白
				row171.setHeight((short) 300);
				
			Row rowColumnInfo = sheet.createRow((short) 3 + cellsNum); // 列名
			rowColumnInfo.setHeight((short) 300);
			Cell cell49111 =  rowColumnInfo.createCell(0);
			cell49111.setCellValue(
					"备注：");
			cell49111.setCellStyle(cs31);
			Cell cell491111 =  rowColumnInfo.createCell(1);
			cell491111.setCellStyle(cs31);
			
			Cell cell491112 =  rowColumnInfo.createCell(2);
			cell491112.setCellStyle(cs31);
			
			Cell cell491113 =  rowColumnInfo.createCell(3);
			cell491113.setCellStyle(cs31);
			
			Cell cell491114 =  rowColumnInfo.createCell(4);
			cell491114.setCellStyle(cs31);
			
			Cell cell491115 =  rowColumnInfo.createCell(5);
			cell491115.setCellStyle(cs31);
			
			Cell cell491116 =  rowColumnInfo.createCell(6);
			cell491116.setCellStyle(cs31);
			Cell cell491117 =  rowColumnInfo.createCell(7);
			cell491117.setCellStyle(cs31);
			Cell cell491118 =  rowColumnInfo.createCell(8);
			cell491118.setCellStyle(cs31);
			Cell cell491119 =  rowColumnInfo.createCell(9);
			cell491119.setCellStyle(cs31);
			
			
			CellRangeAddress c16 = new CellRangeAddress(3 + cellsNum,
					3 + cellsNum, 0, 9);
			sheet.addMergedRegion(c16);
   	
			Row rowColumnInfo2 = sheet.createRow((short) 4 + cellsNum); // 列名
			rowColumnInfo2.setHeight((short) 300);
			Cell cell49112 = rowColumnInfo2.createCell(0);
			cell49112.setCellValue(ResourceUtil.getConfigByName("comfibeizhu1")
					);
			cell49112.setCellStyle(cs32);
			Cell cell491129 =  rowColumnInfo2.createCell(9);
			cell491129.setCellStyle(cs32);
			CellRangeAddress c162 = new CellRangeAddress(4 + cellsNum,
					4 + cellsNum, 0, 9);
			sheet.addMergedRegion(c162);
			
			Row rowColumnInfo3 = sheet.createRow((short) 5 + cellsNum); // 列名
			rowColumnInfo3.setHeight((short) 300);
			Cell cell49113 = rowColumnInfo3.createCell(0);
			cell49113.setCellValue(ResourceUtil.getConfigByName("comfibeizhu2")
					);
			Cell cell491139 =  rowColumnInfo3.createCell(9);
			cell491139.setCellStyle(cs32);
			 CellRangeAddress c163 = new CellRangeAddress(5 + cellsNum,
					5 + cellsNum, 0, 9);
			 cell49113.setCellStyle(cs32);
			sheet.addMergedRegion(c163);
			
			Row rowColumnInfo4 = sheet.createRow((short) 6 + cellsNum); // 列名
			rowColumnInfo4.setHeight((short) 700);
			Cell cell49114 = rowColumnInfo4.createCell(0);
			cell49114.setCellValue(ResourceUtil.getConfigByName("comfibeizhu3")
					);
			cell49114.setCellStyle(cs33);
			
			Cell cell49134 = rowColumnInfo4.createCell(1);
			cell49134.setCellStyle(cs33);
			
			Cell cell491342 = rowColumnInfo4.createCell(2);
			cell491342.setCellStyle(cs33);
			
			Cell cell491343 = rowColumnInfo4.createCell(3);
			cell491343.setCellStyle(cs33);
			
			Cell cell491344 = rowColumnInfo4.createCell(4);
			cell491344.setCellStyle(cs33);
			
			Cell cell491345 = rowColumnInfo4.createCell(5);
			cell491345.setCellStyle(cs33);
			
			Cell cell491346 = rowColumnInfo4.createCell(6);
			cell491346.setCellStyle(cs33);
			
			Cell cell491347 = rowColumnInfo4.createCell(7);
			cell491347.setCellStyle(cs33);
			
			Cell cell491348 = rowColumnInfo4.createCell(8);
			cell491348.setCellStyle(cs33);
			
			Cell cell491349 = rowColumnInfo4.createCell(9);
			cell491349.setCellStyle(cs33);
			
			CellRangeAddress c164 = new CellRangeAddress(6 + cellsNum,
					6 + cellsNum, 0, 9);
			sheet.addMergedRegion(c164);
			ByteArrayOutputStream byteArrayOutz = new ByteArrayOutputStream();
			try{
				bufferImg = ImageIO.read(new File(ResourceUtil.getConfigByName("comfidzyz")));
				ImageIO.write(bufferImg, "jpg", byteArrayOutz);
				//画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
				//anchor主要用于设置图片的属性
				anchor = new HSSFClientAnchor(0, 0, 255, 255,(short) 6, (8+ cellsNum), (short) 8, (16+ cellsNum));
				anchor.setAnchorType(3);
				//插入图片
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOutz.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

			}catch (Exception e){

			}

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
	
	/**
	 * 批量删除mv_cus_cost
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "删除成功";
		try{
			for(String id:ids.split(",")){
				MvCusCostEntity mvCusCost = systemService.getEntity(MvCusCostEntity.class, 
				id
				);
				mvCusCostService.delete(mvCusCost);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加mv_cus_cost
	 * 

	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(MvCusCostEntity mvCusCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		try{
			mvCusCostService.save(mvCusCost);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新mv_cus_cost
	 * 

	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(MvCusCostEntity mvCusCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		MvCusCostEntity t = mvCusCostService.get(MvCusCostEntity.class, mvCusCost.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(mvCusCost, t);
			mvCusCostService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * mv_cus_cost新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(MvCusCostEntity mvCusCost, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mvCusCost.getId())) {
			mvCusCost = mvCusCostService.getEntity(MvCusCostEntity.class, mvCusCost.getId());
			req.setAttribute("mvCusCostPage", mvCusCost);
		}
		return new ModelAndView("com/zzjee/bm/mvCusCost-add");
	}
	/**
	 * mv_cus_cost编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(MvCusCostEntity mvCusCost, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mvCusCost.getId())) {
			mvCusCost = mvCusCostService.getEntity(MvCusCostEntity.class, mvCusCost.getId());
			req.setAttribute("mvCusCostPage", mvCusCost);
		}
		return new ModelAndView("com/zzjee/bm/mvCusCost-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","mvCusCostController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(MvCusCostEntity mvCusCost,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(MvCusCostEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mvCusCost, request.getParameterMap());
		List<MvCusCostEntity> mvCusCosts = this.mvCusCostService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"mv_cus_cost");
		modelMap.put(NormalExcelConstants.CLASS,MvCusCostEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("mv_cus_cost列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,mvCusCosts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(MvCusCostEntity mvCusCost,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"mv_cus_cost");
    	modelMap.put(NormalExcelConstants.CLASS,MvCusCostEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("mv_cus_cost列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
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
				List<MvCusCostEntity> listMvCusCostEntitys = ExcelImportUtil.importExcel(file.getInputStream(),MvCusCostEntity.class,params);
				for (MvCusCostEntity mvCusCost : listMvCusCostEntitys) {
					mvCusCostService.save(mvCusCost);
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
	public List<MvCusCostEntity> list() {
		List<MvCusCostEntity> listMvCusCosts=mvCusCostService.getList(MvCusCostEntity.class);
		return listMvCusCosts;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		MvCusCostEntity task = mvCusCostService.get(MvCusCostEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody MvCusCostEntity mvCusCost, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MvCusCostEntity>> failures = validator.validate(mvCusCost);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			mvCusCostService.save(mvCusCost);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = mvCusCost.getId();
		URI uri = uriBuilder.path("/rest/mvCusCostController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody MvCusCostEntity mvCusCost) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MvCusCostEntity>> failures = validator.validate(mvCusCost);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			mvCusCostService.saveOrUpdate(mvCusCost);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		mvCusCostService.deleteEntityById(MvCusCostEntity.class, id);
	}
}
