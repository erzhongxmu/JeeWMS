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
 * @Description: 库内转移
 * @author erzhongxmu
 * @date 2017-09-11 15:25:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_to_move_goods", schema = "")
@SuppressWarnings("serial")
public class WmToMoveGoodsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
//	@Excel(name="创建人名称")
	private java.lang.String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name="创建日期",  format = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date createDate;
	/**更新人名称*/
//	@Excel(name="更新人名称")
	private java.lang.String updateName;
	/**更新人登录名称*/
//	@Excel(name="更新人登录名称")
	private java.lang.String updateBy;
	/**更新日期*/
//	@Excel(name="更新日期",format = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date updateDate;
	/**所属部门*/
//	@Excel(name="所属部门")
	private java.lang.String sysOrgCode;
	/**所属公司*/
//	@Excel(name="所属公司")
	private java.lang.String sysCompanyCode;
	/**原始单据类型*/
//	@Excel(name="原始单据类型")
	private java.lang.String orderTypeCode;
	/**原始单据编码*/
//	@Excel(name="原始单据编码")
	private java.lang.String orderId;
	/**原始单据行项目*/
	@Excel(name="原始单据行项目")
	private java.lang.String orderIdI;
	/**客户编码*/
	@Excel(name="原客户")
	private java.lang.String cusCode;
	/**客户名称*/
	@Excel(name="原客户名称")
	private java.lang.String cusName;
	/**商品编码*/
	@Excel(name="商品编码")
	private java.lang.String goodsId;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String goodsName;
	/**数量*/
	@Excel(name="现有数量")
	private java.lang.String goodsQua;
	@Excel(name="单位")
	private java.lang.String goodsUnit;
	/**基本单位数量*/
	@Excel(name="移动数量")
	private java.lang.String baseGoodscount;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String baseUnit;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.lang.String goodsProData;
	/**源储位*/
	@Excel(name="源储位")
	private java.lang.String binFrom;

	/**源托盘*/
	@Excel(name="源托盘")
	private java.lang.String tinFrom;
	/**转移客户*/
	@Excel(name="到客户")
	private java.lang.String toCusCode;
	/**转移客户名称*/
	@Excel(name="到客户名称")
	private java.lang.String toCusName;



	/**到储位*/
	@Excel(name="到储位")
	private java.lang.String binTo;
	/**到托盘*/
	@Excel(name="到托盘")
	private java.lang.String tinId;

	@Excel(name="到生产日期")
	private java.lang.String toGoodsProData;
	/**状态*/
	@Excel(name="状态")
	private java.lang.String moveSta;


	private java.lang.String runSta;

	
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
	 *@return: java.lang.String  原始单据类型
	 */
	@Column(name ="ORDER_TYPE_CODE",nullable=true,length=36)
	public java.lang.String getOrderTypeCode(){
		return this.orderTypeCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原始单据类型
	 */
	public void setOrderTypeCode(java.lang.String orderTypeCode){
		this.orderTypeCode = orderTypeCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原始单据编码
	 */
	@Column(name ="ORDER_ID",nullable=true,length=36)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原始单据编码
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原始单据行项目
	 */
	@Column(name ="ORDER_ID_I",nullable=true,length=36)
	public java.lang.String getOrderIdI(){
		return this.orderIdI;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原始单据行项目
	 */
	public void setOrderIdI(java.lang.String orderIdI){
		this.orderIdI = orderIdI;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品编码
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编码
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=45)
	public java.lang.String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsName(java.lang.String goodsName){
		this.goodsName = goodsName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量
	 */
	@Column(name ="GOODS_QUA",nullable=true,length=32)
	public java.lang.String getGoodsQua(){
		return this.goodsQua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setGoodsQua(java.lang.String goodsQua){
		this.goodsQua = goodsQua;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生产日期
	 */
	@Column(name ="GOODS_PRO_DATA",nullable=true,length=32)
	public java.lang.String getGoodsProData(){
		return this.goodsProData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生产日期
	 */
	public void setGoodsProData(java.lang.String goodsProData){
		this.goodsProData = goodsProData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="GOODS_UNIT",nullable=true,length=36)
	public java.lang.String getGoodsUnit(){
		return this.goodsUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setGoodsUnit(java.lang.String goodsUnit){
		this.goodsUnit = goodsUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户编码
	 */
	@Column(name ="CUS_CODE",nullable=true,length=36)
	public java.lang.String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户编码
	 */
	public void setCusCode(java.lang.String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户名称
	 */
	@Column(name ="CUS_NAME",nullable=true,length=45)
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
	 *@return: java.lang.String  源托盘
	 */
	@Column(name ="TIN_FROM",nullable=true,length=45)
	public java.lang.String getTinFrom(){
		return this.tinFrom;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源托盘
	 */
	public void setTinFrom(java.lang.String tinFrom){
		this.tinFrom = tinFrom;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  到托盘
	 */
	@Column(name ="TIN_ID",nullable=true,length=45)
	public java.lang.String getTinId(){
		return this.tinId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到托盘
	 */
	public void setTinId(java.lang.String tinId){
		this.tinId = tinId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源储位
	 */
	@Column(name ="BIN_FROM",nullable=true,length=45)
	public java.lang.String getBinFrom(){
		return this.binFrom;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源储位
	 */
	public void setBinFrom(java.lang.String binFrom){
		this.binFrom = binFrom;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  到储位
	 */
	@Column(name ="BIN_TO",nullable=true,length=45)
	public java.lang.String getBinTo(){
		return this.binTo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到储位
	 */
	public void setBinTo(java.lang.String binTo){
		this.binTo = binTo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="MOVE_STA",nullable=true,length=45)
	public java.lang.String getMoveSta(){
		return this.moveSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setMoveSta(java.lang.String moveSta){
		this.moveSta = moveSta;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  转移客户
	 */
	@Column(name ="TO_CUS_CODE",nullable=true,length=45)
	public java.lang.String getToCusCode(){
		return this.toCusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  转移客户
	 */
	public void setToCusCode(java.lang.String toCusCode){
		this.toCusCode = toCusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  转移客户名称
	 */
	@Column(name ="TO_CUS_NAME",nullable=true,length=45)
	public java.lang.String getToCusName(){
		return this.toCusName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  转移客户名称
	 */
	public void setToCusName(java.lang.String toCusName){
		this.toCusName = toCusName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASE_UNIT",nullable=true,length=45)
	public java.lang.String getBaseUnit(){
		return this.baseUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBaseUnit(java.lang.String baseUnit){
		this.baseUnit = baseUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位数量
	 */
	@Column(name ="BASE_GOODSCOUNT",nullable=true,length=45)
	public java.lang.String getBaseGoodscount(){
		return this.baseGoodscount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位数量
	 */
	public void setBaseGoodscount(java.lang.String baseGoodscount){
		this.baseGoodscount = baseGoodscount;
	}


	@Column(name ="TO_GOODS_PRO_DATA",nullable=true,length=32)
	public java.lang.String getToGoodsProData(){
		return this.toGoodsProData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生产日期
	 */
	public void setToGoodsProData(java.lang.String toGoodsProData){
		this.toGoodsProData = toGoodsProData;
	}

	@Column(name ="RUN_STA",nullable=true,length=32)
	public String getRunSta() {
		return runSta;
	}

	public void setRunSta(String runSta) {
		this.runSta = runSta;
	}
}
