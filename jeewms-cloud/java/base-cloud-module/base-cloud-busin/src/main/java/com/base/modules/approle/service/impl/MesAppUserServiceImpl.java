package com.base.modules.approle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.modules.approle.entity.MesAppUser;
import com.base.modules.approle.mapper.MesAppUserMapper;
import com.base.modules.approle.service.IMesAppUserService;
import org.springframework.stereotype.Service;


/**
 * @Description: APP用户对应角色
 * @Author: jeecg-boot
 * @Date:   2021-11-10
 * @Version: V1.0
 */
@Service
public class MesAppUserServiceImpl extends ServiceImpl<MesAppUserMapper, MesAppUser> implements IMesAppUserService {

}
