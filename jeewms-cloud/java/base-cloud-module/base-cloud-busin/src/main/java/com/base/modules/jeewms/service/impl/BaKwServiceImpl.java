package com.base.modules.jeewms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.controller.dto.BaKwDTO;
import com.base.modules.jeewms.controller.dto.KwPicDataDTO;
import com.base.modules.jeewms.entity.BaKw;
import com.base.modules.jeewms.entity.WmImNoticeI;
import com.base.modules.jeewms.mapper.BaKwMapper;
import com.base.modules.jeewms.service.IBaKwService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.util.QRcodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: ba_kw
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Service
public class BaKwServiceImpl extends ServiceImpl<BaKwMapper, BaKw> implements IBaKwService {

    @Autowired
    private BaKwMapper baKwMapper;

    /**
     * 获取仓位图数据
     * @param param
     * @return
     */
    @Override
    public Map<String,Object> getKwPicData(KwPicDataDTO param) {
        List<Map<String,Object>> data = baKwMapper.getKwPicData(param);

        JSONArray jsonArray = new JSONArray();
        if (data != null && data.size() > 0) {
            for (Map<String, Object> datum : data) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bin_store", datum.get("bin_store"));
                jsonObject.put("binid", datum.get("binid"));
                jsonObject.put("des", datum.get("des"));
                jsonObject.put("tincount", datum.get("tincount"));

                try{

                    if("1".equals(param.getType())){//反向排列
                        try{
                            int hangshuint = Integer.parseInt(param.getRowNum());
                            int xnode =  Integer.parseInt(datum.get("xnode").toString());

                            jsonObject.put("xnode", hangshuint+1-xnode);
                        }catch (Exception e){

                        }



                    }else{
                        jsonObject.put("xnode", datum.get("xnode"));

                    }

                    jsonObject.put("ynode", datum.get("ynode"));

                    jsonObject.put("znode", datum.get("znode"));

                    jsonObject.put("colour", datum.get("colour"));

                }catch (Exception e){
                    throw new JeecgBootException("请输入数字!");
                }
                jsonArray.add(jsonObject);
            }
        }
        Map<String,Object> resultMap = new HashMap<>(1024);

        resultMap.put("dataList",jsonArray);
        resultMap.put("size",jsonArray.size());
        return resultMap;


    }

    /**
     * 批量启停
     * @param param
     */
    @Override
    public void batchStartOrStop(List<BaKwDTO> param) {
        if (param.size() > 0) {
            for (BaKwDTO baKwDTO : param) {
                if (StringUtils.isEmpty(baKwDTO.getId()) || StringUtils.isEmpty(baKwDTO.getStatus())) {
                    throw new JeecgBootException("id或状态为空");
                }
                BaKw baKw = new BaKw();
                baKw.setId(baKwDTO.getId());
                baKw.setStatus(baKwDTO.getStatus());
                this.updateById(baKw);
            }
        }
    }

    @Override
    public String selectRecommandBin(String goodsTypeId) {
        return this.getBaseMapper().selectRecommandBin(goodsTypeId);
    }

    @Override
    public String getStatusBaAreaByBinId(String binId) {
        return baseMapper.getStatusBaAreaByBinId(binId);
    }

    @Override
    public void doExportBq(List<String> idList, HttpServletResponse response) {
        OutputStream fileOut = null;
        BufferedImage bufferImg = null;
        List<BaKw> baKwList = baseMapper.selectBatchIds(idList);
        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            // 进行转码，使其支持中文文件名
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode("库位标签导出"+DateUtils.formatDate(new Date(),"yyyyMMddHHmmss")+".xls", "UTF-8"));

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(DateUtils.formatDate(new Date(),"yyyy-MM-dd"));
            sheet.setMargin(HSSFSheet.TopMargin, 0.1);// 页边距（上）
            sheet.setMargin(HSSFSheet.BottomMargin, 0.1);// 页边距（下）
            sheet.setMargin(HSSFSheet.LeftMargin, 0.3);// 页边距（左）
            sheet.setMargin(HSSFSheet.RightMargin, 0.0);// 页边距（右

            sheet.setColumnWidth(0, 11 * 256);
            sheet.setColumnWidth(1, 11 * 256);
            sheet.setColumnWidth(2, 11 * 256);
            sheet.setColumnWidth(3, 11 * 256);
            sheet.setColumnWidth(4, 11 * 256);

            // 创建两种单元格格式
            CellStyle cs = wb.createCellStyle();
            CellStyle cs1 = wb.createCellStyle();
            CellStyle cs2 = wb.createCellStyle();
            CellStyle cs3 = wb.createCellStyle();
            CellStyle cs4 = wb.createCellStyle();
            CellStyle cs5 = wb.createCellStyle();
            CellStyle cs5r = wb.createCellStyle();

            CellStyle cs51 = wb.createCellStyle();
            CellStyle cs52 = wb.createCellStyle();
            // 创建两种字体
            Font f = wb.createFont();
            Font f2 = wb.createFont();
            Font f5 = wb.createFont();
            // 创建第一种字体样式（用于列名）
            f.setFontHeightInPoints((short) 16);
            f.setColor(IndexedColors.BLACK.getIndex());
            f.setBold(true);

            // 创建第二种字体样式（用于值）
            f2.setFontHeightInPoints((short) 30);
            f2.setColor(IndexedColors.BLACK.getIndex());

            f5.setFontHeightInPoints((short) 26);
            f5.setColor(IndexedColors.BLACK.getIndex());

            // 设置第一种单元格的样式（用于列名）
            cs.setFont(f);
            cs.setBorderLeft(BorderStyle.NONE);
            cs.setBorderRight(BorderStyle.NONE);
            cs.setBorderTop(BorderStyle.NONE);
            cs.setBorderBottom(BorderStyle.NONE);
            cs.setAlignment(HorizontalAlignment.CENTER);

            cs1.setFont(f2);
            cs1.setBorderLeft(BorderStyle.NONE);
            cs1.setBorderRight(BorderStyle.NONE);
            cs1.setBorderTop(BorderStyle.NONE);
            cs1.setBorderBottom(BorderStyle.NONE);
            cs1.setAlignment(HorizontalAlignment.CENTER);
            cs1.setWrapText(true);

            // 设置第二种单元格的样式（用于值）
            cs2.setFont(f2);
            cs2.setBorderLeft(BorderStyle.NONE);
            cs2.setBorderRight(BorderStyle.NONE);
            cs2.setBorderTop(BorderStyle.NONE);
            cs2.setBorderBottom(BorderStyle.NONE);
            cs2.setAlignment(HorizontalAlignment.CENTER);
            cs2.setWrapText(true);

            cs5.setFont(f5);
            cs5.setBorderLeft(BorderStyle.NONE);
            cs5.setBorderRight(BorderStyle.NONE);
            cs5.setBorderTop(BorderStyle.NONE);
            cs5.setBorderBottom(BorderStyle.NONE);
            cs5.setAlignment(HorizontalAlignment.CENTER);
            cs5.setWrapText(true);

            int page = 0;

            // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            for(BaKw baKw : baKwList){
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                bufferImg = QRcodeUtil.createImage(baKw.getKwCode());
                ImageIO.write(bufferImg, "jpg", byteArrayOut);

                // anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                        (short) 0, page * 7 + 3, (short) 5, page * 7 + 6);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                // 插入图片
                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));

                Row row = sheet.createRow((short) page * 7 + 0); // 第一行空白

                Row rowHead1 = sheet.createRow((short) page * 7 + 1); // 头部第1行
                rowHead1.setHeight((short) 1070);
                Cell cellHead1 = rowHead1.createCell(0);
                cellHead1.setCellValue(baKw.getKwName());
                cellHead1.setCellStyle(cs2);

                Row rowHead2 = sheet.createRow((short) page * 7 + 2); // 头部第2行
                rowHead2.setHeight((short) 1300);
                Cell cellHead2 = rowHead2.createCell(0);
                cellHead2.setCellValue(baKw.getKwCode());
                cellHead2.setCellStyle(cs5);

                Row rowHead3 = sheet.createRow((short) page * 7 + 3); // 头部第3行
                rowHead3.setHeight((short) 1670);
                Cell cellHead3 = rowHead3.createCell(0);

                Row rowHead4 = sheet.createRow((short) page * 7 + 4); // 头部第4行
                rowHead4.setHeight((short) 1670);
                Cell cellHead4 = rowHead4.createCell(0);

                Row rowHead5 = sheet.createRow((short) page * 7 + 5); // 头部第5行
                rowHead5.setHeight((short) 1670);
                Cell cellHead5 = rowHead5.createCell(0);

                // 合并单元格
                CellRangeAddress c = new CellRangeAddress(page * 7 + 0, page * 7 + 0, 0, 4); // 第1行空白
                CellRangeAddress c1 = new CellRangeAddress(page * 7 + 1, page * 7 + 1, 0, 4);
                CellRangeAddress c2 = new CellRangeAddress(page * 7 + 2, page * 7 + 2, 0, 4);
                CellRangeAddress c3 = new CellRangeAddress(page * 7 + 3, page * 7 + 3, 0, 4);
                CellRangeAddress c4 = new CellRangeAddress(page * 7 + 4, page * 7 + 4, 0, 4);
                CellRangeAddress c5 = new CellRangeAddress(page * 7 + 5, page * 7 + 5, 0, 4);

                sheet.addMergedRegion(c);
                sheet.addMergedRegion(c1);
                sheet.addMergedRegion(c2);
                sheet.addMergedRegion(c3);
                sheet.addMergedRegion(c4);
                sheet.addMergedRegion(c5);
                page++;
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            wb.write(buffer);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(buffer.size());
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");
            fileOut = response.getOutputStream();
            fileOut.write(buffer.toByteArray());
            buffer.flush();
            fileOut.flush();
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

    @Override
    public void doExportBq2(List<String> idList, HttpServletResponse response) {
        OutputStream fileOut = null;
        BufferedImage bufferImg = null;
        List<BaKw> baKwList = baseMapper.selectBatchIds(idList);
        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            // 进行转码，使其支持中文文件名
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode("库位标签导出"+DateUtils.formatDate(new Date(),"yyyyMMddHHmmss")+".xls", "UTF-8"));

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(DateUtils.formatDate(new Date(),"yyyy-MM-dd"));
            sheet.setMargin(HSSFSheet.TopMargin, 0.1);// 页边距（上）
            sheet.setMargin(HSSFSheet.BottomMargin, 0.1);// 页边距（下）
            sheet.setMargin(HSSFSheet.LeftMargin, 0.3);// 页边距（左）
            sheet.setMargin(HSSFSheet.RightMargin, 0.0);// 页边距（右

            sheet.setColumnWidth(0, 10 * 256);
            sheet.setColumnWidth(1, 10 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 10 * 256);
            sheet.setColumnWidth(4, 10 * 256);
            sheet.setColumnWidth(5, 2 * 256);
            sheet.setColumnWidth(6, 10 * 256);
            sheet.setColumnWidth(7, 10 * 256);
            sheet.setColumnWidth(8, 10 * 256);
            sheet.setColumnWidth(9, 10 * 256);
            sheet.setColumnWidth(10, 10 * 256);

            // 创建两种单元格格式
            CellStyle cs = wb.createCellStyle();
            CellStyle cs2 = wb.createCellStyle();
            CellStyle cs5 = wb.createCellStyle();
            // 创建两种字体
            Font f = wb.createFont();
            Font f2 = wb.createFont();
            Font f5 = wb.createFont();
            // 创建第一种字体样式（用于列名）
            f.setFontHeightInPoints((short) 16);
            f.setColor(IndexedColors.BLACK.getIndex());
            f.setBold(true);

            // 创建第二种字体样式（用于值）
            f2.setFontHeightInPoints((short) 30);
            f2.setColor(IndexedColors.BLACK.getIndex());

            f5.setFontHeightInPoints((short) 26);
            f5.setColor(IndexedColors.BLACK.getIndex());

            // 设置第一种单元格的样式（用于列名）
            cs.setFont(f);
            cs.setBorderLeft(BorderStyle.NONE);
            cs.setBorderRight(BorderStyle.NONE);
            cs.setBorderTop(BorderStyle.NONE);
            cs.setBorderBottom(BorderStyle.NONE);
            cs.setAlignment(HorizontalAlignment.CENTER);

            // 设置第二种单元格的样式（用于值）
            cs2.setFont(f2);
            cs2.setBorderLeft(BorderStyle.NONE);
            cs2.setBorderRight(BorderStyle.NONE);
            cs2.setBorderTop(BorderStyle.NONE);
            cs2.setBorderBottom(BorderStyle.NONE);
            cs2.setAlignment(HorizontalAlignment.CENTER);
            cs2.setWrapText(true);

            cs5.setFont(f5);
            cs5.setBorderLeft(BorderStyle.NONE);
            cs5.setBorderRight(BorderStyle.NONE);
            cs5.setBorderTop(BorderStyle.NONE);
            cs5.setBorderBottom(BorderStyle.NONE);
            cs5.setAlignment(HorizontalAlignment.CENTER);
            cs5.setWrapText(true);

            int page = 0;

            // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            int index = 1;
            for(BaKw baKw : baKwList){
                int type = index % 2;
                if(type == 1){
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = QRcodeUtil.createImage(baKw.getKwCode());
                    ImageIO.write(bufferImg, "jpg", byteArrayOut);

                    // anchor主要用于设置图片的属性
                    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                            (short) 0, page * 7 + 3, (short) 5, page * 7 + 6);
                    anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                    // 插入图片
                    patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));

                    Row row = sheet.createRow((short) page * 7 + 0); // 第一行空白

                    Row rowHead1 = sheet.createRow((short) page * 7 + 1); // 头部第1行
                    rowHead1.setHeight((short) 1070);
                    Cell cellHead1 = rowHead1.createCell(0);
                    cellHead1.setCellValue(baKw.getKwName());
                    cellHead1.setCellStyle(cs2);

                    Row rowHead2 = sheet.createRow((short) page * 7 + 2); // 头部第2行
                    rowHead2.setHeight((short) 1300);
                    Cell cellHead2 = rowHead2.createCell(0);
                    cellHead2.setCellValue(baKw.getKwCode());
                    cellHead2.setCellStyle(cs5);

                    Row rowHead3 = sheet.createRow((short) page * 7 + 3); // 头部第3行
                    rowHead3.setHeight((short) 1670);
                    Cell cellHead3 = rowHead3.createCell(0);

                    Row rowHead4 = sheet.createRow((short) page * 7 + 4); // 头部第4行
                    rowHead4.setHeight((short) 1670);
                    Cell cellHead4 = rowHead4.createCell(0);

                    Row rowHead5 = sheet.createRow((short) page * 7 + 5); // 头部第5行
                    rowHead5.setHeight((short) 1670);
                    Cell cellHead5 = rowHead5.createCell(0);

                    // 合并单元格
                    CellRangeAddress c = new CellRangeAddress(page * 7 + 0, page * 7 + 0, 0, 4); // 第1行空白
                    CellRangeAddress c1 = new CellRangeAddress(page * 7 + 1, page * 7 + 1, 0, 4);
                    CellRangeAddress c2 = new CellRangeAddress(page * 7 + 2, page * 7 + 2, 0, 4);
                    CellRangeAddress c3 = new CellRangeAddress(page * 7 + 3, page * 7 + 3, 0, 4);
                    CellRangeAddress c4 = new CellRangeAddress(page * 7 + 4, page * 7 + 4, 0, 4);
                    CellRangeAddress c5 = new CellRangeAddress(page * 7 + 5, page * 7 + 5, 0, 4);
                    sheet.addMergedRegion(c);
                    sheet.addMergedRegion(c1);
                    sheet.addMergedRegion(c2);
                    sheet.addMergedRegion(c3);
                    sheet.addMergedRegion(c4);
                    sheet.addMergedRegion(c5);
                }
                if(type == 0){
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = QRcodeUtil.createImage(baKw.getKwCode());
                    ImageIO.write(bufferImg, "jpg", byteArrayOut);

                    // anchor主要用于设置图片的属性
                    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                            (short) 6, page * 7 + 3, (short) 11, page * 7 + 6);
                    anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                    // 插入图片
                    patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));

                    Row rowHead1 = sheet.getRow((short) page * 7 + 1); // 头部第1行
                    Cell cellHead1 = rowHead1.createCell(6);
                    cellHead1.setCellValue(baKw.getKwName());
                    cellHead1.setCellStyle(cs2);

                    Row rowHead2 = sheet.getRow((short) page * 7 + 2); // 头部第2行
                    Cell cellHead2 = rowHead2.createCell(6);
                    cellHead2.setCellValue(baKw.getKwCode());
                    cellHead2.setCellStyle(cs5);

                    Row rowHead3 = sheet.getRow((short) page * 7 + 3); // 头部第3行
                    Cell cellHead3 = rowHead3.createCell(6);

                    Row rowHead4 = sheet.getRow((short) page * 7 + 4); // 头部第4行
                    Cell cellHead4 = rowHead4.createCell(6);

                    Row rowHead5 = sheet.getRow((short) page * 7 + 5); // 头部第5行
                    Cell cellHead5 = rowHead5.createCell(6);

                    // 合并单元格
                    CellRangeAddress cr = new CellRangeAddress(page * 7 + 0, page * 7 + 0, 6, 10); // 第1行空白
                    CellRangeAddress cr1 = new CellRangeAddress(page * 7 + 1, page * 7 + 1, 6, 10);
                    CellRangeAddress cr2 = new CellRangeAddress(page * 7 + 2, page * 7 + 2, 6, 10);
                    CellRangeAddress cr3 = new CellRangeAddress(page * 7 + 3, page * 7 + 3, 6, 10);
                    CellRangeAddress cr4 = new CellRangeAddress(page * 7 + 4, page * 7 + 4, 6, 10);
                    CellRangeAddress cr5 = new CellRangeAddress(page * 7 + 5, page * 7 + 5, 6, 10);
                    sheet.addMergedRegion(cr);
                    sheet.addMergedRegion(cr1);
                    sheet.addMergedRegion(cr2);
                    sheet.addMergedRegion(cr3);
                    sheet.addMergedRegion(cr4);
                    sheet.addMergedRegion(cr5);
                }
                if(type == 0){
                    page++;
                }
                index++;
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            wb.write(buffer);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(buffer.size());
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");
            fileOut = response.getOutputStream();
            fileOut.write(buffer.toByteArray());
            buffer.flush();
            fileOut.flush();
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

    @Override
    public void doExportBq3(List<String> idList, HttpServletResponse response) {
        OutputStream fileOut = null;
        BufferedImage bufferImg = null;
        List<BaKw> baKwList = baseMapper.selectBatchIds(idList);
        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            // 进行转码，使其支持中文文件名
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode("库位标签导出"+DateUtils.formatDate(new Date(),"yyyyMMddHHmmss")+".xls", "UTF-8"));

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(DateUtils.formatDate(new Date(),"yyyy-MM-dd"));
            sheet.setMargin(HSSFSheet.TopMargin, 0.1);// 页边距（上）
            sheet.setMargin(HSSFSheet.BottomMargin, 0.1);// 页边距（下）
            sheet.setMargin(HSSFSheet.LeftMargin, 0.3);// 页边距（左）
            sheet.setMargin(HSSFSheet.RightMargin, 0.0);// 页边距（右

            sheet.setColumnWidth(0, 25 * 256);
            sheet.setColumnWidth(1, 9 * 256);
            sheet.setColumnWidth(2, 13 * 256);
            sheet.setColumnWidth(3, 13 * 256);
            sheet.setColumnWidth(4, 9 * 256);
            sheet.setColumnWidth(5, 6 * 256);
            sheet.setColumnWidth(6, 25 * 256);
            sheet.setColumnWidth(7, 9 * 256);
            sheet.setColumnWidth(8, 13 * 256);
            sheet.setColumnWidth(9, 13 * 256);
            sheet.setColumnWidth(10, 9 * 256);

            // 创建两种单元格格式
            CellStyle cs = wb.createCellStyle();
            CellStyle cs2 = wb.createCellStyle();
            CellStyle cs5 = wb.createCellStyle();
            // 创建两种字体
            Font f = wb.createFont();
            Font f2 = wb.createFont();
            Font f5 = wb.createFont();
            // 创建第一种字体样式（用于列名）
            f.setFontHeightInPoints((short) 200);
            f.setColor(IndexedColors.BLACK.getIndex());
            f.setBold(true);

            // 创建第二种字体样式（用于值）
            f2.setFontHeightInPoints((short) 22);
            f2.setColor(IndexedColors.BLACK.getIndex());

            f5.setFontHeightInPoints((short) 20);
            f5.setColor(IndexedColors.BLACK.getIndex());

            // 设置第一种单元格的样式（用于列名）
            cs.setFont(f);
            cs.setBorderLeft(BorderStyle.NONE);
            cs.setBorderRight(BorderStyle.NONE);
            cs.setBorderTop(BorderStyle.NONE);
            cs.setBorderBottom(BorderStyle.NONE);
            cs.setAlignment(HorizontalAlignment.CENTER);

            // 设置第二种单元格的样式（用于值）
            cs2.setFont(f2);
            cs2.setBorderLeft(BorderStyle.NONE);
            cs2.setBorderRight(BorderStyle.NONE);
            cs2.setBorderTop(BorderStyle.NONE);
            cs2.setBorderBottom(BorderStyle.NONE);
            cs2.setAlignment(HorizontalAlignment.CENTER);
            cs2.setWrapText(true);

            cs5.setFont(f5);
            cs5.setBorderLeft(BorderStyle.NONE);
            cs5.setBorderRight(BorderStyle.NONE);
            cs5.setBorderTop(BorderStyle.NONE);
            cs5.setBorderBottom(BorderStyle.NONE);
            cs5.setAlignment(HorizontalAlignment.CENTER);
            cs5.setWrapText(true);

            int page = 0;

            // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            int index = 1;
            for(BaKw baKw : baKwList){
                int type = index % 2;
                if(type == 1){
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = QRcodeUtil.createImage(baKw.getKwCode());
                    ImageIO.write(bufferImg, "jpg", byteArrayOut);

                    // anchor主要用于设置图片的属性
                    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                            (short) 2, page * 5 + 2, (short) 4, page * 5 + 4);
                    anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                    // 插入图片
                    patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));

                    Row rowHead1 = sheet.createRow((short) page * 5 + 0); // 头部第1行
                    rowHead1.setHeight((short) 1100);
                    Cell cellHead01 = rowHead1.createCell(0);
                    cellHead01.setCellValue(baKw.getKwCode().substring(baKw.getKwCode().length()-1));
                    cellHead01.setCellStyle(cs);

                    Cell cellHead1 = rowHead1.createCell(1);
                    cellHead1.setCellValue(baKw.getKwName());
                    cellHead1.setCellStyle(cs2);

                    Row rowHead2 = sheet.createRow((short) page * 5 + 1); // 头部第2行
                    rowHead2.setHeight((short) 1000);
                    Cell cellHead2 = rowHead2.createCell(1);
                    cellHead2.setCellValue(baKw.getKwCode());
                    cellHead2.setCellStyle(cs5);

                    Row rowHead3 = sheet.createRow((short) page * 5 + 2); // 头部第3行
                    rowHead3.setHeight((short) 1670);

                    Row rowHead4 = sheet.createRow((short) page * 5 + 3); // 头部第4行
                    rowHead4.setHeight((short) 1140);

                    Row rowHead5 = sheet.createRow((short) page * 5 + 4); // 头部第5行
                    rowHead5.setHeight((short) 1000);

                    // 合并单元格
                    CellRangeAddress c = new CellRangeAddress(page * 5 + 0, page * 5 + 0, 1, 4); // 第1行空白
                    CellRangeAddress c1 = new CellRangeAddress(page * 5 + 1, page * 5 + 1, 1, 4);
                    CellRangeAddress c2 = new CellRangeAddress(page * 5 + 0, page * 5 + 3, 0, 0);

                    sheet.addMergedRegion(c);
                    sheet.addMergedRegion(c1);
                    sheet.addMergedRegion(c2);
                }
                if(type == 0){
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    bufferImg = QRcodeUtil.createImage(baKw.getKwCode());
                    ImageIO.write(bufferImg, "jpg", byteArrayOut);

                    // anchor主要用于设置图片的属性
                    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                            (short) 8, page * 5 + 2, (short) 10, page * 5 + 4);
                    anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                    // 插入图片
                    patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));

                    Row rowHead1 = sheet.getRow((short) page * 5 + 0); // 头部第1行
                    Cell cellHead01 = rowHead1.createCell(6);
                    cellHead01.setCellValue(baKw.getKwCode().substring(baKw.getKwCode().length()-1));
                    cellHead01.setCellStyle(cs);

                    Cell cellHead1 = rowHead1.createCell(7);
                    cellHead1.setCellValue(baKw.getKwName());
                    cellHead1.setCellStyle(cs2);

                    Row rowHead2 = sheet.getRow((short) page * 5 + 1); // 头部第2行
                    Cell cellHead2 = rowHead2.createCell(7);
                    cellHead2.setCellValue(baKw.getKwCode());
                    cellHead2.setCellStyle(cs5);

                    // 合并单元格
                    CellRangeAddress cr = new CellRangeAddress(page * 5 + 0, page * 5 + 0, 7, 10); // 第1行空白
                    CellRangeAddress cr1 = new CellRangeAddress(page * 5 + 1, page * 5 + 1, 7, 10);
                    CellRangeAddress cr2 = new CellRangeAddress(page * 5 + 0, page * 5 + 3, 6, 6);
                    sheet.addMergedRegion(cr);
                    sheet.addMergedRegion(cr1);
                    sheet.addMergedRegion(cr2);
                }
                if(type == 0){
                    page++;
                }
                index++;
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            wb.write(buffer);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(buffer.size());
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");
            fileOut = response.getOutputStream();
            fileOut.write(buffer.toByteArray());
            buffer.flush();
            fileOut.flush();
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
}
