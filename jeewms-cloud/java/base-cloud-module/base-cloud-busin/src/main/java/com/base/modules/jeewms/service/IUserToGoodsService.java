package com.base.modules.jeewms.service;

import com.base.modules.jeewms.entity.MdGoods;

import java.util.List;

public interface IUserToGoodsService {

    List<MdGoods> query(String userName);
}
