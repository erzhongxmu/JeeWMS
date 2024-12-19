package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeewms.entity.WaveToDown;
import com.base.modules.jeewms.service.IWaveToDownService;
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
 * @Description: wave_to_down
 * @Author: base-boot
 * @Date:   2021-10-20
 * @Version: V1.0
 */
@Api(tags="wave_to_down")
@RestController
@RequestMapping("/jeewms/waveToDown")
@Slf4j
public class WaveToDownController extends BaseController<WaveToDown, IWaveToDownService> {
	@Autowired
	private IWaveToDownService waveToDownService;

	/**
	 * 分页列表查询
	 *
	 * @param waveToDown
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wave_to_down-分页列表查询")
	@ApiOperation(value="wave_to_down-分页列表查询", notes="wave_to_down-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WaveToDown waveToDown,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WaveToDown> queryWrapper = QueryGenerator.initQueryWrapper(waveToDown, req.getParameterMap());
		Page<WaveToDown> page = new Page<WaveToDown>(pageNo, pageSize);
		IPage<WaveToDown> pageList = waveToDownService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param waveToDown
	 * @return
	 */
	@AutoLog(value = "wave_to_down-添加")
	@ApiOperation(value="wave_to_down-添加", notes="wave_to_down-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WaveToDown waveToDown) {
		waveToDownService.save(waveToDown);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param waveToDown
	 * @return
	 */
	@AutoLog(value = "wave_to_down-编辑")
	@ApiOperation(value="wave_to_down-编辑", notes="wave_to_down-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WaveToDown waveToDown) {
		waveToDownService.updateById(waveToDown);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wave_to_down-通过id删除")
	@ApiOperation(value="wave_to_down-通过id删除", notes="wave_to_down-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		waveToDownService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wave_to_down-批量删除")
	@ApiOperation(value="wave_to_down-批量删除", notes="wave_to_down-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.waveToDownService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wave_to_down-通过id查询")
	@ApiOperation(value="wave_to_down-通过id查询", notes="wave_to_down-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WaveToDown waveToDown = waveToDownService.getById(id);
		if(waveToDown==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(waveToDown);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param waveToDown
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WaveToDown waveToDown) {
        return super.exportXls(request, waveToDown, WaveToDown.class, "波次导出列表");
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
        return super.importExcel(request, response, WaveToDown.class);
    }

	 @AutoLog(value = "根据单号波次单打印数据")
	 @ApiOperation(value="根据单号波次单打印数据", notes="根据单号波次单打印数据")
	 @GetMapping(value = "/queryWaveList")
	 public Result<?> queryWaveList(@RequestParam(name="waveId",required=true) String waveId) {
    	 QueryWrapper<WaveToDown> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("wave_id",waveId);
		 List<WaveToDown> waveToDownList = waveToDownService.list(queryWrapper);
		 return Result.OK(waveToDownList);
	 }

}
