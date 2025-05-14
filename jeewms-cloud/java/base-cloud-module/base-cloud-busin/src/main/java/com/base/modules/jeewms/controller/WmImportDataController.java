package com.base.modules.jeewms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.common.api.vo.Result;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.WmImportData;
import com.base.modules.jeewms.entity.WmOmNoticeH;
import com.base.modules.jeewms.entity.WmOmQmI;
import com.base.modules.jeewms.entity.WmToMoveGoods;
import com.base.modules.jeewms.service.IWmImportDataService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeewms.service.IWmOmNoticeHService;
import com.base.modules.jeewms.service.IWmOmQmIService;
import com.base.modules.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import com.base.common.system.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

 /**
 * @Description: wm_import_data
 * @Author: base-boot
 * @Date:   2022-10-20
 * @Version: V1.0
 */
@Api(tags="wm_import_data")
@RestController
@RequestMapping("/jeewms/wmImportData")
@Slf4j
public class WmImportDataController extends BaseController<WmImportData, IWmImportDataService> {
	@Autowired
	private IWmImportDataService wmImportDataService;
	 @Autowired
	 private IWmOmQmIService wmOmQmIService;
	 @Autowired
	 private IWmOmNoticeHService wmOmNoticeHService;
	/**
	 * 分页列表查询
	 *
	 * @param wmImportData
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_import_data-分页列表查询")
	@ApiOperation(value="wm_import_data-分页列表查询", notes="wm_import_data-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmImportData wmImportData,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmImportData> queryWrapper = QueryGenerator.initQueryWrapper(wmImportData, req.getParameterMap());
		Page<WmImportData> page = new Page<WmImportData>(pageNo, pageSize);
		IPage<WmImportData> pageList = wmImportDataService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmImportData
	 * @return
	 */
	@AutoLog(value = "wm_import_data-添加")
	@ApiOperation(value="wm_import_data-添加", notes="wm_import_data-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmImportData wmImportData) {
		wmImportDataService.save(wmImportData);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmImportData
	 * @return
	 */
	@AutoLog(value = "wm_import_data-编辑")
	@ApiOperation(value="wm_import_data-编辑", notes="wm_import_data-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmImportData wmImportData) {
		wmImportDataService.updateById(wmImportData);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_import_data-通过id删除")
	@ApiOperation(value="wm_import_data-通过id删除", notes="wm_import_data-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmImportDataService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_import_data-批量删除")
	@ApiOperation(value="wm_import_data-批量删除", notes="wm_import_data-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmImportDataService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_import_data-通过id查询")
	@ApiOperation(value="wm_import_data-通过id查询", notes="wm_import_data-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmImportData wmImportData = wmImportDataService.getById(id);
		if(wmImportData==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmImportData);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmImportData
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmImportData wmImportData) {
        return super.exportXls(request, wmImportData, WmImportData.class, "wm_import_data");
    }



	 @Excel(name = "仓库", width = 15)
	 @ApiModelProperty(value = "仓库")
	 private String query11;
	 @Excel(name = "出入库类型", width = 15)
	 @ApiModelProperty(value = "出入库类型")
	 private String query01;
	 /**query02*/
	 @Excel(name = "商品编码", width = 15)
	 @ApiModelProperty(value = "商品编码")
	 private String query02;

	 /**query07*/
	 @Excel(name = "箱码", width = 15)
	 @ApiModelProperty(value = "箱码")
	 private String query07;

    /**
      * 通过excel导入数据
    *   需要传入参数  imType   入库和出库
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

    	String imType = request.getParameter("imType");//入库或者出库
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<WmImportData> listim = ExcelImportUtil.importExcel(file.getInputStream(), WmImportData.class, params);
                if(CollectionUtils.isEmpty(listim)){
					return Result.error("文件导入失败: 数据为空");

				}


				List<WmImportData> list = new ArrayList<>();
				for (WmImportData wmImportData : listim) {
					wmImportData.setQuery01(imType);//入库或者出库
					if("出库".equals(imType)){
						QueryWrapper<WmImportData> query2 = new QueryWrapper<>();
						query2.eq("query01", "入库");
						query2.eq("query02", wmImportData.getQuery02());
						query2.eq("query07", wmImportData.getQuery07());
						List<WmImportData> list2 = wmImportDataService.list(query2);
						if(CollectionUtils.isEmpty(list2)){
							return Result.error("文件导入失败:"+wmImportData.getQuery02()+wmImportData.getQuery07()+" 此商品和箱码维入库");


						}

					}
					list.add(wmImportData);
				}

				int i =1;
				for (WmImportData wmImportData : list) {
					if(StringUtil.isBlankOrNull(wmImportData.getQuery01())){
						return Result.error("文件导入失败:第" +i+ "行：出入库类型为空");
					}
					if(StringUtil.isBlankOrNull(wmImportData.getQuery02())){
						return Result.error("文件导入失败:第" +i+ "行：商品编码为空");
					}
					if(StringUtil.isBlankOrNull(wmImportData.getQuery07())){
						return Result.error("文件导入失败:第" +i+ "行：箱码为空");
					}
					if(StringUtil.isBlankOrNull(wmImportData.getQuery11())){
						return Result.error("文件导入失败:第" +i+ "行：仓库为空");
					}
					if(StringUtil.isBlankOrNull(wmImportData.getQuery05())){
						return Result.error("文件导入失败:第" +i+ "行：数量为空");
					}
				}


				//1，检查是否重复
				Map<String, List<WmImportData>> collect = list.stream().collect(Collectors.groupingBy(item -> {
					StringBuilder sb = new StringBuilder(item.getQuery01());
 						sb.append("_").append(item.getQuery02());
 						sb.append("_").append(item.getQuery07());
 						sb.append("_").append(item.getQuery11());
					log.info("最终合并条件：{}",sb);
					return sb.toString();
				}));
				for (Map.Entry<String, List<WmImportData>> stringListEntry : collect.entrySet()) {
					List<WmImportData> listtemp = stringListEntry.getValue();
					if(listtemp.size()>1){
						return Result.error("文件导入失败:" +stringListEntry.getValue()+ "行数大于1，行数为:"+listtemp.size());
					}
					WmImportData wmImportData = listtemp.get(0);
					QueryWrapper<WmImportData> query2 = new QueryWrapper<>();
					query2.eq("query01", wmImportData.getQuery01());
					query2.eq("query02", wmImportData.getQuery02());
					query2.eq("query07", wmImportData.getQuery07());
					query2.eq("query11", wmImportData.getQuery11());

					List<WmImportData> list2 = wmImportDataService.list(query2);
					if(!CollectionUtils.isEmpty(list2)){
						return Result.error("文件导入失败:" +stringListEntry.getValue()+ "已经导入");
					}

				}
				//2,保存数据
				long start = System.currentTimeMillis();
				wmImportDataService.saveBatch(list);
                //3，更新出库，根据入库箱码查找出库单号
				//1，检查是否重复
				Map<String, List<WmImportData>> collect2 = list.stream().collect(Collectors.groupingBy(item -> {
					StringBuilder sb = new StringBuilder(item.getQuery01());
					sb.append("_").append(item.getQuery07());
					log.info("最终合并条件：{}",sb);
					return sb.toString();
				}));
				List<WmImportData> list1 = new ArrayList<>();
				for (Map.Entry<String, List<WmImportData>> stringListEntry : collect2.entrySet()) {
					WmImportData wmImportData = stringListEntry.getValue().get(0);
					if("入库".equals(wmImportData.getQuery01())|| "stockin".equals(wmImportData.getQuery01())){
						list1.add(wmImportData);
					}
				}
				for (WmImportData wmImportData : list1) {
					//1,
					//
					//
					QueryWrapper<WmOmQmI> query2 = new QueryWrapper<>();
					query2.eq("tin_Id", wmImportData.getQuery07());
					query2.eq("bin_sta", "Y");
					List<WmOmQmI> list2 = wmOmQmIService.list(query2);
					if(!CollectionUtils.isEmpty(list2)){
						QueryWrapper<WmOmNoticeH> query3 = new QueryWrapper<>();
						query3.eq("om_Notice_Id", list2.get(0).getOmNoticeId());
						List<WmOmNoticeH> list3 = wmOmNoticeHService.list(query3);
						if(!CollectionUtils.isEmpty(list3)){
							WmOmNoticeH wmOmNoticeH  = list3.get(0);
							wmOmNoticeH.setOmSta("已完成");
							wmOmNoticeHService.updateById(wmOmNoticeH);
						}
					}
				}
				log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");

				return Result.ok("文件导入成功！数据行数：" + list.size());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return Result.error("文件导入失败:" + e.getMessage());
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Result.error("文件导入失败！");


    }

}
