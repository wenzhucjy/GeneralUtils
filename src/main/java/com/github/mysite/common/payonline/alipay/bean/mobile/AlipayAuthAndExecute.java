package com.github.mysite.common.payonline.alipay.bean.mobile;

import com.github.mysite.common.payonline.alipay.AlipayConfig;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * description:
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:26
 */
public class AlipayAuthAndExecute {

    /*   基本参数   start  */
    private String service = "alipay.wap.auth.authAndExecute";        //接口名称
    private String format = "xml";                                    //请求参数格式
    private String v = "2.0";                                         //接口版本号
    private String partner = AlipayConfig.partner;                    //合作者身份 ID
    private String input_charset = AlipayConfig.input_charset;        // 字符集
    private String sec_id = "MD5";                                    //签名方式  MD5 or RSA,若设置RSA则用0001代替
    private String sign;                                            //对请求或响应中参数签名后的值
	/*   基本参数   end  */

    /*   业务参数   start  */
    private String req_data;                                        //请求业务参数
    /*   业务参数   end    */
	/*   req_data 请求业务参数 start */
    private String request_token;                                    //授权令牌,授权令牌，调用“手机网页即时到账授权接口(alipay.wap.trade.create.direct)”成功后返回该值。此参数值不能更改


    /*   req_data 请求业务参数 end   */
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * 交易接口 - 业务参数
     *
     * @return
     */
    public String getReq_data() {
        this.req_data = "<auth_and_execute_req><request_token>" + request_token + "</request_token></auth_and_execute_req>";
        return this.req_data;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    public String getFormat() {
        return format;
    }

    public String getV() {
        return v;
    }

    public String getPartner() {
        return partner;
    }

    public String getSec_id() {
        return sec_id;
    }

    public String getService() {
        return service;
    }

    public Map<String, String> buildMapParam() {
        //把请求参数打包成数组
        Map<String, String> sParaTemp = Maps.newHashMap();
        sParaTemp.put("service", this.service);
        sParaTemp.put("partner", this.partner);
        sParaTemp.put("_input_charset", this.input_charset);
        sParaTemp.put("sec_id", this.sec_id);
        sParaTemp.put("format", this.format);
        sParaTemp.put("v", this.v);
        sParaTemp.put("req_data", getReq_data());
        return sParaTemp;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("service", service)
                .add("format", format)
                .add("v", v)
                .add("partner", partner)
                .add("input_charset", input_charset)
                .add("sec_id", sec_id)
                .add("sign", sign)
                .add("req_data", req_data)
                .add("request_token", request_token)
                .toString();
    }
}
