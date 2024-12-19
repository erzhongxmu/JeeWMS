package com.base.modules.jeewms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.config.sign.util.WebServiceUtil;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.*;
import com.base.modules.jeewms.pdaapi.ApiEntity;
import com.base.modules.jeewms.pdaapi.ApiresEntity;
import com.base.modules.jeewms.service.IWmsPdaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WmsPdaServiceImpl implements IWmsPdaService {

    @Autowired
    private WmsPdaMapper wmsPdaMapper;
    @Autowired
    private WmImNoticeHMapper wmImNoticeHMapper;
    @Autowired
    private WmImNoticeIMapper wmImNoticeIMapper;
    @Autowired
    private WmOmNoticeHMapper wmOmNoticeHMapper;
    @Autowired
    private WmOmNoticeIMapper wmOmNoticeIMapper;
    @Autowired
    private WmToMoveGoodsMapper wmToMoveGoodsMapper;
    @Autowired
    private WmSttInGoodsMapper wmSttInGoodsMapper;

    private static String SetBook = "888";
    private static String SetYear = "2021";
    private static String LoginName = "9002";
    private static String LoginPwd = "qqq";
    private static String Url = "http://192.168.12.103:8010/Service/160Service.asmx/U8API";


    @Override
    public IPage<WmImNoticeI> queryYanshouList(String searchVal, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        Page page = new Page<>(pageNo,pageSize);
        return wmsPdaMapper.queryYanshouList(page,searchVal);
    }

    @Override
    public Result<?> getU8Caigoudaohuosh(String id) throws Exception {
        WmImNoticeH wmImNoticeH = wmImNoticeHMapper.selectById(id);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","CGDHDAudit");//采购入库调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        map2.put("keyId",wmImNoticeH.getU8Id());
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        System.out.println(Json);
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success").toString())){
            wmImNoticeH.setU8Sta("1");
            wmImNoticeHMapper.updateById(wmImNoticeH);
            return Result.ok("审核成功！");
        }else{
            return Result.error("审核失败！" + result.get("msg"));
        }
    }

    @Override
    public Result<?> getU8Diaobodansh(String id) throws Exception {
        WmToMoveGoods wmToMoveGoods = wmToMoveGoodsMapper.selectById(id);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","DBDAudit");//采购入库调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        map2.put("keyId",wmToMoveGoods.getU8Id());
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success"))){
            wmToMoveGoods.setU8Sta("1");
            wmToMoveGoodsMapper.updateById(wmToMoveGoods);
            return Result.ok("审核成功！");
        }else{
            return Result.error("审核失败！" + result.get("msg"));
        }
    }

    @Override
    public Result<?> getU8Caigouruku(String id) throws Exception {
        WmImNoticeH wmImNoticeH = wmImNoticeHMapper.selectById(id);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","CGRKDAdd");//采购入库调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        List<Map> mapList = new ArrayList<>();
        Map map3 = new LinkedHashMap();
        map3.put("AddType",2);//2:采购订单
        map3.put("cCode",wmImNoticeH.getNoticeId());//单据号，string类型(不传自动生成）
        map3.put("dDate",new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeH.getImData()));//单据日期
        map3.put("cVenCode",wmImNoticeH.getCusCode());//供应商编码
        map3.put("dARVDate",new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeH.getImData()));//到货日期
        map3.put("bIsRedVouch",false);//红蓝区分 false:蓝 true:红
        map3.put("cOrderCode",wmImNoticeH.getU8Cgcode());//U8采购订单号
		map3.put("cpersoncode",null);//业务员编码，string类型(可选)
        map3.put("cMemo",wmImNoticeH.getImBeizhu());//备注
        map3.put("cWhCode",wmImNoticeH.getStoreCode());//仓库编码  (必选)
        map3.put("cRdCode","11");//入库类别编码
        List<Map> mapList1 = new ArrayList<>();
        List<WmImNoticeI> wmImNoticeIList = wmImNoticeIMapper.selectByMainId(wmImNoticeH.getNoticeId());
        for(WmImNoticeI wmImNoticeI : wmImNoticeIList){
            Map map4 = new LinkedHashMap();
            map4.put("iPOsID",Integer.parseInt(wmImNoticeI.getU8Cgid()));//主键ID(空单不用传，引用单据传递对应明细的主键ID)
            map4.put("cInvCode",wmImNoticeI.getGoodsCode());//存货编码
            map4.put("iQuantity",new BigDecimal(wmImNoticeI.getGoodsCount()));//数量
            map4.put("iUnitCost",wmImNoticeI.getUnitPrice());//单价，double类型
            map4.put("cbatch",wmImNoticeI.getGoodsBatch());//批号，string类型
            map4.put("cbMemo",wmImNoticeI.getRemark());//备注
            mapList1.add(map4);
        }
        map3.put("Details",mapList1);
        mapList.add(map3);
        map2.put("json", JSON.toJSONString(mapList));
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        System.out.println(Json);
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success"))){
            wmImNoticeH.setU8ReturnCode(result.get("billNumber").toString());
            wmImNoticeHMapper.updateById(wmImNoticeH);
            return Result.ok("创建成功！");
        }else{
            return Result.error("创建失败！" + result.get("msg"));
        }
    }

    @Override
    public Result<?> getU8Chengpinruku(String id) throws Exception {
        WmImNoticeH wmImNoticeH = wmImNoticeHMapper.selectById(id);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","CCPRKDAdd");//产成品入库调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        List<Map> mapList = new ArrayList<>();
        Map map3 = new LinkedHashMap();
        map3.put("AddType",1);//1:引用生产订单
        map3.put("dDate",new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeH.getImData()));//单据日期
        map3.put("cCode",wmImNoticeH.getNoticeId());//单据号，string类型(不传自动生成）
        map3.put("cVenCode",wmImNoticeH.getCusCode());//供应商编码
        map3.put("dARVDate",new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeH.getImData()));//到货日期
        map3.put("bIsRedVouch",false);//红蓝区分 false:蓝 true:红
        map3.put("cMPoCode",wmImNoticeH.getU8Cgcode());//U8生产订单号，string类型
        map3.put("cMemo",wmImNoticeH.getImBeizhu());//备注
        map3.put("cWhCode",wmImNoticeH.getStoreCode());//仓库编码  (必选)
        map3.put("cWhName",wmImNoticeH.getStoreName());//仓库名称，string类型(必填)
        map3.put("cRdCode","12");//入库类别编码
        List<Map> mapList1 = new ArrayList<>();
        List<WmImNoticeI> wmImNoticeIList = wmImNoticeIMapper.selectByMainId(wmImNoticeH.getNoticeId());
        for(WmImNoticeI wmImNoticeI : wmImNoticeIList){
            Map map4 = new LinkedHashMap();
            map4.put("iMPoIds",Integer.parseInt(wmImNoticeI.getU8Dhid()));//生产订单主键ID(必传)
            map4.put("cInvCode",wmImNoticeI.getGoodsCode());//存货编码
            map4.put("iQuantity",new BigDecimal(wmImNoticeI.getGoodsCount()));//数量
            map4.put("iUnitCost",wmImNoticeI.getUnitPrice());//单价，double类型
            map4.put("cBatch",wmImNoticeI.getGoodsBatch());//批号，string类型
            map4.put("cMoLotCode",wmImNoticeI.getGoodsBatch());//生产批号，string类型
            map4.put("cbMemo",wmImNoticeI.getRemark());//备注
//            map4.put("cPosition",wmImNoticeI.getBinPlan());//货位，string类型
//            map4.put("cFree10",wmImNoticeI.getId());//WMS入库子表主键
//            map4.put("cFree9",wmImNoticeI.getTinId());//托盘
            map4.put("dVDate",null);//失效日期
            map4.put("dMadeDate",wmImNoticeI.getGoodsPrdData()!=null?new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeI.getGoodsPrdData()):null);//生产日期
            mapList1.add(map4);
        }
        map3.put("Details",mapList1);
        mapList.add(map3);
        map2.put("json", JSON.toJSONString(mapList));
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        System.out.println(Json);
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success"))){
            wmImNoticeH.setU8ReturnCode(result.get("billNumber").toString());
            wmImNoticeHMapper.updateById(wmImNoticeH);
            return Result.ok("创建成功！");
        }else{
            return Result.error("创建失败！" + result.get("msg"));
        }
    }

    @Override
    public Result<?> getU8Xiaoshouchuku(String id) throws Exception {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","XSCKDAdd");//销售出库调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        List<Map> mapList = new ArrayList<>();
        Map map3 = new LinkedHashMap();
        map3.put("AddType",1);//1:引用销售发货单
        map3.put("dDate",new SimpleDateFormat("yyyy-MM-dd").format(wmOmNoticeH.getDelvData()));//订单日期 不传默认当前生成日期
        map3.put("cCode",wmOmNoticeH.getOmNoticeId());//出库单号（不传自动生成）
        map3.put("bIsRedVouch",false);//红蓝区分 false:蓝 true:红
        map3.put("cBusCode",wmOmNoticeH.getU8Djcode1());//引用U8销售发货单
        map3.put("cWhCode",wmOmNoticeH.getStoreCode());//仓库编码(可选)
        map3.put("cMemo",wmOmNoticeH.getOmBeizhu());//备注
        map3.put("cRdCode","21");//出库类别(默认)
        map3.put("cCusCode",wmOmNoticeH.getOcusCode());//客户编码(可选)
        map3.put("dARVDate",new SimpleDateFormat("yyyy-MM-dd").format(wmOmNoticeH.getDelvData()));//到货日期
        List<Map> mapList1 = new ArrayList<>();
        List<WmOmNoticeI> wmOmNoticeIList = wmOmNoticeIMapper.selectByMainId(wmOmNoticeH.getOmNoticeId());
        for(WmOmNoticeI wmOmNoticeI : wmOmNoticeIList){
            Map map4 = new LinkedHashMap();
            map4.put("isodid",Integer.parseInt(wmOmNoticeI.getU8Djid1()));//发货单明细主键ID(必传) ，string类型
            map4.put("cInvCode",wmOmNoticeI.getGoodsId());//存货编码
            map4.put("iQuantity",new BigDecimal(wmOmNoticeI.getGoodsQua()));//数量
            map4.put("iUnitCost",wmOmNoticeI.getGoodsUnitPrice());//单价，double类型
//            map4.put("cPosition",wmOmNoticeI.getBinId());//货位，string类型
            map4.put("cBatch",wmOmNoticeI.getGoodsBatch());//批号，string类型
            map4.put("cbMemo",wmOmNoticeI.getOmBeiZhu());//备注
//            map4.put("cFree10",wmOmNoticeI.getId());//WMS入库子表主键
//            map4.put("cFree9",wmOmNoticeI.getTinId());//托盘
            map4.put("dMadeDate",wmOmNoticeI.getGoodsProData()!=null?new SimpleDateFormat("yyyy-MM-dd").format(wmOmNoticeI.getGoodsProData()):null);//生产日期
            mapList1.add(map4);
        }
        map3.put("Details",mapList1);
        mapList.add(map3);
        map2.put("json", JSON.toJSONString(mapList));
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        System.out.println(Json);
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success"))){
            wmOmNoticeH.setU8ReturnCode(result.get("billNumber").toString());
            wmOmNoticeHMapper.updateById(wmOmNoticeH);
            return Result.ok("创建成功！");
        }else{
            return Result.error("创建失败！" + result.get("msg"));
        }
    }

    @Override
    public Result<?> getU8Cailiaochuku(String id) throws Exception {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","CLCKDAdd");//材料出库调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        List<Map> mapList = new ArrayList<>();
        Map map3 = new LinkedHashMap();
        map3.put("AddType",1);//1:引用生产订单
        map3.put("dDate",new SimpleDateFormat("yyyy-MM-dd").format(wmOmNoticeH.getDelvData()));//订单日期 不传默认当前生成日期
        map3.put("cCode",wmOmNoticeH.getOmNoticeId());//出库单号（不传自动生成）
        map3.put("bIsRedVouch",false);//红蓝区分 false:蓝 true:红
        map3.put("cMPoCode ",wmOmNoticeH.getU8Djcode1());//引用U8生产订单号
        map3.put("cWhCode",wmOmNoticeH.getStoreCode());//仓库编码
        map3.put("cMemo",wmOmNoticeH.getOmBeizhu());//备注
        map3.put("cRdCode","22");//出库类别(默认)
        map3.put("cProBatch","");//生产批号，string类型
        map3.put("cDepCode","");//部门编码，string类型
        map3.put("cVmivencode","");//代管商代码，string类型
        List<Map> mapList1 = new ArrayList<>();
        List<WmOmNoticeI> wmOmNoticeIList = wmOmNoticeIMapper.selectByMainId(wmOmNoticeH.getOmNoticeId());
        for(WmOmNoticeI wmOmNoticeI : wmOmNoticeIList){
            Map map4 = new LinkedHashMap();
            map4.put("iMPoIds",Integer.parseInt(wmOmNoticeI.getU8Djid1()));//领料单明细主见ID
            map4.put("cInvCode",wmOmNoticeI.getGoodsId());//存货编码
            map4.put("iQuantity",new BigDecimal(wmOmNoticeI.getGoodsQua()));//数量
            map4.put("iUnitCost",wmOmNoticeI.getGoodsUnitPrice());//单价，double类型
//            map4.put("cPosition",wmOmNoticeI.getBinId());//货位编码，string类型(可选)
            map4.put("cBatch",wmOmNoticeI.getGoodsBatch());//批次号（可选）
            map4.put("cbMemo",wmOmNoticeI.getOmBeiZhu());//备注
//            map4.put("cFree10",wmOmNoticeI.getId());//WMS入库子表主键
//            map4.put("cFree9",wmOmNoticeI.getTinId());//托盘
            map4.put("dMadeDate",wmOmNoticeI.getGoodsProData()!=null?new SimpleDateFormat("yyyy-MM-dd").format(wmOmNoticeI.getGoodsProData()):null);//生产日期
            map4.put("cMoLotCode","");//生产批号，string类型
            map4.put("cItemCode","");//项目编码
            map4.put("cItem_class","");//项目大类编码（可选）
            mapList1.add(map4);
        }
        map3.put("Details",mapList1);
        mapList.add(map3);
        map2.put("json", JSON.toJSONString(mapList));
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        System.out.println(Json);
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success"))){
            wmOmNoticeH.setU8ReturnCode(result.get("billNumber").toString());
            wmOmNoticeHMapper.updateById(wmOmNoticeH);
            return Result.ok("创建成功！");
        }else{
            return Result.error("创建失败！" + result.get("msg"));
        }
    }

    @Override
    public Result<?> getU8Diaobodan(String id) throws Exception {
        WmToMoveGoods wmToMoveGoods = wmToMoveGoodsMapper.selectById(id);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","DBDAdd");//调拨单调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        List<Map> mapList = new ArrayList<>();
        Map map3 = new LinkedHashMap();
        map3.put("AddType",0);//新增类型 0：空白单据
        map3.put("dTVDate",new SimpleDateFormat("yyyy-MM-dd").format(wmToMoveGoods.getCreateTime()));//订单日期 不传默认当前生成日期
        map3.put("cTVCode",wmToMoveGoods.getNoticeId());//调拨单号（不传自动生成）
        map3.put("bIsRedVouch",false);//false:蓝字单据 true:红字单据（必填）
        map3.put("cIWhCode",wmToMoveGoods.getStoreCodeTo());//转入仓库编码，string类型（必填）
        map3.put("cOWhCode",wmToMoveGoods.getStoreCodeFrom());//转出仓库编码，string类型（必填）
        map3.put("cORdCode","24");//出库类别编码，string类型(必填)
        map3.put("cRdCode","15");//入库类别编码，string类型(必填)
        map3.put("cODepCode ","");//转出部门编码，string类型
        map3.put("cIDepCode","");//转入部门编码，string类型
        map3.put("cPersonCode","");//申请人编码，string类型
        map3.put("itransflag","1");//调拨方向，int类型 0:反向 1：正向
        map3.put("csource","1");//单据来源，int类型 1 -- 库存,2 -- 零售,3 -- 预留 （必填）
        map3.put("chinvsn","");//序列号，string类型
        map3.put("cTVMemo","");//备注，string类型
        List<Map> mapList1 = new ArrayList<>();
//        List<WmOmNoticeI> wmOmNoticeIList = wmOmNoticeIMapper.selectByMainId(wmOmNoticeH.getOmNoticeId());
//        for(WmOmNoticeI wmOmNoticeI : wmOmNoticeIList){
            Map map4 = new LinkedHashMap();
            map4.put("cInvCode",wmToMoveGoods.getGoodsId());//存货编码
            map4.put("iTVQuantity",new BigDecimal(wmToMoveGoods.getGoodsQua()));//数量
            map4.put("iTVACost",null);//空单必传 单价，double类型
            map4.put("cTVBatch","");//批号
            map4.put("cAssUnit","");//库存单位码，string类型
//            map4.put("cinposcode",wmToMoveGoods.getBinTo());//调入货位编码，string类型
//            map4.put("coutposcode",wmToMoveGoods.getBinFrom());//调出货位编码，string类型
            map4.put("cbMemo","");//备注，string类型
            mapList1.add(map4);
//        }
        map3.put("Details",mapList1);
        mapList.add(map3);
        map2.put("json", JSON.toJSONString(mapList));
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        System.out.println(Json);
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success"))){
            wmToMoveGoods.setU8Id(result.get("billNumber").toString());
            wmToMoveGoodsMapper.updateById(wmToMoveGoods);
            return Result.ok("创建成功！");
        }else{
            return Result.error("创建失败！" + result.get("msg"));
        }
    }

    @Override
    public Result<?> getU8Pandiandan(String id) throws Exception {
        WmSttInGoods wmSttInGoods = wmSttInGoodsMapper.selectById(id);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","PDDAdd");//盘点单调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        List<Map> mapList = new ArrayList<>();
        Map map3 = new LinkedHashMap();
        map3.put("AddType",0);//新增类型 0：空白单据
        map3.put("bIsRedVouch",false);//false:蓝字单据 true:红字单据（必填）
        map3.put("cCVCode",wmSttInGoods.getNoticeId());//盘点单号（不传自动生成）
        map3.put("dCVDate",new SimpleDateFormat("yyyy-MM-dd").format(wmSttInGoods.getCreateTime()));//订单日期 不传默认当前生成日期
        map3.put("dACDate",new SimpleDateFormat("yyyy-MM-dd").format(wmSttInGoods.getCreateTime()));//订单日期 不传默认当前生成日期
        map3.put("cWhCode",wmSttInGoods.getStoreCode());//仓库编码，string类型
        map3.put("cIRdCode","17");//入库类别编码，string类型,17盘盈入库
        map3.put("cORdCode","26");//出库类别编码，string类型(必填),26盘亏出库
        map3.put("cDepCode","");//部门编码，string类型
        map3.put("cPersonCode","");//经手人编码，string类型
        map3.put("cCVMemo","");//备注，string类型
        List<Map> mapList1 = new ArrayList<>();
        QueryWrapper<WmSttInGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notice_id",wmSttInGoods.getNoticeId());
        List<WmSttInGoods> wmSttInGoodsList = wmSttInGoodsMapper.selectList(queryWrapper);
        for(WmSttInGoods wmSttInGoods1 : wmSttInGoodsList){
            Map map4 = new LinkedHashMap();
            map4.put("cInvCode",wmSttInGoods1.getGoodsId());//存货编码
            map4.put("iJhdj",null);//单价，double类型
            map4.put("iAdInQuantity",null);//调整入库数量，double类型
            map4.put("iAdOutQuantity",null);//调整出库数量，double类型
            if(new BigDecimal(wmSttInGoods1.getDiffQua()).compareTo(new BigDecimal("0")) > 0){
                map4.put("iAdInQuantity",new BigDecimal(wmSttInGoods1.getDiffQua()));//调整入库数量，double类型
            }
            if(new BigDecimal(wmSttInGoods1.getDiffQua()).compareTo(new BigDecimal("0")) < 0){
                map4.put("iAdOutQuantity",new BigDecimal(wmSttInGoods1.getDiffQua()));//调整入库数量，double类型
            }
            map4.put("iCVQuantity",new BigDecimal(wmSttInGoods1.getGoodsQua()));//账面数量，double类型
            map4.put("iCVCQuantity",new BigDecimal(wmSttInGoods1.getSttQua()));//盘点数量，double类型
            map4.put("iSjDJ",null);//盘点单价，double类型
            map4.put("cCVBatch",wmSttInGoods1.getGoodsBatch());//批号，string类型
//        map4.put("cPosition","");//货位编码，string类型
            map4.put("cCVReason","");//原因，string类型
            map4.put("cbMemo","");//备注，string类型
            mapList1.add(map4);
        }

        map3.put("Details",mapList1);
        mapList.add(map3);
        map2.put("json", JSON.toJSONString(mapList));
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        System.out.println(Json);
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success"))){
            for(WmSttInGoods wmSttInGoods1 : wmSttInGoodsList){
                wmSttInGoods1.setU8Id(result.get("data").toString());
                wmSttInGoods1.setU8Id(result.get("billNumber").toString());
                wmSttInGoodsMapper.updateById(wmSttInGoods1);
            }
            return Result.ok("创建成功！");
        }else{
            return Result.error("创建失败！" + result.get("msg"));
        }
    }

    @Override
    public IPage<ApiresEntity> queryListType01(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType01(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType02(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType02(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType03(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType03(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType03pltn(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType03pltn(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType26(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType26(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType04(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType04(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType05(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType05(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType06(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType06(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType07(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType07(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType08(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType08(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType09(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType09(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType10(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType10(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType11(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType11(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType12(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType12(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType13(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType13(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType14(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType14(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType15(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType15(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType16(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType16(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType18(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType18(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType19(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType19(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType19swgs(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType19swgs(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType19pltn(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType19pltn(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType20(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType20(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType20pltn(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType20pltn(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType21(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType21(page, querymap));
    }

    @Override
    public IPage<ApiresEntity> queryListType22(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType22(page, querymap));
    }
    @Override
    public IPage<ApiresEntity> queryListType23(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType23(page, querymap));
    }
    @Override
    public IPage<ApiresEntity> queryListType24(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType24(page, querymap));
    }
    @Override
    public IPage<ApiresEntity> queryListType25(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType25(page, querymap));
    }
    @Override
    public IPage<ApiresEntity> queryListType32(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType32(page, querymap));
    }
    @Override
    public IPage<ApiresEntity> queryListType33(Page<ApiresEntity> page, HashMap<String, String> querymap) {
        return page.setRecords(wmsPdaMapper.queryListType33(page, querymap));
    }
    @Override
    public List<ApiEntity> queryCkxx() {
        return wmsPdaMapper.queryCkxx();
    }

    @Override
    public List<ApiEntity> querySpkcxx(HashMap<String, String> querymap) {
        return wmsPdaMapper.querySpkcxx(querymap);
    }

    @Override
    public boolean createU8Chengpinruku(WmImNoticeI wmImNoticeI) throws Exception {
        QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notice_id",wmImNoticeI.getImNoticeId());
        WmImNoticeH wmImNoticeH = wmImNoticeHMapper.selectOne(queryWrapper);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","CCPRKDAdd");//产成品入库调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        List<Map> mapList = new ArrayList<>();
        Map map3 = new LinkedHashMap();
        map3.put("AddType",1);//1:引用生产订单
        map3.put("dDate",new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeH.getImData()));//单据日期
        map3.put("cCode",wmImNoticeH.getNoticeId());//单据号，string类型(不传自动生成）
        map3.put("cVenCode",wmImNoticeH.getCusCode());//供应商编码
        map3.put("dARVDate",new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeH.getImData()));//到货日期
        map3.put("bIsRedVouch",false);//红蓝区分 false:蓝 true:红
        map3.put("cMPoCode",wmImNoticeH.getU8Cgcode());//U8生产订单号，string类型
        map3.put("cMemo",wmImNoticeH.getImBeizhu());//备注
        map3.put("cWhCode",wmImNoticeH.getStoreCode());//仓库编码  (必选)
        map3.put("cWhName",wmImNoticeH.getStoreName());//仓库名称，string类型(必填)
        map3.put("cRdCode","12");//入库类别编码
        List<Map> mapList1 = new ArrayList<>();

        Map map4 = new LinkedHashMap();
        map4.put("iMPoIds",Integer.parseInt(wmImNoticeI.getU8Cgid()));//生产订单主键ID(必传)
        map4.put("cInvCode",wmImNoticeI.getGoodsCode());//存货编码
        map4.put("iQuantity",new BigDecimal(wmImNoticeI.getGoodsCount()));//数量
        map4.put("iUnitCost",wmImNoticeI.getUnitPrice());//单价，double类型
        map4.put("cBatch",wmImNoticeI.getGoodsBatch());//批号，string类型
        map4.put("cMoLotCode",wmImNoticeI.getGoodsBatch());//生产批号，string类型
        map4.put("cbMemo",wmImNoticeI.getRemark());//备注
//            map4.put("cPosition",wmImNoticeI.getBinPlan());//货位，string类型
//            map4.put("cFree10",wmImNoticeI.getId());//WMS入库子表主键
//            map4.put("cFree9",wmImNoticeI.getTinId());//托盘
        map4.put("dVDate",null);//失效日期
        map4.put("dMadeDate",wmImNoticeI.getGoodsPrdData()!=null?new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeI.getGoodsPrdData()):null);//生产日期
        mapList1.add(map4);

        map3.put("Details",mapList1);
        mapList.add(map3);
        map2.put("json", JSON.toJSONString(mapList));
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        System.out.println(Json);
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success"))){
            wmImNoticeH.setU8ReturnCode(result.get("billNumber").toString());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean createU8Caigouruku(WmInQmI wmInQmI) throws Exception {
        QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notice_id",wmInQmI.getImNoticeId());
        WmImNoticeH wmImNoticeH = wmImNoticeHMapper.selectOne(queryWrapper);
        Map map1 = new LinkedHashMap();
        map1.put("Cmd","CGRKDAdd");//采购入库调用方法名称
        map1.put("SetBook",SetBook);//账套名
        map1.put("SetYear",SetYear);//年度
        map1.put("LoginName",LoginName);//登录账号
        map1.put("LoginPwd",LoginPwd);//登录密码
        Map map2 = new LinkedHashMap();
        List<Map> mapList = new ArrayList<>();
        Map map3 = new LinkedHashMap();
        map3.put("AddType",2);//2:采购订单
        map3.put("cCode",wmImNoticeH.getNoticeId());//单据号，string类型(不传自动生成）
        map3.put("dDate",new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeH.getImData()));//单据日期
        map3.put("cVenCode",wmImNoticeH.getCusCode());//供应商编码
        map3.put("dARVDate",new SimpleDateFormat("yyyy-MM-dd").format(wmImNoticeH.getImData()));//到货日期
        map3.put("bIsRedVouch",false);//红蓝区分 false:蓝 true:红
        map3.put("cOrderCode",wmImNoticeH.getU8Cgcode());//U8采购订单号
        map3.put("cpersoncode",null);//业务员编码，string类型(可选)
        map3.put("cMemo",wmImNoticeH.getImBeizhu());//备注
        map3.put("cWhCode",wmImNoticeH.getStoreCode());//仓库编码  (必选)
        map3.put("cRdCode","11");//入库类别编码
        List<Map> mapList1 = new ArrayList<>();
        List<WmImNoticeI> wmImNoticeIList = wmImNoticeIMapper.selectByMainId(wmImNoticeH.getNoticeId());
        for(WmImNoticeI wmImNoticeI : wmImNoticeIList){
            Map map4 = new LinkedHashMap();
            map4.put("iPOsID",Integer.parseInt(wmImNoticeI.getU8Cgid()));//主键ID(空单不用传，引用单据传递对应明细的主键ID)
            map4.put("cInvCode",wmImNoticeI.getGoodsCode());//存货编码
            map4.put("iQuantity",new BigDecimal(wmImNoticeI.getGoodsCount()));//数量
            map4.put("iUnitCost",wmImNoticeI.getUnitPrice());//单价，double类型
            map4.put("cbatch",wmImNoticeI.getGoodsBatch());//批号，string类型
            map4.put("cbMemo",wmImNoticeI.getRemark());//备注
//            map4.put("cPosition",wmImNoticeI.getBinPlan());//货位，string类型
//            map4.put("cFree10",wmImNoticeI.getId());//WMS入库子表主键
//            map4.put("cFree9",wmImNoticeI.getTinId());//托盘
            mapList1.add(map4);
        }
        map3.put("Details",mapList1);
        mapList.add(map3);
        map2.put("json", JSON.toJSONString(mapList));
        map1.put("Params",map2);
        JSONObject jsonObj = new JSONObject(map1);
        String Json = jsonObj.toString();
        System.out.println(Json);
        JSONObject result = WebServiceUtil.httpGet(Url,Json);
        if("true".equals(result.get("success"))){
            wmImNoticeH.setU8Sta("1");
            wmImNoticeHMapper.updateById(wmImNoticeH);
            return true;
        }else{
            return false;
        }
    }

}
