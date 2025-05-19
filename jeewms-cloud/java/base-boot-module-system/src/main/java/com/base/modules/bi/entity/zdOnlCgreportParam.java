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
 * @Description: onl_cgreport_param
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@ApiModel(value="onl_cgreport_head对象", description="onl_cgreport_head")
//@Data
@TableName("onl_cgreport_param")
public class zdOnlCgreportParam implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_UUID)
	@ApiModelProperty(value = "id")
	private String id;
	/**动态报表ID*/
	@ApiModelProperty(value = "动态报表ID")
	private String cgrheadId;
	/**参数字段*/
	@Excel(name = "参数字段", width = 15)
	@ApiModelProperty(value = "参数字段")
	private String paramName;
	/**参数文本*/
	@Excel(name = "参数文本", width = 15)
	@ApiModelProperty(value = "参数文本")
	private String paramTxt;
	/**参数默认值*/
	@Excel(name = "参数默认值", width = 15)
	@ApiModelProperty(value = "参数默认值")
	private String paramValue;
	/**排序*/
	@Excel(name = "排序", width = 15)
	@ApiModelProperty(value = "排序")
	private Integer orderNum;
	/**创建人登录名称*/
	@ApiModelProperty(value = "创建人登录名称")
	private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private Date createTime;
	/**更新人登录名称*/
	@ApiModelProperty(value = "更新人登录名称")
	private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
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

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamTxt() {
		return paramTxt;
	}

	public void setParamTxt(String paramTxt) {
		this.paramTxt = paramTxt;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
		} else if (!(o instanceof zdOnlCgreportParam)) {
			return false;
		} else {
			zdOnlCgreportParam var2 = (zdOnlCgreportParam)o;
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

				String var7 = this.getParamName();
				String var8 = var2.getParamName();
				if (var7 == null) {
					if (var8 != null) {
						return false;
					}
				} else if (!var7.equals(var8)) {
					return false;
				}

				label110: {
					String var9 = this.getParamTxt();
					String var10 = var2.getParamTxt();
					if (var9 == null) {
						if (var10 == null) {
							break label110;
						}
					} else if (var9.equals(var10)) {
						break label110;
					}

					return false;
				}

				label103: {
					String var11 = this.getParamValue();
					String var12 = var2.getParamValue();
					if (var11 == null) {
						if (var12 == null) {
							break label103;
						}
					} else if (var11.equals(var12)) {
						break label103;
					}

					return false;
				}

				Integer var13 = this.getOrderNum();
				Integer var14 = var2.getOrderNum();
				if (var13 == null) {
					if (var14 != null) {
						return false;
					}
				} else if (!var13.equals(var14)) {
					return false;
				}

				label89: {
					String var15 = this.getCreateBy();
					String var16 = var2.getCreateBy();
					if (var15 == null) {
						if (var16 == null) {
							break label89;
						}
					} else if (var15.equals(var16)) {
						break label89;
					}

					return false;
				}

				label82: {
					Date var17 = this.getCreateTime();
					Date var18 = var2.getCreateTime();
					if (var17 == null) {
						if (var18 == null) {
							break label82;
						}
					} else if (var17.equals(var18)) {
						break label82;
					}

					return false;
				}

				String var19 = this.getUpdateBy();
				String var20 = var2.getUpdateBy();
				if (var19 == null) {
					if (var20 != null) {
						return false;
					}
				} else if (!var19.equals(var20)) {
					return false;
				}

				Date var21 = this.getUpdateTime();
				Date var22 = var2.getUpdateTime();
				if (var21 == null) {
					if (var22 != null) {
						return false;
					}
				} else if (!var21.equals(var22)) {
					return false;
				}

				return true;
			}
		}
	}

	protected boolean canEqual(Object other) {
		return other instanceof zdOnlCgreportParam;
	}
	@Override
	public int hashCode() {
		boolean var1 = true;
		byte var2 = 1;
		String var3 = this.getId();
		int var13 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
		String var4 = this.getCgrheadId();
		var13 = var13 * 59 + (var4 == null ? 43 : var4.hashCode());
		String var5 = this.getParamName();
		var13 = var13 * 59 + (var5 == null ? 43 : var5.hashCode());
		String var6 = this.getParamTxt();
		var13 = var13 * 59 + (var6 == null ? 43 : var6.hashCode());
		String var7 = this.getParamValue();
		var13 = var13 * 59 + (var7 == null ? 43 : var7.hashCode());
		Integer var8 = this.getOrderNum();
		var13 = var13 * 59 + (var8 == null ? 43 : var8.hashCode());
		String var9 = this.getCreateBy();
		var13 = var13 * 59 + (var9 == null ? 43 : var9.hashCode());
		Date var10 = this.getCreateTime();
		var13 = var13 * 59 + (var10 == null ? 43 : var10.hashCode());
		String var11 = this.getUpdateBy();
		var13 = var13 * 59 + (var11 == null ? 43 : var11.hashCode());
		Date var12 = this.getUpdateTime();
		var13 = var13 * 59 + (var12 == null ? 43 : var12.hashCode());
		return var13;
	}
	@Override
	public String toString() {
		return "zdOnlCgreportParam(id=" + this.getId() + ", cgrheadId=" + this.getCgrheadId() + ", paramName=" + this.getParamName() + ", paramTxt=" + this.getParamTxt() + ", paramValue=" + this.getParamValue() + ", orderNum=" + this.getOrderNum() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ")";
	}
}
