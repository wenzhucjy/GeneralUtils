package com.github.mysite.common.payonline._abc;

import com.abc.trustpay.client.*;

/**
 * description:中国农业银行支付返回XML数据,需进行GBK编码转换 —— 新电子商务接口V3.0.2
 *
 * @author  jy.chen
 * @version  1.0
 * @since  2015-8-27  10:47
 */
public class AbcPaymentXmlResultGBK extends TrxResponse {
    /**
     * 因系统的默认编码是UTF-8,而农行的接口编码格式为GBK,需转换为GBK
     */
    public static final String DEFAULT_ABC_CHARSET = "GBK";

    public AbcPaymentXmlResultGBK(String aMessage) throws TrxException {
        super("Notify", aMessage);
        LogWriter tLogWriter = new LogWriter();
        try {
            tLogWriter.logNewLine("TrustPayClient Java V3.0.2 交易开始==========================");
            tLogWriter.logNewLine("接收到的支付结果通知：\n[" + aMessage + "]");

            Base64 tBase64 = new Base64();
            String tMessage = new String(tBase64.decode(aMessage), DEFAULT_ABC_CHARSET);
            tLogWriter.logNewLine("经过Base64解码后的支付结果通知：\n[" + tMessage + "]");

            tLogWriter.logNewLine("验证支付结果通知的签名：");
            XMLDocument tResult = MerchantConfig.getUniqueInstance().verifySignXML(new XMLDocument(tMessage));
            tLogWriter.logNewLine("验证通过！\n 经过验证的支付结果通知：\n[" + tResult.toString() + "]");

            init(tResult);
        } catch (TrxException e) {
            setReturnCode(e.getCode());
            setErrorMessage(e.getMessage() + "-" + e.getDetailMessage());
            tLogWriter.log("验证失败！\n");

            tLogWriter.logNewLine("交易结束==================================================");
            try {
                tLogWriter.closeWriter(MerchantConfig.getTrxLogFile("PayResultLog"));
            } catch (Exception localException) {
                tLogWriter.log(localException.toString());
            }
        } catch (Exception e) {
            tLogWriter.log(e.toString());
        } finally {
            tLogWriter.logNewLine("交易结束==================================================");
            try {
                tLogWriter.closeWriter(MerchantConfig.getTrxLogFile("PayResultLog"));
            } catch (Exception localException1) {
                tLogWriter.log(localException1.toString());
            }
        }
    }
}
