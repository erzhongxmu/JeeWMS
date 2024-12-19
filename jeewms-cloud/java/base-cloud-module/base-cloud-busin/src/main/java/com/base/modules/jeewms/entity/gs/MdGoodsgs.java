package com.base.modules.jeewms.entity.gs;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.base.common.aspect.annotation.Dict;
import com.base.modules.jeewms.entity.MdGoodsItem;
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
import java.util.List;

/**
 * @Description: 商品
 * @Author: base-boot
 * @Date: 2021-06-07
 * @Version: V1.0
 */
@Data
@TableName("md_goods")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "md_goods对象", description = "商品")
public class MdGoodsgs implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 创建人名称
     */
    //// @Excel(name = "创建人名称", width = 15)
    @ApiModelProperty(value = "创建人名称")
    private String createName;
    /**
     * 创建人登录名称
     */
    @ApiModelProperty(value = "创建人登录名称")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人名称
     */
    //// @Excel(name = "更新人名称", width = 15)
    @ApiModelProperty(value = "更新人名称")
    private String updateName;
    /**
     * 更新人登录名称
     */
    @ApiModelProperty(value = "更新人登录名称")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 所属公司
     */
    //// @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private String sysCompanyCode;
    /**
     * 所属客户
     */
    @Excel(name = "货主编号", width = 15)
    @Dict(dictTable = "md_cus", dicText = "zhong_wen_qch", dicCode = "ke_hu_bian_ma")
    @ApiModelProperty(value = "所属客户")
    private String suoShuKeHu;
    /**
     * 商品名称
     */
    @Excel(name = "商品名称", width = 15, orderNum = "2")
    @ApiModelProperty(value = "商品名称")
    private String shpMingCheng;
    /**
     * 商品简称
     */
    //@Excel(name = "商品简称", width = 15)
    @ApiModelProperty(value = "商品简称")
    private String shpJianCheng;
    /**
     * 商品编码
     */
    @Excel(name = "商品编码", width = 15, orderNum = "1")
    @ApiModelProperty(value = "商品编码")
    private String shpBianMa;
    /**
     * 商品型号
     */
//    @Excel(name = "商品型号", width = 15, orderNum = "3")
    @ApiModelProperty(value = "商品型号")
    private String shpXingHao;
    /**
     * 商品规格
     */
    @Excel(name = "商品规格", width = 15, orderNum = "4")
    @ApiModelProperty(value = "商品规格")
    private String shpGuiGe;
    /**
     * 商品颜色
     */
//    @Excel(name = "商品颜色", width = 15)
    @ApiModelProperty(value = "商品颜色")
    private String shpYanSe;

    /**
     * 商品颜色编码
     */
//    @Excel(name = "商品颜色编码", width = 15)
    @ApiModelProperty(value = "商品颜色编码")
    private String shpYanSeCode;


    /**
     * 产品属性
     */
    @Excel(name = "产品属性", width = 15)
    @ApiModelProperty(value = "产品属性")
    private String chpShuXing;
    /**
     * 存放温层
     */
    @Excel(name = "存放温层", width = 15)
    @ApiModelProperty(value = "存放温层")
    private String cfWenCeng;
    /**
     * 拆零控制
     */
//    @Excel(name = "拆零控制", width = 15)
    @ApiModelProperty(value = "拆零控制")
    private String chlKongZhi;
    /**
     * 码盘单层数量
     */
    @Excel(name = "码盘单层数量", width = 15)
    @ApiModelProperty(value = "码盘单层数量")
    private String mpDanCeng;
    /**
     * 码盘层高
     */
    @Excel(name = "码盘层高", width = 15)
    @ApiModelProperty(value = "码盘层高")
    private String mpCengGao;
    /**
     * 计费商品类
     */
    @Excel(name = "计费商品类", width = 15)
    @ApiModelProperty(value = "计费商品类")
    private String jfShpLei;
    /**
     * 商品品牌
     */
//    @Excel(name = "商品品牌", width = 15)
    @ApiModelProperty(value = "商品品牌")
    private String shpPinPai;
    /**
     * 商品条码
     */
    @Excel(name = "商品条码", width = 15)
    @ApiModelProperty(value = "商品条码")
    private String shpTiaoMa;
    /**
     * 品牌图片
     */
    //@Excel(name = "品牌图片", width = 15)
    @ApiModelProperty(value = "品牌图片")
    private String ppTuPian;
    /**
     * 保质期
     */
    @Excel(name = "保质期", width = 15)
    @ApiModelProperty(value = "保质期")
    private String bzhiQi;
    /**
     * 单位
     */
    @Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String shlDanWei;
    /**
     * 拆零单位
     */
    @Excel(name = "拆零单位", width = 15)
    @ApiModelProperty(value = "拆零单位")
    private String jshDanWei;
    /**
     * 体积
     */
    @Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private String tiJiCm;
    /**
     * 净重
     */
    @Excel(name = "净重", width = 15)
    @ApiModelProperty(value = "净重")
    private String zhlKg;
    /**
     * 拆零数量
     */
//    @Excel(name = "拆零数量", width = 15)
    @ApiModelProperty(value = "拆零数量")
    private String chlShl;
    /**
     * 件数与体积比
     */
    //@Excel(name = "件数与体积比", width = 15)
    @ApiModelProperty(value = "件数与体积比")
    private String jtiJiBi;
    /**
     * 件数与毛重比
     */
    //@Excel(name = "件数与毛重比", width = 15)
    @ApiModelProperty(value = "件数与毛重比")
    private String jmZhongBi;
    /**
     * 件数与净重比
     */
    //@Excel(name = "件数与净重比", width = 15)
    @ApiModelProperty(value = "件数与净重比")
    private String jjZhongBi;
    /**
     * 尺寸单位
     */
    //@Excel(name = "尺寸单位", width = 15)
    @ApiModelProperty(value = "尺寸单位")
    private String chcDanWei;
    /**
     * 上线包装-长
     */
    //@Excel(name = "上线包装-长", width = 15)
    @ApiModelProperty(value = "上线包装-长")
    private String chDanPin;
    /**
     * 上线包装-宽
     */
    //@Excel(name = "上线包装-宽", width = 15)
    @ApiModelProperty(value = "上线包装-宽")
    private String kuDanPin;
    /**
     * 上线包装-高
     */
    @Excel(name = "价格", width = 15)
    @ApiModelProperty(value = "上线包装-高")
    private String gaoDanPin;
    /**
     * 出厂包装-长
     */
    @Excel(name = "长", width = 15)
    @ApiModelProperty(value = "出厂包装-长")
    private String chZhXiang;
    /**
     * 出厂包装-宽
     */
    @Excel(name = "宽", width = 15, orderNum = "5")
    @ApiModelProperty(value = "出厂包装-宽")
    private String kuZhXiang;
    /**
     * 出厂包装-高
     */
    @Excel(name = "高", width = 15, orderNum = "6")
    @ApiModelProperty(value = "出厂包装-高")
    private String gaoZhXiang;
    /**
     * 商品描述
     */
    //@Excel(name = "商品描述", width = 15)
    @ApiModelProperty(value = "商品描述")
    private String shpMiaoShu;
    /**
     * 停用
     */
//    @Excel(name = "停用", width = 15)
    @ApiModelProperty(value = "停用")
    private String zhuangTai;
    /**
     * 毛重
     */
    @Excel(name = "允收天数", width = 15)
    @ApiModelProperty(value = "允收天数")
    private String zhlKgm;
    /**
     * 供应商编码
     */
    @Excel(name = "货主编号", width = 15, orderNum = "7")
    @Dict(dictTable = "md_sup", dicText = "zhong_wen_qch", dicCode = "gys_bian_ma")
    @ApiModelProperty(value = "供应商编码")
    private String shpBianMakh;
    /**
     * 基准温度
     */
//    @Excel(name = "基准温度", width = 15)
    @ApiModelProperty(value = "基准温度")
    private String jizhunWendu;
    /**
     * 英文名称
     */
    @Excel(name = "英文名称", width = 15)
    @ApiModelProperty(value = "英文名称")
    private String ywMingCheng;
    /**
     * 日文名称
     */
    @Excel(name = "日文名称", width = 15)
    @ApiModelProperty(value = "日文名称")
    private String rwMingCheng;
    /**
     * 供应商名称
     */
//    @Excel(name = "供应商名称", width = 15, orderNum = "8")
    @ApiModelProperty(value = "供应商名称")
    private String cusName;
    /**
     * 配送点
     */
//    @Excel(name = "配送点", width = 15)
    @ApiModelProperty(value = "配送点")
    private String peisongdian;
    /**
     * 上架推荐开关
     */
    //@Excel(name = "上架推荐开关", width = 15)
    @ApiModelProperty(value = "上架推荐开关")
    private String recommend;
    /**
     * 上线包装-是否翻包
     */
//    @Excel(name = "上线包装-是否翻包", width = 15, orderNum = "9")
    @Dict(dicCode = "is_or_not")
    @ApiModelProperty(value = "上线包装-是否翻包")
    private String ifBackpacking;
    /**
     * 出厂包装-SNP
     */
    //@Excel(name = "出厂包装-SNP", width = 15)
    @ApiModelProperty(value = "出厂包装-SNP")
    private String beforeSnp;
    /**
     * 上线包装-SNP
     */
    //@Excel(name = "上线包装-SNP", width = 15)
    @ApiModelProperty(value = "上线包装-SNP")
    private String afterSnp;
    /**
     * 工艺路线
     */
    //@Excel(name = "工艺路线", width = 15)
    @ApiModelProperty(value = "工艺路线")
    private String routing;
    /**
     * Dock管理
     */
    //@Excel(name = "Dock管理", width = 15)
    @ApiModelProperty(value = "Dock管理")
    private String dock;
    /**
     * 库位属性
     */
    //@Excel(name = "库位属性", width = 15)
    @ApiModelProperty(value = "库位属性")
    private String kuWeiShuXing;
    /**
     * 包装页签
     */
    //@Excel(name = "包装页签", width = 15)
    @ApiModelProperty(value = "包装页签")
    private String packingTag;
    /**
     * 客户ID
     */
    //@Excel(name = "客户ID", width = 15)
    @ApiModelProperty(value = "客户ID")
    private String cusId;
    /**
     * 商品名称
     */
    //@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private String partName;
    /**
     * 单位ID
     */
    //@Excel(name = "单位ID", width = 15)
    @ApiModelProperty(value = "单位ID")
    private String orgId;
    /**
     * 商品的重量
     */
    //@Excel(name = "商品的重量", width = 15)
    @ApiModelProperty(value = "商品的重量")
    private String weight;
    /**
     * 体积
     */
    //@Excel(name = "体积", width = 15)
    @ApiModelProperty(value = "体积")
    private String volume;
    /**
     * 商品分类
     */
//    @Excel(name = "商品分类", width = 15, dictTable = "ba_model", dicText = "model_name", dicCode = "model_code", orderNum = "10")
//    @Dict(dictTable = "ba_model", dicText = "model_name", dicCode = "model_code")
//    @ApiModelProperty(value = "商品分类")
    private String model;
    /**
     * 车间
     */
//    @Excel(name = "车间", width = 15, orderNum = "11")
    @ApiModelProperty(value = "车间")
    private String workshop;
    /**
     * BOM中文名称
     */
//    @Excel(name = "BOM中文名称", width = 15, orderNum = "12")
    @ApiModelProperty(value = "BOM中文名称")
    private String bomZw;
    /**
     * 商品分类
     */
    @Excel(name = "商品分类", width = 15, orderNum = "13")
    @Dict(dictTable = "ba_part_type", dicText = "type_name", dicCode = "attr3")
    @ApiModelProperty(value = "商品分类")
    private String classification;
    /**
     * 商品类型
     */
//    @Excel(name = "商品类型", width = 15, orderNum = "14")
    @Dict(dicCode = "packing_type")
    @ApiModelProperty(value = "商品类型")
    private String factoryType;
    /**
     * 上架包装-类型
     */
//    @Excel(name = "上架包装-类型", width = 15, dicCode = "packing_type", orderNum = "15")
    @Dict(dicCode = "packing_type")
    @ApiModelProperty(value = "上架包装-类型")
    private String onlineType;
    /**
     * 存放库区
     */
    //@Excel(name = "存放库位", width = 15)
    @ApiModelProperty(value = "存放库区")
    private String storageArea;
    /**
     * 最大库存量（件）
     */
//    @Excel(name = "最大库存量（件）", width = 15, orderNum = "16")
    @ApiModelProperty(value = "最大库存量（件）")
    private String maxStock;
    /**
     * 上线包装-上线方式
     */
//    @Excel(name = "上线包装-上线方式", width = 15, dicCode = "online_mode", orderNum = "18")
    @Dict(dicCode = "online_mode")
    @ApiModelProperty(value = "上线包装-上线方式")
    private String onlineMode;
    /**
     * 出厂包装-堆码极限
     */
//    @Excel(name = "出厂包装-堆码极限", width = 15, orderNum = "19")
    @ApiModelProperty(value = "出厂包装-堆码极限")
    private String factoryStackingLimit;

    /**
     * 库存上限
     */
    //@Excel(name = "库存上限", width = 15)
    @ApiModelProperty(value = "库存上限")
    private String upLimit;
    /**
     * 库存下限
     */
    //@Excel(name = "库存下限", width = 15)
    @ApiModelProperty(value = "库存下限")
    private String downLimit;
    /**
     * 上架推荐
     */
    //@Excel(name = "上架推荐", width = 15)
    @ApiModelProperty(value = "上架推荐")
    private String upRecommend;
    /**
     * 最小库存
     */
//    @Excel(name = "最小库存量", width = 15, orderNum = "17")
    @ApiModelProperty(value = "最小库存")
    private String minStock;
    /**
     * 出厂包装-SNP-托
     */
    //@Excel(name = "SNP-托", width = 255)
    @Dict(dicCode = "snp_tray")
    @ApiModelProperty(value = "SNP-托")
    private String snpTray;
    /**
     * 出厂包装-SNP-箱
     */
//    @Excel(name = "出厂包装-SNP-箱", width = 15, orderNum = "20")
    @Dict(dicCode = "snp_case")
    @ApiModelProperty(value = "出厂包装-SNP-箱")
    private String factorySnpCase;
    /**
     * 出厂包装-SNP-包
     */
//    @Excel(name = "出厂包装-SNP-包", width = 15, orderNum = "21")
    @Dict(dicCode = "snp_package")
    @ApiModelProperty(value = "出厂包装-SNP-包")
    private String factorySnpPackage;
    /**
     * 出厂包装-SNP-件
     */
//    @Excel(name = "出厂包装-SNP-件", width = 15, orderNum = "22")
    @Dict(dicCode = "snp_piece")
    @ApiModelProperty(value = "出厂包装-SNP-件")
    private String factorySnpPiece;
    /**
     * 上架包装-SNP-箱
     */
    //@Excel(name = "上架包装-SNP-箱", width = 255)
    @Dict(dicCode = "snp_case")
    @ApiModelProperty(value = "上架包装-SNP-箱")
    private String onlineSnpCase;
    /**
     * 上架包装-SNP-包
     */
    //@Excel(name = "上架包装-SNP-包", width = 255)
    @ApiModelProperty(value = "上架包装-SNP-包")
    @Dict(dicCode = "snp_package")
    private String onlineSnpPackage;
    /**
     * 上架包装-SNP-件
     */
    //@Excel(name = "上架包装-SNP-件", width = 255)
    @Dict(dicCode = "snp_piece")
    @ApiModelProperty(value = "上架包装-SNP-件")
    private String onlineSnpPiece;
    /**
     * 出厂包装-SNP-箱数
     */
//    @Excel(name = "出厂包装-SNP-箱数", width = 15, orderNum = "23")
    @ApiModelProperty(value = "出厂包装-SNP-箱数")
    private String factorySnpCaseNum;
    /**
     * 出厂包装-SNP-包数
     */
//    @Excel(name = "出厂包装-SNP-包数", width = 15, orderNum = "24")
    @ApiModelProperty(value = "出厂包装-SNP-包数")
    private String factorySnpPackageNum;
    /**
     * 出厂包装-SNP-件数
     */
//    @Excel(name = "出厂包装-SNP-件数", width = 15, orderNum = "25")
    @ApiModelProperty(value = "出厂包装-SNP-件数")
    private String factorySnpPieceNum;
    /**
     * 上架包装-SNP-箱数
     */
    //@Excel(name = "上架包装-SNP-箱数", width = 255)
    @ApiModelProperty(value = "上架包装-SNP-箱数")
    private String onlineSnpCaseNum;
    /**
     * 上架包装-SNP-包数
     */
    //@Excel(name = "上架包装-SNP-包数", width = 255)
    @ApiModelProperty(value = "上架包装-SNP-包数")
    private String onlineSnpPackageNum;
    /**
     * 上架包装-SNP-件数
     */
    //@Excel(name = "上架包装-SNP-件数", width = 255)
    @ApiModelProperty(value = "上架包装-SNP-件数")
    private String onlineSnpPieceNum;

    @ApiModelProperty("冻结 Y已冻结 N未冻结")
    private String frozen;


    //新增字段
    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("款式")
    private String goodsStyle;

    @ApiModelProperty("仓库")
    private String goodsGranary;

    @ApiModelProperty("警戒补货库存")
    private Integer capacityReplenishmentMin;

    @Dict(dicCode = "snp_status")
    @ApiModelProperty("状态 0暂存，1已确认，9作废")
    private String status;

    @ApiModelProperty("预警天数")
    private Integer warnDays;

    @Dict(dicCode = "is_not")
    @ApiModelProperty("是否包材管理 0 否 1是")
    private String isPackUse;

    @Dict(dicCode = "is_not")
    @ApiModelProperty("是否存在有效期 0 否 1是")
    private String isUstime;

    @Dict(dicCode = "is_not")
    @ApiModelProperty("是否二维码管理 0 否 1是")
    private String isQccodeMgn;

    @ApiModelProperty("拣货考核分类")
    private String packAssessType;

    @Dict(dicCode = "is_not")
    @ApiModelProperty("是否预售")
    private String isPreSales;

    @ApiModelProperty("企业编号")
    private String companyNo;

//    @Excel(name = "海关料号", width = 255)
    @ApiModelProperty("海关料号")
    private String cusNo;

    @ApiModelProperty("售卖单位")
    private String goodsUnit;

    @ApiModelProperty("商品详情")
    private String text;

    @ApiModelProperty("保质期（单位:日)")
    private String dateBzDay;

    @ApiModelProperty("保质期（单位:月）")
    private String dateBz;

    @ApiModelProperty("箱规数量")
    private String boxQty;

    @ApiModelProperty("箱规体积")
    private String boxVolume;

    @ApiModelProperty("箱子重量（kg）")
    private String boxWeight;

    @ApiModelProperty("填充物重量（g）")
    private String fillingWeight;

    @ApiModelProperty("航空禁运标记")
    private String airEmbargo;

    @ApiModelProperty("外部系统料号（sku）")
    private String dnExternal;

    @ApiModelProperty("商品货号(备案序号)")
    private String goodsProductNo;

    @ApiModelProperty("商品生产国")
    private String countryOrigin;

    @ApiModelProperty("内包装简要描述")
    private String matDes;

    @ApiModelProperty("内包装使用介绍url")
    private String matUrls;

    @ApiModelProperty("内包装名称")
    private String matNames;

    @Dict(dicCode = "is_not")
    @ApiModelProperty("是否支持临期品售卖 0-否；1-是")
    private String isAdvent;

    @ApiModelProperty("临期品售卖期 单位/天")
    private Integer adventdays;

    @ApiModelProperty("质量等级")
    private String goodsgrade;

    @ApiModelProperty("贴标标题")
    private String title;

    @ApiModelProperty("贴标文案")
    private String words;

    @Dict(dicCode = "is_label")
    @ApiModelProperty("是否贴标，1=使用  0=不使用")
    private String isLabel;

    @ApiModelProperty("0是默认值，当商品信息更改之后变成1")
    private String isLead;

    @ApiModelProperty("0 已变更，1 未变更")
    private String isUpdateType;

    @Dict(dicCode = "effec")
    @ApiModelProperty("效期区分(1.网易 2.其他) 1.网易的不可修改，2.其他的可以修改")
    private String effectiveDistinguish;

    @ApiModelProperty("耗材，逗号分隔")
    private String packageUseMaterial;

    @Dict(dicCode = "is_not")
    @ApiModelProperty("是否高值品  0 否 1是")
    private String isHighValue;

    @ApiModelProperty("下架天数")
    private String offShelfDays;

    @ApiModelProperty("拒收天数")
    private Integer rejectionDays;

    @ApiModelProperty("安全补货库存")
    private String capacityReplenishmentSafety;

    @ApiModelProperty("法定计量第二单位代码")
    private String extend4;

    @ApiModelProperty("法定数量")
    private String extend5;

    @ApiModelProperty("第二法定数量")
    private String extend6;

    @ApiModelProperty("法定计量单位代码")
    private String extend7;

    @ApiModelProperty("商品类目ID")
    private String categoryId;

    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty("款式编码")
    private String styleCode;

    @ApiModelProperty("供应商商品款号")
    private String supplierStyleCode;

    @ApiModelProperty("年份")
    private String year;

    @ApiModelProperty("执行标准")
    private String implStandards;

    @ApiModelProperty("类别")
    private String category;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("款号")
    private String sectionNumber;

    @ApiModelProperty("季节")
    private String season;

    @ApiModelProperty("面料")
    private String fabric;

    @ApiModelProperty("操作员")
    private String operator;

    @Dict(dicCode = "goods_attr")
    @ApiModelProperty("商品属性1：成品；2半成品3：原材料")
    private String goodsAttr;

    @ApiModelProperty("海关十位数编码")
    private String hsCode;

    @ApiModelProperty("币制")
    private String curr;

    @ApiModelProperty("包材")
    private String packageSp;

    @ApiModelProperty("填充物重量（g）")
    private BigDecimal fillWeight;

    @ApiModelProperty("0 已变更，1 未变更")
    private String type;

    @ApiModelProperty("电商企业编码  电商平台下的商家备案编号")
    private String eCommerceCode;

    @ApiModelProperty("售价")
    private BigDecimal salesPrice;

    @ApiModelProperty("成本价")
    private BigDecimal price;

    @ApiModelProperty("保税类型")
    private String color;

    @ApiModelProperty("系统料号（dn）")
    private String dn;

    /**
     * 商品子表
     */
    @TableField(exist = false)
    List<MdGoodsItem> goodsItems;
    /**租户*/
//    // @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private Integer tenantId;

    /**
     * 商品颜色英文
     */
//    @Excel(name = "商品颜色英文", width = 15)
    @ApiModelProperty(value = "商品颜色英文")
    private String shpYanEnse;


    /**
     * 商品logo
     */
//    @Excel(name = "商品logo", width = 15)
    @ApiModelProperty(value = "商品logo")
    private String shplogo;

}
