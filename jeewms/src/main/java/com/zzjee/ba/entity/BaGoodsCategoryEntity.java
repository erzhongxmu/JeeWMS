package com.zzjee.ba.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;
import java.util.List;

import com.zzjee.ba.vo.BaGoodsCategoryVoo;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;

/**
 * @Title: Entity
 * @Description: 商品类目
 * @author onlineGenerator
 * @date 2021-08-25 17:16:36
 * @version V1.0
 *
 */
@Entity
@Table(name = "ba_goods_category", schema = "")
@SuppressWarnings("serial")
public class BaGoodsCategoryEntity implements java.io.Serializable {
	/**id*/
	private java.lang.Integer id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createTime;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateTime;
	/**所属部门*/
	private java.lang.String sysOrgCode;
	/**类目编码*/
	@Excel(name="类目编码")
	private java.lang.String categoryCode;
	/**类目名称*/
	@Excel(name="类目名称")
	private java.lang.String categoryName;
	/**类目级别*/
	@Excel(name="类目级别")
	private java.lang.Integer categoryLevel;
	/**父级目录*/
	@Excel(name="父级目录")
	private java.lang.Integer pid;
	/**是否为顶级目录*/
	@Excel(name="是否为顶级目录")
	private java.lang.String topNode;


	private List<BaGoodsCategoryEntity> baGoodsCategory;

	@Transient
	public List<BaGoodsCategoryEntity> getBaGoodsCategory() {
		return baGoodsCategory;
	}

	public void setBaGoodsCategory(List<BaGoodsCategoryEntity> baGoodsCategory) {
		this.baGoodsCategory = baGoodsCategory;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",length=10)
	public java.lang.Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(java.lang.Integer id){
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
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
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
	@Column(name ="UPDATE_TIME",nullable=true)
	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
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
	 *@return: java.lang.String  类目编码
	 */
	@Column(name ="CATEGORY_CODE",nullable=true,length=50)
	public java.lang.String getCategoryCode(){
		return this.categoryCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类目编码
	 */
	public void setCategoryCode(java.lang.String categoryCode){
		this.categoryCode = categoryCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类目名称
	 */
	@Column(name ="CATEGORY_NAME",nullable=true,length=50)
	public java.lang.String getCategoryName(){
		return this.categoryName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类目名称
	 */
	public void setCategoryName(java.lang.String categoryName){
		this.categoryName = categoryName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  类目级别
	 */
	@Column(name ="CATEGORY_LEVEL",nullable=true,length=10)
	public java.lang.Integer getCategoryLevel(){
		return this.categoryLevel;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  类目级别
	 */
	public void setCategoryLevel(java.lang.Integer categoryLevel){
		this.categoryLevel = categoryLevel;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  父级目录
	 */
	@Column(name ="PID",nullable=true,length=10)
	public java.lang.Integer getPid(){
		return this.pid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  父级目录
	 */
	public void setPid(java.lang.Integer pid){
		this.pid = pid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否为顶级目录
	 */
	@Column(name ="TOP_NODE",nullable=true,length=10)
	public java.lang.String getTopNode(){
		return this.topNode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否为顶级目录
	 */
	public void setTopNode(java.lang.String topNode){
		this.topNode = topNode;
	}
}
