package com.zzjee.md.entity;

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
 * @Description: 第三方客户
 * @author erzhongxmu
 * @date 2018-09-01 21:06:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "md_cus_other", schema = "")
@SuppressWarnings("serial")
public class MdCusOtherEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",format = "yyyy-MM-dd")
	private Date updateDate;
	/**所属部门*/
	@Excel(name="所属部门")
	private String sysOrgCode;
	/**所属公司*/
	@Excel(name="所属公司")
	private String sysCompanyCode;
	/**所属客户*/
	@Excel(name="所属客户")
	private String suoShuKeHu;
	/**中文全称*/
	@Excel(name="中文全称")
	private String zhongWenQch;
	/**助记码*/
	@Excel(name="助记码")
	private String zhuJiMa;
	/**客户简称*/
	@Excel(name="客户简称")
	private String keHuJianCheng;
	/**客户编码*/
	@Excel(name="客户编码")
	private String keHuBianMa;
	/**客户英文名称*/
	@Excel(name="客户英文名称")
	private String keHuYingWen;
	/**曾用企业代码*/
	@Excel(name="曾用企业代码")
	private String zengYongQi;
	/**曾用企业名称*/
	@Excel(name="曾用企业名称")
	private String zengYongQiYe;
	/**客户状态*/
	@Excel(name="客户状态")
	private String keHuZhuangTai;
	/**企业属性*/
	@Excel(name="企业属性")
	private String xingYeFenLei;
	/**客户等级*/
	@Excel(name="客户等级")
	private String keHuDengJi;
	/**所属行业*/
	@Excel(name="所属行业")
	private String suoShuXingYe;
	/**首签日期*/
	@Excel(name="首签日期",format = "yyyy-MM-dd")
	private Date shouQianRiQi;
	/**终止合作时间*/
	@Excel(name="终止合作时间",format = "yyyy-MM-dd")
	private Date zhongZhiHeShiJian;
	/**申请时间*/
	@Excel(name="申请时间",format = "yyyy-MM-dd")
	private Date shenQingShiJian;
	/**客户属性*/
	@Excel(name="客户属性")
	private String keHuShuXing;
	/**归属组织代码*/
	@Excel(name="归属组织代码")
	private String guiShuZuZh;
	/**归属省份代码*/
	@Excel(name="归属省份代码")
	private String guiShuSheng;
	/**归属市代码*/
	@Excel(name="归属市代码")
	private String guiShuShiDai;
	/**归属县区代码*/
	@Excel(name="归属县区代码")
	private String guiShu;
	/**地址*/
	@Excel(name="地址")
	private String diZhi;
	/**邮政编码*/
	@Excel(name="邮政编码")
	private String youZhengBianMa;
	/**主联系人*/
	@Excel(name="主联系人")
	private String zhuLianXiRen;
	/**电话*/
	@Excel(name="电话")
	private String dianHua;
	/**手机*/
	@Excel(name="手机")
	private String shouJi;
	/**传真*/
	@Excel(name="传真")
	private String chuanZhen;
	/**Email地址*/
	@Excel(name="Email地址")
	private String emaildiZhi;
	/**网页地址*/
	@Excel(name="网页地址")
	private String wangYeDiZhi;
	/**法人代表*/
	@Excel(name="法人代表")
	private String faRenDaiBiao;
	/**法人身份证号*/
	@Excel(name="法人身份证号")
	private String faRenShenFen;
	/**注册资金万元*/
	@Excel(name="注册资金万元")
	private String zhuCeZiJin;
	/**币别*/
	@Excel(name="币别")
	private String biBie;
	/**营业执照号*/
	@Excel(name="营业执照号")
	private String yingYeZhiZhao;
	/**税务登记证号*/
	@Excel(name="税务登记证号")
	private String shuiWuDeng;
	/**组织机构代码证*/
	@Excel(name="组织机构代码证")
	private String zuZhiJiGou;
	/**道路运输经营许可证*/
	@Excel(name="道路运输经营许可证")
	private String daoLuYunShu;
	/**主营业务*/
	@Excel(name="主营业务")
	private String zhuYingYeWu;
	/**合作意向*/
	@Excel(name="合作意向")
	private String heYiXiang;
	/**批准机关*/
	@Excel(name="批准机关")
	private String piZhunJiGuan;
	/**批准文号*/
	@Excel(name="批准文号")
	private String piZhunWenHao;
	/**注册日期*/
	@Excel(name="注册日期",format = "yyyy-MM-dd")
	private Date zhuCeRiQi;
	/**备注*/
	@Excel(name="备注")
	private String beiZhu;
	/**联系人1*/
	@Excel(name="联系人1")
	private String zhuLianXiRen1;
	/**电话1*/
	@Excel(name="电话1")
	private String dianHua1;
	
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
	 *@return: java.lang.String  所属客户
	 */
	@Column(name ="SUO_SHU_KE_HU",nullable=true,length=32)
	public String getSuoShuKeHu(){
		return this.suoShuKeHu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属客户
	 */
	public void setSuoShuKeHu(String suoShuKeHu){
		this.suoShuKeHu = suoShuKeHu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中文全称
	 */
	@Column(name ="ZHONG_WEN_QCH",nullable=true,length=100)
	public String getZhongWenQch(){
		return this.zhongWenQch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中文全称
	 */
	public void setZhongWenQch(String zhongWenQch){
		this.zhongWenQch = zhongWenQch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  助记码
	 */
	@Column(name ="ZHU_JI_MA",nullable=true,length=32)
	public String getZhuJiMa(){
		return this.zhuJiMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  助记码
	 */
	public void setZhuJiMa(String zhuJiMa){
		this.zhuJiMa = zhuJiMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户简称
	 */
	@Column(name ="KE_HU_JIAN_CHENG",nullable=true,length=32)
	public String getKeHuJianCheng(){
		return this.keHuJianCheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户简称
	 */
	public void setKeHuJianCheng(String keHuJianCheng){
		this.keHuJianCheng = keHuJianCheng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户编码
	 */
	@Column(name ="KE_HU_BIAN_MA",nullable=true,length=32)
	public String getKeHuBianMa(){
		return this.keHuBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户编码
	 */
	public void setKeHuBianMa(String keHuBianMa){
		this.keHuBianMa = keHuBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户英文名称
	 */
	@Column(name ="KE_HU_YING_WEN",nullable=true,length=100)
	public String getKeHuYingWen(){
		return this.keHuYingWen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户英文名称
	 */
	public void setKeHuYingWen(String keHuYingWen){
		this.keHuYingWen = keHuYingWen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  曾用企业代码
	 */
	@Column(name ="ZENG_YONG_QI",nullable=true,length=32)
	public String getZengYongQi(){
		return this.zengYongQi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  曾用企业代码
	 */
	public void setZengYongQi(String zengYongQi){
		this.zengYongQi = zengYongQi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  曾用企业名称
	 */
	@Column(name ="ZENG_YONG_QI_YE",nullable=true,length=100)
	public String getZengYongQiYe(){
		return this.zengYongQiYe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  曾用企业名称
	 */
	public void setZengYongQiYe(String zengYongQiYe){
		this.zengYongQiYe = zengYongQiYe;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户状态
	 */
	@Column(name ="KE_HU_ZHUANG_TAI",nullable=true,length=32)
	public String getKeHuZhuangTai(){
		return this.keHuZhuangTai;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户状态
	 */
	public void setKeHuZhuangTai(String keHuZhuangTai){
		this.keHuZhuangTai = keHuZhuangTai;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  企业属性
	 */
	@Column(name ="XING_YE_FEN_LEI",nullable=true,length=32)
	public String getXingYeFenLei(){
		return this.xingYeFenLei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  企业属性
	 */
	public void setXingYeFenLei(String xingYeFenLei){
		this.xingYeFenLei = xingYeFenLei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户等级
	 */
	@Column(name ="KE_HU_DENG_JI",nullable=true,length=32)
	public String getKeHuDengJi(){
		return this.keHuDengJi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户等级
	 */
	public void setKeHuDengJi(String keHuDengJi){
		this.keHuDengJi = keHuDengJi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属行业
	 */
	@Column(name ="SUO_SHU_XING_YE",nullable=true,length=32)
	public String getSuoShuXingYe(){
		return this.suoShuXingYe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属行业
	 */
	public void setSuoShuXingYe(String suoShuXingYe){
		this.suoShuXingYe = suoShuXingYe;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  首签日期
	 */
	@Column(name ="SHOU_QIAN_RI_QI",nullable=true)
	public Date getShouQianRiQi(){
		return this.shouQianRiQi;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  首签日期
	 */
	public void setShouQianRiQi(Date shouQianRiQi){
		this.shouQianRiQi = shouQianRiQi;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  终止合作时间
	 */
	@Column(name ="ZHONG_ZHI_HE_SHI_JIAN",nullable=true)
	public Date getZhongZhiHeShiJian(){
		return this.zhongZhiHeShiJian;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  终止合作时间
	 */
	public void setZhongZhiHeShiJian(Date zhongZhiHeShiJian){
		this.zhongZhiHeShiJian = zhongZhiHeShiJian;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  申请时间
	 */
	@Column(name ="SHEN_QING_SHI_JIAN",nullable=true)
	public Date getShenQingShiJian(){
		return this.shenQingShiJian;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  申请时间
	 */
	public void setShenQingShiJian(Date shenQingShiJian){
		this.shenQingShiJian = shenQingShiJian;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户属性
	 */
	@Column(name ="KE_HU_SHU_XING",nullable=true,length=32)
	public String getKeHuShuXing(){
		return this.keHuShuXing;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户属性
	 */
	public void setKeHuShuXing(String keHuShuXing){
		this.keHuShuXing = keHuShuXing;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  归属组织代码
	 */
	@Column(name ="GUI_SHU_ZU_ZH",nullable=true,length=32)
	public String getGuiShuZuZh(){
		return this.guiShuZuZh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  归属组织代码
	 */
	public void setGuiShuZuZh(String guiShuZuZh){
		this.guiShuZuZh = guiShuZuZh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  归属省份代码
	 */
	@Column(name ="GUI_SHU_SHENG",nullable=true,length=32)
	public String getGuiShuSheng(){
		return this.guiShuSheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  归属省份代码
	 */
	public void setGuiShuSheng(String guiShuSheng){
		this.guiShuSheng = guiShuSheng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  归属市代码
	 */
	@Column(name ="GUI_SHU_SHI_DAI",nullable=true,length=32)
	public String getGuiShuShiDai(){
		return this.guiShuShiDai;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  归属市代码
	 */
	public void setGuiShuShiDai(String guiShuShiDai){
		this.guiShuShiDai = guiShuShiDai;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  归属县区代码
	 */
	@Column(name ="GUI_SHU",nullable=true,length=32)
	public String getGuiShu(){
		return this.guiShu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  归属县区代码
	 */
	public void setGuiShu(String guiShu){
		this.guiShu = guiShu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */
	@Column(name ="DI_ZHI",nullable=true,length=132)
	public String getDiZhi(){
		return this.diZhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setDiZhi(String diZhi){
		this.diZhi = diZhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮政编码
	 */
	@Column(name ="YOU_ZHENG_BIAN_MA",nullable=true,length=32)
	public String getYouZhengBianMa(){
		return this.youZhengBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮政编码
	 */
	public void setYouZhengBianMa(String youZhengBianMa){
		this.youZhengBianMa = youZhengBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主联系人
	 */
	@Column(name ="ZHU_LIAN_XI_REN",nullable=true,length=32)
	public String getZhuLianXiRen(){
		return this.zhuLianXiRen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主联系人
	 */
	public void setZhuLianXiRen(String zhuLianXiRen){
		this.zhuLianXiRen = zhuLianXiRen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="DIAN_HUA",nullable=true,length=32)
	public String getDianHua(){
		return this.dianHua;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setDianHua(String dianHua){
		this.dianHua = dianHua;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机
	 */
	@Column(name ="SHOU_JI",nullable=true,length=32)
	public String getShouJi(){
		return this.shouJi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setShouJi(String shouJi){
		this.shouJi = shouJi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="CHUAN_ZHEN",nullable=true,length=32)
	public String getChuanZhen(){
		return this.chuanZhen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真
	 */
	public void setChuanZhen(String chuanZhen){
		this.chuanZhen = chuanZhen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  Email地址
	 */
	@Column(name ="EMAILDI_ZHI",nullable=true,length=32)
	public String getEmaildiZhi(){
		return this.emaildiZhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  Email地址
	 */
	public void setEmaildiZhi(String emaildiZhi){
		this.emaildiZhi = emaildiZhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  网页地址
	 */
	@Column(name ="WANG_YE_DI_ZHI",nullable=true,length=32)
	public String getWangYeDiZhi(){
		return this.wangYeDiZhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  网页地址
	 */
	public void setWangYeDiZhi(String wangYeDiZhi){
		this.wangYeDiZhi = wangYeDiZhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法人代表
	 */
	@Column(name ="FA_REN_DAI_BIAO",nullable=true,length=32)
	public String getFaRenDaiBiao(){
		return this.faRenDaiBiao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法人代表
	 */
	public void setFaRenDaiBiao(String faRenDaiBiao){
		this.faRenDaiBiao = faRenDaiBiao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法人身份证号
	 */
	@Column(name ="FA_REN_SHEN_FEN",nullable=true,length=32)
	public String getFaRenShenFen(){
		return this.faRenShenFen;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法人身份证号
	 */
	public void setFaRenShenFen(String faRenShenFen){
		this.faRenShenFen = faRenShenFen;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  注册资金万元
	 */
	@Column(name ="ZHU_CE_ZI_JIN",nullable=true,length=32)
	public String getZhuCeZiJin(){
		return this.zhuCeZiJin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  注册资金万元
	 */
	public void setZhuCeZiJin(String zhuCeZiJin){
		this.zhuCeZiJin = zhuCeZiJin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  币别
	 */
	@Column(name ="BI_BIE",nullable=true,length=32)
	public String getBiBie(){
		return this.biBie;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  币别
	 */
	public void setBiBie(String biBie){
		this.biBie = biBie;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  营业执照号
	 */
	@Column(name ="YING_YE_ZHI_ZHAO",nullable=true,length=32)
	public String getYingYeZhiZhao(){
		return this.yingYeZhiZhao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  营业执照号
	 */
	public void setYingYeZhiZhao(String yingYeZhiZhao){
		this.yingYeZhiZhao = yingYeZhiZhao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  税务登记证号
	 */
	@Column(name ="SHUI_WU_DENG",nullable=true,length=32)
	public String getShuiWuDeng(){
		return this.shuiWuDeng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  税务登记证号
	 */
	public void setShuiWuDeng(String shuiWuDeng){
		this.shuiWuDeng = shuiWuDeng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组织机构代码证
	 */
	@Column(name ="ZU_ZHI_JI_GOU",nullable=true,length=32)
	public String getZuZhiJiGou(){
		return this.zuZhiJiGou;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织机构代码证
	 */
	public void setZuZhiJiGou(String zuZhiJiGou){
		this.zuZhiJiGou = zuZhiJiGou;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  道路运输经营许可证
	 */
	@Column(name ="DAO_LU_YUN_SHU",nullable=true,length=32)
	public String getDaoLuYunShu(){
		return this.daoLuYunShu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  道路运输经营许可证
	 */
	public void setDaoLuYunShu(String daoLuYunShu){
		this.daoLuYunShu = daoLuYunShu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主营业务
	 */
	@Column(name ="ZHU_YING_YE_WU",nullable=true,length=32)
	public String getZhuYingYeWu(){
		return this.zhuYingYeWu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主营业务
	 */
	public void setZhuYingYeWu(String zhuYingYeWu){
		this.zhuYingYeWu = zhuYingYeWu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合作意向
	 */
	@Column(name ="HE_YI_XIANG",nullable=true,length=32)
	public String getHeYiXiang(){
		return this.heYiXiang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合作意向
	 */
	public void setHeYiXiang(String heYiXiang){
		this.heYiXiang = heYiXiang;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批准机关
	 */
	@Column(name ="PI_ZHUN_JI_GUAN",nullable=true,length=32)
	public String getPiZhunJiGuan(){
		return this.piZhunJiGuan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批准机关
	 */
	public void setPiZhunJiGuan(String piZhunJiGuan){
		this.piZhunJiGuan = piZhunJiGuan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批准文号
	 */
	@Column(name ="PI_ZHUN_WEN_HAO",nullable=true,length=32)
	public String getPiZhunWenHao(){
		return this.piZhunWenHao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批准文号
	 */
	public void setPiZhunWenHao(String piZhunWenHao){
		this.piZhunWenHao = piZhunWenHao;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  注册日期
	 */
	@Column(name ="ZHU_CE_RI_QI",nullable=true)
	public Date getZhuCeRiQi(){
		return this.zhuCeRiQi;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  注册日期
	 */
	public void setZhuCeRiQi(Date zhuCeRiQi){
		this.zhuCeRiQi = zhuCeRiQi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BEI_ZHU",nullable=true,length=128)
	public String getBeiZhu(){
		return this.beiZhu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBeiZhu(String beiZhu){
		this.beiZhu = beiZhu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人1
	 */
	@Column(name ="ZHU_LIAN_XI_REN1",nullable=true,length=32)
	public String getZhuLianXiRen1(){
		return this.zhuLianXiRen1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人1
	 */
	public void setZhuLianXiRen1(String zhuLianXiRen1){
		this.zhuLianXiRen1 = zhuLianXiRen1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话1
	 */
	@Column(name ="DIAN_HUA1",nullable=true,length=32)
	public String getDianHua1(){
		return this.dianHua1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话1
	 */
	public void setDianHua1(String dianHua1){
		this.dianHua1 = dianHua1;
	}
}
