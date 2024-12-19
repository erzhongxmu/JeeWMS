package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.BaCity;
import org.jeecg.common.exception.JeecgBootException;


/**
 * @Description: 地区信息
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
public interface IBaCityService extends IService<BaCity> {

	/**根节点父ID的值*/
	public static final String ROOT_PID_VALUE = "0";

	/**树节点有子节点状态值*/
	public static final String HASCHILD = "1";

	/**树节点无子节点状态值*/
	public static final String NOCHILD = "0";

	/**新增节点*/
	void addBaCity(BaCity baCity);

	/**修改节点*/
	void updateBaCity(BaCity baCity) throws JeecgBootException;

	/**删除节点*/
	void deleteBaCity(String id) throws JeecgBootException;
	/**
	 * @Describe : 地区下拉数据查询
	 * @Author: zly
	 * @Create Date: 2021-05-18
	 */
	Result<?> getChild();

}
