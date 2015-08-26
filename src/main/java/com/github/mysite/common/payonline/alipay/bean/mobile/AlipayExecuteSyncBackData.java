package com.github.mysite.common.payonline.alipay.bean.mobile;

import com.google.common.base.MoreObjects;

/**
 * description:
 * <p>手机网页交易接口页面跳转同步通知参数，</p>
 *
 * <p>即call_back_url定义的同步跳转url带的参数</p>
 *
 * <p>如果支付失败，直接跳转“手机网页即时到账授权接口中传递的 merchant_url 参数</p>
 *
 * <p>页面跳转后，浏览器地址栏中的链接仅在一分钟内有效，超过一分钟该链接地址会失效</p>
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:28
 */
public class AlipayExecuteSyncBackData {
    /*   基本参数   start  */
    private String sign;                                            //对请求或响应中参数签名后的值
    private String result;                                            //支付结果及交易状态，只有支付成功时（即result=success），才会跳转到支付成功页面
    private String out_trade_no;                                    //支付宝合作商户网站唯一订单号
    private String trade_no;                                        //支付宝交易号
    private String request_token;                                    //授权令牌
	/*   基本参数   end    */

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("sign", sign)
                .add("result", result)
                .add("out_trade_no", out_trade_no)
                .add("trade_no", trade_no)
                .add("request_token", request_token)
                .toString();
    }
}
