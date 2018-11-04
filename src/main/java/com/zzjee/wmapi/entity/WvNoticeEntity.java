package com.zzjee.wmapi.entity;

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
 * @Description: wv_notice
 * @author erzhongxmu
 * @date 2018-05-30 20:21:05
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wv_notice", schema = "")
@SuppressWarnings("serial")
public class WvNoticeEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**进货通知单号*/
	@Excel(name="进货通知单号")
	private String noticeId;
	/**客户编码*/
	@Excel(name="客户编码")
	private String cusCode;
	/**中文全称*/
	@Excel(name="中文全称")
	private String zhongWenQch;
	/**单据状态*/
	@Excel(name="单据状态")
	private String imSta;
	/**商品编码*/
	@Excel(name="商品编码")
	private String goodsCode;
	/**shpMingCheng*/
	@Excel(name="shpMingCheng")
	private String shpMingCheng;
	/**shpTiaoMa*/
	@Excel(name="shpTiaoMa")
	private String shpTiaoMa;
	/**cfWenCeng*/
	@Excel(name="cfWenCeng")
	private String cfWenCeng;
	/**mpDanCeng*/
	@Excel(name="mpDanCeng")
	private String mpDanCeng;
	/**mpCengGao*/
	@Excel(name="mpCengGao")
	private String mpCengGao;
	/**tiJiCm*/
	@Excel(name="tiJiCm")
	private String tiJiCm;
	/**数量*/
	@Excel(name="数量")
	private String goodsCount;
	/**goodsQmCount*/
	@Excel(name="goodsQmCount")
	private String goodsQmCount;
	/**grCount*/
	@Excel(name="grCount")
	private Double grCount;
	/**shlDanWei*/
	@Excel(name="shlDanWei")
	private String shlDanWei;
	/**体积*/
	@Excel(name="体积")
	private String goodsFvol;
	/**重量*/
	@Excel(name="重量")
	private String goodsWeight;
	/**lastgrdate*/
	@Excel(name="lastgrdate",format = "yyyy-MM-dd")
	private Date lastgrdate;
	/**preprodate*/
	@Excel(name="preprodate")
	private String preprodate;
	/**recDeg*/
	@Excel(name="recDeg")
	private String recDeg;
	
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
	 *@return: java.lang.String  进货通知单号
	 */
	@Column(name ="NOTICE_ID",nullable=true,length=32)
	public String getNoticeId(){
		return this.noticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  进货通知单号
	 */
	public void setNoticeId(String noticeId){
		this.noticeId = noticeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户编码
	 */
	@Column(name ="CUS_CODE",nullable=true,length=32)
	public String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户编码
	 */
	public void setCusCode(String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文全称
	 */
	@Column(name ="ZHONG_WEN_QCH",nullable=true,length=100)
	public String getZhongWenQch(){
		return this.zhongWenQch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文全称
	 */
	public void setZhongWenQch(String zhongWenQch){
		this.zhongWenQch = zhongWenQch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据状态
	 */
	@Column(name ="IM_STA",nullable=true,length=32)
	public String getImSta(){
		return this.imSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据状态
	 */
	public void setImSta(String imSta){
		this.imSta = imSta;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品编码
	 */
	@Column(name ="GOODS_CODE",nullable=true,length=32)
	public String getGoodsCode(){
		return this.goodsCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编码
	 */
	public void setGoodsCode(String goodsCode){
		this.goodsCode = goodsCode;
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
	 *@return: java.lang.String  shpTiaoMa
	 */
	@Column(name ="SHP_TIAO_MA",nullable=true,length=32)
	public String getShpTiaoMa(){
		return this.shpTiaoMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shpTiaoMa
	 */
	public void setShpTiaoMa(String shpTiaoMa){
		this.shpTiaoMa = shpTiaoMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  cfWenCeng
	 */
	@Column(name ="CF_WEN_CENG",nullable=true,length=32)
	public String getCfWenCeng(){
		return this.cfWenCeng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cfWenCeng
	 */
	public void setCfWenCeng(String cfWenCeng){
		this.cfWenCeng = cfWenCeng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  mpDanCeng
	 */
	@Column(name ="MP_DAN_CENG",nullable=true,length=32)
	public String getMpDanCeng(){
		return this.mpDanCeng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  mpDanCeng
	 */
	public void setMpDanCeng(String mpDanCeng){
		this.mpDanCeng = mpDanCeng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  mpCengGao
	 */
	@Column(name ="MP_CENG_GAO",nullable=true,length=33)
	public String getMpCengGao(){
		return this.mpCengGao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  mpCengGao
	 */
	public void setMpCengGao(String mpCengGao){
		this.mpCengGao = mpCengGao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  tiJiCm
	 */
	@Column(name ="TI_JI_CM",nullable=true,length=32)
	public String getTiJiCm(){
		return this.tiJiCm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  tiJiCm
	 */
	public void setTiJiCm(String tiJiCm){
		this.tiJiCm = tiJiCm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量
	 */
	@Column(name ="GOODS_COUNT",nullable=true,length=32)
	public String getGoodsCount(){
		return this.goodsCount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setGoodsCount(String goodsCount){
		this.goodsCount = goodsCount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  goodsQmCount
	 */
	@Column(name ="GOODS_QM_COUNT",nullable=true,length=32)
	public String getGoodsQmCount(){
		return this.goodsQmCount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  goodsQmCount
	 */
	public void setGoodsQmCount(String goodsQmCount){
		this.goodsQmCount = goodsQmCount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  grCount
	 */
	@Column(name ="GR_COUNT",nullable=true,length=23)
	public Double getGrCount(){
		return this.grCount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  grCount
	 */
	public void setGrCount(Double grCount){
		this.grCount = grCount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  shlDanWei
	 */
	@Column(name ="SHL_DAN_WEI",nullable=true,length=32)
	public String getShlDanWei(){
		return this.shlDanWei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shlDanWei
	 */
	public void setShlDanWei(String shlDanWei){
		this.shlDanWei = shlDanWei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  体积
	 */
	@Column(name ="GOODS_FVOL",nullable=true,length=32)
	public String getGoodsFvol(){
		return this.goodsFvol;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  体积
	 */
	public void setGoodsFvol(String goodsFvol){
		this.goodsFvol = goodsFvol;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重量
	 */
	@Column(name ="GOODS_WEIGHT",nullable=true,length=32)
	public String getGoodsWeight(){
		return this.goodsWeight;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重量
	 */
	public void setGoodsWeight(String goodsWeight){
		this.goodsWeight = goodsWeight;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  lastgrdate
	 */
	@Column(name ="LASTGRDATE",nullable=true)
	public Date getLastgrdate(){
		return this.lastgrdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  lastgrdate
	 */
	public void setLastgrdate(Date lastgrdate){
		this.lastgrdate = lastgrdate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  preprodate
	 */
	@Column(name ="PREPRODATE",nullable=true,length=32)
	public String getPreprodate(){
		return this.preprodate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  preprodate
	 */
	public void setPreprodate(String preprodate){
		this.preprodate = preprodate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  recDeg
	 */
	@Column(name ="REC_DEG",nullable=true,length=32)
	public String getRecDeg(){
		return this.recDeg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  recDeg
	 */
	public void setRecDeg(String recDeg){
		this.recDeg = recDeg;
	}
}
