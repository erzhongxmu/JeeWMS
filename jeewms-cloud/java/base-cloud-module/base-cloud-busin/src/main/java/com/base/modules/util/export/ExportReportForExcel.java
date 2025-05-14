package com.base.modules.util.export;

//import com.jeecg.util.DayiUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import com.base.modules.util.DayiUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class ExportReportForExcel implements IExportReport {

	@Override
	public void createReport(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reportInfo) throws Exception {
		if (reportInfo.get("cellInfo") == null) {
			throw new Exception("没有导出数据");
		}
		String showFileName = URLEncoder.encode(reportInfo.get("title") + ".xls", "UTF-8");
		showFileName = new String(showFileName.getBytes("iso8859-1"), "gb2312");

		response.reset();
		response.setContentType("application/msexcel");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=30");
		response.setHeader("Content-disposition", "attachment; filename=" + new String(showFileName.getBytes("gb2312"), "iso8859-1"));

		ServletOutputStream out = response.getOutputStream();
		ByteArrayOutputStream bos = (ByteArrayOutputStream)getStream(reportInfo);
		response.setContentLength(bos.size());
		bos.writeTo(out);
		out.close();
		out.flush();
		bos.close();
		bos.flush();
	}

	@Override
	public ByteArrayOutputStream getStream(Map<String, Object> reportInfo) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet((String)reportInfo.get("title"));
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)10);
		font.setBold(true);
		cellStyle.setFont(font);

		String[] excelHeader = (String[])reportInfo.get("header");
		int rowIndex = 0;
		XSSFRow row = sheet.createRow(rowIndex);
		XSSFCell cell = null;
		for (int i = 0; i < excelHeader.length; i++) {
			cell = row.createCell((short)i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		Object[][] excelbody = (Object[][])reportInfo.get("cellInfo");
		for (int i = 0; i < excelbody.length; i++) {
			Object[] dataExcel = excelbody[i];
			rowIndex++; row = sheet.createRow(rowIndex);
			for (int j = 0; j < dataExcel.length; j++) {
				cell = row.createCell((short)j);
				String value=(String)dataExcel[j];
				cell.setCellValue(value);
				cell.setCellStyle(cellStyle);
				int w=sheet.getColumnWidth((short)j);
				if(value!=null&&value.length()>w/256){
					sheet.setColumnWidth((short)j, (short)(value.length()*256));
				}
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);
		return bos;
	}

	public void createReportFormList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reportInfo) throws Exception {
		if (reportInfo.get("result") == null) {
			throw new Exception("没有导出数据");
		}
		String showFileName = URLEncoder.encode(reportInfo.get("title") + ".xls", "UTF-8");
		showFileName = new String(showFileName.getBytes("iso8859-1"), "gb2312");

		response.reset();
		response.setContentType("application/msexcel");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=30");
		response.setHeader("Content-disposition", "attachment; filename=" + new String(showFileName.getBytes("gb2312"), "iso8859-1"));

		ServletOutputStream out = response.getOutputStream();
		ByteArrayOutputStream bos = (ByteArrayOutputStream)getStreamFormList(reportInfo);
		response.setContentLength(bos.size());
		bos.writeTo(out);
		out.close();
		out.flush();
		bos.close();
		bos.flush();
	}

	public OutputStream getStreamFormList(Map<String, Object> reportInfo) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet((String)reportInfo.get("title"));
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)10);
		font.setBold(true);
		cellStyle.setFont(font);

		String[] excelHeader = (String[])reportInfo.get("header");
		int rowIndex = 0;
		HSSFRow row = sheet.createRow(rowIndex);
		HSSFCell cell = null;
		for (int i = 0; i < excelHeader.length; i++) {
			cell = row.createCell((short)i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(cellStyle);
		}
		cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		String[] cols = (String[])reportInfo.get("cols");
		List datas = (List)reportInfo.get("result");
		for (int i = 0; i < datas.size(); i++) {
			Map datapdf = (Map)datas.get(i);
			for (int j = 0; j < cols.length; j++) {
				cell = row.createCell((short)j);
				String value= DayiUtils.toString(datapdf.get(cols[j]));
				cell.setCellValue("".equals(value)?null:value);
				cell.setCellStyle(cellStyle);
				int w=sheet.getColumnWidth((short)j);
				if(value!=null&&value.length()>w/256){
					sheet.setColumnWidth((short)j, (short)(value.length()*256));
				}
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);
		return bos;
	}

	public OutputStream getStreamFormTmp(Map<String, Object> reportInfo) throws Exception {
		Object obju=reportInfo.get("url");
		Object objr=reportInfo.get("rowIndex");
		if(obju==null||objr==null){
			return null;
		}
		String url=obju.toString();
		int rows=(Integer)objr;
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(url));
		HSSFSheet sheet = wb.getSheetAt(0);
		int rowIndex = rows;
		HSSFRow row = null;
		HSSFCell cell = null;
		String[] cols = (String[]) reportInfo.get("cols");
		List datas = (List) reportInfo.get("result");
		if(datas!=null&&datas.size()>0){
			for (int i = 0; i < datas.size(); i++) {
				Map datapdf = (Map) datas.get(i);
				row = sheet.createRow(rowIndex);
				for (int j = 0; j < cols.length; j++) {
//				if(i == 350 ){
//					System.out.println(j);
//				}
					cell = row.createCell((short) j);
					String value = DayiUtils.toString(datapdf.get(cols[j]));
					String temp ="ss";
					if(!".".equals(value)) {
						temp = value.split("\\.")[0];
					}
					if((!"".equals(value) &&("".equals(temp) || "-".equals(temp))) || (temp.matches("^[-+]?\\d+$") && j!=0)){
						if(value.split("\\.").length>1){
							if(value.split("\\.")[1].matches("^\\d+$") && value.split("\\.").length==2){
								cell.setCellValue(Double.valueOf(value));
							}else{
								cell.setCellValue(value);
							}
						}else{
							try{
								cell.setCellValue(Double.valueOf(value));
							}catch(Exception e){
								cell.setCellValue(value);
							}
						}
					}else{
						cell.setCellValue("".equals(value)?null:value);
					}
					int w = sheet.getColumnWidth((short) j);
					if (value != null && value.length() > w / 256) {
						sheet.setColumnWidth((short) j, (short) (value.length() * 256));
					}
				}
				rowIndex++;
				//System.out.println(i+";"+datapdf.get("X1"));
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);
		return bos;
	}

	public OutputStream getStreamFormTmpForMulti(Map<String, Object> reportInfo) throws Exception {
		Object obju=reportInfo.get("url");
		Object objr=reportInfo.get("rowIndex");
		if(obju==null||objr==null){
			return null;
		}
		String url=obju.toString();
		int rows=(Integer)objr;
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(url));
		List colslist=(List)reportInfo.get("cols");
		List datalist=(List)reportInfo.get("result");
		for(int m=0;m<colslist.size();m++){
			HSSFSheet sheet = wb.getSheetAt(m);
			int rowIndex = rows;
			HSSFRow row = null;
			HSSFCell cell = null;
//			String[] cols = (String[]) reportInfo.get("cols");
//			List datas = (List) reportInfo.get("result");
			String[] cols = (String[]) colslist.get(m);
			List datas = (List) datalist.get(m);
			for (int i = 0; i < datas.size(); i++) {
				Map datapdf = (Map) datas.get(i);
				row = sheet.createRow(rowIndex);
				for (int j = 0; j < cols.length; j++) {
	//				if(i == 350 ){
	//					System.out.println(j);
	//				}
					cell = row.createCell((short) j);
					String value = DayiUtils.toString(datapdf.get(cols[j]));
					String temp ="ss";
					if(!".".equals(value)) {
						temp = value.split("\\.")[0];
					}
					if((!"".equals(value) &&("".equals(temp) || "-".equals(temp))) || (temp.matches("^[-+]?\\d+$") && j!=0)){
						if(value.split("\\.").length>1){
							if(value.split("\\.")[1].matches("^\\d+$") && value.split("\\.").length==2){
								cell.setCellValue(Double.valueOf(value));
							}else{
								cell.setCellValue(value);
							}
						}else{
							try{
							cell.setCellValue(Double.valueOf(value));
							}catch(Exception e){
								cell.setCellValue(value);
							}
						}
					}else{
							cell.setCellValue("".equals(value)?null:value);
					}
					int w = sheet.getColumnWidth((short) j);
					if (value != null && value.length() > w / 256) {
						sheet.setColumnWidth((short) j, (short) (value.length() * 256));
					}
				}
				rowIndex++;
				//System.out.println(i+";"+datapdf.get("X1"));
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);
		return bos;
	}

	public OutputStream getStreamForMultisheet(Map<String, Object> reportInfo) throws Exception {
		Object obju=reportInfo.get("url");
		Object objr=reportInfo.get("rowIndex");
		if(obju==null||objr==null){
			return null;
		}
		String url=obju.toString();
		int rows=(Integer)objr;
//		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(url));
//		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(url));


		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(url));
//		SXSSFWorkbook wb = new SXSSFWorkbook(wb1,100);
		List colslist=(List)reportInfo.get("cols");
		List datalist=(List)reportInfo.get("result");
		//XSSFCellStyle style = wb.createCellStyle();
//		XSSFFont font=wb.createFont();
//		font.setColor((short)200);
//		style.setFont(font);
		for(int m=0;m<colslist.size();m++){
			XSSFSheet sheet = wb.getSheetAt(m);
//			SXSSFSheet sheet = (SXSSFSheet)wb.getSheetAt(m);
			int rowIndex = rows;
//			HSSFRow row = null;
//			HSSFCell cell = null;
			XSSFRow row = null;
			Cell cell = null;
			String[] cols = (String[]) colslist.get(m);
			List datas = (List) datalist.get(m);
			for (int i = 0; i < datas.size(); i++) {
				Map datapdf = (Map) datas.get(i);
				row = sheet.createRow(rowIndex);
//				row = sheet.createRow(rowIndex);
				for (int j = 0; j < cols.length; j++) {
					cell = row.createCell((int) j);
//					cell.setCellType(CellStyle.);
					//cell.setCellStyle(style);
					String value = DayiUtils.toString(datapdf.get(cols[j]));
					String temp ="ss";
					if(!".".equals(value)) {
						temp = value.split("\\.")[0];
					}
					if((!"".equals(value) &&("".equals(temp) || "-".equals(temp))) || (temp.matches("^[-+]?\\d+$") && j!=0)){
						if(value.split("\\.").length>1){
							if(value.split("\\.")[1].matches("^\\d+$") && value.split("\\.").length==2){
								cell.setCellValue(Double.valueOf(value));
							}else{
								cell.setCellValue(value);
							}
						}else {
							try{
								cell.setCellValue(Double.valueOf(value));
								}catch(Exception e){
									cell.setCellValue(value);
							}
						}
					}else{
							cell.setCellValue("".equals(value)?null:value);
					}
				}
				rowIndex++;
			}
		}
		wb.setForceFormulaRecalculation(true);//计算公式

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);
		return bos;
	}





	public OutputStream getStreamForMultisheet2003(Map<String, Object> reportInfo) throws Exception {
		Object obju=reportInfo.get("url");
		Object objr=reportInfo.get("rowIndex");
		if(obju==null||objr==null){
			return null;
		}
		String url=obju.toString();
		int rows=(Integer)objr;
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(url));
//		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(url));
		List colslist=(List)reportInfo.get("cols");
		List datalist=(List)reportInfo.get("result");
		//XSSFCellStyle style = wb.createCellStyle();
//		XSSFFont font=wb.createFont();
//		font.setColor((short)200);
//		style.setFont(font);
		for(int m=0;m<colslist.size();m++){
			HSSFSheet sheet = wb.getSheetAt(m);
//			XSSFSheet sheet = wb.getSheetAt(m);
			int rowIndex = rows;
			HSSFRow row = null;
			HSSFCell cell = null;
//			XSSFRow row = null;
//			XSSFCell cell = null;
			String[] cols = (String[]) colslist.get(m);
			List datas = (List) datalist.get(m);
			for (int i = 0; i < datas.size(); i++) {
				Map datapdf = (Map) datas.get(i);
				row = sheet.createRow(rowIndex);
				for (int j = 0; j < cols.length; j++) {
					cell = row.createCell((int) j,CellType.NUMERIC);
					cell.setCellType(CellType.STRING);
					//cell.setCellStyle(style);
					String value = DayiUtils.toString(datapdf.get(cols[j]));
					String temp ="ss";
					if(!".".equals(value)) {
						temp = value.split("\\.")[0];
					}
					if((!"".equals(value) &&("".equals(temp) || "-".equals(temp))) || (temp.matches("^[-+]?\\d+$") && j!=0)){
						if(value.split("\\.").length>1){
							if(value.split("\\.")[1].matches("^\\d+$") && value.split("\\.").length==2){
								cell.setCellValue(Double.valueOf(value));
							}else{
								cell.setCellValue(value);
							}
						}else {
							try{
								cell.setCellValue(Double.valueOf(value));
								}catch(Exception e){
									cell.setCellValue(value);
							}
						}
					}else{
							cell.setCellValue("".equals(value)?null:value);
					}
//					int w = sheet.getColumnWidth((int) j);
//					if (value != null && value.length() > w / 256) {
//						sheet.setColumnWidth((int) j,
//								(short) (value.length() * 256));
//					}
				}
				rowIndex++;
//				System.out.println(i+":"+DateUtil.date2Str(new java.util.Date(), "yyyy-MM-dd hh:mm:ss"));
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);
		return bos;
	}

}
