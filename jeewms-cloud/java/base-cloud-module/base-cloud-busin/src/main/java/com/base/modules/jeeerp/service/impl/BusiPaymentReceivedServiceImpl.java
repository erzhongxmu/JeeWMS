package com.base.modules.jeeerp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.modules.jeeerp.entity.BusiPaymentReceived;
import com.base.modules.jeeerp.entity.BusiPo;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.base.modules.jeeerp.mapper.BusiPaymentReceivedMapper;
import com.base.modules.jeeerp.service.IBusiPaymentReceivedService;
import com.base.modules.jeeerp.vo.BusiPoPage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: busi_payment_received
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Service
public class BusiPaymentReceivedServiceImpl extends ServiceImpl<BusiPaymentReceivedMapper, BusiPaymentReceived> implements IBusiPaymentReceivedService {

}
