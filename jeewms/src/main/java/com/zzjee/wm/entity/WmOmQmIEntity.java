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
 * @Description: 下架任务
 * @author erzhongxmu
 * @date 2017-09-11 14:57:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_om_qm_i", schema = "")
@SuppressWarnings("serial")
public class WmOmQmIEntity implements java.io.Serializable {
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
	@Excel(name="出货日期",format = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date updateDate;
	/**所属部门*/
	private java.lang.String sysOrgCode;
	/**所属公司*/
	private java.lang.String sysCompanyCode;
	/**到货通知单*/
	@Excel(name="到货通知单")
	private java.lang.String omNoticeId;
	/**到货通知行项目*/
	private java.lang.String iomNoticeItem;
	/**商品编码*/
	@Excel(name="商品编码")
	private java.lang.String goodsId;
	/**出货数量*/
	@Excel(name="出货数量")
	private java.lang.String omQuat;
	/**数量*/
	@Excel(name="数量")
	private java.lang.String qmOkQuat;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String itemText;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.lang.String proData;
	/**托盘*/
	@Excel(name="托盘")
	private java.lang.String tinId;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String goodsUnit;
	/**批次*/
	@Excel(name="批次")
	private java.lang.String goodsBatch;
	/**仓位*/
	@Excel(name="仓位")
	private java.lang.String binId;
	/**体积*/
	@Excel(name="体积")
	private java.lang.String tinTj;
	/**重量*/
	@Excel(name="重量")
	private java.lang.String tinZhl;
	/**是否已下架*/
	@Excel(name="是否已下架")
	private java.lang.String binSta;
	/**货主*/
	@Excel(name="货主")
	private java.lang.String cusCode;
	/**温度*/
	@Excel(name="温度")
	private java.lang.String recDeg;
	/**任务接收人*/
	@Excel(name="任务接收人")
	private java.lang.String assignTo;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String baseUnit;
	/**基本单位数量*/
	@Excel(name="基本单位数量")
	private java.lang.String baseGoodscount;
	/**客户名称*/
	@Excel(name="客户名称")
	private java.lang.String cusName;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String goodsName;
	@Excel(name="波次")
	private java.lang.String waveId;
	/**客户订单号*/
	@Excel(name="客户订单号")
	private java.lang.String imCusCode;
	@Excel(name="备注")
	private java.lang.String omBeizhu;
	@Excel(name="商品条码")
	private java.lang.String barCode;

	@Excel(name="保质期")
	private java.lang.String baoZhiq;
	private java.lang.String shpGuiGe;
	private java.lang.String pickNotice;
	@Excel(name="firstRq")
	private String firstRq;
	/**secondRq*/
	@Excel(name="secondRq")
	private String secondRq;
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
	 *@return: java.lang.String  到货通知单
	 */
	@Column(name ="OM_NOTICE_ID",nullable=true,length=36)
	public java.lang.String getOmNoticeId(){
		return this.omNoticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到货通知单
	 */
	public void setOmNoticeId(java.lang.String omNoticeId){
		this.omNoticeId = omNoticeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  到货通知行项目
	 */
	@Column(name ="IOM_NOTICE_ITEM",nullable=true,length=36)
	public java.lang.String getIomNoticeItem(){
		return this.iomNoticeItem;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到货通知行项目
	 */
	public void setIomNoticeItem(java.lang.String iomNoticeItem){
		this.iomNoticeItem = iomNoticeItem;
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
	public void setGoodsId(java.lang.String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货数量
	 */
	@Column(name ="OM_QUAT",nullable=true,length=32)
	public java.lang.String getOmQuat(){
		return this.omQuat;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货数量
	 */
	public void setOmQuat(java.lang.String omQuat){
		this.omQuat = omQuat;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量
	 */
	@Column(name ="QM_OK_QUAT",nullable=true,length=32)
	public java.lang.String getQmOkQuat(){
		return this.qmOkQuat;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setQmOkQuat(java.lang.String qmOkQuat){
		this.qmOkQuat = qmOkQuat;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="ITEM_TEXT",nullable=true,length=32)
	public java.lang.String getItemText(){
		return this.itemText;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setItemText(java.lang.String itemText){
		this.itemText = itemText;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生产日期
	 */
	@Column(name ="PRO_DATA",nullable=true,length=32)
	public java.lang.String getProData(){
		return this.proData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生产日期
	 */
	public void setProData(java.lang.String proData){
		this.proData = proData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  托盘
	 */
	@Column(name ="TIN_ID",nullable=true,length=32)
	public java.lang.String getTinId(){
		return this.tinId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  托盘
	 */
	public void setTinId(java.lang.String tinId){
		this.tinId = tinId;
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
	 *@return: java.lang.String  仓位
	 */
	@Column(name ="BIN_ID",nullable=true,length=32)
	public java.lang.String getBinId(){
		return this.binId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓位
	 */
	public void setBinId(java.lang.String binId){
		this.binId = binId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  体积
	 */
	@Column(name ="TIN_TJ",nullable=true,length=32)
	public java.lang.String getTinTj(){
		return this.tinTj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  体积
	 */
	public void setTinTj(java.lang.String tinTj){
		this.tinTj = tinTj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重量
	 */
	@Column(name ="TIN_ZHL",nullable=true,length=32)
	public java.lang.String getTinZhl(){
		return this.tinZhl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重量
	 */
	public void setTinZhl(java.lang.String tinZhl){
		this.tinZhl = tinZhl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否已下架
	 */
	@Column(name ="BIN_STA",nullable=true,length=32)
	public java.lang.String getBinSta(){
		return this.binSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否已下架
	 */
	public void setBinSta(java.lang.String binSta){
		this.binSta = binSta;
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
	 *@return: java.lang.String  温度
	 */
	@Column(name ="REC_DEG",nullable=true,length=32)
	public java.lang.String getRecDeg(){
		return this.recDeg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  温度
	 */
	public void setRecDeg(java.lang.String recDeg){
		this.recDeg = recDeg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务接收人
	 */
	@Column(name ="ASSIGN_TO",nullable=true,length=50)
	public java.lang.String getAssignTo(){
		return this.assignTo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务接收人
	 */
	public void setAssignTo(java.lang.String assignTo){
		this.assignTo = assignTo;
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
	 *@return: java.lang.String  客户名称
	 */
	@Column(name ="CUS_NAME",nullable=true,length=45)
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
	
	@Column(name ="WAVE_ID",nullable=true,length=45)
	public java.lang.String getWaveId(){
		return this.waveId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setWaveId(java.lang.String waveId){
		this.waveId = waveId;
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
	 *@param: java.lang.String  商品条码
	 */
	public void setImCusCode(java.lang.String imCusCode){
		this.imCusCode = imCusCode;
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

	@Column(name ="BAOZHIQI",nullable=true,length=32)
	public String getBaoZhiq() {
		return baoZhiq;
	}

	public void setBaoZhiq(String baoZhiq) {
		this.baoZhiq = baoZhiq;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品规格
	 */
	@Column(name ="SHP_GUI_GE",nullable=true,length=32)
	public java.lang.String getShpGuiGe(){
		return this.shpGuiGe;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  firstRq
	 */
	@Column(name ="FIRST_RQ",nullable=true,length=45)
	public String getFirstRq(){
		return this.firstRq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  firstRq
	 */
	public void setFirstRq(String firstRq){
		this.firstRq = firstRq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  secondRq
	 */
	@Column(name ="SECOND_RQ",nullable=true,length=45)
	public String getSecondRq(){
		return this.secondRq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  secondRq
	 */
	public void setSecondRq(String secondRq){
		this.secondRq = secondRq;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品规格
	 */
	public void setShpGuiGe(java.lang.String shpGuiGe){
		this.shpGuiGe = shpGuiGe;
	}
	@Column(name ="pick_notice",nullable=true,length=32)
	public String getPickNotice() {
		return pickNotice;
	}
	public void setPickNotice(String pickNotice) {
		this.pickNotice = pickNotice;
	}
}
