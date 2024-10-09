package com.zzjee.plc.entity;

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
 * @Description: PLC指令
 * @author onlineGenerator
 * @date 2022-10-22 10:15:15
 * @version V1.0
 *
 */
@Entity
@Table(name = "wms_plc", schema = "")
@SuppressWarnings("serial")
public class WmsPlcEntity implements java.io.Serializable {
	/**id*/
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
	/**流程状态*/
	private String bpmStatus;
	/**PLCIP*/
	@Excel(name="PLCIP")
	private String plcIp;
	/**PLC端口*/
	@Excel(name="PLC端口")
	private String plcPort;
	/**PLC型号*/
	@Excel(name="PLC型号")
	private String plcType;
	/**指令备注*/
	@Excel(name="指令备注")
	private String comRemark;
	/**执行时间*/
	@Excel(name="执行时间")
	private String comTime;
	/**执行顺序*/
	@Excel(name="执行顺序")
	private String comSeq;
	/**指令集*/
	@Excel(name="指令集")
	private String comCons;
	/**备用1*/
	@Excel(name="备用1")
	private String remark1;
	/**指令编号*/
	@Excel(name="指令编号")
	private String comNo;
	/**单步参数1*/
	@Excel(name="单步参数1")
	private String query01;
	/**单步参数2*/
	@Excel(name="单步参数2")
	private String query02;
	/**单步时间*/
	@Excel(name="单步时间")
	private String setpTime;
	/**步数*/
	@Excel(name="步数")
	private String setpNum;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
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
	 *@return: java.lang.String  PLCIP
	 */
	@Column(name ="PLC_IP",nullable=true,length=32)
	public String getPlcIp(){
		return this.plcIp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  PLCIP
	 */
	public void setPlcIp(String plcIp){
		this.plcIp = plcIp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  PLC端口
	 */
	@Column(name ="PLC_PORT",nullable=true,length=32)
	public String getPlcPort(){
		return this.plcPort;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  PLC端口
	 */
	public void setPlcPort(String plcPort){
		this.plcPort = plcPort;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  PLC型号
	 */
	@Column(name ="PLC_TYPE",nullable=true,length=32)
	public String getPlcType(){
		return this.plcType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  PLC型号
	 */
	public void setPlcType(String plcType){
		this.plcType = plcType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  指令备注
	 */
	@Column(name ="COM_REMARK",nullable=true,length=32)
	public String getComRemark(){
		return this.comRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  指令备注
	 */
	public void setComRemark(String comRemark){
		this.comRemark = comRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  执行时间
	 */
	@Column(name ="COM_TIME",nullable=true,length=32)
	public String getComTime(){
		return this.comTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  执行时间
	 */
	public void setComTime(String comTime){
		this.comTime = comTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  执行顺序
	 */
	@Column(name ="COM_SEQ",nullable=true,length=32)
	public String getComSeq(){
		return this.comSeq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  执行顺序
	 */
	public void setComSeq(String comSeq){
		this.comSeq = comSeq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  指令集
	 */
	@Column(name ="COM_CONS",nullable=true)
	public String getComCons(){
		return this.comCons;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  指令集
	 */
	public void setComCons(String comCons){
		this.comCons = comCons;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备用1
	 */
	@Column(name ="REMARK1",nullable=true,length=32)
	public String getRemark1(){
		return this.remark1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备用1
	 */
	public void setRemark1(String remark1){
		this.remark1 = remark1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  指令编号
	 */
	@Column(name ="COM_NO",nullable=true,length=32)
	public String getComNo(){
		return this.comNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  指令编号
	 */
	public void setComNo(String comNo){
		this.comNo = comNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单步参数1
	 */
	@Column(name ="QUERY01",nullable=true,length=32)
	public String getQuery01(){
		return this.query01;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单步参数1
	 */
	public void setQuery01(String query01){
		this.query01 = query01;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单步参数2
	 */
	@Column(name ="QUERY02",nullable=true,length=32)
	public String getQuery02(){
		return this.query02;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单步参数2
	 */
	public void setQuery02(String query02){
		this.query02 = query02;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单步时间
	 */
	@Column(name ="SETP_TIME",nullable=true,length=32)
	public String getSetpTime(){
		return this.setpTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单步时间
	 */
	public void setSetpTime(String setpTime){
		this.setpTime = setpTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  步数
	 */
	@Column(name ="SETP_NUM",nullable=true,length=32)
	public String getSetpNum(){
		return this.setpNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  步数
	 */
	public void setSetpNum(String setpNum){
		this.setpNum = setpNum;
	}
}
