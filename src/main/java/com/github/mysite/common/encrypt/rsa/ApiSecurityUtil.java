package com.github.mysite.common.encrypt.rsa;


import java.io.FileInputStream;
import java.io.InputStream;

public class ApiSecurityUtil {

    public static int    MY_PRIVATE_PKCS1_KEY      = 2;                                                  // 商户PKCS1编码格式的私钥
    public static int    MY_PRIVATE_PKCS8_KEY      = 3;                                                  // 商户PKCS8编码格式的私钥
    public static int    MY_PUBLIC_KEY             = 4;                                                  // 商户公钥

    public static String MY_PRIVATE_PKCS1_KEY_PATH = Parameters.getProperty("MY_PRIVATE_PKCS1_KEY_PATH");
    public static String MY_PRIVATE_PKCS8_KEY_PATH = Parameters.getProperty("MY_PRIVATE_PKCS8_KEY_PATH");
    public static String MY_PUBLIC_KEY_PATH        = Parameters.getProperty("MY_PUBLIC_KEY_PATH");

    public static RSAHelper getRsaHelper(int keyType) throws Exception {
        RSAHelper helper = RSAHelper.getInstance();
        switch (keyType) {
            case 2:
                try (InputStream file = new FileInputStream(MY_PRIVATE_PKCS1_KEY_PATH)) {
                    helper.loadPrivateKeyPEMPKCS1(file);
                }
                break;
            case 3:
                try (InputStream file = new FileInputStream(MY_PRIVATE_PKCS8_KEY_PATH)) {
                    helper.loadPrivateKeyPEM(file);
                }
                break;
            case 4:
                try (InputStream file = new FileInputStream(MY_PUBLIC_KEY_PATH)) {
                    helper.loadPublicKeyPEM(file);
                }
                break;
            default:
                try (InputStream file = new FileInputStream(MY_PUBLIC_KEY_PATH)) {
                    helper.loadPublicKeyPEM(file);
                }
                break;
        }
        return helper;
    }
}
