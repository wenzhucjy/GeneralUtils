package com.github.mysite.common.initbinder;

import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

/**
 * <p>与spring mvc的@InitBinder结合</p>
 * 
 * 自定义属性编辑器,只要继承 PropertyEditorSupport<br>
 * 
 * 用于防止XSS攻击(Cross-Site Scripting ~ 跨站点脚本(XSS)是一种计算机安全漏洞通常发现在Web应用程序)
 * 
 * @author jy.chen
 * 
 */
public class StringEscapeEditor extends PropertiesEditor {
	/**
	 * 编码HTML
	 */
	private boolean escapeHTML;
	/**
	 * 编码JavaScript
	 */
	private boolean escapeJavaScript;
	/**
	 * 默认构造方法
	 */
	public StringEscapeEditor() {
		
	}
	/**
	 * 带参数构造方法
	 * @param escapeHTML
	 * @param escapeJavaScript
	 */
	public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript) {
		this.escapeHTML = escapeHTML;
		this.escapeJavaScript = escapeJavaScript;
	}
	/**
	 * override getAsText method
	 */
	@Override
	public String getAsText() {
		Object value = getValue();
		return value != null ? value.toString() : "";
	}
	/**
	 * override setAsText method , then register
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		
		 if (!StringUtils.hasText(text)) {  
             return;  
         }else {
			String value = text;
			if (escapeHTML) {
				value = HtmlUtils.htmlEscape(value);
			}
			if (escapeJavaScript) {
				value = JavaScriptUtils.javaScriptEscape(value);
			}
			super.setValue(value);
		}
	}

}
