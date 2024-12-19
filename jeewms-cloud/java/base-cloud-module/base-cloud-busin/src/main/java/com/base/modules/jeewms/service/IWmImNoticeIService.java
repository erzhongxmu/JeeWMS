package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeewms.entity.WmImNoticeI;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: wm_im_notice_i
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
public interface IWmImNoticeIService extends IService<WmImNoticeI> {

    /**
     * 批量收货List
     * @param wmImNoticeI
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    IPage<WmImNoticeI> selectBatchList(WmImNoticeI wmImNoticeI, Integer pageNo, Integer pageSize, HttpServletRequest req);

    void add(WmImNoticeI wmImNoticeI);

    /**
     * 退货验收列表
     * @param wmImNoticeI
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<WmImNoticeI> selectYsList(WmImNoticeI wmImNoticeI, Integer pageNo, Integer pageSize);

    /**
     * 批量收货
     * @param wmImNoticeI
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    IPage<WmImNoticeI> queryBatchPageList(WmImNoticeI wmImNoticeI, Integer pageNo, Integer pageSize, HttpServletRequest req);

    public List<WmImNoticeI> selectByMainId(String mainId);
}
