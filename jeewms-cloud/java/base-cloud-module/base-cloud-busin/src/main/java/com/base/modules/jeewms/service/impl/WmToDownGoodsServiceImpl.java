package com.base.modules.jeewms.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.modules.jeewms.entity.WmOmNoticeH;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.base.modules.jeewms.mapper.WmOmNoticeHMapper;
import com.base.modules.jeewms.mapper.WmOmNoticeIMapper;
import org.apache.commons.lang.StringUtils;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.WmOmQmI;
import com.base.modules.jeewms.entity.WmToDownGoods;
import com.base.modules.jeewms.mapper.WmOmQmIMapper;
import com.base.modules.jeewms.mapper.WmToDownGoodsMapper;
import com.base.modules.jeewms.service.IWmToDownGoodsService;
import com.base.modules.jeewms.vo.BatchLoadingReviewVo;
import com.base.modules.jeewms.vo.EditBatchWmToDownGoodsVo;
import com.base.modules.jeewms.vo.OrderPickingVo;
import com.base.modules.util.ConstUtil;
import com.base.modules.util.NotNullUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 下架明细
 * @Author: base-boot
 * @Date: 2021-05-20
 * @Version: V1.0
 */
@Service
public class WmToDownGoodsServiceImpl extends ServiceImpl<WmToDownGoodsMapper, WmToDownGoods> implements IWmToDownGoodsService {

    @Autowired
    private WmToDownGoodsMapper wmToDownGoodsMapper;

    @Autowired
    private WmOmQmIMapper wmOmQmIMapper;

    @Autowired
    private WmOmNoticeHMapper wmOmNoticeHMapper;

    @Autowired
    private WmOmNoticeIMapper wmOmNoticeIMapper;

    @Override
    public List<WmToDownGoods> selectByMainId(String mainId) {
        return wmToDownGoodsMapper.selectByMainId(mainId);
    }

    /**
     * @param list
     * @return
     * @Describe 批量更改
     * @Author zly
     * @Create Date 2021/5/21
     */
    @Override
    public Result<?> editBatch(List<EditBatchWmToDownGoodsVo> list) {
        int eer = list.size();
        for (EditBatchWmToDownGoodsVo vo : list) {
            WmToDownGoods en = new WmToDownGoods();
            BeanUtils.copyProperties(vo, en, NotNullUtils.getNullPropertyNames(vo));
            int row = wmToDownGoodsMapper.updateById(en);
            eer = eer - row;
        }
        if (eer == 0) {
            return Result.ok("更改成功");
        }
        return Result.ok("更改失败" + eer + "条");
    }


    @Override
    public Result<?> dofubatch(List<String> ids,Integer tenantId) {
        int eer = ids.size();
        for (String id : ids) {
            //获取当前下架信息
            WmToDownGoods en = wmToDownGoodsMapper.selectById(id);
            //修改下架状态
            en.setConfirmationDate(new Date());
            en.setTenantId(tenantId);
//            en.setDownSta(ConstUtil.wm_sta5);
            en.setDownSta(ConstUtil.wm_sta6);
            int row = wmToDownGoodsMapper.updateById(en);
            eer = eer - row;
        }
        if (eer == 0) {
            return Result.ok("复核成功");
        }
        return Result.ok("复核失败" + eer + "条");
    }

    /**
     * @param vo
     * @return
     * @Describe 下架明细-装车复核-批量复核
     * @Author zly
     * @Create Date 2021/5/21
     */
    @Override
    public Result<?> batchLoadingReview(List<BatchLoadingReviewVo> vo) {
        int eer = vo.size();
        for (BatchLoadingReviewVo entity : vo) {
            //获取当前下架信息
            WmToDownGoods en = wmToDownGoodsMapper.selectById(entity.getId());
            BeanUtils.copyProperties(vo, en, NotNullUtils.getNullPropertyNames(vo));
            //修改下架状态
            en.setConfirmationDate(new Date());
            en.setDownSta(ConstUtil.wm_sta5);
            int row = wmToDownGoodsMapper.updateById(en);
            eer = eer - row;
        }
        if (eer == 0) {
            return Result.ok("复核成功");
        }
        return Result.ok("复核失败" + eer + "条");
    }

    /**
     * @return
     * @Describe 按单拣货保存
     * @Author zly
     * @Create Date 2021/6/11
     */
    @Override
    public Result<?> orderPickingEdit(OrderPickingVo orderPickingVo) {
        //查询当前拣货信息
        WmToDownGoods en = wmToDownGoodsMapper.selectById(orderPickingVo.getId());
        if (StringUtils.isEmpty(orderPickingVo.getGoodsQuaok())) {
            return Result.ok("复核数量不能为空！！");
        }
        if (new BigDecimal(orderPickingVo.getGoodsQuaok()).compareTo(new BigDecimal(orderPickingVo.getGoodsQua())) == 0) {
            en.setDownSta(ConstUtil.wm_sta9);
        }

        int row = wmToDownGoodsMapper.updateById(en);
        if (row > 0) {
            return Result.ok("拣单成功" + orderPickingVo.getGoodsQuaok());
        }
        return Result.ok("拣单失败");
    }

    @Override
    public List<String> getWmOmIList(String userName) {
        QueryWrapper<WmOmQmI> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WmOmQmI::getAssignTo,userName).or().isNull(WmOmQmI::getAssignTo);
        List<WmOmQmI> list = wmOmQmIMapper.selectList(queryWrapper);
        List<String> ids = null;
        if(!list.isEmpty()){
             ids = list.stream().map(WmOmQmI::getOmNoticeId).collect(Collectors.toList());
        }
        return ids;
    }

    @Override
    public Result<?> queryWmOmNoticeIByMainNo(String id) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.queryWmOmNoticeIByMainNo(id);
        if(wmOmNoticeH == null){
            return Result.error("该运单号【" + id + "】未找到对应订单！");
        }
        if("复核完成".equals(wmOmNoticeH.getOmSta())){
            return Result.error("该运单号【" + id + "】已复核完成！");
        }
        List<WmOmNoticeI> wmOmNoticeIList = wmOmNoticeIMapper.selectByMainNo(wmOmNoticeH.getOmNoticeId());
        if(wmOmNoticeIList != null&&wmOmNoticeIList.size() > 0){
            return Result.ok(wmOmNoticeIList);
        }else{
            return Result.error("该运单号【" + id + "】未找到对应订单！");
        }
    }

    @Override
    @Transactional
    public Result<?> scanCodeReview(String id, String barCode) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.queryWmOmNoticeIByMainNo(id);
        QueryWrapper<WmOmNoticeI> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("om_notice_id",wmOmNoticeH.getOmNoticeId());
        queryWrapper.eq("BARCODE",barCode);
        WmOmNoticeI wmOmNoticeI = wmOmNoticeIMapper.selectOne(queryWrapper);
        if(wmOmNoticeI!=null){
            if("已复核".equals(wmOmNoticeI.getOmSta())){
                return Result.error("该商品【"+wmOmNoticeI.getGoodsName()+"】已复核完成！");
            }
            Integer count = Integer.parseInt(StringUtils.isNotEmpty(wmOmNoticeI.getGoodsQuaok())?wmOmNoticeI.getGoodsQuaok():"0") + 1;
            wmOmNoticeI.setGoodsQuaok(String.valueOf(count));
            if(wmOmNoticeI.getGoodsQua().equals(wmOmNoticeI.getGoodsQuaok())){
                wmOmNoticeI.setOmSta("已复核");
            }
            wmOmNoticeIMapper.updateById(wmOmNoticeI);
            List<WmOmNoticeI> wmOmNoticeIList = wmOmNoticeIMapper.selectByMainNo(wmOmNoticeH.getOmNoticeId());
            return Result.ok(wmOmNoticeIList);
        }else{
            return Result.error("该条码【" + barCode + "】不属于该订单！");
        }
    }

    @Override
    @Transactional
    public Result<?> finishReview(String id, String packingNo, String childWaybillCount) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.queryWmOmNoticeIByMainNo(id);
        if(wmOmNoticeH == null){
            return Result.error("该运单号【" + id + "】未找到对应订单！");
        }
        QueryWrapper<WmOmNoticeI> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("om_notice_id",wmOmNoticeH.getOmNoticeId());
        List<WmOmNoticeI> wmOmNoticeIList = wmOmNoticeIMapper.selectList(queryWrapper1);
        for(WmOmNoticeI wmOmNoticeI : wmOmNoticeIList){
            if(!wmOmNoticeI.getGoodsQua().equals(wmOmNoticeI.getGoodsQuaok())){
                return Result.error("商品【"+wmOmNoticeI.getGoodsName()+"】应收数量和实收数量不相等，无法完成复核！");
            }
        }
        wmOmNoticeH.setOmSta("复核完成");
        wmOmNoticeHMapper.updateById(wmOmNoticeH);

        return Result.ok("复核完成");
    }

    @Override
    @Transactional
    public Result<?> afreshReview(String id) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.queryWmOmNoticeIByMainNo(id);
        if(wmOmNoticeH == null){
            return Result.error("该运单号【" + id + "】未找到对应订单！");
        }
        wmOmNoticeH.setOmSta("复核中");
        wmOmNoticeHMapper.updateById(wmOmNoticeH);
        QueryWrapper<WmOmNoticeI> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("om_notice_id",wmOmNoticeH.getOmNoticeId());
        List<WmOmNoticeI> wmOmNoticeIList = wmOmNoticeIMapper.selectList(queryWrapper1);
        for(WmOmNoticeI wmOmNoticeI : wmOmNoticeIList){
            wmOmNoticeI.setOmSta("未复核");
            wmOmNoticeI.setGoodsQuaok("0");
            wmOmNoticeIMapper.updateById(wmOmNoticeI);
        }
        return Result.ok(wmOmNoticeIList);
    }

//    @Override
//    public Result<?> scanWeigh(String id) {
//        QueryWrapper<WmsJdExpressRecord> queryWrapper  = new QueryWrapper<>();
//        queryWrapper.eq("delivery_id",id);
//        WmsJdExpressRecord wmsJdExpressRecord = wmsJdExpressRecordMapper.selectOne(queryWrapper);
//        if(wmsJdExpressRecord == null){
//            return Result.error("该运单号【" + id + "】未找到对应订单！");
//        }
//        Double weigh = wmsJdExpressRecord.getWeight();
//        return Result.OK(weigh);
//    }
//
//    @Override
//    @Transactional
//    public Result<?> updateWeigh(String id, String weigh) {
//        QueryWrapper<WmsJdExpressRecord> queryWrapper  = new QueryWrapper<>();
//        queryWrapper.eq("delivery_id",id);
//        WmsJdExpressRecord wmsJdExpressRecord = wmsJdExpressRecordMapper.selectOne(queryWrapper);
//        if(wmsJdExpressRecord == null){
//            return Result.error("该运单号【" + id + "】未找到对应订单！");
//        }
//        wmsJdExpressRecord.setWeight(Double.valueOf(weigh));
//        wmsJdExpressRecordMapper.updateById(wmsJdExpressRecord);
//        return Result.ok("操作成功");
//    }

}

