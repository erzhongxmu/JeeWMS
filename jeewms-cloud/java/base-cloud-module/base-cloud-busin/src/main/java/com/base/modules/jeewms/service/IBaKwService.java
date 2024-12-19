package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeewms.controller.dto.BaKwDTO;
import com.base.modules.jeewms.controller.dto.KwPicDataDTO;
import com.base.modules.jeewms.entity.BaKw;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description: ba_kw
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
public interface IBaKwService extends IService<BaKw> {

    /**
     * 获取仓位图数据
     * @param param
     * @return
     */
    Map<String,Object> getKwPicData(KwPicDataDTO param);

    /**
     * 批量启停
     * @param param
     */
    void batchStartOrStop(List<BaKwDTO> param);

    String selectRecommandBin(String goodsTypeId);

    String getStatusBaAreaByBinId(String binId);

    void doExportBq(List<String> idList, HttpServletResponse response);

    void doExportBq2(List<String> idList, HttpServletResponse response);

    void doExportBq3(List<String> idList, HttpServletResponse response);
}
