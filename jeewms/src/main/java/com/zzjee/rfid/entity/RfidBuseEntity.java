package com.zzjee.rfid.entity;

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
 * @Description: RFID表
 * @author onlineGenerator
 * @date 2020-06-19 06:54:21
 * @version V1.0
 *
 */
@Entity
@Table(name = "rfid_buse", schema = "")
@SuppressWarnings("serial")
public class RfidBuseEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
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
	/**流程状态*/
	private String bpmStatus;
	/**类型*/
	@Excel(name="类型")
	private String rfidType;
	/**业务编号*/
	@Excel(name="业务编号")
	private String rfidBuseno;
	/**业务内容*/
	@Excel(name="业务内容")
	private String rfidBusecont;
	/**RFID1*/
	@Excel(name="RFID1")
	private String rfidId1;
	/**RFID2*/
	@Excel(name="RFID2")
	private String rfidId2;
	/**RFID3*/
	@Excel(name="RFID3")
	private String rfidId3;

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
	 *@return: java.lang.String  流程状态
	 */
	@Column(name ="BPM_STATUS",nullable=true,length=32)
	public String getBpmStatus(){
		return this.bpmStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程状态
	 */
	public void setBpmStatus(String bpmStatus){
		this.bpmStatus = bpmStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */
	@Column(name ="RFID_TYPE",nullable=true,length=128)
	public String getRfidType(){
		return this.rfidType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setRfidType(String rfidType){
		this.rfidType = rfidType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务编号
	 */
	@Column(name ="RFID_BUSENO",nullable=true,length=128)
	public String getRfidBuseno(){
		return this.rfidBuseno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务编号
	 */
	public void setRfidBuseno(String rfidBuseno){
		this.rfidBuseno = rfidBuseno;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务内容
	 */
	@Column(name ="RFID_BUSECONT",nullable=true,length=128)
	public String getRfidBusecont(){
		return this.rfidBusecont;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务内容
	 */
	public void setRfidBusecont(String rfidBusecont){
		this.rfidBusecont = rfidBusecont;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  RFID1
	 */
	@Column(name ="RFID_ID1",nullable=true,length=128)
	public String getRfidId1(){
		return this.rfidId1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  RFID1
	 */
	public void setRfidId1(String rfidId1){
		this.rfidId1 = rfidId1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  RFID2
	 */
	@Column(name ="RFID_ID2",nullable=true,length=128)
	public String getRfidId2(){
		return this.rfidId2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  RFID2
	 */
	public void setRfidId2(String rfidId2){
		this.rfidId2 = rfidId2;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  RFID3
	 */
	@Column(name ="RFID_ID3",nullable=true,length=128)
	public String getRfidId3(){
		return this.rfidId3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  RFID3
	 */
	public void setRfidId3(String rfidId3){
		this.rfidId3 = rfidId3;
	}
}
