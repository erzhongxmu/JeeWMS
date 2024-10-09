package com.zzjee.conf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 第三方登录
 * @author onlineGenerator
 * @date 2019-04-21 23:11:19
 * @version V1.0   
 *
 */
@Entity
@Table(name = "fxj_other_login", schema = "")
@SuppressWarnings("serial")
public class FxjOtherLoginEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",format = "yyyy-MM-dd")
	private Date updateDate;
	/**所属部门*/
	@Excel(name="所属部门")
	private String sysOrgCode;
	/**所属公司*/
	@Excel(name="所属公司")
	private String sysCompanyCode;
	/**流程状态*/
	@Excel(name="流程状态")
	private String bpmStatus;
	/**用户名*/
	@Excel(name="用户名")
	private String username;
	/**姓名*/
	@Excel(name="姓名")
	private String realname;
	/**三方id*/
	@Excel(name="三方id")
	private String otherid;
	/**三方1*/
	@Excel(name="三方1")
	private String otherre1;
	/**三方2*/
	@Excel(name="三方2")
	private String otherre2;
	/**三方3*/
	@Excel(name="三方3")
	private String otherre3;
	/**三方4*/
	@Excel(name="三方4")
	private String otherre4;
	/**登录类型*/
	@Excel(name="登录类型")
	private String otherType;
	
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
	 *@return: java.lang.String  用户名
	 */
	@Column(name ="USERNAME",nullable=true,length=32)
	public String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setUsername(String username){
		this.username = username;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="REALNAME",nullable=true,length=32)
	public String getRealname(){
		return this.realname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setRealname(String realname){
		this.realname = realname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三方id
	 */
	@Column(name ="OTHERID",nullable=true,length=232)
	public String getOtherid(){
		return this.otherid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三方id
	 */
	public void setOtherid(String otherid){
		this.otherid = otherid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三方1
	 */
	@Column(name ="OTHERRE1",nullable=true,length=232)
	public String getOtherre1(){
		return this.otherre1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三方1
	 */
	public void setOtherre1(String otherre1){
		this.otherre1 = otherre1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三方2
	 */
	@Column(name ="OTHERRE2",nullable=true,length=232)
	public String getOtherre2(){
		return this.otherre2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三方2
	 */
	public void setOtherre2(String otherre2){
		this.otherre2 = otherre2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三方3
	 */
	@Column(name ="OTHERRE3",nullable=true,length=232)
	public String getOtherre3(){
		return this.otherre3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三方3
	 */
	public void setOtherre3(String otherre3){
		this.otherre3 = otherre3;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  三方4
	 */
	@Column(name ="OTHERRE4",nullable=true,length=232)
	public String getOtherre4(){
		return this.otherre4;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  三方4
	 */
	public void setOtherre4(String otherre4){
		this.otherre4 = otherre4;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  登录类型
	 */
	@Column(name ="OTHER_TYPE",nullable=true,length=32)
	public String getOtherType(){
		return this.otherType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  登录类型
	 */
	public void setOtherType(String otherType){
		this.otherType = otherType;
	}
}
