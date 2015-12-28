package com.github.mysite.common.payonline.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * description: 配置文件
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-11-30 20:20
 */
public final class SettingUtils implements SettingConstants {


    private final static Log LOG                = LogFactory.getLog(SettingUtils.class);

    private static final Properties DRIVER_URL_MAPPING = new Properties();

    static {
        try {
            ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
            if (ctxClassLoader != null) {
                for (Enumeration<URL> e = ctxClassLoader.getResources("setting.properties"); e.hasMoreElements();) {
                    URL url = e.nextElement();

                    Properties property = new Properties();

                    InputStream is = null;
                    try {
                        is = url.openStream();
                        property.load(is);
                    } finally {
                        IOUtils.closeQuietly(is);
                    }

                    DRIVER_URL_MAPPING.putAll(property);
                }
            }
        } catch (Exception e) {
            LOG.error("load setting.properties error", e);
        }
    }

    public static String getProperty(String key) {
        return (String) DRIVER_URL_MAPPING.get(key);
    }

}
