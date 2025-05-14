package com.base.modules.jobhandler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.util.DateUtils;
import com.base.modules.data.Baseoutientity;
import com.base.modules.data.U8SqlConstant;
import com.base.modules.data.dbUtil;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 *      1、任务开发：在Spring Bean实例中，开发Job方法；
 *      2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 *      3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；
 *      4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Component
@Slf4j
public class U8JiekouXxlJob {

    private static Logger logger = LoggerFactory.getLogger(U8JiekouXxlJob.class);

    @Autowired
    private IMdCusService mdCusService;
    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private IWmImNoticeHService wmImNoticeHService;
    @Autowired
    private IWmU8PushMessageService wmU8PushMessageService;
    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;


    /**
     * 获取U8供应商信息（Bean模式）
     */
    @XxlJob("getU8Gongyingshang")
    public void getU8Gongyingshang() throws Exception {
         List<Baseoutientity> reslit = new ArrayList<>();
         String sql = U8SqlConstant.VENDOR;
         reslit = dbUtil.getbysql(sql, null, 30);
         System.out.println("获取U8供应商信息开始==================================BEGIN:" + new Date());
         for(Baseoutientity baseoutientity : reslit){
             if(StringUtils.isNotEmpty(baseoutientity.getOutX1())){
                 QueryWrapper<MdCus> queryWrapper = new QueryWrapper<>();
                 queryWrapper.eq("ke_hu_bian_ma",baseoutientity.getOutX1());
                 MdCus cus = mdCusService.getOne(queryWrapper);
                 if(cus == null){
                     cus = new MdCus();
                     cus.setKeHuBianMa(baseoutientity.getOutX1());
                     cus.setZhongWenQch(baseoutientity.getOutX2());
                     cus.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(baseoutientity.getOutX3()));
                     cus.setUpdateTime(new Date());
                     cus.setDiZhi(baseoutientity.getOutX4());
                     cus.setZhuLianXiRen(baseoutientity.getOutX5());
                     cus.setShouJi(baseoutientity.getOutX6());
                     cus.setBeiZhu(baseoutientity.getOutX7());
                     mdCusService.save(cus);
                 }
             }
         }
        System.out.println("获取U8供应商信息结束==================================END:" + new Date());
    }

    /**
     * 获取U8商品信息（Bean模式）
     */
    @XxlJob("getU8Shangpin")
    public void getU8Shangpin() throws Exception {
        List<Baseoutientity> reslit = new ArrayList<>();
        String sql = U8SqlConstant.INVENTORY;
        reslit = dbUtil.getbysql(sql, null, 30);
        System.out.println("获取U8商品信息开始==================================BEGIN:" + new Date());
        for(Baseoutientity baseoutientity : reslit){
            if(StringUtils.isNotEmpty(baseoutientity.getOutX1())){
                QueryWrapper<MdGoods> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("shp_bian_ma",baseoutientity.getOutX1());
                MdGoods mdGoods = mdGoodsService.getOne(queryWrapper);
                if(mdGoods==null){
                    mdGoods = new MdGoods();
                    mdGoods.setFrozen("N");
                    mdGoods.setShpBianMa(baseoutientity.getOutX1());
                    mdGoods.setShpTiaoMa(baseoutientity.getOutX1());
                    mdGoods.setShpMingCheng(baseoutientity.getOutX2());
                    mdGoods.setShpBianMakh(baseoutientity.getOutX3());
                    mdGoods.setGoodsPrice(StringUtils.isNotEmpty(baseoutientity.getOutX4())?new BigDecimal(baseoutientity.getOutX4()):null);
                    mdGoods.setShpTiaoMa(baseoutientity.getOutX5());
                    mdGoods.setShpGuiGe(baseoutientity.getOutX6());
                    mdGoods.setYwMingCheng(baseoutientity.getOutX7());
                    mdGoods.setModel(baseoutientity.getOutX8());
                    mdGoods.setShlDanWei(baseoutientity.getOutX9());
                    mdGoods.setChZhXiang(baseoutientity.getOutX10());
                    mdGoods.setKuZhXiang(baseoutientity.getOutX11());
                    mdGoods.setGaoZhXiang(baseoutientity.getOutX12());
                    mdGoods.setWeight(baseoutientity.getOutX13());
                    mdGoods.setTiJiCm(baseoutientity.getOutX14());
                    mdGoods.setBzhiQi(baseoutientity.getOutX15());
                    mdGoods.setCapacityReplenishmentMin(StringUtils.isNotEmpty(baseoutientity.getOutX16())?Double.valueOf(baseoutientity.getOutX16()).intValue():null);
                    mdGoods.setPrice(StringUtils.isNotEmpty(baseoutientity.getOutX17())?new BigDecimal(baseoutientity.getOutX17()):null);
                    mdGoods.setSalesPrice(StringUtils.isNotEmpty(baseoutientity.getOutX18())?new BigDecimal(baseoutientity.getOutX18()):null);
                    mdGoods.setHsCode(baseoutientity.getOutX19());
                    mdGoods.setChlShl("1");
                    mdGoods.setChcDanWei(baseoutientity.getOutX9());
                    mdGoods.setCfWenCeng("默认温层");
                    mdGoods.setSuoShuKeHu("zx00001");
                    mdGoods.setChpShuXing(baseoutientity.getOutX8());
                    mdGoodsService.save(mdGoods);
                }
            }
        }
        System.out.println("获取U8商品信息结束==================================END:" + new Date());
    }

    /**
     * 获取U8已审核采购订单信息（Bean模式）
     */
//    @XxlJob("getU8Caigoudingdan")
    public void getU8Caigoudingdan(String date) throws Exception {
        List<Baseoutientity> reslit = new ArrayList<>();
        String sql = "";
        String para = null;
        if(StringUtils.isNotEmpty(date)){
            sql = U8SqlConstant.POPOMAIN1;
            para = date;
        }else{
            sql = U8SqlConstant.POPOMAIN;
        }
        reslit = dbUtil.getbysql(sql, para, 30);
        System.out.println("获取U8已审核采购订单信息开始==================================BEGIN:" + new Date());
        for(Baseoutientity baseoutientity : reslit){
            if(StringUtils.isNotEmpty(baseoutientity.getOutX4())){
                QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("u8_dhcode",baseoutientity.getOutX4());
                WmImNoticeH wmImNoticeH = wmImNoticeHService.getOne(queryWrapper);
                if(wmImNoticeH==null){
                    List<Baseoutientity> reslitChild = new ArrayList<>();
                    String sql2 = U8SqlConstant.POPODETAILS;
                    reslitChild = dbUtil.getbysql(sql2, baseoutientity.getOutX1(), 30);
                    if(reslitChild!=null && reslitChild.size()>0){
                        wmImNoticeH = new WmImNoticeH();
                        wmImNoticeH.setOrderType("05");
                        wmImNoticeH.setOrderTypeCode("05");
                        wmImNoticeH.setU8Id(baseoutientity.getOutX1());
                        wmImNoticeH.setCusCode("zx00001");
                        wmImNoticeH.setSupCode(baseoutientity.getOutX2());
                        wmImNoticeH.setImData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(baseoutientity.getOutX3()));
                        wmImNoticeH.setU8Cgcode(baseoutientity.getOutX4());
                        wmImNoticeH.setImBeizhu(baseoutientity.getOutX5());
                        wmImNoticeH.setU8Sta("0");
                        List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                        for(Baseoutientity baseoutientity1 : reslitChild){
                            WmImNoticeI wmImNoticeI = new WmImNoticeI();
                            wmImNoticeI.setU8Cgid(baseoutientity1.getOutX1());
                            wmImNoticeI.setGoodsCode(baseoutientity1.getOutX3());
                            wmImNoticeI.setGoodsCount(baseoutientity1.getOutX4());
                            wmImNoticeI.setUnitPrice(StringUtils.isNotEmpty(baseoutientity1.getOutX5())?new BigDecimal(baseoutientity1.getOutX5()):null);
                            wmImNoticeI.setRemark(baseoutientity1.getOutX6());
                            wmImNoticeIList.add(wmImNoticeI);
                        }
                        wmU8PushMessageService.saveCaigoudingdan(wmImNoticeH,wmImNoticeIList);
                    }
                }
            }
        }
        System.out.println("获取U8已审核采购订单信息结束==================================END:" + new Date());
    }

    /**
     * 获取U8未审核采购到货单信息（Bean模式）
     */
    @XxlJob("getU8Caigoudaohuo")
    public void getU8Caigoudaohuo(String date) throws Exception {
        List<Baseoutientity> reslit = new ArrayList<>();
        String sql = "";
        String para = null;
        if(StringUtils.isNotEmpty(date)){
            sql = U8SqlConstant.PUARRIVALVOUCH1;
            para = date;
        }else{
            sql = U8SqlConstant.PUARRIVALVOUCH;
        }
        reslit = dbUtil.getbysql(sql, para, 30);
        System.out.println("获取U8未审核采购到货单信息开始==================================BEGIN:" + new Date());
        for(Baseoutientity baseoutientity : reslit){
            if(StringUtils.isNotEmpty(baseoutientity.getOutX4())){
                QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("u8_dhcode",baseoutientity.getOutX4());
                WmImNoticeH wmImNoticeH = wmImNoticeHService.getOne(queryWrapper);
                if(wmImNoticeH==null){
                    List<Baseoutientity> reslitChild = new ArrayList<>();
                    String sql2 = U8SqlConstant.PUARRIVALVOUCHS;
                    reslitChild = dbUtil.getbysql(sql2, baseoutientity.getOutX1(), 30);
                    if(reslitChild!=null && reslitChild.size()>0){
                        wmImNoticeH = new WmImNoticeH();
                        wmImNoticeH.setOrderType("06");
                        wmImNoticeH.setOrderTypeCode("06");
                        wmImNoticeH.setU8Id(baseoutientity.getOutX1());
                        wmImNoticeH.setCusCode("zx00001");
                        wmImNoticeH.setSupCode(baseoutientity.getOutX2());
                        wmImNoticeH.setImData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(baseoutientity.getOutX3()));
                        wmImNoticeH.setU8Dhcode(baseoutientity.getOutX4());
                        wmImNoticeH.setU8Cgcode(baseoutientity.getOutX5());
                        wmImNoticeH.setImBeizhu(baseoutientity.getOutX6());
                        wmImNoticeH.setU8Sta("0");
                        wmImNoticeH.setStoreCode("U81");
                        List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                        for(Baseoutientity baseoutientity1 : reslitChild){
                            WmImNoticeI wmImNoticeI = new WmImNoticeI();
                            wmImNoticeI.setU8Dhid(baseoutientity1.getOutX1());
                            wmImNoticeI.setGoodsCode(baseoutientity1.getOutX3());
                            wmImNoticeI.setGoodsBatch(baseoutientity1.getOutX4());
                            wmImNoticeI.setGoodsCount(baseoutientity1.getOutX5());
                            wmImNoticeI.setUnitPrice(StringUtils.isNotEmpty(baseoutientity1.getOutX6())?new BigDecimal(baseoutientity1.getOutX6()):null);
                            wmImNoticeI.setRemark(baseoutientity1.getOutX7());
                            wmImNoticeI.setU8Cgid(baseoutientity1.getOutX8());
                            wmImNoticeIList.add(wmImNoticeI);
                        }
                        wmU8PushMessageService.saveCaigouruku(wmImNoticeH,wmImNoticeIList);
                    }
                }
            }
        }
        System.out.println("获取U8未审核采购到货单信息结束==================================END:" + new Date());
    }

    /**
     * 获取U8销售发货单信息（Bean模式）
     */
    @XxlJob("getU8Xiaoshoufahuo")
    public void getU8Xiaoshoufahuo(String date) throws Exception {
        List<Baseoutientity> reslit = new ArrayList<>();
        String sql = "";
        String para = null;
        if(StringUtils.isNotEmpty(date)){
            sql = U8SqlConstant.DISPATCHLIST1;
            para = date;
        }else{
            sql = U8SqlConstant.DISPATCHLIST;
        }
        reslit = dbUtil.getbysql(sql, para, 30);
        System.out.println("获取U8销售发货单信息开始==================================BEGIN:" + new Date());
        for(Baseoutientity baseoutientity : reslit){
            QueryWrapper<WmOmNoticeH> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("u8_djcode1",baseoutientity.getOutX2());
            WmOmNoticeH wmOmNoticeH = wmOmNoticeHService.getOne(queryWrapper);
            if(wmOmNoticeH == null){
                List<Baseoutientity> reslitChild = new ArrayList<>();
                String sql2 = U8SqlConstant.DISPATCHLISTS;
                reslitChild = dbUtil.getbysql(sql2, baseoutientity.getOutX1(), 30);
                if(reslitChild!=null && reslitChild.size()>0){
                    wmOmNoticeH = new WmOmNoticeH();
                    wmOmNoticeH.setOrderTypeCode("12");
                    wmOmNoticeH.setU8Id(baseoutientity.getOutX1());
                    wmOmNoticeH.setU8Djcode1(baseoutientity.getOutX2());
                    wmOmNoticeH.setU8Djcode2(baseoutientity.getOutX3());
                    wmOmNoticeH.setCusCode("zx00001");
                    wmOmNoticeH.setDelvData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(baseoutientity.getOutX5()));
                    wmOmNoticeH.setOmBeizhu(baseoutientity.getOutX7());
                    wmOmNoticeH.setOcusName(baseoutientity.getOutX8());
                    List<WmOmNoticeI> wmOmNoticeIList = new ArrayList<>();
                    for(Baseoutientity baseoutientity1 : reslitChild){
                        WmOmNoticeI wmOmNoticeI = new WmOmNoticeI();
                        wmOmNoticeI.setU8Djid1(baseoutientity1.getOutX1());
                        wmOmNoticeI.setU8Djid2(baseoutientity1.getOutX2());
                        wmOmNoticeI.setGoodsId(baseoutientity1.getOutX3());
                        wmOmNoticeI.setGoodsQua(baseoutientity1.getOutX4());
                        wmOmNoticeI.setGoodsUnitPrice(StringUtils.isNotEmpty(baseoutientity1.getOutX5())?new BigDecimal(baseoutientity1.getOutX5()):null);
                        wmOmNoticeI.setBinId(baseoutientity1.getOutX6());
                        wmOmNoticeI.setGoodsBatch(baseoutientity1.getOutX7());
                        wmOmNoticeI.setStoreCode(baseoutientity1.getOutX8());
                        wmOmNoticeI.setOmBeiZhu(baseoutientity1.getOutX9());
                        wmOmNoticeH.setStoreCode(baseoutientity1.getOutX8());
                        wmOmNoticeIList.add(wmOmNoticeI);
                    }
                    wmU8PushMessageService.saveXiaoshoufahuo(wmOmNoticeH,wmOmNoticeIList);
                }
            }
        }
        System.out.println("获取U8销售发货单信息结束==================================END:" + new Date());
    }

    /**
     * 获取U8生产订单信息-产成品（Bean模式）
     */
    @XxlJob("getU8Shengchandingdan")
    public void getU8Shengchandingdan(String date) throws Exception {
        List<Baseoutientity> reslit = new ArrayList<>();
        String sql = "";
        String para = null;
        if(StringUtils.isNotEmpty(date)){
            sql = U8SqlConstant.MOMORDER1;
            para = date;
        }else{
            sql = U8SqlConstant.MOMORDER;
        }
        reslit = dbUtil.getbysql(sql, para, 30);
        System.out.println("获取U8生产订单信息开始==================================BEGIN:" + new Date());
        for(Baseoutientity baseoutientity : reslit){
            if(StringUtils.isNotEmpty(baseoutientity.getOutX3())){
                QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("u8_dhcode",baseoutientity.getOutX3());
                WmImNoticeH wmImNoticeH = wmImNoticeHService.getOne(queryWrapper);
                if(wmImNoticeH==null){
                    List<Baseoutientity> reslitChild = new ArrayList<>();
                    String sql2 = U8SqlConstant.MOMORDERDETAIL;
                    reslitChild = dbUtil.getbysql(sql2, baseoutientity.getOutX1(), 30);
                    if(reslitChild!=null && reslitChild.size()>0){
                        wmImNoticeH = new WmImNoticeH();
                        wmImNoticeH.setOrderType("07");
                        wmImNoticeH.setOrderTypeCode("07");
                        wmImNoticeH.setU8Id(baseoutientity.getOutX1());
                        wmImNoticeH.setCusCode("zx00001");
                        wmImNoticeH.setImData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(baseoutientity.getOutX2()));
                        wmImNoticeH.setU8Dhcode(baseoutientity.getOutX3());
                        wmImNoticeH.setU8Sta("0");
                        wmImNoticeH.setStoreCode("U81");
                        List<WmImNoticeI> wmImNoticeIList = new ArrayList<>();
                        for(Baseoutientity baseoutientity1 : reslitChild){
                            WmImNoticeI wmImNoticeI = new WmImNoticeI();
                            wmImNoticeI.setU8Dhid(baseoutientity1.getOutX1());
                            wmImNoticeI.setGoodsCode(baseoutientity1.getOutX2());
                            wmImNoticeI.setGoodsCount(baseoutientity1.getOutX3());
                            wmImNoticeI.setGoodsBatch(baseoutientity1.getOutX4());
                            wmImNoticeI.setRemark(baseoutientity1.getOutX5());
                            wmImNoticeIList.add(wmImNoticeI);
                        }
                        wmU8PushMessageService.saveCaigouruku(wmImNoticeH,wmImNoticeIList);
                    }
                }
            }
        }
        System.out.println("获取U8生产订单信息结束==================================END:" + new Date());
    }

    /**
     * 获取U8领料信息（Bean模式）
     */
    @XxlJob("getU8Lingliaoxinxi")
    public void getU8Lingliaoxinxi(String date) throws Exception {
        List<Baseoutientity> reslit = new ArrayList<>();
        String sql = "";
        String para = null;
        if(StringUtils.isNotEmpty(date)){
            sql = U8SqlConstant.MOMORDER1;
            para = date;
        }else{
            sql = U8SqlConstant.MOMORDER;
        }
        reslit = dbUtil.getbysql(sql, para, 30);
        System.out.println("获取U8生产订单-领料信息开始==================================BEGIN:" + new Date());
        for(Baseoutientity baseoutientity : reslit){
            QueryWrapper<WmOmNoticeH> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("u8_djcode1",baseoutientity.getOutX3());
            WmOmNoticeH wmOmNoticeH = wmOmNoticeHService.getOne(queryWrapper);
            if(wmOmNoticeH == null){
                List<Baseoutientity> reslitChild = new ArrayList<>();
                String sql2 = U8SqlConstant.MOMMOALLOCATE;
                reslitChild = dbUtil.getbysql(sql2, baseoutientity.getOutX1(), 30);
                if(reslitChild!=null && reslitChild.size()>0){
                    wmOmNoticeH = new WmOmNoticeH();
                    wmOmNoticeH.setOrderTypeCode("13");
                    wmOmNoticeH.setU8Id(baseoutientity.getOutX1());
                    wmOmNoticeH.setU8Djcode1(baseoutientity.getOutX3());
                    wmOmNoticeH.setCusCode("zx00001");
                    wmOmNoticeH.setDelvData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(baseoutientity.getOutX2()));
                    List<WmOmNoticeI> wmOmNoticeIList = new ArrayList<>();
                    for(Baseoutientity baseoutientity1 : reslitChild){
                        WmOmNoticeI wmOmNoticeI = new WmOmNoticeI();
                        wmOmNoticeI.setU8Djid1(baseoutientity1.getOutX1());
                        wmOmNoticeI.setGoodsId(baseoutientity1.getOutX2());
                        wmOmNoticeI.setGoodsQua(baseoutientity1.getOutX3());
                        wmOmNoticeI.setGoodsBatch(baseoutientity1.getOutX4());
                        wmOmNoticeI.setStoreCode(baseoutientity1.getOutX5());
                        wmOmNoticeI.setOmBeiZhu(baseoutientity1.getOutX6());
                        wmOmNoticeH.setStoreCode(baseoutientity1.getOutX5());
                        wmOmNoticeIList.add(wmOmNoticeI);
                    }
                    wmU8PushMessageService.saveXiaoshoufahuo(wmOmNoticeH,wmOmNoticeIList);
                }
            }
        }
        System.out.println("获取U8生产订单-领料信息结束==================================END:" + new Date());
    }


    /**
     * 2、分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() throws Exception {

        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        XxlJobHelper.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);

        // 业务逻辑
        for (int i = 0; i < shardTotal; i++) {
            if (i == shardIndex) {
                XxlJobHelper.log("第 {} 片, 命中分片开始处理", i);
            } else {
                XxlJobHelper.log("第 {} 片, 忽略", i);
            }
        }

    }


    /**
     * 3、命令行任务
     */
    @XxlJob("commandJobHandler")
    public void commandJobHandler() throws Exception {
        String command = XxlJobHelper.getJobParam();
        int exitValue = -1;

        BufferedReader bufferedReader = null;
        try {
            // command process
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            //Process process = Runtime.getRuntime().exec(command);

            BufferedInputStream bufferedInputStream = new BufferedInputStream(process.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            // command log
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                XxlJobHelper.log(line);
            }

            // command exit
            process.waitFor();
            exitValue = process.exitValue();
        } catch (Exception e) {
            XxlJobHelper.log(e);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        if (exitValue == 0) {
            // default success
        } else {
            XxlJobHelper.handleFail("command exit value("+exitValue+") is failed");
        }

    }


    /**
     * 4、跨平台Http任务
     *  参数示例：
     *      "url: http://www.baidu.com\n" +
     *      "method: get\n" +
     *      "data: content\n";
     */
    @XxlJob("httpJobHandler")
    public void httpJobHandler() throws Exception {

        // param parse
        String param = XxlJobHelper.getJobParam();
        if (param==null || param.trim().length()==0) {
            XxlJobHelper.log("param["+ param +"] invalid.");

            XxlJobHelper.handleFail();
            return;
        }

        String[] httpParams = param.split("\n");
        String url = null;
        String method = null;
        String data = null;
        for (String httpParam: httpParams) {
            if (httpParam.startsWith("url:")) {
                url = httpParam.substring(httpParam.indexOf("url:") + 4).trim();
            }
            if (httpParam.startsWith("method:")) {
                method = httpParam.substring(httpParam.indexOf("method:") + 7).trim().toUpperCase();
            }
            if (httpParam.startsWith("data:")) {
                data = httpParam.substring(httpParam.indexOf("data:") + 5).trim();
            }
        }

        // param valid
        if (url==null || url.trim().length()==0) {
            XxlJobHelper.log("url["+ url +"] invalid.");

            XxlJobHelper.handleFail();
            return;
        }
        if (method==null || !Arrays.asList("GET", "POST").contains(method)) {
            XxlJobHelper.log("method["+ method +"] invalid.");

            XxlJobHelper.handleFail();
            return;
        }
        boolean isPostMethod = "POST".equals(method);

        // request
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        try {
            // connection
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();

            // connection setting
            connection.setRequestMethod(method);
            connection.setDoOutput(isPostMethod);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");

            // do connection
            connection.connect();

            // data
            if (isPostMethod && data!=null && data.trim().length()>0) {
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(data.getBytes("UTF-8"));
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            // valid StatusCode
            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new RuntimeException("Http Request StatusCode(" + statusCode + ") Invalid.");
            }

            // result
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            String responseMsg = result.toString();

            XxlJobHelper.log(responseMsg);

            return;
        } catch (Exception e) {
            XxlJobHelper.log(e);

            XxlJobHelper.handleFail();
            return;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e2) {
                XxlJobHelper.log(e2);
            }
        }

    }

    /**
     * 5、生命周期任务示例：任务初始化与销毁时，支持自定义相关逻辑；
     */
    @XxlJob(value = "demoJobHandler2", init = "init", destroy = "destroy")
    public void demoJobHandler2() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");
    }
    public void init(){
        logger.info("init");
    }
    public void destroy(){
        logger.info("destory");
    }


}
