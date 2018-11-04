package com.zzjee.md.entity;

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
 * @Description: 商品视图
 * @author erzhongxmu
 * @date 2017-09-11 20:01:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "mv_goods", schema = "")
@SuppressWarnings("serial")
public class MvGoodsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**cusCode*/
	@Excel(name="cusCode")
	private java.lang.String cusCode;
	/**goodsCode*/
	@Excel(name="goodsCode")
	private java.lang.String goodsCode;
	@Excel(name="goodsId")
	private java.lang.String goodsId;
	/**goodsName*/
	@Excel(name="goodsName")
	private java.lang.String goodsName;
	/**shlDanWei*/
	@Excel(name="shlDanWei")
	private java.lang.String shlDanWei;
	/**cfWenCeng*/
	@Excel(name="cfWenCeng")
	private java.lang.String cfWenCeng;
	/**mpDanCeng*/
	@Excel(name="mpDanCeng")
	private java.lang.String mpDanCeng;
	/**mpCengGao*/
	@Excel(name="mpCengGao")
	private java.lang.String mpCengGao;
	/**shpTiaoMa*/
	@Excel(name="shpTiaoMa")
	private java.lang.String shpTiaoMa;
	/**bzhiQi*/
	@Excel(name="bzhiQi")
	private java.lang.String bzhiQi;
	/**chlShl*/
	@Excel(name="chlShl")
	private java.lang.String chlShl;
	/**tiJiCm*/
	@Excel(name="tiJiCm")
	private java.lang.String tiJiCm;
	/**zhlKg*/
	@Excel(name="zhlKg")
	private java.lang.String zhlKg;
	/**baseunit*/
	@Excel(name="baseunit")
	private java.lang.String baseunit;
	private java.lang.String shpMingCheng;


	
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
	 *@return: java.lang.String  cusCode
	 */
	@Column(name ="CUS_CODE",nullable=true,length=100)
	public java.lang.String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cusCode
	 */
	public void setCusCode(java.lang.String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  goodsCode
	 */
	
	@Column(name ="GOODS_ID",nullable=true,length=33)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  goodsCode
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	
	@Column(name ="GOODS_CODE",nullable=true,length=33)
	public java.lang.String getGoodsCode(){
		return this.goodsCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  goodsCode
	 */
	public void setGoodsCode(java.lang.String goodsCode){
		this.goodsCode = goodsCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  goodsName
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=166)
	public java.lang.String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  goodsName
	 */
	public void setGoodsName(java.lang.String goodsName){
		this.goodsName = goodsName;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="SHP_MING_CHENG",nullable=true,length=100)
	public java.lang.String getShpMingCheng(){
		return this.shpMingCheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setShpMingCheng(java.lang.String shpMingCheng){
		this.shpMingCheng = shpMingCheng;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  shlDanWei
	 */
	@Column(name ="SHL_DAN_WEI",nullable=true,length=32)
	public java.lang.String getShlDanWei(){
		return this.shlDanWei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shlDanWei
	 */
	public void setShlDanWei(java.lang.String shlDanWei){
		this.shlDanWei = shlDanWei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  cfWenCeng
	 */
	@Column(name ="CF_WEN_CENG",nullable=true,length=32)
	public java.lang.String getCfWenCeng(){
		return this.cfWenCeng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cfWenCeng
	 */
	public void setCfWenCeng(java.lang.String cfWenCeng){
		this.cfWenCeng = cfWenCeng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  mpDanCeng
	 */
	@Column(name ="MP_DAN_CENG",nullable=true,length=32)
	public java.lang.String getMpDanCeng(){
		return this.mpDanCeng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  mpDanCeng
	 */
	public void setMpDanCeng(java.lang.String mpDanCeng){
		this.mpDanCeng = mpDanCeng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  mpCengGao
	 */
	@Column(name ="MP_CENG_GAO",nullable=true,length=33)
	public java.lang.String getMpCengGao(){
		return this.mpCengGao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  mpCengGao
	 */
	public void setMpCengGao(java.lang.String mpCengGao){
		this.mpCengGao = mpCengGao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  shpTiaoMa
	 */
	@Column(name ="SHP_TIAO_MA",nullable=true,length=32)
	public java.lang.String getShpTiaoMa(){
		return this.shpTiaoMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shpTiaoMa
	 */
	public void setShpTiaoMa(java.lang.String shpTiaoMa){
		this.shpTiaoMa = shpTiaoMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  bzhiQi
	 */
	@Column(name ="BZHI_QI",nullable=true,length=32)
	public java.lang.String getBzhiQi(){
		return this.bzhiQi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  bzhiQi
	 */
	public void setBzhiQi(java.lang.String bzhiQi){
		this.bzhiQi = bzhiQi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  chlShl
	 */
	@Column(name ="CHL_SHL",nullable=true,length=32)
	public java.lang.String getChlShl(){
		return this.chlShl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  chlShl
	 */
	public void setChlShl(java.lang.String chlShl){
		this.chlShl = chlShl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  tiJiCm
	 */
	@Column(name ="TI_JI_CM",nullable=true,length=53)
	public java.lang.String getTiJiCm(){
		return this.tiJiCm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  tiJiCm
	 */
	public void setTiJiCm(java.lang.String tiJiCm){
		this.tiJiCm = tiJiCm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  zhlKg
	 */
	@Column(name ="ZHL_KG",nullable=true,length=53)
	public java.lang.String getZhlKg(){
		return this.zhlKg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  zhlKg
	 */
	public void setZhlKg(java.lang.String zhlKg){
		this.zhlKg = zhlKg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  baseunit
	 */
	@Column(name ="BASEUNIT",nullable=true,length=32)
	public java.lang.String getBaseunit(){
		return this.baseunit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  baseunit
	 */
	public void setBaseunit(java.lang.String baseunit){
		this.baseunit = baseunit;
	}


}
