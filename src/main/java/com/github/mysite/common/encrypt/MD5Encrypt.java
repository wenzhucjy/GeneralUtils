package com.github.mysite.common.encrypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description:MD5加密工具类  —— 签名处理核心文件
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/14 - 14:47
 */
public class MD5Encrypt {

    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(MD5Encrypt.class);

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param key           密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param sign          签名结果
     * @param key           密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws Exception
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (Exception e) {
            LOG.error("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
            throw new RuntimeException(e);
        }
    }
}
