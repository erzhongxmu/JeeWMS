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
 * @Description: 账单基础信息配置
 * @Author: base-boot
 * @Date:   2021-12-26
 * @Version: V1.0
 */
@Data
@TableName("wm_bill_conf")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_bill_conf对象", description="账单基础信息配置")
public class WmBillConf implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**货主*/
	@Excel(name = "货主", width = 15, dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
	@Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "货主")
    private java.lang.String cusCode;
	/**logo文件*/
	@Excel(name = "logo文件", width = 15)
    @ApiModelProperty(value = "logo文件")
    private java.lang.String logoFile;
	/**盖章图片*/
	@Excel(name = "盖章图片", width = 15)
    @ApiModelProperty(value = "盖章图片")
    private java.lang.String comfidzyzFile;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String comfiname;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private java.lang.String comfiaddr;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private java.lang.String comfitel;
	/**银行卡号*/
	@Excel(name = "银行卡号", width = 15)
    @ApiModelProperty(value = "银行卡号")
    private java.lang.String comfibankid;
	/**开户行*/
	@Excel(name = "开户行", width = 15)
    @ApiModelProperty(value = "开户行")
    private java.lang.String comfibankname;
	/**营业执照*/
	@Excel(name = "营业执照", width = 15)
    @ApiModelProperty(value = "营业执照")
    private java.lang.String comfizhucehao;
	/**备注1*/
	@Excel(name = "备注1", width = 15)
    @ApiModelProperty(value = "备注1")
    private java.lang.String comfibeizhu1;
	/**备注2*/
	@Excel(name = "备注2", width = 15)
    @ApiModelProperty(value = "备注2")
    private java.lang.String comfibeizhu2;
	/**备注3*/
	@Excel(name = "备注3", width = 15)
    @ApiModelProperty(value = "备注3")
    private java.lang.String comfibeizhu3;
	/**备注4*/
	@Excel(name = "备注4", width = 15)
    @ApiModelProperty(value = "备注4")
    private java.lang.String comfibeizhu4;
	/**备注5*/
	@Excel(name = "备注5", width = 15)
    @ApiModelProperty(value = "备注5")
    private java.lang.String comfibeizhu5;
	/**备注6*/
	@Excel(name = "备注6", width = 15)
    @ApiModelProperty(value = "备注6")
    private java.lang.String comfibeizhu6;
	/**备注7*/
	@Excel(name = "备注7", width = 15)
    @ApiModelProperty(value = "备注7")
    private java.lang.String comfibeizhu7;
	/**备注8*/
	@Excel(name = "备注8", width = 15)
    @ApiModelProperty(value = "备注8")
    private java.lang.String comfibeizhu8;
}
