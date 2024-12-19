package com.base.modules.jeewms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.*;
import jodd.util.StringUtil;
import me.zhyd.oauth.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.date.DateTime.now;

@Service
public class GoodsMoveTask {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Autowired
    WvStockSttMapper wvStockSttMapper;

    @Autowired
    WmToMoveGoodsMapper wmToMoveGoodsMapper;

    @Autowired
    private WmToUpGoodsMapper wmToUpGoodsMapper;
    @Autowired
    private WmToDownGoodsMapper wmToDownGoodsMapper;

    //@Scheduled(cron = "0 */1 * * * ?")
    synchronized
    public void run() {
 /*       QueryWrapper<WmToMoveGoods> query2 = new QueryWrapper<>();
        query2.eq("move_sta", "计划中");
        List<WmToMoveGoods> list2 = wmToMoveGoodsMapper.selectList(query2);*/

        QueryWrapper<WmToMoveGoods> query2 = new QueryWrapper<>();
        query2.eq("run_sta", "计划中");
        query2.eq("move_sta", "已完成");

        List<WmToMoveGoods> list2 = wmToMoveGoodsMapper.selectList(query2);
        if (list2 != null && !list2.isEmpty()) {
            for (WmToMoveGoods mg : list2) {
                WmToUpGoods wmToUpGoods = new WmToUpGoods();
                BeanUtils.copyProperties(mg,wmToUpGoods);
                wmToUpGoods.setId(null);
                wmToUpGoods.setBinId(mg.getTinId());
                wmToUpGoods.setKuWeiBianMa(mg.getBinTo());
                wmToUpGoods.setBaseGoodscount(mg.getBaseGoodscount());
                wmToUpGoods.setCusCode(mg.getCusCode());
                wmToUpGoods.setCusName(mg.getCusName());
                wmToUpGoods.setGoodsId(mg.getGoodsId());
                wmToUpGoods.setGoodsName(mg.getGoodsName());
                wmToUpGoods.setGoodsQua(mg.getGoodsQua());
                wmToUpGoods.setBaseUnit(mg.getBaseUnit());
                wmToUpGoods.setGoodsUnit(mg.getGoodsUnit());
                wmToUpGoods.setOrderId(mg.getOrderId());
                wmToUpGoods.setOrderIdI(mg.getOrderIdI());
                wmToUpGoods.setOrderTypeCode(mg.getOrderTypeCode());
                wmToUpGoods.setCreateTime(now());
                wmToUpGoods.setTenantId(mg.getTenantId());
                wmToUpGoods.setPareId(mg.getPareId());

                wmToUpGoodsMapper.insert(wmToUpGoods);

                WmToDownGoods wmToDownGoods = new WmToDownGoods();
                BeanUtils.copyProperties(mg,wmToDownGoods);
                wmToDownGoods.setId(null);
                wmToDownGoods.setBinIdFrom(mg.getTinFrom());
                wmToDownGoods.setBinIdTo(mg.getTinId());
                wmToDownGoods.setActTypeCode(mg.getOrderTypeCode());
                wmToDownGoods.setBaseGoodscount(mg.getBaseGoodscount());
                wmToDownGoods.setCusCode(mg.getCusCode());
                wmToDownGoods.setDownSta("已拣货");
                wmToDownGoods.setGoodsId(mg.getGoodsId());
                wmToDownGoods.setGoodsName(mg.getGoodsName());
                wmToDownGoods.setBaseUnit(mg.getBaseUnit());
                wmToDownGoods.setGoodsUnit(mg.getGoodsUnit());
                wmToDownGoods.setGoodsQua(mg.getGoodsQua());
                wmToDownGoods.setKuWeiBianMa(mg.getBinFrom());
                wmToDownGoods.setOrderId(mg.getOrderId());
                wmToDownGoods.setOrderIdI(mg.getOrderIdI());
                //wmToDownGoods.setOrderId("");
                //wmToDownGoods.setOrderIdI("");
                wmToDownGoods.setOrderType(mg.getOrderTypeCode());
                wmToDownGoods.setTenantId(mg.getTenantId());
                wmToDownGoods.setParenId(mg.getPareId());
                wmToDownGoods.setRemarks("FXKW");
                wmToDownGoods.setCreateTime(now());
                wmToDownGoodsMapper.insert(wmToDownGoods);
//                mg.setMoveSta("已完成");
                mg.setRunSta("已完成");
                mg.setOrderTypeCode("TPZY");
                wmToMoveGoodsMapper.updateById(mg);
            }
        }
    }

}
