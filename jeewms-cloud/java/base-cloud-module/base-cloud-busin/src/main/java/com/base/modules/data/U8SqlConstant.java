package com.base.modules.data;

public class U8SqlConstant {

    public static  String   DEMO = "";
    //人员hr_hi_person
    public static  String   HRHIPERSON = "select * from hr_hi_person a";

    //部门信息
    public static  String   DEPARTMENT = "select a.cDepCode,a.cDepName,a.iDepGrade,a.iDepOrder from department a ORDER BY a.cDepCode";

    //获取U8供应商信息
    public static  String   VENDOR = "SELECT a.cVenCode,a.cVenName,a.dVenCreateDatetime,cVenAddress,a.cVenPerson,a.cVenHand,a.cMemo FROM Vendor a ORDER BY a.dVenCreateDatetime DESC";

    //获取U8生产订单信息
    public static  String   MOMORDER = "SELECT a.MoId,a.CreateDate,a.MoCode FROM mom_order a ORDER BY a.CreateTime DESC";
    //获取U8生产订单信息(带参数)
    public static  String   MOMORDER1 = "SELECT a.MoId,a.CreateDate,a.MoCode FROM mom_order a WHERE Convert(nvarchar,a.CreateTime,120) like concat(?,'%')";
//    public static  String   MOMORDER1 = "SELECT a.MoId,a.CreateDate,a.MoCode FROM mom_order a WHERE a.MoCode = 'SC2021110054'";
    //获取U8生产订单子表信息
//    public static  String   MOMORDERDETAIL = "SELECT a.MoDId,a.InvCode,a.Qty,a.MoLotCode,a.Remark from mom_orderdetail a where a.Status='3' AND a.MoId = ? ";
    public static  String   MOMORDERDETAIL = "SELECT a.MoDId,a.InvCode,a.Qty,a.MoLotCode,a.Remark from mom_orderdetail a where a.MoId = ? ";
    //获取U8生成订单所需材料
//    public static  String   MOMMOALLOCATE = "select a.AllocateId,a.InvCode,a.Qty,a.LotNo,a.WhCode,a.Remark from mom_moallocate a where a.MoDId in (SELECT b.MoDId from mom_orderdetail b where b.Status='3' AND b.MoId = ? )";
    public static  String   MOMMOALLOCATE = "select a.AllocateId,a.InvCode,a.Qty,a.LotNo,a.WhCode,a.Remark from mom_moallocate a where a.MoDId in (SELECT b.MoDId from mom_orderdetail b where b.MoId = ? )";

    //获取U8商品信息
    public static  String   INVENTORY = "select a.cInvCode,a.cInvName,a.cVenCode,a.fRetailPrice,a.cBarCode,a.cInvStd,a.cEnglishName,a.cInvCCode,a.cShopUnit,a.fLength,a.fWidth,\n" +
                                        "a.fHeight,a.iInvWeight,a.iVolume,a.iMassDate,a.iLowSum,a.iInvSPrice,a.iInvRCost,a.iInvSCost,a.cCIQCode from Inventory a ORDER BY a.dSDate DESC";
    //获取U8已审核采购订单信息
    public static  String   POPOMAIN = "SELECT a.POID,a.cVenCode,a.dPODate,a.cPOID,a.cMemo FROM PO_Pomain a WHERE a.cVerifier IS NOT NULL AND a.cAuditTime IS NOT NULL ORDER BY a.cmaketime  DESC";
    //获取U8已审核采购订单信息(带参数)
    public static  String   POPOMAIN1 = "SELECT a.POID,a.cVenCode,a.dPODate,a.cPOID,a.cMemo FROM PO_Pomain a WHERE a.cVerifier IS NOT NULL AND a.cAuditTime IS NOT NULL AND Convert(nvarchar,a.cmaketime,120) like concat(?,'%')";
    //获取U8已审核采购订单信息子表
    public static  String   POPODETAILS = "SELECT a.id,a.POID,a.cInvCode,a.iQuantity,a.iTaxPrice,a.cbMemo FROM PO_Podetails a WHERE a.POID = ? ";

    //获取U8未审核采购到货单信息
    public static  String   PUARRIVALVOUCH = "SELECT a.id,a.cVenCode,a.dDate,a.cCode,a.cpocode,a.cMemo FROM PU_ArrivalVouch a WHERE a.cverifier IS NULL AND a.caudittime IS NULL ORDER BY a.dDate DESC";
    //获取U8未审核采购到货单信息(带参数)
    public static  String   PUARRIVALVOUCH1 = "SELECT a.id,a.cVenCode,a.dDate,a.cCode,a.cpocode,a.cMemo FROM PU_ArrivalVouch a WHERE a.cverifier IS NULL AND a.caudittime IS NULL AND Convert(nvarchar,a.cMakeTime,120) like concat(?,'%')";
    //获取U8未审核采购到货单信息子表
    public static  String   PUARRIVALVOUCHS = "SELECT a.Autoid,a.id,a.cInvCode,a.cBatch,a.fRealQuantity,a.iOriTaxCost,a.cbMemo,a.iPOsID FROM PU_ArrivalVouchs a WHERE a.id = ? ";

    //获取U8销售发货单信息
    public static  String   DISPATCHLIST = "select a.DLID,a.cDLCode,a.cSOCode,a.cCusCode,a.dDate,a.cRdCode,a.cMemo,a.cCusName from DispatchList a where a.cVouchType='05' ORDER BY a.dcreatesystime DESC";
    //获取U8销售发货单信息(带参数)
    public static  String   DISPATCHLIST1 = "select a.DLID,a.cDLCode,a.cSOCode,a.cCusCode,a.dDate,a.cRdCode,a.cMemo,a.cCusName from DispatchList a where a.cVouchType='05' AND Convert(nvarchar,a.dcreatesystime,120) like concat(?,'%')";
//    public static  String   DISPATCHLIST1 = "select a.DLID,a.cDLCode,a.cSOCode,a.cCusCode,a.dDate,a.cRdCode,a.cMemo,a.cCusName from DispatchList a where a.cDLCode = 'SM2021110360'";
    //获取U8销售发货单信息子表
    public static  String   DISPATCHLISTS = "select a.AutoID,a.iSOsID,a.cInvCode,a.iQuantity,a.iTaxUnitPrice,a.cPosition,a.cBatch,a.cWhCode,a.cMemo from DispatchLists a where a.DLID = ? ";


}
