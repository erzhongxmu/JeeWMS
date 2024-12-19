package com.base.modules.jeewms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.api.vo.Result;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.entity.WmCartonNumber;
import com.base.modules.jeewms.mapper.WmCartonNumberMapper;
import com.base.modules.jeewms.service.IWmCartonNumberService;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: wm_carton_number
 * @Author: base-boot
 * @Date:   2022-07-06
 * @Version: V1.0
 */
@Service
public class WmCartonNumberServiceImpl extends ServiceImpl<WmCartonNumberMapper, WmCartonNumber> implements IWmCartonNumberService {

     @Autowired
     private  WmCartonNumberMapper wmCartonNumberMapper;


    @Override
    public Result<?> add(WmCartonNumber wmCartonNumber) {
        QueryWrapper<WmCartonNumber> queryWrapper = new QueryWrapper<WmCartonNumber>().eq("bin_id", wmCartonNumber.getBinId());
       /* if(wmCartonNumberMapper.selectOne(queryWrapper)==null){

        }else {
            throw  new JeecgBootException("箱码已存在");
        }*/
        //扫描时间
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        wmCartonNumber.setScanningTime(date);
        wmCartonNumberMapper.insert(wmCartonNumber);

        //WmCartonNumber wmCartonNumber1 = wmCartonNumberMapper.selectOne(queryWrapper);
        return Result.OK(wmCartonNumber);
    }
}
