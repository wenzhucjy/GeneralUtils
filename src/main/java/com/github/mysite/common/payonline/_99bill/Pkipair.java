package com.github.mysite.common.payonline._99bill;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * description:
 * <p>快钱PKI加密，即DSA 或者 RSA签名方式</p>
 * <p>商户提交用商户私钥证书加密，快钱通过商户的公钥证书来解密；快钱返回时是通过快钱私钥证书加密，商户用快钱公钥证书解密来验签</p>
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-11-30 17:31
 */
public class Pkipair {
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(Pkipair.class);
    /**
     * 密码：xxx,详见生成商户私钥证书的指令
     */
    public static final String KEY_PWD = "";

    /**
     * 使用商户生成的商户私钥(99bill-rsa.pfx)进行加密
     *
     * @param signMsg   待签名数据
     * @return  签名结果
     */
    public static String signMsg(String signMsg) {

        //Base64 加密
        String base64 = "";
        InputStream ksfis = null;
        BufferedInputStream ksbufin = null;
        try {
            // 密钥仓库，KeyStore是一个有效的安全钥匙和证书的管理工具
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ksfis = Pkipair.class.getClassLoader().getResourceAsStream("99bill-rsa.pfx");
            // 读取密钥仓库文件 99bill-rsa.pfx
            // 获取文件输入流
            //ksfis = new FileInputStream(file);
            // 实例一个带有缓冲区域的InputStream
            ksbufin = new BufferedInputStream(ksfis);
            // 证书密码 xxx
            char[] keyPwd = KEY_PWD.toCharArray();
            ks.load(ksbufin, keyPwd);
            // 从密钥仓库得到私钥
            PrivateKey priK = (PrivateKey) ks.getKey("test-alias", keyPwd);
            // 实例化一个用SHA算法进行的散列，用RSA算法进行加密的Signature
            Signature signature = Signature.getInstance("SHA1withRSA");
            // 设置加密散列码用的私钥
            signature.initSign(priK);
            // 设置散列算法的输入
            signature.update(signMsg.getBytes("utf-8"));
            // 进行散列，对产生的散列码进行加密
            byte[] data = signature.sign();
            // Base64 加密
            byte[] encode = Base64.encode(data);
            base64 = new String(encode);

        } catch (FileNotFoundException e) {
            LOG.error("Pkipair 读取密钥仓库　error ,because 99bill-rsa.pfx file not found.");
        } catch (Exception ex) {
            LOG.error("Pkipair signMsg　error ,the error message is [{}]", ex.getMessage());
        } finally {
            IOUtils.closeQuietly(ksfis);
            IOUtils.closeQuietly(ksbufin);
        }
        return base64;
    }

    /**
     * 用快钱公钥证书(99bill.cert.rsa.20340630.cer)解密并验签，与快钱返回的签名字符串的散列码进行比较
     *
     * @param signMsgVal 待签名字符串
     * @param sign       快钱返回的签名字符串
     * @return
     */
    public static boolean enCodeByCer(String signMsgVal, String sign) {

        LOG.debug("99bill notity signMsgVal : [{}]", signMsgVal);
        boolean flag = false;
        InputStream inStream = null;
        try {
            // 读取快钱的公钥证书文件
            //String file = String.format("%s99bill.cert.rsa.20340630.cer", 
            //        Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());
            //inStream = new FileInputStream(file);
            //通过ClassLoader类加载器
            inStream = Pkipair.class.getClassLoader().getResourceAsStream("99bill.cert.rsa.20340630.cer");
            // 生成一个实现指定证书类型的 CertificateFactory 对象
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            //从证书文件*.cer里读取证书信息
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
            // 获得证书公钥
            PublicKey pk = cert.getPublicKey();
            // 签名
            Signature signature = Signature.getInstance("SHA1withRSA");
            // 设置解密散列码用的公钥
            signature.initVerify(pk);
            // 设置散列算法的输入
            signature.update(signMsgVal.getBytes());
            // 解码
            byte[] decode = Base64.decode(sign);
            // 校验，比较计算所得散列码是否和解密的散列码是否一致。 一致则验证成功，否则失败
            flag = signature.verify(decode);
        } catch (Exception e) {
            LOG.error(" Pkipair enCodeByCer error,the error message : [{}]", e.getMessage());
        } finally {
            IOUtils.closeQuietly(inStream);
        }
        return flag;
    }
}
