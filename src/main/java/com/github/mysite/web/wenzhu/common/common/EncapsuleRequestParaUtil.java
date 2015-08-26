package com.github.mysite.web.wenzhu.common.common;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * description:封装Request参数
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/14 - 14:47
 */
public class EncapsuleRequestParaUtil {

    /**
     * 把request对象封装到bean对象中
     *
     * @param request   HttpServletRequest
     * @param beanClass Class<T>
     * @return
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
     * @param request
     * @return
     */
    public static Map<String, String> buildMapParam(HttpServletRequest request) {
        //获取支付宝GET或POST过来反馈参数
        Map<String, String> params = Maps.newHashMap();
        Map<?, ?> requestParams = request.getParameterMap();
        if (!requestParams.isEmpty()) {
            for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
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
     * 建行建立请求，以表单HTML形式构造（默认）
     *
     * @param sPara     请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strAction 请求的表单的Action URL
     * @return 提交表单HTML文本
     */
    public static String buildRequestPara(Map<String, String> sPara, String strMethod, String strAction) {
        // 待请求参数数组
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"ccbsubmit\" name=\"ccbsubmit\"  action=\"" + strAction
                + "\" method=\"" + strMethod + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);
            if (StringUtils.equals(name, "macSrc")) {
                sbHtml.append("<input type=\"hidden\" id=\"" + name + "\" value=\"" + value + "\"/>");
            } else {
                sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
            }
        }

        sbHtml.append("</form>");
        return sbHtml.toString();
    }

    /**
     * 封装支付请求的form表单,自动提交
     *
     * @param sPara
     * @param strMethod
     * @param strAction
     * @param strButtonName
     * @return
     */
    public static String buildPaymentRequestPara(Map<String, String> sPara, String strMethod, String strAction, String strButtonName) {
        // 待请求参数数组
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"paymentsubmit\"  action=\"" + strAction
                + "\" method=\"" + strMethod + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['paymentsubmit'].submit();</script>");

        return sbHtml.toString();
    }

}
