package com.base.modules.jeewms.service.impl;

import com.base.common.util.oConvertUtils;
import com.base.modules.jeewms.mapper.MdGoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.vo.DictModel;
import org.jeecgframework.dict.service.AutoPoiDictServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.base.modules.jeewms.service.impl
 * @date 2021/7/1 10:38
 * @description
 */
@Service
@Slf4j
public class AutoPoiDictService implements AutoPoiDictServiceI {

    @Autowired
    private MdGoodsMapper sysDictMapper;

    /**
     * 通过字典查询easypoi，所需字典文本
     *
     * @Author:scott
     * @since：2019-04-09
     * @return
     */
    @Override
    public String[] queryDict(String dicTable, String dicCode, String dicText) {
        List<String> dictReplaces = new ArrayList<String>();
        List<DictModel> dictList = null;
        // step.1 如果没有字典表则使用系统字典表
        if (oConvertUtils.isEmpty(dicTable)) {
            dictList = sysDictMapper.queryDictItemsByCode(dicCode);
        } else {
            try {
                dicText = oConvertUtils.getString(dicText, dicCode);
                dictList = sysDictMapper.queryTableDictItemsByCode(dicTable, dicText, dicCode);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }
        for (DictModel t : dictList) {
            if(t!=null){
                dictReplaces.add(t.getText() + "_" + t.getValue());
            }
        }
        if (dictReplaces != null && dictReplaces.size() != 0) {
            log.info("---AutoPoi--Get_DB_Dict------"+ dictReplaces.toString());
            return dictReplaces.toArray(new String[dictReplaces.size()]);
        }
        return null;
    }
}
