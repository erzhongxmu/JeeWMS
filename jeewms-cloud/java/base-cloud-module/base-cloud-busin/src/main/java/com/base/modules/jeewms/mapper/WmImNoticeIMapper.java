package com.base.modules.jeewms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.base.modules.jeewms.entity.WmImNoticeI;

import java.util.List;

/**
 * @Description: wm_im_notice_i
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
public interface WmImNoticeIMapper extends BaseMapper<WmImNoticeI> {

    /**
     * 分页查询退货验收列表
     * @param page
     * @param wmImNoticeH
     * @return
     */
    IPage<WmImNoticeI> selectYsList(@Param("iPage") Page page,@Param("wmImNoticeI") WmImNoticeI wmImNoticeI);

    /**
     * 批量收货
     * @param page
     * @param wmImNoticeI
     * @return
     */
    IPage<WmImNoticeI> queryBatchPageList(Page page,@Param("wmImNoticeI") WmImNoticeI wmImNoticeI);

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<WmImNoticeI> selectByMainId(@Param("mainId") String mainId);

}
