package com.github.wenzhu.initbinder;


import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 基础控制器
 * 
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * 
 * @author jy.chen
 * 
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {
	
	public static final String[] DATE_PATTERNS = { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };
	
	/**
	 * Spring 的 initBinder：绑定表单复杂属性,通过重写initBinder方法注册,initBinder有一个入参binder就是用来注册属性编辑器,<br>
	 * 它是ServletRequestDataBinder类型，查看API，有一个来自父类DataBinder的方法 —— registerCustomEditor
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERNS[4]);
		dateFormat.setLenient(false);
		//设置是否忽略绑定,默认true,关闭这个执行,所有绑定参数必须有一个目标对象匹配字段
		binder.setIgnoreInvalidFields(true);
		binder.setIgnoreUnknownFields(true);
		//binder.setAllowedFields("startDate");
		
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
		
		binder.registerCustomEditor(int.class, new IntegerEditor());   
		binder.registerCustomEditor(double.class, new DoubleEditor()); 
		
		//The following is the CustomNumberEditor
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(false);
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
		 
	}
}
