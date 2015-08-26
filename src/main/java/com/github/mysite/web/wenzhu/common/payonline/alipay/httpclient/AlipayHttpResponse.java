package com.github.mysite.web.wenzhu.common.payonline.alipay.httpclient;

import com.github.mysite.web.wenzhu.common.payonline.alipay.AlipayConfig;
import org.apache.commons.httpclient.Header;

import java.io.UnsupportedEncodingException;

/**
 * description:封装Http返回对象信息
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/14 - 11:00
 */
public class AlipayHttpResponse {


    /**
     * 返回中的Header信息
     */
    private Header[] responseHeaders;

    /**
     * String类型的result
     */
    private String stringResult;

    /**
     * btye类型的result
     */
    private byte[] byteResult;

    public Header[] getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Header[] responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public byte[] getByteResult() {
        if (byteResult != null) {
            return byteResult;
        }
        if (stringResult != null) {
            return stringResult.getBytes();
        }
        return null;
    }

    public void setByteResult(byte[] byteResult) {
        this.byteResult = byteResult;
    }

    public String getStringResult() throws UnsupportedEncodingException {
        if (stringResult != null) {
            return stringResult;
        }
        if (byteResult != null) {
            return new String(byteResult, AlipayConfig.input_charset);
        }
        return null;
    }

    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
    }
}
