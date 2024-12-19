package com.base.modules.jeewms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.base.modules.jeewms.controller.dto.KwPicDataDTO;
import com.base.modules.jeewms.entity.BaKw;
import com.base.modules.jeewms.vo.AvlKwVo;

import java.util.List;
import java.util.Map;

/**
 * @Description: ba_kw
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
public interface BaKwMapper extends BaseMapper<BaKw> {

    List<Map<String,Object>> getKwPicData(KwPicDataDTO param);

    Map<String,Object> findBinId(@Param("lastbinfenzu") String lastbinfenzu, @Param("zuidatiji") String zuidatiji, @Param("goodsId") String goodsId, @Param("cusCode") String cusCode,@Param("binplantuopan") String binplantuopan);

    /**
     * 查询可用储位
     * @param page
     * @param classification
     * @return
     */
    IPage<AvlKwVo> getAvlBin(@Param("iPage") IPage<AvlKwVo> page,@Param("goodsType") String goodsType);

    /**
     * 获取推荐库位
     * @param goodsTypeId
     * @return
     */
    String selectRecommandBin(@Param("goodsTypeId") String goodsTypeId);

    String getStatusBaAreaByBinId(@Param("binId") String binId);
}
