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
 * @Description: ba_store_area
 * @author onlineGenerator
 * @date 2021-07-13 09:34:52
 * @version V1.0
 *
 */
@Entity
@Table(name = "ba_store_area", schema = "")
@SuppressWarnings("serial")
public class BaStoreAreaEntity implements java.io.Serializable {
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
	/**库区编码*/
	@Excel(name="库区编码")
	private String areaCode;
	/**库区名称*/
	@Excel(name="库区名称")
	private String areaName;
	/**库区类型*/
	@Excel(name="库区类型")
	private String areaType;
	/**仓库编码*/
	@Excel(name="仓库编码")
	private String wareCode;
	/**仓库名称*/
	@Excel(name="仓库名称")
	private String wareName;

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
	 *@return: java.lang.String  库区编码
	 */
	@Column(name ="AREA_CODE",nullable=true,length=50)
	public String getAreaCode(){
		return this.areaCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库区编码
	 */
	public void setAreaCode(String areaCode){
		this.areaCode = areaCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库区名称
	 */
	@Column(name ="AREA_NAME",nullable=true,length=50)
	public String getAreaName(){
		return this.areaName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库区名称
	 */
	public void setAreaName(String areaName){
		this.areaName = areaName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库区类型
	 */
	@Column(name ="AREA_TYPE",nullable=true,length=50)
	public String getAreaType(){
		return this.areaType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库区类型
	 */
	public void setAreaType(String areaType){
		this.areaType = areaType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库编码
	 */
	@Column(name ="WARE_CODE",nullable=true,length=50)
	public String getWareCode(){
		return this.wareCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库编码
	 */
	public void setWareCode(String wareCode){
		this.wareCode = wareCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库名称
	 */
	@Column(name ="WARE_NAME",nullable=true,length=50)
	public String getWareName(){
		return this.wareName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库名称
	 */
	public void setWareName(String wareName){
		this.wareName = wareName;
	}
}
