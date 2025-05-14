package com.base.modules.jeewms.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.WvStockMapper;
import com.base.modules.jeewms.mapper.WvStockSttMapper;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.vo.GoodsDetail;
import com.base.modules.jeewms.vo.NewRetailSystemVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: wv_stock_stt
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
@Service
public class WvStockSttServiceImpl extends ServiceImpl<WvStockSttMapper, WvStockStt> implements IWvStockSttService {

    @Autowired
    private IWmSttInGoodsService wmSttInGoodsService;
    @Autowired
    private IMdCusService mdCusService;
    @Autowired
    private WvStockMapper wvStockMapper;
    @Autowired
    private IBaKwService baKwService;
    @Autowired
    private IBaStoreService baStoreService;
    @Autowired
    private IBaStoreAreaService baStoreAreaService;

    @Autowired
    private IFxWmsKucunService fxWmsKucunService;

    /**
     * 生成盘点单
     * @param idList
     */
    @Override
    public void generate(List<String> idList,String type) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String relTenantIds = sysUser.getRelTenantIds();
        Integer integer = Convert.toInt(relTenantIds);
        if (idList !=null && idList.size() > 0) {
            List<WmSttInGoods> wmSttInGoodsList = new ArrayList<>();
            String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
            int count = wmSttInGoodsService.lambdaQuery().apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'").count();
            int newcount = count + 1;
            String noticeid = "PD" + today + "-" + String.format("%04d", newcount);
            //查询所有的stt
            List<WvStockStt> stockSttList = this.lambdaQuery().in(WvStockStt::getId,idList).list();
            if (stockSttList.size() > 0) {
                for (WvStockStt stockStt : stockSttList) {
                    if (stockStt != null ) {
                        WmSttInGoods wmSttInGoods = new WmSttInGoods();
                        wmSttInGoods.setNoticeId(noticeid);
                        wmSttInGoods.setTenantId(integer);
                        wmSttInGoods.setSttType(type);//1 明盘  2 暗盘
                        wmSttInGoods.setSttId(DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd HH:mm")));
                        wmSttInGoods.setBinId(stockStt.getKuWeiBianMa());
                        wmSttInGoods.setCusCode(stockStt.getCusCode());
                        wmSttInGoods.setCusName(stockStt.getZhongWenQch());
                        wmSttInGoods.setGoodsId(stockStt.getGoodsId());
                        wmSttInGoods.setGoodsName(stockStt.getShpMingCheng());
                        wmSttInGoods.setGoodsProData(stockStt.getGoodsProData());
                        wmSttInGoods.setGoodsQua(stockStt.getGoodsQua().toString());
                        wmSttInGoods.setGoodsUnit(stockStt.getGoodsUnit());
                        wmSttInGoods.setGoodsBatch(stockStt.getGoodsBatch());
                        QueryWrapper<BaKw> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("ku_wei_bian_ma",stockStt.getKuWeiBianMa());
                        BaKw baKw = baKwService.getOne(queryWrapper);
                        if(baKw!=null){
                            wmSttInGoods.setStoreCode(baKw.getStoreCode());
                            wmSttInGoods.setStoreName(baKw.getWareName());
                        }
//                        wmSttInGoods.setSttType("01");//01 托盘盘点  02 储位盘点
                        wmSttInGoods.setSttSta("计划中");
                        wmSttInGoods.setTinId(stockStt.getBinId());
                        MdCus mdcus = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa,  wmSttInGoods.getCusCode()).one();
                        if (mdcus != null) {
                            wmSttInGoods.setCusName(mdcus.getZhongWenQch());
                        }

                        wmSttInGoodsList.add(wmSttInGoods);
                    }
                }
            }
            if (wmSttInGoodsList.size() > 0) {
                wmSttInGoodsService.saveBatch(wmSttInGoodsList);
            }
        }

    }

    /**
     * 生成盘点单
     * @param idList
     */
    @Override
    public void generateSw(List<String> idList,String type) {
        if (idList !=null && idList.size() > 0) {
            List<WmSttInGoods> wmSttInGoodsList = new ArrayList<>();
            String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
            int count = wmSttInGoodsService.lambdaQuery().apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'").count();
            int newcount = count + 1;
            String noticeid = "PD" + today + "-" + String.format("%04d", newcount);
            //查询所有的stt
            List<WvStockStt> stockSttList = this.lambdaQuery().in(WvStockStt::getId,idList).list();
            if (stockSttList.size() > 0) {
                for (WvStockStt stockStt : stockSttList) {
                    if (stockStt != null ) {
                        WmSttInGoods wmSttInGoods = new WmSttInGoods();
                        wmSttInGoods.setNoticeId(noticeid);
                        wmSttInGoods.setSttType(type);//1 明盘  2 暗盘
                        wmSttInGoods.setSttId(DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd HH:mm")));
                        wmSttInGoods.setBinId(stockStt.getKuWeiBianMa());
                        wmSttInGoods.setCusCode(stockStt.getCusCode());
                        wmSttInGoods.setCusName(stockStt.getZhongWenQch());
                        wmSttInGoods.setGoodsId(stockStt.getGoodsId());
                        wmSttInGoods.setGoodsName(stockStt.getShpMingCheng());
                        wmSttInGoods.setGoodsProData(stockStt.getGoodsProData());
                        wmSttInGoods.setGoodsQua(stockStt.getGoodsQua().toString());
                        wmSttInGoods.setGoodsUnit(stockStt.getGoodsUnit());
                        wmSttInGoods.setGoodsBatch(stockStt.getGoodsBatch());
                        QueryWrapper<BaKw> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("ku_wei_bian_ma",stockStt.getKuWeiBianMa());
                        BaKw baKw = baKwService.getOne(queryWrapper);
                        if(baKw!=null){
                            wmSttInGoods.setStoreCode(baKw.getStoreCode());
                            wmSttInGoods.setStoreName(baKw.getWareName());
                        }
//                        wmSttInGoods.setSttType("01");//01 托盘盘点  02 储位盘点
                        wmSttInGoods.setSttSta("计划中");
                        wmSttInGoods.setTinId(stockStt.getBinId());
                        MdCus mdcus = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa,  wmSttInGoods.getCusCode()).one();
                        if (mdcus != null) {
                            wmSttInGoods.setCusName(mdcus.getZhongWenQch());
                        }

                        wmSttInGoodsList.add(wmSttInGoods);
                    }
                }
            }
            if (wmSttInGoodsList.size() > 0) {
                for (WmSttInGoods wmSttInGoods : wmSttInGoodsList) {
                    NewRetailSystemVo systemVo = new NewRetailSystemVo();
                    GoodsDetail detail = new GoodsDetail();

                    //仓库收到门店采购单并发货出库后，需将实际发货信息回传给新零售系统
                    ////仓储系统仓库编号
                    systemVo.setStorageMerchantCode(wmSttInGoods.getBinId());
                    //仓储系统单据单号
                    systemVo.setStorageRecordNo(wmSttInGoods.getGoodsId());
                    //新零售系统单据单号
                    systemVo.setRecordNo(wmSttInGoods.getGoodsId());
                    //单据类型
                    systemVo.setRecordType("INVENTORY");
                    //操作时间
                    systemVo.setOperationTime(wmSttInGoods.getUpdateTime().toString());
                    //出入库实际条码
                    detail.setGoodsBarcode(wmSttInGoods.getGoodsId());
                    //最小包装条码
                    detail.setSmallGoodsBarcode(wmSttInGoods.getGoodsId());
                    //进货出货包装件数
                    detail.setGoodsItemCnt(wmSttInGoods.getGoodsQua());
                    //商品数量、盘点时为实盘库存数量
                    detail.setGoodsCnt(wmSttInGoods.getGoodsQua());
                    //商品最小包装数量
                    detail.setGoodsUnitCnt(wmSttInGoods.getGoodsQua());
                    //出入库类型 0 入库，1 出库
//                    detail.setType("1");
                    //生产日期
                    detail.setProductionDate(wmSttInGoods.getGoodsProData());
                    //库位编号
                    detail.setStockNumber(wmSttInGoods.getBinId());
                    //批次号
                    detail.setBatchNumber(wmSttInGoods.getGoodsBatch());
                    //商品明细，需转为json，具体属性见商品明细属性表
                    systemVo.setGoodsDetails(JSON.toJSONString(detail));
                    Map<String, Object> map = new HashMap<>(1024);
                    map.put("systemVo",systemVo);
//                    String post = HttpUtil.post(""https://retail.danbagui.com/api/storage/sync/record/callback", map);
                    String post = HttpUtil.post("https://retail.danbagui.com/api/storage/sync/record/callback", map);
//                NewRetailSystemResult parse = JSON.parseObject(post,NewRetailSystemResult.class);
                }
                wmSttInGoodsService.saveBatch(wmSttInGoodsList);
            }
        }

    }

    /**
     * pda查询库存
     * @param page
     * @param wvStock
     * @return
     */
    @Override
    public IPage<WmSttInGoods> pdaStockList(Page<WvStock> page, WvStock wvStock) {
        return wvStockMapper.pdaStockList(page,wvStock);
    }

    /**
     * 综合判断分页查询
     * @param queryWrapper
     * @param page
     * @return
     */
    @Override
    public IPage<WvStockStt> getZhList(WvStockStt wvStockStt, QueryWrapper<WvStockStt> queryWrapper, Page<WvStockStt> page, HttpServletRequest req) {
        String areaCode = null;
        String storeCode = null;
        Map<String,String[]> paramMap = req.getParameterMap();
        if (paramMap.containsKey("areaCode")) {
            areaCode = req.getParameterMap().get("areaCode")[0];
        }
        if (paramMap.containsKey("storeCode")) {
            storeCode = req.getParameterMap().get("storeCode")[0];
        }
        if (StringUtils.isEmpty(storeCode)) {
            throw new JeecgBootException("请选择盘点仓库");
        }

        List<String> kwCodeList = new ArrayList<>();
        //查询库位
        if (StringUtils.isNotEmpty(areaCode)) {
            List<BaKw> baKwList = baKwService.lambdaQuery().eq(BaKw::getStoreAreaCode,areaCode).list();
            if (baKwList.size() > 0) {
                for (BaKw baKw : baKwList) {
                    kwCodeList.add(baKw.getKwCode());
                }
            }
        }else {
            //查询仓库
            List<BaStore> baStoreList = baStoreService.lambdaQuery().eq(BaStore::getStoreCode,storeCode).list();
            List<String> storeCodeList = new ArrayList<>();
            if (baStoreList.size() > 0) {
                for (BaStore baStore : baStoreList) {
                    storeCodeList.add(baStore.getStoreCode());
                }
            }
            //根据库区查询库位
            List<String> areaCodeList = new ArrayList<>();
            if (storeCodeList.size() > 0) {
                //查询库区
                List<BaStoreArea> baStoreAreaList = baStoreAreaService.lambdaQuery().in(BaStoreArea::getWareCode,storeCodeList).list();
                if (baStoreAreaList.size() > 0) {
                    for (BaStoreArea baStoreArea : baStoreAreaList) {
                        areaCodeList.add(baStoreArea.getAreaCode());
                    }
                }
            }

            //查询库位
            if (areaCodeList.size() > 0) {
                List<BaKw> baKwList = baKwService.lambdaQuery().in(BaKw::getStoreAreaCode,areaCodeList).list();
                if (baKwList.size() > 0) {
                    for (BaKw baKw : baKwList) {
                        kwCodeList.add(baKw.getKwCode());
                    }
                }
            }
        }
        if (kwCodeList.size() == 0) {
            return new Page<>();
        }
        queryWrapper.lambda()
//                .gt(WvStockStt::getGoodsQua,0)
                .in(kwCodeList.size()>0,WvStockStt::getKuWeiBianMa,kwCodeList);


        return this.page(page, queryWrapper);
    }

    @Override
    public WvStock getKwByTinId(String tinId) {
        if (StringUtils.isEmpty(tinId)) {
            return null;
        }
        return wvStockMapper.getKwByTinId(tinId);
    }

    @Override
    public List<WvStock> getTinIdListByBinId(String binId) {
        return wvStockMapper.getTinIdListByBinId(binId);
    }

    /**
     * 查询库存
     * @param kwCode
     * @return
     */
    @Override
    public List<WvStock> getStockByKwAndGoodsId(String kwCode) {

        return wvStockMapper.getStockByKwAndGoodsId(kwCode);
    }
}
