package com.github.mysite.common.payonline.alipay;

import com.github.mysite.common.payonline.util.SettingConstants;
import com.github.mysite.common.payonline.util.SettingUtils;

/**
 * description:设置帐户有关信息及返回路径
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-11-30 11:03
 */
public class AlipayConfig {

    /**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
    public static final String CLOSE_TRADE_SRVICE = "close_trade";                              // 关闭交易service名称
    public static final String QUERYSINGLE_TRADE_SRVICE = "single_trade_query";                 // 查询单笔交易service名称
    /**
     * 旧版本的RSA加密用 0001表示，新版本用RSA表示
     */
    public static String sign_type = "RSA";                                                        // DSA、RSA、MD5 三个值可选，必须大写
    public static String payment_type = "1";                                                 // 购买方式: 1-商户购买
    public static String input_charset = "utf-8";                                               // 商户网站使用的编码格式，如utf-8、gbk、gb2312 等
    public static String log_path = SettingUtils.getProperty(SettingConstants.LOG_PATH);         // 支付调试用，创建TXT日志文件夹路径
    public static String sellerEmail = SettingUtils.getProperty(SettingConstants.SELLER_EMAIL);                                                      // 卖家支付宝账号,email
    public static String partner = SettingUtils.getProperty(SettingConstants.PARTNER);                                                          // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String key = SettingUtils.getProperty(SettingConstants.KEY);                                                              // 交易安全检验码，由数字和字母组成的32位字符串,如果签名方式设置为“MD5”时，请设置该参数
    public static String private_key = SettingUtils.getProperty(SettingConstants.PRIVATE_KEY);                                                     // RSA私钥支付宝公钥(移动支付，需用到此参数，固定值无需变动)
    public static String ali_public_key = SettingUtils.getProperty(SettingConstants.ALI_PUBLIC_KEY);
    public static String mobileService = SettingUtils.getProperty(SettingConstants.MOBILE_SERVICE);         // 手机网站支付接口名称

    /**
     * 以下是即时到账接口部分参数
     */
    public static String pc_service = "create_direct_pay_by_user";                              // 接口名称
    public static String subject = SettingUtils.getProperty(SettingConstants.PC_SUBJECT);        // 商城订单的名称
    public static String pc_show_url = SettingUtils.getProperty(SettingConstants.PC_SHOW_URL);                                                      // 商品的展示地址
    public static String pc_notify_url =SettingUtils.getProperty(SettingConstants.PC_NOTIFY_URL);                                                    // 支付宝服务器主动通知商户网站里指定的页面 http 路径
    public static String pc_return_url = SettingUtils.getProperty(SettingConstants.PC_RETURN_URL);                                                   // 支付宝处理完请求后，当前页面自动跳转到商户网站里指定页面的http 路径
    public static String anti_phishing_key = SettingUtils.getProperty(SettingConstants.ANTI_PHISHING_KEY);                                                  // 防钓鱼时间戳,若要使用请调用类文件submit中的query_timestamp函数
    public static String exter_invoke_ip = SettingUtils.getProperty(SettingConstants.EXTER_INVOKE_IP);                                                  // 客户端的IP地址,非局域网的外网IP地址，如：221.0.0.1


}
