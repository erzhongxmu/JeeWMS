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
 * @Description: rp_wm_in_qm
 * @author erzhongxmu
 * @date 2018-09-11 07:46:59
 * @version V1.0   
 *
 */
@Entity
@Table(name = "rp_wm_in_qm", schema = "")
@SuppressWarnings("serial")
public class RpWmInQmEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private Date createDate;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private String createBy;
	/**到货通知单*/
	@Excel(name="到货通知单")
	private String imNoticeId;
	/**备注*/
	@Excel(name="备注")
	private String itemText;
	/**温度*/
	@Excel(name="温度")
	private String recDeg;
	/**仓位*/
	@Excel(name="仓位")
	private String kuWeiBianMa;
	/**托盘*/
	@Excel(name="托盘")
	private String binId;
	/**货主*/
	@Excel(name="货主")
	private String cusCode;
	/**中文全称*/
	@Excel(name="中文全称")
	private String zhongWenQch;
	/**goodsCode*/
	@Excel(name="goodsCode")
	private String goodsCode;
	/**商品编码*/
	@Excel(name="商品编码")
	private String goodsId;
	/**收货数量*/
	@Excel(name="收货数量")
	private String goodsQua;
	/**shpMingCheng*/
	@Excel(name="shpMingCheng")
	private String shpMingCheng;
	/**生产日期*/
	@Excel(name="生产日期")
	private String goodsProData;
	/**bzhiQi*/
	@Excel(name="bzhiQi")
	private String bzhiQi;
	/**单位*/
	@Excel(name="单位")
	private String goodsUnit;
	/**基本单位*/
	@Excel(name="基本单位")
	private String baseUnit;
	/**基本单位数量*/
	@Excel(name="基本单位数量")
	private String baseGoodscount;
	/**zhlKg*/
	@Excel(name="zhlKg")
	private String zhlKg;
	/**sumzhl*/
	@Excel(name="sumzhl")
	private Double sumzhl;
	
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
	 *@return: java.lang.String  到货通知单
	 */
	@Column(name ="IM_NOTICE_ID",nullable=true,length=36)
	public String getImNoticeId(){
		return this.imNoticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到货通知单
	 */
	public void setImNoticeId(String imNoticeId){
		this.imNoticeId = imNoticeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="ITEM_TEXT",nullable=true,length=32)
	public String getItemText(){
		return this.itemText;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setItemText(String itemText){
		this.itemText = itemText;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  温度
	 */
	@Column(name ="REC_DEG",nullable=true,length=32)
	public String getRecDeg(){
		return this.recDeg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  温度
	 */
	public void setRecDeg(String recDeg){
		this.recDeg = recDeg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓位
	 */
	@Column(name ="KU_WEI_BIAN_MA",nullable=true,length=32)
	public String getKuWeiBianMa(){
		return this.kuWeiBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓位
	 */
	public void setKuWeiBianMa(String kuWeiBianMa){
		this.kuWeiBianMa = kuWeiBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  托盘
	 */
	@Column(name ="BIN_ID",nullable=true,length=32)
	public String getBinId(){
		return this.binId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  托盘
	 */
	public void setBinId(String binId){
		this.binId = binId;
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
	 *@return: java.lang.String  goodsCode
	 */
	@Column(name ="GOODS_CODE",nullable=true,length=32)
	public String getGoodsCode(){
		return this.goodsCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  goodsCode
	 */
	public void setGoodsCode(String goodsCode){
		this.goodsCode = goodsCode;
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
	 *@return: java.lang.String  收货数量
	 */
	@Column(name ="GOODS_QUA",nullable=true,length=32)
	public String getGoodsQua(){
		return this.goodsQua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货数量
	 */
	public void setGoodsQua(String goodsQua){
		this.goodsQua = goodsQua;
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
	 *@return: java.lang.String  zhlKg
	 */
	@Column(name ="ZHL_KG",nullable=true,length=53)
	public String getZhlKg(){
		return this.zhlKg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  zhlKg
	 */
	public void setZhlKg(String zhlKg){
		this.zhlKg = zhlKg;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  sumzhl
	 */
	@Column(name ="SUMZHL",nullable=true,length=23)
	public Double getSumzhl(){
		return this.sumzhl;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  sumzhl
	 */
	public void setSumzhl(Double sumzhl){
		this.sumzhl = sumzhl;
	}
}
