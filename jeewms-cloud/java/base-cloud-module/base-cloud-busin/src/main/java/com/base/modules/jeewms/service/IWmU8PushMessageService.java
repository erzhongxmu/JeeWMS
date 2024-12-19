package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.vo.WmImNoticeHPage;

import java.util.List;

/**
 * @Description: U8消息推送
 * @Author: base-boot
 * @Date:   2021-11-22
 * @Version: V1.0
 */
public interface IWmU8PushMessageService extends IService<WmU8PushMessage> {

    public Result<?> sendWms(WmImNoticeHPage wmImNoticeHPage, WmU8PushMessage wmU8PushMessage);

    public void saveCaigouruku(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList);

    public void saveXiaoshoufahuo(WmOmNoticeH wmOmNoticeH, List<WmOmNoticeI> wmOmNoticeIList);

    public void saveCaigoudingdan(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList);
}
