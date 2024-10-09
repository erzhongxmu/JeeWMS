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
 * @Description: 储位盘点
 * @author erzhongxmu
 * @date 2017-11-20 22:48:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wv_stock_stt_bin", schema = "")
@SuppressWarnings("serial")
public class WvStockSttBinEntity implements java.io.Serializable {
	/**createDate*/
	private java.util.Date createDate;
	/**createName*/
	private java.lang.String createName;
	/**createBy*/
	private java.lang.String createBy;
	/**id*/
	private java.lang.String id;
	/**库存类型*/
	@Excel(name="库存类型")
	private java.lang.String kuctype;
	/**储位*/
	@Excel(name="储位")
	private java.lang.String kuWeiBianMa;
	/**托盘*/
	private java.lang.String binId;
	/**货主*/
	@Excel(name="货主")
	private java.lang.String cusCode;
	/**货主名称*/
	@Excel(name="货主名称")
	private java.lang.String zhongWenQch;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String goodsId;
	/**数量*/
	@Excel(name="数量")
	private java.lang.Integer goodsQua;
	/**品名*/
	@Excel(name="品名")
	private java.lang.String shpMingCheng;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.lang.String goodsProData;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.String bzhiQi;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String goodsUnit;
	/**盘点状态*/
	@Excel(name="盘点状态")
	private java.lang.String sttSta;
	/**移动状态*/
	@Excel(name="移动状态")
	private java.lang.String moveSta;
	/**最后移动时间*/
	@Excel(name="最后移动时间",format = "yyyy-MM-dd")
	private java.util.Date lastMove;
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createDate
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createDate
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createName
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createName
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createBy
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createBy
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=true,length=100)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库存类型
	 */
	@Column(name ="KUCTYPE",nullable=false,length=3)
	public java.lang.String getKuctype(){
		return this.kuctype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库存类型
	 */
	public void setKuctype(java.lang.String kuctype){
		this.kuctype = kuctype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  储位
	 */
	@Column(name ="KU_WEI_BIAN_MA",nullable=true,length=32)
	public java.lang.String getKuWeiBianMa(){
		return this.kuWeiBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  储位
	 */
	public void setKuWeiBianMa(java.lang.String kuWeiBianMa){
		this.kuWeiBianMa = kuWeiBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  托盘
	 */
	@Column(name ="BIN_ID",nullable=true,length=32)
	public java.lang.String getBinId(){
		return this.binId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  托盘
	 */
	public void setBinId(java.lang.String binId){
		this.binId = binId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货主
	 */
	@Column(name ="CUS_CODE",nullable=true,length=36)
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
	 *@return: java.lang.String  货主名称
	 */
	@Column(name ="ZHONG_WEN_QCH",nullable=true,length=100)
	public java.lang.String getZhongWenQch(){
		return this.zhongWenQch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  货主名称
	 */
	public void setZhongWenQch(java.lang.String zhongWenQch){
		this.zhongWenQch = zhongWenQch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  数量
	 */
	@Column(name ="GOODS_QUA",nullable=true,length=19)
	public java.lang.Integer getGoodsQua(){
		return this.goodsQua;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数量
	 */
	public void setGoodsQua(java.lang.Integer goodsQua){
		this.goodsQua = goodsQua;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品名
	 */
	@Column(name ="SHP_MING_CHENG",nullable=true,length=167)
	public java.lang.String getShpMingCheng(){
		return this.shpMingCheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品名
	 */
	public void setShpMingCheng(java.lang.String shpMingCheng){
		this.shpMingCheng = shpMingCheng;
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
	 *@return: java.lang.String  保质期
	 */
	@Column(name ="BZHI_QI",nullable=true,length=32)
	public java.lang.String getBzhiQi(){
		return this.bzhiQi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保质期
	 */
	public void setBzhiQi(java.lang.String bzhiQi){
		this.bzhiQi = bzhiQi;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  移动状态
	 */
	@Column(name ="MOVE_STA",nullable=true,length=45)
	public java.lang.String getMoveSta(){
		return this.moveSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  移动状态
	 */
	public void setMoveSta(java.lang.String moveSta){
		this.moveSta = moveSta;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  最后移动时间
	 */
	@Column(name ="LAST_MOVE",nullable=true)
	public java.util.Date getLastMove(){
		return this.lastMove;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  最后移动时间
	 */
	public void setLastMove(java.util.Date lastMove){
		this.lastMove = lastMove;
	}
}
