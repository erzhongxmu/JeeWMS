package com.base.modules.util;

public interface CommonConstantMSG {

    String BUIS_IM_ADD_MSG = "物料件号(%s)预约数量(%s)不能大于计划差缺数量(%s)";
    String BMS_RULE_MSG = "未查询到计费规则";
    String BASE_CONF_CODE_ADD_MSG = "表名和类型已存在，添加失败！";
    String BASE_CONF_CODE_EDIT_MSG = "表名和类型已存在，修改失败！";
    String BUSI_CANCEL_OM_MSG = "计划出库需从源计划取消！";
    String BUSI_POST_CHECK_GOODS = "生产日期已超近效期";
    String BUSI_POST_CHECK_YUEKU = "越库数量匹配错误";


    String BUSI_PO_IM = "物料(%s)预约数量(%s)不能大于未清数量(%s)";
}
