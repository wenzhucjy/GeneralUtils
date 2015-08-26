package com.github.mysite.common.payonline._abc;

import com.abc.trustpay.client.TrxException;
import com.abc.trustpay.client.ebus.PaymentResult;
import com.google.common.base.MoreObjects;

/**
 * description:中国农业银行支付返回信息 —— 新电子商务接口V3.0.2
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/25 - 17:42
 */
public class AbcPaymentResult extends PaymentResult {
    
    
    public AbcPaymentResult(String aMessage) throws TrxException {
        super(aMessage);
    }

    // 订单编号
    public String getOrderNo() {
        return this.getValue("OrderNo");
    }

    // 订单金额
    public String getAmount() {
        return this.getValue("Amount");
    }

    // 交易批次号
    public String getBatchNo() {
       return this.getValue("BatchNo");
    }

    // 交易凭证号（建议使用 iRspRef 作为对账依据）
    public String getVoucherNo() {
       return this.getValue("VoucherNo");
    }

    // 银行交易日期（YYYY/MM/DD）
    public String getHostDate() {
        return this.getValue("HostDate");
    }

    // 银行交易时间（HH:MM:SS）
    public String getHostTime() {
       return this.getValue("HostTime");
    }

    // 商户备注信息（商户在支付请求时所提交的信息）
    public String getMerchantRemarks() {
        return this.getValue("MerchantRemarks");
    }

    // 消费者支付方式
    public String getPayType() {
        return this.getValue("PayType");
    }

    // 支付结果通知方式
    public String getNotifyType() {
       return this.getValue("NotifyType");
    }

    // 银行返回交易流水号
    public String getiRspRef() {
       return this.getValue("iRspRef");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("orderNo", this.getOrderNo())
                .add("amount", this.getAmount())
                .add("batchNo", this.getBatchNo())
                .add("voucherNo", this.getVoucherNo())
                .add("hostDate", this.getHostDate())
                .add("hostTime", this.getHostTime())
                .add("merchantRemarks", this.getMerchantRemarks())
                .add("payType", this.getPayType())
                .add("notifyType", this.getNotifyType())
                .add("iRspRef", this.getiRspRef())
                .add("ReturnCode",this.getReturnCode())
                .toString();
    }
}
