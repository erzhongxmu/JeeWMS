package com.base.modules.jeebms.service;

import com.base.modules.jeebms.entity.BmsBillH;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeebms.entity.BmsBillI;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: bms_bill_h
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
public interface IBmsBillHService extends IService<BmsBillH> {
    /**
     * 添加一对多
     *
     */
    public void saveMain(BmsBillH bmsBillH, List<BmsBillI> bmsBillIList) ;

    /**
     * 修改一对多
     *
     */
    public void updateMain(BmsBillH bmsBillH,List<BmsBillI> bmsBillIList);

    /**
     * 删除一对多
     */
    public void delMain (String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain (Collection<? extends Serializable> idList);
}
