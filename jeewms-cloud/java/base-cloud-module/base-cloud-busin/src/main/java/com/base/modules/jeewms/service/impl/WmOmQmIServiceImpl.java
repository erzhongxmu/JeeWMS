package com.base.modules.jeewms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.common.base.dto.WmsOrderItemCallbackDTO;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.DateUtils;
import com.base.modules.client.ISysBaseAPIClient;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.WmOmNoticeIMapper;
import com.base.modules.jeewms.mapper.WmOmQmIMapper;
import com.base.modules.jeewms.mapper.WmToDownGoodsMapper;
import com.base.modules.jeewms.mapper.WvStockMapper;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.vo.*;

import com.base.modules.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.SysUserRemoteApi;
import org.jeecg.common.system.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 下架任务
 * @Author: base-boot
 * @Date: 2021-05-20
 * @Version: V1.0
 */
@Service
public class WmOmQmIServiceImpl extends ServiceImpl<WmOmQmIMapper, WmOmQmI> implements IWmOmQmIService {

    @Autowired
    private WmOmQmIMapper wmOmQmIMapper;
    @Autowired
    private WmToDownGoodsMapper wmToDownGoodsMapper;

    @Autowired
    private ISysBaseAPIClient sysBaseAPIClient;
    @Autowired
    private WvStockMapper wvStockMapper;
    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private IWmsUserPartTypeService wmsUserPartTypeService;
    @Autowired
    private SysUserRemoteApi sysUserRemoteApi;
    @Autowired
    private WmOmNoticeIMapper wmOmNoticeIMapper;

    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;

    @Autowired
    private IMdGoodsItemService mdGoodsItemService;


    @Autowired
    private IWvStockSttService wvStockSttService;
    @Autowired
    private PltnSetState pltnSetState;

    @Autowired
    private IWvStockSttQueryService wvStockSttQueryService;

    @Override
    @Transactional
    public void delMain(String id) {
        wmToDownGoodsMapper.deleteByMainId(id);
        wmOmQmIMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            wmToDownGoodsMapper.deleteByMainId(id.toString());
            wmOmQmIMapper.deleteById(id);
        }
    }

    /**
     * @param ids
     * @return
     * @Describe 下架功能
     * @Author zly
     * @Create Date 2021/5/20
     */
    @Override
    public Result<?> dotowavedown(List<String> ids, String query09, String binSta, List<WvStockSttQuery> wvStockStt ) {
        synchronized (this) {
            System.err.println(JSON.toJSONString(ids.size()));
            List<String> ordersNumList = new ArrayList<>();
            int eer = ids.size();
            int i = 0;

            // 查询主表信息
            WmOmQmI wmOmQmIOms = wmOmQmIMapper.selectById(ids.get(0));
            QueryWrapper<WmOmNoticeH> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(WmOmNoticeH::getOmNoticeId,wmOmQmIOms.getOmNoticeId());
            List<WmOmNoticeH> list = wmOmNoticeHService.list(queryWrapper);
            WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
            if(CollectionUtil.isNotEmpty(list)){
                wmOmNoticeH = list.get(0);
            }

            for (String id : ids) {
                WmOmQmI en = wmOmQmIMapper.selectById(id);
                if(CollectionUtil.isNotEmpty(wvStockStt)){
                    List<WvStockSttQuery>  wvStockStt1 = wvStockSttQueryService.lambdaQuery()
                            .eq(WvStockSttQuery::getBinId, en.getTinId())
                            .eq(WvStockSttQuery::getGoodsQua,en.getBaseGoodscount().split("\\.")[0])
                            .eq(WvStockSttQuery::getGoodsProData,en.getProData())
                            .eq(WvStockSttQuery::getGoodsId,en.getGoodsId())
                            .list();
                    if(CollectionUtil.isEmpty(wvStockStt1)){
                        throw new JeecgBootException("已成功:"+i+",请核实该箱信息"+en.getTinId()+"或者箱子已出");
                    }
                }
                if (StringUtils.isNotEmpty(query09)) {
                    if (new BigDecimal(query09).compareTo(new BigDecimal(en.getBaseGoodscount())) == 1) {
                        throw new JeecgBootException("下架数量不能大于出库数量");
                    }
                    en.setBaseGoodscount(query09);
                    en.setQmOkQuat(query09);
                }
                ordersNumList.add(en.getImCusCode());
                //配置下架详情参数
                WmToDownGoods downGoods = getWmToDownGoods(en);
                //保存下架详情
                wmToDownGoodsMapper.insert(downGoods);
                //修改下架状态
                en.setBinSta(binSta);
                int row = wmOmQmIMapper.updateById(en);
                eer = eer - row;
                i++;

                pltnSetState.wmsOmSetState("number",wmOmNoticeH,downGoods);
            }
            pltnSetState.wmsOmSetState("state",wmOmNoticeH,null);
            if (eer == 0) {
                return Result.ok("下架成功");
            }

            return Result.ok("下架失败" + eer + "条");
        }
    }

    /**
     * @param ids
     * @return
     * @Describe 下架功能
     * @Author zly
     * @Create Date 2021/5/20
     */
    @Transactional
    @Override
    public Result<?> dotowavedown1(List<String> ids, String query09, Integer tenantId, String binSta) {
        synchronized (this) {
            System.err.println(JSON.toJSONString(ids.size()));
            List<String> ordersNumList = new ArrayList<>();
            WmOmQmI en = new WmOmQmI();
            WmToDownGoods downGoods = new WmToDownGoods();
            int eer = ids.size();
            String code = "";
            JSONObject parse = null;
            for (String id : ids) {
                //获取当前下架信息
                en = wmOmQmIMapper.selectById(id);
                if (StringUtils.isNotEmpty(query09)) {
                    if (new BigDecimal(query09).compareTo(new BigDecimal(en.getBaseGoodscount())) == 1) {
                        throw new JeecgBootException("下架数量不能大于出库数量");
                    }
                    en.setBaseGoodscount(query09);
                    en.setQmOkQuat(query09);
                    en.setTenantId(tenantId);
                }
                MdGoods mdGoods = mdGoodsService.getOne(new LambdaQueryWrapper<MdGoods>().eq(MdGoods::getShpBianMa, en.getGoodsId()));

                Map map1 = new LinkedHashMap();
                map1.put("storageMerchantCode", en.getBinId());
                map1.put("storageRecordNo", en.getOmNoticeId());
                Map map2 = new LinkedHashMap();
                List<Map> mapList = new ArrayList<>();
                map2.put("goodsBarcode", mdGoods.getShpTiaoMa());
                map2.put("smallGoodsBarcode", mdGoods.getShpTiaoMa());
                map2.put("type", "1");
                map2.put("goodsItemCnt", en.getQmOkQuat());
                map2.put("goodsCnt", en.getQmOkQuat());
                map2.put("goodsUnitCnt", en.getGoodsUnit());
                map2.put("productionDate", en.getProData());
                mapList.add(map2);
                map1.put("goodsDetails", JSON.toJSONString(mapList));

                JSONObject jsonObj = new JSONObject(map1);
                String Json = jsonObj.toString();
                String post = HttpUtil.post("https://retail.danbagui.com/api/storage/sync/record/callback", Json);
                System.out.println("输出参数：" + post);
                parse = (JSONObject) JSONObject.parse(post);
                code = parse.get("code") != null ? parse.get("code").toString() : "";

                if (org.apache.commons.lang3.StringUtils.isNotEmpty(code) && "SUCCESS".equals(code)) {
                    ordersNumList.add(en.getImCusCode());
                    //配置下架详情参数
                    downGoods = getWmToDownGoods(en);
                    //保存下架详情
                    wmToDownGoodsMapper.insert(downGoods);
                    //修改下架状态
                    en.setBinSta(binSta);
                    int row = wmOmQmIMapper.updateById(en);
                    eer = eer - row;
                } else {
                    throw new JeecgBootException("同步信息失败！" + parse.get("msg"));
                }
            }
            if (eer == 0) {
                return Result.OK("仓库已下架，同步信息成功！");
            } else {
                return Result.error("下架失败" + eer + "条");
            }
        }
    }


    private List<WmsOrderItemCallbackDTO> getBatchByOrderId(String orderId) {
        return baseMapper.getBatchByOrderId(orderId);
    }

    /**
     * @param list
     * @return
     * @Describe 批量更改
     * @Author zly
     * @Create Date 2021/5/20
     */
    @Override
    public Result<?> editBatch(List<EditBatchWmQmIVo> list) {
        int eer = list.size();
        for (EditBatchWmQmIVo vo : list) {
            WmOmQmI en = new WmOmQmI();
            BeanUtils.copyProperties(vo, en, NotNullUtils.getNullPropertyNames(vo));
            int row = wmOmQmIMapper.updateById(en);
            eer = eer - row;
        }
        if (eer == 0) {
            return Result.ok("下架更改成功");
        }
        return Result.ok("下架更改失败" + eer + "条");
    }

    /**
     * @param ids
     * @return
     * @Describe 批量确认任务
     * @Author zly
     * @Create Date 2021/5/24
     */
    @Override
    @Transactional
    public Result<?> doassignbatch(List<String> ids) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<WmOmQmI>  queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<WmOmQmI> wmOmQmIS1 = wmOmQmIMapper.selectList(queryWrapper);
        List<String> idom = new ArrayList<>();
        String goodsId ="";
        for (WmOmQmI wmOmQmI : wmOmQmIS1) {
            idom.add(wmOmQmI.getOmNoticeId());
            goodsId = wmOmQmI.getGoodsId();
        }
        QueryWrapper<WmOmNoticeH>  queryWrapperh = new QueryWrapper<>();
        queryWrapperh.in("om_notice_id", idom);
        List<WmOmNoticeH> list = wmOmNoticeHService.list(queryWrapperh);
        List<String> idzx = new ArrayList<>();

        for (WmOmNoticeH wmOmNoticeH : list) {
            System.out.println(wmOmNoticeH.getOrderTypeCode()+"===");
            if("19".equals(wmOmNoticeH.getOrderTypeCode())|| "13".equals(wmOmNoticeH.getOrderTypeCode())){
            }else {
                if(StringUtils.isEmpty(wmOmNoticeH.getU8Djcode1())){
                    throw  new JeecgBootException("装箱单为空");
                }
                idzx.add(wmOmNoticeH.getU8Djcode1());
            }
        }
        QueryWrapper<WmOmNoticeH>  queryWrapperzx = new QueryWrapper<>();

        List<WmOmNoticeH> listzx = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(idzx)){
                 queryWrapperzx.in("u8_djcode1", idzx);
                 listzx = wmOmNoticeHService.list(queryWrapperzx);
            }else {
                 listzx = list;
            }



        if(CollectionUtil.isEmpty(listzx)){
            for (String id : ids) {
                WmOmQmI en = wmOmQmIMapper.selectById(id);
                if(!StringUtils.isEmpty(en.getFirstRq())){
                    continue;
                }
                en.setBinSta("N");
                wmOmQmIMapper.updateById(en);
            }
            return Result.ok("任务确认成功");
        }
        List<String> idomqm = new ArrayList<>();
        for (WmOmNoticeH wmOmNoticeH : listzx) {
            idomqm.add(wmOmNoticeH.getOmNoticeId());
        }
        QueryWrapper<WmOmQmI>  queryWrapperom = new QueryWrapper<>();
        queryWrapperom.in("om_notice_id", idomqm);
        queryWrapperom.orderByDesc("first_rq");
        List<WmOmQmI> wmOmQmISom = wmOmQmIMapper.selectList(queryWrapperom);
        String firstRq = "";
        int max = 0;
        if(CollectionUtil.isNotEmpty(wmOmQmISom)){
           firstRq = wmOmQmISom.get(0).getFirstRq();
        }
        if(!StringUtils.isEmpty(firstRq)){
            max = Integer.parseInt(firstRq.substring(1));
        }
        for (String id : ids) {
            WmOmQmI en = wmOmQmIMapper.selectById(id);
            if(!StringUtils.isEmpty(en.getFirstRq())){
               continue;
            }
            max++;
            en.setBinSta("N");
            en.setFirstRq("T" + String.format("%04d", max));
            wmOmQmIMapper.updateById(en);
        }

        QueryWrapper<WmOmQmI>  queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.in("om_notice_id", idom);
        queryWrapper2.in("goods_id", goodsId);
        List<WmOmQmI> wmOmQmIS2 = wmOmQmIMapper.selectList(queryWrapper2);

        //去重（无序）
        List<WmOmQmI> wmOmQmIS3=wmOmQmIS2.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->
                new TreeSet<WmOmQmI>(Comparator.comparing(WmOmQmI::getTinId))), ArrayList::new));
        int i = 0;
        for (WmOmQmI wmOmQmI : wmOmQmIS2) {
            if(StringUtils.isNotEmpty(wmOmQmI.getSecondRq())){
                i++;
            }
        }


     /*   // 循环选择的ids 存入SecondRq当中
        for (String id : ids) {
            WmOmQmI en = wmOmQmIMapper.selectById(id);
            if(!StringUtils.isEmpty(en.getSecondRq())){
                continue;
            }
            i++;
            en.setSecondRq(i+"/"+wmOmQmIS2.size());
            wmOmQmIMapper.updateById(en);
        }*/
        // 循环选择的ids 存入SecondRq当中
        for (String id : ids) {
            WmOmQmI en = wmOmQmIMapper.selectById(id);
            if(!StringUtils.isEmpty(en.getSecondRq())){
                continue;
            }
            QueryWrapper<WmOmQmI> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("om_notice_id",en.getOmNoticeId());
            queryWrapper1.eq("tin_id",en.getTinId());
            List<WmOmQmI> wmOmQmIList = wmOmQmIMapper.selectList(queryWrapper1);
            String s = "";
            for (WmOmQmI wmOmQmI : wmOmQmIList) {
                if(StringUtils.isNotEmpty(wmOmQmI.getSecondRq())){
                    s = wmOmQmI.getSecondRq();
                }
            }
            if(s !=null && s !=""){
                for (WmOmQmI wmOmQmI : wmOmQmIList) {
                    wmOmQmI.setSecondRq(s);
                    wmOmQmIMapper.updateById(wmOmQmI);
                }
            }else {
                i++;
                en.setSecondRq(i+"/"+wmOmQmIS3.size());
                wmOmQmIMapper.updateById(en);
            }
        }








        return Result.ok("任务确认成功");
    }

    private int getOffTheShelfDirectly(WmOmQmI en) {
        //配置下架详情参数
        WmToDownGoods downGoods = getWmToDownGoods(en);
        //保存下架详情
        wmToDownGoodsMapper.insert(downGoods);
        //修改下架状态
        en.setBinSta(ConstUtil.wm_h);
        int row = wmOmQmIMapper.updateById(en);
        return row;
    }

    /**
     * @param vo
     * @return
     * @Describe 波次生成和波次生成到指定容器
     * @Author zly
     * @Create Date 2021/5/24
     */
    @Override
    public Result<?> wavebatch(WavebatchVo vo) {
        synchronized (this) {
            String firstContainer = vo.getFirstRq();
            String waveId = DateUtils.date2Str(DateUtils.yyyymmddhhmmss.get());

            int eer = vo.getIds().size();
            for (String id : vo.getIds()) {
                //获取波次生成数据
                WmOmQmI en = wmOmQmIMapper.selectById(id);
                en.setWaveId("BC" + waveId);
                en.setBinSta("N");
                if (StringUtils.isNotEmpty(firstContainer)) {
                    en.setFirstRq(firstContainer);
                }
                int row = wmOmQmIMapper.updateById(en);
                eer = eer - row;
            }
            if (eer == 0) {
                return Result.ok("任务确认成功");
            }
            return Result.ok("任务确认失败" + eer + "条");
        }
    }

    /**
     * @param vo
     * @return
     * @Describe 批量设置接收人
     * @Author zly
     * @Create Date 2021/5/24
     */
    @Override
    public Result<?> insertAssignToBatch(InsertAssignToBatchVo vo) {
        String assignTo = vo.getAssignTo();
        int eer = vo.getIds().size();
        for (String id : vo.getIds()) {
            //获取数据
            WmOmQmI en = wmOmQmIMapper.selectById(id);
            en.setAssignTo(assignTo);
            if (ConstUtil.sptmon.equals(ConstUtil.wm_y)) {
                int row = getOffTheShelfDirectly(en);
                eer = eer - row;
            } else {
                int row = wmOmQmIMapper.updateById(en);
                eer = eer - row;
            }
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            String msgContent = assignTo + ",您有一条出库单号为" + en.getOmNoticeId() + ",商品编码为" + en.getGoodsId() + "的拣货任务,请尽快完成拣货";
            sysBaseAPIClient.sendSysAnnouncement(loginUser.getUsername(), assignTo, "任务通知", msgContent);
        }
        if (eer == 0) {
            return Result.ok("任务确认成功");
        }
        return Result.ok("任务确认失败" + eer + "条");
    }

    @Override
    public Result<?> pdaDotowavedown(List<WmOmQmI> wmOmQmIList, String binSta) {
        int eer = wmOmQmIList.size();
        for (WmOmQmI wmOmQmI : wmOmQmIList) {
            if (StringUtils.isEmpty(wmOmQmI.getBinId())) {
                throw new JeecgBootException("库位不能为空");
            }
            if (StringUtils.isEmpty(wmOmQmI.getTinId())) {
                throw new JeecgBootException("托盘不能为空");
            }
            //获取当前下架信息
            WmOmQmI en = wmOmQmIMapper.selectById(wmOmQmI.getId());
            en.setBinId(wmOmQmI.getBinId());
            en.setTinId(wmOmQmI.getTinId());
            en.setQmOkQuat(wmOmQmI.getQmOkQuat());

            QueryWrapper<WmOmNoticeI> query = new QueryWrapper<>();
            query.eq("om_notice_id", en.getOmNoticeId());
            List<WmOmNoticeI> list = wmOmNoticeIMapper.selectList(query);

            if (list != null && !list.isEmpty()) {
                WmOmNoticeI noticeI = list.get(0);
                String waitQua = noticeI.getWaitQua();
                if (StringUtils.isNotEmpty(waitQua)) {
                    BigDecimal wait = new BigDecimal(waitQua);
                    BigDecimal qua = new BigDecimal(wmOmQmI.getQmOkQuat());
                    if (wait.compareTo(qua) > 0) {
                        wait = wait.subtract(qua);
                        noticeI.setWaitQua(wait.toString());
                        wmOmNoticeIMapper.updateById(noticeI);
                    }
                }
            }
            //查询库存
            WvStock wvStock = wvStockMapper.selectKCByBinIdAndTindId(wmOmQmI.getBinId(), wmOmQmI.getTinId(), en.getGoodsId());
            if (wvStock == null) {
                throw new JeecgBootException("没有商品库存");
            }
            if (new BigDecimal(wmOmQmI.getQmOkQuat()).compareTo(new BigDecimal(wvStock.getBaseGoodscount())) == 1) {
                throw new JeecgBootException("商品库存不足");
            }

            en.setQmOkQuat(wmOmQmI.getQmOkQuat());
            //修改下架状态
            en.setBinSta(binSta);
            int row = wmOmQmIMapper.updateById(en);
            //配置下架详情参数
            WmToDownGoods downGoods = getWmToDownGoods(en);
            //保存下架详情
            wmToDownGoodsMapper.insert(downGoods);
            eer = eer - row;
        }
        if (eer == 0) {
            return Result.ok("下架成功");
        }
        return Result.ok("下架失败" + eer + "条");
    }

    @Transactional
    @Override
    public Result<?> pdaDotowavedown1(List<WmOmQmI> wmOmQmIList, String binSta) {
        int eer = wmOmQmIList.size();
        WmOmQmI en = new WmOmQmI();
        for (WmOmQmI wmOmQmI : wmOmQmIList) {
            if (StringUtils.isEmpty(wmOmQmI.getBinId())) {
                throw new JeecgBootException("库位不能为空");
            }
            if (StringUtils.isEmpty(wmOmQmI.getTinId())) {
                throw new JeecgBootException("托盘不能为空");
            }
            //获取当前下架信息
            en = wmOmQmIMapper.selectById(wmOmQmI.getId());
            en.setBinId(wmOmQmI.getBinId());
            en.setTinId(wmOmQmI.getTinId());
            en.setQmOkQuat(wmOmQmI.getQmOkQuat());

            QueryWrapper<WmOmNoticeI> query = new QueryWrapper<>();
            query.eq("om_notice_id", en.getOmNoticeId());
            List<WmOmNoticeI> list = wmOmNoticeIMapper.selectList(query);

            if (list != null && !list.isEmpty()) {
                WmOmNoticeI noticeI = list.get(0);
                String waitQua = noticeI.getWaitQua();
                if (StringUtils.isNotEmpty(waitQua)) {
                    BigDecimal wait = new BigDecimal(waitQua);
                    BigDecimal qua = new BigDecimal(wmOmQmI.getQmOkQuat());
                    if (wait.compareTo(qua) > 0) {
                        wait = wait.subtract(qua);
                        noticeI.setWaitQua(wait.toString());
                        wmOmNoticeIMapper.updateById(noticeI);
                    }
                }
            }
            //查询库存
            WvStock wvStock = wvStockMapper.selectKCByBinIdAndTindId(wmOmQmI.getBinId(), wmOmQmI.getTinId(), en.getGoodsId());
            if (wvStock == null) {
                throw new JeecgBootException("没有商品库存");
            }
            if (new BigDecimal(wmOmQmI.getQmOkQuat()).compareTo(new BigDecimal(wvStock.getBaseGoodscount())) == 1) {
                throw new JeecgBootException("商品库存不足");
            }

            en.setQmOkQuat(wmOmQmI.getQmOkQuat());
            //修改下架状态
            en.setBinSta(binSta);
            int row = wmOmQmIMapper.updateById(en);
            //配置下架详情参数
            WmToDownGoods downGoods = getWmToDownGoods(en);
            //保存下架详情
            wmToDownGoodsMapper.insert(downGoods);
            eer = eer - row;
        }
        String code = "";
        JSONObject parse = null;
        if (eer == 0) {
            MdGoods mdGoods = mdGoodsService.getOne(new LambdaQueryWrapper<MdGoods>().eq(MdGoods::getShpBianMa, en.getGoodsId()));
            Map map1 = new LinkedHashMap();
            map1.put("storageMerchantCode", en.getBinId());
            map1.put("storageRecordNo", en.getOmNoticeId());
            Map map2 = new LinkedHashMap();
            List<Map> mapList = new ArrayList<>();
            map2.put("goodsBarcode", mdGoods.getShpTiaoMa());
            map2.put("smallGoodsBarcode", mdGoods.getShpTiaoMa());
            map2.put("goodsItemCnt", en.getQmOkQuat());
            map2.put("type", "1");
            map2.put("goodsUnitCnt", mdGoods.getChlShl());
            map2.put("goodsCnt", en.getQmOkQuat());
            map2.put("productionDate", en.getProData());
            mapList.add(map2);
            map1.put("goodsDetails", JSON.toJSONString(mapList));

            JSONObject jsonObj = new JSONObject(map1);
            String Json = jsonObj.toString();
//            JSONObject post = WebServiceUtil.httpPost(""https://retail.danbagui.com/api/storage/sync/record/callback",Json);
//            String post = HttpUtil.post(""https://retail.danbagui.com/api/storage/sync/record/callback", Json);
            String post = HttpUtil.post("https://retail.danbagui.com/api/storage/sync/record/callback", Json);
            parse = (JSONObject) JSONObject.parse(post);
            code = parse.get("code") != null ? parse.get("code").toString() : "";
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(code) && "SUCCESS".equals(code)) {
            return Result.OK("仓库已下架，同步信息成功！");
        } else {
            return Result.error("同步信息失败！" + parse.get("msg"));
        }
    }

    /**
     * 任务接收人下拉
     *
     * @param idList
     * @return
     */
    @Override
    public List<SysUser> getJobPersonList(List<String> idList) {

        List<SysUser> userList = new ArrayList<>();
        List<WmsUserPartType> wmsUserPartTypeList = wmsUserPartTypeService.lambdaQuery().in(WmsUserPartType::getPartTypeId, idList).list();
        if (wmsUserPartTypeList.size() > 0) {
            Set<String> userNameList = new HashSet<>();
            for (WmsUserPartType wmsUserPartType : wmsUserPartTypeList) {
                userNameList.add(wmsUserPartType.getUserName());
            }
            if (userNameList.size() > 0) {
                userList = sysUserRemoteApi.userList(new ArrayList<String>(userNameList));
            }
        } else {
            Set<String> userNameList = new HashSet<>();
            userList = sysUserRemoteApi.userList(new ArrayList<String>(userNameList));
        }
        return userList;
    }

    @Override
    public boolean checkOmQyt(String orderNo) {
        return baseMapper.checkOmQyt(orderNo) == 0 ? true : false;
    }

    @Override
    public List<String> getOrderNoByWave(String waveId) {
        return baseMapper.getOrderNoByWave(waveId);
    }

    @Override
    public List<String> getGoodsDetailByDelivery(String orderSn) {
        return baseMapper.getGoodsDetailByDelivery(orderSn);
    }

    @Override
    public List<String> queryOmNoticeIdList() {
        return wmOmQmIMapper.queryOmNoticeIdList();
    }

    private WmToDownGoods getWmToDownGoods(WmOmQmI en) {
        WmToDownGoods downGoods = new WmToDownGoods();
        downGoods.setBinIdFrom(en.getTinId());//下架托盘
        downGoods.setKuWeiBianMa(en.getBinId());//储位
        downGoods.setBinIdTo(en.getTinId());//到托盘
        downGoods.setCusCode(en.getCusCode());//货主
        downGoods.setCusName(en.getCusName());//货主
        downGoods.setGoodsId(en.getGoodsId());//商品编码
        downGoods.setGoodsProData(en.getProData());//生产日期
        downGoods.setGoodsBatch(en.getGoodsBatch());//批次
        downGoods.setOrderId(en.getOmNoticeId());//出货通知单
        downGoods.setOrderIdI(en.getId());//出货通知项目
        downGoods.setBaseUnit(en.getBaseUnit());//基本单位
        downGoods.setBaseGoodscount(en.getBaseGoodscount());//基本单位数量
        downGoods.setGoodsUnit(en.getGoodsUnit());//出货单位
        downGoods.setGoodsQua(en.getQmOkQuat());//出货数量
        downGoods.setGoodsQuaok(en.getQmOkQuat());//出货数量
        downGoods.setGoodsName(en.getGoodsName());//商品名称
        downGoods.setOmBeiZhu(en.getOmBeiZhu());//备注
        downGoods.setImCusCode(en.getImCusCode());//客户单号
        downGoods.setOrderType("01");//默认为01
        downGoods.setDownSta(ConstUtil.wm_sta8);
        downGoods.setGoodsBatch(en.getGoodsBatch());
        downGoods.setTenantId(en.getTenantId());
        return downGoods;
    }

}


