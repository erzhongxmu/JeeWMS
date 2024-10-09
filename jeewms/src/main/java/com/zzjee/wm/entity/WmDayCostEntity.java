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
 * @Description: 费用维护
 * @author erzhongxmu
 * @date 2017-10-15 15:30:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_day_cost", schema = "")
@SuppressWarnings("serial")
public class WmDayCostEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private java.util.Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	/**所属部门*/
	private java.lang.String sysOrgCode;
	/**所属公司*/
	private java.lang.String sysCompanyCode;
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
	/**费用日期*/
	@Excel(name="费用日期",format = "yyyy-MM-dd")
	private java.util.Date costData;
	/**原价*/
	@Excel(name="原价")
	private java.lang.String dayCostYj;
	/**不含税价*/
	@Excel(name="不含税价")
	private java.lang.String dayCostBhs;
	/**税额*/
	@Excel(name="税额")
	private java.lang.String dayCostSe;
	/**含税价*/
	@Excel(name="含税价")
	private java.lang.String dayCostHsj;
	/**费用来源*/
	@Excel(name="费用来源")
	private java.lang.String costOri;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String beizhu;
	/**状态*/
	@Excel(name="状态")
	private java.lang.String costSta;
	@Excel(name="结算状态")
	private java.lang.String costJs;

	/**计费数量*/
	@Excel(name="计费数量")
	private java.lang.String costSl;
	/**数量单位*/
	@Excel(name="数量单位")
	private java.lang.String costUnit;
	
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
	@Column(name ="CREATE_DATE",nullable=true)
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
	@Column(name ="UPDATE_DATE",nullable=true)
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
	 *@return: java.lang.String  原价
	 */
	@Column(name ="DAY_COST_YJ",nullable=true,length=32)
	public java.lang.String getDayCostYj(){
		return this.dayCostYj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原价
	 */
	public void setDayCostYj(java.lang.String dayCostYj){
		this.dayCostYj = dayCostYj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  不含税价
	 */
	@Column(name ="DAY_COST_BHS",nullable=true,length=45)
	public java.lang.String getDayCostBhs(){
		return this.dayCostBhs;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  不含税价
	 */
	public void setDayCostBhs(java.lang.String dayCostBhs){
		this.dayCostBhs = dayCostBhs;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  税额
	 */
	@Column(name ="DAY_COST_SE",nullable=true,length=45)
	public java.lang.String getDayCostSe(){
		return this.dayCostSe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  税额
	 */
	public void setDayCostSe(java.lang.String dayCostSe){
		this.dayCostSe = dayCostSe;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  含税价
	 */
	@Column(name ="DAY_COST_HSJ",nullable=true,length=45)
	public java.lang.String getDayCostHsj(){
		return this.dayCostHsj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  含税价
	 */
	public void setDayCostHsj(java.lang.String dayCostHsj){
		this.dayCostHsj = dayCostHsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  费用来源
	 */
	@Column(name ="COST_ORI",nullable=true,length=128)
	public java.lang.String getCostOri(){
		return this.costOri;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  费用来源
	 */
	public void setCostOri(java.lang.String costOri){
		this.costOri = costOri;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BEIZHU",nullable=true,length=64)
	public java.lang.String getBeizhu(){
		return this.beizhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBeizhu(java.lang.String beizhu){
		this.beizhu = beizhu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="COST_STA",nullable=true,length=45)
	public java.lang.String getCostSta(){
		return this.costSta;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setCostSta(java.lang.String costSta){
		this.costSta = costSta;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setCostJs(java.lang.String costJs){
		this.costJs = costJs;
	}

	@Column(name ="COST_JS",nullable=true,length=45)
	public java.lang.String getCostJs(){
		return this.costJs;
	}



	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计费数量
	 */
	@Column(name ="COST_SL",nullable=true,length=45)
	public java.lang.String getCostSl(){
		return this.costSl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计费数量
	 */
	public void setCostSl(java.lang.String costSl){
		this.costSl = costSl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量单位
	 */
	@Column(name ="COST_UNIT",nullable=true,length=45)
	public java.lang.String getCostUnit(){
		return this.costUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量单位
	 */
	public void setCostUnit(java.lang.String costUnit){
		this.costUnit = costUnit;
	}
}
