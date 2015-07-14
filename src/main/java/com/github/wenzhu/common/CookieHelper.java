package com.github.wenzhu.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
  * HTTP Cookie:存储会话信息
  * 名称和值传送时必须是经过RUL编码的
  * cookie绑定在指定的域名下，非本域无法共享cookie，但是可以是在主站共享cookie给子站
  * cookie有一些限制：比如IE6 & IE6- 限定在20个；IE7 50个；Opear 30个...所以一般会根据【必须】需求才设定cookie
  * cookie的名称不分大小写；同时建议将cookie URL编码；路径是区分cookie在不同情况下传递的好方式；带安全标志cookie
  *     在SSL情况下发送到服务器端，http则不会。建议针对cookie设置expires、domain、 path；每个cookie小于4KB
  * 对cookie的封装，采取getter、setter方式
  */
public final class CookieHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CookieHelper.class);

	 /**
     * 添加cookie
     * @param response HttpServletResponse 响应
     * @param name cookie的名称
     * @param value cookie的值
     * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭(会话结束)而清除)
     * @param path 若有指定 cookie 将只发送到对该路径的请求中。如果未设置该属性，则使用应用程序的路径
     * @param domain 若有指定 cookie 将被发送到对该域的请求中去
     */
    public static void addCookie(HttpServletResponse response, String name, String value, Integer maxAge,String path,String domain) {        
         Assert.notNull(response,"response must not be null");
         Assert.hasText(name,"name must has text");
         Assert.hasText(value,"value must has text");
         try{
        	 name = URLEncoder.encode(name,"UTF-8");
        	 Cookie cookie = new Cookie(name, value);
        	 if (maxAge > 0) {
				 cookie.setMaxAge(maxAge);
			 }
        	 if(StringUtils.isNotEmpty(path)) {
				 cookie.setPath(path);
			 }
             if(StringUtils.isNotEmpty(domain)) {
				 cookie.setDomain(domain);
			 }
             response.addCookie(cookie);
             
         } catch (Exception e) {
        	 LOG.error("addCookie URLEncoder encode fail,[{}]",e.getMessage());
        	 throw new RuntimeException();
         }
    	
    }
    /**
     * 清空 Cookie
     * @param response
     * @param name cookie的名称
     * @param path 若有指定 cookie 将只发送到对该路径的请求中。如果未设置该属性，则使用应用程序的路径
     * @param domain 若有指定 cookie 将被发送到对该域的请求中去
     */
    public static void removeCookie(HttpServletResponse response, String name,String path,String domain) {        
       Assert.notNull(response,"response must not be null");
       Assert.hasText(name,"name must has text");
       try {
			name = URLEncoder.encode(name,"UTF-8");
			Cookie cookie = new Cookie(name, null);
			cookie.setMaxAge(0);
			 if(StringUtils.isNotEmpty(path))
	    		 cookie.setPath(path);
	         if(StringUtils.isNotEmpty(domain))
	        	 cookie.setDomain(domain);
	        response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			 LOG.error("removeCookie URLEncoder encode fail,[{}]",e.getMessage());
			 throw new RuntimeException(e);
		}
    }
    
    /**
     * 获取cookie的值
     * @param request HttpServletRequest 请求
     * @param name cookie的名称
     * @return String
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
    	Assert.notNull(request,"request must not be null");
    	Assert.hasText(name,"name must has text");
		try {
			name = URLEncoder.encode(name,"UTF-8");
			
			Map<String, Cookie> cookieMap = CookieHelper.readCookieMap(request);
	        if(cookieMap.containsKey(name)){
	            Cookie cookie = (Cookie)cookieMap.get(name);
	            return cookie.getValue();
	        }
		} catch (UnsupportedEncodingException e) {
			LOG.error("getCookieByName URLEncoder encode fail,[{}]",e.getMessage());
			throw new RuntimeException(e);
		}
    	return null;
    }
    /**
     * 读取cookie 对象
     * @param request HttpServletRequest 请求
     * @return Map  返回键值对 Map 集合
     */
    public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
    	Assert.notNull(request,"request must be not null");
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
        }
        return cookieMap;
    }
}
