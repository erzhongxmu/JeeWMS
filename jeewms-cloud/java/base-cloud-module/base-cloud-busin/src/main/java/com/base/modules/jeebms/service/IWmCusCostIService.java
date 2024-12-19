package com.base.modules.jeebms.service;

import com.base.modules.jeebms.entity.WmCusCostI;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 合同项目
 * @Author: base-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
public interface IWmCusCostIService extends IService<WmCusCostI> {

	public List<WmCusCostI> selectByMainId(String mainId);
}
