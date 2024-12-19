package com.base.modules.jeeerp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.modules.jeeerp.entity.BillSeq;
import com.base.modules.jeeerp.service.IBillSeqService;
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

/**
* @Description: bill_seq
* @Author: base-boot
* @Date:   2022-11-11
* @Version: V1.0
*/
@Api(tags="bill_seq")
@RestController
@RequestMapping("/busi/billSeq")
@Slf4j
public class BillSeqController extends BaseController<BillSeq, IBillSeqService> {
   @Autowired
   private IBillSeqService billSeqService;

   /**
    * 分页列表查询
    *
    * @param billSeq
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @AutoLog(value = "bill_seq-分页列表查询")
   @ApiOperation(value="bill_seq-分页列表查询", notes="bill_seq-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(BillSeq billSeq,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<BillSeq> queryWrapper = QueryGenerator.initQueryWrapper(billSeq, req.getParameterMap());
       Page<BillSeq> page = new Page<BillSeq>(pageNo, pageSize);
       IPage<BillSeq> pageList = billSeqService.page(page, queryWrapper);
       return Result.OK(pageList);
   }

   /**
    *   添加
    *
    * @param billSeq
    * @return
    */
   @AutoLog(value = "bill_seq-添加")
   @ApiOperation(value="bill_seq-添加", notes="bill_seq-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody BillSeq billSeq) {
       billSeqService.save(billSeq);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param billSeq
    * @return
    */
   @AutoLog(value = "bill_seq-编辑")
   @ApiOperation(value="bill_seq-编辑", notes="bill_seq-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody BillSeq billSeq) {
       billSeqService.updateById(billSeq);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "bill_seq-通过id删除")
   @ApiOperation(value="bill_seq-通过id删除", notes="bill_seq-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       billSeqService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "bill_seq-批量删除")
   @ApiOperation(value="bill_seq-批量删除", notes="bill_seq-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.billSeqService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "bill_seq-通过id查询")
   @ApiOperation(value="bill_seq-通过id查询", notes="bill_seq-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       BillSeq billSeq = billSeqService.getById(id);
       if(billSeq==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(billSeq);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param billSeq
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, BillSeq billSeq) {
       return super.exportXls(request, billSeq, BillSeq.class, "bill_seq");
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
       return super.importExcel(request, response, BillSeq.class);
   }

}
