package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.BaModel;
import com.base.modules.jeewms.service.IBaModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 商品分类
 * @Author: base-boot
 * @Date:   2021-06-07
 * @Version: V1.0
 */
@Api(tags="商品分类")
@RestController
@RequestMapping("/jeewms/baModel")
@Slf4j
public class BaModelController extends JeecgController<BaModel, IBaModelService> {
	@Autowired
	private IBaModelService baModelService;

	/**
	 * 分页列表查询
	 *
	 * @param baModel
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "商品分类-分页列表查询")
	@ApiOperation(value="商品分类-分页列表查询", notes="商品分类-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaModel baModel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaModel> queryWrapper = QueryGenerator.initQueryWrapper(baModel, req.getParameterMap());
		Page<BaModel> page = new Page<BaModel>(pageNo, pageSize);
		IPage<BaModel> pageList = baModelService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baModel
	 * @return
	 */
	@AutoLog(value = "商品分类-添加")
	@ApiOperation(value="商品分类-添加", notes="商品分类-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaModel baModel) {
		if (baModelService.lambdaQuery().eq(BaModel::getModelCode,baModel.getModelCode()).count() > 0) {
			throw new JeecgBootException("编码重复");
		}
		baModelService.save(baModel);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baModel
	 * @return
	 */
	@AutoLog(value = "商品分类-编辑")
	@ApiOperation(value="商品分类-编辑", notes="商品分类-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaModel baModel) {
		baModelService.updateById(baModel);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品分类-通过id删除")
	@ApiOperation(value="商品分类-通过id删除", notes="商品分类-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baModelService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品分类-批量删除")
	@ApiOperation(value="商品分类-批量删除", notes="商品分类-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baModelService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品分类-通过id查询")
	@ApiOperation(value="商品分类-通过id查询", notes="商品分类-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaModel baModel = baModelService.getById(id);
		if(baModel==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baModel);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baModel
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaModel baModel) {
        return super.exportXls(request, baModel, BaModel.class, "商品分类");
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
				List<BaModel> list = ExcelImportUtil.importExcel(file.getInputStream(), BaModel.class, params);
				//update-begin-author:taoyan date:20190528 for:批量插入数据
				List<String> codelList = new ArrayList<>();
				for (BaModel baModel : list) {
					codelList.add(baModel.getModelCode());
				}
				if (codelList.size() > 0) {
					int count = baModelService.lambdaQuery().in(BaModel::getModelCode,codelList).count();
					if (count > 0) {
						throw new JeecgBootException("编码重复");
					}
				}
				long start = System.currentTimeMillis();
				baModelService.saveBatch(list);
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
        //return super.importExcel(request, response, BaModel.class);
    }

}
