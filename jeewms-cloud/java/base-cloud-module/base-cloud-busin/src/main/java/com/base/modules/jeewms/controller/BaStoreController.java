package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeewms.entity.BaGoodsType;
import com.base.modules.jeewms.entity.BaKw;
import com.base.modules.jeewms.entity.BaStore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.BaStore;
import com.base.modules.jeewms.service.IBaStoreService;
import com.base.modules.util.CodeGenerateUtils;
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
 * @Description: ba_store
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="ba_store")
@RestController
@RequestMapping("/jeewms/baStore")
@Slf4j
public class BaStoreController extends JeecgController<BaStore, IBaStoreService> {
	@Autowired
	private IBaStoreService baStoreService;

	/**
	 * 分页列表查询
	 *
	 * @param baStore
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ba_store-分页列表查询")
	@ApiOperation(value="ba_store-分页列表查询", notes="ba_store-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaStore baStore,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaStore> queryWrapper = QueryGenerator.initQueryWrapper(baStore, req.getParameterMap());
		Page<BaStore> page = new Page<BaStore>(pageNo, pageSize);
		IPage<BaStore> pageList = baStoreService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
	  * 下拉查询仓库列表
	  *
	  * @return
	  */
	 @ApiOperation(value="仓库下拉列表", notes="仓库下拉列表")
	 @GetMapping(value = "/selectList")
	 public Result<?> queryPageList() {
		 return Result.ok(baStoreService.list());
	 }

	/**
	 *   添加
	 *
	 * @param baStore
	 * @return
	 */
	@AutoLog(value = "ba_store-添加")
	@ApiOperation(value="ba_store-添加", notes="ba_store-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaStore baStore) {

		if (baStoreService.lambdaQuery()
				.eq(BaStore::getStoreCode,baStore.getStoreCode()).count()>0){
			return Result.error("编码已存在，请重新输入");
		}else {
			baStoreService.save(baStore);
		}
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baStore
	 * @return
	 */
	@AutoLog(value = "ba_store-编辑")
	@ApiOperation(value="ba_store-编辑", notes="ba_store-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaStore baStore) {
		BaStore baGoodsTypes = baStoreService.lambdaQuery().eq(BaStore::getStoreCode, baStore.getStoreCode())
				.ne(BaStore::getId, baStore.getId()).one();
		if (baGoodsTypes != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			baStoreService.updateById(baStore);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_store-通过id删除")
	@ApiOperation(value="ba_store-通过id删除", notes="ba_store-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baStoreService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ba_store-批量删除")
	@ApiOperation(value="ba_store-批量删除", notes="ba_store-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baStoreService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_store-通过id查询")
	@ApiOperation(value="ba_store-通过id查询", notes="ba_store-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaStore baStore = baStoreService.getById(id);
		if(baStore==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baStore);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baStore
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaStore baStore) {
        return super.exportXls(request, baStore, BaStore.class, "仓库");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
   /* @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, BaStore.class);
    }*/

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
				List<BaStore> list = ExcelImportUtil.importExcel(file.getInputStream(), BaStore.class, params);
				long start = System.currentTimeMillis();
				List<BaStore> listsave = new ArrayList<>();
				if (list.size() > 0) {
					for(BaStore baStore : list){
						if(StringUtils.isEmpty(baStore.getStoreCode())){
							continue;
						}
						List<BaStore> list1 = baStoreService.lambdaQuery().in(BaStore::getStoreCode, baStore.getStoreCode()).list();
						for (BaStore kw : list1) {
							baStoreService.removeById(kw.getId());
						}
						listsave.add(baStore);
					}
				}
				baStoreService.saveBatch(listsave);
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
