package com.base.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.modules.app.mapper.WmsAppUserMapper;
import com.base.modules.app.service.IWmsAppUserService;
import com.base.modules.mesapp.entity.WmsAppUser;
import org.springframework.stereotype.Service;

/**
 * @Description: 主数据—用户功能
 * @Author: base-boot
 * @Date:   2020-10-14
 * @Version: V1.0
 */
@Service
public class WmsAppUserServiceImpl extends ServiceImpl<WmsAppUserMapper, WmsAppUser> implements IWmsAppUserService {

}
