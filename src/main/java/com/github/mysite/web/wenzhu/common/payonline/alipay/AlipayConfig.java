package com.github.mysite.web.wenzhu.common.payonline.alipay;

/**
 * description:设置帐户有关信息及返回路径
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/14 - 11:03
 */
public class AlipayConfig {

    public static String sign_type = "";                                                        // DSA、RSA、MD5 三个值可选，必须大写
    public static String pc_payment_type = "1";                                                 // 购买方式: 1-商户购买
    public static String input_charset = "utf-8";                                               // 商户网站使用的编码格式，如utf-8、gbk、gb2312 等
    public static String pc_subject = "";                                                       // 商城订单的名称
    public static String log_path = "/apache_tomcat/web_log/alipay";                            // 支付调试用，创建TXT日志文件夹路径
    public static String WIDseller_email = "";                                                  // 卖家支付宝账号,email
    public static String partner = "";                                                          // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String key = "";                                                              // 交易安全检验码，由数字和字母组成的32位字符串,
    // 如果签名方式设置为“MD5”时，请设置该参数
    public static String pc_service = "create_direct_pay_by_user";                              // 接口名称
    public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";      // 支付宝提供给商户的服务接入网关URL(新)
    public static String pc_show_url = "";                                                      // 商品的展示地址
    public static String pc_notify_url = "";                                                    // 支付宝服务器主动通知商户网站里指定的页面 http 路径
    public static String pc_return_url = "";                                                    // 支付宝处理完请求后，当前页面自动跳转到商户网站里指定页面的http 路径
    public static String anti_phishing_key = "";                                                // 防钓鱼时间戳,若要使用请调用类文件submit中的query_timestamp函数
    public static String exter_invoke_ip = "";                                                  // 客户端的IP地址,非局域网的外网IP地址，如：221.0.0.1
    public static String private_key = "";                                                      // RSA私钥
    //支付宝公钥(移动支付，需用到此参数，固定值无需变动)
    public static String ali_public_key = "";

}
