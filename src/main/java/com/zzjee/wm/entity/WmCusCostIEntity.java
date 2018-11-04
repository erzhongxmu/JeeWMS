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
 * @Description: 费用项目
 * @author erzhongxmu
 * @date 2017-09-26 15:12:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_cus_cost_i", schema = "")
@SuppressWarnings("serial")
public class WmCusCostIEntity implements java.io.Serializable {
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
	/**费用名称*/
    @Excel(name="费用名称")
	private java.lang.String costCode;
	/**价格RMB*/
    @Excel(name="价格RMB")
	private java.lang.String costJg;
	/**税率*/
    @Excel(name="税率")
	private java.lang.String costSl;
	/**折扣*/
    @Excel(name="折扣")
	private java.lang.String costZk;
	/**不含税价RMB*/
    @Excel(name="不含税价RMB")
	private java.lang.String costBhs;
	/**含税价RMB*/
    @Excel(name="含税价RMB")
	private java.lang.String costHs;
	/**费用ID*/
	private java.lang.String cusCostId;
	
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
	 *@return: java.lang.String  费用名称
	 */
	
	@Column(name ="COST_CODE",nullable=true,length=32)
	public java.lang.String getCostCode(){
		return this.costCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  费用名称
	 */
	public void setCostCode(java.lang.String costCode){
		this.costCode = costCode;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  价格RMB
	 */
	
	@Column(name ="COST_JG",nullable=true,length=32)
	public java.lang.String getCostJg(){
		return this.costJg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  价格RMB
	 */
	public void setCostJg(java.lang.String costJg){
		this.costJg = costJg;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  税率
	 */
	
	@Column(name ="COST_SL",nullable=true,length=32)
	public java.lang.String getCostSl(){
		return this.costSl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  税率
	 */
	public void setCostSl(java.lang.String costSl){
		this.costSl = costSl;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  折扣
	 */
	
	@Column(name ="COST_ZK",nullable=true,length=32)
	public java.lang.String getCostZk(){
		return this.costZk;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  折扣
	 */
	public void setCostZk(java.lang.String costZk){
		this.costZk = costZk;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  不含税价RMB
	 */
	
	@Column(name ="COST_BHS",nullable=true,length=32)
	public java.lang.String getCostBhs(){
		return this.costBhs;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  不含税价RMB
	 */
	public void setCostBhs(java.lang.String costBhs){
		this.costBhs = costBhs;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  含税价RMB
	 */
	
	@Column(name ="COST_HS",nullable=true,length=32)
	public java.lang.String getCostHs(){
		return this.costHs;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  含税价RMB
	 */
	public void setCostHs(java.lang.String costHs){
		this.costHs = costHs;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  费用ID
	 */
	
	@Column(name ="CUS_COST_ID",nullable=true,length=36)
	public java.lang.String getCusCostId(){
		return this.cusCostId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  费用ID
	 */
	public void setCusCostId(java.lang.String cusCostId){
		this.cusCostId = cusCostId;
	}
	
}
