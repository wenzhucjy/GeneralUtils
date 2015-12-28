package com.github.mysite.common.payonline._99bill;

import com.github.mysite.common.payonline.util.RequestHelper;
import com.github.mysite.common.payonline.util.SettingConstants;
import com.github.mysite.common.payonline.util.SettingUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * description:快钱支付封装请求参数
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-11-30  17:30
 */
public class _99BillSendData {


    public static final String  BILL_GATEWAY = "https://www.99bill.com/gateway/recvMerchantInfoAction.htm"; // 网关提交地址
    public static final String MOBILE_BILL_GATEWAY = "https://www.99bill.com/mobilegateway/recvMerchantInfoAction.htm";//移动网关地址
    
    public static final String INPUT_CHARSET = "1";             // 字符集 1 代表 UTF-8; 2 代表 GBK; 3 代表 GB2312
    public static final String VERSION = "v2.0";                // 网关版本，固定值：v2.0
    public static final String MOBILE_VERSION = "mobile1.0";    //网关版本
    public static final String MOBILE_GATEWAY = "phone";        //移动网关版本，当version= mobile1.0时有效phone代表手机版移动网关，pad代表平板移动网关，默认为phone
    public static final String LANGUAGE = "1";                  // 语言种类，1代表中文显示，2代表英文显示。默认为1
    public static final String SIGN_TYPE = "4";                 // 签名类型,该值为4，代表PKI加密方式(DSA 戒者 RSA签名方式),该参数必填
    public static final String PAY_TYPE = "00";                 // 支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10（只显示银行卡支付方式），必填
    public static final String REDO_FLAG = "0";                 // 同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空

    private String MERCHANT_ACCT_ID = SettingUtils.getProperty(SettingConstants.MERCHANT_ACCT_ID);  // 人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填
    private String pageUrl;                                     // 接收支付结果的页面地址，该参数一般置为空即可
    private String bgUrl;                                       // 服务器接收支付结果的后台地址，该参数务必填写，不能为空
    private String payerName;                                   // 支付人姓名,可以为空
    private String payerContactType;                            // 支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空
    private String payerContact;                                // 支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空

    private String payerIdType;                                 // 指定付款人，可为空
    private String payerId;                                     // 付款人标识，可为空
    private String payerIP;                                     // 付款人IP，商家传递获取到的客户端 IP（高钓鱼风险商户必填）

    private String orderId;                                     // 商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空
    private String orderAmount;                                 // 订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试。该参数必填
    private String orderTime;                                   // 订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空

    private String orderTimestamp;                              // 快钱时间戳（高钓鱼风险商户），格式：yyyyMMddHHmmss，如：20071117020101，可为空

    private String productName;                                 // 商品名称，可以为空
    private String productNum;                                  // 商品数量，可以为空
    private String productId;                                   // 商品代码，可以为空
    private String productDesc;                                 // 商品描述，可以为空
    private String ext1;                                        // 扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空
    private String ext2;                                        // 扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空
    private String bankId;                                      // 银行代码，如果payType为00，该值可以为空；如果payType为10，该值必须填写，具体请参考银行列表

    private String cardIssuer;                                  // 发卡机构，99BILL：支持预付卡和快运通，99BILL_EAP：支持快运通，包括采购卡，可为空
    private String cardNum;                                     // 卡号，整形数字，仅当 payType 为 15（信用卡支付） 、17（储值卡支付）时有效可为空
    private String remitType;                                   // 线下汇款类型，1 代表银行柜台汇款2 代表邮局汇款，可为空
    private String remitCode;                                   // 汇款识别码，可为空

    private String pid;                                         // 快钱合作伙伴的帐户号，即商户编号，可为空

    private String submitType;                                  //00:代表前台提交 01：代表后台提交，为空默认为前台提交
    private String orderTimeOut;                                // 交易超时时间，正整数,0~ 2592000 （30天），默讣为空，为空表示订单无超时时间，可为空
    private String extDataType;                                 // 附加信息类型，可为空
    private String extDataContent;                              // 附加信息

    /**
     * 加密的拼接数据
     *
     * @param type 类型 type = true 为 人民币网关接入， type = false为移动网关接入
     * @return 签名字符串 signMsg
     */
    public String encryptSrc(boolean type) {
        Map<String, String> paramsMap = buildParamsMap(type);
        //过滤掉map 中 value 为 null或"" 对应的key
        Map<String, String> stringObjectMap = Maps.filterValues(paramsMap, StringUtils::isNotBlank);
        //拼装成 param1=value1&...&paramn=valuen 的待加密字符串
        return Joiner.on("&").withKeyValueSeparator("=").join(stringObjectMap);
    }

    /**
     * 拼装request 请求参数,form表单.
     *
     * @param type 类型 type = true 为 人民币网关接入， type = false为移动网关接入
     * @return form表单数据
     */
    public String bulidBillRequestUrl(boolean type) {
        Map<String, String> sParaTemp = buildParamsMap(type);
        sParaTemp.put("signMsg", Pkipair.signMsg(encryptSrc(type)));
        // 重新组装 form 表单数据
        return RequestHelper.buildPaymentRequestPara(sParaTemp, "POST", type ? BILL_GATEWAY : MOBILE_BILL_GATEWAY, "SUB_MIT");
    }

    private Map<String, String> buildParamsMap(boolean type) {
        Map<String, String> sParaTemp = Maps.newLinkedHashMap();
        sParaTemp.put("inputCharset", INPUT_CHARSET);
        sParaTemp.put("pageUrl", this.pageUrl);
        sParaTemp.put("bgUrl", this.bgUrl);
        sParaTemp.put("version", type ? VERSION : MOBILE_VERSION);
        sParaTemp.put("language", LANGUAGE);
        sParaTemp.put("signType", SIGN_TYPE);
        sParaTemp.put("merchantAcctId", MERCHANT_ACCT_ID);
        sParaTemp.put("payerName", Strings.nullToEmpty(this.payerName));
        sParaTemp.put("payerContactType", Strings.nullToEmpty(this.payerContactType));
        sParaTemp.put("payerContact", Strings.nullToEmpty(this.payerContact));
        sParaTemp.put("payerIdType", Strings.nullToEmpty(this.payerIdType));
        sParaTemp.put("payerId", Strings.nullToEmpty(this.payerId));
        sParaTemp.put("payerIP", Strings.nullToEmpty(this.payerIP));
        sParaTemp.put("orderId", Strings.nullToEmpty(this.orderId));
        sParaTemp.put("orderAmount", Strings.nullToEmpty(this.orderAmount));
        sParaTemp.put("orderTime", Strings.nullToEmpty(this.orderTime));
        sParaTemp.put("orderTimestamp", Strings.nullToEmpty(this.orderTimestamp));
        sParaTemp.put("productName", Strings.nullToEmpty(this.productName));
        sParaTemp.put("productNum", Strings.nullToEmpty(this.productNum));
        sParaTemp.put("productId", Strings.nullToEmpty(this.productId));
        sParaTemp.put("productDesc", Strings.nullToEmpty(this.productDesc));
        sParaTemp.put("ext1", Strings.nullToEmpty(this.ext1));
        sParaTemp.put("ext2", Strings.nullToEmpty(this.ext2));
        sParaTemp.put("payType", Strings.nullToEmpty(PAY_TYPE));
        sParaTemp.put("bankId", Strings.nullToEmpty(this.bankId));
        sParaTemp.put("cardIssuer", Strings.nullToEmpty(this.cardIssuer));
        sParaTemp.put("cardNum", Strings.nullToEmpty(this.cardNum));
        sParaTemp.put("remitType", Strings.nullToEmpty(this.remitType));
        sParaTemp.put("remitCode", Strings.nullToEmpty(this.remitCode));
        sParaTemp.put("redoFlag", Strings.nullToEmpty(REDO_FLAG));
        sParaTemp.put("pid", Strings.nullToEmpty(this.pid));
        sParaTemp.put("submitType", Strings.nullToEmpty(this.submitType));
        sParaTemp.put("orderTimeOut", Strings.nullToEmpty(this.orderTimeOut));
        sParaTemp.put("extDataType", Strings.nullToEmpty(this.extDataType));
        sParaTemp.put("extDataContent", Strings.nullToEmpty(this.extDataContent));
        if (!type) {
            sParaTemp.put("mobileGateway", Strings.nullToEmpty(MOBILE_GATEWAY));
        }
        return sParaTemp;
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
     * @param bgUrl       异步调用地址
     * @param pageUrl     页面调用地址
     * @param ext1        扩展字段1
     */
    public _99BillSendData(String orderId, String orderAmount, String orderTime, String productName, String bgUrl,String pageUrl,String ext1) {
        this.orderId = orderId;
        this.orderAmount = orderAmount;
        this.orderTime = orderTime;
        this.productName = productName;
        this.bgUrl = bgUrl;
        this.pageUrl = pageUrl;
        this.ext1 = ext1;
    }
    
}
