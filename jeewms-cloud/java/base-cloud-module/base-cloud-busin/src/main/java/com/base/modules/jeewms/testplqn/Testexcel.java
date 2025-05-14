package com.base.modules.jeewms.testplqn;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.common.system.util.QRcodeUtil;
import org.junit.Test;
import org.springframework.cloud.commons.util.IdUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: TODO
 * @author: scott
 * @date: 2022年04月16日 15:37
 */

public class Testexcel {

    @Test
    public void test2() throws Exception {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("test1");
        sheet.setDisplayGridlines(false);
        sheet.setColumnWidth(0, 20*200);
        sheet.setColumnWidth(1, 20*256);
        sheet.setColumnWidth(2, 20*300);
        sheet.setColumnWidth(3, 20*256);
        sheet.setColumnWidth(4, 20*265);
        sheet.setColumnWidth(5, 20*256);
        sheet.setColumnWidth(6, 20*256);

        /*第一行布局开始*/
        Row row1 = sheet.createRow(0);
        row1.setHeight((short) 1100);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("采购合同");

        CellStyle cellStyle1 = workbook.createCellStyle();

        Font font1 = workbook.createFont();
        font1.setFontName("SimSun");
        font1.setFontHeightInPoints((short) 18);

        //合并单元格
        /*CellRangeAddress addresses1 = new CellRangeAddress(0,0,0,1);
        sheet.addMergedRegion(addresses1);*/
        //水平居中
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

        cellStyle1.setFont(font1);
        cell1.setCellStyle(cellStyle1);

        FileInputStream inputStream = new FileInputStream("D:\\opt\\1.png");
        //获取图片
        int picIdex = ((XSSFWorkbook)workbook).addPicture(inputStream, Workbook.PICTURE_TYPE_PNG);
        CreationHelper creationHelper = workbook.getCreationHelper();

        //图片编辑器
        Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
        ClientAnchor clientAnchor = creationHelper.createClientAnchor();
        clientAnchor.setRow1(0);
        clientAnchor.setCol1(4);
        Picture picture = drawingPatriarch.createPicture(clientAnchor, picIdex);
        picture.resize();
        /*第一行布局结束*/

        //通用字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 15);
        //通用样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);

        /*第二行布局开始*/
        Row row2 = sheet.createRow(2);
        row2.setHeight((short) 500);
        Cell cell2 = row2.createCell(0);
        cell2.setCellValue("供应商：");
        cell2.setCellStyle(cellStyle);
        /*CellRangeAddress addresses = new CellRangeAddress(2,2,0,4);
        sheet.addMergedRegion(addresses);*/

        Cell cell3 = row2.createCell(3);
        cell3.setCellValue("采购合同号：");
        cell3.setCellStyle(cellStyle);
        /*CellRangeAddress addresses3 = new CellRangeAddress(2,2,5,10);
        sheet.addMergedRegion(addresses3);*/
        /*第二行布局结束*/

        /*第三行布局开始*/
        Row row3 = sheet.createRow(3);
        row3.setHeight((short) 500);
        Cell cell4 = row3.createCell(0);
        cell4.setCellValue("地址：");
        cell4.setCellStyle(cellStyle);
        /*CellRangeAddress addresses4 = new CellRangeAddress(3,3,0,4);
        sheet.addMergedRegion(addresses4);*/

        Cell cell5 = row3.createCell(3);
        cell5.setCellValue("日期：");
        cell5.setCellStyle(cellStyle);
        /*CellRangeAddress addresses5 = new CellRangeAddress(3,3,5,10);
        sheet.addMergedRegion(addresses5);*/
        /*第三行布局结束*/

        /*第四行布局开始*/
        Row row4 = sheet.createRow(4);
        row4.setHeight((short) 500);
        Cell cell6 = row4.createCell(0);
        cell6.setCellValue("联系人：");
        cell6.setCellStyle(cellStyle);
        /*CellRangeAddress addresses6 = new CellRangeAddress(4,4,0,4);
        sheet.addMergedRegion(addresses6);*/

        Cell cell7 = row4.createCell(3);
        cell7.setCellValue("采购名：");
        cell7.setCellStyle(cellStyle);
        /*CellRangeAddress addresses7 = new CellRangeAddress(4,4,5,10);
        sheet.addMergedRegion(addresses7);*/
        /*第四行布局结束*/

        /*第五行布局开始*/
        Row row5 = sheet.createRow(5);
        row5.setHeight((short) 500);
        Cell cell8 = row5.createCell(0);
        cell8.setCellValue("联系电话：");
        cell8.setCellStyle(cellStyle);
        /*CellRangeAddress addresses8 = new CellRangeAddress(5,5,0,4);
        sheet.addMergedRegion(addresses8);*/
        /*第五行布局结束*/

        /*第六行布局开始*/
        Font font2 = workbook.createFont();
        font2.setFontName("SimSun");
        font2.setFontHeightInPoints((short) 12);
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setBorderLeft(BorderStyle.THIN);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setWrapText(true);
        cellStyle2.setFont(font2);

        Row row6 = sheet.createRow(6);
        row6.setHeight((short) 1000);
        Cell cell9 = row6.createCell(0);
        cell9.setCellValue("订单ID");
        cell9.setCellStyle(cellStyle2);

        Cell cell10 = row6.createCell(1);
        cell10.setCellValue("ITEM 品名");
        cell10.setCellStyle(cellStyle2);

        Cell cell11 = row6.createCell(2);
        /*CellRangeAddress addresses9 = new CellRangeAddress(6,6,2,5);
        sheet.addMergedRegion(addresses9);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses9,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,addresses9,sheet);*/
        cell11.setCellValue("DESCRIPTION 产品描述");
        cell11.setCellStyle(cellStyle2);

        Cell cell12 = row6.createCell(3);
        cell12.setCellValue("QUANTITY 数量");
        cell12.setCellStyle(cellStyle2);

        Cell cell13 = row6.createCell(4);
        cell13.setCellValue("UNIT PRICE(RMB) 单价");
        /*CellRangeAddress addresses10 = new CellRangeAddress(6,6,7,8);
        sheet.addMergedRegion(addresses10);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses10,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,addresses10,sheet);*/
        cell13.setCellStyle(cellStyle2);

        Cell cell14 = row6.createCell(5);
        cell14.setCellValue("TOTAL AMOUNT(RMB) 总金额");
        /*CellRangeAddress addresses11 = new CellRangeAddress(6,6,9,10);
        sheet.addMergedRegion(addresses11);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses11,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,addresses11,sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN,addresses11,sheet);*/
        cell14.setCellStyle(cellStyle2);
        /*第六行布局结束*/


        /*第七行布局开始*/
        Row row7 = sheet.createRow(7);
        row7.setHeight((short) 6000);
        Cell cell15 = row7.createCell(0);
        cell15.setCellValue("订单ID");
        cell15.setCellStyle(cellStyle2);

        Cell cell16 = row7.createCell(1);
        cell16.setCellValue("ITEM 品名");
        cell16.setCellStyle(cellStyle2);

        Cell cell17 = row7.createCell(2);
        /*CellRangeAddress addresses12 = new CellRangeAddress(7,7,2,5);
        sheet.addMergedRegion(addresses12);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses12,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,addresses12,sheet);*/
        cell17.setCellValue("DESCRIPTION 产品描述");
        cell17.setCellStyle(cellStyle2);

        Cell cell18 = row7.createCell(3);
        cell18.setCellValue("QUANTITY 数量");
        cell18.setCellStyle(cellStyle2);

        Cell cell19 = row7.createCell(4);
        cell19.setCellValue("UNIT PRICE(RMB) 单价");
        /*CellRangeAddress addresses13 = new CellRangeAddress(7,7,7,8);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses13,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,addresses13,sheet);
        sheet.addMergedRegion(addresses13);*/
        cell19.setCellStyle(cellStyle2);

        Cell cell20 = row7.createCell(5);
        cell20.setCellValue("TOTAL AMOUNT(RMB) 总金额");
        /*CellRangeAddress addresses14 = new CellRangeAddress(7,7,9,10);
        sheet.addMergedRegion(addresses14);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses14,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,addresses14,sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN,addresses14,sheet);*/
        cell20.setCellStyle(cellStyle2);
        /*第七行布局结束*/


        /*第八行布局开始*/
        Row row8 = sheet.createRow(8);
        row8.setHeight((short) 500);
        Cell cell21 = row8.createCell(0);
        cell21.setCellValue("合计");
        CellRangeAddress addresses15 = new CellRangeAddress(8,8,0,2);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses15,sheet);
        sheet.addMergedRegion(addresses15);
        cell21.setCellStyle(cellStyle2);

        Cell cell22 = row8.createCell(3);
        cell22.setCellValue("数量");
        cell22.setCellStyle(cellStyle2);

        Cell cell23 = row8.createCell(4);
        cell23.setCellValue("单价");
        /*CellRangeAddress addresses17 = new CellRangeAddress(8,8,7,8);
        sheet.addMergedRegion(addresses17);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses17,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,addresses17,sheet);*/
        cell23.setCellStyle(cellStyle2);

        Cell cell24 = row8.createCell(5);
        cell24.setCellValue("总金额");
        /*CellRangeAddress addresses16= new CellRangeAddress(8,8,9,10);
        sheet.addMergedRegion(addresses16);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses16,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,addresses16,sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN,addresses16,sheet);*/
        cell24.setCellStyle(cellStyle2);
        /*第八行布局结束*/


        /*第九行布局开始*/
        Row row9 = sheet.createRow(9);
        row9.setHeight((short) 500);
        Cell cell25 = row9.createCell(0);
        cell25.setCellValue("开户行：");
        cell25.setCellStyle(cellStyle);
        /*第九行布局结束*/

        /*第十行布局开始*/
        Row row10 = sheet.createRow(10);
        row10.setHeight((short) 500);
        Cell cell26 = row10.createCell(0);
        /*CellRangeAddress addresses19= new CellRangeAddress(10,10,0,10);
        sheet.addMergedRegion(addresses19);*/
        cell26.setCellValue("账户：");
        cell26.setCellStyle(cellStyle);
        /*第十行布局结束*/

        /*第十一行布局开始*/
        Row row11 = sheet.createRow(11);
        row11.setHeight((short) 500);
        Cell cell27 = row11.createCell(0);
       /* CellRangeAddress addresses20= new CellRangeAddress(11,11,0,10);
        sheet.addMergedRegion(addresses20);*/
        cell27.setCellValue("账号：");
        cell27.setCellStyle(cellStyle);
        /*第十一行布局结束*/

        /*第十二行布局开始*/
        CellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setAlignment(HorizontalAlignment.LEFT);
        cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle3.setBorderLeft(BorderStyle.THIN);
        cellStyle3.setBorderTop(BorderStyle.THIN);
        cellStyle3.setBorderRight(BorderStyle.THIN);
        cellStyle3.setBorderBottom(BorderStyle.THIN);
        cellStyle3.setWrapText(true);
        cellStyle3.setFont(font2);

        Row row12 = sheet.createRow(12);
        row12.setHeight((short) 6000);
        Cell cell28 = row12.createCell(0);
       /* CellRangeAddress addresses21= new CellRangeAddress(12,12,0,10);
        sheet.addMergedRegion(addresses21);*/
       /* RegionUtil.setBorderBottom(BorderStyle.THIN,addresses21,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,addresses21,sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN,addresses21,sheet);*/
        cell28.setCellValue("text");
        cell28.setCellStyle(cellStyle3);
        /*第十二行布局结束*/


        /*第十三行布局开始*/
        CellStyle cellStyle4 = workbook.createCellStyle();
        cellStyle4.setAlignment(HorizontalAlignment.LEFT);
        cellStyle4.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle4.setBorderBottom(BorderStyle.THIN);
        cellStyle4.setFont(font);

        Row row13 = sheet.createRow(14);
        row13.setHeight((short) 500);
        Cell cell29 = row13.createCell(0);
       /* CellRangeAddress addresses22= new CellRangeAddress(14,14,0,4);
        sheet.addMergedRegion(addresses22);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses22,sheet);*/
        cell29.setCellValue("（采购）Purchaser");
        cell29.setCellStyle(cellStyle4);

        Cell cell30 = row13.createCell(1);
       /* CellRangeAddress addresses23= new CellRangeAddress(14,14,6,10);
        sheet.addMergedRegion(addresses23);
        RegionUtil.setBorderBottom(BorderStyle.THIN,addresses23,sheet);*/
        cell30.setCellValue("（供应商）Vendor");
        cell30.setCellStyle(cellStyle4);
        /*第十三行布局结束*/


        /*第十五行布局开始*/
        Row row15 = sheet.createRow(15);
        row15.setHeight((short) 500);
        Cell cell31 = row15.createCell(0);
        /*CellRangeAddress addresses24= new CellRangeAddress(15,15,0,4);
        sheet.addMergedRegion(addresses24);*/
        cell31.setCellValue("（签字）Authorized by");
        cell31.setCellStyle(cellStyle);

        Cell cell32 = row15.createCell(1);
        /*CellRangeAddress addresses25= new CellRangeAddress(15,15,6,10);
        sheet.addMergedRegion(addresses25);*/
        cell32.setCellValue("（签字）Authorized by");
        cell32.setCellStyle(cellStyle);
        /*第十五行布局结束*/


        Font font3 = workbook.createFont();
        font3.setFontName("宋体");
        font3.setFontHeightInPoints((short) 9);
        //通用样式
        CellStyle cellStyle5 = workbook.createCellStyle();
        cellStyle5.setAlignment(HorizontalAlignment.CENTER);
        cellStyle5.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle5.setFont(font3);

        /*第十六行布局开始*/
        Row row16 = sheet.createRow(19);
        row16.setHeight((short) 500);
        Cell cell33 = row16.createCell(0);
       /* CellRangeAddress addresses26= new CellRangeAddress(19,19,0,10);
        sheet.addMergedRegion(addresses26);*/
        cell33.setCellValue("商贸有限公司");
        cell33.setCellStyle(cellStyle5);
        /*第十六行布局结束*/

        /*第十七行布局开始*/
        Row row17 = sheet.createRow(20);
        row17.setHeight((short) 500);
        Cell cell34 = row17.createCell(0);
        /*CellRangeAddress addresses27= new CellRangeAddress(20,20,0,10);
        sheet.addMergedRegion(addresses27);*/
        cell34.setCellValue("(深圳市福田区红荔路上步工业区201栋309)");
        cell34.setCellStyle(cellStyle5);
        /*第十七行布局结束*/

        /*第十八行布局开始*/
        Row row18 = sheet.createRow(21);
        row18.setHeight((short) 500);
        Cell cell35 = row18.createCell(0);
       /* CellRangeAddress addresses28= new CellRangeAddress(21,21,0,10);
        sheet.addMergedRegion(addresses28);*/
        cell35.setCellValue("HOSPITALITY COMPANY LIMITED");
        cell35.setCellStyle(cellStyle5);
        /*第十八行布局结束*/

        /*第十九行布局开始*/
        Row row19 = sheet.createRow(22);
        row19.setHeight((short) 500);
        Cell cell36 = row19.createCell(0);
       /* CellRangeAddress addresses29= new CellRangeAddress(22,22,0,10);
        sheet.addMergedRegion(addresses29);*/
        cell36.setCellValue("Room 309 3F 201 Ridge, Shangbu Industrial, Hongli Road, Futian district, Shenzhen, China. PC: 518028");
        cell36.setCellStyle(cellStyle5);
        /*第十九行布局结束*/

        FileOutputStream outputStream = new FileOutputStream("D:\\opt\\test.xlsx");
        workbook.write(outputStream);
        outputStream.close();
        System.out.println("1");
    }

    @Test
    public void test3() {
        String tinids = "T000001";
        String substring = tinids.substring(tinids.length() - 4, tinids.length());
        Integer integer = Convert.toInt(tinids.substring(tinids.length() - 4, tinids.length())) + 1;
        tinids ="T" + String.format("%06d", integer);
        System.out.println(tinids);
        System.out.println(substring);
    }

    @Test
    public void test4() {
        String yyMMdd = DateUtil.format(new Date(), "yyMMdd");
        String tinids = "SHIP#"+yyMMdd+"00";
        String substring = tinids.substring(tinids.length() - 2, tinids.length());
        Integer integer = Convert.toInt(substring) + 1;
        substring = String.format("%02d", integer);
        String substring1 = tinids.substring(0, 11) + substring;
        System.out.println(tinids);
        System.out.println(substring);
        System.out.println(yyMMdd);
        System.out.println(substring1);
    }

    @Test
    public void test5() {
        List<Integer> integerList = Arrays.asList(1,1,  3,2, 4, 5, 6);

        List<Integer> collect = integerList.stream().filter(integer -> integer > 0).collect(Collectors.toList());
        System.out.println(collect);

        List<Integer> collect1 = integerList.stream().filter(integer -> integer > 0).distinct().sorted().collect(Collectors.toList());
        System.out.println(collect1);

        List<Integer> collect2 = integerList.stream().limit(3).collect(Collectors.toList());
        System.out.println(collect2);

        Optional<Integer> first = integerList.stream().filter(integer -> integer > 0).findFirst();
        System.out.println(first.orElse(-1));

        Integer reduce = integerList.stream().reduce(0, Integer::max);
        System.out.println(reduce);
    }


    @Test
    public void test6() {
        BigDecimal bg = BigDecimal.valueOf(70);
        BigDecimal om2 = BigDecimal.valueOf(20);
        System.out.println(om2.divideAndRemainder(bg)[1]);//取余
    }

    @Test
    public void test7() {
        List<String> list = new ArrayList<>();
        list.add("lee");
        list.add("lee1");
        list.add("lee2");
        System.out.println(list.size());
        System.out.println(list.get(0));
        System.out.println(list.contains("lee"));
        System.out.println(list.indexOf("lee"));
        System.out.println(list.subList(0,3));
    }


    @Test
    public void test8() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("c");

        Map<String, Integer> map = new HashMap<>(1024);
        for (String s : list) {
            Integer i = 1;
            if (map.get(s) !=null){
                i = map.get(s)+1;
            }
            map.put(s,i);
        }
//        System.out.println(map.toString());
        for (Map.Entry<String, Integer> stringIntegerEntry : map.entrySet()) {
            Integer value = stringIntegerEntry.getValue();
            System.out.println(value);
        }

    }
}
