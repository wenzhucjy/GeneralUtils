package com.github.mysite.common.payonline.alipay.bean.mobile;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.MoreObjects;

/**
 * description:手机网站支付（新版本），页面跳转同步参数
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-11-30 17:28
 */
public class AliPayMobileSyncBackData {

    private String is_success;                                      //表示接口调用是否成功，并不表明业务处理结果，T表示成功
    private String sign_type;                                            //签名方式 RSA
    private String sign;                                            //对请求或响应中参数签名后的值
    private String service;                                            //接口名称
    private String notify_id;                                            //通知校验ID，支付宝通知校验ID，商户可以用这个流水号询问支付宝该条通知的合法性。
    private String notify_time;                                            //通知时间（支付宝时间）。格式为yyyy-MM-dd HH:mm:ss
    private String notify_type;                                            //返回通知类型
    private String out_trade_no;                                            //支付宝交易号
    private String trade_no;                                        //支付宝交易号
    private String subject;                                            //商品的标题/交易标题/订单标题/订单关键字等
    private String payment_type;                                            //支付类型
    private String trade_status;                                            //交易目前所处的状态。成功状态的值只有两个：TRADE_FINISHED（普通即时到账的交易成功状态）；TRADE_SUCCESS（开通了高级即时到账或机票分销产品后的交易成功状态）
    private String seller_id;                                            //卖家支付宝账户号
    private String total_fee;                                              //交易金额
    private String body;                                              //商品描述

    public String getIs_success() {
        return is_success;
    }

    public void setIs_success(String is_success) {
        this.is_success = is_success;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getTotal_fee() {
        return StringUtils.isNotBlank(total_fee) ? total_fee : "0";
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("is_success", is_success)
                .add("sign_type", sign_type)
                .add("sign", sign)
                .add("service", service)
                .add("notify_id", notify_id)
                .add("notify_time", notify_time)
                .add("notify_type", notify_type)
                .add("out_trade_no", out_trade_no)
                .add("trade_no", trade_no)
                .add("subject", subject)
                .add("payment_type", payment_type)
                .add("trade_status", trade_status)
                .add("seller_id", seller_id)
                .add("total_fee", total_fee)
                .add("body", body)
                .toString();
    }
}
