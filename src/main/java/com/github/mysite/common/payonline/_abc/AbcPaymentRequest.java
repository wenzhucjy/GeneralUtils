package com.github.mysite.common.payonline._abc;

import com.abc.trustpay.client.JSON;
import com.abc.trustpay.client.ebus.PaymentRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * description:中国农业银行支付对象 —— 新电子商务接口V3.0.2
 *
 * @author :    jy.chen
 *  @version  :  1.0
 * @since  : 2015/8/25 - 15:00
 */
public class AbcPaymentRequest extends PaymentRequest {
    
    private static final Logger LOG = LoggerFactory.getLogger(AbcPaymentRequest.class);
    private AbcDicOrder abcDicOrder;
    private AbcDicOrderItem abcDicOrderItem;
    private AbcDicRequest abcDicRequest;

    /**
     * 带参数构造方法
     * @param abcDicOrder   订单
     * @param abcDicOrderItem   订单项
     * @param abcDicRequest 支付请求对象
     */
    public AbcPaymentRequest(AbcDicOrder abcDicOrder, AbcDicOrderItem abcDicOrderItem, AbcDicRequest abcDicRequest){
        this.abcDicOrder = abcDicOrder;
        this.abcDicOrderItem = abcDicOrderItem;
        this.abcDicRequest = abcDicRequest;
    }

    /**
     * 封装支付对象请求农行支付网关,并取得返回值ReturnCode,若ReturnCode为"0000",则跳转至银行支付页面,否则跳转至定义的 redirectUrl
     *
     * @param request     HttpServletRequest
     * @param redirectUrl 重定向URL
     * @return 返回信息
     */
    @SuppressWarnings("unchecked")
    public String postRequestValue(HttpServletRequest request,String redirectUrl) {
        super.dicOrder.putAll(abcDicOrder.bulidDicOrder());
        super.orderitems.put(1, abcDicOrderItem.bulidAbcDicretMap());
        super.dicRequest.putAll(abcDicRequest.bulidAbcDicRequestMap());
        JSON json = super.postRequest();
        //返回状态，若为0000则代表成功,否则为失败,则需返回失败信息
        String returnCode = json.GetKeyValue("ReturnCode");
        if (StringUtils.equals("0000", returnCode)) {
            //若交易成功,取得支付页面网址，并将消费者的浏览器导向到此支付页面网址进行支付
            String paymentURL = json.GetKeyValue("PaymentURL");
            LOG.debug("Payment url : [{}]", paymentURL);
            return "redirect:" + paymentURL;
        }
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        
        LOG.debug("ABC payment fail ,错误信息 : [{}]", ReturnCode + "-" + ErrorMessage);
        request.setAttribute("ReturnCode", ReturnCode);
        request.setAttribute("ErrorMessage", ErrorMessage);
        request.setAttribute("redirectUrl", redirectUrl);
        return "ebank/abc/abcPaymentRequestFail";
    }
    
}
