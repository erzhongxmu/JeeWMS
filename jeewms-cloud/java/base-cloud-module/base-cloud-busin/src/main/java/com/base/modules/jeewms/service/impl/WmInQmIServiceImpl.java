package com.base.modules.jeewms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.util.DateUtils;
import com.base.modules.jeeerp.entity.BusiPo;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.base.modules.jeeerp.entity.BusiPrdOrd;
import com.base.modules.jeeerp.entity.BusiPrdOrdItem;
import com.base.modules.jeeerp.service.IBusiPoItemService;
import com.base.modules.jeeerp.service.IBusiPoService;
import com.base.modules.jeeerp.service.IBusiPrdOrdItemService;
import com.base.modules.jeeerp.service.IBusiPrdOrdService;
import com.base.modules.jeewms.controller.dto.BatchUpdateProduceDateDTO;
import com.base.modules.jeewms.controller.dto.batchUpdateBinDTO;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.BaKwMapper;
import com.base.modules.jeewms.mapper.WmInQmIMapper;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.vo.AvlKwVo;
import com.base.modules.jeewms.vo.GoodsDetail;
import com.base.modules.jeewms.vo.NewRetailSystemResult;
import com.base.modules.jeewms.vo.NewRetailSystemVo;
import com.base.modules.util.PltnSetState;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 进货管理——批量收货
 * @Author: base-boot
 * @Date: 2021-05-20
 * @Version: V1.0
 */
@Service
public class WmInQmIServiceImpl extends ServiceImpl<WmInQmIMapper, WmInQmI> implements IWmInQmIService {

    @Autowired
    private IBaKwService baKwService;
    @Autowired
    private IWmImNoticeHService wmImNoticeHService;
    @Autowired
    private IWmImNoticeIService wmImNoticeIService;
    @Autowired
    private IMvGoodsService mvGoodsService;
    @Autowired
    private IWmToUpGoodsService wmToUpGoodsService;
    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private BaKwMapper baKwMapper;
    @Autowired
    private IBaPartTypeService baPartTypeService;
    @Autowired
    private IWmInQmIService iWmInQmIService;
    @Autowired
    private IWmTuopanService iWmTuopanService;

    @Autowired
    private PltnSetState pltnSetState;
    /**
     * 添加收货登记
     *
     * @param wmInQmI
     */
    @Override
    public void add(WmInQmI wmInQmI) {

//        if (StringUtils.isEmpty(wmInQmI.getQmOkQuat())) {
//            wmInQmI.setQmOkQuat("0");
//        }

        if (Double.parseDouble(wmInQmI.getQmOkQuat()) <= 0) {
            throw new JeecgBootException("收货数量不正确");
        }

        if (StringUtils.isNotEmpty(wmInQmI.getBinId())) {//库位编码
            //查询库位编码是否存在
            BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, wmInQmI.getBinId()).one();
            if (baKw == null) {
                throw new JeecgBootException("库位不存在");
            }
        } else {
            throw new JeecgBootException("库位为空");
        }

        //查询收货通知
        WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, wmInQmI.getImNoticeId()).one();
        if (wmImNoticeH == null) {
            throw new JeecgBootException("收货通知不存在");
        }
        wmInQmI.setCusCode(wmImNoticeH.getCusCode());
        //查询收货通知明细
        List<WmImNoticeI> wmImNoticeIList = wmImNoticeIService.lambdaQuery().eq(WmImNoticeI::getImNoticeId, wmImNoticeH.getNoticeId()).list();

        if (wmImNoticeIList.size() > 0) {
            boolean flag = true;
            for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
                if (wmImNoticeI.getGoodsCode().equals(wmInQmI.getGoodsId())) {
                    double weiq = Double.parseDouble(wmImNoticeI
                            .getGoodsCount())
                            - Double.parseDouble(wmImNoticeI
                            .getGoodsQmCount());
                    if (Double.parseDouble(wmInQmI.getQmOkQuat()) <= weiq) {
                        MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmInQmI.getGoodsId()).one();
                        if (mdGoods != null) {
                            wmInQmI.setGoodsName(mdGoods.getShpMingCheng());
                            if (StringUtils.isNotEmpty(mdGoods.getTiJiCm())) {
                                wmInQmI.setTinTj(String.valueOf(Double.parseDouble(mdGoods
                                        .getTiJiCm())
                                        * Double.parseDouble(wmInQmI.getQmOkQuat())));
                            }
                            if (StringUtils.isNotEmpty(mdGoods.getZhlKg())) {
                                wmInQmI.setTinZhl(String.valueOf(Double.parseDouble(mdGoods
                                        .getZhlKg())
                                        * Double.parseDouble(wmInQmI.getQmOkQuat())));
                            }
                            wmInQmI.setGoodsUnit(mdGoods.getShlDanWei());
                            wmInQmI.setImNoticeItem(wmImNoticeI.getId());
                            wmInQmI.setImQuat(wmImNoticeI.getGoodsCount());
                            wmInQmI.setImCusCode(wmImNoticeI.getImCusCode());
                            this.save(wmInQmI);
                            return;
                        }
                    }
                    flag = false;
                }
            }
            if (flag) {
                throw new JeecgBootException("商品不正确,通知单下没有该商品");

            }
        }


    }

    /**
     * 上架
     *
     * @param idList
     */
    @Override
    @Transactional
    public void upToShelfForce(List<String> idList) {
        synchronized (this){
            if (idList == null || idList.size() == 0) {
                throw new JeecgBootException("未选择数据");
            }
            for (String id : idList) {
                //查询InQmi
                WmInQmI wmInQmI = this.getById(id);
                //查询储位是否已被停用
                if (StringUtils.isNotEmpty(wmInQmI.getBinId())) {
                    BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, wmInQmI.getBinId()).one();
                    if (baKw == null) {
                        throw new JeecgBootException("库位不存在,请修改库位");
                    }
                    if ("Y".equals(baKw.getStatus())) {
                        throw new JeecgBootException("储位已停用,编码:" + baKw.getKwCode());
                    }
                    if (!checkFitKw(baKw, mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmInQmI.getGoodsId()).one())) {
                        MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmInQmI.getGoodsId()).one();
                        baKw.setPartType(baKw.getPartType() + "," + mdGoods.getClassification());
                        baKwMapper.updateById(baKw);
                        wmInQmI.setRemark(mdGoods.getClassification());
                    }

                } else {
                    throw new JeecgBootException("库位不存在,请修改库位");
                }

                //查询上架列表
                WmToUpGoods wmToUpGoods = wmToUpGoodsService.lambdaQuery().eq(WmToUpGoods::getOrderIdI, id).one();
                if (wmToUpGoods != null) { //已经有了
                    wmInQmI.setBinSta("Y");
                    this.updateById(wmInQmI);
                } else {
                    WmToUpGoods wmToUpGoodsEntity = new WmToUpGoods();
                    wmToUpGoodsEntity.setGoodsId(wmInQmI.getGoodsId());
                    wmToUpGoodsEntity.setGoodsProData(DateUtils.date2Str(wmInQmI.getProData(), new SimpleDateFormat("yyyy-MM-dd")));
                    wmToUpGoodsEntity.setGoodsBatch(wmInQmI.getGoodsBatch());
                    wmToUpGoodsEntity.setGoodsQua(wmInQmI.getQmOkQuat());
                    wmToUpGoodsEntity.setGoodsUnit(wmInQmI.getGoodsUnit());
                    wmToUpGoodsEntity.setOrderIdI(wmInQmI.getId());
                    wmToUpGoodsEntity.setOrderId(wmInQmI.getImNoticeId());
                    wmToUpGoodsEntity.setBinId(wmInQmI.getTinId());
                    wmToUpGoodsEntity.setKuWeiBianMa(wmInQmI.getBinId());
                    wmToUpGoodsEntity.setCusName(wmInQmI.getCusName());
                    wmToUpGoodsEntity.setCusCode(wmInQmI.getCusCode());
                    wmToUpGoodsEntity.setGoodsName(wmInQmI.getGoodsName());
                    wmToUpGoodsEntity.setActTypeCode("01");
                    wmToUpGoodsEntity.setWmToUpId(wmInQmI.getId());

                    //查询商品
                    MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmToUpGoodsEntity.getGoodsId()).one();
                    if (mdGoods == null) {
                        throw new JeecgBootException("上架失败，商品不存在！");
                    }
                    if (StringUtils.isNotEmpty(mdGoods.getJshDanWei())) {
                        wmToUpGoodsEntity.setBaseUnit(mdGoods.getJshDanWei());
                    }
                    if (StringUtils.isNotEmpty(mdGoods.getShlDanWei())) {
                        wmToUpGoodsEntity.setGoodsUnit(mdGoods.getShlDanWei());
                    }

                    if (StringUtils.isNotEmpty(wmToUpGoodsEntity.getGoodsQua()) && StringUtils.isNotEmpty(mdGoods.getShlDanWei()) && StringUtils.isNotEmpty(mdGoods.getChlShl()) && StringUtils.isNotEmpty(mdGoods.getJshDanWei()) && (!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei()))) {
                        wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(
                                Double.parseDouble(mdGoods.getChlShl())
                                        * Double.parseDouble(wmToUpGoodsEntity.getGoodsQua())));
                    } else {
                        wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
                    }

                    wmInQmI.setBinSta("Y");
                    this.updateById(wmInQmI);
                    wmToUpGoodsService.save(wmToUpGoodsEntity);

                }

            }

        }
    }

    @Override
    @Transactional
    public void upToShelf(List<String> idList) {
        synchronized (this){
            if (idList == null || idList.size() == 0) {
                throw new JeecgBootException("未选择数据");
            }
            // 查询主表信息
            WmInQmI wmInQmI2 = this.getById(idList.get(0));
            QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(WmImNoticeH::getNoticeId,wmInQmI2.getImNoticeId());
            List<WmImNoticeH> list = wmImNoticeHService.list(queryWrapper);
            WmImNoticeH wmImNoticeH = new WmImNoticeH();
            if(CollectionUtil.isNotEmpty(list)){
                wmImNoticeH = list.get(0);
            }
            for (String id : idList) {
                //查询InQmi
                WmInQmI wmInQmI = this.getById(id);
                //查询储位是否已被停用
                if (StringUtils.isNotEmpty(wmInQmI.getBinId())) {
                    BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, wmInQmI.getBinId()).one();
                    if (baKw == null) {
                        throw new JeecgBootException("库位不存在,请修改库位");
                    }
                    if ("Y".equals(baKw.getStatus())) {
                        throw new JeecgBootException("库位已停用,编码:" + baKw.getKwCode());
                    }

//                if (!checkFitKw(baKw, mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmInQmI.getGoodsId()).one())) {
//                    throw new JeecgBootException("该库位不能存放此商品类型！");
//                }

                } else {
                    throw new JeecgBootException("库位不存在,请修改库位");
                }

                //查询上架列表
                WmToUpGoods wmToUpGoods = wmToUpGoodsService.lambdaQuery().eq(WmToUpGoods::getOrderIdI, id).one();
                if (wmToUpGoods != null) {//已经有了
                    wmInQmI.setBinSta("Y");
                    this.updateById(wmInQmI);
                } else {
                    WmToUpGoods wmToUpGoodsEntity = new WmToUpGoods();
                    wmToUpGoodsEntity.setGoodsId(wmInQmI.getGoodsId());
                    wmToUpGoodsEntity.setGoodsProData(DateUtils.date2Str(wmInQmI.getProData(), new SimpleDateFormat("yyyy-MM-dd")));
                    wmToUpGoodsEntity.setGoodsBatch(wmInQmI.getGoodsBatch());
//                wmToUpGoodsEntity.setGoodsQua(wmInQmI.getQmOkQuat());
                    wmToUpGoodsEntity.setGoodsQua(wmInQmI.getBaseGoodscount());
                    wmToUpGoodsEntity.setGoodsUnit(wmInQmI.getGoodsUnit());
                    wmToUpGoodsEntity.setOrderIdI(wmInQmI.getId());
                    wmToUpGoodsEntity.setOrderId(wmInQmI.getImNoticeId());
                    wmToUpGoodsEntity.setBinId(wmInQmI.getTinId());
                    wmToUpGoodsEntity.setKuWeiBianMa(wmInQmI.getBinId());
                    wmToUpGoodsEntity.setCusName(wmInQmI.getCusName());
                    wmToUpGoodsEntity.setCusCode(wmInQmI.getCusCode());
                    wmToUpGoodsEntity.setGoodsName(wmInQmI.getGoodsName());
                    wmToUpGoodsEntity.setActTypeCode("01");
                    wmToUpGoodsEntity.setWmToUpId(wmInQmI.getId());
                    wmToUpGoodsEntity.setGoodsUnit(wmInQmI.getGoodsUnit());
                    wmToUpGoodsEntity.setTenantId(wmInQmI.getTenantId());

                    //查询商品
                    MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmToUpGoodsEntity.getGoodsId()).one();
                    if (mdGoods == null) {
                        throw new JeecgBootException("上架失败，商品不存在！");
                    }
                    if (StringUtils.isNotEmpty(mdGoods.getJshDanWei())) {
                        wmToUpGoodsEntity.setBaseUnit(mdGoods.getJshDanWei());
                    }else {
                        wmToUpGoodsEntity.setBaseUnit(wmInQmI.getGoodsUnit());
                    }
                    if (StringUtils.isNotEmpty(mdGoods.getShlDanWei())) {
                        wmToUpGoodsEntity.setGoodsUnit(mdGoods.getShlDanWei());
                    }

                    if (StringUtils.isNotEmpty(wmToUpGoodsEntity.getGoodsQua()) && StringUtils.isNotEmpty(mdGoods.getShlDanWei()) && StringUtils.isNotEmpty(mdGoods.getChlShl()) && StringUtils.isNotEmpty(mdGoods.getJshDanWei()) && (!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei()))) {
                        wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(
                                Double.parseDouble(mdGoods.getChlShl())
                                        * Double.parseDouble(wmToUpGoodsEntity.getGoodsQua())));
                    } else {
                        wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
                    }

                    wmInQmI.setBinSta("Y");
                    this.updateById(wmInQmI);
                    wmToUpGoodsService.save(wmToUpGoodsEntity);
                    // 更新OMS未清数量和已完成数量
                    pltnSetState.wmsPoSetState("number",wmImNoticeH,wmToUpGoodsEntity);
                    // 更新OMS单据状态
                    pltnSetState.wmsPoSetState("state",wmImNoticeH,null);
                }
            }
        }

    }

    @Override
    @Transactional
    @AutoLog(value = "双维上架")
    public void upToShelf1(List<String> idList) {
        synchronized (this){
            if (idList == null || idList.size() == 0) {
                throw new JeecgBootException("未选择数据");
            }
            String code = "";
            JSONObject parse = null;
            for (String id : idList) {
                //查询InQmi
                WmInQmI wmInQmI = this.getById(id);
                //查询储位是否已被停用
                if (StringUtils.isNotEmpty(wmInQmI.getBinId())) {
                    BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, wmInQmI.getBinId()).one();
                    if (baKw == null) {
                        throw new JeecgBootException("库位不存在,请修改库位");
                    }
                    if ("Y".equals(baKw.getStatus())) {
                        throw new JeecgBootException("库位已停用,编码:" + baKw.getKwCode());
                    }

                } else {
                    throw new JeecgBootException("库位不存在,请修改库位");
                }

                MdGoods mdGoods1 = mdGoodsService.getOne(new LambdaQueryWrapper<MdGoods>().eq(MdGoods::getShpBianMa, wmInQmI.getGoodsId()));
                Map map1 = new LinkedHashMap();
                map1.put("storageMerchantCode", wmInQmI.getBinId());
                map1.put("storageRecordNo", wmInQmI.getImNoticeId());
                Map map2 = new LinkedHashMap();
                List<Map> mapList = new ArrayList<>();
                map2.put("goodsBarcode", mdGoods1.getShpTiaoMa());
                map2.put("smallGoodsBarcode", mdGoods1.getShpTiaoMa());
                map2.put("goodsItemCnt", wmInQmI.getQmOkQuat());
                map2.put("type", "0");
                map2.put("goodsUnitCnt", mdGoods1.getChlShl());
                map2.put("goodsCnt", wmInQmI.getQmOkQuat());
                map2.put("productionDate", wmInQmI.getProData());
                mapList.add(map2);
                map1.put("goodsDetails", JSON.toJSONString(mapList));

                JSONObject jsonObj = new JSONObject(map1);
                String Json = jsonObj.toString();
//            JSONObject post = WebServiceUtil.httpPost(""https://retail.danbagui.com/api/storage/sync/record/callback",Json);
//                String post = HttpUtil.post(""https://retail.danbagui.com/api/storage/sync/record/callback", Json);
                String post = HttpUtil.post("https://retail.danbagui.com/api/storage/sync/record/callback", Json);
//                String post = "";
                System.out.println("输出参数："+post);
                parse = (JSONObject) JSONObject.parse(post);
                code = parse.get("code") != null ? parse.get("code").toString() : "";
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(code) && "SUCCESS".equals(code)) {
                    //查询上架列表
                    WmToUpGoods wmToUpGoods = wmToUpGoodsService.lambdaQuery().eq(WmToUpGoods::getOrderIdI, id).one();
                    if (wmToUpGoods != null) { //已经有了
                        wmInQmI.setBinSta("Y");
                        this.updateById(wmInQmI);
                    } else {
                        WmToUpGoods wmToUpGoodsEntity = new WmToUpGoods();
                        wmToUpGoodsEntity.setGoodsId(wmInQmI.getGoodsId());
                        wmToUpGoodsEntity.setGoodsProData(DateUtils.date2Str(wmInQmI.getProData(), new SimpleDateFormat("yyyy-MM-dd")));
                        wmToUpGoodsEntity.setGoodsBatch(wmInQmI.getGoodsBatch());
//                wmToUpGoodsEntity.setGoodsQua(wmInQmI.getQmOkQuat());
                        wmToUpGoodsEntity.setGoodsQua(wmInQmI.getBaseGoodscount());
                        wmToUpGoodsEntity.setGoodsUnit(wmInQmI.getGoodsUnit());
                        wmToUpGoodsEntity.setOrderIdI(wmInQmI.getId());
                        wmToUpGoodsEntity.setOrderId(wmInQmI.getImNoticeId());
                        wmToUpGoodsEntity.setBinId(wmInQmI.getTinId());
                        wmToUpGoodsEntity.setKuWeiBianMa(wmInQmI.getBinId());
                        wmToUpGoodsEntity.setCusName(wmInQmI.getCusName());
                        wmToUpGoodsEntity.setCusCode(wmInQmI.getCusCode());
                        wmToUpGoodsEntity.setGoodsName(wmInQmI.getGoodsName());
                        wmToUpGoodsEntity.setActTypeCode("01");
                        wmToUpGoodsEntity.setWmToUpId(wmInQmI.getId());
                        wmToUpGoodsEntity.setGoodsUnit(wmInQmI.getGoodsUnit());
                        wmToUpGoodsEntity.setTenantId(wmInQmI.getTenantId());

                        //查询商品
                        MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmToUpGoodsEntity.getGoodsId()).one();
                        if (mdGoods == null) {
                            throw new JeecgBootException("上架失败，商品不存在！");
                        }
                        if (StringUtils.isNotEmpty(mdGoods.getJshDanWei())) {
                            wmToUpGoodsEntity.setBaseUnit(mdGoods.getJshDanWei());
                        }
                        if (StringUtils.isNotEmpty(mdGoods.getShlDanWei())) {
                            wmToUpGoodsEntity.setGoodsUnit(mdGoods.getShlDanWei());
                        }

                        if (StringUtils.isNotEmpty(wmToUpGoodsEntity.getGoodsQua()) && StringUtils.isNotEmpty(mdGoods.getShlDanWei()) &&
                                StringUtils.isNotEmpty(mdGoods.getChlShl()) && StringUtils.isNotEmpty(mdGoods.getJshDanWei()) &&
                                (!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei()))) {
                            wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(
                                    Double.parseDouble(mdGoods.getChlShl())
                                            * Double.parseDouble(wmToUpGoodsEntity.getGoodsQua())));
                        } else {
                            wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
                        }

                        wmInQmI.setBinSta("Y");
                        this.updateById(wmInQmI);
                        wmToUpGoodsService.save(wmToUpGoodsEntity);
                    }
                }else {
                    throw new JeecgBootException("同步信息失败！" + parse.get("msg"));
                }
            }
        }
    }

    /**
     * 批量更新储位
     *
     * @param param
     */
    @Override
    public void batchUpdateBin(batchUpdateBinDTO param) {

        //查询储位是否存在
        BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, param.getKwCode()).one();
        if (baKw == null) {
            throw new JeecgBootException("库位不存在");
        }

        //查询需要更新的行项目
        List<WmInQmI> wmInQmIList = this.lambdaQuery().in(WmInQmI::getId, param.getId()).list();
        if (wmInQmIList.size() > 0) {
            for (WmInQmI wmInQmI : wmInQmIList) {
                wmInQmI.setBinId(param.getKwCode());
            }
            this.updateBatchById(wmInQmIList);
        }

    }

    /**
     * 批量更新生产日期
     *
     * @param param
     */
    @Override
    public void batchUpdateProduceDate(BatchUpdateProduceDateDTO param) {
        //查询需要更新的行项目
        List<WmInQmI> wmInQmIList = this.lambdaQuery().in(WmInQmI::getId, param.getId()).list();
        if (wmInQmIList.size() > 0) {
            for (WmInQmI wmInQmI : wmInQmIList) {
                wmInQmI.setProData(param.getProduceDate());
            }
            this.updateBatchById(wmInQmIList);
        }
    }

    /**
     * 退货上架列表查询
     *
     * @param wmInQmI
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<WmInQmI> querySjPageList(WmInQmI wmInQmI, Integer pageNo, Integer pageSize) {

        Page<WmInQmI> page = new Page<>(pageNo, pageSize);
        QueryWrapper<WmInQmI> qmIQueryWrapper = new QueryWrapper<>();
        qmIQueryWrapper.lambda().eq(WmInQmI::getBinSta, "N")
                .likeRight(WmInQmI::getImNoticeId, "TH")
                .like(StringUtils.isNotEmpty(wmInQmI.getImNoticeId()), WmInQmI::getImNoticeId, wmInQmI.getImNoticeId())
                .like(StringUtils.isNotEmpty(wmInQmI.getImCusCode()), WmInQmI::getImCusCode, wmInQmI.getImCusCode())
                .like(StringUtils.isNotEmpty(wmInQmI.getGoodsId()), WmInQmI::getGoodsId, wmInQmI.getGoodsId())
                .like(StringUtils.isNotEmpty(wmInQmI.getGoodsName()), WmInQmI::getGoodsName, wmInQmI.getGoodsName())
                .like(StringUtils.isNotEmpty(wmInQmI.getTinId()), WmInQmI::getTinId, wmInQmI.getTinId())
                .like(StringUtils.isNotEmpty(wmInQmI.getBinId()), WmInQmI::getBinId, wmInQmI.getBinId())
                .eq(StringUtils.isNotEmpty(wmInQmI.getCusCode()), WmInQmI::getCusCode, wmInQmI.getCusCode())
                .like(StringUtils.isNotEmpty(wmInQmI.getBinSta()), WmInQmI::getBinSta, wmInQmI.getBinSta());

        return this.page(page, qmIQueryWrapper);
    }

    /**
     * 批量验收
     *
     * @param wmImNoticeIList
     */
    @Override
    @Transactional
    public void  batchAdd(List<WmImNoticeI> wmImNoticeIList) {
        synchronized (this){
            if (wmImNoticeIList != null && wmImNoticeIList.size() > 0) {
                List<WmInQmI> wmInQmIList = new ArrayList<>();
                List<WmTuopan> WmTuopanList = new ArrayList<>();
                List<WmToUpGoods> wmToUpGoodsList = new ArrayList<>();
                for (WmImNoticeI wmImNoticeIItem : wmImNoticeIList) {
                    String tinId = wmImNoticeIItem.getTinId();
                    if (StringUtils.isEmpty(tinId)) {
                        throw new JeecgBootException("托盘为空！");
                    }
                    WmImNoticeI wmImNoticeISelect = wmImNoticeIService.getById(wmImNoticeIItem.getId());
                    wmImNoticeISelect.setShenqingsl(wmImNoticeIItem.getShenqingsl());
                    //查询主表
                    WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, wmImNoticeISelect.getImNoticeId()).one();
                    if(wmImNoticeH==null){
                        throw new JeecgBootException("无收获订单！");
                    }
                    WmInQmI wminqm = new WmInQmI();
                    //创建一个空的托盘对象
                    WmTuopan wmTuopan = new WmTuopan();
                    if(StringUtils.isEmpty(wmImNoticeIItem.getTuoNum()) && StringUtils.isNotEmpty(wmImNoticeIItem.getGoodsWqmCount())){
                        wmImNoticeIItem.setTuoNum(wmImNoticeIItem.getGoodsWqmCount());
                    }
                    wminqm.setQmOkQuat(wmImNoticeIItem.getTuoNum());
                    wminqm.setBaseGoodscount(wmImNoticeIItem.getTuoNum());
                    wminqm.setGoodsUnit(wmImNoticeISelect.getGoodsUnit());
                    wminqm.setImNoticeItem(wmImNoticeISelect.getId());
                    wminqm.setGoodsId(wmImNoticeISelect.getGoodsCode());
                    wminqm.setUnitPrice(wmImNoticeISelect.getUnitPrice());
                    wminqm.setTenantId(wmImNoticeISelect.getTenantId());
                    wminqm.setBaseUnit(wmImNoticeISelect.getGoodsUnit());

                    //保存托盘
                    //长
                    wmTuopan.setTinLength(wmImNoticeIItem.getTinLength());
                    //宽
                    wmTuopan.setTinWidth(wmImNoticeIItem.getTinWidth());
                    //高
                    wmTuopan.setTinHigh(wmImNoticeIItem.getTinHigh());
                    //托盘号
                    wmTuopan.setTinId(wmImNoticeIItem.getTinId());
                    //体积
                    BigDecimal decimal = NumberUtil.mul(wmImNoticeIItem.getTinLength(), wmImNoticeIItem.getTinWidth(), wmImNoticeIItem.getTinHigh());
                    wmTuopan.setTinVolume(decimal.toString());
                    wmTuopan.setBoxmark(wmImNoticeH.getCusName() + "-" + wmImNoticeIItem.getGoodsCode() +
                            "-" + wmImNoticeIItem.getGoodsName() + "-" + wmImNoticeIItem.getTuoNum() + "-" + wmImNoticeIItem.getGoodsBatch() + "-" + " ");
                    //重量
                    wmTuopan.setTinWeight(wmImNoticeIItem.getTinWeight());
                    // 商品编码 + 入库单号
                    wmTuopan.setRemark(wmImNoticeIItem.getGoodsCode()+","+wmImNoticeIItem.getImNoticeId());


                    MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wminqm.getGoodsId()).one();
                    if (mdGoods != null) {
                        wminqm.setGoodsTypeId(mdGoods.getClassification());
                        //查询商品分类
                        BaPartType baPartType = baPartTypeService.getById(mdGoods.getClassification());
                        if (baPartType != null) {
                            wminqm.setGoodsTypeName(baPartType.getTypeName());
                        }
                    }
                    if (wmImNoticeIItem.getGoodsPrdData() == null) {
                        Date date = new Date();
                        wminqm.setProData(date);
                    } else {
                        wminqm.setProData(wmImNoticeIItem.getGoodsPrdData());
                    }
                    wminqm.setGoodsBatch(wmImNoticeIItem.getGoodsBatch());

                    wminqm.setImNoticeId(wmImNoticeISelect.getImNoticeId());
                    wminqm.setGoodsName(wmImNoticeISelect.getGoodsName());
//                wminqm.setBinId(wmImNoticeIItem.getBinPlan());




                    if (wmImNoticeIItem.getTinId() == null) {
                        wminqm.setTinId("");
                    } else {
                        //查询添加的托盘id是否已有数据
                        if(iWmInQmIService.lambdaQuery().eq(WmInQmI::getTinId, wmImNoticeIItem.getTinId()).one() == null){
                            wminqm.setTinId(wmImNoticeIItem.getTinId());
                        }else {
                            throw  new JeecgBootException("验收的箱码已存在");
                        }
                    }
                    wminqm.setGoodsUnit(wmImNoticeISelect.getGoodsUnit());
                    wminqm.setGoodsBatch(wmImNoticeIItem.getGoodsBatch());
                    wminqm.setCusCode(wmImNoticeH.getCusCode());
                    wminqm.setCusName(wmImNoticeH.getCusName());
                    wminqm.setItemText(wmImNoticeIItem.getImBeizhu());
                    if (StringUtil.isEmpty(wminqm.getGoodsBatch())) {
                        wminqm.setGoodsBatch(DateUtils.date2Str(wminqm.getProData(), new SimpleDateFormat("yyyy-MM-dd")));
                    }


                    if (StringUtils.isEmpty(wminqm.getQmOkQuat())) {
                        throw new JeecgBootException("收货数量为空");
                    }
                    if (Double.parseDouble(wminqm.getQmOkQuat()) <= 0) {
                        throw new JeecgBootException("收货数量不正确");
                    }

                    if (StringUtils.isNotEmpty(wminqm.getBinId())) {//库位编码
                        //查询库位编码是否存在
                        BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, wminqm.getBinId()).one();
                        if (baKw == null) {
                            throw new JeecgBootException("库位不存在");
                        }
//                    if (!checkFitKw(baKw, mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmImNoticeISelect.getGoodsCode()).one())) {
//                        throw new JeecgBootException("该库位不能存放此商品类型！");
//                    }

                    }
                    if (wmImNoticeH == null) {
                        throw new JeecgBootException("收货通知不存在");
                    }
                    //更新收货明细
                    wmImNoticeISelect.setGoodsQmCount(String.valueOf(Double.parseDouble(wmImNoticeISelect.getGoodsQmCount()) + Double.parseDouble(wmImNoticeIItem.getTuoNum())));
                    if (Double.parseDouble(wmImNoticeISelect.getGoodsQmCount()) >= Double.parseDouble(wmImNoticeISelect.getGoodsCount())) {
                        wmImNoticeISelect.setBinPre("Y");
                    }
                    wmImNoticeISelect.setGoodsBatch(wmImNoticeIItem.getGoodsBatch());
                    wmImNoticeISelect.setTinId(tinId);
//                wmImNoticeISelect.setBinPlan(wmImNoticeIItem.getBinPlan());

                    double weiq = Double.parseDouble(wmImNoticeISelect
                            .getGoodsCount())
                            - Double.parseDouble(wmImNoticeISelect
                            .getGoodsQmCount());
                    if (Double.parseDouble(wminqm.getQmOkQuat()) <= weiq) {
                        if (mdGoods != null) {
                            wminqm.setGoodsName(mdGoods.getShpMingCheng());
                            if (StringUtils.isNotEmpty(mdGoods.getTiJiCm())) {
                                wminqm.setTinTj(String.valueOf(Double.parseDouble(mdGoods
                                        .getTiJiCm())
                                        * Double.parseDouble(wminqm.getQmOkQuat())));
                            }
                            if (StringUtils.isNotEmpty(mdGoods.getZhlKg())) {
                                wminqm.setTinZhl(String.valueOf(Double.parseDouble(mdGoods
                                        .getZhlKg())
                                        * Double.parseDouble(wminqm.getQmOkQuat())));
                            }
                            wminqm.setGoodsUnit(mdGoods.getShlDanWei());
                        }
                    }
                    wminqm.setImNoticeItem(wmImNoticeISelect.getId());
                    wminqm.setImCusCode(wmImNoticeISelect.getImCusCode());

                    //更新到货数量
                    List<WmInQmI> wmInQmIUpdateList = this.lambdaQuery().eq(WmInQmI::getImNoticeId, wmImNoticeISelect.getImNoticeId()).eq(WmInQmI::getGoodsId, wmImNoticeISelect.getGoodsCode()).list();
                    if (wmInQmIUpdateList.size() > 0) {
                        double total = Double.parseDouble(wmImNoticeIItem.getGoodsWqmCount());
                        List<String> idList = new ArrayList<>();
                        for (WmInQmI wmInQmI : wmInQmIUpdateList) {
                            total = total + Double.parseDouble(wmInQmI.getQmOkQuat());
                            idList.add(wmInQmI.getId());
                        }
                        UpdateWrapper<WmInQmI> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.lambda().in(WmInQmI::getId, idList);
                        WmInQmI wmInQmIUpdate = new WmInQmI();
                        wmInQmIUpdate.setImQuat(total + "");
                        this.update(wmInQmIUpdate, updateWrapper);
                        wminqm.setImQuat(total + "");
                    } else {
                        wminqm.setImQuat(wmImNoticeIItem.getGoodsWqmCount());
                    }
                    if ("1".equals(wmImNoticeH.getOrderType())) {//直接入库
                        wminqm.setBinSta("Y");
                        //新增上架表数据
                        WmToUpGoods wmToUpGoodsEntity = new WmToUpGoods();
                        wmToUpGoodsEntity.setGoodsId(wminqm.getGoodsId());
                        wmToUpGoodsEntity.setGoodsProData(DateUtils.date2Str(wminqm.getProData(), new SimpleDateFormat("yyyy-MM-dd")));
                        wmToUpGoodsEntity.setGoodsBatch(wminqm.getGoodsBatch());
//                    wmToUpGoodsEntity.setGoodsQua(wminqm.getQmOkQuat());
                        wmToUpGoodsEntity.setGoodsQua(wminqm.getBaseGoodscount());
                        wmToUpGoodsEntity.setGoodsUnit(wminqm.getGoodsUnit());
                        wmToUpGoodsEntity.setOrderIdI(wminqm.getId());
                        wmToUpGoodsEntity.setOrderId(wminqm.getImNoticeId());
                        wmToUpGoodsEntity.setBinId(wminqm.getTinId());
                        wmToUpGoodsEntity.setKuWeiBianMa(wminqm.getBinId());
                        wmToUpGoodsEntity.setCusCode(wminqm.getCusCode());
                        wmToUpGoodsEntity.setCusName(wminqm.getCusName());
                        wmToUpGoodsEntity.setGoodsName(wminqm.getGoodsName());
                        wmToUpGoodsEntity.setActTypeCode("01");
                        wmToUpGoodsEntity.setWmToUpId(wminqm.getId());
                        wmToUpGoodsEntity.setTenantId(wminqm.getTenantId());

                        //查询商品
                        if (mdGoods == null) {
                            throw new JeecgBootException("上架失败，商品不存在！");
                        }
                        if (StringUtils.isNotEmpty(mdGoods.getJshDanWei())) {
                            wmToUpGoodsEntity.setBaseUnit(mdGoods.getJshDanWei());
                        }
                        if (StringUtils.isNotEmpty(mdGoods.getShlDanWei())) {
                            wmToUpGoodsEntity.setGoodsUnit(mdGoods.getShlDanWei());
                        }

                        if (StringUtils.isNotEmpty(wmToUpGoodsEntity.getGoodsQua()) && StringUtils.isNotEmpty(mdGoods.getShlDanWei()) && StringUtils.isNotEmpty(mdGoods.getChlShl()) && StringUtils.isNotEmpty(mdGoods.getJshDanWei()) && (!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei()))) {
                            wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(
                                    Double.parseDouble(mdGoods.getChlShl())
                                            * Double.parseDouble(wmToUpGoodsEntity.getGoodsQua())));
                        } else {
                            wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
                        }
                        wmToUpGoodsList.add(wmToUpGoodsEntity);

                    } else {
                        wminqm.setBinSta("N");
                    }

                    wmInQmIList.add(wminqm);
                    WmTuopanList.add(wmTuopan);


                    wmImNoticeIService.updateById(wmImNoticeISelect);
                }

                if (wmInQmIList.size() > 0) {
                    this.saveBatch(wmInQmIList);
                }

                if (WmTuopanList.size() > 0) {
                    iWmTuopanService.saveBatch(WmTuopanList);
                }

                if (wmToUpGoodsList.size() > 0) {
                    wmToUpGoodsService.saveBatch(wmToUpGoodsList);
                }
            }
        }
    }

    /**
     * 上架
     *
     * @param wmInQmI
     */
    @Override
    public void upToShelfOne(WmInQmI wmInQmI) {
        //查询InQmi
        //查询储位是否已被停用
        BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, wmInQmI.getBinId()).one();
        if (baKw == null) {
            throw new JeecgBootException("库位不存在");
        }
        if ("Y".equals(baKw.getStatus())) {
            throw new JeecgBootException("储位已停用,编码:" + baKw.getKwCode());
        }
        //查询qmi
        WmInQmI wmInQmISelect = this.getById(wmInQmI.getId());
//        if (!checkFitKw(baKw, mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmInQmISelect.getGoodsId()).one())) {
//            throw new JeecgBootException("该库位不能存放此商品类型！");
//        }

        //查询上架列表
        WmToUpGoods wmToUpGoods = wmToUpGoodsService.lambdaQuery().eq(WmToUpGoods::getOrderIdI, wmInQmI.getId()).one();
        if (wmToUpGoods != null) { //已经有了
            wmInQmI.setBinSta("Y");
            this.updateById(wmInQmI);
        } else {
            WmToUpGoods wmToUpGoodsEntity = new WmToUpGoods();
            wmToUpGoodsEntity.setGoodsId(wmInQmISelect.getGoodsId());
            wmToUpGoodsEntity.setGoodsProData(DateUtils.date2Str(wmInQmISelect.getProData(), new SimpleDateFormat("yyyy-MM-dd")));
            wmToUpGoodsEntity.setGoodsBatch(wmInQmISelect.getGoodsBatch());
            wmToUpGoodsEntity.setGoodsQua(wmInQmISelect.getQmOkQuat());
            wmToUpGoodsEntity.setGoodsUnit(wmInQmISelect.getGoodsUnit());
            wmToUpGoodsEntity.setOrderIdI(wmInQmISelect.getId());
            wmToUpGoodsEntity.setOrderId(wmInQmISelect.getImNoticeId());
            wmToUpGoodsEntity.setBinId(wmInQmISelect.getTinId());
            wmToUpGoodsEntity.setKuWeiBianMa(wmInQmI.getBinId());
            wmToUpGoodsEntity.setCusCode(wmInQmISelect.getCusCode());
            wmToUpGoodsEntity.setGoodsName(wmInQmISelect.getGoodsName());
            wmToUpGoodsEntity.setActTypeCode("01");
            wmToUpGoodsEntity.setWmToUpId(wmInQmISelect.getId());

            //查询商品
            MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmToUpGoodsEntity.getGoodsId()).one();
            if (mdGoods == null) {
                throw new JeecgBootException("商品不存在");
            }
            wmToUpGoodsEntity.setBaseUnit(mdGoods.getJshDanWei());
            wmToUpGoodsEntity.setGoodsUnit(mdGoods.getShlDanWei());

            if (StringUtils.isNotEmpty(mdGoods.getJshDanWei()) && StringUtils.isNotEmpty(mdGoods.getShlDanWei())) {
                if (!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei())) {
                    if (StringUtils.isNotEmpty(mdGoods.getChlShl())) {
                        wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(
                                Double.parseDouble(mdGoods.getChlShl())
                                        * Double.parseDouble(wmToUpGoodsEntity.getGoodsQua())));
                    } else {
                        wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
                    }

                } else {
                    wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
                }
            } else {
                wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
            }

            wmInQmI.setBinSta("Y");
            this.updateById(wmInQmI);
            wmToUpGoodsService.save(wmToUpGoodsEntity);
        }
    }

    /**
     * 上架
     *
     * @param wmInQmI
     */
    @Override
    public void upToShelfOneforce(WmInQmI wmInQmI) {
        //查询InQmi
        //查询储位是否已被停用
        BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, wmInQmI.getBinId()).one();
        if (baKw == null) {
            throw new JeecgBootException("库位不存在");
        }
        if ("Y".equals(baKw.getStatus())) {
            throw new JeecgBootException("储位已停用,编码:" + baKw.getKwCode());
        }
        //查询qmi
        WmInQmI wmInQmISelect = this.getById(wmInQmI.getId());
        if (!checkFitKw(baKw, mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmInQmI.getGoodsId()).one())) {
            MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmInQmI.getGoodsId()).one();
            baKw.setPartType(baKw.getPartType() + "," + mdGoods.getClassification());
            baKwMapper.updateById(baKw);
            wmInQmI.setRemark(mdGoods.getClassification());
        }

        //查询上架列表
        WmToUpGoods wmToUpGoods = wmToUpGoodsService.lambdaQuery().eq(WmToUpGoods::getOrderIdI, wmInQmI.getId()).one();
        if (wmToUpGoods != null) { //已经有了
            wmInQmI.setBinSta("Y");
            this.updateById(wmInQmI);
        } else {
            WmToUpGoods wmToUpGoodsEntity = new WmToUpGoods();
            wmToUpGoodsEntity.setGoodsId(wmInQmISelect.getGoodsId());
            wmToUpGoodsEntity.setGoodsProData(DateUtils.date2Str(wmInQmISelect.getProData(), new SimpleDateFormat("yyyy-MM-dd")));
            wmToUpGoodsEntity.setGoodsBatch(wmInQmISelect.getGoodsBatch());
            wmToUpGoodsEntity.setGoodsQua(wmInQmISelect.getQmOkQuat());
            wmToUpGoodsEntity.setGoodsUnit(wmInQmISelect.getGoodsUnit());
            wmToUpGoodsEntity.setOrderIdI(wmInQmISelect.getId());
            wmToUpGoodsEntity.setOrderId(wmInQmISelect.getImNoticeId());
            wmToUpGoodsEntity.setBinId(wmInQmISelect.getTinId());
            wmToUpGoodsEntity.setKuWeiBianMa(wmInQmI.getBinId());
            wmToUpGoodsEntity.setCusCode(wmInQmISelect.getCusCode());
            wmToUpGoodsEntity.setGoodsName(wmInQmISelect.getGoodsName());
            wmToUpGoodsEntity.setActTypeCode("01");
            wmToUpGoodsEntity.setWmToUpId(wmInQmISelect.getId());

            //查询商品
            MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmToUpGoodsEntity.getGoodsId()).one();
            if (mdGoods == null) {
                throw new JeecgBootException("商品不存在");
            }
            wmToUpGoodsEntity.setBaseUnit(mdGoods.getJshDanWei());
            wmToUpGoodsEntity.setGoodsUnit(mdGoods.getShlDanWei());

            if (StringUtils.isNotEmpty(mdGoods.getJshDanWei()) && StringUtils.isNotEmpty(mdGoods.getShlDanWei())) {
                if (!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei())) {
                    if (StringUtils.isNotEmpty(mdGoods.getChlShl())) {
                        wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(
                                Double.parseDouble(mdGoods.getChlShl())
                                        * Double.parseDouble(wmToUpGoodsEntity.getGoodsQua())));
                    } else {
                        wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
                    }

                } else {
                    wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
                }
            } else {
                wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
            }

            wmInQmI.setBinSta("Y");
            this.updateById(wmInQmI);
            wmToUpGoodsService.save(wmToUpGoodsEntity);
        }
    }

    /**
     * 查询可用储位
     *
     * @param id
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<AvlKwVo> getAvlBin(String id, Integer pageNo, Integer pageSize) {
        //查询qmi
        WmInQmI wmInQmI = this.getById(id);
        if (wmInQmI == null) {
            throw new JeecgBootException("记录不存在");
        }
        //查询商品
        MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmInQmI.getGoodsId()).one();
        if (mdGoods == null) {
            throw new JeecgBootException("商品不存在:" + wmInQmI.getGoodsId());
        }

        IPage<AvlKwVo> page = new Page<>(pageNo, pageSize);
        IPage<AvlKwVo> pageResult = baKwMapper.getAvlBin(page, mdGoods.getClassification());
        List<AvlKwVo> list = new ArrayList<>();
//        for (AvlKwVo av : pageResult.getRecords()) {
//            if (Integer.parseInt(av.getTinCount()) > 0) {
//                list.add(av);
//            }
//        }
//        pageResult.setRecords(list);
        return pageResult;
    }

    /**
     * 获取推荐库位
     * @param goodsId
     * @return
     */
    @Override
    public String getRecommendBin(String goodsId) {
        //根据商品查询可用库位
        //查询商品
        MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa,goodsId).one();
        //根据商品类型id查询可用库位

        return null;
    }

    public boolean checkFitKw(BaKw baKw, MdGoods mdGoods) {
        if (baKw != null && StringUtils.isNotEmpty(baKw.getPartType())) {
            if (mdGoods == null) {
                return true;
            }
            List<String> partTypeList = Arrays.asList(baKw.getPartType().split(","));

            return partTypeList.contains(mdGoods.getClassification());

        } else {
            return true;
        }
    }
}
