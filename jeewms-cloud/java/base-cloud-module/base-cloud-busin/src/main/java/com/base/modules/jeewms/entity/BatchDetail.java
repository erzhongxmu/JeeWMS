package com.base.modules.jeewms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchDetail implements Serializable {
    private 	String 	  batch_no					 			;	//必填	批次号 详情见批次相关概念(不超过64个字符)
    private 	Integer	  good_count							;	//必填	良品数量
    private 	Integer	  bad_count								;	//必填	次品数量
    private     Integer	  lock_sum					            ;	//是	锁库存
    private 	Integer	  primary_defective_count				;	//必填	一级残次数量
    private 	Integer	  secondary_defective_count				;	//必填	二级残次数量
    private 	Integer	  advent_count							;	//必填	临期品数量

}
