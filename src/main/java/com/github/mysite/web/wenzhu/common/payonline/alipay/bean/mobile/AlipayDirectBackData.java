package com.github.mysite.web.wenzhu.common.payonline.alipay.bean.mobile;

import com.google.common.base.MoreObjects;

/**
 * description:手机网页即时到账授权接口html同步返回参数
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/12 - 17:26
 */
public class AlipayDirectBackData {

    /*   基本参数   start  */
    private String partner;                                            //合作者身份 ID
    private String req_id;                                            //请求号，须保证每次请求都是唯一,最长32位
    private String sec_id;                                            //签名方式  MD5 or RSA,若设置RSA则用0001代替
    private String service;                                            //接口名称
    private String v;                                                //接口版本号
    private String format;                                            //请求参数格式
    private String sign;                                            //对请求或响应中参数签名后的值

	/*   基本参数   end  */

    /*   业务参数   start  */
    private String res_data;                                        //返回业务参数
    private String res_error;                                        //返回错误参数,请求失败后才会返回该值。包含请求失败的错误码和错误原因
    /*   业务参数  end    */

    /* res_data 返回业务参数   start */
    private String request_token;                                    //授权令牌，请求成功后才返回该值
	/* res_data 返回业务参数   end */

    /* res_error 返回业务参数   start */
    private String code;                                            //请求失败时，返回的错误代码
    private String sub_code;                                        //错误子代码
    private String msg;                                                //错误原因
    private String detail;                                            //错误详细描述
	/* res_error 返回业务参数   end */

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getSec_id() {
        return sec_id;
    }

    public void setSec_id(String sec_id) {
        this.sec_id = sec_id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRes_data() {
        return res_data;
    }

    public void setRes_data(String res_data) {
        this.res_data = res_data;
    }

    public String getRes_error() {
        return res_error;
    }

    public void setRes_error(String res_error) {
        this.res_error = res_error;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("partner", partner)
                .add("req_id", req_id)
                .add("sec_id", sec_id)
                .add("service", service)
                .add("v", v)
                .add("format", format)
                .add("sign", sign)
                .add("res_data", res_data)
                .add("res_error", res_error)
                .add("request_token", request_token)
                .add("code", code)
                .add("sub_code", sub_code)
                .add("msg", msg)
                .add("detail", detail)
                .toString();
    }
}
