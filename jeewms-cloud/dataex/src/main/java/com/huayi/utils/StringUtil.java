package com.huayi.utils;

/**
 * 字符串处理及转换工具类
 * @author   cez
 */

public class StringUtil {
	/**
	 * 判断对象是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		boolean flag = true;
		if (str != null && !str.equals("")) {
			if (str.toString().length() > 0) {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}

}
