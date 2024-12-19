package com.base.modules.jeewms.entity.gs;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: wm_im_notice_i
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
@Data
@TableName("wm_im_notice_i")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wm_im_notice_i对象", description="wm_im_notice_i")
public class WmImNoticeIgs implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人登录名称*/
    @ApiModelProperty(value = "创建人登录名称")
    private String createBy;
    @ApiModelProperty(value = "创建人名称")
    private String createName;
	/**创建日期*/
//	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人登录名称*/
    @ApiModelProperty(value = "更新人登录名称")
    private String updateBy;
	/**更新日期*/
//	@Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**到货通知单号*/
//	@Excel(name = "到货通知单号", width = 15,orderNum = "1")
    @ApiModelProperty(value = "到货通知单号")
    private String imNoticeId;
	/**到货通知项目*/
//	@Excel(name = "到货通知项目", width = 15)
    @ApiModelProperty(value = "到货通知项目")
    private String imNoticeItem;
	/**商品编码*/
	@Excel(name = "商品编码", width = 15,orderNum = "1")
    @ApiModelProperty(value = "商品编码")
    private String goodsCode;
	/**数量*/
	@Excel(name = "数量", width = 15,orderNum = "2")
    @ApiModelProperty(value = "数量")
    private String goodsCount;
	/**生产日期*/
//	@Excel(name = "生产日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "生产日期")
    private Date goodsPrdData;
	/**批次*/
//	@Excel(name = "批次", width = 15,orderNum = "8")
    @ApiModelProperty(value = "批次")
    private String goodsBatch;
	/**库位整理*/
	//@Excel(name = "库位整理", width = 15)
    @ApiModelProperty(value = "库位整理")
    private String binPre;
	/**体积*/
	//@Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private String goodsFvol;
	/**重量*/
	//@Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
    private String goodsWeight;
	/**计划库位*/
//	@Excel(name = "计划库位", width = 15,orderNum = "7")
    @ApiModelProperty(value = "计划库位")
    private String binPlan;
	/**单位*/
	@Excel(name = "单位", width = 15,orderNum = "3")
    @ApiModelProperty(value = "单位")
    private String goodsUnit;
	/**未清数量*/
	//@Excel(name = "未清数量", width = 15)
    @ApiModelProperty(value = "未清数量")
    private String goodsWqmCount;
	/**收货登记数量*/
	@Excel(name = "收货登记数量", width = 15,orderNum = "4")
    @ApiModelProperty(value = "收货登记数量")
    private String goodsQmCount;
	/**行项目状态*/
	//@Excel(name = "行项目状态", width = 15)
    @ApiModelProperty(value = "行项目状态")
    private String noticeiSta;
	/**基本单位*/
//	@Excel(name = "基本单位", width = 15)
    @ApiModelProperty(value = "基本单位")
    private String baseUnit;
	/**基本单位数量*/
//	@Excel(name = "基本单位数量", width = 15)
    @ApiModelProperty(value = "基本单位数量")
    private String baseGoodscount;
	/**基本单位收货数量*/
	//@Excel(name = "基本单位收货数量", width = 15)
    @ApiModelProperty(value = "基本单位收货数量")
    private String baseQmcount;
	/**goodsName*/
	@Excel(name = "商品名称", width = 15,orderNum = "0")
    @ApiModelProperty(value = "goodsName")
    private String goodsName;
	/**otherId*/
//	@Excel(name = "其他系统行项目编号", width = 15)
    @ApiModelProperty(value = "otherId")
    private String otherId;
	/**imCusCode*/
//	@Excel(name = "行项目订单号", width = 15)
    @ApiModelProperty(value = "imCusCode")
    private String imCusCode;
	/**imBeizhu*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "imBeizhu")
    private String imBeizhu;
	/**barcode*/
//	@Excel(name = "商品条码", width = 15)
    @ApiModelProperty(value = "barcode")
    private String barcode;
	/**规格*/
	//@Excel(name = "规格", width = 15)
    @ApiModelProperty(value = "规格")
    private String shpGuiGe;
	/**bzhiQi*/
	//@Excel(name = "bzhiQi", width = 15)
    @ApiModelProperty(value = "bzhiQi")
    private String bzhiQi;
	/**产品属性*/
	//@Excel(name = "产品属性", width = 15)
    @ApiModelProperty(value = "产品属性")
    private String chpShuXing;
	/**tinId*/
	//@Excel(name = "托盘", width = 15)
    @ApiModelProperty(value = "托盘")
    private String tinId;

    @ApiModelProperty(value = "tinId")
//    @Excel(name = "货主编码", width = 15,orderNum = "6")
    @Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    private String cusCode;

    @TableField(exist = false)
    private String goodsTypeName;
    @TableField(exist = false)
    private String goodsTypeId;

    /**出厂包装-SNP-箱数*/
    @ApiModelProperty(value = "出厂包装-SNP-箱数")
    @TableField(exist = false)
    private String factorySnpCaseNum;
    /**出厂包装-SNP-包数*/
    @ApiModelProperty(value = "出厂包装-SNP-包数")
    @TableField(exist = false)
    private String factorySnpPackageNum;
    /**出厂包装-SNP-件数*/
    @ApiModelProperty(value = "出厂包装-SNP-件数")
    @TableField(exist = false)
    private String factorySnpPieceNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(exist = false)
    private String tuoNum;

    @TableField(exist = false)
    private  String recommendBinId;

    @ApiModelProperty("采购单价")
    private BigDecimal unitPrice;

    private String u8Cgid;
    private String u8Dhid;
    private String u8Sta;
    /**租户*/
//    // @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;


    //国声字段
//    @Excel(name = "应收数量", width = 15,orderNum = "3")
    @ApiModelProperty(value = "应收数量")
    private String mustqty;

//    @Excel(name = "收料通知单号", width = 15,orderNum = "3")
    @ApiModelProperty(value = "收料通知单号")
    private String contractlno;

    //国声字段

    //生产订单编号
    private String procode;
    //单据创建人
    private String ucreatename;
    //退料时间
    private String rettime;
    //库存状态
    private String kucunstatus;
    //申请数量
    private String shenqingsl;



    //验货类型
//    @Excel(name = "验货类型", width = 15)
    private String totalamountvat;
    private String avgunitprice;
    //出库日期
//    @Excel(name = "发货日期", width = 15)
    @ApiModelProperty(value = "出库日期")
    private String chukudate;

    //总价
//    private String procode;
    //  po号：private String contractlno;

    //不含税总价
    private String totalamout;
    //开票品名
    private String billingproductname;
    //物料英文名称
//    @Excel(name = "英文名称", width = 15)
    private String ywMingCheng;

    @TableField(exist = false)
    private String shpmiaoshu;
    @TableField(exist = false)
    private String cusName;
    @TableField(exist = false)
    private String supName;
    @TableField(exist = false)
    private String diZhi;
    @TableField(exist = false)
    private String zhuLianXiRen;
    @TableField(exist = false)
    private String xingYeFenLei;
    @TableField(exist = false)
    private String shouJi;
    @TableField(exist = false)
    private String purchasename;

}
