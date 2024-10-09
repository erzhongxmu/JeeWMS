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
 * @Description: 配置信息
 * @author onlineGenerator
 * @date 2019-04-21 23:11:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wx_config", schema = "")
@SuppressWarnings("serial")
public class WxConfigEntity implements java.io.Serializable {
	/**id*/
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
	/**前端编码*/
	@Excel(name="前端编码")
	private String appCode;
	/**备注*/
	@Excel(name="备注")
	private String appRemark;
	/**appID*/
	@Excel(name="appID")
	private String appId;
	/**appsecret*/
	@Excel(name="appsecret")
	private String appSecret;
	/**appkey*/
	@Excel(name="appkey")
	private String appKey;
	/**商户号*/
	@Excel(name="商户号")
	private String mchId;
	/**通知地址*/
	@Excel(name="通知地址")
	private String notifyUrl;
	/**GRANT_TYPE*/
	@Excel(name="GRANT_TYPE")
	private String grantType;
	/**备用1*/
	@Excel(name="备用1")
	private String wxBy1;
	/**备用2*/
	@Excel(name="备用2")
	private String wxBy2;
	/**备用3*/
	@Excel(name="备用3")
	private String wxBy3;
	/**备用4*/
	@Excel(name="备用4")
	private String wxBy4;
	/**备用5*/
	@Excel(name="备用5")
	private String wxBy5;
	
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
	 *@return: java.lang.String  前端编码
	 */
	@Column(name ="APP_CODE",nullable=true,length=32)
	public String getAppCode(){
		return this.appCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  前端编码
	 */
	public void setAppCode(String appCode){
		this.appCode = appCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="APP_REMARK",nullable=true,length=32)
	public String getAppRemark(){
		return this.appRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setAppRemark(String appRemark){
		this.appRemark = appRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  appID
	 */
	@Column(name ="APP_ID",nullable=true)
	public String getAppId(){
		return this.appId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  appID
	 */
	public void setAppId(String appId){
		this.appId = appId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  appsecret
	 */
	@Column(name ="APP_SECRET",nullable=true)
	public String getAppSecret(){
		return this.appSecret;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  appsecret
	 */
	public void setAppSecret(String appSecret){
		this.appSecret = appSecret;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  appkey
	 */
	@Column(name ="APP_KEY",nullable=true,length=132)
	public String getAppKey(){
		return this.appKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  appkey
	 */
	public void setAppKey(String appKey){
		this.appKey = appKey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商户号
	 */
	@Column(name ="MCH_ID",nullable=true,length=32)
	public String getMchId(){
		return this.mchId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商户号
	 */
	public void setMchId(String mchId){
		this.mchId = mchId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知地址
	 */
	@Column(name ="NOTIFY_URL",nullable=true,length=132)
	public String getNotifyUrl(){
		return this.notifyUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知地址
	 */
	public void setNotifyUrl(String notifyUrl){
		this.notifyUrl = notifyUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  GRANT_TYPE
	 */
	@Column(name ="GRANT_TYPE",nullable=true)
	public String getGrantType(){
		return this.grantType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  GRANT_TYPE
	 */
	public void setGrantType(String grantType){
		this.grantType = grantType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用1
	 */
	@Column(name ="WX_BY1",nullable=true)
	public String getWxBy1(){
		return this.wxBy1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用1
	 */
	public void setWxBy1(String wxBy1){
		this.wxBy1 = wxBy1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用2
	 */
	@Column(name ="WX_BY2",nullable=true)
	public String getWxBy2(){
		return this.wxBy2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用2
	 */
	public void setWxBy2(String wxBy2){
		this.wxBy2 = wxBy2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用3
	 */
	@Column(name ="WX_BY3",nullable=true)
	public String getWxBy3(){
		return this.wxBy3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用3
	 */
	public void setWxBy3(String wxBy3){
		this.wxBy3 = wxBy3;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用4
	 */
	@Column(name ="WX_BY4",nullable=true)
	public String getWxBy4(){
		return this.wxBy4;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用4
	 */
	public void setWxBy4(String wxBy4){
		this.wxBy4 = wxBy4;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用5
	 */
	@Column(name ="WX_BY5",nullable=true)
	public String getWxBy5(){
		return this.wxBy5;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用5
	 */
	public void setWxBy5(String wxBy5){
		this.wxBy5 = wxBy5;
	}
}
