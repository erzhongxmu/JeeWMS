package com.zzjee.mvyj.entity;

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
 * @Description: 效期预警
 * @author erzhongxmu
 * @date 2017-09-17 22:13:08
 * @version V1.0   
 *
 */
@Entity
@Table(name = "mv_stock_yj", schema = "")
@SuppressWarnings("serial")
public class MvStockYjEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**库存类型*/
	@Excel(name="库存类型")
	private java.lang.String kuctype;
	/**基本数量*/
	@Excel(name="基本数量")
	private java.lang.Integer baseGoodscount;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String baseUnit;
	/**储位*/
	@Excel(name="储位")
	private java.lang.String kuWeiBianMa;
	/**托盘*/
	@Excel(name="托盘")
	private java.lang.String binId;
	/**客户编码*/
	@Excel(name="客户编码")
	private java.lang.String cusCode;
	/**客户名称*/
	@Excel(name="客户名称")
	private java.lang.String zhongWenQch;
	/**商品编码*/
	@Excel(name="商品编码")
	private java.lang.String goodsId;
	@Excel(name="客户商品编码")
	private java.lang.String shpBianmakh;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String shpMingCheng;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.lang.String goodsProData;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.String bzhiQi;
	/**到期日*/
	@Excel(name="到期日")
	private java.lang.String dqr;
	/**剩余天数*/
	@Excel(name="剩余天数")
	private java.lang.String resDate;
	@Excel(name="剩余比例")
	private java.lang.String guoqiBili;

	/**取货次序*/

	private java.lang.String quHuoCiXu;
	/**上架次序*/

	private java.lang.String shangJiaCiXu;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  基本数量
	 */
	@Column(name ="BASE_GOODSCOUNT",nullable=true,length=19)
	public java.lang.Integer getBaseGoodscount(){
		return this.baseGoodscount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  基本数量
	 */
	public void setBaseGoodscount(java.lang.Integer baseGoodscount){
		this.baseGoodscount = baseGoodscount;
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
	 *@return: java.lang.String  客户编码
	 */
	@Column(name ="CUS_CODE",nullable=true,length=36)
	public java.lang.String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户编码
	 */
	public void setCusCode(java.lang.String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户名称
	 */
	@Column(name ="ZHONG_WEN_QCH",nullable=true,length=100)
	public java.lang.String getZhongWenQch(){
		return this.zhongWenQch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户名称
	 */
	public void setZhongWenQch(java.lang.String zhongWenQch){
		this.zhongWenQch = zhongWenQch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品编码
	 */
	@Column(name ="GOODS_ID",nullable=true,length=32)
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
	
	@Column(name ="shp_bian_makh",nullable=true,length=32)
	public java.lang.String getShpBianmakh(){
		return this.shpBianmakh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编码
	 */
	public void setShpBianmakh(java.lang.String shpBianmakh){
		this.shpBianmakh = shpBianmakh;
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
	 *@return: java.lang.String  到期日
	 */
	@Column(name ="DQR",nullable=true,length=29)
	public java.lang.String getDqr(){
		return this.dqr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到期日
	 */
	public void setDqr(java.lang.String dqr){
		this.dqr = dqr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  取货次序
	 */
	@Column(name ="QU_HUO_CI_XU",nullable=true,length=32)
	public java.lang.String getQuHuoCiXu(){
		return this.quHuoCiXu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  取货次序
	 */
	public void setQuHuoCiXu(java.lang.String quHuoCiXu){
		this.quHuoCiXu = quHuoCiXu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上架次序
	 */
	@Column(name ="SHANG_JIA_CI_XU",nullable=true,length=32)
	public java.lang.String getShangJiaCiXu(){
		return this.shangJiaCiXu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  剩余天数
	 */
	public void setShangJiaCiXu(java.lang.String shangJiaCiXu){
		this.shangJiaCiXu = shangJiaCiXu;
	}
	
	@Column(name ="RES_DATE",nullable=true,length=32)
	public java.lang.String getResDate(){
		return this.resDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  过期比例
	 */
	public void setResDate(java.lang.String resDate){
		this.resDate = resDate;
	}
	@Column(name ="guoqibili",nullable=true,length=32)
	public java.lang.String getGuoqiBili(){
		return this.guoqiBili;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  过期比例
	 */
	public void setGuoqiBili(java.lang.String guoqiBili){
		this.guoqiBili = guoqiBili;
	}
}
