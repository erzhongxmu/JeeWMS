
package com.zzjee.wm.page;

import com.zzjee.tms.entity.TmsYwDingdanEntity;
import com.zzjee.wm.entity.WmOmNoticeIEntity;
import com.zzjee.wm.entity.WmTmsNoticeIEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: 出货通知抬头
 * @author erzhongxmu
 * @date 2017-09-11 15:24:58
 * @version V1.0   
 *
 */
public class WmTmsNoticeHPage implements java.io.Serializable {
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
	private String orderTypeCode;

	private String wherecon;
	private String sysOrgCode;
	/**所属公司*/

	private String sysCompanyCode;
	/**客户*/
	private String cusCode;
	/**要求交货时间*/
	private Date delvData;
	/**收货人*/
	private String delvMember;
	/**收货人电话*/
	private String delvMobile;
	/**收货人地址*/
	private String delvAddr;
	/**承运人*/
	private String reMember;
	/**承运人电话*/
	private String reMobile;
	/**承运人车号*/
	private String reCarno;
	/**发货月台*/
	private String omPlatNo;
	/**备注*/
	private String omBeizhu;
	/**状态*/
	private String omSta;
	/**出货单号*/
	private String omNoticeId;
	/**附件*/

	private String fuJian;

	private String ocusCode;

	private String ocusName;
	private String imCusCode;
	private String printStatus;
	private String piClass;

	private String piMaster;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}
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

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getWherecon() {
		return wherecon;
	}

	public void setWherecon(String wherecon) {
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
	private List<WmTmsNoticeIEntity> wmOmNoticeIList = new ArrayList<WmTmsNoticeIEntity>();
		public List<WmTmsNoticeIEntity> getWmOmNoticeIList() {
		return wmOmNoticeIList;
		}
		public void setWmOmNoticeIList(List<WmTmsNoticeIEntity> wmOmNoticeIList) {
		this.wmOmNoticeIList = wmOmNoticeIList;
		}

	/**保存-出货通知项目*/
	private List<TmsYwDingdanEntity> wmOmtmsIList = new ArrayList<TmsYwDingdanEntity>();


	public List<TmsYwDingdanEntity> getWmOmtmsIList() {
		return wmOmtmsIList;
	}

	public void setWmOmtmsIList(List<TmsYwDingdanEntity> wmOmtmsIList) {
		this.wmOmtmsIList = wmOmtmsIList;
	}
}
