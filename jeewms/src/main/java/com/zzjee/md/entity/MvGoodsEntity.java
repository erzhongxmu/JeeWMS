package com.zzjee.md.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
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
	@Excel(name="商品条码")
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

	@Excel(name="商品规格")
	private java.lang.String shpGuiGe;
	/**商品品牌*/
	@Excel(name="商品品牌")
	private java.lang.String shpPinPai;
	@Excel(name="长")
	private java.lang.String chZhXiang;
	/**宽整箱*/
	@Excel(name="宽")
	private java.lang.String kuZhXiang;
	/**高整箱*/
	@Excel(name="高")
	private java.lang.String gaoZhXiang;
	@Excel(name="产品属性")
	private java.lang.String chpShuXing;
	@Excel(name="价格")
	private java.lang.String gaoDanPin;
	@Excel(name="SKU")
	private java.lang.String sku;
	@Column(name ="gao_dan_pin",nullable=true,length=100)
	public String getGaoDanPin() {
		return gaoDanPin;
	}

	public MvGoodsEntity setGaoDanPin(String gaoDanPin) {
		this.gaoDanPin = gaoDanPin;
		return this;
	}

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

	@Column(name ="SHP_GUI_GE",nullable=true,length=32)
	public java.lang.String getShpGuiGe(){
		return this.shpGuiGe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品规格
	 */
	public void setShpGuiGe(java.lang.String shpGuiGe){
		this.shpGuiGe = shpGuiGe;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  长整箱
	 */
	@Column(name ="CH_ZH_XIANG",nullable=true,length=32)
	public java.lang.String getChZhXiang(){
		return this.chZhXiang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  长整箱
	 */
	public void setChZhXiang(java.lang.String chZhXiang){
		this.chZhXiang = chZhXiang;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  宽整箱
	 */
	@Column(name ="KU_ZH_XIANG",nullable=true,length=32)
	public java.lang.String getKuZhXiang(){
		return this.kuZhXiang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  宽整箱
	 */
	public void setKuZhXiang(java.lang.String kuZhXiang){
		this.kuZhXiang = kuZhXiang;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  高整箱
	 */
	@Column(name ="GAO_ZH_XIANG",nullable=true,length=32)
	public java.lang.String getGaoZhXiang(){
		return this.gaoZhXiang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  高整箱
	 */
	public void setGaoZhXiang(java.lang.String gaoZhXiang){
		this.gaoZhXiang = gaoZhXiang;
	}
	@Column(name ="SHP_PIN_PAI",nullable=true,length=35)
	public java.lang.String getShpPinPai(){
		return this.shpPinPai;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品品牌
	 */
	public void setShpPinPai(java.lang.String shpPinPai){
		this.shpPinPai = shpPinPai;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品条码
	 */
	@Column(name ="CHP_SHU_XING",nullable=true,length=32)
	public java.lang.String getChpShuXing(){
		return this.chpShuXing;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品属性
	 */
	public void setChpShuXing(java.lang.String chpShuXing){
		this.chpShuXing = chpShuXing;
	}

	@Column(name ="sku",nullable=true,length=32)
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}
