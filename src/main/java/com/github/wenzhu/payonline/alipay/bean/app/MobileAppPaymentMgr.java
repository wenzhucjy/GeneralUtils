package com.github.wenzhu.payonline.alipay.bean.app;

import com.github.wenzhu.common.EncapsuleRequestParaUtil;
import com.github.wenzhu.payonline.alipay.util.AlipayNotify;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * description:支付宝SDK支付
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/14 - 14:09
 */
public abstract class MobileAppPaymentMgr {
    //支付宝服务器通知地址
    private final String ALIPAY_MOBILE_NOTIFYULR_SUFFIX = "/payment/mobile/alipay/ASyncNotify";
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(MobileAppPaymentMgr.class);

    /**
     * 组装支付宝SDK支付的请求参数URL
     *
     * @param request
     * @return
     */
    protected String bulidAlipayUrlData(HttpServletRequest request) {

        //截取访问路径的组拼为 http://host:ip/portal
        String rUrl = StringUtils.substringBeforeLast(request.getRequestURL().toString(), request.getContextPath()).concat(request
                .getContextPath());
        //服务器接收支付结果的后台地址
        String notifyUrl = String.format("%s%s", rUrl, this.ALIPAY_MOBILE_NOTIFYULR_SUFFIX);

        String total_fee = "0.01";
        String out_trade_no = "";

        //封装支付宝移动支付请求的参数
        AlipayMobileSendData sendData = new AlipayMobileSendData(out_trade_no, total_fee, "desc", "desc order", notifyUrl, "10d");
        String alipayData = sendData.generateSign();
        return alipayData;
    }

    /**
     * 支付宝主动发起的异步通知
     *
     * @param request 获取支付宝POST过来反馈信息
     * @return
     */
    protected String aSyncNotify(HttpServletRequest request) {

        boolean flag = false;
        Map<?, ?> requestParams = request.getParameterMap();
        if (!requestParams.isEmpty()) {
            //封装request参数
            AlipayMobileBackData backData = EncapsuleRequestParaUtil.request2Bean(request, AlipayMobileBackData.class);
            LOG.debug("Alipay mobile payment back data  :[{}]", backData);
            Map<String, String> params = newHashMap();
            for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            //验证签名
            boolean isVerify = AlipayNotify.verifyMobileAlipay(params);
            LOG.debug("alipay notify verify flag : [{}]", isVerify);
            //如果验证通过
            if (isVerify) {
                //若支付支付成功
                if (StringUtils.equals(backData.getTrade_status(), "TRADE_FINISHED") || StringUtils.equals(backData.getTrade_status(),
                        "TRADE_SUCCESS")) {
                    //商户逻辑处理
                    this.dealBussinessCode(backData);
                    flag = true;
                }
            }
        }
        LOG.debug("Mobile notifyMessage params :[{}] ", new Object[]{flag ? "success" : "fail"});
        return flag ? "success" : "fail";
    }

    /**
     * 商户业务逻辑处理
     *
     * @param backData 返回参数对象
     */
    protected abstract void dealBussinessCode(AlipayMobileBackData backData);

}
