package com.base.modules.system.vo;

import com.base.modules.system.entity.BaseScheduleInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author qinfeng
 * @Date 2020/1/2 21:58
 * @Description:
 * @Version 1.0
 */
@Data
public class SysUserDepVo {
    private String userId; // 用户id
    private String departName; // 部门名称
    private String orgCode; // 部门编码
    private String realname; // 用户名
    private String goodsGroup;
    private String confItem;
    private String userNo;  // 用户编号
    private String whNo; // 仓库
    private String tenantId; // 租户
    private String priority; // 优先级
    private String suplyCode; // 供应商编码
    private String binGroup;
    private List<BaseScheduleInfo> scheduleInfo;
}
