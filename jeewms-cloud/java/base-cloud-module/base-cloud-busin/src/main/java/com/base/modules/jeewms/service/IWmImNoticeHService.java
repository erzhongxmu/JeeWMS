package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.controller.dto.WmImNoticeHDTO;
import com.base.modules.jeewms.entity.WmImNoticeH;
import com.base.modules.jeewms.entity.WmImNoticeI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: wm_im_notice_h
 * @Author: base-boot
 * @Date:   2021-05-21
 * @Version: V1.0
 */
public interface IWmImNoticeHService extends IService<WmImNoticeH> {

    /**
     * 添加进货
     * @param param
     */
    void add(WmImNoticeHDTO param);

    /**
     * 客户进货List
     * @param wmImNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    IPage<WmImNoticeH> selectCusList(WmImNoticeH wmImNoticeH, Integer pageNo, Integer pageSize, HttpServletRequest req);

    /**
     * 越库订单List
     * @param wmImNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    IPage<WmImNoticeH> selectCressList(WmImNoticeH wmImNoticeH, Integer pageNo, Integer pageSize, HttpServletRequest req);

    /**
     * 完成
     * @param id
     */
    void complete(String id);

    /**
     * 打印验收单
     * @param id
     * @param response
     */
    void doPrintYsd(String id, HttpServletResponse response);

    /**
     * 打印入库单
     * @param id
     * @param response
     */
    void doPrintRkd(String id, HttpServletResponse response);

    void doPrintBq(String id, HttpServletResponse response);

    /**
     * 退货列表
     * @param wmImNoticeH
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    IPage<WmImNoticeH> selectThList(WmImNoticeH wmImNoticeH, Integer pageNo, Integer pageSize,HttpServletRequest request);


    /**
     * 打印货品id
     * @param id
     * @param response
     */
    void doPrintId(String id, HttpServletResponse response);

    /**
     * 添加一对多
     *
     */
    public void saveMain(WmImNoticeH wmImNoticeH,List<WmImNoticeI> wmImNoticeIList) ;

    /**
     * 添加成功之后返回单号
     * @param wmImNoticeH
     * @param wmImNoticeIList
     * @return
     */
    public String saveMain1(WmImNoticeH wmImNoticeH,List<WmImNoticeI> wmImNoticeIList) ;

    /**
     * 修改一对多
     *
     */
    public void updateMain(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList) throws Exception;

    /**
     * 删除一对多
     */
    public void delMain (String id);
    public void delMains (String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain (Collection<? extends Serializable> idList);

    /**
     * 强制添加一对多
     *
     */
    public void saveMainForce(WmImNoticeH wmImNoticeH,List<WmImNoticeI> wmImNoticeIList) ;

    public Result<?> doPrintBqList(String id);

    public void doPrintckd(String id,String language,HttpServletResponse response);
}
