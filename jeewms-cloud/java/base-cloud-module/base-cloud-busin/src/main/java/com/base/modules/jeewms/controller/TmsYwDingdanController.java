package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.controller.dto.HdDTO;
import com.base.modules.jeewms.controller.dto.SendCarDTO;
import com.base.modules.jeewms.entity.TmsYwDingdan;
import com.base.modules.jeewms.service.ITmsMdCheliangService;
import com.base.modules.jeewms.service.ITmsYwDingdanService;
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
import java.util.List;

/**
 * @Description: tms_yw_dingdan
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
@Api(tags="运输管理-订单管理")
@RestController
@RequestMapping("/jeewms/tmsYwDingdan")
@Slf4j
public class TmsYwDingdanController extends JeecgController<TmsYwDingdan, ITmsYwDingdanService> {
	@Autowired
	private ITmsYwDingdanService tmsYwDingdanService;
	@Autowired
	private ITmsMdCheliangService tmsMdCheliangService;

	/**
	 * 分页列表查询
	 *
	 * @param tmsYwDingdan
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tms_yw_dingdan-分页列表查询")
	@ApiOperation(value="tms_yw_dingdan-分页列表查询", notes="tms_yw_dingdan-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TmsYwDingdan tmsYwDingdan,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
	    tmsYwDingdan.setZhuangtai("已下单");
		QueryWrapper<TmsYwDingdan> queryWrapper = QueryGenerator.initQueryWrapper(tmsYwDingdan, req.getParameterMap());
		Page<TmsYwDingdan> page = new Page<TmsYwDingdan>(pageNo, pageSize);
		IPage<TmsYwDingdan> pageList = tmsYwDingdanService.page(page, queryWrapper);
		return Result.ok(pageList);
	}


	/**
	 * 分页列表查询
	 *
	 * @param tmsYwDingdan
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "tms_yw_dingdan-分页列表查询")
	@ApiOperation(value="tms_yw_dingdan-分页列表查询", notes="tms_yw_dingdan-分页列表查询")
	@GetMapping(value = "/zclist")
	public Result<?> queryPageZcList(TmsYwDingdan tmsYwDingdan,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		tmsYwDingdan.setZhuangtai("已派车");
		QueryWrapper<TmsYwDingdan> queryWrapper = QueryGenerator.initQueryWrapper(tmsYwDingdan, req.getParameterMap());
		Page<TmsYwDingdan> page = new Page<TmsYwDingdan>(pageNo, pageSize);
		IPage<TmsYwDingdan> pageList = tmsYwDingdanService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	 /**
	  *  派车管理-查询车辆列表
	  *
	  * @return
	  */
	 @AutoLog(value = "派车管理-查询车辆列表")
	 @ApiOperation(value="派车管理-查询车辆列表", notes="派车管理-查询车辆列表")
	 @GetMapping(value = "/getCarList")
	 public Result<?> getCarList() {
		 return Result.ok(tmsMdCheliangService.list());
	 }

	 /**
	  *  派车管理-批量派车
	  *
	  * @return
	  */
	 @AutoLog(value = "派车管理-批量派车")
	 @ApiOperation(value="派车管理-批量派车", notes="派车管理-批量派车")
	 @PostMapping(value = "/batchSendCar")
	 public Result<?> batchSendCar(@RequestBody SendCarDTO param) {
	     tmsYwDingdanService.batchSendCar(param);
		 return Result.ok("操作成功");
	 }

     /**
      *  装车
      *
      * @return
      */
     @AutoLog(value = "装车")
     @ApiOperation(value="装车", notes="装车")
     @PostMapping(value = "/dozc")
     public Result<?> dozc(@RequestParam String id) {
         TmsYwDingdan tmsYwDingdan = tmsYwDingdanService.getById(id);
         tmsYwDingdan.setZhuangtai("已装车");
         tmsYwDingdanService.updateById(tmsYwDingdan);
         return Result.ok("操作成功");
     }

     /**
      *  取消装车
      *
      * @return
      */
     @AutoLog(value = "取消装车")
     @ApiOperation(value="取消装车", notes="取消装车")
     @PostMapping(value = "/cancelZc")
     public Result<?> cancelZc(@RequestParam String id) {
         TmsYwDingdan tmsYwDingdan = tmsYwDingdanService.getById(id);
         tmsYwDingdan.setZhuangtai("已派车");
         tmsYwDingdanService.updateById(tmsYwDingdan);
         return Result.ok("操作成功");
     }

     /**
      * 回单列表
      *
      * @param tmsYwDingdan
      * @param pageNo
      * @param pageSize
      * @param req
      * @return
      */
     @AutoLog(value = "回单列表")
     @ApiOperation(value="回单列表", notes="回单列表")
     @GetMapping(value = "/hdList")
     public Result<?> hdList(TmsYwDingdan tmsYwDingdan,
                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                    HttpServletRequest req) {
         tmsYwDingdan.setZhuangtai("已装车");
         QueryWrapper<TmsYwDingdan> queryWrapper = QueryGenerator.initQueryWrapper(tmsYwDingdan, req.getParameterMap());
         Page<TmsYwDingdan> page = new Page<TmsYwDingdan>(pageNo, pageSize);
         IPage<TmsYwDingdan> pageList = tmsYwDingdanService.page(page, queryWrapper);
         return Result.ok(pageList);
     }

    /**
     * 结算列表
     *
     * @param tmsYwDingdan
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "结算列表")
    @ApiOperation(value="结算列表", notes="结算列表")
    @GetMapping(value = "/jsList")
    public Result<?> jsList(TmsYwDingdan tmsYwDingdan,
                            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                            HttpServletRequest req) {
        tmsYwDingdan.setZhuangtai("已回单");
        QueryWrapper<TmsYwDingdan> queryWrapper = QueryGenerator.initQueryWrapper(tmsYwDingdan, req.getParameterMap());
        Page<TmsYwDingdan> page = new Page<TmsYwDingdan>(pageNo, pageSize);
        IPage<TmsYwDingdan> pageList = tmsYwDingdanService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 报表查询
     *
     * @param tmsYwDingdan
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "报表查询")
    @ApiOperation(value="报表查询", notes="报表查询")
    @GetMapping(value = "/bbList")
    public Result<?> bbList(TmsYwDingdan tmsYwDingdan,
                            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                            HttpServletRequest req) {
        QueryWrapper<TmsYwDingdan> queryWrapper = QueryGenerator.initQueryWrapper(tmsYwDingdan, req.getParameterMap());
        Page<TmsYwDingdan> page = new Page<TmsYwDingdan>(pageNo, pageSize);
        IPage<TmsYwDingdan> pageList = tmsYwDingdanService.page(page, queryWrapper);
        return Result.ok(pageList);
    }



     /**
      *  批量回单
      *
      * @return
      */
     @AutoLog(value = "批量回单")
     @ApiOperation(value="批量回单", notes="批量回单")
     @PostMapping(value = "/batchHd")
     public Result<?> batchHd(@RequestParam HdDTO param) {
         tmsYwDingdanService.batchHd((param));
         return Result.ok("操作成功");

     }

    /**
     *  批量回单
     *
     * @return
     */
    @AutoLog(value = "批量取消回单")
    @ApiOperation(value="批量取消回单", notes="批量取消回单")
    @PostMapping(value = "/batchCancelHd")
    public Result<?> batchCancelHd(@RequestParam List<String> id) {
        tmsYwDingdanService.batchCancelHd((id));
        return Result.ok("操作成功");
    }


	/**
	 *   添加
	 * fuck you moheu fuckr caoniamde nishi shene dng
	 * @param tmsYwDingdan
	 * @return
	 */
	@AutoLog(value = "tms_yw_dingdan-添加")
	@ApiOperation(value="tms_yw_dingdan-添加", notes="tms_yw_dingdan-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TmsYwDingdan tmsYwDingdan) {
		tmsYwDingdan.setZhuangtai("已下单");
		tmsYwDingdanService.save(tmsYwDingdan);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param tmsYwDingdan
	 * @return
	 */
	@AutoLog(value = "tms_yw_dingdan-编辑")
	@ApiOperation(value="tms_yw_dingdan-编辑", notes="tms_yw_dingdan-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TmsYwDingdan tmsYwDingdan) {
		tmsYwDingdanService.updateById(tmsYwDingdan);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tms_yw_dingdan-通过id删除")
	@ApiOperation(value="tms_yw_dingdan-通过id删除", notes="tms_yw_dingdan-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		tmsYwDingdanService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "tms_yw_dingdan-批量删除")
	@ApiOperation(value="tms_yw_dingdan-批量删除", notes="tms_yw_dingdan-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tmsYwDingdanService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "tms_yw_dingdan-通过id查询")
	@ApiOperation(value="tms_yw_dingdan-通过id查询", notes="tms_yw_dingdan-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TmsYwDingdan tmsYwDingdan = tmsYwDingdanService.getById(id);
		if(tmsYwDingdan==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(tmsYwDingdan);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tmsYwDingdan
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TmsYwDingdan tmsYwDingdan) {
        return super.exportXls(request, tmsYwDingdan, TmsYwDingdan.class, "tms_yw_dingdan");
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
        return super.importExcel(request, response, TmsYwDingdan.class);
    }

}
