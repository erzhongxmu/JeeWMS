package com.zzjee.bireport.entity;

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
 * @Description: 期间出货统计
 * @author onlineGenerator
 * @date 2019-01-17 12:55:46
 * @version V1.0
 *
 */
@Entity
@Table(name = "rp_period_in_out", schema = "")
@SuppressWarnings("serial")
public class RpPeriodInOutEntity implements java.io.Serializable {
	/**主键*/
	private Integer id;
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
	/**期间*/
	@Excel(name="期间")
	private String datePeriod;
	/**用户名*/
	@Excel(name="用户名")
	private String username;
	/**商品编码*/
	@Excel(name="商品编码")
	private String goodsId;
	/**商品名称*/
	@Excel(name="商品名称")
	private String goodsName;
	/**单位*/
	@Excel(name="单位")
	private String goodsUnit;
	/**规格*/
	@Excel(name="规格")
	private String goodsGuige;
	/**入库数量*/
	@Excel(name="期初数量")
	private String goodsQc;
	/**入库数量*/
	@Excel(name="入库数量")
	private String goodsIn;
	/**出库数量*/
	@Excel(name="出库数量")
	private String goodsOut;
	/**入库数量*/
	@Excel(name="期末数量")
	private String goodsQm;
	/**现库存*/
	@Excel(name="现库存")
	private String goodsNow;
	@Excel(name="供应商编码")
	private java.lang.String supCode;
	/**附件*/

	@Excel(name="供应商名称")
	private java.lang.String supName;
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",nullable=false,length=20)
	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  主键
	 */
	public void setId(Integer id){
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
	 *@return: java.lang.String  期间
	 */
	@Column(name ="DATE_PERIOD",nullable=true,length=32)
	public String getDatePeriod(){
		return this.datePeriod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  期间
	 */
	public void setDatePeriod(String datePeriod){
		this.datePeriod = datePeriod;
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
	 *@return: java.lang.String  商品编码
	 */
	@Column(name ="GOODS_ID",nullable=true,length=32)
	public String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编码
	 */
	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=32)
	public String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="GOODS_UNIT",nullable=true,length=32)
	public String getGoodsUnit(){
		return this.goodsUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setGoodsUnit(String goodsUnit){
		this.goodsUnit = goodsUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格
	 */
	@Column(name ="GOODS_GUIGE",nullable=true,length=32)
	public String getGoodsGuige(){
		return this.goodsGuige;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格
	 */
	public void setGoodsGuige(String goodsGuige){
		this.goodsGuige = goodsGuige;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  入库数量
	 */
	@Column(name ="GOODS_IN",nullable=true,length=32)
	public String getGoodsIn(){
		return this.goodsIn;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  入库数量
	 */
	public void setGoodsIn(String goodsIn){
		this.goodsIn = goodsIn;
	}


	@Column(name ="GOODS_QC",nullable=true,length=32)
	public String getGoodsQc() {
		return goodsQc;
	}

	public void setGoodsQc(String goodsQc) {
		this.goodsQc = goodsQc;
	}
	@Column(name ="GOODS_QM",nullable=true,length=32)
	public String getGoodsQm() {
		return goodsQm;
	}

	public void setGoodsQm(String goodsQm) {
		this.goodsQm = goodsQm;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出库数量
	 */
	@Column(name ="GOODS_OUT",nullable=true,length=32)
	public String getGoodsOut(){
		return this.goodsOut;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出库数量
	 */
	public void setGoodsOut(String goodsOut){
		this.goodsOut = goodsOut;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  现库存
	 */
	@Column(name ="GOODS_NOW",nullable=true,length=32)
	public String getGoodsNow(){
		return this.goodsNow;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  现库存
	 */
	public void setGoodsNow(String goodsNow){
		this.goodsNow = goodsNow;
	}


	@Column(name ="sup_code",nullable=true,length=32)
	public String getSupCode() {
		return supCode;
	}

	public void setSupCode(String supCode) {
		this.supCode = supCode;
	}

	@Column(name ="sup_name",nullable=true,length=32)
	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}
}
