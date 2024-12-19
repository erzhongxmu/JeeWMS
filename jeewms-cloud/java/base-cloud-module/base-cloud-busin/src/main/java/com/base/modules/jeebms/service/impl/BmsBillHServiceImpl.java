package com.base.modules.jeebms.service.impl;

import com.base.modules.jeebms.entity.BmsBillH;
import com.base.modules.jeebms.entity.BmsBillI;
import com.base.modules.jeebms.mapper.BmsBillHMapper;
import com.base.modules.jeebms.mapper.BmsBillIMapper;
import com.base.modules.jeebms.service.IBmsBillHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: bms_bill_h
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Service
public class BmsBillHServiceImpl extends ServiceImpl<BmsBillHMapper, BmsBillH> implements IBmsBillHService {
    @Autowired
    private BmsBillHMapper bmsBillHMapper;
    @Autowired
    private BmsBillIMapper bmsBillIMapper;

    @Override
    @Transactional
    public void saveMain(BmsBillH bmsBillH, List<BmsBillI> bmsBillIList) {
        bmsBillHMapper.insert(bmsBillH);
        if(bmsBillIList!=null && bmsBillIList.size()>0) {
            for(BmsBillI entity:bmsBillIList) {
                //外键设置
                entity.setBillNo(bmsBillH.getBillNo());
                bmsBillIMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(BmsBillH bmsBillH,List<BmsBillI> bmsBillIList) {
        bmsBillHMapper.updateById(bmsBillH);

        //1.先删除子表数据
        bmsBillIMapper.deleteByMainId(bmsBillH.getBillNo());

        //2.子表数据重新插入
        if(bmsBillIList!=null && bmsBillIList.size()>0) {
            for(BmsBillI entity:bmsBillIList) {
                //外键设置
                entity.setBillNo(bmsBillH.getBillNo());
                bmsBillIMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        bmsBillIMapper.deleteByMainId(id);
        bmsBillHMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for(Serializable id:idList) {
            bmsBillIMapper.deleteByMainId(id.toString());
            bmsBillHMapper.deleteById(id);
        }
    }
}
