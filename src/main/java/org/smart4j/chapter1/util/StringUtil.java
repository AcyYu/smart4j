package org.smart4j.chapter1.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Acy的字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空【commons lang的isEmpty不会自动去除空格】
     *
     * @param str 字符串
     * @return 判断结果
     */
    public static Boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否为f非空【commons lang的isNotEmpty不会自动去除空格】
     *
     * @param str 字符串
     * @return 判断结果
     */
    public static Boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
