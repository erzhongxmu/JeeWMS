package com.base.modules.util;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public final class StringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    private static final char[] HEX_LOOKUP_STRING = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
            'd', 'e', 'f' };

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^\\+\\d{1,4}\\-\\d{1,17}|1[3,4,5,7,8]\\d{9}");

    private static final Pattern NICK_PATTERN = Pattern.compile("[\\\\/:*?”<>|;]");

    //手机号码正则表达式
    private static final String MOBILE_PHONE = "^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$";

    private StringUtil() {
    }

    /*****************
     * <P>
     * 对字符串参数进行编码处理
     * </P>
     *
     * @param content
     *            内容
     * @param fromCode
     *            从一种编码
     * @param toCode
     *            转义到编码
     * @param defaultStr
     *            默认输出
     * @return 编码后输出
     */
    public static String encodeStringParameter(final String content, final String fromCode, final String toCode,
                                               final String defaultStr) {
        if (StringUtils.isBlank(content)) {
            return "";
        }
        try {
            return new String(content.getBytes(fromCode), toCode);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException", e);
            return defaultStr;
        }
    }
    public static String defaultNotNullContent( String content){
        if(StringUtil.isBlankOrNull(content)){
            return "";
        }
        return content;
    }

    public static String defaultZoreInsteadOf( String content){
        if(StringUtil.isBlankOrNull(content)){
            return "0";
        }
        return content;
    }

    /**
     * 判断字符串是什么编码的
     *
     * 通过对str 无损的iso-8859-1转码成 二进制数据<br>
     * 对二进制数据用新的编码对其重新编码后，再次返回二进制数据<br>
     * 因为编码转换在一定程度上是有损的<br>
     * 如果返回false，则这个字符串一定不是这个编码的<br>
     * 如果返回true，则这个字符串可能是这个编码的<br>
     *
     * @author gyq
     * @param str
     *            字符串
     * @param codeName
     *            编码名称
     * @return 这个字符串是否是这个编码的
     */
    public static boolean isEncoding(String str, String codeName) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException("str should not empty!");
        }
        if (StringUtils.isBlank(codeName)) {
            throw new IllegalArgumentException("codeName should not empty!");
        }
        try {
            byte[] bytes = str.getBytes(StandardCharsets.ISO_8859_1);
            byte[] bytesEncode = new String(bytes, codeName).getBytes(codeName);
            if (new EqualsBuilder().append(bytes, bytesEncode).isEquals()) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
        return false;
    }

    /*******************
     * 删除HTML的尖括号
     *
     * @param html
     * @return
     */
    public static final String removeHtmlBrackets(final String html) {
        if (html == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(html.length());
        for (int i = 0; i < html.length(); i++) {
            if (html.charAt(i) == '<' || html.charAt(i) == '>') {
                continue;
            }
            sb.append(html.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 取count个char字符。中文、英文都为1个char
     *
     * @param str
     *            被处理字符串
     * @param needDots
     *            在字节数超过时是否加省略号
     */
    public static String substringByCharCount(String str, int charCount, boolean needDots) {
        if (str == null) {
            return "";
        }

        String result = str;
        if (str.length() > charCount) {
            result = str.substring(0, charCount);
            if (needDots) {
                result += "...";
            }
        }

        return result;
    }

    /**
     * 将字节数组转换为16进制字符串的形式.
     */
    public static String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);

        for (byte aBcd : bcd) {
            s.append(HEX_LOOKUP_STRING[(aBcd >>> 4) & 0x0f]);
            s.append(HEX_LOOKUP_STRING[aBcd & 0x0f]);
        }

        return s.toString();
    }

    /**
     * 将16进制字符串还原为字节数组.
     */
    public static byte[] hexStrToBytes(String s) {
        byte[] bytes;

        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }

    public static boolean isValidMobilePhoneFormat(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }

        Matcher m = MOBILE_PATTERN.matcher(s);
        return m.matches();
    }

    public static boolean isValidRegisterYixinNickName(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }

        Matcher m = NICK_PATTERN.matcher(s);
        return !m.find();
    }

    /**
     * 空串或字符串null
     *
     * @param s
     * @return
     */
    public static boolean isBlankOrNull(String s){
        return StringUtils.isBlank(s) || "null".equalsIgnoreCase(s);
    }


    /**
     * 判定给定字符串是否是手机号码
     * @param mobiles 手机号码
     * @return 是否是合法的手机号码
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile(MOBILE_PHONE);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String generateUpperStr(Integer num){
        String str = "ABCDUEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i = 0 ; i < num; i++){
            Integer index = random.nextInt(26);
            sb.append(str.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 截取
     * @param source
     * @param length
     * @return
     */
    public static String subString(String source, int length) {
        StringBuffer buffer = new StringBuffer();
        char[] chars = source.toCharArray();
        char c;
        for (int i = 0;; i++) {
            if (length <= 0) {
                break;
            }
            c = source.charAt(i);
            buffer.append(c);
            length -= String.valueOf(c).getBytes().length;
            if (i + 1 < chars.length) {
                if (String.valueOf(chars[i + 1]).getBytes().length > length) {
                    break;
                };
            }
        }
        return buffer.toString();
    }


    public static Map<String, Object> getProxyPojoValue(Object object, Set<String> key) {
        String id = null;
        // 返回参数
        HashMap<String, Object> hashMap = new HashMap<>(1024);
        for (String s : key) {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                //在类的外面获取此类的私有成员变量的value时需配置 否则会抛异常
                field.setAccessible(true);
                // 获取表名
                TableName table = object.getClass().getAnnotation(TableName.class);
                if (table != null) {
                    String tableName = table.value();
                    //先判断key是否存在，不存在则插入到 HashMap中
                    hashMap.putIfAbsent("tableName", tableName);
                }
                // 获取主键id
                if (id == null) {
                    boolean isIdField = field.isAnnotationPresent(TableId.class);
                    if (isIdField) {
                        TableField tableField = field.getAnnotation(TableField.class);
                        if (s.toLowerCase().equals(field.getName().toLowerCase())) {
                            String tableId = tableField.value();
                            hashMap.put(s, tableId);
                            id = tableId;
                        }
                    }
                }

                // 获取字段的值
                boolean isTableField = field.isAnnotationPresent(TableField.class);
                if (isTableField) {
                    TableField tableField = field.getAnnotation(TableField.class);
                    if (s.toLowerCase().equals(field.getName().toLowerCase())) {
                        String fieldValue = tableField.value();
                        hashMap.put(s, fieldValue);
                    }
                }
            }
        }
        return hashMap;
    }

    public static String leftPad(int num, final int maxLen, char filledChar) {
        StringBuffer sb = new StringBuffer();
        String str = String.valueOf(num);
        for(int i = str.length(); i < maxLen; i++){
            sb.append(filledChar);
        }
        return sb.append(str).toString();
    }
}
