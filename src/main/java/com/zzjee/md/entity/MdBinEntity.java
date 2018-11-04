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
 * @Description: 仓位定义
 * @author erzhongxmu
 * @date 2017-08-15 23:17:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "md_bin", schema = "")
@SuppressWarnings("serial")
public class MdBinEntity implements java.io.Serializable {
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
	/**库位名称*/
	private java.lang.String kuWeiMingCheng;
	/**库位编码*/
	@Excel(name="库位编码")
	private java.lang.String kuWeiBianMa;
	/**库位条码*/
	@Excel(name="库位条码")
	private java.lang.String kuWeiTiaoMa;
	/**库位类型*/
	@Excel(name="库位类型")
	private java.lang.String kuWeiLeiXing;
	/**库位属性*/
	@Excel(name="库位属性")
	private java.lang.String kuWeiShuXing;
	@Excel(name="产品属性")
	private java.lang.String chpShuXing;
	/**上架次序*/
	@Excel(name="上架次序")
	private java.lang.String shangJiaCiXu;
	/**取货次序*/
	@Excel(name="取货次序")
	private java.lang.String quHuoCiXu;
	/**所属客户*/
	@Excel(name="所属客户")
	private java.lang.String suoShuKeHu;
	/**体积单位*/
	private java.lang.String tiJiDanWei;
	/**重量单位*/
	private java.lang.String zhongLiangDanWei;
	/**面积单位*/
	private java.lang.String mianJiDanWei;
	/**最大体积*/
	@Excel(name="最大体积")
	private java.lang.String zuiDaTiJi;
	/**最大重量*/
	@Excel(name="最大重量")
	private java.lang.String zuiDaZhongLiang;
	/**最大面积*/
	private java.lang.String zuiDaMianJi;
	/**最大托盘*/
	@Excel(name="最大托盘")
	private java.lang.String zuiDaTuoPan;
	/**长度*/
	@Excel(name="长度")
	private java.lang.String chang;
	/**宽度*/
	@Excel(name="宽度")
	private java.lang.String kuan;
	/**高度*/
	@Excel(name="高度")
	private java.lang.String gao;
	/**停用*/
	@Excel(name="停用")
	private java.lang.String tingYong;
	/**明细*/
	@Excel(name="备注")
	private java.lang.String mingXi;
	/**明细*/
	@Excel(name="备注1")
	private java.lang.String mingXi1;
	/**明细*/
	@Excel(name="备注2")
	private java.lang.String mingXi2;
	@Excel(name="动线")
	private java.lang.String mingXi3;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String binStore;
	
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
	 *@return: java.lang.String  库位名称
	 */
	@Column(name ="KU_WEI_MING_CHENG",nullable=true,length=100)
	public java.lang.String getKuWeiMingCheng(){
		return this.kuWeiMingCheng;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位名称
	 */
	public void setKuWeiMingCheng(java.lang.String kuWeiMingCheng){
		this.kuWeiMingCheng = kuWeiMingCheng;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库位编码
	 */
	@Column(name ="KU_WEI_BIAN_MA",nullable=true,length=32)
	public java.lang.String getKuWeiBianMa(){
		return this.kuWeiBianMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位编码
	 */
	public void setKuWeiBianMa(java.lang.String kuWeiBianMa){
		this.kuWeiBianMa = kuWeiBianMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库位条码
	 */
	@Column(name ="KU_WEI_TIAO_MA",nullable=true,length=32)
	public java.lang.String getKuWeiTiaoMa(){
		return this.kuWeiTiaoMa;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位条码
	 */
	public void setKuWeiTiaoMa(java.lang.String kuWeiTiaoMa){
		this.kuWeiTiaoMa = kuWeiTiaoMa;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库位类型
	 */
	@Column(name ="KU_WEI_LEI_XING",nullable=true,length=32)
	public java.lang.String getKuWeiLeiXing(){
		return this.kuWeiLeiXing;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位类型
	 */
	public void setKuWeiLeiXing(java.lang.String kuWeiLeiXing){
		this.kuWeiLeiXing = kuWeiLeiXing;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  库位属性
	 */
	@Column(name ="KU_WEI_SHU_XING",nullable=true,length=32)
	public java.lang.String getKuWeiShuXing(){
		return this.kuWeiShuXing;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  库位属性
	 */
	public void setKuWeiShuXing(java.lang.String kuWeiShuXing){
		this.kuWeiShuXing = kuWeiShuXing;
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
	 *@return: java.lang.String  上架次序
	 */
	@Column(name ="SHANG_JIA_CI_XU",nullable=true,length=32)
	public java.lang.String getShangJiaCiXu(){
		return this.shangJiaCiXu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上架次序
	 */
	public void setShangJiaCiXu(java.lang.String shangJiaCiXu){
		this.shangJiaCiXu = shangJiaCiXu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  取货次序
	 */
	@Column(name ="QU_HUO_CI_XU",nullable=true,length=32)
	public java.lang.String getQuHuoCiXu(){
		return this.quHuoCiXu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  取货次序
	 */
	public void setQuHuoCiXu(java.lang.String quHuoCiXu){
		this.quHuoCiXu = quHuoCiXu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属客户
	 */
	@Column(name ="SUO_SHU_KE_HU",nullable=true,length=32)
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
	 *@return: java.lang.String  体积单位
	 */
	@Column(name ="TI_JI_DAN_WEI",nullable=true,length=32)
	public java.lang.String getTiJiDanWei(){
		return this.tiJiDanWei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  体积单位
	 */
	public void setTiJiDanWei(java.lang.String tiJiDanWei){
		this.tiJiDanWei = tiJiDanWei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重量单位
	 */
	@Column(name ="ZHONG_LIANG_DAN_WEI",nullable=true,length=32)
	public java.lang.String getZhongLiangDanWei(){
		return this.zhongLiangDanWei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重量单位
	 */
	public void setZhongLiangDanWei(java.lang.String zhongLiangDanWei){
		this.zhongLiangDanWei = zhongLiangDanWei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  面积单位
	 */
	@Column(name ="MIAN_JI_DAN_WEI",nullable=true,length=32)
	public java.lang.String getMianJiDanWei(){
		return this.mianJiDanWei;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  面积单位
	 */
	public void setMianJiDanWei(java.lang.String mianJiDanWei){
		this.mianJiDanWei = mianJiDanWei;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最大体积
	 */
	@Column(name ="ZUI_DA_TI_JI",nullable=true,length=33)
	public java.lang.String getZuiDaTiJi(){
		return this.zuiDaTiJi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最大体积
	 */
	public void setZuiDaTiJi(java.lang.String zuiDaTiJi){
		this.zuiDaTiJi = zuiDaTiJi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最大重量
	 */
	@Column(name ="ZUI_DA_ZHONG_LIANG",nullable=true,length=34)
	public java.lang.String getZuiDaZhongLiang(){
		return this.zuiDaZhongLiang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最大重量
	 */
	public void setZuiDaZhongLiang(java.lang.String zuiDaZhongLiang){
		this.zuiDaZhongLiang = zuiDaZhongLiang;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最大面积
	 */
	@Column(name ="ZUI_DA_MIAN_JI",nullable=true,length=35)
	public java.lang.String getZuiDaMianJi(){
		return this.zuiDaMianJi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最大面积
	 */
	public void setZuiDaMianJi(java.lang.String zuiDaMianJi){
		this.zuiDaMianJi = zuiDaMianJi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  最大托盘
	 */
	@Column(name ="ZUI_DA_TUO_PAN",nullable=true,length=32)
	public java.lang.String getZuiDaTuoPan(){
		return this.zuiDaTuoPan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  最大托盘
	 */
	public void setZuiDaTuoPan(java.lang.String zuiDaTuoPan){
		this.zuiDaTuoPan = zuiDaTuoPan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  长度
	 */
	@Column(name ="CHANG",nullable=true,length=32)
	public java.lang.String getChang(){
		return this.chang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  长度
	 */
	public void setChang(java.lang.String chang){
		this.chang = chang;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  宽度
	 */
	@Column(name ="KUAN",nullable=true,length=32)
	public java.lang.String getKuan(){
		return this.kuan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  宽度
	 */
	public void setKuan(java.lang.String kuan){
		this.kuan = kuan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  高度
	 */
	@Column(name ="GAO",nullable=true,length=32)
	public java.lang.String getGao(){
		return this.gao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  高度
	 */
	public void setGao(java.lang.String gao){
		this.gao = gao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  停用
	 */
	@Column(name ="TING_YONG",nullable=true,length=32)
	public java.lang.String getTingYong(){
		return this.tingYong;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  停用
	 */
	public void setTingYong(java.lang.String tingYong){
		this.tingYong = tingYong;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  明细
	 */
	@Column(name ="MING_XI",nullable=true,length=32)
	public java.lang.String getMingXi(){
		return this.mingXi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  明细
	 */
	public void setMingXi(java.lang.String mingXi){
		this.mingXi = mingXi;
	}
	@Column(name ="MING_XI1",nullable=true,length=45)
	public java.lang.String getMingXi1(){
		return this.mingXi1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  明细
	 */
	public void setMingXi1(java.lang.String mingXi1){
		this.mingXi1 = mingXi1;
	}
	@Column(name ="MING_XI2",nullable=true,length=32)
	public java.lang.String getMingXi2(){
		return this.mingXi2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  明细
	 */
	public void setMingXi2(java.lang.String mingXi2){
		this.mingXi2 = mingXi2;
	}
	
	@Column(name ="MING_XI3",nullable=true,length=32)
	public java.lang.String getMingXi3(){
		return this.mingXi3;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  明细
	 */
	public void setMingXi3(java.lang.String mingXi3){
		this.mingXi3 = mingXi3;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="BIN_STORE",nullable=true,length=32)
	public java.lang.String getBinStore(){
		return this.binStore;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setBinStore(java.lang.String binStore){
		this.binStore = binStore;
	}
}
