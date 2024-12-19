package com.base.common.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuInventory implements Serializable {
    private String 				    sku_id						;	//(不超过64个字符)		SkuId
    private Integer					good_count					;	//是	良品数量
    private Integer					bad_count					;	//是	次品数量
    private Integer					lock_sum					;	//是	锁库存
    private Integer					primary_defective_count		;	//是	一级残次数量
    private Integer					secondary_defective_count	;	//是	二级残次数量
    private Integer					advent_count				;	//是	出入库临期品数量
    private List<BatchDetail> batch_detail;	//是	批次明细
}