package com.base.modules.jeewms.mapper;

import java.util.List;
import java.util.Map;

import com.base.modules.jeewms.entity.WmOmNoticeI;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.base.modules.jeewms.entity.WvStock;

/**
 * @Description: 出货通知项目
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
public interface WmOmNoticeIMapper extends BaseMapper<WmOmNoticeI> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<WmOmNoticeI> selectByMainId(@Param("mainId") String mainId);

	public WvStock findStockSum(@Param("goods_id") String goods_id,@Param("cuscode") String cuscode);
	public WvStock findStockSum1(@Param("goods_id") String goods_id,@Param("tenant_id") Integer tenant_id,@Param("cuscode") String cuscode);
	public WvStock findStockSum2(@Param("goods_id") String goods_id,@Param("cuscode") String cuscode);
	public List<Map<String, Object>> findStock(
			@Param("binId") String binId,
			@Param("tinId") String tinId,
			@Param("goods") String goods,
			@Param("prodate") String prodate,
			@Param("goodsbatch") String goodsbatch,
			@Param("cusCode") String cusCode,
			@Param("hiti") String hiti);

	public List<Map<String, Object>> findStock1(
			@Param("binId") String binId,
			@Param("tinId") String tinId,
			@Param("goods") String goods,
			@Param("prodate") String prodate,
			@Param("goodsbatch") String goodsbatch,
			@Param("cusCode") String cusCode,
			@Param("hiti") String hiti,
			@Param("tenantId") Integer tenantId,
			@Param("kwType") String kwType);

	public List<WmOmNoticeI> selectByMainNo(@Param("mainNo") String mainNo);
}
