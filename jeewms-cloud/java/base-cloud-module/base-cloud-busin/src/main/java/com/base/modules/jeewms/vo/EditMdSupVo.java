package com.base.modules.jeewms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.base.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Describe : 入库单审核编辑
 * @Author: zly
 * @Create Date: 2021-05-18
 */
@Data
public class EditMdSupVo implements Serializable {
    private static final long serialVersionUID = 1132240495745740305L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "企业属性")
    private String xingYeFenLei;

    @ApiModelProperty(value = "供应商编码")
    private String gysBianMa;

    @ApiModelProperty(value = "中文全称")
    private String zhongWenQch;

    @ApiModelProperty(value = "地址")
    private String diZhi;

    @ApiModelProperty(value = "主联系人")
    private String zhuLianXiRen;

    @ApiModelProperty(value = "电话")
    private String dianHua;

    @ApiModelProperty(value = "手机")
    private String shouJi;

    @ApiModelProperty(value = "Email地址")
    private String emaildiZhi;

    @Excel(name = "停用", width = 15, dicCode = "sf_yn")
    @Dict(dicCode = "sf_yn")
    @ApiModelProperty(value = "停用")
    private java.lang.String gysZhuangTai;

    @ApiModelProperty(value = "备注")
    private String beiZhu;
}
