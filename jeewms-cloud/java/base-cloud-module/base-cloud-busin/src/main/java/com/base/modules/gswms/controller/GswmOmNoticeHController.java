package com.base.modules.gswms.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.vo.LoginUser;
import com.base.modules.jeewms.entity.WmOmNoticeH;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.base.modules.jeewms.service.IWmOmNoticeHService;
import com.base.modules.jeewms.service.IWmOmNoticeIService;
import com.base.modules.jeewms.service.IWmOmQmIService;
import com.base.modules.jeewms.service.impl.AnalysisWarehousingImpl;
import com.base.modules.jobhandler.U8JiekouXxlJob;
import com.google.gson.Gson;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 出货管理
 * @Author: base-boot
 * @Date: 2021-05-18
 * @Version: V1.0
 */
@Api(tags = "出货管理")
@RestController
@RequestMapping("/gswms/wmOmNoticeH")
@Slf4j
public class GswmOmNoticeHController extends JeecgController<WmOmNoticeH, IWmOmNoticeHService> {

    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;
    @Autowired
    private IWmOmNoticeIService wmOmNoticeIService;
    @Autowired
    private AnalysisWarehousingImpl analysisWarehousing;
    @Autowired
    private IWmOmQmIService wmOmQmIService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private U8JiekouXxlJob u8JiekouXxlJob;

    @AutoLog(value = "原材料出库/同步国声生产领料单")
    @ApiOperation(value = "同步国声生产领料单", notes = "同步国声生产领料单")
    @GetMapping(value = "/getGslingliaodan")
    public Result<?> getGslingliaodan(@RequestParam(name = "date", required = false) String date) throws Exception {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        synchronized (this) {
            K3CloudApi api = new K3CloudApi();
            Date date1 = DateUtil.parse(date, "yyyy-MM-dd");
            Date newDate2 = cn.hutool.core.date.DateUtil.offsetDay(date1, 1);
            String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
            //     FCREATEDATE >= '+  2019-09-10 + '   and FCREATEDATE <= '2019-09-11'
            Map<String, String> map = new HashMap<>(1024);
            map.put("FormId", "PRD_PickMtrl");
            map.put("FieldKeys", "FBILLNO,FPPBOMBILLNO,FMOBILLNO,FSTOCKORGID.FNumber,FSTOCKORGID.FName,FCREATEDATE,FBASEUNITID.FName,FACTUALQTY,FCREATORID.FName,FPICKERID.FName,FSTOCKERID.FName," +
                    "FAPPROVEDATE,FBILLTYPE.FName,FMaterialId.FNumber,FMaterialId.FName,FLOT");
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
            List<WmOmNoticeI> noticeIS = new ArrayList<>();
            List<WmOmNoticeH> noticeHS = new ArrayList<>();
            if (datas1 != null) {
                for (List<Object> objects : datas1) {
                    //每一条都是明细
                    WmOmNoticeI noticeI = new WmOmNoticeI();
                    //商品编码
                    noticeI.setGoodsId(String.valueOf(objects.get(13)));
                    //商品名称
                    noticeI.setGoodsName(String.valueOf(objects.get(14)));
                    //基本单位
                    noticeI.setBaseUnit(String.valueOf(objects.get(6)));
                    noticeI.setGoodsUnit(String.valueOf(objects.get(6)));
                    //实收数量
                    noticeI.setGoodsQua(String.valueOf(objects.get(7)));
                    //批号
                    noticeI.setGoodsBatch(String.valueOf(objects.get(15)));
                    //主表单据编号
                    noticeI.setU8Djid1(String.valueOf(objects.get(0)));
                    //生产用料清单编号
                    noticeI.setU8Djid2(String.valueOf(objects.get(1)));
                    //生产订单编号
                    noticeI.setProcode(String.valueOf(objects.get(2)));
                    //领料日期
                    noticeI.setPickingdate(String.valueOf(objects.get(5)));
                    //单据创建人
                    noticeI.setUcreatename(String.valueOf(objects.get(8)));
                    //领料人
                    noticeI.setPickername(String.valueOf(objects.get(9)));
                    //仓管员
                    noticeI.setWarehousepername(String.valueOf(objects.get(10)));

                    noticeI.setCreateBy(sysUser.getUsername());
                    noticeI.setCreateName(sysUser.getRealname());
                    noticeIS.add(noticeI);

                    WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
                    //单据编号
                    wmOmNoticeH.setU8ReturnCode(String.valueOf(objects.get(0)));
                    //发货组织内码
                    wmOmNoticeH.setCusCode(String.valueOf(objects.get(3)));
                    //发货组织名称
                    wmOmNoticeH.setCusName(String.valueOf(objects.get(4)));
                    //客户
                    wmOmNoticeH.setOcusName(String.valueOf(objects.get(9)));
                    //审核日期
//                    Date dates = DateUtil.parse(String.valueOf(objects.get(11)));
                    wmOmNoticeH.setDelvData(new Date());
                    //入库类型
                    wmOmNoticeH.setOrderTypeCode("13");
                    wmOmNoticeH.setOrderTypes(String.valueOf(objects.get(12)));

                    wmOmNoticeH.setCreateBy(sysUser.getUsername());
                    wmOmNoticeH.setCreateName(sysUser.getRealname());

                    noticeHS.add(wmOmNoticeH);
                }
                List<WmOmNoticeH> distinctClass = noticeHS.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(o -> o.getU8ReturnCode()))), ArrayList::new));

                for (WmOmNoticeH aClass : distinctClass) {
                    List<WmOmNoticeI> noticeIS1 = new ArrayList<>();
                    for (WmOmNoticeI noticeI : noticeIS) {
                        if (aClass.getU8ReturnCode().equals(noticeI.getU8Djid1())) {
                            noticeIS1.add(noticeI);
                        }
                    }
                    WmOmNoticeH noticeH = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getU8ReturnCode, aClass.getU8ReturnCode()).one();
                    if (noticeH != null) {
                        continue;
                    }
                    wmOmNoticeHService.saveMain(aClass, noticeIS1);
                }
            }
            return Result.ok("同步成功！");
        }
    }


    /**
     * 分页列表查询
     *
     * @param wmOmNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "出货通知-分页列表查询")
    @ApiOperation(value = "出货通知-分页列表查询", notes = "出货通知-分页列表查询")
    @GetMapping(value = "/customerlist")
    public Result<?> customerList(WmOmNoticeH wmOmNoticeH,
                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        QueryWrapper<WmOmNoticeH> queryWrapper = QueryGenerator.initQueryWrapper(wmOmNoticeH, req.getParameterMap());
        Page<WmOmNoticeH> page = new Page<WmOmNoticeH>(pageNo, pageSize);
        IPage<WmOmNoticeH> pageList = wmOmNoticeHService.page(page, queryWrapper);
        List<WmOmNoticeH> records = pageList.getRecords();
        for (WmOmNoticeH record : records) {
            List<WmOmNoticeI> list = wmOmNoticeIService.lambdaQuery().eq(WmOmNoticeI::getOmNoticeId, record.getOmNoticeId()).list();
            record.setWmOmNoticeIList(list);
        }
        return Result.ok(pageList);
    }



    @AutoLog(value = "成品出库/同步国声发货通知单")
    @ApiOperation(value = "同步国声发货通知单", notes = "同步国声发货通知单")
    @GetMapping(value = "/getGsfahuodan")
    public Result<?> getGsfahuodan(@RequestParam(name = "date", required = false) String date) throws Exception {
        synchronized (this) {
            K3CloudApi api = new K3CloudApi();
            Date date1 = DateUtil.parse(date, "yyyy-MM-dd");
            Date newDate2 = cn.hutool.core.date.DateUtil.offsetDay(date1, 1);
            String date2 = DateUtil.format(newDate2, "yyyy-MM-dd");
            //     FCREATEDATE >= '+  2019-09-10 + '   and FCREATEDATE <= '2019-09-11'
            Map<String, String> map = new HashMap<>(1024);
            map.put("FormId", "SAL_DELIVERYNOTICE");
            map.put("FieldKeys", "FCUSTOMERID.FNumber,FCUSTOMERID.FName,FDELIVERYORGID.FNumber,FDELIVERYORGID.FName,FDELIVERYDEPTID.FNumber,FDELIVERYDEPTID.FName,FBILLNO," +
                    "FMaterialId.FNumber,FMaterialId.FName,FUNITID.FName,FQTY,FDELIVERYDATE,FNOTE,FCREATORID.FName,FAPPROVERID.FName,FBILLTYPEID.FName");
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
            List<WmOmNoticeI> noticeIS = new ArrayList<>();
            List<WmOmNoticeH> noticeHS = new ArrayList<>();
            if (datas1 != null) {
                for (List<Object> objects : datas1) {
                    //每一条都是明细
                    WmOmNoticeI noticeI = new WmOmNoticeI();
                    //商品编码
                    noticeI.setGoodsId(String.valueOf(objects.get(7)));
                    //商品名称
                    noticeI.setGoodsName(String.valueOf(objects.get(8)));
                    //基本单位
                    noticeI.setBaseUnit(String.valueOf(objects.get(9)));
                    noticeI.setGoodsUnit(String.valueOf(objects.get(9)));
                    //发货数量
                    noticeI.setGoodsQua(String.valueOf(objects.get(10)));
                    //单据编号
                    noticeI.setU8Djid1(String.valueOf(objects.get(6)));
                    //发货部门
                    noticeI.setShipdepcode(String.valueOf(objects.get(4)));
                    noticeI.setShipdepname(String.valueOf(objects.get(5)));
                    //备注
                    noticeI.setOmBeiZhu(String.valueOf(objects.get(12)));
                    //单据创建人
                    noticeI.setUcreatename(String.valueOf(objects.get(13)));
                    //审核人
                    noticeI.setCheckname(String.valueOf(objects.get(14)));
                    noticeIS.add(noticeI);

                    WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
                    //单据编号
                    wmOmNoticeH.setU8ReturnCode(String.valueOf(objects.get(6)));
                    //发货组织内码
                    wmOmNoticeH.setCusCode(String.valueOf(objects.get(2)));
                    //发货组织名称
                    wmOmNoticeH.setCusName(String.valueOf(objects.get(3)));
                    //客户
                    wmOmNoticeH.setOcusCode(String.valueOf(objects.get(0)));
                    wmOmNoticeH.setOcusName(String.valueOf(objects.get(1)));
                    //要求交货时间
                    String s1 = String.valueOf(objects.get(11));
                    String[] ts = s1.split("T");
                    Date dates = DateUtil.parse(StringUtils.isNotEmpty(ts[0]) ? ts[0] : new Date().toString());
                    wmOmNoticeH.setDelvData(dates);
                    //入库类型
                    wmOmNoticeH.setOrderTypeCode("12");
                    wmOmNoticeH.setOrderTypes(String.valueOf(objects.get(15)));
                    noticeHS.add(wmOmNoticeH);
                }
                List<WmOmNoticeH> distinctClass = noticeHS.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(o -> o.getU8ReturnCode()))), ArrayList::new));

                for (WmOmNoticeH aClass : distinctClass) {
                    List<WmOmNoticeI> noticeIS1 = new ArrayList<>();
                    for (WmOmNoticeI noticeI : noticeIS) {
                        if (aClass.getU8ReturnCode().equals(noticeI.getU8Djid1())) {
                            noticeIS1.add(noticeI);
                        }
                    }
                    WmOmNoticeH noticeH = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getU8ReturnCode, aClass.getU8ReturnCode()).one();
                    if (noticeH != null) {
                        continue;
                    }
                    wmOmNoticeHService.saveMain(aClass, noticeIS1);
                }
            }
            return Result.ok("同步成功！");
        }
    }
}
