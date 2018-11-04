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
 * @Description: 预约通知项目
 * @author erzhongxmu
 * @date 2017-09-11 15:08:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_im_notice_i", schema = "")
@SuppressWarnings("serial")
public class WmImNoticeIEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
 
	private java.lang.String createName;
	/**创建人登录名称*/
   
	private java.lang.String createBy;
	/**创建日期*/
 
	private java.util.Date createDate;
	/**更新人名称*/
  
	private java.lang.String updateName;
	/**更新人登录名称*/
    
	private java.lang.String updateBy;
	/**更新日期*/
  
	private java.util.Date updateDate;
	/**所属部门*/
   
	private java.lang.String sysOrgCode;
	/**所属公司*/
    
	private java.lang.String sysCompanyCode;
	/**到货通知单号*/
    @Excel(name="到货通知单号")
	private java.lang.String imNoticeId;
	/**到货通知项目*/
	/**客户订单号*/
	@Excel(name="客户订单号")
	private java.lang.String imCusCode;
	@Excel(name="备注")
	private java.lang.String imBeizhu;

	private java.lang.String imNoticeItem;
	/**商品编码*/
    @Excel(name="商品编码")
	private java.lang.String goodsCode;
	@Excel(name="商品名称")
	private java.lang.String goodsName;
//	@Excel(name="其他系统ID")
	private java.lang.String otherId;
	/**数量*/
    @Excel(name="数量")
	private java.lang.String goodsCount;
	/**生产日期*/
    @Excel(name="生产日期",format = "yyyy-MM-dd")
	private java.util.Date goodsPrdData;
	/**批次*/
 
	private java.lang.String goodsBatch;
	/**库位整理*/
    @Excel(name="收货完成")
	private java.lang.String binPre;
	/**体积*/
 
	private java.lang.String goodsFvol;
	/**重量*/
 
	private java.lang.String goodsWeight;
	/**计划库位*/
  
	private java.lang.String binPlan;
	/**单位*/
    @Excel(name="单位")
	private java.lang.String goodsUnit;
	/**未清数量*/
   
	private java.lang.String goodsWqmCount;
	/**收货登记数量*/
  
	private java.lang.String goodsQmCount;
	/**行项目状态*/
 
	private java.lang.String noticeiSta;
	/**基本单位*/
    
	private java.lang.String baseUnit;
	/**基本单位数量*/
 
	private java.lang.String baseGoodscount;
	/**基本单位收货数量*/
   
	private java.lang.String baseQmcount;
	@Excel(name="商品条码")
	private java.lang.String barCode;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="IM_BEIZHU",nullable=true,length=32)
	public java.lang.String getImBeizhu(){
		return this.imBeizhu;
	}


	@Column(name ="IM_CUS_CODE",nullable=true,length=32)
	public java.lang.String getImCusCode(){
		return this.imCusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户订单号
	 */
	public void setImCusCode(java.lang.String imCusCode){
		this.imCusCode = imCusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setImBeizhu(java.lang.String imBeizhu){
		this.imBeizhu = imBeizhu;
	}
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
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */
	
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public java.lang.String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(java.lang.String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公司
	 */
	
	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public java.lang.String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公司
	 */
	public void setSysCompanyCode(java.lang.String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  到货通知单号
	 */
	
	@Column(name ="IM_NOTICE_ID",nullable=true,length=36)
	public java.lang.String getImNoticeId(){
		return this.imNoticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到货通知单号
	 */
	public void setImNoticeId(java.lang.String imNoticeId){
		this.imNoticeId = imNoticeId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  到货通知项目
	 */
	
	@Column(name ="IM_NOTICE_ITEM",nullable=true,length=36)
	public java.lang.String getImNoticeItem(){
		return this.imNoticeItem;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到货通知项目
	 */
	public void setImNoticeItem(java.lang.String imNoticeItem){
		this.imNoticeItem = imNoticeItem;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品编码
	 */
	
	@Column(name ="GOODS_CODE",nullable=true,length=32)
	public java.lang.String getGoodsCode(){
		return this.goodsCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编码
	 */
	public void setGoodsCode(java.lang.String goodsCode){
		this.goodsCode = goodsCode;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量
	 */
	
	@Column(name ="GOODS_COUNT",nullable=true,length=32)
	public java.lang.String getGoodsCount(){
		return this.goodsCount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setGoodsCount(java.lang.String goodsCount){
		this.goodsCount = goodsCount;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	
	@Column(name ="GOODS_PRD_DATA",nullable=true)
	public java.util.Date getGoodsPrdData(){
		return this.goodsPrdData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setGoodsPrdData(java.util.Date goodsPrdData){
		this.goodsPrdData = goodsPrdData;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批次
	 */
	
	@Column(name ="GOODS_BATCH",nullable=true,length=32)
	public java.lang.String getGoodsBatch(){
		return this.goodsBatch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批次
	 */
	public void setGoodsBatch(java.lang.String goodsBatch){
		this.goodsBatch = goodsBatch;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库位整理
	 */
	
	@Column(name ="BIN_PRE",nullable=true,length=32)
	public java.lang.String getBinPre(){
		return this.binPre;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位整理
	 */
	public void setBinPre(java.lang.String binPre){
		this.binPre = binPre;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  体积
	 */
	
	@Column(name ="GOODS_FVOL",nullable=true,length=32)
	public java.lang.String getGoodsFvol(){
		return this.goodsFvol;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  体积
	 */
	public void setGoodsFvol(java.lang.String goodsFvol){
		this.goodsFvol = goodsFvol;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重量
	 */
	
	@Column(name ="GOODS_WEIGHT",nullable=true,length=32)
	public java.lang.String getGoodsWeight(){
		return this.goodsWeight;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重量
	 */
	public void setGoodsWeight(java.lang.String goodsWeight){
		this.goodsWeight = goodsWeight;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计划库位
	 */
	
	@Column(name ="BIN_PLAN",nullable=true,length=128)
	public java.lang.String getBinPlan(){
		return this.binPlan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计划库位
	 */
	public void setBinPlan(java.lang.String binPlan){
		this.binPlan = binPlan;
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
	 *@return: java.lang.String  未清数量
	 */
	
	@Column(name ="GOODS_WQM_COUNT",nullable=true,length=32)
	public java.lang.String getGoodsWqmCount(){
		return this.goodsWqmCount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  未清数量
	 */
	public void setGoodsWqmCount(java.lang.String goodsWqmCount){
		this.goodsWqmCount = goodsWqmCount;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货登记数量
	 */
	
	@Column(name ="GOODS_QM_COUNT",nullable=true,length=32)
	public java.lang.String getGoodsQmCount(){
		return this.goodsQmCount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货登记数量
	 */
	public void setGoodsQmCount(java.lang.String goodsQmCount){
		this.goodsQmCount = goodsQmCount;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行项目状态
	 */
	
	@Column(name ="NOTICEI_STA",nullable=true,length=45)
	public java.lang.String getNoticeiSta(){
		return this.noticeiSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行项目状态
	 */
	public void setNoticeiSta(java.lang.String noticeiSta){
		this.noticeiSta = noticeiSta;
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
	 *@return: java.lang.String  基本单位数量
	 */
	
	@Column(name ="BASE_GOODSCOUNT",nullable=true,length=45)
	public java.lang.String getBaseGoodscount(){
		return this.baseGoodscount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位数量
	 */
	public void setBaseGoodscount(java.lang.String baseGoodscount){
		this.baseGoodscount = baseGoodscount;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位收货数量
	 */
	
	@Column(name ="BASE_QMCOUNT",nullable=true,length=45)
	public java.lang.String getBaseQmcount(){
		return this.baseQmcount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位收货数量
	 */
	public void setBaseQmcount(java.lang.String baseQmcount){
		this.baseQmcount = baseQmcount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=45)
	public java.lang.String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsName(java.lang.String goodsName){
		this.goodsName = goodsName;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  其他ID
	 */
	@Column(name ="OTHER_ID",nullable=true,length=45)
	public java.lang.String getOtherId(){
		return this.otherId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  其他ID
	 */
	public void setOtherId(java.lang.String otherId){
		this.otherId = otherId;
	}


	@Column(name ="BARCODE",nullable=true,length=32)
	public java.lang.String getBarCode(){
		return this.barCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品条码
	 */
	public void setBarCode(java.lang.String barCode){
		this.barCode = barCode;
	}
}
