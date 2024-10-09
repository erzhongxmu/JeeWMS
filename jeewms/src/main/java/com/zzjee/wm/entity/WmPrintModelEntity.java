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
 * @Description: 打印模板
 * @author onlineGenerator
 * @date 2021-08-02 14:51:38
 * @version V1.0
 *
 */
@Entity
@Table(name = "wm_print_model", schema = "")
@SuppressWarnings("serial")
public class WmPrintModelEntity implements java.io.Serializable {
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
	/**用户名*/
	@Excel(name="用户名")
	private String userName;
	/**打印类型*/
	@Excel(name="打印类型")
	private String printType;
	/**打印模板*/
	@Excel(name="打印模板")
	private String printModel;
	/**打印服务器地址*/
	@Excel(name="打印服务器地址")
	private String printServerAddress;
	/**打印文件类型*/
	@Excel(name="打印文件类型")
	private String printFileType;
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
	@Column(name ="CREATE_DATE",nullable=true,length=20)
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
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
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
	 *@return: java.lang.String  用户名
	 */
	@Column(name ="USER_NAME",nullable=true,length=50)
	public String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  打印类型
	 */
	@Column(name ="PRINT_TYPE",nullable=true,length=50)
	public String getPrintType(){
		return this.printType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  打印类型
	 */
	public void setPrintType(String printType){
		this.printType = printType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  打印模板
	 */
	@Column(name ="PRINT_MODEL",nullable=true,length=50)
	public String getPrintModel(){
		return this.printModel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  打印模板
	 */
	public void setPrintModel(String printModel){
		this.printModel = printModel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  打印服务器地址
	 */
	@Column(name ="PRINT_SERVER_ADDRESS",nullable=true,length=50)
	public String getPrintServerAddress(){
		return this.printServerAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  打印服务器地址
	 */
	public void setPrintServerAddress(String printServerAddress){
		this.printServerAddress = printServerAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  打印文件类型
	 */
	@Column(name ="PRINT_FILE_TYPE",nullable=true,length=50)
	public String getPrintFileType(){
		return this.printFileType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  打印文件类型
	 */
	public void setPrintFileType(String printFileType){
		this.printFileType = printFileType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用1
	 */
	@Column(name ="QUERY1",nullable=true,length=50)
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
	@Column(name ="QUERY2",nullable=true,length=50)
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
	@Column(name ="QUERY3",nullable=true,length=50)
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
	@Column(name ="QUERY4",nullable=true,length=50)
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
	@Column(name ="QUERY5",nullable=true,length=50)
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
}
