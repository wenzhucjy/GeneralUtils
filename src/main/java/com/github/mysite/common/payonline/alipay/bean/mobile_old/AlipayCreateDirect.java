package com.github.mysite.common.payonline.alipay.bean.mobile_old;

import com.github.mysite.common.payonline.alipay.AlipayConfig;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * description:手机网页即时到账授权接口
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-11-30 17:25
 */
public class AlipayCreateDirect {

    /* 基本参数 start */
    public static final String directGateWay = "http://wappaygw.alipay.com/service/rest.htm"; // 支付宝手机网页即时到账授权接口网关
    private String service = "alipay.wap.trade.create.direct";  		// 接口名称
    private String format = "xml";                                         // 请求参数格式
    private String v = "2.0";                                              // 接口版本号
    private String partner = AlipayConfig.partner;                         // 合作者身份 ID
    private String input_charset = AlipayConfig.input_charset;             // 字符集
    private String req_id;                                                 // 请求号，须保证每次请求都是唯一,最长32位
    private String sec_id = "0001";                                         // 签名方式 MD5 or RSA,若设置RSA则用0001代替
    private String sign;                                                   // 对请求或响应中参数签名后的值
    /* 基本参数 end */

    /* 业务参数 start */
    private String req_data;                                                // 请求业务参数
    private String subject;                                                 // 商品名称
    private String out_trade_no;                                            // 支付宝合作商户网站唯一订单号
    private String total_fee;                                               // 该笔订单的资金总额， 单位为 RMB-Yuan。
    // 取值范围为[0.01，100,000,000.00]，精确到小数点后两位。
    private String seller_account_name = AlipayConfig.sellerEmail;      // 卖家支付宝账号
    private String call_back_url;                                           // 支付成功的跳转页面
    // 以下参数可为空
    private String notify_url;                                              // 服务器异步通知的页面路径,页面返回success代表成功
    private String merchant_url;                                            // 操作中断返回地址
    private String out_user;                                                // 商户系统唯一标识
    private String pay_expire;                                              // 交易自动关闭时间， 单位为分钟，默认21600，即15天
    private String agent_id;                                                // 代理人ID，用于需要给代理分佣的情况下传入

    /**
     * 带参数构造方法
     *
     * @param out_trade_no  订单号
     * @param total_fee     金额
     * @param subject       名称
     * @param pay_expire    过期时间
     * @param req_id        唯一标志位
     * @param notify_url    服务器异步通知地址
     * @param call_back_url 页面通知地址
     * @param merchant_url  交易中断地址
     */
    public AlipayCreateDirect(String out_trade_no, String total_fee, String subject, String pay_expire, String req_id, String notify_url, String
            call_back_url, String merchant_url) {
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.subject = subject;
        this.pay_expire = pay_expire;
        this.req_id = req_id;
        this.notify_url = notify_url;
        this.call_back_url = call_back_url;
        this.merchant_url = merchant_url;
    }


    /* 业务参数 end */
    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * 业务参数详细 - 授权交易接口
     *
     * @return
     */
    public String getReq_data() {
        this.req_data = "<direct_trade_create_req>" + "<subject>" + subject + "</subject>" + "<out_trade_no>" + out_trade_no
                + "</out_trade_no>" + "<total_fee>" + total_fee + "</total_fee>" + "<seller_account_name>" + seller_account_name
                + "</seller_account_name>" + "<call_back_url>" + call_back_url + "</call_back_url>" + "<notify_url>" + notify_url
                + "</notify_url>" + "<merchant_url>" + merchant_url + "</merchant_url>" + "<pay_expire>" + pay_expire
                + "</pay_expire>" + "</direct_trade_create_req>";
        return req_data;
    }

    public Map<String, String> buildMapParam() {
        Map<String, String> sParaTempToken = Maps.newHashMap();
        sParaTempToken.put("service", this.service);
        sParaTempToken.put("partner", this.partner);
        sParaTempToken.put("_input_charset", this.input_charset);
        sParaTempToken.put("sec_id", this.sec_id);
        sParaTempToken.put("format", this.format);
        sParaTempToken.put("v", this.v);
        sParaTempToken.put("req_id", this.req_id);
        sParaTempToken.put("req_data", getReq_data());
        return sParaTempToken;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getCall_back_url() {
        return call_back_url;
    }

    public void setCall_back_url(String call_back_url) {
        this.call_back_url = call_back_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getMerchant_url() {
        return merchant_url;
    }

    public void setMerchant_url(String merchant_url) {
        this.merchant_url = merchant_url;
    }

    public String getOut_user() {
        return out_user;
    }

    public void setOut_user(String out_user) {
        this.out_user = out_user;
    }

    public String getPay_expire() {
        return pay_expire;
    }

    public void setPay_expire(String pay_expire) {
        this.pay_expire = pay_expire;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getService() {
        return service;
    }

    public String getFormat() {
        return format;
    }

    public String getV() {
        return v;
    }

    public String getPartner() {
        return partner;
    }

    public String getSec_id() {
        return sec_id;
    }

    public String getSeller_account_name() {
        return seller_account_name;
    }

    public String getInput_charset() {
        return input_charset;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("service", service)
                .add("format", format)
                .add("v", v)
                .add("partner", partner)
                .add("input_charset", input_charset)
                .add("req_id", req_id)
                .add("sec_id", sec_id)
                .add("sign", sign)
                .add("req_data", req_data)
                .add("subject", subject)
                .add("out_trade_no", out_trade_no)
                .add("total_fee", total_fee)
                .add("seller_account_name", seller_account_name)
                .add("call_back_url", call_back_url)
                .add("notify_url", notify_url)
                .add("merchant_url", merchant_url)
                .add("out_user", out_user)
                .add("pay_expire", pay_expire)
                .add("agent_id", agent_id)
                .toString();
    }
}
