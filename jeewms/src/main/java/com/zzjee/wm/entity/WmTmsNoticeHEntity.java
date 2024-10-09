package com.zzjee.wm.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 出货通知抬头
 * @author erzhongxmu
 * @date 2017-09-11 15:24:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tms_om_notice_h", schema = "")
@SuppressWarnings("serial")
public class WmTmsNoticeHEntity implements java.io.Serializable {
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
	private String readonly;

	private String wherecon;

	private String sysOrgCode;
	/**所属公司*/
	private String orderTypeCode;

	private String sysCompanyCode;
	/**客户*/
    @Excel(name="客户")
	private String cusCode;
	/**客户订单号*/
	@Excel(name="客户订单号")
	private String imCusCode;
	/**要求交货时间*/
    @Excel(name="要求交货时间",format = "yyyy-MM-dd")
	private Date delvData;
	/**收货人*/
    @Excel(name="收货人")
	private String delvMember;
	/**收货人电话*/
    @Excel(name="收货人电话")
	private String delvMobile;
	/**收货人地址*/
    @Excel(name="收货人地址")
	private String delvAddr;
	/**承运人*/
    @Excel(name="承运人")
	private String reMember;
	/**承运人电话*/
    @Excel(name="承运人电话")
	private String reMobile;
	/**承运人车号*/
    @Excel(name="承运人车号")
	private String reCarno;
	/**发货月台*/
    @Excel(name="发货月台")
	private String omPlatNo;
	/**备注*/
    @Excel(name="备注")
	private String omBeizhu;
	/**状态*/
    @Excel(name="状态")
	private String omSta;
	/**出货单号*/
    @Excel(name="出货单号")
	private String omNoticeId;
	/**附件*/

	private String fuJian;
	@Excel(name="三方客户编号")

	private String ocusCode;
	@Excel(name="三方客户名称")
	private String ocusName;

	private String printStatus;
	private String piClass;

	private String piMaster;

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
	@Column(name ="ORDER_TYPE_CODE",nullable=true,length=32)
	public String getOrderTypeCode(){
		return this.orderTypeCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单类型
	 */
	public void setOrderTypeCode(String orderTypeCode){
		this.orderTypeCode = orderTypeCode;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  要求交货时间
	 */

	@Column(name ="DELV_DATA",nullable=true)
	public Date getDelvData(){
		return this.delvData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  要求交货时间
	 */
	public void setDelvData(Date delvData){
		this.delvData = delvData;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人
	 */

	@Column(name ="DELV_MEMBER",nullable=true,length=32)
	public String getDelvMember(){
		return this.delvMember;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人
	 */
	public void setDelvMember(String delvMember){
		this.delvMember = delvMember;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人电话
	 */

	@Column(name ="DELV_MOBILE",nullable=true,length=32)
	public String getDelvMobile(){
		return this.delvMobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人电话
	 */
	public void setDelvMobile(String delvMobile){
		this.delvMobile = delvMobile;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人地址
	 */

	@Column(name ="DELV_ADDR",nullable=true,length=32)
	public String getDelvAddr(){
		return this.delvAddr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人地址
	 */
	public void setDelvAddr(String delvAddr){
		this.delvAddr = delvAddr;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承运人
	 */

	@Column(name ="RE_MEMBER",nullable=true,length=32)
	public String getReMember(){
		return this.reMember;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承运人
	 */
	public void setReMember(String reMember){
		this.reMember = reMember;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承运人电话
	 */

	@Column(name ="RE_MOBILE",nullable=true,length=32)
	public String getReMobile(){
		return this.reMobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承运人电话
	 */
	public void setReMobile(String reMobile){
		this.reMobile = reMobile;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承运人车号
	 */

	@Column(name ="RE_CARNO",nullable=true,length=32)
	public String getReCarno(){
		return this.reCarno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承运人车号
	 */
	public void setReCarno(String reCarno){
		this.reCarno = reCarno;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发货月台
	 */

	@Column(name ="OM_PLAT_NO",nullable=true,length=32)
	public String getOmPlatNo(){
		return this.omPlatNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发货月台
	 */
	public void setOmPlatNo(String omPlatNo){
		this.omPlatNo = omPlatNo;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */

	@Column(name ="OM_BEIZHU",nullable=true,length=32)
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

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */

	@Column(name ="OM_STA",nullable=true,length=32)
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
	 *@return: java.lang.String  出货单号
	 */

	@Column(name ="OM_NOTICE_ID",nullable=true,length=32)
	public String getOmNoticeId(){
		return this.omNoticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出货单号
	 */
	public void setOmNoticeId(String omNoticeId){
		this.omNoticeId = omNoticeId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件
	 */

	@Column(name ="FU_JIAN",nullable=true,length=128)
	public String getFuJian(){
		return this.fuJian;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件
	 */
	public void setFuJian(String fuJian){
		this.fuJian = fuJian;
	}
	@Column(name ="READ_ONLY",nullable=true,length=32)
	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	@Column(name ="WHERE_CON",nullable=true,length=32)
	public String getWherecon() {
		return wherecon;
	}

	public void setWherecon(String wherecon) {
		this.wherecon = wherecon;
	}
	@Column(name ="OCUS_CODE",nullable=true,length=32)
	public String getOcusCode() {
		return ocusCode;
	}

	public void setOcusCode(String ocusCode) {
		this.ocusCode = ocusCode;
	}
	@Column(name ="OCUS_NAME",nullable=true,length=32)
	public String getOcusName() {
		return ocusName;
	}

	public void setOcusName(String ocusName) {
		this.ocusName = ocusName;
	}
	@Column(name ="IM_CUS_CODE",nullable=true,length=32)
	public String getImCusCode(){
		return this.imCusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String   打印状态
	 */
	public void setImCusCode(String imCusCode){
		this.imCusCode = imCusCode;
	}

	@Column(name ="PRINT_STATUS",nullable=true,length=32)
	public String getPrintStatus(){
		return this.printStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  打印状态
	 */
	public void setPrintStatus(String printStatus){
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
