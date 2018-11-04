package com.zzjee.bm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: mv_cus_cost
 * @author erzhongxmu
 * @date 2017-10-19 12:23:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "mv_cus_cost", schema = "")
@SuppressWarnings("serial")
public class MvCusCostEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**客户编码*/
	@Excel(name="客户编码")
	private java.lang.String cusCode;
	/**中文全称*/
	@Excel(name="中文全称")
	private java.lang.String cusName;
	/**costData*/
	@Excel(name="costData",format = "yyyy-MM-dd")
	private java.util.Date costData;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户编码
	 */
	@Column(name ="CUS_CODE",nullable=true,length=32)
	public java.lang.String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户编码
	 */
	public void setCusCode(java.lang.String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文全称
	 */
	@Column(name ="CUS_NAME",nullable=true,length=100)
	public java.lang.String getCusName(){
		return this.cusName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文全称
	 */
	public void setCusName(java.lang.String cusName){
		this.cusName = cusName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  costData
	 */
	@Column(name ="COST_DATA",nullable=true)
	public java.util.Date getCostData(){
		return this.costData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  costData
	 */
	public void setCostData(java.util.Date costData){
		this.costData = costData;
	}
}
