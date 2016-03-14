package com.github.mysite.common.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: Excel注解定义
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-11 14:06
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     * 导出字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
     */
    String value() default "";

    /**
     * 导出字段标题
     */
    String title();

    /**
     * 字段类型（0：导出导入；1：仅导出；2：仅导入），暂时只用到导出
     */
    int type() default 0;

    /**
     * 导出字段对齐方式（0：自动；1：靠左；2：居中；3：靠右）
     */
    int align() default 0;

    /**
     * 导出字段字段排序（升序）
     */
    int sort() default 0;

    /**
     * 如果是字典类型，请设置字典的type值，暂保留字段
     */
    String dictType() default "";

    /**
     * 反射类型
     */
    Class<?> fieldType() default Class.class;

}

