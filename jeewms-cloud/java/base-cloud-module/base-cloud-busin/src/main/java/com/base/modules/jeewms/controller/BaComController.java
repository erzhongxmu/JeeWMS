package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaCom;
import com.base.modules.jeewms.service.IBaComService;

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
 * @Description: ba_com
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="ba_com")
@RestController
@RequestMapping("/jeewms/baCom")
@Slf4j
public class BaComController extends JeecgController<BaCom, IBaComService> {
	@Autowired
	private IBaComService baComService;

	/**
	 * 分页列表查询
	 *
	 * @param baCom
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ba_com-分页列表查询")
	@ApiOperation(value="ba_com-分页列表查询", notes="ba_com-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaCom baCom,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaCom> queryWrapper = QueryGenerator.initQueryWrapper(baCom, req.getParameterMap());
		Page<BaCom> page = new Page<BaCom>(pageNo, pageSize);
		IPage<BaCom> pageList = baComService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baCom
	 * @return
	 */
	@AutoLog(value = "ba_com-添加")
	@ApiOperation(value="ba_com-添加", notes="ba_com-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaCom baCom) {
		baComService.save(baCom);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baCom
	 * @return
	 */
	@AutoLog(value = "ba_com-编辑")
	@ApiOperation(value="ba_com-编辑", notes="ba_com-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaCom baCom) {
		baComService.updateById(baCom);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_com-通过id删除")
	@ApiOperation(value="ba_com-通过id删除", notes="ba_com-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baComService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ba_com-批量删除")
	@ApiOperation(value="ba_com-批量删除", notes="ba_com-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baComService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ba_com-通过id查询")
	@ApiOperation(value="ba_com-通过id查询", notes="ba_com-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaCom baCom = baComService.getById(id);
		if(baCom==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baCom);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baCom
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaCom baCom) {
        return super.exportXls(request, baCom, BaCom.class, "ba_com");
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
        return super.importExcel(request, response, BaCom.class);
    }

}
