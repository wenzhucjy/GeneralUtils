package com.github.mysite.common.payonline.alipay.bean.mobile;


import com.github.mysite.common.payonline.alipay.AlipayConfig;
import com.github.mysite.common.payonline.alipay.util.AlipaySubmit;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * description: 手机网站支付请求参数(新版本)
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-11-30 17:26
 */
public class AliPayMobileSendData {

    private String service = AlipayConfig.mobileService;        //接口名称
    private String partner = AlipayConfig.partner;                    //合作者身份 ID
    private String _input_charset = AlipayConfig.input_charset;        // 字符集
    private String seller_id = AlipayConfig.sellerEmail;           //卖家支付宝用户号
    //private String sign_type = AlipayConfig.sign_type;                 //签名方式
    //private String sign;                                              //对请求或响应中参数签名后的值
    private String notify_url;                                       //服务器异步通知页面路径
    private String return_url;                                          //页面跳转同步通知页面路径
    private String out_trade_no;                                        //商户网站唯一订单号，String(64)
    private String subject;                             //商品名称
    private String total_fee;       //交易金额，取值范围为[0.01，100000000.00]，精确到小数点后两位。
    private String payment_type = AlipayConfig.payment_type;  //支付类型
    private String body;                //商品描述
    private String show_url;            //商品展示网址
    /**
     * 设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭。取值范围：1m～15d。 m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
     * 该参数数值不接受小数点，如1.5h，可转换为90m。
     * 当用户输入支付密码、点击确认付款后（即创建支付宝交易后）开始计时。
     * 支持绝对超时时间，格式为yyyy-MM-dd HH:mm。
     */
    private String it_b_pay;

    private String extern_token;            //手机支付宝token
    /**
     * 航旅订单其它费用，航旅订单中除去票面价之外的费用，单位为RMB-Yuan。取值范围为[0.01,100000000.00]，精确到小数点后两位。
     */
    private String otherfee;

    private String airticket;//航旅订单金额

    private String rn_check;  //是否发起实名校验 T：发起实名校验；F：不发起实名校验。

    private String buyer_cert_no;   //买家证件号码
    private String buyer_real_name; //买家真实姓名

    private String scene;       //收单场景。如需使用该字段，需向支付宝申请开通，否则传入无效。

    /**
     * 带参数构造方法
     *
     * @param notify_url   异步通知url
     * @param return_url   同步通知url
     * @param out_trade_no 商户订单号
     * @param subject      订单名称
     * @param total_fee    订单金额
     * @param body         商品描述
     * @param show_url     展示url
     * @param it_b_pay     过期时间
     * @param extern_token 手机Token
     */
    public AliPayMobileSendData(String notify_url, String return_url, String out_trade_no, String subject, String total_fee, String body, String show_url, String it_b_pay, String extern_token) {
        this.notify_url = notify_url;
        this.return_url = return_url;
        this.out_trade_no = out_trade_no;
        this.subject = subject;
        this.total_fee = total_fee;
        this.body = body;
        this.show_url = show_url;
        this.it_b_pay = it_b_pay;
        this.extern_token = extern_token;
    }

    public String buildMapParam() {
        //把请求参数打包成数组
        Map<String, String> sParaTemp = Maps.newHashMap();
        sParaTemp.put("service", this.service);
        sParaTemp.put("partner", this.partner);
        sParaTemp.put("seller_id", this.seller_id);
        sParaTemp.put("_input_charset", this._input_charset);
        sParaTemp.put("payment_type", this.payment_type);
        sParaTemp.put("notify_url", this.notify_url);
        sParaTemp.put("return_url", this.return_url);
        sParaTemp.put("out_trade_no", this.out_trade_no);
        sParaTemp.put("subject", this.subject);
        sParaTemp.put("total_fee", this.total_fee);
        sParaTemp.put("show_url", this.show_url);
        sParaTemp.put("body", this.body);
        sParaTemp.put("it_b_pay", this.it_b_pay);
        sParaTemp.put("extern_token", this.extern_token);
        return AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
    }
}
