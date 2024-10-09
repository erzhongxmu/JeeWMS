package com.zzjee.wmapi.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 出货信息
 * @author onlineGenerator
 * @date 2019-10-13 10:52:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "erp_om_item", schema = "")
@SuppressWarnings("serial")
public class WmToDownGoodsErpEntity implements java.io.Serializable {
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
	/**商品编码*/
    @Excel(name="商品编码",width=15)
		
	private String goodsId;
	/**数量*/
    @Excel(name="数量",width=15)
		
	private String goodsQua;
	/**完成数量*/
    @Excel(name="完成数量",width=15)
		
	private String goodsQuaok;
	/**原始单据编码*/
    @Excel(name="原始单据编码",width=15)
		
	private String orderId;
	/**原始单据行项目*/
    @Excel(name="原始单据行项目",width=15)
		
	private String orderIdI;
	/**原始单据类型*/
    @Excel(name="原始单据类型",width=15)
		
	private String orderType;
	/**单位*/
    @Excel(name="单位",width=15)
		
	private String goodsUnit;
	/**生产日期*/
    @Excel(name="生产日期",width=15)
		
	private String goodsProData;
	/**批次*/
    @Excel(name="批次",width=15)
		
	private String goodsBatch;
	/**作业类型*/
    @Excel(name="作业类型",width=15)
		
	private String actTypeCode;
	/**库位编码*/
    @Excel(name="库位编码",width=15)
		
	private String kuWeiBianMa;
	/**目标托盘*/
    @Excel(name="目标托盘",width=15)
		
	private String binIdTo;
	/**源托盘码*/
    @Excel(name="源托盘码",width=15)
		
	private String binIdFrom;
	/**货主*/
    @Excel(name="货主",width=15)
		
	private String cusCode;
	/**状态*/
    @Excel(name="状态",width=15)
		
	private String downSta;
	/**基本单位*/
    @Excel(name="基本单位",width=15)
		
	private String baseUnit;
	/**基本单位数量*/
    @Excel(name="基本单位数量",width=15)
		
	private String baseGoodscount;
	/**商品名称*/
    @Excel(name="商品名称",width=15)
		
	private String goodsName;
	/**ERP单号*/
    @Excel(name="ERP单号",width=15)
		
	private String imCusCode;
	/**出货备注*/
    @Excel(name="出货备注",width=15)
		
	private String omBeiZhu;
	/**ERP_ID*/
    @Excel(name="ERP_ID",width=15)
		
	private String erpId;
	/**wms_id*/
    @Excel(name="wms_id",width=15)
		
	private String wmsId;
	
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
	 *@return: java.lang.String  商品编码
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
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
	 *@return: java.lang.String  数量
	 */
	@Column(name ="GOODS_QUA",nullable=true,length=32)
	public String getGoodsQua(){
		return this.goodsQua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setGoodsQua(String goodsQua){
		this.goodsQua = goodsQua;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  完成数量
	 */
	@Column(name ="GOODS_QUAOK",nullable=true,length=36)
	public String getGoodsQuaok(){
		return this.goodsQuaok;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  完成数量
	 */
	public void setGoodsQuaok(String goodsQuaok){
		this.goodsQuaok = goodsQuaok;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原始单据编码
	 */
	@Column(name ="ORDER_ID",nullable=true,length=36)
	public String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原始单据编码
	 */
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原始单据行项目
	 */
	@Column(name ="ORDER_ID_I",nullable=true,length=36)
	public String getOrderIdI(){
		return this.orderIdI;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原始单据行项目
	 */
	public void setOrderIdI(String orderIdI){
		this.orderIdI = orderIdI;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原始单据类型
	 */
	@Column(name ="ORDER_TYPE",nullable=true,length=36)
	public String getOrderType(){
		return this.orderType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  原始单据类型
	 */
	public void setOrderType(String orderType){
		this.orderType = orderType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="GOODS_UNIT",nullable=true,length=36)
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
	 *@return: java.lang.String  生产日期
	 */
	@Column(name ="GOODS_PRO_DATA",nullable=true,length=32)
	public String getGoodsProData(){
		return this.goodsProData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生产日期
	 */
	public void setGoodsProData(String goodsProData){
		this.goodsProData = goodsProData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批次
	 */
	@Column(name ="GOODS_BATCH",nullable=true,length=32)
	public String getGoodsBatch(){
		return this.goodsBatch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批次
	 */
	public void setGoodsBatch(String goodsBatch){
		this.goodsBatch = goodsBatch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作业类型
	 */
	@Column(name ="ACT_TYPE_CODE",nullable=true,length=32)
	public String getActTypeCode(){
		return this.actTypeCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作业类型
	 */
	public void setActTypeCode(String actTypeCode){
		this.actTypeCode = actTypeCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库位编码
	 */
	@Column(name ="KU_WEI_BIAN_MA",nullable=true,length=32)
	public String getKuWeiBianMa(){
		return this.kuWeiBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位编码
	 */
	public void setKuWeiBianMa(String kuWeiBianMa){
		this.kuWeiBianMa = kuWeiBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  目标托盘
	 */
	@Column(name ="BIN_ID_TO",nullable=true,length=32)
	public String getBinIdTo(){
		return this.binIdTo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  目标托盘
	 */
	public void setBinIdTo(String binIdTo){
		this.binIdTo = binIdTo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源托盘码
	 */
	@Column(name ="BIN_ID_FROM",nullable=true,length=32)
	public String getBinIdFrom(){
		return this.binIdFrom;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源托盘码
	 */
	public void setBinIdFrom(String binIdFrom){
		this.binIdFrom = binIdFrom;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货主
	 */
	@Column(name ="CUS_CODE",nullable=true,length=32)
	public String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  货主
	 */
	public void setCusCode(String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="DOWN_STA",nullable=true,length=32)
	public String getDownSta(){
		return this.downSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setDownSta(String downSta){
		this.downSta = downSta;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASE_UNIT",nullable=true,length=45)
	public String getBaseUnit(){
		return this.baseUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBaseUnit(String baseUnit){
		this.baseUnit = baseUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位数量
	 */
	@Column(name ="BASE_GOODSCOUNT",nullable=true,length=45)
	public String getBaseGoodscount(){
		return this.baseGoodscount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位数量
	 */
	public void setBaseGoodscount(String baseGoodscount){
		this.baseGoodscount = baseGoodscount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=145)
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
	 *@return: java.lang.String  ERP单号
	 */
	@Column(name ="IM_CUS_CODE",nullable=true,length=45)
	public String getImCusCode(){
		return this.imCusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ERP单号
	 */
	public void setImCusCode(String imCusCode){
		this.imCusCode = imCusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货备注
	 */
	@Column(name ="OM_BEI_ZHU",nullable=true,length=320)
	public String getOmBeiZhu(){
		return this.omBeiZhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货备注
	 */
	public void setOmBeiZhu(String omBeiZhu){
		this.omBeiZhu = omBeiZhu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ERP_ID
	 */
	@Column(name ="ERP_ID",nullable=true,length=145)
	public String getErpId(){
		return this.erpId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ERP_ID
	 */
	public void setErpId(String erpId){
		this.erpId = erpId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  wms_id
	 */
	@Column(name ="WMS_ID",nullable=true,length=43)
	public String getWmsId(){
		return this.wmsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  wms_id
	 */
	public void setWmsId(String wmsId){
		this.wmsId = wmsId;
	}
}
