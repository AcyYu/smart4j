package org.smart4j.chapter1.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties文件工具类
 */
public class PropUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtil.class);

    /**
     * 读取文件
     *
     * @param fileName 文件名
     * @return Properties实例对象
     */
    public static Properties loadProps(String fileName) {
        Properties prop = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + "文件未找到!");
            }
            prop = new Properties();
            prop.load(is);
        } catch (IOException e) {
            LOGGER.error("读取properties文件失败", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("关闭文件输入流失败", e);
                }
            }
        }
        return prop;
    }

    /**
     * 获取指定key对应的值，若key不存在，则返回空字符串
     *
     * @param props Properties对象
     * @param key   建
     * @return key对应的值
     */
    public String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    /**
     * 获取指定key对应的值，若key不存在，则返回默认值
     *
     * @param props        Properties对象
     * @param key          建
     * @param defaultValue 默认值
     * @return key对应的值
     */
    public String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.contains(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取数值型属性值(默认值为0)
     *
     * @param props 属性文件
     * @param key   键
     * @return 属性值
     */
    public Integer getInteger(Properties props, String key) {
        return getInteger(props, key, 0);
    }


    /**
     * 获取数值型属性(可指定默认值)
     *
     * @param props        属性文件
     * @param key          键
     * @param defaultValue 默认值
     * @return 属性值
     */
    public Integer getInteger(Properties props, String key, Integer defaultValue) {
        Integer value = defaultValue;
        if (props.contains(key)) {
            value = CastUtil.castInteger(props.getProperty(key));
        }
        return value;
    }

    /**
     * 获取Boolean类型的属性值(默认值为false)
     *
     * @param props 属性文件
     * @param key   键
     * @return 属性值
     */
    public Boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * 获取Boolean类型的属性值(可指定默认值)
     *
     * @param props        属性文件
     * @param key          键
     * @param defaultValue 默认值
     * @return 属性值
     */
    public Boolean getBoolean(Properties props, String key, Boolean defaultValue) {
        Boolean value = defaultValue;
        if (props.contains(key)) {
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
