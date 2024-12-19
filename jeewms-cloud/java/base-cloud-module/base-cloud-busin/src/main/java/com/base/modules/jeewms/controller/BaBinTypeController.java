package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.BaBinType;
import com.base.modules.jeewms.entity.BaQmQa;
import com.base.modules.jeewms.entity.BaUnit;
import com.base.modules.jeewms.service.IBaBinTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 库位类型
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Api(tags="库位类型")
@RestController
@RequestMapping("/jeewms/baBinType")
@Slf4j
public class BaBinTypeController extends JeecgController<BaBinType, IBaBinTypeService> {
	@Autowired
	private IBaBinTypeService baBinTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param baBinType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "库位类型-分页列表查询")
	@ApiOperation(value="库位类型-分页列表查询", notes="库位类型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BaBinType baBinType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BaBinType> queryWrapper = QueryGenerator.initQueryWrapper(baBinType, req.getParameterMap());
		Page<BaBinType> page = new Page<BaBinType>(pageNo, pageSize);
		IPage<BaBinType> pageList = baBinTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param baBinType
	 * @return
	 */
	@AutoLog(value = "库位类型-添加")
	@ApiOperation(value="库位类型-添加", notes="库位类型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BaBinType baBinType) {
//		baBinTypeService.save(baBinType);

		if (baBinTypeService.lambdaQuery()
				.eq(BaBinType::getBinTypeCode, baBinType.getBinTypeCode()).count()>0){
			return Result.error("编码已存在，请重新输入");
		}else {
			baBinTypeService.save(baBinType);
		}
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param baBinType
	 * @return
	 */
	@AutoLog(value = "库位类型-编辑")
	@ApiOperation(value="库位类型-编辑", notes="库位类型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BaBinType baBinType) {

		BaBinType baBinTypes = baBinTypeService.lambdaQuery().eq(BaBinType::getBinTypeCode, baBinType.getBinTypeCode())
				.ne(BaBinType::getId, baBinType.getId()).one();
		if (baBinTypes != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			baBinTypeService.updateById(baBinType);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "库位类型-通过id删除")
	@ApiOperation(value="库位类型-通过id删除", notes="库位类型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		baBinTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "库位类型-批量删除")
	@ApiOperation(value="库位类型-批量删除", notes="库位类型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.baBinTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "库位类型-通过id查询")
	@ApiOperation(value="库位类型-通过id查询", notes="库位类型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BaBinType baBinType = baBinTypeService.getById(id);
		if(baBinType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(baBinType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param baBinType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaBinType baBinType) {
        return super.exportXls(request, baBinType, BaBinType.class, "库位类型");
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
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<BaBinType> list = ExcelImportUtil.importExcel(file.getInputStream(), BaBinType.class, params);
				long start = System.currentTimeMillis();
				List<BaBinType> listsave = new ArrayList<>();
				if (list.size() > 0) {
					for(BaBinType baBinType : list){
						if(org.apache.commons.lang3.StringUtils.isEmpty(baBinType.getBinTypeCode())){
							continue;
						}
						List<BaBinType> list1 = baBinTypeService.lambdaQuery().in(BaBinType::getBinTypeCode, baBinType.getBinTypeCode()).list();
						for (BaBinType kw : list1) {
							baBinTypeService.removeById(kw.getId());
						}
						listsave.add(baBinType);
					}
				}
				baBinTypeService.saveBatch(listsave);
				log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				return Result.ok("文件导入成功！数据行数：" + list.size());
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

}
