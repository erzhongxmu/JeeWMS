package com.base.modules.jeewms.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.base.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 商品子表
 * @Author: base-boot
 * @Date:   2022-02-10
 * @Version: V1.0
 */
@Data
@TableName("md_goods_item")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="md_goods_item对象", description="商品子表")
public class MdGoodsItem implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;

	/**商品编码*/
    @ApiModelProperty(value = "商品编码")
    private String shpBianMa;
	/**拆零单位1*/
    @ApiModelProperty(value = "拆零单位1")
    private String unit1;
	/**拆零单位2*/
    @ApiModelProperty(value = "拆零单位2")
    private String unit2;
	/**拆零数量1*/
    @ApiModelProperty(value = "拆零数量1")
    private String num1;

	/**备用字段*/
	@Excel(name = "成品编码", width = 15)
    @ApiModelProperty(value = "成品编码")
    private String sttr1;
	/**备用字段*/
	@Excel(name = "材料编码", width = 15)
    @ApiModelProperty(value = "材料编码")
    private String sttr2;
    /**商品名称*/
    @Excel(name = "材料名称", width = 15)
    @ApiModelProperty(value = "材料名称")
    private String shpMingCheng;
    /**拆零数量2*/
    @Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private String num2;
	/**备用字段*/
    @ApiModelProperty(value = "备用字段")
    private String sttr3;
	/**备用字段*/
    @ApiModelProperty(value = "备用字段")
    private String sttr4;
	/**备用字段*/
    @ApiModelProperty(value = "备用字段")
    private String sttr5;
    /**租户*/
    @ApiModelProperty(value = "租户")
    private Integer tenantId;
}
