package com.base.modules.baseUtil;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlUtil {
    private static final Logger m = LoggerFactory.getLogger(SqlUtil.class);
    public static final String a = "select * from ( {0}) sel_tab00 limit {1},{2}";
    public static final String b = "select * from ( {0}) sel_tab00 limit {2} offset {1}";
    public static final String c = "select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}";
    public static final String d = "select * from ( select row_number() over(order by tempColumn) tempRowNumber, * from (select top {1} tempColumn = 0, {0}) t ) tt where tempRowNumber > {2}";
    public static final String e = "select distinct table_name from information_schema.columns where table_schema = {0}";
    public static final String f = "SELECT distinct c.relname AS  table_name FROM pg_class c";
    public static final String g = "select distinct colstable.table_name as  table_name from user_tab_cols colstable";
    public static final String h = "select distinct c.name as  table_name from sys.objects c";
    public static final String i = "select column_name from information_schema.columns where table_name = {0} and table_schema = {1}";
    public static final String j = "select table_name from information_schema.columns where table_name = {0}";
    public static final String k = "select column_name from all_tab_columns where table_name ={0}";
    public static final String l = "select name from syscolumns where id={0}";

    public SqlUtil() {
    }

    public static String a(String var0, Map var1) {
        StringBuilder var2 = new StringBuilder();
        var2.append("SELECT t.* FROM ( ");
        var2.append(var0 + " ");
        var2.append(") t ");
        if (var1 != null && var1.size() >= 1) {
            var2.append("WHERE 1=1  ");
            Iterator var3 = var1.keySet().iterator();

            while(var3.hasNext()) {
                String var4 = String.valueOf(var3.next());
                String var5 = String.valueOf(var1.get(var4));
                if (oConvertUtils.isNotEmpty(var5)) {
                    var2.append(" AND ");
                    var2.append(" " + var4 + var5);
                }
            }
        }

        return var2.toString();
    }

    public static String b(String var0, Map var1) {
        String var2 = a(var0, var1);
        var2 = "SELECT COUNT(*) COUNT FROM (" + var2 + ") t2";
        return var2;
    }

    public static String a(String var0, Map var1, int var2, int var3) {
        String var4 = "jdbc:mysql://127.0.0.1:3306/base-boot?characterEncoding=UTF-8&useUnicode=true&useSSL=false";
        int var5 = (var2 - 1) * var3;
        String[] var6 = new String[]{var0, var5 + "", var3 + ""};
        if (var4.indexOf("MYSQL") != -1) {
            var0 = MessageFormat.format("select * from ( {0}) sel_tab00 limit {1},{2}", var6);
        } else if (var4.indexOf("POSTGRESQL") != -1) {
            var0 = MessageFormat.format("select * from ( {0}) sel_tab00 limit {2} offset {1}", var6);
        } else {
            int var7 = (var2 - 1) * var3;
            int var8 = var7 + var3;
            var6[2] = Integer.toString(var7);
            var6[1] = Integer.toString(var8);
            if (var4.indexOf("ORACLE") != -1) {
                var0 = MessageFormat.format("select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}", var6);
            } else if (var4.indexOf("SQLSERVER") != -1) {
                var6[0] = var0.substring(c(var0));
                var0 = MessageFormat.format("select * from ( select row_number() over(order by tempColumn) tempRowNumber, * from (select top {1} tempColumn = 0, {0}) t ) tt where tempRowNumber > {2}", var6);
            }
        }

        return var0;
    }

    public static String a(String var0, String var1, String var2, Map var3, int var4, int var5) {
        var2 = a(var2, var3);
        int var6 = (var4 - 1) * var5;
        String[] var7 = new String[]{var2, var6 + "", var5 + ""};
        String var8 = "";
        if ("MYSQL".equalsIgnoreCase(var8)) {
            var2 = MessageFormat.format("select * from ( {0}) sel_tab00 limit {1},{2}", var7);
        } else if ("POSTGRESQL".equalsIgnoreCase(var8)) {
            var2 = MessageFormat.format("select * from ( {0}) sel_tab00 limit {2} offset {1}", var7);
        } else {
            int var9 = (var4 - 1) * var5;
            int var10 = var9 + var5;
            var7[2] = Integer.toString(var9);
            var7[1] = Integer.toString(var10);
            if ("ORACLE".equalsIgnoreCase(var8)) {
                var2 = MessageFormat.format("select * from (select row_.*,rownum rownum_ from ({0}) row_ where rownum <= {1}) where rownum_>{2}", var7);
            } else if ("SQLSERVER".equalsIgnoreCase(var8)) {
                var7[0] = var2.substring(c(var2));
                var2 = MessageFormat.format("select * from ( select row_number() over(order by tempColumn) tempRowNumber, * from (select top {1} tempColumn = 0, {0}) t ) tt where tempRowNumber > {2}", var7);
            }
        }

        return var2;
    }

    private static int c(String var0) {
        int var1 = var0.toLowerCase().indexOf("select");
        int var2 = var0.toLowerCase().indexOf("select distinct");
        return var1 + (var2 == var1 ? 15 : 6);
    }

    public static String a(String var0, String... var1) {
        if (oConvertUtils.isNotEmpty(var0)) {
            if ("MYSQL".equals(var0)) {
                return MessageFormat.format("select distinct table_name from information_schema.columns where table_schema = {0}", var1);
            }

            if ("ORACLE".equals(var0)) {
                return "select distinct colstable.table_name as  table_name from user_tab_cols colstable";
            }

            if ("POSTGRESQL".equals(var0)) {
                return "SELECT distinct c.relname AS  table_name FROM pg_class c";
            }

            if ("SQLSERVER".equals(var0)) {
                return "select distinct c.name as  table_name from sys.objects c";
            }
        }

        return null;
    }

    public static String b(String var0, String... var1) {
        if (oConvertUtils.isNotEmpty(var0)) {
            if ("MYSQL".equals(var0)) {
                return MessageFormat.format("select column_name from information_schema.columns where table_name = {0} and table_schema = {1}", var1);
            }

            if ("ORACLE".equals(var0)) {
                return MessageFormat.format("select column_name from all_tab_columns where table_name ={0}", var1);
            }

            if ("POSTGRESQL".equals(var0)) {
                return MessageFormat.format("select table_name from information_schema.columns where table_name = {0}", var1);
            }

            if ("SQLSERVER".equals(var0)) {
                return MessageFormat.format("select name from syscolumns where id={0}", var1);
            }
        }

        return null;
    }

    public static String a(String var0) {
        var0 = var0.toLowerCase();
        String var1 = "(\\w|\\.)+ *\\S+ *\\S*\\$\\{\\w+\\}\\S*";
        Pattern var2 = Pattern.compile(var1);

        for(Matcher var3 = var2.matcher(var0); var3.find(); m.info("${}替换后结果 ==>" + var0)) {
            String var4 = var3.group();
            m.info("${}匹配带参SQL片段 ==>" + var4);
            if (var4.indexOf("where") != -1) {
                var0 = var0.replace(var4, "where 1=1");
            } else if (var4.indexOf("and") != -1) {
                var0 = var0.replace(var4, "and 1=1");
            } else if (var4.indexOf("or") != -1) {
                var0 = var0.replace(var4, "or 1=1");
            } else {
                var0 = var0.replace(var4, "1=1");
            }
        }

        var0 = var0.replaceAll("(?i)AND *1=1", "");
        return var0;
    }

    public static void main(String[] args) {
        String var1 = "select * from sys_user where id   ='${id}' and del_flag=  ${flag}";
        System.out.println(a(var1));
    }

    public static Map<String, Object> a(HttpServletRequest var0) {
        Map var1 = var0.getParameterMap();
        HashMap var2 = new HashMap(1024);
        Iterator var3 = var1.entrySet().iterator();
        String var5 = "";
        String var6 = "";

        for(Object var7 = null; var3.hasNext(); var2.put(var5, var6)) {
            Entry var4 = (Entry)var3.next();
            var5 = (String)var4.getKey();
            var7 = var4.getValue();
            if (!"_t".equals(var5) && null != var7) {
                if (!(var7 instanceof String[])) {
                    var6 = var7.toString();
                } else {
                    String[] var8 = (String[])((String[])var7);

                    for(int var9 = 0; var9 < var8.length; ++var9) {
                        var6 = var8[var9] + ",";
                    }

                    var6 = var6.substring(0, var6.length() - 1);
                }
            } else {
                var6 = "";
            }
        }

        return var2;
    }

    public static String b(String var0) {
        String var1 = QueryGenerator.convertSystemVariables(var0);
        String var2 = QueryGenerator.getAllConfigAuth();
        return var0.toLowerCase().indexOf("where") > 0 ? var1 + var2 : var1 + " where 1=1  " + var2;
    }
}

