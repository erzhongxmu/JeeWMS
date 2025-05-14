package com.base.modules.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.base.modules.util.export.ExportReportForExcel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtil {


	public static ByteArrayOutputStream downloadExcel(Map<String, Object> reportInfo,String path)  {
		try {
			Object obju = reportInfo.get("url");
			Object objt = reportInfo.get("title");
			ExportReportForExcel excel = new ExportReportForExcel();
			if (obju == null) {
				ByteArrayOutputStream bos = (ByteArrayOutputStream) excel.getStreamFormList(reportInfo);
			   return bos;
			}else{
				//判断是否需要登录BI服务器
				String savePath="";
//				String path = ResourceUtil.getConfigByName("excelmodel");
				savePath=path+obju;
				reportInfo.put("url", savePath);
				ByteArrayOutputStream bos = (ByteArrayOutputStream) excel.getStreamFormTmp(reportInfo);
				return bos;
			}
		} catch (Exception e) {
			e.printStackTrace();
 		}
		return null;
	}
//
//	public static ByteArrayOutputStream downloadExcelForMulti(Map<String, Object> reportInfo){
//		try {
//			Object obju = reportInfo.get("url");
//			Object objt = reportInfo.get("title");
//			ExportReportForExcel excel = new ExportReportForExcel();
//			//判断是否需要登录BI服务器
//			String savePath="";
//			String path = ResourceUtil.getConfigByName("excelmodel");
//			savePath=path+obju;
//			reportInfo.put("url", savePath);
//			ByteArrayOutputStream bos = (ByteArrayOutputStream) excel.getStreamForMultisheet(reportInfo);
//			return bos;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
	public static void downloadfile(HttpServletResponse response, ByteArrayOutputStream fileOut, String filename, String contentType)  {
		try{
//			String contentType ="application/msexcel";
           if(StringUtils.isEmpty(contentType)){
			   contentType ="application/msexcel";
		   }
			String fileName = filename;
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("GBK"), "ISO8859_1"));
			response.addHeader("Content-Length",fileOut.toByteArray().length+"");
			ServletOutputStream out = response.getOutputStream();
			fileOut.writeTo(out);
			out.flush();
		}catch(Exception e){

		}
	}

	public static List<List<String>> publicReadExcel(MultipartFile file, int row1, int col1, int maxcol){
		try{

		String fileName = file.getOriginalFilename();
		InputStream fis = file.getInputStream();
		Workbook wb = null;
		if("xls".equalsIgnoreCase(fileName.substring((fileName.lastIndexOf(".")+1)))){
			//获取2003版本
			wb = new HSSFWorkbook(fis);

		}
		else if("xlsx".equalsIgnoreCase(fileName.substring((fileName.lastIndexOf(".")+1)))){
			//获取2007版本
			wb = new XSSFWorkbook(fis);
		}
		else{
			throw new Exception("请选择Excel文件!");
		}
		if(wb == null){
			if(fis != null){
				fis.close();
			}
			return null;
		}
		//通过Workbook获取Excel的sheet工作表对象
		Sheet sheet = wb.getSheetAt(0);
		List<List<String>> sheetList = new ArrayList<List<String>>();
		int minRowIx = sheet.getFirstRowNum();
        int maxRowIx = sheet.getLastRowNum();
        String alertmsg="";
        if(maxRowIx <= minRowIx){
			return null;
		}
	    for (int rowIx = minRowIx+row1; rowIx <= maxRowIx; rowIx++) {
	        Row row = sheet.getRow(rowIx);
	        if(row == null){
	        	continue;
	        }
//	        int minColIx = row.getFirstCellNum();
//	        int maxColIx = row.getLastCellNum();
	        int minColIx=0;
	        int maxColIx = maxcol;
	        String cellValue = "";
	        int nowRow = rowIx + 1;//当前行
	        List<String> rowList = new ArrayList<String>();
	        for (int colIx = minColIx+col1; colIx <= maxColIx; colIx++) {
	        	Cell cell = null;
	        	int nowCol = colIx + 1;//当前列
	        	try{
	        	 cell = row.getCell(colIx);
	           }catch(NullPointerException e1){
	        	   continue;
	           }
//	           System.out.println(cell);
	           if (cell == null) {
	        	   cellValue="";
	           }else{
	           //获取cell类型
	           if(cell.getCellType() == CellType.NUMERIC){ //数据类型
				   try {
					   if(DateUtil.isCellDateFormatted(cell)){
						   Date date = cell.getDateCellValue();
						   if (date != null) {
							   cellValue = new SimpleDateFormat("yyyy/MM/dd").format(date);
						   } else {
							   cellValue = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
						   }
					   }else{
						   cellValue = getRightStr(cell.getNumericCellValue());
//						   System.out.println(cellValue);

					   }
				   } catch (Exception e) {
					   cellValue = "";
//					   alertmsg +="第 "+nowRow+" 行的第 "+nowCol+" 列数据有误（含有公式），请校验!";  //公式会提示错误
					   //throw new Exception("第 "+nowRow+" 行的第 "+nowCol+" 列数据有误，请校验!");
				   }
	           }else if(cell.getCellType() == CellType.STRING){ //字符串
	           	try{
					cellValue = cell.getStringCellValue();

				}catch (Exception e){

				}
	           }else if(cell.getCellType() == CellType.BOOLEAN){ //布尔型
				   try{
	        	   cellValue = cell.getBooleanCellValue()+"";
			   }catch (Exception e){

				   }
	           }else if(CellType.FORMULA == cell.getCellType()){ //计算公式
	        	   try {
	        		   if(DateUtil.isCellDateFormatted(cell)){
		        		   Date date = cell.getDateCellValue();
		        		   if (date != null) {
		        			   cellValue = new SimpleDateFormat("yyyy/MM/dd").format(date);
		        		   } else {
		        			   cellValue = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		        		   }
		        	   }else{
		        		   cellValue = getRightStr(cell.getNumericCellValue());
		        	   }
	        	   } catch (Exception e) {
//	        		   alertmsg +="第 "+nowRow+" 行的第 "+nowCol+" 列数据有误（含有公式），请校验!";  //公式会提示错误
	        		   //throw new Exception("第 "+nowRow+" 行的第 "+nowCol+" 列数据有误，请校验!");
	        	   }
	           }else if(CellType.FORMULA == cell.getCellType()){
	        	   cellValue = "";
	           }else{
	        	   cellValue = ""; //#开头的excel特殊字符转换为空
//	        	   alertmsg +="第 "+nowRow+" 行的第 "+nowCol+" 列数据有误，请校验!";
	        	   //throw new Exception("第 "+nowRow+" 行的第 "+nowCol+" 列数据有误，请校验!");
	           }
	           }
	           rowList.add(cellValue);
//	           System.out.println(rowIx +"行：" + cellValue + "\n");
	        }
	        sheetList.add(rowList);
	    }
	    if(!"".equals(alertmsg)){
	    	return null;
	    }
	    return sheetList;

		}catch (Exception e){
			return null;
		}
	}

	public static List<List<String>> publicReadExcel(File file, int row1, int col1, int maxcol){
		try{

			String fileName = file.getName();
			InputStream fis = new FileInputStream(file);
			Workbook wb = null;
			if("xls".equalsIgnoreCase(fileName.substring((fileName.lastIndexOf(".")+1)))){
				//获取2003版本
				wb = new HSSFWorkbook(fis);

			}
			else if("xlsx".equalsIgnoreCase(fileName.substring((fileName.lastIndexOf(".")+1)))){
				//获取2007版本
				wb = new XSSFWorkbook(fis);
			}
			else{
				throw new Exception("请选择Excel文件!");
			}
			if(wb == null){
				if(fis != null){
					fis.close();
				}
				return null;
			}
			//通过Workbook获取Excel的sheet工作表对象
			Sheet sheet = wb.getSheetAt(0);
			List<List<String>> sheetList = new ArrayList<List<String>>();
			int minRowIx = sheet.getFirstRowNum();
			int maxRowIx = sheet.getLastRowNum();
			String alertmsg="";
			if(maxRowIx <= minRowIx){
				return null;
			}
			for (int rowIx = minRowIx+row1; rowIx <= maxRowIx; rowIx++) {
				Row row = sheet.getRow(rowIx);
				if(row == null){
					continue;
				}
//	        int minColIx = row.getFirstCellNum();
//	        int maxColIx = row.getLastCellNum();
				int minColIx=0;
				int maxColIx = maxcol;
				String cellValue = "";
				int nowRow = rowIx + 1;//当前行
				List<String> rowList = new ArrayList<String>();
				for (int colIx = minColIx+col1; colIx <= maxColIx; colIx++) {
					Cell cell = null;
					int nowCol = colIx + 1;//当前列
					try{
						cell = row.getCell(colIx);
					}catch(NullPointerException e1){
						continue;
					}
//	           System.out.println(cell);
					if (cell == null) {
						cellValue="";
					}else{
						//获取cell类型
						if(cell.getCellType() == CellType.NUMERIC){ //数据类型
							try {
								if(DateUtil.isCellDateFormatted(cell)){
									Date date = cell.getDateCellValue();
									if (date != null) {
										cellValue = new SimpleDateFormat("yyyy/MM/dd").format(date);
									} else {
										cellValue = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
									}
								}else{
									cellValue = getRightStr(cell.getNumericCellValue());
//						   System.out.println(cellValue);

								}
							} catch (Exception e) {
								cellValue = "";
//					   alertmsg +="第 "+nowRow+" 行的第 "+nowCol+" 列数据有误（含有公式），请校验!";  //公式会提示错误
								//throw new Exception("第 "+nowRow+" 行的第 "+nowCol+" 列数据有误，请校验!");
							}
						}else if(cell.getCellType() == CellType.STRING){ //字符串
							try{
								cellValue = cell.getStringCellValue();

							}catch (Exception e){

							}
						}else if(cell.getCellType() == CellType.BOOLEAN){ //布尔型
							try{
								cellValue = cell.getBooleanCellValue()+"";
							}catch (Exception e){

							}
						}else if(CellType.FORMULA == cell.getCellType()){ //计算公式
							try {
								if(DateUtil.isCellDateFormatted(cell)){
									Date date = cell.getDateCellValue();
									if (date != null) {
										cellValue = new SimpleDateFormat("yyyy/MM/dd").format(date);
									} else {
										cellValue = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
									}
								}else{
									cellValue = getRightStr(cell.getNumericCellValue());
								}
							} catch (Exception e) {
//	        		   alertmsg +="第 "+nowRow+" 行的第 "+nowCol+" 列数据有误（含有公式），请校验!";  //公式会提示错误
								//throw new Exception("第 "+nowRow+" 行的第 "+nowCol+" 列数据有误，请校验!");
							}
						}else if(CellType.BLANK == cell.getCellType()){
							cellValue = "";
						}else{
							cellValue = ""; //#开头的excel特殊字符转换为空
//	        	   alertmsg +="第 "+nowRow+" 行的第 "+nowCol+" 列数据有误，请校验!";
							//throw new Exception("第 "+nowRow+" 行的第 "+nowCol+" 列数据有误，请校验!");
						}
					}
					rowList.add(cellValue);
//	           System.out.println(rowIx +"行：" + cellValue + "\n");
				}
				sheetList.add(rowList);
			}
			if(!"".equals(alertmsg)){
				return null;
			}
			return sheetList;

		}catch (Exception e){
			return null;
		}
	}

	public static String getRightStr(Double Num){
		DecimalFormat decimalFormat = new DecimalFormat("#.000000000000");
		if(Num.doubleValue()==0){
			decimalFormat = new DecimalFormat("0.000000000000");
		}
		String resultStr = decimalFormat.format(Num);

		if(resultStr.matches("^[-+]?\\d+\\.[0]+$")){
			resultStr = resultStr.substring(0, resultStr.indexOf("."));
		} else {
			String ab = "";
			String xsd = resultStr.substring(resultStr.indexOf(".") + 1, 12);
			int numa = 0;
			String nwxsd = "";

			int ws = xsd.indexOf("999999");
//			System.out.println(resultStr+"xsdws"+"xd"+xsd+"ws"+ws);
			if (ws == 0) {// 99999 整数+1
				String zs1 = resultStr.substring(0, resultStr.indexOf("."));
				int a1 = Integer.valueOf(zs1) + 1;
				resultStr = Integer.toString(a1);
			} else {
//				System.out.println(resultStr+"xsdws"+"xd"+xsd+"ws"+ws);
				for (int i = xsd.length(); i >= 1; i--) {
					ab = xsd.substring(i - 1, i);
					numa = Integer.parseInt(ab);
					if (numa > 0 || i == 1) {
						String xdsa = Integer.toString(numa);
						nwxsd = xsd.substring(0, i);
						break;
					}
				}
//				System.out.println(xsd+"dd"+nwxsd);
				String nwstr = "";
				String xddq = resultStr.substring(0, resultStr.indexOf("."));
////如果是小于1 则前面加0
				if (xddq.length() == 0) {
					nwstr = "0." + nwxsd;
//				System.out.println("0." + nwxsd);
				} else {
					if (!"0".equals(nwxsd)) {
						nwstr = resultStr.substring(0, resultStr.indexOf(".")) + "." + nwxsd;
					} else {
						nwstr = resultStr.substring(0, resultStr.indexOf("."));
					}
				}

//				System.out.println(nwstr);
				if (!"".equals(nwstr)) {
					resultStr = nwstr;
				}
			}
		}
//		System.out.println(resultStr);
		resultStr = resultStr.replaceAll(",", "");
//		resultStr = resultStr.replace("0", " ").trim().replaceAll(" ", "0");
		return resultStr;
	}
}
