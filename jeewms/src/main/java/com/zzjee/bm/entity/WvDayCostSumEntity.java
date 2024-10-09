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
 * @Description: wv_day_cost_sum
 * @author erzhongxmu
 * @date 2017-10-21 21:08:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wv_day_cost_sum", schema = "")
@SuppressWarnings("serial")
public class WvDayCostSumEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**费用日期*/
	@Excel(name="费用日期",format = "yyyy-MM-dd")
	private java.util.Date costData;
	/**客户*/
	@Excel(name="客户")
	private java.lang.String cusCode;
	/**客户名称*/
	@Excel(name="客户名称")
	private java.lang.String cusName;
	/**费用*/
	@Excel(name="费用")
	private java.lang.String costCode;
	/**费用名称*/
	@Excel(name="费用名称")
	private java.lang.String costName;
	@Excel(name="数量")
	private java.lang.String costSl;

	@Excel(name="单位")
	private java.lang.String costUnit;
	/**yuanj*/
	@Excel(name="原价")
	private java.lang.String yuanj;
	/**bhsj*/
	@Excel(name="不含税")
	private java.lang.String bhsj;
	/**shuie*/
	@Excel(name="税")
	private java.lang.String shuie;
	/**hansj*/
	@Excel(name="含税价")
	private java.lang.String hansj;
	@Excel(name="是否结算")
	private java.lang.String costJs;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=true,length=83)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  费用日期
	 */
	@Column(name ="COST_DATA",nullable=true)
	public java.util.Date getCostData(){
		return this.costData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  费用日期
	 */
	public void setCostData(java.util.Date costData){
		this.costData = costData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户
	 */
	@Column(name ="CUS_CODE",nullable=true,length=32)
	public java.lang.String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户
	 */
	public void setCusCode(java.lang.String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户名称
	 */
	@Column(name ="CUS_NAME",nullable=true,length=100)
	public java.lang.String getCusName(){
		return this.cusName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户名称
	 */
	public void setCusName(java.lang.String cusName){
		this.cusName = cusName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  费用
	 */
	@Column(name ="COST_CODE",nullable=true,length=32)
	public java.lang.String getCostCode(){
		return this.costCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  费用
	 */
	public void setCostCode(java.lang.String costCode){
		this.costCode = costCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  费用名称
	 */
	@Column(name ="COST_NAME",nullable=true,length=45)
	public java.lang.String getCostName(){
		return this.costName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  费用名称
	 */
	public void setCostName(java.lang.String costName){
		this.costName = costName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  yuanj
	 */
	@Column(name ="YUANJ",nullable=true,length=63)
	public java.lang.String getYuanj(){
		return this.yuanj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  yuanj
	 */
	public void setYuanj(java.lang.String yuanj){
		this.yuanj = yuanj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  bhsj
	 */
	@Column(name ="BHSJ",nullable=true,length=63)
	public java.lang.String getBhsj(){
		return this.bhsj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  bhsj
	 */
	public void setBhsj(java.lang.String bhsj){
		this.bhsj = bhsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  shuie
	 */
	@Column(name ="SHUIE",nullable=true,length=63)
	public java.lang.String getShuie(){
		return this.shuie;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shuie
	 */
	public void setShuie(java.lang.String shuie){
		this.shuie = shuie;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  hansj
	 */
	@Column(name ="HANSJ",nullable=true,length=63)
	public java.lang.String getHansj(){
		return this.hansj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  hansj
	 */
	public void setHansj(java.lang.String hansj){
		this.hansj = hansj;
	}


	@Column(name ="COST_JS",nullable=true,length=63)
	public java.lang.String getCostJs(){
		return this.costJs;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  hansj
	 */
	public void setCostJs(java.lang.String costJs){
		this.costJs = costJs;
	}

	@Column(name ="COST_SL",nullable=true,length=63)
	public java.lang.String getCostSl(){
		return this.costSl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  hansj
	 */
	public void setCostSl(java.lang.String costSl){
		this.costSl = costSl;
	}


	@Column(name ="COST_UNIT",nullable=true,length=63)
	public java.lang.String getCostUnit(){
		return this.costUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  hansj
	 */
	public void setCostUnit(java.lang.String costUnit){
		this.costUnit = costUnit;
	}
}
