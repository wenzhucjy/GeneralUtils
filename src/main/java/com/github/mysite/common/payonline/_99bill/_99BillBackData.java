package com.github.mysite.common.payonline._99bill;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.MoreObjects;

/**
 * description:快钱支付返回的封装数据
 *
 * @author  jy.chen
 * @version  1.0
 * @since 2015-11-30 - 17:29
 */
public class _99BillBackData {

    /**
     * 处理结果， 10支付成功，11 支付失败，00订单申请成功，01 订单申请失败
     */
	public static String[]	PayResults	= new String[] { "10", "11", "00", "01" };

	private String			merchantAcctId;										    // 人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填
	private String			version;												// 网关版本，固定值：v2.0；若为移动网关，则为mobile1.0
	private String			language;												// 语言种类，1代表中文显示，2代表英文显示。默认为1
	private String			signType;												// 签名类型,该值为4，代表PKI加密方式(DSA或者 RSA签名方式),该参数必填
	private String			payType;												// 支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10（只显示银行卡支付方式），必填
	private String			bankId;												    // 银行代码，如果payType为00，该值可以为空；如果payType为10，该值必须填写，具体请参考银行列表
	private String			orderId;												// 商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空
	private String			orderTime;												// 订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空
	private String			orderAmount;											// 订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试。该参数必填

	private String			bindCard;												// 已绑短卡号
	private String			bindMobile;												// 已绑短手机尾号

	private String			dealId;												    // 快钱交易号，商户每一笔交易都会在快钱生成一个交易号
	private String			bankDealId;												// 银行交易号
																					// ，快钱交易在银行支付时对应的交易号，如果不是通过银行卡支付，则为空
	private String			dealTime;												// 快钱交易时间，快钱对交易进行处理的时间,格式：yyyyMMddHHmmss
	private String			payAmount;												// 商户实际支付金额以分为单位。比方10元，提交时金额应为1000。该金额代表商户快钱账户最终收到的金额
	private String			fee;													// 费用，快钱收取商户的手续费，单位为分
	private String			ext1;													// 扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空
	private String			ext2;													// 扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空
	private String			payResult;												// 处理结果，
																					// 10支付成功，11
																					// 支付失败，00订单申请成功，01
																					// 订单申请失败
	private String			errCode;												// 错误代码
																					// ，请参照《人民币网关接口文档》最后部分的详细解释

	private String			signMsgVal;											    // 待签名的拼接字符串
	private String			signMsg;												// 快钱返回的签名字符串

    public String getMerchantAcctId() {
        return merchantAcctId;
    }

    public void setMerchantAcctId(String merchantAcctId) {
        this.merchantAcctId = merchantAcctId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getBindCard() {
        return bindCard;
    }

    public void setBindCard(String bindCard) {
        this.bindCard = bindCard;
    }

    public String getBindMobile() {
        return bindMobile;
    }

    public void setBindMobile(String bindMobile) {
        this.bindMobile = bindMobile;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getBankDealId() {
        return bankDealId;
    }

    public void setBankDealId(String bankDealId) {
        this.bankDealId = bankDealId;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    /**
     * 快钱返回给商户时的组成加密串
     *
     * @return 待解密的加密字符串
     */
    public String getSignMsgVal() {
        
        this.signMsgVal =
                StringUtils.join(
                        "merchantAcctId=", merchantAcctId,
                        "&version=", version,
                        "&language=", language,
                        "&signType=", signType,
                        StringUtils.isNotBlank(payType) ? StringUtils.join("&payType=", payType) : "",
                        StringUtils.isNotBlank(bankId) ? StringUtils.join("&bankId=", bankId) : "",
                        "&orderId=", orderId,
                        "&orderTime=", orderTime,
                        "&orderAmount=", orderAmount,
                        StringUtils.isNotBlank(bindCard) ? StringUtils.join("&bindCard=", bindCard) : "",
                        StringUtils.isNotBlank(bindMobile) ? StringUtils.join("&bindMobile=", bindMobile) : "",
                        StringUtils.isNotBlank(dealId) ? StringUtils.join("&dealId=", dealId) : "",
                        StringUtils.isNotBlank(bankDealId) ? StringUtils.join("&bankDealId=", bankDealId) : "",
                        StringUtils.isNotBlank(dealTime) ? StringUtils.join("&dealTime=", dealTime) : "",
                        StringUtils.isNotBlank(payAmount) ? StringUtils.join("&payAmount=", payAmount) : "",
                        StringUtils.isNotBlank(fee) ? StringUtils.join("&fee=", fee) : "",
                        StringUtils.isNotBlank(ext1) ? StringUtils.join("&ext1=", ext1) : "",
                        StringUtils.isNotBlank(ext2) ? StringUtils.join("&ext2=", ext2) : "",
                        StringUtils.isNotBlank(payResult) ? StringUtils.join("&payResult=", payResult) : "",
                        StringUtils.isNotBlank(errCode) ? StringUtils.join("&errCode=", errCode) : ""
                );
        return this.signMsgVal;
    }

    /**
     * 用快钱的公钥进行验签
     *
     * @return  验签结果 true 为验签成功，false 为验签失败
     */
    public boolean enCodeBy99billCer() {
        return Pkipair.enCodeByCer(getSignMsgVal(), this.signMsg);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("merchantAcctId", merchantAcctId)
                .add("version", version)
                .add("language", language)
                .add("signType", signType)
                .add("payType", payType)
                .add("bankId", bankId)
                .add("orderId", orderId)
                .add("orderTime", orderTime)
                .add("orderAmount", orderAmount)
                .add("bindCard", bindCard)
                .add("bindMobile", bindMobile)
                .add("dealId", dealId)
                .add("bankDealId", bankDealId)
                .add("dealTime", dealTime)
                .add("payAmount", payAmount)
                .add("fee", fee)
                .add("ext1", ext1)
                .add("ext2", ext2)
                .add("payResult", payResult)
                .add("errCode", errCode)
                .add("signMsgVal", signMsgVal)
                .add("signMsg", signMsg)
                .toString();
    }
}
