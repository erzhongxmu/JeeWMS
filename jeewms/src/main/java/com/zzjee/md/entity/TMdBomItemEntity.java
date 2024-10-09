package com.zzjee.md.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: BOM项目
 * @author onlineGenerator
 * @date 2018-05-05 12:56:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_md_bom_item", schema = "")
@SuppressWarnings("serial")
public class TMdBomItemEntity implements java.io.Serializable {
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
	/**流程状态*/
	private String bpmStatus;
	/**组件*/
    @Excel(name="组件",width=15,dictTable ="pop_material",dicCode ="component,itemname,unit",dicText ="matcode,matname,unit")
	private String component;
	/**数量*/
    @Excel(name="数量",width=15)
	private String quantity;
	/**单位*/
    @Excel(name="单位",width=15)
	private String unit;
	/**损耗率*/
    @Excel(name="损耗率",width=15)
	private String scrap;
	/**文本*/
    @Excel(name="文本",width=15)
	private String text;
	/**bom抬头ID*/
	private String bomid;
	/**物料名称*/
    @Excel(name="物料名称",width=15)
	private String itemname;
	/**工位*/
    @Excel(name="工位",width=15)
	private String workplace;
	/**成本中心*/
	private String prccode;
	/**成本中心名称*/
	private String prcname;
	
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
	 *@return: java.lang.String  组件
	 */
	
	@Column(name ="COMPONENT",nullable=true,length=32)
	public String getComponent(){
		return this.component;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组件
	 */
	public void setComponent(String component){
		this.component = component;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量
	 */
	
	@Column(name ="QUANTITY",nullable=true,length=32)
	public String getQuantity(){
		return this.quantity;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setQuantity(String quantity){
		this.quantity = quantity;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	
	@Column(name ="UNIT",nullable=true,length=10)
	public String getUnit(){
		return this.unit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnit(String unit){
		this.unit = unit;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  损耗率
	 */
	
	@Column(name ="SCRAP",nullable=true,length=10)
	public String getScrap(){
		return this.scrap;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  损耗率
	 */
	public void setScrap(String scrap){
		this.scrap = scrap;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文本
	 */
	
	@Column(name ="TEXT",nullable=true,length=50)
	public String getText(){
		return this.text;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文本
	 */
	public void setText(String text){
		this.text = text;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  bom抬头ID
	 */
	
	@Column(name ="BOMID",nullable=true,length=32)
	public String getBomid(){
		return this.bomid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  bom抬头ID
	 */
	public void setBomid(String bomid){
		this.bomid = bomid;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  物料名称
	 */
	
	@Column(name ="ITEMNAME",nullable=true,length=132)
	public String getItemname(){
		return this.itemname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  物料名称
	 */
	public void setItemname(String itemname){
		this.itemname = itemname;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工位
	 */
	
	@Column(name ="WORKPLACE",nullable=true,length=32)
	public String getWorkplace(){
		return this.workplace;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工位
	 */
	public void setWorkplace(String workplace){
		this.workplace = workplace;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  成本中心
	 */
	
	@Column(name ="PRCCODE",nullable=true,length=32)
	public String getPrccode(){
		return this.prccode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  成本中心
	 */
	public void setPrccode(String prccode){
		this.prccode = prccode;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  成本中心名称
	 */
	
	@Column(name ="PRCNAME",nullable=true,length=32)
	public String getPrcname(){
		return this.prcname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  成本中心名称
	 */
	public void setPrcname(String prcname){
		this.prcname = prcname;
	}
	
}
