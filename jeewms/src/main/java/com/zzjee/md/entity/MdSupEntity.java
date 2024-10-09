package com.zzjee.md.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 供应商
 * @author erzhongxmu
 * @date 2017-08-15 23:16:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "md_sup", schema = "")
@SuppressWarnings("serial")
public class MdSupEntity implements java.io.Serializable {
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
	private java.lang.String sysOrgCode;
	/**所属公司*/
	private java.lang.String sysCompanyCode;
	/**中文全称*/
	@Excel(name="中文全称")
	private java.lang.String zhongWenQch;
	/**助记码*/
	private java.lang.String zhuJiMa;
	/**供应商简称*/
	private java.lang.String gysJianCheng;
	/**供应商编码*/
	@Excel(name="供应商编码")
	private java.lang.String gysBianMa;
	/**供应商英文名称*/
	private java.lang.String gysYingWen;
	/**曾用企业代码*/
	private java.lang.String zengYongQi;
	/**曾用企业名称*/
	private java.lang.String zengYongQiYe;
	/**停用*/
	private java.lang.String gysZhuangTai;
	/**企业属性*/
	@Excel(name="企业属性")
	private java.lang.String xingYeFenLei;
	/**供应商等级*/
	private java.lang.String gysDengJi;
	/**所属行业*/
	private java.lang.String suoShuXingYe;
	/**首签日期*/
	private java.util.Date shouQianRiQi;
	/**终止合作时间*/
	private java.util.Date zhongZhiHeShiJian;
	/**申请时间*/
	private java.util.Date shenQingShiJian;
	/**供应商属性*/
	private java.lang.String gysShuXing;
	/**归属组织代码*/
	private java.lang.String guiShuZuZh;
	/**归属省份代码*/
	private java.lang.String guiShuSheng;
	/**归属市代码*/
	private java.lang.String guiShuShiDai;
	/**归属县区代码*/
	private java.lang.String guiShu;
	/**地址*/
	@Excel(name="地址")
	private java.lang.String diZhi;
	/**邮政编码*/
	private java.lang.String youZhengBianMa;
	/**主联系人*/
	@Excel(name="主联系人")
	private java.lang.String zhuLianXiRen;
	/**电话*/
	@Excel(name="电话")
	private java.lang.String dianHua;
	/**手机*/
	@Excel(name="手机")
	private java.lang.String shouJi;
	/**传真*/
	private java.lang.String chuanZhen;
	/**Email地址*/
	@Excel(name="Email地址")
	private java.lang.String emaildiZhi;
	/**网页地址*/
	private java.lang.String wangYeDiZhi;
	/**法人代表*/
	private java.lang.String faRenDaiBiao;
	/**法人身份证号*/
	private java.lang.String faRenShenFen;
	/**注册资金万元*/
	private java.lang.String zhuCeZiJin;
	/**币别*/
	private java.lang.String biBie;
	/**营业执照号*/
	private java.lang.String yingYeZhiZhao;
	/**税务登记证号*/
	private java.lang.String shuiWuDeng;
	/**组织机构代码证*/
	private java.lang.String zuZhiJiGou;
	/**道路运输经营许可证*/
	private java.lang.String daoLuYunShu;
	/**主营业务*/
	private java.lang.String zhuYingYeWu;
	/**合作意向*/
	private java.lang.String heYiXiang;
	/**批准机关*/
	private java.lang.String piZhunJiGuan;
	/**批准文号*/
	private java.lang.String piZhunWenHao;
	/**注册日期*/
	private java.util.Date zhuCeRiQi;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String beiZhu;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	@Column(name ="CREATE_NAME",nullable=true,length=50)
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
	@Column(name ="CREATE_BY",nullable=true,length=50)
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
	@Column(name ="CREATE_DATE",nullable=true)
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
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
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
	@Column(name ="UPDATE_BY",nullable=true,length=50)
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
	@Column(name ="UPDATE_DATE",nullable=true)
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
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
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
	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
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
	 *@return: java.lang.String  中文全称
	 */
	@Column(name ="ZHONG_WEN_QCH",nullable=true,length=100)
	public java.lang.String getZhongWenQch(){
		return this.zhongWenQch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文全称
	 */
	public void setZhongWenQch(java.lang.String zhongWenQch){
		this.zhongWenQch = zhongWenQch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  助记码
	 */
	@Column(name ="ZHU_JI_MA",nullable=true,length=32)
	public java.lang.String getZhuJiMa(){
		return this.zhuJiMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  助记码
	 */
	public void setZhuJiMa(java.lang.String zhuJiMa){
		this.zhuJiMa = zhuJiMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商简称
	 */
	@Column(name ="GYS_JIAN_CHENG",nullable=true,length=32)
	public java.lang.String getGysJianCheng(){
		return this.gysJianCheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商简称
	 */
	public void setGysJianCheng(java.lang.String gysJianCheng){
		this.gysJianCheng = gysJianCheng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商编码
	 */
	@Column(name ="GYS_BIAN_MA",nullable=true,length=32)
	public java.lang.String getGysBianMa(){
		return this.gysBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商编码
	 */
	public void setGysBianMa(java.lang.String gysBianMa){
		this.gysBianMa = gysBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商英文名称
	 */
	@Column(name ="GYS_YING_WEN",nullable=true,length=100)
	public java.lang.String getGysYingWen(){
		return this.gysYingWen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商英文名称
	 */
	public void setGysYingWen(java.lang.String gysYingWen){
		this.gysYingWen = gysYingWen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  曾用企业代码
	 */
	@Column(name ="ZENG_YONG_QI",nullable=true,length=32)
	public java.lang.String getZengYongQi(){
		return this.zengYongQi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  曾用企业代码
	 */
	public void setZengYongQi(java.lang.String zengYongQi){
		this.zengYongQi = zengYongQi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  曾用企业名称
	 */
	@Column(name ="ZENG_YONG_QI_YE",nullable=true,length=100)
	public java.lang.String getZengYongQiYe(){
		return this.zengYongQiYe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  曾用企业名称
	 */
	public void setZengYongQiYe(java.lang.String zengYongQiYe){
		this.zengYongQiYe = zengYongQiYe;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  停用
	 */
	@Column(name ="GYS_ZHUANG_TAI",nullable=true,length=32)
	public java.lang.String getGysZhuangTai(){
		return this.gysZhuangTai;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  停用
	 */
	public void setGysZhuangTai(java.lang.String gysZhuangTai){
		this.gysZhuangTai = gysZhuangTai;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  企业属性
	 */
	@Column(name ="XING_YE_FEN_LEI",nullable=true,length=32)
	public java.lang.String getXingYeFenLei(){
		return this.xingYeFenLei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  企业属性
	 */
	public void setXingYeFenLei(java.lang.String xingYeFenLei){
		this.xingYeFenLei = xingYeFenLei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商等级
	 */
	@Column(name ="GYS_DENG_JI",nullable=true,length=32)
	public java.lang.String getGysDengJi(){
		return this.gysDengJi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商等级
	 */
	public void setGysDengJi(java.lang.String gysDengJi){
		this.gysDengJi = gysDengJi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属行业
	 */
	@Column(name ="SUO_SHU_XING_YE",nullable=true,length=32)
	public java.lang.String getSuoShuXingYe(){
		return this.suoShuXingYe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属行业
	 */
	public void setSuoShuXingYe(java.lang.String suoShuXingYe){
		this.suoShuXingYe = suoShuXingYe;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  首签日期
	 */
	@Column(name ="SHOU_QIAN_RI_QI",nullable=true)
	public java.util.Date getShouQianRiQi(){
		return this.shouQianRiQi;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  首签日期
	 */
	public void setShouQianRiQi(java.util.Date shouQianRiQi){
		this.shouQianRiQi = shouQianRiQi;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  终止合作时间
	 */
	@Column(name ="ZHONG_ZHI_HE_SHI_JIAN",nullable=true)
	public java.util.Date getZhongZhiHeShiJian(){
		return this.zhongZhiHeShiJian;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  终止合作时间
	 */
	public void setZhongZhiHeShiJian(java.util.Date zhongZhiHeShiJian){
		this.zhongZhiHeShiJian = zhongZhiHeShiJian;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  申请时间
	 */
	@Column(name ="SHEN_QING_SHI_JIAN",nullable=true)
	public java.util.Date getShenQingShiJian(){
		return this.shenQingShiJian;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  申请时间
	 */
	public void setShenQingShiJian(java.util.Date shenQingShiJian){
		this.shenQingShiJian = shenQingShiJian;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商属性
	 */
	@Column(name ="GYS_SHU_XING",nullable=true,length=32)
	public java.lang.String getGysShuXing(){
		return this.gysShuXing;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商属性
	 */
	public void setGysShuXing(java.lang.String gysShuXing){
		this.gysShuXing = gysShuXing;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  归属组织代码
	 */
	@Column(name ="GUI_SHU_ZU_ZH",nullable=true,length=32)
	public java.lang.String getGuiShuZuZh(){
		return this.guiShuZuZh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  归属组织代码
	 */
	public void setGuiShuZuZh(java.lang.String guiShuZuZh){
		this.guiShuZuZh = guiShuZuZh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  归属省份代码
	 */
	@Column(name ="GUI_SHU_SHENG",nullable=true,length=32)
	public java.lang.String getGuiShuSheng(){
		return this.guiShuSheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  归属省份代码
	 */
	public void setGuiShuSheng(java.lang.String guiShuSheng){
		this.guiShuSheng = guiShuSheng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  归属市代码
	 */
	@Column(name ="GUI_SHU_SHI_DAI",nullable=true,length=32)
	public java.lang.String getGuiShuShiDai(){
		return this.guiShuShiDai;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  归属市代码
	 */
	public void setGuiShuShiDai(java.lang.String guiShuShiDai){
		this.guiShuShiDai = guiShuShiDai;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  归属县区代码
	 */
	@Column(name ="GUI_SHU",nullable=true,length=32)
	public java.lang.String getGuiShu(){
		return this.guiShu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  归属县区代码
	 */
	public void setGuiShu(java.lang.String guiShu){
		this.guiShu = guiShu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */
	@Column(name ="DI_ZHI",nullable=true,length=32)
	public java.lang.String getDiZhi(){
		return this.diZhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setDiZhi(java.lang.String diZhi){
		this.diZhi = diZhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮政编码
	 */
	@Column(name ="YOU_ZHENG_BIAN_MA",nullable=true,length=32)
	public java.lang.String getYouZhengBianMa(){
		return this.youZhengBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮政编码
	 */
	public void setYouZhengBianMa(java.lang.String youZhengBianMa){
		this.youZhengBianMa = youZhengBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主联系人
	 */
	@Column(name ="ZHU_LIAN_XI_REN",nullable=true,length=32)
	public java.lang.String getZhuLianXiRen(){
		return this.zhuLianXiRen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主联系人
	 */
	public void setZhuLianXiRen(java.lang.String zhuLianXiRen){
		this.zhuLianXiRen = zhuLianXiRen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="DIAN_HUA",nullable=true,length=32)
	public java.lang.String getDianHua(){
		return this.dianHua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setDianHua(java.lang.String dianHua){
		this.dianHua = dianHua;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机
	 */
	@Column(name ="SHOU_JI",nullable=true,length=32)
	public java.lang.String getShouJi(){
		return this.shouJi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setShouJi(java.lang.String shouJi){
		this.shouJi = shouJi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="CHUAN_ZHEN",nullable=true,length=32)
	public java.lang.String getChuanZhen(){
		return this.chuanZhen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真
	 */
	public void setChuanZhen(java.lang.String chuanZhen){
		this.chuanZhen = chuanZhen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  Email地址
	 */
	@Column(name ="EMAILDI_ZHI",nullable=true,length=32)
	public java.lang.String getEmaildiZhi(){
		return this.emaildiZhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  Email地址
	 */
	public void setEmaildiZhi(java.lang.String emaildiZhi){
		this.emaildiZhi = emaildiZhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  网页地址
	 */
	@Column(name ="WANG_YE_DI_ZHI",nullable=true,length=32)
	public java.lang.String getWangYeDiZhi(){
		return this.wangYeDiZhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  网页地址
	 */
	public void setWangYeDiZhi(java.lang.String wangYeDiZhi){
		this.wangYeDiZhi = wangYeDiZhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法人代表
	 */
	@Column(name ="FA_REN_DAI_BIAO",nullable=true,length=32)
	public java.lang.String getFaRenDaiBiao(){
		return this.faRenDaiBiao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法人代表
	 */
	public void setFaRenDaiBiao(java.lang.String faRenDaiBiao){
		this.faRenDaiBiao = faRenDaiBiao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法人身份证号
	 */
	@Column(name ="FA_REN_SHEN_FEN",nullable=true,length=32)
	public java.lang.String getFaRenShenFen(){
		return this.faRenShenFen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法人身份证号
	 */
	public void setFaRenShenFen(java.lang.String faRenShenFen){
		this.faRenShenFen = faRenShenFen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  注册资金万元
	 */
	@Column(name ="ZHU_CE_ZI_JIN",nullable=true,length=32)
	public java.lang.String getZhuCeZiJin(){
		return this.zhuCeZiJin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  注册资金万元
	 */
	public void setZhuCeZiJin(java.lang.String zhuCeZiJin){
		this.zhuCeZiJin = zhuCeZiJin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  币别
	 */
	@Column(name ="BI_BIE",nullable=true,length=32)
	public java.lang.String getBiBie(){
		return this.biBie;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  币别
	 */
	public void setBiBie(java.lang.String biBie){
		this.biBie = biBie;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  营业执照号
	 */
	@Column(name ="YING_YE_ZHI_ZHAO",nullable=true,length=32)
	public java.lang.String getYingYeZhiZhao(){
		return this.yingYeZhiZhao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  营业执照号
	 */
	public void setYingYeZhiZhao(java.lang.String yingYeZhiZhao){
		this.yingYeZhiZhao = yingYeZhiZhao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  税务登记证号
	 */
	@Column(name ="SHUI_WU_DENG",nullable=true,length=32)
	public java.lang.String getShuiWuDeng(){
		return this.shuiWuDeng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  税务登记证号
	 */
	public void setShuiWuDeng(java.lang.String shuiWuDeng){
		this.shuiWuDeng = shuiWuDeng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组织机构代码证
	 */
	@Column(name ="ZU_ZHI_JI_GOU",nullable=true,length=32)
	public java.lang.String getZuZhiJiGou(){
		return this.zuZhiJiGou;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织机构代码证
	 */
	public void setZuZhiJiGou(java.lang.String zuZhiJiGou){
		this.zuZhiJiGou = zuZhiJiGou;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  道路运输经营许可证
	 */
	@Column(name ="DAO_LU_YUN_SHU",nullable=true,length=32)
	public java.lang.String getDaoLuYunShu(){
		return this.daoLuYunShu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  道路运输经营许可证
	 */
	public void setDaoLuYunShu(java.lang.String daoLuYunShu){
		this.daoLuYunShu = daoLuYunShu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主营业务
	 */
	@Column(name ="ZHU_YING_YE_WU",nullable=true,length=32)
	public java.lang.String getZhuYingYeWu(){
		return this.zhuYingYeWu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主营业务
	 */
	public void setZhuYingYeWu(java.lang.String zhuYingYeWu){
		this.zhuYingYeWu = zhuYingYeWu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合作意向
	 */
	@Column(name ="HE_YI_XIANG",nullable=true,length=32)
	public java.lang.String getHeYiXiang(){
		return this.heYiXiang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合作意向
	 */
	public void setHeYiXiang(java.lang.String heYiXiang){
		this.heYiXiang = heYiXiang;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批准机关
	 */
	@Column(name ="PI_ZHUN_JI_GUAN",nullable=true,length=32)
	public java.lang.String getPiZhunJiGuan(){
		return this.piZhunJiGuan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批准机关
	 */
	public void setPiZhunJiGuan(java.lang.String piZhunJiGuan){
		this.piZhunJiGuan = piZhunJiGuan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批准文号
	 */
	@Column(name ="PI_ZHUN_WEN_HAO",nullable=true,length=32)
	public java.lang.String getPiZhunWenHao(){
		return this.piZhunWenHao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批准文号
	 */
	public void setPiZhunWenHao(java.lang.String piZhunWenHao){
		this.piZhunWenHao = piZhunWenHao;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  注册日期
	 */
	@Column(name ="ZHU_CE_RI_QI",nullable=true)
	public java.util.Date getZhuCeRiQi(){
		return this.zhuCeRiQi;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  注册日期
	 */
	public void setZhuCeRiQi(java.util.Date zhuCeRiQi){
		this.zhuCeRiQi = zhuCeRiQi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BEI_ZHU",nullable=true,length=128)
	public java.lang.String getBeiZhu(){
		return this.beiZhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBeiZhu(java.lang.String beiZhu){
		this.beiZhu = beiZhu;
	}
}
