package com.github.mysite.common.payonline.util;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * description:封装Request参数
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-11-30 14:47
 */
public class RequestHelper {

    /**
     * 把request对象封装到bean对象中
     *
     * @param request   HttpServletRequest
     * @param beanClass Class<T>
     * @return T
     */
    public static <T> T request2Bean(HttpServletRequest request, Class<T> beanClass) {

        try {
            // 1.创建要封装数据的bean
            T bean = beanClass.newInstance();
            // 2.request数据整到bean中
            Enumeration<?> e = request.getParameterNames();
            while (e.hasMoreElements()) {
                String name = (String) e.nextElement();
                String value = request.getParameter(name);
                // 3.反射技术,设置bean的name及value属性
                BeanUtils.setProperty(bean, name, value);
            }
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 封装 Request 参数到Map 集合中
     *
     * @param request HttpServletRequest
     * @return  Map
     */
    public static Map<String, String> buildMapParam(HttpServletRequest request) {
        //获取支付宝GET或POST过来反馈参数
        Map<String, String> params = Maps.newHashMap();
        Map<?, ?> requestParams = request.getParameterMap();
        if (!requestParams.isEmpty()) {
            for (Object o : requestParams.keySet()) {
                String name = (String) o;
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
        }
        return params;
    }

    /**
     * 封装支付请求的form表单,自动提交
     *
     * @param sPara 待组装的map集合数据
     * @param strMethod get或post请求方法
     * @param strAction 表单提交的action地址
     * @param strButtonName 表单提交的按钮名称
     * @return  form表单数据
     */
    public static String buildPaymentRequestPara(Map<String, String> sPara, String strMethod, String strAction, String strButtonName) {
        // 待请求参数数组
        List<String> keys = new ArrayList<>(sPara.keySet());

        StringBuilder sbHtml = new StringBuilder();

        sbHtml.append("<form id=\"paymentsubmit\"  action=\"").append(strAction).append("\" method=\"").append(strMethod).append("\">");

        for (String key : keys) {
            String value = sPara.get(key);
            sbHtml.append("<input type=\"hidden\" name=\"").append(key).append("\" value=\"").append(value).append("\"/>");
        }
        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"").append(strButtonName).append("\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['paymentsubmit'].submit();</script>");
        return sbHtml.toString();
    }


    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sPara	请求参数数组
     * @param strMethod	提交方式。两个值可选：post、get
     * @param strAction 请求的表单的Action URL
     * @return 提交表单HTML文本
     */
    public static String buildRequestPara(Map<String, String> sPara, String strMethod,String strAction) {
        // 待请求参数数组
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuilder sbHtml = new StringBuilder();

        sbHtml.append("<form id=\"ccbsubmit\" name=\"ccbsubmit\"  action=\"").append(strAction).append("\" method=\"").append(strMethod).append("\">");

        for (String name : keys) {
            String value = sPara.get(name);
            if (StringUtils.equals(name, "macSrc")) {
                sbHtml.append("<input type=\"hidden\" id=\"").append(name).append("\" value=\"").append(value).append("\"/>");
            } else {
                sbHtml.append("<input type=\"hidden\" name=\"").append(name).append("\" value=\"").append(value).append("\"/>");
            }
        }

        sbHtml.append("</form>");
        return sbHtml.toString();
    }


    /**
     * 获取项目的前缀，http://ip:port/ctx
     * @param request   HttpServletRequest
     * @return 项目路径前缀
     */
    public static String prefixCTX(HttpServletRequest request) {
        return StringUtils.substringBeforeLast(request.getRequestURL().toString(), request.getContextPath()).concat(request.getContextPath());
    }

}
