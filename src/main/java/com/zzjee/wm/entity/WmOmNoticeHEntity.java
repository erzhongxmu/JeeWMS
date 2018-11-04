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
 * @Description: 出货通知抬头
 * @author erzhongxmu
 * @date 2017-09-11 15:24:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "wm_om_notice_h", schema = "")
@SuppressWarnings("serial")
public class WmOmNoticeHEntity implements java.io.Serializable {
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
	private java.lang.String readonly;
	
	private java.lang.String wherecon;
	
	private java.lang.String sysOrgCode;
	/**所属公司*/
	private java.lang.String orderTypeCode;

	private java.lang.String sysCompanyCode;
	/**客户*/
    @Excel(name="客户")
	private java.lang.String cusCode;
	/**客户订单号*/
	@Excel(name="客户订单号")
	private java.lang.String imCusCode;
	/**要求交货时间*/
    @Excel(name="要求交货时间",format = "yyyy-MM-dd")
	private java.util.Date delvData;
	/**收货人*/
    @Excel(name="收货人")
	private java.lang.String delvMember;
	/**收货人电话*/
    @Excel(name="收货人电话")
	private java.lang.String delvMobile;
	/**收货人地址*/
    @Excel(name="收货人地址")
	private java.lang.String delvAddr;
	/**承运人*/
    @Excel(name="承运人")
	private java.lang.String reMember;
	/**承运人电话*/
    @Excel(name="承运人电话")
	private java.lang.String reMobile;
	/**承运人车号*/
    @Excel(name="承运人车号")
	private java.lang.String reCarno;
	/**发货月台*/
    @Excel(name="发货月台")
	private java.lang.String omPlatNo;
	/**备注*/
    @Excel(name="备注")
	private java.lang.String omBeizhu;
	/**状态*/
    @Excel(name="状态")
	private java.lang.String omSta;
	/**出货单号*/
    @Excel(name="出货单号")
	private java.lang.String omNoticeId;
	/**附件*/

	private java.lang.String fuJian;
	@Excel(name="三方客户编号")

	private java.lang.String ocusCode;
	@Excel(name="三方客户名称")
	private java.lang.String ocusName;

	private java.lang.String printStatus;
	private java.lang.String piClass;

	private java.lang.String piMaster;

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
	@Column(name ="ORDER_TYPE_CODE",nullable=true,length=32)
	public java.lang.String getOrderTypeCode(){
		return this.orderTypeCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单类型
	 */
	public void setOrderTypeCode(java.lang.String orderTypeCode){
		this.orderTypeCode = orderTypeCode;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  要求交货时间
	 */
	
	@Column(name ="DELV_DATA",nullable=true)
	public java.util.Date getDelvData(){
		return this.delvData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  要求交货时间
	 */
	public void setDelvData(java.util.Date delvData){
		this.delvData = delvData;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人
	 */
	
	@Column(name ="DELV_MEMBER",nullable=true,length=32)
	public java.lang.String getDelvMember(){
		return this.delvMember;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人
	 */
	public void setDelvMember(java.lang.String delvMember){
		this.delvMember = delvMember;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人电话
	 */
	
	@Column(name ="DELV_MOBILE",nullable=true,length=32)
	public java.lang.String getDelvMobile(){
		return this.delvMobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人电话
	 */
	public void setDelvMobile(java.lang.String delvMobile){
		this.delvMobile = delvMobile;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人地址
	 */
	
	@Column(name ="DELV_ADDR",nullable=true,length=32)
	public java.lang.String getDelvAddr(){
		return this.delvAddr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人地址
	 */
	public void setDelvAddr(java.lang.String delvAddr){
		this.delvAddr = delvAddr;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承运人
	 */
	
	@Column(name ="RE_MEMBER",nullable=true,length=32)
	public java.lang.String getReMember(){
		return this.reMember;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承运人
	 */
	public void setReMember(java.lang.String reMember){
		this.reMember = reMember;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承运人电话
	 */
	
	@Column(name ="RE_MOBILE",nullable=true,length=32)
	public java.lang.String getReMobile(){
		return this.reMobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承运人电话
	 */
	public void setReMobile(java.lang.String reMobile){
		this.reMobile = reMobile;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承运人车号
	 */
	
	@Column(name ="RE_CARNO",nullable=true,length=32)
	public java.lang.String getReCarno(){
		return this.reCarno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承运人车号
	 */
	public void setReCarno(java.lang.String reCarno){
		this.reCarno = reCarno;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发货月台
	 */
	
	@Column(name ="OM_PLAT_NO",nullable=true,length=32)
	public java.lang.String getOmPlatNo(){
		return this.omPlatNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发货月台
	 */
	public void setOmPlatNo(java.lang.String omPlatNo){
		this.omPlatNo = omPlatNo;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	
	@Column(name ="OM_BEIZHU",nullable=true,length=32)
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
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	
	@Column(name ="OM_STA",nullable=true,length=32)
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
	 *@return: java.lang.String  出货单号
	 */
	
	@Column(name ="OM_NOTICE_ID",nullable=true,length=32)
	public java.lang.String getOmNoticeId(){
		return this.omNoticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货单号
	 */
	public void setOmNoticeId(java.lang.String omNoticeId){
		this.omNoticeId = omNoticeId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件
	 */
	
	@Column(name ="FU_JIAN",nullable=true,length=128)
	public java.lang.String getFuJian(){
		return this.fuJian;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件
	 */
	public void setFuJian(java.lang.String fuJian){
		this.fuJian = fuJian;
	}
	@Column(name ="READ_ONLY",nullable=true,length=32)
	public java.lang.String getReadonly() {
		return readonly;
	}

	public void setReadonly(java.lang.String readonly) {
		this.readonly = readonly;
	}
	@Column(name ="WHERE_CON",nullable=true,length=32)
	public java.lang.String getWherecon() {
		return wherecon;
	}

	public void setWherecon(java.lang.String wherecon) {
		this.wherecon = wherecon;
	}
	@Column(name ="OCUS_CODE",nullable=true,length=32)
	public java.lang.String getOcusCode() {
		return ocusCode;
	}

	public void setOcusCode(java.lang.String ocusCode) {
		this.ocusCode = ocusCode;
	}
	@Column(name ="OCUS_NAME",nullable=true,length=32)
	public java.lang.String getOcusName() {
		return ocusName;
	}

	public void setOcusName(java.lang.String ocusName) {
		this.ocusName = ocusName;
	}
	@Column(name ="IM_CUS_CODE",nullable=true,length=32)
	public java.lang.String getImCusCode(){
		return this.imCusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String   打印状态
	 */
	public void setImCusCode(java.lang.String imCusCode){
		this.imCusCode = imCusCode;
	}

	@Column(name ="PRINT_STATUS",nullable=true,length=32)
	public java.lang.String getPrintStatus(){
		return this.printStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  打印状态
	 */
	public void setPrintStatus(java.lang.String printStatus){
		this.printStatus = printStatus;
	}

	@Column(name ="PI_CLASS",nullable=true,length=32)
	public String getPiClass() {
		return piClass;
	}

	public void setPiClass(String piClass) {
		this.piClass = piClass;
	}
	@Column(name ="PI_MASTER",nullable=true,length=32)
	public String getPiMaster() {
		return piMaster;
	}

	public void setPiMaster(String piMaster) {
		this.piMaster = piMaster;
	}
}
