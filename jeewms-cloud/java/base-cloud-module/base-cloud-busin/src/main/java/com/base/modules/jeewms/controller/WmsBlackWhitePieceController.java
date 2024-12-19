package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeewms.entity.WmsBlackWhitePiece;
import com.base.modules.jeewms.service.IWmsBlackWhitePieceService;
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
 * @Description: 黑白件
 * @Author: base-boot
 * @Date:   2021-11-27
 * @Version: V1.0
 */
@Api(tags="黑白件")
@RestController
@RequestMapping("/jeewms/wmsBlackWhitePiece")
@Slf4j
public class WmsBlackWhitePieceController extends BaseController<WmsBlackWhitePiece, IWmsBlackWhitePieceService> {
	@Autowired
	private IWmsBlackWhitePieceService wmsBlackWhitePieceService;

	/**
	 * 分页列表查询
	 *
	 * @param wmsBlackWhitePiece
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "黑白件-分页列表查询")
	@ApiOperation(value="黑白件-分页列表查询", notes="黑白件-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsBlackWhitePiece wmsBlackWhitePiece,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsBlackWhitePiece> queryWrapper = QueryGenerator.initQueryWrapper(wmsBlackWhitePiece, req.getParameterMap());
		Page<WmsBlackWhitePiece> page = new Page<WmsBlackWhitePiece>(pageNo, pageSize);
		IPage<WmsBlackWhitePiece> pageList = wmsBlackWhitePieceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmsBlackWhitePiece
	 * @return
	 */
	@AutoLog(value = "黑白件-添加")
	@ApiOperation(value="黑白件-添加", notes="黑白件-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsBlackWhitePiece wmsBlackWhitePiece) {
		wmsBlackWhitePieceService.save(wmsBlackWhitePiece);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmsBlackWhitePiece
	 * @return
	 */
	@AutoLog(value = "黑白件-编辑")
	@ApiOperation(value="黑白件-编辑", notes="黑白件-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsBlackWhitePiece wmsBlackWhitePiece) {
		wmsBlackWhitePieceService.updateById(wmsBlackWhitePiece);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "黑白件-通过id删除")
	@ApiOperation(value="黑白件-通过id删除", notes="黑白件-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsBlackWhitePieceService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "黑白件-批量删除")
	@ApiOperation(value="黑白件-批量删除", notes="黑白件-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsBlackWhitePieceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "黑白件-通过id查询")
	@ApiOperation(value="黑白件-通过id查询", notes="黑白件-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsBlackWhitePiece wmsBlackWhitePiece = wmsBlackWhitePieceService.getById(id);
		if(wmsBlackWhitePiece==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsBlackWhitePiece);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsBlackWhitePiece
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsBlackWhitePiece wmsBlackWhitePiece) {
        return super.exportXls(request, wmsBlackWhitePiece, WmsBlackWhitePiece.class, "黑白件");
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
        return super.importExcel(request, response, WmsBlackWhitePiece.class);
    }

}
