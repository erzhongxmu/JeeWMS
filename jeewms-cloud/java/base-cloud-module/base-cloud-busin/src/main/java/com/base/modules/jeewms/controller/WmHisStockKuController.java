package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.entity.WmHisStockKu;
import com.base.modules.jeewms.service.IWmHisStockKuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: wm_his_stock_ku
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wm_his_stock_ku")
@RestController
@RequestMapping("/jeewms/wmHisStockKu")
@Slf4j
public class WmHisStockKuController extends JeecgController<WmHisStockKu, IWmHisStockKuService> {
	@Autowired
	private IWmHisStockKuService wmHisStockKuService;

	/**
	 * 分页列表查询
	 *
	 * @param wmHisStockKu
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wm_his_stock_ku-分页列表查询")
	@ApiOperation(value="wm_his_stock_ku-分页列表查询", notes="wm_his_stock_ku-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmHisStockKu wmHisStockKu,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmHisStockKu> queryWrapper = QueryGenerator.initQueryWrapper(wmHisStockKu, req.getParameterMap());
		Page<WmHisStockKu> page = new Page<WmHisStockKu>(pageNo, pageSize);
		IPage<WmHisStockKu> pageList = wmHisStockKuService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmHisStockKu
	 * @return
	 */
	@AutoLog(value = "wm_his_stock_ku-添加")
	@ApiOperation(value="wm_his_stock_ku-添加", notes="wm_his_stock_ku-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmHisStockKu wmHisStockKu) {
		wmHisStockKuService.save(wmHisStockKu);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmHisStockKu
	 * @return
	 */
	@AutoLog(value = "wm_his_stock_ku-编辑")
	@ApiOperation(value="wm_his_stock_ku-编辑", notes="wm_his_stock_ku-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmHisStockKu wmHisStockKu) {
		wmHisStockKuService.updateById(wmHisStockKu);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_his_stock_ku-通过id删除")
	@ApiOperation(value="wm_his_stock_ku-通过id删除", notes="wm_his_stock_ku-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmHisStockKuService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wm_his_stock_ku-批量删除")
	@ApiOperation(value="wm_his_stock_ku-批量删除", notes="wm_his_stock_ku-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmHisStockKuService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wm_his_stock_ku-通过id查询")
	@ApiOperation(value="wm_his_stock_ku-通过id查询", notes="wm_his_stock_ku-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmHisStockKu wmHisStockKu = wmHisStockKuService.getById(id);
		if(wmHisStockKu==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmHisStockKu);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmHisStockKu
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmHisStockKu wmHisStockKu) {
        return super.exportXls(request, wmHisStockKu, WmHisStockKu.class, "wm_his_stock_ku");
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
        return super.importExcel(request, response, WmHisStockKu.class);
    }

}
