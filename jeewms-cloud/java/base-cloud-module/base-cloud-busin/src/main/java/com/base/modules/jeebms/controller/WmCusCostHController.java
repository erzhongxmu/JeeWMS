package com.base.modules.jeebms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeewms.entity.BaCostConf;
import com.base.modules.jeewms.service.IBaCostConfService;
import lombok.extern.slf4j.Slf4j;
import com.base.common.system.base.controller.BaseController;
import com.base.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import com.base.common.util.oConvertUtils;
import com.base.modules.jeebms.entity.WmCusCostI;
import com.base.modules.jeebms.entity.WmCusCostH;
import com.base.modules.jeebms.service.IWmCusCostHService;
import com.base.modules.jeebms.service.IWmCusCostIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.base.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import com.base.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 仓储合同
 * @Author: base-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
@Api(tags="仓储合同")
@RestController
@RequestMapping("/jeebms/wmCusCostH")
@Slf4j
public class WmCusCostHController extends BaseController<WmCusCostH, IWmCusCostHService> {

	@Autowired
	private IWmCusCostHService wmCusCostHService;

	@Autowired
	private IWmCusCostIService wmCusCostIService;
	 @Autowired
	 private IBaCostConfService baCostConfService;

	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param wmCusCostH
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "仓储合同-分页列表查询")
	@ApiOperation(value="仓储合同-分页列表查询", notes="仓储合同-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmCusCostH wmCusCostH,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmCusCostH> queryWrapper = QueryGenerator.initQueryWrapper(wmCusCostH, req.getParameterMap());
		Page<WmCusCostH> page = new Page<WmCusCostH>(pageNo, pageSize);
		IPage<WmCusCostH> pageList = wmCusCostHService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param wmCusCostH
     * @return
     */
    @AutoLog(value = "仓储合同-添加")
    @ApiOperation(value="仓储合同-添加", notes="仓储合同-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WmCusCostH wmCusCostH) {
        wmCusCostHService.save(wmCusCostH);
		System.out.println("wmCusCostH.getId()"+wmCusCostH.getId());

		BaCostConf baCostConf = new BaCostConf();
		QueryWrapper<BaCostConf> queryWrapper = new QueryWrapper<>();
		List<BaCostConf> list = baCostConfService.list(queryWrapper);
		for (BaCostConf costConf : list) {
			WmCusCostI wmCusCostI = new WmCusCostI();
			wmCusCostI.setCusCostId(wmCusCostH.getId());
			wmCusCostI.setCostCode(costConf.getCostCode());
			wmCusCostI.setCostSl(costConf.getCostSl());
			wmCusCostI.setCostHs(costConf.getCostHs());
			wmCusCostI.setCostZk(costConf.getCostZk());
			wmCusCostI.setCostBhs(costConf.getCostBhs());
			wmCusCostI.setCostJg(costConf.getCostJg());
			wmCusCostIService.save(wmCusCostI);
		}
		return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param wmCusCostH
     * @return
     */
    @AutoLog(value = "仓储合同-编辑")
    @ApiOperation(value="仓储合同-编辑", notes="仓储合同-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmCusCostH wmCusCostH) {
        wmCusCostHService.updateById(wmCusCostH);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "仓储合同-通过id删除")
    @ApiOperation(value="仓储合同-通过id删除", notes="仓储合同-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        wmCusCostHService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "仓储合同-批量删除")
    @ApiOperation(value="仓储合同-批量删除", notes="仓储合同-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.wmCusCostHService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmCusCostH wmCusCostH) {
        return super.exportXls(request, wmCusCostH, WmCusCostH.class, "仓储合同");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WmCusCostH.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/


    /*--------------------------------子表处理-合同项目-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "合同项目-通过主表ID查询")
	@ApiOperation(value="合同项目-通过主表ID查询", notes="合同项目-通过主表ID查询")
	@GetMapping(value = "/listWmCusCostIByMainId")
    public Result<?> listWmCusCostIByMainId(WmCusCostI wmCusCostI,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<WmCusCostI> queryWrapper = QueryGenerator.initQueryWrapper(wmCusCostI, req.getParameterMap());
        Page<WmCusCostI> page = new Page<WmCusCostI>(pageNo, pageSize);
        IPage<WmCusCostI> pageList = wmCusCostIService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param wmCusCostI
	 * @return
	 */
	@AutoLog(value = "合同项目-添加")
	@ApiOperation(value="合同项目-添加", notes="合同项目-添加")
	@PostMapping(value = "/addWmCusCostI")
	public Result<?> addWmCusCostI(@RequestBody WmCusCostI wmCusCostI) {
		wmCusCostIService.save(wmCusCostI);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param wmCusCostI
	 * @return
	 */
	@AutoLog(value = "合同项目-编辑")
	@ApiOperation(value="合同项目-编辑", notes="合同项目-编辑")
	@PutMapping(value = "/editWmCusCostI")
	public Result<?> editWmCusCostI(@RequestBody WmCusCostI wmCusCostI) {
		wmCusCostIService.updateById(wmCusCostI);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "合同项目-通过id删除")
	@ApiOperation(value="合同项目-通过id删除", notes="合同项目-通过id删除")
	@DeleteMapping(value = "/deleteWmCusCostI")
	public Result<?> deleteWmCusCostI(@RequestParam(name="id",required=true) String id) {
		wmCusCostIService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "合同项目-批量删除")
	@ApiOperation(value="合同项目-批量删除", notes="合同项目-批量删除")
	@DeleteMapping(value = "/deleteBatchWmCusCostI")
	public Result<?> deleteBatchWmCusCostI(@RequestParam(name="ids",required=true) String ids) {
	    this.wmCusCostIService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportWmCusCostI")
    public ModelAndView exportWmCusCostI(HttpServletRequest request, WmCusCostI wmCusCostI) {
		 // Step.1 组装查询条件
		 QueryWrapper<WmCusCostI> queryWrapper = QueryGenerator.initQueryWrapper(wmCusCostI, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<WmCusCostI> pageList = wmCusCostIService.list(queryWrapper);
		 List<WmCusCostI> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "合同项目"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, WmCusCostI.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("合同项目报表", "导出人:" + sysUser.getRealname(), "合同项目"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importWmCusCostI/{mainId}")
    public Result<?> importWmCusCostI(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<WmCusCostI> list = ExcelImportUtil.importExcel(file.getInputStream(), WmCusCostI.class, params);
				 for (WmCusCostI temp : list) {
                    temp.setCusCostId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 wmCusCostIService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-合同项目-end----------------------------------------------*/




}
