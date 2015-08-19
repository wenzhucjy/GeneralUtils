package com.github.mysite.web.wenzhu.common.payonline.alipay.bean.mobile;

import com.github.mysite.web.wenzhu.common.common.EncapsuleRequestParaUtil;
import com.github.mysite.web.wenzhu.common.payonline.alipay.AlipayConfig;
import com.github.mysite.web.wenzhu.common.payonline.alipay.util.AlipayNotify;
import com.github.mysite.web.wenzhu.common.payonline.alipay.util.AlipaySubmit;
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
 * description:WS_WAP_PAYWAP支付宝手机网站支付管理，包含支付接入网关和服务器异步调用及同步通知
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/14 - 14:23
 */
public abstract class MobilePaymentMgr {

    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(MobilePaymentMgr.class);

    protected void accessGateWay(HttpServletRequest request) throws Exception {

        //1.先验证数据的合法性,校验通过,表示来源合法
        //

        //2.通过订单号获取整个订单信息，包含订单金额等等
        //

        //3.逻辑处理
        ////////////////////////////////// 手机网页即时到账授权接口  request_token/////////////////////////////////////////////
        String call_back_url = "";
        String notify_url = "";
        String merchant_url = "";
        String subject = "消费账单";
        String pay_expire = "21600";
        String total_fee = "0.01";
        String out_trade_no = "";
        String req_id = UUID.randomUUID().toString();

        // 封装授权接口的请求参数
        AlipayCreateDirect alipayDirect = new AlipayCreateDirect(out_trade_no, total_fee, subject,
                pay_expire, req_id, notify_url, call_back_url, merchant_url);

        // 建立请求
        String sHtmlTextToken = AlipaySubmit.mBuildRequest(AlipayCreateDirect.directGateWay, "", "",
                alipayDirect.buildMapParam());
        sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, alipayDirect.getInput_charset());
        // 获取授权token
        String request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);

        System.out.println("授权接口token:" + request_token);
        // 授权token 必须不为空
        if (StringUtils.isNotBlank(request_token)) {
            //////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute ////////////////////////
            AlipayAuthAndExecute alipayExecute = new AlipayAuthAndExecute();
            // 设置授权令牌 token
            alipayExecute.setRequest_token(request_token);

            String sHtmlTxt = AlipaySubmit.mBuildRequest(AlipayCreateDirect.directGateWay,
                    alipayExecute.buildMapParam(), "get", "确认");
            request.setAttribute("sHtmlText", sHtmlTxt);
        }
    }

    protected String baseExecuteAsync(HttpServletRequest request) {
        Map<String, String> params = EncapsuleRequestParaUtil.buildMapParam(request);
        boolean flag = false;
        if (null != params && !params.isEmpty()) {
            try {
                // RSA签名解密
                if (AlipayConfig.sign_type.equals("0001")) {
                    params = AlipayNotify.decrypt(params);
                }
                AlipayExecuteAsyncBackData backData = EncapsuleRequestParaUtil.request2Bean(request, AlipayExecuteAsyncBackData.class);
                LOG.debug("Alipay ExecuteASync Notify Param backData : [{}]", backData);
                if (null != params.get("notify_data")) {
                    // XML解析notify_data数据
                    Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
                    // 商户订单号
                    String out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
                    // 支付宝交易号
                    String trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
                    // 交易状态
                    String trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();

                    //买家支付宝账号
                    String buyer_email = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();
                    // 验证服务器异步通知的参数
                    boolean verify_result = AlipayNotify.mVerifyNotify(params);
                    LOG.debug("Alipay ExecuteASync Notify verify_result : [{}]", verify_result);
                    if (verify_result) {
                        // 验证成功
                        if (StringUtils.equals(trade_status, "TRADE_FINISHED") || StringUtils.equals(trade_status, "TRADE_SUCCESS")) {
                            this.bussinessDealCode(out_trade_no, trade_no, buyer_email);
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

    public void baseExecuteSync(HttpServletRequest request) {
        Map<String, String> params = EncapsuleRequestParaUtil.buildMapParam(request);
        AlipayExecuteSyncBackData backData = EncapsuleRequestParaUtil.request2Bean(request, AlipayExecuteSyncBackData.class);
        LOG.debug("Alipay ExecuteSync Notify Param backData : [{}]", backData);
        // 计算得出通知验证结果
        boolean verify_result = AlipayNotify.verifyReturn(params);
        LOG.debug("Alipay ExecuteSync Notify verify_result : [{}]", verify_result);
        if (verify_result) {
            if (StringUtils.equals(backData.getResult(), "success")) {
                // 验证成功
                this.bussinessDealCode(backData.getOut_trade_no(), backData.getTrade_no(), null);
            }

        }
    }

    /**
     * 商户业务逻辑方法
     *
     * @param out_trade_no 支付宝合作商户网站唯一订单号
     * @param trade_no     支付宝交易号
     * @param buyer_email  买家支付宝账号
     * @return
     */
    protected abstract void bussinessDealCode(String out_trade_no, String trade_no, String buyer_email);

}
