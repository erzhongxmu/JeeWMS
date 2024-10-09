package com.zzjee.tmsv.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: v_ysdd
 * @author onlineGenerator
 * @date 2018-05-04 00:07:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_ysdd", schema = "")
@SuppressWarnings("serial")
public class VYsddEntity implements java.io.Serializable {
	/**主键*/

	private String id;
	/**下单日期*/
    @Excel(name="下单日期",width=15)
		
	private Date createDate;
	/**发货人*/
    @Excel(name="发货人",width=15)
		
	private String fahuoren;
	/**货物*/
    @Excel(name="货物",width=15)
		
	private String huowu;
	/**件数*/
    @Excel(name="件数",width=15)
		
	private String hwshjs;
	/**重量*/
    @Excel(name="重量",width=15)
		
	private String zhongl;
	/**立方米*/
    @Excel(name="立方米",width=15)
		
	private String tiji;
	/**收货人地址*/
    @Excel(name="收货人地址",width=15)
		
	private String shrdh;
	/**收货人*/
    @Excel(name="收货人",width=15)
		
	private String shouhuoren;
	/**送货方式*/
    @Excel(name="送货方式",width=15)
		
	private String hwshfs;
	/**收货人电话*/
    @Excel(name="收货人电话",width=15)
		
	private String shrsj;
	/**代收款金额*/
    @Excel(name="代收款金额",width=15)
		
	private String daishouk;
	/**运费*/
    @Excel(name="运费",width=15)
		
	private String hwyf;
	/**货物总费用*/
    @Excel(name="货物总费用",width=15)
		
	private String hwzfy;
	/**卸货费*/
    @Excel(name="卸货费",width=15)
		
	private String hwxhf;
	/**车号*/
    @Excel(name="车号",width=15)
		
	private String chehao;
	/**状态*/
    @Excel(name="状态",width=15)
		
	private String zhuangtai;
	/**回单备注*/
    @Excel(name="回单备注",width=15)
		
	private String ywhdbz;
	/**送达时间*/
    @Excel(name="送达时间",width=15)
	private Date sdsj;

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
	 *@return: java.lang.String  下单日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=10)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下单日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  送达时间
	 */
	@Column(name ="SDSJ",nullable=true)
	public Date getSdsj(){
		return this.sdsj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  送达时间
	 */
	public void setSdsj(Date sdsj){
		this.sdsj = sdsj;
	}

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
