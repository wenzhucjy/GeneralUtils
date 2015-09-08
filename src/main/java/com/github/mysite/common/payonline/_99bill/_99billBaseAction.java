package com.github.mysite.common.payonline._99bill;

import com.github.mysite.common.common.EncapsuleRequestParaUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * description: 快钱支付基础Action
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-09-08 16:53
 */
public abstract class _99billBaseAction {
    
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(_99billBaseAction.class);
    
    /**
     * 链接支付网关.
     *
     * @param request  HttpServletRequest
     * @param sendData the send data
     * @param type     类型 type = true 为 PC端， type = false为移动客户端
     * @return form 表单自动提交
     */
    protected String access99BillGateway(HttpServletRequest request, _99BillSendData sendData, boolean type) {
        //截取访问路径的组拼为 http://host:ip/portal
        String rUrl = StringUtils.substringBeforeLast(request.getRequestURL().toString(), request.getContextPath()).concat(request.getContextPath());
        //接收支付结果的页面地址,只请求一次
        String pageUrl = String.format("%s/ebank/99bill/notify/%s", rUrl, type ? "pageSync" : "mobilePageSync");
        //服务器接收支付结果的后台地址
        String bgUrl = String.format("%s/ebank/99bill/notify/%s", rUrl, type ? "bgASync" : "mobileBgASync");
        sendData.setBgUrl(bgUrl);
        sendData.setPageUrl(pageUrl);
        // true 表示PC端访问
        String sHtmlText = sendData.bulidBillRequestUrl(type);
        request.setAttribute("sHtmlText", sHtmlText);
        LOG.debug("99bill sHtmlText : [{}]", sHtmlText);
        return type ? "pc/accessGateWay" : "mobile/mAccessGateWay";
    }

    /**
     * 快钱支付返回 notify bgUrl(异步通知) , pageUrl(页面通知)
     *
     * @param request HttpServletRequest
     * @return 交易类型 + 序列号
     */
    protected String notifyBgUrl(HttpServletRequest request) {
        Map<?, ?> requestParams = request.getParameterMap();
        if (!requestParams.isEmpty()) {
            // 封装request参数
            _99BillBackData backData = EncapsuleRequestParaUtil.request2Bean(request, _99BillBackData.class);
            LOG.debug("99bill backData : [{}]", backData);
            //用于标注验证URL签名的合法性 false 非法,true 合法
            boolean verifyFlag = backData.enCodeBy99billCer();

            LOG.debug("99bill enCode By Cer :[{}]", verifyFlag);
            if (verifyFlag) {
                // 支付成功
                if (StringUtils.equals(backData.getPayResult(), _99BillBackData.PayResults[0])) {
                    return this.bussinessDealCode(backData);
                }
            }
        }
        return null;
    }

    protected abstract String bussinessDealCode(_99BillBackData backData);
}
