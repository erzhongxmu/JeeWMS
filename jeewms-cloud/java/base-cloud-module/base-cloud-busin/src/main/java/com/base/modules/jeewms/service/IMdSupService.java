package com.base.modules.jeewms.service;

import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.MdSup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeewms.vo.EditMdSupVo;

/**
 * @Description: 供应商
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
public interface IMdSupService extends IService<MdSup> {

    /**
     * @Describe : 入库单审核编辑
     * @Author: zly
     * @Create Date: 2021-05-18
     */
    Result<?> editStatus(EditMdSupVo vo);

}
