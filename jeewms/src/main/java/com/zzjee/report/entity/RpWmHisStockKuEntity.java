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
 * @Description: rp_wm_his_stock_ku
 * @author erzhongxmu
 * @date 2018-09-11 07:47:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "rp_wm_his_stock_ku", schema = "")
@SuppressWarnings("serial")
public class RpWmHisStockKuEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**结余日期*/
	@Excel(name="结余日期")
	private String hisDate;
	/**货主*/
	@Excel(name="货主")
	private String cusCode;
	/**中文全称*/
	@Excel(name="中文全称")
	private String zhongWenQch;
	/**储位*/
	@Excel(name="储位")
	private String kuWeiBianMa;
	/**托盘*/
	@Excel(name="托盘")
	private String binId;
	/**商品*/
	@Excel(name="商品")
	private String goodsId;
	/**商品名称*/
	@Excel(name="商品名称")
	private String shpMingCheng;
	/**数量*/
	@Excel(name="数量")
	private String count;
	/**单位*/
	@Excel(name="单位")
	private String baseUnit;
	/**zhlKg*/
	@Excel(name="zhlKg")
	private Double zhlKg;
	/**拆零数量*/
	@Excel(name="拆零数量")
	private String chlShl;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=128)
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
	 *@return: java.lang.String  结余日期
	 */
	@Column(name ="HIS_DATE",nullable=true,length=45)
	public String getHisDate(){
		return this.hisDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结余日期
	 */
	public void setHisDate(String hisDate){
		this.hisDate = hisDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货主
	 */
	@Column(name ="CUS_CODE",nullable=true,length=45)
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
	 *@return: java.lang.String  储位
	 */
	@Column(name ="KU_WEI_BIAN_MA",nullable=true,length=45)
	public String getKuWeiBianMa(){
		return this.kuWeiBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  储位
	 */
	public void setKuWeiBianMa(String kuWeiBianMa){
		this.kuWeiBianMa = kuWeiBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  托盘
	 */
	@Column(name ="BIN_ID",nullable=true,length=45)
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
	 *@return: java.lang.String  商品
	 */
	@Column(name ="GOODS_ID",nullable=true,length=45)
	public String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="SHP_MING_CHENG",nullable=true,length=100)
	public String getShpMingCheng(){
		return this.shpMingCheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setShpMingCheng(String shpMingCheng){
		this.shpMingCheng = shpMingCheng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量
	 */
	@Column(name ="COUNT",nullable=true,length=45)
	public String getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setCount(String count){
		this.count = count;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="BASE_UNIT",nullable=true,length=45)
	public String getBaseUnit(){
		return this.baseUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setBaseUnit(String baseUnit){
		this.baseUnit = baseUnit;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  zhlKg
	 */
	@Column(name ="ZHL_KG",nullable=true,length=23)
	public Double getZhlKg(){
		return this.zhlKg;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  zhlKg
	 */
	public void setZhlKg(Double zhlKg){
		this.zhlKg = zhlKg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  拆零数量
	 */
	@Column(name ="CHL_SHL",nullable=true,length=32)
	public String getChlShl(){
		return this.chlShl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  拆零数量
	 */
	public void setChlShl(String chlShl){
		this.chlShl = chlShl;
	}
}
