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
 * @Description: 下架明细
 * @author erzhongxmu
 * @date 2017-09-11 15:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_to_down_goods", schema = "")
@SuppressWarnings("serial")
public class WmToDownGoodsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	@Excel(name="创建人名称")
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
	/**商品编码*/
	@Excel(name="商品编码")
	private java.lang.String goodsId;
	@Excel(name="商品名称")
	private java.lang.String goodsName;
	/**数量*/
	@Excel(name="数量")
	private java.lang.String goodsQua;
	/**完成数量*/
	@Excel(name="完成数量")
	private java.lang.String goodsQuaok;
	/**原始单据编码*/
	@Excel(name="原始单据编码")
	private java.lang.String orderId;
	/**原始单据行项目*/
	@Excel(name="原始单据行项目")
	private java.lang.String orderIdI;
	/**原始单据类型*/
	@Excel(name="原始单据类型")
	private java.lang.String orderType;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String goodsUnit;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.lang.String goodsProData;
	/**批次*/
	private java.lang.String goodsBatch;
	/**作业类型*/
	@Excel(name="作业类型")
	private java.lang.String actTypeCode;
	/**库位编码*/
	@Excel(name="库位编码")
	private java.lang.String kuWeiBianMa;
	/**目标托盘*/
	@Excel(name="目标托盘")
	private java.lang.String binIdTo;
	/**源托盘码*/
	@Excel(name="源托盘码")
	private java.lang.String binIdFrom;
	/**货主*/
	@Excel(name="货主")
	private java.lang.String cusCode;
	/**状态*/
	@Excel(name="状态")
	private java.lang.String downSta;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String baseUnit;
	/**基本单位数量*/
	@Excel(name="基本单位数量")
	private java.lang.String baseGoodscount;

	/**客户订单号*/
	@Excel(name="客户订单号")
	private java.lang.String imCusCode;
	@Excel(name="备注")
	private java.lang.String omBeizhu;
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
	 *@return: java.lang.String  完成数量
	 */
	@Column(name ="GOODS_QUAOK",nullable=true,length=36)
	public java.lang.String getGoodsQuaok(){
		return this.goodsQuaok;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  完成数量
	 */
	public void setGoodsQuaok(java.lang.String goodsQuaok){
		this.goodsQuaok = goodsQuaok;
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
	 *@return: java.lang.String  原始单据类型
	 */
	@Column(name ="ORDER_TYPE",nullable=true,length=36)
	public java.lang.String getOrderType(){
		return this.orderType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原始单据类型
	 */
	public void setOrderType(java.lang.String orderType){
		this.orderType = orderType;
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
	 *@return: java.lang.String  批次
	 */
	@Column(name ="GOODS_BATCH",nullable=true,length=32)
	public java.lang.String getGoodsBatch(){
		return this.goodsBatch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批次
	 */
	public void setGoodsBatch(java.lang.String goodsBatch){
		this.goodsBatch = goodsBatch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作业类型
	 */
	@Column(name ="ACT_TYPE_CODE",nullable=true,length=32)
	public java.lang.String getActTypeCode(){
		return this.actTypeCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作业类型
	 */
	public void setActTypeCode(java.lang.String actTypeCode){
		this.actTypeCode = actTypeCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库位编码
	 */
	@Column(name ="KU_WEI_BIAN_MA",nullable=true,length=32)
	public java.lang.String getKuWeiBianMa(){
		return this.kuWeiBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位编码
	 */
	public void setKuWeiBianMa(java.lang.String kuWeiBianMa){
		this.kuWeiBianMa = kuWeiBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  目标托盘
	 */
	@Column(name ="BIN_ID_TO",nullable=true,length=32)
	public java.lang.String getBinIdTo(){
		return this.binIdTo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  目标托盘
	 */
	public void setBinIdTo(java.lang.String binIdTo){
		this.binIdTo = binIdTo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源托盘码
	 */
	@Column(name ="BIN_ID_FROM",nullable=true,length=32)
	public java.lang.String getBinIdFrom(){
		return this.binIdFrom;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源托盘码
	 */
	public void setBinIdFrom(java.lang.String binIdFrom){
		this.binIdFrom = binIdFrom;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货主
	 */
	@Column(name ="CUS_CODE",nullable=true,length=32)
	public java.lang.String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  货主
	 */
	public void setCusCode(java.lang.String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="DOWN_STA",nullable=true,length=32)
	public java.lang.String getDownSta(){
		return this.downSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setDownSta(java.lang.String downSta){
		this.downSta = downSta;
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

	@Column(name ="OM_BEI_ZHU",nullable=true,length=32)
	public java.lang.String getOmBeizhu(){
		return this.omBeizhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setOmBeizhu(java.lang.String omBeizhu){
		this.omBeizhu = omBeizhu;
	}


	@Column(name ="IM_CUS_CODE",nullable=true,length=32)
	public java.lang.String getImCusCode(){
		return this.imCusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户订单号
	 */
	public void setImCusCode(java.lang.String imCusCode){
		this.imCusCode = imCusCode;
	}
}
