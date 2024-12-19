package com.base.modules.jeewms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.entity.MvGoods;
import com.base.modules.jeewms.entity.WmOmNoticeH;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.base.modules.jeewms.entity.WmOmQmI;
import com.base.modules.jeewms.mapper.MvGoodsMapper;
import com.base.modules.jeewms.mapper.WmOmNoticeHMapper;
import com.base.modules.jeewms.mapper.WmOmNoticeIMapper;
import com.base.modules.jeewms.mapper.WmOmQmIMapper;
import com.base.modules.jeewms.service.IWmOmNoticeIService;
import com.base.modules.jeewms.vo.EditWmOmNoticeIListVo;
import com.base.modules.util.NotNullUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 出货通知项目
 * @Author: base-boot
 * @Date: 2021-05-18
 * @Version: V1.0
 */
@Service
public class WmOmNoticeIServiceImpl extends ServiceImpl<WmOmNoticeIMapper, WmOmNoticeI> implements IWmOmNoticeIService {

    @Autowired
    private WmOmNoticeHMapper wmOmNoticeHMapper;

    @Autowired
    private WmOmNoticeIMapper wmOmNoticeIMapper;

    @Autowired
    private WmOmQmIMapper wmOmQmIMapper;

    @Autowired
    private MvGoodsMapper mvGoodsMapper;

    @Autowired
    private SmsSendImpl smsSend;

    @Override
    public List<WmOmNoticeI> selectByMainId(String mainId) {
        return wmOmNoticeIMapper.selectByMainId(mainId);
    }

    /**
     * @Describe : 批量修改出货通知项目
     * @Author: zly
     * @Create Date: 2021-05-18
     */
    @Override
    public Result<?> editWmOmNoticeIList(List<EditWmOmNoticeIListVo> voList) {
        voList.stream().forEach(vo -> {
            WmOmNoticeI en = new WmOmNoticeI();
            BeanUtils.copyProperties(vo, en, NotNullUtils.getNullPropertyNames(vo));
            int row = wmOmNoticeIMapper.updateById(en);
            if (row > 0) {
                smsSend.run();
            }
        });
        return Result.ok("修改成功");
    }

    /**
     * @Describe : 添加出库项目明细
     * @Author: zly
     * @Create Date: 2021-05-28
     */
    @Override
    public Result<?> saveWmOmQmIM(WmOmNoticeI wmOmNoticeI) {
        //获取主表信息
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectByMainId(wmOmNoticeI.getOmNoticeId());
        if (ObjectUtil.isEmpty(wmOmNoticeH)) {
            return Result.ok("出库通知不存在,请先创建出库通知");
        }
        //验证库存
        if (!checkstcoka(wmOmNoticeI)) {
            return Result.ok("库存不足");
        }
        //添加项目主表信息
        int row = wmOmNoticeIMapper.insert(wmOmNoticeI);
        if (row > 0) {
            //添加确认任务单
            getWmOmQmI(wmOmNoticeH, wmOmNoticeI);
        }
        return Result.ok("出库项目添加成功");
    }

    @Override
    public List<WmOmNoticeI> selectByMainNo(String mainNo) {
        return wmOmNoticeIMapper.selectByMainNo(mainNo);
    }

    private void getWmOmQmI(WmOmNoticeH wmOmNoticeH, WmOmNoticeI wmOmNoticeI) {
        WmOmQmI wmOmQmI = new WmOmQmI();
        wmOmQmI.setImCusCode(wmOmNoticeI.getImCusCode());
        wmOmQmI.setOmBeiZhu(wmOmNoticeI.getOmBeiZhu());
        wmOmQmI.setCreateBy(wmOmNoticeI.getCreateBy());
        wmOmQmI.setCreateName(wmOmNoticeI.getCreateName());
        wmOmQmI.setCusCode(wmOmNoticeI.getCusCode());
        wmOmQmI.setOmNoticeId(wmOmNoticeI.getOmNoticeId());
        wmOmQmI.setIomNoticeItem(wmOmNoticeI.getId());
        wmOmQmI.setBinSta("I");//预分配
        MvGoods goods = findMvGoods(wmOmNoticeI.getGoodsId());
        wmOmQmI.setGoodsId(goods.getGoodsId());
        wmOmQmI.setBarcode(goods.getShpTiaoMa());
        wmOmQmI.setGoodsName(goods.getGoodsName());
        wmOmQmI.setBaozhiqi(goods.getBzhiQi());
        wmOmQmI.setGoodsUnit(goods.getBaseunit());
        String prodate = null;
        if (ObjectUtil.isNotEmpty(wmOmNoticeI.getGoodsProData())) {
            prodate = DateUtils.date2Str(wmOmNoticeI.getGoodsProData(), DateUtils.date_sdf.get());
        }
        List<Map<String, Object>> list = findStockList(wmOmNoticeI.getBinOm(), wmOmNoticeI.getBinId(), goods.getGoodsId(), prodate);
        double omcount = 0.00;
        double omcountok = 0.00;
        double omcountwq = 0.00;
        if (StringUtil.isNotEmpty(wmOmQmI.getBaseGoodscount())) {
            omcountok = omcountok + Double.parseDouble(wmOmQmI.getBaseGoodscount());
        }
        try {
            omcount = Double.parseDouble(wmOmNoticeI.getBaseGoodscount());// 总出货数量
        } catch (Exception e) {
            omcount = Double.parseDouble(wmOmNoticeI.getGoodsQua());// 不存在总出货数量
        }
        omcountwq = omcount - omcountok;
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                double bin_qua = Double.valueOf(NotNullUtils.tranObj2Str(list.get(i).get("goods_qua")));

                if (bin_qua > 0 && omcountwq > 0) {
                    if (omcountwq > bin_qua) {
                        wmOmQmI.setBinId(NotNullUtils.tranObj2Str(list.get(i).get("ku_wei_bian_ma")));
                        wmOmQmI.setTinId(NotNullUtils.tranObj2Str(list.get(i).get("bin_id")));
                        wmOmQmI.setBaseUnit(NotNullUtils.tranObj2Str(list.get(i).get("base_unit")));
                        wmOmQmI.setBaseGoodscount(NotNullUtils.tranObj2Str(list.get(i).get("goods_qua")));
                        wmOmQmI.setProData(NotNullUtils.tranObj2Str(list.get(i).get("goods_pro_data")));
                        wmOmQmI.setCusName(NotNullUtils.tranObj2Str(list.get(i).get("zhong_wen_qch")));
                        wmOmQmI.setGoodsName(NotNullUtils.tranObj2Str(list.get(i).get("shp_ming_cheng")));
                        omcountwq = omcountwq - bin_qua;
                        if (wmOmQmI.getGoodsUnit().equals(wmOmQmI.getBaseUnit())) {
                            wmOmQmI.setQmOkQuat(Double.toString(bin_qua));
                            wmOmQmI.setTinTj(String.valueOf(Double.parseDouble(goods.getTiJiCm()) / Double.parseDouble(goods.getChlShl()) * Double.parseDouble(wmOmQmI.getQmOkQuat())));
                            wmOmQmI.setTinZhl(String.valueOf(Double.parseDouble(goods.getZhlKg()) / Double.parseDouble(goods.getChlShl()) * Double.parseDouble(wmOmQmI.getQmOkQuat())));
                        } else {
                            wmOmQmI.setTinTj(String.valueOf(Double.parseDouble(goods.getTiJiCm()) * Double.parseDouble(wmOmQmI.getQmOkQuat()) / Double.parseDouble(goods.getChlShl())));
                            wmOmQmI.setTinZhl(String.valueOf(Double.parseDouble(goods.getZhlKg()) * Double.parseDouble(wmOmQmI.getQmOkQuat()) / Double.parseDouble(goods.getChlShl())));
                            wmOmQmI.setQmOkQuat(Double.toString(bin_qua
                            ));
                        }
                        wmOmQmIMapper.insert(wmOmQmI);
                    } else {
                        try {
                            wmOmQmI.setBinId(NotNullUtils.tranObj2Str(list.get(i).get("ku_wei_bian_ma")));
                            wmOmQmI.setTinId(NotNullUtils.tranObj2Str(list.get(i).get("bin_id")));
                            wmOmQmI.setBaseUnit(NotNullUtils.tranObj2Str(list.get(i).get("base_unit")));
                            wmOmQmI.setBaseGoodscount(Double.toString(omcountwq));
                            wmOmQmI.setProData(NotNullUtils.tranObj2Str(list.get(i).get("goods_pro_data")));
                            wmOmQmI.setCusName(NotNullUtils.tranObj2Str(list.get(i).get("zhong_wen_qch")));
                            wmOmQmI.setGoodsName(NotNullUtils.tranObj2Str(list.get(i).get("shp_ming_cheng")));
                            if (wmOmQmI.getGoodsUnit().equals(wmOmQmI.getBaseUnit())) {
                                wmOmQmI.setQmOkQuat(Double.toString(omcountwq));
                                wmOmQmI.setTinTj(String.valueOf(Double.parseDouble(goods.getTiJiCm()) / Double.parseDouble(goods.getChlShl()) * Double.parseDouble(wmOmQmI.getQmOkQuat())));
                                wmOmQmI.setTinZhl(String.valueOf(Double.parseDouble(goods.getZhlKg()) / Double.parseDouble(goods.getChlShl()) * Double.parseDouble(wmOmQmI.getQmOkQuat())));
                            } else {
                                wmOmQmI.setTinTj(String.valueOf(Double.parseDouble(goods.getTiJiCm()) * omcountwq / Double.parseDouble(goods.getChlShl())));
                                wmOmQmI.setTinZhl(String.valueOf(Double.parseDouble(goods.getZhlKg()) * omcountwq / Double.parseDouble(goods.getChlShl())));
                                wmOmQmI.setQmOkQuat(Double.toString(omcountwq));
                            }
                        } catch (Exception e) {

                        }
                        wmOmQmIMapper.insert(wmOmQmI);
                        wmOmNoticeI.setPlanSta("Y");
                        wmOmNoticeIMapper.updateById(wmOmNoticeI);
                        break;
                    }
                }
            }
            wmOmNoticeI.setPlanSta("Y");
            wmOmNoticeIMapper.updateById(wmOmNoticeI);
        }
    }

    private MvGoods findMvGoods(String goodsId) {
        String goods = null;
        if (goodsId.endsWith("l")) {
            goods = goodsId.substring(0, goodsId.length() - 1);
        } else {
            goods = goodsId;
        }
        QueryWrapper<MvGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_code", goods);
        MvGoods mvGoods = mvGoodsMapper.selectOne(queryWrapper);

        return mvGoods;
    }

    private boolean checkstcoka(WmOmNoticeI wmOmNoticeI) {

        String goods = null;
        if (!StringUtil.isEmpty(wmOmNoticeI.getGoodsId())) {
            if (wmOmNoticeI.getGoodsId().endsWith("l")) {
                goods = wmOmNoticeI.getGoodsId().substring(0, wmOmNoticeI.getGoodsId().length() - 1);
            } else {
                goods = wmOmNoticeI.getGoodsId();
            }
        }
        //查询库存信息
        List<Map<String, Object>> list = findStockList(wmOmNoticeI.getBinOm(), wmOmNoticeI.getBinId(), goods, null);

        if (list.size() > 0) {
            BigDecimal totalMoney =list.stream().map(map->new BigDecimal(Double.parseDouble(map.get("goods_qua").toString()))).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (Double.parseDouble(totalMoney.toString()) >= Double.parseDouble(wmOmNoticeI.getBaseGoodscount())) {
                return true;
            }

        }
        return false;
    }

    private List<Map<String, Object>> findStockList(String binId, String tinId, String goods, String prodate) {
        List<Map<String, Object>> list = wmOmNoticeIMapper.findStock(binId, tinId, goods, prodate, null,null, null);
        return list;
    }
}
