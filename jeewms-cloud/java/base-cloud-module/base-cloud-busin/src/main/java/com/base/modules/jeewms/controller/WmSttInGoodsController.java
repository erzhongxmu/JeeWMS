package com.base.modules.jeewms.controller;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.DateUtils;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.entity.WmImNoticeH;
import com.base.modules.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.BaKw;
import com.base.modules.jeewms.entity.WmSttInGoods;
import com.base.modules.jeewms.service.IBaKwService;
import com.base.modules.jeewms.service.IWmSttInGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: wm_stt_in_goods
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
@Api(tags="wm_stt_in_goods")
@RestController
@RequestMapping("/jeewms/wmSttInGoods")
@Slf4j
public class WmSttInGoodsController extends JeecgController<WmSttInGoods, IWmSttInGoodsService> {
	@Autowired
	private IWmSttInGoodsService wmSttInGoodsService;
	@Autowired
	private IBaKwService baKwService;

	/**
	 * 分页列表查询
	 *
	 * @param wmSttInGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="盘点-分页列表查询", notes="盘点-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmSttInGoods wmSttInGoods,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmSttInGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmSttInGoods, req.getParameterMap());
		Page<WmSttInGoods> page = new Page<WmSttInGoods>(pageNo, pageSize);
		queryWrapper.gt("goods_qua","0");
		IPage<WmSttInGoods> pageList = wmSttInGoodsService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	 /**
	  * 复盘分页查询
	  *
	  * @param wmSttInGoods
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "复盘-分页列表查询")
	 @ApiOperation(value="复盘-分页列表查询", notes="复盘-分页列表查询")
	 @GetMapping(value = "/fpList")
	 public Result<?> queryPageFpList(WmSttInGoods wmSttInGoods,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<WmSttInGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmSttInGoods, req.getParameterMap());
		 queryWrapper.lambda().eq(WmSttInGoods::getSttSta,"已完成");
		 //todo 盘点数量和库存数量不一致的
		 Page<WmSttInGoods> page = new Page<WmSttInGoods>(pageNo, pageSize);
		 IPage<WmSttInGoods> pageList = wmSttInGoodsService.page(page, queryWrapper);
		 return Result.ok(pageList);
	 }



	/**
	 *   添加
	 *
	 * @param wmSttInGoods
	 * @return
	 */
	@AutoLog(value = "wm_stt_in_goods-添加")
	@ApiOperation(value="wm_stt_in_goods-添加", notes="wm_stt_in_goods-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmSttInGoods wmSttInGoods) {
		wmSttInGoodsService.save(wmSttInGoods);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmSttInGoods
	 * @return
	 */
	@AutoLog(value = "wm_stt_in_goods-编辑")
	@ApiOperation(value="wm_stt_in_goods-编辑", notes="wm_stt_in_goods-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmSttInGoods wmSttInGoods) {
		if (StringUtils.isNotEmpty(wmSttInGoods.getSttQua())){
			BigDecimal sub = NumberUtil.sub(wmSttInGoods.getGoodsQua(), wmSttInGoods.getSttQua());
			wmSttInGoods.setDiffQua(sub.toString());
			wmSttInGoods.setSttSta("已完成");
		}
		wmSttInGoodsService.updateById(wmSttInGoods);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_stt_in_goods-通过id删除")
	@ApiOperation(value="wm_stt_in_goods-通过id删除", notes="wm_stt_in_goods-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmSttInGoodsService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_stt_in_goods-批量删除")
	@ApiOperation(value="wm_stt_in_goods-批量删除", notes="wm_stt_in_goods-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmSttInGoodsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	 @AutoLog(value = "生成盘点单号")
	 @ApiOperation(value="生成盘点单号", notes="生成盘点单号")
	 @GetMapping(value = "/queryPdNo")
	 public Result<?> queryPdNo(@RequestParam(name="ids",required=true) String ids) {
		 String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
		 int count = wmSttInGoodsService.lambdaQuery().apply("notice_id is not null and date_format(create_time,'%Y-%m-%d') = '" + today + "'").count();
		 int newcount = count + 1;
		 String noticeid = "PD" + today + "-" + String.format("%04d", newcount);
		 for(String id : Arrays.asList(ids.split(","))){
			 WmSttInGoods wmSttInGoods = wmSttInGoodsService.getById(id);
			 if(StringUtils.isEmpty(wmSttInGoods.getNoticeId())){
				 wmSttInGoods.setNoticeId(noticeid);
				 wmSttInGoodsService.updateById(wmSttInGoods);
			 }
		 }
		 return Result.ok("操作成功");
	 }

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_stt_in_goods-通过id查询")
	@ApiOperation(value="wm_stt_in_goods-通过id查询", notes="wm_stt_in_goods-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmSttInGoods wmSttInGoods = wmSttInGoodsService.getById(id);
		if(wmSttInGoods==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmSttInGoods);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmSttInGoods
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmSttInGoods wmSttInGoods) {
        return super.exportXls(request, wmSttInGoods, WmSttInGoods.class, "盘点");
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
        return super.importExcel(request, response, WmSttInGoods.class);
    }

	 /**
	  * 盘点差异过账
	  *
	  * @param wmSttInGoods
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @ApiOperation(value="盘点差异过账-分页列表查询", notes="盘点差异过账-分页列表查询")
	 @GetMapping(value = "/differentPageList")
	 public Result<?> differentPageList(WmSttInGoods wmSttInGoods,
										@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
										@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
										HttpServletRequest req) {

		 IPage<WmSttInGoods> pageList = wmSttInGoodsService.differentPageList(pageNo, pageSize,wmSttInGoods);
		 return Result.ok(pageList);
	 }

     /**
      *  (差异过账) 生成上架、下架任务
      *
      * @param wmSttInGoods
      * @return
      */
     @ApiOperation(value="(差异过账) 生成上架、下架任务", notes="(差异过账) 生成上架、下架任务")
     @PostMapping(value = "/doDifferent")
     public Result<?> doDifferent(@RequestBody WmSttInGoods wmSttInGoods) {
         wmSttInGoodsService.doDifferent(wmSttInGoods);
         return Result.ok("添加成功！");
     }


	 /*************************************************** pda接口 **********************************************************************/

     /**
      *  pda盘点
      *
      * @param wmSttInGoods
      * @return
      */
     @ApiOperation(value="pda盘点接口", notes="pda盘点接口")
     @PostMapping(value = "/pdaCheck")
     public Result<?> pdaCheck(@RequestBody WmSttInGoods wmSttInGoods) {
         return wmSttInGoodsService.inventory(wmSttInGoods);
     }

	 /**
      * pda盘点分页查询
      *
      * @param wmSttInGoods
      * @param pageNo
      * @param pageSize
      * @param req
      * @return
      */
     @ApiOperation(value="pda盘点-分页列表查询", notes="pda盘点-分页列表查询")
     @GetMapping(value = "/pdaList")
     public Result<?> pdaList(WmSttInGoods wmSttInGoods,
                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                    HttpServletRequest req) {
         QueryWrapper<WmSttInGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmSttInGoods, req.getParameterMap());
         queryWrapper.lambda().eq(WmSttInGoods::getSttSta,"计划中").orderByDesc(WmSttInGoods::getCreateTime);
         Page<WmSttInGoods> page = new Page<WmSttInGoods>(pageNo, pageSize);
         IPage<WmSttInGoods> pageList = wmSttInGoodsService.page(page, queryWrapper);
         pageList.getRecords().forEach( item -> {
             BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode,wmSttInGoods.getBinId()).one();
             if (baKw != null) {
                 item.setAreaCode(baKw.getStoreAreaCode());
                 item.setKwName(baKw.getKwName());

             }
		 });
         return Result.ok(pageList);
     }


	 @ApiOperation(value = "通过", notes = "商品-通主表ID查询")
	 @GetMapping(value = "/queryWmSttInGoodListByMainId")
	 public Result<?> queryWmSttInGoodListByMainId(@RequestParam(name = "id", required = true) String id) {
		 QueryWrapper<WmSttInGoods> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("notice_id",id);
		 List<WmSttInGoods> wmSttInGoodsList = wmSttInGoodsService.list(queryWrapper);
		 return Result.ok(wmSttInGoodsList);
	 }


	/**
	 * 分页列表查询
	 *
	 * @param wmSttInGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="盘点-分页列表查询", notes="盘点-分页列表查询")
	@GetMapping(value = "/list3")
	public Result<?> queryPages(WmSttInGoods wmSttInGoods,
								@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								HttpServletRequest req) {
		QueryWrapper<WmSttInGoods> queryWrapper = QueryGenerator.initQueryWrapper(wmSttInGoods, req.getParameterMap());
		Page<WmSttInGoods> page = new Page<WmSttInGoods>(pageNo, pageSize);
		IPage<WmSttInGoods> pageList = wmSttInGoodsService.page(page, queryWrapper);
		List<WmSttInGoods> records = pageList.getRecords();
		List<WmSttInGoods> collects = records.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
				new TreeSet<>(Comparator.comparing(WmSttInGoods::getNoticeId))), ArrayList::new));
		List<WmSttInGoods> goodsList = new ArrayList<>();
		for (WmSttInGoods collect : collects) {
			WmSttInGoods goods = new WmSttInGoods();
			goods.setNoticeId(collect.getNoticeId());
			goods.setCreateBy(collect.getCreateBy());
			goods.setSttType(collect.getSttType());
			goodsList.add(goods);
		}
		pageList.setRecords(goodsList);
		return Result.ok(pageList);
	}
}
