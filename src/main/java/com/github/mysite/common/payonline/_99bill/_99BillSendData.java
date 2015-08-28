package com.github.mysite.common.payonline._99bill;

import com.github.mysite.common.common.EncapsuleRequestParaUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * description:快钱支付封装请求参数
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:30
 */
public class _99BillSendData {


    public static final String  BILL_GATEWAY = "https://www.99bill.com/gateway/recvMerchantInfoAction.htm"; // 网关提交地址
    public static final String MOBILE_BILL_GATEWAY = "https://www.99bill.com/mobilegateway/recvMerchantInfoAction.htm";//移动网关地址
    private String inputCharset = "1"; // 字符集 1 代表 UTF-8; 2 代表 GBK; 3 代表 GB2312
    private String merchantAcctId = "1002418164901"; // 人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填
    private String pageUrl; // 接收支付结果的页面地址，该参数一般置为空即可
    private String bgUrl; // 服务器接收支付结果的后台地址，该参数务必填写，不能为空
    private String version = "v2.0";// 网关版本，固定值：v2.0
    private String mobileVersion = "mobile1.0";//网关版本
    private String mobileGateway = "phone";//移动网关版本，当version= mobile1.0时有效phone代表手机版移动网关，pad代表平板移动网关，默认为phone
    private String language = "1"; // 语言种类，1代表中文显示，2代表英文显示。默认为1
    private String signType = "4"; // 签名类型,该值为4，代表PKI加密方式(DSA 戒者 RSA签名方式),该参数必填
    private String payerName;// 支付人姓名,可以为空
    private String payerContactType;// 支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空
    private String payerContact;// 支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空
    //
    private String payerIdType; // 指定付款人，可为空
    private String payerId; // 付款人标识，可为空
    private String payerIP; // 付款人IP，商家传递获取到的客户端 IP（高钓鱼风险商户必填）

    private String orderId;// 商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空
    private String orderAmount;// 订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试。该参数必填
    private String orderTime; // 订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空
    //
    private String orderTimestamp; // 快钱时间戳（高钓鱼风险商户），格式：yyyyMMddHHmmss，如：20071117020101，可为空

    private String productName;// 商品名称，可以为空
    private String productNum;// 商品数量，可以为空
    private String productId;// 商品代码，可以为空
    private String productDesc;// 商品描述，可以为空
    private String ext1;// 扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空
    private String ext2;// 扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空
    private String payType = "00";// 支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10（只显示银行卡支付方式），必填
    private String bankId;// 银行代码，如果payType为00，该值可以为空；如果payType为10，该值必须填写，具体请参考银行列表
    //
    private String cardIssuer; // 发卡机构，99BILL：支持预付卡和快运通，99BILL_EAP：支持快运通，包括采购卡，可为空
    private String cardNum; // 卡号，整形数字，仅当 payType 为 15（信用卡支付） 、17（储值卡支付）时有效可为空
    private String remitType; // 线下汇款类型，1 代表银行柜台汇款2 代表邮局汇款，可为空
    private String remitCode; // 汇款识别码，可为空

    private String redoFlag = "0";// 同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空
    private String pid;// 快钱合作伙伴的帐户号，即商户编号，可为空
    //
    private String submitType;   //00:代表前台提交 01：代表后台提交，为空默认为前台提交
    private String orderTimeOut; // 交易超时时间，正整数,0~ 2592000 （30
    // 天），默讣为空，为空表示订单无超时时间，可为空
    private String extDataType; // 附加信息类型，可为空
    private String extDataContent; // 附加信息

    private String signMsg; // 签名字符串

    /**
     * 加密的拼接数据.
     *
     * @param type 类型 type = true 为 PC端， type = false为客户端
     * @return the string
     */
    public String encryptSrc(boolean type) {
        this.signMsg =
                StringUtils.join("inputCharset=", inputCharset,
                        "&pageUrl=", pageUrl,
                        "&bgUrl=", bgUrl,
                        "&version=", type ? version : mobileVersion,
                        "&language=", language,
                        "&signType=", signType,
                        "&merchantAcctId=", merchantAcctId,
                        StringUtils.isNotBlank(payerName) ? StringUtils.join("&payerName=", payerName) : "",
                        StringUtils.isNotBlank(payerContactType) ? StringUtils.join("&payerContactType=", payerContactType) : "",
                        StringUtils.isNotBlank(payerContact) ? StringUtils.join("&payerContact=", payerContact) : "",
                        StringUtils.isNotBlank(payerIdType) ? StringUtils.join("&payerIdType=", payerIdType) : "",
                        StringUtils.isNotBlank(payerId) ? StringUtils.join("&payerId=", payerId) : "",
                        StringUtils.isNotBlank(payerIP) ? StringUtils.join("&payerIP=", payerIP) : "",
                        StringUtils.isNotBlank(payerName) ? StringUtils.join("&payerName=", payerName) : "",
                        "&orderId=", orderId,
                        "&orderAmount=", orderAmount,
                        "&orderTime=", orderTime,
                        StringUtils.isNotBlank(orderTimestamp) ? StringUtils.join("&orderTimestamp=", orderTimestamp) : "",
                        StringUtils.isNotBlank(productName) ? StringUtils.join("&productName=", productName) : "",
                        StringUtils.isNotBlank(productNum) ? StringUtils.join("&productNum=", productNum) : "",
                        StringUtils.isNotBlank(productId) ? StringUtils.join("&productId=", productId) : "",
                        StringUtils.isNotBlank(productDesc) ? StringUtils.join("&productDesc=", productDesc) : "",
                        StringUtils.isNotBlank(ext1) ? StringUtils.join("&ext1=", ext1) : "",
                        StringUtils.isNotBlank(ext2) ? StringUtils.join("&ext2=", ext2) : "",
                        "&payType=", payType,
                        StringUtils.isNotBlank(bankId) ? StringUtils.join("&bankId=", bankId) : "",
                        StringUtils.isNotBlank(cardIssuer) ? StringUtils.join("&cardIssuer=", cardIssuer) : "",
                        StringUtils.isNotBlank(cardNum) ? StringUtils.join("&cardNum=", cardNum) : "",
                        StringUtils.isNotBlank(remitType) ? StringUtils.join("&remitType=", remitType) : "",
                        StringUtils.isNotBlank(remitCode) ? StringUtils.join("&remitCode=", remitCode) : "",
                        StringUtils.isNotBlank(redoFlag) ? StringUtils.join("&redoFlag=", redoFlag) : "",
                        StringUtils.isNotBlank(pid) ? StringUtils.join("&pid=", pid) : "",
                        StringUtils.isNotBlank(submitType) ? StringUtils.join("&submitType=", submitType) : "",
                        StringUtils.isNotBlank(orderTimeOut) ? StringUtils.join("&orderTimeOut=", orderTimeOut) : "",
                        StringUtils.isNotBlank(extDataType) ? StringUtils.join("&extDataType=", extDataType) : "",
                        StringUtils.isNotBlank(extDataContent) ? StringUtils.join("&extDataContent=", extDataContent) : ""
                        //移动网关版本，针对客户端访问
                        , type ? "" : StringUtils.join("&mobileGateway=", mobileGateway)
                );
        return this.signMsg;
    }

    /**
     * 拼装request 请求参数,form表单.
     *
     * @param type 类型 type = true 为 PC端， type = false为客户端
     * @return the string
     */
    public String bulidBillRequestUrl(boolean type) {
        Map<String, String> sParaTemp = Maps.newLinkedHashMap();
        sParaTemp.put("inputCharset", this.inputCharset);
        sParaTemp.put("pageUrl", this.pageUrl);
        sParaTemp.put("bgUrl", this.bgUrl);
        sParaTemp.put("version", type ? this.version : this.mobileVersion);
        sParaTemp.put("language", this.language);
        sParaTemp.put("signType", this.signType);
        sParaTemp.put("merchantAcctId", this.merchantAcctId);
        sParaTemp.put("payerName", StringUtils.join("", this.payerName));
        sParaTemp.put("payerContactType", StringUtils.join("", this.payerContactType));
        sParaTemp.put("payerContact", StringUtils.join("", this.payerContact));
        sParaTemp.put("payerIdType", StringUtils.join("", this.payerIdType));
        sParaTemp.put("payerId", StringUtils.join("", this.payerId));
        sParaTemp.put("payerIP", StringUtils.join("", this.payerIP));
        sParaTemp.put("orderId", StringUtils.join("", this.orderId));
        sParaTemp.put("orderAmount", StringUtils.join("", this.orderAmount));
        sParaTemp.put("orderTime", StringUtils.join("", this.orderTime));
        sParaTemp.put("orderTimestamp", StringUtils.join("", this.orderTimestamp));
        sParaTemp.put("productName", StringUtils.join("", this.productName));
        sParaTemp.put("productNum", StringUtils.join("", this.productNum));
        sParaTemp.put("productId", StringUtils.join("", this.productId));
        sParaTemp.put("productDesc", StringUtils.join("", this.productDesc));
        sParaTemp.put("ext1", StringUtils.join("", this.ext1));
        sParaTemp.put("ext2", StringUtils.join("", this.ext2));
        sParaTemp.put("payType", StringUtils.join("", this.payType));
        sParaTemp.put("bankId", StringUtils.join("", this.bankId));
        sParaTemp.put("cardIssuer", StringUtils.join("", this.cardIssuer));
        sParaTemp.put("cardNum", StringUtils.join("", this.cardNum));
        sParaTemp.put("remitType", StringUtils.join("", this.remitType));
        sParaTemp.put("remitCode", StringUtils.join("", this.remitCode));
        sParaTemp.put("redoFlag", StringUtils.join("", this.redoFlag));
        sParaTemp.put("pid", StringUtils.join("", this.pid));
        sParaTemp.put("submitType", StringUtils.join("", this.submitType));
        sParaTemp.put("orderTimeOut", StringUtils.join("", this.orderTimeOut));
        sParaTemp.put("extDataType", StringUtils.join("", this.extDataType));
        sParaTemp.put("extDataContent", StringUtils.join("", this.extDataContent));
        if (!type) {
            sParaTemp.put("mobileGateway", StringUtils.join("", this.mobileGateway));
        }
        sParaTemp.put("signMsg", Pkipair.signMsg(encryptSrc(type)));
        // 重新组装 form 表单数据
        return EncapsuleRequestParaUtil.buildPaymentRequestPara(sParaTemp, "POST", type ? BILL_GATEWAY : MOBILE_BILL_GATEWAY, "SUB_MIT");
    }


    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }

    /**
     * 构造方法
     *
     * @param orderId     订单号
     * @param orderAmount 订单金额
     * @param orderTime   订单时间
     * @param ext1        用于区分订单的类型(商城道具,充鱼订单,激活订单)
     */
    public _99BillSendData(String orderId, String orderAmount, String orderTime, String productName, String ext1) {
        this.orderId = orderId;
        this.orderAmount = orderAmount;
        this.orderTime = orderTime;
        this.productName = productName;
        this.ext1 = ext1;
    }

}
