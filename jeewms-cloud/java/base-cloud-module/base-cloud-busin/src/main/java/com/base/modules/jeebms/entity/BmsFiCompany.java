package com.base.modules.jeebms.entity;

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
 * @Description: bms_fi_company
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
@TableName("bms_fi_company")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bms_fi_company对象", description="账单主体")
public class BmsFiCompany implements Serializable {
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
    /**租户*/
    //	@Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private String tenantId;
    /**是否删除*/
    //@Excel(name = "是否删除", width = 15)
    @ApiModelProperty(value = "是否删除")
    private Integer isDel;
    /**公司编号*/
    @Excel(name = "公司编号", width = 15)
    @ApiModelProperty(value = "公司编号")
    private String comfiNo;
    /**公司地址*/
    @Excel(name = "公司地址", width = 15)
    @ApiModelProperty(value = "公司地址")
    private String comfiAddr;
    /**公司名字*/
    @Excel(name = "公司名字", width = 15)
    @ApiModelProperty(value = "公司名字")
    private String comfiName;
    /**营业执照号码*/
    @Excel(name = "营业执照号码", width = 15)
    @ApiModelProperty(value = "营业执照号码")
    private String comfiZhucehao;
    /**银行账号*/
    @Excel(name = "银行账号", width = 15)
    @ApiModelProperty(value = "银行账号")
    private String comfiBankid;
    /**电话*/
    @Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private String comfiTel;
    /**开户行*/
    @Excel(name = "开户行", width = 15)
    @ApiModelProperty(value = "开户行")
    private String comfiBankname;
    /**logo*/
    @Excel(name = "logo", width = 15)
    @ApiModelProperty(value = "logo")
    private String logoFile;
    /**运营章*/
    @Excel(name = "运营章", width = 15)
    @ApiModelProperty(value = "运营章")
    private String comfiDzyz;
    /**备注1*/
    @Excel(name = "备注1", width = 15)
    @ApiModelProperty(value = "备注1")
    private String comfiBeizhu1;
    /**备注2*/
    @Excel(name = "备注2", width = 15)
    @ApiModelProperty(value = "备注2")
    private String comfiBeizhu2;
    /**备注3*/
    @Excel(name = "备注3", width = 15)
    @ApiModelProperty(value = "备注3")
    private String comfiBeizhu3;
    /**备用1*/
    @Excel(name = "备用1", width = 15)
    @ApiModelProperty(value = "备用1")
    private String attr1;
    /**备用2*/
    @Excel(name = "备用2", width = 15)
    @ApiModelProperty(value = "备用2")
    private String attr2;
    /**备用3*/
    @Excel(name = "备用3", width = 15)
    @ApiModelProperty(value = "备用3")
    private String attr3;
    /**备用4*/
    @Excel(name = "备用4", width = 15)
    @ApiModelProperty(value = "备用4")
    private String attr4;
    /**备用5*/
    @Excel(name = "备用5", width = 15)
    @ApiModelProperty(value = "备用5")
    private String attr5;
}
