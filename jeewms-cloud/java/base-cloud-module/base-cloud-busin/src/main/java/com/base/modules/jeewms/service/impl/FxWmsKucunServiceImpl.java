package com.base.modules.jeewms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.FxWmsKucunMapper;
import com.base.modules.jeewms.mapper.WvStockMapper;
import com.base.modules.jeewms.service.*;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: fx_wms_kucun
 * @Author: base-boot
 * @Date:   2022-07-01
 * @Version: V1.0
 */
@Service
public class FxWmsKucunServiceImpl extends ServiceImpl<FxWmsKucunMapper, FxWmsKucun> implements IFxWmsKucunService {


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

    @Override
    public IPage<FxWmsKucun> getZhList(FxWmsKucun fxWmsKucun, QueryWrapper<FxWmsKucun> queryWrapper, Page<FxWmsKucun> page, HttpServletRequest req) {
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
        queryWrapper.lambda().eq(FxWmsKucun::getBaStore,storeCode);
        List<String> kwCodeList = new ArrayList<>();
        //查询库位
        if (StringUtils.isNotEmpty(areaCode)) {
            List<BaKw> baKwList = baKwService.lambdaQuery().eq(BaKw::getStoreAreaCode,areaCode).list();
            if (baKwList.size() > 0) {
                for (BaKw baKw : baKwList) {
                    kwCodeList.add(baKw.getKwCode());
                }
            }
            queryWrapper.lambda().in(kwCodeList.size()>0,FxWmsKucun::getKuWeiBianMa,kwCodeList);
        }
        return this.page(page, queryWrapper);
    }
}
