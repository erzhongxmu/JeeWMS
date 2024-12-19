package com.base.modules.jeewms.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import jodd.util.CollectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.base.modules.jeewms.mapper.BaKwMapper;
import com.base.modules.jeewms.mapper.WmImNoticeIMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: wm_im_notice_i
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Service
public class WmImNoticeIServiceImpl extends ServiceImpl<WmImNoticeIMapper, WmImNoticeI> implements IWmImNoticeIService {

    @Autowired
    private IMvGoodsService mvGoodsService;
    @Autowired
    private IWmImNoticeHService wmImNoticeHService;
    @Autowired
    private IWmInQmIService wmInQmIService;
    @Autowired
    private WmImNoticeIMapper wmImNoticeIMapper;
    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private BaKwMapper baKwMapper;

    @Autowired
    private IBaPartTypeService typeService;



    /**
     * 批量收货List
     * @param wmImNoticeI
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @Override
    public IPage<WmImNoticeI> selectBatchList(WmImNoticeI wmImNoticeI, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        QueryWrapper<WmImNoticeI> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotEmpty(wmImNoticeI.getImNoticeId()),WmImNoticeI::getImNoticeId,wmImNoticeI.getImNoticeId())
                .like(StringUtils.isNotEmpty(wmImNoticeI.getImCusCode()),WmImNoticeI::getImCusCode,wmImNoticeI.getImCusCode())
                .like(StringUtils.isNotEmpty(wmImNoticeI.getImBeizhu()),WmImNoticeI::getImBeizhu,wmImNoticeI.getImBeizhu())
                .like(StringUtils.isNotEmpty(wmImNoticeI.getGoodsCode()),WmImNoticeI::getGoodsCode,wmImNoticeI.getGoodsCode())
                .like(StringUtils.isNotEmpty(wmImNoticeI.getGoodsName()),WmImNoticeI::getGoodsName,wmImNoticeI.getGoodsName());
        Page<WmImNoticeI> page = new Page<>(pageNo,pageSize);
        IPage<WmImNoticeI> pageRecord = this.page(page,queryWrapper);
        List<WmImNoticeI> dataList = pageRecord.getRecords();
        if (dataList !=null && dataList.size() > 0) {
            List<WmImNoticeI> newDataList = new ArrayList<>();
            for (WmImNoticeI imNoticeI : dataList) {
                if (Double.parseDouble(imNoticeI.getGoodsCount()) > Double.parseDouble(imNoticeI.getGoodsQmCount())) {
                    imNoticeI.setGoodsWqmCount(Double.toString(Double.parseDouble(imNoticeI.getGoodsCount()) - Double.parseDouble(imNoticeI.getGoodsQmCount())));
                    newDataList.add(imNoticeI);
                }
            }
            pageRecord.setRecords(newDataList);
            return pageRecord;
        }
        return pageRecord;

    }

    @Override
    public void add(WmImNoticeI wmImNoticeI) {

        //查询主表
        WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId,wmImNoticeI.getImNoticeId()).one();

        //BeanUtils.copyProperties(wmImNoticeI,wmImNoticeI);
        //查询商品
        MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa,wmImNoticeI.getGoodsCode()).one();
        long hiti = 0;
        if(StringUtil.isEmpty(wmImNoticeI.getBinPlan()) && StringUtils.isNotEmpty(mdGoods.getMpCengGao()) &&
                StringUtils.isNotEmpty(mdGoods.getMpDanCeng()) && StringUtils.isNotEmpty(mdGoods.getChlShl())){
            hiti = Long.parseLong(wmImNoticeI.getGoodsCount())/ ( Long.parseLong(mdGoods.getMpCengGao()) * Long.parseLong(mdGoods.getMpDanCeng()) *
                    Long.parseLong(mdGoods.getChlShl()));
            wmImNoticeI.setBinPlan(Long.toString(hiti));
        }

        wmImNoticeI.setGoodsCode(mdGoods.getShpBianMa());
        wmImNoticeI.setGoodsName(mdGoods.getShpMingCheng());
        if (StringUtils.isNotEmpty(mdGoods.getTiJiCm()) && StringUtils.isNotEmpty(mdGoods.getTiJiCm()) &&
                StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
            wmImNoticeI.setGoodsFvol(String.valueOf(Double.parseDouble(mdGoods.getTiJiCm())*Double.parseDouble(wmImNoticeI.getGoodsCount())));
        }
        wmImNoticeI.setBarcode(mdGoods.getShpTiaoMa());
        wmImNoticeI.setChpShuXing(mdGoods.getChpShuXing());
        if(StringUtils.isNotEmpty(mdGoods.getJshDanWei()) &&(!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei())) &&
                StringUtils.isNotEmpty(mdGoods.getChlShl()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())){
            wmImNoticeI.setBaseGoodscount(String.valueOf(Double.parseDouble(mdGoods.getChlShl())*Double.parseDouble(wmImNoticeI.getGoodsCount())));
        }else{
            wmImNoticeI.setBaseGoodscount(wmImNoticeI.getGoodsCount());
        }
        wmImNoticeI.setGoodsUnit(mdGoods.getShlDanWei());
        wmImNoticeI.setBaseUnit(mdGoods.getJshDanWei());
        if (StringUtils.isNotEmpty(mdGoods.getZhlKg()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount()) &&
                StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
            wmImNoticeI.setGoodsWeight(String.valueOf(Double.parseDouble(mdGoods.getZhlKg())*Double.parseDouble(wmImNoticeI.getGoodsCount())));
        }
        if("04".equals(wmImNoticeH.getOrderTypeCode())){//越库任务
            wmImNoticeI.setGoodsQmCount("0");
            wmImNoticeI.setBinPre("Y");
            wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());


            WmInQmI wmInQmI = new WmInQmI();
            wmInQmI.setBinId(wmImNoticeH.getNoticeId());//仓位
            wmInQmI.setImNoticeId(wmImNoticeH.getNoticeId());//通知单号
            wmInQmI.setImNoticeItem(wmImNoticeI.getId());
            wmInQmI.setBinSta("Y");
            wmInQmI.setCusCode(wmImNoticeH.getCusCode());;
            wmInQmI.setTinId(wmImNoticeH.getNoticeId());
            wmInQmI.setTinTj(wmImNoticeI.getGoodsFvol());
            wmInQmI.setTinZhl(wmImNoticeI.getGoodsWeight());
            wmInQmI.setGoodsId(wmImNoticeI.getGoodsCode());
            wmInQmI.setGoodsUnit(wmImNoticeI.getGoodsUnit());
            wmInQmI.setQmOkQuat(wmImNoticeI.getGoodsCount());
            wmInQmI.setImQuat(wmImNoticeI.getGoodsCount());
            wmInQmI.setBaseQmcount(wmImNoticeI.getBaseGoodscount());
            wmInQmI.setBaseUnit(wmImNoticeI.getBaseUnit());
            wmInQmI.setProData(new Date());
            wmInQmI.setGoodsBatch(new SimpleDateFormat("yyyy-MM-dd").format(wmInQmI.getProData()));
            wmInQmI.setBaseGoodscount(wmImNoticeI.getBaseGoodscount());
            wmInQmI.setImCusCode(wmImNoticeH.getImCusCode());
            wmInQmIService.save(wmInQmI);

        }else{
            wmImNoticeI.setBinPre("N");
            wmImNoticeI.setGoodsQmCount("0");


            wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
            wmImNoticeI.setImBeizhu(wmImNoticeH.getImBeizhu());
            wmImNoticeI.setImCusCode(wmImNoticeH.getImCusCode());
        }

        this.save(wmImNoticeI);
    }

    /**
     * 退货验收列表
     * @param wmImNoticeI
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<WmImNoticeI> selectYsList(WmImNoticeI wmImNoticeI, Integer pageNo, Integer pageSize) {
        Page page = new Page<>(pageNo,pageSize);
        IPage<WmImNoticeI> iPage = wmImNoticeIMapper.selectYsList(page,wmImNoticeI);
        return iPage;
    }

    /**
     * 批量收货
     * @param wmImNoticeI
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @Override
    public IPage<WmImNoticeI> queryBatchPageList(WmImNoticeI wmImNoticeI, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        Page page = new Page<>(pageNo,pageSize);
        IPage<WmImNoticeI> wmImNoticeIIPage = wmImNoticeIMapper.queryBatchPageList(page, wmImNoticeI);
        List<WmImNoticeI> records = wmImNoticeIIPage.getRecords();
        for (WmImNoticeI record : records) {
            List<MdGoods> list = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, record.getGoodsCode()).list();
            if (CollectionUtils.isNotEmpty(list)){
                MdGoods mdgoods = list.get(0);
                record.setFactorySnpCaseNum(mdgoods.getFactorySnpCaseNum());
                record.setFactorySnpPackageNum(mdgoods.getFactorySnpPackageNum());
                record.setFactorySnpPieceNum(mdgoods.getFactorySnpPieceNum());
                if (StringUtil.isNotEmpty(mdgoods.getClassification())){
                    BaPartType partType = typeService.getById(mdgoods.getClassification());
                    if (partType != null) {
                        record.setGoodsTypeId(partType.getId());
                        record.setGoodsTypeName(partType.getTypeName());
                    }
                }
            }
        }
        return wmImNoticeIIPage;

    }

    @Override
    public List<WmImNoticeI> selectByMainId(String mainId) {
        return wmImNoticeIMapper.selectByMainId(mainId);
    }
}
