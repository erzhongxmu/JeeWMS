package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.BaStore;
import com.base.modules.jeewms.entity.BaStoreArea;
import com.base.modules.jeewms.service.IBaStoreAreaService;
import com.base.modules.jeewms.service.IBaStoreService;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 库区
 * @Author: base-boot
 * @Date:   2021-06-07
 * @Version: V1.0
 */
@Api(tags="库区")
@RestController
@RequestMapping("/jeewms/baStoreArea")
@Slf4j
public class BaStoreAreaController extends JeecgController<BaStoreArea, IBaStoreAreaService> {
	@Autowired
	private IBaStoreAreaService baStoreAreaService;
	@Autowired
    private IBaStoreService baStoreService;

	/**
	 * 分页列表查询
	 *
	 * @param baStoreArea
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "库区-分页列表查询")
	@ApiOperation(value="库区-分页列表查询", notes="库区-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaStoreArea baStoreArea,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaStoreArea> queryWrapper = QueryGenerator.initQueryWrapper(baStoreArea, req.getParameterMap());
		Page<BaStoreArea> page = new Page<BaStoreArea>(pageNo, pageSize);
		IPage<BaStoreArea> pageList = baStoreAreaService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	 /**
	  *   查询库区下拉列表
	  *
	  * @param storeCode 仓库编码
	  * @return
	  */
	 @ApiOperation(value="库区-下拉列表", notes="库区-下拉列表")
	 @GetMapping(value = "/getAreaList")
	 public Result<?> getAreaList(String storeCode) {

		 return Result.ok(baStoreAreaService
				 .lambdaQuery()
				 .eq(StringUtils.isNotEmpty(storeCode),BaStoreArea::getWareCode,storeCode)
				 .list());
	 }

	 /**
	  *   根据库区code查询仓库
	  *
	  * @param areaCode
	  * @return
	  */
	 @ApiOperation(value="库区-根据库区code查询仓库", notes="库区-根据库区code查询仓库")
	 @GetMapping(value = "/getStoreByAreaCode")
	 public Result<?> getStoreByAreaCode(@RequestParam(required = true) String areaCode) {
	     //查询库区
         BaStoreArea baStoreArea = baStoreAreaService.lambdaQuery().eq(BaStoreArea::getAreaCode,areaCode).one();
         if (baStoreArea != null) {
             return Result.ok(baStoreService.lambdaQuery().eq(BaStore::getStoreCode,baStoreArea.getWareCode()).one());
         }else {
             return Result.ok("");
         }

	 }


	/**
	 *   添加
	 *
	 * @param baStoreArea
	 * @return
	 */
	@AutoLog(value = "库区-添加")
	@ApiOperation(value="库区-添加", notes="库区-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaStoreArea baStoreArea) {
		if (baStoreAreaService.lambdaQuery().eq(BaStoreArea::getAreaCode,baStoreArea.getAreaCode()).count() > 0) {
			return Result.error("编码重复");
		}
		baStoreAreaService.save(baStoreArea);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baStoreArea
	 * @return
	 */
	@AutoLog(value = "库区-编辑")
	@ApiOperation(value="库区-编辑", notes="库区-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaStoreArea baStoreArea) {
		BaStoreArea baStoreAreas = baStoreAreaService.lambdaQuery().eq(BaStoreArea::getAreaCode, baStoreArea.getAreaCode())
				.ne(BaStoreArea::getId, baStoreArea.getId()).one();
		if (baStoreAreas != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			baStoreAreaService.updateById(baStoreArea);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "库区-通过id删除")
	@ApiOperation(value="库区-通过id删除", notes="库区-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baStoreAreaService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "库区-批量删除")
	@ApiOperation(value="库区-批量删除", notes="库区-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baStoreAreaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "库区-通过id查询")
	@ApiOperation(value="库区-通过id查询", notes="库区-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaStoreArea baStoreArea = baStoreAreaService.getById(id);
		if(baStoreArea==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baStoreArea);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baStoreArea
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaStoreArea baStoreArea) {
        return super.exportXls(request, baStoreArea, BaStoreArea.class, "库区");
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
        return super.importExcel(request, response, BaStoreArea.class);
    }

}
