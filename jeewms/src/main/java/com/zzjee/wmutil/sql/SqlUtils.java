package com.zzjee.wmutil.sql;


import org.jeecgframework.core.util.StringUtil;

import java.util.Map;

public class SqlUtils {

	public static String getAndLike(String sql, String filedName, Object params) {
		if (StringUtil.isNotEmpty(params)) {
			sql += " AND " + filedName + " LIKE '%" + params + "%'\n";
			return sql;
		} else {
			return sql;
		}
	}

	public static String getOrLike(String sql, String filedName, Object params) {
		if (StringUtil.isNotEmpty(params)) {
			sql += " OR " + filedName + " LIKE '%" + params + "%'\n";
			return sql;
		} else {
			return sql;
		}
	}

	public static String getAndEqual(String sql, String filedName, Object params) {
		if (StringUtil.isNotEmpty(params)) {
			sql += " AND " + filedName + " = '" + params + "'\n";
			return sql;
		} else {
			return sql;
		}
	}

	public static String getOrEqual(String sql, String filedName, Object params) {
		if (StringUtil.isNotEmpty(params)) {
			sql += " OR " + filedName + " = '" + params + "'\n";
			return sql;
		} else {
			return sql;
		}
	}

	public static String getIn(String sql, String filedName, Object params) {
		if (StringUtil.isNotEmpty(params)) {
			String tmpParams = params.toString();
			if (tmpParams.indexOf("(") == -1) {
				tmpParams = "(" + tmpParams;
			}
			if (tmpParams.indexOf(")") == -1) {
				tmpParams = tmpParams + ")";
			}
			sql += " AND " + filedName + " IN " + tmpParams + "\n";
			return sql;
		} else {
			return sql;
		}
	}

	public static String getBatchAndLike(String sql, String filedName, String key, Map params) {
		if (StringUtil.isEmpty(filedName) || StringUtil.isEmpty(key)) {
			return sql;
		}
		String[] names = filedName.split(",");
		String[] keys = key.split(",");
		int len = names.length;
		if (len != keys.length) {
			return sql;
		}
		for (int i = 0; i < len; i++) {
			sql = getAndLike(sql, names[i], params.get(keys[i]));
		}
		return sql;
	}

	public static String getBatchAndEqual(String sql, String filedName, String key, Map params) {
		if (StringUtil.isEmpty(filedName) || StringUtil.isEmpty(key)) {
			return sql;
		}
		String[] names = filedName.split(",");
		String[] keys = key.split(",");
		int len = names.length;
		if (len != keys.length) {
			return sql;
		}
		for (int i = 0; i < len; i++) {
			sql = getAndEqual(sql, names[i], params.get(keys[i]));
		}
		return sql;
	}

	public static String getBatchIn(String sql, String filedName, String key, Map params) {
		if (StringUtil.isEmpty(filedName) || StringUtil.isEmpty(key)) {
			return sql;
		}
		String[] names = filedName.split(",");
		String[] keys = key.split(",");
		int len = names.length;
		if (len != keys.length) {
			return sql;
		}
		for (int i = 0; i < len; i++) {
			sql = getIn(sql, names[i], params.get(keys[i]));
		}
		return sql;
	}
}
