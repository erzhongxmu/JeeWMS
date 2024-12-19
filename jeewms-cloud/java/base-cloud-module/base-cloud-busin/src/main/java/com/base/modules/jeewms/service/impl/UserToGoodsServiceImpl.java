package com.base.modules.jeewms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.WmsUserPartType;
import com.base.modules.jeewms.service.IMdGoodsService;
import com.base.modules.jeewms.service.IUserToGoodsService;
import com.base.modules.jeewms.service.IWmsUserPartTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserToGoodsServiceImpl implements IUserToGoodsService {

    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private IWmsUserPartTypeService wmsUserPartTypeService;

    @Override
    public List<MdGoods> query(String userName) {
        if (StringUtils.isNotEmpty(userName)) {
            QueryWrapper<WmsUserPartType> query = new QueryWrapper<>();
            if (StringUtils.isNotEmpty(userName)) {
                query.eq("user_name", userName);
            }
            List<WmsUserPartType> list = wmsUserPartTypeService.list(query);
            if (!(list == null || list.isEmpty())) {
                QueryWrapper<MdGoods> query2 = new QueryWrapper<>();
                for (WmsUserPartType upt : list) {
                    query2.or().eq("classification", upt.getPartTypeId());
                }
                return mdGoodsService.list(query2);
            }
        }
        return new ArrayList<>(0);
    }
}
