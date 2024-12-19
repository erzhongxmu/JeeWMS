package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeewms.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: fx_wms_kucun
 * @Author: base-boot
 * @Date:   2022-07-01
 * @Version: V1.0
 */
public interface IFxWmsKucunService extends IService<FxWmsKucun> {
    IPage<FxWmsKucun> getZhList(FxWmsKucun fxWmsKucun, QueryWrapper<FxWmsKucun> queryWrapper, Page<FxWmsKucun> page, HttpServletRequest req);
}
