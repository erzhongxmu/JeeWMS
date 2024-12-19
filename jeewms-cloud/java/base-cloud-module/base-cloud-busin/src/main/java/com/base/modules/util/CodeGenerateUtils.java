package com.base.modules.util;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;

import java.util.Random;

/**
 * @Package com.base.modules.util
 * @date 2021/6/8 15:55
 * @description
 */
public class CodeGenerateUtils {

    public static final String WARE_HOUSE = "wareHouse";
    public static final String TRAY = "tray";

    public static String generateCode(String type) {
        if (StringUtils.isEmpty(type)) {
            throw new JeecgBootException("type为空!");
        }
        long time = System.currentTimeMillis();
        switch (type) {
            case WARE_HOUSE:
                return "WH"+time;
            case TRAY:
                return "TP"+time+String.format("%02d", new Random().nextInt(99));
            default:
                throw new JeecgBootException("type错误");
        }
    }
}
