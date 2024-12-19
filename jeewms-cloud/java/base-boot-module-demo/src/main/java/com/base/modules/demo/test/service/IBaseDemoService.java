package com.base.modules.demo.test.service;

import com.base.common.system.base.service.BaseService;
import com.base.modules.demo.test.entity.BaseDemo;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: sshuang 测试demo
 * @Author: base-boot
 * @Date:  2018-12-29
 * @Version: V1.0
 */
public interface IBaseDemoService extends BaseService<BaseDemo> {
	
	public void testTran();
	
	public BaseDemo getByIdCacheable(String id);
	
	/**
	 * 查询列表数据 在service中获取数据权限sql信息
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	IPage<BaseDemo> queryListWithPermission(int pageSize, int pageNo);

	/**
	 * 根据用户权限获取导出字段
	 * @return
	 */
	String getExportFields();
}
