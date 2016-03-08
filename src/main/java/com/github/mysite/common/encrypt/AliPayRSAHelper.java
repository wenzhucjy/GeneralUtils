package com.github.mysite.common.encrypt;

import com.github.mysite.common.encrypt.rsa.Parameters;
import com.github.mysite.common.encrypt.rsa.RSAHelper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * description: 支付宝—— RSA加解密工具类
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-08 19:48
 */
public class AliPayRSAHelper {

    /**
     * RSA签名
     *
     * @param content 待签名数据
     * @param inputCharset 编码格式
     * @return 签名值
     */
    public static String sign(String content, String inputCharset) {
        return sign(content, inputCharset, false);
    }

    /**
     * RSA签名
     *
     * @param content 待签名数据
     * @param inputCharset 编码格式
     * @param needURLEncode 是否需要URLEncode
     * @return 签名值
     */
    public static String sign(String content, String inputCharset, boolean needURLEncode) {

        RSAHelper helper = RSAHelper.getInstance();
        try (InputStream file = new FileInputStream(Parameters.getProperty("MY_PRIVATE_PKCS8_KEY_PATH"))) {
            helper.loadPrivateKeyPEM(file);

            String sign = helper.signSHA1WithRSA(content, inputCharset);
            // 若需要URLEncode，则进行URLEncode
            if (needURLEncode) {
                return URLEncoder.encode(sign, inputCharset);
            }
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA验签名检查
     *
     * @param content 待签名数据
     * @param sign 签名值
     * @param aliPublicKey 支付宝公钥
     * @param inputCharset 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String aliPublicKey, String inputCharset) {
        try {
            RSAHelper helper = RSAHelper.getInstance();
            helper.loadPublicKeyPEM(aliPublicKey);
            return helper.doCheckSHA1WithRSA(content, sign, inputCharset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解密
     *
     * @param content 密文
     * @param privateKey 商户私钥
     * @param inputCharSet 编码格式
     * @return 解密后的字符串
     */
    public static String decrypt(String content, String privateKey, String inputCharSet) throws Exception {

        RSAHelper helper = RSAHelper.getInstance();
        try {
            helper.loadPrivateKeyPEM(privateKey);
            return helper.decryptByPrivateKey(content, inputCharSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
