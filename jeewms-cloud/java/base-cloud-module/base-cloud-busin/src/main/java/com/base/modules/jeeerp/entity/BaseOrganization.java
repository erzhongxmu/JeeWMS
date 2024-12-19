package com.base.modules.jeeerp.entity;

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
 * @Description: base_organization
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Data
@TableName("base_organization")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="base_organization对象", description="base_organization")
public class BaseOrganization implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人名称*/
	@Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人名称*/
	@Excel(name = "更新人名称", width = 15)
    @ApiModelProperty(value = "更新人名称")
    private java.lang.String updateName;
	/**更新人登录名称*/
    @ApiModelProperty(value = "更新人登录名称")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**公司*/
	@Excel(name = "公司", width = 15)
    @ApiModelProperty(value = "公司")
    private java.lang.String query01;
	/**工厂*/
	@Excel(name = "工厂", width = 15)
    @ApiModelProperty(value = "工厂")
    private java.lang.String query02;
	/**库存地点*/
	@Excel(name = "库存地点", width = 15)
    @ApiModelProperty(value = "库存地点")
    private java.lang.String query03;
	/**仓库*/
	@Excel(name = "仓库", width = 15)
    @ApiModelProperty(value = "仓库")
    private java.lang.String query04;
	/**币种*/
	@Excel(name = "币种", width = 15)
    @ApiModelProperty(value = "币种")
    private java.lang.String query05;
	/**query06*/
	@Excel(name = "query06", width = 15)
    @ApiModelProperty(value = "query06")
    private java.lang.String query06;
	/**query07*/
	@Excel(name = "query07", width = 15)
    @ApiModelProperty(value = "query07")
    private java.lang.String query07;
	/**query08*/
	@Excel(name = "query08", width = 15)
    @ApiModelProperty(value = "query08")
    private java.lang.String query08;
	/**query09*/
	@Excel(name = "query09", width = 15)
    @ApiModelProperty(value = "query09")
    private java.lang.String query09;
	/**query10*/
	@Excel(name = "query10", width = 15)
    @ApiModelProperty(value = "query10")
    private java.lang.String query10;
	/**query11*/
	@Excel(name = "query11", width = 15)
    @ApiModelProperty(value = "query11")
    private java.lang.String query11;
	/**query12*/
	@Excel(name = "query12", width = 15)
    @ApiModelProperty(value = "query12")
    private java.lang.String query12;
	/**query13*/
	@Excel(name = "query13", width = 15)
    @ApiModelProperty(value = "query13")
    private java.lang.String query13;
	/**query14*/
	@Excel(name = "query14", width = 15)
    @ApiModelProperty(value = "query14")
    private java.lang.String query14;
	/**query15*/
	@Excel(name = "query15", width = 15)
    @ApiModelProperty(value = "query15")
    private java.lang.String query15;
	/**query16*/
	@Excel(name = "query16", width = 15)
    @ApiModelProperty(value = "query16")
    private java.lang.String query16;
	/**query17*/
	@Excel(name = "query17", width = 15)
    @ApiModelProperty(value = "query17")
    private java.lang.String query17;
	/**query18*/
	@Excel(name = "query18", width = 15)
    @ApiModelProperty(value = "query18")
    private java.lang.String query18;
	/**query19*/
	@Excel(name = "query19", width = 15)
    @ApiModelProperty(value = "query19")
    private java.lang.String query19;
	/**query20*/
	@Excel(name = "query20", width = 15)
    @ApiModelProperty(value = "query20")
    private java.lang.String query20;
	/**query21*/
	@Excel(name = "query21", width = 15)
    @ApiModelProperty(value = "query21")
    private java.lang.String query21;
	/**query22*/
	@Excel(name = "query22", width = 15)
    @ApiModelProperty(value = "query22")
    private java.lang.String query22;
	/**query23*/
	@Excel(name = "query23", width = 15)
    @ApiModelProperty(value = "query23")
    private java.lang.String query23;
	/**query24*/
	@Excel(name = "query24", width = 15)
    @ApiModelProperty(value = "query24")
    private java.lang.String query24;
	/**query25*/
	@Excel(name = "query25", width = 15)
    @ApiModelProperty(value = "query25")
    private java.lang.String query25;
	/**query26*/
	@Excel(name = "query26", width = 15)
    @ApiModelProperty(value = "query26")
    private java.lang.String query26;
	/**query27*/
	@Excel(name = "query27", width = 15)
    @ApiModelProperty(value = "query27")
    private java.lang.String query27;
	/**query28*/
	@Excel(name = "query28", width = 15)
    @ApiModelProperty(value = "query28")
    private java.lang.String query28;
	/**query29*/
	@Excel(name = "query29", width = 15)
    @ApiModelProperty(value = "query29")
    private java.lang.String query29;
	/**query30*/
	@Excel(name = "query30", width = 15)
    @ApiModelProperty(value = "query30")
    private java.lang.String query30;
	/**num01*/
	@Excel(name = "num01", width = 15)
    @ApiModelProperty(value = "num01")
    private java.lang.Double num01;
	/**num02*/
	@Excel(name = "num02", width = 15)
    @ApiModelProperty(value = "num02")
    private java.lang.Double num02;
	/**num03*/
	@Excel(name = "num03", width = 15)
    @ApiModelProperty(value = "num03")
    private java.lang.Double num03;
	/**num04*/
	@Excel(name = "num04", width = 15)
    @ApiModelProperty(value = "num04")
    private java.lang.Double num04;
	/**num05*/
	@Excel(name = "num05", width = 15)
    @ApiModelProperty(value = "num05")
    private java.lang.Double num05;
	/**link01*/
	@Excel(name = "link01", width = 15)
    @ApiModelProperty(value = "link01")
    private java.lang.String link01;
	/**link02*/
	@Excel(name = "link02", width = 15)
    @ApiModelProperty(value = "link02")
    private java.lang.String link02;
	/**link03*/
	@Excel(name = "link03", width = 15)
    @ApiModelProperty(value = "link03")
    private java.lang.String link03;
	/**link04*/
	@Excel(name = "link04", width = 15)
    @ApiModelProperty(value = "link04")
    private java.lang.String link04;
	/**link05*/
	@Excel(name = "link05", width = 15)
    @ApiModelProperty(value = "link05")
    private java.lang.String link05;
	/**text01*/
	@Excel(name = "text01", width = 15)
    @ApiModelProperty(value = "text01")
    private java.lang.String text01;
	/**text02*/
	@Excel(name = "text02", width = 15)
    @ApiModelProperty(value = "text02")
    private java.lang.String text02;
	/**text03*/
	@Excel(name = "text03", width = 15)
    @ApiModelProperty(value = "text03")
    private java.lang.String text03;
	/**text04*/
	@Excel(name = "text04", width = 15)
    @ApiModelProperty(value = "text04")
    private java.lang.String text04;
	/**text05*/
	@Excel(name = "text05", width = 15)
    @ApiModelProperty(value = "text05")
    private java.lang.String text05;
	/**attr1*/
	@Excel(name = "attr1", width = 15)
    @ApiModelProperty(value = "attr1")
    private java.lang.String attr1;
	/**attr2*/
	@Excel(name = "attr2", width = 15)
    @ApiModelProperty(value = "attr2")
    private java.lang.String attr2;
	/**attr3*/
	@Excel(name = "attr3", width = 15)
    @ApiModelProperty(value = "attr3")
    private java.lang.String attr3;
	/**attr4*/
	@Excel(name = "attr4", width = 15)
    @ApiModelProperty(value = "attr4")
    private java.lang.String attr4;
	/**attr5*/
	@Excel(name = "attr5", width = 15)
    @ApiModelProperty(value = "attr5")
    private java.lang.String attr5;
}
