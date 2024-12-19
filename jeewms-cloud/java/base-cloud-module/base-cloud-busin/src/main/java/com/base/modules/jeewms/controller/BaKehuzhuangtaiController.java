package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaKehuzhuangtai;
import com.base.modules.jeewms.service.IBaKehuzhuangtaiService;

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
 * @Description: 合同计费方式
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="合同计费方式")
@RestController
@RequestMapping("/jeewms/baKehuzhuangtai")
@Slf4j
public class BaKehuzhuangtaiController extends JeecgController<BaKehuzhuangtai, IBaKehuzhuangtaiService> {
	@Autowired
	private IBaKehuzhuangtaiService baKehuzhuangtaiService;

	/**
	 * 分页列表查询
	 *
	 * @param baKehuzhuangtai
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "合同计费方式-分页列表查询")
	@ApiOperation(value="合同计费方式-分页列表查询", notes="合同计费方式-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaKehuzhuangtai baKehuzhuangtai,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaKehuzhuangtai> queryWrapper = QueryGenerator.initQueryWrapper(baKehuzhuangtai, req.getParameterMap());
		Page<BaKehuzhuangtai> page = new Page<BaKehuzhuangtai>(pageNo, pageSize);
		IPage<BaKehuzhuangtai> pageList = baKehuzhuangtaiService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baKehuzhuangtai
	 * @return
	 */
	@AutoLog(value = "合同计费方式-添加")
	@ApiOperation(value="合同计费方式-添加", notes="合同计费方式-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaKehuzhuangtai baKehuzhuangtai) {
		baKehuzhuangtaiService.save(baKehuzhuangtai);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baKehuzhuangtai
	 * @return
	 */
	@AutoLog(value = "合同计费方式-编辑")
	@ApiOperation(value="合同计费方式-编辑", notes="合同计费方式-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaKehuzhuangtai baKehuzhuangtai) {
		baKehuzhuangtaiService.updateById(baKehuzhuangtai);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "合同计费方式-通过id删除")
	@ApiOperation(value="合同计费方式-通过id删除", notes="合同计费方式-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baKehuzhuangtaiService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "合同计费方式-批量删除")
	@ApiOperation(value="合同计费方式-批量删除", notes="合同计费方式-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baKehuzhuangtaiService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "合同计费方式-通过id查询")
	@ApiOperation(value="合同计费方式-通过id查询", notes="合同计费方式-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaKehuzhuangtai baKehuzhuangtai = baKehuzhuangtaiService.getById(id);
		if(baKehuzhuangtai==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baKehuzhuangtai);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baKehuzhuangtai
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaKehuzhuangtai baKehuzhuangtai) {
        return super.exportXls(request, baKehuzhuangtai, BaKehuzhuangtai.class, "合同计费方式");
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
        return super.importExcel(request, response, BaKehuzhuangtai.class);
    }

}
