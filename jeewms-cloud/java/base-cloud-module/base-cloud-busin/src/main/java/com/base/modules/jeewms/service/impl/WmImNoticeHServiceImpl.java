package com.base.modules.jeewms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.controller.dto.WmImNoticeHDTO;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.*;
import com.base.modules.jeewms.pdaapi.ApiEntity;
import com.base.modules.jeewms.service.*;
import com.base.modules.util.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.jacoco.agent.rt.internal_43f5073.CoverageTransformer;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.SysUserRemoteApi;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.QRcodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: wm_im_notice_h
 * @Author: base-boot
 * @Date: 2021-05-21
 * @Version: V1.0
 */
@Service
public class WmImNoticeHServiceImpl extends ServiceImpl<WmImNoticeHMapper, WmImNoticeH> implements IWmImNoticeHService {

    public static final String wm_sta0 = "初始化";
    public static final String wm_sta1 = "计划中";
    public static final String wm_sta2 = "操作中";
    public static final String wm_sta3 = "已删除";
    public static final String wm_sta4 = "已完成";
    public static final String wm_sta5 = "复核中";
    public static final String wm_sta6 = "复核完成";

    @Value("${jeecg.path.upload}")
    private String upLoadPath;
    @Autowired
    private WmImNoticeIMapper wmImNoticeIMapper;
    @Autowired
    private SysUserRemoteApi sysUserRemoteApi;
    @Autowired
    private IWmPlatIoService wmPlatIoService;
    @Autowired
    private IMdSupService mdSupService;
    @Autowired
    private IMvGoodsService mvGoodsService;
    @Autowired
    private IWmImNoticeIService wmImNoticeIService;
    @Autowired
    private IWmInQmIService wmInQmIService;
    @Autowired
    private IMdCusService mdCusService;
    @Autowired
    private WmInQmIMapper wmInQmIMapper;
    @Autowired
    private WmImNoticeHMapper wmImNoticeHMapper;
    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private IBaKwService baKwService;
    @Autowired
    private BaKwMapper baKwMapper;
    @Autowired
    private IMdGoodsItemService mdGoodsItemService;


    /**
     * 添加进货
     *
     * @param param
     */
    @Override
    @Transactional
    public void add(WmImNoticeHDTO param) {
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        BeanUtils.copyProperties(param, wmImNoticeH);
        String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
        String noticeid = null;
        if (StringUtils.isEmpty(param.getOrderTypeCode())) {
            wmImNoticeH.setOrderTypeCode("01");
        }
        //查询今天入库量
        int count = this.lambdaQuery()
                .eq(WmImNoticeH::getOrderTypeCode, wmImNoticeH.getOrderTypeCode())
                .apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'")
                .count();
        int newcount = count + 1;

        if ("03".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "TH"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("01".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "RK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
            ;
        } else if ("04".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "YK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
            ;
        } else if ("09".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "QTRK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
            ;
        } else {
            noticeid = "QT"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
            ;
        }
        wmImNoticeH.setNoticeId(noticeid);
        wmImNoticeH.setImSta(wm_sta1);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

//        Result<Set<String>> resultRole =  sysUserRemoteApi.getUserRolesSet(loginUser.getUsername());
//        Set<String> roleList = resultRole.getResult();
//        if (roleList !=null && roleList.size() > 0) {
//            for (String roleName : roleList) {
//                if ("CUS".equals(roleName)) {
//                    wmImNoticeH.setCusCode(loginUser.getUsername());
//                }
//            }
//        }
        this.save(wmImNoticeH);


        //月台
        WmPlatIo wmPlatIo = new WmPlatIo();
        wmPlatIo.setCarno(wmImNoticeH.getImCarNo());
        wmPlatIo.setDocId(wmImNoticeH.getNoticeId());
        wmPlatIo.setPlanIndata(wmImNoticeH.getImData());
        wmPlatIo.setPlatId(wmImNoticeH.getPlatformCode());
        wmPlatIo.setPlatSta(wm_sta1);
        wmPlatIo.setPlatBeizhu("司机:" + wmImNoticeH.getImCarDri() + "电话:"
                + wmImNoticeH.getImCarMobile());
        wmPlatIoService.save(wmPlatIo);

        //供应商
        MdSup mdSup = mdSupService.lambdaQuery().eq(MdSup::getGysBianMa, param.getCusCode()).one();
        if (mdSup != null) {
            wmImNoticeH.setSupName(mdSup.getZhongWenQch());
        }


    }


    @Override
    public IPage<WmImNoticeH> selectCusList(WmImNoticeH wmImNoticeH, Integer pageNo, Integer pageSize, HttpServletRequest req) {

        QueryWrapper<WmImNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmImNoticeH, req.getParameterMap());
        queryWrapper.lambda().orderByDesc(WmImNoticeH::getCreateTime);
        //queryWrapper.lambda().orderByDesc(WmImNoticeH::getImSta);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

//        Result<Set<String>> resultRole =  sysUserRemoteApi.getUserRolesSet(loginUser.getUsername());
//        Set<String> roleList = resultRole.getResult();
//        if (roleList !=null && roleList.size() > 0) {
//            for (String roleName : roleList) {
//                if ("CUS".equals(roleName)) {
//                    queryWrapper.lambda().eq(WmImNoticeH::getCusCode,loginUser.getUsername());
//                }
//            }
//        }
        Page<WmImNoticeH> page = new Page<WmImNoticeH>(pageNo, pageSize);
        return this.page(page, queryWrapper);
    }

    @Override
    public IPage<WmImNoticeH> selectCressList(WmImNoticeH wmImNoticeH, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        QueryWrapper<WmImNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmImNoticeH, req.getParameterMap());
        queryWrapper.lambda().orderByDesc(WmImNoticeH::getCreateTime);
        queryWrapper.lambda().eq(WmImNoticeH::getOrderTypeCode, "04");
        Page<WmImNoticeH> page = new Page<WmImNoticeH>(pageNo, pageSize);
        return this.page(page, queryWrapper);
    }

    /**
     * 完成
     *
     * @param id
     */
    @Override
    public void complete(String id) {
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        wmImNoticeH.setId(id);
//        wmImNoticeH.setImSta(wm_sta4);
        this.updateById(wmImNoticeH);

        //查询子表
        List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.lambdaQuery()
                .eq(WmImNoticeI::getImNoticeId, wmImNoticeH.getNoticeId()).list();
        if (wmImNoticeIList.size() > 0) {
            for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
                wmImNoticeI.setBinPre("Y");
            }
            wmImNoticeIService.updateBatchById(wmImNoticeIList);
        }
    }

    /**
     * 打印验收单
     *
     * @param id
     * @param response
     */
    @Override
    public void doPrintYsd(String id, HttpServletResponse response) {
        OutputStream fileOut = null;
        BufferedImage bufferImg = null;
//		String codedFileName = null;
        WmImNoticeH wmImNoticeH = this.getById(id);


        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
//			StringBuffer sber = new StringBuffer();

            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//			bufferImg = ImageIO.read(BarcodeUtil.generateToStream(wmImNoticeH
//					.getNoticeId()));
            bufferImg = QRcodeUtil.createImage(wmImNoticeH
                    .getNoticeId());

            // 进行转码，使其支持中文文件名
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode("验收单-" + wmImNoticeH.getNoticeId() + ".xls", "UTF-8"));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(wmImNoticeH.getNoticeId());
            sheet.setMargin(HSSFSheet.TopMargin, 0.1);// 页边距（上）
            sheet.setMargin(HSSFSheet.BottomMargin, 0.1);// 页边距（下）
            sheet.setMargin(HSSFSheet.LeftMargin, 0.3);// 页边距（左）
            sheet.setMargin(HSSFSheet.RightMargin, 0.0);// 页边距（右
//			sheet.setDisplayGridlines(true);
            //set print grid lines or not
//			sheet.setPrintGridlines(true);
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 15 * 256);
            sheet.setColumnWidth(2, 25 * 256);
            sheet.setColumnWidth(3, 11 * 256);
            sheet.setColumnWidth(4, 5 * 256);
            sheet.setColumnWidth(5, 5 * 256);
            sheet.setColumnWidth(6, 7 * 256);
            sheet.setColumnWidth(7, 7 * 256);
            sheet.setColumnWidth(8, 9 * 256);
            sheet.setColumnWidth(9, 7 * 256);
            sheet.setColumnWidth(10, 3 * 256);
            // sheet.setColumnWidth(6, 8 * 256);
            // sheet.setColumnWidth(7, 8 * 256);
            // sheet.setColumnWidth(8, 8 * 256);

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
            f2.setFontHeightInPoints((short) 10);
            f2.setColor(IndexedColors.BLACK.getIndex());


            f5.setFontHeightInPoints((short) 8);
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
            cs2.setWrapText(true);

            // cs2.setAlignment(BorderStyle.NONE);

            cs3.setFont(f2);
            cs3.setBorderLeft(BorderStyle.MEDIUM);
            cs3.setBorderRight(BorderStyle.MEDIUM);
            cs3.setBorderTop(BorderStyle.MEDIUM);
            cs3.setBorderBottom(BorderStyle.MEDIUM);
            // cs3.setAlignment(CellStyle.BORDER_HAIR);
            cs4.setFont(f2);
            cs4.setBorderTop(BorderStyle.MEDIUM);
            cs4.setBorderBottom(BorderStyle.MEDIUM);

            cs5.setFont(f2);
            cs5.setBorderLeft(BorderStyle.THIN);
            cs5.setBorderRight(BorderStyle.THIN);
            cs5.setBorderTop(BorderStyle.THIN);
            cs5.setBorderBottom(BorderStyle.THIN);
            cs5.setWrapText(true);


            cs5r.setFont(f2);
            cs5r.setBorderLeft(BorderStyle.THIN);
            cs5r.setBorderRight(BorderStyle.THIN);
            cs5r.setBorderTop(BorderStyle.THIN);
            cs5r.setBorderBottom(BorderStyle.THIN);
            cs5r.setWrapText(true);
            cs5r.setAlignment(HorizontalAlignment.RIGHT);


            cs51.setFont(f2);
            cs51.setBorderLeft(BorderStyle.THIN);
            cs51.setBorderRight(BorderStyle.THIN);
            cs51.setBorderTop(BorderStyle.THIN);
            cs51.setBorderBottom(BorderStyle.THIN);
            cs51.setAlignment(HorizontalAlignment.CENTER);

            cs51.setWrapText(true);

            cs52.setFont(f5);
            cs52.setBorderLeft(BorderStyle.NONE);
            cs52.setBorderRight(BorderStyle.NONE);
            cs52.setBorderTop(BorderStyle.NONE);
            cs52.setBorderBottom(BorderStyle.NONE);
            cs52.setAlignment(HorizontalAlignment.CENTER);

            cs52.setWrapText(true);
            cs52.setRotation((short) 255);

            int page = 0;
            int cerconNo = 1;

            List<Map<String, Object>> result = wmInQmIMapper.getImQmiMap(wmImNoticeH.getNoticeId());


            int size = result.size();
            int pagesize = 10;
            int pagecount = size % pagesize == 0 ? size / pagesize : size / pagesize + 1;
            double sum = 0;
            double sumzl = 0;
            do {

                // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                // anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                        (short) 8, page * 20 + 1, (short) 10, page * 20 + 5);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                // 插入图片
                patriarch
                        .createPicture(anchor, wb.addPicture(
                                byteArrayOut.toByteArray(),
                                HSSFWorkbook.PICTURE_TYPE_JPEG));

                // 创建第一行
                Row row = sheet.createRow((short) page * 20 + 0); // 第一行空白


                Row row1 = sheet.createRow((short) page * 20 + 1); // 第二行标题
                row1.setHeight((short) 700);
                Cell cellTitle = row1.createCell(0);
                if ("03".equals(wmImNoticeH.getOrderTypeCode())) {
                    cellTitle.setCellValue("退货验收单");
                } else if ("01".equals(wmImNoticeH.getOrderTypeCode())) {
                    cellTitle.setCellValue("收货验收单");
                } else if ("04".equals(wmImNoticeH.getOrderTypeCode())) {
                    cellTitle.setCellValue("越库单");
                } else if ("09".equals(wmImNoticeH.getOrderTypeCode())) {
                    cellTitle.setCellValue("收货验收单");
                }


                cellTitle.setCellStyle(cs);

                Row rowHead1 = sheet.createRow((short) page * 20 + 2); // 头部第一行
                Cell cellHead1 = rowHead1.createCell(0);
                cellHead1.setCellValue("公司地址：");
                cellHead1.setCellStyle(cs1);

                Row rowHead2 = sheet.createRow((short) page * 20 + 3); // 头部第二行
                Cell cellHead2 = rowHead2.createCell(0);
                cellHead2.setCellValue("电话：");
                cellHead2.setCellStyle(cs1);


                Row rowHead4 = sheet.createRow((short) page * 20 + 4); // 头部第二行
                Cell cellHead4 = rowHead4.createCell(0);
                String arriveTime = DateUtils.date2Str(wmImNoticeH.getImData(), new SimpleDateFormat("yyyy-MM-dd"));
                cellHead4.setCellValue("到货日期： " + arriveTime == null ? "" : arriveTime);
                cellHead4.setCellStyle(cs2);

                Cell cellHead42 = rowHead4.createCell(3);
                cellHead42.setCellValue("预约单号： " + wmImNoticeH.getNoticeId());
                cellHead42.setCellStyle(cs2);

                Row rowHead5 = sheet.createRow((short) page * 20 + 5); // 头部第二行
                Cell cellHead5 = rowHead5.createCell(0);
                cellHead5.setCellValue("客户采购单号： " + wmImNoticeH.getImCusCode() == null ? "" : wmImNoticeH.getImCusCode());
                cellHead5.setCellStyle(cs2);

                Cell cellHead52 = rowHead5.createCell(3);
                cellHead52.setCellValue("月台： " + wmImNoticeH.getPlatformCode() == null ? "" : wmImNoticeH.getPlatformCode());
                cellHead52.setCellStyle(cs2);

                Row rowHead6 = sheet.createRow((short) page * 20 + 6); // 头部第二行
                Cell cellHead6 = rowHead6.createCell(0);
                MdCus md = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa, wmImNoticeH.getCusCode()).one();


                cellHead6.setCellValue("客户名称： " + wmImNoticeH.getCusCode() + md.getZhongWenQch());
                cellHead6.setCellStyle(cs2);

                Cell cellHead62 = rowHead6.createCell(3);
                cellHead62.setCellValue("供应商/车号： " + wmImNoticeH.getImCarNo() == null ? "" : wmImNoticeH.getImCarNo());
                cellHead62.setCellStyle(cs2);

                Row rowHead7 = sheet.createRow((short) page * 20 + 7); // 头部第二行
                Cell cellHead7 = rowHead7.createCell(0);
                cellHead7.setCellValue("客户电话： " + md.getDianHua() == null ? "" : md.getDianHua());
                cellHead7.setCellStyle(cs2);

                Cell cellHead72 = rowHead7.createCell(3);
                cellHead72.setCellValue("打印时间： " + DateUtils.date2Str(DateUtils.getDate(), new SimpleDateFormat("yyyy-MM-dd")) + "                        第" + (page + 1) + "页");
                cellHead72.setCellStyle(cs2);


                // 合并单元格
                CellRangeAddress c = new CellRangeAddress(page * 20 + 0, page * 20 + 0, 0, 9); // 第一行空白
                CellRangeAddress c1 = new CellRangeAddress(page * 20 + 1, page * 20 + 1, 0, 8);// 第二行标题
                CellRangeAddress c2 = new CellRangeAddress(page * 20 + 2, page * 20 + 2, 0, 9);// 第三行地址
                CellRangeAddress c3 = new CellRangeAddress(page * 20 + 3, page * 20 + 3, 0, 9);// 第四行电话

                CellRangeAddress c4 = new CellRangeAddress(page * 20 + 4, page * 20 + 4, 0, 2);// 第5行 到货日期
                CellRangeAddress c42 = new CellRangeAddress(page * 20 + 4, page * 20 + 4, 3, 9);// 第5行预约单号
                CellRangeAddress c5 = new CellRangeAddress(page * 20 + 5, page * 20 + 5, 0, 2);// 第6行客户采购单号
                CellRangeAddress c52 = new CellRangeAddress(page * 20 + 5, page * 20 + 5, 3, 9);// 第6行月台
                CellRangeAddress c6 = new CellRangeAddress(page * 20 + 6, page * 20 + 6, 0, 2);// 第7行客户名称
                CellRangeAddress c62 = new CellRangeAddress(page * 20 + 6, page * 20 + 6, 3, 9);// 第7行车号
                CellRangeAddress c7 = new CellRangeAddress(page * 20 + 7, page * 20 + 7, 0, 2);//第7行客户电话
                CellRangeAddress c72 = new CellRangeAddress(page * 20 + 7, page * 20 + 7, 3, 9);//第7行打印时间
                sheet.addMergedRegion(c);
                sheet.addMergedRegion(c1);
                sheet.addMergedRegion(c2);
                sheet.addMergedRegion(c3);
                sheet.addMergedRegion(c4);
                sheet.addMergedRegion(c5);
                sheet.addMergedRegion(c6);
                sheet.addMergedRegion(c7);
                sheet.addMergedRegion(c42);
                sheet.addMergedRegion(c52);
                sheet.addMergedRegion(c62);
                sheet.addMergedRegion(c72);

                Cell cell73 = row.createCell(10);
                cell73.setCellValue("① 财务联 ② 客户联 ③司机联 ④回单联   ");
                cell73.setCellStyle(cs52);


                CellRangeAddress c73 = new CellRangeAddress(page * 20 + 0, page * 20 + 19, 10, 10);//第7行打印时间
                sheet.addMergedRegion(c73);

                Row rowColumnName = sheet.createRow((short) page * 20 + 8); // 列名
                String[] columnNames = {"序号", "商品编码", "商品名称", "生产日期", "货温", "单位", "数量", "毛重KG", "体积cm³", "备注"};
                for (int i = 0; i < columnNames.length; i++) {
                    Cell cell = rowColumnName.createCell(i);
                    cell.setCellValue(columnNames[i]);
                    cell.setCellStyle(cs3);
                }


                int cellsNum = page * 20 + 8;
                int oversize = 0;
                if (size == pagesize && page == pagecount - 1) {
                    oversize = 1;
                }
                for (int i = page * pagesize; i < (page + 1) * pagesize + oversize; i++) {
                    if (i < size) {

                        cellsNum++;
                        Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
                        rowColumnValue.setHeight((short) 250);

                        Cell cell1 = rowColumnValue.createCell(0);
                        cell1.setCellValue(cerconNo);
                        cell1.setCellStyle(cs51);
                        Cell cell2 = rowColumnValue.createCell(1);
                        cell2.setCellValue(result.get(i).get("goods_id")
                                .toString());
                        cell2.setCellStyle(cs5);

                        Cell cell3 = rowColumnValue.createCell(2);
                        cell3.setCellValue(result.get(i).get("shp_ming_cheng")
                                .toString());
                        cell3.setCellStyle(cs5);
                        try {
                            Cell cell4 = rowColumnValue.createCell(3);// 生产日期

                            cell4.setCellStyle(cs5r);
                            cell4.setCellValue(result.get(i).get("pro_data")
                                    .toString());

                        } catch (Exception e) {
                            // TODO: handle exception

                        }

                        try {
                            Cell cell5 = rowColumnValue.createCell(4);// 温度

                            cell5.setCellStyle(cs5);
                            cell5.setCellValue(result.get(i)
                                    .get("rec_deg").toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        try {
                            Cell cell6 = rowColumnValue.createCell(5);// 单位

                            cell6.setCellStyle(cs5);
                            cell6.setCellValue(result.get(i).get("goods_unit")
                                    .toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        try {
                            sum = sum + Double.parseDouble(result.get(i).get("goods_count")
                                    .toString());
                            Cell cell7 = rowColumnValue.createCell(6);// 数量

                            cell7.setCellStyle(cs5);
                            cell7.setCellValue(result.get(i).get("goods_count")
                                    .toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        try {
                            Cell cell8 = rowColumnValue.createCell(7);// 毛重
                            sumzl = sumzl + Double.parseDouble(result.get(i).get("tin_zhl")
                                    .toString());
                            cell8.setCellStyle(cs5);
                            cell8.setCellValue(result.get(i).get("tin_zhl")
                                    .toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        try {
                            Cell cell9 = rowColumnValue.createCell(8);// 体积
                            cell9.setCellStyle(cs5);
                            cell9.setCellValue(result.get(i).get("tin_tj")
                                    .toString());

                        } catch (Exception e) {
                            // TODO: handle exception
                        }


                        Cell cell10 = rowColumnValue.createCell(9);// 备注
                        cell10.setCellStyle(cs5);

                        cerconNo++;
                    }
                    if (i == size) {

                        cellsNum++;
                        Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
                        rowColumnValue.setHeight((short) 250);
                        Cell cell6 = rowColumnValue.createCell(6);// 备注
                        cell6.setCellValue(Double.toString(sum));
                        Cell cell7 = rowColumnValue.createCell(7);// 重量
                        cell7.setCellValue(Double.toString(sumzl));
//				cell6.setCellStyle(cs5);
                        Cell cell0 = rowColumnValue.createCell(0);// 合计
                        cell0.setCellValue("合计：");
//				cell0.setCellStyle(cs5);
                        CellRangeAddress c15 = new CellRangeAddress(cellsNum,
                                cellsNum, 0, 5);
                        sheet.addMergedRegion(c15);
                        cerconNo++;

                    }


                }
                Row rowColumnInfo = sheet.createRow((short) 1 + cellsNum); // 列名
                rowColumnInfo.setHeight((short) 250);
                rowColumnInfo.createCell(0).setCellValue(
                        "验收人员：                               送货人员：                                客户/委托人：");
                CellRangeAddress c15 = new CellRangeAddress(1 + cellsNum,
                        1 + cellsNum, 0, 9);
                sheet.addMergedRegion(c15);
                page++;
            } while (page < pagecount);
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

    /**
     * 打印入库单
     *
     * @param id
     * @param response
     */
    @Override
    public void doPrintRkd(String id, HttpServletResponse response) {
        OutputStream fileOut = null;
        BufferedImage bufferImg = null;

        WmImNoticeH wmImNoticeH = this.getById(id);

        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
//			StringBuffer sber = new StringBuffer();

            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//			bufferImg = ImageIO.read(BarcodeUtil.generateToStream(wmImNoticeH
//					.getNoticeId()));
            bufferImg = QRcodeUtil.createImage(wmImNoticeH
                    .getNoticeId());

            // 进行转码，使其支持中文文件名
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode("入库单-" + wmImNoticeH.getNoticeId() + ".xls", "UTF-8"));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(wmImNoticeH.getNoticeId());
            sheet.setMargin(HSSFSheet.TopMargin, 0.1);// 页边距（上）
            sheet.setMargin(HSSFSheet.BottomMargin, 0.1);// 页边距（下）
            sheet.setMargin(HSSFSheet.LeftMargin, 0.3);// 页边距（左）
            sheet.setMargin(HSSFSheet.RightMargin, 0.0);// 页边距（右
//			sheet.setDisplayGridlines(true);
            //set print grid lines or not
//			sheet.setPrintGridlines(true);
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 15 * 256);
            sheet.setColumnWidth(2, 25 * 256);
            sheet.setColumnWidth(3, 11 * 256);
            sheet.setColumnWidth(4, 5 * 256);
            sheet.setColumnWidth(5, 5 * 256);
            sheet.setColumnWidth(6, 7 * 256);
            sheet.setColumnWidth(7, 7 * 256);
            sheet.setColumnWidth(8, 9 * 256);
            sheet.setColumnWidth(9, 7 * 256);
            sheet.setColumnWidth(10, 3 * 256);
            // sheet.setColumnWidth(6, 8 * 256);
            // sheet.setColumnWidth(7, 8 * 256);
            // sheet.setColumnWidth(8, 8 * 256);

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
            f2.setFontHeightInPoints((short) 10);
            f2.setColor(IndexedColors.BLACK.getIndex());


            f5.setFontHeightInPoints((short) 8);
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
            cs2.setWrapText(true);

            // cs2.setAlignment(BorderStyle.NONE);

            cs3.setFont(f2);
            cs3.setBorderLeft(BorderStyle.MEDIUM);
            cs3.setBorderRight(BorderStyle.MEDIUM);
            cs3.setBorderTop(BorderStyle.MEDIUM);
            cs3.setBorderBottom(BorderStyle.MEDIUM);
            // cs3.setAlignment(CellStyle.BORDER_HAIR);
            cs4.setFont(f2);
            cs4.setBorderTop(BorderStyle.MEDIUM);
            cs4.setBorderBottom(BorderStyle.MEDIUM);

            cs5.setFont(f2);
            cs5.setBorderLeft(BorderStyle.THIN);
            cs5.setBorderRight(BorderStyle.THIN);
            cs5.setBorderTop(BorderStyle.THIN);
            cs5.setBorderBottom(BorderStyle.THIN);
            cs5.setWrapText(true);


            cs5r.setFont(f2);
            cs5r.setBorderLeft(BorderStyle.THIN);
            cs5r.setBorderRight(BorderStyle.THIN);
            cs5r.setBorderTop(BorderStyle.THIN);
            cs5r.setBorderBottom(BorderStyle.THIN);
            cs5r.setWrapText(true);
            cs5r.setAlignment(HorizontalAlignment.RIGHT);


            cs51.setFont(f2);
            cs51.setBorderLeft(BorderStyle.THIN);
            cs51.setBorderRight(BorderStyle.THIN);
            cs51.setBorderTop(BorderStyle.THIN);
            cs51.setBorderBottom(BorderStyle.THIN);
            cs51.setAlignment(HorizontalAlignment.CENTER);

            cs51.setWrapText(true);

            cs52.setFont(f5);
            cs52.setBorderLeft(BorderStyle.NONE);
            cs52.setBorderRight(BorderStyle.NONE);
            cs52.setBorderTop(BorderStyle.NONE);
            cs52.setBorderBottom(BorderStyle.NONE);
            cs52.setAlignment(HorizontalAlignment.CENTER);

            cs52.setWrapText(true);
            cs52.setRotation((short) 255);

            int page = 0;
            int cerconNo = 1;

            List<Map<String, Object>> result = wmInQmIMapper.getImQmiMap(wmImNoticeH.getNoticeId());


            int size = result.size();
            int pagesize = 10;
            int pagecount = size % pagesize == 0 ? size / pagesize : size / pagesize + 1;
            double sum = 0;
            double sumzl = 0;
            do {

                // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                // anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                        (short) 8, page * 20 + 1, (short) 10, page * 20 + 5);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                // 插入图片
                patriarch
                        .createPicture(anchor, wb.addPicture(
                                byteArrayOut.toByteArray(),
                                HSSFWorkbook.PICTURE_TYPE_JPEG));

                // 创建第一行
                Row row = sheet.createRow((short) page * 20 + 0); // 第一行空白


                Row row1 = sheet.createRow((short) page * 20 + 1); // 第二行标题
                row1.setHeight((short) 700);
                Cell cellTitle = row1.createCell(0);
                if ("03".equals(wmImNoticeH.getOrderTypeCode())) {
                    cellTitle.setCellValue("退货入库单");
                } else if ("01".equals(wmImNoticeH.getOrderTypeCode())) {
                    cellTitle.setCellValue("收货入库单");
                } else if ("04".equals(wmImNoticeH.getOrderTypeCode())) {
                    cellTitle.setCellValue("越库单");
                } else if ("09".equals(wmImNoticeH.getOrderTypeCode())) {
                    cellTitle.setCellValue("收货入库单");
                }


                cellTitle.setCellStyle(cs);

                Row rowHead1 = sheet.createRow((short) page * 20 + 2); // 头部第一行
                Cell cellHead1 = rowHead1.createCell(0);
                cellHead1.setCellValue("公司地址：");
                cellHead1.setCellStyle(cs1);

                Row rowHead2 = sheet.createRow((short) page * 20 + 3); // 头部第二行
                Cell cellHead2 = rowHead2.createCell(0);
                cellHead2.setCellValue("电话：");
                cellHead2.setCellStyle(cs1);


                Row rowHead4 = sheet.createRow((short) page * 20 + 4); // 头部第二行
                Cell cellHead4 = rowHead4.createCell(0);
                String arriveTime = DateUtils.date2Str(wmImNoticeH.getImData(), new SimpleDateFormat("yyyy-MM-dd"));
                cellHead4.setCellValue("到货日期： " + arriveTime == null ? "" : arriveTime);
                cellHead4.setCellStyle(cs2);

                Cell cellHead42 = rowHead4.createCell(3);
                cellHead42.setCellValue("预约单号： " + wmImNoticeH.getNoticeId());
                cellHead42.setCellStyle(cs2);

                Row rowHead5 = sheet.createRow((short) page * 20 + 5); // 头部第二行
                Cell cellHead5 = rowHead5.createCell(0);
                cellHead5.setCellValue("客户采购单号： " + wmImNoticeH.getImCusCode() == null ? "" : wmImNoticeH.getImCusCode());
                cellHead5.setCellStyle(cs2);

                Cell cellHead52 = rowHead5.createCell(3);
                cellHead52.setCellValue("月台： " + wmImNoticeH.getPlatformCode() == null ? "" : wmImNoticeH.getPlatformCode());
                cellHead52.setCellStyle(cs2);

                Row rowHead6 = sheet.createRow((short) page * 20 + 6); // 头部第二行
                Cell cellHead6 = rowHead6.createCell(0);
                MdCus md = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa, wmImNoticeH.getCusCode()).one();

                cellHead6.setCellValue("客户名称： " + wmImNoticeH.getCusCode() + md.getZhongWenQch());
                cellHead6.setCellStyle(cs2);

                Cell cellHead62 = rowHead6.createCell(3);
                cellHead62.setCellValue("供应商/车号： " + wmImNoticeH.getImCarNo() == null ? "" : wmImNoticeH.getImCarNo());
                cellHead62.setCellStyle(cs2);

                Row rowHead7 = sheet.createRow((short) page * 20 + 7); // 头部第二行
                Cell cellHead7 = rowHead7.createCell(0);
                cellHead7.setCellValue("客户电话： " + md.getDianHua() == null ? "" : md.getDianHua());
                cellHead7.setCellStyle(cs2);

                Cell cellHead72 = rowHead7.createCell(3);
                cellHead72.setCellValue("打印时间： " + DateUtils.date2Str(DateUtils.getDate(), new SimpleDateFormat("yyyy-MM-dd")) + "                        第" + (page + 1) + "页");
                cellHead72.setCellStyle(cs2);


                // 合并单元格
                CellRangeAddress c = new CellRangeAddress(page * 20 + 0, page * 20 + 0, 0, 9); // 第一行空白
                CellRangeAddress c1 = new CellRangeAddress(page * 20 + 1, page * 20 + 1, 0, 8);// 第二行标题
                CellRangeAddress c2 = new CellRangeAddress(page * 20 + 2, page * 20 + 2, 0, 9);// 第三行地址
                CellRangeAddress c3 = new CellRangeAddress(page * 20 + 3, page * 20 + 3, 0, 9);// 第四行电话

                CellRangeAddress c4 = new CellRangeAddress(page * 20 + 4, page * 20 + 4, 0, 2);// 第5行 到货日期
                CellRangeAddress c42 = new CellRangeAddress(page * 20 + 4, page * 20 + 4, 3, 9);// 第5行预约单号
                CellRangeAddress c5 = new CellRangeAddress(page * 20 + 5, page * 20 + 5, 0, 2);// 第6行客户采购单号
                CellRangeAddress c52 = new CellRangeAddress(page * 20 + 5, page * 20 + 5, 3, 9);// 第6行月台
                CellRangeAddress c6 = new CellRangeAddress(page * 20 + 6, page * 20 + 6, 0, 2);// 第7行客户名称
                CellRangeAddress c62 = new CellRangeAddress(page * 20 + 6, page * 20 + 6, 3, 9);// 第7行车号
                CellRangeAddress c7 = new CellRangeAddress(page * 20 + 7, page * 20 + 7, 0, 2);//第7行客户电话
                CellRangeAddress c72 = new CellRangeAddress(page * 20 + 7, page * 20 + 7, 3, 9);//第7行打印时间
                sheet.addMergedRegion(c);
                sheet.addMergedRegion(c1);
                sheet.addMergedRegion(c2);
                sheet.addMergedRegion(c3);
                sheet.addMergedRegion(c4);
                sheet.addMergedRegion(c5);
                sheet.addMergedRegion(c6);
                sheet.addMergedRegion(c7);
                sheet.addMergedRegion(c42);
                sheet.addMergedRegion(c52);
                sheet.addMergedRegion(c62);
                sheet.addMergedRegion(c72);

                Cell cell73 = row.createCell(10);
                cell73.setCellValue("① 财务联 ② 客户联 ③司机联 ④回单联   ");
                cell73.setCellStyle(cs52);


                CellRangeAddress c73 = new CellRangeAddress(page * 20 + 0, page * 20 + 19, 10, 10);//第7行打印时间
                sheet.addMergedRegion(c73);

                Row rowColumnName = sheet.createRow((short) page * 20 + 8); // 列名
                String[] columnNames = {"序号", "商品编码", "商品名称", "生产日期", "货温", "单位", "数量", "毛重KG", "体积cm³", "备注"};

                for (int i = 0; i < columnNames.length; i++) {
                    Cell cell = rowColumnName.createCell(i);
                    cell.setCellValue(columnNames[i]);
                    cell.setCellStyle(cs3);
                }


                int cellsNum = page * 20 + 8;
                int oversize = 0;
                if (size == pagesize && page == pagecount - 1) {
                    oversize = 1;
                }
                for (int i = page * pagesize; i < (page + 1) * pagesize + oversize; i++) {
                    if (i < size) {

                        cellsNum++;
                        Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
                        rowColumnValue.setHeight((short) 250);

                        Cell cell1 = rowColumnValue.createCell(0);
                        cell1.setCellValue(cerconNo);
                        cell1.setCellStyle(cs51);
                        Cell cell2 = rowColumnValue.createCell(1);
                        cell2.setCellValue(result.get(i).get("goods_id")
                                .toString());
                        cell2.setCellStyle(cs5);

                        Cell cell3 = rowColumnValue.createCell(2);
                        cell3.setCellValue(result.get(i).get("shp_ming_cheng")
                                .toString());
                        cell3.setCellStyle(cs5);
                        try {
                            Cell cell4 = rowColumnValue.createCell(3);// 生产日期

                            cell4.setCellStyle(cs5r);
                            cell4.setCellValue(result.get(i).get("pro_data")
                                    .toString());

                        } catch (Exception e) {
                            // TODO: handle exception

                        }

                        try {
                            Cell cell5 = rowColumnValue.createCell(4);// 温度

                            cell5.setCellStyle(cs5);
                            cell5.setCellValue(result.get(i)
                                    .get("rec_deg").toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        try {
                            Cell cell6 = rowColumnValue.createCell(5);// 单位

                            cell6.setCellStyle(cs5);
                            cell6.setCellValue(result.get(i).get("goods_unit")
                                    .toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        try {
                            sum = sum + Double.parseDouble(result.get(i).get("goods_count")
                                    .toString());
                            Cell cell7 = rowColumnValue.createCell(6);// 数量

                            cell7.setCellStyle(cs5);
                            cell7.setCellValue(result.get(i).get("goods_count")
                                    .toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        try {
                            Cell cell8 = rowColumnValue.createCell(7);// 毛重
                            sumzl = sumzl + Double.parseDouble(result.get(i).get("tin_zhl")
                                    .toString());
                            cell8.setCellStyle(cs5);
                            cell8.setCellValue(result.get(i).get("tin_zhl")
                                    .toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        try {

                            Cell cell9 = rowColumnValue.createCell(8);// 体积
                            cell9.setCellStyle(cs5);
                            cell9.setCellValue(result.get(i).get("tin_tj")
                                    .toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }


                        Cell cell10 = rowColumnValue.createCell(9);// 备注

                        cell10.setCellStyle(cs5);

                        cerconNo++;
                    }
                    if (i == size) {

                        cellsNum++;
                        Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
                        rowColumnValue.setHeight((short) 250);
                        Cell cell6 = rowColumnValue.createCell(6);// 备注
                        cell6.setCellValue(Double.toString(sum));
                        Cell cell7 = rowColumnValue.createCell(7);// 重量
                        cell7.setCellValue(Double.toString(sumzl));
//				cell6.setCellStyle(cs5);
                        Cell cell0 = rowColumnValue.createCell(0);// 合计
                        cell0.setCellValue("合计：");
//				cell0.setCellStyle(cs5);
                        CellRangeAddress c15 = new CellRangeAddress(cellsNum,
                                cellsNum, 0, 5);
                        sheet.addMergedRegion(c15);
                        cerconNo++;

                    }


                }
                Row rowColumnInfo = sheet.createRow((short) 1 + cellsNum); // 列名
                rowColumnInfo.setHeight((short) 250);
                rowColumnInfo.createCell(0).setCellValue(
                        "验收人员：                               送货人员：                                客户/委托人：");
                CellRangeAddress c15 = new CellRangeAddress(1 + cellsNum,
                        1 + cellsNum, 0, 9);
                sheet.addMergedRegion(c15);
                page++;
            } while (page < pagecount);

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
    public void doPrintBq(String id, HttpServletResponse response) {
        OutputStream fileOut = null;
        BufferedImage bufferImg = null;

        WmImNoticeH wmImNoticeH = this.getById(id);
        List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.selectByMainId(wmImNoticeH.getNoticeId());

        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            // 进行转码，使其支持中文文件名
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode("标签-" + wmImNoticeH.getNoticeId() + ".xls", "UTF-8"));

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(wmImNoticeH.getNoticeId());
            sheet.setMargin(HSSFSheet.TopMargin, 0.1);// 页边距（上）
            sheet.setMargin(HSSFSheet.BottomMargin, 0.1);// 页边距（下）
            sheet.setMargin(HSSFSheet.LeftMargin, 0.3);// 页边距（左）
            sheet.setMargin(HSSFSheet.RightMargin, 0.0);// 页边距（右
//			sheet.setDisplayGridlines(true);
            //set print grid lines or not
//			sheet.setPrintGridlines(true);
            sheet.setColumnWidth(0, 14 * 256);
            sheet.setColumnWidth(1, 3 * 256);
            sheet.setColumnWidth(2, 21 * 256);
            sheet.setColumnWidth(3, 17 * 256);
//            sheet.setColumnWidth(4, 5 * 256);
//            sheet.setColumnWidth(5, 5 * 256);
//            sheet.setColumnWidth(6, 7 * 256);
//            sheet.setColumnWidth(7, 7 * 256);
//            sheet.setColumnWidth(8, 9 * 256);
//            sheet.setColumnWidth(9, 7 * 256);
//            sheet.setColumnWidth(10, 3 * 256);
            // sheet.setColumnWidth(6, 8 * 256);
            // sheet.setColumnWidth(7, 8 * 256);
            // sheet.setColumnWidth(8, 8 * 256);

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
            f2.setFontHeightInPoints((short) 10);
            f2.setColor(IndexedColors.BLACK.getIndex());


            f5.setFontHeightInPoints((short) 8);
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
            cs2.setWrapText(true);

            // cs2.setAlignment(BorderStyle.NONE);

            cs3.setFont(f2);
            cs3.setBorderLeft(BorderStyle.MEDIUM);
            cs3.setBorderRight(BorderStyle.MEDIUM);
            cs3.setBorderTop(BorderStyle.MEDIUM);
            cs3.setBorderBottom(BorderStyle.MEDIUM);
            // cs3.setAlignment(CellStyle.BORDER_HAIR);
            cs4.setFont(f2);
            cs4.setBorderTop(BorderStyle.MEDIUM);
            cs4.setBorderBottom(BorderStyle.MEDIUM);

            cs5.setFont(f2);
            cs5.setBorderLeft(BorderStyle.THIN);
            cs5.setBorderRight(BorderStyle.THIN);
            cs5.setBorderTop(BorderStyle.THIN);
            cs5.setBorderBottom(BorderStyle.THIN);
            cs5.setWrapText(true);


            cs5r.setFont(f2);
            cs5r.setBorderLeft(BorderStyle.THIN);
            cs5r.setBorderRight(BorderStyle.THIN);
            cs5r.setBorderTop(BorderStyle.THIN);
            cs5r.setBorderBottom(BorderStyle.THIN);
            cs5r.setWrapText(true);
            cs5r.setAlignment(HorizontalAlignment.RIGHT);


            cs51.setFont(f2);
            cs51.setBorderLeft(BorderStyle.THIN);
            cs51.setBorderRight(BorderStyle.THIN);
            cs51.setBorderTop(BorderStyle.THIN);
            cs51.setBorderBottom(BorderStyle.THIN);
            cs51.setAlignment(HorizontalAlignment.CENTER);

            cs51.setWrapText(true);

            cs52.setFont(f5);
            cs52.setBorderLeft(BorderStyle.NONE);
            cs52.setBorderRight(BorderStyle.NONE);
            cs52.setBorderTop(BorderStyle.NONE);
            cs52.setBorderBottom(BorderStyle.NONE);
            cs52.setAlignment(HorizontalAlignment.CENTER);

            cs52.setWrapText(true);
            cs52.setRotation((short) 255);

            int page = 0;

            // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                bufferImg = QRcodeUtil.createImage(StringUtils.isNotEmpty(wmImNoticeI.getGoodsCode()) ? wmImNoticeI.getGoodsCode() : "");
                ImageIO.write(bufferImg, "jpg", byteArrayOut);

                ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();
                bufferImg = QRcodeUtil.createImage(StringUtils.isNotEmpty(wmImNoticeI.getGoodsBatch()) ? wmImNoticeI.getGoodsBatch() : "");
                ImageIO.write(bufferImg, "jpg", byteArrayOut1);

                // anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                        (short) 0, page * 12 + 5, (short) 2, page * 12 + 11);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                // 插入图片
                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

                // anchor主要用于设置图片的属性
                HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 0, 0,
                        (short) 3, page * 12 + 5, (short) 4, page * 12 + 11);
                anchor1.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
                patriarch.createPicture(anchor1, wb.addPicture(byteArrayOut1.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

                Row row = sheet.createRow((short) page * 12 + 0); // 第一行空白

                Row rowHead1 = sheet.createRow((short) page * 12 + 1); // 头部第1行
                Cell cellHead1 = rowHead1.createCell(0);
                cellHead1.setCellValue("批次号： " + (StringUtils.isNotEmpty(wmImNoticeI.getGoodsBatch()) ? wmImNoticeI.getGoodsBatch() : ""));
                cellHead1.setCellStyle(cs2);

                String dateTime = DateUtils.formatDate(new Date(), "yyyy/MM/dd HH:mm");
                Cell cellHead12 = rowHead1.createCell(3);
                cellHead12.setCellValue(dateTime);
                cellHead12.setCellStyle(cs2);

                Row rowHead2 = sheet.createRow((short) page * 12 + 2); // 头部第2行
                Cell cellHead2 = rowHead2.createCell(0);
                cellHead2.setCellValue("存货编码：" + (StringUtils.isNotEmpty(wmImNoticeI.getGoodsCode()) ? wmImNoticeI.getGoodsCode() : ""));
                cellHead2.setCellStyle(cs2);

                Row rowHead3 = sheet.createRow((short) page * 12 + 3); // 头部第3行
                Cell cellHead3 = rowHead3.createCell(0);
                cellHead3.setCellValue("存货名称：" + (StringUtils.isNotEmpty(wmImNoticeI.getGoodsName()) ? wmImNoticeI.getGoodsName() : ""));
                cellHead3.setCellStyle(cs2);

                Row rowHead4 = sheet.createRow((short) page * 12 + 4); // 头部第4行
                Cell cellHead4 = rowHead4.createCell(0);
                cellHead4.setCellValue("产品规格：" + (StringUtils.isNotEmpty(wmImNoticeI.getShpGuiGe()) ? wmImNoticeI.getShpGuiGe() : ""));
                cellHead4.setCellStyle(cs2);

                Row rowHead5 = sheet.createRow((short) page * 12 + 11); // 头部第11行
                Cell cellHead5 = rowHead5.createCell(0);
                cellHead5.setCellValue(StringUtils.isNotEmpty(wmImNoticeI.getGoodsCode()) ? wmImNoticeI.getGoodsCode() : "");
                cellHead5.setCellStyle(cs2);

                Cell cellHead52 = rowHead5.createCell(3);
                cellHead52.setCellValue(StringUtils.isNotEmpty(wmImNoticeI.getGoodsBatch()) ? wmImNoticeI.getGoodsBatch() : "");
                cellHead52.setCellStyle(cs2);

                // 合并单元格
                CellRangeAddress c = new CellRangeAddress(page * 12 + 0, page * 12 + 0, 0, 3); // 第1行空白
                CellRangeAddress c1 = new CellRangeAddress(page * 12 + 1, page * 12 + 1, 0, 2);// 第2行批次号
                CellRangeAddress c12 = new CellRangeAddress(page * 12 + 1, page * 12 + 1, 3, 3);// 第2行日期
                CellRangeAddress c2 = new CellRangeAddress(page * 12 + 2, page * 12 + 2, 0, 3);// 第3行存货编码
                CellRangeAddress c3 = new CellRangeAddress(page * 12 + 3, page * 12 + 3, 0, 3);// 第4行存货名称
                CellRangeAddress c4 = new CellRangeAddress(page * 12 + 4, page * 12 + 4, 0, 3);// 第5行 产品规格
                CellRangeAddress c5 = new CellRangeAddress(page * 12 + 11, page * 12 + 11, 0, 2);// 第11行 存货编码
                CellRangeAddress c52 = new CellRangeAddress(page * 12 + 11, page * 12 + 11, 3, 3);// 第11行 批次号

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

    /**
     * 退货列表
     *
     * @param wmImNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @Override
    public IPage<WmImNoticeH> selectThList(WmImNoticeH wmImNoticeH, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        QueryWrapper<WmImNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmImNoticeH, req.getParameterMap());

        queryWrapper.lambda().eq(WmImNoticeH::getOrderTypeCode, "03");
        Page<WmImNoticeH> page = new Page<WmImNoticeH>(pageNo, pageSize);
        return this.page(page, queryWrapper);
    }

    /**
     * 货品id
     *
     * @param id
     * @param response
     */
    @Override
    public void doPrintId(String id, HttpServletResponse response) {
        OutputStream fileOut = null;
        BufferedImage bufferImg = null;
//		String codedFileName = null;
        WmImNoticeH wmImNoticeH = this.getById(id);
        String hql0 = "from WmInQmIEntity where 1 = 1 AND imNoticeId = ? ";
        List<WmInQmI> wmImQmIEntityList = wmInQmIService.lambdaQuery().eq(WmInQmI::getImNoticeId, wmImNoticeH.getNoticeId()).list();
        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
//			StringBuffer sber = new StringBuffer();

            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//			bufferImg = ImageIO.read(BarcodeUtil.generateToStream(wmImNoticeH
//					.getNoticeId()));
            // 进行转码，使其支持中文文件名
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename="
                    + wmImNoticeH.getNoticeId() + "hpid.xls");
//			ImageIO.write(bufferImg, "jpg", byteArrayOut);

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("货品ID");

            sheet.setMargin(HSSFSheet.TopMargin, 0.1);// 页边距（上）
            sheet.setMargin(HSSFSheet.BottomMargin, 0.1);// 页边距（下）
            sheet.setMargin(HSSFSheet.LeftMargin, 0.1);// 页边距（左）
            sheet.setMargin(HSSFSheet.RightMargin, 0.1);// 页边距（右
            sheet.setColumnWidth(0, 28 * 256);
            sheet.setColumnWidth(1, 29 * 256);
            sheet.setColumnWidth(2, 6 * 200);

            sheet.setColumnWidth(3, 14 * 256);
            sheet.setColumnWidth(4, 14 * 256);
            sheet.setColumnWidth(5, 6 * 256);
            // sheet.setColumnWidth(6, 8 * 256);
            // sheet.setColumnWidth(7, 8 * 256);
            // sheet.setColumnWidth(8, 8 * 256);
            // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            // anchor主要用于设置图片的属性
            HSSFClientAnchor anchor = null;


            // 创建两种单元格格式
            CellStyle csh = wb.createCellStyle();
            CellStyle csh1 = wb.createCellStyle();
            CellStyle cs = wb.createCellStyle();
            CellStyle cs2 = wb.createCellStyle();
            CellStyle cs3 = wb.createCellStyle();
            CellStyle cs4 = wb.createCellStyle();
            CellStyle cs5 = wb.createCellStyle();
            // 创建两种字体
            Font f = wb.createFont();
            Font f2 = wb.createFont();
            Font fh = wb.createFont();
            fh.setFontHeightInPoints((short) 42);
            fh.setColor(IndexedColors.BLACK.getIndex());
            fh.setBold(true);
            Font fh1 = wb.createFont();
            fh1.setFontHeightInPoints((short) 32);
            fh1.setColor(IndexedColors.BLACK.getIndex());
            fh1.setBold(true);
            // 创建第一种字体样式（用于列名）
            f.setFontHeightInPoints((short) 22);
            f.setColor(IndexedColors.BLACK.getIndex());
            f.setBold(true);

            // 创建第二种字体样式（用于值）
            f2.setFontHeightInPoints((short) 10);
            f2.setColor(IndexedColors.BLACK.getIndex());

            // Font f3=wb.createFont();
            // f3.setFontHeightInPoints((short) 10);
            // f3.setColor(IndexedColors.RED.getIndex());
            csh.setFont(fh);
            csh.setBorderLeft(BorderStyle.NONE);
            csh.setBorderRight(BorderStyle.NONE);
            csh.setBorderTop(BorderStyle.NONE);
            csh.setBorderBottom(BorderStyle.NONE);
            csh.setAlignment(HorizontalAlignment.LEFT);
            csh1.setFont(fh1);
            csh1.setBorderLeft(BorderStyle.NONE);
            csh1.setBorderRight(BorderStyle.NONE);
            csh1.setBorderTop(BorderStyle.NONE);
            csh1.setBorderBottom(BorderStyle.NONE);
            csh1.setAlignment(HorizontalAlignment.RIGHT);
            // 设置第一种单元格的样式（用于列名）
            cs.setFont(f);
            cs.setBorderLeft(BorderStyle.NONE);
            cs.setBorderRight(BorderStyle.NONE);
            cs.setBorderTop(BorderStyle.NONE);
            cs.setBorderBottom(BorderStyle.NONE);
            cs.setAlignment(HorizontalAlignment.RIGHT);
            // 设置第二种单元格的样式（用于值）
            cs2.setFont(f2);
            cs2.setBorderLeft(BorderStyle.NONE);
            cs2.setBorderRight(BorderStyle.NONE);
            cs2.setBorderTop(BorderStyle.NONE);
            cs2.setBorderBottom(BorderStyle.NONE);
            cs2.setWrapText(true);

            // cs2.setAlignment(BorderStyle.NONE);

            cs3.setFont(f2);
            cs3.setBorderLeft(BorderStyle.MEDIUM);
            cs3.setBorderRight(BorderStyle.MEDIUM);
            cs3.setBorderTop(BorderStyle.MEDIUM);
            cs3.setBorderBottom(BorderStyle.MEDIUM);
            // cs3.setAlignment(CellStyle.BORDER_HAIR);
            cs4.setFont(f2);
            cs4.setBorderTop(BorderStyle.MEDIUM);
            cs4.setBorderBottom(BorderStyle.MEDIUM);

            cs5.setFont(f2);
            cs5.setBorderLeft(BorderStyle.NONE);
            cs5.setBorderRight(BorderStyle.NONE);
            cs5.setBorderTop(BorderStyle.NONE);
            cs5.setBorderBottom(BorderStyle.NONE);
            cs5.setWrapText(true);

            int cellsNum = 0;
            int pagesize = 1;
            int page = 0;
            short high = 600;
            for (WmInQmI wmInQmIEntity : wmImQmIEntityList) {
                cellsNum++;
                MdGoods goods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmInQmIEntity.getGoodsId()).one();
                Row rowColumnValue = sheet.createRow((short) page * pagesize + cellsNum); // 列名
                rowColumnValue.setHeight((short) 1000);
                Cell cell1 = rowColumnValue.createCell(0);
                try {
                    cell1.setCellValue("库别：" + wmInQmIEntity.getBinId().substring(0, 2) + "  " + wmInQmIEntity.getQmOkQuat() + goods.getShlDanWei());

                } catch (Exception e) {
                    // TODO: handle exception
                }
                cell1.setCellStyle(csh);
                CellRangeAddress c0 = new CellRangeAddress(page * pagesize + cellsNum, page * pagesize + cellsNum, 0, 5);
                sheet.addMergedRegion(c0);

                cellsNum++;
                Row rowColumnValue1 = sheet.createRow((short) page * pagesize + cellsNum); // 列名
                rowColumnValue1.setHeight((short) 1000);
                Cell cell2 = rowColumnValue1.createCell(0);

                cell2.setCellValue(goods.getShpMingCheng());
                cell2.setCellStyle(csh1);
                CellRangeAddress c2 = new CellRangeAddress(page * pagesize + cellsNum, page * pagesize + cellsNum, 0, 5);
                sheet.addMergedRegion(c2);
                cellsNum++;
                //插入图片
                try {
                    byteArrayOut = new ByteArrayOutputStream();
                    try {
                        bufferImg =
                                QRcodeUtil.createImage(wmInQmIEntity.getBinId().substring(0, 2));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    ImageIO.write(bufferImg, "jpg", byteArrayOut);
                    anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 2, page * pagesize + cellsNum,
                            (short) 5, page * pagesize + cellsNum + 4);
                    patriarch.createPicture(anchor,
                            wb.addPicture(byteArrayOut.toByteArray(),
                                    HSSFWorkbook.PICTURE_TYPE_JPEG));
                } catch (Exception e) {
                    // TODO: handle exception
                }
                Row rowColumnValue12 = sheet.createRow((short) page * pagesize + cellsNum); // 列名
                rowColumnValue12.setHeight((short) 1000);
                Cell cell13 = rowColumnValue12.createCell(0);
                cell13.setCellValue(goods.getShpBianMa());
                cell13.setCellStyle(cs);
                CellRangeAddress c13 = new CellRangeAddress(page * pagesize + cellsNum, page * pagesize + cellsNum, 0, 5);
                sheet.addMergedRegion(c13);
                cellsNum++;

                Row rowColumnValue2 = sheet.createRow((short) page * pagesize + cellsNum); // 列名
                rowColumnValue2.setHeight(high);
                Cell cell3 = rowColumnValue2.createCell(0);
                cell3.setCellValue("生产日期:" + wmInQmIEntity.getProData());
                cell3.setCellStyle(cs);
                CellRangeAddress c3 = new CellRangeAddress(page * pagesize + cellsNum, page * pagesize + cellsNum, 0, 5);
                sheet.addMergedRegion(c3);
                cellsNum++;
                Row rowColumnValue3 = sheet.createRow((short) page * pagesize + cellsNum); // 列名
                rowColumnValue3.setHeight(high);
                Cell cell4 = rowColumnValue3.createCell(0);
                try {
                    Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
                    cal.setTime(wmInQmIEntity.getProData());
                    cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(goods.getBzhiQi()));//取当前日期的后一天.
                    cell4.setCellValue("到期日期:" + DateUtils.date2Str(cal.getTime(), new SimpleDateFormat("yyyy-MM-dd")));
                } catch (Exception e) {
                    // TODO: handle exception
                }
                cell4.setCellStyle(cs);
                CellRangeAddress c4 = new CellRangeAddress(page * pagesize + cellsNum, page * pagesize + cellsNum, 0, 5);
                sheet.addMergedRegion(c4);
                cellsNum++;
                Row rowColumnValue4 = sheet.createRow((short) page * pagesize + cellsNum); // 列名
                rowColumnValue4.setHeight(high);
                Cell cell5 = rowColumnValue4.createCell(0);
                cell5.setCellValue("进货日期:" + DateUtils.date2Str(wmInQmIEntity.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd")));
                cell5.setCellStyle(cs);
                CellRangeAddress c5 = new CellRangeAddress(page * pagesize + cellsNum, page * pagesize + cellsNum, 0, 5);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    @Transactional
    synchronized
    public void saveMain(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList) {
        String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
        String noticeid = null;
        if (StringUtils.isNotEmpty(wmImNoticeH.getOrderType())) {
            wmImNoticeH.setOrderTypeCode(wmImNoticeH.getOrderType());
        }
        if (StringUtils.isEmpty(wmImNoticeH.getOrderTypeCode())) {
            wmImNoticeH.setOrderTypeCode("01");
        }

        List<WmImNoticeH> list = this.lambdaQuery().eq(WmImNoticeH::getOrderTypeCode, wmImNoticeH.getOrderTypeCode()).apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'").list();
        Integer max = 0;
        if (CollectionUtil.isNotEmpty(list)){
            List<Integer> list1 = new ArrayList<>();
            for (WmImNoticeH noticeH : list) {
                //进货通知单号
                String noticeId = noticeH.getNoticeId();
                String s = org.apache.commons.lang.StringUtils.split(noticeId, "-")[1];
                Integer integer = Convert.toInt(s);
                list1.add(integer);
            }
            max = Collections.max(list1);
        }

        int newcount = max + 1;
        if ("03".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "TH"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("01".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "RK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("04".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "YK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("09".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "QTRK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("06".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "CGRK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("07".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "SCRK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("05".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "CG"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else {
            noticeid = "QT"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        }
        wmImNoticeH.setNoticeId(noticeid);
        //单据状态
        wmImNoticeH.setImSta(wm_sta1);
        //查询供应商
        if (StringUtils.isEmpty(wmImNoticeH.getCusName())) {
            MdCus mdCus = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa, wmImNoticeH.getCusCode()).one();
            if (mdCus != null) {
                wmImNoticeH.setCusName(mdCus.getZhongWenQch());
            }
        }

        wmImNoticeHMapper.insert(wmImNoticeH);

        //月台
        WmPlatIo wmPlatIo = new WmPlatIo();
        //车号
        wmPlatIo.setCarno(wmImNoticeH.getImCarNo());
        //单据编号
        wmPlatIo.setDocId(wmImNoticeH.getNoticeId());
        //计划进入时间
        wmPlatIo.setPlanIndata(wmImNoticeH.getImData());
        //月台编号
        wmPlatIo.setPlatId(wmImNoticeH.getPlatformCode());
        //月台状态
        wmPlatIo.setPlatSta(wm_sta1);
        //备注
        wmPlatIo.setPlatBeizhu("司机:" + wmImNoticeH.getImCarDri() + "电话:"
                + wmImNoticeH.getImCarMobile());
        wmPlatIoService.save(wmPlatIo);

        if (wmImNoticeIList != null && wmImNoticeIList.size() > 0) {
            List<WmImNoticeI> noticeIList = packNoticeI(wmImNoticeH, wmImNoticeIList);
            wmImNoticeIService.saveBatch(noticeIList);
        }
    }

    @Override
    @Transactional
    public String saveMain1(WmImNoticeH noticeHs, List<WmImNoticeI> wmImNoticeIList) {
        String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
        List<String> noticeids = new ArrayList<>();

        List<WmImNoticeI> noticeIS = wmImNoticeIList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(
                                Comparator.comparing(noticeI -> noticeI.getCusCode()))), ArrayList::new)
        );

        List<String> cusCodes = new ArrayList<>();
        for (WmImNoticeI noticeI : noticeIS) {
            String cusCode = noticeI.getCusCode();
            cusCodes.add(cusCode);
        }

        List<WmImNoticeH> wmImNoticeHS = new ArrayList<>();
        for (String cusCode : cusCodes) {
            WmImNoticeH wmImNoticeH1 = new WmImNoticeH();
            BeanUtils.copyProperties(noticeHs, wmImNoticeH1);
            wmImNoticeH1.setCusCode(cusCode);
            wmImNoticeHS.add(wmImNoticeH1);
        }

        for (WmImNoticeH noticeH : wmImNoticeHS) {
            List<WmImNoticeI> wmImNoticeIS = new ArrayList<>();
            for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
                if (noticeH.getCusCode().equals(wmImNoticeI.getCusCode())) {
                    wmImNoticeIS.add(wmImNoticeI);
                }
            }
            noticeH.setWmImNoticeIS(wmImNoticeIS);
        }

        for (WmImNoticeH wmImNoticeH : wmImNoticeHS) {

            String noticeid = null;

            if (StringUtils.isNotEmpty(wmImNoticeH.getOrderType())) {
                wmImNoticeH.setOrderTypeCode(wmImNoticeH.getOrderType());
            }
            if (StringUtils.isEmpty(wmImNoticeH.getOrderTypeCode())) {
                wmImNoticeH.setOrderTypeCode("01");
            }
            //查询今天入库量
            /*int count = this.lambdaQuery()
                    .eq(WmImNoticeH::getOrderTypeCode, wmImNoticeH.getOrderTypeCode())
                    .apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'")
                    .count();*/
            List<WmImNoticeH> list = this.lambdaQuery().eq(WmImNoticeH::getOrderTypeCode, wmImNoticeH.getOrderTypeCode())
                    .apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'").list();
            Integer max = 0;
            if (CollectionUtil.isNotEmpty(list)) {
                List<Integer> list1 = new ArrayList<>();
                for (WmImNoticeH noticeH : list) {
                    String noticeId = noticeH.getNoticeId();
                    String s = StringUtils.split(noticeId, "-")[1];
                    Integer integer = Convert.toInt(s);
                    list1.add(integer);
                }
                max = Collections.max(list1);
            }
            int newcount = max + 1;
            if ("03".equals(wmImNoticeH.getOrderTypeCode())) {
                noticeid = "TH"
                        + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                        + "-"
                        + String.format("%04d", newcount);
            } else if ("01".equals(wmImNoticeH.getOrderTypeCode())) {
                noticeid = "RK"
                        + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                        + "-"
                        + String.format("%04d", newcount);
            } else if ("04".equals(wmImNoticeH.getOrderTypeCode())) {
                noticeid = "YK"
                        + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                        + "-"
                        + String.format("%04d", newcount);
            } else if ("09".equals(wmImNoticeH.getOrderTypeCode())) {
                noticeid = "QTRK"
                        + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                        + "-"
                        + String.format("%04d", newcount);
            } else if ("06".equals(wmImNoticeH.getOrderTypeCode())) {
                noticeid = "CGRK"
                        + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                        + "-"
                        + String.format("%04d", newcount);
            } else if ("07".equals(wmImNoticeH.getOrderTypeCode())) {
                noticeid = "SCRK"
                        + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                        + "-"
                        + String.format("%04d", newcount);
            } else if ("05".equals(wmImNoticeH.getOrderTypeCode())) {
                noticeid = "CG"
                        + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                        + "-"
                        + String.format("%04d", newcount);
            } else {
                noticeid = "QT"
                        + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                        + "-"
                        + String.format("%04d", newcount);
            }
            wmImNoticeH.setNoticeId(noticeid);
            wmImNoticeH.setImSta(wm_sta1);
            //查询供应商
            MdCus mdCus = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa, wmImNoticeH.getCusCode()).one();
            if (mdCus != null) {
                wmImNoticeH.setCusName(mdCus.getZhongWenQch());
            }
            wmImNoticeHMapper.insert(wmImNoticeH);

            //月台
            WmPlatIo wmPlatIo = new WmPlatIo();
            wmPlatIo.setCarno(wmImNoticeH.getImCarNo());
            wmPlatIo.setDocId(wmImNoticeH.getNoticeId());
            wmPlatIo.setPlanIndata(wmImNoticeH.getImData());
            wmPlatIo.setPlatId(wmImNoticeH.getPlatformCode());
            wmPlatIo.setPlatSta(wm_sta1);
            wmPlatIo.setPlatBeizhu("司机:" + wmImNoticeH.getImCarDri() + "电话:"
                    + wmImNoticeH.getImCarMobile());
            wmPlatIoService.save(wmPlatIo);
            if (wmImNoticeH.getWmImNoticeIS() != null && wmImNoticeH.getWmImNoticeIS().size() > 0) {
                List<WmImNoticeI> noticeIList = packNoticeI(wmImNoticeH, wmImNoticeH.getWmImNoticeIS());
                if (CollectionUtil.isNotEmpty(noticeIList)) {
                    wmImNoticeIService.saveBatch(noticeIList);
                }
            }
            noticeids.add(noticeid);
        }
        String join = org.apache.commons.lang.StringUtils.join(noticeids, ",");
        return join;
    }

    private List<WmImNoticeI> packNoticeI(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList) {

        List<WmImNoticeI> wmImNoticeIReturnList = new ArrayList<>();
        for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
            wmImNoticeI.setTenantId(wmImNoticeH.getTenantId());
            //外键设置
            wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
            wmImNoticeI.setCusCode(wmImNoticeH.getCusCode());
//                wmImNoticeIMapper.insert(entity);
            //查询商品
            MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmImNoticeI.getGoodsCode()).one();
            if (mdGoods == null) {
//                return null;
                throw new JeecgBootException("该商品不存在" + wmImNoticeI.getGoodsCode());
            }
            wmImNoticeI.setShpGuiGe(mdGoods.getShpGuiGe());
            //多单位  cez
            List<MdGoodsItem> listmdgoodsitem = mdGoodsItemService.lambdaQuery().eq(MdGoodsItem::getShpBianMa, wmImNoticeI.getGoodsCode()).list();
            String itembaseunit = "";
            String basegoodscount = "";
            String itembarcode = "";
//           String goodsname = mdGoods.getShpBianMa();
            String goodsname = mdGoods.getShpMingCheng();
            Boolean itemflag = false;
            for (MdGoodsItem mdGoodsItem : listmdgoodsitem) {
                if (mdGoodsItem.getUnit2().equals(wmImNoticeI.getGoodsUnit())) {
                    itembaseunit = mdGoodsItem.getUnit1();
                    itembarcode = mdGoodsItem.getSttr1();
                    Double basegoods = Double.parseDouble(mdGoodsItem.getNum2()) * Double.parseDouble(wmImNoticeI.getGoodsCount());
                    basegoodscount = basegoods.toString();
                    goodsname = mdGoodsItem.getShpMingCheng();
                    itemflag = true;
                }
            }
            //多单位   cez
//            wmImNoticeI.setGoodsCode(mdGoods.getGoodsId());
            wmImNoticeI.setGoodsCode(mdGoods.getShpBianMa());
            if (StringUtils.isNotEmpty(mdGoods.getTiJiCm()) && StringUtils.isNotEmpty(mdGoods.getTiJiCm()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
                wmImNoticeI.setGoodsFvol(String.valueOf(Double.parseDouble(mdGoods.getTiJiCm()) * Double.parseDouble(wmImNoticeI.getGoodsCount())));
            }

            wmImNoticeI.setChpShuXing(mdGoods.getChpShuXing());

            //多单位   cez
            if (itemflag) {
                wmImNoticeI.setBarcode(itembarcode);
                wmImNoticeI.setBaseUnit(itembaseunit);
                wmImNoticeI.setBaseGoodscount(basegoodscount);
            } else {
                wmImNoticeI.setBarcode(mdGoods.getShpTiaoMa());
                wmImNoticeI.setBaseUnit(mdGoods.getJshDanWei());
                wmImNoticeI.setBaseGoodscount(wmImNoticeI.getGoodsCount());
            }
            wmImNoticeI.setGoodsName(goodsname);
            //多单位   cez

            if (StringUtils.isNotEmpty(mdGoods.getZhlKg()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
                wmImNoticeI.setGoodsWeight(String.valueOf(Double.parseDouble(mdGoods.getZhlKg()) * Double.parseDouble(wmImNoticeI.getGoodsCount())));
            }
            if ("04".equals(wmImNoticeH.getOrderTypeCode())) {//越库任务
                wmImNoticeI.setGoodsQmCount("0");
                wmImNoticeI.setBinPre("Y");
                wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
                WmInQmI wmInQmI = new WmInQmI();
                wmInQmI.setBinId(wmImNoticeH.getNoticeId());//仓位
                wmInQmI.setImNoticeId(wmImNoticeH.getNoticeId());//通知单号
                wmInQmI.setImNoticeItem(wmImNoticeI.getId());
                wmInQmI.setBinSta("Y");
                wmInQmI.setCusCode(wmImNoticeH.getCusCode());
                ;
                wmInQmI.setTinId(wmImNoticeH.getNoticeId());
                wmInQmI.setTinTj(wmImNoticeI.getGoodsFvol());
                wmInQmI.setTinZhl(wmImNoticeI.getGoodsWeight());
                wmInQmI.setGoodsId(wmImNoticeI.getGoodsCode());
                wmInQmI.setGoodsUnit(wmImNoticeI.getGoodsUnit());
                wmInQmI.setQmOkQuat(wmImNoticeI.getGoodsCount());
                wmInQmI.setImQuat(wmImNoticeI.getGoodsCount());
                wmInQmI.setBaseQmcount(wmImNoticeI.getBaseGoodscount());
                wmInQmI.setBaseUnit(wmImNoticeI.getBaseUnit());
                wmInQmI.setProData(new Date());
                wmInQmI.setGoodsBatch(new SimpleDateFormat("yyyy-MM-dd").format(wmInQmI.getProData()));
                wmInQmI.setBaseGoodscount(wmImNoticeI.getBaseGoodscount());
                wmInQmI.setImCusCode(wmImNoticeH.getImCusCode());
                wmInQmIService.save(wmInQmI);
            } else {
                //wmImNoticeI.setBinPre("N");
                wmImNoticeI.setGoodsQmCount("0");
                wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
//                wmImNoticeI.setImBeizhu(wmImNoticeH.getImBeizhu());
                wmImNoticeI.setImCusCode(wmImNoticeH.getImCusCode());
            }
            wmImNoticeIReturnList.add(wmImNoticeI);
        }
        return wmImNoticeIReturnList;
    }

    @Override
    @Transactional
    public void updateMain(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList) throws Exception {
        wmImNoticeHMapper.updateById(wmImNoticeH);

        //1.先删除子表数据
//        wmImNoticeIMapper.deleteByMainId(wmImNoticeH.getNoticeId());

        //2.子表数据重新插入
      /*  if (wmImNoticeIList != null && wmImNoticeIList.size() > 0) {
            for (WmImNoticeI entity : wmImNoticeIList) {
                //外键设置
                entity.setImNoticeId(wmImNoticeH.getNoticeId());
                wmImNoticeIMapper.insert(entity);

            }
//            wmImNoticeIService.updateBatchById(wmImNoticeIList);
        }*/
        //修改采购编辑

        //查询单号下所有的子表
        List<WmImNoticeI> wmImNoticeIListsold = wmImNoticeIService.selectByMainId(wmImNoticeH.getNoticeId());

        if (wmImNoticeIList != null && wmImNoticeIList.size() > 0) {
            List<WmImNoticeI> noticeIList = packNoticeI(wmImNoticeH, wmImNoticeIList);
            for (WmImNoticeI wmImNoticeI : noticeIList) {
              /*  for (WmImNoticeI imNoticeI : wmImNoticeIList) {
                    if(wmImNoticeI.getId().equals(imNoticeI.getId())){
                        wmImNoticeI.setBinPre(imNoticeI.getBinPre());
                    }
                }*/
                wmImNoticeI.setGoodsQmCount(null);
                if(StringUtils.isNotEmpty(wmImNoticeI.getId())){
                    for (WmImNoticeI imNoticeI : wmImNoticeIListsold) {
                        if(wmImNoticeI.getId().equals(imNoticeI.getId())){
                            BeanUtil.copyProperties(wmImNoticeI,imNoticeI,CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
                            wmImNoticeIService.updateById(imNoticeI);
                        }
                    }
                }else {
                    wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
                    wmImNoticeI.setCusCode(wmImNoticeIListsold.get(0).getCusCode());
                    wmImNoticeIService.save(wmImNoticeI);
                }

            }
            for (WmImNoticeI wmImNoticeI : wmImNoticeIListsold) {
                boolean isupdate = false;
                for (WmImNoticeI imNoticeI : wmImNoticeIList) {
                    if(StringUtils.isNotEmpty(imNoticeI.getId())){
                        if(wmImNoticeI.getId().equals(imNoticeI.getId())){
                            isupdate = true;
                        }

                    }
                }
                if(!isupdate){
                        wmImNoticeI.setBinPre("Y");
                        wmImNoticeIService.updateById(wmImNoticeI);
                }
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        WmImNoticeH wmImNoticeH = this.getById(id);
        //查询是否已经收货
        if (wmInQmIService.lambdaQuery().eq(WmInQmI::getImNoticeId, wmImNoticeH.getNoticeId()).count() > 0) {
            throw new JeecgBootException("部分商品已收货，不允许删除");
        }
        wmImNoticeIMapper.deleteByMainId(wmImNoticeH.getNoticeId());
        wmImNoticeHMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delMains(String id) {
        WmImNoticeH wmImNoticeH = this.getById(id);
        //查询是否已经收货
        if (wmInQmIService.lambdaQuery().eq(WmInQmI::getImNoticeId, wmImNoticeH.getNoticeId()).count() > 0) {
            throw new JeecgBootException("部分商品已收货，不允许删除");
        }
        List<WmImNoticeI> list = wmImNoticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId, wmImNoticeH.getNoticeId()).list();
        for (WmImNoticeI wmImNoticeI : list) {
            wmImNoticeIMapper.deleteByMainId(wmImNoticeI.getImNoticeId());
        }
        wmImNoticeHMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            wmImNoticeIMapper.deleteByMainId(id.toString());
            wmImNoticeHMapper.deleteById(id);
        }
    }

    public boolean checkFitKw(BaKw baKw, MdGoods mdGoods) {
        if (baKw != null && StringUtils.isNotEmpty(baKw.getPartType())) {
            if (mdGoods == null) {
                return true;
            }
            List<String> partTypeList = Arrays.asList(baKw.getPartType().split(","));

            return partTypeList.contains(mdGoods.getClassification());

        } else {
            return true;
        }
    }


    @Override
    @Transactional
    public void saveMainForce(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList) {
        String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
        String noticeid = null;
        if (StringUtils.isEmpty(wmImNoticeH.getOrderTypeCode())) {
            wmImNoticeH.setOrderTypeCode("01");
        }
        //查询今天入库量
        int count = this.lambdaQuery()
                .eq(WmImNoticeH::getOrderTypeCode, wmImNoticeH.getOrderTypeCode())
                .apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'")
                .count();
        int newcount = count + 1;

        if ("03".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "TH"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("01".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "RK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("04".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "YK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("09".equals(wmImNoticeH.getOrderTypeCode())) {
            noticeid = "QTRK"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else {
            noticeid = "QT"
                    + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        }
        wmImNoticeH.setNoticeId(noticeid);
        wmImNoticeH.setImSta(wm_sta1);
        //查询供应商
        MdCus mdCus = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa, wmImNoticeH.getCusCode()).one();
        if (mdCus != null) {
            wmImNoticeH.setCusName(mdCus.getZhongWenQch());
        }
        wmImNoticeHMapper.insert(wmImNoticeH);

        //月台
        WmPlatIo wmPlatIo = new WmPlatIo();
        wmPlatIo.setCarno(wmImNoticeH.getImCarNo());
        wmPlatIo.setDocId(wmImNoticeH.getNoticeId());
        wmPlatIo.setPlanIndata(wmImNoticeH.getImData());
        wmPlatIo.setPlatId(wmImNoticeH.getPlatformCode());
        wmPlatIo.setPlatSta(wm_sta1);
        wmPlatIo.setPlatBeizhu("司机:" + wmImNoticeH.getImCarDri() + "电话:"
                + wmImNoticeH.getImCarMobile());
        wmPlatIoService.save(wmPlatIo);

        if (wmImNoticeIList != null && wmImNoticeIList.size() > 0) {
            List<WmImNoticeI> noticeIList = packNoticeIForce(wmImNoticeH, wmImNoticeIList);
            wmImNoticeIService.saveBatch(noticeIList);
        }

    }

    @Override
    public Result<?> doPrintBqList(String id) {
        WmImNoticeH wmImNoticeH = this.getById(id);
        List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.selectByMainId(wmImNoticeH.getNoticeId());
        List<ApiEntity> apiEntityList = new ArrayList<>();
        String dateTime = DateUtils.formatDate(new Date(), "yyyy/MM/dd HH:mm");
        for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
            ApiEntity apiEntity = new ApiEntity();
            apiEntity.setQuery01(wmImNoticeI.getGoodsBatch());
            apiEntity.setQuery02(dateTime);
            apiEntity.setQuery03(wmImNoticeI.getGoodsCode());
            apiEntity.setQuery04(wmImNoticeI.getGoodsName());
            apiEntity.setQuery05(wmImNoticeI.getShpGuiGe());
            apiEntity.setQuery06(wmImNoticeH.getU8Dhcode());
            apiEntityList.add(apiEntity);
        }
        return Result.ok(apiEntityList);
    }


    private List<WmImNoticeI> packNoticeIForce(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList) {
        List<WmImNoticeI> wmImNoticeIReturnList = new ArrayList<>();
        for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
            //外键设置
            wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
            wmImNoticeI.setCusCode(wmImNoticeH.getCusCode());
//                wmImNoticeIMapper.insert(entity);
            //查询商品
            MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmImNoticeI.getGoodsCode()).one();
            long hiti = 0;
            if (StringUtil.isEmpty(wmImNoticeI.getBinPlan()) && StringUtils.isNotEmpty(mdGoods.getMpCengGao()) && StringUtils.isNotEmpty(mdGoods.getMpDanCeng()) && StringUtils.isNotEmpty(mdGoods.getChlShl())) {
                hiti = Long.parseLong(wmImNoticeI.getGoodsCount()) / (Long.parseLong(mdGoods.getMpCengGao()) * Long.parseLong(mdGoods.getMpDanCeng()) * Long.parseLong(mdGoods.getChlShl()));
                wmImNoticeI.setBinPlan(Long.toString(hiti));
            }

            if (StringUtils.isNotEmpty(wmImNoticeI.getBinPlan())) {
                //查询库位
                BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, wmImNoticeI.getBinPlan()).one();
                if (StringUtils.isNotEmpty(baKw.getPartType())) {
                    List<String> partTypeList = Arrays.asList(baKw.getPartType().split(","));
                    if (!partTypeList.contains(mdGoods.getClassification())) {
                        baKw.setPartType(baKw.getPartType() + "," + mdGoods.getClassification());
                        baKwMapper.updateById(baKw);
                        wmImNoticeI.setRemark(mdGoods.getClassification());
                    }
                }
            }

            wmImNoticeI.setGoodsCode(mdGoods.getShpBianMa());
            wmImNoticeI.setGoodsName(mdGoods.getShpMingCheng());
            if (StringUtils.isNotEmpty(mdGoods.getTiJiCm()) && StringUtils.isNotEmpty(mdGoods.getTiJiCm()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
                wmImNoticeI.setGoodsFvol(String.valueOf(Double.parseDouble(mdGoods.getTiJiCm()) * Double.parseDouble(wmImNoticeI.getGoodsCount())));
            }
            wmImNoticeI.setBarcode(mdGoods.getShpTiaoMa());
            wmImNoticeI.setChpShuXing(mdGoods.getChpShuXing());
            if (StringUtils.isNotEmpty(mdGoods.getJshDanWei()) && (!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei())) && StringUtils.isNotEmpty(mdGoods.getChlShl()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
                wmImNoticeI.setBaseGoodscount(String.valueOf(Double.parseDouble(mdGoods.getChlShl()) * Double.parseDouble(wmImNoticeI.getGoodsCount())));
            } else {
                wmImNoticeI.setBaseGoodscount(wmImNoticeI.getGoodsCount());
            }
            wmImNoticeI.setGoodsUnit(mdGoods.getShlDanWei());
            wmImNoticeI.setBaseUnit(mdGoods.getJshDanWei());
            if (StringUtils.isNotEmpty(mdGoods.getZhlKg()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
                wmImNoticeI.setGoodsWeight(String.valueOf(Double.parseDouble(mdGoods.getZhlKg()) * Double.parseDouble(wmImNoticeI.getGoodsCount())));
            }
            if ("04".equals(wmImNoticeH.getOrderTypeCode())) {//越库任务
                wmImNoticeI.setGoodsQmCount("0");
                wmImNoticeI.setBinPre("Y");
                wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
                WmInQmI wmInQmI = new WmInQmI();
                wmInQmI.setBinId(wmImNoticeH.getNoticeId());//仓位
                wmInQmI.setImNoticeId(wmImNoticeH.getNoticeId());//通知单号
                wmInQmI.setImNoticeItem(wmImNoticeI.getId());
                wmInQmI.setBinSta("Y");
                wmInQmI.setCusCode(wmImNoticeH.getCusCode());
                wmInQmI.setTinId(wmImNoticeH.getNoticeId());
                wmInQmI.setTinTj(wmImNoticeI.getGoodsFvol());
                wmInQmI.setTinZhl(wmImNoticeI.getGoodsWeight());
                wmInQmI.setGoodsId(wmImNoticeI.getGoodsCode());
                wmInQmI.setGoodsUnit(wmImNoticeI.getGoodsUnit());
                wmInQmI.setQmOkQuat(wmImNoticeI.getGoodsCount());
                wmInQmI.setImQuat(wmImNoticeI.getGoodsCount());
                wmInQmI.setBaseQmcount(wmImNoticeI.getBaseGoodscount());
                wmInQmI.setBaseUnit(wmImNoticeI.getBaseUnit());
                wmInQmI.setProData(new Date());
                wmInQmI.setGoodsBatch(new SimpleDateFormat("yyyy-MM-dd").format(wmInQmI.getProData()));
                wmInQmI.setBaseGoodscount(wmImNoticeI.getBaseGoodscount());
                wmInQmI.setImCusCode(wmImNoticeH.getImCusCode());
                wmInQmIService.save(wmInQmI);
            } else {
                wmImNoticeI.setBinPre("N");
                wmImNoticeI.setGoodsQmCount("0");
                wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
                wmImNoticeI.setImBeizhu(wmImNoticeH.getImBeizhu());
                wmImNoticeI.setImCusCode(wmImNoticeH.getImCusCode());
            }
            wmImNoticeIReturnList.add(wmImNoticeI);
        }
        return wmImNoticeIReturnList;
    }

    @Override
    public void doPrintckd(String id,String language, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(language) && "en".equals(language)){
            try {
                WmImNoticeH wmImNoticeH = wmImNoticeHMapper.selectById(id);//获取抬头
                MdSup mdSup = new MdSup();
                if (StringUtil.isNotEmpty(wmImNoticeH.getSupCode())) {
                    List<MdSup> list = mdSupService.lambdaQuery().eq(MdSup::getGysBianMa, wmImNoticeH.getSupCode()).list();
                    if (CollectionUtil.isNotEmpty(list)) {
                        mdSup = list.get(0);
                    }
                }

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("ContractOfPurchase");
                sheet.setDisplayGridlines(false);
                sheet.setColumnWidth(0, 20 * 200);
                sheet.setColumnWidth(1, 20 * 256);
                sheet.setColumnWidth(2, 20 * 300);
                sheet.setColumnWidth(3, 20 * 256);
                sheet.setColumnWidth(4, 20 * 265);
                sheet.setColumnWidth(5, 20 * 256);
                sheet.setColumnWidth(6, 20 * 256);

                Row row1 = sheet.createRow(0);
                row1.setHeight((short) 1100);
                Cell cell1 = row1.createCell(0);
                cell1.setCellValue("ContractOfPurchase");

                CellStyle cellStyle1 = workbook.createCellStyle();

                Font font1 = workbook.createFont();
                font1.setFontName("SimSun");
                font1.setFontHeightInPoints((short) 18);

                //水平居中
                cellStyle1.setAlignment(HorizontalAlignment.CENTER);
                //垂直居中
                cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

                cellStyle1.setFont(font1);
                cell1.setCellStyle(cellStyle1);

                try {
                    FileInputStream inputStream = new FileInputStream("D:\\opt\\1.png");
                    //获取图片
                    int picIdex = ((XSSFWorkbook) workbook).addPicture(inputStream, Workbook.PICTURE_TYPE_PNG);
                    CreationHelper creationHelper = workbook.getCreationHelper();

                    //图片编辑器
                    Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
                    ClientAnchor clientAnchor = creationHelper.createClientAnchor();
                    clientAnchor.setRow1(0);
                    clientAnchor.setCol1(4);
                    Picture picture = drawingPatriarch.createPicture(clientAnchor, picIdex);
                    picture.resize();
                }catch (Exception e){
                    e.printStackTrace();
                }

                //通用字体
                Font font = workbook.createFont();
                font.setFontName("宋体");
                font.setFontHeightInPoints((short) 15);
                //通用样式
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFont(font);

                Row row2 = sheet.createRow(2);
                row2.setHeight((short) 500);
                Cell cell2 = row2.createCell(0);
                MdSup sup = mdSupService.lambdaQuery().eq(MdSup::getGysBianMa, wmImNoticeH.getSupCode()).one();
                cell2.setCellValue("Supplier：" + (StringUtils.isNotEmpty(sup.getZhongWenQch()) ? sup.getZhongWenQch() : ""));
                cell2.setCellStyle(cellStyle);

                Cell cell3 = row2.createCell(3);
                cell3.setCellValue("PurchaseContractNumber：" + (StringUtils.isNotEmpty(wmImNoticeH.getImBeizhu()) ? wmImNoticeH.getImBeizhu() : ""));
                cell3.setCellStyle(cellStyle);

                Row row3 = sheet.createRow(3);
                row3.setHeight((short) 500);
                Cell cell4 = row3.createCell(0);
                cell4.setCellValue("Addr：" + (StringUtils.isNotEmpty(mdSup.getDiZhi()) ? mdSup.getDiZhi() : ""));
                cell4.setCellStyle(cellStyle);

                Cell cell5 = row3.createCell(3);
                cell5.setCellValue("Date：" + DateUtil.format(wmImNoticeH.getCreateTime(), "yyyy-MM-dd"));
                cell5.setCellStyle(cellStyle);

                Row row4 = sheet.createRow(4);
                row4.setHeight((short) 500);
                Cell cell6 = row4.createCell(0);
                cell6.setCellValue("Contact：" + (StringUtils.isNotEmpty(mdSup.getZhuLianXiRen()) ? mdSup.getZhuLianXiRen() : ""));
                cell6.setCellStyle(cellStyle);

                Cell cell7 = row4.createCell(3);
                cell7.setCellValue("PurchaseName：" + (StringUtils.isNotEmpty(wmImNoticeH.getCreateBy()) ? wmImNoticeH.getCreateBy() : ""));
                cell7.setCellStyle(cellStyle);

                Row row5 = sheet.createRow(5);
                row5.setHeight((short) 500);
                Cell cell8 = row5.createCell(0);
                cell8.setCellValue("Tel：" + (StringUtils.isNotEmpty(mdSup.getDianHua()) ? mdSup.getDianHua() : ""));
                cell8.setCellStyle(cellStyle);
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

                List<WmImNoticeI> noticeIS = wmImNoticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId, wmImNoticeH.getNoticeId()).list();
                String[] titles = {"ORDERID", "ITERMNAME", "DESCRIPTION", "QUANTITY", "UNITPRICE(RMB)", "TOTALAMOUNT(RMB)"};
                Row row6 = sheet.createRow(6);
                //定义列名
                for (int i = 0; i < titles.length; i++) {
                    Cell cell = row6.createCell(i);
                    cell.setCellValue(titles[i]);
                    cell.setCellStyle(cellStyle2);
                }
                for (int i = 0; i < noticeIS.size(); i++) {
                    row6 = sheet.createRow(i + 7);
                    row6.setHeight((short) 700);
                    WmImNoticeI wmImNoticeI = noticeIS.get(i);
                    Cell imNoticeId = row6.createCell(0);
                    imNoticeId.setCellValue(wmImNoticeI.getContractlno());
                    imNoticeId.setCellStyle(cellStyle2);
                    Cell goodsName = row6.createCell(1);
                    goodsName.setCellValue(wmImNoticeI.getGoodsName());
                    goodsName.setCellStyle(cellStyle2);
                    Cell imBeizhu = row6.createCell(2);
                    imBeizhu.setCellValue(wmImNoticeI.getImBeizhu());
                    imBeizhu.setCellStyle(cellStyle2);
                    Cell goodsCount = row6.createCell(3);
                    goodsCount.setCellValue(wmImNoticeI.getGoodsCount());
                    goodsCount.setCellStyle(cellStyle2);
                    Cell avgunitprice = row6.createCell(4);
                    avgunitprice.setCellValue(wmImNoticeI.getUnitPrice().toString());
                    avgunitprice.setCellStyle(cellStyle2);
                    Cell totalamout = row6.createCell(5);
                    totalamout.setCellValue(wmImNoticeI.getProcode());
                    totalamout.setCellStyle(cellStyle2);
                }
                Row row8 = sheet.createRow(noticeIS.size() + 7);
                row8.setHeight((short) 500);
                Cell cell21 = row8.createCell(0);
                cell21.setCellValue("TOTAL");
                CellRangeAddress addresses15 = new CellRangeAddress(noticeIS.size() + 7, noticeIS.size() + 7, 0, 2);
                RegionUtil.setBorderBottom(BorderStyle.THIN, addresses15, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, addresses15, sheet);
                sheet.addMergedRegion(addresses15);
                cell21.setCellStyle(cellStyle2);

                BigDecimal quanties = new BigDecimal("0");
                BigDecimal prices = new BigDecimal("0");
                BigDecimal totalprices = new BigDecimal("0");
                for (WmImNoticeI noticeI : noticeIS) {
                    quanties = NumberUtil.add(quanties.toString(), noticeI.getGoodsCount());
//                prices = NumberUtil.add(prices.toString(), noticeI.getAvgunitprice());
                    totalprices = NumberUtil.add(totalprices.toString(), noticeI.getTotalamout());
                }
                Cell cell22 = row8.createCell(3);
                cell22.setCellValue(quanties.toString());
                cell22.setCellStyle(cellStyle2);

                Cell cell23 = row8.createCell(4);
                cell23.setCellValue(prices.toString());
                cell23.setCellStyle(cellStyle2);

                Cell cell24 = row8.createCell(5);
                cell24.setCellValue(totalprices.toString());
                cell24.setCellStyle(cellStyle2);

                String[] titlesss = {"BANK：", "ACCOUNT：", "ACCOUNTNUM："};
                //定义列名
                Row row9 = null;
                for (int i = 0; i < titlesss.length; i++) {
                    row9 = sheet.createRow(noticeIS.size() + 9 + i);
                    row9.setHeight((short) 500);
                    Cell cell33 = row9.createCell(0);
                    cell33.setCellValue(titlesss[i]);
                    cell33.setCellStyle(cellStyle);
                }
                CellStyle cellStyle3 = workbook.createCellStyle();
                cellStyle3.setAlignment(HorizontalAlignment.LEFT);
                cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle3.setBorderLeft(BorderStyle.THIN);
                cellStyle3.setBorderTop(BorderStyle.THIN);
                cellStyle3.setBorderRight(BorderStyle.THIN);
                cellStyle3.setBorderBottom(BorderStyle.THIN);
                cellStyle3.setWrapText(true);
                cellStyle3.setFont(font2);

                Row row12 = sheet.createRow(noticeIS.size() + 12);
                row12.setHeight((short) 6000);
                Cell cell28 = row12.createCell(0);
                CellRangeAddress addresses21 = new CellRangeAddress(noticeIS.size() + 12, noticeIS.size() + 12, 0, 5);
                sheet.addMergedRegion(addresses21);
                RegionUtil.setBorderBottom(BorderStyle.THIN, addresses21, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, addresses21, sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, addresses21, sheet);
                cell28.setCellValue("");
                cell28.setCellStyle(cellStyle3);

                CellStyle cellStyle4 = workbook.createCellStyle();
                cellStyle4.setAlignment(HorizontalAlignment.LEFT);
                cellStyle4.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle4.setBorderBottom(BorderStyle.THIN);
                cellStyle4.setFont(font);

                Row row13 = sheet.createRow(noticeIS.size() + 16);
                row13.setHeight((short) 500);
                Cell cell29 = row13.createCell(0);
                CellRangeAddress addresses22 = new CellRangeAddress(noticeIS.size() + 16, noticeIS.size() + 16, 0, 2);
                sheet.addMergedRegion(addresses22);
                RegionUtil.setBorderBottom(BorderStyle.THIN, addresses22, sheet);
                cell29.setCellValue("Purchaser");
                cell29.setCellStyle(cellStyle4);

                Cell cell30 = row13.createCell(3);
                CellRangeAddress addresses23 = new CellRangeAddress(noticeIS.size() + 16, noticeIS.size() + 16, 3, 5);
                sheet.addMergedRegion(addresses23);
                RegionUtil.setBorderBottom(BorderStyle.THIN, addresses23, sheet);
                cell30.setCellValue("Vendor");
                cell30.setCellStyle(cellStyle4);

                Row row15 = sheet.createRow(noticeIS.size() + 17);
                row15.setHeight((short) 500);
                Cell cell31 = row15.createCell(0);
                CellRangeAddress addresses24 = new CellRangeAddress(noticeIS.size() + 17, noticeIS.size() + 17, 0, 2);
                sheet.addMergedRegion(addresses24);
                cell31.setCellValue("AuthorizedBy");
                cell31.setCellStyle(cellStyle);

                Cell cell32 = row15.createCell(3);
                CellRangeAddress addresses25 = new CellRangeAddress(noticeIS.size() + 17, noticeIS.size() + 17, 3, 5);
                sheet.addMergedRegion(addresses25);
                cell32.setCellValue("AuthorizedBy");
                cell32.setCellStyle(cellStyle);

                Font font3 = workbook.createFont();
                font3.setFontName("宋体");
                font3.setFontHeightInPoints((short) 12);
                //通用样式
                CellStyle cellStyle5 = workbook.createCellStyle();
                cellStyle5.setAlignment(HorizontalAlignment.CENTER);
                cellStyle5.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle5.setFont(font3);

                String[] titless = {"HOSPITALITY", "(Room 309, Building 201, Bu Industrial Zone, Hongli Road, Futian District, Shenzhen)", "HOSPITALITY COMPANY LIMITED",
                        "Room 309 3F 201 Ridge, Shangbu Industrial, Hongli Road, Futian district, Shenzhen, China. PC: 518028"};
                Row row16 = null;
                for (int i = 0; i < titless.length; i++) {
                    row16 = sheet.createRow(noticeIS.size() + 22 + i);
                    row16.setHeight((short) 500);
                    Cell cell33 = row16.createCell(0);
                    CellRangeAddress addresses26 = new CellRangeAddress(noticeIS.size() + 22 + i, noticeIS.size() + 22 + i, 0, 5);
                    sheet.addMergedRegion(addresses26);
                    cell33.setCellValue(titless[i]);
                    cell33.setCellStyle(cellStyle5);
                }
                String fileName = URLEncoder.encode("Contract Of Purchase.xlsx", "UTF-8");
                response.setContentType("application/octet-stream");
                response.setHeader("content-disposition", "attachment;filename=" + fileName);
                response.setHeader("filename", fileName);
                ServletOutputStream outputStream = response.getOutputStream();
                workbook.write(outputStream);
                outputStream.close();
            /*String filest = DateUtil.format(new Date(),"yyyy-MM-dd");
            String savaPath = upLoadPath;
            String fileurl = "pltn" + id + "/" + filest + "/"+ filest + ".xls";

            File file = new File(savaPath + "/" + "pltn" + id + "/" + filest);
            savaPath = savaPath.replace("D:/","D:/");

            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            String outfilePath = savaPath + "/" + fileurl;
            FileOutputStream fileOutputStream = new FileOutputStream(outfilePath);//指定路径与名字和格式
            workbook.write(fileOutputStream);//将数据写出去
            fileOutputStream.close();//关闭输出流
            printUtil.printfile(outfilePath);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                WmImNoticeH wmImNoticeH = wmImNoticeHMapper.selectById(id);//获取抬头
                MdSup mdSup = new MdSup();
                if (StringUtil.isNotEmpty(wmImNoticeH.getSupCode())) {
                    List<MdSup> list = mdSupService.lambdaQuery().eq(MdSup::getGysBianMa, wmImNoticeH.getSupCode()).list();
                    if (CollectionUtil.isNotEmpty(list)) {
                        mdSup = list.get(0);
                    }
                }

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("采购合同");
                sheet.setDisplayGridlines(false);
                sheet.setColumnWidth(0, 20 * 200);
                sheet.setColumnWidth(1, 20 * 256);
                sheet.setColumnWidth(2, 20 * 300);
                sheet.setColumnWidth(3, 20 * 256);
                sheet.setColumnWidth(4, 20 * 265);
                sheet.setColumnWidth(5, 20 * 256);
                sheet.setColumnWidth(6, 20 * 256);

                Row row1 = sheet.createRow(0);
                row1.setHeight((short) 1100);
                Cell cell1 = row1.createCell(0);
                cell1.setCellValue("采购合同");

                CellStyle cellStyle1 = workbook.createCellStyle();

                Font font1 = workbook.createFont();
                font1.setFontName("SimSun");
                font1.setFontHeightInPoints((short) 18);

                //水平居中
                cellStyle1.setAlignment(HorizontalAlignment.CENTER);
                //垂直居中
                cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

                cellStyle1.setFont(font1);
                cell1.setCellStyle(cellStyle1);

                try {
                    FileInputStream inputStream = new FileInputStream("D:\\opt\\1.png");
                    //获取图片
                    int picIdex = ((XSSFWorkbook) workbook).addPicture(inputStream, Workbook.PICTURE_TYPE_PNG);
                    CreationHelper creationHelper = workbook.getCreationHelper();

                    //图片编辑器
                    Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
                    ClientAnchor clientAnchor = creationHelper.createClientAnchor();
                    clientAnchor.setRow1(0);
                    clientAnchor.setCol1(4);
                    Picture picture = drawingPatriarch.createPicture(clientAnchor, picIdex);
                    picture.resize();
                }catch (Exception e){
                    e.printStackTrace();
                }

                //通用字体
                Font font = workbook.createFont();
                font.setFontName("宋体");
                font.setFontHeightInPoints((short) 15);
                //通用样式
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFont(font);

                Row row2 = sheet.createRow(2);
                row2.setHeight((short) 500);
                Cell cell2 = row2.createCell(0);
                MdSup sup = mdSupService.lambdaQuery().eq(MdSup::getGysBianMa, wmImNoticeH.getSupCode()).one();
                cell2.setCellValue("供应商：" + (StringUtils.isNotEmpty(sup.getZhongWenQch()) ? sup.getZhongWenQch() : ""));
                cell2.setCellStyle(cellStyle);

                Cell cell3 = row2.createCell(3);
                cell3.setCellValue("采购合同号：" + (StringUtils.isNotEmpty(wmImNoticeH.getImBeizhu()) ? wmImNoticeH.getImBeizhu() : ""));
                cell3.setCellStyle(cellStyle);

                Row row3 = sheet.createRow(3);
                row3.setHeight((short) 500);
                Cell cell4 = row3.createCell(0);
                cell4.setCellValue("地址：" + (StringUtils.isNotEmpty(mdSup.getDiZhi()) ? mdSup.getDiZhi() : ""));
                cell4.setCellStyle(cellStyle);

                Cell cell5 = row3.createCell(3);
                cell5.setCellValue("日期：" + DateUtil.format(wmImNoticeH.getCreateTime(), "yyyy-MM-dd"));
                cell5.setCellStyle(cellStyle);

                Row row4 = sheet.createRow(4);
                row4.setHeight((short) 500);
                Cell cell6 = row4.createCell(0);
                cell6.setCellValue("联系人：" + (StringUtils.isNotEmpty(mdSup.getZhuLianXiRen()) ? mdSup.getZhuLianXiRen() : ""));
                cell6.setCellStyle(cellStyle);

                Cell cell7 = row4.createCell(3);
                cell7.setCellValue("采购名：" + (StringUtils.isNotEmpty(wmImNoticeH.getCreateBy()) ? wmImNoticeH.getCreateBy() : ""));
                cell7.setCellStyle(cellStyle);

                Row row5 = sheet.createRow(5);
                row5.setHeight((short) 500);
                Cell cell8 = row5.createCell(0);
                cell8.setCellValue("联系电话：" + (StringUtils.isNotEmpty(mdSup.getDianHua()) ? mdSup.getDianHua() : ""));
                cell8.setCellStyle(cellStyle);
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

                List<WmImNoticeI> noticeIS = wmImNoticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId, wmImNoticeH.getNoticeId()).list();
                String[] titles = {"订单ID", "ITEM 品名", "DESCRIPTION 产品描述", "QUANTITY 数量", "UNIT PRICE(RMB) 单价", "TOTAL AMOUNT(RMB) 总金额"};
                Row row6 = sheet.createRow(6);
                //定义列名
                for (int i = 0; i < titles.length; i++) {
                    Cell cell = row6.createCell(i);
                    cell.setCellValue(titles[i]);
                    cell.setCellStyle(cellStyle2);
                }
                for (int i = 0; i < noticeIS.size(); i++) {
                    row6 = sheet.createRow(i + 7);
                    row6.setHeight((short) 700);
                    WmImNoticeI wmImNoticeI = noticeIS.get(i);
                    MdGoods md = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmImNoticeI.getGoodsCode()).one();
                    Cell imNoticeId = row6.createCell(0);
                    imNoticeId.setCellValue(wmImNoticeI.getContractlno());
                    imNoticeId.setCellStyle(cellStyle2);
                    Cell goodsName = row6.createCell(1);
                    goodsName.setCellValue(wmImNoticeI.getGoodsName());
                    goodsName.setCellStyle(cellStyle2);
                   /* Cell imBeizhu = row6.createCell(2);
                    imBeizhu.setCellValue(wmImNoticeI.getImBeizhu());
                    imBeizhu.setCellStyle(cellStyle2);*/
                    Cell imBeizhu = row6.createCell(2);
                    imBeizhu.setCellValue(md.getShpMiaoShu());
                    imBeizhu.setCellStyle(cellStyle2);
                    Cell goodsCount = row6.createCell(3);
                    goodsCount.setCellValue(wmImNoticeI.getGoodsCount());
                    goodsCount.setCellStyle(cellStyle2);

                    if(wmImNoticeI.getUnitPrice()!=null){
                        Cell avgunitprice = row6.createCell(4);
                        avgunitprice.setCellValue(wmImNoticeI.getUnitPrice().toString());
                        avgunitprice.setCellStyle(cellStyle2);
                    }else {
                        Cell avgunitprice = row6.createCell(4);
                        avgunitprice.setCellValue("无价格");
                        avgunitprice.setCellStyle(cellStyle2);
                    }
                    Cell totalamout = row6.createCell(5);
                    totalamout.setCellValue(wmImNoticeI.getProcode());
                    totalamout.setCellStyle(cellStyle2);
                }
                Row row8 = sheet.createRow(noticeIS.size() + 7);
                row8.setHeight((short) 500);
                Cell cell21 = row8.createCell(0);
                cell21.setCellValue("合计");
                CellRangeAddress addresses15 = new CellRangeAddress(noticeIS.size() + 7, noticeIS.size() + 7, 0, 2);
                RegionUtil.setBorderBottom(BorderStyle.THIN, addresses15, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, addresses15, sheet);
                sheet.addMergedRegion(addresses15);
                cell21.setCellStyle(cellStyle2);

                BigDecimal quanties = new BigDecimal("0");
                BigDecimal prices = new BigDecimal("0");
                BigDecimal totalprices = new BigDecimal("0");
                for (WmImNoticeI noticeI : noticeIS) {
                    quanties = NumberUtil.add(quanties.toString(), noticeI.getGoodsCount());
//                prices = NumberUtil.add(prices.toString(), noticeI.getAvgunitprice());
                    totalprices = NumberUtil.add(totalprices.toString(), noticeI.getTotalamout());
                }
                Cell cell22 = row8.createCell(3);
                cell22.setCellValue(quanties.toString());
                cell22.setCellStyle(cellStyle2);

                Cell cell23 = row8.createCell(4);
                cell23.setCellValue(prices.toString());
                cell23.setCellStyle(cellStyle2);

                Cell cell24 = row8.createCell(5);
                cell24.setCellValue(totalprices.toString());
                cell24.setCellStyle(cellStyle2);

                String[] titlesss = {"开户行：", "账户：", "账号："};
                //定义列名
                Row row9 = null;
                for (int i = 0; i < titlesss.length; i++) {
                    row9 = sheet.createRow(noticeIS.size() + 9 + i);
                    row9.setHeight((short) 500);
                    Cell cell33 = row9.createCell(0);
                    cell33.setCellValue(titlesss[i]);
                    cell33.setCellStyle(cellStyle);
                }
                CellStyle cellStyle3 = workbook.createCellStyle();
                cellStyle3.setAlignment(HorizontalAlignment.LEFT);
                cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle3.setBorderLeft(BorderStyle.THIN);
                cellStyle3.setBorderTop(BorderStyle.THIN);
                cellStyle3.setBorderRight(BorderStyle.THIN);
                cellStyle3.setBorderBottom(BorderStyle.THIN);
                cellStyle3.setWrapText(true);
                cellStyle3.setFont(font2);

                Row row12 = sheet.createRow(noticeIS.size() + 12);
                row12.setHeight((short) 6000);
                Cell cell28 = row12.createCell(0);
                CellRangeAddress addresses21 = new CellRangeAddress(noticeIS.size() + 12, noticeIS.size() + 12, 0, 5);
                sheet.addMergedRegion(addresses21);
                RegionUtil.setBorderBottom(BorderStyle.THIN, addresses21, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, addresses21, sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, addresses21, sheet);
                cell28.setCellValue("");
                cell28.setCellStyle(cellStyle3);

                CellStyle cellStyle4 = workbook.createCellStyle();
                cellStyle4.setAlignment(HorizontalAlignment.LEFT);
                cellStyle4.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle4.setBorderBottom(BorderStyle.THIN);
                cellStyle4.setFont(font);

                Row row13 = sheet.createRow(noticeIS.size() + 16);
                row13.setHeight((short) 500);
                Cell cell29 = row13.createCell(0);
                CellRangeAddress addresses22 = new CellRangeAddress(noticeIS.size() + 16, noticeIS.size() + 16, 0, 2);
                sheet.addMergedRegion(addresses22);
                RegionUtil.setBorderBottom(BorderStyle.THIN, addresses22, sheet);
                cell29.setCellValue("（采购）Purchaser");
                cell29.setCellStyle(cellStyle4);

                Cell cell30 = row13.createCell(3);
                CellRangeAddress addresses23 = new CellRangeAddress(noticeIS.size() + 16, noticeIS.size() + 16, 3, 5);
                sheet.addMergedRegion(addresses23);
                RegionUtil.setBorderBottom(BorderStyle.THIN, addresses23, sheet);
                cell30.setCellValue("（供应商）Vendor");
                cell30.setCellStyle(cellStyle4);

                Row row15 = sheet.createRow(noticeIS.size() + 17);
                row15.setHeight((short) 500);
                Cell cell31 = row15.createCell(0);
                CellRangeAddress addresses24 = new CellRangeAddress(noticeIS.size() + 17, noticeIS.size() + 17, 0, 2);
                sheet.addMergedRegion(addresses24);
                cell31.setCellValue("（签字）Authorized by");
                cell31.setCellStyle(cellStyle);

                Cell cell32 = row15.createCell(3);
                CellRangeAddress addresses25 = new CellRangeAddress(noticeIS.size() + 17, noticeIS.size() + 17, 3, 5);
                sheet.addMergedRegion(addresses25);
                cell32.setCellValue("（签字）Authorized by");
                cell32.setCellStyle(cellStyle);

                Font font3 = workbook.createFont();
                font3.setFontName("宋体");
                font3.setFontHeightInPoints((short) 12);
                //通用样式
                CellStyle cellStyle5 = workbook.createCellStyle();
                cellStyle5.setAlignment(HorizontalAlignment.CENTER);
                cellStyle5.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle5.setFont(font3);

                String[] titless = {"商贸有限公司", "(深圳市福田区红荔路上步工业区201栋309)", "HOSPITALITY COMPANY LIMITED",
                        "Room 309 3F 201 Ridge, Shangbu Industrial, Hongli Road, Futian district, Shenzhen, China. PC: 518028"};
                Row row16 = null;
                for (int i = 0; i < titless.length; i++) {
                    row16 = sheet.createRow(noticeIS.size() + 22 + i);
                    row16.setHeight((short) 500);
                    Cell cell33 = row16.createCell(0);
                    CellRangeAddress addresses26 = new CellRangeAddress(noticeIS.size() + 22 + i, noticeIS.size() + 22 + i, 0, 5);
                    sheet.addMergedRegion(addresses26);
                    cell33.setCellValue(titless[i]);
                    cell33.setCellStyle(cellStyle5);
                }
                String fileName = URLEncoder.encode("采购合同.xlsx", "UTF-8");
                response.setContentType("application/octet-stream");
                response.setHeader("content-disposition", "attachment;filename=" + fileName);
                response.setHeader("filename", fileName);
                ServletOutputStream outputStream = response.getOutputStream();
                workbook.write(outputStream);
                outputStream.close();
            /*String filest = DateUtil.format(new Date(),"yyyy-MM-dd");
            String savaPath = upLoadPath;
            String fileurl = "pltn" + id + "/" + filest + "/"+ filest + ".xls";

            File file = new File(savaPath + "/" + "pltn" + id + "/" + filest);
            savaPath = savaPath.replace("D:/","D:/");

            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            String outfilePath = savaPath + "/" + fileurl;
            FileOutputStream fileOutputStream = new FileOutputStream(outfilePath);//指定路径与名字和格式
            workbook.write(fileOutputStream);//将数据写出去
            fileOutputStream.close();//关闭输出流
            printUtil.printfile(outfilePath);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
