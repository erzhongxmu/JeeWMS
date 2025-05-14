package com.huayi.k3cloud;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;

import com.google.gson.Gson;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.*;
import java.util.stream.Collectors;


@Api(tags = "k3cloud")
@RestController
@RequestMapping("/k3cloud/api")
@Slf4j
public class k3CloudController {

    public static final String begindate = "2023-05-24";

    @GetMapping(value = "/getlingliaodan")
    public List<List<Object>> getlingliaodan(@RequestParam(name = "date", required = false) String date) throws Exception {

        K3CloudApi api = new K3CloudApi();
        String datbegin = begindate;
        Date datebeg = com.huayi.utils.DateUtils.parseDate(datbegin, "yyyy-MM-dd");
        Date date1 = DateUtil.parse(date, "yyyy-MM-dd");
        if (date1.before(datebeg)) {
            date1 = datebeg;
        }
        Date newDate2 = DateUtil.date();
        String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
        //     FCREATEDATE >= '+  2019-09-10 + '   and FCREATEDATE <= '2019-09-11'
        Map<String, String> map = new HashMap<>(1024);
        map.put("FormId", "PRD_PickMtrl");
        map.put("FieldKeys", "FBILLNO,FPPBOMBILLNO,FMOBILLNO,FSTOCKORGID.FNumber,FSTOCKORGID.FName,FCREATEDATE,FBASEUNITID.FName,FACTUALQTY,FCREATORID.FName,FPICKERID.FName,FSTOCKERID.FName," +
                "FAPPROVEDATE,FBILLTYPE.FName,FMaterialId.FNumber,FMaterialId.FName,FLOT,");
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
        return datas1;

    }


    @ApiOperation(value = "同步其他出库", notes = "同步其他出库")
    @GetMapping(value = "/getqtck")
    public List<List<Object>> getqtck(@RequestParam(name = "date", required = false) String date) throws Exception {

        K3CloudApi api = new K3CloudApi();
        String datbegin = begindate;
        Date datebeg = com.huayi.utils.DateUtils.parseDate(datbegin, "yyyy-MM-dd");
        Date date1 = DateUtil.parse(date, "yyyy-MM-dd");
        if (date1.before(datebeg)) {
            date1 = datebeg;
        }
        Date newDate2 = DateUtil.date();
        String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
        //     FCREATEDATE >= '+  2019-09-10 + '   and FCREATEDATE <= '2019-09-11'
        Map<String, String> map = new HashMap<>(1024);
        map.put("FormId", "STK_MisDelivery");
        map.put("FieldKeys",
                "FBILLNO," +
                        "FID ," +
                        "FDeptId," +
                        "FSTOCKORGID.FNumber," +
                        "FSTOCKORGID.FName," +
                        "FCREATEDATE," +
                        "FBASEUNITID.FName," +
                        "FQty," +
                        "FCREATORID.FName," +
                        "FPICKERID.FName," +
                        "FSTOCKERID.FName," +
                        "FAPPROVEDATE," +
//                            "FBILLTYPE.FName," +
                        "FMaterialId.FNumber," +
                        "FMaterialId.FName," +
                        "FLOT,");
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
        return datas1;
    }

    @ApiOperation(value = "同步发货通知单", notes = "同步发货通知单")
    @GetMapping(value = "/getfahuodan")
    public List<List<Object>> getfahuodan(@RequestParam(name = "date", required = false) String date) throws Exception {
        K3CloudApi api = new K3CloudApi();
        String datbegin = begindate;
        Date datebeg = com.huayi.utils.DateUtils.parseDate(datbegin, "yyyy-MM-dd");
        Date date1 = DateUtil.parse(date, "yyyy-MM-dd");
        if (date1.before(datebeg)) {
            date1 = datebeg;
        }
        Date newDate2 = DateUtil.date();
        String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
        //     FCREATEDATE >= '+  2019-09-10 + '   and FCREATEDATE <= '2019-09-11'
        Map<String, String> map = new HashMap<>(1024);
        map.put("FormId", "SAL_DELIVERYNOTICE");
        map.put("FieldKeys", "FCUSTOMERID.FNumber,FCUSTOMERID.FName,FDELIVERYORGID.FNumber,FDELIVERYORGID.FName,FDELIVERYDEPTID.FNumber,FDELIVERYDEPTID.FName,FBILLNO," +
                "FMaterialId.FNumber,FMaterialId.FName,FUNITID.FName,FQTY,FDELIVERYDATE,FNOTE,FCREATORID.FName,FAPPROVERID.FName,FBILLTYPEID.FName,FCreateDate");
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
        return datas1;
    }


    @ApiOperation(value = "同步商品管理", notes = "同步商品管理")
    @GetMapping(value = "/getTbmdgoods")
    public List<List<Object>> getTbmdgoods(@RequestParam(name = "date", required = false) String date) throws Exception {

        K3CloudApi api = new K3CloudApi();
        Date date1 = null;
        String date2 = "";
        if (!org.thymeleaf.util.StringUtils.isEmpty(date)) {
            date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
            Date newDate2 = cn.hutool.core.date.DateUtil.date();
            date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
        }
        System.out.println("时间" + date1);
        //     FCREATEDATE >= '2019-09-10'   and FCREATEDATE <= '2019-09-11'
        Map<String, String> map = new HashMap<>(1024);
        //id,货主编码(没有)，货主，商品名称，英文名称，日文名称(没有)，商品编码，商品分类(没有)，商品规格，产品属性(没有)，存放温层(没有)，码盘单层数量(没有)，码盘层高(没有)，计费商品类(没有)，商品条码，保质期，允许天数(没有)，
        // 单位，拆零单位(没有)，体积，重量(净重)，价格(采购单价)，长，宽，高，停用(没有)
        map.put("FormId", "BD_MATERIAL");
        map.put("FieldKeys", "FMATERIALID," +
//							    "F_PAEZ_Text," +
                        "FName," +
                        "FNameEn," +
                        "FNumber," +
                        "FSpecification," +
                        "FBARCODE," +
                        "FExpPeriod," +
                        "FVOLUME," +
                        "FNETWEIGHT," +
                        "FPurPrice_CMK," +
                        "FLENGTH," +
                        "FWIDTH," +
                        "FHEIGHT," +
                        "FBaseUnitId.FName," +
                        "FBaseUnitId.Fname," +
                        "FMNEMONICCODE"

        );
        map.put("OrderString", "FCREATEDATE desc");
        map.put("Limit", "0");
        if (!org.thymeleaf.util.StringUtils.isEmpty(date)) {
            map.put("FilterString", "FDocumentStatus = 'C' and FCREATEDATE >= '" + date1 + "'and FCREATEDATE < '" + date2 + "'");
        } else {
            map.put("FilterString", "FDocumentStatus = 'C'");
        }
        Gson gson = new Gson();
        String s = gson.toJson(map);
        List<List<Object>> datas1 = null;
        try {
            datas1 = api.executeBillQuery(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //处理数据

        return datas1;
    }


    @ApiOperation(value = "同步生产订单", notes = "同步生产订单")
    @GetMapping(value = "/getscdd")
    public List<List<Object>> getscdd(@RequestParam(name = "date", required = false) String date) throws Exception {
        K3CloudApi api = new K3CloudApi();
        String datbegin = begindate;
        Date datebeg = com.huayi.utils.DateUtils.parseDate(datbegin, "yyyy-MM-dd");
        Date date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
        if (date1.before(datebeg)) {
            date1 = datebeg;
        }
        Date newDate2 = cn.hutool.core.date.DateUtil.date();
        String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
        Map<String, String> map = new HashMap<>(1024);
        map.put("FormId", "PRD_MO");//FMATERIALID
        map.put("FieldKeys",
                "FBillNo," +
                        "FPlanFinishDate," +
                        "FPlanStartDate," +

                        "FCreateDate," +
                        "FMaterialId.FNumber," +
                        "FMATERIALNAME," +
                        "FSpecification," +
                        "FUnitID.Fname," +
                        "FProductType," +
                        "FLOT," +
                        "FQty," +
                        "FPrdOrgId.Fname," +

                        "F_WMCO_Assistant," +
                        "FDescription," +
                        "FWorkShopID0.Fname," +
                        "FBillType");
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
        return datas1;
    }


    @ApiOperation(value = "同步采购入库单", notes = "同步采购入库单")
    @GetMapping(value = "/getcgrk")
    public List<List<Object>> getcgrk(@RequestParam(name = "date", required = false) String date) throws Exception {

//        this.getGscgrks();
        K3CloudApi api = new K3CloudApi();
        String datbegin = begindate;
        Date datebeg = com.huayi.utils.DateUtils.parseDate(datbegin, "yyyy-MM-dd");
        Date date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
        if (date1.before(datebeg)) {
            date1 = datebeg;
        }

        Date newDate2 = cn.hutool.core.date.DateUtil.date();
        String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
        //     FCREATEDATE >= '2019-09-10'   and FCREATEDATE <= '2019-09-11'
        Map<String, String> map = new HashMap<>(1024);
        map.put("FormId", "STK_InStock");
        map.put("FieldKeys", "FBILLNO," +
                "FSupplierId.FNumber," +
                "FSupplierId.FName," +
                "FSTOCKDEPTID.FNumber," +
                "FSTOCKDEPTID.FName," +
                "FSTOCKORGID.FNumber," +
                "FSTOCKORGID.FName," +
                "FSTOCKERID.FName," +
                "FPURCHASERID.FName," +
                "FBILLTYPEID.FName," +
                "FMaterialId.FNumber," +
                "FMaterialId.FName," +
//                    "F_PAEZ_BaseProperty1," +
                "FUOM," +
                "FBaseUnitID.FName," +
                "FMustQty," +
                "FRealQty," +
                "FLot," +
                "FNote," +
                "FContractlNo," +
                "FApproveDate," +
                "FCreateDate," +
//                    "F_TPS_BaseProperty," +
                "FOwnerIdHead.FNumber," +
                "FOwnerIdHead.Fname");

        map.put("OrderString", "FCREATEDATE desc");
        map.put("FilterString", "FDocumentStatus = 'C' and FCREATEDATE >= '" + date1 + "'and FCREATEDATE < '" + date2 + "'");
        Gson gson = new Gson();
        String s = gson.toJson(map);
        List<List<Object>> datas1 = null;
        try {
            datas1 = api.executeBillQuery(s);
            System.out.println("api*******" + datas1.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return datas1;

    }


    @ApiOperation(value = "同步成品入库单", notes = "同步成品入库单")
    @GetMapping(value = "/getcpruku")
    public List<List<Object>> getcpruku(@RequestParam(name = "date", required = false) String date) throws Exception {

        K3CloudApi api = new K3CloudApi();

        String datbegin = begindate;
        Date datebeg = com.huayi.utils.DateUtils.parseDate(datbegin, "yyyy-MM-dd");
        Date date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
        if (date1.before(datebeg)) {
            date1 = datebeg;
        }
        Date newDate2 = cn.hutool.core.date.DateUtil.date();
        String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
        Map<String, String> map = new HashMap<>(1024);
        map.put("FormId", "PRD_INSTOCK");
        map.put("FieldKeys", "FBILLNO,FMOBILLNO,FMaterialId.FNumber,FMaterialId.FName,FBaseUnitID.FName,FBILLTYPE," +
                "FMUSTQTY,FREALQTY,FSTOCKID.FNumber,FSTOCKID.FName," +
                "FCREATORID.FName,FPRDORGID.FNumber,FPRDORGID.FName,FSTOCKERID.FName,FLot,FApproveDate,FCreateDate");
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

        return datas1;

    }


    @ApiOperation(value = "同步生产退料单", notes = "同步生产退料单")
    @GetMapping(value = "/getsctl")
    public List<List<Object>> getsctl(@RequestParam(name = "date", required = false) String date) throws Exception {


//        this.getGscgrks();
        K3CloudApi api = new K3CloudApi();
//        String date1 = "2019-09-10";
        String datbegin = begindate;
        Date datebeg = com.huayi.utils.DateUtils.parseDate(datbegin, "yyyy-MM-dd");
        Date date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
        if (date1.before(datebeg)) {
            date1 = datebeg;
        }
        Date newDate2 = cn.hutool.core.date.DateUtil.date();
        String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
        Map<String, String> map = new HashMap<>(1024);
        map.put("FormId", "PRD_ReturnMtrl");
        map.put("FieldKeys", "FBILLNO,FMOBILLNO,FMaterialId.FNumber,FMaterialId.FName,FBaseUnitID.FName,FSTOCKSTATUSID,FAPPQTY,FQTY,FSTOCKID.FNumber,FSTOCKID.FName," +
                "FRETURNREASON,FRETURNERID.FName,FBILLTYPE,FSTOCKERID.FName,FStockOrgId.FNumber,FStockOrgId.FName,FApproveDate,FDate,FCreateDate");//,F_TPS_BaseProperty
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

        return datas1;

    }


    @ApiOperation(value = "同步其他入库单", notes = "同步其他入库单")
    @GetMapping(value = "/getqtrk")
    public List<List<Object>> getqtrk(@RequestParam(name = "date", required = false) String date) throws Exception {

        K3CloudApi api = new K3CloudApi();
        String datbegin = begindate;
        Date datebeg = com.huayi.utils.DateUtils.parseDate(datbegin, "yyyy-MM-dd");
        Date date1 = cn.hutool.core.date.DateUtil.parse(date, "yyyy-MM-dd");
        if (date1.before(datebeg)) {
            date1 = datebeg;
        }
        Date newDate2 = cn.hutool.core.date.DateUtil.date();
        String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
        Map<String, String> map = new HashMap<>(1024);
        map.put("FormId", "STK_MISCELLANEOUS");//FMATERIALID
        map.put("FieldKeys", "FBillNo,FSUPPLIERID.FNumber,FSUPPLIERID.Fname,FNOTE,FCreateDate,FMaterialId.FNumber,FMATERIALNAME,FUnitID.Fname,FLOT,FQty,FSTOCKID.FNumber,FSTOCKID.Fname");
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
        return datas1;

    }
}
