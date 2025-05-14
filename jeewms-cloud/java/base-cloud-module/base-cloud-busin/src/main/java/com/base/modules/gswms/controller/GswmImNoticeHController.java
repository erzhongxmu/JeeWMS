package com.base.modules.gswms.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.vo.LoginUser;
import com.base.modules.jeewms.entity.WmImNoticeH;
import com.base.modules.jeewms.entity.WmImNoticeI;
import com.base.modules.jeewms.service.IWmImNoticeHService;
import com.base.modules.jeewms.service.IWmImNoticeIService;
import com.base.modules.jeewms.service.IWmInQmIService;
import com.base.modules.jobhandler.U8JiekouXxlJob;
import com.github.xiaoymin.knife4j.core.io.ResourceUtil;
import com.google.gson.Gson;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jeecg.common.system.base.controller.JeecgController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

;

/**
 * @Description: wm_im_notice_h
 * @Author: base-boot
 * @Date: 2021-05-21
 * @Version: V1.0
 */
@Api(tags = "进货管理")
@RestController
@RequestMapping("/gswms/wmImNoticeH")
@Slf4j
public class GswmImNoticeHController extends JeecgController<WmImNoticeH, IWmImNoticeHService> {
    @Autowired
    private IWmImNoticeHService wmImNoticeHService;
    @Autowired
    private IWmImNoticeIService wmImNoticeIService;
    @Autowired
    private IWmInQmIService wmInQmIService;
    @Autowired
    private U8JiekouXxlJob u8JiekouXxlJob;

    @AutoLog(value = "同步国声采购入库单")
    @ApiOperation(value = "同步国声采购入库单", notes = "同步国声采购入库单")
    @GetMapping(value = "/getGscgrk")
    public Result<?> getGscgrk(@RequestParam(name = "date", required = false) String date) throws Exception {
        LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        synchronized (this) {
//        this.getGscgrks();
            K3CloudApi api = new K3CloudApi();
//        String date1 = "2019-09-10";
            Date date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
            Date newDate2 = cn.hutool.core.date.DateUtil.offsetDay(date1, 1);
            String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
            //     FCREATEDATE >= '2019-09-10'   and FCREATEDATE <= '2019-09-11'
            Map<String, String> map = new HashMap<>(1024);
            map.put("FormId", "STK_InStock");
            map.put("FieldKeys", "FBILLNO,FSupplierId.FNumber,FSupplierId.FName,FSTOCKDEPTID.FNumber,FSTOCKDEPTID.FName,FSTOCKORGID.FNumber,FSTOCKORGID.FName," +
                    "FSTOCKERID.FName,FPURCHASERID.FName,FBILLTYPEID.FName," +
                    "FMaterialId.FNumber,FMaterialId.FName,F_PAEZ_BaseProperty1,FUOM,FBaseUnitID.FName,FMustQty,FRealQty,FLot,FNote,FContractlNo,FCREATEDATE");
            map.put("OrderString", "FCREATEDATE desc");
            map.put("FilterString", "FDocumentStatus = 'C' and FCREATEDATE >= '" + date1 + "'and FCREATEDATE < '" + date2 + "'");
            Gson gson = new Gson();
            String s = gson.toJson(map);
            List<List<Object>> datas1 = null;
            try {
                datas1 = api.executeBillQuery(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //处理数据
            List<WmImNoticeI> noticeIS = new ArrayList<>();
            List<WmImNoticeH> noticeHS = new ArrayList<>();
            if (datas1 != null) {
                for (List<Object> objects : datas1) {
                    //获取当前用户
//                    LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
                    //每一条都是明细
                    WmImNoticeI noticeI = new WmImNoticeI();
                    //商品编码
                    noticeI.setGoodsCode(String.valueOf(objects.get(10)));
                    //商品名称
                    noticeI.setGoodsName(String.valueOf(objects.get(11)));
                    //客户料号
//                    noticeI.setImNoticeItem(String.valueOf(objects.get(12)));
                    //规格
                    noticeI.setShpGuiGe(String.valueOf(objects.get(13)));
                    //基本单位
                    noticeI.setBaseUnit(String.valueOf(objects.get(14)));
                    noticeI.setGoodsUnit(String.valueOf(objects.get(14)));
                    //应收数量
                    noticeI.setMustqty(String.valueOf(objects.get(15)));
                    //实收数量
                    noticeI.setGoodsCount(String.valueOf(objects.get(16)));
                    //批号
                    noticeI.setGoodsBatch(String.valueOf(objects.get(17)));
                    //备注
                    noticeI.setRemark(String.valueOf(objects.get(18)));
                    //收料通知单号
                    noticeI.setContractlno(String.valueOf(objects.get(19)));
                    //单据编号
                    noticeI.setU8Dhid(String.valueOf(objects.get(0)));

                    noticeI.setCreateBy(sysUser.getUsername());
                    noticeI.setCreateName(sysUser.getRealname());
                    noticeIS.add(noticeI);

                    WmImNoticeH wmImNoticeH = new WmImNoticeH();
                    wmImNoticeH.setCreateBy(sysUser.getUsername());
                    wmImNoticeH.setCreateName(sysUser.getRealname());
                    //单据编号
                    wmImNoticeH.setU8ReturnCode(String.valueOf(objects.get(0)));
                    //供应商编码
                    wmImNoticeH.setSupCode(String.valueOf(objects.get(1)));
                    //供应商名称
                    wmImNoticeH.setSupName(String.valueOf(objects.get(2)));
                    //仓库代码
                    wmImNoticeH.setStoreCode(String.valueOf(objects.get(3)));
                    //仓库名称
                    wmImNoticeH.setStoreName(String.valueOf(objects.get(4)));
                    //收货组织内码
                    wmImNoticeH.setCusCode(String.valueOf(objects.get(5)));
                    //收货组织名称
                    wmImNoticeH.setCusName(String.valueOf(objects.get(6)));
                    //仓管员
                    wmImNoticeH.setFstockername(String.valueOf(objects.get(7)));
                    //采购员
                    wmImNoticeH.setPurchasename(String.valueOf(objects.get(8)));
                    wmImNoticeH.setAssociation(String.valueOf(objects.get(20)));

                    //预计到货时间默认今天
                    wmImNoticeH.setImData(new Date());
                    //u8制单人
//                    noticeI.setU8Cgid(String.valueOf(objects.get(20)));
                    //入库类型
                    wmImNoticeH.setOrderType("06");
                    wmImNoticeH.setOrderTypes(String.valueOf(objects.get(9)));
                    noticeHS.add(wmImNoticeH);
                }
                List<WmImNoticeH> distinctClass = noticeHS.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(o -> o.getU8ReturnCode()))), ArrayList::new));

                for (WmImNoticeH aClass : distinctClass) {
                    List<WmImNoticeI> noticeIS1 = new ArrayList<>();
                    for (WmImNoticeI noticeI : noticeIS) {
                        if (aClass.getU8ReturnCode().equals(noticeI.getU8Dhid())) {
                            noticeIS1.add(noticeI);
                        }
                    }
                    WmImNoticeH noticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getU8ReturnCode, aClass.getU8ReturnCode()).one();
                    if (noticeH != null) {
                        continue;
                    } else {
                        wmImNoticeHService.saveMain(aClass, noticeIS1);
                    }
                }
            }
            return Result.ok("同步成功！");
        }
    }

    /**
     * 分页列表查询
     *
     * @param wmImNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "客户进货、预约进货-分页列表查询", notes = "客户进货、预约进货-分页列表查询")
    @GetMapping(value = "/cusList")
    public Result<?> queryCusPageList(WmImNoticeH wmImNoticeH,
                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      HttpServletRequest req) {
        IPage<WmImNoticeH> wmImNoticeHIPage = wmImNoticeHService.selectCusList(wmImNoticeH, pageNo, pageSize, req);
        List<WmImNoticeH> records = wmImNoticeHIPage.getRecords();
        for (WmImNoticeH record : records) {
            List<WmImNoticeI> list = wmImNoticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId, record.getNoticeId()).list();
            record.setWmImNoticeIS(list);
        }
        return Result.ok(records);
    }

    @AutoLog(value = "同步国声成品入库单")
    @ApiOperation(value = "同步国声成品入库单", notes = "同步国声成品入库单")
    @GetMapping(value = "/getGscpruku")
    public Result<?> getGscpruku(@RequestParam(name = "date", required = false) String date) throws Exception {
        LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        synchronized (this) {
//        this.getGscgrks();
            K3CloudApi api = new K3CloudApi();
//        String date1 = "2019-09-10";
            Date date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
            Date newDate2 = cn.hutool.core.date.DateUtil.offsetDay(date1, 1);
            String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
            Map<String, String> map = new HashMap<>(1024);
            map.put("FormId", "PRD_INSTOCK");
            map.put("FieldKeys", "FBILLNO,FMOBILLNO,FMaterialId.FNumber,FMaterialId.FName,FBaseUnitID.FName,FBILLTYPE," +
                    "FMUSTQTY,FREALQTY,FSTOCKID.FNumber,FSTOCKID.FName," +
                    "FCREATORID.FName,FPRDORGID.FNumber,FPRDORGID.FName,FSTOCKERID.FName,FLot");
            map.put("OrderString", "FCREATEDATE desc");
            map.put("FilterString", "FDocumentStatus = 'C' and FCREATEDATE >= '" + date1 + "'and FCREATEDATE < '" + date2 + "'");
            Gson gson = new Gson();
            String s = gson.toJson(map);
            List<List<Object>> datas1 = null;
            try {
                datas1 = api.executeBillQuery(s);
                System.out.println(datas1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //处理数据
            List<WmImNoticeI> noticeIS = new ArrayList<>();
            List<WmImNoticeH> noticeHS = new ArrayList<>();
            if (datas1 != null) {
                for (List<Object> objects : datas1) {
                    //每一条都是明细
                    WmImNoticeI noticeI = new WmImNoticeI();
                    //商品编码
                    noticeI.setGoodsCode(String.valueOf(objects.get(2)));
                    //商品名称
                    noticeI.setGoodsName(String.valueOf(objects.get(3)));
                    //基本单位
                    noticeI.setBaseUnit(String.valueOf(objects.get(4)));
                    noticeI.setGoodsUnit(String.valueOf(objects.get(4)));
                    //应收数量
                    noticeI.setMustqty(String.valueOf(objects.get(6)));
                    //实收数量
                    noticeI.setGoodsCount(String.valueOf(objects.get(7)));
                    //单据编号
                    noticeI.setU8Dhid(String.valueOf(objects.get(0)));
                    //生产订单编号
                    noticeI.setProcode(String.valueOf(objects.get(1)));
                    //批号
                    noticeI.setGoodsBatch(String.valueOf(objects.get(14)));
                    //单据创建人
                    noticeI.setUcreatename(String.valueOf(objects.get(10)));
                    noticeI.setCreateBy(sysUser.getUsername());
                    noticeI.setCreateName(sysUser.getRealname());
                    noticeIS.add(noticeI);

                    WmImNoticeH wmImNoticeH = new WmImNoticeH();
                    //单据编号
                    wmImNoticeH.setU8ReturnCode(String.valueOf(objects.get(0)));
                    //仓库代码
                    wmImNoticeH.setStoreCode(String.valueOf(objects.get(8)));
                    //仓库名称
                    wmImNoticeH.setStoreName(String.valueOf(objects.get(9)));
                    //收货组织内码
                    wmImNoticeH.setCusCode(String.valueOf(objects.get(11)));
                    //收货组织名称
                    wmImNoticeH.setCusName(String.valueOf(objects.get(12)));
                    //仓管员
                    wmImNoticeH.setFstockername(String.valueOf(objects.get(13)));
                    //入库类型
                    wmImNoticeH.setOrderType("06");
//                    wmImNoticeH.setOrderTypes(String.valueOf(objects.get(5)));
                    wmImNoticeH.setOrderTypes("标准生产入库单");

                    wmImNoticeH.setCreateBy(sysUser.getUsername());
                    wmImNoticeH.setCreateName(sysUser.getRealname());
                    //预计到货时间默认今天
                    wmImNoticeH.setImData(new Date());
                    noticeHS.add(wmImNoticeH);
                }
                List<WmImNoticeH> distinctClass = noticeHS.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(o -> o.getU8ReturnCode()))), ArrayList::new));

                for (WmImNoticeH aClass : distinctClass) {
                    List<WmImNoticeI> noticeIS1 = new ArrayList<>();
                    for (WmImNoticeI noticeI : noticeIS) {
                        if (aClass.getU8ReturnCode().equals(noticeI.getU8Dhid())) {
                            noticeIS1.add(noticeI);
                        }
                    }
                    WmImNoticeH noticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getU8ReturnCode, aClass.getU8ReturnCode()).one();
                    if (noticeH != null) {
                        continue;
                    } else {
                        wmImNoticeHService.saveMain(aClass, noticeIS1);
                    }
                }
            }
            return Result.ok("同步成功！");
        }
    }

    @AutoLog(value = "同步国声生产退料单")
    @ApiOperation(value = "同步国声生产退料单", notes = "同步国声生产退料单")
    @GetMapping(value = "/getGssctl")
    public Result<?> getGssctl(@RequestParam(name = "date", required = false) String date) throws Exception {
        LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        synchronized (this) {

//        this.getGscgrks();
            K3CloudApi api = new K3CloudApi();
//        String date1 = "2019-09-10";
            Date date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
            Date newDate2 = cn.hutool.core.date.DateUtil.offsetDay(date1, 1);
            String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
            Map<String, String> map = new HashMap<>(1024);
            map.put("FormId", "PRD_ReturnMtrl");
            map.put("FieldKeys", "FBILLNO,FMOBILLNO,FMaterialId.FNumber,FMaterialId.FName,FBaseUnitID.FName,FSTOCKSTATUSID,FAPPQTY,FQTY,FSTOCKID.FNumber,FSTOCKID.FName," +
                    "FRETURNREASON,FRETURNERID.FName,FBILLTYPE,FSTOCKERID.FName,FStockOrgId.FNumber,FStockOrgId.FName,FLot");
            map.put("OrderString", "FCREATEDATE desc");
            map.put("FilterString", "FDocumentStatus = 'C' and FCREATEDATE >= '" + date1 + "'and FCREATEDATE < '" + date2 + "'");
            Gson gson = new Gson();
            String s = gson.toJson(map);
            List<List<Object>> datas1 = null;
            try {
                datas1 = api.executeBillQuery(s);
                System.out.println(datas1 + "--------");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //处理数据
            List<WmImNoticeI> noticeIS = new ArrayList<>();
            List<WmImNoticeH> noticeHS = new ArrayList<>();
            if (datas1 != null) {
                for (List<Object> objects : datas1) {
                    //每一条都是明细
                    WmImNoticeI noticeI = new WmImNoticeI();
                    //商品编码
                    noticeI.setGoodsCode(String.valueOf(objects.get(2)));
                    //商品名称
                    noticeI.setGoodsName(String.valueOf(objects.get(3)));
                    //基本单位
                    noticeI.setBaseUnit(String.valueOf(objects.get(4)));
                    noticeI.setGoodsUnit(String.valueOf(objects.get(4)));
                    //实收数量
                    noticeI.setGoodsCount(String.valueOf(objects.get(7)));
                    //单据编号
                    noticeI.setU8Dhid(String.valueOf(objects.get(0)));
                    //生产订单编号
                    noticeI.setProcode(String.valueOf(objects.get(1)));
                    //单据创建人
                    noticeI.setUcreatename(String.valueOf(objects.get(11)));
                    //库存状态
                    noticeI.setKucunstatus(String.valueOf(objects.get(5)));
                    //申请数量
                    noticeI.setShenqingsl(String.valueOf(objects.get(6)));
                    //退料原因
                    noticeI.setImBeizhu(String.valueOf(objects.get(10)));
                    //批次
                    noticeI.setGoodsBatch(String.valueOf(objects.get(16)));
                    //退料时间
//                    noticeI.setRettime(String.valueOf(objects.get(14)));

                    noticeI.setCreateBy(sysUser.getUsername());
                    noticeI.setCreateName(sysUser.getRealname());
                    noticeIS.add(noticeI);

                    WmImNoticeH wmImNoticeH = new WmImNoticeH();
                    //单据编号
                    wmImNoticeH.setU8ReturnCode(String.valueOf(objects.get(0)));
                    //仓库代码
                    wmImNoticeH.setStoreCode(String.valueOf(objects.get(8)));
                    //仓库名称
                    wmImNoticeH.setStoreName(String.valueOf(objects.get(9)));
                    //仓管员
                    wmImNoticeH.setFstockername(String.valueOf(objects.get(13)));
                    wmImNoticeH.setCusCode(String.valueOf(objects.get(14)));
                    wmImNoticeH.setCusName(String.valueOf(objects.get(15)));
                    //入库类型
                    wmImNoticeH.setOrderType("06");
//                    wmImNoticeH.setOrderTypes(String.valueOf(objects.get(12)));
                    wmImNoticeH.setOrderTypes("标准生产退料单");

                    wmImNoticeH.setCreateBy(sysUser.getUsername());
                    wmImNoticeH.setCreateName(sysUser.getRealname());
                    //预计到货时间默认今天
                    wmImNoticeH.setImData(new Date());
                    noticeHS.add(wmImNoticeH);
                }
                List<WmImNoticeH> distinctClass = noticeHS.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(o -> o.getU8ReturnCode()))), ArrayList::new));

                for (WmImNoticeH aClass : distinctClass) {
                    List<WmImNoticeI> noticeIS1 = new ArrayList<>();
                    for (WmImNoticeI noticeI : noticeIS) {
                        if (aClass.getU8ReturnCode().equals(noticeI.getU8Dhid())) {
                            noticeIS1.add(noticeI);
                        }
                    }
                    WmImNoticeH noticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getU8ReturnCode, aClass.getU8ReturnCode()).one();
                    if (noticeH != null) {
                        continue;
                    } else {
                        wmImNoticeHService.saveMain(aClass, noticeIS1);
                    }
                }
            }
            return Result.ok("同步成功！");
        }
    }

    /*@Test
    public void getGscgrks() throws Exception {
        synchronized (this) {

//        this.getGscgrks();
            K3CloudApi api = new K3CloudApi();
        String date1 = "2019-09-10";
            Date date = cn.hutool.core.date.DateUtil.parse(date1, "yyyy-MM-dd");
            Date newDate2 = cn.hutool.core.date.DateUtil.offsetDay(date, 1);
            String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
            Map<String, String> map = new HashMap<>(1024);
            map.put("FormId", "PRD_INSTOCK");
            map.put("FieldKeys", "FBILLNO,FMOBILLNO,FMaterialId.FNumber,FMaterialId.FName,FBaseUnitID.FName,FBILLTYPEID.FName,FMUSTQTY,FREALQTY,FSTOCKID.FNumber,FSTOCKID.FName,FCREATORID.FName,FPRDORGID.FNumber,FPRDORGID.FName,FSTOCKERID.FName");
            map.put("OrderString", "FCREATEDATE desc");
            map.put("FilterString", "FDocumentStatus = 'C' and FCREATEDATE >= '" + date + "'and FCREATEDATE <= '" + date2 + "'");
            Gson gson = new Gson();
            String s = gson.toJson(map);
            List<List<Object>> datas1 = null;
            try {
                datas1 = api.executeBillQuery(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(datas1);
            //处理数据
            List<WmImNoticeI> noticeIS = new ArrayList<>();
            List<WmImNoticeH> noticeHS = new ArrayList<>();
//            if (datas1 != null) {
//                for (List<Object> objects : datas1) {
//                    //每一条都是明细
//                    WmImNoticeI noticeI = new WmImNoticeI();
//                    //商品编码
//                    noticeI.setGoodsCode(String.valueOf(objects.get(2)));
//                    //商品名称
//                    noticeI.setGoodsName(String.valueOf(objects.get(3)));
//                    //基本单位
//                    noticeI.setBaseUnit(String.valueOf(objects.get(4)));
//                    //应收数量
//                    noticeI.setMustqty(String.valueOf(objects.get(6)));
//                    //实收数量
//                    noticeI.setGoodsCount(String.valueOf(objects.get(7)));
//                    //单据编号
//                    noticeI.setU8Dhid(String.valueOf(objects.get(0)));
//                    //生产订单编号
//                    noticeI.setProcode(String.valueOf(objects.get(1)));
//                    //
//                    noticeI.setUcreatename(String.valueOf(objects.get(10)));
//                    noticeIS.add(noticeI);
//
//                    WmImNoticeH wmImNoticeH = new WmImNoticeH();
//                    //单据编号
//                    wmImNoticeH.setU8ReturnCode(String.valueOf(objects.get(0)));
//                    //仓库代码
//                    wmImNoticeH.setStoreCode(String.valueOf(objects.get(8)));
//                    //仓库名称
//                    wmImNoticeH.setStoreName(String.valueOf(objects.get(9)));
//                    //收货组织内码
//                    wmImNoticeH.setCusCode(String.valueOf(objects.get(11)));
//                    //收货组织名称
//                    wmImNoticeH.setCusName(String.valueOf(objects.get(12)));
//                    //仓管员
//                    wmImNoticeH.setFstockername(String.valueOf(objects.get(13)));
//                    //入库类型
//                    wmImNoticeH.setOrderType("06");
//                    wmImNoticeH.setOrderTypes(String.valueOf(objects.get(5)));
//                    noticeHS.add(wmImNoticeH);
//                }
//                List<WmImNoticeH> distinctClass = noticeHS.stream()
//                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
//                                new TreeSet<>(Comparator.comparing(o -> o.getU8ReturnCode()))), ArrayList::new));
//
//                for (WmImNoticeH aClass : distinctClass) {
//                    List<WmImNoticeI> noticeIS1 = new ArrayList<>();
//                    for (WmImNoticeI noticeI : noticeIS) {
//                        if (aClass.getU8ReturnCode().equals(noticeI.getU8Dhid())) {
//                            noticeIS1.add(noticeI);
//                        }
//                    }
//                    WmImNoticeH noticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getU8ReturnCode, aClass.getU8ReturnCode()).one();
//                    if (noticeH != null) {
//                        continue;
//                    } else {
//                        wmImNoticeHService.saveMain(aClass, noticeIS1);
//                    }
//                }
//            }
        }
    }*/


    @AutoLog(value = "同步其他入库单")
    @ApiOperation(value = "同步其他入库单", notes = "同步其他入库单")
    @GetMapping(value = "/getGsqtrk")
    public Result<?> getGsqtrk(@RequestParam(name = "date", required = false) String date) throws Exception {
        LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        synchronized (this) {
            K3CloudApi api = new K3CloudApi();
            Date date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
            Date newDate2 = cn.hutool.core.date.DateUtil.offsetDay(date1, 1);
            String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
            Map<String, String> map = new HashMap<>(1024);
            map.put("FormId", "STK_MISCELLANEOUS");
            map.put("FieldKeys", "FBillNo,FSUPPLIERID.FNumber,FSUPPLIERID.Fname,FNOTE,FCreateDate,FMATERIALID,FMATERIALNAME,FUnitID.Fname,FLOT,FQty,FSTOCKID.FNumber,FSTOCKID.Fname");
            map.put("OrderString", "FCREATEDATE desc");
            map.put("FilterString", "FDocumentStatus = 'C' and FCREATEDATE >= '" + date1 + "'and FCREATEDATE < '" + date2 + "'");
            Gson gson = new Gson();
            String s = gson.toJson(map);
            List<List<Object>> datas1 = null;
            try {
                datas1 = api.executeBillQuery(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //处理数据
            List<WmImNoticeI> noticeIS = new ArrayList<>();
            List<WmImNoticeH> noticeHS = new ArrayList<>();
            if (datas1 != null) {
                for (List<Object> objects : datas1) {
                    //每一条都是明细
                    WmImNoticeI noticeI = new WmImNoticeI();
                    //商品编码
                    noticeI.setGoodsCode(String.valueOf(objects.get(5)));
                    //商品名称
                    noticeI.setGoodsName(String.valueOf(objects.get(6)));

                    //基本单位
                    noticeI.setBaseUnit(String.valueOf(objects.get(7)));
                    noticeI.setGoodsUnit(String.valueOf(objects.get(7)));
                    //应收数量
                    noticeI.setMustqty(String.valueOf(objects.get(9)));
                    //实收数量
                    noticeI.setGoodsCount(String.valueOf(objects.get(9)));
                    //批号
                    noticeI.setGoodsBatch(String.valueOf(objects.get(8)));
                    //单据编号
                    noticeI.setU8Dhid(String.valueOf(objects.get(0)));

                    noticeI.setCreateBy(sysUser.getUsername());
                    noticeI.setCreateName(sysUser.getRealname());
                    noticeIS.add(noticeI);

                    WmImNoticeH wmImNoticeH = new WmImNoticeH();
                    wmImNoticeH.setCreateBy(sysUser.getUsername());
                    wmImNoticeH.setCreateName(sysUser.getRealname());
                    //单据编号
                    wmImNoticeH.setU8ReturnCode(String.valueOf(objects.get(0)));
                    //供应商编码
                    wmImNoticeH.setSupCode(String.valueOf(objects.get(1)));
                    //供应商名称
                    wmImNoticeH.setSupName(String.valueOf(objects.get(2)));

                    //供应商编码
                    wmImNoticeH.setCusCode(String.valueOf(objects.get(1)));
                    //供应商名称
                    wmImNoticeH.setCusName(String.valueOf(objects.get(2)));

                    //仓库代码
                    wmImNoticeH.setStoreCode(String.valueOf(objects.get(10)));
                    //仓库名称
                    wmImNoticeH.setStoreName(String.valueOf(objects.get(11)));

                    //预计到货时间默认今天
                    wmImNoticeH.setImData(new Date());
                    //u8制单人
//                    noticeI.setU8Cgid(String.valueOf(objects.get(20)));
                    //入库类型
                    wmImNoticeH.setOrderType("09");
                    wmImNoticeH.setOrderTypes(String.valueOf(objects.get(3)));
                    noticeHS.add(wmImNoticeH);
                }
                List<WmImNoticeH> distinctClass = noticeHS.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(o -> o.getU8ReturnCode()))), ArrayList::new));

                for (WmImNoticeH aClass : distinctClass) {
                    List<WmImNoticeI> noticeIS1 = new ArrayList<>();
                    for (WmImNoticeI noticeI : noticeIS) {
                        if (aClass.getU8ReturnCode().equals(noticeI.getU8Dhid())) {
                            noticeIS1.add(noticeI);
                        }
                    }
                    WmImNoticeH noticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getU8ReturnCode, aClass.getU8ReturnCode()).one();
                    if (noticeH != null) {
                        continue;
                    } else {
                        wmImNoticeHService.saveMain(aClass, noticeIS1);
                    }
                }
            }
            return Result.ok("同步成功！");
        }
    }
}
