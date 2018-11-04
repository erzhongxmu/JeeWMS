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
 * @Description: 盘点
 * @author erzhongxmu
 * @date 2017-09-11 15:25:24
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_stt_in_goods", schema = "")
@SuppressWarnings("serial")
public class WmSttInGoodsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private java.lang.String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private java.lang.String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",format = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date updateDate;
	/**所属部门*/
	@Excel(name="所属部门")
	private java.lang.String sysOrgCode;
	/**所属公司*/
	@Excel(name="所属公司")
	private java.lang.String sysCompanyCode;
	
	/**盘点单号*/
	@Excel(name="盘点单号")
	private java.lang.String sttId;
	
	/**库位编码*/
	@Excel(name="库位编码")
	private java.lang.String binId;
	@Excel(name="动线")
	private java.lang.String dongXian;
	/**托盘编码*/
	@Excel(name="托盘编码")
	private java.lang.String tinId;
	/**商品编码*/
	@Excel(name="商品编码")
	private java.lang.String goodsId;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String goodsName;
	/**数量*/
	@Excel(name="数量")
	private java.lang.String goodsQua;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String goodsUnit;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.lang.String goodsProData;
	/**批次*/
	@Excel(name="批次")
	private java.lang.String goodsBatch;
	/**盘点数量*/
	@Excel(name="盘点数量")
	private java.lang.String sttQua;
	/**客户名称*/
	@Excel(name="客户名称")
	private java.lang.String cusName;
	/**客户*/
	@Excel(name="客户")
	private java.lang.String cusCode;
	/**盘点状态*/
	@Excel(name="盘点状态")
	private java.lang.String sttSta;
	/**盘点状态*/
	@Excel(name="盘点类型")
	private java.lang.String sttType;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String baseUnit;
	/**基本单位数量*/
	@Excel(name="基本单位数量")
	private java.lang.String baseGoodscount;
	
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
	
	
	
	
	@Column(name ="STT_ID",nullable=true,length=36)
	public java.lang.String getSttId() {
		return sttId;
	}

	public void setSttId(java.lang.String sttId) {
		this.sttId = sttId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库位编码
	 */
	@Column(name ="BIN_ID",nullable=true,length=36)
	public java.lang.String getBinId(){
		return this.binId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位编码
	 */
	public void setBinId(java.lang.String binId){
		this.binId = binId;
	}
	
	@Column(name ="DONG_XIAN",nullable=true,length=36)
	public java.lang.String getDongXian(){
		return this.dongXian;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位编码
	 */
	public void setDongXian(java.lang.String dongXian){
		this.dongXian = dongXian;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  托盘编码
	 */
	@Column(name ="TIN_ID",nullable=true,length=36)
	public java.lang.String getTinId(){
		return this.tinId;
	}
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  托盘编码
	 */
	public void setTinId(java.lang.String tinId){
		this.tinId = tinId;
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
	 *@return: java.lang.String  盘点数量
	 */
	@Column(name ="STT_QUA",nullable=true,length=32)
	public java.lang.String getSttQua(){
		return this.sttQua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  盘点数量
	 */
	public void setSttQua(java.lang.String sttQua){
		this.sttQua = sttQua;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户名称
	 */
	@Column(name ="CUS_NAME",nullable=true,length=36)
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
	 *@return: java.lang.String  客户
	 */
	@Column(name ="CUS_CODE",nullable=true,length=36)
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
	 *@return: java.lang.String  盘点状态
	 */
	@Column(name ="STT_STA",nullable=true,length=45)
	public java.lang.String getSttSta(){
		return this.sttSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  盘点状态
	 */
	public void setSttSta(java.lang.String sttSta){
		this.sttSta = sttSta;
	}
	@Column(name ="STT_TYPE",nullable=true,length=45)
	public java.lang.String getSttType(){
		return this.sttType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  盘点状态
	 */
	public void setSttType(java.lang.String sttType){
		this.sttType = sttType;
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
}
