package org.smart4j.chapter1.util;


/**
 * 类型转换工具类
 */
public class CastUtil {

    /**
     * 装换为String类型(可指定默认值)
     *
     * @param obj          原始数据
     * @param defaultValue 默认值
     * @return 字符数据
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 装换为String类型(默认值为空字符串)
     *
     * @param obj 原始数据
     * @return 字符数据
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 装换为Double类型(可指定默认值)
     *
     * @param obj          原始数据
     * @param defaultValue 默认值
     * @return Double数据
     */
    public static Double castDouble(Object obj, Double defaultValue) {
        Double value = defaultValue;
        if (obj != null) {
            String s = castString(obj);
            if (StringUtil.isNotEmpty(s)) {
                try {
                    value = Double.parseDouble(s);
                } catch (Exception e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 装换为Double类型(默认值0)
     *
     * @param obj 原始数据
     * @return Double数据
     */
    public static Double castDouble(Object obj) {
        return castDouble(obj, 0.0);
    }


    /**
     * 装换为Long类型(可指定默认值)
     *
     * @param obj          原始数据
     * @param defaultValue 默认值
     * @return Long数据
     */
    public static Long castLong(Object obj, Long defaultValue) {
        Long value = defaultValue;
        String s = castString(obj);
        if (StringUtil.isNotEmpty(s)) {
            try {
                value = Long.parseLong(s);
            } catch (Exception e) {
                value = defaultValue;
            }
        }
        return value;
    }

    /**
     * 装换为Long类型(默认值0)
     *
     * @param obj 原始数据
     * @return Long数据
     */
    public static Long castLong(Object obj) {
        return castLong(obj, 0L);
    }

    /**
     * 装换为Integer类型(可指定默认值)
     *
     * @param obj          原始数据
     * @param defaultValue 默认值
     * @return 整数数据
     */
    public static Integer castInteger(Object obj, Integer defaultValue) {
        Integer value = defaultValue;
        if (obj != null) {
            String string = castString(obj);
            if (StringUtil.isNotEmpty(string)) {
                try {
                    value = Integer.parseInt(string);
                } catch (Exception e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 装换为Integer类型(默认值为0)
     *
     * @param obj 原始数据
     * @return 整数数据
     */
    public static Integer castInteger(Object obj) {
        return castInteger(obj, 0);
    }

    /**
     * 装换为Boolean类型(可指定默认值)
     *
     * @param obj          原始数据
     * @param defaultValue 默认值
     * @return Boolean数据
     */
    public static Boolean castBoolean(Object obj, Boolean defaultValue) {
        Boolean value = defaultValue;
        if (obj != null) {
            String s = castString(obj);
            if (StringUtil.isNotEmpty(s)) {
                try {
                    value = Boolean.parseBoolean(s);
                } catch (Exception e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 装换为Boolean类型(默认值false)
     *
     * @param obj 原始数据
     * @return Boolean数据
     */
    public static Boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }
}
