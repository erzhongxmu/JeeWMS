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
 * @Description: wv_gi_notice
 * @author erzhongxmu
 * @date 2018-05-30 20:20:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wv_gi_notice", schema = "")
@SuppressWarnings("serial")
public class WvGiNoticeEntity implements java.io.Serializable {
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private Date createDate;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private String createBy;
	/**主键*/
	private String id;
	/**到货通知单*/
	@Excel(name="到货通知单")
	private String omNoticeId;
	/**到货通知行项目*/
	@Excel(name="到货通知行项目")
	private String iomNoticeItem;
	/**仓位*/
	@Excel(name="仓位")
	private String binId;
	/**托盘*/
	@Excel(name="托盘")
	private String tinId;
	/**货主*/
	@Excel(name="货主")
	private String cusCode;
	/**中文全称*/
	@Excel(name="中文全称")
	private String zhongWenQch;
	/**商品编码*/
	@Excel(name="商品编码")
	private String goodsId;
	/**数量*/
	@Excel(name="数量")
	private String giCount;
	/**shpMingCheng*/
	@Excel(name="shpMingCheng")
	private String shpMingCheng;
	/**生产日期*/
	@Excel(name="生产日期")
	private String goodsProData;
	/**bzhiQi*/
	@Excel(name="bzhiQi")
	private String bzhiQi;
	/**zhxUnit*/
	@Excel(name="zhxUnit")
	private String zhxUnit;
	/**chlShl*/
	@Excel(name="chlShl")
	private String chlShl;
	/**单位*/
	@Excel(name="单位")
	private String shlDanWei;
	/**单位*/
	@Excel(name="单位")
	private String goodsUnit;
	/**基本单位*/
	@Excel(name="基本单位")
	private String baseUnit;
	/**基本单位数量*/
	@Excel(name="基本单位数量")
	private String baseGoodscount;
	/**omBeiZhu*/
	@Excel(name="omBeiZhu")
	private String omBeiZhu;
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
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
	 *@return: java.lang.String  到货通知单
	 */
	@Column(name ="OM_NOTICE_ID",nullable=true,length=36)
	public String getOmNoticeId(){
		return this.omNoticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到货通知单
	 */
	public void setOmNoticeId(String omNoticeId){
		this.omNoticeId = omNoticeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  到货通知行项目
	 */
	@Column(name ="IOM_NOTICE_ITEM",nullable=true,length=36)
	public String getIomNoticeItem(){
		return this.iomNoticeItem;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到货通知行项目
	 */
	public void setIomNoticeItem(String iomNoticeItem){
		this.iomNoticeItem = iomNoticeItem;
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
	 *@return: java.lang.String  数量
	 */
	@Column(name ="GI_COUNT",nullable=true,length=32)
	public String getGiCount(){
		return this.giCount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setGiCount(String giCount){
		this.giCount = giCount;
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
	 *@return: java.lang.String  bzhiQi
	 */
	@Column(name ="BZHI_QI",nullable=true,length=32)
	public String getBzhiQi(){
		return this.bzhiQi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  bzhiQi
	 */
	public void setBzhiQi(String bzhiQi){
		this.bzhiQi = bzhiQi;
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
	 *@return: java.lang.String  单位
	 */
	@Column(name ="SHL_DAN_WEI",nullable=true,length=36)
	public String getShlDanWei(){
		return this.shlDanWei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setShlDanWei(String shlDanWei){
		this.shlDanWei = shlDanWei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="GOODS_UNIT",nullable=true,length=36)
	public String getGoodsUnit(){
		return this.goodsUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setGoodsUnit(String goodsUnit){
		this.goodsUnit = goodsUnit;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位数量
	 */
	@Column(name ="BASE_GOODSCOUNT",nullable=true,length=45)
	public String getBaseGoodscount(){
		return this.baseGoodscount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位数量
	 */
	public void setBaseGoodscount(String baseGoodscount){
		this.baseGoodscount = baseGoodscount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  omBeiZhu
	 */
	@Column(name ="OM_BEI_ZHU",nullable=true,length=32)
	public String getOmBeiZhu(){
		return this.omBeiZhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  omBeiZhu
	 */
	public void setOmBeiZhu(String omBeiZhu){
		this.omBeiZhu = omBeiZhu;
	}
}
