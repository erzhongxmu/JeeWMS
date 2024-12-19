package com.base.modules.jeewms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.modules.jeewms.controller.dto.HdDTO;
import com.base.modules.jeewms.controller.dto.SendCarDTO;
import com.base.modules.jeewms.entity.TmsYwDingdan;
import com.base.modules.jeewms.mapper.TmsYwDingdanMapper;
import com.base.modules.jeewms.service.ITmsYwDingdanService;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: tms_yw_dingdan
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
@Service
public class TmsYwDingdanServiceImpl extends ServiceImpl<TmsYwDingdanMapper, TmsYwDingdan> implements ITmsYwDingdanService {

    /**
     * 批量派车
     * @param param
     */
    @Override
    public void batchSendCar(SendCarDTO param) {
        if (StringUtils.isEmpty(param.getId())) {
            throw new JeecgBootException("订单id不能为空");
        }
        if (StringUtils.isEmpty(param.getSj())) {
            throw new JeecgBootException("司机不能为空");
        }
        if (StringUtils.isEmpty(param.getChehao())) {
            throw new JeecgBootException("车号不能为空");
        }

        String[] ids = param.getId().split(",");
        List<TmsYwDingdan> tmsYwDingdanList = this.lambdaQuery().in(TmsYwDingdan::getId, Arrays.asList(ids)).list();
        for (TmsYwDingdan tmsYwDingdan : tmsYwDingdanList) {
            tmsYwDingdan.setSiji(param.getSj());
            tmsYwDingdan.setChehao(param.getChehao());
            tmsYwDingdan.setZhuangtai("已派车");
        }
        this.updateBatchById(tmsYwDingdanList);

    }

    /**
     * 批量回单
     * @param param
     */
    @Override
    public void batchHd(HdDTO param) {
        if (param.getId().size() == 0) {
           throw new JeecgBootException("请选择一条数据");
        }
        List<String> idList = new ArrayList<>(param.getId());

        List<TmsYwDingdan> tmsYwDingdanList = this.lambdaQuery().in(TmsYwDingdan::getId,idList).list();
        if (tmsYwDingdanList.size() > 0) {
            for (TmsYwDingdan tmsYwDingdan : tmsYwDingdanList) {
                tmsYwDingdan.setZhuangtai("已回单");
                tmsYwDingdan.setYwhdbz(param.getYwhdbz());
                tmsYwDingdan.setHwyf(param.getHwyf());
                tmsYwDingdan.setHwzfy(param.getHwzfy());
                tmsYwDingdan.setHwxhf(param.getHwxhf());
                tmsYwDingdan.setSdsj(new Date());
            }
            this.updateBatchById(tmsYwDingdanList);
        }

    }

    /**
     * 批量取消回单
     * @param id
     */
    @Override
    public void batchCancelHd(List<String> id) {

        List<TmsYwDingdan> tmsYwDingdanList = this.lambdaQuery().in(TmsYwDingdan::getId,id).list();
        if (tmsYwDingdanList.size() > 0) {
            for (TmsYwDingdan tmsYwDingdan : tmsYwDingdanList) {
                tmsYwDingdan.setZhuangtai("已装车");
                tmsYwDingdan.setSdsj(new Date());
            }
            this.updateBatchById(tmsYwDingdanList);
        }
    }
}
