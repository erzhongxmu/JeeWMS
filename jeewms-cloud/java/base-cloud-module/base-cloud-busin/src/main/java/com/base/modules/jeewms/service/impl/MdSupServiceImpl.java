package com.base.modules.jeewms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.MdSup;
import com.base.modules.jeewms.mapper.MdSupMapper;
import com.base.modules.jeewms.service.IMdSupService;
import com.base.modules.jeewms.vo.EditMdSupVo;
import com.base.modules.util.NotNullUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 供应商
 * @Author: base-boot
 * @Date: 2021-05-17
 * @Version: V1.0
 */
@Service
public class MdSupServiceImpl extends ServiceImpl<MdSupMapper, MdSup> implements IMdSupService {

    @Autowired
    public MdSupMapper mdSupMapper;


    /**
     * @Describe : 入库单审核编辑
     * @Author: zly
     * @Create Date: 2021-05-18
     */
    @Override
    public Result<?> editStatus(EditMdSupVo vo) {
        //获取订单确认订单存在
        MdSup en = mdSupMapper.selectById(vo.getId());
        if (ObjectUtil.isNotEmpty(en)) {
            //修改入参
            BeanUtils.copyProperties(vo, en, NotNullUtils.getNullPropertyNames(vo));
            int row = mdSupMapper.updateById(en);
            if (row > 0) {
                return Result.ok("编辑成功");
            } else {
                return Result.ok("编辑失败");
            }
        }
        return Result.ok("未查到供应商！！");
    }
}
