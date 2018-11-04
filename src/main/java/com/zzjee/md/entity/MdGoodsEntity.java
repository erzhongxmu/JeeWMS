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
 * @Description: 商品信息
 * @author erzhongxmu
 * @date 2017-08-15 23:16:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "md_goods", schema = "")
@SuppressWarnings("serial")
public class MdGoodsEntity implements java.io.Serializable {
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
	/**所属客户*/
	@Excel(name="所属客户")
	private java.lang.String suoShuKeHu;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String shpMingCheng;
	/**商品简称*/
	private java.lang.String shpJianCheng;
	/**商品编码*/
	@Excel(name="商品编码")
	private java.lang.String shpBianMa;
	@Excel(name="客户商品编码")
	private java.lang.String shpBianMakh;
	/**商品型号*/
	private java.lang.String shpXingHao;
	/**商品规格*/
	private java.lang.String shpGuiGe;
	/**商品颜色*/
	private java.lang.String shpYanSe;
	/**产品属性*/
	@Excel(name="产品属性")
	private java.lang.String chpShuXing;
	/**存放温层*/
	@Excel(name="存放温层")
	private java.lang.String cfWenCeng;
	/**拆零控制*/
	@Excel(name="拆零控制")
	private java.lang.String chlKongZhi;
	/**码盘单层数量*/
	@Excel(name="码盘单层数量")
	private java.lang.String mpDanCeng;
	/**码盘层高*/
	@Excel(name="码盘层高")
	private java.lang.String mpCengGao;
	/**计费商品类*/
	@Excel(name="计费商品类")
	private java.lang.String jfShpLei;
	/**商品品牌*/
	private java.lang.String shpPinPai;
	/**商品条码*/
	@Excel(name="商品条码")
	private java.lang.String shpTiaoMa;
	/**品牌图片*/
	private java.lang.String ppTuPian;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.String bzhiQi;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String shlDanWei;
	/**拆零单位*/
	@Excel(name="拆零单位")
	private java.lang.String jshDanWei;
	/**体积*/
	@Excel(name="体积")
	private java.lang.String tiJiCm;
	/**重量*/
	@Excel(name="重量")
	private java.lang.String zhlKg;
	/**重量*/
	@Excel(name="允收天数")
	private java.lang.String zhlKgm;
	/**拆零数量*/
	@Excel(name="拆零数量")
	private java.lang.String chlShl;
	/**件数与体积比*/
	private java.lang.String jtiJiBi;
	/**件数与毛重比*/
	private java.lang.String jmZhongBi;
	/**件数与净重比*/
	private java.lang.String jjZhongBi;
	/**尺寸单位*/
	private java.lang.String chcDanWei;
	/**长单品*/
	private java.lang.String chDanPin;
	/**宽单品*/
	private java.lang.String kuDanPin;
	/**高单品*/
	private java.lang.String gaoDanPin;
	/**长整箱*/
	@Excel(name="长")
	private java.lang.String chZhXiang;
	/**宽整箱*/
	@Excel(name="宽")
	private java.lang.String kuZhXiang;
	/**高整箱*/
	@Excel(name="高")
	private java.lang.String gaoZhXiang;
	@Excel(name="基准温度")
	private java.lang.String jiZhunwendu;
	/**商品描述*/
	private java.lang.String shpMiaoShu;
	/**停用*/
	private java.lang.String zhuangTai;
	
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
	 *@return: java.lang.String  所属客户
	 */
	@Column(name ="SUO_SHU_KE_HU",nullable=true,length=100)
	public java.lang.String getSuoShuKeHu(){
		return this.suoShuKeHu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属客户
	 */
	public void setSuoShuKeHu(java.lang.String suoShuKeHu){
		this.suoShuKeHu = suoShuKeHu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="SHP_MING_CHENG",nullable=true,length=100)
	public java.lang.String getShpMingCheng(){
		return this.shpMingCheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setShpMingCheng(java.lang.String shpMingCheng){
		this.shpMingCheng = shpMingCheng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品简称
	 */
	@Column(name ="SHP_JIAN_CHENG",nullable=true,length=32)
	public java.lang.String getShpJianCheng(){
		return this.shpJianCheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品简称
	 */
	public void setShpJianCheng(java.lang.String shpJianCheng){
		this.shpJianCheng = shpJianCheng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品编码
	 */
	@Column(name ="SHP_BIAN_MA",nullable=true,length=32)
	public java.lang.String getShpBianMa(){
		return this.shpBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编码
	 */
	public void setShpBianMa(java.lang.String shpBianMa){
		this.shpBianMa = shpBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品编码
	 */
	@Column(name ="SHP_BIAN_MAKH",nullable=true,length=32)
	public java.lang.String getShpBianMakh(){
		return this.shpBianMakh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编码
	 */
	public void setShpBianMakh(java.lang.String shpBianMakh){
		this.shpBianMakh = shpBianMakh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品型号
	 */
	@Column(name ="SHP_XING_HAO",nullable=true,length=100)
	public java.lang.String getShpXingHao(){
		return this.shpXingHao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品型号
	 */
	public void setShpXingHao(java.lang.String shpXingHao){
		this.shpXingHao = shpXingHao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品规格
	 */
	@Column(name ="SHP_GUI_GE",nullable=true,length=32)
	public java.lang.String getShpGuiGe(){
		return this.shpGuiGe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品规格
	 */
	public void setShpGuiGe(java.lang.String shpGuiGe){
		this.shpGuiGe = shpGuiGe;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品颜色
	 */
	@Column(name ="SHP_YAN_SE",nullable=true,length=32)
	public java.lang.String getShpYanSe(){
		return this.shpYanSe;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品颜色
	 */
	public void setShpYanSe(java.lang.String shpYanSe){
		this.shpYanSe = shpYanSe;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产品属性
	 */
	@Column(name ="CHP_SHU_XING",nullable=true,length=32)
	public java.lang.String getChpShuXing(){
		return this.chpShuXing;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产品属性
	 */
	public void setChpShuXing(java.lang.String chpShuXing){
		this.chpShuXing = chpShuXing;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  存放温层
	 */
	@Column(name ="CF_WEN_CENG",nullable=true,length=32)
	public java.lang.String getCfWenCeng(){
		return this.cfWenCeng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  存放温层
	 */
	public void setCfWenCeng(java.lang.String cfWenCeng){
		this.cfWenCeng = cfWenCeng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  拆零控制
	 */
	@Column(name ="CHL_KONG_ZHI",nullable=true,length=32)
	public java.lang.String getChlKongZhi(){
		return this.chlKongZhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  拆零控制
	 */
	public void setChlKongZhi(java.lang.String chlKongZhi){
		this.chlKongZhi = chlKongZhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  码盘单层数量
	 */
	@Column(name ="MP_DAN_CENG",nullable=true,length=32)
	public java.lang.String getMpDanCeng(){
		return this.mpDanCeng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  码盘单层数量
	 */
	public void setMpDanCeng(java.lang.String mpDanCeng){
		this.mpDanCeng = mpDanCeng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  码盘层高
	 */
	@Column(name ="MP_CENG_GAO",nullable=true,length=33)
	public java.lang.String getMpCengGao(){
		return this.mpCengGao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  码盘层高
	 */
	public void setMpCengGao(java.lang.String mpCengGao){
		this.mpCengGao = mpCengGao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计费商品类
	 */
	@Column(name ="JF_SHP_LEI",nullable=true,length=34)
	public java.lang.String getJfShpLei(){
		return this.jfShpLei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计费商品类
	 */
	public void setJfShpLei(java.lang.String jfShpLei){
		this.jfShpLei = jfShpLei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品品牌
	 */
	@Column(name ="SHP_PIN_PAI",nullable=true,length=35)
	public java.lang.String getShpPinPai(){
		return this.shpPinPai;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品品牌
	 */
	public void setShpPinPai(java.lang.String shpPinPai){
		this.shpPinPai = shpPinPai;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品条码
	 */
	@Column(name ="SHP_TIAO_MA",nullable=true,length=32)
	public java.lang.String getShpTiaoMa(){
		return this.shpTiaoMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品条码
	 */
	public void setShpTiaoMa(java.lang.String shpTiaoMa){
		this.shpTiaoMa = shpTiaoMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌图片
	 */
	@Column(name ="PP_TU_PIAN",nullable=true,length=32)
	public java.lang.String getPpTuPian(){
		return this.ppTuPian;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品牌图片
	 */
	public void setPpTuPian(java.lang.String ppTuPian){
		this.ppTuPian = ppTuPian;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期
	 */
	@Column(name ="BZHI_QI",nullable=true,length=32)
	public java.lang.String getBzhiQi(){
		return this.bzhiQi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保质期
	 */
	public void setBzhiQi(java.lang.String bzhiQi){
		this.bzhiQi = bzhiQi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="SHL_DAN_WEI",nullable=true,length=32)
	public java.lang.String getShlDanWei(){
		return this.shlDanWei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setShlDanWei(java.lang.String shlDanWei){
		this.shlDanWei = shlDanWei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  拆零单位
	 */
	@Column(name ="JSH_DAN_WEI",nullable=true,length=32)
	public java.lang.String getJshDanWei(){
		return this.jshDanWei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  拆零单位
	 */
	public void setJshDanWei(java.lang.String jshDanWei){
		this.jshDanWei = jshDanWei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  体积
	 */
	@Column(name ="TI_JI_CM",nullable=true,length=32)
	public java.lang.String getTiJiCm(){
		return this.tiJiCm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  体积
	 */
	public void setTiJiCm(java.lang.String tiJiCm){
		this.tiJiCm = tiJiCm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重量
	 */
	@Column(name ="ZHL_KG",nullable=true,length=32)
	public java.lang.String getZhlKg(){
		return this.zhlKg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重量
	 */
	public void setZhlKgm(java.lang.String zhlKgm){
		this.zhlKgm = zhlKgm;
	}
	@Column(name ="ZHL_KGM",nullable=true,length=32)
	public java.lang.String getZhlKgm(){
		return this.zhlKgm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重量
	 */
	public void setZhlKg(java.lang.String zhlKg){
		this.zhlKg = zhlKg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  拆零数量
	 */
	@Column(name ="CHL_SHL",nullable=true,length=32)
	public java.lang.String getChlShl(){
		return this.chlShl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  拆零数量
	 */
	public void setChlShl(java.lang.String chlShl){
		this.chlShl = chlShl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  件数与体积比
	 */
	@Column(name ="JTI_JI_BI",nullable=true,length=32)
	public java.lang.String getJtiJiBi(){
		return this.jtiJiBi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  件数与体积比
	 */
	public void setJtiJiBi(java.lang.String jtiJiBi){
		this.jtiJiBi = jtiJiBi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  件数与毛重比
	 */
	@Column(name ="JM_ZHONG_BI",nullable=true,length=32)
	public java.lang.String getJmZhongBi(){
		return this.jmZhongBi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  件数与毛重比
	 */
	public void setJmZhongBi(java.lang.String jmZhongBi){
		this.jmZhongBi = jmZhongBi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  件数与净重比
	 */
	@Column(name ="JJ_ZHONG_BI",nullable=true,length=32)
	public java.lang.String getJjZhongBi(){
		return this.jjZhongBi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  件数与净重比
	 */
	public void setJjZhongBi(java.lang.String jjZhongBi){
		this.jjZhongBi = jjZhongBi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  尺寸单位
	 */
	@Column(name ="CHC_DAN_WEI",nullable=true,length=32)
	public java.lang.String getChcDanWei(){
		return this.chcDanWei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  尺寸单位
	 */
	public void setChcDanWei(java.lang.String chcDanWei){
		this.chcDanWei = chcDanWei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  长单品
	 */
	@Column(name ="CH_DAN_PIN",nullable=true,length=32)
	public java.lang.String getChDanPin(){
		return this.chDanPin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  长单品
	 */
	public void setChDanPin(java.lang.String chDanPin){
		this.chDanPin = chDanPin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  宽单品
	 */
	@Column(name ="KU_DAN_PIN",nullable=true,length=32)
	public java.lang.String getKuDanPin(){
		return this.kuDanPin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  宽单品
	 */
	public void setKuDanPin(java.lang.String kuDanPin){
		this.kuDanPin = kuDanPin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  高单品
	 */
	@Column(name ="GAO_DAN_PIN",nullable=true,length=32)
	public java.lang.String getGaoDanPin(){
		return this.gaoDanPin;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  高单品
	 */
	public void setGaoDanPin(java.lang.String gaoDanPin){
		this.gaoDanPin = gaoDanPin;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  长整箱
	 */
	@Column(name ="CH_ZH_XIANG",nullable=true,length=32)
	public java.lang.String getChZhXiang(){
		return this.chZhXiang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  长整箱
	 */
	public void setChZhXiang(java.lang.String chZhXiang){
		this.chZhXiang = chZhXiang;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  宽整箱
	 */
	@Column(name ="KU_ZH_XIANG",nullable=true,length=32)
	public java.lang.String getKuZhXiang(){
		return this.kuZhXiang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  宽整箱
	 */
	public void setKuZhXiang(java.lang.String kuZhXiang){
		this.kuZhXiang = kuZhXiang;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  高整箱
	 */
	@Column(name ="GAO_ZH_XIANG",nullable=true,length=32)
	public java.lang.String getGaoZhXiang(){
		return this.gaoZhXiang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  高整箱
	 */
	public void setGaoZhXiang(java.lang.String gaoZhXiang){
		this.gaoZhXiang = gaoZhXiang;
	}
	
	@Column(name ="JIZHUN_WENDU",nullable=true,length=32)
	public java.lang.String getJiZhunwendu(){
		return this.jiZhunwendu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  高整箱
	 */
	public void setJiZhunwendu(java.lang.String jiZhunwendu){
		this.jiZhunwendu = jiZhunwendu;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品描述
	 */
	@Column(name ="SHP_MIAO_SHU",nullable=true,length=32)
	public java.lang.String getShpMiaoShu(){
		return this.shpMiaoShu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品描述
	 */
	public void setShpMiaoShu(java.lang.String shpMiaoShu){
		this.shpMiaoShu = shpMiaoShu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  停用
	 */
	@Column(name ="ZHUANG_TAI",nullable=true,length=32)
	public java.lang.String getZhuangTai(){
		return this.zhuangTai;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  停用
	 */
	public void setZhuangTai(java.lang.String zhuangTai){
		this.zhuangTai = zhuangTai;
	}
}
