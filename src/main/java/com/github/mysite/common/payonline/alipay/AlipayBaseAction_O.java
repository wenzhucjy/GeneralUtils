package com.github.mysite.common.payonline.alipay;

import com.github.mysite.common.payonline.alipay.bean.mobile_old.AlipayAuthAndExecute;
import com.github.mysite.common.payonline.alipay.bean.mobile_old.AlipayCreateDirect;
import com.github.mysite.common.payonline.alipay.bean.mobile_old.AlipayExecuteAsyncBackData;
import com.github.mysite.common.payonline.alipay.bean.mobile_old.AlipayExecuteSyncBackData;
import com.github.mysite.common.payonline.alipay.util.AlipayNotify_O;
import com.github.mysite.common.payonline.alipay.util.AlipaySubmit_O;
import com.github.mysite.common.payonline.util.RequestHelper;
import com.github.mysite.common.payonline.util.SettingConstants;
import com.github.mysite.common.payonline.util.SettingUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;
import java.util.UUID;

/**
 * description: 手机网站支付宝支付
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-12-01 10:44
 */
public abstract class AlipayBaseAction_O {
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(AlipayBaseAction_O.class);

    protected String accessGateWay(HttpServletRequest request, String outTradeNo, String totalFee, String subject
            , String notifyUrl, String callBackUrl, String merchantUrl) throws Exception {


        //1.先验证数据的合法性,校验通过,表示来源合法
        //

        //2.通过订单号获取整个订单信息，包含订单金额等等

        //3.逻辑处理
        ////////////////////////////////// 手机网页即时到账授权接口  request_token/////////////////////////////////////////////
        String pay_expire = SettingUtils.getProperty(SettingConstants.PAY_EXPIRE);
        String req_id = UUID.randomUUID().toString().replace("-", "");

        // 封装授权接口的请求参数
        AlipayCreateDirect alipayDirect = new AlipayCreateDirect(outTradeNo, totalFee, subject,
                pay_expire, req_id, notifyUrl, callBackUrl, merchantUrl);

        Map<String, String> map = alipayDirect.buildMapParam();
        // 建立请求
        String sHtmlTextToken = AlipaySubmit_O.mBuildRequest(AlipayCreateDirect.directGateWay, "", "", map);
        LOG.debug("授权接口业务参数:[{}]", map);
        if (StringUtils.isNotBlank(sHtmlTextToken)) {
            sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, alipayDirect.getInput_charset());
            // 获取授权token
            String request_token = AlipaySubmit_O.getRequestToken(sHtmlTextToken);

            System.out.println("授权接口token:" + request_token);
            // 授权token 必须不为空
            if (StringUtils.isNotBlank(request_token)) {
                //////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute ////////////////////////
                AlipayAuthAndExecute alipayExecute = new AlipayAuthAndExecute();
                // 设置授权令牌 token
                alipayExecute.setRequest_token(request_token);

                String sHtmlTxt = AlipaySubmit_O.mBuildRequest(AlipayCreateDirect.directGateWay,
                        alipayExecute.buildMapParam(), "get", "确认");
                request.setAttribute("sHtmlText", sHtmlTxt);
                return "/m/store/mAccessGateWay";
            }
        }
        return "/m/store/mPaidFailure";
    }

    /**
     * 支付宝服务器异步调用接口函数
     *
     * @param request HttpServletRequest
     * @return success 或 fail
     */
    protected String baseExecuteAsync(HttpServletRequest request) {
        Map<String, String> params = RequestHelper.buildMapParam(request);
        boolean flag = false;
        if (null != params && !params.isEmpty()) {
            try {
                // RSA签名解密
                if (AlipayConfig.sign_type.equals("0001")) {
                    params = AlipayNotify_O.decrypt(params);
                }
                AlipayExecuteAsyncBackData backData = RequestHelper.request2Bean(request, AlipayExecuteAsyncBackData.class);
                LOG.debug("Alipay ExecuteASync Notify Param backData : [{}]", backData);
                if (null != params.get("notify_data")) {
                    // XML解析notify_data数据
                    Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
                    // 商户订单号
                    String orderSn = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();

                    // 支付宝交易号 trade_no
                    String aliPayTradeNo = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
                    // 交易状态 trade_status
                    String trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();

                    //买家支付宝账号 buyer_email
                    String buyerEmail = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();

                    //交易金额 total_fee
                    String totalFee = doc_notify_data.selectSingleNode("//notify/total_fee").getText();
                    // 验证服务器异步通知的参数
                    boolean verify_result = AlipayNotify_O.mVerifyNotify(params);
                    LOG.debug("Alipay ExecuteASync Notify verify_result : [{}]", verify_result);
                    if (verify_result) {
                        // 验证成功
                        if (StringUtils.equals(trade_status, "TRADE_FINISHED") || StringUtils.equals(trade_status, "TRADE_SUCCESS")) {
                            LOG.info("验证成功，OrderSn : {} , OutTradeNo : {} ,BuyerEmail : {} , TotalFee : {}  "
                                    , orderSn, aliPayTradeNo,buyerEmail, totalFee);
                            this.businessDealCode(orderSn, aliPayTradeNo,buyerEmail, Integer.valueOf(totalFee));
                            flag = true;
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error("Alipay executeAsync params error , error msg : ", e.getMessage());
            }
        }
        LOG.debug("Mobile alipay execute async verify flag : [{}]", flag);
        return flag ? "success" : "fail";
    }

    public String baseExecuteSync(HttpServletRequest request) {
        Map<String, String> params = RequestHelper.buildMapParam(request);
        if (!params.isEmpty()) {
            // 计算得出通知验证结果
            boolean verify_result = AlipayNotify_O.verifyReturn(params);
            LOG.debug("Alipay ExecuteSync Notify verify_result : [{}]", verify_result);
            if (verify_result) {
                AlipayExecuteSyncBackData backData = RequestHelper.request2Bean(request, AlipayExecuteSyncBackData.class);
                if (null != backData) {
                    LOG.debug("Alipay ExecuteSync Notify Param backData : [{}]", backData);
                    if (StringUtils.equals(backData.getResult(), "success")) {
                        String orderSn = backData.getOut_trade_no();
                        // 验证成功
                        this.businessDealCode(orderSn, backData.getOut_trade_no(), "", 0);
                        return orderSn;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 商户业务逻辑处理
     *
     * @param orderSn       商户订单号
     * @param aliPayTradeNo 支付宝外部交易号
     * @param buyerEmail    付款账号
     * @param totalFee      支付金额
     */
    protected abstract void businessDealCode(String orderSn, String aliPayTradeNo, String buyerEmail, Integer totalFee);

}
