package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaComClassfy;
import com.base.modules.jeewms.entity.BaComDeg;
import com.base.modules.jeewms.service.IBaComDegService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;

 /**
 * @Description: 企业等级
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
@Api(tags="企业等级")
@RestController
@RequestMapping("/jeewms/baComDeg")
@Slf4j
public class BaComDegController extends JeecgController<BaComDeg, IBaComDegService> {
	@Autowired
	private IBaComDegService baComDegService;

	/**
	 * 分页列表查询
	 *
	 * @param baComDeg
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "企业等级-分页列表查询")
	@ApiOperation(value="企业等级-分页列表查询", notes="企业等级-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaComDeg baComDeg,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaComDeg> queryWrapper = QueryGenerator.initQueryWrapper(baComDeg, req.getParameterMap());
		Page<BaComDeg> page = new Page<BaComDeg>(pageNo, pageSize);
		IPage<BaComDeg> pageList = baComDegService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baComDeg
	 * @return
	 */
	@AutoLog(value = "企业等级-添加")
	@ApiOperation(value="企业等级-添加", notes="企业等级-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaComDeg baComDeg) {
		if (baComDegService.lambdaQuery()
				.eq(BaComDeg::getComDegCode,baComDeg.getComDegCode()).count()>0){
			return Result.error("编码已存在，请重新输入");
		}else {
			baComDegService.save(baComDeg);
		}
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baComDeg
	 * @return
	 */
	@AutoLog(value = "企业等级-编辑")
	@ApiOperation(value="企业等级-编辑", notes="企业等级-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaComDeg baComDeg) {
		BaComDeg baComDegs = baComDegService.lambdaQuery().eq(BaComDeg::getComDegCode, baComDeg.getComDegCode())
				.ne(BaComDeg::getId, baComDeg.getId()).one();
		if (baComDegs != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			baComDegService.updateById(baComDeg);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "企业等级-通过id删除")
	@ApiOperation(value="企业等级-通过id删除", notes="企业等级-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baComDegService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "企业等级-批量删除")
	@ApiOperation(value="企业等级-批量删除", notes="企业等级-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baComDegService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "企业等级-通过id查询")
	@ApiOperation(value="企业等级-通过id查询", notes="企业等级-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaComDeg baComDeg = baComDegService.getById(id);
		if(baComDeg==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baComDeg);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baComDeg
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaComDeg baComDeg) {
        return super.exportXls(request, baComDeg, BaComDeg.class, "企业等级");
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
        return super.importExcel(request, response, BaComDeg.class);
    }

}
