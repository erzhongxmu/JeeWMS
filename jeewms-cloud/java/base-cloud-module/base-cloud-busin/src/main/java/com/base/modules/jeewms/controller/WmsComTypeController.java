package com.base.modules.jeewms.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.vo.LoginUser;
import com.base.modules.jeewms.entity.BaStore;
import com.base.modules.jeewms.entity.MdSup;
import com.base.modules.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.base.controller.JeecgController;;
import com.base.modules.jeewms.controller.dto.ComTypeDTO;
import com.base.modules.jeewms.entity.WmsComType;
import com.base.modules.jeewms.service.IWmsComTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.BeanUtils;
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
 * @Description: wms_com_type
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="wms_com_type")
@RestController
@RequestMapping("/jeewms/wmsComType")
@Slf4j
public class WmsComTypeController extends JeecgController<WmsComType, IWmsComTypeService> {
	@Autowired
	private IWmsComTypeService wmsComTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param wmsComType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wms_com_type-分页列表查询")
	@ApiOperation(value="wms_com_type-分页列表查询", notes="wms_com_type-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsComType wmsComType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsComType> queryWrapper = QueryGenerator.initQueryWrapper(wmsComType, req.getParameterMap());
		/*QueryWrapper<WmsComType> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(StringUtils.isNotBlank(wmsComType.getComTypeCode()),"com_type_code",wmsComType.getComTypeCode());
		queryWrapper.eq(StringUtils.isNotBlank(wmsComType.getComTypeName()),"com_type_name",wmsComType.getComTypeName());
		if (!Convert.toStr(wmsComType.getTenantId()).equals("0")){
			queryWrapper.eq("tenant_id",wmsComType.getTenantId());
		}*/
		Page<WmsComType> page = new Page<WmsComType>(pageNo, pageSize);
		IPage<WmsComType> pageList = wmsComTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmsComType
	 * @return
	 */
	@AutoLog(value = "wms_com_type-添加")
	@ApiOperation(value="wms_com_type-添加", notes="wms_com_type-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsComType wmsComType) {
		wmsComType.setComTypeDel("0");
		if (wmsComTypeService.lambdaQuery()
				.eq(WmsComType::getComTypeCode,wmsComType.getComTypeCode()).count()>0){
			return Result.error("编码已存在，请重新输入");
		}else {
			wmsComTypeService.save(wmsComType);
		}
		return Result.ok("添加成功！");
	}

	 /**
	  *   启用/停用
	  *
	  * @param param
	  * @return
	  */
	 @AutoLog(value = "启用/停用")
	 @ApiOperation(value="启用/停用", notes="启用/停用")
	 @PostMapping(value = "/startOrStop")
	 public Result<?> startOrStop(@RequestBody ComTypeDTO param) {
	     WmsComType wmsComType = new WmsComType();
         BeanUtils.copyProperties(param,wmsComType);
		 wmsComTypeService.updateById(wmsComType);
		 return Result.ok("操作成功");
	 }
	/**
	 *  编辑
	 *
	 * @param wmsComType
	 * @return
	 */
	@AutoLog(value = "wms_com_type-编辑")
	@ApiOperation(value="wms_com_type-编辑", notes="wms_com_type-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsComType wmsComType) {
		WmsComType wmsComTypes = wmsComTypeService.lambdaQuery().eq(WmsComType::getComTypeCode, wmsComType.getComTypeCode())
				.ne(WmsComType::getId, wmsComType.getId()).one();
		if (wmsComTypes != null) {
			return Result.error("编码已存在，请重新输入");
		}else {
			wmsComTypeService.updateById(wmsComType);
		}
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wms_com_type-通过id删除")
	@ApiOperation(value="wms_com_type-通过id删除", notes="wms_com_type-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsComTypeService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wms_com_type-批量删除")
	@ApiOperation(value="wms_com_type-批量删除", notes="wms_com_type-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsComTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wms_com_type-通过id查询")
	@ApiOperation(value="wms_com_type-通过id查询", notes="wms_com_type-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsComType wmsComType = wmsComTypeService.getById(id);
		if(wmsComType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(wmsComType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsComType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsComType wmsComType) {
        return super.exportXls(request, wmsComType, WmsComType.class, "企业属性");
    }

    /**
    * 通过excel导入数据
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
				List<WmsComType> list = ExcelImportUtil.importExcel(file.getInputStream(), WmsComType.class, params);
				long start = System.currentTimeMillis();
				List<WmsComType> listsave = new ArrayList<>();
				if (list.size() > 0) {
					for(WmsComType wmsComType : list){
						if(org.apache.commons.lang3.StringUtils.isEmpty(wmsComType.getComTypeCode())){
							continue;
						}
						List<WmsComType> list1 = wmsComTypeService.lambdaQuery().in(WmsComType::getComTypeCode, wmsComType.getComTypeCode()).list();
						for (WmsComType kw : list1) {
							wmsComTypeService.removeById(kw.getId());
						}
						listsave.add(wmsComType);
					}
				}
				wmsComTypeService.saveBatch(listsave);
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
