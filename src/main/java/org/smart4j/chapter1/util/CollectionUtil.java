package org.smart4j.chapter1.util;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 */
public class CollectionUtil {
    /**
     * 判断Collection是否为空
     *
     * @param collection Collection及其子类对象
     * @return 结果
     */
    public static Boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断Collection是否为非空
     *
     * @param collection Collection及其子类对象
     * @return 结果
     */
    public static Boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断Map是否为空
     *
     * @param map Map及其子类
     * @return 结果
     */
    public static Boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    /**
     * 判断Map是否为非空
     *
     * @param map Map及其子类
     * @return 结果
     */
    public static Boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
