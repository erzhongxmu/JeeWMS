package com.base.modules.demo.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.modules.demo.test.entity.BaseOrderCustomer;
import com.base.modules.demo.test.entity.BaseOrderMain;
import com.base.modules.demo.test.entity.BaseOrderTicket;
import com.base.modules.demo.test.service.IBaseOrderCustomerService;
import com.base.modules.demo.test.service.IBaseOrderMainService;
import com.base.modules.demo.test.service.IBaseOrderTicketService;
import com.base.modules.demo.test.vo.BaseOrderMainPage;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description: 一对多示例（ERP TAB风格）
 * @Author: ZhiLin
 * @Date: 2019-02-20
 * @Version: v2.0
 */
@Slf4j
@RestController
@RequestMapping("/test/order")
public class BaseOrderTabMainController {

    @Autowired
    private IBaseOrderMainService baseOrderMainService;
    @Autowired
    private IBaseOrderCustomerService baseOrderCustomerService;
    @Autowired
    private IBaseOrderTicketService baseOrderTicketService;

    /**
     * 分页列表查询
     *
     * @param baseOrderMain
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/orderList")
    public Result<?> respondePagedData(BaseOrderMain baseOrderMain,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req) {
        QueryWrapper<BaseOrderMain> queryWrapper = QueryGenerator.initQueryWrapper(baseOrderMain, req.getParameterMap());
        Page<BaseOrderMain> page = new Page<BaseOrderMain>(pageNo, pageSize);
        IPage<BaseOrderMain> pageList = baseOrderMainService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param baseOrderMainPage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody BaseOrderMainPage baseOrderMainPage) {
        BaseOrderMain baseOrderMain = new BaseOrderMain();
        BeanUtils.copyProperties(baseOrderMainPage, baseOrderMain);
        baseOrderMainService.save(baseOrderMain);
        return Result.ok("添加成功!");
    }

    /**
     * 编辑
     *
     * @param baseOrderMainPage
     * @return
     */
    @PutMapping("/edit")
    public Result<?> edit(@RequestBody BaseOrderMainPage baseOrderMainPage) {
        BaseOrderMain baseOrderMain = new BaseOrderMain();
        BeanUtils.copyProperties(baseOrderMainPage, baseOrderMain);
        baseOrderMainService.updateById(baseOrderMain);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        baseOrderMainService.delMain(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.baseOrderMainService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        BaseOrderMain baseOrderMain = baseOrderMainService.getById(id);
        return Result.ok(baseOrderMain);
    }


    /**
     * 通过id查询
     *
     * @param baseOrderCustomer
     * @return
     */
    @GetMapping(value = "/listOrderCustomerByMainId")
    public Result<?> queryOrderCustomerListByMainId(BaseOrderCustomer baseOrderCustomer,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<BaseOrderCustomer> queryWrapper = QueryGenerator.initQueryWrapper(baseOrderCustomer, req.getParameterMap());
        Page<BaseOrderCustomer> page = new Page<BaseOrderCustomer>(pageNo, pageSize);
        IPage<BaseOrderCustomer> pageList = baseOrderCustomerService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 通过id查询
     *
     * @param baseOrderTicket
     * @return
     */
    @GetMapping(value = "/listOrderTicketByMainId")
    public Result<?> queryOrderTicketListByMainId(BaseOrderTicket baseOrderTicket,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  HttpServletRequest req) {
        QueryWrapper<BaseOrderTicket> queryWrapper = QueryGenerator.initQueryWrapper(baseOrderTicket, req.getParameterMap());
        Page<BaseOrderTicket> page = new Page<BaseOrderTicket>(pageNo, pageSize);
        IPage<BaseOrderTicket> pageList = baseOrderTicketService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param baseOrderCustomer
     * @return
     */
    @PostMapping(value = "/addCustomer")
    public Result<?> addCustomer(@RequestBody BaseOrderCustomer baseOrderCustomer) {
        baseOrderCustomerService.save(baseOrderCustomer);
        return Result.ok("添加成功!");
    }

    /**
     * 编辑
     *
     * @param baseOrderCustomer
     * @return
     */
    @PutMapping("/editCustomer")
    public Result<?> editCustomer(@RequestBody BaseOrderCustomer baseOrderCustomer) {
        baseOrderCustomerService.updateById(baseOrderCustomer);
        return Result.ok("添加成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteCustomer")
    public Result<?> deleteCustomer(@RequestParam(name = "id", required = true) String id) {
        baseOrderCustomerService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchCustomer")
    public Result<?> deleteBatchCustomer(@RequestParam(name = "ids", required = true) String ids) {
        this.baseOrderCustomerService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 添加
     *
     * @param baseOrderTicket
     * @return
     */
    @PostMapping(value = "/addTicket")
    public Result<?> addTicket(@RequestBody BaseOrderTicket baseOrderTicket) {
        baseOrderTicketService.save(baseOrderTicket);
        return Result.ok("添加成功!");
    }

    /**
     * 编辑
     *
     * @param baseOrderTicket
     * @return
     */
    @PutMapping("/editTicket")
    public Result<?> editTicket(@RequestBody BaseOrderTicket baseOrderTicket) {
        baseOrderTicketService.updateById(baseOrderTicket);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteTicket")
    public Result<?> deleteTicket(@RequestParam(name = "id", required = true) String id) {
        baseOrderTicketService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchTicket")
    public Result<?> deleteBatchTicket(@RequestParam(name = "ids", required = true) String ids) {
        this.baseOrderTicketService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

}
