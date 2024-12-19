package com.base.common.util.security.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class SecurityResp {
    private Boolean success;
    private JSONObject data;
}
