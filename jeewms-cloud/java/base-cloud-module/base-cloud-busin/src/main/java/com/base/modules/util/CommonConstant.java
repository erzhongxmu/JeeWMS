package com.base.modules.util;

import java.math.BigDecimal;

public interface CommonConstant {




    /** 资源状态*/

    String TABLE_NAME_BMS_BILL = "bms_bill";
    String TABLE_NAME_BMS_INVOICE = "bms_invoice";
    String TABLE_NAME_BMS_PAY = "bms_pay";
    String TABLE_NAME_BMS_BILL_STATUS_0 = "0";//已生成
    String TABLE_NAME_BMS_BILL_STATUS_1 = "1";//已审核
    String TABLE_NAME_BMS_BILL_STATUS_2 = "2";//已发送
    String TABLE_NAME_BMS_BILL_STATUS_3 = "3";//已确认
    String TABLE_NAME_BMS_BILL_STATUS_4 = "4";//已完成
    String TABLE_NAME_BMS_BILL_STATUS_5 = "5";//已开票


    String TASK_DAYPLAN_STATUS_WSC = "未生成";

     String BUSI_DEFAULT_CREATE_BY = "admin";
     String BUSI_DEFAULT_STATUS_1 = "1";

    //默认编码类型
    String BASE_DEFAULT_CODE_TYPE_10 = "10";
    String TABLE_NAME_WMS_TIN = "wms_tin";


}
