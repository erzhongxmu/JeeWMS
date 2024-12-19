package com.base.modules.bi.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.bi.entity.zdOnlCgreportParam;

import java.util.List;

/**
 * @Description: onl_cgreport_param
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
public interface IzdOnlCgreportParamService extends IService<zdOnlCgreportParam> {

	public List<zdOnlCgreportParam> selectByMainId(String mainId);
}
