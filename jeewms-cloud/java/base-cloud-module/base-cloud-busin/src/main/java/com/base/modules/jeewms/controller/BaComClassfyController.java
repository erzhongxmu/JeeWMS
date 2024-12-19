package com.base.modules.jeewms.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaComClassfy;
import com.base.modules.jeewms.entity.WmsComType;
import com.base.modules.jeewms.service.IBaComClassfyService;

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
 * @Description: 行业分类
 * @Author: base-boot
 * @Date:   2021-05-21
 * @Version: V1.0
 */
@Api(tags="行业分类")
@RestController
@RequestMapping("/jeewms/baComClassfy")
@Slf4j
public class BaComClassfyController extends JeecgController<BaComClassfy, IBaComClassfyService> {
	@Autowired
	private IBaComClassfyService baComClassfyService;

	/**
	 * 分页列表查询
	 *
	 * @param baComClassfy
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "行业分类-分页列表查询")
	@ApiOperation(value="行业分类-分页列表查询", notes="行业分类-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaComClassfy baComClassfy,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaComClassfy> queryWrapper = QueryGenerator.initQueryWrapper(baComClassfy, req.getParameterMap());
		Page<BaComClassfy> page = new Page<BaComClassfy>(pageNo, pageSize);
		IPage<BaComClassfy> pageList = baComClassfyService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baComClassfy
	 * @return
	 */
	@AutoLog(value = "行业分类-添加")
	@ApiOperation(value="行业分类-添加", notes="行业分类-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaComClassfy baComClassfy) {

		if (baComClassfyService.lambdaQuery()
				.eq(BaComClassfy::getClassflCode,baComClassfy.getClassflCode()).count()>0){
			return Result.error("编码已存在，请重新输入");
		}else {
			baComClassfyService.save(baComClassfy);
		}
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baComClassfy
	 * @return
	 */
	@AutoLog(value = "行业分类-编辑")
	@ApiOperation(value="行业分类-编辑", notes="行业分类-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaComClassfy baComClassfy) {

		BaComClassfy baComClassfys = baComClassfyService.lambdaQuery().eq(BaComClassfy::getClassflCode, baComClassfy.getClassflCode())
				.ne(BaComClassfy::getId, baComClassfy.getId()).one();
		if (baComClassfys != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			baComClassfyService.updateById(baComClassfy);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "行业分类-通过id删除")
	@ApiOperation(value="行业分类-通过id删除", notes="行业分类-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baComClassfyService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "行业分类-批量删除")
	@ApiOperation(value="行业分类-批量删除", notes="行业分类-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baComClassfyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "行业分类-通过id查询")
	@ApiOperation(value="行业分类-通过id查询", notes="行业分类-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaComClassfy baComClassfy = baComClassfyService.getById(id);
		if(baComClassfy==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baComClassfy);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baComClassfy
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaComClassfy baComClassfy) {
        return super.exportXls(request, baComClassfy, BaComClassfy.class, "行业分类");
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
        return super.importExcel(request, response, BaComClassfy.class);
    }

}
