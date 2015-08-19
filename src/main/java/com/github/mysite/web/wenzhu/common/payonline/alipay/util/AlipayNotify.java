package com.github.mysite.web.wenzhu.common.payonline.alipay.util;

import com.github.mysite.web.wenzhu.common.encrypt.MD5Encrypt;
import com.github.mysite.web.wenzhu.common.encrypt.RSA;
import com.github.mysite.web.wenzhu.common.payonline.alipay.AlipayConfig;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * description:处理支付宝各接口通知返回
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/14 - 14:54
 */
public class AlipayNotify {


    /**
     * 支付宝消息验证地址
     */
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    /**
     * 验证消息是否是支付宝发出的合法消息
     *
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params) {

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        String responseTxt = "true";
        if (params.get("notify_id") != null) {
            String notify_id = params.get("notify_id");
            responseTxt = verifyResponse(notify_id);
        }
        String sign = "";
        if (params.get("sign") != null) {
            sign = params.get("sign");
        }
        boolean isSign = getSignVeryfy(params, sign);

        /**
         * 写日志记录（若要调试，请取消下面两行注释）
         */
//        	String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 同步通知返回回来的参数：" + AlipayCore.createLinkString(params);
//
//	    	AlipayCore.logResult(sWord);

        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证消息是否是支付宝发出的合法消息
     *
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verifyMobileAlipay(Map<String, String> params) {

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        String responseTxt = "true";
        if (params.get("notify_id") != null) {
            String notify_id = params.get("notify_id");
            responseTxt = verifyResponse(notify_id);
        }
        String sign = "";
        if (params.get("sign") != null) {
            sign = params.get("sign");
        }
        boolean isSign = getSignVeryfyMobileAlipay(params, sign);

        //写日志记录（若要调试，请取消下面两行注释）
        String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n  移动支付异步服务器通知的参数：" + AlipayCore.createLinkString(params);
        AlipayCore.logResult(sWord, "alipay_log_mobileNotifyUrl_");

        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证消息是否是支付宝发出的合法消息 Notify_Url 异步通知页面
     *
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verifyNotify(Map<String, String> params) {

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        String responseTxt = "true";
        if (params.get("notify_id") != null) {
            String notify_id = params.get("notify_id");
            responseTxt = verifyResponse(notify_id);
        }
        String sign = "";
        if (params.get("sign") != null) {
            sign = params.get("sign");
        }
        boolean isSign = getSignVeryfy(params, sign);

        /**
         * 写日志记录（若要调试，请取消下面两行注释）
         */
        String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 异步通知返回回来的参数：" + AlipayCore.createLinkString(params);
        AlipayCore.logNotifyResult(sWord);

        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     *
     * @param Params 通知返回来的参数数组
     * @param sign   比对的签名结果
     * @return 生成的签名结果
     */
    private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
        //获取待签名字符串
        String preSignStr = AlipayCore.createLinkString(sParaNew);
        //获得签名验证结果
        boolean isSign = false;
        if (AlipayConfig.sign_type.equals("MD5")) {
            isSign = MD5Encrypt.verify(preSignStr, sign, AlipayConfig.key, AlipayConfig.input_charset);
        }
        return isSign;
    }


    /**
     * 根据反馈回来的信息，生成签名结果
     *
     * @param Params 通知返回来的参数数组
     * @param sign   比对的签名结果
     * @return 生成的签名结果
     */
    private static boolean getSignVeryfyMobileAlipay(Map<String, String> Params, String sign) {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
        //获取待签名字符串
        String preSignStr = AlipayCore.createLinkString(sParaNew);
        //获得签名验证结果
        boolean isSign = false;
        //因移动支付只存在RSA签名，固无需判断
        isSign = RSA.verify(preSignStr, sign, AlipayConfig.ali_public_key, AlipayConfig.input_charset);

        return isSign;
    }


    /**
     * 验证消息是否是支付宝发出的合法消息，验证callback
     *
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verifyReturn(Map<String, String> params) {
        String sign = "";
        //获取返回时的签名验证结果
        if (params.get("sign") != null) {
            sign = params.get("sign");
        }
        //验证签名
        boolean isSign = getSignVeryfy(params, sign, true);

        //写日志记录（若要调试，请取消下面两行注释）
        String sWord = "isSign=" + isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
        AlipayCore.logResult(sWord, "alipay_log_returnUrl_");

        //判断isSign是否为true
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        if (isSign) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证消息是否是支付宝发出的合法消息，验证服务器异步通知
     *
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean mVerifyNotify(Map<String, String> params) throws Exception {

        //获取是否是支付宝服务器发来的请求的验证结果
        String responseTxt = "true";
        try {
            //XML解析notify_data数据，获取notify_id
            Document document = DocumentHelper.parseText(params.get("notify_data"));
            String notify_id = document.selectSingleNode("//notify/notify_id").getText();
            responseTxt = verifyResponse(notify_id);
        } catch (Exception e) {
            responseTxt = e.toString();
        }

        //获取返回时的签名验证结果
        String sign = "";
        if (params.get("sign") != null) {
            sign = params.get("sign");
        }
        boolean isSign = getSignVeryfy(params, sign, false);

        //写日志记录（若要调试，请取消下面两行注释）
        String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
        AlipayCore.logNotifyResult(sWord);

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解密
     *
     * @param inputPara 要解密数据
     * @return 解密后结果
     */
    public static Map<String, String> decrypt(Map<String, String> inputPara) throws Exception {
        inputPara.put("notify_data", RSA.decrypt(inputPara.get("notify_data"), AlipayConfig.private_key, AlipayConfig.input_charset));
        return inputPara;
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     *
     * @param Params 通知返回来的参数数组
     * @param sign   比对的签名结果
     * @param isSort 是否排序
     * @return 生成的签名结果
     */
    private static boolean getSignVeryfy(Map<String, String> Params, String sign, boolean isSort) {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
        //获取待签名字符串
        String preSignStr = "";
        if (isSort) {
            preSignStr = AlipayCore.createLinkString(sParaNew);
        } else {
            preSignStr = AlipayCore.createLinkStringNoSort(sParaNew);
        }
        //获得签名验证结果
        boolean isSign = false;
        if (AlipayConfig.sign_type.equals("MD5")) {
            isSign = MD5Encrypt.verify(preSignStr, sign, AlipayConfig.key, AlipayConfig.input_charset);
        }
        if (AlipayConfig.sign_type.equals("0001")) {
            isSign = RSA.verify(preSignStr, sign, AlipayConfig.ali_public_key, AlipayConfig.input_charset);
        }
        return isSign;
    }

    /**
     * 获取远程服务器ATN结果,验证返回URL
     *
     * @param notify_id 通知校验ID
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private static String verifyResponse(String notify_id) {
        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求

        String partner = AlipayConfig.partner;
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    /**
     * 获取远程服务器ATN结果
     *
     * @param urlvalue 指定URL路径地址
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                    .getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }
}
