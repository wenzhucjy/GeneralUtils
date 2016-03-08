package com.github.mysite.common.encrypt.rsa;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

/**
 * description: RSA 加解密工具类
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-07 16:59
 */
public class RSAHelper {

    /**
     * Logger for MD5withRSAHelper
     */
    private static final Logger LOG                         = LoggerFactory.getLogger(RSAHelper.class);

    private PrivateKey          privateKey;
    private PublicKey           publicKey;
    public static int           RSA_PRIVATE_KEY             = 0;
    public static int           RSA_PUBLIC_KEY              = 1;
    public final String         SIGN_ALGORITHMS             = "RSA";
    public final String         DEFAULT_CHARSET             = "utf-8";
    public final String         MD5WithRSA_SIGN_ALGORITHMS  = "MD5WithRSA";
    public final String         SHA1WithRSA_SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * 类级的内部类，静态的成员内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到时才会被装载，从而实现了延迟加载
     */
    private static class SingletonHolder {

        /**
         * 静态初始化器，由 JVM 来保证线程安全
         */
        private static RSAHelper instance = new RSAHelper();
    }

    /**
     * 私有化构造方法
     */
    private RSAHelper(){

    }

    public static RSAHelper getInstance() {
        return SingletonHolder.instance;
    }

    public void initKeyByIntValue(BigInteger exponent, BigInteger modules, int keyType) throws Exception {
        if (keyType == RSA_PRIVATE_KEY) {
            this.initPrivateKey(exponent, modules);
        } else {
            if (keyType != RSA_PUBLIC_KEY) {
                throw new Exception("密钥类型有误");
            }
            this.initPublicKey(exponent, modules);
        }

    }

    private void initPublicKey(BigInteger exponent, BigInteger modules) throws Exception {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modules, exponent);
        KeyFactory fact = KeyFactory.getInstance(SIGN_ALGORITHMS);
        try {
            this.publicKey = fact.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException var6) {
            throw new Exception("公钥获取失败");
        }
    }

    private void initPrivateKey(BigInteger exponent, BigInteger modules) throws Exception {
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(modules, exponent);
        KeyFactory fact = KeyFactory.getInstance(SIGN_ALGORITHMS);
        try {
            this.privateKey = fact.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException var6) {
            throw new Exception("私钥获取失败");
        }
    }

    /**
     * RSA 私钥解密
     * 
     * @param content 密文
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decryptByPrivateKey(String content) throws Exception {
        return decryptByPrivateKey(content, DEFAULT_CHARSET);
    }

    /**
     * RSA 私钥解密
     *
     * @param content 密文
     * @param inputCharset 编码格式
     * @return 解密后的字符串
     */
    public String decryptByPrivateKey(String content, String inputCharset) throws Exception {
        Cipher cipher = Cipher.getInstance(SIGN_ALGORITHMS);
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block;
            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                System.arraycopy(buf, 0, block, 0, bufl);
            }
            writer.write(cipher.doFinal(block));
        }
        return new String(writer.toByteArray(), inputCharset);
    }

    /**
     * RSA 公钥加密
     * 
     * @param data 待加密数据
     * @return 加密结果
     * @throws Exception
     */
    public byte[] encryptByPublicKey(byte[] data) throws Exception {
        if (this.publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        } else {
            Cipher cipher;
            try {
                cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", new BouncyCastleProvider());
                cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
                int e = cipher.getBlockSize();
                int outputSize = cipher.getOutputSize(data.length);
                int leavedSize = data.length % e;
                int blocksSize = leavedSize != 0 ? data.length / e + 1 : data.length / e;
                byte[] raw = new byte[outputSize * blocksSize];

                for (int i = 0; data.length - i * e > 0; ++i) {
                    if (data.length - i * e > e) {
                        cipher.doFinal(data, i * e, e, raw, i * outputSize);
                    } else {
                        cipher.doFinal(data, i * e, data.length - i * e, raw, i * outputSize);
                    }
                }
                return raw;
            } catch (NoSuchAlgorithmException var9) {
                throw new Exception("无此加密算法");
            } catch (NoSuchPaddingException var10) {
                throw new Exception(var10.getMessage());
            } catch (InvalidKeyException var11) {
                throw new Exception("加密公钥非法,请检查");
            } catch (IllegalBlockSizeException var12) {
                throw new Exception("明文长度非法");
            } catch (BadPaddingException var13) {
                throw new Exception("明文数据已损坏");
            }
        }
    }

    /**
     * RSA 私钥解密
     * 
     * @param data 待解密数据
     * @return 解密结果
     * @throws Exception
     */
    public byte[] decryptByPrivateKey(byte[] data) throws Exception {
        if (this.privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        } else {
            Cipher cipher;
            try {
                cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", new BouncyCastleProvider());
                cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
                int e = cipher.getBlockSize();
                ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
                for (int j = 0; data.length - j * e > 0; ++j) {
                    bout.write(cipher.doFinal(data, j * e, e));
                }
                return bout.toByteArray();
            } catch (NoSuchAlgorithmException var6) {
                throw new Exception("无此解密算法");
            } catch (NoSuchPaddingException var7) {
                throw new Exception(var7.getMessage());
            } catch (InvalidKeyException var8) {
                throw new Exception("解密私钥非法,请检查");
            } catch (IllegalBlockSizeException var9) {
                throw new Exception("密文长度非法");
            } catch (BadPaddingException var10) {
                throw new Exception("密文数据已损坏");
            }
        }
    }

    /**
     * 随机生成密钥对
     */
    public void genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(SIGN_ALGORITHMS);
            if (keyPairGen != null) {
                keyPairGen.initialize(1024, new SecureRandom());
                KeyPair keyPair = keyPairGen.generateKeyPair();
                this.privateKey = keyPair.getPrivate();
                this.publicKey = keyPair.getPublic();
            }
        } catch (Exception e) {
            throw new Exception("随机生成密钥对错误");
        }
    }

    /**
     * 加载 pem 格式的公钥
     * 
     * @param in InputStream
     * @throws Exception
     */
    public void loadPublicKeyPEM(InputStream in) throws Exception {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(in));
            String e;
            StringBuilder sb = new StringBuilder();

            while ((e = br.readLine()) != null) {
                /* 去掉前后 -----BEGIN PUBLIC KEY----- 与 -----END PUBLIC KEY----- */
                if (e.charAt(0) != 45) {
                    sb.append(e);
                    sb.append('\r');
                }
            }
            this.loadPublicKeyPEM(sb.toString());
        } catch (IOException var9) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException var10) {
            throw new Exception("公钥输入流为空");
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    /**
     * 加载 pem 格式的公钥
     * 
     * @param publicKeyStr 公钥
     * @throws Exception
     */
    public void loadPublicKeyPEM(String publicKeyStr) throws Exception {
        try {
            byte[] e = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_ALGORITHMS);
            // 加载公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(e);
            // 获取公钥
            this.publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException var5) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException var6) {
            throw new Exception("公钥非法");
        } catch (NullPointerException var7) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 加载 crt 格式的公钥文件
     * 
     * @param in InputStream
     * @throws Exception
     */
    public void loadPublicKeyCRT(InputStream in) throws Exception {
        try {
            // 生成一个实现指定证书类型的 CertificateFactory 对象
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // 从证书文件*.crt里读取证书信息
            X509Certificate e = (X509Certificate) cf.generateCertificate(in);
            // 获取公钥对象
            this.publicKey = e.getPublicKey();
        } catch (CertificateException var4) {
            throw new Exception("证书加载失败");
        }
    }

    /**
     * 加载 PKCS#8 编码的 pem 格式私钥文件
     * 
     * @param in InputStream
     * @throws Exception
     */
    public void loadPrivateKeyPEM(InputStream in) throws Exception {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in));
            String e;
            StringBuilder sb = new StringBuilder();

            while ((e = br.readLine()) != null) {
                if (e.charAt(0) != 45) {
                    sb.append(e);
                    sb.append('\r');
                }
            }
            // while((e= br.readLine())!=null){
            // if(e.charAt(0)=='-'){
            // continue;
            // }else{
            // sb.append(e);
            // sb.append('\r');
            // }
            // }
            this.loadPrivateKeyPEM(sb.toString());
        } catch (IOException var9) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException var10) {
            throw new Exception("私钥输入流为空");
        } finally {
            if (br != null) {
                br.close();
            }
        }

    }

    /**
     * 加载 PKCS#1 编码的 pem 格式私钥文件
     * 
     * @param in InputStream
     * @throws Exception
     */
    public void loadPrivateKeyPEMPKCS1(InputStream in) throws Exception {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();

            String e;
            while ((e = br.readLine()) != null) {
                if (e.charAt(0) != 45) {
                    sb.append(e);
                    sb.append('\r');
                }
            }
            this.loadPrivateKeyPEMPKCS1(sb.toString());
        } catch (IOException var9) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException var10) {
            throw new Exception("私钥输入流为空");
        } finally {
            if (br != null) {
                br.close();
            }

        }

    }

    /**
     * 加载 PKCS#1 编码的 pem 格式私钥
     * 
     * @param privateKeyStr 私钥
     * @throws Exception
     */
    public void loadPrivateKeyPEMPKCS1(String privateKeyStr) throws Exception {
        try {
            byte[] e = Base64.decode(privateKeyStr);
            // 读取 PKCS#1的私钥
            RSAPrivateKeyStructure asn1PrivateKey = new RSAPrivateKeyStructure(
                                                                               (ASN1Sequence) ASN1Sequence.fromByteArray(e));
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(asn1PrivateKey.getModulus(),
                                                                        asn1PrivateKey.getPrivateExponent());
            // 实例化KeyFactory对象，并指定 RSA 算法
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_ALGORITHMS);
            // 获得 PrivateKey 对象
            this.privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
        } catch (NoSuchAlgorithmException var6) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException var7) {
            throw new Exception("私钥非法");
        } catch (IOException var8) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException var9) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 加载 PKCS#8 编码的 pem 格式私钥文件
     * 
     * @param privateKeyStr 私钥
     * @throws Exception
     */
    public void loadPrivateKeyPEM(String privateKeyStr) throws Exception {
        try {
            // Base64 解码
            byte[] e = Base64.decode(privateKeyStr);
            // 使用 PKCS#8 标准作为密钥规范管理的编码格式
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(e);
            // 实例化KeyFactory对象，并指定 RSA 算法
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_ALGORITHMS);
            // 获得PrivateKey对象
            this.privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException var5) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException var6) {
            throw new Exception("私钥非法");
        } catch (NullPointerException var7) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 加载 PFX 格式的私钥证书
     *
     * @param in InputStream
     * @param password 密码
     * @throws Exception
     */
    public void loadPrivateKeyPFX(InputStream in, String password) throws Exception {
        /* 添加 BouncyCastleProvider */
        Security.addProvider(new BouncyCastleProvider());
        // 密钥仓库，KeyStore 是一个有效的安全钥匙和证书的管理工具
        KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
        // 证书密码 xxx
        char[] charPassword = null;
        if (StringUtils.isNotBlank(password)) {
            charPassword = password.toCharArray();
        }
        ks.load(in, charPassword);
        String keyAlias = this.getAlsformPfxFile(ks);
        // 从密钥仓库得到私钥
        this.privateKey = (PrivateKey) ks.getKey(keyAlias, charPassword);
    }

    /**
     * MD5WithRSA 算法的rsa签名
     * 
     * @param content 待签名内容
     * @return 签名结果
     * @throws Exception
     */
    public String signMD5WithRSA(String content) throws Exception {
        return sign(content, MD5WithRSA_SIGN_ALGORITHMS, DEFAULT_CHARSET);
    }

    /**
     * MD5WithRSA 算法的rsa签名
     *
     * @param content 待签名内容
     * @param inputCharSet 编码格式
     * @return 签名结果
     * @throws Exception
     */
    public String signMD5WithRSA(String content, String inputCharSet) throws Exception {
        return sign(content, MD5WithRSA_SIGN_ALGORITHMS, inputCharSet);
    }

    /**
     * SHA1WithRSA 算法的rsa签名
     * 
     * @param content 待签名内容
     * @return 签名结果
     * @throws Exception
     */
    public String signSHA1WithRSA(String content) throws Exception {
        return sign(content, SHA1WithRSA_SIGN_ALGORITHMS, DEFAULT_CHARSET);
    }

    /**
     * SHA1WithRSA 算法的rsa签名
     *
     * @param content 待签名内容
     * @param inputCharSet 编码格式
     * @return 签名结果
     * @throws Exception
     */
    public String signSHA1WithRSA(String content, String inputCharSet) throws Exception {
        return sign(content, SHA1WithRSA_SIGN_ALGORITHMS, inputCharSet);
    }

    /**
     * RSA 加密
     * 
     * @param content 待加密内容
     * @param encryptType 加密算法
     * @return 加密结果
     * @throws Exception
     */
    public String sign(String content, String encryptType, String inputCharSet) throws Exception {
        try {
            Signature e = Signature.getInstance(encryptType);
            e.initSign(this.privateKey);
            e.update(content.getBytes(inputCharSet));
            byte[] signed = e.sign();
            return new String(Base64.encode(signed));
        } catch (Exception var5) {
            throw new Exception("签名失败");
        }
    }

    /**
     * 获取私钥pfx证书的key别名
     * 
     * @param ks KeyStore
     * @return keyAlias 结果
     * @throws Exception
     */
    private String getAlsformPfxFile(KeyStore ks) throws Exception {
        String keyAlias;
        /* 私钥证书key别名 */
        Enumeration enumeration = ks.aliases();
        keyAlias = null;
        if (enumeration.hasMoreElements()) {
            keyAlias = (String) enumeration.nextElement();
        }

        return keyAlias;
    }

    /**
     * RSA验签名检查
     *
     * @param content 待签名数据
     * @param sign 签名值
     * @return 布尔值
     */
    public boolean doCheck(String content, String sign) {
        return doCheck(content, sign, DEFAULT_CHARSET, SIGN_ALGORITHMS);
    }

    /**
     * RSA验签名检查
     *
     * @param content 待签名数据
     * @param sign 签名值
     * @param inputCharset 编码格式
     * @return 布尔值
     */
    public boolean doCheck(String content, String sign, String inputCharset) {
        return doCheck(content, sign, inputCharset, SIGN_ALGORITHMS);
    }

    /**
     * MD5WithRSA 加密方式校验
     *
     * @param content 待延签内容
     * @param sign 加密内容
     * @return 验签结果
     */
    public boolean doCheckMD5WithRSA(String content, String sign) {
        return doCheck(content, sign, DEFAULT_CHARSET, MD5WithRSA_SIGN_ALGORITHMS);
    }

    /**
     * MD5WithRSA 加密方式校验
     *
     * @param content 待延签内容
     * @param sign 加密内容
     * @param inputCharSet 编码格式
     * @return 验签结果
     */
    public boolean doCheckMD5WithRSA(String content, String sign, String inputCharSet) {
        return doCheck(content, sign, inputCharSet, MD5WithRSA_SIGN_ALGORITHMS);
    }

    /**
     * SHA1WithRSA 加密方式校验
     *
     * @param content 待延签内容
     * @param sign 加密内容
     * @return 验签结果
     */
    public boolean doCheckSHA1WithRSA(String content, String sign) {
        return doCheck(content, sign, DEFAULT_CHARSET, SHA1WithRSA_SIGN_ALGORITHMS);
    }

    /**
     * SHA1WithRSA 加密方式校验
     *
     * @param content 待延签内容
     * @param sign 加密内容
     * @param inputCharSet 编码格式
     * @return 验签结果
     */
    public boolean doCheckSHA1WithRSA(String content, String sign, String inputCharSet) {
        return doCheck(content, sign, inputCharSet, SHA1WithRSA_SIGN_ALGORITHMS);
    }

    /**
     * RSA 公钥解密验签
     *
     * @param content 待延签内容
     * @param sign 加密内容
     * @param inputCharSet 编码方式
     * @param encryptType 加密方式
     * @return 验签结果
     */
    private boolean doCheck(String content, String sign, String inputCharSet, String encryptType) {
        try {
            // 实例化一个用 encryptType 算法进行的散列，用RSA算法进行加密的Signature
            Signature e = Signature.getInstance(encryptType);
            // 设置解密散列码用的公钥
            e.initVerify(this.publicKey);
            // 设置散列算法的输入
            e.update(content.getBytes(inputCharSet));
            // 解码
            byte[] decode = Base64.decode(sign);
            // 校验，比较计算所得散列码是否和解密的散列码是否一致。 一致则验证成功，否则失败
            return e.verify(decode);
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

}
