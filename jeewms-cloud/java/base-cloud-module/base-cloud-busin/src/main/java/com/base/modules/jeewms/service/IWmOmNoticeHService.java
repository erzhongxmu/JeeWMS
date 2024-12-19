package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.api.vo.Result;
import com.base.common.base.dto.WMSResult;
import com.base.modules.jeewms.controller.dto.WmImNoticeHDTO;
import com.base.modules.jeewms.entity.WmOmNoticeH;
import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.base.modules.jeewms.vo.EditBatchWmOmNoticeHVo;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 出货通知抬头
 * @Author: base-boot
 * @Date: 2021-05-18
 * @Version: V1.0
 */
public interface IWmOmNoticeHService extends IService<WmOmNoticeH> {

    /**
     * 添加一对多
     *
     */
    public void saveMain(WmOmNoticeH wmOmNoticeH,List<WmOmNoticeI> wmOmNoticeIList) ;
    public String addSw(WmOmNoticeH wmOmNoticeH,List<WmOmNoticeI> wmOmNoticeIList) ;

    public String saveMain1(WmOmNoticeH wmOmNoticeH,List<WmOmNoticeI> wmOmNoticeIList) ;

    /**
     * 修改一对多
     *
     */
    public void updateMain(WmOmNoticeH wmOmNoticeH,List<WmOmNoticeI> wmOmNoticeIList);
    /**
     * 删除一对多
     */
    public void delMain(String id);

    public void delMains(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

    /**
     * @param list
     * @return
     * @Describe 批量回单-批量回单修改
     * @Author zly
     * @Create Date 2021/5/21
     */
    public Result<?> editBatchReceipt(List<EditBatchWmOmNoticeHVo> list);

    public void downReceiveExcel(String id, HttpServletResponse response);

    public void doPrintckd(String id, HttpServletResponse response);

    public Result<?> doPrintpage(String id);

    public Result<?> doPrintpage1(String id);

    public Result<?> doPrintpageckd(String id);

    public void insertAnalys(List<List<Object>> listob) ;

    public WMSResult declCancel(String src);

    public WmOmNoticeH getByDeliveryId(String deliveryId);

    public Result<?> doPrintBqList(String id);
}
