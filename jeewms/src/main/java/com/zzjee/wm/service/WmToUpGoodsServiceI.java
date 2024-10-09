package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmToUpGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;

public interface WmToUpGoodsServiceI extends CommonService{

 	public void delete(WmToUpGoodsEntity entity) throws Exception;

 	public Serializable save(WmToUpGoodsEntity entity) throws Exception;

 	public void saveOrUpdate(WmToUpGoodsEntity entity) throws Exception;

}
