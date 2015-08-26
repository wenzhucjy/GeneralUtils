package com.github.mysite.web.wenzhu.common.payonline.alipay.bean.mobile;

import com.google.common.base.MoreObjects;

/**
 * description:手机网页交易接口服务器异步通知参数，
 * 即 notify_url 定义的同步跳转url带的参数
 * 如果商户反馈给支付宝的字符不是 success 这 7 个字符，支付宝服务器会不断重发通知，直到超过 24 小时 22 分钟
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:28
 */
public class AlipayExecuteAsyncBackData {

    /*   基本参数   start  */
    private String service;                                            //接口名称
    private String v;                                                //接口版本号
    private String sec_id;                                            //签名方式  MD5 or RSA,若设置RSA则用0001代替
    private String sign;                                            //对请求或响应中参数签名后的值
    /*   基本参数   end   */
    /*   业务参数   start  */
    private String notify_data;                                        //通知业务参数
    /*   业务参数   end    */
	/*   notify_data 通知业务参数  start  */
    private String payment_type;                                    //支付方式 1-商品购买,2-捐赠
    private String subject;
    private String trade_no;                                        //支付宝交易号
    private String buyer_email;                                        //买家支付宝账号
    private String gmt_create;                                        //交易创建时间,格式为 yyyy-MM-dd HH:mm:ss
    private String notify_type;                                        //通知类型,trade_status_sync 固定值
    private String quantity;                                        //购买数量
    private String out_trade_no;                                    //支付宝合作商户网站唯一订单号
    private String notify_time;                                        //通知时间，格式为 yyyy-MM-dd HH:mm:ss
    private String seller_id;                                        //卖家支付宝用户号
    private String trade_status;                                    //交易状态,TRADE_FINISHED(交易成功) 或 TRADE_SUCCESS(支付成功)
    private String is_total_fee_adjust;                                //是否调整总价(N or Y)
    private String total_fee;                                        //交易金额
    private String seller_email;                                    //卖家支付宝账号
    private String gmt_close;                                        //交易关闭时间，格式为 yyyy-MM-dd HH:mm:ss
    private String price;                                            //目前和 total_fee 相同,单位：元
    private String buyer_id;                                        //买家支付宝用户名
    private String notify_id;                                        //通过校验ID,当商户收到服务器异步通知并打印出 success 时，服务器异步通知参数notify_id 才会失效
    private String use_coupon;                                        //是否使用红包买家(N or Y)
    private String gmt_payment;                                        //交易付款时间,如果交易未付款， 则不返回该参数,格式为 yyyy-MM-dd HH:mm:ss
    private String refund_status;                                    //退款状态
    private String gmt_refund;                                        //退款时间,格式为 yyyy-MM-dd HH:mm:ss

    /*   notify_data 通知业务参数  end   */
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getSec_id() {
        return sec_id;
    }

    public void setSec_id(String sec_id) {
        this.sec_id = sec_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNotify_data() {
        return notify_data;
    }

    public void setNotify_data(String notify_data) {
        this.notify_data = notify_data;
    }

    // ----- notify_data 业务参数   ------
    public String getPayment_type() {
        return payment_type;
    }

    public String getSubject() {
        return subject;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getNotify_time() {
        return notify_time;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public String getIs_total_fee_adjust() {
        return is_total_fee_adjust;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public String getGmt_close() {
        return gmt_close;
    }

    public String getPrice() {
        return price;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public String getUse_coupon() {
        return use_coupon;
    }

    public String getGmt_payment() {
        return gmt_payment;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public String getGmt_refund() {
        return gmt_refund;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("service", service)
                .add("v", v)
                .add("sec_id", sec_id)
                .add("sign", sign)
                .add("notify_data", notify_data)
                .add("payment_type", payment_type)
                .add("subject", subject)
                .add("trade_no", trade_no)
                .add("buyer_email", buyer_email)
                .add("gmt_create", gmt_create)
                .add("notify_type", notify_type)
                .add("quantity", quantity)
                .add("out_trade_no", out_trade_no)
                .add("notify_time", notify_time)
                .add("seller_id", seller_id)
                .add("trade_status", trade_status)
                .add("is_total_fee_adjust", is_total_fee_adjust)
                .add("total_fee", total_fee)
                .add("seller_email", seller_email)
                .add("gmt_close", gmt_close)
                .add("price", price)
                .add("buyer_id", buyer_id)
                .add("notify_id", notify_id)
                .add("use_coupon", use_coupon)
                .add("gmt_payment", gmt_payment)
                .add("refund_status", refund_status)
                .add("gmt_refund", gmt_refund)
                .toString();
    }
}

