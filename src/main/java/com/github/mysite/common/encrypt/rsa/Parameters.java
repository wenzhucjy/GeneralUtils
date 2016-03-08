package com.github.mysite.common.encrypt.rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * description: Parameters
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-07 16:59
 */
public class Parameters {

    /**
     * Logger for Parameters
     */
    private static final Logger LOG           = LoggerFactory.getLogger(Parameters.class);

    public static final String  UMPS_API_FILE = "rsa_config.properties";
    private static Properties   properties    = null;

    static {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl != null) {
                try (Reader inputStream = new InputStreamReader(
                        cl.getResourceAsStream(UMPS_API_FILE),"UTF-8")) {
                        properties = new Properties();
                        properties.load(inputStream);
                }

            }
        } catch (Exception e) {
            LOG.error("load rsa_config.properties error,{}", e);
        }
        Enumeration<?> enumeration = properties.keys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement().toString();
            String value = properties.getProperty(key);
            properties.setProperty(key, wipeOffQuotation(value));
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * 如果传入字符串以'"'双引号开头并结尾，去掉此头尾返回中间；不是，直接返回原始字符串
     *
     * @param value 待处理字符串
     * @return 处理后结果
     */
    private static String wipeOffQuotation(String value) {
        if ((value != null) && (value.length() > 0) && (value.indexOf("\"") == 0)
            && (value.lastIndexOf("\"") == value.length() - 1)) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    /**
     * 根据转入的key值，得到相应的value，并按照‘；’切割为数组
     *
     * @param key key值
     * @return value
     */
    public static String[] getPropertys(String key) {
        String str = properties.getProperty(key);
        return str.split(";");
    }
}
