package com.github.mysite.web.wenzhu.common.uuid.dao;

/**
 * description:简单工厂
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:47
 */
public class SnCodeFactory {

    private SnCodeFactory() {

    }

    public static SnCodeDao generateSnCodeDao() {
        return new FileImpl();
    }

}
