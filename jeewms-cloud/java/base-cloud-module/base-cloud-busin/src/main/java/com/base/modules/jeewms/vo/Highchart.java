package com.base.modules.jeewms.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Highchart implements Serializable {
    private String name;
    private String type;//类型
    private List data;//数据
}
