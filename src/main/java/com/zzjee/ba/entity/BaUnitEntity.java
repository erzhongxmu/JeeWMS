package com.zzjee.ba.entity;

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
 * @Description: 计量单位
 * @author onlineGenerator
 * @date 2018-10-31 12:01:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "ba_unit", schema = "")
@SuppressWarnings("serial")
public class BaUnitEntity implements java.io.Serializable {
	/**主键*/
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
	/**单位代码*/
	@Excel(name="单位代码")
	private String unitCode;
	/**单位名称*/
	@Excel(name="单位名称")
	private String unitZhName;
	/**英文名称*/
	@Excel(name="英文名称")
	private String unitEnName;
	/**国际度量衡换算值*/
	private String unitChange;
	/**单位类型*/
	private String unitType;
	/**停用*/
	private String unitDel;
	
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
	@Column(name ="CREATE_DATE",nullable=true,length=20)
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
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
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
	 *@return: java.lang.String  单位代码
	 */
	@Column(name ="UNIT_CODE",nullable=true,length=32)
	public String getUnitCode(){
		return this.unitCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位代码
	 */
	public void setUnitCode(String unitCode){
		this.unitCode = unitCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位名称
	 */
	@Column(name ="UNIT_ZH_NAME",nullable=true,length=32)
	public String getUnitZhName(){
		return this.unitZhName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位名称
	 */
	public void setUnitZhName(String unitZhName){
		this.unitZhName = unitZhName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  英文名称
	 */
	@Column(name ="UNIT_EN_NAME",nullable=true,length=32)
	public String getUnitEnName(){
		return this.unitEnName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  英文名称
	 */
	public void setUnitEnName(String unitEnName){
		this.unitEnName = unitEnName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国际度量衡换算值
	 */
	@Column(name ="UNIT_CHANGE",nullable=true,length=32)
	public String getUnitChange(){
		return this.unitChange;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国际度量衡换算值
	 */
	public void setUnitChange(String unitChange){
		this.unitChange = unitChange;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位类型
	 */
	@Column(name ="UNIT_TYPE",nullable=true,length=32)
	public String getUnitType(){
		return this.unitType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位类型
	 */
	public void setUnitType(String unitType){
		this.unitType = unitType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  停用
	 */
	@Column(name ="UNIT_DEL",nullable=true,length=32)
	public String getUnitDel(){
		return this.unitDel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  停用
	 */
	public void setUnitDel(String unitDel){
		this.unitDel = unitDel;
	}
}
