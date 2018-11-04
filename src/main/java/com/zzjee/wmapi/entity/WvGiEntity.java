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
 * @Description: wv_gi
 * @author erzhongxmu
 * @date 2018-05-30 20:21:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wv_gi", schema = "")
@SuppressWarnings("serial")
public class WvGiEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**库位编码*/
	@Excel(name="库位编码")
	private String kuWeiBianMa;
	/**原始单据编码*/
	@Excel(name="原始单据编码")
	private String orderId;
	/**商品编码*/
	@Excel(name="商品编码")
	private String goodsId;
	/**goodsName*/
	@Excel(name="goodsName")
	private String goodsName;
	/**基本单位数量*/
	@Excel(name="基本单位数量")
	private String goodsQua;
	/**shlDanWei*/
	@Excel(name="shlDanWei")
	private String shlDanWei;
	/**zhxUnit*/
	@Excel(name="zhxUnit")
	private String zhxUnit;
	/**chlShl*/
	@Excel(name="chlShl")
	private String chlShl;
	/**货主*/
	@Excel(name="货主")
	private String cusCode;
	/**cusName*/
	@Excel(name="cusName")
	private String cusName;
	/**生产日期*/
	@Excel(name="生产日期")
	private String goodsProData;
	/**状态*/
	@Excel(name="状态")
	private String downSta;
	
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
	 *@return: java.lang.String  goodsName
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=167)
	public String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  goodsName
	 */
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位数量
	 */
	@Column(name ="GOODS_QUA",nullable=true,length=45)
	public String getGoodsQua(){
		return this.goodsQua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位数量
	 */
	public void setGoodsQua(String goodsQua){
		this.goodsQua = goodsQua;
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
	 *@return: java.lang.String  zhxUnit
	 */
	@Column(name ="ZHX_UNIT",nullable=true,length=32)
	public String getZhxUnit(){
		return this.zhxUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  zhxUnit
	 */
	public void setZhxUnit(String zhxUnit){
		this.zhxUnit = zhxUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  chlShl
	 */
	@Column(name ="CHL_SHL",nullable=true,length=32)
	public String getChlShl(){
		return this.chlShl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  chlShl
	 */
	public void setChlShl(String chlShl){
		this.chlShl = chlShl;
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
	 *@return: java.lang.String  cusName
	 */
	@Column(name ="CUS_NAME",nullable=true,length=133)
	public String getCusName(){
		return this.cusName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cusName
	 */
	public void setCusName(String cusName){
		this.cusName = cusName;
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
}
