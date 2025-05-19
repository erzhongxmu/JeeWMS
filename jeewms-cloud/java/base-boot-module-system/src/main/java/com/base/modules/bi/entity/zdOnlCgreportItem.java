package com.base.modules.bi.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: onl_cgreport_item
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@ApiModel(value="onl_cgreport_head对象", description="onl_cgreport_head")
//@Data
@TableName("onl_cgreport_item")
public class zdOnlCgreportItem implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value = "id")
	private String id;
	/**报表ID*/
	@ApiModelProperty(value = "报表ID")
	private String cgrheadId;
	/**字段名字*/
	@Excel(name = "字段名字", width = 15)
	@ApiModelProperty(value = "字段名字")
	private String fieldName;
	/**字段文本*/
	@Excel(name = "字段文本", width = 15)
	@ApiModelProperty(value = "字段文本")
	private String fieldTxt;
	/**fieldWidth*/
	@Excel(name = "fieldWidth", width = 15)
	@ApiModelProperty(value = "fieldWidth")
	private Integer fieldWidth;
	/**字段类型*/
	@Excel(name = "字段类型", width = 15)
	@ApiModelProperty(value = "字段类型")
	private String fieldType;
	/**查询模式*/
	@Excel(name = "查询模式", width = 15)
	@ApiModelProperty(value = "查询模式")
	private String searchMode;
	/**是否排序  0否,1是*/
	@Excel(name = "是否排序  0否,1是", width = 15)
	@ApiModelProperty(value = "是否排序  0否,1是")
	private Integer isOrder;
	/**是否查询  0否,1是*/
	@Excel(name = "是否查询  0否,1是", width = 15)
	@ApiModelProperty(value = "是否查询  0否,1是")
	private Integer isSearch;
	/**字典CODE*/
	@Excel(name = "字典CODE", width = 15)
	@ApiModelProperty(value = "字典CODE")
	private String dictCode;
	/**字段跳转URL*/
	@Excel(name = "字段跳转URL", width = 15)
	@ApiModelProperty(value = "字段跳转URL")
	private String fieldHref;
	/**是否显示  0否,1显示*/
	@Excel(name = "是否显示  0否,1显示", width = 15)
	@ApiModelProperty(value = "是否显示  0否,1显示")
	private Integer isShow;
	/**排序*/
	@Excel(name = "排序", width = 15)
	@ApiModelProperty(value = "排序")
	private Integer orderNum;
	/**取值表达式*/
	@Excel(name = "取值表达式", width = 15)
	@ApiModelProperty(value = "取值表达式")
	private String replaceVal;
	/**是否合计 0否,1是（仅对数值有效）*/
	@Excel(name = "是否合计 0否,1是（仅对数值有效）", width = 15)
	@ApiModelProperty(value = "是否合计 0否,1是（仅对数值有效）")
	private String isTotal;
	/**分组标题*/
	@Excel(name = "分组标题", width = 15)
	@ApiModelProperty(value = "分组标题")
	private String groupTitle;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**修改人*/
	@ApiModelProperty(value = "修改人")
	private String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCgrheadId() {
		return cgrheadId;
	}

	public void setCgrheadId(String cgrheadId) {
		this.cgrheadId = cgrheadId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTxt() {
		return fieldTxt;
	}

	public void setFieldTxt(String fieldTxt) {
		this.fieldTxt = fieldTxt;
	}

	public Integer getFieldWidth() {
		return fieldWidth;
	}

	public void setFieldWidth(Integer fieldWidth) {
		this.fieldWidth = fieldWidth;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public Integer getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(Integer isOrder) {
		this.isOrder = isOrder;
	}

	public Integer getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(Integer isSearch) {
		this.isSearch = isSearch;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getFieldHref() {
		return fieldHref;
	}

	public void setFieldHref(String fieldHref) {
		this.fieldHref = fieldHref;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getReplaceVal() {
		return replaceVal;
	}

	public void setReplaceVal(String replaceVal) {
		this.replaceVal = replaceVal;
	}

	public String getIsTotal() {
		return isTotal;
	}

	public void setIsTotal(String isTotal) {
		this.isTotal = isTotal;
	}

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof zdOnlCgreportItem)) {
			return false;
		} else {
			zdOnlCgreportItem var2 = (zdOnlCgreportItem)o;
			if (!var2.canEqual(this)) {
				return false;
			} else {
				String var3 = this.getId();
				String var4 = var2.getId();
				if (var3 == null) {
					if (var4 != null) {
						return false;
					}
				} else if (!var3.equals(var4)) {
					return false;
				}

				String var5 = this.getCgrheadId();
				String var6 = var2.getCgrheadId();
				if (var5 == null) {
					if (var6 != null) {
						return false;
					}
				} else if (!var5.equals(var6)) {
					return false;
				}

				String var7 = this.getFieldName();
				String var8 = var2.getFieldName();
				if (var7 == null) {
					if (var8 != null) {
						return false;
					}
				} else if (!var7.equals(var8)) {
					return false;
				}

				label206: {
					String var9 = this.getFieldTxt();
					String var10 = var2.getFieldTxt();
					if (var9 == null) {
						if (var10 == null) {
							break label206;
						}
					} else if (var9.equals(var10)) {
						break label206;
					}

					return false;
				}

				label199: {
					Integer var11 = this.getFieldWidth();
					Integer var12 = var2.getFieldWidth();
					if (var11 == null) {
						if (var12 == null) {
							break label199;
						}
					} else if (var11.equals(var12)) {
						break label199;
					}

					return false;
				}

				String var13 = this.getFieldType();
				String var14 = var2.getFieldType();
				if (var13 == null) {
					if (var14 != null) {
						return false;
					}
				} else if (!var13.equals(var14)) {
					return false;
				}

				label185: {
					String var15 = this.getSearchMode();
					String var16 = var2.getSearchMode();
					if (var15 == null) {
						if (var16 == null) {
							break label185;
						}
					} else if (var15.equals(var16)) {
						break label185;
					}

					return false;
				}

				label178: {
					Integer var17 = this.getIsOrder();
					Integer var18 = var2.getIsOrder();
					if (var17 == null) {
						if (var18 == null) {
							break label178;
						}
					} else if (var17.equals(var18)) {
						break label178;
					}

					return false;
				}

				Integer var19 = this.getIsSearch();
				Integer var20 = var2.getIsSearch();
				if (var19 == null) {
					if (var20 != null) {
						return false;
					}
				} else if (!var19.equals(var20)) {
					return false;
				}

				String var21 = this.getDictCode();
				String var22 = var2.getDictCode();
				if (var21 == null) {
					if (var22 != null) {
						return false;
					}
				} else if (!var21.equals(var22)) {
					return false;
				}

				label157: {
					String var23 = this.getFieldHref();
					String var24 = var2.getFieldHref();
					if (var23 == null) {
						if (var24 == null) {
							break label157;
						}
					} else if (var23.equals(var24)) {
						break label157;
					}

					return false;
				}

				label150: {
					Integer var25 = this.getIsShow();
					Integer var26 = var2.getIsShow();
					if (var25 == null) {
						if (var26 == null) {
							break label150;
						}
					} else if (var25.equals(var26)) {
						break label150;
					}

					return false;
				}

				Integer var27 = this.getOrderNum();
				Integer var28 = var2.getOrderNum();
				if (var27 == null) {
					if (var28 != null) {
						return false;
					}
				} else if (!var27.equals(var28)) {
					return false;
				}

				label136: {
					String var29 = this.getReplaceVal();
					String var30 = var2.getReplaceVal();
					if (var29 == null) {
						if (var30 == null) {
							break label136;
						}
					} else if (var29.equals(var30)) {
						break label136;
					}

					return false;
				}

				String var31 = this.getCreateBy();
				String var32 = var2.getCreateBy();
				if (var31 == null) {
					if (var32 != null) {
						return false;
					}
				} else if (!var31.equals(var32)) {
					return false;
				}

				label122: {
					Date var33 = this.getCreateTime();
					Date var34 = var2.getCreateTime();
					if (var33 == null) {
						if (var34 == null) {
							break label122;
						}
					} else if (var33.equals(var34)) {
						break label122;
					}

					return false;
				}

				String var35 = this.getUpdateBy();
				String var36 = var2.getUpdateBy();
				if (var35 == null) {
					if (var36 != null) {
						return false;
					}
				} else if (!var35.equals(var36)) {
					return false;
				}

				Date var37 = this.getUpdateTime();
				Date var38 = var2.getUpdateTime();
				if (var37 == null) {
					if (var38 != null) {
						return false;
					}
				} else if (!var37.equals(var38)) {
					return false;
				}

				return true;
			}
		}
	}

	protected boolean canEqual(Object other) {
		return other instanceof zdOnlCgreportItem;
	}
	@Override
	public int hashCode() {
		boolean var1 = true;
		byte var2 = 1;
		String var3 = this.getId();
		int var21 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
		String var4 = this.getCgrheadId();
		var21 = var21 * 59 + (var4 == null ? 43 : var4.hashCode());
		String var5 = this.getFieldName();
		var21 = var21 * 59 + (var5 == null ? 43 : var5.hashCode());
		String var6 = this.getFieldTxt();
		var21 = var21 * 59 + (var6 == null ? 43 : var6.hashCode());
		Integer var7 = this.getFieldWidth();
		var21 = var21 * 59 + (var7 == null ? 43 : var7.hashCode());
		String var8 = this.getFieldType();
		var21 = var21 * 59 + (var8 == null ? 43 : var8.hashCode());
		String var9 = this.getSearchMode();
		var21 = var21 * 59 + (var9 == null ? 43 : var9.hashCode());
		Integer var10 = this.getIsOrder();
		var21 = var21 * 59 + (var10 == null ? 43 : var10.hashCode());
		Integer var11 = this.getIsSearch();
		var21 = var21 * 59 + (var11 == null ? 43 : var11.hashCode());
		String var12 = this.getDictCode();
		var21 = var21 * 59 + (var12 == null ? 43 : var12.hashCode());
		String var13 = this.getFieldHref();
		var21 = var21 * 59 + (var13 == null ? 43 : var13.hashCode());
		Integer var14 = this.getIsShow();
		var21 = var21 * 59 + (var14 == null ? 43 : var14.hashCode());
		Integer var15 = this.getOrderNum();
		var21 = var21 * 59 + (var15 == null ? 43 : var15.hashCode());
		String var16 = this.getReplaceVal();
		var21 = var21 * 59 + (var16 == null ? 43 : var16.hashCode());
		String var17 = this.getCreateBy();
		var21 = var21 * 59 + (var17 == null ? 43 : var17.hashCode());
		Date var18 = this.getCreateTime();
		var21 = var21 * 59 + (var18 == null ? 43 : var18.hashCode());
		String var19 = this.getUpdateBy();
		var21 = var21 * 59 + (var19 == null ? 43 : var19.hashCode());
		Date var20 = this.getUpdateTime();
		var21 = var21 * 59 + (var20 == null ? 43 : var20.hashCode());
		return var21;
	}
	@Override
	public String toString() {
		return "zdOnlCgreportItem(id=" + this.getId() + ", cgrheadId=" + this.getCgrheadId() + ", fieldName=" + this.getFieldName() + ", fieldTxt=" + this.getFieldTxt() + ", fieldWidth=" + this.getFieldWidth() + ", fieldType=" + this.getFieldType() + ", searchMode=" + this.getSearchMode() + ", isOrder=" + this.getIsOrder() + ", isSearch=" + this.getIsSearch() + ", dictCode=" + this.getDictCode() + ", fieldHref=" + this.getFieldHref() + ", isShow=" + this.getIsShow() + ", orderNum=" + this.getOrderNum() + ", replaceVal=" + this.getReplaceVal() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
	}
}
