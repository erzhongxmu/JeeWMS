package com.base.modules.bidata.service.impl;

import com.base.modules.bidata.entity.BiSql;
import com.base.modules.bidata.mapper.BiSqlMapper;
import com.base.modules.bidata.service.IBiSqlService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 数据配置接口
 * @Author: base-boot
 * @Date:   2020-09-09
 * @Version: V1.0
 */
@Service
public class BiSqlServiceImpl extends ServiceImpl<BiSqlMapper, BiSql> implements IBiSqlService {

}
