package org.jeecgframework.core.groovy.impl;

import org.jeecgframework.core.groovy.IScript;
import org.springframework.stereotype.Component;

/**
 * 公式脚本
 */

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
@Component
public class FormulaImpl implements IScript {
    public Double add(Double a, Double b) {
        return a * b;
    }
}