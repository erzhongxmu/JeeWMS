
package com.zzjee.wm.page;
import com.zzjee.wm.entity.WmImNoticeHEntity;
import com.zzjee.wm.entity.WmImNoticeIEntity;

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
 * @Description: 预约进货抬头
 * @author erzhongxmu
 * @date 2017-09-11 15:08:34
 * @version V1.0   
 *
 */
public class WmImNoticeHPage implements java.io.Serializable {
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
  
	private java.lang.String sysCompanyCode;
	/**客户编码*/
    @Excel(name="客户编码")
	private java.lang.String cusCode;
	/**预计到货时间*/
    @Excel(name="预计到货时间",format = "yyyy-MM-dd")
	private java.util.Date imData;
	/**客户订单号*/
    @Excel(name="客户订单号")
	private java.lang.String imCusCode;
	/**司机*/
    @Excel(name="司机")
	private java.lang.String imCarDri;
	/**司机电话*/
    @Excel(name="司机电话")
	private java.lang.String imCarMobile;
	/**车号*/
    @Excel(name="车号")
	private java.lang.String imCarNo;
	/**订单类型*/

	private java.lang.String orderTypeCode;
	/**月台*/
    @Excel(name="月台")
	private java.lang.String platformCode;
	/**备注*/
    @Excel(name="备注")
	private java.lang.String imBeizhu;
	/**单据状态*/
    @Excel(name="单据状态")
	private java.lang.String imSta;
	/**进货通知单号*/
    @Excel(name="进货通知单号")
	private java.lang.String noticeId;
	/**附件*/

	private java.lang.String fuJian;
	private java.lang.String supCode;
	/**附件*/

	private java.lang.String supName;
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
	 *@return: java.lang.String  客户编码
	 */
	public java.lang.String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户编码
	 */
	public void setCusCode(java.lang.String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  预计到货时间
	 */
	public java.util.Date getImData(){
		return this.imData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  预计到货时间
	 */
	public void setImData(java.util.Date imData){
		this.imData = imData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户订单号
	 */
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  司机
	 */
	public java.lang.String getImCarDri(){
		return this.imCarDri;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  司机
	 */
	public void setImCarDri(java.lang.String imCarDri){
		this.imCarDri = imCarDri;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  司机电话
	 */
	public java.lang.String getImCarMobile(){
		return this.imCarMobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  司机电话
	 */
	public void setImCarMobile(java.lang.String imCarMobile){
		this.imCarMobile = imCarMobile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车号
	 */
	public java.lang.String getImCarNo(){
		return this.imCarNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车号
	 */
	public void setImCarNo(java.lang.String imCarNo){
		this.imCarNo = imCarNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单类型
	 */
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
	 *@return: java.lang.String  月台
	 */
	public java.lang.String getPlatformCode(){
		return this.platformCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  月台
	 */
	public void setPlatformCode(java.lang.String platformCode){
		this.platformCode = platformCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	public java.lang.String getImBeizhu(){
		return this.imBeizhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setImBeizhu(java.lang.String imBeizhu){
		this.imBeizhu = imBeizhu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据状态
	 */
	public java.lang.String getImSta(){
		return this.imSta;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据状态
	 */
	public void setImSta(java.lang.String imSta){
		this.imSta = imSta;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  进货通知单号
	 */
	public java.lang.String getNoticeId(){
		return this.noticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  进货通知单号
	 */
	public void setNoticeId(java.lang.String noticeId){
		this.noticeId = noticeId;
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

	public String getSupCode() {
		return supCode;
	}

	public void setSupCode(String supCode) {
		this.supCode = supCode;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
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

	/**保存-预约通知项目*/
    @ExcelCollection(name="预约通知项目")
	private List<WmImNoticeIEntity> wmImNoticeIList = new ArrayList<WmImNoticeIEntity>();
		public List<WmImNoticeIEntity> getWmImNoticeIList() {
		return wmImNoticeIList;
		}
		public void setWmImNoticeIList(List<WmImNoticeIEntity> wmImNoticeIList) {
		this.wmImNoticeIList = wmImNoticeIList;
		}
}
