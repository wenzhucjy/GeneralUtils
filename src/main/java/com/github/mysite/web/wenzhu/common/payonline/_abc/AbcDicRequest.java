package com.github.mysite.web.wenzhu.common.payonline._abc;

import com.abc.trustpay.client.Constants;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;


/**
 * description:中国农业银行支付请求对象 —— 新电子商务接口V3.0.2
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:35
 */
public class AbcDicRequest {

    private String PaymentType;                         // 支付类型 必须设定， 1：农行卡支付2：国际卡支付3：农行贷记卡支付5：基于第三方的跨行支付 6：银联跨行支付 7：对公户  A:支付方式合并
    private String PaymentLinkType = "1";               // 交易渠道 必须设定， 1：internet 网络接入 2：手机网络接入 3：数字电视网络接入 4：智能客户端
    private String UnionPayLinkType;                    // 非必须，但是如果选择的支付帐户类型为 6(银联跨行支付)交易渠道为 2(手机网络接入)，必须设定 0：页面接入 1:客户端接入(暂不支持)
    private String ReceiveAccount;                      // 收款方账号 非必须
    private String ReceiveAccName;                      // 收款方户名 非必须
    private String NotifyType = "1";                    // 通知方式  必须设定， 0：URL 页面通知 1：服务器通知 
    private String ResultNotifyURL;                     // 通知 URL 地址 必须设定 
    private String MerchantRemarks;                     // 附言 非必须
    private String IsBreakAccount = "0";                // 交易是否分账 必须设定， 0:否；1:是
    private String SplitAccTemplate;                    // 分账模版编号 非必须

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getPaymentLinkType() {
        return PaymentLinkType;
    }

    public void setPaymentLinkType(String paymentLinkType) {
        PaymentLinkType = paymentLinkType;
    }

    public String getUnionPayLinkType() {
        return UnionPayLinkType;
    }

    public void setUnionPayLinkType(String unionPayLinkType) {
        UnionPayLinkType = unionPayLinkType;
    }

    public String getReceiveAccount() {
        return ReceiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        ReceiveAccount = receiveAccount;
    }

    public String getReceiveAccName() {
        return ReceiveAccName;
    }

    public void setReceiveAccName(String receiveAccName) {
        ReceiveAccName = receiveAccName;
    }

    public String getNotifyType() {
        return NotifyType;
    }

    public void setNotifyType(String notifyType) {
        NotifyType = notifyType;
    }

    public String getResultNotifyURL() {
        return ResultNotifyURL;
    }

    public void setResultNotifyURL(String resultNotifyURL) {
        ResultNotifyURL = resultNotifyURL;
    }

    public String getMerchantRemarks() {
        return MerchantRemarks;
    }

    public void setMerchantRemarks(String merchantRemarks) {
        MerchantRemarks = merchantRemarks;
    }

    public String getIsBreakAccount() {
        return IsBreakAccount;
    }

    public void setIsBreakAccount(String isBreakAccount) {
        IsBreakAccount = isBreakAccount;
    }

    public String getSplitAccTemplate() {
        return SplitAccTemplate;
    }

    public void setSplitAccTemplate(String splitAccTemplate) {
        SplitAccTemplate = splitAccTemplate;
    }

    /**
     * 默认构造方法
     */
    public AbcDicRequest() {

    }

    /**
     * 带参数构造方法
     *
     * @param paymentType     支付类型
     * @param resultNotifyURL 通知URL
     */
    public AbcDicRequest(String paymentType, String resultNotifyURL) {
        this.PaymentType = paymentType;
        this.ResultNotifyURL = resultNotifyURL;
    }

    public Map<String, String> bulidAbcDicRequestMap() {

        Map<String, String> retMap = Maps.newLinkedHashMap();
        retMap.put("PaymentType", Strings.nullToEmpty(this.PaymentType));            //设定支付类型
        retMap.put("PaymentLinkType", Strings.nullToEmpty(this.PaymentLinkType));    //设定支付接入方式
        if (StringUtils.equals(Constants.PAY_TYPE_UCBP, this.PaymentType) && StringUtils.equals(Constants.PAY_LINK_TYPE_MOBILE, this
                .PaymentLinkType)) {
            retMap.put("UnionPayLinkType", Strings.nullToEmpty(this.UnionPayLinkType));  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
        }
        retMap.put("ReceiveAccount", Strings.nullToEmpty(this.ReceiveAccount));      //设定收款方账号
        retMap.put("ReceiveAccName", Strings.nullToEmpty(this.ReceiveAccName));      //设定收款方户名
        retMap.put("NotifyType", Strings.nullToEmpty(this.NotifyType));              //设定通知方式
        retMap.put("ResultNotifyURL", Strings.nullToEmpty(this.ResultNotifyURL));    //设定通知URL地址
        retMap.put("MerchantRemarks", Strings.nullToEmpty(this.MerchantRemarks));    //设定附言
        retMap.put("IsBreakAccount", Strings.nullToEmpty(this.IsBreakAccount));      //设定交易是否分账
        retMap.put("SplitAccTemplate", Strings.nullToEmpty(this.SplitAccTemplate));  //分账模版编号
        return retMap;
    }

}
