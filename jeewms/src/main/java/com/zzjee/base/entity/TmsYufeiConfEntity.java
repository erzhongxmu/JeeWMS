package com.zzjee.base.entity;

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
 * @Description: 运费配置
 * @author onlineGenerator
 * @date 2020-01-19 13:54:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tms_yufei_conf", schema = "")
@SuppressWarnings("serial")
public class TmsYufeiConfEntity implements java.io.Serializable {
	/**id*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**所属部门*/
	private String sysOrgCode;
	/**所属公司*/
	private String sysCompanyCode;
	/**流程状态*/
	private String bpmStatus;
	/**配送点*/
	@Excel(name="配送点")
	private String peisondian;
	/**运费类型*/
	@Excel(name="运费类型")
	private String yfType;
	/**运费名称*/
	@Excel(name="运费名称")
	private String yfTypeName;
	/**运费单价*/
	@Excel(name="运费单价")
	private String yfPrice;
	/**备注1*/
	@Excel(name="备注1")
	private String yfBz1;
	/**备注2*/
	@Excel(name="备注2")
	private String yfBz2;
	/**备注3*/
	@Excel(name="备注3")
	private String yfBz3;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公司
	 */
	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公司
	 */
	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  流程状态
	 */
	@Column(name ="BPM_STATUS",nullable=true,length=32)
	public String getBpmStatus(){
		return this.bpmStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程状态
	 */
	public void setBpmStatus(String bpmStatus){
		this.bpmStatus = bpmStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配送点
	 */
	@Column(name ="PEISONDIAN",nullable=true,length=32)
	public String getPeisondian(){
		return this.peisondian;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配送点
	 */
	public void setPeisondian(String peisondian){
		this.peisondian = peisondian;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  运费类型
	 */
	@Column(name ="YF_TYPE",nullable=true,length=32)
	public String getYfType(){
		return this.yfType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  运费类型
	 */
	public void setYfType(String yfType){
		this.yfType = yfType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  运费名称
	 */
	@Column(name ="YF_TYPE_NAME",nullable=true,length=32)
	public String getYfTypeName(){
		return this.yfTypeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  运费名称
	 */
	public void setYfTypeName(String yfTypeName){
		this.yfTypeName = yfTypeName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  运费单价
	 */
	@Column(name ="YF_PRICE",nullable=true,length=32)
	public String getYfPrice(){
		return this.yfPrice;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  运费单价
	 */
	public void setYfPrice(String yfPrice){
		this.yfPrice = yfPrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注1
	 */
	@Column(name ="YF_BZ1",nullable=true,length=32)
	public String getYfBz1(){
		return this.yfBz1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注1
	 */
	public void setYfBz1(String yfBz1){
		this.yfBz1 = yfBz1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注2
	 */
	@Column(name ="YF_BZ2",nullable=true,length=32)
	public String getYfBz2(){
		return this.yfBz2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注2
	 */
	public void setYfBz2(String yfBz2){
		this.yfBz2 = yfBz2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注3
	 */
	@Column(name ="YF_BZ3",nullable=true,length=32)
	public String getYfBz3(){
		return this.yfBz3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注3
	 */
	public void setYfBz3(String yfBz3){
		this.yfBz3 = yfBz3;
	}
}
