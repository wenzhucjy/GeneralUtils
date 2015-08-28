package com.github.mysite.common.payonline._abc;

import com.abc.trustpay.client.Constants;
import com.abc.trustpay.client.TrxException;
import com.github.mysite.common.jodatime.DateHelper;
import com.github.mysite.common.encrypt.MD5Encrypt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * description:中国农业银行支付订单 —— 新电子商务接口V3.0.2
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/25 - 17:35
 */
public abstract class AbcBaseAction {

    private static final Logger LOG = LoggerFactory.getLogger(AbcBaseAction.class);

    protected String receiveNotifyUrlPrefix = "";   //服务器通知URL的前缀
    protected String inputCharSet = "UTF-8";        //编码格式
    protected String rsaPrivateKey = "";            //加密私钥

    /**
     * 请求农行支付网关
     *
     * @param request         HttpServletRequest
     * @param paymentType     支付类型
     * @param aOrderSn        订单号
     * @param aOrderName      订单描述
     * @param aOrderAmount    订单金额,小数点后两位
     * @param aOrderDate      订单时间
     * @param aQty            数量
     * @param redirectUrl     跳转URL
     * @param resultNotifyUrl 服务器通知URL,公网能访问的地址，而且端口号必须为 80 或者 443（http 默认端口为 80；https 默认端口为 443）
     * @return  结果页面
     */
    protected String accessGateWay(HttpServletRequest request, String paymentType, String aOrderSn, String aOrderName, String aOrderAmount,
                                   Date aOrderDate, int aQty, String redirectUrl, String resultNotifyUrl) {

        String formatDate = DateHelper.formateDate(aOrderDate, AbcDicOrder.DATE_PATTERNS[9]);
        //订单过期时间
        String orderTimeoutDate = DateHelper.formateDate(DateHelper.plusDays(aOrderDate, 10), AbcDicOrder.DATE_PATTERNS[8]);
        //支付订单
        AbcDicOrder abcDicOrder = new AbcDicOrder(
                aOrderSn, aOrderAmount, formatDate.split(" ")[0], formatDate.split(" ")[1], aOrderName, orderTimeoutDate
        );
        //支付订单项
        AbcDicOrderItem abcDicOrderItem = new AbcDicOrderItem(aOrderName, aQty + "");
        //支付请求
        AbcDicRequest abcDicRequest = new AbcDicRequest(paymentType, resultNotifyUrl);

        AbcPaymentRequest paymentRequest = new AbcPaymentRequest(abcDicOrder, abcDicOrderItem, abcDicRequest);

        LOG.debug("订单农行支付,类型 : [{}] , sn : [{}] ,amount : [{}], aOrderName : [{}]  , aQty : [{}] , " +
                        "resultNotifyUrl : [{}]", StringUtils.equals(paymentType, Constants.PAY_TYPE_UCBP) ? "银联在线支付"
                        : StringUtils.equals(paymentType, Constants.PAY_TYPE_ALL) ? "B2C支付" : "B2B对公户支付",
                aOrderSn, aOrderAmount, aOrderName, aQty, resultNotifyUrl
        );
        return paymentRequest.postRequestValue(request, redirectUrl);
    }

    // ------ notification for merchant payment ------

    /**
     * 接收服务器通知 ServerNotify
     *
     * @param MSG  银行返回的加密信息,客户端需进行解密验签
     * @param type 业务类型
     * @return  结果
     */
    protected String notifyResult(String MSG, String type) {
        String pcKey = "";
        String resultUrl = "";
        //1.判断MSG是否存在
        if (StringUtils.isNotBlank(MSG)) {
            try {
                //2.把MSG进行转换GBK编码解密
                AbcPaymentXmlResultGBK xmlResultGBK = new AbcPaymentXmlResultGBK(MSG);
                //3.封装解码后的数据
                AbcPaymentResult tResult = new AbcPaymentResult(xmlResultGBK);
                LOG.info("ABC paymentResult : {}", tResult);
                // 4、判断支付结果处理状态，进行后续操作
                if (xmlResultGBK.isSuccess()) {
                    // 5、支付成功并且验签、解析成功
                    // 以下是商户业务逻辑代码
                    pcKey = MD5Encrypt.sign(tResult.getOrderNo(), rsaPrivateKey, inputCharSet);
                    bussinessDealCode(tResult);
                } else {
                    // 6、支付成功但是由于验签或者解析报文等操作失败
                    LOG.error("ABC Payonline notifyResult fail, MSG : [{}] ==> fails; code: [{}], error message: [{}]",
                            MSG, tResult.getReturnCode(), xmlResultGBK.getErrorMessage());
                }
                // 设定商户结果显示页面
                resultUrl = String.format("%s/%s/payment%s", receiveNotifyUrlPrefix, type,
                        (xmlResultGBK.isSuccess() ? "Success?orderNo=" + tResult.getOrderNo() + "&pcKey=" + pcKey
                                : "Fail?orderNo=" + tResult.getOrderNo() + "&pcKey=" + pcKey));

                LOG.info("abcBaseController resultUrl: [{}]", resultUrl);

            } catch (TrxException e) {
                // 支付成功但是由于验签或者解析报文等操作失败
                LOG.error("ABC Payonline notifyResult fail, MSG : [{}] ==> fails; code: [{}], error message: [{}] , detail message : [{}]",
                        MSG, e.getCode(), e.getMessage(), e.getDetailMessage());
            }
            //7.跳转到支付结果页面
            return String.format("<URL>%1$s</URL>\n<HTML>\n<HEAD>\n<meta http-equiv=\"refresh\" content=\"0; "
                    + "url='%1$s'\">\n</HEAD>\n</HTML>", resultUrl); // <URL>%1$s</URL>
        } else {
            return "The wrong link address,please check the link address again.";
        }
    }

    /**
     * 支付成功 —— 通知页面
     *
     * @param request HttpServletRequest
     * @return 支付成功返回页面
     */
    protected String paymentSuccessPage(HttpServletRequest request) {

        if (StringUtils.isNotBlank(request.getParameter("orderNo")) && StringUtils.isNotBlank(request.getParameter("pcKey"))) {
            // url 验签
            boolean isSign = MD5Encrypt.verify(request.getParameter("orderNo"), request.getParameter("pcKey"), rsaPrivateKey, inputCharSet);
            LOG.debug("ABC payment success,isSign:[{}]", isSign);
            if (isSign) {
                return this.paymentSuccess(request);
            }
        }
        return "ebank/paymentLinkFail";
    }

    /**
     * 支付失败 ——　通知页面
     *
     * @param request HttpServletRequest
     * @return 支付失败返回页面
     */
    protected String paymentFailPage(HttpServletRequest request) {
        if (StringUtils.isNotBlank(request.getParameter("orderNo")) && StringUtils.isNotBlank(request.getParameter("pcKey"))) {
            // url 验签
            boolean isSign = MD5Encrypt.verify(request.getParameter("orderNo"), request.getParameter("pcKey"), rsaPrivateKey, inputCharSet);
            LOG.debug("ABC payment fail,isSign:[{}]", isSign);
            if (isSign) {
                return this.paymentFail(request);
            }
        }
        return "ebank/paymentLinkFail";
    }

    /**
     * 商户业务逻辑
     *
     * @param tResult AbcPaymentResult
     */
    protected abstract void bussinessDealCode(AbcPaymentResult tResult);

    /**
     * 支付成功
     *
     * @param request HttpServletRequest
     * @return 返回页面
     */
    protected abstract String paymentSuccess(HttpServletRequest request);

    /**
     * 支付失败
     *
     * @param request HttpServletRequest
     * @return 返回页面
     */
    protected abstract String paymentFail(HttpServletRequest request);
}
