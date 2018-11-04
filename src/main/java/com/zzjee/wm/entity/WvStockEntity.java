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
 * @Description: 生成盘点单
 * @author erzhongxmu
 * @date 2017-09-08 22:26:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wv_stock_stt", schema = "")
@SuppressWarnings("serial")
public class WvStockEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	private java.util.Date lastMove;
	/**库存类型*/
	@Excel(name="库存类型")
	private java.lang.String kuctype;
	/**储位*/
	@Excel(name="储位")
	private java.lang.String kuWeiBianMa;
	/**托盘*/
	@Excel(name="托盘")
	private java.lang.String binId;
	/**货主*/
	@Excel(name="货主")
	private java.lang.String cusCode;
	/**货主名称*/
	@Excel(name="货主名称")
	private java.lang.String zhongWenQch;
	/**商品编码*/
	@Excel(name="商品编码")
	private java.lang.String goodsId;
	private java.lang.String goodsCode;
	/**商品数量*/
	@Excel(name="商品数量")
	private java.lang.Long goodsQua;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String shpMingCheng;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.lang.String goodsProData;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.String bzhiQi;
	//允收天数
	private java.lang.String yushoutianshu;

	/**单位*/
	@Excel(name="单位")
	private java.lang.String goodsUnit;

	private java.lang.String sttSta;

	private java.lang.String moveSta;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=128)
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
	
	@Column(name ="LAST_MOVE",nullable=true)
	public java.util.Date getLastMove(){
		return this.lastMove;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setLastMove(java.util.Date lastMove){
		this.lastMove = lastMove;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库存类型
	 */
	@Column(name ="KUCTYPE",nullable=false,length=2)
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
	public void setGoodsCode(java.lang.String goodsCode){
		this.goodsCode = goodsCode;
	}
	@Column(name ="GOODS_CODE",nullable=true,length=36)
	public java.lang.String getGoodsCode(){
		return this.goodsCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编码
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  商品数量
	 */
	@Column(name ="GOODS_QUA",nullable=true,length=22)
	public java.lang.Long getGoodsQua(){
		return this.goodsQua;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  商品数量
	 */
	public void setGoodsQua(java.lang.Long goodsQua){
		this.goodsQua = goodsQua;
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
	@Column(name ="STT_STA",nullable=true,length=36)
	public java.lang.String getSttSta() {
		return sttSta;
	}
	public void setSttSta(java.lang.String sttSta) {
		this.sttSta = sttSta;
	}
	@Column(name ="MOVE_STA",nullable=true,length=36)
	public java.lang.String getMoveSta() {
		return moveSta;
	}
	public void setMoveSta(java.lang.String moveSta) {
		this.moveSta = moveSta;
	}
	@Column(name ="yushoutianshu",nullable=true,length=36)
	public String getYushoutianshu() {
		return yushoutianshu;
	}

	public void setYushoutianshu(String yushoutianshu) {
		this.yushoutianshu = yushoutianshu;
	}
}
