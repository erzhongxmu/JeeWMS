package com.zzjee.tms.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**
 * @Title: Entity
 * @Description: 车辆管理
 * @author onlineGenerator
 * @date 2018-01-29 21:57:07
 * @version V1.0
 *
 */
@Entity
@Table(name = "tms_md_cheliang", schema = "")
@SuppressWarnings("serial")
public class TmsMdCheliangEntity implements java.io.Serializable {
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
	/**流程状态*/
	private String bpmStatus;
	/**车牌号*/
    @Excel(name="车号",width=15)
	private String chepaihao;
	/**车型*/
    @Excel(name="车型",width=15)
	private String chexing;
	/**最大体积*/
    @Excel(name="最大体积",width=15)
	private String zuidatiji;
	/**载重*/
    @Excel(name="载重",width=15)
	private String zaizhong;
	/**载人数*/
    @Excel(name="载人数",width=15)
	private String zairen;
	/**准假驾照*/
    @Excel(name="准假驾照",width=15)
	private String jiazhao;
	/**是否可用*/
    @Excel(name="姓名",width=15)
	private String zhuangtai;
	/**备注*/
    @Excel(name="电话",width=15)
	private String beizhu;
	/**默认司机*/
    @Excel(name="默认司机",width=15)
	private String username;
	/**gps*/
    @Excel(name="车牌号",width=15)
	private String gpsid;
	@Excel(name="区域",width=15)
	private java.lang.String quyu;
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
	 *@return: java.lang.String  车牌号
	 */
	@Column(name ="CHEPAIHAO",nullable=true,length=32)
	public String getChepaihao(){
		return this.chepaihao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车牌号
	 */
	public void setChepaihao(String chepaihao){
		this.chepaihao = chepaihao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车型
	 */
	@Column(name ="CHEXING",nullable=true,length=32)
	public String getChexing(){
		return this.chexing;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车型
	 */
	public void setChexing(String chexing){
		this.chexing = chexing;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最大体积
	 */
	@Column(name ="ZUIDATIJI",nullable=true,length=32)
	public String getZuidatiji(){
		return this.zuidatiji;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最大体积
	 */
	public void setZuidatiji(String zuidatiji){
		this.zuidatiji = zuidatiji;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  载重
	 */
	@Column(name ="ZAIZHONG",nullable=true,length=32)
	public String getZaizhong(){
		return this.zaizhong;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  载重
	 */
	public void setZaizhong(String zaizhong){
		this.zaizhong = zaizhong;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  载人数
	 */
	@Column(name ="ZAIREN",nullable=true,length=32)
	public String getZairen(){
		return this.zairen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  载人数
	 */
	public void setZairen(String zairen){
		this.zairen = zairen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  准假驾照
	 */
	@Column(name ="JIAZHAO",nullable=true,length=32)
	public String getJiazhao(){
		return this.jiazhao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  准假驾照
	 */
	public void setJiazhao(String jiazhao){
		this.jiazhao = jiazhao;
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
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BEIZHU",nullable=true,length=32)
	public String getBeizhu(){
		return this.beizhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBeizhu(String beizhu){
		this.beizhu = beizhu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  默认司机
	 */
	@Column(name ="USERNAME",nullable=true,length=32)
	public String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  默认司机
	 */
	public void setUsername(String username){
		this.username = username;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  gps
	 */
	@Column(name ="GPSID",nullable=true,length=32)
	public String getGpsid(){
		return this.gpsid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  gps
	 */
	public void setGpsid(String gpsid){
		this.gpsid = gpsid;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区域
	 */

	@Column(name ="QUYU",nullable=true,length=32)
	public java.lang.String getQuyu(){
		return this.quyu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区域
	 */
	public void setQuyu(java.lang.String quyu){
		this.quyu = quyu;
	}
}
