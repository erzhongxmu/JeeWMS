package com.base.modules.jeewms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.base.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: tms_yw_dingdan
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
@Data
@TableName("tms_yw_dingdan")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tms_yw_dingdan对象", description="tms_yw_dingdan")
public class TmsYwDingdan implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
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
	/**单号*/
	@Excel(name = "单号", width = 15)
    @ApiModelProperty(value = "单号")
    private java.lang.String fadh;
	/**下单人*/
	@Excel(name = "下单人", width = 15)
    @ApiModelProperty(value = "下单人")
    private java.lang.String username;
	/**发货人*/
	@Excel(name = "发货人", width = 15)
    @ApiModelProperty(value = "发货人")
    private java.lang.String fahuoren;
	/**发货人电话*/
	@Excel(name = "发货人电话", width = 15)
    @ApiModelProperty(value = "发货人电话")
    private java.lang.String fhrdh;
	/**发货人地址*/
	@Excel(name = "发货人地址", width = 15)
    @ApiModelProperty(value = "发货人地址")
    private java.lang.String fhrdz;
	/**收货人*/
	@Excel(name = "收货人", width = 15)
    @ApiModelProperty(value = "收货人")
    private java.lang.String shouhuoren;
	/**收货人电话*/
	@Excel(name = "收货人电话", width = 15)
    @ApiModelProperty(value = "收货人电话")
    private java.lang.String shrsj;
	/**收货人地址*/
	@Excel(name = "收货人地址", width = 15)
    @ApiModelProperty(value = "收货人地址")
    private java.lang.String shrdh;
	/**车号*/
	@Excel(name = "车号", width = 15)
    @ApiModelProperty(value = "车号")
    private java.lang.String chehao;
	/**货物*/
	@Excel(name = "货物", width = 15)
    @ApiModelProperty(value = "货物")
    private java.lang.String huowu;
	/**长米*/
	@Excel(name = "长米", width = 15)
    @ApiModelProperty(value = "长米")
    private java.lang.String chang;
	/**宽米*/
	@Excel(name = "宽米", width = 15)
    @ApiModelProperty(value = "宽米")
    private java.lang.String kuan;
	/**高米*/
	@Excel(name = "高米", width = 15)
    @ApiModelProperty(value = "高米")
    private java.lang.String gao;
	/**立方米*/
	@Excel(name = "立方米", width = 15)
    @ApiModelProperty(value = "立方米")
    private java.lang.String tiji;
	/**重量*/
	@Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
    private java.lang.String zhongl;
	/**代收款金额*/
	@Excel(name = "代收款金额", width = 15)
    @ApiModelProperty(value = "代收款金额")
    private java.lang.String daishouk;
	/**是否等通知*/
	@Excel(name = "是否等通知", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "是否等通知")
    private java.lang.String dengtongzhi;
	/**价格*/
	@Excel(name = "价格", width = 15)
    @ApiModelProperty(value = "价格")
    private java.lang.String jiage;
	/**下单附件*/
	@Excel(name = "下单附件", width = 15)
    @ApiModelProperty(value = "下单附件")
    private java.lang.String xiadanfj;
	/**回单附件*/
	@Excel(name = "回单附件", width = 15)
    @ApiModelProperty(value = "回单附件")
    private java.lang.String huidanfj;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String zhuangtai;
	/**下单人名字*/
	@Excel(name = "下单人名字", width = 15)
    @ApiModelProperty(value = "下单人名字")
    private java.lang.String xdrmz;
	/**司机*/
	@Excel(name = "司机", width = 15)
    @ApiModelProperty(value = "司机")
    private java.lang.String siji;
	/**送达时间*/
	@Excel(name = "送达时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "送达时间")
    private java.util.Date sdsj;
	/**预计送达时间*/
	@Excel(name = "预计送达时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "预计送达时间")
    private java.util.Date yjsdsj;
	/**下单备注*/
	@Excel(name = "下单备注", width = 15)
    @ApiModelProperty(value = "下单备注")
    private java.lang.String ywddbz;
	/**客户单号*/
	@Excel(name = "客户单号", width = 15)
    @ApiModelProperty(value = "客户单号")
    private java.lang.String ywkhdh;
	/**回单备注*/
	@Excel(name = "回单备注", width = 15)
    @ApiModelProperty(value = "回单备注")
    private java.lang.String ywhdbz;
	/**送货方式*/
	@Excel(name = "送货方式", width = 15, dicCode = "ys_type")
	@Dict(dicCode = "ys_type")
    @ApiModelProperty(value = "送货方式")
    private java.lang.String hwshfs;
	/**派车备注*/
	@Excel(name = "派车备注", width = 15)
    @ApiModelProperty(value = "派车备注")
    private java.lang.String ywpcbz;
	/**件数*/
	@Excel(name = "件数", width = 15)
    @ApiModelProperty(value = "件数")
    private java.lang.String hwshjs;
	/**装车备注*/
	@Excel(name = "装车备注", width = 15)
    @ApiModelProperty(value = "装车备注")
    private java.lang.String ywzcbz;
	/**运费*/
	@Excel(name = "运费", width = 15)
    @ApiModelProperty(value = "运费")
    private java.lang.String hwyf;
	/**货物总费用*/
	@Excel(name = "货物总费用", width = 15)
    @ApiModelProperty(value = "货物总费用")
    private java.lang.String hwzfy;
	/**卸货费*/
	@Excel(name = "卸货费", width = 15)
    @ApiModelProperty(value = "卸货费")
    private java.lang.String hwxhf;
	/**by1*/
	@Excel(name = "by1", width = 15)
    @ApiModelProperty(value = "by1")
    private java.lang.String by1;
}
