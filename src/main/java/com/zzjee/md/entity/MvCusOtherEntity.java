package com.zzjee.md.entity;

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
 * @Description: mv_cus_other
 * @author erzhongxmu
 * @date 2018-09-16 09:22:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "mv_cus_other", schema = "")
@SuppressWarnings("serial")
public class MvCusOtherEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**所属客户*/
	@Excel(name="所属客户")
	private String suoShuKeHu;
	/**客户编码*/
	@Excel(name="客户编码")
	private String cusCode;
	/**cusName*/
	@Excel(name="cusName")
	private String cusName;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属客户
	 */
	@Column(name ="SUO_SHU_KE_HU",nullable=true,length=32)
	public String getSuoShuKeHu(){
		return this.suoShuKeHu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属客户
	 */
	public void setSuoShuKeHu(String suoShuKeHu){
		this.suoShuKeHu = suoShuKeHu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户编码
	 */
	@Column(name ="CUS_CODE",nullable=true,length=32)
	public String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户编码
	 */
	public void setCusCode(String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  cusName
	 */
	@Column(name ="CUS_NAME",nullable=true,length=133)
	public String getCusName(){
		return this.cusName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cusName
	 */
	public void setCusName(String cusName){
		this.cusName = cusName;
	}
}
