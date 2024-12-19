package com.base.modules.jeewms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Package com.base.modules.jeewms.entity
 * @date 2021/5/21 17:35
 * @description
 */
@TableName("mv_goods")
@Data
public class MvGoods {

    /**主键*/
    private java.lang.String id;
    /**cusCode*/
    @Excel(name="cusCode")
    private java.lang.String cusCode;
    /**goodsCode*/
    @Excel(name="goodsCode")
    private java.lang.String goodsCode;
    @Excel(name="goodsId")
    private java.lang.String goodsId;
    /**goodsName*/
    @Excel(name="goodsName")
    private java.lang.String goodsName;
    /**shlDanWei*/
    @Excel(name="shlDanWei")
    private java.lang.String shlDanWei;
    /**cfWenCeng*/
    @Excel(name="cfWenCeng")
    private java.lang.String cfWenCeng;
    /**mpDanCeng*/
    @Excel(name="mpDanCeng")
    private java.lang.String mpDanCeng;
    /**mpCengGao*/
    @Excel(name="mpCengGao")
    private java.lang.String mpCengGao;
    /**shpTiaoMa*/
    @Excel(name="商品条码")
    private java.lang.String shpTiaoMa;
    /**bzhiQi*/
    @Excel(name="bzhiQi")
    private java.lang.String bzhiQi;
    /**chlShl*/
    @Excel(name="chlShl")
    private java.lang.String chlShl;
    /**tiJiCm*/
    @Excel(name="tiJiCm")
    private java.lang.String tiJiCm;
    /**zhlKg*/
    @Excel(name="zhlKg")
    private java.lang.String zhlKg;
    /**baseunit*/
    @Excel(name="baseunit")
    private java.lang.String baseunit;
    private java.lang.String shpMingCheng;

    @Excel(name="商品规格")
    private java.lang.String shpGuiGe;
    /**商品品牌*/
    @Excel(name="商品品牌")
    private java.lang.String shpPinPai;
    @Excel(name="长")
    private java.lang.String chZhXiang;
    /**宽整箱*/
    @Excel(name="宽")
    private java.lang.String kuZhXiang;
    /**高整箱*/
    @Excel(name="高")
    private java.lang.String gaoZhXiang;
    @Excel(name="产品属性")
    private java.lang.String chpShuXing;
}
