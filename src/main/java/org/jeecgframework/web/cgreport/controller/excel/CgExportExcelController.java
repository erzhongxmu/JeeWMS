package org.jeecgframework.web.cgreport.controller.excel;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.online.def.CgReportConstant;
import org.jeecgframework.core.online.exception.CgReportNotFoundException;
import org.jeecgframework.core.online.util.CgReportQueryParamUtil;
import org.jeecgframework.core.util.DynamicDBUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SqlUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.vo.MapExcelConstants;
import org.jeecgframework.web.cgreport.service.core.CgReportServiceI;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Title:ExportExcelController
 * @description:报表excel导出
 * @author 赵俊夫
 * @date Aug 1, 2013 10:52:26 AM
 * @version V1.0
 */
@Controller
@RequestMapping("/cgExportExcelController")
public class CgExportExcelController extends BaseController {
	@Autowired
	private CgReportServiceI cgReportService;
	@Autowired
	private SystemService systemService;
	/**
	 * 将报表导出为excel
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("all")
	@RequestMapping(params = "exportXls")
	public String exportXls(HttpServletRequest request,
						  HttpServletResponse response, ModelMap modelMap) {
		AjaxJson ex = new AjaxJson();

		//step.1 设置，获取配置信息
		TSUser user = ResourceUtil.getSessionUserName();
		String roles = "";
		if (user != null) {
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleCode() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
			if (roles.equals("QUERY")) {

				return "没有权限";

			}
		}

		//权限判断

		String codedFileName = "报表";
		String sheetName = "导出信息";
		if (StringUtil.isNotEmpty(request.getParameter("configId"))) {
			String configId = request.getParameter("configId");
			Map<String, Object> cgReportMap = null;
			try {
				cgReportMap = cgReportService.queryCgReportConfig(configId);
			} catch (Exception e) {
				throw new CgReportNotFoundException("动态报表配置不存在!");
			}
			List<Map<String, Object>> fieldList = (List<Map<String, Object>>) cgReportMap.get(CgReportConstant.ITEMS);
			Map configM = (Map) cgReportMap.get(CgReportConstant.MAIN);
			codedFileName = configM.get("name") + codedFileName;
			String querySql = (String) configM.get(CgReportConstant.CONFIG_SQL);
			List<Map<String, Object>> items = (List<Map<String, Object>>) cgReportMap.get(CgReportConstant.ITEMS);
			Map queryparams = new LinkedHashMap<String, Object>();
			for (Map<String, Object> item : items) {
				String isQuery = (String) item.get(CgReportConstant.ITEM_ISQUERY);
				if (CgReportConstant.BOOL_TRUE.equalsIgnoreCase(isQuery)) {
					//step.2 装载查询条件
					CgReportQueryParamUtil.loadQueryParams(request, item, queryparams);
				}
			}
			//step.3 进行查询返回结果

			String dbKey = (String) configM.get("db_source");
			List<Map<String, Object>> result = null;
			if (StringUtils.isNotBlank(dbKey)) {
				result = DynamicDBUtil.findList(dbKey, SqlUtil.getFullSql(querySql, queryparams));
			} else {
				result = cgReportService.queryByCgReportSql(querySql, queryparams, -1, -1);
			}
			//--author：JueYue---------date:20150620--------for: 导出替换成EasyPoi
			List<ExcelExportEntity> entityList = new ArrayList<ExcelExportEntity>();
			for (int i = 0; i < fieldList.size(); i++) {
				entityList.add(new ExcelExportEntity(fieldList.get(i).get("field_txt").toString(), fieldList.get(i).get("field_name")));
			}
			modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
			modelMap.put(MapExcelConstants.MAP_LIST, result);
			modelMap.put(MapExcelConstants.FILE_NAME, codedFileName);
			modelMap.put(MapExcelConstants.PARAMS, new ExportParams(null, sheetName));
			return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
		}else{
			return "网络错误";
		}
//			String[] columnNames=new String[fieldList.size()];
//			String[] columnNamestype =new String[fieldList.size()];
//			String[] columnNameslenth =new String[fieldList.size()];
//			String[] columnNamesfiels =new String[fieldList.size()];
//			//标题和类型
//			for (int i = 0;i< fieldList.size();i++){
//				entityList.add(new ExcelExportEntity(fieldList.get(i).get("field_txt").toString(),fieldList.get(i).get("field_name"), 20));
//				columnNames[i] = fieldList.get(i).get("field_txt").toString();
//				columnNamestype[i] =fieldList.get(i).get("field_type").toString();
//				columnNamesfiels[i] = fieldList.get(i).get("field_name").toString();
//			}
//			//长度
//			int countsize =0;
//			for(int i=0;i< result.size();i++){
//				countsize = countsize + 1;
//				if (countsize > 100){
//					break;
//				}
//				for(int j=0;j<fieldList.size();j++){
//					String lenths=null;
//					try{
//						lenths = result.get(i).get(fieldList.get(j).get("field_name").toString()).toString();
//
//					}catch (Exception e){
//
//					}
//
//					int lenthcol = 1;
//					if(StringUtil.isNotEmpty(lenths)){
//						lenthcol = lenths.length();
//					}
//					int lenthold = 1;
//					try{
//						lenthold = Integer.parseInt(columnNameslenth[j]);
//					}catch (Exception e){
//					}
//					if(lenthcol>=lenthold){
//						columnNameslenth[j] = Integer.toString(lenthcol);
//					}else{
//						columnNameslenth[j] = Integer.toString(lenthold);
//
//					}
//				}
//			}
//
//
//			OutputStream fileOut = null;
//			try{
//				codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
//				response.setHeader("content-disposition", "attachment;filename="
//						+ codedFileName + ".xls");
//				fileOut = response.getOutputStream();
//			}catch (Exception e){
//
//			}
//
//			HSSFWorkbook wb = new HSSFWorkbook();
//			CellStyle cs = wb.createCellStyle();
//			CellStyle cs2 = wb.createCellStyle();
//			Font f = wb.createFont();
//			Font f2 = wb.createFont();
//
//			// 创建第一种字体样式（用于列名）
//			f.setFontHeightInPoints((short) 14);
//			f.setColor(IndexedColors.BLACK.getIndex());
//
//			// 创建第二种字体样式（用于值）
//			f2.setFontHeightInPoints((short) 12);
//			f2.setColor(IndexedColors.BLACK.getIndex());
//			// 设置第一种单元格的样式（用于列名）
//			cs.setFont(f);
//			cs.setBorderLeft(CellStyle.BORDER_THIN);
//			cs.setBorderRight(CellStyle.BORDER_THIN);
//			cs.setBorderTop(CellStyle.BORDER_THIN);
//			cs.setBorderBottom(CellStyle.BORDER_THIN);
//			cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//			// 设置第二种单元格的样式（用于值）
//			cs2.setFont(f2);
//			cs2.setBorderLeft(CellStyle.BORDER_THIN);
//			cs2.setBorderRight(CellStyle.BORDER_THIN);
//			cs2.setBorderTop(CellStyle.BORDER_THIN);
//			cs2.setBorderBottom(CellStyle.BORDER_THIN);
//
//
//
//			try {
//
//				//首次输出开始
//				HSSFSheet sheetsum = wb.createSheet(sheetName);
//				sheetsum.setMargin(HSSFSheet.TopMargin,0.1);// 页边距（上）
//				sheetsum.setMargin(HSSFSheet.BottomMargin,0.1);// 页边距（下）
//				sheetsum.setMargin(HSSFSheet.LeftMargin,0.5);// 页边距（左）
//				sheetsum.setMargin(HSSFSheet.RightMargin,0.0);// 页边距（右
//				for (int i = 0; i < columnNameslenth.length; i++) {
//					int width  = Integer.parseInt(columnNameslenth[i]);
//					sheetsum.setColumnWidth(i,  width * 600);
//				}
//				Row rowColumnNamesum = sheetsum.createRow((short) 0); // 列名
//				for (int i = 0; i < columnNames.length; i++) {
//					Cell cell = rowColumnNamesum.createCell(i);
//					cell.setCellValue(columnNames[i]);
//					cell.setCellStyle(cs);
//				}
//				int rownumsum = 0;
//				int cellnumsum = -1;
//				for(int i=0;i< result.size();i++) {
//					rownumsum++;
//					Row rowColumnValuesum = sheetsum.createRow((short) rownumsum); // 列名
//					rowColumnValuesum.setHeight((short) 500);
//					cellnumsum = -1;
//					for(int j  =0;j<columnNames.length;j++){
//						cellnumsum++;
//						Cell cell = rowColumnValuesum.createCell(cellnumsum);
//						try {
//							cell.setCellStyle(cs2);
//							String value = result.get(i).get(columnNamesfiels[j]).toString();
//							if(columnNamestype[j].equals("Integer")){
//								cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//								cell.setCellValue(Double.parseDouble(value));
//							}else{
//								cell.setCellValue(value);
//							}
//						}catch (Exception e){
//						}
//					}
//				}
//
//
//				//汇总输出结束
//
//
//				String groupfield = "cus_name";
//				String rp_name = "销售日汇总,销售月汇总,年汇总,年汇总报表";
//
//				String sheetnamearray[]=new String[result.size()];
//
//				if(rp_name.indexOf(configM.get("name").toString())!=-1){//如果存在则按照
//					for(int j  =0;j<result.size();j++) {
//						sheetnamearray[j]=result.get(j).get(groupfield).toString();
//					}
//				}
//
//				Set set = new HashSet();
//				for (int i = 0; i < sheetnamearray.length; i++) {
//					set.add(sheetnamearray[i]);
//				}
//				sheetnamearray = (String[]) set.toArray(new String[0]);
//
//
//				for(int out =0;out<sheetnamearray.length;out++){
//					if(StringUtil.isNotEmpty(sheetnamearray[out])){
//
//						sheetName =  sheetnamearray[out];
//						HSSFSheet sheet = wb.createSheet(sheetName);
//						sheet.setMargin(HSSFSheet.TopMargin,0.1);// 页边距（上）
//						sheet.setMargin(HSSFSheet.BottomMargin,0.1);// 页边距（下）
//						sheet.setMargin(HSSFSheet.LeftMargin,0.5);// 页边距（左）
//						sheet.setMargin(HSSFSheet.RightMargin,0.0);// 页边距（右
//						for (int i = 0; i < columnNameslenth.length; i++) {
//							int width  = Integer.parseInt(columnNameslenth[i]);
//							sheet.setColumnWidth(i,  width * 600);
//						}
//						Row rowColumnName = sheet.createRow((short) 0); // 列名
//						for (int i = 0; i < columnNames.length; i++) {
//							Cell cell = rowColumnName.createCell(i);
//							cell.setCellValue(columnNames[i]);
//							cell.setCellStyle(cs);
//						}
//						int rownum = 0;
//						int cellnum = -1;
//						for(int i=0;i< result.size();i++) {
//							if(sheetName.equals(result.get(i).get(groupfield).toString())) {
//								rownum++;
//								Row rowColumnValue = sheet.createRow((short) rownum); // 列名
//								rowColumnValue.setHeight((short) 500);
//								cellnum = -1;
//								for (int j = 0; j < columnNames.length; j++) {
//									cellnum++;
//									Cell cell = rowColumnValue.createCell(cellnum);
//									try {
//										cell.setCellStyle(cs2);
//										String value = result.get(i).get(columnNamesfiels[j]).toString();
//										if (columnNamestype[j].equals("Integer")) {
//											cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//											cell.setCellValue(Double.parseDouble(value));
//										} else {
//											cell.setCellValue(value);
//										}
//									} catch (Exception e) {
//									}
//								}
//							}
//						}
//
//					}
//					//循环输出
//				}
//
//
//
//
//				wb.write(fileOut);
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		} else {
//			throw new BusinessException("参数错误");
//		}
//		return null;

}
}
