package com.github.mysite.common.payonline._abc;

import com.google.common.base.MoreObjects;

/**
 * description:中国农业银行支付返回结果 —— 新电子商务接口V3.0.2
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/26 - 17:35
 */
public class AbcPaymentResult {

    private String orderNo;             // 订单号
    private String amount;              // 订单金额
    private String batchNo;             // 交易凭证号（建议使用 iRspRef 作为对账依据）
    private String hostDate;            // 银行交易日期（YYYY/MM/DD）
    private String hostTime;            // 银行交易时间（HH:MM:SS）
    private String voucherNo;           // 交易凭证号
    private String merchantRemarks;     // 商户的备注信息
    private String notifyType;          // 支付结果通知方式
    private String payType;             // 消费者支付方式
    private String rspRef;              // 银行返回交易流水号
    private String returnCode;          // 返回CODE

    /**
     * 默认构造方法
     */
    public AbcPaymentResult() {

    }

    /**
     * 带参数构造方法
     *
     * @param result
     */
    public AbcPaymentResult(AbcPaymentXmlResultGBK result) {
        this.orderNo = result.getValue("OrderNo");//订单号
        this.amount = result.getValue("Amount");//订单金额
        this.batchNo = result.getValue("BatchNo");//交易批次号
        this.voucherNo = result.getValue("VoucherNo");//交易凭证号
        this.hostTime = result.getValue("HostTime");
        this.hostDate = result.getValue("HostDate");
        this.merchantRemarks = result.getValue("MerchantRemarks");//商户的备注信息
        this.notifyType = result.getValue("NotifyType");//支付结果通知方式
        this.payType = result.getValue("PayType");
        this.rspRef = result.getValue("iRspRef");//银行返回交易流水号
        this.returnCode = result.getValue("ReturnCode");
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getAmount() {
        return amount;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public String getHostDate() {
        return hostDate;
    }

    public String getHostTime() {
        return hostTime;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public String getMerchantRemarks() {
        return merchantRemarks;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public String getPayType() {
        return payType;
    }

    public String getRspRef() {
        return rspRef;
    }

    public String getReturnCode() {
        return returnCode;
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
                .add("iRspRef", this.getRspRef())
                .add("ReturnCode", this.getReturnCode())
                .toString();
    }
}
