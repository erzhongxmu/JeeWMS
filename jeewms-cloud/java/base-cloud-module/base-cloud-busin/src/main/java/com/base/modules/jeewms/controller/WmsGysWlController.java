package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.WmsGysWl;
import com.base.modules.jeewms.service.IMdCusService;
import com.base.modules.jeewms.service.IMdGoodsService;
import com.base.modules.jeewms.service.IWmsGysWlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.QRcodeUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 物料供应关系
 * @Author: base-boot
 * @Date:   2021-11-26
 * @Version: V1.0
 */
@Api(tags="物料供应关系")
@RestController
@RequestMapping("/jeewms/wmsGysWl")
@Slf4j
public class WmsGysWlController extends BaseController<WmsGysWl, IWmsGysWlService> {
	@Autowired
	private IWmsGysWlService wmsGysWlService;
	@Autowired
	private IMdCusService mdCusService;
	@Autowired
	private IMdGoodsService mdGoodsService;

//	private static final String ewmPath = "C:/Users/Administrator/Desktop/wms2022/nginx-1.19.4/old/";
//	private static final String ewmPath = "D:/abc/qr/";
//	private static final String ewMurl = "http://47.113.229.131:9000/";

	private static final String ewmPath = "D:/opt/";
//	private static final String ewMurl = "http://127.0.0.1:9999/";
	private static final String ewMurl = "file:///D:/opt/";
	/**
	 * 分页列表查询
	 *
	 * @param wmsGysWl
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "物料供应关系-分页列表查询")
	@ApiOperation(value="物料供应关系-分页列表查询", notes="物料供应关系-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsGysWl wmsGysWl,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsGysWl> queryWrapper = QueryGenerator.initQueryWrapper(wmsGysWl, req.getParameterMap());
		Page<WmsGysWl> page = new Page<WmsGysWl>(pageNo, pageSize);
		IPage<WmsGysWl> pageList = wmsGysWlService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmsGysWl
	 * @return
	 */
	@AutoLog(value = "物料供应关系-添加")
	@ApiOperation(value="物料供应关系-添加", notes="物料供应关系-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsGysWl wmsGysWl) throws Exception {
		String content = wmsGysWl.getSuplyCode() + "-" + wmsGysWl.getMaterialCode();
		String url = ewMurl + wmsGysWl.getSuplyCode()+wmsGysWl.getMaterialCode()+".jpg";
		String path = ewmPath + wmsGysWl.getSuplyCode()+wmsGysWl.getMaterialCode()+".jpg";
		QRcodeUtil.encodeStr(content, wmsGysWl.getMaterialCode(), path);
		wmsGysWl.setPicture(url);
		QueryWrapper<MdCus> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("ke_hu_bian_ma",wmsGysWl.getSuplyCode());
		MdCus mdCus = mdCusService.getOne(queryWrapper);
		if(mdCus!=null){
			wmsGysWl.setSuplyName(mdCus.getZhongWenQch());
		}
		QueryWrapper<MdGoods> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("shp_bian_ma",wmsGysWl.getMaterialCode());
		MdGoods mdGoods = mdGoodsService.getOne(queryWrapper1);
		if(mdGoods!=null){
			wmsGysWl.setMaterialName(mdGoods.getShpMingCheng());
		}
		wmsGysWlService.save(wmsGysWl);
		return Result.OK("添加成功！");
	}


	/**
	 *   添加
	 *
	 * @param wmsGysWl
	 * @return
	 */
	@AutoLog(value = "物料供应关系-添加")
	@ApiOperation(value="物料供应关系-添加", notes="物料供应关系-添加")
	@PostMapping(value = "/adds")
	public Result<?> adds(@RequestBody WmsGysWl wmsGysWl){
		wmsGysWlService.save(wmsGysWl);
		return Result.OK("添加成功！");
	}

	/**
	 *   修改
	 *
	 * @param wmsGysWl
	 * @return
	 */
	@AutoLog(value = "物料供应关系-添加")
	@ApiOperation(value="物料供应关系-添加", notes="物料供应关系-添加")
	@PutMapping(value = "/edits")
	public Result<?> edits(@RequestBody WmsGysWl wmsGysWl){
		wmsGysWlService.updateById(wmsGysWl);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmsGysWl
	 * @return
	 */
	@AutoLog(value = "物料供应关系-编辑")
	@ApiOperation(value="物料供应关系-编辑", notes="物料供应关系-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsGysWl wmsGysWl) throws Exception {
		String content = wmsGysWl.getSuplyCode() + "-" + wmsGysWl.getMaterialCode();
		String url = ewMurl + wmsGysWl.getSuplyCode()+wmsGysWl.getMaterialCode()+".jpg";
		String path = ewmPath + wmsGysWl.getSuplyCode()+wmsGysWl.getMaterialCode()+".jpg";
		QRcodeUtil.encodeStr(content, wmsGysWl.getMaterialCode(), path);
		wmsGysWl.setPicture(url);
		wmsGysWlService.updateById(wmsGysWl);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物料供应关系-通过id删除")
	@ApiOperation(value="物料供应关系-通过id删除", notes="物料供应关系-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsGysWlService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "物料供应关系-批量删除")
	@ApiOperation(value="物料供应关系-批量删除", notes="物料供应关系-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsGysWlService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物料供应关系-通过id查询")
	@ApiOperation(value="物料供应关系-通过id查询", notes="物料供应关系-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsGysWl wmsGysWl = wmsGysWlService.getById(id);
		if(wmsGysWl==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsGysWl);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsGysWl
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsGysWl wmsGysWl) {
        return super.exportXls(request, wmsGysWl, WmsGysWl.class, "物料供应关系");
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
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<WmsGysWl> wmsGysWlList = new ArrayList<>();
				List<WmsGysWl> list = ExcelImportUtil.importExcel(file.getInputStream(), WmsGysWl.class, params);
				for(WmsGysWl wmsGysWl : list){
					String content = wmsGysWl.getSuplyCode() + "-" + wmsGysWl.getMaterialCode();
					String url = ewMurl + wmsGysWl.getSuplyCode() + wmsGysWl.getMaterialCode()+".jpg";
					String path = ewmPath + wmsGysWl.getSuplyCode() + wmsGysWl.getMaterialCode()+".jpg";
					QRcodeUtil.encodeStr(content, wmsGysWl.getMaterialCode(), path);
					wmsGysWl.setPicture(url);
					wmsGysWlList.add(wmsGysWl);
				}
				//update-begin-author:taoyan date:20190528 for:批量插入数据
				long start = System.currentTimeMillis();
				wmsGysWlService.saveBatch(wmsGysWlList);
				//400条 saveBatch消耗时间1592毫秒  循环插入消耗时间1947毫秒
				//1200条  saveBatch消耗时间3687毫秒 循环插入消耗时间5212毫秒
				log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				//update-end-author:taoyan date:20190528 for:批量插入数据
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

	public static void main(String[] args) throws Exception {
		String content = "ceshi01WHH123456789";
		String bm = "123456789";
		String path = "D:\\abc\\qr\\ceshi01WHH123456789.jpg";
		QRcodeUtil.encodeStr(content, bm, path);
	}

}
