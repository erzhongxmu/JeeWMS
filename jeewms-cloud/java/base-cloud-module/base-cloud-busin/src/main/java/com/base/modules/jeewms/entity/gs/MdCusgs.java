package com.base.modules.jeewms.entity.gs;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.common.aspect.annotation.Dict;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 国声
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
@Data
@TableName("md_cus")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="md_cus对象", description="客户")
@ApiModel(value="md_cus对象", description="供应商")
public class MdCusgs implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人名称*/
	//// @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private String createName;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人名称*/
	// @Excel(name = "更新人名称", width = 15)
    @ApiModelProperty(value = "更新人名称")
    private String updateName;
	/**更新人登录名称*/
    @ApiModelProperty(value = "更新人登录名称")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**所属公司*/
	// @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private String sysCompanyCode;
	/**中文全称*/
	@Excel(name = "中文全称", width = 15)
    @ApiModelProperty(value = "中文全称")
    private String zhongWenQch;
	/**助记码*/
	//@Excel(name = "助记码", width = 15)
    @ApiModelProperty(value = "助记码")
    private String zhuJiMa;
	/**客户简称*/
//	@Excel(name = "客户简称", width = 15)
    @ApiModelProperty(value = "客户简称")
    private String keHuJianCheng;
	/**客户编码*/
	//@Excel(name = "客户编码", width = 15)
    //@ApiModelProperty(value = "客户编码")
    @Excel(name = "编码", width = 15)
    @ApiModelProperty(value = "编码")
    private String keHuBianMa;
	/**客户英文名称*/
	//@Excel(name = "客户英文名称", width = 15)
    @ApiModelProperty(value = "客户英文名称")
    private String keHuYingWen;
	/**曾用企业代码*/
	//@Excel(name = "曾用企业代码", width = 15)
    @ApiModelProperty(value = "曾用企业代码")
    private String zengYongQi;
	/**曾用企业名称*/
	//@Excel(name = "曾用企业名称", width = 15)
    @ApiModelProperty(value = "曾用企业名称")
    private String zengYongQiYe;
	/**计费属性*/
//    @Excel(name = "计费属性", width = 15, dictTable = "ba_kehuzhuangtai", dicText = "kehuzhuangtai_name", dicCode = "kehuzhuangtai_code")
//    @Dict(dictTable = "ba_kehuzhuangtai", dicText = "kehuzhuangtai_name", dicCode = "kehuzhuangtai_code")
//    @ApiModelProperty(value = "计费属性")
    private String keHuZhuangTai;
	/**企业属性*/
	@Excel(name = "企业属性", width = 15, dictTable = "wms_com_type", dicText = "com_type_name", dicCode = "com_type_code")
	@Dict(dictTable = "wms_com_type", dicText = "com_type_name", dicCode = "com_type_code")
    @ApiModelProperty(value = "企业属性")
    private String xingYeFenLei;
	/**客户等级*/
	//@Excel(name = "客户等级", width = 15, dictTable = "ba_com_deg", dicText = "com_deg_name", dicCode = "com_deg_code")
	//@Dict(dictTable = "ba_com_deg", dicText = "com_deg_name", dicCode = "com_deg_code")
    //@ApiModelProperty(value = "客户等级")
//    @Excel(name = "等级", width = 15, dictTable = "ba_com_deg", dicText = "com_deg_name", dicCode = "com_deg_code")
    @Dict(dictTable = "ba_com_deg", dicText = "com_deg_name", dicCode = "com_deg_code")
    @ApiModelProperty(value = "等级")
    private String keHuDengJi;
	/**所属行业*/
	@Excel(name = "所属行业", width = 15, dictTable = "ba_com_classfy", dicText = "classfl_name", dicCode = "classfl_code")
	@Dict(dictTable = "ba_com_classfy", dicText = "classfl_name", dicCode = "classfl_code")
    @ApiModelProperty(value = "所属行业")
    private String suoShuXingYe;
	/**首签日期*/
	//@Excel(name = "首签日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "首签日期")
    private java.util.Date shouQianRiQi;
	/**终止合作时间*/
	//@Excel(name = "终止合作时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "终止合作时间")
    private java.util.Date zhongZhiHeShiJian;
	/**申请时间*/
	//@Excel(name = "申请时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "申请时间")
    private java.util.Date shenQingShiJian;
	/**客户属性*/
//    @Excel(name = "计费属性", width = 15, dictTable = "ba_kehushuxing", dicText = "kehushuxing_name", dicCode = "kehushuxing_code")
    @Dict(dictTable = "ba_kehushuxing", dicText = "kehushuxing_name", dicCode = "kehushuxing_code")
    @ApiModelProperty(value = "计费属性")
    private String keHuShuXing;
	/**归属组织代码*/
	//@Excel(name = "归属组织代码", width = 15)
    @ApiModelProperty(value = "归属组织代码")
    private String guiShuZuZh;
	/**归属省份代码*/
	//@Excel(name = "归属省份代码", width = 15)
    @ApiModelProperty(value = "归属省份代码")
    private String guiShuSheng;
	/**归属市代码*/
	//@Excel(name = "归属市代码", width = 15)
    @ApiModelProperty(value = "归属市代码")
    private String guiShuShiDai;
	/**归属县区代码*/
	//@Excel(name = "归属县区代码", width = 15)
    @ApiModelProperty(value = "归属县区代码")
    private String guiShu;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private String diZhi;
	/**邮政编码*/
	//@Excel(name = "邮政编码", width = 15)
    @ApiModelProperty(value = "邮政编码")
    private String youZhengBianMa;
	/**主联系人*/
	@Excel(name = "主联系人", width = 15)
    @ApiModelProperty(value = "主联系人")
    private String zhuLianXiRen;
	/**电话*/
	//@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private String dianHua;
	/**手机*/
	@Excel(name = "手机", width = 15)
    @ApiModelProperty(value = "手机")
    private String shouJi;
	/**传真*/
	//@Excel(name = "传真", width = 15)
    @ApiModelProperty(value = "传真")
    private String chuanZhen;
	/**Email地址*/
	//@Excel(name = "Email地址", width = 15)
    @ApiModelProperty(value = "Email地址")
    private String emaildiZhi;
	/**网页地址*/
	//@Excel(name = "网页地址", width = 15)
    @ApiModelProperty(value = "网页地址")
    private String wangYeDiZhi;
	/**法人代表*/
	//@Excel(name = "法人代表", width = 15)
    @ApiModelProperty(value = "法人代表")
    private String faRenDaiBiao;
	/**法人身份证号*/
	//@Excel(name = "法人身份证号", width = 15)
    @ApiModelProperty(value = "法人身份证号")
    private String faRenShenFen;
	/**注册资金万元*/
	//@Excel(name = "注册资金万元", width = 15)
    @ApiModelProperty(value = "注册资金万元")
    private String zhuCeZiJin;
	/**币别*/
	//@Excel(name = "币别", width = 15)
    @ApiModelProperty(value = "币别")
    private String biBie;
	/**营业执照号*/
	//@Excel(name = "营业执照号", width = 15)
    @ApiModelProperty(value = "营业执照号")
    private String yingYeZhiZhao;
	/**税务登记证号*/
	//@Excel(name = "税务登记证号", width = 15)
    @ApiModelProperty(value = "税务登记证号")
    private String shuiWuDeng;
	/**组织机构代码证*/
	//@Excel(name = "组织机构代码证", width = 15)
    @ApiModelProperty(value = "组织机构代码证")
    private String zuZhiJiGou;
	/**道路运输经营许可证*/
	//@Excel(name = "道路运输经营许可证", width = 15)
    @ApiModelProperty(value = "道路运输经营许可证")
    private String daoLuYunShu;
	/**主营业务*/
	//@Excel(name = "主营业务", width = 15)
    @ApiModelProperty(value = "主营业务")
    private String zhuYingYeWu;
	/**合作意向*/
	//@Excel(name = "合作意向", width = 15)
    @ApiModelProperty(value = "合作意向")
    private String heYiXiang;
	/**批准机关*/
	//@Excel(name = "批准机关", width = 15)
    @ApiModelProperty(value = "批准机关")
    private String piZhunJiGuan;
	/**批准文号*/
	//@Excel(name = "批准文号", width = 15)
    @ApiModelProperty(value = "批准文号")
    private String piZhunWenHao;
	/**注册日期*/
	//@Excel(name = "注册日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册日期")
    private java.util.Date zhuCeRiQi;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String beiZhu;
	/**联系人1*/
	//@Excel(name = "联系人1", width = 15)
    @ApiModelProperty(value = "联系人1")
    private String zhuLianXiRen1;
	/**电话1*/
	//@Excel(name = "电话1", width = 15)
    @ApiModelProperty(value = "电话1")
    private String dianHua1;

    private String disableStatus;

    /**租户*/
//    // @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;
}
