
package com.zzjee.wm.page;
import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.entity.WmOmNoticeIEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

/**   
 * @Title: Entity
 * @Description: 出货通知抬头
 * @author erzhongxmu
 * @date 2017-09-11 15:24:58
 * @version V1.0   
 *
 */
public class WmOmNoticeHPage implements java.io.Serializable {
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
	private java.lang.String orderTypeCode;

	private java.lang.String wherecon;
	private java.lang.String sysOrgCode;
	/**所属公司*/
 
	private java.lang.String sysCompanyCode;
	/**客户*/
    @Excel(name="客户")
	private java.lang.String cusCode;
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
	@Excel(name="客户订单号")
	private java.lang.String imCusCode;
	private java.lang.String printStatus;
	private java.lang.String piClass;

	private java.lang.String piMaster;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	public java.lang.String getSysOrgCode(){
		return this.sysOrgCode;
	}
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

	public java.lang.String getReadonly() {
		return readonly;
	}

	public void setReadonly(java.lang.String readonly) {
		this.readonly = readonly;
	}

	public java.lang.String getWherecon() {
		return wherecon;
	}

	public void setWherecon(java.lang.String wherecon) {
		this.wherecon = wherecon;
	}

	public String getOcusCode() {
		return ocusCode;
	}

	public void setOcusCode(String ocusCode) {
		this.ocusCode = ocusCode;
	}

	public String getOcusName() {
		return ocusName;
	}

	public void setOcusName(String ocusName) {
		this.ocusName = ocusName;
	}

	public String getImCusCode() {
		return imCusCode;
	}

	public void setImCusCode(String imCusCode) {
		this.imCusCode = imCusCode;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getPiClass() {
		return piClass;
	}

	public void setPiClass(String piClass) {
		this.piClass = piClass;
	}

	public String getPiMaster() {
		return piMaster;
	}

	public void setPiMaster(String piMaster) {
		this.piMaster = piMaster;
	}

	/**保存-出货通知项目*/
    @ExcelCollection(name="出货通知项目")
	private List<WmOmNoticeIEntity> wmOmNoticeIList = new ArrayList<WmOmNoticeIEntity>();
		public List<WmOmNoticeIEntity> getWmOmNoticeIList() {
		return wmOmNoticeIList;
		}
		public void setWmOmNoticeIList(List<WmOmNoticeIEntity> wmOmNoticeIList) {
		this.wmOmNoticeIList = wmOmNoticeIList;
		}
}
