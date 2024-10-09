package com.zzjee.tms.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 运输订单
 * @author onlineGenerator
 * @date 2018-04-26 00:42:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tms_yw_dingdan", schema = "")
@SuppressWarnings("serial")
public class TmsYwDingdanEntity implements java.io.Serializable {
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
	/**单号*/
    @Excel(name="单号",width=15)
		
	private String fadh;
	/**下单人*/
    @Excel(name="下单人",width=15)
		
	private String username;
	/**发货人*/
    @Excel(name="发货人",width=15)
		
	private String fahuoren;
	/**发货人电话*/
    @Excel(name="发货人电话",width=15)
		
	private String fhrdh;
	/**发货人地址*/
    @Excel(name="发货人地址",width=15)
		
	private String fhrdz;
	/**收货人*/
    @Excel(name="收货人",width=15)
		
	private String shouhuoren;
	/**收货人地址*/
    @Excel(name="收货人地址",width=15)
		
	private String shrdh;
	/**车号*/
    @Excel(name="车号",width=15)
		
	private String chehao;
	/**货物*/
    @Excel(name="货物",width=15)
		
	private String huowu;
	/**长米*/
    @Excel(name="长米",width=15)
		
	private String chang;
	/**宽米*/
    @Excel(name="宽米",width=15)
		
	private String kuan;
	/**高米*/
    @Excel(name="高米",width=15)
		
	private String gao;
	/**立方米*/
    @Excel(name="立方米",width=15)
		
	private String tiji;
	/**重量*/
    @Excel(name="重量",width=15)
		
	private String zhongl;
	/**代收款金额*/
    @Excel(name="代收款金额",width=15)
		
	private String daishouk;
	/**是否等通知*/
    @Excel(name="是否等通知",width=15)
		
	private String dengtongzhi;
	/**价格*/
    @Excel(name="价格",width=15)
		
	private String jiage;
	/**下单附件*/
//    @Excel(name="下单附件",width=15)
		
	private String xiadanfj;
	/**回单附件*/
//    @Excel(name="回单附件",width=15)
		
	private String huidanfj;
	/**状态*/
    @Excel(name="状态",width=15)
		
	private String zhuangtai;
	/**下单人名字*/
//    @Excel(name="下单人名字",width=15)
		
	private String xdrmz;
	/**司机*/
    @Excel(name="司机",width=15)
		
	private String siji;
	/**送达时间*/
    @Excel(name="送达时间",width=15)
		
	private String sdsj;
	/**预计送达时间*/
//    @Excel(name="预计送达时间",width=15)
		
	private String yjsdsj;
	/**收货人电话*/
    @Excel(name="收货人电话",width=15)
		
	private String shrsj;
	/**送货方式*/
    @Excel(name="送货方式",width=15)
		
	private String hwshfs;
	/**件数*/
    @Excel(name="件数",width=15)
		
	private String hwshjs;
	/**运费*/
    @Excel(name="运费",width=15)
		
	private String hwyf;
	/**卸货费*/
    @Excel(name="卸货费",width=15)
		
	private String hwxhf;
	/**货物总费用*/
    @Excel(name="货物总费用",width=15)
		
	private String hwzfy;
	/**下单备注*/
    @Excel(name="下单备注",width=15)
		
	private String ywddbz;
	/**派车备注*/
//    @Excel(name="派车备注",width=15)
		
	private String ywpcbz;
	/**装车备注*/
//    @Excel(name="装车备注",width=15)
		
	private String ywzcbz;
	/**回单备注*/
    @Excel(name="回单备注",width=15)
		
	private String ywhdbz;
	/**客户单号*/
    @Excel(name="客户单号",width=15)
		
	private String ywkhdh;
	private String by1;
	
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
	 *@return: java.lang.String  单号
	 */
	@Column(name ="FADH",nullable=true,length=32)
	public String getFadh(){
		return this.fadh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单号
	 */
	public void setFadh(String fadh){
		this.fadh = fadh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  下单人
	 */
	@Column(name ="USERNAME",nullable=true,length=32)
	public String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下单人
	 */
	public void setUsername(String username){
		this.username = username;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发货人
	 */
	@Column(name ="FAHUOREN",nullable=true,length=32)
	public String getFahuoren(){
		return this.fahuoren;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发货人
	 */
	public void setFahuoren(String fahuoren){
		this.fahuoren = fahuoren;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发货人电话
	 */
	@Column(name ="FHRDH",nullable=true,length=32)
	public String getFhrdh(){
		return this.fhrdh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发货人电话
	 */
	public void setFhrdh(String fhrdh){
		this.fhrdh = fhrdh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发货人地址
	 */
	@Column(name ="FHRDZ",nullable=true,length=32)
	public String getFhrdz(){
		return this.fhrdz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发货人地址
	 */
	public void setFhrdz(String fhrdz){
		this.fhrdz = fhrdz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人
	 */
	@Column(name ="SHOUHUOREN",nullable=true,length=32)
	public String getShouhuoren(){
		return this.shouhuoren;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人
	 */
	public void setShouhuoren(String shouhuoren){
		this.shouhuoren = shouhuoren;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人地址
	 */
	@Column(name ="SHRDH",nullable=true,length=32)
	public String getShrdh(){
		return this.shrdh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人地址
	 */
	public void setShrdh(String shrdh){
		this.shrdh = shrdh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车号
	 */
	@Column(name ="CHEHAO",nullable=true,length=32)
	public String getChehao(){
		return this.chehao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车号
	 */
	public void setChehao(String chehao){
		this.chehao = chehao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货物
	 */
	@Column(name ="HUOWU",nullable=true,length=32)
	public String getHuowu(){
		return this.huowu;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  货物
	 */
	public void setHuowu(String huowu){
		this.huowu = huowu;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  长米
	 */
	@Column(name ="CHANG",nullable=true,length=32)
	public String getChang(){
		return this.chang;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  长米
	 */
	public void setChang(String chang){
		this.chang = chang;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  宽米
	 */
	@Column(name ="KUAN",nullable=true,length=32)
	public String getKuan(){
		return this.kuan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  宽米
	 */
	public void setKuan(String kuan){
		this.kuan = kuan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  高米
	 */
	@Column(name ="GAO",nullable=true,length=32)
	public String getGao(){
		return this.gao;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  高米
	 */
	public void setGao(String gao){
		this.gao = gao;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  立方米
	 */
	@Column(name ="TIJI",nullable=true,length=32)
	public String getTiji(){
		return this.tiji;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  立方米
	 */
	public void setTiji(String tiji){
		this.tiji = tiji;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重量
	 */
	@Column(name ="ZHONGL",nullable=true,length=32)
	public String getZhongl(){
		return this.zhongl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  重量
	 */
	public void setZhongl(String zhongl){
		this.zhongl = zhongl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代收款金额
	 */
	@Column(name ="DAISHOUK",nullable=true,length=32)
	public String getDaishouk(){
		return this.daishouk;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代收款金额
	 */
	public void setDaishouk(String daishouk){
		this.daishouk = daishouk;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否等通知
	 */
	@Column(name ="DENGTONGZHI",nullable=true,length=32)
	public String getDengtongzhi(){
		return this.dengtongzhi;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否等通知
	 */
	public void setDengtongzhi(String dengtongzhi){
		this.dengtongzhi = dengtongzhi;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  价格
	 */
	@Column(name ="JIAGE",nullable=true,length=32)
	public String getJiage(){
		return this.jiage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  价格
	 */
	public void setJiage(String jiage){
		this.jiage = jiage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  下单附件
	 */
	@Column(name ="XIADANFJ",nullable=true,length=250)
	public String getXiadanfj(){
		return this.xiadanfj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下单附件
	 */
	public void setXiadanfj(String xiadanfj){
		this.xiadanfj = xiadanfj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回单附件
	 */
	@Column(name ="HUIDANFJ",nullable=true,length=250)
	public String getHuidanfj(){
		return this.huidanfj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回单附件
	 */
	public void setHuidanfj(String huidanfj){
		this.huidanfj = huidanfj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="ZHUANGTAI",nullable=true,length=32)
	public String getZhuangtai(){
		return this.zhuangtai;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setZhuangtai(String zhuangtai){
		this.zhuangtai = zhuangtai;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  下单人名字
	 */
	@Column(name ="XDRMZ",nullable=true,length=32)
	public String getXdrmz(){
		return this.xdrmz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下单人名字
	 */
	public void setXdrmz(String xdrmz){
		this.xdrmz = xdrmz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  司机
	 */
	@Column(name ="SIJI",nullable=true,length=32)
	public String getSiji(){
		return this.siji;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  司机
	 */
	public void setSiji(String siji){
		this.siji = siji;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  送达时间
	 */
	@Column(name ="SDSJ",nullable=true,length=32)
	public String getSdsj(){
		return this.sdsj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  送达时间
	 */
	public void setSdsj(String sdsj){
		this.sdsj = sdsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预计送达时间
	 */
	@Column(name ="YJSDSJ",nullable=true,length=32)
	public String getYjsdsj(){
		return this.yjsdsj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预计送达时间
	 */
	public void setYjsdsj(String yjsdsj){
		this.yjsdsj = yjsdsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收货人电话
	 */
	@Column(name ="SHRSJ",nullable=true,length=32)
	public String getShrsj(){
		return this.shrsj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收货人电话
	 */
	public void setShrsj(String shrsj){
		this.shrsj = shrsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  送货方式
	 */
	@Column(name ="HWSHFS",nullable=true,length=32)
	public String getHwshfs(){
		return this.hwshfs;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  送货方式
	 */
	public void setHwshfs(String hwshfs){
		this.hwshfs = hwshfs;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  件数
	 */
	@Column(name ="HWSHJS",nullable=true,length=32)
	public String getHwshjs(){
		return this.hwshjs;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  件数
	 */
	public void setHwshjs(String hwshjs){
		this.hwshjs = hwshjs;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  运费
	 */
	@Column(name ="HWYF",nullable=true,length=32)
	public String getHwyf(){
		return this.hwyf;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  运费
	 */
	public void setHwyf(String hwyf){
		this.hwyf = hwyf;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  卸货费
	 */
	@Column(name ="HWXHF",nullable=true,length=32)
	public String getHwxhf(){
		return this.hwxhf;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  卸货费
	 */
	public void setHwxhf(String hwxhf){
		this.hwxhf = hwxhf;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  货物总费用
	 */
	@Column(name ="HWZFY",nullable=true,length=256)
	public String getHwzfy(){
		return this.hwzfy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  货物总费用
	 */
	public void setHwzfy(String hwzfy){
		this.hwzfy = hwzfy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  下单备注
	 */
	@Column(name ="YWDDBZ",nullable=true,length=256)
	public String getYwddbz(){
		return this.ywddbz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下单备注
	 */
	public void setYwddbz(String ywddbz){
		this.ywddbz = ywddbz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  派车备注
	 */
	@Column(name ="YWPCBZ",nullable=true,length=256)
	public String getYwpcbz(){
		return this.ywpcbz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  派车备注
	 */
	public void setYwpcbz(String ywpcbz){
		this.ywpcbz = ywpcbz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  装车备注
	 */
	@Column(name ="YWZCBZ",nullable=true,length=256)
	public String getYwzcbz(){
		return this.ywzcbz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  装车备注
	 */
	public void setYwzcbz(String ywzcbz){
		this.ywzcbz = ywzcbz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回单备注
	 */
	@Column(name ="YWHDBZ",nullable=true,length=256)
	public String getYwhdbz(){
		return this.ywhdbz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回单备注
	 */
	public void setYwhdbz(String ywhdbz){
		this.ywhdbz = ywhdbz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户单号
	 */
	@Column(name ="YWKHDH",nullable=true,length=32)
	public String getYwkhdh(){
		return this.ywkhdh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户单号
	 */
	public void setYwkhdh(String ywkhdh){
		this.ywkhdh = ywkhdh;
	}

	@Column(name ="BY1",nullable=true,length=32)
	public String getBy1(){
		return this.by1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户单号
	 */
	public void setBy1(String by1){
		this.by1 = by1;
	}
}
