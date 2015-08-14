package com.github.wenzhu.payonline.alipay.bean.app;

import com.github.wenzhu.encrypt.RSA;
import com.github.wenzhu.payonline.alipay.AlipayConfig;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

/**
 * description:支付宝SDK移动支付请求的参数
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:48
 */
public class AlipayMobileSendData {


    private String parnter = AlipayConfig.partner;
    private String seller_id = AlipayConfig.WIDseller_email;
    private String out_trade_no;
    private String total_fee;
    private String subject;
    private String body;
    private String _input_charset = "UTF-8";
    private String service = "mobile.securitypay.pay";
    private String payment_type = "1";
    private String sign_type = "RSA";   //只有RSA签名方式
    private String notify_url;
    private String it_b_pay;
    private String sign;


    public AlipayMobileSendData(String out_trade_no, String total_fee, String subject, String body, String notify_url, String it_b_pay) {
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.subject = subject;
        this.body = body;
        this.notify_url = notify_url;
        this.it_b_pay = it_b_pay;
    }

    /**
     * 需要参与签名的参数
     *
     * @return  组装好的需要固定格式签名的参数
     */
    public String buildRequestParam() {
        String strParam =
                StringUtils.join(
                        "partner=\"", this.parnter,
                        "\"&seller_id=\"", this.seller_id,
                        "\"&out_trade_no=\"", Strings.nullToEmpty(this.out_trade_no),
                        "\"&subject=\"", Strings.nullToEmpty(this.subject),
                        "\"&body=\"", Strings.nullToEmpty(this.body),
                        "\"&total_fee=\"", Strings.nullToEmpty(this.total_fee),
                        "\"&notify_url=\"", Strings.nullToEmpty(this.notify_url),
                        "\"&service=\"", this.service,
                        "\"&payment_type=\"", this.payment_type,
                        "\"&_input_charset=\"", this._input_charset,
                        "\"&it_b_pay=\"", Strings.nullToEmpty(it_b_pay)
                        , "\""
                );

        return strParam;
    }

    /**
     * 生成签名的数据，并拼接出URL
     * @return  签名结果
     */
    public String generateSign() {
        this.sign = RSA.sign(buildRequestParam(), AlipayConfig.private_key, this._input_charset, true);
        return String.format("%s&sign=\"%s\"&sign_type=\"%s\"", buildRequestParam(), this.sign, this.sign_type);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("alipay mobile send data")
                .omitNullValues()
                .add("parnter", this.parnter)
                .add("seller_id", this.seller_id)
                .add("out_trade_no", this.out_trade_no)
                .add("subject", this.subject)
                .add("body", this.body)
                .add("_input_charset", this._input_charset)
                .add("service", this.service)
                .add("payment_type", this.payment_type)
                .add("notify_url", this.notify_url)
                .add("it_b_pay", this.it_b_pay)
                .add("sign_type", this.sign_type)
                .add("sign", this.sign)
                .toString();
    }
}
