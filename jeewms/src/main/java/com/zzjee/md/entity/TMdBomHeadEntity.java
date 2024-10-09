package com.zzjee.md.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: BOM抬头
 * @author onlineGenerator
 * @date 2018-05-05 12:56:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_md_bom_head", schema = "")
@SuppressWarnings("serial")
public class TMdBomHeadEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人登录名称*/
	private String createBy;
	/**创建人名称*/
	private String createName;
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
	/**物料*/
    @Excel(name="物料",width=15,dictTable ="pop_material",dicCode ="matcode,matname",dicText ="matcode,matname")
	private String matcode;
	/**状态*/
    @Excel(name="状态",width=15,dicCode="status")
	private String status;
	/**文本*/
    @Excel(name="文本",width=15)
	private String text;
	/**物料名称*/
    @Excel(name="物料名称",width=15)
	private String matname;
	/**设计数量*/
    @Excel(name="设计数量",width=15)
	private Double qty;
	/**计量单位*/
    @Excel(name="计量单位",width=15)
	private String units;
	/**成本中心代码*/
	private String prccode;
	/**成本中心名称*/
	private String prcname;
	/**生效日期*/
    @Excel(name="生效日期",width=15,format = "yyyy-MM-dd")
	private Date startdate;
	/**失效日期*/
    @Excel(name="失效日期",width=15,format = "yyyy-MM-dd")
	private Date enddate;
	
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
	 *@return: java.lang.String  物料
	 */
	
	@Column(name ="MATCODE",nullable=true,length=32)
	public String getMatcode(){
		return this.matcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  物料
	 */
	public void setMatcode(String matcode){
		this.matcode = matcode;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	
	@Column(name ="STATUS",nullable=true,length=32)
	public String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setStatus(String status){
		this.status = status;
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
	 *@return: java.lang.String  物料名称
	 */
	
	@Column(name ="MATNAME",nullable=true,length=132)
	public String getMatname(){
		return this.matname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  物料名称
	 */
	public void setMatname(String matname){
		this.matname = matname;
	}
	
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  设计数量
	 */
	
	@Column(name ="QTY",nullable=true,scale=2,length=32)
	public Double getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  设计数量
	 */
	public void setQty(Double qty){
		this.qty = qty;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计量单位
	 */
	
	@Column(name ="UNITS",nullable=true,length=32)
	public String getUnits(){
		return this.units;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计量单位
	 */
	public void setUnits(String units){
		this.units = units;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  成本中心代码
	 */
	
	@Column(name ="PRCCODE",nullable=true,length=32)
	public String getPrccode(){
		return this.prccode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  成本中心代码
	 */
	public void setPrccode(String prccode){
		this.prccode = prccode;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  成本中心名称
	 */
	
	@Column(name ="PRCNAME",nullable=true,length=132)
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
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生效日期
	 */
	
	@Column(name ="STARTDATE",nullable=true,length=32)
	public Date getStartdate(){
		return this.startdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生效日期
	 */
	public void setStartdate(Date startdate){
		this.startdate = startdate;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  失效日期
	 */
	
	@Column(name ="ENDDATE",nullable=true,length=32)
	public Date getEnddate(){
		return this.enddate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  失效日期
	 */
	public void setEnddate(Date enddate){
		this.enddate = enddate;
	}
	
}
