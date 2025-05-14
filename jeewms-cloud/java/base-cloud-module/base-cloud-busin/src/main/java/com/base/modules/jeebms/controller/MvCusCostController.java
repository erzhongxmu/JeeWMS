package com.base.modules.jeebms.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.net.URLDecoder;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyuncs.utils.StringUtils;
import com.base.common.api.vo.Result;
import com.base.common.util.DateUtils;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeebms.entity.MvCusCost;
import com.base.modules.jeebms.entity.WmBillConf;
import com.base.modules.jeebms.service.IMvCusCostService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeebms.service.IWmBillConfService;
import com.base.modules.jeewms.entity.BaCostConf;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.mapper.WmDayCostConfMapper;
import com.base.modules.jeewms.service.IMdCusService;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import com.base.common.system.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

import static org.apache.poi.ss.usermodel.ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE;

/**
 * @Description: 客户账单
 * @Author: base-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
@Api(tags="客户账单")
@RestController
@RequestMapping("/jeebms/mvCusCost")
@Slf4j
public class MvCusCostController extends BaseController<MvCusCost, IMvCusCostService> {
	@Autowired
	private IMvCusCostService mvCusCostService;
	@Autowired
	private IMdCusService mdCusService;
	@Autowired
	private WmDayCostConfMapper wmDayCostConfMapper;
	@Autowired
	private IWmBillConfService wmBillConfService;
	/**
	 * 分页列表查询
	 *
	 * @param mvCusCost
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "客户账单-分页列表查询")
	@ApiOperation(value="客户账单-分页列表查询", notes="客户账单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MvCusCost mvCusCost,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MvCusCost> queryWrapper = QueryGenerator.initQueryWrapper(mvCusCost, req.getParameterMap());
		Page<MvCusCost> page = new Page<MvCusCost>(pageNo, pageSize);
		IPage<MvCusCost> pageList = mvCusCostService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param mvCusCost
	 * @return
	 */
	@AutoLog(value = "客户账单-添加")
	@ApiOperation(value="客户账单-添加", notes="客户账单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MvCusCost mvCusCost) {
		mvCusCostService.save(mvCusCost);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param mvCusCost
	 * @return
	 */
	@AutoLog(value = "客户账单-编辑")
	@ApiOperation(value="客户账单-编辑", notes="客户账单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MvCusCost mvCusCost) {
		mvCusCostService.updateById(mvCusCost);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户账单-通过id删除")
	@ApiOperation(value="客户账单-通过id删除", notes="客户账单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mvCusCostService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "客户账单-批量删除")
	@ApiOperation(value="客户账单-批量删除", notes="客户账单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mvCusCostService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户账单-通过id查询")
	@ApiOperation(value="客户账单-通过id查询", notes="客户账单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MvCusCost mvCusCost = mvCusCostService.getById(id);
		if(mvCusCost==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(mvCusCost);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mvCusCost
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MvCusCost mvCusCost) {
        return super.exportXls(request, mvCusCost, MvCusCost.class, "客户账单");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, MvCusCost.class);
    }
	 @RequestMapping(value = "/doPrint",method = RequestMethod.GET)
	 public void downReceiveExcelysd(@RequestParam(value = "id",required = false) String id,
									 @RequestParam(value = "begindate",required = false) String begindate,
									 @RequestParam(value = "enddate",required = false) String enddate,
									 @RequestParam(value = "sfqj",required = false) String sfqj
//			 ,
//									 HttpServletRequest request
						 ,
						 HttpServletResponse response
	 ) {

		 MvCusCost mvCusCost = mvCusCostService.getById(id);// 获取抬头

		 WmBillConf wmBillConf = new WmBillConf();
		 QueryWrapper<WmBillConf> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("cus_code",mvCusCost.getCusCode());
		 List<WmBillConf> listbill = wmBillConfService.list(queryWrapper);

		 WmBillConf wmBillConf1 = listbill.get(0);


		 String  logofile = wmBillConf1.getLogoFile();  //logo图片
    	String comfidzyz = wmBillConf1.getComfidzyzFile();   //章
    	String comfiname= wmBillConf1.getComfiname();
    	String comfiaddr = wmBillConf1.getComfiaddr();
    	String comfitel = wmBillConf1.getComfitel();
    	String comfibankid = wmBillConf1.getComfibankid();
    	String comfibankname = wmBillConf1.getComfibankname();
    	String comfizhucehao = wmBillConf1.getComfizhucehao();
    	String comfibeizhu1= wmBillConf1.getComfibeizhu1();
    	String comfibeizhu2= wmBillConf1.getComfibeizhu2();
    	String comfibeizhu3= wmBillConf1.getComfibeizhu3();
		 // TODO: 2021/12/26
		 OutputStream fileOut = null;
		 BufferedImage bufferImg = null;
//		String codedFileName = null;
		 // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		 try {
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");

			 String setHeaderEnddate =enddate;
			 response.setHeader("content-disposition", "attachment;filename="
					 + mvCusCost.getCusCode()+"("+begindate+" "+setHeaderEnddate+").xls");
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
				 bufferImg = ImageIO.read(new File(logofile));
				 ImageIO.write(bufferImg, "png", byteArrayOut);
				 //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
				 //anchor主要用于设置图片的属性
				 anchor.setAnchorType(DONT_MOVE_AND_RESIZE);
				 //插入图片
				 patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

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
			 cs.setAlignment(HorizontalAlignment.RIGHT);

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
			 cs3.setAlignment(HorizontalAlignment.CENTER);

			 cs3d.setFont(f2);
			 cs3d.setBorderLeft(BorderStyle.MEDIUM);
			 cs3d.setBorderRight(BorderStyle.MEDIUM);
			 cs3d.setBorderTop(BorderStyle.MEDIUM);
			 cs3d.setBorderBottom(BorderStyle.MEDIUM);
			 cs3d.setAlignment(HorizontalAlignment.RIGHT);
			 CellStyle cs31 = wb.createCellStyle();
			 cs31.setFont(f2);
			 cs31.setBorderLeft(BorderStyle.MEDIUM);
			 cs31.setBorderRight(BorderStyle.MEDIUM);
			 cs31.setBorderTop(BorderStyle.MEDIUM);
			 cs31.setBorderBottom(BorderStyle.NONE);

			 CellStyle cs32 = wb.createCellStyle();
			 cs32.setFont(f2);
			 cs32.setBorderLeft(BorderStyle.MEDIUM);
			 cs32.setBorderRight(BorderStyle.MEDIUM);
			 cs32.setBorderTop(BorderStyle.NONE);
			 cs32.setBorderBottom(BorderStyle.NONE);


			 CellStyle cs32c = wb.createCellStyle();
			 cs32c.setFont(f2);
			 cs32c.setBorderLeft(BorderStyle.MEDIUM);
			 cs32c.setBorderRight(BorderStyle.MEDIUM);
			 cs32c.setBorderTop(BorderStyle.NONE);
			 cs32c.setBorderBottom(BorderStyle.NONE);
			 cs32c.setAlignment(HorizontalAlignment.CENTER);

			 CellStyle cs32r = wb.createCellStyle();
			 cs32r.setFont(f2);
			 cs32r.setBorderLeft(BorderStyle.MEDIUM);
			 cs32r.setBorderRight(BorderStyle.MEDIUM);
			 cs32r.setBorderTop(BorderStyle.NONE);
			 cs32r.setBorderBottom(BorderStyle.NONE);
			 cs32r.setAlignment(HorizontalAlignment.RIGHT);


			 CellStyle cs32ra = wb.createCellStyle();
			 cs32ra.setFont(f2);
			 cs32ra.setBorderLeft(BorderStyle.MEDIUM);
			 cs32ra.setBorderRight(BorderStyle.MEDIUM);
			 cs32ra.setBorderTop(BorderStyle.MEDIUM);
			 cs32ra.setBorderBottom(BorderStyle.MEDIUM);
			 cs32ra.setAlignment(HorizontalAlignment.RIGHT);

			 CellStyle cs33 = wb.createCellStyle();
			 cs33.setFont(f2);
			 cs33.setBorderLeft(BorderStyle.MEDIUM);
			 cs33.setBorderRight(BorderStyle.MEDIUM);
			 cs33.setBorderTop(BorderStyle.NONE);
			 cs33.setBorderBottom(BorderStyle.MEDIUM);


			 CellStyle cs34 = wb.createCellStyle();
			 cs34.setFont(f2);
			 cs34.setBorderLeft(BorderStyle.MEDIUM);
			 cs34.setBorderRight(BorderStyle.NONE);
			 cs34.setBorderTop(BorderStyle.NONE);
			 cs34.setBorderBottom(BorderStyle.NONE);

			 CellStyle cs35 = wb.createCellStyle();
			 cs35.setFont(f2);
			 cs35.setBorderLeft(BorderStyle.NONE);
			 cs35.setBorderRight(BorderStyle.MEDIUM);
			 cs35.setBorderTop(BorderStyle.NONE);
			 cs35.setBorderBottom(BorderStyle.NONE);


			 CellStyle cs35c = wb.createCellStyle();
			 cs35c.setFont(f2);
			 cs35c.setBorderLeft(BorderStyle.NONE);
			 cs35c.setBorderRight(BorderStyle.MEDIUM);
			 cs35c.setBorderTop(BorderStyle.NONE);
			 cs35c.setBorderBottom(BorderStyle.NONE);
			 cs35c.setAlignment(HorizontalAlignment.RIGHT);


			 CellStyle cs35cd = wb.createCellStyle();
			 cs35cd.setFont(f2);
			 cs35cd.setBorderLeft(BorderStyle.NONE);
			 cs35cd.setBorderRight(BorderStyle.MEDIUM);
			 cs35cd.setBorderTop(BorderStyle.NONE);
			 cs35cd.setBorderBottom(BorderStyle.NONE);
			 cs35cd.setAlignment(HorizontalAlignment.RIGHT);


			 CellStyle cs36 = wb.createCellStyle();
			 cs36.setFont(f2);
			 cs36.setBorderLeft(BorderStyle.MEDIUM);
			 cs36.setBorderRight(BorderStyle.NONE);
			 cs36.setBorderTop(BorderStyle.NONE);
			 cs36.setBorderBottom(BorderStyle.MEDIUM);

			 CellStyle cs37 = wb.createCellStyle();
			 cs37.setFont(f2);
			 cs37.setBorderLeft(BorderStyle.NONE);
			 cs37.setBorderRight(BorderStyle.MEDIUM);
			 cs37.setBorderTop(BorderStyle.NONE);
			 cs37.setBorderBottom(BorderStyle.MEDIUM);

			 cs4.setFont(f2);
			 cs4.setBorderTop(BorderStyle.MEDIUM);
			 cs4.setBorderBottom(BorderStyle.MEDIUM);

			 cs5.setFont(f2);
			 cs5.setBorderLeft(BorderStyle.THIN);
			 cs5.setBorderRight(BorderStyle.THIN);
			 cs5.setBorderTop(BorderStyle.THIN);
			 cs5.setBorderBottom(BorderStyle.THIN);
			 cs5.setWrapText(true);
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
			 cs53.setFont(f2);
			 cs53.setBorderLeft(BorderStyle.THIN);
			 cs53.setBorderRight(BorderStyle.THIN);
			 cs53.setBorderTop(BorderStyle.THIN);
			 cs53.setBorderBottom(BorderStyle.THIN);
			 cs53.setAlignment(HorizontalAlignment.RIGHT);

			 cs52.setWrapText(true);
			 cs52.setRotation((short)255);

			 int page = 0;
			 int cerconNo = 1;


			 if(StringUtils.isEmpty(begindate)){
				 begindate = "1990-01-01";
			 }
			 if(StringUtils.isEmpty(enddate)){
				 enddate =  DateUtils.date2Str(DateUtils.date_sdf.get());
			 }
			 List<Map<String, Object>> result = wmDayCostConfMapper.resultbill(sfqj,mvCusCost.getCusCode(),begindate,enddate);
			 List<Map<String, Object>> resultmx = wmDayCostConfMapper.resultmx(sfqj,mvCusCost.getCusCode(),begindate,enddate);

			 List<Map<String, Object>> resultsum = wmDayCostConfMapper.resultsum(sfqj,mvCusCost.getCusCode(),begindate,enddate);
			 List<Map<String, Object>> resultcountsum = wmDayCostConfMapper.resultcountsum(sfqj,mvCusCost.getCusCode(),begindate,enddate);
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
			 cellHead42.setCellValue(comfiname );
			 cellHead42.setCellStyle(cs2);


			 Cell cellHead149 = rowHead4.createCell(9);
			 cellHead149.setCellStyle(cs35);

			 Cell cellHead43 = rowHead4.createCell(5);
			 cellHead43.setCellValue("公司名称:"  );
			 cellHead43.setCellStyle(cs34);

			 MdCus md = (MdCus) mdCusService.findByKeHuBianMa( mvCusCost.getCusCode()).getResult();
			 Cell cellHead44 = rowHead4.createCell(6);
			 cellHead44.setCellValue(md.getZhongWenQch() );
			 cellHead44.setCellStyle(cs35);


			 Row rowHead5 = sheet.createRow((short) 5); // 头部第二行
			 Cell cellHead5 = rowHead5.createCell(0);
			 rowHead5.setHeight((short) 300);
			 cellHead5.setCellValue("公司地址: ");
			 cellHead5.setCellStyle(cs34);

			 Cell cellHead52 = rowHead5.createCell(1);
			 cellHead52.setCellValue(comfiaddr );
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
			 cellHead62.setCellValue(comfitel  );
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
			 cellHead72.setCellValue(comfibankid  );
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
			 cellHead82.setCellValue(comfibankname );
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
			 cellHead92.setCellValue(comfizhucehao );
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
			 cellHead96.setCellValue(DateUtils.date2Str(DateUtils.getDate(), DateUtils.date_sdf.get()) );
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
						 if("501".equals(resultsum.get(j).get("cost_code").toString())){// 操作费
							 sumcount =sumcount + 1;
							 try{
								 Cell cell3 = rowColumnValue.createCell(3);
								 cell3.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("hansj")
										 .toString()))));
								 cell3.setCellStyle(cs3d);
								 cell3.setCellType(CellType.NUMERIC);

								 Cell cell4 = rowColumnValue.createCell(1);

								 cell4.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("costsl")
										 .toString()))));
								 cell4.setCellStyle(cs3d);
								 cell4.setCellType(CellType.NUMERIC);

								 Cell cell5 = rowColumnValue.createCell(2);
								 cell5.setCellValue(resultsum.get(j).get("cost_unit")
										 .toString());
								 cell5.setCellStyle(cs3);
							 }catch (Exception e){

							 }
						 }
						 if("5011".equals(resultsum.get(j).get("cost_code").toString())){// 操作费出
							 sumcount =sumcount + 1;
							 try{
								 Cell cell3 = rowColumnValue.createCell(6);
								 cell3.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("hansj")
										 .toString()))));
								 cell3.setCellStyle(cs3d);
								 cell3.setCellType(CellType.NUMERIC);

								 Cell cell4 = rowColumnValue.createCell(4);

								 cell4.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("costsl")
										 .toString()))));
								 cell4.setCellStyle(cs3d);
								 cell4.setCellType(CellType.NUMERIC);

								 Cell cell5 = rowColumnValue.createCell(5);
								 cell5.setCellValue(resultsum.get(j).get("cost_unit")
										 .toString());
								 cell5.setCellStyle(cs3);
							 }catch (Exception e){

							 }
						 }
						 if("1011".equals(resultsum.get(j).get("cost_code").toString())){// 仓租
							 sumcount =sumcount + 1;
							 try{
								 Cell cell3 = rowColumnValue.createCell(9);
								 cell3.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("hansj")
										 .toString()))));
								 cell3.setCellStyle(cs3d);
								 cell3.setCellType(CellType.NUMERIC);

								 Cell cell4 = rowColumnValue.createCell(7);

								 cell4.setCellValue(Double.parseDouble(dfsum.format(Double.parseDouble(resultsum.get(j).get("costsl")
										 .toString()))));
								 cell4.setCellStyle(cs3d);
								 cell4.setCellType(CellType.NUMERIC);

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
				 cell3.setCellType(CellType.NUMERIC);

				 Cell cell4 = rowColumnValue.createCell(3);

				 cell4.setCellValue(Double.parseDouble(dfdetail.format(Double.parseDouble(resultmx.get(i).get("costsl")
						 .toString()))));
				 cell4.setCellStyle(cs3d);
				 cell4.setCellType(CellType.NUMERIC);

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
					 cell.setCellValue(begindate+"至"+enddate);
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

			 try{
				 CellRangeAddress c141 = new CellRangeAddress(12, 12, 0, 2);// 第5行 到货日期
				 CellRangeAddress c142 = new CellRangeAddress(12, 12, 3, 4);// 第5行预约单号
				 CellRangeAddress c143 = new CellRangeAddress(12, 12, 5, 6);// 第6行客户采购单号
				 CellRangeAddress c144 = new CellRangeAddress(12, 12, 7, 8);// 第6行月台
//				 CellRangeAddress c145 = new CellRangeAddress(12, 12, 9, 9);// 第7行客户名称
				 CellRangeAddress c151 = new CellRangeAddress(13, 13, 0, 2);// 第7行车号
				 CellRangeAddress c152 = new CellRangeAddress(13, 13, 3, 4);// 第7行客户名称
				 CellRangeAddress c153 = new CellRangeAddress(13, 13, 5, 6);// 第7行车号
				 CellRangeAddress c154 = new CellRangeAddress(13, 13, 7, 8);// 第7行客户名称
//				 CellRangeAddress c155 = new CellRangeAddress(13, 13, 9, 9);// 第7行车号
				 sheet.addMergedRegion(c141);
				 sheet.addMergedRegion(c142);
				 sheet.addMergedRegion(c143);
				 sheet.addMergedRegion(c144);
//				 sheet.addMergedRegion(c145);
				 sheet.addMergedRegion(c151);
				 sheet.addMergedRegion(c152);
				 sheet.addMergedRegion(c153);
				 sheet.addMergedRegion(c154);
//				 sheet.addMergedRegion(c155);
			 }catch (Exception e){

			 }

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
//					 if(ResourceUtil.getConfigByName("sysdzd").equals("yes")){
						 cell2.setCellValue(result.get(i).get("cost_name")
								 .toString()+"("+ df.format(Double.parseDouble(result.get(i).get("costsl")
								 .toString())) + result.get(i).get("cost_unit")
								 .toString() +")" ) ;
//					 }else{
//						 cell2.setCellValue(result.get(i).get("cost_name").toString()) ;
//					 }

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
			 cell49112.setCellValue(comfibeizhu1);
			 cell49112.setCellStyle(cs32);
			 Cell cell491129 =  rowColumnInfo2.createCell(9);
			 cell491129.setCellStyle(cs32);
			 CellRangeAddress c162 = new CellRangeAddress(4 + cellsNum,
					 4 + cellsNum, 0, 9);
			 sheet.addMergedRegion(c162);

			 Row rowColumnInfo3 = sheet.createRow((short) 5 + cellsNum); // 列名
			 rowColumnInfo3.setHeight((short) 300);
			 Cell cell49113 = rowColumnInfo3.createCell(0);
			 cell49113.setCellValue(comfibeizhu2);
			 Cell cell491139 =  rowColumnInfo3.createCell(9);
			 cell491139.setCellStyle(cs32);
			 CellRangeAddress c163 = new CellRangeAddress(5 + cellsNum,
					 5 + cellsNum, 0, 9);
			 cell49113.setCellStyle(cs32);
			 sheet.addMergedRegion(c163);

			 Row rowColumnInfo4 = sheet.createRow((short) 6 + cellsNum); // 列名
			 rowColumnInfo4.setHeight((short) 700);
			 Cell cell49114 = rowColumnInfo4.createCell(0);
			 cell49114.setCellValue(comfibeizhu3);
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
				 bufferImg = ImageIO.read(new File(comfidzyz));
				 ImageIO.write(bufferImg, "png", byteArrayOutz);
				 //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
				 //anchor主要用于设置图片的属性
				 anchor = new HSSFClientAnchor(0, 0, 255, 255,(short) 6, (8+ cellsNum), (short) 8, (16+ cellsNum));
				 anchor.setAnchorType(DONT_MOVE_AND_RESIZE);
				 //插入图片
				 patriarch.createPicture(anchor, wb.addPicture(byteArrayOutz.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

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
}
