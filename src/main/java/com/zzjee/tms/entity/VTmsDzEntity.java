package com.zzjee.tms.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: v_tms_dz
 * @author onlineGenerator
 * @date 2018-08-08 01:31:13
 * @version V1.0   
 * 电子地图地址实体类，用于表示电子地图中的地址信息。
 */
@Entity
@Table(name = "v_tms_dz", schema = "")
@SuppressWarnings("serial")
public class VTmsDzEntity implements java.io.Serializable {
	/**
	 * id
	 */
	private String id;
	/**
	 * dizhi
	 */
	@Excel(name="dizhi",width=15)
	private String dizhi;
	
	/**
	 * get方法: 取得java.lang.String
	 * @return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 * set方法: 设置java.lang.String
	 * @param: java.lang.String  id
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * get方法: 取得java.lang.String
	 * @return: java.lang.String  dizhi
	 */
	@Column(name ="DIZHI",nullable=true,length=131)
	public String getDizhi(){
		return this.dizhi;
	}

	/**
	 * set方法: 设置java.lang.String
	 * @param: java.lang.String  dizhi
	 */
	public void setDizhi(String dizhi){
		this.dizhi = dizhi;
	}
}
