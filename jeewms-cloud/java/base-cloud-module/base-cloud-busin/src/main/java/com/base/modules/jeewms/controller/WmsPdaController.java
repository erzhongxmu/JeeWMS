package com.base.modules.jeewms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.vo.LoginUser;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.WmImNoticeH;
import com.base.modules.jeewms.entity.WmInQmI;
import com.base.modules.jeewms.entity.WmOmQmI;
import com.base.modules.jeewms.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: PDA
 * @Author: base-boot
 * @Date: 2021-11-27
 * @Version: V1.0
 */
@Api(tags="WMSPDA")
@RestController
@RequestMapping("/jeewms/wmsPda")
@Slf4j
public class WmsPdaController {

    @Autowired
    private IWmsPdaService wmsPdaService;
    @Autowired
    private IUserToGoodsService userToGoodsService;
    @Autowired
    private IWmInQmIService wmInQmIService;
    @Autowired
    private IBaKwService baKwService;
    @Autowired
    private IWmOmQmIService wmOmQmIService;
    @Autowired
    private IWmImNoticeHService wmImNoticeHService;

    @AutoLog(value = "获取列表")
    @ApiOperation(value="获取列表", notes="获取列表")
    @GetMapping(value = "/list")
    public Result<?> queryList(@RequestParam(name="type",required=false) String type,
                               @RequestParam(name="searchVal",required=false) String searchVal,
                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                               HttpServletRequest req) {
        //1.验收、2.上架、3.按单拣货、4.复核、5.波次下架、6.波次分拣、7.储位转移、8.库存查询、9.边拣边分、10.商品查询
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if("1".equals(type)){
            return Result.ok(wmsPdaService.queryYanshouList(searchVal, pageNo, pageSize, req));
        }else if("2".equals(type)){
            QueryWrapper<WmInQmI> queryWrapper = new QueryWrapper<>();
            if(StringUtils.isNotEmpty(searchVal)){
                queryWrapper.and(wrapper -> wrapper.eq("bin_sta", searchVal).or().like("im_notice_id", searchVal)
                        .or().like("goods_id",searchVal).or().like("goods_name",searchVal).or().like("cus_code",searchVal)
                        .or().like("tin_id",searchVal).or().like("bin_id",searchVal));
            }
            queryWrapper.orderByDesc("create_time");
            Page<WmInQmI> page = new Page<WmInQmI>(pageNo, pageSize);
            List<MdGoods> goodsList = userToGoodsService.query(loginUser.getUsername());
            if (!goodsList.isEmpty()) {
                queryWrapper.in("goods_id", goodsList.stream().map(MdGoods::getShpBianMa).collect(Collectors.toList()));
                IPage<WmInQmI> pageList = wmInQmIService.page(page, queryWrapper);
                pageList.getRecords().forEach(item -> {
                    item.setRecommendBinId(baKwService.selectRecommandBin(item.getGoodsTypeId()));
                });
                return Result.ok(pageList);
            }
            return Result.ok(new Page<>());
        }else if("3".equals(type)){
            List<MdGoods> goodsList = userToGoodsService.query(loginUser.getUsername());
            QueryWrapper<WmOmQmI> queryWrapper = new QueryWrapper<>();
            if(StringUtils.isNotEmpty(searchVal)){
                queryWrapper.and(wrapper -> wrapper.eq("om_notice_id", searchVal).or().like("goods_id", searchVal)
                        .or().like("goods_name",searchVal).or().like("cus_code",searchVal)
                        .or().like("bin_id",searchVal).or().like("tin_id",searchVal));
            }
            queryWrapper.like("bin_sta", "N");//未下架
            if (!goodsList.isEmpty()) {
                queryWrapper.in("goods_id", goodsList.stream().map(MdGoods::getShpBianMa).collect(Collectors.toList()));
                Page<WmOmQmI> page = new Page<WmOmQmI>(pageNo, pageSize);
                return Result.ok(wmOmQmIService.page(page, queryWrapper));
            }
            return Result.ok(new Page<>());
        }else if("4".equals(type)){

        }else if("5".equals(type)){

        }else if("6".equals(type)){

        }else if("7".equals(type)){

        }else if("8".equals(type)){

        }else if("9".equals(type)){

        }else if("10".equals(type)){

        }

        return null;
    }

    @AutoLog(value = "查询采购到货未审核单据")
    @ApiOperation(value="查询采购到货未审核单据", notes="查询采购到货未审核单据")
    @GetMapping(value = "/queryCaigoudaohuowsh")
    public Result<?> queryCaigoudaohuowsh(@RequestParam(name="id",required=true) String id) {
        QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u8_sta","0");
        queryWrapper.isNotNull("u8_id");
        List<WmImNoticeH> wmImNoticeHList = wmImNoticeHService.list(queryWrapper);
        return Result.ok(wmImNoticeHList);
    }

    @AutoLog(value = "调用U8采购到货审核")
    @ApiOperation(value="调用U8采购到货审核", notes="调用U8采购到货审核")
    @GetMapping(value = "/getU8Caigoudaohuosh")
    public Result<?> getU8Caigoudaohuosh(@RequestParam(name="id",required=true) String id) throws Exception {
        Result<?> result = wmsPdaService.getU8Caigoudaohuosh(id);
        return result;
    }

    @AutoLog(value = "查询采购到货已审核单据")
    @ApiOperation(value="查询采购到货已审核单据", notes="查询采购到货已审核单据")
    @GetMapping(value = "/queryCaigoudaohuoysh")
    public Result<?> queryCaigoudaohuoysh(@RequestParam(name="id",required=true) String id) {
        QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u8_sta","1");
        queryWrapper.isNotNull("u8_id");
        List<WmImNoticeH> wmImNoticeHList = wmImNoticeHService.list(queryWrapper);
        return Result.ok(wmImNoticeHList);
    }

    @AutoLog(value = "调用U8采购入库")
    @ApiOperation(value="调用U8采购入库", notes="调用U8采购入库")
    @GetMapping(value = "/getU8Caigouruku")
    public Result<?> getU8Caigouruku(@RequestParam(name="id",required=true) String id) throws Exception {
        Result<?> result = wmsPdaService.getU8Caigouruku(id);
        return result;
    }

    @AutoLog(value = "调用U8产成品入库")
    @ApiOperation(value="调用U8产成品入库", notes="调用U8产成品入库")
    @GetMapping(value = "/getU8Chengpinruku")
    public Result<?> getU8Chengpinruku(@RequestParam(name="id",required=true) String id) throws Exception {
        Result<?> result = wmsPdaService.getU8Chengpinruku(id);
        return result;
    }

    @AutoLog(value = "调用U8销售出库")
    @ApiOperation(value="调用U8销售出库", notes="调用U8销售出库")
    @GetMapping(value = "/getU8Xiaoshouchuku")
    public Result<?> getU8Xiaoshouchuku(@RequestParam(name="id",required=true) String id) throws Exception {
        Result<?> result = wmsPdaService.getU8Xiaoshouchuku(id);
        return result;
    }

    @AutoLog(value = "调用U8材料出库")
    @ApiOperation(value="调用U8材料出库", notes="调用U8材料出库")
    @GetMapping(value = "/getU8Cailiaochuku")
    public Result<?> getU8Cailiaochuku(@RequestParam(name="id",required=true) String id) throws Exception {
        Result<?> result = wmsPdaService.getU8Cailiaochuku(id);
        return result;
    }

    @AutoLog(value = "调用U8调拨单")
    @ApiOperation(value="调用U8调拨单", notes="调用U8调拨单")
    @GetMapping(value = "/getU8Diaobodan")
    public Result<?> getU8Diaobodan(@RequestParam(name="id",required=true) String id) throws Exception {
        Result<?> result = wmsPdaService.getU8Diaobodan(id);
        return result;
    }

    @AutoLog(value = "调用U8调拨单审核")
    @ApiOperation(value="调用U8调拨单审核", notes="调用U8调拨单审核")
    @GetMapping(value = "/getU8Diaobodansh")
    public Result<?> getU8Diaobodansh(@RequestParam(name="id",required=true) String id) throws Exception {
        Result<?> result = wmsPdaService.getU8Diaobodansh(id);
        return result;
    }

    @AutoLog(value = "调用U8盘点单")
    @ApiOperation(value="调用U8盘点单", notes="调用U8盘点单")
    @GetMapping(value = "/getU8Pandiandan")
    public Result<?> getU8Pandiandan(@RequestParam(name="id",required=true) String id) throws Exception {
        Result<?> result = wmsPdaService.getU8Pandiandan(id);
        return result;
    }

}
