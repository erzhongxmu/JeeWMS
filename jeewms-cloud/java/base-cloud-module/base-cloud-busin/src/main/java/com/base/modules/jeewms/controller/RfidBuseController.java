package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.modules.jeewms.entity.RfidBuse;
import com.base.modules.jeewms.service.IRfidBuseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: RFID表
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Api(tags="RFID表")
@RestController
@RequestMapping("/jeewms/rfidBuse")
@Slf4j
public class RfidBuseController extends JeecgController<RfidBuse, IRfidBuseService> {
	@Autowired
	private IRfidBuseService rfidBuseService;

	/**
	 * 分页列表查询
	 *
	 * @param rfidBuse
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "RFID表-分页列表查询")
	@ApiOperation(value="RFID表-分页列表查询", notes="RFID表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(RfidBuse rfidBuse,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<RfidBuse> queryWrapper = QueryGenerator.initQueryWrapper(rfidBuse, req.getParameterMap());
		Page<RfidBuse> page = new Page<RfidBuse>(pageNo, pageSize);
		IPage<RfidBuse> pageList = rfidBuseService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 *   添加
	 *
	 * @param rfidBuse
	 * @return
	 */
	@AutoLog(value = "RFID表-添加")
	@ApiOperation(value="RFID表-添加", notes="RFID表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody RfidBuse rfidBuse) {
		rfidBuseService.save(rfidBuse);
		return Result.ok("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param rfidBuse
	 * @return
	 */
	@AutoLog(value = "RFID表-编辑")
	@ApiOperation(value="RFID表-编辑", notes="RFID表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody RfidBuse rfidBuse) {
		rfidBuseService.updateById(rfidBuse);
		return Result.ok("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "RFID表-通过id删除")
	@ApiOperation(value="RFID表-通过id删除", notes="RFID表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		rfidBuseService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "RFID表-批量删除")
	@ApiOperation(value="RFID表-批量删除", notes="RFID表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.rfidBuseService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "RFID表-通过id查询")
	@ApiOperation(value="RFID表-通过id查询", notes="RFID表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		RfidBuse rfidBuse = rfidBuseService.getById(id);
		if(rfidBuse==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(rfidBuse);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param rfidBuse
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RfidBuse rfidBuse) {
        return super.exportXls(request, rfidBuse, RfidBuse.class, "RFID表");
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
        return super.importExcel(request, response, RfidBuse.class);
    }

}
