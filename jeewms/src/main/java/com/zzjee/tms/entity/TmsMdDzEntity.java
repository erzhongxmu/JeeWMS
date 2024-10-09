package com.zzjee.tms.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 客户地址
 * @author onlineGenerator
 * @date 2018-01-29 21:57:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tms_md_dz", schema = "")
@SuppressWarnings("serial")
public class TmsMdDzEntity implements java.io.Serializable {
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
	/**用户*/
    @Excel(name="用户",width=15)
	private String username;
	/**联系人*/
    @Excel(name="联系人",width=15)
	private String lianxiren;
	/**联系电话*/
    @Excel(name="联系电话",width=15)
	private String dianhua;
	/**详细地址*/
    @Excel(name="详细地址",width=15)
	private String xiangxidizhi;
	/**省份*/
    @Excel(name="省份",width=15)
	private String shengfen;
	/**城市*/
    @Excel(name="城市",width=15)
	private String chengshi;
	/**区域*/
    @Excel(name="区域",width=15)
	private String quyu;
	/**默认地址*/
    @Excel(name="默认地址",width=15,dicCode="sf_yn")
	private String morendizhi;
	/**是否可用*/
    @Excel(name="是否可用",width=15,dicCode="sf_yn")
	private String zhuangtai;
	/**地址类型*/
    @Excel(name="地址类型",width=15)
	private String dizhileixing;
	
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
	 *@return: java.lang.String  用户
	 */
	@Column(name ="USERNAME",nullable=true,length=32)
	public String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户
	 */
	public void setUsername(String username){
		this.username = username;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="LIANXIREN",nullable=true,length=32)
	public String getLianxiren(){
		return this.lianxiren;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setLianxiren(String lianxiren){
		this.lianxiren = lianxiren;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="DIANHUA",nullable=true,length=32)
	public String getDianhua(){
		return this.dianhua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setDianhua(String dianhua){
		this.dianhua = dianhua;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  详细地址
	 */
	@Column(name ="XIANGXIDIZHI",nullable=true,length=32)
	public String getXiangxidizhi(){
		return this.xiangxidizhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  详细地址
	 */
	public void setXiangxidizhi(String xiangxidizhi){
		this.xiangxidizhi = xiangxidizhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省份
	 */
	@Column(name ="SHENGFEN",nullable=true,length=32)
	public String getShengfen(){
		return this.shengfen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省份
	 */
	public void setShengfen(String shengfen){
		this.shengfen = shengfen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市
	 */
	@Column(name ="CHENGSHI",nullable=true,length=32)
	public String getChengshi(){
		return this.chengshi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市
	 */
	public void setChengshi(String chengshi){
		this.chengshi = chengshi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区域
	 */
	@Column(name ="QUYU",nullable=true,length=32)
	public String getQuyu(){
		return this.quyu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区域
	 */
	public void setQuyu(String quyu){
		this.quyu = quyu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  默认地址
	 */
	@Column(name ="MORENDIZHI",nullable=true,length=32)
	public String getMorendizhi(){
		return this.morendizhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  默认地址
	 */
	public void setMorendizhi(String morendizhi){
		this.morendizhi = morendizhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否可用
	 */
	@Column(name ="ZHUANGTAI",nullable=true,length=32)
	public String getZhuangtai(){
		return this.zhuangtai;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否可用
	 */
	public void setZhuangtai(String zhuangtai){
		this.zhuangtai = zhuangtai;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址类型
	 */
	@Column(name ="DIZHILEIXING",nullable=true,length=32)
	public String getDizhileixing(){
		return this.dizhileixing;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址类型
	 */
	public void setDizhileixing(String dizhileixing){
		this.dizhileixing = dizhileixing;
	}
}
