
package com.zzjee.wm.page;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**
 * @Title: Entity
 * @Description: 导入
 * @author erzhongxmu
 * @date 2017-09-11 15:08:34
 * @version V1.0
 *
 */
public class WmOmNoticeImpnewPage implements java.io.Serializable {
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

	private String sysCompanyCode;
	/**客户编码*/
    @Excel(name="货主编码")
	private String cusCode;
	/**预计到货时间*/
    @Excel(name="预计出货时间",format = "yyyy-MM-dd")
	private Date imData;
	/**客户订单号*/
    @Excel(name="客户订单号")
	private String imCusCode;

	@Excel(name="订单类型")
	private String orderTypeCode;

    @Excel(name="备注")
	private String imBeizhu;
	@Excel(name="收货人")
	private java.lang.String delvMember;
	/**收货人电话*/
	@Excel(name="收货人电话")
	private java.lang.String delvMobile;
	@Excel(name="收货地址")
	private String delvAddr;





	/**承运人*/
	@Excel(name="运输公司")
	private java.lang.String reMember;

	/**承运人车号*/
	@Excel(name="发货运单号")
	private java.lang.String reCarno;



	/**进货通知单号*/
//    @Excel(name="通知单号")
	private String noticeId;
	@Excel(name="合作伙伴编号")

	private java.lang.String ocusCode;
	@Excel(name="合作伙伴名称")
	private java.lang.String ocusName;

//	@Excel(name="其他系统ID")
	private String otherId;
	@Excel(name="商品")
	private String goodsId;

	@Excel(name="商品名称")
	private String goodsName;

	/**出货数量*/
	@Excel(name="数量")
	private String goodsQua;
	/**出货单位*/
	@Excel(name="单位")
	private String goodsUnit;


//	@Excel(name="储位")
	private String binOm;

//	@Excel(name="托盘")
	private String binId;


//	@Excel(name="生产日期")
	private String goodsProData;


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
	 *@return: java.lang.String  客户编码
	 */
	public String getCusCode(){
		return this.cusCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户编码
	 */
	public void setCusCode(String cusCode){
		this.cusCode = cusCode;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  预计到货时间
	 */
	public Date getImData(){
		return this.imData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  预计到货时间
	 */
	public void setImData(Date imData){
		this.imData = imData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户订单号
	 */
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  司机
	 */

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单类型
	 */
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
	 *@return: java.lang.String  月台
	 */
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	public String getImBeizhu(){
		return this.imBeizhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setImBeizhu(String imBeizhu){
		this.imBeizhu = imBeizhu;
	}



	public String getNoticeId(){
		return this.noticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  进货通知单号
	 */
	public void setNoticeId(String noticeId){
		this.noticeId = noticeId;
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

	public String getOtherId() {
		return otherId;
	}

	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsQua() {
		return goodsQua;
	}

	public void setGoodsQua(String goodsQua) {
		this.goodsQua = goodsQua;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}


	public String getDelvAddr() {
		return delvAddr;
	}

	public void setDelvAddr(String delvAddr) {
		this.delvAddr = delvAddr;
	}

	public String getBinOm() {
		return binOm;
	}

	public void setBinOm(String binOm) {
		this.binOm = binOm;
	}

	public String getBinId() {
		return binId;
	}

	public void setBinId(String binId) {
		this.binId = binId;
	}

	public String getGoodsProData() {
		return goodsProData;
	}

	public void setGoodsProData(String goodsProData) {
		this.goodsProData = goodsProData;
	}


	public String getDelvMember() {
		return delvMember;
	}

	public void setDelvMember(String delvMember) {
		this.delvMember = delvMember;
	}

	public String getDelvMobile() {
		return delvMobile;
	}

	public void setDelvMobile(String delvMobile) {
		this.delvMobile = delvMobile;
	}

	public String getReMember() {
		return reMember;
	}

	public void setReMember(String reMember) {
		this.reMember = reMember;
	}

	public String getReCarno() {
		return reCarno;
	}

	public void setReCarno(String reCarno) {
		this.reCarno = reCarno;
	}
}
