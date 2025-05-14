package com.base.modules.external.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.XmlUtil;
import com.alibaba.fastjson.JSONObject;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.base.dto.CDSReceiptInfo;
import com.base.common.base.dto.WMSStockQueryDTO;
import com.base.common.util.CDSUtils;
import com.base.common.base.dto.WMSResult;
import com.base.modules.jeewms.entity.RfidBuse;
import com.base.modules.jeewms.entity.WMSUserOrderPushData;
import com.base.modules.jeewms.entity.WmOmNoticeH;
import com.base.modules.jeewms.service.IMdGoodsService;
import com.base.modules.jeewms.service.IRfidBuseService;
import com.base.modules.jeewms.service.IWmOmNoticeHService;
import com.base.modules.jeewms.service.IWmOmQmIService;
import com.base.modules.jeewms.vo.GoodsRegisterRequest;
import com.base.modules.jeewms.vo.WmOmNoticeHPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(tags = "对外接口对接")
@RestController
@RequestMapping("/jeewms/externalApi")
@Slf4j
public class ExternalApiController {

    @Autowired
    private IMdGoodsService mdGoodsService;

    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;

    @Autowired
    private IWmOmQmIService wmOmQmIService;

    @Autowired
    private IRfidBuseService rfidBuseService;




    /**
     *   添加
     *
     * @param
     * @return
     */
    @AutoLog(value = "商品信息-外部写入")
    @ApiOperation(value="商品信息-添加", notes="商品信息-添加")
    @PostMapping(value = "/add1")
    public Result<?> add1(@RequestBody GoodsRegisterRequest goodsRegister,HttpServletRequest request) {
        mdGoodsService.goodsToWMS(goodsRegister);
        return Result.OK();
    }


    /**
     *   添加
     *
     * @return
     */
    @AutoLog(value = "订单信息-外部写入")
    @ApiOperation(value="订单信息-外部写入", notes="订单信息-外部写入")
    @PostMapping(value = "/add2")
    public Result<?> add2(@RequestBody String json) {
        WmOmNoticeHPage wmOmNoticeHPage = JSONObject.parseObject(json).toJavaObject(WmOmNoticeHPage.class);
        WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
        BeanUtils.copyProperties(wmOmNoticeHPage, wmOmNoticeH);
        wmOmNoticeHService.saveMain1(wmOmNoticeH, wmOmNoticeHPage.getWmOmNoticeIList());
        WMSUserOrderPushData wmsUserOrderPushData = wmOmNoticeHPage.getWmsUserOrderPushData();
        return Result.ok("添加成功！");
    }


    /**
     *   添加
     *
     * @param rfidBuse
     * @return
     */
    @AutoLog(value = "称重信息-添加")
    @ApiOperation(value="称重信息-添加", notes="称重信息-添加")
    @PostMapping(value = "/add3")
    public Result<?> add3(@RequestBody RfidBuse rfidBuse) {
        rfidBuseService.save(rfidBuse);
        return Result.ok("添加成功！");
    }

    /**
     *  编辑
     *
     * @param wmOmNoticeHPage
     * @return
     */
    @AutoLog(value = "出库管理-修改状态")
    @ApiOperation(value="出库管理-编辑", notes="出库管理-修改状态")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WmOmNoticeHPage wmOmNoticeHPage) {
        WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
        BeanUtils.copyProperties(wmOmNoticeHPage, wmOmNoticeH);
        WmOmNoticeH wmOmNoticeHEntity = wmOmNoticeHService.getById(wmOmNoticeH.getId());
        if(wmOmNoticeHEntity==null) {
            return Result.error("未找到对应数据");
        }
        wmOmNoticeHService.updateMain(wmOmNoticeH, wmOmNoticeHPage.getWmOmNoticeIList());
        return Result.ok("编辑成功!");
    }


    @AutoLog(value = "CDS-回执写入")
    @ApiOperation(value="CDS回执-添加", notes="CDS回执-添加")
    @PostMapping(value = "/addCdsReceipt")
    public String addCdsReceipt(@RequestBody CDSReceiptInfo cdsReceiptInfo,HttpServletRequest request){
        JSONObject result = new JSONObject();
        result.put("success",false);result.put("message","");result.put("data","");
        log.info("CDS返回回执==========={}",JSONObject.toJSONString(cdsReceiptInfo));
        String sign = request.getHeader("sign");
        try {
            //验证签名信息
            String success = CDSUtils.cdsReceipt(cdsReceiptInfo, sign);
            if(!StringUtils.equals("success",success)){
                result.put("message",success);
                return result.toJSONString();
            }

        } catch (Exception e) {
           log.error("cds海关回执异常===",e);
            result.put("message","返回异常");
        }
        return result.toJSONString();
    }

    @PostMapping("/autoWeighing")
    public String autoWeighing(Map<String,String> params, @RequestBody String body){
        Map<String,Object> map = new HashMap<>(1024);
        log.info("自动称重返回参数====={}",body);
        map.put("flag","failure");map.put("data",false);map.put("message","");map.put("code","");
        try {
            if(StringUtils.isEmpty(body)){
                map.put("message","业务数据为空");
                map.put("code","S035");
                return XmlUtil.mapToXmlStr(map);
            }
            Map<String, Object> bodyMap = XmlUtil.xmlToMap(body);
            String trackingNumber = MapUtil.getStr(bodyMap, "tracking_number");
            List<String> deliverNums = new ArrayList<>();
            deliverNums.add(trackingNumber);
//            WMSResult result = wmOmQmIService.wmsDeliverGoodsToShop(deliverNums);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return XmlUtil.mapToXmlStr(map);
    }

    /**
     *  商城退款,取消订单接口
     * @param
     * @return
     */
    @AutoLog(value = "取消订单-修改状态")
    @ApiOperation(value="取消订单-取消", notes="取消订单-修改状态")
    @PostMapping(value = "/declCancel")
    public WMSResult declCancel(@RequestBody String src, HttpServletRequest request) {
        WMSResult result = new WMSResult();
        try {
            result = wmOmNoticeHService.declCancel(src);
        } catch (JeecgBootException e) {
            result.setError_code(500);
            result.setSuccess(false);
            result.setError_msg(e.getMessage());
        }
        return result;
    }

    /**
     *  wms推送库存到商城接口
     * @param
     * @return
     */
    @AutoLog(value = "wms推送库存到商城接口")
    @ApiOperation(value="wms推送库存到商城接口", notes="wms推送库存到商城接口")
    @PostMapping(value = "/stockQuery")
    public WMSResult stockQuery(@RequestBody WMSStockQueryDTO wmsStockQueryDTO,HttpServletRequest request) {
        return mdGoodsService.stockQuery(wmsStockQueryDTO);
    }

}
