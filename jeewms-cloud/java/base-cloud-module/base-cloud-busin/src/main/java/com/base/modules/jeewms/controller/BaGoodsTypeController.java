package com.base.modules.jeewms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaBinType;
import com.base.modules.jeewms.entity.BaGoodsType;
import com.base.modules.jeewms.entity.BaUnit;
import com.base.modules.jeewms.service.IBaGoodsTypeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

 /**
 * @Description: 产品属性
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="产品属性")
@RestController
@RequestMapping("/jeewms/baGoodsType")
@Slf4j
public class BaGoodsTypeController extends JeecgController<BaGoodsType, IBaGoodsTypeService> {
	@Autowired
	private IBaGoodsTypeService baGoodsTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param baGoodsType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "产品属性-分页列表查询")
	@ApiOperation(value="产品属性-分页列表查询", notes="产品属性-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaGoodsType baGoodsType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaGoodsType> queryWrapper = QueryGenerator.initQueryWrapper(baGoodsType, req.getParameterMap());
		Page<BaGoodsType> page = new Page<BaGoodsType>(pageNo, pageSize);
		IPage<BaGoodsType> pageList = baGoodsTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baGoodsType
	 * @return
	 */
	@AutoLog(value = "产品属性-添加")
	@ApiOperation(value="产品属性-添加", notes="产品属性-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaGoodsType baGoodsType) {
		if (baGoodsTypeService.lambdaQuery()
				.eq(BaGoodsType::getGoodsTypeCode,baGoodsType.getGoodsTypeCode()).count()>0){
			return Result.error("编码已存在，请重新输入");
		}else {
			baGoodsTypeService.save(baGoodsType);
		}
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baGoodsType
	 * @return
	 */
	@AutoLog(value = "产品属性-编辑")
	@ApiOperation(value="产品属性-编辑", notes="产品属性-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaGoodsType baGoodsType) {
		BaGoodsType baGoodsTypes = baGoodsTypeService.lambdaQuery().eq(BaGoodsType::getGoodsTypeCode, baGoodsType.getGoodsTypeCode())
				.ne(BaGoodsType::getId, baGoodsType.getId()).one();
		if (baGoodsTypes != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			baGoodsTypeService.updateById(baGoodsType);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品属性-通过id删除")
	@ApiOperation(value="产品属性-通过id删除", notes="产品属性-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baGoodsTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "产品属性-批量删除")
	@ApiOperation(value="产品属性-批量删除", notes="产品属性-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baGoodsTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品属性-通过id查询")
	@ApiOperation(value="产品属性-通过id查询", notes="产品属性-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaGoodsType baGoodsType = baGoodsTypeService.getById(id);
		if(baGoodsType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baGoodsType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baGoodsType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaGoodsType baGoodsType) {
        return super.exportXls(request, baGoodsType, BaGoodsType.class, "产品属性");
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
				List<BaGoodsType> list = ExcelImportUtil.importExcel(file.getInputStream(), BaGoodsType.class, params);
				long start = System.currentTimeMillis();
				List<BaGoodsType> listsave = new ArrayList<>();
				if (list.size() > 0) {
					for(BaGoodsType baGoodsType : list){
						if(org.apache.commons.lang3.StringUtils.isEmpty(baGoodsType.getGoodsTypeCode())){
							continue;
						}
						List<BaGoodsType> list1 = baGoodsTypeService.lambdaQuery().in(BaGoodsType::getGoodsTypeCode, baGoodsType.getGoodsTypeCode()).list();
						for (BaGoodsType kw : list1) {
							baGoodsTypeService.removeById(kw.getId());
						}
						listsave.add(baGoodsType);
					}
				}
				baGoodsTypeService.saveBatch(listsave);
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
