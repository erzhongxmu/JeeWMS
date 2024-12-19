package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import org.jeecg.common.system.base.controller.JeecgController;;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: wm_to_up_goods
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
@Api(tags="上架明细")
@RestController
@RequestMapping("/jeewms/wmToUpGoods")
@Slf4j
public class WmToUpGoodsController extends JeecgController<WmToUpGoods, IWmToUpGoodsService> {
	@Autowired
	private IWmToUpGoodsService wmToUpGoodsService;
	@Autowired
	private IWmInQmIService wmInQmIService;
	@Autowired
	private IWmHisStockDataService wmHisStockDataService;

	@Autowired
	private IWmTuopanService iWmTuopanService;

	/**
	 * 分页列表查询
	 *
	 * @param wmToUpGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_to_up_goods-分页列表查询")
	@ApiOperation(value="wm_to_up_goods-分页列表查询", notes="wm_to_up_goods-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmToUpGoods wmToUpGoods,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmToUpGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmToUpGoods, req.getParameterMap());
		Page<WmToUpGoods> page = new Page<WmToUpGoods>(pageNo, pageSize);
		IPage<WmToUpGoods> pageList = wmToUpGoodsService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmToUpGoods
	 * @return
	 */
	@AutoLog(value = "wm_to_up_goods-添加")
	@ApiOperation(value="wm_to_up_goods-添加", notes="wm_to_up_goods-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmToUpGoods wmToUpGoods) {
		wmToUpGoodsService.save(wmToUpGoods);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmToUpGoods
	 * @return
	 */
	@AutoLog(value = "wm_to_up_goods-编辑")
	@ApiOperation(value="wm_to_up_goods-编辑", notes="wm_to_up_goods-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmToUpGoods wmToUpGoods) {

		WmToUpGoods byId = wmToUpGoodsService.getById(wmToUpGoods.getId());
		List<WmTuopan> list2 = iWmTuopanService.lambdaQuery().eq(WmTuopan::getTinId,byId.getBinId()).
				list();
		WmTuopan wmTuopan = list2.get(0).setTinId(wmToUpGoods.getBinId());
		List<WmTuopan> list = iWmTuopanService.lambdaQuery().eq(WmTuopan::getTinId, wmToUpGoods.getBinId()).list();
		if(CollectionUtils.isEmpty(list)){
			iWmTuopanService.updateById(wmTuopan);
		}
		wmToUpGoodsService.updateById(wmToUpGoods);


		//更新入库子表
		WmInQmI wmInQmI = new WmInQmI();
		wmInQmI.setGoodsId(wmToUpGoods.getGoodsId());
		wmInQmI.setBaseGoodscount(wmToUpGoods.getBaseGoodscount());
		wmInQmIService.updateById(wmInQmI);
		//更新历史库存表
		WmHisStockData wmHisStockData = new WmHisStockData();
		wmHisStockData.setGoodsId(wmToUpGoods.getGoodsId());
		wmHisStockData.setCount(wmToUpGoods.getBaseGoodscount());
		wmHisStockDataService.updateById(wmHisStockData);
		return Result.ok("编辑成功!");
	}

	 /**
	  *  批量编辑
	  *
	  *
	  * @param wmToUpGoods
	  * @return
	  */
	 @AutoLog(value = "批量编辑")
	 @ApiOperation(value="批量编辑", notes="批量编辑")
	 @PostMapping(value = "/updateBatch")
	 public Result<?> updateBatch(@RequestBody List<WmToUpGoods> wmToUpGoods) {
	 	 //修改原有托盘托盘
		 for (WmToUpGoods wmToUpGood1 : wmToUpGoods) {
			 WmToUpGoods byId = wmToUpGoodsService.getById(wmToUpGood1.getId());
			 List<WmTuopan> list2 = iWmTuopanService.lambdaQuery().eq(WmTuopan::getTinId,byId.getBinId()).
					 list();
			 List<WmTuopan> list = iWmTuopanService.lambdaQuery().eq(WmTuopan::getTinId, wmToUpGood1.getBinId()).list();
			 WmTuopan wmTuopan = list2.get(0).setTinId(wmToUpGood1.getBinId());
			 if(CollectionUtils.isEmpty(list)){
				 iWmTuopanService.updateById(wmTuopan);
			 }
		 }
		 wmToUpGoodsService.updateBatchById(wmToUpGoods);
		 return Result.ok("编辑成功!");
	 }

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_to_up_goods-通过id删除")
	@ApiOperation(value="wm_to_up_goods-通过id删除", notes="wm_to_up_goods-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmToUpGoodsService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_to_up_goods-批量删除")
	@ApiOperation(value="wm_to_up_goods-批量删除", notes="wm_to_up_goods-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmToUpGoodsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_to_up_goods-通过id查询")
	@ApiOperation(value="wm_to_up_goods-通过id查询", notes="wm_to_up_goods-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmToUpGoods wmToUpGoods = wmToUpGoodsService.getById(id);
		if(wmToUpGoods==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmToUpGoods);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmToUpGoods
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmToUpGoods wmToUpGoods) {
        //return super.exportXls(request, wmToUpGoods, WmToUpGoods.class, "wm_to_up_goods");
		return super.exportXls(request, wmToUpGoods, WmToUpGoods.class, "上架");
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
        return super.importExcel(request, response, WmToUpGoods.class);
    }

}



