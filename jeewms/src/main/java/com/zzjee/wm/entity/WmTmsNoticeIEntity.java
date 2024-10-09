package com.zzjee.wm.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 出货通知项目
 * @author erzhongxmu
 * @date 2017-09-11 15:24:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tms_om_notice_i", schema = "")
@SuppressWarnings("serial")
public class WmTmsNoticeIEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/

	private String createName;
	/**创建人登录名称*/

	private String createBy;
	/**创建日期*/

	private Date createDate;
	/**更新人名称*/

	private String updateName;
	/**更新人登录名称*/

	private String updateBy;
	/**更新日期*/

	private Date updateDate;
	/**所属部门*/

	private String sysOrgCode;
	/**所属公司*/

	private String sysCompanyCode;
	/**出货通知ID*/

	private String omNoticeId;

	/**客户订单号*/
	@Excel(name="客户订单号")
	private String imCusCode;
	@Excel(name="备注")
	private String omBeizhu;

	/**出货商品*/
    @Excel(name="出货商品")
	private String goodsId;

	@Excel(name="商品名称")
	private String goodsName;
	//	@Excel(name="其他系统ID")
	private String otherId;

	/**出货数量*/
    @Excel(name="出货数量")
	private String goodsQua;
	/**出货单位*/
    @Excel(name="出货单位")
	private String goodsUnit;

    @Excel(name="生产日期",format = "yyyy-MM-dd")
	private Date goodsProData;
	/**批次*/

	private String goodsBatch;
	/**出货仓位*/

	private String binOm;
	/**已出货数量*/

	private String goodsQuaok;
	/**预约出货时间*/

	private String delvData;
	/**客户*/

	private String cusCode;
	/**客户名称*/

	private String cusName;
	/**商品名称*/

	private String goodsText;
	/**波次号*/

	private String waveId;
	/**状态*/

	private String omSta;
	/**基本单位*/

	private String baseUnit;
	/**基本单位数量*/

	private String baseGoodscount;
	/**下架计划生成*/

	private String planSta;
	private String binId;


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
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */

	@Column(name ="UPDATE_DATE",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */

	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公司
	 */

	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公司
	 */
	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货通知ID
	 */

	@Column(name ="OM_NOTICE_ID",nullable=true,length=36)
	public String getOmNoticeId(){
		return this.omNoticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货通知ID
	 */
	public void setOmNoticeId(String omNoticeId){
		this.omNoticeId = omNoticeId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货商品
	 */

	@Column(name ="GOODS_ID",nullable=true,length=36)
	public String getGoodsId(){
		return this.goodsId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货商品
	 */
	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货数量
	 */

	@Column(name ="GOODS_QUA",nullable=true,length=32)
	public String getGoodsQua(){
		return this.goodsQua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货数量
	 */
	public void setGoodsQua(String goodsQua){
		this.goodsQua = goodsQua;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货单位
	 */

	@Column(name ="GOODS_UNIT",nullable=true,length=32)
	public String getGoodsUnit(){
		return this.goodsUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货单位
	 */
	public void setGoodsUnit(String goodsUnit){
		this.goodsUnit = goodsUnit;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */

	@Column(name ="GOODS_PRO_DATA",nullable=true)
	public Date getGoodsProData(){
		return this.goodsProData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setGoodsProData(Date goodsProData){
		this.goodsProData = goodsProData;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批次
	 */

	@Column(name ="GOODS_BATCH",nullable=true,length=32)
	public String getGoodsBatch(){
		return this.goodsBatch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批次
	 */
	public void setGoodsBatch(String goodsBatch){
		this.goodsBatch = goodsBatch;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出货仓位
	 */

	@Column(name ="BIN_OM",nullable=true,length=36)
	public String getBinOm(){
		return this.binOm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货仓位
	 */
	public void setBinOm(String binOm){
		this.binOm = binOm;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  已出货数量
	 */

	@Column(name ="GOODS_QUAOK",nullable=true,length=32)
	public String getGoodsQuaok(){
		return this.goodsQuaok;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  已出货数量
	 */
	public void setGoodsQuaok(String goodsQuaok){
		this.goodsQuaok = goodsQuaok;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预约出货时间
	 */

	@Column(name ="DELV_DATA",nullable=true,length=32)
	public String getDelvData(){
		return this.delvData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预约出货时间
	 */
	public void setDelvData(String delvData){
		this.delvData = delvData;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户
	 */

	@Column(name ="CUS_CODE",nullable=true,length=32)
	public String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户
	 */
	public void setCusCode(String cusCode){
		this.cusCode = cusCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户名称
	 */

	@Column(name ="CUS_NAME",nullable=true,length=64)
	public String getCusName(){
		return this.cusName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户名称
	 */
	public void setCusName(String cusName){
		this.cusName = cusName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */

	@Column(name ="GOODS_TEXT",nullable=true,length=45)
	public String getGoodsText(){
		return this.goodsText;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsText(String goodsText){
		this.goodsText = goodsText;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  波次号
	 */

	@Column(name ="WAVE_ID",nullable=true,length=45)
	public String getWaveId(){
		return this.waveId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  波次号
	 */
	public void setWaveId(String waveId){
		this.waveId = waveId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */

	@Column(name ="OM_STA",nullable=true,length=45)
	public String getOmSta(){
		return this.omSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setOmSta(String omSta){
		this.omSta = omSta;
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
	 *@return: java.lang.String  下架计划生成
	 */

	@Column(name ="PLAN_STA",nullable=true,length=45)
	public String getPlanSta(){
		return this.planSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下架计划生成
	 */
	public void setPlanSta(String planSta){
		this.planSta = planSta;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=45)
	public String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  其他ID
	 */
	@Column(name ="OTHER_ID",nullable=true,length=45)
	public String getOtherId(){
		return this.otherId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  其他ID
	 */
	public void setOtherId(String otherId){
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
	public String getOmBeizhu(){
		return this.omBeizhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setOmBeizhu(String omBeizhu){
		this.omBeizhu = omBeizhu;
	}


	@Column(name ="IM_CUS_CODE",nullable=true,length=32)
	public String getImCusCode(){
		return this.imCusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户订单号
	 */
	public void setImCusCode(String imCusCode){
		this.imCusCode = imCusCode;
	}



}
