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
 * @Description: 出货通知项目
 * @author erzhongxmu
 * @date 2017-09-11 15:24:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_om_notice_i", schema = "")
@SuppressWarnings("serial")
public class WmOmNoticeIEntity implements java.io.Serializable {
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
	/**出货通知ID*/

	private java.lang.String omNoticeId;

	/**客户订单号*/
	@Excel(name="客户订单号")
	private java.lang.String imCusCode;
	@Excel(name="备注")
	private java.lang.String omBeizhu;

	/**出货商品*/
    @Excel(name="出货商品")
	private java.lang.String goodsId;

	@Excel(name="商品名称")
	private java.lang.String goodsName;
	//	@Excel(name="其他系统ID")
	private java.lang.String otherId;

	/**出货数量*/
    @Excel(name="出货数量")
	private java.lang.String goodsQua;
	/**出货单位*/
    @Excel(name="出货单位")
	private java.lang.String goodsUnit;
	
    @Excel(name="生产日期",format = "yyyy-MM-dd")
	private java.util.Date goodsProData;
	/**批次*/
   
	private java.lang.String goodsBatch;
	/**出货仓位*/

	private java.lang.String binOm;
	/**已出货数量*/

	private java.lang.String goodsQuaok;
	/**预约出货时间*/
  
	private java.lang.String delvData;
	/**客户*/

	private java.lang.String cusCode;
	/**客户名称*/

	private java.lang.String cusName;
	/**商品名称*/
 
	private java.lang.String goodsText;
	/**波次号*/
  
	private java.lang.String waveId;
	/**状态*/
 
	private java.lang.String omSta;
	/**基本单位*/

	private java.lang.String baseUnit;
	/**基本单位数量*/

	private java.lang.String baseGoodscount;
	/**下架计划生成*/

	private java.lang.String planSta;
	private java.lang.String binId;


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
	 *@return: java.lang.String  出货通知ID
	 */
	
	@Column(name ="OM_NOTICE_ID",nullable=true,length=36)
	public java.lang.String getOmNoticeId(){
		return this.omNoticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货通知ID
	 */
	public void setOmNoticeId(java.lang.String omNoticeId){
		this.omNoticeId = omNoticeId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货商品
	 */
	
	@Column(name ="GOODS_ID",nullable=true,length=36)
	public java.lang.String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货商品
	 */
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货数量
	 */
	
	@Column(name ="GOODS_QUA",nullable=true,length=32)
	public java.lang.String getGoodsQua(){
		return this.goodsQua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货数量
	 */
	public void setGoodsQua(java.lang.String goodsQua){
		this.goodsQua = goodsQua;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货单位
	 */
	
	@Column(name ="GOODS_UNIT",nullable=true,length=32)
	public java.lang.String getGoodsUnit(){
		return this.goodsUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货单位
	 */
	public void setGoodsUnit(java.lang.String goodsUnit){
		this.goodsUnit = goodsUnit;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	
	@Column(name ="GOODS_PRO_DATA",nullable=true)
	public java.util.Date getGoodsProData(){
		return this.goodsProData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setGoodsProData(java.util.Date goodsProData){
		this.goodsProData = goodsProData;
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
	 *@return: java.lang.String  出货仓位
	 */
	
	@Column(name ="BIN_OM",nullable=true,length=36)
	public java.lang.String getBinOm(){
		return this.binOm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货仓位
	 */
	public void setBinOm(java.lang.String binOm){
		this.binOm = binOm;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  已出货数量
	 */
	
	@Column(name ="GOODS_QUAOK",nullable=true,length=32)
	public java.lang.String getGoodsQuaok(){
		return this.goodsQuaok;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  已出货数量
	 */
	public void setGoodsQuaok(java.lang.String goodsQuaok){
		this.goodsQuaok = goodsQuaok;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预约出货时间
	 */
	
	@Column(name ="DELV_DATA",nullable=true,length=32)
	public java.lang.String getDelvData(){
		return this.delvData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预约出货时间
	 */
	public void setDelvData(java.lang.String delvData){
		this.delvData = delvData;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户
	 */
	
	@Column(name ="CUS_CODE",nullable=true,length=32)
	public java.lang.String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户
	 */
	public void setCusCode(java.lang.String cusCode){
		this.cusCode = cusCode;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户名称
	 */
	
	@Column(name ="CUS_NAME",nullable=true,length=64)
	public java.lang.String getCusName(){
		return this.cusName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户名称
	 */
	public void setCusName(java.lang.String cusName){
		this.cusName = cusName;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	
	@Column(name ="GOODS_TEXT",nullable=true,length=45)
	public java.lang.String getGoodsText(){
		return this.goodsText;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsText(java.lang.String goodsText){
		this.goodsText = goodsText;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  波次号
	 */
	
	@Column(name ="WAVE_ID",nullable=true,length=45)
	public java.lang.String getWaveId(){
		return this.waveId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  波次号
	 */
	public void setWaveId(java.lang.String waveId){
		this.waveId = waveId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	
	@Column(name ="OM_STA",nullable=true,length=45)
	public java.lang.String getOmSta(){
		return this.omSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setOmSta(java.lang.String omSta){
		this.omSta = omSta;
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
	 *@return: java.lang.String  下架计划生成
	 */
	
	@Column(name ="PLAN_STA",nullable=true,length=45)
	public java.lang.String getPlanSta(){
		return this.planSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下架计划生成
	 */
	public void setPlanSta(java.lang.String planSta){
		this.planSta = planSta;
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

	@Column(name ="BIN_ID",nullable=true,length=45)
	public String getBinId() {
		return binId;
	}

	public void setBinId(String binId) {
		this.binId = binId;
	}


	@Column(name ="OM_BEI_ZHU",nullable=true,length=32)
	public java.lang.String getOmBeizhu(){
		return this.omBeizhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setOmBeizhu(java.lang.String omBeizhu){
		this.omBeizhu = omBeizhu;
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



}
