package com.github.mysite.common.payonline.alipay;

import com.github.mysite.common.payonline.alipay.bean.mobile.AliPayMobileAsyncBackData;
import com.github.mysite.common.payonline.alipay.bean.mobile.AliPayMobileSendData;
import com.github.mysite.common.payonline.alipay.bean.mobile.AliPayMobileSyncBackData;
import com.github.mysite.common.payonline.alipay.util.AlipayNotify;
import com.github.mysite.common.payonline.util.RequestHelper;
import com.github.mysite.common.payonline.util.SettingConstants;
import com.github.mysite.common.payonline.util.SettingUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;


/**
 * description: 手机网站支付宝支付（新版本）
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-12-23 10:44
 */
public abstract class AlipayBaseAction {
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(AlipayBaseAction.class);

    protected String accessGateWay(HttpServletRequest request, String outTradeNo, String totalFee, String subject
            , String notifyUrl, String returnUrl, String showUrl, String body, String token) throws Exception {
        String pay_expire = SettingUtils.getProperty(SettingConstants.PAY_EXPIRE);
        AliPayMobileSendData sendData = new AliPayMobileSendData(notifyUrl, returnUrl, outTradeNo, subject, totalFee, body, showUrl, pay_expire, token);
        String sHtmlText = sendData.buildMapParam();
        LOG.info("orderSn : {} , sHtmlText : {}", outTradeNo, sHtmlText);
        request.setAttribute("sHtmlText", sHtmlText);
        return "accessGateWay";//form表单自动提交
    }

    /**
     * 支付宝服务器异步调用接口函数
     *
     * @param request HttpServletRequest
     * @return success 或 fail
     */
    protected String baseExecuteAsync(HttpServletRequest request) {
        //封装Request参数为Map对象
        Map<String, String> params = RequestHelper.buildMapParam(request);
        boolean flag = false;
        if (!params.isEmpty()) {
            try {
                AliPayMobileAsyncBackData backData = RequestHelper.request2Bean(request, AliPayMobileAsyncBackData.class);
                if (null != backData) {
                    LOG.info("AliPay Mobile Async Notify Param backData : [{}]", backData);
                    //计算得出通知验证结果
                    boolean verify_result = AlipayNotify.verify(params, "ASync");
                    LOG.info("AliPay Mobile Async Notify verify_result : {}", verify_result);
                    //验证通过
                    if (verify_result) {
                        //支付成功
                        if (StringUtils.equals("TRADE_FINISHED", backData.getTrade_status()) || StringUtils.equals("TRADE_SUCCESS", backData.getTrade_status())) {

                            //商户订单号
                            String orderSn = backData.getOut_trade_no();
                            //支付宝交易号
                            String aliPayTradeNo = backData.getTrade_no();
                            //付款账号
                            String buyerEmail = backData.getBuyer_email();
                            //金额
                            String totalFee = backData.getTotal_fee();
                            //商户业务逻辑处理
                            this.businessDealCode(orderSn, aliPayTradeNo, buyerEmail, new BigDecimal(totalFee));
                            LOG.info("异步验证 Success，OrderSn : {} , AliPayTradeNo : {} ,BuyerEmail : {} , TotalFee : {}  "
                                    , orderSn, aliPayTradeNo, buyerEmail, totalFee);
                            flag = true;
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error("AliPay Mobile Async Notify Error , error msg : ", e);
            }
        }
        LOG.error("AliPay Mobile ASync Result : {}" ,flag ? "success" : "fail");
        return flag ? "success" : "fail";
    }

    public String baseExecuteSync(HttpServletRequest request) {
        //封装Request参数为Map对象
        Map<String, String> params = RequestHelper.buildMapParam(request);
        if (!params.isEmpty()) {
            try {
                AliPayMobileSyncBackData backData = RequestHelper.request2Bean(request, AliPayMobileSyncBackData.class);
                if (null != backData) {
                    LOG.debug("AliPay Mobile Sync Notify Param backData : [{}]", backData);
                    //计算得出通知验证结果
                    boolean verify_result = AlipayNotify.verify(params, "Sync");
                    LOG.info("AliPay Mobile Sync Notify verify_result : {}", verify_result);
                    //验证通过
                    if (verify_result) {
                        //支付成功
                        if (StringUtils.equals("TRADE_FINISHED", backData.getTrade_status()) || StringUtils.equals("TRADE_SUCCESS", backData.getTrade_status())) {
                            //商户订单号
                            String orderSn = backData.getOut_trade_no();
                            //支付宝交易号
                            String aliPayTradeNo = backData.getTrade_no();
                            //金额
                            String totalFee = backData.getTotal_fee();
                            LOG.info("同步验证Success ，OrderSn : {} , AliPayTradeNo : {} , TotalFee : {}  "
                                    , orderSn, aliPayTradeNo , totalFee);
                            //商户业务逻辑处理
                            this.businessDealCode(orderSn, aliPayTradeNo, "", new BigDecimal(totalFee));
                            return orderSn;
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error("AliPay Mobile Sync Notify Error , error msg : ", e);
            }
        }
        return "";
    }

    /**
     * 商户业务逻辑处理
     *
     * @param orderSn       商户订单号
     * @param aliPayTradeNo 支付宝外部交易号
     * @param buyerEmail    付款账号
     * @param totalFee      支付金额
     */
    protected abstract void businessDealCode(String orderSn, String aliPayTradeNo, String buyerEmail, BigDecimal totalFee);

}
