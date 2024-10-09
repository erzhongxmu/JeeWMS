package com.zzjee.wm.entity;

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
 * @Description: 月台进出
 * @author erzhongxmu
 * @date 2017-08-20 17:54:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_plat_io", schema = "")
@SuppressWarnings("serial")
public class WmPlatIoEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**所属部门*/
	private java.lang.String sysOrgCode;
	/**所属公司*/
	private java.lang.String sysCompanyCode;
	/**车号*/
	@Excel(name="车号")
	private java.lang.String carno;
	/**单据编号*/
	@Excel(name="单据编号")
	private java.lang.String docId;
	/**月台编号*/
	@Excel(name="月台编号")
	private java.lang.String platId;
	/**进入时间*/
	@Excel(name="进入时间",format = "yyyy-MM-dd")
	private java.util.Date inData;
	/**驶出时间*/
	@Excel(name="驶出时间",format = "yyyy-MM-dd")
	private java.util.Date outData;
	/**月台状态*/
	@Excel(name="月台状态")
	private java.lang.String platSta;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String platBeizhu;
	/**计划进入时间*/
	@Excel(name="计划进入时间",format = "yyyy-MM-dd")
	private java.util.Date planIndata;
	/**计划驶出时间*/
	@Excel(name="计划驶出时间",format = "yyyy-MM-dd")
	private java.util.Date planOutdata;
	/**月台操作*/
	@Excel(name="月台操作")
	private java.lang.String platOper;
	
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
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public java.lang.String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(java.lang.String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公司
	 */
	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public java.lang.String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公司
	 */
	public void setSysCompanyCode(java.lang.String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车号
	 */
	@Column(name ="CARNO",nullable=true,length=32)
	public java.lang.String getCarno(){
		return this.carno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车号
	 */
	public void setCarno(java.lang.String carno){
		this.carno = carno;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据编号
	 */
	@Column(name ="DOC_ID",nullable=true,length=36)
	public java.lang.String getDocId(){
		return this.docId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setDocId(java.lang.String docId){
		this.docId = docId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  月台编号
	 */
	@Column(name ="PLAT_ID",nullable=true,length=36)
	public java.lang.String getPlatId(){
		return this.platId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  月台编号
	 */
	public void setPlatId(java.lang.String platId){
		this.platId = platId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  进入时间
	 */
	@Column(name ="IN_DATA",nullable=true,length=32)
	public java.util.Date getInData(){
		return this.inData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  进入时间
	 */
	public void setInData(java.util.Date inData){
		this.inData = inData;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  驶出时间
	 */
	@Column(name ="OUT_DATA",nullable=true,length=32)
	public java.util.Date getOutData(){
		return this.outData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  驶出时间
	 */
	public void setOutData(java.util.Date outData){
		this.outData = outData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  月台状态
	 */
	@Column(name ="PLAT_STA",nullable=true,length=32)
	public java.lang.String getPlatSta(){
		return this.platSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  月台状态
	 */
	public void setPlatSta(java.lang.String platSta){
		this.platSta = platSta;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="PLAT_BEIZHU",nullable=true,length=32)
	public java.lang.String getPlatBeizhu(){
		return this.platBeizhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setPlatBeizhu(java.lang.String platBeizhu){
		this.platBeizhu = platBeizhu;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  计划进入时间
	 */
	@Column(name ="PLAN_INDATA",nullable=true,length=32)
	public java.util.Date getPlanIndata(){
		return this.planIndata;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  计划进入时间
	 */
	public void setPlanIndata(java.util.Date planIndata){
		this.planIndata = planIndata;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  计划驶出时间
	 */
	@Column(name ="PLAN_OUTDATA",nullable=true,length=32)
	public java.util.Date getPlanOutdata(){
		return this.planOutdata;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  计划驶出时间
	 */
	public void setPlanOutdata(java.util.Date planOutdata){
		this.planOutdata = planOutdata;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  月台操作
	 */
	@Column(name ="PLAT_OPER",nullable=true,length=32)
	public java.lang.String getPlatOper(){
		return this.platOper;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  月台操作
	 */
	public void setPlatOper(java.lang.String platOper){
		this.platOper = platOper;
	}
}
