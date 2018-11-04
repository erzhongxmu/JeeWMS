package com.zzjee.wave.entity;

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
 * @Description: wave_to_down
 * @author erzhongxmu
 * @date 2018-09-10 13:09:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wave_to_down", schema = "")
@SuppressWarnings("serial")
public class WaveToDownEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人登录名称*/
//	@Excel(name="创建人登录名称")
	private String createBy;
	/**创建人名称*/
//	@Excel(name="创建人名称")
	private String createName;
	/**货主*/
	@Excel(name="货主")
	private String cusCode;
	/**客户名称*/
	@Excel(name="客户名称")
	private String cusName;
	/**waveId*/
	@Excel(name="波次号")
	private String waveId;
	/**商品编码*/
	@Excel(name="商品编码")
	private String goodsId;
	/**商品名称*/
	@Excel(name="商品名称")
	private String goodsName;

	/**客户订单号*/
	@Excel(name="客户订单号")
	private java.lang.String imCusCode;
	@Excel(name="备注")
	private java.lang.String omBeizhu;
	/**仓位*/
	@Excel(name="仓位")
	private String binId;
	/**托盘*/
	@Excel(name="托盘")
	private String tinId;
	/**生产日期*/
	@Excel(name="生产日期")
	private String proData;
	/**baseGoodscount*/
	@Excel(name="数量")
	private Double baseGoodscount;
	/**基本单位*/
	@Excel(name="基本单位")
	private String baseUnit;
	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货主
	 */
	@Column(name ="CUS_CODE",nullable=true,length=36)
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
	 *@return: java.lang.String  客户名称
	 */
	@Column(name ="CUS_NAME",nullable=true,length=45)
	public String getCusName(){
		return this.cusName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户名称
	 */
	public void setCusName(String cusName){
		this.cusName = cusName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  waveId
	 */
	@Column(name ="WAVE_ID",nullable=true,length=45)
	public String getWaveId(){
		return this.waveId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  waveId
	 */
	public void setWaveId(String waveId){
		this.waveId = waveId;
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
	 *@return: java.lang.String  仓位
	 */
	@Column(name ="BIN_ID",nullable=true,length=32)
	public String getBinId(){
		return this.binId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓位
	 */
	public void setBinId(String binId){
		this.binId = binId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  托盘
	 */
	@Column(name ="TIN_ID",nullable=true,length=32)
	public String getTinId(){
		return this.tinId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  托盘
	 */
	public void setTinId(String tinId){
		this.tinId = tinId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生产日期
	 */
	@Column(name ="PRO_DATA",nullable=true,length=32)
	public String getProData(){
		return this.proData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生产日期
	 */
	public void setProData(String proData){
		this.proData = proData;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  baseGoodscount
	 */
	@Column(name ="BASE_GOODSCOUNT",nullable=true,length=23)
	public Double getBaseGoodscount(){
		return this.baseGoodscount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  baseGoodscount
	 */
	public void setBaseGoodscount(Double baseGoodscount){
		this.baseGoodscount = baseGoodscount;
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
