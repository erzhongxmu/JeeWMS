package com.zzjee.md.service;
import com.zzjee.md.entity.MdBinEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface MdBinServiceI extends CommonService{

 	public void delete(MdBinEntity entity) throws Exception;

 	public Serializable save(MdBinEntity entity) throws Exception;

 	public void saveOrUpdate(MdBinEntity entity) throws Exception;

}
