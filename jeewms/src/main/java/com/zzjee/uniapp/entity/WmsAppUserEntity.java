package com.zzjee.uniapp.entity;

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
 * @Description: APP角色分配
 * @author onlineGenerator
 * @date 2022-06-13 08:41:32
 * @version V1.0
 *
 */
@Entity
@Table(name = "wms_app_user", schema = "")
@SuppressWarnings("serial")
public class WmsAppUserEntity implements java.io.Serializable {
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
	/**用户编号*/
	@Excel(name="用户编号")
	private String appuserCode;
	/**用户名称*/
	@Excel(name="用户名称")
	private String appuserName;
	/**角色id*/
	@Excel(name="角色id")
	private String approleId;
	/**角色编号*/
	@Excel(name="角色编号")
	private String approleCode;
	/**角色名称*/
	@Excel(name="角色名称")
	private String approleName;
	/**备用1*/
	private String query1;
	/**备用2*/
	private String query2;
	/**备用3*/
	private String query3;
	/**备用4*/
	private String query4;
	/**备用5*/
	private String query5;
	/**备用6*/
	private String query6;

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
	 *@return: java.lang.String  用户编号
	 */
	@Column(name ="APPUSER_CODE",nullable=true,length=64)
	public String getAppuserCode(){
		return this.appuserCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户编号
	 */
	public void setAppuserCode(String appuserCode){
		this.appuserCode = appuserCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户名称
	 */
	@Column(name ="APPUSER_NAME",nullable=true,length=64)
	public String getAppuserName(){
		return this.appuserName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名称
	 */
	public void setAppuserName(String appuserName){
		this.appuserName = appuserName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  角色id
	 */
	@Column(name ="APPROLE_ID",nullable=true,length=64)
	public String getApproleId(){
		return this.approleId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  角色id
	 */
	public void setApproleId(String approleId){
		this.approleId = approleId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  角色编号
	 */
	@Column(name ="APPROLE_CODE",nullable=true,length=64)
	public String getApproleCode(){
		return this.approleCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  角色编号
	 */
	public void setApproleCode(String approleCode){
		this.approleCode = approleCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  角色名称
	 */
	@Column(name ="APPROLE_NAME",nullable=true,length=64)
	public String getApproleName(){
		return this.approleName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  角色名称
	 */
	public void setApproleName(String approleName){
		this.approleName = approleName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用1
	 */
	@Column(name ="QUERY1",nullable=true,length=64)
	public String getQuery1(){
		return this.query1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用1
	 */
	public void setQuery1(String query1){
		this.query1 = query1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用2
	 */
	@Column(name ="QUERY2",nullable=true,length=64)
	public String getQuery2(){
		return this.query2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用2
	 */
	public void setQuery2(String query2){
		this.query2 = query2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用3
	 */
	@Column(name ="QUERY3",nullable=true,length=64)
	public String getQuery3(){
		return this.query3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用3
	 */
	public void setQuery3(String query3){
		this.query3 = query3;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用4
	 */
	@Column(name ="QUERY4",nullable=true,length=64)
	public String getQuery4(){
		return this.query4;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用4
	 */
	public void setQuery4(String query4){
		this.query4 = query4;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用5
	 */
	@Column(name ="QUERY5",nullable=true,length=64)
	public String getQuery5(){
		return this.query5;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用5
	 */
	public void setQuery5(String query5){
		this.query5 = query5;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用6
	 */
	@Column(name ="QUERY6",nullable=true,length=64)
	public String getQuery6(){
		return this.query6;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用6
	 */
	public void setQuery6(String query6){
		this.query6 = query6;
	}
}
