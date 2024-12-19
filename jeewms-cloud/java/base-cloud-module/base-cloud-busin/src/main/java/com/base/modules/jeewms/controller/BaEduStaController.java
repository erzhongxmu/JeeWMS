package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaEduSta;
import com.base.modules.jeewms.service.IBaEduStaService;

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
 * @Description: ba_edu_sta
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="ba_edu_sta")
@RestController
@RequestMapping("/jeewms/baEduSta")
@Slf4j
public class BaEduStaController extends JeecgController<BaEduSta, IBaEduStaService> {
	@Autowired
	private IBaEduStaService baEduStaService;

	/**
	 * 分页列表查询
	 *
	 * @param baEduSta
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ba_edu_sta-分页列表查询")
	@ApiOperation(value="ba_edu_sta-分页列表查询", notes="ba_edu_sta-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaEduSta baEduSta,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaEduSta> queryWrapper = QueryGenerator.initQueryWrapper(baEduSta, req.getParameterMap());
		Page<BaEduSta> page = new Page<BaEduSta>(pageNo, pageSize);
		IPage<BaEduSta> pageList = baEduStaService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baEduSta
	 * @return
	 */
	@AutoLog(value = "ba_edu_sta-添加")
	@ApiOperation(value="ba_edu_sta-添加", notes="ba_edu_sta-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaEduSta baEduSta) {
		baEduStaService.save(baEduSta);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baEduSta
	 * @return
	 */
	@AutoLog(value = "ba_edu_sta-编辑")
	@ApiOperation(value="ba_edu_sta-编辑", notes="ba_edu_sta-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaEduSta baEduSta) {
		baEduStaService.updateById(baEduSta);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_edu_sta-通过id删除")
	@ApiOperation(value="ba_edu_sta-通过id删除", notes="ba_edu_sta-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baEduStaService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ba_edu_sta-批量删除")
	@ApiOperation(value="ba_edu_sta-批量删除", notes="ba_edu_sta-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baEduStaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_edu_sta-通过id查询")
	@ApiOperation(value="ba_edu_sta-通过id查询", notes="ba_edu_sta-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaEduSta baEduSta = baEduStaService.getById(id);
		if(baEduSta==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baEduSta);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baEduSta
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaEduSta baEduSta) {
        return super.exportXls(request, baEduSta, BaEduSta.class, "ba_edu_sta");
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
        return super.importExcel(request, response, BaEduSta.class);
    }

}
