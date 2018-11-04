package com.zzjee.report.entity;

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
 * @Description: rp_wm_up_and_down
 * @author erzhongxmu
 * @date 2018-09-11 07:47:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "rp_wm_up_and_down", schema = "")
@SuppressWarnings("serial")
public class RpWmUpAndDownEntity implements java.io.Serializable {
	/**id*/
	private String id;
	/**createDate*/
	@Excel(name="createDate",format = "yyyy-MM-dd")
	private Date createDate;
	/**orderId*/
	@Excel(name="orderId")
	private String orderId;
	/**kuWeiBianMa*/
	@Excel(name="kuWeiBianMa")
	private String kuWeiBianMa;
	/**binId*/
	@Excel(name="binId")
	private String binId;
	/**cusCode*/
	@Excel(name="cusCode")
	private String cusCode;
	/**zhongWenQch*/
	@Excel(name="zhongWenQch")
	private String zhongWenQch;
	/**goodsId*/
	@Excel(name="goodsId")
	private String goodsId;
	/**shpMingCheng*/
	@Excel(name="shpMingCheng")
	private String shpMingCheng;
	/**goodsProData*/
	@Excel(name="goodsProData")
	private String goodsProData;
	/**baseGoodscount*/
	@Excel(name="baseGoodscount")
	private Double baseGoodscount;
	/**baseUnit*/
	@Excel(name="baseUnit")
	private String baseUnit;
	/**库位类型*/
	@Excel(name="库位类型")
	private String leixing;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createDate
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createDate
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  orderId
	 */
	@Column(name ="ORDER_ID",nullable=true,length=36)
	public String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  orderId
	 */
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  kuWeiBianMa
	 */
	@Column(name ="KU_WEI_BIAN_MA",nullable=true,length=32)
	public String getKuWeiBianMa(){
		return this.kuWeiBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  kuWeiBianMa
	 */
	public void setKuWeiBianMa(String kuWeiBianMa){
		this.kuWeiBianMa = kuWeiBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  binId
	 */
	@Column(name ="BIN_ID",nullable=true,length=32)
	public String getBinId(){
		return this.binId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  binId
	 */
	public void setBinId(String binId){
		this.binId = binId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  cusCode
	 */
	@Column(name ="CUS_CODE",nullable=true,length=36)
	public String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cusCode
	 */
	public void setCusCode(String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  zhongWenQch
	 */
	@Column(name ="ZHONG_WEN_QCH",nullable=true,length=100)
	public String getZhongWenQch(){
		return this.zhongWenQch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  zhongWenQch
	 */
	public void setZhongWenQch(String zhongWenQch){
		this.zhongWenQch = zhongWenQch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  goodsId
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  goodsId
	 */
	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  shpMingCheng
	 */
	@Column(name ="SHP_MING_CHENG",nullable=true,length=167)
	public String getShpMingCheng(){
		return this.shpMingCheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shpMingCheng
	 */
	public void setShpMingCheng(String shpMingCheng){
		this.shpMingCheng = shpMingCheng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  goodsProData
	 */
	@Column(name ="GOODS_PRO_DATA",nullable=true,length=32)
	public String getGoodsProData(){
		return this.goodsProData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  goodsProData
	 */
	public void setGoodsProData(String goodsProData){
		this.goodsProData = goodsProData;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  baseGoodscount
	 */
	@Column(name ="BASE_GOODSCOUNT",nullable=true,length=22)
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
	 *@return: java.lang.String  baseUnit
	 */
	@Column(name ="BASE_UNIT",nullable=true,length=45)
	public String getBaseUnit(){
		return this.baseUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  baseUnit
	 */
	public void setBaseUnit(String baseUnit){
		this.baseUnit = baseUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库位类型
	 */
	@Column(name ="LEIXING",nullable=true,length=32)
	public String getLeixing(){
		return this.leixing;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位类型
	 */
	public void setLeixing(String leixing){
		this.leixing = leixing;
	}
}
