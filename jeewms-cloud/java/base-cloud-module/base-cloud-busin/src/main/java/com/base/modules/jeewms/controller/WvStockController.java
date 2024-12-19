package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.service.impl.CostTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.WmSttInGoods;
import com.base.modules.jeewms.entity.WvStock;
import com.base.modules.jeewms.service.IWmToUpGoodsService;
import com.base.modules.jeewms.service.IWvStockSttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Package com.base.modules.jeewms.controller
 * @date 2021/5/28 18:39
 * @description
 */
@RequestMapping("/jeewms/stock")
@RestController
@Api(tags = "库存")
public class WvStockController {

	@Autowired
	private IWvStockSttService wvStockSttService;

	@Autowired
	private IWmToUpGoodsService wmToUpGoodsService;

	@Autowired
	private CostTask costTask;


	/**
	 * pda库存查询
	 *
	 * @param wmSttInGoods
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="pda库存查询", notes="pda库存查询")
	@GetMapping(value = "/pdaStockList")
	public Result<?> pdaStockList(WvStock wvStock,
							 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
							 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
							 HttpServletRequest req) {
		Page<WvStock> page = new Page<>(pageNo,pageSize);
		IPage<WmSttInGoods> pageList = wvStockSttService.pdaStockList(page, wvStock);
		return Result.ok(pageList);
	}


	/**
	 * 查询到达库存临界点的数据
	 * @return
	 */
	@ApiOperation(value="查询到达库存临界点的数据", notes="查询到达库存临界点的数据")
	@GetMapping(value = "/getLowStockGoods")
	public List<MdGoods> getLowStockGoods() {

		return wmToUpGoodsService.getLowStockGoods();
	}

	/**
	 * 查询到达库存临界点的数据
	 * @return
	 */
	@ApiOperation(value="哥根据托盘查询库位", notes="哥根据托盘查询库位")
	@GetMapping(value = "/getKwByTinId")
	public Result getKwByTinId(@RequestParam String tinId) {

		return Result.ok(wvStockSttService.getKwByTinId(tinId));
	}

	/**
	 * 查询到达库存临界点的数据
	 * @return
	 */
	@ApiOperation(value="根据库位查询托盘", notes="根据库位查询托盘")
	@GetMapping(value = "/getTinIdListByBinId")
	public Result getTinIdListByBinId(@RequestParam String binId) {

		return Result.ok(wvStockSttService.getTinIdListByBinId(binId));
	}

	/**
	 * 查询到达库存临界点的数据
	 * @return
	 */
	@ApiOperation(value="根据库位、商品查询库存信息", notes="根据库位、商品查询库存信息")
	@GetMapping(value = "/getStockByKwAndGoodsId")
	public Result getStockByKwAndGoodsId(@RequestParam String kwCode ) {

		return Result.ok(wvStockSttService.getStockByKwAndGoodsId(kwCode));
	}


	@AutoLog(value = "库存结转")
	@ApiOperation(value = "库存结转", notes = "库存结转")
	@GetMapping(value = "/inventoryCarryDown")
	public  Result<?> inventoryCarryDown(){
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(format.format(calendar.getTime()));
		costTask.costcount(format.format(calendar.getTime()),"N");
		return Result.ok();
	}


}
